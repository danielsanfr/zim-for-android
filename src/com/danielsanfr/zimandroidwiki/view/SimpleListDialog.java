package com.danielsanfr.zimandroidwiki.view;

import java.util.ArrayList;
import java.util.List;

import com.danielsanfr.zimandroidwiki.R;
import com.danielsanfr.zimandroidwiki.controller.command.ListCommand;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class SimpleListDialog extends DialogFragment {

	public static final String ARG_TITLE_DIALOG = "title";
	public static final String ARG_TITLE_BUTTON = "button_ok_name";
	public static final String ARG_LIST = "content_list";
	private ListView listItens;
	private ListCommand command;
	private SimpleListDialogListener simpleListDialogListener;

	public interface SimpleListDialogListener {
		void onFinishListDialog(ListCommand command, List<String> selectedItems);
	}

	public SimpleListDialog() {
		// Empty constructor required for DialogFragment
	}

	public void show(ListCommand command,
			SimpleListDialogListener simpleListDialogListener,
			FragmentManager manager, String tag) {
		// TODO Auto-generated method stub
		this.command = command;
		this.simpleListDialogListener = simpleListDialogListener;
		super.show(manager, tag);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		final List<String> itemList = getArguments().getStringArrayList(
				ARG_LIST);
		final List<String> selectedItems = new ArrayList<String>();
		LayoutInflater inflater = LayoutInflater.from(getActivity());
		final View view = inflater.inflate(R.layout.fragment_list_dialog, null);
		listItens = (ListView) view.findViewById(R.id.listItens);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				String item = itemList.get(position);
				View view = convertView;
				if (view == null) {
					LayoutInflater inflater = getActivity().getLayoutInflater();
					view = (View) inflater.inflate(R.layout.item_list_dialog,
							null);
				}
				CheckBox chk = (CheckBox) view.findViewById(R.id.chkItens);
				chk.setTag(item);
				chk.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						CheckBox chk = (CheckBox) v;
						String item = (String) chk.getTag();
						if (chk.isChecked()) {
							if (!selectedItems.contains(item))
								selectedItems.add(item);
						} else {
							if (selectedItems.contains(item))
								selectedItems.remove(item);
						}
					}

				});
				if (selectedItems.contains(item)) {
					chk.setChecked(true);
				} else {
					chk.setChecked(false);
				}
				TextView txv = (TextView) view.findViewById(R.id.txvItens);
				txv.setTextAppearance(getContext(),
						android.R.style.TextAppearance_Medium);
				txv.setText(item);
				return view;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				// return super.getItemId(position);
				return position;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return itemList.size();
			}
		};
		// ArrayAdapter<String> adapter = new
		// ArrayAdapter<String>(getActivity(),
		// android.R.layout.simple_list_item_1, getArguments()
		// .getStringArrayList(ARG_LIST));
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
								simpleListDialogListener.onFinishListDialog(
										command, selectedItems);
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
