package com.github.api;

import org.apache.tools.ant.*;
import org.kohsuke.github.*;

public class CreateRelease extends GitHubReleaseTask {
	public String executeGitHub() throws BuildException {
		return createRelease().getHtmlUrl();
	}
	public GHRelease createRelease() {
		return createRelease(getRepository(), tag, name, body, commitish, draft, prerelease);
	}
	public GHRelease createRelease(GHRepository repository, String tag, String name, String body, String commitish, boolean draft, boolean prerelease) throws BuildException {
		try {
			GHRelease release = getRelease(repository, tag);
			if (release != null) {
				return release;
			} else {
				GHReleaseBuilder builder = repository.createRelease(tag);
				if (name != null) {
					builder.name(name);
				}
				if (body != null) {
					builder.body(body);
				}
				if (commitish != null) {
					builder.commitish(commitish);
				}
				builder.draft(draft);
				builder.prerelease(prerelease);
				return builder.create();
			}
		} catch (Exception e) {
			throw new BuildException(e);
		}
	}
}