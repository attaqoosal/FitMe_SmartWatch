package test.com.fitMe.Product.service;

import java.util.List;

import test.com.fitMe.Product.model.ProductVO;

public interface ProductDAOService {

	public int insert(ProductVO vo);
	public int update(ProductVO vo);
	public int delete(ProductVO vo);
	public List<ProductVO> selectAll();
	public ProductVO selectOne(ProductVO vo);
	
}
