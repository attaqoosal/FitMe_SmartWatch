package test.com.fitMe.Jumprope.model;

import java.util.List;

public interface JumpropeDAO {

	public int j_insert(JumpropeVO vo);
	public int j_update(JumpropeVO vo);
	public int j_selectOne(JumpropeVO vo);
	public int delete(JumpropeVO vo);
	public List<JumpropeVO> selectAll();
	


}
