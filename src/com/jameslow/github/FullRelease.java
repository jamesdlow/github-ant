package com.github.api;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.apache.tools.ant.*;
import org.apache.tools.ant.types.*;
import org.kohsuke.github.*;

public class FullRelease extends CreateRelease {
	protected Vector<FileSet> filesets = new Vector<FileSet>();
	
	public void addFileSet(FileSet fileset) {
		if (!filesets.contains(fileset)) {
			filesets.add(fileset);
		}
	}
	
	public String executeGitHub() throws BuildException {
		try {
			GHRelease release = createRelease();
		
			//http://www.developer.com/lang/article.php/10924_3636196_2/More-on-Custom-Ant-Tasks.htm
			DirectoryScanner ds;
			for (FileSet fileset : filesets) {
				ds = fileset.getDirectoryScanner(getProject());
				File dir = ds.getBasedir();
				String[] filesInSet = ds.getIncludedFiles();
				for (String filename : filesInSet) {
					File file = new File(dir,filename);
					release.uploadAsset(file, MimeUtils.getMimeType(file));
				}
			}
			return release.getHtmlUrl();
		} catch (Exception e) {
			throw new BuildException(e);
		}
	}
}