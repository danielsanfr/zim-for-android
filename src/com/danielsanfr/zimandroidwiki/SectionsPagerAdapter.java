package com.danielsanfr.zimandroidwiki;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
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

	private File sdcard;
	private Boolean mTwoPane;
	private FragmentManager fragmentManager;
	public List<String> notebooksList;
	private Drawable originalBackground;
	private Context context;
	
	public interface MenuVisible {
		
		public void setVisibleMenusPage(Boolean visible);
		
	};

	public SectionsPagerAdapter(FragmentManager fm, Boolean mTwoPane,
			Context context, Drawable originalBackground) {
		super(fm);
		fragmentManager = fm;
		this.mTwoPane = mTwoPane;
		this.context = context;
		notebooksList = new ArrayList<String>();
		sdcard = new File(Environment.getExternalStorageDirectory().toString()
				+ "/ZimAndroidWiki");
		String[] listaExistentes = sdcard.list();
		for (int i = 0; i < listaExistentes.length; i++) {
			File diretorio = new File(sdcard, listaExistentes[i]);
			if (diretorio.isDirectory())
				notebooksList.add(listaExistentes[i]);
		}
		Collections.sort(notebooksList);
		this.originalBackground = originalBackground;
	}

	private void removeFragmentDetail() {
		
		((MenuVisible) context).setVisibleMenusPage(false);
		// Cria novo fragmento e transação
		Fragment newFragment = new Fragment();
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		// Substitui quaisquer views do tipo fragment_containter com esse
		// fragmento
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
		args.putString("notebook", notebooksList.get(position));
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getCount() {
		return notebooksList.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		if (mTwoPane) {
			removeFragmentDetail();
			View lastItemSelected = DummySectionFragment.lastSelected;
			if (lastItemSelected != null)
				resetBackgroundItemList(lastItemSelected);
		}
		return notebooksList.get(position);
	}

	@SuppressLint("NewApi")
	private void resetBackgroundItemList(View lastItemSelected) {
		lastItemSelected.setBackground(originalBackground);
	}

}
