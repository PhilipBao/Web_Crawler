package com.netins.crawler;

import com.netins.crawler.Spider;
import com.netins.crawler.URLQueue;
import com.netins.crawler.Utils.Queue;

import java.net.URL;

public class SpiderTest {
	//Test Class
	public static void main(String[] args){
        //spider.search("https://twitter.com/?lang=en", "recruit");
        
        try {
			int maxLevel = 3;
			int maxThreads = 10;
			int maxDoc = -1;
			String searchWord = null;
			if (args.length >= 6) {
				maxThreads = Integer.parseInt(args[5]);
			}
			if (args.length >= 5) {
				maxDoc = Integer.parseInt(args[4]);
			}
			if (args.length >= 4) {
				maxLevel = Integer.parseInt(args[3]);
			}
			if (args.length >= 3) {
				searchWord = args[2];
			}
			if (args.length >= 2) {
				URLQueue queue = new URLQueue();
				queue.setFilenamePrefix(args[1]);
				queue.setMaxElements(maxDoc);
				queue.push(new String(args[0]), 0);
				new Spider((Queue) queue, maxLevel, maxThreads, searchWord);
				return;
			}
		} catch (Exception e) {
			System.err.println("An error occured: ");
			e.printStackTrace();
			// System.err.println(e.toString());
		}
		System.err.println("Usage: java PSucker <url> <filenamePrefix> [<maxLevel> [<maxDoc> [<maxThreads>]]]");
		System.err.println("Crawls the web for jpeg pictures and mpeg, avi or wmv movies.");
		System.err.println("-1 for either maxLevel or maxDoc means 'unlimited'.");
        
    }
}
