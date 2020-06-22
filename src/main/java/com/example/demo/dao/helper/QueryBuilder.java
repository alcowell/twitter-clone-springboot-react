package com.example.demo.dao.helper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * @author yosuk
 *
 */
public class QueryBuilder {

	EntityManager em;

	StringBuilder queryString;

	/**
	 * List holding key of parameters.
	 */
	private List<String> paramNameList;

	/**
	 * List holding key of parameters.
	 */
	private List<Object> paramValueList;

	private Query query;

	public QueryBuilder(EntityManager em) {
		this.em = em;
		this.queryString = new StringBuilder();
		this.paramNameList = new ArrayList<String>();
		this.paramValueList = new ArrayList<Object>();
	}


	public QueryBuilder append(String inputQuery) {
		this.queryString.append(inputQuery);
		this.queryString.append(" ");
		System.out.println(inputQuery);
		return this;
	}

	public QueryBuilder setParam(String key, Object value) {
		this.paramNameList.add(key);
		this.paramValueList.add(value);
		return this;
	}

	public QueryBuilder createQuery(Class<?> entityClass) {
		this.query = this.em.createNativeQuery(this.queryString.toString(), entityClass);

		for(int i=0; i < this.paramNameList.size(); i++) {
			this.query.setParameter(this.paramNameList.get(i), this.paramValueList.get(i));
		}

		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findResultList() {
		return this.query.getResultList();
	}


	@SuppressWarnings("unchecked")
	public <T> T findSingle() {

		List<T> result = this.query.getResultList();
		if (result.isEmpty()) {
			return null;
		}

		return result.get(0);
	}

}
