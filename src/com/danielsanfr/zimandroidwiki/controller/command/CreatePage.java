package com.danielsanfr.zimandroidwiki.controller.command;

import java.io.IOException;
import java.util.Date;

import com.danielsanfr.zimandroidwiki.controller.ManageFiles;
import com.danielsanfr.zimandroidwiki.controller.PageControl;
import com.danielsanfr.zimandroidwiki.model.NotebookModel;
import com.danielsanfr.zimandroidwiki.model.PageModel;

public class CreatePage implements EditCommand {
	
	private NotebookModel notebook;

	public CreatePage(NotebookModel notebook) {
		// TODO Auto-generated constructor stub
		this.notebook = notebook;
	}

	private void createPage(String title) {
		Date today = new Date();
		PageModel pageModel = new PageModel(title, today);
		ManageFiles manageFiles = new ManageFiles();
		try {
			manageFiles.createFile(notebook.getName(), PageControl.getNameFile(title),
					pageModel.getContentToBeSaved());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		notebook.updateNotebook();
	}

	@Override
	public void execute(String title) {
		// TODO Auto-generated method stub
		createPage(title);
	}

}
