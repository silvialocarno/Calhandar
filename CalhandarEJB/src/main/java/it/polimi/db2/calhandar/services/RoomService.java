package it.polimi.db2.calhandar.services;

import it.polimi.db2.calhandar.entities.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Stateless
public class RoomService {
	@PersistenceContext(unitName = "CalhandarEJB")
	private EntityManager em;

	public RoomService() {
	}

	public Room findFreeRoom(Date startTime, Date endTime) {
		List<Room> rooms = em.createNamedQuery("Room.findFree", Room.class).setParameter(1, startTime).setParameter(2, endTime).getResultList();
		Random rand = new Random();
		Room randomRoom = rooms.get(rand.nextInt(rooms.size()));
		return randomRoom;
	}
}
