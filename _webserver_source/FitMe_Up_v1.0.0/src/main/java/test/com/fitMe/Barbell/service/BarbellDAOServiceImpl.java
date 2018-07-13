package test.com.fitMe.Barbell.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.com.fitMe.Barbell.model.BarbellDAO;
import test.com.fitMe.Barbell.model.BarbellVO;

@Service
public class BarbellDAOServiceImpl implements BarbellDAOService {

private static final Logger logger = LoggerFactory.getLogger(BarbellDAOServiceImpl.class);
	
	@Autowired
	BarbellDAO dao;
	
	
	

	@Override
	public int delete(BarbellVO vo) {
		// TODO Auto-generated method stub
		return dao.b_delete(vo);
	}

	@Override
	public List<BarbellVO> selectAll() {
		// TODO Auto-generated method stub
		return dao.b_selectAll();
	}

	@Override
	public int b_insert(BarbellVO vo) {
		// TODO Auto-generated method stub
		return dao.b_insert(vo);
	}

	@Override
	public int b_update(BarbellVO vo) {
		// TODO Auto-generated method stub
		return dao.b_update(vo);
	}

	@Override
	public int b_selectOne(BarbellVO vo) {
		// TODO Auto-generated method stub
		return dao.b_selectOne(vo);
	}



}
