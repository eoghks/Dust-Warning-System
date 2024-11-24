package dust;

import java.util.List;

import domain.vo.DustDataVo;
import service.FileService;
import service.WarningService;

public class DustWaringSystem {
	public static void main(String[] args) {
		FileService fileService = new FileService();
		WarningService warningService = new WarningService();
		//JSON 파일 읽기
		List<DustDataVo> datas = fileService.readJsonFile();
		//경고 발령 및 이력 저장
		warningService.createWarning(datas);
	}



}
