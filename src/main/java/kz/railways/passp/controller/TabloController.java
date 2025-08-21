package kz.railways.passp.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import kz.railways.passp.entity.Poezd;
import kz.railways.passp.entity.Station;
import kz.railways.passp.service.StationService;
import kz.railways.passp.service.TabloService;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
public class TabloController {
	
	@Autowired
	TabloService tabloService;
	
	@Autowired
	StationService stationService;
	
	/*@PostMapping(path= "/api/getnodopozddata")
	public List<List<Poezd>> getNodOpozdData(int nod, String date) throws ParseException{
		return tabloService.getNodOpozdData(nod, date);
    }*/
	
	@PostMapping(path= "/api/mainpagedata")
	public Map<String, Object> getMainPageAll(int nod, String date){
		return tabloService.getMainPageTabloData(nod, date);
    }
	
	@PostMapping(path= "/api/gettablopribdata")
	public List<Poezd> getTabloPribData(int codeSt, String date, int hour) throws ParseException{
		return tabloService.getTabloPribData(codeSt, date, hour);
    }
	
	@PostMapping(path= "/api/getstationlist")
	public List<Station> getStationListData(int nod, String date){
		return stationService.getAllStationsByRegionId(nod, date);
    }
	
	@PostMapping(path= "/api/getpoezdlist")
	public Poezd getTabloHistoryData(String indPoezd) throws ParseException {
		return tabloService.getTabloHistoryData(indPoezd);
	}
}