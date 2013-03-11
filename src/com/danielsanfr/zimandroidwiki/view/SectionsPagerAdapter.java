package com.danielsanfr.zimandroidwiki.view;

import java.util.ArrayList;
import java.util.List;

import com.danielsanfr.zimandroidwiki.R;
import com.danielsanfr.zimandroidwiki.model.Notebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one
 * of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

	private Boolean mTwoPane;
	private FragmentManager fragmentManager;
	private List<Notebook> notebooks;
	private Context context;

	public interface MenuVisible {

		public void setVisibleMenusPage(Boolean visible);

	};

	public SectionsPagerAdapter(FragmentManager fm, Boolean mTwoPane,
			Context context, List<Notebook> notebooks) {
		super(fm);
		fragmentManager = fm;
		this.mTwoPane = mTwoPane;
		this.context = context;
		this.notebooks = notebooks;
		// setListNotebooksName();
	}

	// private void setListNotebooksName() {
	// notebooksName = new ArrayList<String>();
	// for (Notebook notebook : notebooks) {
	// notebooksName.add(notebook.getName());
	// }
	// }

	private void removeFragmentDetail() {

		((MenuVisible) context).setVisibleMenusPage(false);
		// Cria novo fragmento e transação
		Fragment newFragment = new Fragment();
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		// Substitui quaisquer views do tipo fragment_containter com esse
		// fragmentonotebooks
		// e adiciona uma transação ao back stack
		transaction.replace(R.id.item_detail_container, newFragment);
		transaction.addToBackStack(null);

		// Faz o commit da transação
		transaction.commit();
	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		Fragment fragment = new DummySectionFragment();
		Bundle args = new Bundle();
		args.putStringArrayList(DummySectionFragment.ARG_LIST_ITENS,
				(ArrayList<String>) notebooks.get(position)
						.getListNameOfPages());
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		return notebooks.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (mTwoPane) {
			removeFragmentDetail();
			View lastItemSelected = DummySectionFragment.lastSelected;
			if (lastItemSelected != null)
				resetBackgroundItemList(lastItemSelected);
		}
		return notebooks.get(position).getName();
	}

	@SuppressLint("NewApi")
	private void resetBackgroundItemList(View lastItemSelected) {
		lastItemSelected.setBackground(ItemListActivity.originalBackground);
	}

}
