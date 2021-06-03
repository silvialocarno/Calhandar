package it.polimi.db2.calhandar.entities;

import it.polimi.db2.calhandar.entities.PK.ProfessorCommitmentParticipantPK;
import it.polimi.db2.calhandar.entities.PK.StudentCommitmentParticipantPK;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "professorCommitmentParticipant", schema = "db_calhandar")

public class ProfessorCommitmentParticipant implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProfessorCommitmentParticipantPK id;

	@ManyToOne
	@MapsId("professor")
	@JoinColumn(name = "professorId")
	private Professor professor;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("commitmentId")
	@JoinColumn(name = "commitmentId")
	private Commitment commitment;

	public ProfessorCommitmentParticipant() {
	}

    public ProfessorCommitmentParticipant(Professor professor, Commitment commitment) {
		this.professor = professor;
		this.commitment = commitment;
    }
}