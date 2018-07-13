package test.com.fitMe.Sleep.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import test.com.fitMe.Heartbeat.model.HeartbeatVO;

@Repository
public class SleepDAOImpl implements SleepDAO {
	private static final Logger logger = LoggerFactory.getLogger(SleepDAOImpl.class);

	@Autowired
	private SqlSession sqlSession;

	public SleepDAOImpl() {
		logger.info("UserDAOImpl()....");
	}
	


	@Override
	public int sl_insert(SleepVO vo) {
		int a=sqlSession.insert("sl_insert",vo);
		return a;
	}

	@Override
	public List<SleepVO> selectAll() {
		logger.info("selectAll()");
		List<SleepVO> list = sqlSession.selectList("sl_selectAll");
		return list;
	}


}
