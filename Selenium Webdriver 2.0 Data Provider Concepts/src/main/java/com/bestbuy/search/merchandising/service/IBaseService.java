/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import java.util.Collection;
import java.util.List;

import com.bestbuy.search.merchandising.common.SearchCriteria;
import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.wrapper.KeyValueWrapper;

/**
 * Interface that defines the Base structure of the service layer
 * @author Lakshmi Valluripalli
 */
public interface IBaseService<K,E> {
	
	/**
	 * Method to retrieve data based on primaryKey
	 * @param K primaryKeyId
	 * @return E entity
	 * @throws ServiceException
	 */
	public E retrieveById(K primaryKeyId) throws ServiceException;
	
	/**
	 * Method to retrieve all the Data available in the DB
	 * @return Collection<E>  entities
	 * @throws ServiceException
	 */
	public Collection<E> retrieveAll() throws ServiceException;
	
	/**
	 * Method to Save a Single Entity
	 * @param entity
	 * @return
	 * @throws ServiceException
	 */
	public E save(E entity) throws ServiceException;
	
	/**
	 * Method to save the entity Collection 
	 * @param entities
	 * @throws ServiceException
	 */
	public void save(Collection<E> entities) throws ServiceException;
	
	/**
	 * Method to update the single Entity
	 * @param entity
	 * @return E entity persisted to DB
	 * @throws ServiceException
	 */
	public E update(E entity) throws ServiceException;
	
	/**
	 * Method to update the Entity collection
	 * @param entity collection
	 * 
	 */
	public void update(Collection<E> entities) throws ServiceException;
	
	/**
	 * Method to delete the Entity
	 * @param entity
	 * @return E
	 * @throws ServiceException
	 */
	public E delete(E entity) throws ServiceException;
	
	/**
	 * Method to delete the Entity 
	 * @param primaryKeyId
	 * @param entity
	 * @return E entity
	 * @throws ServiceException
	 */
	public E delete(K primaryKeyId, E entity) throws ServiceException;
	
	/**
	 * Method to load the objects depending on Search Criteria
	 * @param criteria
	 * @return
	 * @throws ServiceException
	 */
	public Collection<E> executeQuery(SearchCriteria criteria) throws ServiceException;
	
	/**
	 * Count Query on Criteria
	 * @return
	 * @throws DataAcessException
	 */
	public Long getCount(SearchCriteria criteria) throws ServiceException;
	
	public List<E> loadEntitiesWithCriteria(SearchCriteria criteria) throws DataAcessException;
	
	/**
	 * 
	 * Checks if any record exists with the Specified KeyValueWrapper in the database by calling the baseDao
	 * 
	 * @param KeyValueWrapper
	 * @return
	 * @throws ServiceException
	 */
	public boolean isExists(KeyValueWrapper KeyValueWrapper, boolean isCreate) throws ServiceException ;
	
	
}
