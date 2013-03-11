package com.danielsanfr.zimandroidwiki.controller;

import android.app.FragmentManager;
import android.util.Log;

public class RenamePage extends EditDialogManage {

	public RenamePage(FragmentManager fragmentManager) {
		// TODO Auto-generated constructor stub
		showDialog(fragmentManager, "Novo nome da página", "Renomear");
	}

	@Override
	public void onFinishEditDialog(String inPaginationDialogFragmentputText) {
		// TODO Auto-generated method stub
		Log.i("Renomear Página", inPaginationDialogFragmentputText);
	}

}
