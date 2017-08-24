package io.robusta.upload.api;

import java.io.File;
import java.util.List;

public class FileExplorer {

	private String initialpath = "";

	public FileExplorer(String path) {
		this.initialpath = path;
	}

	public List list(String files) {
		this.listDirectory(this.initialpath);
		return list(files);
	}

	private void listDirectory(String dir) {
		File file = new File(dir);
		File[] files = file.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory() == true) {

					System.out.println("  Fichier: " + files[i].getName());

				}

			}
		}
	}
}
