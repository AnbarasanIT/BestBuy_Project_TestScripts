package com.bestbuy.search.merchandising.service;

import java.text.ParseException;
import java.util.List;

import com.bestbuy.search.merchandising.common.exception.DataAcessException;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.BoostAndBlock;
import com.bestbuy.search.merchandising.workflow.exception.InvalidStatusException;
import com.bestbuy.search.merchandising.workflow.exception.WorkflowException;
import com.bestbuy.search.merchandising.wrapper.BoostAndBlockWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;

/**
 * @author Kalaiselvi Jaganathan
 * Interface for BoostAndBlock service
 */
public interface IBoostAndBlockService extends IBaseService<Long,BoostAndBlock>{

	/**
	 * Method to create BoostAndBlock
	 * @param boostAndBlockWrapper
	 * @return 
	 * @throws ServiceException
	 * @throws ParseException
	 */
	public IWrapper createBoostAndBlock(BoostAndBlockWrapper boostAndBlockWrapper) throws ServiceException, ParseException, InvalidStatusException;
	
	/**
	 * Method to update BoostAndBlock
	 * @param boostAndBlockWrapper
	 * @throws ServiceException
	 * @throws ParseException
	 */
	public IWrapper updateBoostAndBlock(BoostAndBlockWrapper boostAndBlockWrapper) throws ServiceException, ParseException, DataAcessException,InvalidStatusException,WorkflowException ;
	
	/**
	 * Method to update BoostAndBlock
	 * @param boostAndBlockId
	 * @return BoostAndBlockWrapper
	 * @throws ServiceException
	 */
	public BoostAndBlockWrapper loadEditBoostBlockData(Long boostAndBlockId) throws ServiceException;
	
	/**
	 * Method to delete BoostAndBlock
	 * @param id The boostAndBlockId for this BoostAndBlock
	 * @return IWrapper
	 * @throws ServiceException If any error occurs
	 * @author Chanchal.Kumari
	 */
	public IWrapper deleteBoostAndBlock(Long id) throws ServiceException ;
	
	/**
	 * Method to retrieve the list of Boost&Block which are not in the deleted status from DB
	 * @throws ServiceException
	 * @author Chanchal.Kumari
	 */
	public  List<IWrapper> load(PaginationWrapper paginationWrapper) throws ServiceException;
	
	/**
	 * Method to approve BoostAndBlock
	 * @param id The boostAndBlockId for this BoostAndBlock
	 * @return IWrapper
	 * @throws ServiceException If any error occurs
	 * @author Chanchal.Kumari
	 */
	public IWrapper approveBoostAndBlock(Long id) throws ServiceException ;
	
	/**
	 * Method to reject BoostAndBlock
	 * @param id The boostAndBlockId for this BoostAndBlock
	 * @return IWrapper
	 * @throws ServiceException If any error occurs
	 * @author Chanchal.Kumari
	 */
	public IWrapper rejectBoostAndBlock(Long id) throws ServiceException ;

	 /**
	 * Validates the following criteria against the DB: 
	 * 1. The combination (search term, search profile, category) should not exist in the DB
	 * otherwise it will return 1
	 * 2. The search term is not the name of any promo page otherwise it will return 2
	 * If both conditions are satisfied the method will return 0
	 * @param searchTerm The term to validate
	 * @param searchProfileId The id of the profile to validate
	 * @param categoryId The id of the category to validate
	 * @return 0, 1 or 2 depending on the result
	 * @throws DataAcessException If an error occurs while talking to the DB
	 * @author Ramiro.Serrato
	 */
	public Integer validateNewBoostAndBlock(String searchTerm, Long searchProfileId, String categoryId) throws ServiceException;
}
