package com.danielsanfr.zimandroidwiki;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;

/**
 * An activity representing a list of Items. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link ItemDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details (if present) is a
 * {@link ItemDetailFragment}.
 * <p>
 * This activity also implements the required {@link ItemListFragment.Callbacks}
 * interface to listen for item selections.
 */
public class ItemListActivity extends FragmentActivity{
	
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);
		// TODO: If exposing deep links into your app, handle intents here.
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager(), findViewById(R.id.item_detail_container) != null);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		getMenuInflater().inflate(R.menu.formatar, menu);
		getMenuInflater().inflate(R.menu.inserir, menu);

		return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
//		int startSelection = edtText.getSelectionStart();
//		int endSelection = edtText.getSelectionEnd();
//		String textoSelecionado = edtText.getText().toString().substring(startSelection, endSelection);
//		
//		SpannableString spanString = new SpannableString(textoSelecionado);
//		spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0); // Negrito
//		spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0); // Italico
//		spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0); //Sublinhado
//		edtText.setText(spanString);
//		
//		Toast.makeText(this, textoSelecionado, Toast.LENGTH_SHORT).show ();
	    getMenuInflater().inflate(R.menu.menu_context_teste, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_quit:
			finish();
			return true;
		}
		return false;
	}
}
