package test.com.fitMe.Walk.service;

import java.util.List;

import test.com.fitMe.Walk.model.WalkVO;

public interface WalkDAOService {
	public int w_insert(WalkVO vo);
	public int w_delete(WalkVO vo);
	public int w_update(WalkVO vo);
	public int w_selectOne(WalkVO vo);
	public List<WalkVO> w_selectAll();


}
