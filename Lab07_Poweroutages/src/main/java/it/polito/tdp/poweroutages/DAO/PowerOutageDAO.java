package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.NercIdMap;
import it.polito.tdp.poweroutages.model.PowerOutages;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	// Stesso metodo del precedente ma utilizzo una mappa per evitare perdite
	public List<Nerc> getNercList(NercIdMap nercIdMap) {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(nercIdMap.get(n));
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	public List<PowerOutages> getPowerOutages(NercIdMap nercIdMap) {
		
		String sql = "select id, nerc_id, date_event_began, " + 
				"date_event_finished, customers_affected " + 
				"from poweroutages";
		List<PowerOutages> result = new ArrayList<>();
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Nerc n = nercIdMap.get(rs.getInt("nerc_id"));
				if(n == null) {
					System.err.println("Database is not consistent. Missing corresponding NERC.");
				} else {
					
					PowerOutages po = new PowerOutages(rs.getInt("id"), n, 
							rs.getTimestamp("date_event_began").toLocalDateTime(), 
							rs.getTimestamp("date_event_finished").toLocalDateTime(),
							rs.getInt("customers_affected"));
					result.add(po);
				}
				
			}
			
			conn.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	
	
	
	

}
