package kz.railways.passp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="TN_STAN", schema = "NSI_LAYER")
public class Station {
	@Id
	@Column(name="KOD_STAN")
	private int codeStation;
	@Column(name="MNEM_STAN")
	private String shortName;
	@Column(name="NAIM_STAN")
	private String longName;
	@Column(name="KOD_OTD_DOR")
	private int regionId;
	
	@Transient
	private String vremPribPlan;
	@Transient
	private String vremPribFakt;
	@Transient
	private String vremPribOpozd;
	@Transient
	private Prichina prichinaBrosPrib;
	@Transient
	private String vremOtprPlan;
	@Transient	
	private String vremOtprFakt;
	@Transient
	private String vremOtprOpozd;
	@Transient
	private Prichina prichinaBrosOtpr;
}
