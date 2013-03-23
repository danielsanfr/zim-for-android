package com.danielsanfr.zimandroidwiki.controller;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentManager;
import android.os.Bundle;

import com.danielsanfr.zimandroidwiki.controller.command.ListCommand;
import com.danielsanfr.zimandroidwiki.view.CheckBoxListDialog;
import com.danielsanfr.zimandroidwiki.view.CheckBoxListDialog.CheckBoxListDialogListener;

public class CheckBoxListDialogController {

	private ListCommand command;
	private CheckBoxListDialogListener simpleListDialogListener;

	public CheckBoxListDialogController(ListCommand command,
			CheckBoxListDialogListener simpleListDialogListener) {
		// TODO Auto-generated constructor stub
		this.command = command;
		this.simpleListDialogListener = simpleListDialogListener;
	}

	public void showDialog(FragmentManager fragmentManager,
			List<String> titles, String titleDialog, String nameButtonOK) {
		CheckBoxListDialog simpleListDialog = new CheckBoxListDialog();
		Bundle arguments = new Bundle();
		arguments.putStringArrayList(CheckBoxListDialog.ARG_LIST,
				(ArrayList<String>) titles);
		arguments.putString(CheckBoxListDialog.ARG_TITLE_DIALOG, titleDialog);
		arguments.putString(CheckBoxListDialog.ARG_TITLE_BUTTON, nameButtonOK);
		simpleListDialog.setArguments(arguments);
		simpleListDialog.show(command, simpleListDialogListener,
				fragmentManager, "fragment_list_name");
	}

}
