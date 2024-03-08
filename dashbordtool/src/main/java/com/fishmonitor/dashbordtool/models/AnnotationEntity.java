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
@Table(name = "annotation_store")
public class AnnotationEntity {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    @Column(name="user_id")
	    private String userId;
	    @Column(name="xml_file_name")
	    private String xmlFileName; 
	    
	    @Column(name="image_file_name")
	    private String imageFileName;
	    
	    @Column(name="species_name")
	    private String typeOfSpecies;
	    
	    @Lob
	    @Column(name="media_file", columnDefinition = "LONGBLOB")
	    private byte[] mediaFile;
	    
	    @Lob
	    @Column(name="xml_file",columnDefinition = "BLOB")
	    private byte[] xmlFile;
	    
	    @Column(name="created_time")
	    @CreationTimestamp
	    private Date createdTime;

	    @Column(name = "updated_time")
	    @UpdateTimestamp
	    private Date updatedTime;
}
