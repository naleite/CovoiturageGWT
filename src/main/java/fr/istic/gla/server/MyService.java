package fr.istic.gla.server;


import fr.istic.gla.shared.*;

import java.util.Date;
import java.util.List;

public interface MyService {
	
	public abstract String proposeTrajet(String id, String depart, String dest, Date dateDeDepart);
	
	public abstract List<Evenement> findEvenement(String depart, String dest, Date fromTime, Date toTime);
	
	public abstract void redigeCom(long idPersone, long idEven, String ch);
	
	public abstract void takeTrajet(long idPersonne, long idEven);
	
	public abstract Personne deleteById(String arg0);
	
	public abstract List<Personne> getAllPersonne();
	
}
