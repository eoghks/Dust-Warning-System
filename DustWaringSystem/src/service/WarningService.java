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
	private SendService sendService = SendService.getInstance();
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
		mybatisService.deleteAllWarningHistory();
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
		sendService.sendWarningHistory(dto);
	}

	public void selectHistory() throws Exception {
		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("DB 저장이 완료되었습니다. 저장 내역을 보시겠습니까?(저장 내역을 보시려면 Y 또는 y를 입력하세요.");
			String answer = sc.next();
			if(answer.equals("Y") || answer.equals("y")) {
				List<InspectionHistoryDto> inspection = mybatisService.selectAllInspectionHistory();
				List<WarningHistoryDto> warning = mybatisService.selectAllWarningHistory();
				System.out.printf("점검 이력 %d건, 경고 이력 %d건이 DB에 저장되었습니다.\n", inspection.size(), warning.size());
				System.out.println("상세 내역을 보시겠습니까? (상세 내역을 보시려면 Y 또는 y를 입력하세요.)");
				answer= sc.next();
				if(answer.equals("Y") || answer.equals("y")) {
					System.out.println("-------------점검 내역-------------");
					for(InspectionHistoryDto dto: inspection) {
						System.out.printf("%-16s %-4s 점검\n", dto.getDate(), dto.getDistrictName());
					}

					System.out.println("-------------경고 내역-------------");
					for(WarningHistoryDto dto: warning) {
						System.out.printf("%-16s %-4s %s\n", dto.getDate(), dto.getDistrictName(), RateEnum.convertKoreanName(dto.getRate()));
					}
				}
			}
		} catch(Exception e) {
			System.out.println("DB 조회 오류");
			throw e;
		}
	}
}
