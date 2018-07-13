package test.com.fitMe.Walk.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class WalkDAOImpl implements WalkDAO {
	
	
	private static final Logger logger = LoggerFactory.getLogger(WalkDAOImpl.class);

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int w_insert(WalkVO vo) {
		int a;
		a=sqlSession.insert("w_insert",vo);
		return a;
	}



	@Override
	public int w_delete(WalkVO vo) {
		int a;
		a=sqlSession.delete("w_delete", vo);
		return a;
	}

	@Override
	public List<WalkVO> w_selectAll() {
		
		List<WalkVO> list=sqlSession.selectList("w_selectAll");
		return list;
	}



	@Override
	public int w_update(WalkVO vo) {
		int a;
		a=sqlSession.update("w_update",vo);
		return a;
	}



	@Override
	public int w_selectOne(WalkVO vo) {
		int a;
		a=sqlSession.selectOne("w_selectOne",vo);
		return a;
	}


}
