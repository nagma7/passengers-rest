package kz.railways.passp.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kz.railways.passp.entity.PTeknData;
import kz.railways.passp.entity.Poezd;
import kz.railways.passp.entity.Prichina;
import kz.railways.passp.entity.RaspData;
import kz.railways.passp.entity.Station;
import kz.railways.passp.repository.RaspRepository;
import kz.railways.passp.repository.TabloRepository;

@Service
public class TabloService {

	@Autowired
	private RaspRepository raspRepository;
	@Autowired
	private TabloRepository tabloRepository;
	@Autowired
	private StationService stationService;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/*public List<List<Poezd>> getNodOpozdData(int regionId, String dateOp) throws ParseException {
		List<List<Poezd>> ans = new ArrayList<List<Poezd>>();
		entityManager.clear();
		List<Station> stationList = stationService.getAllStationsByRegionId(regionId, dateOp);
		for(Station station: stationList) {
			entityManager.clear();
			List<Poezd> poezdList = getTabloPribData(station.getCodeStation(), dateOp);
			ans.add(poezdList);
		}
		return ans;
	}*/
	
	public Map<String, Object> getMainPageTabloData(int regionId, String dateOp) {
		int count = 0;
		int countHours = 0;
		entityManager.clear();
		List<Station> stationList = stationService.getAllStationsByRegionId(regionId, dateOp);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		Date today = Calendar.getInstance().getTime();
		for(Station station: stationList) {			
			entityManager.clear();
			List<PTeknData> pTeknPribDataList = tabloRepository.getPribData(station.getCodeStation(), dateOp + " 20:00:00", df.format(today));
			for(PTeknData dataPribPT: pTeknPribDataList) {
				entityManager.clear();				
				RaspData raspPrib = raspRepository.findRaspOtprData(station.getCodeStation(), dataPribPT.getNomPoezd(), dataPribPT.getNaprPosl());
				if(raspPrib != null) { 
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
					LocalTime eventStart = LocalTime.parse(dataPribPT.getTimeOp(), formatter);
					LocalTime eventEnd = LocalTime.parse(raspPrib.getVremPrib(), formatter);
			        if(ChronoUnit.MINUTES.between(eventEnd, eventStart) > 0) { 
			        	count++;
			        	countHours += ChronoUnit.MINUTES.between(eventEnd, eventStart);
			        }
				}
			}	
			entityManager.clear();
			List<PTeknData> pTeknOtprDataList = tabloRepository.getOtprData(station.getCodeStation(), dateOp + " 20:00:00", df.format(today));
			for(PTeknData dataOtprPT: pTeknOtprDataList) {
				entityManager.clear();
				RaspData raspOtpr = raspRepository.findRaspOtprData(station.getCodeStation(), dataOtprPT.getNomPoezd(), dataOtprPT.getNaprPosl());
				if(raspOtpr != null) { 
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
					LocalTime eventStart = LocalTime.parse(dataOtprPT.getTimeOp(), formatter);
			        LocalTime eventEnd = LocalTime.parse(raspOtpr.getVremOtpr(), formatter);
			        if(ChronoUnit.MINUTES.between(eventEnd, eventStart) > 0) { 
			        	count++;
			        	countHours += ChronoUnit.MINUTES.between(eventEnd, eventStart);
			        }				
				}
			}
		}
		Map<String, Object> ans = new HashMap<String, Object>();
		if(count > 0) {
			ans.put("count", count);
			ans.put("hours", String.format("%d", countHours / 60));
			ans.put("minutes", String.format("%02d", countHours % 60));
		}
		return ans;
	}
	
