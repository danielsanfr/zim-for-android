package com.danielsanfr.zimandroidwiki.view;

import com.danielsanfr.zimandroidwiki.R;
import com.danielsanfr.zimandroidwiki.controller.text.ManageContent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView.BufferType;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemDetailFragment extends Fragment {

	private EditText edtText;
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_CONTENT = "content";

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemDetailFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_item_detail,
				container, false);
		edtText = (EditText) rootView.findViewById(R.id.editText);

		SpannableStringBuilder refinedContent = new SpannableStringBuilder(
				getArguments().getString(ARG_CONTENT));
		ManageContent manageContent = new ManageContent(refinedContent,
				getActivity());
		manageContent.generateRefinedContent();

		edtText.setText(refinedContent, BufferType.SPANNABLE);
//		 edtText.setText(refinedContent);

		return rootView;
	}
}
