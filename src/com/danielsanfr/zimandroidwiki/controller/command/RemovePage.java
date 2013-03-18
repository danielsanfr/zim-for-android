package com.danielsanfr.zimandroidwiki.controller.command;

import java.util.List;

import com.danielsanfr.zimandroidwiki.controller.ManageFiles;
import com.danielsanfr.zimandroidwiki.controller.PageControl;
import com.danielsanfr.zimandroidwiki.model.NotebookModel;

public class RemovePage implements ListCommand {

	// private static final String ZIM_FILE = "notebook.zim";
	// private static final String ZIM_DIRECTORY = "/.zim";
	// private static final String ZIM_CONF = "/.zim/state.conf";
	private NotebookModel notebook;

	public RemovePage(NotebookModel notebook) {
		// TODO Auto-generated constructor stub
		this.notebook = notebook;
	}

	private void removePages(List<String> selectedItems) {
		ManageFiles manageFiles = new ManageFiles();
		for (String string : selectedItems) {
			manageFiles.removeDirectory(ManageFiles.getDirectory(notebook
					.getName() + "/" + PageControl.getNameFile(string)));
		}
		notebook.updateNotebook();
	}

	@Override
	public void execute(List<String> strings) {
		// TODO Auto-generated method stub
		removePages(strings);
	}

}
