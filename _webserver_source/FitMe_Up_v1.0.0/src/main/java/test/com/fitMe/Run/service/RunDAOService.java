package test.com.fitMe.Run.service;

import java.util.List;

import test.com.fitMe.Run.model.RunVO;

public interface RunDAOService {

	public int r_insert(RunVO vo);
	public int r_update(RunVO vo);
	public int delete(RunVO vo);
	public int r_selectOne(RunVO vo);
	public List<RunVO> selectAll();


}
