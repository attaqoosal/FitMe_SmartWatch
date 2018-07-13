package test.com.fitMe;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import test.com.fitMe.data.model.DataVO;
import test.com.fitMe.data.service.DataDAOService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class DataController {

	private MaxSports[] max = new MaxSports[12];
	private static final Logger logger = LoggerFactory.getLogger(DataController.class);

	@Autowired
	DataDAOService dts;

	/**
	 * Simply selects the home view to render by returning its name.
	 */

	@RequestMapping(value = "/data_select2.do", method = RequestMethod.GET)
	public String data_selectAll(Model model) {
		logger.info("/data_select.do");
		List<DataVO> list = dts.selectAll();
		logger.info("VIEW:" + list.get(0).getAvg_walk());
		model.addAttribute("list", list);
		return "data/data_select2";
	}

	@RequestMapping(value = "/data_json_select2.do", method = RequestMethod.POST)
	@ResponseBody
	public List<DataVO> data_json_selectAll() {
		logger.info("/data_json_select.do");
		List<DataVO> list = dts.selectAll();

		Collections.sort(list);
		for (int i = 0; i < list.size(); i++) {
			logger.info("json     :" + list.get(i).gen + " " + list.get(i).avg_walk);
		}
		return list;
	}


	@RequestMapping(value = "/data_select.do", method = RequestMethod.GET)
	public String data_select(Model model) {
		logger.info("/data_select.do");

		return "data/data_select";
	}



	@RequestMapping(value = "/data_avg.do", method = RequestMethod.GET)
	public String data_avg(Model model) {
		logger.info("/data_avg.do");

		return "data/data_avg";
	}

	@RequestMapping(value = "/data_real.do", method = RequestMethod.GET)
	public String data_real(Model model) {
		logger.info("/data_real.do");

		return "data/data_real";
	}

	@RequestMapping(value = "/data_popular.do", method = RequestMethod.GET)
	public String data_popular(Model model) {
		logger.info("/data_popular.do");

		return "data/data_popular";
	}

	@RequestMapping(value = "/data_json_popular.do", method = RequestMethod.POST)
	@ResponseBody
	public MaxSports[] data_json_popular() {
		logger.info("/data_json_popular.do");
		List<DataVO> list_jump = dts.jumprope_selectAll();
		Collections.sort(list_jump);

		logger.info("aaaaaa" + list_jump.get(0).getType());
		List<DataVO> list_barbell = dts.barbell_selectAll();
		Collections.sort(list_barbell);

		List<DataVO> list_dumbbell = dts.dumbbell_selectAll();
		Collections.sort(list_dumbbell);

		List<DataVO> list_run = dts.run_selectAll();
		Collections.sort(list_run);

		List<DataVO> list_situp = dts.situp_selectAll();
		Collections.sort(list_situp);

		List<DataVO> list_walk = dts.walk_selectAll();
		Collections.sort(list_walk);
		
		for(int i = 0; i < 12; i++) {
			MaxSports maxSports = new MaxSports();
			maxSports.type = "없음";
			if(maxSports.max < list_walk.get(i).getPopular()) {
				maxSports.max = list_walk.get(i).getPopular();
				maxSports.type = list_walk.get(i).getType();
				if(maxSports.max < list_situp.get(i).getPopular()) {
					maxSports.max = list_situp.get(i).getPopular();
					maxSports.type = list_situp.get(i).getType();
					if(maxSports.max < list_run.get(i).getPopular()) {
						maxSports.max = list_run.get(i).getPopular();
						maxSports.type = list_run.get(i).getType();
						if(maxSports.max < list_barbell.get(i).getPopular()) {
							maxSports.max = list_barbell.get(i).getPopular();
							maxSports.type = list_barbell.get(i).getType();
							if(maxSports.max < list_dumbbell.get(i).getPopular()) {
								maxSports.max = list_dumbbell.get(i).getPopular();
								maxSports.type = list_dumbbell.get(i).getType();
								if(maxSports.max < list_jump.get(i).getPopular()) {
									maxSports.max = list_jump.get(i).getPopular();
									maxSports.type = list_jump.get(i).getType();
								}
							}
						}
					}
				}
			}
			max[i] = maxSports;
		}
		

		return max;
	}

}

class MaxSports {
	public String type;
	public int max;
	
	public MaxSports() {
		
	}
}
