package test.com.fitMe.Sleep.model;

import java.util.List;

public interface SleepDAO {

	public int sl_insert(SleepVO vo);
	public List<SleepVO> selectAll();

}