	public List<Poezd> getTabloPribData(int codeSt, String dateOp, int hour) throws ParseException {
		List<Poezd> poezdList = new ArrayList<>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		Date today = Calendar.getInstance().getTime();		
		List<PTeknData> pTeknPribDataList = null;
		int h = Integer.parseInt(String.format("%-4s", hour).replace(' ', '0'));
		entityManager.clear();
		if(hour == 8) {
			System.out.println(dateOp + " 20:00:00");
			System.out.println(df.format(today));
			pTeknPribDataList = tabloRepository.getPribData(codeSt, dateOp + " 20:00:00", df.format(today));
		}
		else {
			System.out.println(df.format(new Date(System.currentTimeMillis() - 3600 * h)));
			System.out.println(df.format(today));
			pTeknPribDataList = tabloRepository.getPribData(codeSt, df.format(new Date(System.currentTimeMillis() - 3600 * h)), df.format(today));
		}
		List<PTeknData> pTeknPribDataNewList = new ArrayList<>();
		PTeknData pTeknData = null;
		String indPoezd = "";
		int sizeOfArr = pTeknPribDataList.size();
		for(PTeknData dataPribPT: pTeknPribDataList) {			
			if(pTeknData == null ) {
				pTeknData = dataPribPT;
				indPoezd = dataPribPT.getIndexPoezd();
			}else {
				if(indPoezd.equals("")) {
					indPoezd = dataPribPT.getIndexPoezd();
					pTeknData = dataPribPT;
				}else{
					if(!indPoezd.equals(dataPribPT.getIndexPoezd())) {
						pTeknPribDataNewList.add(pTeknData);
						pTeknData = dataPribPT;
						indPoezd = dataPribPT.getIndexPoezd();
					}else
						indPoezd = "";
				}
			}
			if(sizeOfArr == 1) {
				pTeknPribDataNewList.add(pTeknData);
			}
			sizeOfArr--;
		}
		entityManager.clear();
		List<PTeknData> pTeknOtprDataList = null;
		if(hour == 8)
			pTeknOtprDataList = tabloRepository.getOtprData(codeSt, dateOp + " 20:00:00", df.format(today));
		else {
			pTeknOtprDataList = tabloRepository.getOtprData(codeSt, df.format(new Date(System.currentTimeMillis() - 3600 * h)), df.format(today));
		}
		for(PTeknData dataPribPT: pTeknPribDataNewList) {
			entityManager.clear();
			RaspData raspPrib = raspRepository.findRaspPribData(codeSt, dataPribPT.getNomPoezd(), dataPribPT.getNaprPosl());
			if(raspPrib != null) {
				Poezd poezd = new Poezd();
				poezd.setNomPoezd(dataPribPT.getNomPoezd());
				poezd.setNamePoezd(dataPribPT.getStan1().trim() + " - " + dataPribPT.getStan2().trim());
				poezd.setIndPoezd(dataPribPT.getIndexPoezd());
				Prichina prichinaPrib = new Prichina();
				prichinaPrib.setCodePrichina(dataPribPT.getCodePrichBros());
				poezd.setPrichinaBrosPrib(prichinaPrib);
				poezd.setVremPribPlan(raspPrib.getVremPrib());
				poezd.setVremPribFakt(dataPribPT.getTimeOp());
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
				LocalTime eventStart = LocalTime.parse(dataPribPT.getTimeOp(), formatter);
		        LocalTime eventEnd = LocalTime.parse(raspPrib.getVremPrib(), formatter);
		        poezd.setVremPribPlan(String.format("%02d", eventEnd.getHour()) + ":" + String.format("%02d", eventEnd.getMinute()));
		        poezd.setVremPribFakt(String.format("%02d", eventStart.getHour()) + ":" + String.format("%02d", eventStart.getMinute()));
		        
		        if(eventEnd.getHour() == 23 && eventStart.getHour() == 0) {
		        	df = new SimpleDateFormat("yyyy-MM-dd");
			        SimpleDateFormat frmttr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        Date d1 = frmttr.parse(dateOp + " " + raspPrib.getVremPrib());
			        Date d2 = frmttr.parse(df.format(today) + " " + dataPribPT.getTimeOp());
			        long diffMs = d1.getTime() - d2.getTime();
			        long diffSec = diffMs / 1000;
			        long min = diffSec / 60;
			        long sec = diffSec % 60;
			        poezd.setVremPribOpozd(String.valueOf(min * -1));
		        }else {
			        Duration duration = Duration.between(eventEnd, eventStart);
					long minutes = duration.toMinutes();
		            poezd.setVremPribOpozd(String.valueOf(minutes));
		        }
	            PTeknData pTeknLastOperPribData = tabloRepository.getLastOperData(dataPribPT.getIndexPoezd());
	            poezd.setLastOperStan(pTeknLastOperPribData.getStan1());
	            poezd.setLastOper(pTeknLastOperPribData.getKopPmd());
	            
	            //List<RaspData> raspLastList = raspRepository.findRaspLastOperDataList(pTeknLastOperPribData.getStanOp(), pTeknLastOperPribData.getNomPoezd());
	            RaspData raspLastPrib = raspRepository.findRaspPribData(pTeknLastOperPribData.getStanOp(), pTeknLastOperPribData.getNomPoezd(), pTeknLastOperPribData.getNaprPosl());
	            if(raspLastPrib == null) {
	            	raspLastPrib = raspRepository.findRaspOtprData(pTeknLastOperPribData.getStanOp(), pTeknLastOperPribData.getNomPoezd(), pTeknLastOperPribData.getNaprPosl());
	            }
	            formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	            eventStart = LocalTime.parse(raspLastPrib.getVremPrib(), formatter);
	            poezd.setLastOperTime(String.format("%02d", eventStart.getHour()) + ":" + String.format("%02d", eventStart.getMinute()));
	            
	            
				for(PTeknData pTekn: pTeknOtprDataList){
					if(dataPribPT.getIndexPoezd().equals(pTekn.getIndexPoezd())) {
						Prichina prichinaOtpr = new Prichina();
						prichinaOtpr.setCodePrichina(pTekn.getCodePrichBros());
						poezd.setPrichinaBrosOtpr(prichinaOtpr);
						entityManager.clear();
						RaspData raspOtpr = raspRepository.findRaspOtprData(codeSt, pTekn.getNomPoezd(), pTekn.getNaprPosl());
						poezd.setVremOtprPlan(raspOtpr.getVremOtpr());
						poezd.setVremOtprFakt(pTekn.getTimeOp());
						DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
						LocalTime eventStart1 = LocalTime.parse(pTekn.getTimeOp(), formatter1);
				        LocalTime eventEnd1 = LocalTime.parse(raspOtpr.getVremOtpr(), formatter1);
				        poezd.setVremOtprPlan(String.format("%02d", eventEnd1.getHour()) + ":" + String.format("%02d", eventEnd1.getMinute()));
				        poezd.setVremOtprFakt(String.format("%02d", eventStart1.getHour()) + ":" + String.format("%02d", eventStart1.getMinute()));
				        
				        if(eventEnd1.getHour() == 23 && eventStart1.getHour() == 0) {
				        	df = new SimpleDateFormat("yyyy-MM-dd");
				        	SimpleDateFormat frmttr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					        Date d11 = frmttr.parse(dateOp + " " + raspOtpr.getVremOtpr());
					        Date d12 = frmttr.parse(df.format(today) + " " + pTekn.getTimeOp());			        
					        
					        long diffMs = d11.getTime() - d12.getTime();
					        long diffSec = diffMs / 1000;
					        long min = diffSec / 60;
					        long sec = diffSec % 60;
					        poezd.setVremOtprOpozd(String.valueOf(min * -1));
				        }else {Duration duration1 = Duration.between(eventEnd1, eventStart1);
							long minutes1 = duration1.toMinutes();
				            poezd.setVremOtprOpozd(String.valueOf(minutes1));
				        }
			            
			            PTeknData pTeknLastOperOtprData = tabloRepository.getLastOperData(pTekn.getIndexPoezd());
			            if(pTeknLastOperOtprData != null) {
				            poezd.setLastOperStan(pTeknLastOperOtprData.getStan1());
				            poezd.setLastOper(pTeknLastOperOtprData.getKopPmd());
				            RaspData raspLastOtpr = raspRepository.findRaspOtprData(pTeknLastOperOtprData.getStanOp(), pTeknLastOperOtprData.getNomPoezd(), pTeknLastOperOtprData.getNaprPosl());
				            if(raspLastOtpr == null)
				            	raspLastOtpr = raspRepository.findRaspPribData(pTeknLastOperOtprData.getStanOp(), pTeknLastOperOtprData.getNomPoezd(), pTeknLastOperOtprData.getNaprPosl());
				            formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
				            eventStart1 = LocalTime.parse(raspLastOtpr.getVremOtpr(), formatter1);
				            poezd.setLastOperTime(String.format("%02d", eventStart1.getHour()) + ":" + String.format("%02d", eventStart1.getMinute()));
			            }
			            
					}
				}
				
	            poezdList.add(poezd);
			}
		}
		return poezdList;
	}
	
	
	public Poezd getTabloHistoryData(String indPoezd) throws ParseException {
		Poezd poezd = new Poezd();
		List<Station> stationList = new ArrayList<>();
		entityManager.clear();
		List<PTeknData> pTeknPribDataList = tabloRepository.getPoezdByIndexData(indPoezd);
		Station lastStan = new Station();
		Timestamp lastDate = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(PTeknData dataPribPT: pTeknPribDataList) {
			poezd.setNomPoezd(StringUtils.leftPad(dataPribPT.getNomPoezd(), 3, '0'));
			poezd.setNamePoezd(dataPribPT.getStan1().trim() + " - " + dataPribPT.getStan2().trim());
			Station station = new Station();
			System.out.println("stationname " + dataPribPT.getStanOp());
			station.setLongName(dataPribPT.getNameStanOp());
			station.setCodeStation(dataPribPT.getStanOp());
			if(lastStan == null) {
				lastStan = new Station();
				lastStan.setLongName(dataPribPT.getNameStanOp());
				lastStan.setCodeStation(dataPribPT.getStanOp());
				Prichina prichinaPrib = new Prichina();
		        System.out.println("prib cod bros 1 " + dataPribPT.getCodePrichBros());
				prichinaPrib.setCodePrichina(dataPribPT.getCodePrichBros());
				lastStan.setPrichinaBrosPrib(prichinaPrib);
			}
			if(lastDate == null)
				lastDate = dataPribPT.getDateOp();
			switch (dataPribPT.getKopPmd()) {
				case 1,11,31,51 -> {
					entityManager.clear();
					RaspData raspPrib = raspRepository.findRaspPribData(dataPribPT.getStanOp(), dataPribPT.getNomPoezd(), dataPribPT.getNaprPosl());
					if(raspPrib != null) {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
						LocalTime eventStart = LocalTime.parse(dataPribPT.getTimeOp(), formatter);
				        LocalTime eventEnd = LocalTime.parse(raspPrib.getVremPrib(), formatter);
				        lastStan.setVremPribPlan(String.format("%02d", eventEnd.getHour()) + ":" + String.format("%02d", eventEnd.getMinute()));
				        lastStan.setVremPribFakt(String.format("%02d", eventStart.getHour()) + ":" + String.format("%02d", eventStart.getMinute()));
				        Prichina prichinaPrib = new Prichina();
				        System.out.println("prib cod bros " + dataPribPT.getCodePrichBros());
						prichinaPrib.setCodePrichina(dataPribPT.getCodePrichBros());
						lastStan.setPrichinaBrosPrib(prichinaPrib);
				        if(eventEnd.getHour() == 23 && eventStart.getHour() == 0) {
				        	df = new SimpleDateFormat("yyyy-MM-dd");
					        SimpleDateFormat frmttr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					        Date d1 = frmttr.parse(df.format(lastDate) + " " + raspPrib.getVremPrib());
					        Date d2 = frmttr.parse(frmttr.format(dataPribPT.getDateOp()));
					        long diffMs = d1.getTime() - d2.getTime();
					        long diffSec = diffMs / 1000;
					        long min = diffSec / 60;
					        long sec = diffSec % 60;
					        lastStan.setVremPribOpozd(String.valueOf(min * -1));
					        
				        }else {
					        Duration duration = Duration.between(eventEnd, eventStart);
							long minutes = duration.toMinutes();
							lastStan.setVremPribOpozd(String.valueOf(minutes));
				        }
				        
					}
				}
				case 2,10,22,20,42,62 -> {
					entityManager.clear();
					RaspData raspOtpr = raspRepository.findRaspOtprData(dataPribPT.getStanOp(), dataPribPT.getNomPoezd(), dataPribPT.getNaprPosl());
					if(raspOtpr != null){
						DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
						LocalTime eventStart1 = LocalTime.parse(dataPribPT.getTimeOp(), formatter1);
				        LocalTime eventEnd1 = LocalTime.parse(raspOtpr.getVremOtpr(), formatter1);
				        station.setVremOtprPlan(String.format("%02d", eventEnd1.getHour()) + ":" + String.format("%02d", eventEnd1.getMinute()));
				        station.setVremOtprFakt(String.format("%02d", eventStart1.getHour()) + ":" + String.format("%02d", eventStart1.getMinute()));
				        Prichina prichinaOtpr = new Prichina();
				        System.out.println("otpr cod bros " + dataPribPT.getCodePrichBros());
						prichinaOtpr.setCodePrichina(dataPribPT.getCodePrichBros());
						station.setPrichinaBrosOtpr(prichinaOtpr);
				        if(eventEnd1.getHour() == 23 && eventStart1.getHour() == 0) {
				        	df = new SimpleDateFormat("yyyy-MM-dd");
					        SimpleDateFormat frmttr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					        Date d1 = frmttr.parse(df.format(lastDate) + " " + raspOtpr.getVremOtpr());
					        Date d2 = frmttr.parse(frmttr.format(dataPribPT.getDateOp()));
					        long diffMs = d1.getTime() - d2.getTime();
					        long diffSec = diffMs / 1000;
					        long min = diffSec / 60;
					        long sec = diffSec % 60;
					        station.setVremOtprOpozd(String.valueOf(min * -1));
				        }else {
				        	Duration duration1 = Duration.between(eventEnd1, eventStart1);
							long minutes1 = duration1.toMinutes();
				            station.setVremOtprOpozd(String.valueOf(minutes1));
				        }
					}
					if(lastStan != null) {
						station.setVremPribPlan(lastStan.getVremPribPlan());
						station.setVremPribFakt(lastStan.getVremPribFakt());
						station.setPrichinaBrosPrib(lastStan.getPrichinaBrosPrib());
						station.setVremPribOpozd(lastStan.getVremPribOpozd());
						station.setVremPribOpozd(lastStan.getVremPribOpozd());
					}
					stationList.add(station);
					lastStan = null;
					lastDate = dataPribPT.getDateOp();
				}
			}
			
		}
		
		if(lastStan != null) {
			stationList.add(lastStan);
		}
		
		poezd.setStation(stationList);
		return poezd;
	}
}