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

import test.com.fitMe.Run.model.RunVO;
import test.com.fitMe.Run.service.RunDAOService;
import test.com.fitMe.Situp.model.SitupVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class RunController {
	int result;
	
	private static final Logger logger = LoggerFactory.getLogger(RunController.class);
	
	@Autowired
	RunDAOService rs;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */


/*	@RequestMapping(value = "/r_updateOK.do", method = RequestMethod.GET)
	public String r_updateOK(@RequestBody RunVO vo) {
		logger.info("/r_updateOK");
		logger.info("id: " + vo.getUser_id());

		result = rs.r_update(vo);
		logger.info("result: " + result);

		return "redirect:r_selectAll.do";
	}

	@RequestMapping(value = "/r_insertOK.do", method = RequestMethod.POST)
	public String r_insertOK(@RequestBody RunVO vo, Model model) {
		logger.info("/r_insertOK.do");
		result = rs.r_insert(vo);

			return "redirect:r_selectAll.do";
	}*/
	
	@RequestMapping(value = "/r_selectOneOK.do", method = RequestMethod.POST)
	@ResponseBody
	public int r_selectOneOK(@RequestBody RunVO vo) {
		logger.info("/r_selectOneOK.do");
		logger.info("id: " + vo.getUser_id());
		logger.info("wdate: " + vo.getWdate());
		logger.info("count: " + vo.getCount());
		logger.info("kcal: " + vo.getKcal());
		
		if(rs.r_selectOne(vo)>0) {
			result = rs.r_update(vo);
		}else {
			result=rs.r_insert(vo);
			
		}
			return result;
	}
	
	
	@RequestMapping(value = "/r_selectAll.do", method = RequestMethod.GET)
	public String r_selectAll(Model model) {
		logger.info("r_selectAll.do");

		List<RunVO> list=rs.selectAll();
		model.addAttribute("list", list);
		return "run/r_selectAll";
	}
	
	
	
	
	
	
}
