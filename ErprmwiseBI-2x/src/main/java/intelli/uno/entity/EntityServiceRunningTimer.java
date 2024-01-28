package intelli.uno.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
@Data
@Entity
@Table(name="servicerunner_sr")
public class EntityServiceRunningTimer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_sr")
	private Long id;
	
	private String serviceNameSr;
	
	private String serviceTimerSr;
	
	private String deleteFlagSr;	
	
	@CreationTimestamp
	@Column(name="createdatetime_sr")
	private LocalDateTime createdatetimSr;
	
	@UpdateTimestamp
	@Column(name="changedatetime_sr")
	private LocalDateTime changedDatedSr;
	
}
