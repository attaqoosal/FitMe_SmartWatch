package test.com.fitMe.Register.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RegisterDAOImpl implements RegisterDAO {
	private static final Logger logger = LoggerFactory.getLogger(RegisterDAOImpl.class);


	@Autowired
	private SqlSession sqlSession;

	public RegisterDAOImpl() {
		logger.info("RegisterDAOImpl()....");
	}
	@Override
	public int insert(RegisterVO vo) {
		logger.info("insert()");
		logger.info("id: "+vo.getSerial_num());
		logger.info("id: "+vo.getUser_id());
		
		int flag = sqlSession.insert("register_insert", vo);
		logger.info("aflag:"+flag);
		return flag;
	}


	@Override
	public int delete(RegisterVO vo) {
		logger.info("register_delete()");
		logger.info("id: "+vo.getUser_id());
		int flag = sqlSession.delete("register_delete",vo);
		return flag;
	}

	@Override
	public List<RegisterVO> selectAll() {
		logger.info("register_selectAll()");
		List<RegisterVO> list = sqlSession.selectList("register_selectAll");
		return list;
	}


	@Override
	public int register_selectOne(RegisterVO vo) {
		logger.info("register_selectOne()");
		int vo2=sqlSession.selectOne("register_selectOne",vo);
		logger.info("vo2:"+vo2);
		return vo2;
	}
	

}
