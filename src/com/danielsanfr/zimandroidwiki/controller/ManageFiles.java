package com.danielsanfr.zimandroidwiki.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

public final class ManageFiles {

	public static final File ROOT_DIRECTORY = new File(Environment
			.getExternalStorageDirectory().toString() + "/ZimAndroidWiki");

	public static Boolean rootDirectoryExist() {
		Boolean exist = ROOT_DIRECTORY.exists();
		// Boolean exist = ROOT_DIRECTORY.isDirectory();
		if (!exist)
			ROOT_DIRECTORY.mkdir();
		return exist;
	}

	public static File getDirectory(String directoryName) {
		return new File(ROOT_DIRECTORY, directoryName);
	}

	public Boolean removeDirectory(File inFile) {
		if(inFile.exists()) {
			if (inFile.isFile()) {
				inFile.delete();
			} else {
				File files[] = inFile.listFiles();
				if(files != null) {
					for (int i = 0; i < files.length; i++) {
						removeDirectory(files[i]);
					}
				}
				inFile.delete();
			}
		}
		return true;
	}

	public Boolean createDiretory(String directoryName) {
		File notebook = new File(ROOT_DIRECTORY, directoryName);
		return notebook.mkdir();
	}
	
	public Boolean createFile(String notebook, String fileName)
			throws IOException {
		File file = new File(ROOT_DIRECTORY, notebook + "/" + fileName);
		return file.createNewFile();
	}

	public Boolean createFile(String notebook, String fileName, String content)
			throws IOException {
		File file = new File(ROOT_DIRECTORY, notebook + "/" + fileName);
		Boolean create = file.createNewFile();
		if(!create && file.isDirectory())
			return create;
		salveContentInFile(file, content);
		return create;
	}

	public Boolean salveContentInFile(File file, String content) {
		FileOutputStream fileOutputStream;
		byte[] dados = content.getBytes();
		try {
			fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(dados);
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Boolean salveContentInFile(String fileName, String content) {
		File file = new File(ROOT_DIRECTORY, fileName);
		return salveContentInFile(file, content);
	}

	public List<File> getNotebooks() {
		List<File> notebooks = new ArrayList<File>();
		String[] listOfExisting = ROOT_DIRECTORY.list();
		for (int i = 0; i < listOfExisting.length; i++) {
			File directory = new File(ROOT_DIRECTORY, listOfExisting[i]);
			if (directory.isDirectory()) {
				if ((new File(directory, "notebook.zim").exists()))
					notebooks.add(directory);
			}
		}
		return notebooks;
	}

	public List<File> getPages(File notebook) {
		List<File> pages = new ArrayList<File>();
		String[] listOfExisting = notebook.list();
		for (int i = 0; i < listOfExisting.length; i++) {
			File page = new File(notebook, listOfExisting[i]);
			if (page.isFile() && listOfExisting[i].endsWith(".txt"))
				pages.add(page);
		}
		return pages;
	}

}
