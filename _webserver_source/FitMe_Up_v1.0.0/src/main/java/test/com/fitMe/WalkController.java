package test.com.fitMe;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import test.com.fitMe.Walk.model.WalkVO;
import test.com.fitMe.Walk.service.WalkDAOService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class WalkController {
	
	private int result;
	private static final Logger logger = LoggerFactory.getLogger(WalkController.class);
	
	@Autowired
	WalkDAOService ws;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	
	/*@RequestMapping(value = "/w_updateOK.do", method = RequestMethod.GET)
	public String w_updateOK(WalkVO vo) {
		logger.info("/w_updateOK");
		logger.info("id: " + vo.getUser_id());

		result = ws.w_update(vo);
		logger.info("result: " + result);

		return "redirect:w_selectAll.do";
	}

	@RequestMapping(value = "/w_insertOK.do", method = RequestMethod.POST)
	public String w_insertOK(@RequestBody WalkVO vo, Model model) {
		logger.info("/w_insertOK.do");
		if()
		result = ws.w_insert(vo);

			return "redirect:w_selectAll.do";
	}*/
	
	@RequestMapping(value = "/w_selectAll.do", method = RequestMethod.GET)
	public String w_selectAll(Model model) {
		logger.info("w_selectAll.do");

		List<WalkVO> list=ws.w_selectAll();
		model.addAttribute("list", list);
		return "walk/w_selectAll";
	}
	
	
	
	@RequestMapping(value = "/w_selectOneOK.do", method = RequestMethod.POST)
	@ResponseBody
	public int w_selectOneOK(@RequestBody WalkVO vo) {
		logger.info("/w_selectOneOK.do");
		logger.info("id: " + vo.getUser_id());
		logger.info("wdate: " + vo.getWdate());
		logger.info("count: " + vo.getCount());
		logger.info("dist: " + vo.getDist());
		logger.info("kcal: " + vo.getKcal());
		
		if(ws.w_selectOne(vo)>0) {
			result = ws.w_update(vo);
		}else {
			result=ws.w_insert(vo);
			
		}
			return result;
	}
	
	
	
	
	
	
	
}
