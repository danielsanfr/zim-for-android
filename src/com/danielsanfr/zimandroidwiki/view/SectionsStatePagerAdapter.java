package com.danielsanfr.zimandroidwiki.view;

import java.util.ArrayList;
import java.util.List;

import com.danielsanfr.zimandroidwiki.R;
import com.danielsanfr.zimandroidwiki.model.NotebookModel;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one
 * of the sections/tabs/pages.
 */
public class SectionsStatePagerAdapter extends FragmentStatePagerAdapter {

	private Boolean mTwoPane;
	private FragmentManager fragmentManager;
	private List<NotebookModel> notebooks;
	private List<MenuItem> observers;

	public SectionsStatePagerAdapter(FragmentManager fm, Boolean mTwoPane,
			List<NotebookModel> notebooks) {
		super(fm);
		fragmentManager = fm;
		this.mTwoPane = mTwoPane;
		this.notebooks = notebooks;
		observers = new ArrayList<MenuItem>();
	}

	public void addObserver(MenuItem observer) {
		observers.add(observer);
	}

	public void deleteObserver(MenuItem observer) {
		observers.remove(observer);
	}

	public void notifyObservers(Boolean visible) {
		for (MenuItem observer : observers) {
			observer.setVisible(visible);
		}
	}

	public void setNotebooks(List<NotebookModel> notebooks) {
		this.notebooks = notebooks;
	}

	private void removeFragmentDetail() {

		notifyObservers(false);
		// ((MenuVisible) context).setVisibleMenusPage(false);
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
		args.putInt("POSITION", position);
		args.putStringArrayList(DummySectionFragment.ARG_LIST_ITENS,
				(ArrayList<String>) notebooks.get(position)
						.getListNameOfPages());
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
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
