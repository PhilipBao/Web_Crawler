package com.netins.crawler;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.netins.crawler.Utils.ControllableThread;

public class SpiderThread extends ControllableThread {

	// Arbitrary USER_AGENT
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) "
			+ "AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	private static final int REQUEST_OK_CODE = 200;

	@Override
	public void process(Object o) {
		try {
			Document currDocument = null;
			String pageURL = (String) o;
			
			currDocument = crawl(pageURL);
			//System.out.println("Searching for the word \"" + Spider.searchWord + "\" on " + pageURL);
			
			boolean result = searchForWord(currDocument, Spider.searchWord);
			
			if(result) {
				System.out.println("Successfully Searched the word \"" + Spider.searchWord + "\" on " + pageURL);
			}
			System.out.println("Site visited :" + Spider.counter.get());
			if(Spider.counter.addAndGet(1) >= Spider.MAX_PAGES_TO_REACH){
				cancelAll();
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
		}

	}

	private Document crawl(String url) {
		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);

			if (connection != null) {
				Document htmlDocument = connection.get();
				

				Elements linksInPage = htmlDocument.select("a[href]");

				if (linksInPage.size() > 0) {
					for (Element link : linksInPage) {
						//this.links.add(link.absUrl("href"));
						if (tc.getMaxLevel() == -1)
							queue.push(link.absUrl("href"), level);
						else
							queue.push(link.absUrl("href"), level + 1);
					}
				}
				
				if(checkStatus(connection)){
					return connection.get();
				}

			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean searchForWord(Document htmlDocument, String searchWord){
		if(htmlDocument == null || searchWord == null || searchWord.isEmpty()){
            return false;
        }
        String bodyText = htmlDocument.body().text();
        
        return bodyText.toLowerCase().contains(searchWord.toLowerCase());
        
	}
	
	private boolean checkStatus(Connection connection){
		int statusCode = connection.response().statusCode();
		String pageType = connection.response().contentType();
		String statusMes = " With status code: " + statusCode + " ";

		if (statusCode != REQUEST_OK_CODE
				|| !pageType.contains("text/html")) {
			System.out
					.println("\n**Failure -- "
							+ ((statusCode != REQUEST_OK_CODE) ? statusMes
									: "")
							+ ((!pageType.contains("text/html")) ? " Page not HTML type"
									: ""));
			return false;
		} else {
			return true;
		}
	}

}
