package com.ebusato.survey.persistence.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ebusato.survey.persistence.entity.Survey;

@Repository
public interface SurveyRepository extends PagingAndSortingRepository<Survey, Long>, QueryDslPredicateExecutor<Survey> {
}
