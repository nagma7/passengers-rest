package kz.railways.passp.entity;

import java.sql.Timestamp;

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
public class PTeknData {	
	@Column(name = "nom_poezd")
	private String nomPoezd;
	
	@Column(name = "stan1")
	private String stan1;
	
	@Column(name = "stan2")
	private String stan2;
	
	@Column(name = "name_stan_op")
	private String nameStanOp;
	
	@Id
	@Column(name = "date_op")
	private Timestamp dateOp;
	
	@Column(name = "time_op")
	private String timeOp;
	
	@Column(name = "date_op_m")
	private String dateOpM;
	
	@Column(name = "NAPR_POSL")
	private int naprPosl;
	
	@Column(name = "KDS_T")
	private int kdsT;
	
	@Column(name = "INDEX_POEZD")
	private String indexPoezd;
	
	@Column(name = "KOD_PRICH_BROS")
	private String codePrichBros;
	
	@Column(name = "KOP_PMD")
	private int kopPmd;
	
	@Column(name = "stan_op")
	private int stanOp;
}