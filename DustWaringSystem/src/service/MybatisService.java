package service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import config.MyBatisConnectionFactory;
import dao.InspectionHistoryDao;
import dao.WarningHistoryDao;
import model.dto.InspectionHistoryDto;
import model.dto.WarningHistoryDto;

public class MybatisService {
	private SqlSessionFactory sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
	private WarningHistoryDao warningDao = new WarningHistoryDao();
	private InspectionHistoryDao InspectionDao = new InspectionHistoryDao();

	//Select
	public List<WarningHistoryDto> selectAllWarningHistory() throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<WarningHistoryDto> list = new ArrayList<>();
		try {
			list = warningDao.selectAllWarningHistory(sqlSession);
		}catch(Exception e) {
			System.out.println("DB 조회 오류");
			throw e;
		} finally {
			sqlSession.close();
		}
		return list;
	}

	public List<InspectionHistoryDto> selectAllInspectionHistory() throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<InspectionHistoryDto> list = new ArrayList<>();
		try {
			list = InspectionDao.selectAllInsepctionHistory(sqlSession);
		}catch(Exception e) {
			System.out.println("DB 조회 오류");
			throw e;
		} finally {
			sqlSession.close();
		}
		return list;
	}

	//Delete
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

	//Insert
	public void insertWarningHistory(WarningHistoryDto dto) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		try {
			warningDao.insertWarningHistory(sqlSession, dto);
		}catch(Exception e) {
			System.out.println("Warning History Insert Error");
			throw e;
		} finally {
			sqlSession.close();
		}
	}

	public void insertInspectionHistory(InspectionHistoryDto dto) throws Exception{
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		try {
			int test = InspectionDao.insertInspectionHistory(sqlSession, dto);
		}catch(Exception e) {
			System.out.println("Inspection History Insert Error");
			throw e;
		} finally {
			sqlSession.close();
		}
	}
}
