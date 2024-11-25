package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import model.constant.ApplicationConst;
import model.dto.WarningHistoryDto;

public class WarningHistoryDao {
	public List<WarningHistoryDto> selectAllWarningHistory(SqlSession sqlSession) {
		return sqlSession.selectList(ApplicationConst.Facade+".selectAllWarningHistory");
	}

	public void deleteAllWarningHistory(SqlSession sqlSession) {
		sqlSession.selectList(ApplicationConst.Facade+".deleteAllWarningHistory");
	}

	public int insertWarningHistory(SqlSession sqlSession, WarningHistoryDto dto) {
		return sqlSession.insert(ApplicationConst.Facade+".insertWarningHistory", dto);
	}
}
