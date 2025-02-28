package com.taniya.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;  // ✅ Added missing import

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Twit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private User user;

    @SuppressWarnings("unused")
	private String content;
    @SuppressWarnings("unused")
	private String image;
    @SuppressWarnings("unused")
	private String video;

    @OneToMany(mappedBy = "twit", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @OneToMany
    private List<Twit> replyTwits = new ArrayList<>();  // ✅ Fixed missing import issue

    @ManyToMany
    private List<User> retwitUser = new ArrayList<>();
    
    @ManyToOne
    private Twit replyFor;
    
    @SuppressWarnings("unused")
	private boolean isReply;
    @SuppressWarnings("unused")
	private boolean isTwit;
    
    private LocalDateTime createdAt;
}
