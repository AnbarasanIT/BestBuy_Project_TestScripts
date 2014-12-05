package com.bestbuy.search.merchandising.unittest.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.bestbuy.search.merchandising.domain.AttrValSortType;
import com.bestbuy.search.merchandising.domain.Attribute;
import com.bestbuy.search.merchandising.domain.AttributeValue;
import com.bestbuy.search.merchandising.domain.Banner;
import com.bestbuy.search.merchandising.domain.BannerTemplate;
import com.bestbuy.search.merchandising.domain.BannerTemplateMeta;
import com.bestbuy.search.merchandising.domain.BoostAndBlock;
import com.bestbuy.search.merchandising.domain.BoostAndBlockProduct;
import com.bestbuy.search.merchandising.domain.Category;
import com.bestbuy.search.merchandising.domain.CategoryFacet;
import com.bestbuy.search.merchandising.domain.Context;
import com.bestbuy.search.merchandising.domain.ContextFacet;
import com.bestbuy.search.merchandising.domain.ContextFacetPK;
import com.bestbuy.search.merchandising.domain.ContextKeyword;
import com.bestbuy.search.merchandising.domain.ContextKeywordPK;
import com.bestbuy.search.merchandising.domain.Facet;
import com.bestbuy.search.merchandising.domain.FacetAttributeValueOrder;
import com.bestbuy.search.merchandising.domain.FacetAttributeValueOrderPK;
import com.bestbuy.search.merchandising.domain.KeywordRedirect;
import com.bestbuy.search.merchandising.domain.SearchProfile;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Synonym;
import com.bestbuy.search.merchandising.domain.SynonymTerm;
import com.bestbuy.search.merchandising.domain.SynonymTermPK;
import com.bestbuy.search.merchandising.domain.SynonymType;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.domain.common.DisplayEnum;
import com.bestbuy.search.merchandising.domain.common.DisplayModeEnum;
import com.bestbuy.search.merchandising.jobs.DaasRequestWrapper;
import com.bestbuy.search.merchandising.wrapper.AttributeValueWrapper;
import com.bestbuy.search.merchandising.wrapper.BannerBaseWrapper;
import com.bestbuy.search.merchandising.wrapper.BannerWrapper;
import com.bestbuy.search.merchandising.wrapper.BoostAndBlockWrapper;
import com.bestbuy.search.merchandising.wrapper.BoostBlockProductWrapper;
import com.bestbuy.search.merchandising.wrapper.CategoryWrapper;
import com.bestbuy.search.merchandising.wrapper.ContextWrapper;
import com.bestbuy.search.merchandising.wrapper.FacetWrapper;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.PaginationWrapper;
import com.bestbuy.search.merchandising.wrapper.SynonymWrapper;

/**
 * @author Lakshmi Valluripalli
 * Modified by Kalaiselvi Jaganathan Added base data for assets and keyword redirect
 */
public class BaseData {

	/**
	 * creates SynonymWrapper Object
	 * @return SynonymWrapper
	 */
	public static SynonymWrapper getSynonymWrapper(){
		SynonymWrapper synonymWrapper = new SynonymWrapper();
		List<String> terms = new ArrayList<String>();
		terms.add("Music");
		terms.add("Rock");
		terms.add("Other");
		synonymWrapper.setPrimaryTerm("testCreate");
		synonymWrapper.setListId(1147192016834L);
		synonymWrapper.setDirectionality("One-way");
		synonymWrapper.setExactMatch("yes");
		synonymWrapper.setTerm(terms);
		synonymWrapper.setSynonymId(1l);
		synonymWrapper.setEndDate(new Date());
		synonymWrapper.setModifiedBy("a1234567");
		synonymWrapper.setModifiedDate(new Date());
		synonymWrapper.setStartDate(new Date());
		synonymWrapper.setStatus("Approved");
		synonymWrapper.setCreatedDate(new Date());
		synonymWrapper.setCreatedBy("a1234567");
		synonymWrapper.setStatusId(3L);
		return synonymWrapper;
	}
	/**
	 * Created SynonymListType Object
	 * @return SynonymListType
	 */
	public static SynonymType getSynonymListType(){
		SynonymType synonymType = new SynonymType();
		synonymType.setSynonymListId(1147192016834L);
		synonymType.setSynonymListType("music");
		return synonymType;

	}

	/**
	 * Created the SynonymGroup Object to Save to DB
	 * @param listType
	 * @return
	 */
	public static Synonym getSynonymGroup(SynonymType listType){
		Synonym synonym = new Synonym();
		synonym.setExactMatch("yes");
		synonym.setDirectionality("One-way");
		synonym.setPrimaryTerm("testCreate");
		synonym.setSynListId(listType);
		return synonym;
	}

	/**
	 * creates SynonymGroup Object for persisted mock
	 * @param listType
	 * @return SynonymGroup
	 */
	public static Synonym getSavedSynonymGroup(SynonymType listType){
		Synonym synonym = new Synonym();
		synonym.setExactMatch("yes");
		synonym.setDirectionality("One-way");
		synonym.setPrimaryTerm("testCreate");
		synonym.setId(2L);
		synonym.setSynListId(listType);
		SynonymTerm synonymGroupTerms = getSynonymGroupTerm(synonym);
		List<SynonymTerm> synonymTerms = new ArrayList<SynonymTerm>();
		synonymTerms.add(synonymGroupTerms);
		synonym.setSynonymGroupTerms(synonymTerms);
		synonym.setStatus(getStatus());
		return synonym;
	}

	public static SynonymTerm getSynonymGroupTerm(Synonym synonym){
		SynonymTerm synonymGroupTerms = new SynonymTerm();
		SynonymTermPK termPk = new SynonymTermPK();
		termPk.setSynonym(synonym);
		termPk.setSynTerm("junitSynTerm");
		synonymGroupTerms.setSynonymTerms(termPk);
		return synonymGroupTerms;
	}

	/**
	 * creates the Status Object
	 * @return Status
	 */
	public static Status getStatus(){
		Status status = new Status();
		status.setStatus("Draft");
		status.setStatusId(2L);
		return status;
	}
	
	/**
	 * creates the Status Object
	 * @return Status
	 */
	public static Status getApproveStatus(){
		Status status = new Status();
		status.setStatus("Approved");
		status.setStatusId(3L);
		return status;
	}
	
	/**
	 * creates the Status Object
	 * @return Status
	 */
	public static Status getRejectStatus(){
		Status status = new Status();
		status.setStatus("Rejected");
		status.setStatusId(6L);
		return status;
	}

	/**
	 * creates the Status Object for delete
	 * @return Status
	 */
	public static Status getDeleteStatus(){
		Status status = new Status();
		status.setStatus("Deleted");
		status.setStatusId(7L);
		return status;
	}

