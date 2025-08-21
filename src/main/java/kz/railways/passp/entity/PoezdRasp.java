package kz.railways.passp.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PoezdRasp {
	private String nomPoezd;
	private String namePoezd;
	private String indPoezd;
	private List<Station> station;
	private String vremPribPlan;
	private String vremPribFakt;
	private String vremPribOpozd;
	private boolean pribOpozd;
	private String vremOtprPlan;
	private String vremOtprFakt;
	private String vremOtprOpozd;
	private boolean otprOpozd;
	private String status;
	private String currentDisl;
	private Prichina prichinaBros;
	private String lastOperStan;
	private int lastOper;
	private String lastOperTime;
	private String perevozchik;
	private String filial;
}
