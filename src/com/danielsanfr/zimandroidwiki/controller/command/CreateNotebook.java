package com.danielsanfr.zimandroidwiki.controller.command;

import java.io.IOException;

import com.danielsanfr.zimandroidwiki.controller.ManageFiles;

public class CreateNotebook implements EditCommand {

	private static final String ZIM_FILE = "notebook.zim";
	private static final String ZIM_DIRECTORY = "/zim";
	private static final String ZIM_CONF = "zim/state.conf";

	public CreateNotebook() {
		// TODO Auto-generated constructor stub
	}

	private void createNotebook(String notebookName) {
		ManageFiles manageFiles = new ManageFiles();
		manageFiles.createDiretory(notebookName);
		try {
			manageFiles.createFile(notebookName, ZIM_FILE, new String(
					"[Notebook]\nname=" + notebookName
							+ "\nversion=0.4\nendofline=unix\nprofile=None\n"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		manageFiles.createDiretory(notebookName + ZIM_DIRECTORY);
		try {
			manageFiles.createFile(notebookName, ZIM_CONF);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void execute(String title) {
		// TODO Auto-generated method stub
		createNotebook(title);
	}

}
