package com.prototype.organisation.member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prototype.organisation.CustomProjectsSerializer;
import com.prototype.organisation.CustomTasksSerializer;
import com.prototype.organisation.project.Project;
import com.prototype.organisation.task.Task;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Member{
	
	@Id 
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	//@Column(columnDefinition = "BINARY(16)", updatable = false, nullable = false) //only for dev (with MySQL)-> @Type must be deactivated
	@Column(updatable = false, nullable = false)
	@Type(type = "uuid-binary")
    private UUID id;
	private String firstName;
	private String lastName;
	private String photoUrl;
	private String position;
	private String email;
	private String phone;
	private String birthday;
	@JsonIgnore
	private String password;
	
	@CreatedDate
	private Date joinDate = new Date();
	@LastModifiedDate
	private Date modifiedDate = new Date();

	@JsonSerialize(using = CustomProjectsSerializer.class)
	@ManyToMany(mappedBy = "projectLeaders")
	@LazyCollection(LazyCollectionOption.TRUE)
	private Collection<Project> leadedProjects = new ArrayList<>(); 
	
	@JsonSerialize(using = CustomProjectsSerializer.class)
	@ManyToMany(mappedBy = "projectMembers")
	@LazyCollection(LazyCollectionOption.TRUE)
	private Collection<Project> memberProjects = new ArrayList<>();
	
	@JsonSerialize(using = CustomProjectsSerializer.class)
	@ManyToMany
	@LazyCollection(LazyCollectionOption.TRUE)
	private Collection<Project> finishedProjects = new ArrayList<>();
	
	@JsonSerialize(using = CustomTasksSerializer.class)
	@ManyToMany(mappedBy = "members")
	@LazyCollection(LazyCollectionOption.TRUE)
	private Collection<Task> tasks = new ArrayList<>();
	
	public Member() {
		
	}
	
	
	public Member(UUID id, String firstName, String lastName, String photoUrl, String position, String email,
			String phone, Date joinDate, Date modifiedDate, Collection<Project> leadedProjects,
			Collection<Project> memberProjects, Collection<Project> finishedProjects, String birthday, String password,
			Collection<Task> tasks) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.photoUrl = photoUrl;
		this.position = position;
		this.email = email;
		this.phone = phone;
		this.joinDate = joinDate;
		this.modifiedDate = modifiedDate;
		this.leadedProjects = leadedProjects;
		this.memberProjects = memberProjects;
		this.finishedProjects = finishedProjects;
		this.birthday = birthday;
		this.password = password;
		this.tasks = tasks;
	}
	
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getBirthday() {
		return birthday;
	}


	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	public String getPhotoUrl() {
		return photoUrl;
	}


	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}


	public Collection<Project> getLeadedProjects() {
		return this.leadedProjects;
	}


	public void addLeadedProjects(Project leadedProject) {
		getLeadedProjects().add(leadedProject);
	}
	
	public void setLeadedProjects(Collection<Project> leadedProjects) {
		this.leadedProjects = leadedProjects;
	}
	
	public void removeLeadedProjects(Project leadedProject) {
		getLeadedProjects().remove(leadedProject);
	}


	public Collection<Project> getMemberProjects() {
		return memberProjects;
	}


	public void addMemberProjects(Project memberProject) {
		getMemberProjects().add(memberProject);
	}
	
	public void setMemberProjects(Collection<Project> memberProjects) {
		this.memberProjects = memberProjects;
	}
	
	public void removeMemberProjects(Project memberProject) {
		getMemberProjects().remove(memberProject);
	}


	public Collection<Project> getFinishedProjects() {
		return finishedProjects;
	}


	public void addFinishedProjects(Project finishedProject) {
		getFinishedProjects().add(finishedProject);
	}


	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Collection<Task> getTasks(){
		return this.tasks;
	}
	public void addTask(Task task) {
		this.tasks.add(task);
	}
	public void removeTask(Task task) {
		this.tasks.remove(task);
	}
	
}
