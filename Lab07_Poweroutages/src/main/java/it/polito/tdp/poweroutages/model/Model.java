package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	private List<Nerc> nercList;
	
	public Model() {
		podao = new PowerOutageDAO();
		nercList = new ArrayList<>(podao.getNercList());
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

}
