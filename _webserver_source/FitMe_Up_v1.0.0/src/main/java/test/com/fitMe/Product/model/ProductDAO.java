package test.com.fitMe.Product.model;

import java.util.List;

public interface ProductDAO {

	public int insert(ProductVO vo);
	public int update(ProductVO vo);
	public int delete(ProductVO vo);
	public List<ProductVO> selectAll();
	public ProductVO selectOne(ProductVO vo);
	
}
