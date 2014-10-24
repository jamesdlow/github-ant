package com.github.api;

import java.io.*;
import java.nio.file.*;
import org.apache.tools.ant.*;
import org.kohsuke.github.*;

public class UploadAsset extends GitHubReleaseTask {
	//Common properties
	protected String file;
	protected String contenttype;
	
	public void setFile(String file) {
		this.file = file;
	}
	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}
	
	public String executeGitHub() throws BuildException {
		return uploadAsset().getBrowserDownloadUrl();
	}
	public GHAsset uploadAsset()  throws BuildException {
		return uploadAsset(getRelease(), file, contenttype);
	}
	public GHAsset uploadAsset(GHRelease release, String filepath, String contenttype)  throws BuildException {
		try {
			System.out.println(filepath);
			File file = new File(filepath);
			if (contenttype == null) {
				contenttype = Files.probeContentType(file.toPath());
			}
			return release.uploadAsset(file, contenttype);
		} catch (Exception e) {
			throw new BuildException(e);
		}
	}
}