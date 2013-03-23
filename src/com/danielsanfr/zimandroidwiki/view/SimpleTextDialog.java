package com.danielsanfr.zimandroidwiki.view;

import com.danielsanfr.zimandroidwiki.R;
import com.danielsanfr.zimandroidwiki.controller.command.TextCommand;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class SimpleTextDialog extends DialogFragment {

	public static final String ARG_TITLE_DIALOG = "title";
	public static final String ARG_TEXT = "content";
	private TextView txtContent;
	private TextCommand command;
	private SimpleTextDialogListener simpleTextDialogListener;

	public interface SimpleTextDialogListener {
		void onFinishEditDialog(TextCommand command);
	}

	public SimpleTextDialog() {
		// Empty constructor required for DialogFragment
	}

	public void show(TextCommand command,
			SimpleTextDialogListener simpleTextDialogListener,
			FragmentManager manager, String tag) {
		// TODO Auto-generated method stub
		this.command = command;
		this.simpleTextDialogListener = (SimpleTextDialogListener) simpleTextDialogListener;
		super.show(manager, tag);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		final View view = inflater.inflate(R.layout.fragment_text_dialog, null);
		txtContent = (TextView) view.findViewById(R.id.textContent);
		txtContent.setText(getArguments().getString(ARG_TEXT));
		return new AlertDialog.Builder(getActivity())
				.setTitle(getArguments().getString(ARG_TITLE_DIALOG))
				.setView(view)
				.setCancelable(true)
				.setPositiveButton("Sim",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// validation code
								simpleTextDialogListener
										.onFinishEditDialog(command);
							}
						})
				.setNegativeButton("Não",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						}).create();
	}

}
