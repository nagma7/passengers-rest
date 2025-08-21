package kz.railways.passp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kz.railways.passp.entity.Station;

public interface StationRepository extends JpaRepository<Station, Integer>{
	@Query(value="SELECT * FROM NSI_LAYER.TN_STAN ts WHERE ts.KOD_OTD_DOR = :nod AND ts.KOD_DOR = 68", nativeQuery = true)
	List<Station> findStationsByRegion(@Param("nod") int regionId);
	
	@Query(value="SELECT DISTINCT(vpt.STAN_OP) AS kod_stan, "
			+ "	ts1.NAIM_STAN,  "
			+ "	1 AS mnem_stan,  "
			+ "	1 AS kod_otd_dor FROM W_LAYER.VIEW_P_TEKN vpt  "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts1 ON TS1.KOD_STAN = vpt.STAN_OP  "
			+ "	WHERE vpt.NOD_DIS = :nod AND vpt.nom_poezd BETWEEN 1 and 1000 and "
			+ "		vpt.kop_pmd IN ('01','11','31','51','02','10','22','20','42','62') AND "
			+ "		vpt.DATE_OP BETWEEN :b_date_op AND :e_date_op and "			
			+ "		vpt.INDEX_POEZD LIKE '000000%' ORDER BY vpt.STAN_OP", nativeQuery = true)
	List<Station> findStationByRegion(@Param("nod") int regionId,
			@Param("b_date_op") String bDateOp,
			@Param("e_date_op") String eDateOp);
}
