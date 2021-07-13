package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.*;

import it.polito.tdp.crimes.model.*;


public class EventsDAO {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			List<Event> list = new ArrayList<>() ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> getAllCategories() {
		String sql ="SELECT distinct offense_category_id "
				+ "FROM events";
		List<String> result = new ArrayList<String>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			while (res.next()) {
				result.add(res.getString("offense_category_id"));
			}
			conn.close();
			return result;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> getOffenseType(String offense_category_id, int mese) {
		String sql ="SELECT distinct offense_type_id "
				+ "FROM events "
				+ "WHERE offense_category_id = ? AND Month(reported_date) = ?";
		List<String> result = new ArrayList<String>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, offense_category_id);
			st.setInt(2, mese);
			ResultSet res = st.executeQuery() ;
			
			while (res.next()) {
				result.add(res.getString("offense_type_id"));
			}
			conn.close();
			return result;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Adiacenza> getAdiacenze(String category, int month) {
		String sql = "SELECT e1.offense_type_id as v1, e2.offense_type_id as v2, COUNT(distinct(e1.neighborhood_id)) as peso "
				+ "FROM events e1, events e2 "
				+ "WHERE e1.offense_category_id = ? AND e1.offense_category_id = e2.offense_category_id "
				+ 	"AND Month(e1.reported_date) = ? AND Month(e1.reported_date) = Month(e2.reported_date) "
				+ 	"AND e1.offense_type_id > e2.offense_type_id AND e1.neighborhood_id = e2.neighborhood_id "
				+ "GROUP BY e1.offense_type_id, e2.offense_type_id";
		List<Adiacenza> result = new LinkedList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, category);
			st.setInt(2, month);
			ResultSet res = st.executeQuery() ;
			
			while (res.next()) {
				String v1 = res.getString("v1");
				String v2 = res.getString("v2");
				int peso = res.getInt("peso");
				Adiacenza a = new Adiacenza (v1, v2, peso);
				result.add(a);
			}
			conn.close();
			st.close();
			res.close();
			return result;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}

}
