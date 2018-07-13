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

import test.com.fitMe.Heartbeat.model.HeartbeatVO;
import test.com.fitMe.Heartbeat.service.HeartbeatDAOService;
import test.com.fitMe.User.model.UserVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HeartbeatController {
	private int a;
	private static final Logger logger = LoggerFactory.getLogger(HeartbeatController.class);
	
	@Autowired
	HeartbeatDAOService hs;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	@RequestMapping(value = { "/h_insertOK.do" }, method = RequestMethod.POST)
	@ResponseBody
	public int h_insertnOK(@RequestBody HeartbeatVO vo) {
		logger.info("h_insertOK");
		logger.info("id: " + vo.getUser_id());
		logger.info("pw: " + vo.getNum());
		logger.info("name: " + vo.getWdate());
		logger.info("max: " + vo.getMax());
		logger.info("int: " + vo.getMin());
		return hs.h_insert(vo);
	}

	
	@RequestMapping(value="/h_selectAll.do", method=RequestMethod.GET)
	public String h_selectAll(Model model) {
		logger.info("h_selectAll.do");
		
		List<HeartbeatVO> list=hs.h_selectAll();
		model.addAttribute("list",list);
		return "heartbeat/h_selectAll";
	}
	
	
	
}
