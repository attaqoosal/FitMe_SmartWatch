package test.com.fitMe.Register.model;

import java.util.List;

public interface RegisterDAO {

	public int insert(RegisterVO vo);

	public int delete(RegisterVO vo);
	public List<RegisterVO> selectAll();
	public int register_selectOne(RegisterVO vo);
	
}
