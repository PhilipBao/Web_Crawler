package com.netins.crawler;

import java.util.AbstractQueue;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;

public class Spider {
	private final int MAX_PAGES_TO_REACH;
	private final static int MAX_SIZE_OF_BLOCKING_QUEUE = 80;
	private Set<String> pagesVisited = new HashSet<String>();
	//private ArrayBlockingQueue<String> pagesToVisit = new LinkedList<String>();
	//private ArrayBlockingQueue<String> pagesToVisit = new ArrayBlockingQueue<String>(/*MAX_SIZE_OF_BLOCKING_QUEUE*/Integer.MAX_VALUE);
	private Queue<String> pagesToVisit = new LinkedList<String>();

	public Spider(){
		MAX_PAGES_TO_REACH = 10;
	}
	
	public Spider(int maxPages){
		MAX_PAGES_TO_REACH = maxPages;
	}
	
	public void search(String url, String searchWord){
		SpiderLeg leg = new SpiderLeg();
		
		while(this.pagesVisited.size() < MAX_PAGES_TO_REACH){
			String currentUrl;
			
			if(this.pagesToVisit.isEmpty()){
				currentUrl = url;
				this.pagesVisited.add(url);
			} else {
				//this.pagesToVisit.add(url);
				currentUrl = this.nextUrl();
			}
			
			leg.crawl(currentUrl);//do crawling
			
			boolean success = leg.searchForWord(searchWord);
			
			if (success){
				System.out.println("**Success -- Word " + searchWord + " found at: " + currentUrl);
                break;
			}
			List<String> linksInCurrentPages = leg.getLinks();
			int spareSpace = MAX_SIZE_OF_BLOCKING_QUEUE - this.pagesToVisit.size();
			this.pagesToVisit.addAll(linksInCurrentPages); //List<String>
			
			System.out.println("TAG_DEBUGGGGGGGGGG " + Integer.toString(spareSpace));
        }
		
        System.out.println("**Done-Visited " + this.pagesVisited.size() + " web page(s)");
        
        System.out.println("**Error Visiting : " + leg.getErrUrls());
    }
			
		

	private String nextUrl(){
		String nextUrl;
		do{
			nextUrl = this.pagesToVisit.poll();
		} while (this.pagesVisited.contains(nextUrl));
		
		this.pagesVisited.add(nextUrl);
        return nextUrl;
	}
	
}
