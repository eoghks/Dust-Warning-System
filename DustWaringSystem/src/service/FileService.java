package service;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;

import domain.constant.PropertiesEnum;

public class FileService {
	public List<Object> readJsonFile(Properties properties) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			File file = new File(properties.getProperty(PropertiesEnum.JsonFilePath.getStr()));
		} catch(Exception e) {
			System.out.println("Read Json File Error");
			e.printStackTrace();
		}
		return new ArrayList<>();
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
