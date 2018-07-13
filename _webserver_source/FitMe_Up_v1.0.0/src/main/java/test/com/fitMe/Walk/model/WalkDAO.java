package test.com.fitMe.Walk.model;

import java.util.List;

public interface WalkDAO {
	public int w_insert(WalkVO vo);
	public int w_delete(WalkVO vo);
	public int w_update(WalkVO vo);
	public int w_selectOne(WalkVO vo);
	public List<WalkVO> w_selectAll();


}
