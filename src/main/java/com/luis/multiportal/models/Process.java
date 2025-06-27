package com.luis.multiportal.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "processing_summary")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "total_records")
    private Integer totalRecords;

    @Column(name = "female_count")
    private Integer femaleCount;

    @Column(name = "male_count")
    private Integer maleCount;

    @Column(name = "average_age_female", scale = 2)
    private Double averageAgeFemale;

    @Column(name = "average_age_male", scale = 2)
    private Double averageAgeMale;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;


}

