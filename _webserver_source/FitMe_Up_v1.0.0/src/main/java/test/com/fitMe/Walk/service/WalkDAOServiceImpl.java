package test.com.fitMe.Walk.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import test.com.fitMe.Walk.model.WalkDAO;
import test.com.fitMe.Walk.model.WalkVO;

@Repository
public class WalkDAOServiceImpl implements WalkDAOService {
	
	private static final Logger logger = LoggerFactory.getLogger(WalkDAOServiceImpl.class);

	@Autowired
	WalkDAO dao;
	@Override
	public int w_insert(WalkVO vo) {
		// TODO Auto-generated method stub
		return dao.w_insert(vo);
	}


	@Override
	public int w_delete(WalkVO vo) {
		// TODO Auto-generated method stub
		return dao.w_delete(vo);
	}

	@Override
	public List<WalkVO> w_selectAll() {
		// TODO Auto-generated method stub
		return dao.w_selectAll();
	}


	@Override
	public int w_update(WalkVO vo) {
		// TODO Auto-generated method stub
		return dao.w_update(vo);
	}


	@Override
	public int w_selectOne(WalkVO vo) {
		// TODO Auto-generated method stub
		return dao.w_selectOne(vo);
	}



}
