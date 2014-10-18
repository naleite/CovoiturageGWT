package fr.istic.gla.server;


import fr.istic.gla.shared.*;

import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Path("/ev")
public class EvenementResource implements CovoitService {

	private List<Evenement> evenements=new ArrayList<Evenement>();
	
	EntityManager manager;
	
	public  EvenementResource() {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("dev");
		manager = factory.createEntityManager();
		EntityTransaction t = manager.getTransaction();
		
		Personne p = new Personne();
		
		t.begin();
		this.remplirBdd(t,manager);
        }
	
	private void remplirBdd(EntityTransaction t ,EntityManager manager) {
		// TODO Auto-generated method stub
		try {

			Voiture v=new Voiture();
			v.setNbPlaceTotal(3);
			v.setSeries("Benz");
			manager.persist(v);
			Personne c=new Personne();
			((Personne) c).setVoiture(v);
			c.setNom("Conducteur");
			c.setDestination("Paris");
			c.setLocalisation("Rennes");
			Date date=new Date();
			Evenement trajet=((Personne) c).proposeTrajet(c.getLocalisation(),c.getDestination(),date);
			
			Personne p1=new Personne();
			Personne p2=new Personne();
			
			p1.setNom("Paula");
			p2.setNom("Aday");
			
			p1.setDestination("Paris");
			p1.setLocalisation("Rennes");
			
			p2.setDestination("Paris");
			p2.setLocalisation("Rennes");
			
			manager.persist(c);
			manager.persist(p1);
			manager.persist(p2);
			
			trajet.addParticipant(p1);
			trajet.addParticipant(p2);
			manager.persist(p1.redigeCom(trajet, "hello world des commentaires"));
			manager.persist(trajet);
			Logger.getGlobal().info(trajet.toString());
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		t.commit();
		
	}
	
	@POST
	@Path("propose/{id}-{depart}-{dest}")
	//@Produces(MediaType.TEXT_PLAIN)
	public String proposeTrajet(@PathParam ("id") String id, String depart, String dest,
			Date dateDeDepart) {

		Query query=manager.createQuery("SELECT p FROM PERSONNE AS p WHERE ID=id");
		List result=query.getResultList();

		Personne personne = (Personne) result.get(0);
		manager.persist(personne.proposeTrajet(depart, dest, dateDeDepart));
		return "OK";
	}
	
	@GET
	@Path("findev/{depart}-{dest}-{from}-{to}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Evenement> findEvenement(@PathParam("depart")String depart, @PathParam("dest")String dest,
			@PathParam("from")Date fromTime,@PathParam("to") Date toTime) {
		Query query=manager.createQuery("SELECT evens FROM EVENEMENT AS evens WHERE DATE<=toTime AND DATE>=fromTime AND DEPART=depart AND DEST=dest");
		List result=query.getResultList();
		return result;
	}

	
	public void redigeCom(long idPersone,long idEven, String ch) {
		Query query_p=manager.createQuery("SELECT p FROM PERSONNE AS p WHERE ID=idPersonne");
		List personnes=query_p.getResultList();	
		Personne personne = (Personne) personnes.get(0);
		Query query_e=manager.createQuery("SELECT evens FROM EVENEMENT AS evens WHERE ID=idEven");
		Evenement ev=(Evenement) query_e.getResultList().get(0);
		manager.persist(personne.redigeCom(ev, ch));
		
	}

	public void takeTrajet(long idPersonne, long idEven) {
		Query query_p=manager.createQuery("SELECT p FROM PERSONNE AS p WHERE ID=idPersonne");
		List personnes=query_p.getResultList();	
		Personne personne = (Personne) personnes.get(0);
		Query query_e=manager.createQuery("SELECT evens FROM EVENEMENT AS evens WHERE ID=idEven");
		Evenement ev=(Evenement) query_e.getResultList().get(0);
		ev.addParticipant(personne);
		
		
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Personne> sayPlainTextHello()
	{
		Query query = manager.createQuery("select e from Personne as e");
		System.out.println("chargement des utilisateurs ....");
		List<Personne> ch=query.getResultList();
		return ch;
	}

	@DELETE
	@Path("delete/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Personne deleteById(@PathParam("id") String arg0) {
		EntityTransaction t = manager.getTransaction();
		t.begin();
		Personne b = manager.find(Personne.class, Integer.parseInt(arg0)) ;
		manager.remove(b);
		t.commit();
		return b;
	}

	public List<Personne> getAllPersonne() {
		
		return manager.createQuery("select e from Personne as b").getResultList();
	}
}
