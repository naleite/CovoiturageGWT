package fr.istic.gla.shared;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Date;


@Entity
@XmlRootElement
public class Personne {
	
	long id;
	
	Collection<Commentaire> listCom;
	
	
	protected void setId(long id) {
		this.id = id;
	}

	Collection<Evenement> listEvent;
	
	
	protected void setListEvent(Collection<Evenement> listEvent) {
		this.listEvent = listEvent;
	}

	private Voiture voiture;
	
	@ManyToMany(mappedBy="participants")
	@JsonIgnore
	public Collection<Evenement> getListEvent() {
		return listEvent;
	}

	String localisation;
	
	String destination; //l'endroit ou veut aller la personne
	
	
	String nom;

	/**
	 * @return the dummy
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param dummy the dummy to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	@Transient
	public String getLocalisation() {
		return localisation;
	}

	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}
	@Transient
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	
	@OneToOne
	@JsonIgnore
	public Voiture getVoiture() {
		return voiture;
	}

	public void setVoiture(Voiture voiture) {
		this.voiture = voiture;
		this.voiture.setProprietaire(this);
	}
	
	/**
	 *  Le conducteur peut proposer un trajet avec sa voiture 
	 * @param depart
	 * @param dest
	 * @param dateDeDepart
	 * @return
	 */
	public Evenement proposeTrajet(String depart, String dest, Date dateDeDepart){
		Evenement trajet=new Evenement();
		trajet.setDateDeDepart(dateDeDepart);
		trajet.setDepart(depart);
		trajet.setDest(dest);
		trajet.setVoiture(this.voiture);
		trajet.addParticipant(this);
		return trajet;
	}
	
	public Commentaire redigeCom(Evenement ev, String ch)
	{
		Commentaire c = new Commentaire();
		c.personne= this;
		c.value=ch;
		c.evenement=ev;
		c.setDateField(new Date());
		return c;
		
	}
}
