package com.danielsanfr.zimandroidwiki.controller.command;

import java.util.List;

import com.danielsanfr.zimandroidwiki.controller.ManageFiles;
import com.danielsanfr.zimandroidwiki.model.NotebookModel;

public class RemoveNotebooks implements ListCommand {

//	private static final String ZIM_FILE = "notebook.zim";
//	private static final String ZIM_DIRECTORY = "/.zim";
//	private static final String ZIM_CONF = "/.zim/state.conf";

	public RemoveNotebooks(List<NotebookModel> notebooks) {
		// TODO Auto-generated constructor stub
	}

	private void removeNotebooks(List<String> selectedItems) {
		ManageFiles manageFiles = new ManageFiles();
		for (String string : selectedItems) 
			manageFiles.removeDirectory(ManageFiles.getDirectory(string));
	}

	@Override
	public void execute(List<String> strings) {
		// TODO Auto-generated method stub
		removeNotebooks(strings);
	}

}
