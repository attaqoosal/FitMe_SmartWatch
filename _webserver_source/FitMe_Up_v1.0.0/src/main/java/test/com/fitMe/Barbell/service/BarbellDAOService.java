package test.com.fitMe.Barbell.service;


import java.util.List;

import org.springframework.stereotype.Service;

import test.com.fitMe.Barbell.model.BarbellVO;

public interface BarbellDAOService {

	public int b_insert(BarbellVO vo);
	public int b_update(BarbellVO vo);
	public int b_selectOne(BarbellVO vo);
	public int delete(BarbellVO vo);
	public List<BarbellVO> selectAll();

}