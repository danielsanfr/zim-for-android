package com.danielsanfr.zimandroidwiki;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SimpleListDialog extends DialogFragment {

	public static final String ARG_TITLE_DIALOG = "title";
	public static final String ARG_TITLE_BUTTON = "button_ok_name";
	public static final String ARG_LIST = "content_list";
	private ListView listItens;

	public interface SimpleListDialogListener {
		void onFinishEditDialog(String inPaginationDialogFragmentputText);
	}

	public SimpleListDialog() {
		// Empty constructor required for DialogFragment
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		LayoutInflater inflater = LayoutInflater.from(getActivity());
		final View view = inflater.inflate(R.layout.fragment_list_dialog, null);
		listItens = (ListView) view.findViewById(R.id.listItens);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, getArguments()
						.getStringArrayList(ARG_LIST));
		listItens.setAdapter(adapter);
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

}
