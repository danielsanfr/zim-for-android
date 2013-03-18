package com.danielsanfr.zimandroidwiki.controller;

import android.app.FragmentManager;
import android.os.Bundle;

import com.danielsanfr.zimandroidwiki.controller.command.EditCommand;
import com.danielsanfr.zimandroidwiki.view.SimpleEditDialog;
import com.danielsanfr.zimandroidwiki.view.SimpleEditDialog.SimpleEditDialogListener;

public class EditDialogController {
	
	private EditCommand command;
	private SimpleEditDialogListener simpleEditDialogListener;
	
	public EditDialogController(EditCommand command, SimpleEditDialogListener simpleEditDialogListener) {
		// TODO Auto-generated constructor stub
		this.command = command;
		this.simpleEditDialogListener = simpleEditDialogListener;
	}

	public void showDialog(FragmentManager fragmentManager, String titleDialog,
			String nameButtonOK) {
		SimpleEditDialog simpleEditDialog = new SimpleEditDialog();
		Bundle arguments = new Bundle();
		arguments.putString(SimpleEditDialog.ARG_TITLE_DIALOG, titleDialog);
		arguments.putString(SimpleEditDialog.ARG_TITLE_BUTTON, nameButtonOK);
		simpleEditDialog.setArguments(arguments);
		simpleEditDialog.show(command, simpleEditDialogListener, fragmentManager,
				"fragment_edit_name");
	}

}
