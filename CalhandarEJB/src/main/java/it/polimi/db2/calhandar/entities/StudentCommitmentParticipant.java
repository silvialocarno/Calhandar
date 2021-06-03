package it.polimi.db2.calhandar.entities;

import it.polimi.db2.calhandar.entities.PK.StudentCommitmentParticipantPK;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "studentCommitmentParticipant", schema = "db_calhandar")

public class StudentCommitmentParticipant implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private StudentCommitmentParticipantPK id;

	@ManyToOne
	@MapsId("student")
	@JoinColumn(name = "studentEmail")
	private Student student;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("commitmentId")
	@JoinColumn(name = "commitmentId")
	private Commitment commitment;

	public StudentCommitmentParticipant() {
	}

    public StudentCommitmentParticipant(Student s, Commitment commitment) {
		this.student = s;
		this.commitment = commitment;
    }
}