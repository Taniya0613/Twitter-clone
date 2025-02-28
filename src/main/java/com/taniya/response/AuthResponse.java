package com.taniya.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor  // ✅ Auto-generates constructor with all fields
public class AuthResponse {
    
    private String token;
    private boolean status;
}
