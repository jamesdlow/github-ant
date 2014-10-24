package com.github.api;

import org.apache.tools.ant.*;
import org.kohsuke.github.*;

public abstract class GitHubReleaseTask extends GitHubTask {
	//Common properties
	protected String tag;
	protected String name;
	protected String body;
	protected String commitish;
	protected boolean draft;
	protected boolean prerelease;
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public void setCommitish(String commitish) {
		this.commitish = commitish;
	}
	public void setDraft(boolean draft) {
		this.draft = draft;
	}
	public void setPrerelease(boolean prerelease) {
		this.prerelease = prerelease;
	}
	
	public GHRelease getRelease() throws BuildException {
		return getRelease(getRepository(), tag);
	}
	public GHRelease getRelease(GHRepository repository, String tag) throws BuildException {
		try {
			PagedIterable<GHRelease> releases = repository.listReleases();
			for (GHRelease release : releases) {
				if (release.getTagName().equals(tag)) {
					return release;
				}
			}
			return null;
		} catch (Exception e) {
			throw new BuildException(e);
		}
	}
}