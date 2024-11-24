package service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import config.MyBatisConnectionFactory;
import dao.InspectionHistoryDao;
import dao.WarningHistoryDao;
import model.dto.WarningHistoryDto;

public class MybatisService {
	private SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
	private WarningHistoryDao warningDao = new WarningHistoryDao();
	private InspectionHistoryDao InspectionDao = new InspectionHistoryDao();

	public List<WarningHistoryDto> getHistory() throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<WarningHistoryDto> list = new ArrayList<>();
		try {
			list = warningDao.selectWarningHistoryList(sqlSession);
		}catch(Exception e) {
			System.out.println("DB 조회 오류");
			throw e;
		} finally {
			sqlSession.close();
		}
		return list;
	}

	public void deleteAllWarningHistory() throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			warningDao.deleteAllWarningHistory(sqlSession);
		}catch(Exception e) {
			System.out.println("Warning History Delete Error");
			throw e;
		} finally {
			sqlSession.close();
		}
	}

	public void deleteAllInspectionHistory() throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
			InspectionDao.deleteAllInspectionHistory(sqlSession);
		}catch(Exception e) {
			System.out.println("Inspection History Delete Error");
			throw e;
		} finally {
			sqlSession.close();
		}
	}
}
