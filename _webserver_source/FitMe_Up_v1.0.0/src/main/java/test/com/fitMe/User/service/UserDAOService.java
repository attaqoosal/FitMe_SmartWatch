package test.com.fitMe.User.service;

import java.util.List;

import test.com.fitMe.User.model.UserVO;

public interface UserDAOService {

	public int insert(UserVO vo);
	public int update(UserVO vo);
	public int delete(UserVO vo);
	public List<UserVO> selectAll();
	public UserVO loginCheck(UserVO vo);
	public UserVO selectOne(UserVO vo);
	public int overlap_check(UserVO vo);
}
