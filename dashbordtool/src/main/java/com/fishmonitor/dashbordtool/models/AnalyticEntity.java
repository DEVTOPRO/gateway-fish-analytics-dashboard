package com.fishmonitor.dashbordtool.models;
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

@Entity
@Data
@NoArgsConstructor
@Builder
@Table(name = "user_infor")
@AllArgsConstructor
public class AnalyticEntity {
		@Column(name = "id")
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
		@Column(name = "full_name")
		private String fullName;
		@Column(name = "mobile_number")
		private long mobileNumber;
		@Column(name = "mail_id")
		private String email;
}
