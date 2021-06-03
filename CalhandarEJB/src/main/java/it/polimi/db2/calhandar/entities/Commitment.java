package it.polimi.db2.calhandar.entities;

import it.polimi.db2.calhandar.entities.Enum.Commitment_type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "commitment", schema = "db_calhandar")

public class Commitment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int commitmentId;

	@Enumerated(EnumType.STRING)
	private Commitment_type type;

	@Temporal(TemporalType.TIME)
	private java.util.Date startTime;

	@Temporal(TemporalType.TIME)
	private java.util.Date endTime;

	@Temporal(TemporalType.DATE)
	private Date date;

	private Boolean isReplicated;

	private String topic;

	private String name;

	private String material;

	private String recording;

	private String location;

	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "room")
	private Room room;

	@OneToMany(mappedBy = "commitment")
	private List<StudentCommitmentParticipant> stud_participants;

	@OneToMany(mappedBy = "commitment")
	private List<ProfessorCommitmentParticipant> prof_participants;

	@ManyToOne
	@JoinColumn(name = "creator")
	private Professor creator;

	@ManyToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "subject")
	private Subject subject;

	public Commitment() {
	}

	public Commitment(String name, Professor professor, Date startTime, Date endTime, String material, String recording, String topic, Subject subject, boolean replica) {
		this.name = name;
		this.creator = professor;
		this.startTime = startTime;
		this.endTime = endTime;
		this.material = material;
		this.recording = recording;
		this.topic = topic;
		this.isReplicated = replica;
		this.subject = subject;
		this.date = new Date();
		this.type = Commitment_type.LESSON;
	}

	public int getCommitmentId() {
		return commitmentId;
	}

	public void setCommitmentId(int commitmentId) {
		this.commitmentId = commitmentId;
	}

	public Commitment_type getCommitment_type() {
		return type;
	}

	public void setCommitment_type(Commitment_type commitment_type) {
		this.type = commitment_type;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getTime(Date date) {
		SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
		return localDateFormat.format(date);
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getFormattedDate(Date date){
		SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return localDateFormat.format(date);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getReplicated() {
		return isReplicated;
	}

	public void setReplicated(Boolean replicated) {
		isReplicated = replicated;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getCommitmentName() {
		return name;
	}

	public void setCommitmentName(String name) {
		this.name = name;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getRecording() {
		return recording;
	}

	public void setRecording(String recording) {
		this.recording = recording;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<StudentCommitmentParticipant> getParticipants() {
		return stud_participants;
	}

	public void setParticipants(List<StudentCommitmentParticipant> participants) {
		this.stud_participants = participants;
	}

	public List<ProfessorCommitmentParticipant> getProf_participants() {
		return prof_participants;
	}

	public void setProf_participants(List<ProfessorCommitmentParticipant> prof_participants) {
		this.prof_participants = prof_participants;
	}

	public Professor getCreator() {
		return creator;
	}

	public void setCreator(Professor creator) {
		this.creator = creator;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}