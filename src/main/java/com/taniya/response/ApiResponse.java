package com.taniya.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor  // ✅ Auto-generates constructor with all fields
public class ApiResponse {
    
    private String message;
    private boolean status;
}
