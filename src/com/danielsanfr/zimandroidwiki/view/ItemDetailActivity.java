package com.danielsanfr.zimandroidwiki.view;

import com.danielsanfr.zimandroidwiki.R;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * An activity representing a single Item detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link ItemListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link ItemDetailFragment}.
 */
public class ItemDetailActivity extends FragmentActivity implements
		SimpleEditDialog.SimpleEditDialogListener {

	public static final int RESULT_SETTINGS = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(ItemDetailFragment.ARG_CONTENT, getIntent()
					.getStringExtra(ItemDetailFragment.ARG_CONTENT));
			ItemDetailFragment fragment = new ItemDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.item_detail_container, fragment).commit();
		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		getMenuInflater().inflate(R.menu.activity_detail, menu);
		getMenuInflater().inflate(R.menu.formatar, menu);
		getMenuInflater().inflate(R.menu.inserir, menu);
		getMenuInflater().inflate(R.menu.ferramentas, menu);
		menu.findItem(R.id.menu_manage_notebooks).setVisible(false);
		menu.findItem(R.id.menu_settings_notebook).setVisible(false);
		menu.findItem(R.id.menu_quit).setVisible(false);
		menu.findItem(R.id.create_page).setVisible(false);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.rename_page:
			renamePage();
			break;
		case R.id.menu_settings_page:
			showSettingsPage();
			break;
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpTo(this,
					new Intent(this, ItemListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void showSettingsPage() {
		Intent intent = new Intent(this, PageSettings.class);
		Bundle bundle = new Bundle();
		bundle.putString(ItemDetailFragment.ARG_CONTENT, getIntent()
				.getStringExtra(ItemDetailFragment.ARG_CONTENT));
		startActivityForResult(intent, RESULT_SETTINGS);
	}

	private void renamePage() {
		FragmentManager fm = getFragmentManager();
		SimpleEditDialog notebookRename = new SimpleEditDialog();
		Bundle arguments = new Bundle();
		arguments.putString(SimpleEditDialog.ARG_TITLE_DIALOG,
				"Novo nome da página");
		arguments.putString(SimpleEditDialog.ARG_TITLE_BUTTON, "Renomear");
		arguments.putString(SimpleEditDialog.ARG_TEXT, getIntent()
				.getStringExtra(ItemDetailFragment.ARG_CONTENT));
		notebookRename.setArguments(arguments);
		notebookRename.show(fm, "fragment_edit_name");
	}

	@Override
	public void onFinishEditDialog(String inputText) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
	}

}