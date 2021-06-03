package it.polimi.db2.calhandar.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "professor", schema = "db_calhandar")
@NamedQuery(name = "Professor.checkCredentials", query = "SELECT p FROM Professor p  WHERE p.email = ?1 and p.password = ?2")
@NamedQuery(name = "Professor.findComDay", query = "SELECT cp.commitment FROM Professor p, ProfessorCommitmentParticipant cp WHERE p.email = cp.professor.email  and p.email = ?1 and cp.commitment.date = CURRENT_DATE ")
@NamedQuery(name = "Professor.findSubjects", query = "SELECT s.subject FROM ProfessorCourse s, Professor p WHERE p.email=s.professor.email and p.email= ?1")
public class Professor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String email;

	private String name;

	private String surname;

	@Temporal(TemporalType.DATE)
	private Date birthdate;

	private String password;

	private String department;

	@OneToMany(mappedBy = "creator")
	private List<Commitment> commitments;

	@OneToMany(mappedBy = "professor")
	private List<ProfessorCommitmentParticipant> events;

	@OneToMany(mappedBy = "professor")
	private List<ProfessorCourse> courses;

	public String getEmail() {
		return email;
	}
}