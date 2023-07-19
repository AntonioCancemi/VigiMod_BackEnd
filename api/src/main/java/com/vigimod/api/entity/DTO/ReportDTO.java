package com.vigimod.api.entity.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReportDTO {

    private Long adId;
    private Long userId;

    // private User user;
    private String message;
    private LocalDateTime createdAt;
}
