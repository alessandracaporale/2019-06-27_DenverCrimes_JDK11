package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

//controller turno A --> switchare al branch master_turnoB o master_turnoC per turno B o C

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxCategoria;

    @FXML
    private ComboBox<Integer> boxAnno;

    @FXML
    private Button btnAnalisi;

    @FXML
    private ComboBox<?> boxArco;

    @FXML
    private Button btnPercorso;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcolaPercorso(ActionEvent event) {

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.setText("");
    	String category = this.boxCategoria.getValue();
    	int month = this.boxAnno.getValue();
    	this.model.creaGrafo(category, month);
    	this.txtResult.setText(this.model.getArchiMaggioriDiPM(category, month));
    }

    @FXML
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

        
    }

	public void setModel(Model model) {
		this.model = model;
		
		ObservableList<String> data = FXCollections.observableArrayList();
		for (String s : this.model.getAllCategories()) {
			data.add(s);
		}
		this.boxCategoria.setItems(data);
		
		ObservableList<Integer> mesi = FXCollections.observableArrayList();
		for (int i=1; i<13; i++) {
			mesi.add(i);
		}
		this.boxAnno.setItems(mesi);
		
	}
}
