package it.polimi.db2.calhandar.services;

import it.polimi.db2.calhandar.entities.*;
import it.polimi.db2.calhandar.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.List;

@Stateless
public class CommitmentService {
	@PersistenceContext(unitName = "CalhandarEJB")
	private EntityManager em;

	public CommitmentService() {
	}

	public Commitment createCommitment(String name, Professor professor, Date startTime, Date endTime, String material, String recording, String topic, String subject, boolean replica) {
		Subject sub = em.createNamedQuery("Subject.findById", Subject.class).setParameter(1, subject).getSingleResult();
		Commitment commitment = new Commitment(name, professor, startTime, endTime, material, recording, topic, sub, replica);
		em.persist(commitment);

		ProfessorCommitmentParticipant professorCommitmentParticipant = new ProfessorCommitmentParticipant(professor, commitment);
		em.persist(professorCommitmentParticipant);

		List<Student> students = em.createNamedQuery("Student.findBySubject", Student.class).setParameter(1, subject).getResultList();
		for (Student s: students) {
			StudentCommitmentParticipant studentCommitmentParticipant = new StudentCommitmentParticipant(s, commitment);
			em.persist(studentCommitmentParticipant);
		}
		return commitment;
	}

	public void setRoom(Commitment commitment, Room room) {
		commitment.setRoom(room);
		em.merge(commitment);
	}
}
