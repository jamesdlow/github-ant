package com.github.api;

import org.apache.tools.ant.*;
import org.kohsuke.github.*;

/**
 * Uses: http://github-api.kohsuke.org
 * Docs: http://github-api.kohsuke.org/apidocs/index.html
 *
 */
public abstract class GitHubTask extends Task {
	//Ant properties
	protected String returnproperty;
	
	//Common properties
	protected String token;
	protected String tokenuser;
	protected String repo;
	protected String repouser;
	
	//Internal properties
	protected GitHub github;
	
	//Main Execute
	public void execute() throws BuildException {
		String result = executeGitHub();
		if (returnproperty != null) {
			getProject().setProperty(returnproperty, result);
		}
	}
	public abstract String executeGitHub() throws BuildException;
	
	//Getters/Setter
	public void setReturnProperty(String returnproperty) {
		this.returnproperty = returnproperty;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setTokenUser(String tokenuser) {
		this.token = tokenuser;
	}
	public void setRepo(String repo) {
		this.repo = repo;
	}
	public void setRepoUser(String repouser) {
		this.repouser = repouser;
	}
	
	//Helper Methods
	public GitHub connect() throws BuildException {
		try {
			if (github == null) {
				if (token != null) {
					github = GitHub.connect(tokenuser, token);
				} else {
					//Read from ~/.github
					github = GitHub.connect();
				}
			}
			return github;
		} catch (Exception e) {
			throw new BuildException(e);
		}
	}
	public GHRepository getRepository() throws BuildException {
		return getRepository(repouser, repo);
	}
	public GHRepository getRepository(String repouser, String repo) throws BuildException {
		try {
			return connect().getRepository(repouser+'/'+repo);
		} catch (Exception e) {
			throw new BuildException(e);
		}
	}
	public String httpGet() throws BuildException {
		try {
			System.out.println(getRepository().getDescription());
			//repository.getReleases()
		} catch (Exception e) {
			throw new BuildException(e);
		}
		return "ok";
	}
}