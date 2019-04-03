package myob.technicaltest.calculator.entities;

import java.util.HashMap;

/**
 * Response for the /calculator/metadata endpoint
 * @author Juan Carlos Fuentes Carranza <juan.fuentes.carranza@gmail.com>
 *
 */
public class Metadata {
	HashMap<String, String> author;
	HashMap<String, String> project;
	
	public Metadata(HashMap<String, String> authormeta, HashMap<String, String> project) {
		this.author = authormeta;
		this.project = project;
		
	}

	public HashMap<String, String> getAuthor() {
		return author;
	}

	public void setAuthor(HashMap<String, String> author) {
		this.author = author;
	}

	public HashMap<String, String> getProject() {
		return project;
	}

	public void setProject(HashMap<String, String> project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "Metadata [author=" + author + ", project=" + project + "]";
	}
	
	
	
}
