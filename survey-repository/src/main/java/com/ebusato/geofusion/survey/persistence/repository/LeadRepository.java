package com.ebusato.geofusion.survey.persistence.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.ebusato.geofusion.survey.persistence.entity.Lead;

@org.springframework.stereotype.Repository
interface LeadRepository extends Repository<Lead, Long> {
	
	Lead save(Lead entity);
	
	Optional<Lead> findOne(Long id);
	
	Optional<Lead> findByEmail(String email);

}
