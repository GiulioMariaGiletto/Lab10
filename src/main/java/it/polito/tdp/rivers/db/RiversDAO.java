package it.polito.tdp.rivers.db;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.rivers.model.Event;
import it.polito.tdp.rivers.model.Misura;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	public Misura getDatiFiumi(River r){
		String sql="SELECT COUNT(*) AS tot, AVG(flow) AS fMed,MIN(DAY) AS pmis,MAX(DAY) AS umis "
				+ "FROM flow "
				+ "WHERE river=? "
				+ "GROUP BY river";
		Misura result=null;
		

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			if (res.next()) {
				result=new Misura(r,res.getInt("tot"),res.getDouble("fMed"),res.getDate("pmis").toLocalDate(),res.getDate("umis").toLocalDate());
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}

	public PriorityQueue<Event> getEventiFiume(River r) {
		PriorityQueue<Event> result=new PriorityQueue<>();
		String sql="SELECT DAY, flow "
				+ "FROM flow "
				+ "WHERE river=?";
		
		

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Event(res.getDate("DAY").toLocalDate(),res.getDouble("flow")));
				
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
		
		
	}
}