	/**
	 * Created the User Object
	 * @return Users
	 */
	public static Users getUsers(){
		Users user = new Users();
		user.setLoginName("a1234567");
		user.setFirstName("MerchandisingUI");
		user.setLastName("BestBuy");
		user.setEmail("MerchandisingUI-email@bestbuy.com");
		return user;
	}

	/**
	 * Created the User Object
	 * @return Users
	 */
	public static Users getDBUsers(){
		Users user = new Users();
		user.setLoginName("a1234567");
		user.setFirstName("MerchandisingUI");
		user.setLastName("BestBuy");
		user.setEmail("MerchandisingUI-email@bestbuy.com");
		return user;
	}


	/**
	 * Created the Edit SynonymGroup Object to retrieve data from DB
	 * @param listType
	 * @return synonymGroup
	 */
	public static Synonym getDBSynonym(SynonymType listType){
		Synonym synonymGroup = new Synonym();
		synonymGroup.setId(2l);
		synonymGroup.setExactMatch("yes");
		synonymGroup.setDirectionality("One-way");
		synonymGroup.setPrimaryTerm("PrimaryTerm");
		synonymGroup.setSynListId(listType);
		SynonymTerm synonymGroupTerms = getSynonymGroupTerm(synonymGroup);
		List<SynonymTerm> synonymTerms = new ArrayList<SynonymTerm>();
		synonymTerms.add(synonymGroupTerms);
		synonymGroup.setSynonymGroupTerms(synonymTerms);
		synonymGroup.setStatus(getStatus());
		return synonymGroup;
	}

	/**
	 * creates SynonymWrapper Object
	 * @return SynonymWrapper
	 */
	public static SynonymWrapper getEditSynonymWrapper(){
		SynonymWrapper synonymWrapper = new SynonymWrapper();
		synonymWrapper.setSynonymId(2l);
		synonymWrapper.setPrimaryTerm("testEdit");
		synonymWrapper.setListId(1147192016834L);
		synonymWrapper.setDirectionality("One-way");
		synonymWrapper.setExactMatch("yes");
		List<String> terms = new ArrayList<String>();
		terms.add("Music");
		terms.add("Rock");
		terms.add("Other");
		synonymWrapper.setTerm(terms);
		synonymWrapper.setStatusId(2L);
		synonymWrapper.setModifiedBy("a1234567");
		return synonymWrapper;
	}
	/**
	 * Created SynonymListType Collection object
	 * @return SynonymListType
	 */
	@SuppressWarnings({ "null"})
	public static Collection<SynonymType> getSynListTypes(){
		SynonymType synonymTypes = new SynonymType();
		Collection<SynonymType> synListTypes = null;
		synonymTypes.setSynonymListId(1147192016834L);
		synonymTypes.setSynonymListType("music");
		synListTypes.add(synonymTypes);
		synonymTypes.setSynonymListId(1147192016834L);
		synonymTypes.setSynonymListType("song");
		synListTypes.add((SynonymType) synonymTypes);
		return synListTypes;

	}
	/**
	 * @author Chanchal Kumari
	 * @param 
	 * @return Collection<SynonymGroup>
	 */
	public static List<Synonym> getSynonymGroupList(){
		List<Synonym> synonymGroupsList = new ArrayList<Synonym>();
		Synonym synonymGroup = new Synonym();
		synonymGroup.setExactMatch("yes");
		synonymGroup.setDirectionality("One-way");
		synonymGroup.setPrimaryTerm("testCreate");
		synonymGroup.setSynListId(getSynonymListType());
		// synonymGroup.setAssets(assets);
		synonymGroupsList.add(synonymGroup);
		return synonymGroupsList;
	}

	/** 
	 * Test data for keywordRedirect
	 * @return keywordRedirect
	 */
	public static KeywordRedirect getRedirects(){
		KeywordRedirect keywordRedirect=new KeywordRedirect();
		keywordRedirect.setRedirectString("http://url");
		keywordRedirect.setRedirectType("URL");
		keywordRedirect.setSearchProfile(getSearchProfile());
		keywordRedirect.setKeyword("keyword");
		return keywordRedirect;
	}

	/**
	 * Test data for search profile
	 * @return searchProfile
	 */
	public static SearchProfile getSearchProfile(){
		SearchProfile searchProfile=new SearchProfile();
		searchProfile.setSearchProfileId(3L);
		searchProfile.setSynonymListId(getSynonymListType());
		searchProfile.setCollections("Collections");
		searchProfile.setDefaultPath("defaultPath");
		searchProfile.setLastModifiedID("A922998");
		searchProfile.setPipelineName("pipelineName");
		searchProfile.setProfileName("profileName");
		searchProfile.setRankProfileName("rankProfileName");
		searchProfile.setSearchFieldName("searchFieldName");
		searchProfile.setTopCategoryId("topCategoryId");
		searchProfile.setModifiedDate(new Date());
		return searchProfile;
	}

	/**
	 * Test data for the banner
	 * @return banner
	 */
	public static Banner getBannerData(){
		Banner banner=new Banner();
		banner.setBannerId(123L);
		banner.setBannerName("bannerName");
		banner.setBannerPriority(1L);
		banner.setBannerType("1");
		banner.setBannerTemplate("bannerTemplate");
		banner.setStatus(getStatus());
		banner.setBannerTemplate("HTML_ONLY");
		banner.setBannerTemplates(getBannerHTMLData());
		banner.setContexts(getContextList());
		return banner;
	}
	
	/**
	 * Test data for the banner
	 * @return banner
	 */
	public static Banner getBannerDataInvalid(){
		Banner banner=new Banner();
		banner.setBannerId(123L);
		banner.setBannerName("bannerName");
		banner.setBannerPriority(1L);
		banner.setBannerType("1");
		banner.setBannerTemplate("bannerTemplate");
		banner.setStatus(getStatus());
		banner.setBannerTemplate("HTML_ONLY");
		banner.setBannerTemplates(getBannerHTMLData());
		banner.setContexts(getContextListInvalid());
		return banner;
	}
	
	/**
	 * Test data for the banner with 1header_3SKU template
	 * @return banner
	 */
	public static Banner getBannerData1(){
		Banner banner=new Banner();
		banner.setBannerId(123L);
		banner.setBannerName("bannerName");
		banner.setBannerPriority(1L);
		banner.setBannerType("1");
		banner.setBannerTemplate("bannerTemplate");
		banner.setStatus(getApproveStatus());
		banner.setBannerTemplate("1HEADER_3SKU");
		banner.setBannerTemplates(getBanner1H3SData());
		banner.setContexts(getContextList());
		return banner;
	}
	
	/**
	 * Test data for the banner
	 * @return banner
	 */
	public static List<BannerTemplate> getBanner1H3SData(){
		List<BannerTemplate> bannerTemplates=new ArrayList<BannerTemplate>();
		BannerTemplate bannerTemplate=new BannerTemplate();
		bannerTemplate.setId(1L);
		bannerTemplate.setSku("12345,1345");
		bannerTemplate.setSkuPosition(0L);
		bannerTemplate.setTemplateHeader("Head");
		bannerTemplates.add(bannerTemplate);
		return bannerTemplates;
	}
	
