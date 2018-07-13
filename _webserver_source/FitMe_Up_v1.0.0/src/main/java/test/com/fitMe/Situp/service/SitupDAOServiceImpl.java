package test.com.fitMe.Situp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.com.fitMe.Situp.model.SitupDAO;
import test.com.fitMe.Situp.model.SitupVO;

@Service
public class SitupDAOServiceImpl implements SitupDAOService {
	
	
	private static final Logger logger = LoggerFactory.getLogger(SitupDAOServiceImpl.class);

	
	@Autowired
	SitupDAO dao;
	


	@Override
	public int st_delete(SitupVO vo) {
		// TODO Auto-generated method stub
		return dao.st_delete(vo);
	}

	@Override
	public List<SitupVO> st_selectAll() {
		// TODO Auto-generated method stub
		return dao.st_selectAll();
	}

	@Override
	public int st_insert(SitupVO vo) {
		// TODO Auto-generated method stub
		return dao.st_insert(vo);
	}

	@Override
	public int st_update(SitupVO vo) {
		// TODO Auto-generated method stub
		return dao.st_update(vo);
	}

	@Override
	public int st_selectOne(SitupVO vo) {
		// TODO Auto-generated method stub
		return dao.st_selectOne(vo);
	}


}
