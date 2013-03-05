package com.danielsanfr.zimandroidwiki.model;

import java.util.List;

public class Notebook {
	
	private String title;
	private List<Page> pages;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Page> getPages() {
		return pages;
	}
	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

}
