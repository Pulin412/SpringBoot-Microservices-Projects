package com.sitemap.service.test;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.sitemap.service.SiteMapService;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
public class SiteMapServiceTest {

	@MockBean
	SiteMapService siteMapService;

	String site = "https://babylonhealth.com";
	String[] skipKeyWords = { "support", "blog" };
	String[] extensions = { "pdf" };
	String originalHostName = "babylonhealth.com";

	@Before
	public void setUp() {

		doNothing().when(siteMapService).processPage(isA(String.class), isA(String.class), isA(String[].class),
				isA(String[].class));
	}

	@Test
	public void whenProcessMethodCalled_thenNothingReturned() {

		siteMapService.processPage(site, originalHostName, skipKeyWords, extensions);
	}
	
	@Test
	public void whenGenerate_thenSiteMapReturned() {

		List<String> output = siteMapService.generateMap(site, skipKeyWords, extensions);
		Assert.assertNotNull(output);
	}
}
