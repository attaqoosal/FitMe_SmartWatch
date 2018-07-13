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

import test.com.fitMe.Register.model.RegisterVO;
import test.com.fitMe.Register.service.RegisterDAOService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class RegisterController {

	private int flag;
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	RegisterDAOService rgs;

	/**
	 * Simply selects the home view to render by returning its name.
	 */


	@RequestMapping(value = "/rg_insert.do", method = RequestMethod.GET)
	public String rg_insert() {
		logger.info("rg_insert");

		return "register/rg_insert";
	}

	@RequestMapping(value = "/rg_insertOK.do", method = RequestMethod.GET)
	@ResponseBody
	public int rg_insertOK(String user_id, int serial_num, HttpServletRequest request) {
		logger.info("/rg_insertOK.do");
		RegisterVO vo = new RegisterVO();
		vo.setUser_id(user_id);
		vo.setSerial_num(serial_num);
		flag = rgs.register_selectOne(vo);
		if (flag < 1) {
			return 0;
		} else {
			flag = rgs.insert(vo);

			return flag;
		}

	}

	@RequestMapping(value = "/rg_selectAll.do", method = RequestMethod.GET)
	public String rg_selectAll(Model model) {
		logger.info("rg_selectAll");

		List<RegisterVO> list = rgs.selectAll();
		model.addAttribute("list", list);
		return "register/rg_selectAll";
	}

	@RequestMapping(value = "/rg_deleteOK.do", method = RequestMethod.GET)
	public String delete(RegisterVO vo) {
		logger.info("delete");
		flag = rgs.delete(vo);
		logger.info("flag: " + flag);
		return "redirect:rg_selectAll.do";
	}
	

}
