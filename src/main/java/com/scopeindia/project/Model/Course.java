package com.scopeindia.project.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Courses")
public class Course {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
		@Column(name="Course")
    private String name;
		@Column(name="fees")
		private int fees;
	public int getFees() {
			return fees;
		}
		public void setFees(int fees) {
			this.fees = fees;
		}
	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Course(Long id, String name,int fees) {
		super();
		this.id = id;
		this.name = name;
		this.fees=fees;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
}
