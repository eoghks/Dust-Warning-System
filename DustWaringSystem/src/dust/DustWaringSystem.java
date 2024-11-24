package dust;

import java.util.List;

import domain.vo.DustDataVo;
import service.FileService;
import service.WarningService;

public class DustWaringSystem {
	public static void main(String[] args) {
		FileService fileService = new FileService();
		WarningService warningService = new WarningService();
		try {
			//JSON 파일 읽기
			List<DustDataVo> datas = fileService.readJsonFile();
			//경고 발령 및 이력 저장
			warningService.createWarning(datas);
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		/* DB 연결 테스트
		MybatisService w = new MybatisService();
		List<WarningHistoryDto> test = w.getHistory();

		for(WarningHistoryDto dto: test) {
			System.out.println(dto.getDistrictName());
		}*/
	}



}
