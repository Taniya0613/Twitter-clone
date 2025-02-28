package com.taniya.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String fullName;
    private String location;
    private String website;
    private String birthDate;
    private String email;
    private String password;
    private String mobile;
    private String image;
    private String backgroundImage;
    private String bio;
    private boolean req_user;
    private boolean login_with_google;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Twit> twit = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @Embedded
    private Verification verification;

    @JsonIgnore
    @ManyToMany
    private List<User> followers = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    private List<User> following = new ArrayList<>();
    
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

	public void setFullName(String fullName2) {
		// TODO Auto-generated method stub
		
	}

	public void setEmail(String email2) {
		// TODO Auto-generated method stub
		
	}

	public void setPassword(String encode) {
		// TODO Auto-generated method stub
		
	}

	public void setVerification(Verification verification2) {
		// TODO Auto-generated method stub
		
	}

	public void setBirthDate(String birthDate2) {
		// TODO Auto-generated method stub
		
	}

	public void setFullName1(String fullName2) {
		// TODO Auto-generated method stub
		
	}

	public void setPassword1(String encode) {
		// TODO Auto-generated method stub
		
	}

	public void setEmail1(String email2) {
		// TODO Auto-generated method stub
		
	}

	public Object getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getBackgroundImage() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> getFollowers() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> getFollowings() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object isLogin_with_google() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getBio() {
		// TODO Auto-generated method stub
		return null;
	}

    
}
