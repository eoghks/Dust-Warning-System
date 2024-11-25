package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import model.constant.ApplicationConst;
import model.dto.InspectionHistoryDto;

public class InspectionHistoryDao {
	public List<InspectionHistoryDto> selectAllInsepctionHistory(SqlSession sqlSession) {
		return sqlSession.selectList(ApplicationConst.Facade+".selectAllInspectionHistory");
	}

	public void deleteAllInspectionHistory(SqlSession sqlSession) {
		sqlSession.selectList(ApplicationConst.Facade+".deleteAllInspectionHistory");
	}

	public int insertInspectionHistory(SqlSession sqlSession, InspectionHistoryDto dto) {
		return sqlSession.insert(ApplicationConst.Facade+".insertInspectionHistory", dto);
	}
}
