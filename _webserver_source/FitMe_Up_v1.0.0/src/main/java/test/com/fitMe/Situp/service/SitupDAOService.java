package test.com.fitMe.Situp.service;

import java.util.List;

import test.com.fitMe.Situp.model.SitupVO;

public interface SitupDAOService {

	public int st_insert(SitupVO vo);
	public int st_update(SitupVO vo);
	public int st_delete(SitupVO vo);
	public int st_selectOne(SitupVO vo);
	public List<SitupVO> st_selectAll();


}
