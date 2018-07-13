package test.com.fitMe;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import test.com.fitMe.Barbell.model.BarbellVO;
import test.com.fitMe.Barbell.service.BarbellDAOService;
import test.com.fitMe.Dumbbell.model.DumbbellVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BarbellController {
	private int result;
	private static final Logger logger = LoggerFactory.getLogger(BarbellController.class);
	
	@Autowired
	BarbellDAOService bs;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	
	@RequestMapping(value = "/b_selectOneOK.do", method = RequestMethod.POST)
	@ResponseBody
	public int b_selectOneOK(@RequestBody BarbellVO vo) {
		logger.info("/b_selectOneOK.do");
		logger.info("id: " + vo.getUser_id());
		logger.info("wdate: " + vo.getWdate());
		logger.info("count: " + vo.getCount());

		
		if(bs.b_selectOne(vo)>0) {
			result = bs.b_update(vo);
		}else {
			result=bs.b_insert(vo);
			
		}
			return result;
	}
	
	
	@RequestMapping(value = "/b_selectAll.do", method = RequestMethod.GET)
	public String b_selectAll(Model model) {
		logger.info("b_selectAll.do");

		List<BarbellVO> list=bs.selectAll();
		model.addAttribute("list", list);
		return "barbell/b_selectAll";
	}
	
	
	
	
	
	
}
