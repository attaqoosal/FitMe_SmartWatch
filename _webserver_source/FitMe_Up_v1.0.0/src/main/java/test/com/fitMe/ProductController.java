package test.com.fitMe;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import test.com.fitMe.Product.model.ProductVO;
import test.com.fitMe.Product.service.ProductDAOService;
import test.com.fitMe.Run.model.RunVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ProductController {
	
	private int flag;


	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	ProductDAOService ps;


	


	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	
	@RequestMapping(value = "/p_insertOK.do", method = RequestMethod.POST)
	public String prodcut_insertOK(ProductVO vo, HttpServletRequest request, Model model) {
		logger.info("/p_insertOK.do");
	
		flag = ps.insert(vo);
		logger.info("flag: "+flag);
		return "redirect:p_selectAll.do";
		
	}
	
	
	@RequestMapping(value = "/p_insert.do", method = RequestMethod.GET)
	public String p_insert() {
		logger.info("insert");
				
		return "product/p_insert";
	}
	
	@RequestMapping(value = "/p_selectAll.do", method = RequestMethod.GET)
	public String p_selectAll(Model model) {
		logger.info("selectAll");
		List<ProductVO> list = ps.selectAll();
		model.addAttribute("list",list);
		return "product/p_selectAll";
	}
	

	@RequestMapping(value = "/p_selectOne.do", method = RequestMethod.GET)
	public String selectOne(ProductVO vo, Model model) {
		logger.info("/p_selectOne.do");
		
		ProductVO vo2 = ps.selectOne(vo);
		vo = vo2;
		model.addAttribute("vo", vo);
		return "product/p_selectOne";
	}
	
	
	@RequestMapping(value = "/p_updateOK.do", method = RequestMethod.GET)
	public String p_updateOK(ProductVO vo) {
		logger.info("/p_updateOK");

		flag = ps.update(vo);
		logger.info("flag: "+flag);
		
		return "redirect:p_selectAll.do";
	}
	@RequestMapping(value = "/p_deleteOK.do", method = RequestMethod.GET)
	public String delete(ProductVO vo,  HttpServletRequest request) {
		logger.info("delete");
		flag = ps.delete(vo);
		String serial_num=request.getParameter("serial_num");
		
		logger.info("flag: "+flag);
		return "redirect:p_selectAll.do";
	}
	
	
	
	
	
	
}
