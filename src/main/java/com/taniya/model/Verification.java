package com.taniya.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Verification {
    @SuppressWarnings("unused")
	private boolean status=false;
    @SuppressWarnings("unused")
	private LocalDateTime startedAt;
    @SuppressWarnings("unused")
	private LocalDateTime endsAt;
    @SuppressWarnings("unused")
	private String planType;
}