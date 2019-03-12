package com.mkp.spider;


import java.sql.Time;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class CrawlerHandler extends WebCrawler {

	private static int count = 0;

	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		return href.startsWith("https://blog.csdn.net");
	}

	@Override
	public void visit(Page page) {
		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			String html = htmlParseData.getHtml();
			Document document = Jsoup.parse(html);
			Element content = document.getElementById("feedlist_id");
			Elements lis = content.getElementsByTag("li");
			for (Element li : lis) {
				if (li.getElementsByClass("read_num").hasText()) {
					System.out.println("count: " + count++ +
							" time:" + li.getElementsByClass("time").text() +
							" title: " + li.getElementsByClass("title").text() + 
							" " + li.getElementsByClass("read_num").text());
				}

			}
		}
	}
}
