package it.polimi.db2.calhandar.entities.PK;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProfessorCommitmentParticipantPK implements Serializable {

    @Column(name = "professorId")
    private int professor;

    @Column(name = "commitmentId")
    private int commitmentId;

    public ProfessorCommitmentParticipantPK() {
    }

    public ProfessorCommitmentParticipantPK(int professor, int commitmentId) {
        this.professor = professor;
        this.commitmentId = commitmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfessorCommitmentParticipantPK that = (ProfessorCommitmentParticipantPK) o;
        return commitmentId == that.commitmentId && Objects.equals(professor, that.professor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(professor, commitmentId);
    }

    public int getStudent() {
        return professor;
    }

    public void setStudent(int student) {
        this.professor = student;
    }

    public int getCommitmentId() {
        return commitmentId;
    }

    public void setCommitmentId(int commitmentId) {
        this.commitmentId = commitmentId;
    }
}
