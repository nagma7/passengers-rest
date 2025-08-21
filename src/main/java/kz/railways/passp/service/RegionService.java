package kz.railways.passp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kz.railways.passp.entity.Region;
import kz.railways.passp.repository.RegionRepository;


@Service
public class RegionService {
	
	@Autowired
	RegionRepository regionRepository;
	
	public List<Region> getAllRegionList(){
		return regionRepository.findAll();
	}
	
	public List<Region> getAllRegionByActiveList(){
		return regionRepository.findByActive(1);
	}
	
	public Region getRegion(int id){
		return regionRepository.findByCode(id);
	}
}
