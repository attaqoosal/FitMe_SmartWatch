package test.com.fitMe.User.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);


	@Autowired
	private SqlSession sqlSession;

	public UserDAOImpl() {
		logger.info("UserDAOImpl()....");
	}
	@Override
	public int insert(UserVO vo) {
		logger.info("insert()");
		logger.info("id: "+vo.getUser_id());
		int flag = sqlSession.insert("u_insert", vo);
		logger.info("aflag:"+flag);
		return flag;
	}

	@Override
	public int update(UserVO vo) {
		logger.info("update()");
		logger.info("id: "+vo.getUser_id());
		int flag = sqlSession.update("a_update", vo);
		return flag;
	}

	@Override
	public int delete(UserVO vo) {
		logger.info("delete()");
		logger.info("id: "+vo.getUser_id());
		int flag = sqlSession.delete("a_delete",vo);
		return flag;
	}

	@Override
	public List<UserVO> selectAll() {
		logger.info("selectAll()");
		List<UserVO> list = sqlSession.selectList("u_selectAll");
		return list;
	}

	@Override
	public UserVO selectOne(UserVO vo) {
		logger.info("selectOne()");
		logger.info("id: "+vo.getUser_id());
		UserVO vo2 = sqlSession.selectOne("a_loginCheck", vo);
		return vo2;
	}
	@Override
	public UserVO loginCheck(UserVO vo) {
		logger.info("loginCheck");
		UserVO result = sqlSession.selectOne("a_loginCheck", vo);
		logger.info("id: "+result);
		return result;
	}
	@Override
	public int overlap_check(UserVO vo) {
		logger.info("overlap_check");
		int flag=sqlSession.selectOne("overlap_check",vo);
		return flag;
	}

}
