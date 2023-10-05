package com.cdn.smartcdn.dto.response;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HealthResponse {
    private String version = "1.0.0";
    private String status = "RUNNING";
}
