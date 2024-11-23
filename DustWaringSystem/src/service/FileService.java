package service;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import domain.constant.PropertiesEnum;
import domain.vo.DustDataVo;

public class FileService {
	public List<DustDataVo> readJsonFile(Properties properties) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		List<DustDataVo> list = new ArrayList<>();
		try {
			File file = new File(properties.getProperty(PropertiesEnum.JsonFilePath.getStr()));
			list = mapper.readValue(file, new TypeReference<List<DustDataVo>>() {});
		} catch(Exception e) {
			System.out.println("Read Json File Error");
			e.printStackTrace();
		}
		return list;
	}

	public Properties readProperties() {
		Properties properties = new Properties();
		try {
			FileReader reader= new FileReader(PropertiesEnum.PropertiesPath.getStr());
			properties.load(reader);
		} catch(Exception e) {
			System.out.println("Read Properties Error");
			e.printStackTrace();
		}
		return properties;
	}
}
