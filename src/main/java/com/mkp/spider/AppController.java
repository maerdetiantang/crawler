package com.mkp.spider;

import java.util.HashSet;
import org.apache.http.message.BasicHeader;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class AppController {
	public static void main(String[] args) throws Exception {
		String crawlStorageFolder = "/private/tmp/crawler_cache";
		int numberOfCrawlers = 10;
		CrawlConfig config = new CrawlConfig();

		config.setFollowRedirects(false);
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setMaxDepthOfCrawling(0); // 抓取深度
		config.setPolitenessDelay(200); // 请求延时等待时间

		HashSet<BasicHeader> collections = new HashSet<BasicHeader>();
		collections.add(new BasicHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) "
				+ "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3192.0 Safari/537.36"));
		collections.add(new BasicHeader("Accept", "text/html,application/xhtml+xml,application/xml;"
				+ "q=0.9,image/webp,image/apng,*/*;q=0.8"));
		collections.add(new BasicHeader("Accept-Encoding", "gzip,deflate,sdch"));
		collections.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6"));
		collections.add(new BasicHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"));
		collections.add(new BasicHeader("Connection", "keep-alive"));
		config.setDefaultHeaders(collections);

		// 实力化爬虫控制器
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		// 添加种子网址，即爬取网页的入口URL，然后爬虫根据此URL对应的页面和页面中的链接进行深度爬取
		controller.addSeed("https://blog.csdn.net");

		// 开启指定数量线程开始爬取
		controller.start(CrawlerHandler.class, numberOfCrawlers);
	}
}
