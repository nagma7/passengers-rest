package kz.railways.passp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import kz.railways.passp.entity.PTeknData;

public interface TabloRepository extends CrudRepository<PTeknData, Long>{
	@Query(value="SELECT ts1.naim_stan AS stan1, ts2.naim_stan AS stan2, ts3.naim_stan AS name_stan_op, pt.NOM_POEZD, pt.DATE_OP, date(pt.DATE_OP) AS date_op_m, time(pt.DATE_OP) AS time_op, pt.NAPR_POSL, pt.INDEX_POEZD, pt.KDS_T, pt.KOD_PRICH_BROS, pt.KOP_PMD, pt.stan_op FROM "
			+ "	W_LAYER.VIEW_P_TEKN pt "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts1 ON TS1.KOD_STAN = pt.KSNM_POEZD "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts2 ON TS2.KOD_STAN = pt.KSKM_POEZD "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts3 ON TS3.KOD_STAN = pt.STAN_OP "
			+ "	WHERE "
			+ "		pt.nom_poezd BETWEEN 1 and 1000 and "
			+ "		pt.kop_pmd IN ('01','11','31','51') AND "
			+ "		pt.INDEX_POEZD LIKE '000000%' AND "
			+ "		pt.stan_op= :stan_op AND "
			+ "		pt.DATE_OP BETWEEN :b_date_op AND :e_date_op "
			+ "	ORDER BY pt.DATE_OP desc", nativeQuery = true)
	List<PTeknData> getPribData(@Param("stan_op") int stanOp,
			@Param("b_date_op") String bDateOp,
			@Param("e_date_op") String eDateOp);
	
	@Query(value="SELECT ts1.naim_stan AS stan1, ts2.naim_stan AS stan2, ts3.naim_stan AS name_stan_op, pt.NOM_POEZD, pt.DATE_OP, date(pt.DATE_OP) AS date_op_m, time(pt.DATE_OP) AS time_op, pt.NAPR_POSL, pt.INDEX_POEZD, pt.KDS_T, pt.KOD_PRICH_BROS, pt.KOP_PMD, pt.stan_op  FROM "
			+ "	W_LAYER.VIEW_P_TEKN pt "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts1 ON TS1.KOD_STAN = pt.KSNM_POEZD "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts2 ON TS2.KOD_STAN = pt.KSKM_POEZD "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts3 ON TS3.KOD_STAN = pt.STAN_OP "
			+ "	WHERE "
			+ "		pt.nom_poezd BETWEEN 1 and 1000 and "
			+ "		pt.kop_pmd IN ('02','10','22','42','62') AND "
			+ "		pt.INDEX_POEZD LIKE '000000%' AND "
			+ "		pt.stan_op= :stan_op AND "
			+ "		pt.DATE_OP BETWEEN :b_date_op AND :e_date_op "
			+ "	ORDER BY pt.DATE_OP desc", nativeQuery = true)
	List<PTeknData> getOtprData(@Param("stan_op") int stanOp,
			@Param("b_date_op") String bDateOp,
			@Param("e_date_op") String eDateOp);
	
	@Query(value="SELECT ts1.naim_stan AS stan1, ts2.naim_stan AS stan2, ts3.naim_stan AS name_stan_op, pt.NOM_POEZD, pt.DATE_OP, date(pt.DATE_OP) AS date_op_m, time(pt.DATE_OP) AS time_op, pt.NAPR_POSL, pt.INDEX_POEZD, pt.KDS_T, pt.KOD_PRICH_BROS, pt.KOP_PMD, pt.stan_op FROM "
			+ "	W_LAYER.VIEW_P_TEKN pt "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts1 ON TS1.KOD_STAN = pt.KSNM_POEZD "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts2 ON TS2.KOD_STAN = pt.KSKM_POEZD "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts3 ON TS3.KOD_STAN = pt.STAN_OP "			
			+ "	WHERE "			
			+ "		pt.kop_pmd IN ('01','11','31','51','02','10','22','20','42','62') AND "
			+ "		pt.INDEX_POEZD LIKE :ind_poezd AND "
			+ "		pt.DATE_OP = (SELECT max(vpt2.DATE_OP) FROM W_LAYER.VIEW_P_TEKN vpt2 WHERE vpt2.INDEX_POEZD LIKE :ind_poezd and "
			+ "				vpt2.kop_pmd IN ('01','11','31','51','02','10','22','20','42','62')) "
			+ "	ORDER BY pt.DATE_OP desc", nativeQuery = true)
	PTeknData getLastOperData(@Param("ind_poezd") String indPoezd);
	
