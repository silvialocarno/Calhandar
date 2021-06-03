package it.polimi.db2.calhandar.entities;

import it.polimi.db2.calhandar.entities.PK.ProfessorCoursePK;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "professorcourse", schema = "db_calhandar")

public class ProfessorCourse implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProfessorCoursePK id;

	@ManyToOne
	@MapsId("professor")
	@JoinColumn(name = "professorId")
	private Professor professor;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("subject")
	@JoinColumn(name = "subject")
	private Subject subject;
}