package com.danielsanfr.zimandroidwiki.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.danielsanfr.zimandroidwiki.controller.ManageFiles;

public class NotebookModel implements Comparable<NotebookModel> {

	private String name;
	private List<PageModel> pages;
	private List<String> pagesName;

	public NotebookModel(String name) {
		// TODO Auto-generated constructor stub
		notebookInitialize(name, null);
	}

	public NotebookModel(File file) {
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
		pages = new ArrayList<PageModel>();
		pagesName = new ArrayList<String>();
		findPages(file);
	}

	private void findPages(File file) {
		if (file == null)
			file = new File(ManageFiles.ROOT_DIRECTORY, name);
		ManageFiles manageFiles = new ManageFiles();
		List<File> files = manageFiles.getPages(file);
		for (File file2 : files) {
			pages.add(new PageModel(file2));
		}
		Collections.sort(pages);
		setListPagesName();
	}

	private void setListPagesName() {
		for (PageModel page : pages) {
			pagesName.add(page.getTitle());
		}
	}
	
	public void updateNotebook() {
		notebookInitialize(name, ManageFiles.getDirectory(name));
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

	public List<PageModel> getPages() {
		return pages;
	}

	public void addPage(PageModel page) {
		pages.add(page);
		ManageFiles manageFiles = new ManageFiles();
		try {
			manageFiles.createFile(name, page.getName(),
					page.getContentToBeSaved());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.sort(pages);
	}

	public void removePage(int location) {
		pages.remove(location);
	}

	@Override
	public int compareTo(NotebookModel notebook) {
		// TODO Auto-generated method stub
		return name.compareToIgnoreCase(notebook.getName());
		// return notebook.getName().compareTo(name);
	}

}
