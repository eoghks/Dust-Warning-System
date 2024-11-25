package service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.dto.InspectionHistoryDto;
import model.dto.WarningHistoryDto;
import model.vo.DustDataVo;

public class WarningService {
	private MybatisService mybatisService = new MybatisService();

	public void createWarning(List<DustDataVo> datas) throws Exception {
		//기존 이력 삭제
		deleteHistory();

		Map<String, List<DustDataVo>> map = datas.stream()
				.sorted(Comparator.comparing(DustDataVo::getDate))
				.collect(Collectors.groupingBy(DustDataVo::getDistrictName));

		for(List<DustDataVo> list: map.values()) {
			int prev10 = 0;
			int prev25 = 0;
			for(DustDataVo vo: list) {
				if(vo.getPm10() == null || vo.getPm25() == null) {
					InspectionHistoryDto dto = new InspectionHistoryDto(vo.getDistrictName(), vo.getDate());
					mybatisService.insertInspectionHistory(dto);
					prev10 =0;
					prev25 =0;
				} else {
					int cur10 = vo.getPm10();
					int cur25 = vo.getPm25();
					if(prev25 >= 150 && cur25 >= 150) {
						WarningHistoryDto dto = new WarningHistoryDto(vo.getDistrictName(), vo.getDate(), 1);
						mybatisService.insertWarningHistory(dto);
					} else if(prev10 >= 300 && cur10 >= 300) {
						WarningHistoryDto dto = new WarningHistoryDto(vo.getDistrictName(), vo.getDate(), 2);
						mybatisService.insertWarningHistory(dto);
					} else if(prev25 >= 75 && cur25 >= 75) {
						WarningHistoryDto dto = new WarningHistoryDto(vo.getDistrictName(), vo.getDate(), 3);
						mybatisService.insertWarningHistory(dto);
					} else if(prev10 >= 150 && cur10 >= 150) {
						WarningHistoryDto dto = new WarningHistoryDto(vo.getDistrictName(), vo.getDate(), 4);
						mybatisService.insertWarningHistory(dto);
					}
					prev10 = cur10;
					prev25 = cur25;
				}
			}
		}
	}

	private void deleteHistory() throws Exception {
		System.out.println("경보 이력 삭제");
		mybatisService.deleteAllWarningHistory();
		System.out.println("점검 이력 삭제");
		mybatisService.deleteAllInspectionHistory();
	}

	public void selectHistory() throws Exception {
		try (Scanner sc = new Scanner(System.in)) {
			List<InspectionHistoryDto> inspection = mybatisService.selectAllInspectionHistory();
			List<WarningHistoryDto> warning = mybatisService.selectAllWarningHistory();
			System.out.printf("점검 이력: %d, 경고 이력: %d이 DB에서 조회되었습니다.\n", inspection.size(), warning.size());
			System.out.println("상세 내역을 보시겠습니까? (Y/N)");
			String res = sc.next();
			if(res.equals("Y") || res.equals("y")) {
				System.out.println("점검 내역");
				for(InspectionHistoryDto dto: inspection) {
					System.out.printf("측정소: %s 점검 시간: %s\n", dto.getDistrictName(), dto.getDate());
				}

				System.out.println("경고 내역");
				for(WarningHistoryDto dto: warning) {
					System.out.printf("측정소: %s 경고 시간: %s 발령 단계: %d\n", dto.getDistrictName(), dto.getDate(), dto.getRate());
				}
			}
		} catch(Exception e) {
			System.out.println("DB 조회 오류");
			throw e;
		}
	}
}
