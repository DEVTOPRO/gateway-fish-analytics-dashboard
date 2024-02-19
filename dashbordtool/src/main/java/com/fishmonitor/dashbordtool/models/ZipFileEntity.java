package com.fishmonitor.dashbordtool.models;

import jakarta.persistence.*;

@Entity
public class ZipFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalFilename;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }
    public String getOriginalFilename() {
        return originalFilename;
    }
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] content;


}
