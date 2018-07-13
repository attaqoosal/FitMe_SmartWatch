package test.com.fitMe.Product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.com.fitMe.Product.model.ProductDAO;
import test.com.fitMe.Product.model.ProductVO;

@Service
public class ProductDAOServiceImpl implements ProductDAOService {

	@Autowired
	ProductDAO dao;
	
	public int insert(ProductVO vo) {
		return dao.insert(vo);
	}

	public int update(ProductVO vo) {
		return dao.update(vo);
	}

	public int delete(ProductVO vo) {
		return dao.delete(vo);
	}

	@Override
	public List<ProductVO> selectAll() {
		return dao.selectAll();
	}

	public ProductVO selectOne(ProductVO vo) {
		return dao.selectOne(vo);
	}



}
