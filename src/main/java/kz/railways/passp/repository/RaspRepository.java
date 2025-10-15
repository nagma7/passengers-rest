package kz.railways.passp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import kz.railways.passp.entity.RaspData;

public interface RaspRepository extends CrudRepository<RaspData, String>{
	@Query(value="SELECT trp.VREM_PRIB, trp.VREM_OTPR  FROM NSI_LAYER.TN_RASP_PASS trp "
			+ "	WHERE  "
			+ "		trp.KOD_STAN = :stan_op AND"
			+ "		trp.NOM_POEZD = :nom_poezd AND "			
			+ "		trp.KOD_VID_ST_PRIB = :prib", nativeQuery = true)
	RaspData findRaspPribData(
			@Param("stan_op") int stanOp,
			@Param("nom_poezd") String nomPoezd,
			@Param("prib") int prib);
	
	@Query(value="SELECT trp.VREM_PRIB, trp.VREM_OTPR  FROM NSI_LAYER.TN_RASP_PASS trp "
			+ "	WHERE  "
			+ "		trp.KOD_STAN = :stan_op AND"
			+ "		trp.NOM_POEZD = :nom_poezd AND "			
			+ "		trp.KOD_VID_ST_OTPR = :otpr", nativeQuery = true)
	RaspData findRaspOtprData(
			@Param("stan_op") int stanOp,
			@Param("nom_poezd") String nomPoezd,
			@Param("otpr") int otpr);
	
	@Query(value="SELECT trp.VREM_PRIB, trp.VREM_OTPR  FROM NSI_LAYER.TN_RASP_PASS trp "
			+ "	WHERE  "
			+ "		trp.KOD_STAN = :stan_op AND"
			+ "		trp.NOM_POEZD = :nom_poezd ", nativeQuery = true)
	List<RaspData> findRaspLastOperDataList(
			@Param("stan_op") int stanOp,
			@Param("nom_poezd") String nomPoezd);
}