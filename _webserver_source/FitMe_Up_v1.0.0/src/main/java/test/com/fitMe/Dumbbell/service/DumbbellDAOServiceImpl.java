package test.com.fitMe.Dumbbell.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.com.fitMe.Dumbbell.model.DumbbellDAO;
import test.com.fitMe.Dumbbell.model.DumbbellVO;

@Service
public class DumbbellDAOServiceImpl implements DumbbellDAOService {

	private static final Logger logger = LoggerFactory.getLogger(DumbbellDAOServiceImpl.class);
	
	@Autowired
	DumbbellDAO dao;
	
	
	@Override
	public int delete(DumbbellVO vo) {
		// TODO Auto-generated method stub
		return dao.d_delete(vo);

	}

	@Override
	public List<DumbbellVO> selectAll() {
		// TODO Auto-generated method stub
		return dao.d_selectAll();
	}

	@Override
	public int d_insert(DumbbellVO vo) {
		// TODO Auto-generated method stub
		return dao.d_insert(vo);
	}

	@Override
	public int d_update(DumbbellVO vo) {
		// TODO Auto-generated method stub
		return dao.d_update(vo);
	}

	@Override
	public int d_selectOne(DumbbellVO vo) {
		// TODO Auto-generated method stub
		return dao.d_selectOne(vo);
	}

	
}
