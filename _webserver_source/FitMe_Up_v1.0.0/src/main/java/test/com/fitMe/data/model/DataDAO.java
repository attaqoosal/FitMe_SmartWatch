package test.com.fitMe.data.model;

import java.util.List;

public interface DataDAO {

	public List<DataVO> selectAll();
	public List<DataVO> popular_selectAll();
	public List<DataVO> barbell_selectAll();
	public List<DataVO> dumbbell_selectAll();
	public List<DataVO> jumprope_selectAll();
	public List<DataVO> run_selectAll();
	public List<DataVO> situp_selectAll();
	public List<DataVO> walk_selectAll();
	
	


}
