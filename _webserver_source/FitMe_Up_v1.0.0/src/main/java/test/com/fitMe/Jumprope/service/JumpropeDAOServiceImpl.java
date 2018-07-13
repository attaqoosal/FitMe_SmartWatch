package test.com.fitMe.Jumprope.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.com.fitMe.Jumprope.model.JumpropeDAO;
import test.com.fitMe.Jumprope.model.JumpropeVO;

@Service
public class JumpropeDAOServiceImpl implements JumpropeDAOService {
	
	private static final Logger logger = LoggerFactory.getLogger(JumpropeDAOServiceImpl.class);

	@Autowired
	JumpropeDAO dao;
	

	@Override
	public List<JumpropeVO> selectAll() {
		// TODO Auto-generated method stub
		return dao.selectAll();
	}

	@Override
	public int j_insert(JumpropeVO vo) {
		// TODO Auto-generated method stub
		return dao.j_insert(vo);
	}

	@Override
	public int j_update(JumpropeVO vo) {
		// TODO Auto-generated method stub
		return dao.j_update(vo);
	}

	@Override
	public int j_selectOne(JumpropeVO vo) {
		// TODO Auto-generated method stub
		return dao.j_selectOne(vo);
	}

	@Override
	public int j_delete(JumpropeVO vo) {
		// TODO Auto-generated method stub
		return dao.delete(vo);
	}


}
