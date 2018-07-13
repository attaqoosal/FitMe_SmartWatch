package test.com.fitMe.Run.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RunDAOImpl implements RunDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(RunDAOImpl.class);

	@Autowired
	private SqlSession sqlSession;
	


	@Override
	public int r_delete(RunVO vo) {
		int a;
		a=sqlSession.delete("r_delete",vo);
		return a;
	}

	@Override
	public List<RunVO> r_selectAll() {
		List<RunVO> list=sqlSession.selectList("r_selectAll");
		return list;
	}

	@Override
	public int r_insert(RunVO vo) {
		int a;
		a=sqlSession.insert("r_insert",vo);
		return a;
	}

	@Override
	public int r_update(RunVO vo) {
		int a;
		a=sqlSession.update("r_update",vo);
		return a;
	}

	@Override
	public int r_selectOne(RunVO vo) {
		int a;
		a=sqlSession.selectOne("r_selectOne",vo);
		return a;
	}



}
