package test.com.fitMe.Heartbeat.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import test.com.fitMe.HeartbeatController;

@Repository
public class HeartbeatDAOImpl implements HeartbeatDAO {
	
	private int a;
	private static final Logger logger = LoggerFactory.getLogger(HeartbeatController.class);
	
	@Autowired
	private SqlSession sqlSession;


	@Override
	public List<HeartbeatVO> h_selectAll() {
		List<HeartbeatVO> list = sqlSession.selectList("h_selectAll");
		return list;
	}

	@Override
	public int h_insert(HeartbeatVO vo) {
		a=sqlSession.insert("h_insert",vo);
		return a;
	}


}
