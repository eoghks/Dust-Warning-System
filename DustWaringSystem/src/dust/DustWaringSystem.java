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
			//JSON 파일 읽기
			List<DustDataVo> datas = fileService.readJsonFile();
			//JWT 발급 기능 추가
			String jwtToken = sendService.getJwtToken();
			warningService.setToken(jwtToken);
			//경고 발령 및 이력 저장
			warningService.createWarning(datas);
			//DB 조회 및 출력
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
