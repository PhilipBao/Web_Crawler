package com.netins.crawler;


import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SpiderLeg {
	
	// Arbitrary USER_AGENT
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	
    private static final int REQUEST_OK_CODE = 200;
    
	private List<String> links = new LinkedList<String>();
	private List<String> errLinks = new LinkedList<String>();
	private Document htmlDocument; // Current web page
	
	// Give it a URL and it makes an HTTP request for a web page
	public boolean crawl(String url){
		try{
			System.out.println("\n-------- **Start Processing -- URL: " + url + " --------");
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            
            if(connection != null){
            	this.htmlDocument = connection.get();
            
	            int statusCode = connection.response().statusCode();
	            String pageType = connection.response().contentType();
	            String statusMes = " With status code: " + statusCode;
	            
	            if(statusCode != REQUEST_OK_CODE || !pageType.contains("text/html")){
					System.out.println("\n**Failure -- " + 
							((statusCode != REQUEST_OK_CODE)? statusMes : "") + 
							((!pageType.contains("text/html"))? " Page not HTML type":""));
					
					errLinks.add(url);
				} else {
					System.out.println("\n**Processing -- Received web page at " + url);
				}
	
	            Elements linksInPage = htmlDocument.select("a[href]");
	            
	            if(linksInPage.size() > 0) {
	            	System.out.println("Found (" + linksInPage.size() + ") links");
		            for(Element link : linksInPage){
		                this.links.add(link.absUrl("href"));
		            }
	            }
	            
	            return true;
	            
	        } else {
	        	errLinks.add(url);
            	this.htmlDocument = null;
            }
            
        } catch(IOException ioe){
            // HTTP Error Handle
        	errLinks.add(url);
            System.out.println("Error in out HTTP request " + ioe.toString());
            
        } catch(Exception e){
            // Unexpected Error Handle
        	errLinks.add(url);
            System.out.println("Unexpected Error " + e.toString());
            
        }
		return false;
	}
	
	// Find a word on the page
	public boolean searchForWord(String searchWord){
		if(this.htmlDocument == null || searchWord == null || searchWord.isEmpty()){
            return false;
        }
		
		System.out.println("Searching for the word " + searchWord + "...");
        String bodyText = this.htmlDocument.body().text();
        
        return bodyText.toLowerCase().contains(searchWord.toLowerCase());
        
	}
	
	// Returns a list of all URLs on the page
	public List<String> getLinks(){
		@SuppressWarnings("unchecked")
		LinkedList<String> res = (LinkedList<String>) ((LinkedList<String>) this.links).clone();
		
		this.links.clear();
		return res;
		
	}
	
	// Returns a list of URLs with error
	public List<String> getErrUrls(){
		@SuppressWarnings("unchecked")
		LinkedList<String> res = (LinkedList<String>) ((LinkedList<String>) this.errLinks).clone();
		
		this.errLinks.clear();
		return res;
		
	}
}
