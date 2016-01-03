package com.netins.crawler;

import java.util.*;

import com.netins.crawler.Utils.Queue;

/**
 *
 * This code is in the public domain.
 *
 * @author Andreas Hess <andreas.hess@ucd.ie>, 01/02/2003
 * 
 */
public class URLQueue implements Queue {

	LinkedList evenQueue;
	LinkedList oddQueue;
	Set gatheredLinks;
	Set processedLinks;

	int maxElements;

	String filenamePrefix;

	public URLQueue() {
		evenQueue = new LinkedList();
		oddQueue = new LinkedList();
		gatheredLinks = new HashSet();
		processedLinks = new HashSet();
		maxElements = -1;
		filenamePrefix = "";
	}

	public URLQueue(int _maxElements, String _filenamePrefix) {
		evenQueue = new LinkedList();
		oddQueue = new LinkedList();
		gatheredLinks = new HashSet();
		processedLinks = new HashSet();
		maxElements = _maxElements;
		filenamePrefix = _filenamePrefix;
	}

	public void setFilenamePrefix(String _filenamePrefix) {
		filenamePrefix = _filenamePrefix;
	}

	public String getFilenamePrefix() {
		return filenamePrefix;
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
		// try to get element from the appropriate queue
		// is the queue is empty, return null
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