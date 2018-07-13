package test.com.fitMe.Situp.model;

import java.util.List;

public interface SitupDAO {
	
	public int st_insert(SitupVO vo);
	public int st_update(SitupVO vo);
	public int st_delete(SitupVO vo);
	public int st_selectOne(SitupVO vo);
	public List<SitupVO> st_selectAll();


}
