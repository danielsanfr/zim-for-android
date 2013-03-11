package com.danielsanfr.zimandroidwiki.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.danielsanfr.zimandroidwiki.controller.ManageFiles;

public class Notebook implements Comparable<Notebook> {

	private String name;
	private List<Page> pages;
	private List<String> pagesName;

	public Notebook(String name) {
		// TODO Auto-generated constructor stub
		notebookInitialize(name, null);
	}

	public Notebook(File file) {
		StringBuffer stringBuffer = new StringBuffer();
		String path = file.getPath();
		for (int i = path.length() - 1; i >= 0; i--) {
			if (path.charAt(i) != '/')
				stringBuffer.append(path.charAt(i));
			else
				i = 0;
		}
		notebookInitialize(stringBuffer.reverse().toString(), file);
	}

	private void notebookInitialize(String name, File file) {
		this.name = name;
		pages = new ArrayList<Page>();
		pagesName = new ArrayList<String>();
		findPages(file);
	}

	private void findPages(File file) {
		if (file == null)
			file = new File(ManageFiles.ROOT_DIRECTORY, name);
		ManageFiles manageFiles = new ManageFiles();
		List<File> files = manageFiles.getPages(file);
		for (File file2 : files) {
			pages.add(new Page(file2));
		}
		Collections.sort(pages);
		setListPagesName();
	}
	
	private void setListPagesName() {
		for (Page page : pages) {
			pagesName.add(page.getTitle());
		}
	}
	
	public List<String> getListNameOfPages() {
		return pagesName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Page> getPages() {
		return pages;
	}

	public void addPage(Page page) {
		pages.add(page);
	}

	public void removePage(int location) {
		pages.remove(location);
	}
	
	

	@Override
	public int compareTo(Notebook notebook) {
		// TODO Auto-generated method stub
		return name.compareToIgnoreCase(notebook.getName());
		// return notebook.getName().compareTo(name);
	}

}
