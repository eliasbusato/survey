package com.ebusato.survey.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ebusato.survey.persistence.entity.Lead;

@Repository
public interface LeadRepository extends PagingAndSortingRepository<Lead, Long>, QueryDslPredicateExecutor<Lead> {
	
	public Page<Lead> findByEngagedFalse(Pageable pageable);

}
