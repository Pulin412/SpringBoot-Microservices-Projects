/**
 *
 */
package com.pressford.publisher.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.PieChart;
import com.googlecode.charts4j.Slice;
import com.pressford.publisher.entities.Message;
import com.pressford.publisher.entities.User;
import com.pressford.publisher.service.MessageService;
import com.pressford.publisher.utility.Utility;

/**
 * @author Pulin
 *
 *         Controller to create pie chart for the number of likes on each
 *         message using the Google Charts API
 *
 */
@Controller
public class GraphController {

	private static final Logger log = LogManager.getLogger(GraphController.class);

	@Autowired
	private MessageService messageService;

	/**
	 * @param model
	 * @return View to show the pie chart
	 *
	 *         Get mapping to show the pie chart
	 */
	@RequestMapping(value = "/piechart", method = RequestMethod.GET)
	public String drawPieChart(ModelMap model) {

		/*
		 * 1. Create a hashmap for each message and the number of likes on it.
		 *
		 * 2. Get the count of all the likes in the hashmap.
		 *
		 * 3. Iterate the hashmap and create the pie chart.
		 *
		 * 3a. If there is no like on any message, that message won't be visible on the
		 * pie chart.
		 *
		 * 3b. If there are no messages, piechart with 0 likes will be shown.
		 */
		log.info("INSIDE drawPieChart METHOD OF GRAPH CONTROLLER");
		Iterable<Message> messages = messageService.findAll();
		HashMap<Message, Integer> messageLikesMap = new HashMap<>();
		int totalLikes = 0;

		for (Message message : messages) {
			int size = 0;
			Set<User> users = message.getLikedByEmployees();
			if (users != null) {
				size = users.size();
			}
			messageLikesMap.put(message, size);
		}
		for (int count : messageLikesMap.values()) {
			totalLikes += count;
		}

		List<Slice> sliceList = new ArrayList<>();
		int i = 0;

		for (Map.Entry<Message, Integer> entry : messageLikesMap.entrySet()) {
			Message message = entry.getKey();
			int likes = entry.getValue();
			if (totalLikes > 0) {
				if (likes > 0) {
					float percent = (likes * 100) / (totalLikes);
					sliceList.add(Slice.newSlice((int) percent, Utility.getColor(i), "Likes = " + String.valueOf(likes),
							message.getTitle()));
					i++;
				} else {
					continue;
				}
			} else {
				Slice s = Slice.newSlice(100, Color.newColor("CACACA"), "0 likes", "0 likes");
				PieChart pieChart = GCharts.newPieChart(s);
				pieChart.setTitle("Likes Distribution", Color.BLACK, 15);
				pieChart.setSize(720, 360);
				pieChart.setThreeD(true);

				model.addAttribute("pieUrl", pieChart.toURLString());
				return "display";
			}
		}
		Slice[] sliceArr = new Slice[sliceList.size()];
		PieChart pieChart = GCharts.newPieChart(sliceList.toArray(sliceArr));
		pieChart.setTitle("Likes Distribution", Color.BLACK, 15);
		pieChart.setSize(720, 360);
		pieChart.setThreeD(true);

		model.addAttribute("pieUrl", pieChart.toURLString());

		return "display";
	}
}
