/**
 * 
 */
package com.bestbuy.search.merchandising.service;

import java.util.List;

import org.json.JSONException;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.Synonym;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;
import com.bestbuy.search.merchandising.wrapper.SynonymWrapper;

/**
 * defines  Structure of the Status Service
 * @author Lakshmi Valluripalli
 */
public interface ISynonymService extends IBaseService<Long,Synonym>{
	
	/**
	 * Method to update the synonymGroup in DB
	 * @param synonymGroup
	 * @return String
	 */
	public IWrapper update(SynonymWrapper synonymWrapper) throws ServiceException;
	public IWrapper createSynonym(SynonymWrapper synonymWrapper) throws ServiceException;
	public String loadSynonym()  throws ServiceException,JSONException;
	public SynonymWrapper getSynonym(Long id) throws ServiceException;
	
	/**
	 * Deletes a synonym with the given id, it is a soft delete this means the method will just update the status of the asset to Deleted
	 * @param id A Long with the SynonymGroup id of the synonym that we want to delete
	 * @throws ServiceException
	 * @author Ramiro.Serrato
	 */
	public IWrapper deleteSynonym(Long id) throws ServiceException;
	
	/**
	 * Method to load Synonyms
	 * @throws ServiceException
	 */
	public List<Synonym> getAllSynonyms(PaginationWrapper paginationWrapper) throws ServiceException;
	
	/**
	 * Approves a synonym with the given id
	 * @param id A Long with the SynonymGroup id of the synonym that we want to approve
	 * @throws ServiceException
	 * @author Ramiro.Serrato
	 */
	public IWrapper approveSynonym(Long id) throws ServiceException;
	
	/**
	 * Rejects a synonym with the given id
	 * @param id A Long with the SynonymGroup id of the synonym that we want to reject
	 * @throws ServiceException
	 * @author Ramiro.Serrato
	 */
	public IWrapper rejectSynonym(Long id) throws ServiceException;
//	/**
//	 * Updates the status to a synonym, also logs the change in the history table
//	 * @param id The Asset id which represents the synonym to be updated
//	 * @param status The status name for the new status to be set
//	 * @throws ServiceException 
//	 * @author Ramiro.Serrato
//	 */	
//	public void updateStatusForSynonym(Long id, String status) throws ServiceException;
	
	/**
	 * Retrieves the list of synonyms from the DB
	 * @return A list of SynonymWrapper with the synonyms
	 * @throws ServiceException
	 * @author Ramiro.Serrato
	 */
	public List<SynonymWrapper> listSynonyms(PaginationWrapper paginationWrapper) throws ServiceException;
	
	/**
	 * Publishes the synomym, by changing the status to Published
	 * @param id The asset id that represents the synonym to be updated
	 * @param status - current status of the synonym
	 * @throws ServiceException
	 * @author Ramiro.Serrato
	 */
	public void publishSynonym(Long id, String status)  throws ServiceException;
	
}
