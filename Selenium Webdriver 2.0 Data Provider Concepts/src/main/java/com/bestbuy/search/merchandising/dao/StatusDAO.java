/**
 * 
 */
package com.bestbuy.search.merchandising.dao;

import java.util.List;

import javax.persistence.Query;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.domain.Status;

/**
 * CRUD operations for StatusDAO
 * @author a1003132
 */
public class StatusDAO extends BaseDAO<Long,Status> implements IStatusDAO {

	
	/**
	 * Gets the status id for the given status name
	 * @param name The name of the status
	 * @return The id of the status
	 * @author Ramiro.Serrato
	 */
	public Long getStatusId(String name) throws DataAcessException {
		try {
			String jpql = "SELECT s.statusId FROM Status s WHERE s.status = '" + name + "'";
			Query query = entityManager.createQuery(jpql);
			
			@SuppressWarnings("unchecked")
			List<Long> results = query.getResultList();
			
			return results.get(0);
		} 
		
		catch (RuntimeException e) {
			throw new DataAcessException("The status id could not be resolved for the status name[" + name + "]", e);
		}
	}
}
