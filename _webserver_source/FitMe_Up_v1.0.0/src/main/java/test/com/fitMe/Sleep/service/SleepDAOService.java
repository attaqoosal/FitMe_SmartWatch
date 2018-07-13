package test.com.fitMe.Sleep.service;

import java.util.List;

import test.com.fitMe.Sleep.model.SleepVO;

public interface SleepDAOService {

	public int sl_insert(SleepVO vo);
	public List<SleepVO> selectAll();

}
