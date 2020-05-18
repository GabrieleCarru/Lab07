package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PowerOutages implements Comparable<PowerOutages> {

	private int id;
	private Nerc nerc;
	private LocalDateTime outageStart;
	private LocalDateTime outageEnd;
	private int affectedPeople;
	private long outageDuration;
	private int year;
	
	
	
	/**
	 * @param id
	 * @param nerc
	 * @param outageStart
	 * @param outageEnd
	 * @param affectedPeople
	 * @param outageDuration
	 * @param year
	 */
	public PowerOutages(int id, Nerc nerc, LocalDateTime outageStart, 
			LocalDateTime outageEnd, int affectedPeople) {
		super();
		this.id = id;
		this.nerc = nerc;
		this.outageStart = outageStart;
		this.outageEnd = outageEnd;
		this.affectedPeople = affectedPeople;	
		
		// Definizione temporale!
		LocalDateTime tempDateTime = LocalDateTime.from(outageStart);
		this.outageDuration = tempDateTime.until(outageEnd, ChronoUnit.HOURS);
		
		this.year = outageStart.getYear();
	}

	

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public Nerc getNerc() {
		return nerc;
	}



	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}



	public LocalDateTime getOutageStart() {
		return outageStart;
	}



	public void setOutageStart(LocalDateTime outageStart) {
		this.outageStart = outageStart;
	}



	public LocalDateTime getOutageEnd() {
		return outageEnd;
	}



	public void setOutageEnd(LocalDateTime outageEnd) {
		this.outageEnd = outageEnd;
	}



	public int getAffectedPeople() {
		return affectedPeople;
	}



	public void setAffectedPeople(int affectedPeople) {
		this.affectedPeople = affectedPeople;
	}



	public long getOutageDuration() {
		return outageDuration;
	}



	public void setOutageDuration(long outageDuration) {
		this.outageDuration = outageDuration;
	}



	public int getYear() {
		return year;
	}



	public void setYear(int year) {
		this.year = year;
	}


	// CompareTo definito confrontando l'inzio di uno con l'inizio dell'altro
	@Override
	public int compareTo(PowerOutages o) {
		return this.outageStart.compareTo(o.getOutageStart());
	}



	@Override
	public String toString() {
		return "[PO_id=" + id + "]";
	}
	
	

}
