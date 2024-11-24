package service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import domain.constant.ApplicationConst;
import domain.vo.DustDataVo;

public class FileService {
	public List<DustDataVo> readJsonFile() throws Exception{
		System.out.println("JSON 파일 읽기");
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		List<DustDataVo> list = new ArrayList<>();
		try {
			File file = new File(ApplicationConst.JsonFilePath);
			list = mapper.readValue(file, new TypeReference<List<DustDataVo>>() {});
		} catch(Exception e) {
			System.out.println("Read Json File Error");
			throw e;
		}
		return list;
	}
}
