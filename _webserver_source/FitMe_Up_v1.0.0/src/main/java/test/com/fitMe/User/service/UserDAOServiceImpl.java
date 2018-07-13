package test.com.fitMe.User.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.com.fitMe.User.model.UserDAO;
import test.com.fitMe.User.model.UserVO;

@Service
public class UserDAOServiceImpl implements UserDAOService {

	@Autowired
	UserDAO dao;
	
	@Override
	public int insert(UserVO vo) {
		return dao.insert(vo);
	}

	@Override
	public int update(UserVO vo) {
		return dao.update(vo);
	}

	@Override
	public int delete(UserVO vo) {
		return dao.delete(vo);
	}

	@Override
	public List<UserVO> selectAll() {
		return dao.selectAll();
	}

	@Override
	public UserVO selectOne(UserVO vo) {
		return dao.selectOne(vo);
	}

	@Override
	public UserVO loginCheck(UserVO vo) {
		return dao.loginCheck(vo);
	}

	@Override
	public int overlap_check(UserVO vo) {
		// TODO Auto-generated method stub
		return dao.overlap_check(vo);
	}

}
