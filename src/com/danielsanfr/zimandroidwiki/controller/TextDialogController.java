package com.danielsanfr.zimandroidwiki.controller;

import android.app.FragmentManager;
import android.os.Bundle;

import com.danielsanfr.zimandroidwiki.controller.command.TextCommand;
import com.danielsanfr.zimandroidwiki.view.SimpleTextDialog;
import com.danielsanfr.zimandroidwiki.view.SimpleTextDialog.SimpleTextDialogListener;

public class TextDialogController {

	private TextCommand command;
	private SimpleTextDialogListener simpleTextDialogListener;

	public TextDialogController(TextCommand command,
			SimpleTextDialogListener simpleTextDialogListener) {
		// TODO Auto-generated constructor stub
		this.command = command;
		this.simpleTextDialogListener = simpleTextDialogListener;
	}

	public void showDialog(FragmentManager fragmentManager, String titleDialog) {
		SimpleTextDialog simpleTextDialog = new SimpleTextDialog();
		Bundle arguments = new Bundle();
		arguments.putString(SimpleTextDialog.ARG_TITLE_DIALOG, titleDialog);
		arguments.putString(SimpleTextDialog.ARG_TEXT,
				"Deseja mesmo fazer isso?\nSe escolher 'Sim' não poderá mais recuperar este item!");
		simpleTextDialog.setArguments(arguments);
		simpleTextDialog.show(command, simpleTextDialogListener,
				fragmentManager, "fragment_text_name");
	}

}
