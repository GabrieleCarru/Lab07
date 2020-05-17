package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	private PowerOutageDAO podao;
	private List<Nerc> nercList;
	
	public Model() {
		podao = new PowerOutageDAO();
		nercList = podao.getNercList();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

}
