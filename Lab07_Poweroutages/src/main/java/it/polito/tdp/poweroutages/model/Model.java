package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	private PowerOutageDAO podao;
	
	private List<Nerc> nercList;
	private NercIdMap nercIdMap;
	
	private List<PowerOutages> eventiList;
	private List<PowerOutages> eventListFiltred;
	private List<PowerOutages> solution;
	
	private int maxAffectedPeople;
	
	public Model() {
		podao = new PowerOutageDAO();
		
		nercIdMap = new NercIdMap();
		nercList = podao.getNercList(nercIdMap);
		
		eventiList = podao.getPowerOutages(nercIdMap);
	}
	
	// Metodo che chiama/prepara la ricorsione
	public List<PowerOutages> getWorstCase(int maxNumbersOfYears, 
			int maxHoursOfOutage, Nerc nerc) {
		
		solution = new ArrayList<>();
		maxAffectedPeople = 0;
		
		eventListFiltred = new ArrayList<>();
		
		// Per diminuire il numero di elementi (PowerOutage) da verificare, controllo  
		// che riguardino il 'nerc' di interesse. Se si, li aggiungo alla listaFILTRATA.
		for(PowerOutages event : eventiList) {
			if(event.getNerc().equals(nerc)) {
				eventListFiltred.add(event);
			}
		}
		
		Collections.sort(eventListFiltred);		// implementa Comparable per tempo di inizio
		
		// chiamata al metodo ricorsivo
		recursive(new ArrayList<PowerOutages>(), maxNumbersOfYears, maxHoursOfOutage);
		
		return solution;
	}
	

	public int sumAffectedPeople(List<PowerOutages> partial) {
		
		int sum = 0;
		
		for(PowerOutages po : partial) {
			sum += po.getAffectedPeople();
		}
		return sum;
		
	}
	
	private boolean checkMaxYears(List<PowerOutages> partial, int maxNumberOfYears) {
		
		if(partial.size() >= 2) {
			
			// acquisisco il primo e l'ultimo inserito e li controllo
			int y1 = partial.get(0).getYear();
			int y2 = partial.get(partial.size()-1).getYear();
			
			if((y2-y1+1) > maxNumberOfYears) {
				return false;
			}
		}
		return true;
		
	}
	
	public int sumOutageHours(List<PowerOutages> partial) {
		
		int sum = 0;
		
		for(PowerOutages po : partial) {
			sum += po.getOutageDuration();
		}
		return sum;
		
	}
	
	private boolean checkMaxHoursOfOutage(List<PowerOutages> partial, int maxHoursOfOutage) {
		
		int sum = sumOutageHours(partial);
		if(sum > maxHoursOfOutage) {
			return false;
		}
		return true;
		
	}
	
	
	// Funzione ricorsiva
	private void recursive(ArrayList<PowerOutages> partial, 
			int maxNumberOfYears, int maxHoursOfOutage) {
		
		// Condizione per verificare se una soluzione é migliore di un altra.
		// Aggiorno man mano la soluzione migliore che trovo. 
		if(sumAffectedPeople(partial) > maxAffectedPeople) {
			maxAffectedPeople = sumAffectedPeople(partial);
			solution = new ArrayList<PowerOutages>(partial);
		}
		
		for(PowerOutages event : eventListFiltred) {
			
			// Controllo che l'evento non sia ancora stato aggiunto
			if(!partial.contains(event)) {
				partial.add(event);
			
				// CONDIZIONE DI TERMINAZIONE (non netta come al solito perché devo comunque
				// 								controllare tutti gli eventi)
				if(checkMaxYears(partial, maxNumberOfYears) 
						&& checkMaxHoursOfOutage(partial, maxHoursOfOutage)) {
				
					recursive(partial, maxNumberOfYears, maxHoursOfOutage);
				}
			
				// Backtacking
				partial.remove(event);
			
			}
		}	
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<Integer> getYearsList() {
		
		// Usiamo il Set perché non puó contenere duplicati
		Set<Integer> yearSet = new HashSet<Integer>();
		
		for(PowerOutages event : eventiList) {
			yearSet.add(event.getYear());
		}
		
		List<Integer> yearList = new ArrayList<Integer>(yearSet);
		
		yearList.sort(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2.compareTo(o1);
			}
		});
		return yearList;
	}

}
