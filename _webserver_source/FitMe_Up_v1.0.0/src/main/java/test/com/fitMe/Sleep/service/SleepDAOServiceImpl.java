package test.com.fitMe.Sleep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.com.fitMe.Sleep.model.SleepDAO;
import test.com.fitMe.Sleep.model.SleepVO;

@Service
public class SleepDAOServiceImpl implements SleepDAOService {
	
	@Autowired
	SleepDAO dao;
	
	@Override
	public List<SleepVO> selectAll() {
		return dao.selectAll();
	}


	@Override
	public int sl_insert(SleepVO vo) {
		// TODO Auto-generated method stub
		return dao.sl_insert(vo);
	}


}
