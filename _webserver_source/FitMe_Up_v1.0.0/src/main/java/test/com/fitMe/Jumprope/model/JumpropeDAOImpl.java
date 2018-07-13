package test.com.fitMe.Jumprope.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class JumpropeDAOImpl implements JumpropeDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(JumpropeDAOImpl.class);

	@Autowired
	private SqlSession sqlSession;
	


	@Override
	public int delete(JumpropeVO vo) {
		int a;
		a = sqlSession.delete("j_delete", vo);
		return a;
	}

	@Override
	public List<JumpropeVO> selectAll() {
		List<JumpropeVO> list = sqlSession.selectList("j_selectAll");
		return list;
	}

	@Override
	public int j_insert(JumpropeVO vo) {
		int a;
		a = sqlSession.insert("j_insert", vo);
		return a;
	}

	@Override
	public int j_update(JumpropeVO vo) {
		int a;
		a = sqlSession.update("j_update", vo);
		return a;
	}

	@Override
	public int j_selectOne(JumpropeVO vo) {
		int a;
		a = sqlSession.selectOne("j_selectOne", vo);
		return a;
	}

}
