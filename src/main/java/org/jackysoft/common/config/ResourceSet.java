package org.jackysoft.common.config;

import java.util.HashSet;
import java.util.Set;

public class ResourceSet extends HashSet<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6603240000559501243L;
    
	public ResourceSet(String... eres){
		for(String res:eres)this.add(res);
	}
	public ResourceSet(Set<String> eres){
    	this.addAll(eres);
    }
	
}
