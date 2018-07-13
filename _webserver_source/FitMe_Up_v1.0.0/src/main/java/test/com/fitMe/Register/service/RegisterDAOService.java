package test.com.fitMe.Register.service;

import java.util.List;

import test.com.fitMe.Register.model.RegisterVO;

public interface RegisterDAOService {

	public int insert(RegisterVO vo);

	public int delete(RegisterVO vo);

	public List<RegisterVO> selectAll();

	public int register_selectOne(RegisterVO vo);

}
