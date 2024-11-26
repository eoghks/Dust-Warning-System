package service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.constant.RateEnum;
import model.constant.SeoulDistrictEnum;
import model.dto.InspectionHistoryDto;
import model.dto.WarningHistoryDto;
import model.vo.DustDataVo;

public class WarningService {
	private MybatisService mybatisService = new MybatisService();

	public void createWarning(List<DustDataVo> datas) throws Exception {
		//기존 이력 삭제
		deleteHistory();

		datas = datas.stream().sorted(Comparator.comparing(DustDataVo::getDate)).collect(Collectors.toList());
		Map<String, Integer> pm10Map = initDistrictMap();
		Map<String, Integer> pm25Map = initDistrictMap();

		for(DustDataVo vo: datas) {
			String district = vo.getDistrictName();
			int prev10 = pm10Map.get(district);
			int prev25 = pm25Map.get(district);
			if(vo.getPm10() == null || vo.getPm25() == null) {
				InspectionHistoryDto dto = new InspectionHistoryDto(district, vo.getDate());
				mybatisService.insertInspectionHistory(dto);
				pm10Map.put(district, 0);
				pm25Map.put(district, 0);
			} else {
				int cur10 = vo.getPm10();
				int cur25 = vo.getPm25();
				if(prev25 >= 150 && cur25 >= 150) {
					createWarningHistory(vo, RateEnum.ULTRA_FINE_DUST_WARNING.getRate());
				} else if(prev10 >= 300 && cur10 >= 300) {
					createWarningHistory(vo, RateEnum.FINE_DUST_WARNING.getRate());
				} else if(prev25 >= 75 && cur25 >= 75) {
					createWarningHistory(vo, RateEnum.ULTRA_FINE_DUST_ADVISORY.getRate());
				} else if(prev10 >= 150 && cur10 >= 150) {
					createWarningHistory(vo, RateEnum.FINE_DUST_ADVISORY.getRate());
				}
				pm10Map.put(district, cur10);
				pm25Map.put(district, cur25);
			}
		}
	}

	private void deleteHistory() throws Exception {
		System.out.println("경보 이력 삭제");
		mybatisService.deleteAllWarningHistory();
		System.out.println("점검 이력 삭제");
		mybatisService.deleteAllInspectionHistory();
	}

	private Map<String, Integer> initDistrictMap() {
		Map<String, Integer> map = new HashMap<>();
		for(SeoulDistrictEnum district:SeoulDistrictEnum.values()) {
			map.put(district.getKoreanName(), 0);
		}
		return map;
	}

	private void createWarningHistory(DustDataVo vo ,Integer rate) throws Exception{
		WarningHistoryDto dto = new WarningHistoryDto(vo.getDistrictName(), vo.getDate(), rate);
		mybatisService.insertWarningHistory(dto);
		//이력 전송 추가 요구 사항(전송된 이력에 대한 성공 메시지 받을지 여부는 추후 결정)
	}

	public void selectHistory() throws Exception {
		try (Scanner sc = new Scanner(System.in)) {
			List<InspectionHistoryDto> inspection = mybatisService.selectAllInspectionHistory();
			List<WarningHistoryDto> warning = mybatisService.selectAllWarningHistory();
			System.out.printf("점검 이력: %d, 경고 이력: %d개가 DB에서 조회되었습니다.\n", inspection.size(), warning.size());
			System.out.println("상세 내역을 보시겠습니까? (Y/N)");
			String res = sc.next();
			if(res.equals("Y") || res.equals("y")) {
				System.out.println("점검 내역");
				for(InspectionHistoryDto dto: inspection) {
					System.out.printf("점검 >>> 측정소: %-5s 점검 시간: %-17s\n", dto.getDistrictName(), dto.getDate());
				}

				System.out.println("경고 내역");
				for(WarningHistoryDto dto: warning) {
					System.out.printf("경고 >>> 측정소: %-5s 경고 시간: %-17s 발령 단계: %s\n", dto.getDistrictName(), dto.getDate(), RateEnum.convertKoreanName(dto.getRate()));
				}
			}
		} catch(Exception e) {
			System.out.println("DB 조회 오류");
			throw e;
		}
	}
}
