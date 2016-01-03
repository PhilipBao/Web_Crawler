package com.netins.crawler;

import com.netins.crawler.Utils.ThreadController;
import com.netins.crawler.SpiderThread;
import com.netins.crawler.Utils.MessageReceiver;
import com.netins.crawler.Utils.Queue;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Spider implements MessageReceiver {
	final static int MAX_PAGES_TO_REACH = 20;
	private final static int MAX_SIZE_OF_BLOCKING_QUEUE = 80;
	private Set<String> pagesVisited = new HashSet<String>();
	
	static AtomicInteger counter = new AtomicInteger();
	static String searchWord = "";
	
	
	/*
	 * public PSucker(Queue q, int maxLevel, int maxThreads)
		throws InstantiationException, IllegalAccessException {
		ThreadController tc = new ThreadController(PSuckerThread.class,
												   maxThreads,
												   maxLevel,
												   q,
												   0,
												   this);
	}
	 */
	
	public Spider(Queue q, int maxLevel, int maxThreads, String searchWord)
		throws InstantiationException, IllegalAccessException {
		this.searchWord = searchWord;
		ThreadController tc = new ThreadController(SpiderThread.class,
				   maxThreads,
				   maxLevel,
				   q,
				   0,
				   this);
	}
	
	
	/*public void search(String url, String searchWord){
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
			
		
	private Queue<String> pagesToVisit = new LinkedList<String>();
	
	private String nextUrl(){
		String nextUrl;
		do{
			nextUrl = this.pagesToVisit.poll();
		} while (this.pagesVisited.contains(nextUrl));
		
		this.pagesVisited.add(nextUrl);
        return nextUrl;
	}*/



	@Override
	public void receiveMessage(Object o, int threadId) {
		// TODO Auto-generated method stub
		System.out.println("[" + threadId + "] " + o.toString());
	}



	@Override
	public void finished(int threadId) {
		// TODO Auto-generated method stub
		System.out.println("[" + threadId + "] finished");
	}



	@Override
	public void finishedAll() {
		// TODO Auto-generated method stub
		// not implemented
		return;
	}
	
}
