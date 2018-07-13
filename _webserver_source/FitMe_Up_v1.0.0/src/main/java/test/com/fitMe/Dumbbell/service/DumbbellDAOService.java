package test.com.fitMe.Dumbbell.service;

import java.util.List;

import org.springframework.stereotype.Service;

import test.com.fitMe.Dumbbell.model.DumbbellVO;

@Service
public interface DumbbellDAOService {

	public int d_insert(DumbbellVO vo);
	public int d_update(DumbbellVO vo);
	public int d_selectOne(DumbbellVO vo);
	public int delete(DumbbellVO vo);
	public List<DumbbellVO> selectAll();


}
