package com.netins.crawler;

import com.netins.crawler.Spider;

public class SpiderTest {
	//Test Class
	public static void main(String[] args){
        //Url: "https://twitter.com/?lang=en", Search Keyword: "recruit"
        
        try {
			int maxLevel = 3;
			int maxThreads = 10;
			String searchWord = null;
			if (args.length >= 4) {
				maxThreads = Integer.parseInt(args[3]);
			}
			if (args.length >= 3) {
				maxLevel = Integer.parseInt(args[2]);
			}
			if (args.length >= 2) {
				Queue queue = new Queue();
				queue.push(new String(args[0]), 0);
				searchWord = args[1];
				new Spider((Queue) queue, maxLevel, maxThreads, searchWord);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.err.println("Usage: java PSucker <url> <searchWord> [<maxLevel> [<maxThreads>]]");
		System.err.println("Crawls the web searching keyword.");
    }
}