	/**
	 * Test data for the banner template
	 * @return banner
	 */
	public static List<BannerTemplate> getBannerHTMLData(){
		List<BannerTemplate> bannerTemplates=new ArrayList<BannerTemplate>();
		BannerTemplate bannerTemplate=new BannerTemplate();
		bannerTemplate.setId(1L);
		bannerTemplate.setSku("12345");
		bannerTemplates.add(bannerTemplate);
		return bannerTemplates;
	}

	/**
	 * Test data for the saved banner
	 * @return banner
	 */
	public static Banner getSavedBannerData(){
		Banner banner=new Banner();
		banner.setBannerId(123L);
		banner.setBannerName("bannerName");
		banner.setBannerPriority(1L);
		banner.setBannerType("1");
		banner.setBannerTemplate("bannerTemplate");
		banner.setContexts(getContextList());
		//banner.setBannerURL(getBannerUrlList());
		return banner;
	}

	/**
	 * Test data for Banner
	 * @return bannerList
	 */
	public static List<Banner> getBannerDataList(){
		List<Banner> bannerList = new ArrayList<Banner>();
		bannerList.add(getBannerData());
		return bannerList;
	}

	/**
	 * Test data for Banner
	 * @return bannerList
	 */
	public static List<Banner> getBannerDataLists(){
		List<Banner> bannerList = new ArrayList<Banner>();
		bannerList.add(getBannerDataInvalid());
		return bannerList;
	}
	
	/**
	 * Test data for BannerTemplate
	 * @return BannerTemplate
	 */
	public static BannerTemplateMeta getBannerTemplateData(){
		BannerTemplateMeta bannerTemplate=new BannerTemplateMeta();
		bannerTemplate.setBannerTemplateFieldList("list");
		bannerTemplate.setBannerTemplateId(1L);
		bannerTemplate.setBannerTemplateName("bannerTemplateName");
		bannerTemplate.setBannerTemplateHeaderCount(3L);
		bannerTemplate.setBannerTemplateSlotCount(3L);
		return bannerTemplate;
	}

	/**
	 * Test data for Context
	 * @return Context
	 */
	public static List<Context> getContextList(){
		List<Context> contexts=new ArrayList<Context>();
		Context context=new Context();
		Category categoryNode = new Category();
		categoryNode.setCategoryNodeId("pcmcat");
		context.setCategoryNode(categoryNode);
		context.setContextId(1L);
		context.setSearchProfile(getSearchProfile());
		context.setContextFacet(getContextFacetList());
		context.setContextKeyword(getContextKeywordList());
		context.setInheritable(0l);
		context.setIsActive("Y");
		contexts.add(context);
		return contexts;
	}
	
	/**
	 * Test data for Context
	 * @return Context
	 */
	public static List<Context> getContextListInvalid(){
		List<Context> contexts=new ArrayList<Context>();
		Context context=new Context();
		Category categoryNode = new Category();
		categoryNode.setCategoryNodeId("pcmcat");
		context.setCategoryNode(categoryNode);
		context.setContextId(1L);
		context.setSearchProfile(getSearchProfile());
		context.setContextFacet(getContextFacetList());
		context.setContextKeyword(getContextKeywordList());
		context.setInheritable(0l);
		context.setIsActive("N");
		contexts.add(context);
		return contexts;
	}
	
	public static List<Context> getContextLists(){
		List<Context> contexts=new ArrayList<Context>();
		Context context=new Context();
		Category categoryNode = new Category();
		categoryNode.setCategoryNodeId("pcmcat");
		context.setCategoryNode(categoryNode);
		context.setContextId(1L);
		context.setSearchProfile(getSearchProfile());
		context.setContextFacet(getContextFacetList());
		context.setContextKeyword(getContextKeywordList());
		context.setInheritable(0l);
		context.setBanners(getBannerDatas());
		contexts.add(context);
		return contexts;
	}
	
	/**
	 * Test data for the banner
	 * @return banner
	 */
	public static Banner getBannerDatas(){
		Banner banner=new Banner();
		banner.setBannerId(123L);
		banner.setBannerName("bannerName");
		banner.setBannerPriority(1L);
		banner.setBannerType("1");
		banner.setBannerTemplate("bannerTemplate");
		banner.setStatus(getStatus());
		banner.setBannerTemplate("HTML_ONLY");
		banner.setBannerTemplates(getBannerHTMLData());
		return banner;
	}

	/**
	 * Test data for BannerTemplate
	 * @return bannerTemplateList
	 */
	public static List<BannerTemplateMeta> getBannerTemplateList(){
		List<BannerTemplateMeta> bannerTemplateList=new ArrayList<BannerTemplateMeta>();
		BannerTemplateMeta bannerTemplate=new BannerTemplateMeta();
		bannerTemplate.setBannerTemplateFieldList("list");
		bannerTemplate.setBannerTemplateId(1L);
		bannerTemplate.setBannerTemplateName("bannerTemplateName");
		bannerTemplate.setBannerTemplateHeaderCount(3L);
		bannerTemplate.setBannerTemplateSlotCount(3L);
		bannerTemplateList.add(bannerTemplate);
		return bannerTemplateList;
	}

	/**
	 * Test data for ContextFacet
	 * @return ContextFacet
	 */
	public static List<ContextFacet> getContextFacetList(){
		List<ContextFacet> contextFacets=new ArrayList<ContextFacet>();
		ContextFacet contextFacet=new ContextFacet();
		//contextFacet.setContextFacetId(1L);
		//contextFacet.setFacetValue("facetValue");
		//contextFacet.setContextFacet(getContextData());
		//contextFacets.add(contextFacet);
		return contextFacets;
	}

	/**
	 * Test data for ContextKeyword list
	 * @return ContextKeywords
	 */
	public static List<ContextKeyword> getContextKeywordList(){
		List<ContextKeyword> contextKeywords=new ArrayList<ContextKeyword>();
		ContextKeyword contextKeyword=new ContextKeyword();
		//contextKeyword.setContextKeywordId(1L);
		//contextKeyword.setKeyword("Keyword");
		contextKeyword.setContextKeyword(getContextData());
		ContextKeywordPK contextKeywordPK = new ContextKeywordPK();
		contextKeywordPK.setContextId(1L);
		contextKeywordPK.setKeyword("key1");
		contextKeyword.setId(contextKeywordPK);
		contextKeywords.add(contextKeyword);
		contextKeyword.setContextKeyword(getContextData1());
		contextKeywordPK.setContextId(2L);
		contextKeywordPK.setKeyword("key2");
		contextKeyword.setId(contextKeywordPK);
		contextKeywords.add(contextKeyword);
		return contextKeywords;
	}

