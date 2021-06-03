package it.polimi.db2.calhandar.entities.PK;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProfessorCoursePK implements Serializable {

    @Column(name = "professorId")
    private int professor;

    @Column(name = "subject")
    private String subject;

    public ProfessorCoursePK() {
    }

    public ProfessorCoursePK(int professor, String subject) {
        this.professor = professor;
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfessorCoursePK that = (ProfessorCoursePK) o;
        return Objects.equals(professor, that.professor) && Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(professor, subject);
    }

    public int getProfessor() {
        return professor;
    }

    public void setProfessor(int professor) {
        this.professor = professor;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
