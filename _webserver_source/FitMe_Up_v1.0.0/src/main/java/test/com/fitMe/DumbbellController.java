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

import test.com.fitMe.Dumbbell.model.DumbbellVO;
import test.com.fitMe.Dumbbell.service.DumbbellDAOService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class DumbbellController {
	private int result;
	private static final Logger logger = LoggerFactory.getLogger(DumbbellController.class);
	
	@Autowired
	DumbbellDAOService ds;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	
	@RequestMapping(value = "/d_selectOneOK.do", method = RequestMethod.POST)
	@ResponseBody
	public int d_selectOneOK(@RequestBody DumbbellVO vo) {
		logger.info("/d_selectOneOK.do");
		logger.info("id: " + vo.getUser_id());
		logger.info("wdate: " + vo.getWdate());
		logger.info("count: " + vo.getCount());

		
		if(ds.d_selectOne(vo)>0) {
			result = ds.d_update(vo);
		}else {
			result=ds.d_insert(vo);
			
		}
			return result;
	}

	
	@RequestMapping(value="/d_selectAll.do", method=RequestMethod.GET)
	public String d_selectAll(Model model) {
		logger.info("d_selectAll.do");
		
		List<DumbbellVO> list=ds.selectAll();
		model.addAttribute("list",list);
		return "dumbbell/d_selectAll";
	}
	
	
	
}
