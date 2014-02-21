package com.bestbuy.search.merchandising.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.domain.AttributeValue;
import com.bestbuy.search.merchandising.domain.Banner;
import com.bestbuy.search.merchandising.domain.Category;
import com.bestbuy.search.merchandising.domain.Context;
import com.bestbuy.search.merchandising.domain.ContextFacet;
import com.bestbuy.search.merchandising.domain.ContextFacetPK;
import com.bestbuy.search.merchandising.domain.ContextKeyword;
import com.bestbuy.search.merchandising.domain.ContextKeywordPK;
import com.bestbuy.search.merchandising.domain.Facet;
import com.bestbuy.search.merchandising.domain.SearchProfile;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.security.UserUtil;
import com.bestbuy.search.merchandising.service.IAttributeValueService;
import com.bestbuy.search.merchandising.service.ICategoryNodeService;
import com.bestbuy.search.merchandising.service.IFacetService;
import com.bestbuy.search.merchandising.service.ISearchProfileService;
import com.bestbuy.search.merchandising.wrapper.BannerWrapper;
import com.bestbuy.search.merchandising.wrapper.ContextFacetWrapper;
import com.bestbuy.search.merchandising.wrapper.ContextWrapper;

/**
 * @author Kalaiselvi Jaganathan
 * Utility to convert banner wrapper to banner entity
 */
public class BannerWrapperConverter {

	@Autowired
	private ISearchProfileService searchProfileService;

	@Autowired
	private ICategoryNodeService categoryNodeService;
	
	@Autowired
	private UserUtil userUtil;
	
	@Autowired
	private IAttributeValueService attributeValueService;
	
	@Autowired
	private IFacetService facetService;
	
	
	/**
	 * sets the instance of ISearchProfileService
	 * @param searchProfileService
	 */
	public void setSearchProfileService(ISearchProfileService searchProfileService) {
		this.searchProfileService = searchProfileService;
	}

	/**Method to convert banner wrapper to banner entity.
	 * @param bannerwrapper
	 * @return
	 * @throws ServiceException
	 */
	public Banner wrapperConverter(BannerWrapper bannerWrapper,Banner banner) throws ServiceException{
		if(bannerWrapper != null){
			//Retrieve the values from wrapper and set the banner entity
			if(bannerWrapper.getBannerName() != null){
				banner.setBannerName(bannerWrapper.getBannerName());
			}

			if(bannerWrapper.getBannerPriority() != null){
				banner.setBannerPriority(bannerWrapper.getBannerPriority());
			}

			if(bannerWrapper.getBannerTemplate()!=null){
				banner.setBannerTemplate(bannerWrapper.getBannerTemplate());
			}

			if(bannerWrapper.getBannerType()!=null){
				banner.setBannerType(bannerWrapper.getBannerType());
			}
			
			if(bannerWrapper.getStartDate() != null){
				banner.setStartDate(bannerWrapper.getStartDate());
			}
			
			if(bannerWrapper.getEndDate() != null){
				banner.setEndDate(bannerWrapper.getEndDate());
			}
			Date date=new Date();
			Users user = userUtil.getUser();
			if(banner.getCreatedBy() ==  null){
				banner.setCreatedBy(user);
				banner.setCreatedDate(date);
			}
			banner.setUpdatedBy(user);
			banner.setUpdatedDate(date);
			
		}
		return banner;
	}


