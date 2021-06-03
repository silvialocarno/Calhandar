package it.polimi.db2.calhandar.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.NonUniqueResultException;

import it.polimi.db2.calhandar.entities.Commitment;
import it.polimi.db2.calhandar.entities.Professor;
import it.polimi.db2.calhandar.entities.Student;
import it.polimi.db2.calhandar.entities.Subject;
import it.polimi.db2.calhandar.exceptions.*;
import java.util.List;

@Stateless
public class ProfessorService {
	@PersistenceContext(unitName = "CalhandarEJB")
	private EntityManager em;

	public ProfessorService() {
	}

	public Professor checkCredentials(String usrn, String pwd) throws CredentialsException, NonUniqueResultException {
		List<Professor> uList = null;
		try {
			uList = em.createNamedQuery("Professor.checkCredentials", Professor.class).setParameter(1, usrn).setParameter(2, pwd)
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

	public List<Commitment> findCommitmentsOfTheDayByProfessor(String email) {
		List<Commitment> commitments = null;
		commitments = em.createNamedQuery("Professor.findComDay", Commitment.class).setParameter(1, email).getResultList();
		return commitments;
	}

	public List<Subject> findSubjectByProfessor(String email){
		List<Subject> subjects = null;
		subjects = em.createNamedQuery("Professor.findSubjects", Subject.class).setParameter(1, email).getResultList();
		return  subjects;
	}
}
