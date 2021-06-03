package it.polimi.db2.calhandar.entities.PK;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StudentCommitmentParticipantPK implements Serializable {

    @Column(name = "studentEmail")
    private String student;

    @Column(name = "commitmentId")
    private int commitmentId;

    public StudentCommitmentParticipantPK() {
    }

    public StudentCommitmentParticipantPK(String student, int commitmentId) {
        this.student = student;
        this.commitmentId = commitmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentCommitmentParticipantPK that = (StudentCommitmentParticipantPK) o;
        return commitmentId == that.commitmentId && Objects.equals(student, that.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, commitmentId);
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public int getCommitmentId() {
        return commitmentId;
    }

    public void setCommitmentId(int commitmentId) {
        this.commitmentId = commitmentId;
    }
}
