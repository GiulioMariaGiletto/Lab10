package it.polito.tdp.rivers.model;

import it.polito.tdp.rivers.db.RiversDAO;
import java.util.*;

public class Model {
	Simulatore si;
	double giorniNCminSi;
	double CmedSi;
	RiversDAO rdao= new RiversDAO();	
	public List<River> getFiumi() {		
		return rdao.getAllRivers();		
	}
	public Misura getStat(River r){
		return rdao.getDatiFiumi(r);		
	}
	
	//SIMULATORE
	public void loadSimulatore(double k, double fMed, River r) {
		si=new Simulatore(k,fMed,rdao.getEventiFiume(r));
		si.Run();
		this.giorniNCminSi=si.getNGiorniCmin();
		this.CmedSi=si.getCmed();	
		}
	public double getGiorniNCmin() {
		return giorniNCminSi;
	}
	public double getCmed() {
		return CmedSi;
	}
	
	

}
