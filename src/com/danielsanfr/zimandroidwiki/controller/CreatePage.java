package com.danielsanfr.zimandroidwiki.controller;

import android.app.FragmentManager;
import android.util.Log;

public class CreatePage extends EditDialogManage {

	public CreatePage(FragmentManager fragmentManager) {
		// TODO Auto-generated constructor stub
		showDialog(fragmentManager, "Nome da página", "Criar");
	}

	@Override
	public void onFinishEditDialog(String inPaginationDialogFragmentputText) {
		// TODO Auto-generated method stub
		Log.i("Criar Página", inPaginationDialogFragmentputText);
	}

}
