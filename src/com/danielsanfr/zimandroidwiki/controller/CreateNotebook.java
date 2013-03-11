package com.danielsanfr.zimandroidwiki.controller;

import java.io.IOException;

import com.danielsanfr.zimandroidwiki.view.SimpleEditDialog;

import android.app.FragmentManager;

public class CreateNotebook extends EditDialogManage implements
		SimpleEditDialog.SimpleEditDialogListener {

	private static final String ZIM_FILE = "notebook.zim";
	private static final String ZIM_DIRECTORY = "/.zim";
	private static final String ZIM_CONF = "/.zim/state.conf";

	public CreateNotebook(FragmentManager fragmentManager) {
		// TODO Auto-generated constructor stub
		showDialog(fragmentManager, "Nome do caderno", "Criar");
	}

	private void createNotebook(String notebookName) {
		ManageFiles manageFiles = new ManageFiles();
		manageFiles.createDiretory(notebookName);
		try {
			manageFiles.createFile(notebookName, ZIM_FILE);
			manageFiles.salveContentInFile(notebookName + "/" + ZIM_FILE,
					new String("[Notebook]\nname=" + notebookName
							+ "\nversion=0.4\nendofline=unix\nprofile=None\n"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		manageFiles.createDiretory(notebookName + ZIM_DIRECTORY);
		try {
			manageFiles.createFile(notebookName, notebookName + ZIM_CONF);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onFinishEditDialog(String inPaginationDialogFragmentputText) {
		// TODO Auto-generated method stub
		createNotebook(inPaginationDialogFragmentputText);
	}

}
