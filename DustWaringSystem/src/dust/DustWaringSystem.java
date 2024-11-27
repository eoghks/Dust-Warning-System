package dust;

import java.util.List;
import java.util.Scanner;

import model.vo.DustDataVo;
import service.FileService;
import service.SendService;
import service.WarningService;

public class DustWaringSystem {
	public static void main(String[] args) {
		FileService fileService = new FileService();
		WarningService warningService = new WarningService();
		SendService sendService = new SendService();
		Scanner sc = new Scanner(System.in);
		try {
			List<DustDataVo> datas = fileService.readJsonFile();
			String jwtToken = sendService.getJwtToken();
			warningService.setToken(jwtToken);
			warningService.createWarning(datas);
			warningService.selectHistory();
			System.out.println("작업 완료");
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}finally {
			sc.close();
		}
	}
}
