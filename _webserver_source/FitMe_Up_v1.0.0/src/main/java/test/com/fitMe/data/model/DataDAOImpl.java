package test.com.fitMe.data.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataDAOImpl implements DataDAO {

	private static final Logger logger = LoggerFactory.getLogger(DataDAOImpl.class);
	
	@Autowired
	private SqlSession sqlSession;


	@Override
	public List<DataVO> selectAll() {
		
		List<DataVO> list = sqlSession.selectList("data_selectAll");
		System.out.println("aaa" + list.get(0).getAvg_walk()+"    "+list.size());
		for(int i = 0; i < list.size(); i++) {
			logger.info("         "+list.get(i).avg_walk);
			logger.info("vvvvv"+list.get(i).gen);
			
		}
		return list;
	}
	
	@Override
	public List<DataVO> popular_selectAll() {
		
		List<DataVO> list = sqlSession.selectList("popular_selectAll");
		boolean[] visited = new boolean[6];
		if(list.size() != 6) {
			for(int i = 0; i < list.size(); i++) {
				visited[Integer.parseInt(list.get(i).gen)/10-1] = true;
			}
			for(int i = 0; i < visited.length; i++) {
				if(!visited[i]) {
					DataVO data = new DataVO();
					data.setGen(String.valueOf(i*10+10));
					data.setPopular(0);
					list.add(data);
				}
		
			}
		}
		
		System.out.println("popular" + list.get(0).getGen());
		return list;
	}
	
	
	
	@Override
	public List<DataVO> jumprope_selectAll() {
		List<DataVO> list = sqlSession.selectList("data_jumprope_selectAll");
		boolean[][] visited = new boolean[2][6];
		logger.info("aa" + list.size());
		if(list.size() != 12) {
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).gender.equals("남자")) {
					visited[0][Integer.parseInt(list.get(i).gen)/10-1] = true;
				}else {
					visited[1][Integer.parseInt(list.get(i).gen)/10-1] = true;
				}
			}
			for(int i = 0; i < 2; i++) {
				for(int j = 0; j < 6; j++) {
					if(!visited[i][j]) {
						DataVO data = new DataVO();
						data.setGen(String.valueOf(j*10+10));
						data.setPopular(0);
						if(i == 0) {
							data.setGender("남자");
						}else {
							data.setGender("여자");
						}
						list.add(data);

					}
				}
			}
		}
		for(int i = 0; i < list.size(); i++) {
			list.get(i).setType("jumprope");
		}
		System.out.println("jumprope" + list.get(0).getPopular());
		return list;
	}

	@Override
	public List<DataVO> dumbbell_selectAll() {
		List<DataVO> list = sqlSession.selectList("data_dumbbell_selectAll");
		boolean[][] visited = new boolean[2][6];
		logger.info("aa" + list.size());
		if(list.size() != 12) {
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).gender.equals("남자")) {
					visited[0][Integer.parseInt(list.get(i).gen)/10-1] = true;
				}else {
					visited[1][Integer.parseInt(list.get(i).gen)/10-1] = true;
				}
			}
			for(int i = 0; i < 2; i++) {
				for(int j = 0; j < 6; j++) {
					if(!visited[i][j]) {
						DataVO data = new DataVO();
						data.setGen(String.valueOf(j*10+10));
						data.setPopular(0);
						if(i == 0) {
							data.setGender("남자");
						}else {
							data.setGender("여자");
						}
						list.add(data);

					}
				}
			}
		}
		for(int i = 0; i < list.size(); i++) {
			list.get(i).setType("dumbbell");
		}
		System.out.println("dumbbell" + list.get(0).getPopular());
		return list;
	}

	@Override
	public List<DataVO> run_selectAll() {
		List<DataVO> list = sqlSession.selectList("data_run_selectAll");
		boolean[][] visited = new boolean[2][6];
		logger.info("aa" + list.size());
		if(list.size() != 12) {
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).gender.equals("남자")) {
					visited[0][Integer.parseInt(list.get(i).gen)/10-1] = true;
				}else {
					visited[1][Integer.parseInt(list.get(i).gen)/10-1] = true;
				}
			}
			for(int i = 0; i < 2; i++) {
				for(int j = 0; j < 6; j++) {
					if(!visited[i][j]) {
						DataVO data = new DataVO();
						data.setGen(String.valueOf(j*10+10));
						data.setPopular(0);
						if(i == 0) {
							data.setGender("남자");
						}else {
							data.setGender("여자");
						}
						list.add(data);

					}
				}
			}
		}
		for(int i = 0; i < list.size(); i++) {
			list.get(i).setType("run");
		}
		System.out.println("run" + list.get(0).getPopular());
		return list;
	}

	@Override
	public List<DataVO> situp_selectAll() {
		List<DataVO> list = sqlSession.selectList("data_situp_selectAll");
		boolean[][] visited = new boolean[2][6];
		logger.info("aa" + list.size());
		if(list.size() != 12) {
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).gender.equals("남자")) {
					visited[0][Integer.parseInt(list.get(i).gen)/10-1] = true;
				}else {
					visited[1][Integer.parseInt(list.get(i).gen)/10-1] = true;
				}
			}
			for(int i = 0; i < 2; i++) {
				for(int j = 0; j < 6; j++) {
					if(!visited[i][j]) {
						DataVO data = new DataVO();
						data.setGen(String.valueOf(j*10+10));
						data.setPopular(0);
						if(i == 0) {
							data.setGender("남자");
						}else {
							data.setGender("여자");
						}
						list.add(data);

					}
				}
			}
		}
		for(int i = 0; i < list.size(); i++) {
			list.get(i).setType("situp");
		}
		System.out.println("situp" + list.get(0).getPopular());
		return list;
	}

	@Override
	public List<DataVO> walk_selectAll() {
		List<DataVO> list = sqlSession.selectList("data_walk_selectAll");
		boolean[][] visited = new boolean[2][6];
		logger.info("aa" + list.size());
		if(list.size() != 12) {
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).gender.equals("남자")) {
					visited[0][Integer.parseInt(list.get(i).gen)/10-1] = true;
				}else {
					visited[1][Integer.parseInt(list.get(i).gen)/10-1] = true;
				}
			}
			for(int i = 0; i < 2; i++) {
				for(int j = 0; j < 6; j++) {
					if(!visited[i][j]) {
						DataVO data = new DataVO();
						data.setGen(String.valueOf(j*10+10));
						data.setPopular(0);
						if(i == 0) {
							data.setGender("남자");
						}else {
							data.setGender("여자");
						}
						list.add(data);

					}
				}
			}
		}
		for(int i = 0; i < list.size(); i++) {
			list.get(i).setType("walk");
		}
		System.out.println("walk" + list.get(0).getPopular());
		return list;
	}


	@Override
	public List<DataVO> barbell_selectAll() {
		List<DataVO> list = sqlSession.selectList("data_barbell_selectAll");
		boolean[][] visited = new boolean[2][6];
		logger.info("aa" + list.size());
		if(list.size() != 12) {
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).gender.equals("남자")) {
					visited[0][Integer.parseInt(list.get(i).gen)/10-1] = true;
				}else {
					visited[1][Integer.parseInt(list.get(i).gen)/10-1] = true;
				}
			}
			for(int i = 0; i < 2; i++) {
				for(int j = 0; j < 6; j++) {
					if(!visited[i][j]) {
						DataVO data = new DataVO();
						data.setGen(String.valueOf(j*10+10));
						data.setPopular(0);
						if(i == 0) {
							data.setGender("남자");
						}else {
							data.setGender("여자");
						}
						list.add(data);

					}
				}
			}
		}
		for(int i = 0; i < list.size(); i++) {
			list.get(i).setType("barbell");
		}
		System.out.println("barbell" + list.get(0).getPopular());
		return list;
	}

	

}
