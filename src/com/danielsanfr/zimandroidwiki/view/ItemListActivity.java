package com.danielsanfr.zimandroidwiki.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.danielsanfr.zimandroidwiki.R;
import com.danielsanfr.zimandroidwiki.controller.EditDialogController;
import com.danielsanfr.zimandroidwiki.controller.ListDialogController;
import com.danielsanfr.zimandroidwiki.controller.ManageFiles;
import com.danielsanfr.zimandroidwiki.controller.command.CreateNotebook;
import com.danielsanfr.zimandroidwiki.controller.command.CreatePage;
import com.danielsanfr.zimandroidwiki.controller.command.EditCommand;
import com.danielsanfr.zimandroidwiki.controller.command.ListCommand;
import com.danielsanfr.zimandroidwiki.controller.command.RemoveNotebooks;
import com.danielsanfr.zimandroidwiki.controller.command.RemovePage;
import com.danielsanfr.zimandroidwiki.model.NotebookModel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

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
public class ItemListActivity extends FragmentActivity implements
		DummySectionFragment.Callbacks,
		SimpleEditDialog.SimpleEditDialogListener,
		SimpleListDialog.SimpleListDialogListener,
		SectionsStatePagerAdapter.MenuVisible {

	private static final int RESULT_SETTINGS = 1;
	private List<NotebookModel> notebooks;
	private List<MenuItem> listMenuNotebook;
	private List<MenuItem> listMenuPage;
	public static Drawable originalBackground;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsStatePagerAdapter mSectionsStatePagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);
		// TODO: If exposing deep links into your app, handle intents here.
		if (!ManageFiles.rootDirectoryExist()) {
			EditDialogController editDialogCreateNotebook = new EditDialogController(
					new CreateNotebook(), this);
			editDialogCreateNotebook.showDialog(getFragmentManager(),
					"Nome do caderno", "Criar");
		}
		notebooks = new ArrayList<NotebookModel>();
		listMenuNotebook = new ArrayList<MenuItem>();
		listMenuPage = new ArrayList<MenuItem>();
		// CmenusListreate the adapter that will return a fragment for each of
		// the three
		// primary sections of the app.

		findNotebooks();

		mViewPager = (ViewPager) findViewById(R.id.pager);
		originalBackground = mViewPager.getBackground();
		mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(
				getSupportFragmentManager(),
				findViewById(R.id.item_detail_container) != null, this,
				notebooks);
		// Set up the ViewPager with the sections adapter.
		mViewPager.setAdapter(mSectionsStatePagerAdapter);
		// mViewPager.setCurrentItem(1);
		Log.i("onCreate", "onCreate complete");
	}

	private void findNotebooks() {
		ManageFiles manageFiles = new ManageFiles();
		List<File> notebooks = manageFiles.getNotebooks();
		for (File notebook : notebooks) {
			this.notebooks.add(new NotebookModel(notebook));
		}
		Collections.sort(this.notebooks);
	}

	private void updateNotebooks() {
		Boolean exist;
		List<NotebookModel> notebooksAux = new ArrayList<NotebookModel>();
		ManageFiles manageFiles = new ManageFiles();
		List<File> notebooksInRootDirectory = manageFiles.getNotebooks();
		if (notebooks.size() < notebooksInRootDirectory.size()) {
			for (File directory : notebooksInRootDirectory) {
				exist = false;
				for (NotebookModel notebookModel : notebooks) {
					if (directory.getName().compareTo(notebookModel.getName()) == 0) {
						exist = true;
						break;
					}
				}
				if (!exist)
					notebooksAux.add(new NotebookModel(directory));
			}
			notebooks.addAll(notebooksAux);
			Collections.sort(notebooks);
		} else if (notebooks.size() > notebooksInRootDirectory.size()) {
			for (NotebookModel notebookModel : notebooks) {
				exist = false;
				for (File notebook : notebooksInRootDirectory) {
					if (notebookModel.getName().compareTo(notebook.getName()) == 0) {
						exist = true;
						break;
					}
				}
				if (!exist)
					notebooksAux.add(notebookModel);
			}
			notebooks.removeAll(notebooksAux);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		getMenuInflater().inflate(R.menu.activity_main, menu);
		getMenuInflater().inflate(R.menu.activity_detail, menu);
		getMenuInflater().inflate(R.menu.discard, menu);
		getMenuInflater().inflate(R.menu.inserir, menu);
		getMenuInflater().inflate(R.menu.ferramentas, menu);
		listMenuNotebook.add(menu.findItem(R.id.menu_manage_notebooks));
		listMenuNotebook.add(menu.findItem(R.id.menu_settings_notebook));
		listMenuNotebook.add(menu.findItem(R.id.create_page));
		listMenuPage.add(menu.findItem(R.id.menu_settings_page));
		listMenuPage.add(menu.findItem(R.id.menu_manage_page));
		listMenuPage.add(menu.findItem(R.id.menu_tools));
		listMenuPage.add(menu.findItem(R.id.menu_insert));
		listMenuPage.add(menu.findItem(R.id.menu_discard));
		setVisibleMenusPage(false);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void setVisibleMenusPage(Boolean visible) {
		for (MenuItem menuItem : listMenuNotebook) {
			menuItem.setVisible(!visible);
		}

		for (MenuItem menuItem : listMenuPage) {
			menuItem.setVisible(visible);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		// int startSelection = edtText.getSelectionStart();
		// int endSelection = edtText.getSelectionEnd();
		// String textoSelecionado =
		// edtText.getText().toString().substring(startSelection, endSelection);
		//
		// SpannableString spanString = new SpannableString(textoSelecionado);
		// spanString.setSpan(new StyleSpan(Typeface.BOLD), 0,
		// spanString.length(), 0); // Negrito
		// spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0,
		// spanString.length(), 0); // Italico
		// spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
		// //Sublinhado
		// edtText.setText(spanString);
		//
		// Toast.makeText(this, textoSelecionado, Toast.LENGTH_SHORT).show ();
		getMenuInflater().inflate(R.menu.menu_context_teste, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated
		switch (item.getItemId()) {
		case R.id.menu_settings_notebook:
			Intent intent = new Intent(this, GeneralSettings.class);
			startActivityForResult(intent, RESULT_SETTINGS);
			// startActivity(intent);
			break;
		case R.id.create_page:
			EditDialogController editDialogCreatePage = new EditDialogController(
					new CreatePage(notebooks.get(mViewPager.getCurrentItem())),
					this);
			editDialogCreatePage.showDialog(getFragmentManager(),
					"Nome da página", "Criar");
			break;
		case R.id.rename_page:
			break;
		case R.id.remove_page:
			ListDialogController listDialogRemovePage = new ListDialogController(
					new RemovePage(notebooks.get(mViewPager.getCurrentItem())),
					this);
			listDialogRemovePage.showDialog(getFragmentManager(), notebooks
					.get(mViewPager.getCurrentItem()).getListNameOfPages(),
					"Excluir páginas", "Excluir");
			break;
		case R.id.menu_settings_page:
			showSettingsPage();
			break;
		case R.id.create_notebook:
			EditDialogController editDialogCreateNotebook = new EditDialogController(
					new CreateNotebook(), this);
			editDialogCreateNotebook.showDialog(getFragmentManager(),
					"Nome do caderno", "Criar");
			break;
		case R.id.rename_notebook:
			break;
		case R.id.remove_notebook:
			List<String> notebookNames = new ArrayList<String>();
			for (NotebookModel notebookModel : notebooks) {
				notebookNames.add(notebookModel.getName());
			}
			ListDialogController listDialogRemoveNotebooks = new ListDialogController(
					new RemoveNotebooks(notebooks), this);
			listDialogRemoveNotebooks.showDialog(getFragmentManager(),
					notebookNames, "Excluir cadernos", "Excluir");
			break;
		case R.id.menu_quit:
			finish();
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

	@Override
	public void onFinishEditDialog(EditCommand command, String inputText) {
		Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
		command.execute(inputText);
		updateNotebooks();
		mSectionsStatePagerAdapter.setNotebooks(notebooks);
		mSectionsStatePagerAdapter.notifyDataSetChanged();
	}

	@Override
	public void onFinishListDialog(ListCommand command,
			List<String> selectedItems) {
		// TODO Auto-generated method stub
		command.execute(selectedItems);
		updateNotebooks();
		mSectionsStatePagerAdapter.setNotebooks(notebooks);
		mSectionsStatePagerAdapter.notifyDataSetChanged();
	}

	@SuppressLint("NewApi")
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if (DummySectionFragment.lastSelected != null)
			DummySectionFragment.lastSelected.setBackground(originalBackground);
		DummySectionFragment.lastSelected = arg1;
		arg1.setBackgroundColor(Color.rgb(255, 138, 01));

		if (findViewById(R.id.item_detail_container) != null) {
			// In two-pane mode, show the detail view in this activity
			// by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			setVisibleMenusPage(true);
			Bundle arguments = new Bundle();
			if (notebooks.get(mViewPager.getCurrentItem()).getPages().size() > arg2)
				arguments.putString(ItemDetailFragment.ARG_CONTENT, notebooks
						.get(mViewPager.getCurrentItem()).getPages().get(arg2)
						.getContent());
			else
				arguments.putString(ItemDetailFragment.ARG_CONTENT, "===== "
						+ ((TextView) arg1).getText().toString() + " ====="
						+ "\nCriado em...\n\n");
			ItemDetailFragment fragment = new ItemDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.item_detail_container, fragment).commit();
		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, ItemDetailActivity.class);
			detailIntent.putExtra(ItemDetailFragment.ARG_CONTENT, notebooks
					.get(mViewPager.getCurrentItem()).getPages().get(arg2)
					.getContent());
			startActivity(detailIntent);
		}
	}

}
