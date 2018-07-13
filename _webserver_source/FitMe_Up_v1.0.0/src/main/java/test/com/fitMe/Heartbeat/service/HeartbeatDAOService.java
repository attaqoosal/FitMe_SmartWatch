package test.com.fitMe.Heartbeat.service;

import java.util.List;

import test.com.fitMe.Heartbeat.model.HeartbeatVO;

public interface HeartbeatDAOService {

	public int h_insert(HeartbeatVO vo);
	public List<HeartbeatVO> h_selectAll();


}
