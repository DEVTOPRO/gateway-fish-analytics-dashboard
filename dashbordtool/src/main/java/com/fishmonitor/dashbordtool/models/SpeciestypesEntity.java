package com.fishmonitor.dashbordtool.models;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="species_types")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpeciestypesEntity{
	@Column(name="id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="species_name")
	private String speciesKey;
	
	@Column(name="species_value")
	private String speciesValue;
	
    @Column(name="created_time")
    @CreationTimestamp
    private Date createdTime;

    @Column(name = "updated_time")
    @UpdateTimestamp
    private Date updatedTime;

    @Column(name="status")
    private Integer status;
	
}