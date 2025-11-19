package com.dev.anh.course.model;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.dev.anh.course.api.output.PageInfo;
import com.dev.anh.course.api.output.PageResult;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

public class BaseRepositoryImpl<T,ID> extends SimpleJpaRepository<T, ID> implements BaseRespository<T, ID>{

	private EntityManager entityManager;
	
	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public <R> List<R> search(Function<CriteriaBuilder, CriteriaQuery<R>> queryFunc) {
		
		var criteriaQuery = queryFunc.apply(entityManager.getCriteriaBuilder());
		var query = entityManager.createQuery(criteriaQuery);
		
		return query.getResultList();
	}

	@Override
	public <R> PageResult<R> search(Function<CriteriaBuilder, CriteriaQuery<R>> queryFunc,
			Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc, int page, int size) {
		
		Long count = entityManager.createQuery(countFunc.apply(entityManager.getCriteriaBuilder()))
								  .getSingleResult();
		
		
		
		var criteriaQuery = queryFunc.apply(entityManager.getCriteriaBuilder());
		var query = entityManager.createQuery(criteriaQuery);
		query.setFirstResult(page * size); // default value of page is zero e.g start point = 0*10(size10)  => 0 
		query.setMaxResults(size); // default max size  = 10  it starts from 0 to 10
		
		var contents = query.getResultList();
		
		return new PageResult<R>(contents, new PageInfo(page,size, count.intValue()));
	}

	@Override
	public <R> Optional<R> searchOne(Function<CriteriaBuilder, CriteriaQuery<R>> queryFunc) {
		
		var criteriaQuery = queryFunc.apply(entityManager.getCriteriaBuilder());
		var query = entityManager.createQuery(criteriaQuery);
		
		return Optional.ofNullable(query.getSingleResult());
	}

}
