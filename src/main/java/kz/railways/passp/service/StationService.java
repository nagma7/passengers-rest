package kz.railways.passp.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kz.railways.passp.entity.Station;
import kz.railways.passp.repository.StationRepository;

@Service
public class StationService {

	@Autowired
	StationRepository stationRepository;
	
	public List<Station> getAllStationsByRegionId(int regionId, String dateOp){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		Date today = Calendar.getInstance().getTime();
		return stationRepository.findStationByRegion(regionId, dateOp + " 20:00:00", df.format(today));
	}
}
