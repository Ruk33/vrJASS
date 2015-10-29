package com.ruke.vrjassc.vrjassc.util;

import java.util.LinkedList;

/**
 * @deprecated
 * @author Ruke
 *
 */
public class Preprocessor {

	protected LinkedList<PreprocessorAction> preprocessors;
	protected String code;
	protected String output;
	
	public Preprocessor(String code) {
		this.preprocessors = new LinkedList<PreprocessorAction>();
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getOutput() {
		return this.output;
	}
	
	public void add(PreprocessorAction action) {
		this.preprocessors.add(action);
	}
	
	public void run() {
		this.output = this.code;
		
		for (PreprocessorAction action : this.preprocessors) {
			this.output = action.run(this.output);
		}
	}
	
}
