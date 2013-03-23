package com.danielsanfr.zimandroidwiki.controller.command;

import com.danielsanfr.zimandroidwiki.controller.ManageFiles;
import com.danielsanfr.zimandroidwiki.controller.PageControl;
import com.danielsanfr.zimandroidwiki.model.NotebookModel;

public class RemovePage implements TextCommand {

	private NotebookModel notebook;
	private int pageSelectedID;

	public RemovePage(NotebookModel notebook, int pageSelectedID) {
		// TODO Auto-generated constructor stub
		this.notebook = notebook;
		this.pageSelectedID = pageSelectedID;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		ManageFiles manageFiles = new ManageFiles();
		manageFiles.removeDirectory(ManageFiles.getDirectory(notebook.getName()
				+ "/"
				+ PageControl.getNameFile(notebook.getListNameOfPages().get(
						pageSelectedID))));
		notebook.updateNotebook();
	}

}
