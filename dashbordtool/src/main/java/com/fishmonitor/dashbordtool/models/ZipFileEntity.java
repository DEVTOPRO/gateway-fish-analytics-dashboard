package com.fishmonitor.dashbordtool.models;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data
@NoArgsConstructor
@Table(name = "file_Repo")
public class ZipFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user_id")
    private String userId;
    private String originalFilename; 
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] content;
    
}


