package kz.railways.passp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class RaspData {
	@Id
	@Column(name = "vrem_prib")
	private String vremPrib;
	
	@Column(name = "vrem_otpr")
	private String vremOtpr;
}
