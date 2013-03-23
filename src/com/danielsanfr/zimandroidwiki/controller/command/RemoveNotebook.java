package com.danielsanfr.zimandroidwiki.controller.command;

import com.danielsanfr.zimandroidwiki.controller.ManageFiles;

public class RemoveNotebook implements TextCommand {

	private String notebook;

	public RemoveNotebook(String notebook) {
		// TODO Auto-generated constructor stub
		this.notebook = notebook;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		ManageFiles manageFiles = new ManageFiles();
		manageFiles.removeDirectory(ManageFiles.getDirectory(notebook));
	}

}
