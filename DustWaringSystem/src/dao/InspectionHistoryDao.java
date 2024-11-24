package dao;

import org.apache.ibatis.session.SqlSession;

import model.constant.ApplicationConst;

public class InspectionHistoryDao {
	public void deleteAllInspectionHistory(SqlSession sqlSession) {
		sqlSession.selectList(ApplicationConst.Facade+".deleteAllInspectionHistory");
	}
}
