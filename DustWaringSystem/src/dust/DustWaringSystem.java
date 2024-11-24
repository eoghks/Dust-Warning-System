package dust;

import java.util.List;
import java.util.Properties;

import domain.vo.DustDataVo;
import service.FileService;
import service.WarningService;

public class DustWaringSystem {
	public static void main(String[] args) {
		FileService fileService = new FileService();
		WarningService warningService = new WarningService();
		//Properties 읽기(팡리 경로 및 DB 정보)
		Properties properties = fileService.readProperties();
		//JSON 파일 읽기
		List<DustDataVo> datas = fileService.readJsonFile(properties);
		//경고 발령 및 이력 저장
		warningService.createWarning(datas);
	}



}
