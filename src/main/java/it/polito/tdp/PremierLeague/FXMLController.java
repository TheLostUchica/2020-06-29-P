/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Arco;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<Integer> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<Match> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<Match> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	if(model.isGraphLoaded()) {
    		for(Arco a : model.getConnessa()) {
    			this.txtResult.appendText(a.toString()+"\n");
    		}
    	}else {
    		this.txtResult.appendText("Il grafo non è stato creato.\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	this.cmbM1.getItems().clear();
    	this.cmbM2.getItems().clear();
    	
    	int mese = this.cmbMese.getValue();
    	String s = this.txtMinuti.getText();
    	try {
    		int minuti = Integer.parseInt(s);
    		if(minuti>0 && minuti<100) {
    			model.creagrafo(mese, minuti);
    			if(model.isGraphLoaded()) {
    				this.txtResult.appendText("Grafo creato con "+model.getGrafo().vertexSet().size()+ " vertici e "+model.getGrafo().edgeSet().size()+" archi. \n");
    				this.cmbM1.getItems().addAll(model.getGrafo().vertexSet());
    				this.cmbM2.getItems().addAll(model.getGrafo().vertexSet());
    			}else {
    	    		this.txtResult.appendText("Il grafo non è stato creato.\n");
    	    	}
    		}else {
    			this.txtResult.appendText("Inserire dati coerenti con il programma!\n");
    		}
    	}catch(NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.appendText("Inserisci i dati nel formato corretto.\n");
    	}
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	Match m1 = this.cmbM1.getValue();
    	Match m2 = this.cmbM2.getValue();
    	
    	List<Match> result = model.ricorsione(m1, m2);
    	if(result.size()>0) {
    		for(Match m : result) {
    			this.txtResult.appendText(m.toString()+"\n");
    		}
    		this.txtResult.appendText(model.getScore()+"\n");
    	}else {
    		this.txtResult.appendText("Non esiste cammino tra questi due match.\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
  
    }
    
    public void setComboMesi() {
    	for(int i = 1; i<=12; i++) {
    		this.cmbMese.getItems().add(i);
    	}
    }
    
    
}