	/** 
	 * Test data for Context
	 * @return context
	 */
	public static Context getContextData(){
		Context context=new Context();
		Category categoryNode = new Category();
		categoryNode.setCategoryNodeId("pcmcat");
		context.setCategoryNode(categoryNode);
		context.setContextId(1L);
		context.setSearchProfile(getSearchProfile());
		return context;
	}

	/** 
	 * Test data for Context
	 * @return context
	 */
	public static Context getContextData1(){
		Context context=new Context();
		Category categoryNode = new Category();
		categoryNode.setCategoryNodeId("pcmcat");
		context.setCategoryNode(categoryNode);
		context.setContextId(1L);
		context.setSearchProfile(getSearchProfile());
		return context;
	}
	
	/**
	 * Test data for ContextFacet
	 * @return ContextFacet
	 */
	public static ContextFacet getContextFacetData(){
		ContextFacet contextFacet=new ContextFacet();
		contextFacet.setContextFacetId(getContextFacetDataPK());
		contextFacet.getContextFacetId().setAttributeValue(getAttributeValueList().get(0));
		
		//contextFacet.setContextFacetId(1L);
		//contextFacet.setFacetValue("facetValue");
		//contextFacet.setContextFacet(getContextData());
		return contextFacet;
	}
	
	/**
	 * Test data for ContextFacet
	 * @return ContextFacet
	 */
	public static ContextFacetPK getContextFacetDataPK(){
		ContextFacetPK contextFacetPK=new ContextFacetPK();
		contextFacetPK.setContext(getContextData());
		contextFacetPK.setFacet(getFacet());
		return contextFacetPK;
	}

	/**
	 * Test data for ContextKeyword
	 * @return ContextKeyword
	 */
	public static ContextKeyword getContextKeywordData(){
		ContextKeywordPK contextPK = new ContextKeywordPK();
		contextPK.setContextId(1L);
		contextPK.setKeyword("Keyword");
		ContextKeyword contextKeyword=new ContextKeyword();
		contextKeyword.setId(contextPK);
		contextKeyword.setContextKeyword(getContextData());
		return contextKeyword;
	}

	/** 
	 * BannerHTMLTemplate test data
	 * @return bannerURLList
	 */
	public static BannerTemplate getBannerUrlList(){
		BannerTemplate bannerURLList=new BannerTemplate();
		bannerURLList.setId(1l);
		bannerURLList.setBanner(getBannerData());
		return bannerURLList;
	}

	/**
	 * Created object BannerWrapper
	 * @return bannerWrapper
	 */
	public static BannerWrapper getBannerWrapper(){
		BannerWrapper bannerWrapper = new BannerWrapper();
		// bannerWrapper.setAssetId(123L);
		bannerWrapper.setBannerId(1L);
		bannerWrapper.setBannerName("bannerName");
		bannerWrapper.setBannerPriority(1L);
		bannerWrapper.setBannerTemplate("bannerTemplate");
		bannerWrapper.setContexts(getContextWrapper());
		bannerWrapper.setStartDate(new Date());
		bannerWrapper.setEndDate(new Date());
		return bannerWrapper;
	}

	/**
	 * created context wrapper object
	 * @return contextWrappers
	 */
	public static List<ContextWrapper> getContextWrapper(){
		List<ContextWrapper> contextWrappers = new ArrayList<ContextWrapper>();
		ContextWrapper contextWrapper = new ContextWrapper();
		contextWrapper.setContextId(1L);
		contextWrapper.setContextPathId("contextName");
		contextWrapper.setInherit(1L);
		contextWrapper.setKeywords("keyword");
		contextWrappers.add(contextWrapper);
		return contextWrappers;
	}

	/**
	 * Promo Test Data
	 * @return
	 */

	/**
	 * Facet Test Data
	 */
	public static Facet getFacet(){
		Facet facet = new Facet();
		facet.setFacetId(1L);
		facet.setStatus(getStatus());
		facet.setUpdatedBy(getUsers());
		facet.setUpdatedDate(new Date());
		facet.setAttribute(getAttribute());
		facet.setDisplayName("FacetTesting");
		facet.setSystemName("FacetSystemName");
		facet.setMaxAttrValue(10L);
		facet.setMinAttrValue(1L);
		facet.setCategoryFacet(getCategoryFacet());
		facet.setCreatedBy(getUsers());
		facet.setCreatedDate(new Date());
		facet.setPosition("1");
		facet.setDisplay(DisplayEnum.Y);
		facet.setDisplayFacetRemoveLink(DisplayEnum.Y);
		facet.setDisplayMobileFacet(DisplayEnum.Y);
		facet.setDisplayMobileFacetRemoveLink(DisplayEnum.Y);
		facet.setDisplayMode(DisplayModeEnum.BROWSE);
		facet.setDisplayRecurrence(DisplayEnum.Y);
		facet.setGlossaryItem(1L);
		facet.setAttrValSortType("1");
		facet.setStartDate(new Date());
		facet.setEndDate(new Date()); 
		facet.setFacetAttributeOrder(getFacetAttributeValueOrders());
		return facet;
	}
	
	/**
	 * Facet Test Data
	 */
	public static Facet getNullFacet(){
		Facet facet = new Facet();
		facet.setFacetId(null);
		facet.setStatus(null);
		facet.setUpdatedBy(null);
		facet.setUpdatedDate(null);
		facet.setAttribute(null);
		facet.setDisplayName(null);
		facet.setSystemName(null);
		facet.setMaxAttrValue(null);
		facet.setMinAttrValue(null);
		facet.setCategoryFacet(null);
		facet.setCreatedBy(null);
		facet.setDisplay(null);
		facet.setDisplayFacetRemoveLink(null);
		facet.setDisplayMobileFacet(null);
		facet.setDisplayMobileFacetRemoveLink(null);
		facet.setDisplayMode(null);
		facet.setDisplayRecurrence(null);
		facet.setGlossaryItem(null);
		facet.setAttrValSortType(null);
		facet.setStartDate(null);
		facet.setEndDate(null); 
		
		return facet;
	}

	/**
	 * Facet Test Data
	 */
	public static Facet getFacetForCatg(){
		Facet facet = new Facet();
		facet.setFacetId(1L);
		facet.setStatus(getStatus());
		facet.setUpdatedBy(getUsers());
		facet.setUpdatedDate(new Date());
		facet.setAttribute(getAttribute());
		facet.setDisplayName("FacetTesting");
		facet.setSystemName("FacetSystemName");
		facet.setMaxAttrValue(10L);
		facet.setMinAttrValue(1L);
		return facet;
	}

	/** 
	 * Created FacetAttributeValueOrder object
	 * @return facetAttributeValueOrders
	 */
	public static List<FacetAttributeValueOrder> getFacetAttributeValueOrder(){
		List<FacetAttributeValueOrder> facetAttributeValueOrders = new ArrayList<FacetAttributeValueOrder>();
		FacetAttributeValueOrder facetAttributeValueOrder = new FacetAttributeValueOrder();
		facetAttributeValueOrder.setDisplayOrder(1L);
		facetAttributeValueOrder.setFacetAttributeValueOrderPK(getFacetAttributeValueOrderPK());
		facetAttributeValueOrder.setFacetDoNotInclude(DisplayEnum.Y);
		facetAttributeValueOrders.add(facetAttributeValueOrder);
		return facetAttributeValueOrders;
	}

