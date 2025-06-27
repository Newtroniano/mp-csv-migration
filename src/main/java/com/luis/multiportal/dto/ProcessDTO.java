package com.luis.multiportal.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProcessDTO {


    private Long id;
    private String fileName;
    private Integer totalRecords;
    private Integer femaleCount;
    private Integer maleCount;
    private Double averageAgeFemale;
    private Double averageAgeMale;
    private LocalDateTime processedAt;

    public ProcessDTO(
            Long id,
            String fileName,
            Integer totalRecords,
            Integer femaleCount,
            Integer maleCount,
            Double averageAgeFemale,
            Double averageAgeMale,
            LocalDateTime processedAt
    ) {
        this.id = id;
        this.fileName = fileName;
        this.totalRecords = totalRecords;
        this.femaleCount = femaleCount;
        this.maleCount = maleCount;
        this.averageAgeFemale = averageAgeFemale;
        this.averageAgeMale = averageAgeMale;
        this.processedAt = processedAt;
    }

    public Long getId() { return id; }
    public String getFileName() { return fileName; }
    public Integer getTotalRecords() { return totalRecords; }
    public Integer getFemaleCount() { return femaleCount; }
    public Integer getMaleCount() { return maleCount; }
    public Double getAverageAgeFemale() { return averageAgeFemale; }
    public Double getAverageAgeMale() { return averageAgeMale; }
    public LocalDateTime getProcessedAt() { return processedAt; }
}
