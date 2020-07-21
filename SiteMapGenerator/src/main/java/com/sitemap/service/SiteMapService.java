/**
 * 
 */
package com.sitemap.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

/**
 * @author Pulin
 *
 */
@Service
public class SiteMapService {

	static List<String> siteList = new ArrayList<>();

	public List<String> generateMap(String site, String[] skipKeyWords, String[] extensions) {

		if (site != null && site.length() > 0) {
			String[] sArr = site.split("/");
			if (sArr.length > 1) {
				processPage(site, sArr[2], skipKeyWords, extensions);
				return siteList;
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	public void processPage(String URL, String originalHostName, String[] skipKeyWords, String[] extensions) {

		// check if invalid links or any other path is there.
		if (Arrays.asList(extensions).stream().anyMatch(URL::contains))
			return;

		String[] sArr = URL.split("/");
		String dynamicHostName = "";
		if (sArr != null && sArr.length > 1) {
			dynamicHostName = sArr[2];
		}

		// ignore the customized keywords given in the properties file in the URL
		if (Arrays.asList(skipKeyWords).stream().anyMatch(URL::contains)) {
			return;
		}

		// if the URL doesn't start with the main hostname, ignore it.
		else if (!"".equals(dynamicHostName) && !dynamicHostName.contains(originalHostName)) {
			return;
		} else if (URL.contains(dynamicHostName) && URL.endsWith("/")) {
			URL = URL.substring(0, URL.length() - 1);
		} else if (URL.contains(dynamicHostName) && !URL.endsWith("/")) {
		} else {
			// ignore if any other external URL is present
			return;
		}

		// check if the link is already added or not
		if (!siteList.contains(URL)) {
			siteList.add(URL);
			Document doc = null;
			try {
				doc = Jsoup.connect(URL).get();
			} catch (IOException e1) {
				return;
			}

			try {
				Elements questions = doc.select("a[href]");
				for (Element link : questions) {
					processPage(link.attr("abs:href"), originalHostName, skipKeyWords, extensions);
				}
			} catch (Exception ex) {
				return;
			}
		} else {
			// do nothing
			return;
		}

	}
}
