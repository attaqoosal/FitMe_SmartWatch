package test.com.fitMe.Run.model;

import java.util.List;

public interface RunDAO {

	public int r_insert(RunVO vo);
	public int r_update(RunVO vo);
	public int r_delete(RunVO vo);
	public int r_selectOne(RunVO vo);
	public List<RunVO> r_selectAll();

}
