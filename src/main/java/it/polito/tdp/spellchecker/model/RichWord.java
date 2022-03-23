package it.polito.tdp.spellchecker.model;

public class RichWord {

	private String parola;
	private boolean corretto;
	
	public RichWord(String s) {
		corretto = false ;
		parola = s;
	}

	public String getParola() {
		return parola;
	}

	public void setParola(String parola) {
		this.parola = parola;
	}

	public boolean isCorretto() {
		return corretto;
	}

	public void setCorretto(boolean corretto) {
		this.corretto = corretto;
	}

	@Override
	public String toString() {
		return this.parola;
	}
	
	
	
}
