package test.com.fitMe.Register.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.com.fitMe.Register.model.RegisterDAO;
import test.com.fitMe.Register.model.RegisterVO;

@Service
public class RegisterDAOServiceImpl implements RegisterDAOService {

	@Autowired
	RegisterDAO dao;

	@Override
	public int insert(RegisterVO vo) {
		return dao.insert(vo);
	}

	@Override
	public int delete(RegisterVO vo) {
		return dao.delete(vo);
	}

	@Override
	public List<RegisterVO> selectAll() {
		return dao.selectAll();
	}

	@Override
	public int register_selectOne(RegisterVO vo) {
		return dao.register_selectOne(vo);
	}

}