	/**
	 * Get the context related data and prepare to load into context tables
	 * @param bannerWrapper
	 * @param bannerContext
	 * @return Context
	 * @throws ServiceException
	 */
	public List<Context> contextWrapperConverter(BannerWrapper bannerWrapper, List<Context> bannerContexts) throws ServiceException{
		if(bannerContexts == null){
			bannerContexts=new ArrayList<Context>();
		}
		Context bannerContext = new Context();
		//Get the list of contexts from banner wrapper
		if(bannerWrapper.getContexts() != null && bannerWrapper.getContexts().size() > 0){
			for(ContextWrapper contextWrapper:bannerWrapper.getContexts()){
				bannerContext=bannerContextwrapper(contextWrapper, null, null);
				bannerContexts.add(bannerContext);
			}
		}
		return bannerContexts;
	}


	
	/**
	 * 
	 * @param contextWrapper
	 * @param contextId
	 * @param context
	 * @return
	 * @throws ServiceException
	 */
	public Context bannerContextwrapper(ContextWrapper contextWrapper, Long contextId, Context context) throws ServiceException{
		if(context == null){
			context=new Context(); 
		}
		Category categoryNode = new Category();
		if(contextWrapper.getSearchProfileId() != null){
			SearchProfile searchProfile = searchProfileService.retrieveById(contextWrapper.getSearchProfileId());
			context.setSearchProfile(searchProfile);
		}
		if(contextWrapper.getContextPathId() != null){
			categoryNode=categoryNodeService.retrieveById(contextWrapper.getContextPathId());
			context.setCategoryNode(categoryNode);
		}
		//Set the context entity
		context.setInheritable(contextWrapper.getInherit());	

		if(contextWrapper.getContextFacetWrapper() != null)
		{	
			
			List<ContextFacet> ctxfacets = new ArrayList<ContextFacet>();
			if(context.getContextFacet() != null && context.getContextFacet().size() > 0){
				//set the context keyword entity
				for(Iterator<ContextFacet> iterator = context.getContextFacet().iterator(); iterator.hasNext();){
					ContextFacet ctxFacet = iterator.next();
					boolean isExist= false;
					for(ContextFacetWrapper ctxFacetWrapper:contextWrapper.getContextFacetWrapper()){
						if(ctxFacetWrapper.getFacetId().equals(ctxFacet.getContextFacetId().getFacet().getFacetId())
								&& ctxFacetWrapper.getAttributeValueId().equals(ctxFacet.getContextFacetId().getAttributeValue())){
							contextWrapper.getContextFacetWrapper().remove(ctxFacetWrapper);
							isExist = true;
							break;
						}
					}
					if(!isExist){
						iterator.remove();
					}
				}
			}
				ctxfacets = context.getContextFacet();
				for(ContextFacetWrapper ctxFacetWrapper:contextWrapper.getContextFacetWrapper()){
					ContextFacet contextFacet = new ContextFacet();
					contextFacet.setIsActive("Y");
					ContextFacetPK contextFacetPK = new ContextFacetPK();
					contextFacetPK.setContext(context);
					Facet facet = facetService.retrieveById(ctxFacetWrapper.getFacetId());
					contextFacetPK.setFacet(facet);
					AttributeValue attributeValue = attributeValueService.retrieveById(ctxFacetWrapper.getAttributeValueId());
					contextFacetPK.setAttributeValue(attributeValue);
					contextFacet.setContextFacetId(contextFacetPK);
					ctxfacets.add(contextFacet);
					}
			
			context.setContextFacet(ctxfacets);
		}
		//Get the list of keywords for each context
		if(contextWrapper.getKeywords() != null && !contextWrapper.getKeywords().isEmpty())
		{   
			String keywordTerms[] = contextWrapper.getKeywords().split(",");
			List<String> keywords = Arrays.asList(keywordTerms);
			List<String>  updateTerms = new ArrayList<String>(keywords);
			List<ContextKeyword> contextKeywords = new ArrayList<ContextKeyword>();
			if(context.getContextKeyword() != null && context.getContextKeyword().size() > 0){
				//set the context keyword entity
				for(Iterator<ContextKeyword> iterator = context.getContextKeyword().iterator(); iterator.hasNext();){
					ContextKeyword keyword = iterator.next();
					if(!updateTerms.contains(keyword.getId().getKeyword())){
						iterator.remove();
					}else{
						updateTerms.remove(keyword.getId().getKeyword());
					}
				}
				contextKeywords = context.getContextKeyword();
			}
			for(int i = 0;i< updateTerms.size() ; i++){
					ContextKeyword contextKeyword=new ContextKeyword();
					contextKeyword.setContextKeyword(context);
					ContextKeywordPK contextPK = new ContextKeywordPK();
					contextPK.setKeyword(updateTerms.get(i));
					contextKeyword.setId(contextPK);
					contextKeywords.add(contextKeyword);
			}
			context.setContextKeyword(contextKeywords);
		}	
		return context;
	}
}
