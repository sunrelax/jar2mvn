package it.eng.mvn.service.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.Attributes.Name;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class ListFile {

	public static void main(String[] args) throws IOException {
		File f = new File("/home/giuseppe/Documents/java/workspace/av_be/ws_regauth/ws_regauth/lib");
		listFilesForFolder(f);
	}

	public static void listFilesForFolder(final File folder) throws IOException {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				System.out.println();
				System.out.println(fileEntry.getName());
				FileInputStream fileInputStream = new FileInputStream(fileEntry);
				JarInputStream jarStream = new JarInputStream(fileInputStream);
				Manifest mf = jarStream.getManifest();
				System.out.println(mf.getMainAttributes().getValue(Name.EXTENSION_NAME));
				System.out.println(mf.getMainAttributes().getValue(Name.CLASS_PATH));
				System.out.println(mf.getMainAttributes().getValue(Name.EXTENSION_INSTALLATION));
				System.out.println(mf.getMainAttributes().getValue(Name.EXTENSION_LIST));
				System.out.println(mf.getMainAttributes().getValue(Name.EXTENSION_NAME));
				System.out.println(mf.getMainAttributes().getValue(Name.IMPLEMENTATION_TITLE));
				System.out.println(mf.getMainAttributes().getValue(Name.IMPLEMENTATION_URL));
				System.out.println(mf.getMainAttributes().getValue(Name.IMPLEMENTATION_VENDOR));
				System.out.println(mf.getMainAttributes().getValue(Name.IMPLEMENTATION_VENDOR_ID));
				System.out.println(mf.getMainAttributes().getValue(Name.IMPLEMENTATION_VERSION));
				System.out.println(mf.getMainAttributes().getValue(Name.MAIN_CLASS));
				System.out.println(mf.getMainAttributes().getValue(Name.MANIFEST_VERSION));
				System.out.println(mf.getMainAttributes().getValue(Name.SEALED));
				System.out.println(mf.getMainAttributes().getValue(Name.SIGNATURE_VERSION));
				System.out.println(mf.getMainAttributes().getValue(Name.SPECIFICATION_TITLE));
				System.out.println(mf.getMainAttributes().getValue(Name.SPECIFICATION_VENDOR));
				System.out.println(mf.getMainAttributes().getValue(Name.SPECIFICATION_VERSION));

				
			}
		}
	}
}
