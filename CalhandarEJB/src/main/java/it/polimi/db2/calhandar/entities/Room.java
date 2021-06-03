package it.polimi.db2.calhandar.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "room", schema = "db_calhandar")
@NamedQuery(name = "Room.findFree", query = "SELECT r FROM Room r  WHERE not exists (SELECT c FROM Commitment c WHERE r.roomId = c.room.roomId and c.date = current_date and (c.startTime > ?2 or c.endTime < ?1))")
public class Room implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String roomId;

	@OneToMany(mappedBy = "room")
	private List<Commitment> commitments;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
}