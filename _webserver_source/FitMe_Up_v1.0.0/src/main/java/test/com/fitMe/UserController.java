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

import test.com.fitMe.User.model.UserVO;
import test.com.fitMe.User.service.UserDAOService;
import test.com.fitMe.data.service.DataDAOService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class UserController {

	private int flag;
	private static String result;
	private static String result2;
	private static int result3;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserDAOService us;
	DataDAOService dts;

	public String getResult() {
		return result;
	}

	public String getResult2() {
		return result2;
	}

	public int getResult3() {
		return result3;
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = { "/", "/main.do" }, method = RequestMethod.GET)
	public String main() {

		return "main";
	}

	@RequestMapping(value = { "home.do" }, method = RequestMethod.GET)
	public String home() {

		return "home";
	}

/*	@RequestMapping(value = { "loginCheck.do" }, method = RequestMethod.GET)
	public String loginCheck(UserVO vo, Model model) {

		return "redirect:main.do";
	}*/

	@RequestMapping(value = { "/user_login.do" }, method = RequestMethod.GET) //안으로이드에서 로그인
	@ResponseBody
	public UserVO a_loginCheck(UserVO vo, HttpServletRequest request) {
		logger.info("login");
		UserVO flag1 = us.loginCheck(vo);
		return flag1; // 입력된 아이디와 비밀전호가 존재하는지 SQL문으로 확인!
	}


	@RequestMapping(value = { "/admin_idCheck.do" }, method = RequestMethod.POST)
	//@ResponseBody
	public String admin_idCheck(UserVO vo, HttpServletRequest request) {
		logger.info("user_idCheck");
		logger.info("id: " + vo.getUser_id());
		logger.info("pw: " + vo.getPw());

		UserVO flag1 = us.loginCheck(vo);
		
		logger.info("flag1"+flag1);
		if(flag1 != null) {
			return "home";
		}else
			return "main";
	}

	
/*	@RequestMapping(value = { "/overlap_check.do" }, method = RequestMethod.GET)
	@ResponseBody
	public int overlap_check(String user_id, String pw, HttpServletRequest request) {
		logger.info("overlap");

		UserVO vo = new UserVO();
		vo.setUser_id(user_id);
		vo.setPw(pw);
		int flag1 = us.overlap_check(vo);
		if (flag1 < 1) {
			return 0;
		} else {

			return flag1;
		} // 입력된 아이디와 비밀전호가 존재하는지 SQL문으로 확인!
	}*/

	@RequestMapping(value = { "/user_idCheck.do" }, method = RequestMethod.GET) //안드로이드에서 아이디 중복체크
	@ResponseBody
	public int user_idCheck(String user_id, String pw, HttpServletRequest request) {
		logger.info("user_idCheck");
		logger.info("id: " + user_id);
		UserVO vo1 = new UserVO();
		vo1.setUser_id(user_id);
		vo1.setPw(pw);
		
		int flag1 = us.overlap_check(vo1);
		
		if (flag1 < 1) {
			return 0;
		} else {
			return flag1;
		}
	}

	@RequestMapping(value = { "/logout.do" }, method = RequestMethod.GET)
	public String loginout(UserVO vo, HttpServletRequest request) {
		logger.info("logout");

		return "home";
	}

	@RequestMapping(value = { "/join.do" }, method = RequestMethod.GET)
	public String join() {
		logger.info("join");

		return "join";
	}

	@RequestMapping(value = { "/user_joinOK.do" }, method = RequestMethod.POST)
	@ResponseBody
	public int user_joinOK(@RequestBody UserVO vo) {
		logger.info("user_joinOK");
		logger.info("id: " + vo.getUser_id());
		logger.info("pw: " + vo.getPw());
		logger.info("name: " + vo.getName());
		logger.info("birth: " + vo.getBirth());
		logger.info("gender: " + vo.getGender());
		logger.info("height: " + vo.getHeight());
		logger.info("weight: " + vo.getWeight());
		logger.info("tel: " + vo.getTel());
		logger.info("u_check: " + vo.getU_check());

		return us.insert(vo);
	}

	@RequestMapping(value = { "/admin_joinOK.do" }, method = RequestMethod.POST)
	public String admin_joinOK(UserVO vo) {
		logger.info("admin_joinOK");
		logger.info("id: " + vo.getUser_id());
		logger.info("pw: " + vo.getPw());
		logger.info("name: " + vo.getName());
		logger.info("birth: " + vo.getBirth());
		logger.info("gender: " + vo.getGender());
		logger.info("height: " + vo.getHeight());
		logger.info("weight: " + vo.getWeight());
		logger.info("tel: " + vo.getTel());
		logger.info("u_check: " + vo.getU_check());

		flag = us.insert(vo);

		return "redirect:home.do";

	}

	@RequestMapping(value = "/u_insertOK.do", method = RequestMethod.POST)
	public String and_insertOK(UserVO vo, HttpServletRequest request, Model model) {
		logger.info("/insertOK.do");

		flag = us.insert(vo);
		logger.info("flag: " + flag);
		return "redirect:a_selectAll.do";

	}

	@RequestMapping(value = "/a_insert.do", method = RequestMethod.GET)
	public String a_insert() {
		logger.info("insert");

		return "user/a_insert";
	}

	@RequestMapping(value = "/a_insertOK.do", method = RequestMethod.POST)
	public String a_insertOK(UserVO vo, HttpServletRequest request, Model model) {
		logger.info("/a_insertOK.do");

		flag = us.insert(vo);
		logger.info("flag: " + flag);
		return "redirect:u_selectAll.do";

	}

	@RequestMapping(value = "/a_selectAll.do", method = RequestMethod.GET)
	public String selectAll(Model model) {
		logger.info("selectAll");

		return "user/a_selectAll";
	}

	@RequestMapping(value = "/a_json_selectAll.do", method = RequestMethod.POST)
	@ResponseBody
	public List<UserVO> a_json_selectAll() {
		logger.info("/a_json_selectAll.do");
		List<UserVO> list = us.selectAll();
		logger.info("list.size():" + list.size());
		logger.info("list:" + list.get(0).getUser_id());
		return list;
	}

	@RequestMapping(value = "/a_selectOne.do", method = RequestMethod.GET)
	public String selectOne(UserVO vo, Model model) {
		logger.info("/a_selectOne.do");
		logger.info("user_id: " + vo.getUser_id());

		UserVO vo2 = us.selectOne(vo);
		vo = vo2;
		model.addAttribute("vo", vo);
		return "user/a_selectOne";
	}

	@RequestMapping(value = "/a_updateOK.do", method = RequestMethod.GET)
	public String updateOK(UserVO vo) {
		logger.info("/a_updateOK");
		logger.info("id: " + vo.getUser_id());

		flag = us.update(vo);
		logger.info("flag: " + flag);

		return "redirect:a_selectAll.do";
	}

	@RequestMapping(value = "/a_deleteOK.do", method = RequestMethod.GET)
	public String delete(UserVO vo) {
		logger.info("delete");
		flag = us.delete(vo);
		logger.info("flag: " + flag);
		return "redirect:u_selectAll.do";
	}

}
