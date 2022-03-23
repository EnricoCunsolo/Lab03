package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Dictionary {
	
	private List<String> dizionario;
	private String lingua;
	private int numeroErrori;

	
	public void loadDictionary(String language) {
		
		dizionario = new LinkedList<>();
		lingua=language;
		try {
			FileReader fr = new FileReader("src/main/resources/" +lingua+ ".txt"); // Indico la risorsa dove si trovano i file da leggere
			BufferedReader br = new BufferedReader(fr);
			String word;
			while ((word = br.readLine()) != null) {
				// Aggiungo parola al dizionario (già minuscola)
				dizionario.add(word.toLowerCase());											
			}
			br.close();
			System.out.println("Dizionario creato con "+dizionario.size()+" parole");
			return;
			 
		} catch (IOException e){
			System.out.println("Errore nella lettura del file");
		}	
	}
	
	public List<RichWord> spellCheckText(List<String> inputTextList) {
		// E' la più veloce perchè utilizza hashcode
		List<RichWord> paroleSbagliate = new LinkedList<>();
		int countErrors = 0;
		for(String s : inputTextList) {
			RichWord rw = new RichWord(s);
			if(dizionario.contains(s)) {
				rw.setCorretto(true);
			}
			else { 
				rw.setCorretto(false);
				paroleSbagliate.add(rw);
				countErrors++;
			}
			}
		setErrori(countErrors);
		return paroleSbagliate;		
	}

	public void setErrori(int countErrors) {
		this.numeroErrori  = countErrors;
	}
	public int getNumeroErrori() {
		return this.numeroErrori;
	}
	
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList) {
		List<RichWord> paroleSbagliate = new LinkedList<>();
		int countErrors = 0;
		for(String s : inputTextList) {
			RichWord rw = new RichWord(s);
			if(ricercaLineare(s)==false) {
				paroleSbagliate.add(rw);
				countErrors++;
			}
		}
		setErrori(countErrors);
		return paroleSbagliate;
	}
	
	public boolean ricercaLineare(String s) {
		boolean trovato = false;
			for(String sd : dizionario) {
				if(s.compareTo(sd)==0) {
					trovato = true;
					return trovato;
				}
		}
		return trovato;
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList) {
		List<RichWord> paroleSbagliate = new LinkedList<>();
		int countErrors = 0;
		for(String s : inputTextList) {			
			RichWord rw = new RichWord(s);			
			if(ricercaDico(s)==false) {
				paroleSbagliate.add(rw);
				countErrors++;				
			}
		}
		setErrori(countErrors);
		return paroleSbagliate;
	}
	
	public boolean ricercaDico(String s) {
		
		boolean trovato = false;
		int n1 = 0;
		int n2 = dizionario.size();
		
		while (n1 <= n2) {
			
			int nMedio = n1 + (n2-n1) / 2; // Se faccio n1+n2/2 vi è una piccollissima percentuale che possa contenere dei bug
			
			// MEDIO = PICCOLO + ( GRANDE - PICCOLO ) / 2
			
			if(dizionario.get(nMedio).compareTo(s) == 0) {
				trovato = true ;
				return trovato;
			}
			if(dizionario.get(nMedio).compareTo(s) > 0) {
				n2 = nMedio-1;
			}
			else
				n1 = nMedio+1;
		}
		
		return trovato;
	}
	
	
	
	
}
