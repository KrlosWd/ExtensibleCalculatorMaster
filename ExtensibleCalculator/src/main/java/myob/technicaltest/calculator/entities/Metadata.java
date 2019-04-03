package myob.technicaltest.calculator.entities;

import java.util.HashMap;

/**
 * Response for the /calculator/metadata endpoint
 * @author Juan Carlos Fuentes Carranza <juan.fuentes.carranza@gmail.com>
 *
 */
public class Metadata {
	HashMap<String, String> author;
	HashMap<String, String> maven;
	HashMap<String, String> git;
	
	public Metadata(HashMap<String, String> authormeta, HashMap<String, String> mavenmeta, HashMap<String, String> gitmeta) {
		this.author = authormeta;
		this.maven = mavenmeta;
		this.git = gitmeta;
		
	}

	public HashMap<String, String> getAuthor() {
		return author;
	}

	public void setAuthor(HashMap<String, String> author) {
		this.author = author;
	}

	public HashMap<String, String> getMaven() {
		return maven;
	}

	public void setMaven(HashMap<String, String> maven) {
		this.maven = maven;
	}

	public HashMap<String, String> getGit() {
		return git;
	}

	public void setGit(HashMap<String, String> git) {
		this.git = git;
	}

	@Override
	public String toString() {
		return "Metadata [author=" + author + ", maven=" + maven + ", git=" + git + "]";
	}
	
	
	
}
