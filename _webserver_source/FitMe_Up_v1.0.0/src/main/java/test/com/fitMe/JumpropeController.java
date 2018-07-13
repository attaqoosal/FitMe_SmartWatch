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

import test.com.fitMe.Jumprope.model.JumpropeVO;
import test.com.fitMe.Jumprope.service.JumpropeDAOService;
import test.com.fitMe.Run.model.RunVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class JumpropeController {
	private int result;
	private static final Logger logger = LoggerFactory.getLogger(JumpropeController.class);
	
	@Autowired
	JumpropeDAOService js;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
/*	@RequestMapping(value = "/j_updateOK.do", method = RequestMethod.GET)
	public String r_updateOK(@RequestBody JumpropeVO vo) {
		logger.info("/_updateOK");
		logger.info("id: " + vo.getUser_id());

		result = rs.r_update(vo);
		logger.info("result: " + result);

		return "redirect:r_selectAll.do";
	}

	@RequestMapping(value = "/j_insertOK.do", method = RequestMethod.POST)
	public String r_insertOK(@RequestBody JumpropeVO vo, Model model) {
		logger.info("/r_insertOK.do");
		result = rs.r_insert(vo);

			return "redirect:r_selectAll.do";
	}
	*/
	
	@RequestMapping(value = "/j_selectOneOK.do", method = RequestMethod.POST)
	@ResponseBody
	public int j_selectOneOK(@RequestBody JumpropeVO vo) {
		logger.info("/j_selectOneOK.do");
		logger.info("id: " + vo.getUser_id());
		logger.info("wdate: " + vo.getWdate());
		logger.info("count: " + vo.getCount());
		logger.info("kcal: " + vo.getKcal());
		
		if(js.j_selectOne(vo)>0) {
			result = js.j_update(vo);
		}else {
			result=js.j_insert(vo);
			
		}
			return result;
	}
	
	
	@RequestMapping(value="/j_selectAll.do", method=RequestMethod.GET)
	public String selectAll(Model model) {
		logger.info("j_selectAll.do");
		
		List<JumpropeVO> list=js.selectAll();
		model.addAttribute("list",list);
		return "jumprope/j_selectAll";
	}
	
	
	
}