	public static FacetAttributeValueOrderPK getFacetAttributeValueOrderPK(){
		FacetAttributeValueOrderPK facetAttributeValueOrderPK = new FacetAttributeValueOrderPK();
		facetAttributeValueOrderPK.setAttributeValue(getAttributeValueList().get(0));
		facetAttributeValueOrderPK.setFacet(getFacet());
		return facetAttributeValueOrderPK;
	}

	/** 
	 * Created category facet list object
	 * @return categoryFacets
	 */
	public static List<CategoryFacet> getCategoryFacet(){
		List<CategoryFacet> categoryFacets = new ArrayList<CategoryFacet>();
		CategoryFacet categoryFacet = new CategoryFacet();
		categoryFacet.setCatgFacetId(1L);
		categoryFacet.setFacet(getFacetForCatg());
		categoryFacet.setDepFacet(new Facet());
		categoryFacet.setDisplayOrder(1L);
		categoryFacet.setDisplay(DisplayEnum.Y);
		categoryFacet.setApplySubCategory(DisplayEnum.Y);
		categoryFacet.setDepFacetDisplay(DisplayEnum.Y);
		categoryFacet.setAttributeValue(getAttributeValueList().get(0));
		categoryFacet.setCategoryNode(getCategoryNode());
		categoryFacets.add(categoryFacet);
		return categoryFacets;
	}
	
	/** 
	 * Created category facet list object
	 * @return categoryFacets
	 */
	public static List<CategoryFacet> getNullCategoryFacet(){
		List<CategoryFacet> categoryFacets = new ArrayList<CategoryFacet>();
		CategoryFacet categoryFacet = new CategoryFacet();
		categoryFacet.setCatgFacetId(null);
		categoryFacet.setFacet(null);
		categoryFacet.setDepFacet(null);
		categoryFacet.setDisplayOrder(null);
		categoryFacet.setDisplay(null);
		categoryFacet.setApplySubCategory(null);
		categoryFacet.setDepFacetDisplay(null);
		categoryFacet.setAttributeValue(null);
		categoryFacet.setCategoryNode(getCategoryNode());
		categoryFacets.add(categoryFacet);
		return categoryFacets;
	}

	/** 
	 * Created Category Node object
	 * @return CategoryNode
	 */
	public static Category getCategoryNode(){
		Category categoryNode = new Category();
		categoryNode.setCategoryNodeId("Pmcat23");
		categoryNode.setCategoryPath("Home & App");
		return categoryNode;
	}

	/**
	 * Created Attribute object
	 * @return attribute
	 */
	public static Attribute getAttribute(){
		Attribute attribute = new Attribute();
		attribute.setAttributeId(1L);
		attribute.setAttributeName("Name");
		attribute.setAttributeValue(getAttributeValueList());
		return attribute;
	}

	/**
	 * Created AttributeValue object
	 * @return attributeValues
	 */
	public static List<AttributeValue> getAttributeValueList(){
		List<AttributeValue> attributeValues = new ArrayList<AttributeValue>();
		AttributeValue attributeValue = new AttributeValue();
		//attributeValue.setAttributeValue("Name");
		attributeValue.setAttributeValueId(1L);
		//attributeValue.setStatus(stat);
		attributeValues.add(attributeValue);
		return attributeValues;
	}	

	/**
	 * created categorytree object
	 * @return categoryTree
	 */
	public static String getCategoryTree(){
		String categoryTree = "<category id=\"rootcategoryid\"><![CDATA[Home]]>" +
				"<category id=\"abcat0100000\"><![CDATA[TV & Home Theater<!-- abcat0100000 -->]]>" +
				"<category id=\"abcat0101000\"><![CDATA[TVs<!-- abcat0101000 -->]]>" +
				"<category id=\"abcat0101001\"><![CDATA[All Flat-Panel TVs<!-- abcat0101001 -->]]>" +
				"</category></category></category></category>";
		return categoryTree;
	}

	/**
	 * Created FacetWrapper List object
	 * @return facetWrappers
	 */

	public static List<FacetWrapper> getFacetWrappers(){
		List<FacetWrapper> facetWrappers = new ArrayList<FacetWrapper>();
		FacetWrapper facetWrapper = new FacetWrapper();
		facetWrapper.setFacetId(1L);
		facetWrapper.setSystemName("3D Capable");
		facetWrapper.setDisplayName("3D Capable");
		facetWrapper.setMinValue(11l);
		facetWrapper.setMaxValue(99l);
		facetWrapper.setStartDate(new Date());
		facetWrapper.setEndDate(new Date());
		facetWrapper.setDisplayMode("SEARCH");
		facetWrapper.setFacetDisplay("Y");
		facetWrapper.setGlossaryItem(1L);
		facetWrapper.setStatus(getStatus().getStatus());
		facetWrapper.setStatusId(getStatus().getStatusId());
		facetWrapper.setAttributeId(1001L);
		facetWrapper.setAttributeName("brand");
		facetWrapper.setCreatedBy("A922998");
		facetWrapper.setCreatedDate(new Date());
		facetWrapper.setPromoteList(getAttrValueWrappers());
		facetWrapper.setDisplayFacetRemoveLink("Y");
		facetWrapper.setDisplayMobileFacet("Y");
		facetWrapper.setDisplayMobileFacetRemoveLink("Y");
		facetWrapper.setDisplayRecurrence("Y");
		facetWrapper.setSortType("AZ");
		facetWrapper.setCategoryWrapper(getCategoryWrapper());
		facetWrappers.add(facetWrapper);
		return facetWrappers;
	}
	
	public static List<CategoryWrapper> getCategoryWrapper(){
		List<CategoryWrapper> categoryWrappers = new ArrayList<CategoryWrapper>();
		CategoryWrapper categoryWrapper = new CategoryWrapper();
		categoryWrapper.setApplySubCategory("Y");
		categoryWrapper.setCategoryId("Pmcat23");
		categoryWrapper.setCategoryPath("Home");
		categoryWrapper.setDepFacetId(1L);
		categoryWrapper.setDisplayContext("Y");
		categoryWrapper.setDisplayDepFacet("Y");
		categoryWrapper.setFacetAttributeValueId(1L);
		categoryWrapper.setFacetOrder(1L);
		categoryWrapper.setValid("Y");
		categoryWrappers.add(categoryWrapper);
		return categoryWrappers;
	}

	/**
	 * Created AttributeValueWrapper List object
	 * @return attributeValue wrapper
	 */

