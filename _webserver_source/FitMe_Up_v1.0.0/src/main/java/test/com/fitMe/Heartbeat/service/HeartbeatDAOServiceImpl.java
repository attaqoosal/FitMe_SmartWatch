package test.com.fitMe.Heartbeat.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import test.com.fitMe.Heartbeat.model.HeartbeatDAO;
import test.com.fitMe.Heartbeat.model.HeartbeatVO;

@Repository
public class HeartbeatDAOServiceImpl implements HeartbeatDAOService {
	
	private static final Logger logger = LoggerFactory.getLogger(HeartbeatDAOServiceImpl.class);
	
	@Autowired
	HeartbeatDAO dao;



	@Override
	public List<HeartbeatVO> h_selectAll() {
		// TODO Auto-generated method stub
		return dao.h_selectAll();
	}

	@Override
	public int h_insert(HeartbeatVO vo) {
		// TODO Auto-generated method stub
		return dao.h_insert(vo);
	}


}
