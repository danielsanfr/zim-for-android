package com.danielsanfr.zimandroidwiki.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PageModel implements Comparable<PageModel> {

	private static final String CONTENT_TYPE = new String("text/x-zim-wiki");
	private static final String WIKI_FORMAT = new String("zim 0.4");

	private String name;
	private String title;
	private String contentType;
	private String wikiFormat;
	private String creationDate;
	private String content;

	private List<PageModel> subPages;

	@SuppressWarnings("deprecation")
	public PageModel(String title, Date today) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.contentType = CONTENT_TYPE;
		this.wikiFormat = WIKI_FORMAT;
		this.creationDate = today.toGMTString();
		subPages = new ArrayList<PageModel>();
		generateName();
		content = new String(title + "\nCriado em " + today.toLocaleString()
				+ "\n");
	}

	public PageModel(File file) {
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			String line;
			StringBuffer content = new StringBuffer();
			contentType = bufferedReader.readLine();
			wikiFormat = bufferedReader.readLine();
			creationDate = bufferedReader.readLine();
			bufferedReader.readLine();
			while ((line = bufferedReader.readLine()) != null) {
				content.append(line + "\n");
			}
			this.content = content.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		subPages = new ArrayList<PageModel>();
		StringBuffer stringBuffer = new StringBuffer();
		String path = file.getPath();
		for (int i = path.length() - 1; i >= 0; i--) {
			if (path.charAt(i) != '/')
				stringBuffer.append(path.charAt(i));
			else
				i = 0;
		}
		this.name = stringBuffer.reverse().toString();
		generateTitle();
	}

	public String getContentToBeSaved() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("Content-Type: " + CONTENT_TYPE + "\nWiki-Format: "
				+ WIKI_FORMAT + "\nCreation-Date: " + creationDate + "\n\n");
		stringBuffer.append(content);
		return stringBuffer.toString();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		generateName();
	}

	public String getName() {
		return name;
	}

	public String getContentType() {
		return contentType;
	}

	public String getWikiFormat() {
		return wikiFormat;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<PageModel> getSubPages() {
		return subPages;
	}

	public void addSubPages(PageModel page) {
		subPages.add(page);
	}

	private void generateName() {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < title.length(); i++) {
			if (title.charAt(i) == ' ') {
				stringBuffer.append('_');
			} else
				stringBuffer.append(title.charAt(i));
		}
		this.name = stringBuffer.toString() + ".txt";
	}

	private void generateTitle() {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < name.length() - 4; i++) {
			if (name.charAt(i) == '_') {
				stringBuffer.append(' ');
			} else
				stringBuffer.append(name.charAt(i));
		}
		this.title = stringBuffer.toString();
	}

	@Override
	public int compareTo(PageModel page) {
		// TODO Auto-generated method stub
		return title.compareToIgnoreCase(page.getTitle());
	}

}
