package com.netins.crawler;

import java.util.*;

public class Queue {

	LinkedList evenQueue;
	LinkedList oddQueue;
	Set gatheredLinks;
	Set processedLinks;

	int maxElements;

	public Queue() {
		evenQueue = new LinkedList();
		oddQueue = new LinkedList();
		gatheredLinks = new HashSet();
		processedLinks = new HashSet();
		maxElements = -1;
	}
	
	public Queue(int maxSize) {
		evenQueue = new LinkedList();
		oddQueue = new LinkedList();
		gatheredLinks = new HashSet();
		processedLinks = new HashSet();
		maxElements = maxSize;
	}

	public void setMaxElements(int _maxElements) {
		maxElements = _maxElements;
	}

	public Set getGatheredElements() {
		return gatheredLinks;
	}

	public Set getProcessedElements() {
		return processedLinks;
	}

	public int getQueueSize(int level) {
		if (level % 2 == 0) {
			return evenQueue.size();
		} else {
			return oddQueue.size();
		}
	}

	public int getProcessedSize() {
		return processedLinks.size();
	}

	public int getGatheredSize() {
		return gatheredLinks.size();
	}

	public synchronized Object pop(int level) {
		String s;
		if (level % 2 == 0) {
			if (evenQueue.size() == 0) {
				return null;
			} else {
				s = (String) evenQueue.removeFirst();
			}
		} else {
			if (oddQueue.size() == 0) {
				return null;
			} else {
				s = (String) oddQueue.removeFirst();
			}
		}
		// convert the string to a url and add to the set of processed links
		// URL url = new URL(s);
		processedLinks.add(s);
		return s;

	}

	public synchronized boolean push(Object url, int level) {
		// don't allow more than maxElements links to be gathered
		if (maxElements != -1 && maxElements <= gatheredLinks.size())
			return false;
		String s = (String) url;
		if (gatheredLinks.add(s)) {
			// has not been in set yet, so add to the appropriate queue
			if (level % 2 == 0) {
				evenQueue.addLast(s);
			} else {
				oddQueue.addLast(s);
			}
			return true;
		} else {
			// this link has already been gathered
			return false;
		}
	}

	public synchronized void clear() {
		evenQueue.clear();
		oddQueue.clear();
	}
}