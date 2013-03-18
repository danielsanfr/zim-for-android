package com.danielsanfr.zimandroidwiki.controller;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentManager;
import android.os.Bundle;

import com.danielsanfr.zimandroidwiki.controller.command.ListCommand;
import com.danielsanfr.zimandroidwiki.view.SimpleListDialog;
import com.danielsanfr.zimandroidwiki.view.SimpleListDialog.SimpleListDialogListener;

public class ListDialogController {

	private ListCommand command;
	private SimpleListDialogListener simpleListDialogListener;

	public ListDialogController(ListCommand command,
			SimpleListDialogListener simpleListDialogListener) {
		// TODO Auto-generated constructor stub
		this.command = command;
		this.simpleListDialogListener = simpleListDialogListener;
	}

	public void showDialog(FragmentManager fragmentManager,
			List<String> titles, String titleDialog, String nameButtonOK) {
		SimpleListDialog simpleListDialog = new SimpleListDialog();
		Bundle arguments = new Bundle();
		arguments.putStringArrayList(SimpleListDialog.ARG_LIST,
				(ArrayList<String>) titles);
		arguments.putString(SimpleListDialog.ARG_TITLE_DIALOG, titleDialog);
		arguments.putString(SimpleListDialog.ARG_TITLE_BUTTON, nameButtonOK);
		simpleListDialog.setArguments(arguments);
		simpleListDialog.show(command, simpleListDialogListener,
				fragmentManager, "fragment_edit_name");
	}

}
