package test.com.fitMe.Barbell.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BarbellDAOImpl implements BarbellDAO {

	private static final Logger logger = LoggerFactory.getLogger(BarbellDAOImpl.class);
	
	@Autowired
	private SqlSession sqlSession;

	

	@Override
	public int b_delete(BarbellVO vo) {
		int a;
		a = sqlSession.delete("b_delete", vo);
		return a;
	}

	@Override
	public List<BarbellVO> b_selectAll() {
		
		List<BarbellVO> list = sqlSession.selectList("b_selectAll");
		return list;
	}

	@Override
	public int b_insert(BarbellVO vo) {
		int a;
		a = sqlSession.insert("b_insert",vo);
		return a;
	}

	@Override
	public int b_update(BarbellVO vo) {
		int a;
		a = sqlSession.update("b_update",vo);
		return a;
	}

	@Override
	public int b_selectOne(BarbellVO vo) {
		int a;
		a = sqlSession.selectOne("b_selectOne",vo);
		return a;
	}


}
