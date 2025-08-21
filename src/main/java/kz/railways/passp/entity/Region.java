package kz.railways.passp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="regions", schema = "nsi")
public class Region {
	
	@Id
	@Column(name="code")
	private Integer code;
	
	@Column(name="name")
	private String name;
	
	@Column(name="mnk")
	private String mnk;
	
	@Column(name="active")
	private Integer active;
	
	@Column(name="pr_electr")
	private Integer prElectr;
	
}