	public static List<AttributeValueWrapper> getAttrValueWrappers(){
		List<AttributeValueWrapper> attributeValueWrappers = new ArrayList<AttributeValueWrapper>();
		AttributeValueWrapper attributeValueWrapper = new AttributeValueWrapper();
		attributeValueWrapper.setAttributeValue("Value");
		attributeValueWrapper.setAttributeValueId(1L);
		attributeValueWrapper.setAttrValuedisplay("Y");
		attributeValueWrapper.setSortOrder(10L);
		attributeValueWrappers.add(attributeValueWrapper);
		return attributeValueWrappers;
	}

	/**
	 * Created FacetWrapper List object
	 * @return facetWrappers
	 */

	public static List<FacetWrapper> getNullFacetWrappers(){
		List<FacetWrapper> facetWrappers = new ArrayList<FacetWrapper>();
		FacetWrapper facetWrapper = new FacetWrapper();
		facetWrapper.setSystemName(null);
		facetWrapper.setDisplayName(null);
		facetWrapper.setMinValue(null);
		facetWrapper.setMaxValue(null);
		facetWrapper.setStartDate(null);
		facetWrapper.setEndDate(null);
		facetWrapper.setDisplayMode(null);
		facetWrapper.setFacetDisplay(null);
		facetWrapper.setGlossaryItem(null);
		facetWrapper.setStatus(null);
		facetWrapper.setStatusId(null);
		facetWrapper.setDisplayFacetRemoveLink(null);
		facetWrapper.setDisplayMobileFacet(null);
		facetWrapper.setDisplayMobileFacetRemoveLink(null);
		facetWrapper.setDisplayRecurrence(null);
		facetWrapper.setSortType(null);
		facetWrappers.add(facetWrapper);
		return facetWrappers;
	}
	
	/**
	 * Created FacetWrapper List object
	 * @return facetWrappers
	 */

	public static List<FacetWrapper> getEmptyFacetWrappers(){
		List<FacetWrapper> facetWrappers = new ArrayList<FacetWrapper>();
		FacetWrapper facetWrapper = new FacetWrapper();
		facetWrapper.setSystemName(" ");
		facetWrapper.setDisplayName(" ");
		facetWrapper.setDisplayMode(" ");
		facetWrapper.setFacetDisplay(" ");
		facetWrapper.setStatus(" ");
		facetWrapper.setDisplayFacetRemoveLink(" ");
		facetWrapper.setDisplayMobileFacet(" ");
		facetWrapper.setDisplayMobileFacetRemoveLink(" ");
		facetWrapper.setDisplayRecurrence(" ");
		facetWrapper.setSortType(" ");
		facetWrappers.add(facetWrapper);
		return facetWrappers;
	}

	/**
	 * Created facet list object
	 * @return facets
	 */
	public static List<Facet> getFacets(){
		List<Facet> facets = new ArrayList<Facet>();
		facets.add(getFacet());
		return facets;
	}

	/**
	 * Created AttrValSortType object
	 * @return attrValSortType
	 */
	public static AttrValSortType getAttrValSortType(){
		AttrValSortType attrValSortType = new AttrValSortType();
		attrValSortType.setValSortTypeId(1L);
		attrValSortType.setAttrValSortType("Alphabetical");
		return attrValSortType;
	}

	/**
	 * Created AttributeValue object
	 * @return attributeValues
	 */
	public static List<IWrapper> getAttributeValueWrapperList(){
		List<IWrapper> attributeValues = new ArrayList<IWrapper>();
		AttributeValueWrapper attributeValueWrapper = new AttributeValueWrapper();
		attributeValueWrapper.setAttributeValueId(1L);
		attributeValueWrapper.setAttributeValue("Active");
		//attributeValue.setStatus(stat);
		attributeValues.add(attributeValueWrapper);
		return attributeValues;
	}	
	
	@SuppressWarnings("unchecked")
	public static DaasRequestWrapper<BannerBaseWrapper> getBannerBaseWrapper(){
		DaasRequestWrapper<BannerBaseWrapper> wrappers = new DaasRequestWrapper<BannerBaseWrapper>();
		List<BannerBaseWrapper> baseWrappers = new ArrayList<BannerBaseWrapper>();
		BannerBaseWrapper wrapper = new BannerBaseWrapper();
		wrapper.setBannerName("Banner");
		baseWrappers.add(wrapper);
		wrappers.setContent(baseWrappers);
		return wrappers;
	}
	
	public static Facet getFacetsData(){
		Facet facet = new Facet();
		facet.setFacetId(1L);
		facet.setStatus(getStatus());
		facet.setUpdatedBy(getUsers());
		facet.setUpdatedDate(new Date());
		facet.setAttribute(getAttribute());
		facet.setDisplayName("FacetTesting");
		facet.setSystemName("FacetSystemName");
		facet.setMaxAttrValue(10L);
		facet.setMinAttrValue(1L);
		facet.setCategoryFacet(getCategoryFacet());
		facet.setCreatedBy(getUsers());
		facet.setCreatedDate(new Date());
		facet.setPosition("1");
		facet.setDisplay(DisplayEnum.Y);
		facet.setDisplayFacetRemoveLink(DisplayEnum.Y);
		facet.setDisplayMobileFacet(DisplayEnum.Y);
		facet.setDisplayMobileFacetRemoveLink(DisplayEnum.Y);
		facet.setDisplayMode(DisplayModeEnum.BROWSE);
		facet.setDisplayRecurrence(DisplayEnum.Y);
		facet.setGlossaryItem(1L);
		facet.setAttrValSortType("1");
		facet.setStartDate(new Date());
		facet.setEndDate(new Date()); 
		facet.setFacetAttributeOrder(getFacetAttributeValueOrders());
		return facet;
	}
	
	/** 
	 * Created FacetAttributeValueOrder object
	 * @return facetAttributeValueOrders
	 */
	public static List<FacetAttributeValueOrder> getFacetAttributeValueOrders(){
		List<FacetAttributeValueOrder> facetAttributeValueOrders = new ArrayList<FacetAttributeValueOrder>();
		FacetAttributeValueOrder facetAttributeValueOrder = new FacetAttributeValueOrder();
		facetAttributeValueOrder.setDisplayOrder(1L);
		facetAttributeValueOrder.setFacetAttributeValueOrderPK(getFacetAttributeValueOrderPKs());
		facetAttributeValueOrder.setFacetDoNotInclude(DisplayEnum.Y);
		facetAttributeValueOrders.add(facetAttributeValueOrder);
		return facetAttributeValueOrders;
	}

	public static FacetAttributeValueOrderPK getFacetAttributeValueOrderPKs(){
		FacetAttributeValueOrderPK facetAttributeValueOrderPK = new FacetAttributeValueOrderPK();
		facetAttributeValueOrderPK.setAttributeValue(getAttributeValueList().get(0));
		return facetAttributeValueOrderPK;
	}
	
