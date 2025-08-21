package kz.railways.passp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kz.railways.passp.entity.Region;

public interface RegionRepository extends JpaRepository<Region, Integer>{
	Region findByCode(int id);
	List<Region> findByActive(int id);
}
