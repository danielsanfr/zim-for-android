package com.danielsanfr.zimandroidwiki.controller;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentManager;
import android.os.Bundle;

import com.danielsanfr.zimandroidwiki.model.Notebook;
import com.danielsanfr.zimandroidwiki.view.SimpleListDialog;

public abstract class ListDialogManage implements
		SimpleListDialog.SimpleListDialogListener {

	protected void showDialog(FragmentManager fragmentManager, List<String> notebookNames,
			String titleDialog, String nameButtonOK) {
		SimpleListDialog simpleListDialog = new SimpleListDialog();
		Bundle arguments = new Bundle();
		arguments.putStringArrayList(SimpleListDialog.ARG_LIST,
				(ArrayList<String>) notebookNames);
		arguments.putString(SimpleListDialog.ARG_TITLE_DIALOG,
				titleDialog);
		arguments.putString(SimpleListDialog.ARG_TITLE_BUTTON, nameButtonOK);
		simpleListDialog.setArguments(arguments);
		simpleListDialog.show(this, fragmentManager, "fragment_edit_name");
	}

}
