package test.com.fitMe.data.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.com.fitMe.data.model.DataDAO;
import test.com.fitMe.data.model.DataVO;

@Service
public class DataDAOServiceImpl implements DataDAOService {

private static final Logger logger = LoggerFactory.getLogger(DataDAOServiceImpl.class);
	
	@Autowired
	DataDAO dao;
	


	@Override
	public List<DataVO> selectAll() {
		// TODO Auto-generated method stub
		return dao.selectAll();
	}





	@Override
	public List<DataVO> barbell_selectAll() {
		// TODO Auto-generated method stub
		return dao.barbell_selectAll();
	}



	@Override
	public List<DataVO> dumbbell_selectAll() {
		// TODO Auto-generated method stub
		return dao.dumbbell_selectAll();
	}



	@Override
	public List<DataVO> jumprope_selectAll() {
		// TODO Auto-generated method stub
		return dao.jumprope_selectAll();
	}



	@Override
	public List<DataVO> run_selectAll() {
		// TODO Auto-generated method stub
		return dao.run_selectAll();
	}



	@Override
	public List<DataVO> situp_selectAll() {
		// TODO Auto-generated method stub
		return dao.situp_selectAll();
	}



	@Override
	public List<DataVO> walk_selectAll() {
		// TODO Auto-generated method stub
		return dao.walk_selectAll();
	}





	@Override
	public List<DataVO> popular_selectAll() {
		// TODO Auto-generated method stub
		return dao.popular_selectAll();
	}



}
