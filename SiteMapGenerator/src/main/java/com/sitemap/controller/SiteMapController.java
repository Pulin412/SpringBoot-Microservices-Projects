package com.sitemap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sitemap.service.SiteMapService;

@Controller
public class SiteMapController {

	@Value("${skipKeyWords}")
	private String[] skipKeyWords;
	
	@Value("${extensions}")
	private String[] extensions;
	
	@GetMapping("/main")
	public String show(Model model) {
		return "index";
	}
	
	@Autowired
	private SiteMapService service;
	
	@PostMapping("/main")
	public String generateMap(@RequestParam("site") String site, Model model) {
		
		try {
			List<String> siteMap = service.generateMap(site, skipKeyWords, extensions);
			if(siteMap == null) {
				model.addAttribute("message", "Some error occured, please check the input");
			}else {
				model.addAttribute("message","Site Map generated successfully");
				model.addAttribute("siteMap",siteMap);
			}
		} catch (Exception e) {
			model.addAttribute("message", "Some error occured, try again..");
		}
		return "index";
	}
}