	@Query(value="SELECT ts1.naim_stan AS stan1, ts2.naim_stan AS stan2, ts3.naim_stan AS name_stan_op, pt.NOM_POEZD, pt.DATE_OP, date(pt.DATE_OP) AS date_op_m, time(pt.DATE_OP) AS time_op, pt.NAPR_POSL, pt.INDEX_POEZD, pt.KDS_T, pt.KOD_PRICH_BROS, pt.KOP_PMD, pt.stan_op FROM "
			+ "	W_LAYER.VIEW_P_TEKN pt "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts1 ON TS1.KOD_STAN = pt.KSNM_POEZD "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts2 ON TS2.KOD_STAN = pt.KSKM_POEZD "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts3 ON TS3.KOD_STAN = pt.STAN_OP "
			+ "	WHERE "			
			+ "		pt.kop_pmd IN ('01','11','31','51','02','10','22','20','42','62') AND "
			+ "		pt.INDEX_POEZD = :ind_poezd "
			+ "	ORDER BY pt.DATE_OP asc", nativeQuery = true)
	List<PTeknData> getPoezdByIndexData(@Param("ind_poezd") String indPoezd);
	
	@Query(value="SELECT ts1.naim_stan AS stan1, ts2.naim_stan AS stan2, pt.NOM_POEZD, pt.DATE_OP, date(pt.DATE_OP) AS date_op_m, time(pt.DATE_OP) AS time_op, pt.NAPR_POSL, pt.INDEX_POEZD, pt.KDS_T, pt.KOD_PRICH_BROS, pt.KOP_PMD, pt.stan_op FROM "
			+ "	W_LAYER.VIEW_P_TEKN pt "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts1 ON TS1.KOD_STAN = pt.KSNM_POEZD "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts2 ON TS2.KOD_STAN = pt.KSKM_POEZD "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts3 ON TS3.KOD_STAN = pt.STAN_OP "			
			+ "	WHERE "			
			+ "		pt.kop_pmd IN ('01','11','31','51','02','10','22','20','42','62') AND "
			+ "		pt.INDEX_POEZD LIKE :ind_poezd AND "
			+ "		pt.DATE_OP = (SELECT min(vpt2.DATE_OP) FROM W_LAYER.VIEW_P_TEKN vpt2 WHERE vpt2.INDEX_POEZD LIKE :ind_poezd and "
			+ "				vpt2.kop_pmd IN ('01','11','31','51','02','10','22','20','42','62')) "
			+ "	ORDER BY pt.DATE_OP desc", nativeQuery = true)
	PTeknData getFirstOperData(@Param("ind_poezd") String indPoezd);
	
	@Query(value="SELECT ts1.naim_stan AS stan1, ts2.naim_stan AS stan2, ts3.naim_stan AS name_stan_op, pt.NOM_POEZD, pt.DATE_OP, date(pt.DATE_OP) AS date_op_m, time(pt.DATE_OP) AS time_op, pt.NAPR_POSL, pt.INDEX_POEZD, pt.KDS_T, pt.KOD_PRICH_BROS, pt.KOP_PMD, pt.stan_op FROM "
			+ "	W_LAYER.VIEW_P_TEKN pt "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts1 ON TS1.KOD_STAN = pt.KSNM_POEZD "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts2 ON TS2.KOD_STAN = pt.KSKM_POEZD "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts3 ON TS3.KOD_STAN = pt.STAN_OP "			
			+ "	WHERE "			
			+ "		pt.INDEX_POEZD = :ind_poezd and "
			+ "		pt.kop_pmd IN ('01','11','31','51') AND "
			+ "		pt.NAPR_POSL = :napr_posl", nativeQuery = true)
	PTeknData getNextStanPrib(@Param("ind_poezd") String indPoezd, @Param("napr_posl") int naprPosl);
	
	@Query(value="SELECT ts1.naim_stan AS stan1, ts2.naim_stan AS stan2, ts3.naim_stan AS name_stan_op, pt.NOM_POEZD, pt.DATE_OP, date(pt.DATE_OP) AS date_op_m, time(pt.DATE_OP) AS time_op, pt.NAPR_POSL, pt.INDEX_POEZD, pt.KDS_T, pt.KOD_PRICH_BROS, pt.KOP_PMD, pt.stan_op FROM "
			+ "	W_LAYER.VIEW_P_TEKN pt "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts1 ON TS1.KOD_STAN = pt.KSNM_POEZD "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts2 ON TS2.KOD_STAN = pt.KSKM_POEZD "
			+ "	LEFT JOIN NSI_LAYER.TN_STAN ts3 ON TS3.KOD_STAN = pt.STAN_OP "			
			+ "	WHERE "			
			+ "		pt.INDEX_POEZD = :ind_poezd and "
			+ "		pt.kop_pmd IN ('02','10','22','20','42','62') AND "
			+ "		pt.NAPR_POSL = :napr_posl", nativeQuery = true)
	PTeknData getNextStanOtpr(@Param("ind_poezd") String indPoezd, @Param("napr_posl") int naprPosl);
}
