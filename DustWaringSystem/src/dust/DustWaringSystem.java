package dust;

import java.util.List;

import model.vo.DustDataVo;
import service.FileService;
import service.SendService;
import service.WarningService;

public class DustWaringSystem {
	public static void main(String[] args) {
		FileService fileService = new FileService();
		WarningService warningService = new WarningService();
		try {
			SendService sendService = SendService.getInstance();
			List<DustDataVo> datas = fileService.readJsonFile();
			warningService.createWarning(datas);
			warningService.selectHistory();
			System.out.println("작업 완료");
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
