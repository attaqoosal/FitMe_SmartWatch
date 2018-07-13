package test.com.fitMe.Situp.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SitupDAOImpl implements SitupDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(SitupDAOImpl.class);

	@Autowired
	private SqlSession sqlSession;
	
	
	

	@Override
	public int st_delete(SitupVO vo) {
		int a;
		a=sqlSession.delete("st_delete",vo);
		return a;
	}

	@Override
	public List<SitupVO> st_selectAll() {
	
		List<SitupVO> list =sqlSession.selectList("st_selectAll");
		return list;
	}

	@Override
	public int st_insert(SitupVO vo) {
		int a;
		a=sqlSession.insert("st_insert",vo);
		return a;
	}

	@Override
	public int st_update(SitupVO vo) {
		int a;
		a=sqlSession.update("st_update",vo);
		return a;
	}

	@Override
	public int st_selectOne(SitupVO vo) {
		int a;
		a=sqlSession.selectOne("st_selectOne",vo);
		return a;
	}


}
