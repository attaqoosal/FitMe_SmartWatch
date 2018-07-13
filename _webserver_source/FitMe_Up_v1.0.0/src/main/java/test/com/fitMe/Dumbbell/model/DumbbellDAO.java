package test.com.fitMe.Dumbbell.model;

import java.util.List;

public interface DumbbellDAO {

	public int d_insert(DumbbellVO vo);
	public int d_update(DumbbellVO vo);
	public int d_selectOne(DumbbellVO vo);
	public int d_delete(DumbbellVO vo);
	public List<DumbbellVO> d_selectAll();


}
