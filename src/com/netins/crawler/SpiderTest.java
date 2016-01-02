package com.netins.crawler;

public class SpiderTest {
	//Test Class
	public static void main(String[] args){
        Spider spider = new Spider(100);
        spider.search("https://twitter.com/?lang=en", "recruit");
        
    }
}
