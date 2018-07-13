package test.com.fitMe.Dumbbell.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DumbbellDAOImpl implements DumbbellDAO {

	
	private static final Logger logger = LoggerFactory.getLogger(DumbbellDAOImpl.class);
	
	@Autowired
	private SqlSession sqlSession;

	

	@Override
	public int d_delete(DumbbellVO vo) {
		int a;
		a = sqlSession.delete("d_delete", vo);
		return a;
	}

	@Override
	public List<DumbbellVO> d_selectAll() {
		
		List<DumbbellVO> list = sqlSession.selectList("d_selectAll");
		return list;
	}

	@Override
	public int d_insert(DumbbellVO vo) {
		int a;
		a = sqlSession.insert("d_insert",vo);
		return a;
	}

	@Override
	public int d_update(DumbbellVO vo) {
		int a;
		a = sqlSession.update("d_update",vo);
		return a;
	}

	@Override
	public int d_selectOne(DumbbellVO vo) {
		int a;
		a = sqlSession.selectOne("d_selectOne",vo);
		return a;
	}

}
