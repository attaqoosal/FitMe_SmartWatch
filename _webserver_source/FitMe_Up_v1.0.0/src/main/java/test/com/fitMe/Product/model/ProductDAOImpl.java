package test.com.fitMe.Product.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAOImpl implements ProductDAO {
	private static final Logger logger = LoggerFactory.getLogger(ProductDAOImpl.class);


	@Autowired
	private SqlSession sqlSession;

	public ProductDAOImpl() {
		logger.info("ProductDAOImpl()....");
	}
	@Override
	public int insert(ProductVO vo) {
		logger.info("insert()");
		
		logger.info("id: "+vo.getSerial_num());
		logger.info("id: "+vo.getName());
		logger.info("id: "+vo.getManufacture_date());
		logger.info("id: "+vo.getPrice());
	
		int flag = sqlSession.insert("product_insert", vo);
		return flag;
	}

	@Override
	public int update(ProductVO vo) {
		logger.info("register_update()");
		logger.info("serial_num: "+vo.getSerial_num());
		int flag = sqlSession.update("product_update", vo);
		return flag;
	}

	@Override
	public int delete(ProductVO vo) {
		logger.info("register_delete()");
		logger.info("serial_num: "+vo.getSerial_num());
		int flag = sqlSession.delete("product_delete",vo);
		return flag;
	}

	@Override
	public List<ProductVO> selectAll() {
		logger.info("product_selectAll()");
		List<ProductVO> list = sqlSession.selectList("product_selectAll");
		return list;
	}

	@Override
	public ProductVO selectOne(ProductVO vo) {
		logger.info("register_selectOne()");
		logger.info("serial_num: "+vo.getSerial_num());
		ProductVO vo2 = sqlSession.selectOne("product_selectOne", vo);
		return vo2;
	}
	

}
