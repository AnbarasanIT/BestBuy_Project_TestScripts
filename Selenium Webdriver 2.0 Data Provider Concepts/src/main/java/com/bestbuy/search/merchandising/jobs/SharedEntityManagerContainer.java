package com.bestbuy.search.merchandising.jobs;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class SharedEntityManagerContainer {
	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
