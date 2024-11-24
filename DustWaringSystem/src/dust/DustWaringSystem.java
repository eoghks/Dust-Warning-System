package dust;

import java.util.List;

import domain.dto.WarningHistoryDto;
import service.MybatisService;

public class DustWaringSystem {
	public static void main(String[] args) {
		/*
		FileService fileService = new FileService();
		WarningService warningService = new WarningService();
		//JSON 파일 읽기
		List<DustDataVo> datas = fileService.readJsonFile();
		//경고 발령 및 이력 저장
		warningService.createWarning(datas);*/
		MybatisService w = new MybatisService();
		List<WarningHistoryDto> test = w.getHistory();

		for(WarningHistoryDto dto: test) {
			System.out.println(dto.getDistrictName());
		}
	}



}
