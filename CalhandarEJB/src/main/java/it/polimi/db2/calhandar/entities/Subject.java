package it.polimi.db2.calhandar.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "subject", schema = "db_calhandar")
@NamedQuery(name="Subject.findById", query = "SELECT s FROM Subject s WHERE s.subjectId = ?1")

public class Subject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String subjectId;

	@OneToMany(mappedBy = "subject")
	private List<Commitment> commitments;

	@OneToMany(mappedBy = "subject")
	private List<ProfessorCourse> teachingSubjects;

	@OneToMany(mappedBy = "subject")
	private List<StudentCourse> studentSubjects;

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public List<Commitment> getCommitments() {
		return commitments;
	}

	public void setCommitments(List<Commitment> commitments) {
		this.commitments = commitments;
	}

	public List<ProfessorCourse> getTeachingSubjects() {
		return teachingSubjects;
	}

	public void setTeachingSubjects(List<ProfessorCourse> teachingSubjects) {
		this.teachingSubjects = teachingSubjects;
	}

	public List<StudentCourse> getStudentSubjects() {
		return studentSubjects;
	}

	public void setStudentSubjects(List<StudentCourse> studentSubjects) {
		this.studentSubjects = studentSubjects;
	}
}