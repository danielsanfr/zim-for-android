package com.danielsanfr.zimandroidwiki;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

	private FragmentManager fragmentManager;
	private Boolean mTwoPane;

	public SectionsPagerAdapter(FragmentManager fm, Boolean mTwoPane) {
		super(fm);
		fragmentManager = fm;
		this.mTwoPane = mTwoPane;
	}
	
	private void removeFragmentDetail() {
	    // Cria novo fragmento e transação
	    Fragment newFragment = new Fragment();
	    FragmentTransaction transaction = fragmentManager.beginTransaction();

	    // Substitui quaisquer views do tipo fragment_containter com esse fragmento
	    // e adiciona uma transação ao back stack
	    transaction.replace(R.id.item_detail_container, newFragment);
	    transaction.addToBackStack(null);

	    // Faz o commit da transação
	    transaction.commit();
	}
	
	private void resetBackgroundItemList(View lastItemSelected) {
		lastItemSelected.setBackgroundColor(Color.rgb(231, 231, 231));
	}

	@Override
	public Fragment getItem(int position) {		
		// getItem is called to instantiate the fragment for the given page.
		// Return a DummySectionFragment (defined as a static inner class
		// below) with the page number as its lone argument.
		Fragment fragment = new DummySectionFragment();
		Bundle args = new Bundle();
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		// Show 3 total pages.
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if(mTwoPane) {
			removeFragmentDetail();
			View lastItemSelected = DummySectionFragment.getLastItemSelected();
			if(lastItemSelected != null)
				resetBackgroundItemList(lastItemSelected);
		}
		switch (position) {
		case 0:
			return "Section 1";
		case 1:
			return "Section 2";
		case 2:
			return "Section 3";
		}
		return "Section NULL";
	}
}
