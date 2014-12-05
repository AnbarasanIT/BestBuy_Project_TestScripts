/**
 * 
 */
package com.bestbuy.search.merchandising.dao;

import java.util.Collection;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;

/**
 * Interface that defines the Base structure of the dao layer
 * @author Lakshmi.valluripalli
 */
public interface IBaseDAO<K, E> {
	
	/**
	 * Method to retrieve data based on primaryKey
	 * @param K primaryKeyId
	 * @return E entity
	 * @throws DataAcessException
	 */
	public E retrieveById(K primaryKeyId) throws DataAcessException;
	
	/**
	 * Method to retrieve all the Data available in the DB
	 * @return Collection<E>  entities
	 * @throws DataAcessException
	 */
	public Collection<E> retrieveAll() throws DataAcessException;
	
	/**
	 * Method to Save a Single Entity
	 * @param entity
	 * @return
	 * @throws DataAcessException
	 */
	public E save(E entity) throws DataAcessException;
	
	/**
	 * Method to save the entity Collection 
	 * @param entities
	 * @throws DataAcessException
	 */
	public void save(Collection<E> entities) throws DataAcessException;
	
	/**
	 * Method to update the single Entity
	 * @param entity
	 * @return E entity persisted to DB
	 * @throws DataAcessException
	 */
	public E update(E entity) throws DataAcessException;
	
	/**
	 * Method to update the Entity Collection
	 * @param entity
	 * @return E
	 * @throws DataAcessException
	 */
	public void update(Collection<E> entities) throws DataAcessException;
	
	/**
	 * Method to delete the Entity 
	 * @param primaryKeyId
	 * @param entity
	 * @return E entity
	 * @throws DataAcessException
	 */
	public E delete(E entity) throws DataAcessException;
	
	/**
	 * Method to delete the Entity 
	 * @param primaryKeyId
	 * @param entity
	 * @return E entity
	 * @throws DataAcessException
	 */
	public E delete(K primaryKeyId, E entity) throws DataAcessException;
	
	/**
	 * This method creates the Query depending onthe Criteria
	 * @param criteria
	 * @return
	 * @throws DataAcessException
	 */
	public Collection<E> executeQuery(SearchCriteria criteria) throws DataAcessException;
	
	/**
	 * Count Query on Criteria
	 * @return
	 * @throws DataAcessException
	 */
	public Long getCount(SearchCriteria criteria) throws DataAcessException;
}
