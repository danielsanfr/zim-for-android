package com.danielsanfr.zimandroidwiki;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

/**getLastItemSelected()
 * A dummy fragment representing a section of the app, but that simply displays
 * dummy text.
 */
@SuppressLint({ "NewApi", "ValidFragment", "ShowToast" })
public class DummySectionFragment extends Fragment {

	private static ListView listView;
	private File sdcard;
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";

	public static View lastSelected;
	
	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;
	
	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3);
	}
	
	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
		}
	};
	
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public DummySectionFragment() {
		// TODO Auto-generated constructor stub
	}

	private final List<String> alimentarLista() {
		if (sdcard == null)
			sdcard = new File(Environment.getExternalStorageDirectory()
					.toString() + "/ZimAndroidWiki/" + getArguments().getString("notebook"));
		List<String> listaDiretorios = new ArrayList<String>();
		String [] listaExistentes = sdcard.list();
		for (int i = 0; i < listaExistentes.length; i++) {
			File file = new File(sdcard, listaExistentes[i]);
			if(file.isFile() && listaExistentes[i].endsWith(".txt"))
				listaDiretorios.add(listaExistentes[i]);
		}
		if (listaDiretorios.size() == 0) {
			listaDiretorios.add("Nenhum arquivo Encontrado!.txt");
		}
		return listaDiretorios;

	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
//		super.onViewCreated(container, savedInstanceState);
		// Create a new TextView and set its text to the fragment's section
		// number argument value.
		listView = new ListView(getActivity());

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, alimentarLista());
		// Associar o adapter ao listview
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(listenItemList);
		return listView;
	}

	private OnItemClickListener listenItemList = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			mCallbacks.onItemClick(arg0, arg1, arg2, arg3);
		}
	};

}
