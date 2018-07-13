package test.com.fitMe.Run.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.com.fitMe.Run.model.RunDAO;
import test.com.fitMe.Run.model.RunVO;

@Service
public class RunDAOServiceImpl implements RunDAOService {
	
	private static final Logger logger = LoggerFactory.getLogger(RunDAOServiceImpl.class);

	
	@Autowired
	RunDAO dao;
	

	@Override
	public int delete(RunVO vo) {
		return dao.r_delete(vo);
	}

	@Override
	public List<RunVO> selectAll() {
		return dao.r_selectAll();
	}

	@Override
	public int r_insert(RunVO vo) {
		// TODO Auto-generated method stub
		return dao.r_insert(vo);
	}

	@Override
	public int r_update(RunVO vo) {
		// TODO Auto-generated method stub
		return dao.r_update(vo);
	}

	@Override
	public int r_selectOne(RunVO vo) {
		// TODO Auto-generated method stub
		return dao.r_selectOne(vo);
	}

}
