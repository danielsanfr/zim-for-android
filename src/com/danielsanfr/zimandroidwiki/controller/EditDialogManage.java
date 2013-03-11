package com.danielsanfr.zimandroidwiki.controller;

import android.app.FragmentManager;
import android.os.Bundle;

import com.danielsanfr.zimandroidwiki.view.SimpleEditDialog;

public abstract class EditDialogManage implements
		SimpleEditDialog.SimpleEditDialogListener {

	protected void showDialog(FragmentManager fragmentManager,
			String titleDialog, String nameButtonOK) {
		SimpleEditDialog simpleEditDialog = new SimpleEditDialog();
		Bundle arguments = new Bundle();
		arguments.putString(SimpleEditDialog.ARG_TITLE_DIALOG, titleDialog);
		arguments.putString(SimpleEditDialog.ARG_TITLE_BUTTON, nameButtonOK);
		simpleEditDialog.setArguments(arguments);
		simpleEditDialog.show(this, fragmentManager, "fragment_edit_name");
	}

}
