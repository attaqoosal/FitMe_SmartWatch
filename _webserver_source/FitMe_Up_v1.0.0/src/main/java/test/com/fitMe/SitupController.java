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

import test.com.fitMe.Situp.model.SitupVO;
import test.com.fitMe.Situp.service.SitupDAOService;
import test.com.fitMe.Walk.model.WalkVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SitupController {
	private int a;
	private static final Logger logger = LoggerFactory.getLogger(SitupController.class);
	
	@Autowired
	SitupDAOService sts;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	

	
	@RequestMapping(value="/st_selectAll.do", method=RequestMethod.GET)
	public String st_selectAll(Model model) {
		logger.info("st_selectAll.do");
		
		List<SitupVO> list=sts.st_selectAll();
		model.addAttribute("list",list);
		return "situp/st_selectAll";
	}
	
	@RequestMapping(value = "/st_selectOneOK.do", method = RequestMethod.POST)
	@ResponseBody
	public int st_selectOneOK(@RequestBody SitupVO vo) {
		logger.info("/st_selectOneOK.do");
		logger.info("id: " + vo.getUser_id());
		logger.info("wdate: " + vo.getWdate());
		logger.info("count: " + vo.getCount());
		logger.info("kcal: " + vo.getKcal());
		
		if(sts.st_selectOne(vo)>0) {
			a = sts.st_update(vo);
		}else {
			a=sts.st_insert(vo);
			
		}
			return a;
	}
	
	
	
}
