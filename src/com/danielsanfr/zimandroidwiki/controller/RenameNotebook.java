package com.danielsanfr.zimandroidwiki.controller;

import android.app.FragmentManager;
import android.util.Log;

public class RenameNotebook extends EditDialogManage {

	public RenameNotebook(FragmentManager fragmentManager) {
		// TODO Auto-generated constructor stub
		showDialog(fragmentManager, "Novo nome do caderno", "Renomear");
	}

	@Override
	public void onFinishEditDialog(String inPaginationDialogFragmentputText) {
		// TODO Auto-generated method stub
		Log.i("Renomear Caderno", inPaginationDialogFragmentputText);
	}

}
