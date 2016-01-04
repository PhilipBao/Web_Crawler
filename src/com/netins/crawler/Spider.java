package com.netins.crawler;

import com.netins.crawler.Utils.ThreadController;
import com.netins.crawler.SpiderThread;
import com.netins.crawler.Utils.MessageReceiver;

import java.util.concurrent.atomic.AtomicInteger;

public class Spider implements MessageReceiver {
	final static int MAX_PAGES_TO_REACH = 20;
	
	static AtomicInteger counter = new AtomicInteger();
	static String searchWord = "";

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
	
	@Override
	public void receiveMessage(Object o, int threadId) {
		System.out.println("[" + threadId + "] " + o.toString());
	}

	@Override
	public void finished(int threadId) {
		System.out.println("[" + threadId + "] finished");
	}

	@Override
	public void finishedAll() {
		// not implemented
		return;
	}
	
}