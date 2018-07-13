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

import test.com.fitMe.Heartbeat.model.HeartbeatVO;
import test.com.fitMe.Sleep.model.SleepVO;
import test.com.fitMe.Sleep.service.SleepDAOService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SleepController {
	private int flag;
	private static final Logger logger = LoggerFactory.getLogger(SleepController.class);
	
	@Autowired
	SleepDAOService sls;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@RequestMapping(value = "/sl_selectAll.do", method = RequestMethod.GET)
	public String selectAll(Model model) {
		logger.info("selectAll");
		
		return "sleep/sl_selectAll";
	}
	@RequestMapping(value = "/sl_json_selectAll.do", method = RequestMethod.POST)
	@ResponseBody
	public List<SleepVO> sl_json_selectAll() {
		logger.info("/sl_json_selectAll.do");
		List<SleepVO> list = sls.selectAll();
		logger.info("list.size():" + list.size());
		logger.info("list:" + list.get(0).getUser_id());
		return list;
	}

	@RequestMapping(value = { "/sl_insertOK.do" }, method = RequestMethod.POST)
	@ResponseBody
	public int sl_joinOK(@RequestBody SleepVO vo) {
		logger.info("sl_insertOK");
		logger.info("id: " + vo.getUser_id());
		logger.info("pw: " + vo.getNum());
		logger.info("name: " + vo.getWdate());
		logger.info("birth: " + vo.getValue());

		return sls.sl_insert(vo);
	}
	
}
