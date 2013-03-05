package com.danielsanfr.zimandroidwiki;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
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
		SimpleEditDialog.SimpleListDialogListener, SectionsPagerAdapter.MenuVisible {

	private static final int RESULT_SETTINGS = 1;
	private List<MenuItem> listMenuNotebook;
	private List<MenuItem> listMenuPage;
	private String content;

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
	private Drawable originalBackground;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_list);
		// TODO: If exposing deep links into your app, handle intents here.

		listMenuNotebook = new ArrayList<MenuItem>();
		listMenuPage = new ArrayList<MenuItem>();
		// CmenusListreate the adapter that will return a fragment for each of the three
		// primary sections of the app.

		mViewPager = (ViewPager) findViewById(R.id.pager);
		originalBackground = mViewPager.getBackground();
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager(),
				findViewById(R.id.item_detail_container) != null, this,
				originalBackground);
		// Set up the ViewPager with the sections adapter.
		mViewPager.setAdapter(mSectionsPagerAdapter);
		// mViewPager.setCurrentItem(1);
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
		listMenuNotebook.add(menu.findItem(R.id.menu_manage_notebooks));
		listMenuNotebook.add(menu.findItem(R.id.menu_settings_notebook));
		listMenuNotebook.add(menu.findItem(R.id.create_page));
		listMenuPage.add(menu.findItem(R.id.menu_settings_page));
		listMenuPage.add(menu.findItem(R.id.menu_manage_page));
		listMenuPage.add(menu.findItem(R.id.menu_format));
		listMenuPage.add(menu.findItem(R.id.menu_tools));
		listMenuPage.add(menu.findItem(R.id.menu_insert));
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
			createPage();
			break;
		case R.id.rename_page:
			renamePage();
			break;
		case R.id.menu_settings_page:
			showSettingsPage();
			break;
		case R.id.create_notebook:
			createNotebook();
			break;
		case R.id.rename_notebook:
			renameNotebook();
			break;
		case R.id.remove_notebook:
			removeNotebook();
			break;
		case R.id.menu_quit:
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void createPage() {
		FragmentManager fm = getFragmentManager();
		SimpleEditDialog notebookCreate = new SimpleEditDialog();
		Bundle arguments = new Bundle();
		arguments.putString(SimpleEditDialog.ARG_TITLE_DIALOG,
				"Nome da página");
		arguments.putString(SimpleEditDialog.ARG_TITLE_BUTTON, "Criar");
		notebookCreate.setArguments(arguments);
		notebookCreate.show(fm, "fragment_edit_name");
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
		arguments.putString(SimpleEditDialog.ARG_TEXT, content);
		notebookRename.setArguments(arguments);
		notebookRename.show(fm, "fragment_edit_name");
	}

	private void createNotebook() {
		FragmentManager fm = getFragmentManager();
		SimpleEditDialog notebookCreate = new SimpleEditDialog();
		Bundle arguments = new Bundle();
		arguments.putString(SimpleEditDialog.ARG_TITLE_DIALOG,
				"Nome do caderno");
		arguments.putString(SimpleEditDialog.ARG_TITLE_BUTTON, "Criar");
		notebookCreate.setArguments(arguments);
		notebookCreate.show(fm, "fragment_edit_name");
	}

	private void renameNotebook() {
		FragmentManager fm = getFragmentManager();
		SimpleEditDialog notebookRename = new SimpleEditDialog();
		Bundle arguments = new Bundle();
		arguments.putString(SimpleEditDialog.ARG_TITLE_DIALOG,
				"Novo nome do caderno");
		arguments.putString(SimpleEditDialog.ARG_TITLE_BUTTON, "Renomear");
		arguments.putString(SimpleEditDialog.ARG_TEXT,
				mSectionsPagerAdapter.notebooksList.get(mViewPager
						.getCurrentItem()));
		notebookRename.setArguments(arguments);
		notebookRename.show(fm, "fragment_edit_name");
	}

	private void removeNotebook() {
		FragmentManager fm = getFragmentManager();
		SimpleListDialog notebookRemove = new SimpleListDialog();
		Bundle arguments = new Bundle();
		arguments.putStringArrayList(SimpleListDialog.ARG_LIST,
				(ArrayList<String>) mSectionsPagerAdapter.notebooksList);
		arguments.putString(SimpleListDialog.ARG_TITLE_DIALOG,
				"Excluir cadernos");
		arguments.putString(SimpleListDialog.ARG_TITLE_BUTTON, "Excluir");
		notebookRemove.setArguments(arguments);
		notebookRemove.show(fm, "fragment_edit_name");
	}

	@Override
	public void onFinishEditDialog(String inputText) {
		Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
	}

	@SuppressLint("NewApi")
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		if (DummySectionFragment.lastSelected != null)
			DummySectionFragment.lastSelected.setBackground(originalBackground);
		DummySectionFragment.lastSelected = arg1;
		arg1.setBackgroundColor(Color.rgb(255, 138, 01));
		BufferedReader bufferedReader;
		// Toast.makeText(
		// this,
		// "Você Clicou no texto: " + arg2 + " - "
		// + ((TextView) arg1).getText().toString(),
		// Toast.LENGTH_LONG).show();
		StringBuffer content = new StringBuffer();
		File arquivo = new File(Environment.getExternalStorageDirectory()
				.toString()
				+ "/ZimAndroidWiki/"
				+ mSectionsPagerAdapter.notebooksList.get(mViewPager
						.getCurrentItem())
				+ "/"
				+ ((TextView) arg1).getText().toString());
		try {
			bufferedReader = new BufferedReader(new FileReader(arquivo));
			String linha;
			while ((linha = bufferedReader.readLine()) != null) {
				content.append(linha + "\n");
			}
			this.content = content.toString();
			if (findViewById(R.id.item_detail_container) != null) {
				// In two-pane mode, show the detail view in this activity
				// by
				// adding or replacing the detail fragment using a
				// fragment transaction.
				setVisibleMenusPage(true);
				Bundle arguments = new Bundle();
				arguments.putString(ItemDetailFragment.ARG_CONTENT,
						content.toString());
				ItemDetailFragment fragment = new ItemDetailFragment();
				fragment.setArguments(arguments);
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.item_detail_container, fragment).commit();
			} else {
				// In single-pane mode, simply start the detail activity
				// for the selected item ID.
				Intent detailIntent = new Intent(this, ItemDetailActivity.class);
				detailIntent.putExtra(ItemDetailFragment.ARG_CONTENT,
						content.toString());
				startActivity(detailIntent);
			}
		} catch (Exception e) {
			// Toast.makeText(this, "Erro : " + e.getMessage(),
			// Toast.LENGTH_SHORT)
			// .show();
			byte[] dados;
			// transforma o texto digitado em array de bytes
			dados = String.valueOf("Daniel San").getBytes();
			FileOutputStream fileOutputStream;
			try {
				fileOutputStream = new FileOutputStream(arquivo);
				// escreve os dados e fecha o arquivo
				fileOutputStream.write(dados);
				fileOutputStream.flush();
				fileOutputStream.close();
				onItemClick(arg0, arg1, arg2, arg3);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
