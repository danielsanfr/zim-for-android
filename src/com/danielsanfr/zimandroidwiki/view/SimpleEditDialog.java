package com.danielsanfr.zimandroidwiki.view;

import com.danielsanfr.zimandroidwiki.R;
import com.danielsanfr.zimandroidwiki.controller.command.EditCommand;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class SimpleEditDialog extends DialogFragment implements
		OnEditorActionListener {

	public static final String ARG_TITLE_DIALOG = "title";
	public static final String ARG_TITLE_BUTTON = "button_ok_name";
	public static final String ARG_TEXT = "content";
	private EditText edtName;
	private EditCommand command;
	private SimpleEditDialogListener simpleEditDialogListener;

	public interface SimpleEditDialogListener {
		void onFinishEditDialog(EditCommand command,
				String inPaginationDialogFragmentputText);
	}

	public SimpleEditDialog() {
		// Empty constructor required for DialogFragment
	}

	public void show(EditCommand command,
			SimpleEditDialogListener simpleEditDialogListener,
			FragmentManager manager, String tag) {
		// TODO Auto-generated method stub
		this.command = command;
		this.simpleEditDialogListener = (SimpleEditDialogListener) simpleEditDialogListener;
		super.show(manager, tag);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		final View view = inflater.inflate(R.layout.fragment_edit_dialog, null);
		edtName = (EditText) view.findViewById(R.id.editName);
		edtName.setText(getArguments().getString(ARG_TEXT));
		edtName.requestFocus();
		edtName.setOnEditorActionListener(this);
		return new AlertDialog.Builder(getActivity())
				.setTitle(getArguments().getString(ARG_TITLE_DIALOG))
				.setView(view)
				.setCancelable(true)
				.setPositiveButton(getArguments().getString(ARG_TITLE_BUTTON),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// validation code
								onEditorAction(edtName,
										EditorInfo.IME_ACTION_DONE, null);
							}
						})
				.setNegativeButton("Cancelar",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						}).create();
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (EditorInfo.IME_ACTION_DONE == actionId) {
			// Return input text to activity
			// SimpleListDialogListener activity = (SimpleListDialogListener)
			// getActivity();
			// activity.onFinishEditDialog(edtName.getText().toString());
			this.simpleEditDialogListener.onFinishEditDialog(command, edtName
					.getText().toString());
			this.dismiss();
			return true;
		}
		return false;
	}

}
