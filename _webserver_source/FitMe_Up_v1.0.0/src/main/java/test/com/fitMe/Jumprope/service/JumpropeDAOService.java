package test.com.fitMe.Jumprope.service;

import java.util.List;

import test.com.fitMe.Jumprope.model.JumpropeVO;

public interface JumpropeDAOService {

	public int j_insert(JumpropeVO vo);
	public int j_update(JumpropeVO vo);
	public int j_selectOne(JumpropeVO vo);
	public int j_delete(JumpropeVO vo);
	public List<JumpropeVO> selectAll();

}
