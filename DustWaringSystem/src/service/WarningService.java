package service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import domain.dto.InspectionHistoryDto;
import domain.dto.WarningHistoryDto;
import domain.vo.DustDataVo;

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
					System.out.println("점검");
					prev10 =0;
					prev25 =0;
				} else {
					int cur10 = vo.getPm10();
					int cur25 = vo.getPm25();
					if(prev25 >= 150 && cur25 >= 150) {
						WarningHistoryDto dto = new WarningHistoryDto(vo.getDistrictName(), vo.getDate(), 1);
						System.out.println(vo.getDistrictName() + "초미세먼지 경보");
					} else if(prev10 >= 300 && cur10 >= 300) {
						WarningHistoryDto dto = new WarningHistoryDto(vo.getDistrictName(), vo.getDate(), 2);
						System.out.println(vo.getDistrictName() +"미세먼지 경보");
					} else if(prev25 >= 75 && cur25 >= 75) {
						WarningHistoryDto dto = new WarningHistoryDto(vo.getDistrictName(), vo.getDate(), 3);
						System.out.println(vo.getDistrictName() +"초미세먼지 주의보");
					} else if(prev10 >= 150 && cur10 >= 150) {
						WarningHistoryDto dto = new WarningHistoryDto(vo.getDistrictName(), vo.getDate(), 4);
						System.out.println(vo.getDistrictName() +"미세먼지 주의보");
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
}
