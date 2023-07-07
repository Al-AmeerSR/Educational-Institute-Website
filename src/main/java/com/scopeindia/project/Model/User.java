package com.scopeindia.project.Model;

import java.time.LocalDate;
import java.util.List;

//import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
//import org.springframework.web.multipart.MultipartFile;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="Scope_Users")
public class User {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
	    @Column(name="firstName")
	 	private String firstName;
	    @Column(name="lastName")
	    private String lastName;
	    @Column(name="gender")
	    private String gender;
	    @Column(name="dob")
	    private LocalDate dob;
	    @Column(name="email")
	    private String email;
	    @Column(name="phoneNumber")
	    private String phoneNumber;
	    @Column(name="country",nullable=true)
	    private String country;
	    @Column(name="state",nullable=true)
	    private String state;
	    @Column(name="city",nullable=true)
	    private String city;
	    @Column(name="hobbies",nullable=true)
	    private List<String> hobbies;
	    @Lob
	    @Column(name="image",nullable=true)
	    private byte[] image;
		@Column(name="course", nullable=true)
	    private String course;
	    @Column(name="pass")
	    private String pass;
	    @Column(name="otp")
	    private int otp;
	    @Column(name="Verified")
	    private boolean isVerified=false;
	public User(int id, String firstName, String lastName, String gender, LocalDate dob, String email,
				String phoneNumber, byte[] image,String country, String state, String city, List<String> hobbies,String course,String pass,int otp,boolean isVerified) {
			super();
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.gender = gender;
			this.dob = dob;
			this.email = email;
			this.phoneNumber = phoneNumber;
			this.country = country;
			this.state = state;
			this.city = city;
			this.hobbies = hobbies;
			this.image = image;
			this.course=course;
			this.pass=pass;
			this.otp=otp;
			this.isVerified=isVerified;
		}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<String> getHobbies() {
		return hobbies;
	}

	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

    public byte[] getImage() {
			return image;
		}

		public void setImage(byte[] image) {
			this.image = image;
		}

}


