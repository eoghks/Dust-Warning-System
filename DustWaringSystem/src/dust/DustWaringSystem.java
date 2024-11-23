package dust;

import java.util.List;
import java.util.Properties;

import service.FileService;

public class DustWaringSystem {
	public static void main(String[] args) {
		FileService fileService = new FileService();
		Properties properties = fileService.readProperties();
		System.out.println("파일 읽기 시작");
		List<Object> datas = fileService.readJsonFile(properties);
		System.out.println("경고 판단 시작");

		System.out.println("이력 저장");

	}



}
