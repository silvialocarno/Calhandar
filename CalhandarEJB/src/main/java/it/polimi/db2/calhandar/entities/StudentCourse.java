package it.polimi.db2.calhandar.entities;

import it.polimi.db2.calhandar.entities.PK.StudentCoursePK;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "studentcourse", schema = "db_calhandar")

public class StudentCourse implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private StudentCoursePK id;

	@ManyToOne
	@MapsId("student")
	@JoinColumn(name = "studentEmail")
	private Student student;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("subject")
	@JoinColumn(name = "subject")
	private Subject subject;
}