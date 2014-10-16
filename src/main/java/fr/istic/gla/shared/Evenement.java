package fr.istic.gla.shared;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Entity
/**
 * Classe qui définit un Evenement
 * @author josian
 *
 */
public class Evenement {


private long id ;

protected void setId(long id) {
	this.id = id;
}
protected void setNbPersonRest(int nbPersonRest) {
	this.nbPersonRest = nbPersonRest;
}
protected void setParticipants(Collection<Personne> participants) {
	this.participants = participants;
}
private String dest;
private String depart;

private Date dateDeDepart;
private int nbPersonRest;



@OneToOne
private Voiture voiture;


private Collection<Personne> participants ;


public Evenement() {
	this.participants=new ArrayList<Personne>();
}
/**
 * @return the id
 */
@Id @GeneratedValue
public long getId() {
	return id;
}

/**
 * @return the dest
 */
@Column
public String getDest() {
	return dest;
}

/**
 * @param dest the dest to set
 */
public void setDest(String dest) {
	this.dest = dest;
}

/**
 * @return the depart
 */
@Column
public String getDepart() {
	return depart;
}

/**
 * @param depart the depart to set
 */
public void setDepart(String depart) {
	this.depart = depart;
}


/**
 * @return the dateDeDepart
 */
@Column
public Date getDateDeDepart() {
	return dateDeDepart;
}
/**
 * @param dateDeDepart the dateDeDepart to set
 */
public void setDateDeDepart(Date dateDeDepart) {
	this.dateDeDepart = dateDeDepart;
}




/**
 * 
 * @param p ajoute p à l'évenement
 */
public void addParticipant(Personne p){
	if (!participants.contains(p) && getNbPersonRest()>0){
		this.participants.add(p);
		this.nbPersonRest--;
		System.out.println("One person added in this trip!");
	}
	else {
		System.err.println("Can not add!");
	}
}



@ManyToMany
public Collection<Personne> getParticipants(){
	return this.participants;
}


public Iterator<Personne> IterParticipants(){
	
	return this.participants.iterator();
}

/**
 * @return the nbPersonRest
 */
@Column
public int getNbPersonRest() {
	return nbPersonRest;

}


@Override
public String toString(){
	String info="Info Trajet: id="+this.getId()+" . From "+this.getDepart()+" to "+this.getDest()+" at "+getDateDeDepart();
	String info2 = " \nParicipant:";
	Iterator<Personne> iter=IterParticipants();
	while(iter.hasNext()){
		Personne p = iter.next();
		info2+=p.getId()+"    "+p.getNom()+"\n\t"; 
	}
	
	
	return info+info2;
}

@OneToOne
public Voiture getVoiture() {
	return voiture;
}
public void setVoiture(Voiture voiture) {
	this.voiture = voiture;
	this.nbPersonRest=this.voiture.getNbPlaceTotal();
}

}
