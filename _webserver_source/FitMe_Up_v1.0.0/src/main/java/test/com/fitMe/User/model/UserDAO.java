package test.com.fitMe.User.model;

import java.util.List;

public interface UserDAO {

	public int insert(UserVO vo);
	public int update(UserVO vo);
	public int delete(UserVO vo);
	public List<UserVO> selectAll();
	public UserVO loginCheck(UserVO vo);
	public UserVO selectOne(UserVO vo);
	public int overlap_check(UserVO vo);
	
}
