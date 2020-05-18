/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutages;
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
    	
    	txtResult.clear();
    	
    	try {
    		Nerc selectedNerc = cmbNERC.getValue();
    		if(selectedNerc == null) {
    			txtResult.setText("Please select a Nerc (area identifier)");
    			return;
    		}
    		
    		int maxY = Integer.parseInt(txtMaxYears.getText());
    		int maxH = Integer.parseInt(txtMaxHours.getText());
    		int yearListSize = model.getYearsList().size();
    		if(maxY <= 0 || maxY > yearListSize) {
    			txtResult.setText("Please insert a number of years in range "
    					+ "[1, ... " + yearListSize + "]");
    			return;
    		}
    		
    		if(maxH < 0) {
    			txtResult.setText("Please insert a number bigger than 0.");
    			return;
    		}
    		
    		txtResult.setText(String.format("Computing the worst case analysis... "
    				+ "for %d hours and %d years.", maxH, maxY));
    		
    		List<PowerOutages> worstCase = model.getWorstCase(maxY, maxH, selectedNerc);
    		
    		txtResult.clear();
    		txtResult.appendText("Tot people affected: " + model.sumAffectedPeople(worstCase) + "\n");
    		txtResult.appendText("Tot hours of outage: " + model.sumOutageHours(worstCase) + "\n");
    		
    		for(PowerOutages po : worstCase) {
    			txtResult.appendText(String.format("%d %s %s %d %d", 
    					po.getYear(), po.getOutageStart(), po.getOutageEnd(), 
    					po.getOutageDuration(), po.getAffectedPeople()));
    			txtResult.appendText("\n");
    		}
    		
    	} catch(NumberFormatException e) {
    		txtResult.setText("Insert a valid number of years and od hours.");
    	}
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