	/**
	 * Created AttributeValue object
	 * @return attributeValues
	 */
	public static List<AttributeValueWrapper> getAttributeValueWrapperLists(){
		List<AttributeValueWrapper> attributeValues = new ArrayList<AttributeValueWrapper>();
		AttributeValueWrapper attributeValueWrapper = new AttributeValueWrapper();
		attributeValueWrapper.setAttributeValueId(1L);
		attributeValueWrapper.setAttributeValue("Active");
		//attributeValue.setStatus(stat);
		attributeValues.add(attributeValueWrapper);
		return attributeValues;
	}	
	
	public static CategoryWrapper getCatgWrapper(){
		CategoryWrapper categoryWrapper = new CategoryWrapper();
		categoryWrapper.setCategoryId(null);
		categoryWrapper.setDisplayContext(null) ;
		categoryWrapper.setDisplayDepFacet(null) ;
		categoryWrapper.setFacetAttributeValueId(null) ;
		categoryWrapper.setApplySubCategory(null) ;
		categoryWrapper.setDepFacetId(null);
		return categoryWrapper;
	}
  
  public static BoostAndBlock getBoostAndBlock(){
		BoostAndBlock boostAndBlock = new BoostAndBlock();
		boostAndBlock.setId(123456789L);
		boostAndBlock.setStatus(getStatus());
		boostAndBlock.setTerm("testTerm");
		boostAndBlock.setBoostAndBlockProducts(getBoostAndBlockProducts());
		return boostAndBlock;
	}
	public static BoostAndBlockProduct getBoostAndBlockProduct() {
		BoostAndBlockProduct boostAndBlockProduct = new BoostAndBlockProduct();
		boostAndBlockProduct.setProductName("testProduct");
		return boostAndBlockProduct;
	}
	
	public static List<BoostAndBlockProduct> getBoostAndBlockProducts() {
		List<BoostAndBlockProduct> products = new ArrayList<BoostAndBlockProduct>();
		BoostAndBlockProduct boostAndBlockProduct = new BoostAndBlockProduct();
		boostAndBlockProduct.setProductName("testProduct");
		boostAndBlockProduct.setPosition(1);
		products.add(boostAndBlockProduct);
		return products;
	}


	public static List<BoostAndBlockWrapper> getBoostAndBlockWrappers(){
		List<BoostAndBlockWrapper> wrappers = new ArrayList<BoostAndBlockWrapper>();
		BoostAndBlockWrapper wrapper = new BoostAndBlockWrapper();
		wrapper.setCategoryId("pmcat");
		wrapper.setCategoryPath("Home");
		wrapper.setCreatedBy("A922998");
		wrapper.setCreatedDate(new Date());
		wrapper.setEndDate(new Date());
		wrapper.setBoostBlockId(123456789L);
		wrapper.setModifiedBy("A922998");
		wrapper.setModifiedDate(new Date());
		wrapper.setSearchProfileId(1L);
		wrapper.setSearchProfileName("Global");
		wrapper.setSearchTerm("testTerm");
		wrapper.setStartDate(new Date());
		wrapper.setStatus("Approved");
		wrapper.setBoostBlockId(123456789L);
		wrapper.setBlockProduct(getBoostBlockProductWrapper());
		wrapper.setBoostProduct(getBoostBlockProductWrapper());
		wrappers.add(wrapper);
		return wrappers;
	}
	public static  List<BoostBlockProductWrapper> getBoostBlockProductWrapper(){
		List<BoostBlockProductWrapper> wrappers = new ArrayList<BoostBlockProductWrapper>();
		BoostBlockProductWrapper wrapper = new BoostBlockProductWrapper();
		wrapper.setPosition(1);
		wrapper.setProductName("testProduct");
		wrapper.setSkuId(1L);
		wrapper.setProductId(1L);
		wrappers.add(wrapper);
		return wrappers;
	}
	
	public static  List<BoostBlockProductWrapper> getBoostBlockProductWrappers(){
		List<BoostBlockProductWrapper> wrappers = new ArrayList<BoostBlockProductWrapper>();
		BoostBlockProductWrapper wrapper = new BoostBlockProductWrapper();
		wrapper.setPosition(1);
		wrapper.setProductName("testProduct");
		wrapper.setSkuId(1L);
		wrappers.add(wrapper);
		return wrappers;
	}
	
	public static List<BoostAndBlock> getBoostAndBlocks(){
		List<BoostAndBlock> boostAndBlocks = new ArrayList<BoostAndBlock>();
		BoostAndBlock boostAndBlock = new BoostAndBlock();
		boostAndBlock.setBoostAndBlockProducts(getBoostBlockProductList());
		boostAndBlock.setCategoryId(getCategoryNode());
		boostAndBlock.setCreatedDate(new Date());
		boostAndBlock.setCreatedBy(getUsers());
		boostAndBlock.setId(123456789L);
		boostAndBlock.setSearchProfileId(getSearchProfile());
		boostAndBlock.setStatus(getStatus());
		boostAndBlock.setTerm("testTerm");
		boostAndBlock.setUpdatedDate(new Date());
		boostAndBlocks.add(boostAndBlock);
		return boostAndBlocks;
	}
	public static  List<BoostAndBlockProduct> getBoostBlockProductList(){
		List<BoostAndBlockProduct> wrappers = new ArrayList<BoostAndBlockProduct>();
		BoostAndBlockProduct wrapper = new BoostAndBlockProduct();
		wrapper.setPosition(1);
		wrapper.setProductName("testProduct");
		wrapper.setSkuId(1L);
		wrapper.setId(1L);
		wrappers.add(wrapper);
		return wrappers;
	}
	
	public static List<BoostAndBlockWrapper> getBoostAndBlockNullWrappers(){
		List<BoostAndBlockWrapper> wrappers = new ArrayList<BoostAndBlockWrapper>();
		BoostAndBlockWrapper wrapper = new BoostAndBlockWrapper();
		wrapper.setCategoryId(null);
		wrapper.setCategoryPath(null);
		wrapper.setCreatedBy(null);
		wrapper.setCreatedDate(null);
		wrapper.setEndDate(null);
		wrapper.setBoostBlockId(null);
		wrapper.setModifiedBy(null);
		wrapper.setModifiedDate(null);
		wrapper.setSearchProfileId(null);
		wrapper.setSearchProfileName(null);
		wrapper.setSearchTerm(null);
		wrapper.setStartDate(null);
		wrapper.setStatus(null);
		wrapper.setBoostBlockId(null);
		wrapper.setBlockProduct(null);
		wrapper.setBoostProduct(null);
		wrappers.add(wrapper);
		return wrappers;
	}
	
	public static  List<BoostBlockProductWrapper> getBoostBlockProductNullWrapper(){
		List<BoostBlockProductWrapper> wrappers = new ArrayList<BoostBlockProductWrapper>();
		BoostBlockProductWrapper wrapper = new BoostBlockProductWrapper();
		wrapper.setPosition(null);
		wrapper.setProductName(null);
		wrapper.setSkuId(null);
		wrappers.add(wrapper);
		return wrappers;
	}
	
