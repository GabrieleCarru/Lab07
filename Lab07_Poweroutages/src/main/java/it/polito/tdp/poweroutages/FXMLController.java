/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbNERC"
    private ComboBox<Nerc> cmbNERC; // Value injected by FXMLLoader

    @FXML // fx:id="txtMaxYears"
    private TextField txtMaxYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtMaxHours"
    private TextField txtMaxHours; // Value injected by FXMLLoader

    @FXML // fx:id="btnWorstCase"
    private Button btnWorstCase; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void analysisWorstCase(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbNERC != null : "fx:id=\"cmbNERC\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMaxYears != null : "fx:id=\"txtMaxYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMaxHours != null : "fx:id=\"txtMaxHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnWorstCase != null : "fx:id=\"btnWorstCase\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cmbNERC.getItems().addAll(this.model.getNercList());
    }
}
