package test.com.fitMe.Heartbeat.model;

import java.util.List;

public interface HeartbeatDAO {

	public int h_insert(HeartbeatVO vo);
	public List<HeartbeatVO> h_selectAll();
	/*public HeartbeatVO h_selectOne(HeartbeatVO vo);*/

}
