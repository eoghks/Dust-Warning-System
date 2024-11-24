package config;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import domain.constant.ApplicationConst;

public class MyBatisConnectionFactory {
	private static SqlSessionFactory sqlSessionFactory;
	static {
		try {
			Reader reader =  Resources.getResourceAsReader(ApplicationConst.MyabtisConfigPath);
			if(sqlSessionFactory == null) {
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			}
		} catch(Exception e) {
			System.out.println("DB 연결 오류");
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
}
