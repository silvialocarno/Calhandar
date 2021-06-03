package it.polimi.db2.calhandar.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "student", schema = "db_calhandar")
@NamedQuery(name = "Student.findBySubject", query = "SELECT s.student FROM StudentCourse s WHERE s.subject.subjectId = ?1")
@NamedQuery(name = "Student.checkCredentials", query = "SELECT s FROM Student s  WHERE s.email = ?1 and s.password = ?2")
@NamedQuery(name = "Student.findComDay", query = "SELECT cp.commitment FROM Student s, StudentCommitmentParticipant cp WHERE s.email = cp.student.email  and s.email = ?1 and cp.commitment.date = CURRENT_DATE ")

public class Student implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String email;

	private String name;

	private String surname;

	@Temporal(TemporalType.DATE)
	private Date birthdate;

	private String password;

	private Boolean isPhd;

	private String faculty;

	@OneToMany(mappedBy = "student")
	private List<StudentCommitmentParticipant> events;

	@OneToMany(mappedBy = "student")
	private List<StudentCourse> courses;

	public String getEmail() {
		return email;
	}
}