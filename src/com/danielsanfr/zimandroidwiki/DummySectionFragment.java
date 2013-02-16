package com.danielsanfr.zimandroidwiki;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
@SuppressLint({ "NewApi", "ValidFragment" })
public class DummySectionFragment extends Fragment {

	private static ListView listView;
	private static File sdcard;
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	private static View lastSelected;

	public DummySectionFragment() {
	}
	
	public static View getLastItemSelected() {
		return lastSelected;
	}

	private final List<String> alimentarLista() {
		if(sdcard == null)
			sdcard = new File(Environment.getExternalStorageDirectory().toString() + "/ZimAndroidWiki/Testes");
		String[] listaArqExistentes = sdcard.list();
		List<String> listaArqProcurados = new ArrayList<String>();

		final int MAX_ARQUIVOS = listaArqExistentes.length;

		for (int i = 0; i < MAX_ARQUIVOS; i++) {
					listaArqProcurados.add(listaArqExistentes[i]);
		}

		if (listaArqProcurados.size() == 0) {
			listaArqProcurados.add("Nenhum arquivo Encontrado!");
		}
		return listaArqProcurados;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onViewCreated(container, savedInstanceState);
		// Create a new TextView and set its text to the fragment's section
		// number argument value.
		listView = new ListView(getActivity());
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, alimentarLista());
		// Associar o adapter ao listview
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				if(lastSelected != null)
					lastSelected.setBackgroundColor(Color.rgb(231, 231, 231));
				lastSelected = arg1;
				arg1.setBackgroundColor(Color.rgb(255, 138, 01));
				BufferedReader bufferedReader;
				Toast.makeText(
						getActivity(),
						"Você Clicou no texto: " + arg2 + " - "
								+ ((TextView) arg1).getText().toString(),
						Toast.LENGTH_LONG).show();
				StringBuffer conteudo = new StringBuffer();
				try
		        {
		            File arquivo = new File(Environment.getExternalStorageDirectory().toString() + "/ZimAndroidWiki/Testes/" + ((TextView) arg1).getText().toString());
		            bufferedReader = new BufferedReader(new FileReader(arquivo));
		            String linha;
		            while ((linha = bufferedReader.readLine()) != null) {
		                conteudo.append(linha + "\n");
		            }
		            if(getActivity().findViewById(R.id.item_detail_container) != null) {
		    			// In two-pane mode, show the detail view in this activity by
		    			// adding or replacing the detail fragment using a
		    			// fragment transaction.
		    			Bundle arguments = new Bundle();
		    			arguments.putString(ItemDetailFragment.ARG_CONTENT, conteudo.toString());
		    			ItemDetailFragment fragment = new ItemDetailFragment();
		    			fragment.setArguments(arguments);
		    			getActivity().getSupportFragmentManager().beginTransaction()
		    					.replace(R.id.item_detail_container, fragment).commit();

		    		} else {
		    			// In single-pane mode, simply start the detail activity
		    			// for the selected item ID.
		    			Intent detailIntent = new Intent(getActivity(), ItemDetailActivity.class);
		    			detailIntent.putExtra(ItemDetailFragment.ARG_CONTENT, conteudo.toString());
		    			startActivity(detailIntent);
		    		}
		        } catch (Exception e) {
		        	Toast.makeText (getActivity(), "Erro : " + e.getMessage(), Toast.LENGTH_SHORT).show ();
		        }
			}
		});
		return listView;
	}
}
