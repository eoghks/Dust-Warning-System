package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import domain.constant.ApplicationConst;
import domain.dto.WarningHistoryDto;

public class WarningDao {
	public List<WarningHistoryDto> selectWarningHistoryList(SqlSession sqlSession) {
		return sqlSession.selectList(ApplicationConst.Facade+".selectAllWarningHistory");
	}
}
