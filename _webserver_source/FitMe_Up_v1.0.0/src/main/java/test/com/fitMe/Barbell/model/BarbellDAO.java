package test.com.fitMe.Barbell.model;

import java.util.List;

public interface BarbellDAO {

	public int b_insert(BarbellVO vo);
	public int b_update(BarbellVO vo);
	public int b_selectOne(BarbellVO vo);
	public int b_delete(BarbellVO vo);
	public List<BarbellVO> b_selectAll();

}
