package com.danielsanfr.zimandroidwiki.controller;

import java.util.List;

import android.app.FragmentManager;

public class RemoveNotebooks extends ListDialogManage {

//	private static final String ZIM_FILE = "notebook.zim";
//	private static final String ZIM_DIRECTORY = "/.zim";
//	private static final String ZIM_CONF = "/.zim/state.conf";

	public RemoveNotebooks(FragmentManager fragmentManager, List<String> notebookNames) {
		// TODO Auto-generated constructor stub
		showDialog(fragmentManager, notebookNames, "Remover cadernos", "Remover");
	}

	private void removeNotebooks(List<String> selectedItems) {
		ManageFiles manageFiles = new ManageFiles();
		for (String string : selectedItems) {
			manageFiles.removeDirectory(ManageFiles.getDirectory(string));
		}
	}

	@Override
	public void onFinishListDialog(List<String> selectedItems) {
		// TODO Auto-generated method stub
		removeNotebooks(selectedItems);
	}

}
