package it.polimi.db2.calhandar.services;

import it.polimi.db2.calhandar.entities.Commitment;
import it.polimi.db2.calhandar.entities.Professor;
import it.polimi.db2.calhandar.entities.Student;
import it.polimi.db2.calhandar.entities.Subject;
import it.polimi.db2.calhandar.exceptions.CredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class StudentService {
	@PersistenceContext(unitName = "CalhandarEJB")
	private EntityManager em;

	public StudentService() {
	}

	public Student checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {
		List<Student> uList = null;
		try {
			uList = em.createNamedQuery("Student.checkCredentials", Student.class).setParameter(1, usrn).setParameter(2, pwd)
					.getResultList();
		} catch (PersistenceException e) {
			throw new CredentialsException("Could not verify credentals");
		}
		if (uList.isEmpty())
			return null;
		else if (uList.size() == 1)
			return uList.get(0);
		throw new NonUniqueResultException("More than one user registered with same credentials");

	}

	public List<Commitment> findCommitmentsOfTheDayByStudent(String email) {
		List<Commitment> commitments = null;
		commitments = em.createNamedQuery("Student.findComDay", Commitment.class).setParameter(1, email).getResultList();
		return commitments;
	}

}