	public static List<BoostAndBlock> getNullBoostAndBlocks(){
		List<BoostAndBlock> boostAndBlocks = new ArrayList<BoostAndBlock>();
		BoostAndBlock boostAndBlock = new BoostAndBlock();
		boostAndBlock.setBoostAndBlockProducts(getBoostBlockProductList());
		boostAndBlock.setCategoryId(null);
		boostAndBlock.setCreatedDate(null);
		boostAndBlock.setCreatedBy(null);
		boostAndBlock.setId(null);
		boostAndBlock.setSearchProfileId(null);
		boostAndBlock.setStatus(getStatus());
		boostAndBlock.setTerm(null);
		boostAndBlock.setUpdatedDate(null);
		boostAndBlock.setUpdatedBy(null);
		boostAndBlocks.add(boostAndBlock);
		return boostAndBlocks;
	}
	
	public static List<BoostAndBlock> getBoostAndBlockList(){
		List<BoostAndBlock> boostAndBlocks = new ArrayList<BoostAndBlock>();
		BoostAndBlock boostAndBlock = new BoostAndBlock();
		boostAndBlock.setBoostAndBlockProducts(getBoostBlockProductLists());
		boostAndBlock.setCategoryId(getCategoryNode());
		boostAndBlock.setCreatedDate(new Date());
		boostAndBlock.setCreatedBy(getUsers());
		boostAndBlock.setId(123456789L);
		boostAndBlock.setSearchProfileId(getSearchProfile());
		boostAndBlock.setStatus(getStatus());
		boostAndBlock.setTerm("testTerm");
		boostAndBlock.setUpdatedDate(new Date());
		boostAndBlock.setUpdatedBy(getUsers());
		boostAndBlocks.add(boostAndBlock);
		return boostAndBlocks;
	}
	public static  List<BoostAndBlockProduct> getBoostBlockProductLists(){
		List<BoostAndBlockProduct> wrappers = new ArrayList<BoostAndBlockProduct>();
		BoostAndBlockProduct wrapper = new BoostAndBlockProduct();
		wrapper.setPosition(-1);
		wrapper.setProductName("testProduct");
		wrapper.setSkuId(1L);
		wrapper.setId(2L);
		wrappers.add(wrapper);
		return wrappers;
	}
	
	public static BoostAndBlockWrapper getBoostAndBlockDeletedWrappers(){
		BoostAndBlockWrapper wrapper = new BoostAndBlockWrapper();
		wrapper.setCategoryId("pmcat");
		wrapper.setCategoryPath("Home");
		wrapper.setCreatedBy("a1234567");
		wrapper.setCreatedDate(new Date());
		wrapper.setEndDate(new Date());
		wrapper.setBoostBlockId(123456789L);
		wrapper.setModifiedBy("a1234567");
		wrapper.setModifiedDate(new Date());
		wrapper.setSearchProfileId(1L);
		wrapper.setSearchProfileName("Global");
		wrapper.setSearchTerm("testTerm");
		wrapper.setStartDate(new Date());
		wrapper.setStatus("Deleted");
		wrapper.setBoostBlockId(123456789L);
		wrapper.setBlockProduct(getBoostBlockProductWrapper());
		wrapper.setBoostProduct(getBoostBlockProductWrapper());
		return wrapper;
	}
	
	public static BoostAndBlockWrapper getBoostAndBlockApprovedWrappers(){
		BoostAndBlockWrapper wrapper = new BoostAndBlockWrapper();
		wrapper.setCategoryId("pmcat");
		wrapper.setCategoryPath("Home");
		wrapper.setCreatedBy("a1234567");
		wrapper.setCreatedDate(new Date());
		wrapper.setEndDate(new Date());
		wrapper.setBoostBlockId(123456789L);
		wrapper.setModifiedBy("a1234567");
		wrapper.setModifiedDate(new Date());
		wrapper.setSearchProfileId(1L);
		wrapper.setSearchProfileName("Global");
		wrapper.setSearchTerm("testTerm");
		wrapper.setStartDate(new Date());
		wrapper.setStatus("Approved");
		wrapper.setBoostBlockId(123456789L);
		wrapper.setBlockProduct(getBoostBlockProductWrapper());
		wrapper.setBoostProduct(getBoostBlockProductWrapper());
		return wrapper;
	}
	
	public static BoostAndBlock getDeleteBoostAndBlock(){
		BoostAndBlock boostAndBlock = new BoostAndBlock();
		boostAndBlock.setId(123456789L);
		boostAndBlock.setTerm("testTerm");
		boostAndBlock.setStatus(getDeleteStatus());
		boostAndBlock.setBoostAndBlockProducts(getBoostAndBlockProducts());
		return boostAndBlock;
	}
	
	public static BoostAndBlock getApproveBoostAndBlock(){
		BoostAndBlock boostAndBlock = new BoostAndBlock();
		boostAndBlock.setId(123456789L);
		boostAndBlock.setTerm("testTerm");
		boostAndBlock.setStatus(getApproveStatus());
		boostAndBlock.setBoostAndBlockProducts(getBoostAndBlockProducts());
		return boostAndBlock;
	}
	
	public static BoostAndBlockWrapper getBoostAndBlockRejectedWrappers(){
		BoostAndBlockWrapper wrapper = new BoostAndBlockWrapper();
		wrapper.setCategoryId("pmcat");
		wrapper.setCategoryPath("Home");
		wrapper.setCreatedBy("a1234567");
		wrapper.setCreatedDate(new Date());
		wrapper.setEndDate(new Date());
		wrapper.setBoostBlockId(123456789L);
		wrapper.setModifiedBy("a1234567");
		wrapper.setModifiedDate(new Date());
		wrapper.setSearchProfileId(1L);
		wrapper.setSearchProfileName("Global");
		wrapper.setSearchTerm("testTerm");
		wrapper.setStartDate(new Date());
		wrapper.setStatus("Rejected");
		wrapper.setBoostBlockId(123456789L);
		wrapper.setBlockProduct(getBoostBlockProductWrapper());
		wrapper.setBoostProduct(getBoostBlockProductWrapper());
		return wrapper;
	}
	public static BoostAndBlock getRejectBoostAndBlock(){
		BoostAndBlock boostAndBlock = new BoostAndBlock();
		boostAndBlock.setId(123456789L);
		boostAndBlock.setTerm("testTerm");
		boostAndBlock.setStatus(getRejectStatus());
		boostAndBlock.setBoostAndBlockProducts(getBoostAndBlockProducts());
		return boostAndBlock;
	}
	
	public static PaginationWrapper getPaginationWrapper(){
		PaginationWrapper wrapper = new PaginationWrapper();
		wrapper.setPageIndex(1);
		wrapper.setRowsPerPage(10);
		wrapper.setSortColumn("modifiedDate");
		wrapper.setSortOrder("desc");
		return wrapper;
	}
}
