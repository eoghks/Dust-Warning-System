package service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import config.MyBatisConnectionFactory;
import dao.WarningDao;
import domain.dto.WarningHistoryDto;

public class MybatisService {
	private SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
	private WarningDao warningDao = new WarningDao();

	public List<WarningHistoryDto> getHistory() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<WarningHistoryDto> list = new ArrayList<>();
		try {
			list = warningDao.selectWarningHistoryList(sqlSession);
		}catch(Exception e) {
			System.out.println("DB 조회 오류");
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return list;
	}
}
