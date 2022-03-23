package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Dictionary model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cmbLanguage;

    @FXML
    private TextArea txtInput;

    @FXML
    private TextArea txtOutput;

    @FXML
    private TextField txtOutputMassageNumber;

    @FXML
    void chooseLanguage(ActionEvent event) {
    	model.loadDictionary(cmbLanguage.getValue());
    }

    @FXML
    void doClearText(ActionEvent event) {
    	txtInput.clear();
    	txtOutput.clear();
    	txtOutputMassageNumber.clear();
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	String testo = txtInput.getText().toLowerCase();
    	
    	testo = testo.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\?-_`~()\\[\\]\"]", "");
    	testo = testo.toLowerCase();
    	
    	if(testo.compareTo("")==0) {
    		txtOutput.setText("Inserire Qualcosa!");
    		return;
    	}
    	
    	List<String> parole = new LinkedList<>();
    	
    	StringTokenizer st = new StringTokenizer(testo, " "); // Stringtokenizer prende una stringa e la dividi in altre piccole stringhe
    														  // dove considera come spazio delimitatorio quello che voglio io ( in questo
    														  // caso lo spazio " " )
    	
    	while(st.hasMoreTokens()) { // In questo ciclo while aggiungo ad una lista di parole ogni piccola stringa estratta
    								// dalla stringa iniziale ( st.hasMoreTokens() )
    		parole.add(st.nextToken());
    	}
    	String s="";
    	
    	long start = System.currentTimeMillis();
    	List<RichWord> paroleSbagliate = new LinkedList<>(model.spellCheckText(parole));
    	long end = System.currentTimeMillis();
    	System.out.println("Normale (contains) = "+(end-start)+" ms");
    	
    	long startDico = System.currentTimeMillis();
    	List<RichWord> paroleSbagliate2 = new LinkedList<>(model.spellCheckTextDichotomic(parole));
    	long endDico = System.currentTimeMillis();
    	System.out.println("Dicotomica = "+(endDico-startDico)+" ms");
    	
    	long startLin = System.currentTimeMillis();
    	List<RichWord> paroleSbagliate3 = new LinkedList<>(model.spellCheckTextLinear(parole));
    	long endLin = System.currentTimeMillis();
    	System.out.println("Lineare = "+(endLin-startLin)+" ms");
    	
    	if(paroleSbagliate.size()==0) {
    		txtOutput.setText("Non ci sono errori");
    		return;
    	} else
    		
  
    	for(RichWord rw : paroleSbagliate) {
    		s+=rw.getParola()+"\n";
    	}
    	
    	//for(RichWord rw : paroleSbagliate2) {
    	//	s+=rw.getParola()+"\n";
    	//}
    	txtOutput.setText(s);
    	txtOutputMassageNumber.setText("Numero totale di errori:" +model.getNumeroErrori());
    	return;
    }
    
    public void setModel(Dictionary model) {
    	cmbLanguage.getItems().addAll("English","Italian"); // Scrivere le Stringhe dentro una 
    														// ComboBox
    	this.model = model ;
    }

    @FXML
    void initialize() {
        assert cmbLanguage != null : "fx:id=\"cmbLanguage\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtOutputMassageNumber != null : "fx:id=\"txtOutputMassageNumber\" was not injected: check your FXML file 'Scene.fxml'.";

    }

}

