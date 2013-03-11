package com.danielsanfr.zimandroidwiki.controller;

import java.util.List;

import android.app.FragmentManager;

public class RemovePage extends ListDialogManage {

//	private static final String ZIM_FILE = "notebook.zim";
//	private static final String ZIM_DIRECTORY = "/.zim";
//	private static final String ZIM_CONF = "/.zim/state.conf";

	public RemovePage(FragmentManager fragmentManager, List<String> PageNames) {
		// TODO Auto-generated constructor stub
		showDialog(fragmentManager, PageNames, "Remover páginas", "Remover");
	}

	private void removePages(List<String> selectedItems) {
	}

	@Override
	public void onFinishListDialog(List<String> selectedItems) {
		// TODO Auto-generated method stub
		removePages(selectedItems);
	}

}
