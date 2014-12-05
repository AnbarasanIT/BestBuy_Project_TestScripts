package com.bestbuy.search.bt.uiconstants;

import java.lang.reflect.Field;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class where constants of MerchandisingUIData.xml are defined
 */

public class MerchandisingUIData {

	private Log log = LogFactory.getLog("MerchandisingUIData");

	private String synPrimaryTermData = "PrimaryTermData";
	private String synTermsData = "TermsData";
	private String synTermsData1 = "TermsData1";
	private String synSearchValue = "Syn_Search_Value";
	private String startDateData = "StartDateData";
	private String endDateData = "EndDateData";
	private String startDateYearData = "StartDate_Year_Data";
	private String endDateYearData = "EndDate_Year_Data";
	private String timeHourData = "Time_Hour_Data";
	private String timeMinuteData = "Time_Minute_Data";
	private String synInvalidSearchValue = "Syn_InValid_Search_Value";
	private String synSearchDropDownData = "Syn_Search_DropDown_Value";
	private String synPrimaryColumnSearchFieldData = "Syn_PrimaryColumn_SearchField_Data";

	private String krdRedirectTermData = "rd_redirectTerm_value";
	private String krdRiderectUrlValue = "rd_RedirectUrl_value";
	private String krdSearchProfileData = "rd_SearchProfile_value";
	private String rdInvalidSearchData = "rd_Invalid_Search_Data";
	private String krdUpdateRedirectTermData = "rd_Update_redirectTerm_value";
	private String krdSearchData = "rd_Search_Data";
	private String krdSearchDropDownData = "rd_Search_DropDown_Data";
	private String krdTermsSearchFieldData = "rd_TermsSearchField_Data";

	private String bannerName = "banners_Banner_Name";
	private String bannerPosition = "banners_Banner_Position";
	private String bannerPriority = "banners_Banner_Priority";
	private String bannerStartDate = "banners_Banner_StartDate";
	private String bannerEndDate = "banners_Banner_EndDate";
	private String bannerContextKeyword = "banners_Context_KeyWord";
	private String bannerContextSearchProfile = "banners_Context_SearchProfile";
	private String bannerThemetemplate = "banners_Theme_Template";
	private String bannerThemeAssetId = "banners_Theme_AssetID";
	private String bannerUpdateName = "banners_Update_BannerName";
	private String bannerSearchData = "banners_Search_Data";
	private String bannerSearchDropDownData = "banners_Search_DropDown_Data";
	private String bannersBannersNameSearchData = "banners_BannersNameSearch_Data";
	private String bannersKeywordSearch = "banners_KeywordSearch";

	private String promoCreateName = "promo_Create_PromoName_Data";
	private String promoCreateSkuidData = "promo_Create_PromoSKUID_Data";
	private String promoCreateStartDate = "promo_Create_StartDate";
	private String promoCreateEndDate = "promo_Create_EndDate";
	private String promoUpdatedName = "promo_Update_Promo_Name";
	private String promoSearchData = "promo_Search_PromoName_Data";
	private String promoSearchDropDownData = "promo_Search_DropDown_Data";
	private String promoNameSearchFieldData = "promo_promoNameSearchField_Data";
	private String promoNameInvalidSearch = "promo_promoNameInvalidSearch_Data";

	private String facetsSearchData = "facets_Search_Data";
	private String facetsSystemNameSearchData = "facetsSystemNameSearchData";
	private String facetsInvalidSearchData = "facets_Invalid_Search_Data";
	private String facetsSearchDropDownData = "facets_Search_DropDown_Data";
	private String facetsSystemNameData = "facets_Create_SysName_Data";
	private String facetsDisplayNameData = "facets_Create_DispName_Data";
	private String facetsUpdateDisplayNameData = "facets_Update_DispName_Data";
	private String facetsAssetData = "facets_Create_AssetData";
	private String facetsUpdatedAssetData = "facets_Updated_AssetData";
	private String facetsMinValue = "facets_Create_MinValue";
	private String facetsMaxValue = "facets_Create_MaxValue";
	private String facetsSystemNameSearchFieldData = "facets_SystemNameSearchField_Data";
	private String facetsSystemNameInvalidSearchData = "facets_SystemNameInvalidSearch_Data";
	private String facetsSystemNameData1 = "facets_Create_SysName_Data1";
	private String facetsDisplayNameData1 = "facets_Create_DispName_Data1";
	private String facetOrderValueSearchData="facets_OrderValue_SearchData";

	private String facetOrderingTextFieldData="fo_textField_Data";
	private String facetOrderingTextFieldData1="fo_textField_Data1";

	private String bbSearchData = "bnb_Search_Data";
	private String bbSearchNotAvailableData = "bnb_Search_Not_Available_Data";
	private String bbCreateSearchData = "bnb_Create_Search_Data";
	private String bbCreateSuccessfullText = "bnb_Create_Successfully_Text";
	private String bbUpdatedSuccessfullText = "bnb_Update_Successfully_Text";
	private String bbDeletedSuccessfullText = "bnb_deleted_Successfully_Text";
	private String bbApprovedSuccessfullText = "bnb_Approved_Successfully_Text";
	private String bbSearchDropDownData = "bnb_Search_DropDown_Data";
	private String bbSearchTermsSearchFieldData = "bnb_SearchTermsSearchField_Data";
	private String bbSearchInvalidTerms = "bnb_SearchTermsInvalidSearch_Data";
	private String userFirstNameUpdatedData = "user_FirstName_UpdateData";
	private String userLastNameUpdatedData = "user_LastName_UpdateData";
	private String updatedUserId = "user_UpdateID";
	private String userSearchData = "user_Search_Data";
	private String userInvalidSearchData = "user_InValid_Search_Data";
	private String userSearchDropDownData = "user_Search_DropDown_Data";

	private String validationMsgInvalidSearch = "validation_Msg_InvalidSearch";
	private String synonymsValidationMsgCreate = "synonyms_validation_Msg_Create";
	private String synonymsValidationMsgUpdate = "synonyms_validation_Msg_Update";
	private String synonymsValidationMsgApprove = "synonyms_validation_Msg_Approve";
	private String synonymsValidationMsgReject = "synonyms_validation_Msg_Reject";
	private String synonymsValidationMsgDelete = "synonyms_validation_Msg_Delete";

	private String keywordValidationMsgCreate = "keyword_validation_Msg_Create";
	private String keywordValidationMsgUpdate = "keyword_validation_Msg_Update";
	private String keywordValidationMsgApprove = "keyword_validation_Msg_Approve";
	private String keywordValidationMsgReject = "keyword_validation_Msg_Reject";
	private String keywordValidationMsgDelete = "keyword_validation_Msg_Delete";

	private String bannersValidationMsgCreate = "banners_validation_Msg_Create";
	private String bannersValidationMsgUpdate = "banners_validation_Msg_Update";
	private String bannersValidationMsgApprove = "banners_validation_Msg_Approve";
	private String bannersValidationMsgReject = "banners_validation_Msg_Reject";
	private String bannersValidationMsgDelete = "banners_validation_Msg_Delete";

	private String promoValidationMsgCreate = "promo_validation_Msg_Create";
	private String promoValidationMsgUpdate = "promo_validation_Msg_Update";
	private String promoValidationMsgApprove = "promo_validation_Msg_Approve";
	private String promoValidationMsgReject = "promo_validation_Msg_Reject";
	private String promoValidationMsgDelete = "promo_validation_Msg_Delete";

	private String facetsValidationMsgCreate = "facets_validation_Msg_Create";
	private String facetsValidationMsgUpdate = "facets_validation_Msg_Update";
	private String facetsValidationMsgApprove = "facets_validation_Msg_Approve";
	private String facetsValidationMsgReject = "facets_validation_Msg_Reject";
	private String facetsValidationMsgDelete = "facets_validation_Msg_Delete";

	private String bnbValidationMsgCreate = "bnb_validation_Msg_Create";
	private String bnbValidationMsgUpdate = "bnb_validation_Msg_Update";
	private String bnbValidationMsgApprove = "bnb_validation_Msg_Approve";
	private String bnbValidationMsgReject = "bnb_validation_Msg_Reject";
	private String bnbValidationMsgDelete = "bnb_validation_Msg_Delete";

	private String userValidationMsgCreate = "user_validation_Msg_Create";
	private String userValidationMsgUpdate = "user_validation_Msg_Update";



	/**
	 * Method to read and assign data to variables
	 */

	public MerchandisingUIData() {
		try {
			ReadXMLFile readXml = new ReadXMLFile();
			readXml.loadMerchandisingUIData();
			Field[] arrayOfField = this.getClass().getDeclaredFields();
			for (Field field : arrayOfField) {
				field.setAccessible(true);
				Object localObject = field.get(this);
				if (localObject instanceof String) {
					field.set(this, readXml.getValue((String) localObject));
				}
			}
		} catch (Exception localException) {
			log.info("Exception in MerchandisingUIData"
					+ localException.getMessage());
		}
	}

	public String getSynPrimaryTermData() {
		return synPrimaryTermData;
	}

	public void setSynPrimaryTermData(String synPrimaryTermData) {
		this.synPrimaryTermData = synPrimaryTermData;
	}

	public String getSynTermsData() {
		return synTermsData;
	}

	public void setSynTermsData(String synTermsData) {
		this.synTermsData = synTermsData;
	}

	public String getSynTermsData1() {
		return synTermsData1;
	}

	public void setSynTermsData1(String synTermsData1) {
		this.synTermsData1 = synTermsData1;
	}

	public String getStartDateData() {
		return startDateData;
	}

	public void setStartDateData(String startDateData) {
		this.startDateData = startDateData;
	}

	public String getEndDateData() {
		return endDateData;
	}

	public void setEndDateData(String endDateData) {
		this.endDateData = endDateData;
	}

	public String getStartDateYearData() {
		return startDateYearData;
	}

	public void setStartDateYearData(String startDateYearData) {
		this.startDateYearData = startDateYearData;
	}

	public String getEndDateYearData() {
		return endDateYearData;
	}

	public void setEndDateYearData(String endDateYearData) {
		this.endDateYearData = endDateYearData;
	}

	public String getTimeHourData() {
		return timeHourData;
	}

	public void setTimeHourData(String timeHourData) {
		this.timeHourData = timeHourData;
	}

	public String getTimeMinuteData() {
		return timeMinuteData;
	}

	public void setTimeMinuteData(String timeMinuteData) {
		this.timeMinuteData = timeMinuteData;
	}

	public String getSynSearchValue() {
		return synSearchValue;
	}

	public void setSynSearchValue(String synSearchValue) {
		this.synSearchValue = synSearchValue;
	}

	public String getSynInvalidSearchValue() {
		return synInvalidSearchValue;
	}

	public void setSynInvalidSearchValue(String synInvalidSearchValue) {
		this.synInvalidSearchValue = synInvalidSearchValue;
	}

	public String getSynSearchDropDownData() {
		return synSearchDropDownData;
	}

	public void setSynSearchDropDownData(String synSearchDropDownData) {
		this.synSearchDropDownData = synSearchDropDownData;
	}

	public String getKrdRedirectTermData() {
		return krdRedirectTermData;
	}

	public void setKrdRedirectTermData(String krdRedirectTermData) {
		this.krdRedirectTermData = krdRedirectTermData;
	}

	public String getKrdRiderectUrlValue() {
		return krdRiderectUrlValue;
	}

	public void setKrdRiderectUrlValue(String krdRiderectUrlValue) {
		this.krdRiderectUrlValue = krdRiderectUrlValue;
	}

	public String getKrdSearchProfileData() {
		return krdSearchProfileData;
	}

	public void setKrdSearchProfileData(String krdSearchProfileData) {
		this.krdSearchProfileData = krdSearchProfileData;
	}

	public String getKrdUpdateRedirectTermData() {
		return krdUpdateRedirectTermData;
	}

	public void setKrdUpdateRedirectTermData(String krdUpdateRedirectTermData) {
		this.krdUpdateRedirectTermData = krdUpdateRedirectTermData;
	}

	public String getKrdSearchData() {
		return krdSearchData;
	}

	public void setKrdSearchData(String krdSearchData) {
		this.krdSearchData = krdSearchData;
	}

	public String getRdInvalidSearchData() {
		return rdInvalidSearchData;
	}

	public void setRdInvalidSearchData(String rdInvalidSearchData) {
		this.rdInvalidSearchData = rdInvalidSearchData;
	}

	public String getKrdSearchDropDownData() {
		return krdSearchDropDownData;
	}

	public void setKrdSearchDropDownData(String krdSearchDropDownData) {
		this.krdSearchDropDownData = krdSearchDropDownData;
	}

	public String getBannerName() {
		return bannerName;
	}

	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}

	public String getBannerPosition() {
		return bannerPosition;
	}

	public void setBannerPosition(String bannerPosition) {
		this.bannerPosition = bannerPosition;
	}

	public String getBannerPriority() {
		return bannerPriority;
	}

	public void setBannerPriority(String bannerPriority) {
		this.bannerPriority = bannerPriority;
	}

	public String getBannerStartDate() {
		return bannerStartDate;
	}

	public void setBannerStartDate(String bannerStartDate) {
		this.bannerStartDate = bannerStartDate;
	}

	public String getBannerEndDate() {
		return bannerEndDate;
	}

	public void setBannerEndDate(String bannerEndDate) {
		this.bannerEndDate = bannerEndDate;
	}

	public String getBannerContextKeyword() {
		return bannerContextKeyword;
	}

	public void setBannerContextKeyword(String bannerContextKeyword) {
		this.bannerContextKeyword = bannerContextKeyword;
	}

	public String getBannerContextSearchProfile() {
		return bannerContextSearchProfile;
	}

	public void setBannerContextSearchProfile(String bannerContextSearchProfile) {
		this.bannerContextSearchProfile = bannerContextSearchProfile;
	}

	public String getBannerThemetemplate() {
		return bannerThemetemplate;
	}

	public void setBannerThemetemplate(String bannerThemetemplate) {
		this.bannerThemetemplate = bannerThemetemplate;
	}

	public String getBannerThemeAssetId() {
		return bannerThemeAssetId;
	}

	public void setBannerThemeAssetId(String bannerThemeAssetId) {
		this.bannerThemeAssetId = bannerThemeAssetId;
	}

	public String getBannerUpdateName() {
		return bannerUpdateName;
	}

	public void setBannerUpdateName(String bannerUpdateName) {
		this.bannerUpdateName = bannerUpdateName;
	}

	public String getBannerSearchData() {
		return bannerSearchData;
	}

	public void setBannerSearchData(String bannerSearchData) {
		this.bannerSearchData = bannerSearchData;
	}

	public String getBannerSearchDropDownData() {
		return bannerSearchDropDownData;
	}

	public void setBannerSearchDropDownData(String bannerSearchDropDownData) {
		this.bannerSearchDropDownData = bannerSearchDropDownData;
	}

	public String getPromoCreateName() {
		return promoCreateName;
	}

	public void setPromoCreateName(String promoCreateName) {
		this.promoCreateName = promoCreateName;
	}

	public String getPromoCreateSkuidData() {
		return promoCreateSkuidData;
	}

	public void setPromoCreateSkuidData(String promoCreateSkuidData) {
		this.promoCreateSkuidData = promoCreateSkuidData;
	}

	public String getPromoCreateStartDate() {
		return promoCreateStartDate;
	}

	public void setPromoCreateStartDate(String promoCreateStartDate) {
		this.promoCreateStartDate = promoCreateStartDate;
	}

	public String getPromoCreateEndDate() {
		return promoCreateEndDate;
	}

	public void setPromoCreateEndDate(String promoCreateEndDate) {
		this.promoCreateEndDate = promoCreateEndDate;
	}

	public String getPromoUpdatedName() {
		return promoUpdatedName;
	}

	public void setPromoUpdatedName(String promoUpdatedName) {
		this.promoUpdatedName = promoUpdatedName;
	}

	public String getPromoSearchData() {
		return promoSearchData;
	}

	public void setPromoSearchData(String promoSearchData) {
		this.promoSearchData = promoSearchData;
	}

	public String getPromoSearchDropDownData() {
		return promoSearchDropDownData;
	}

	public void setPromoSearchDropDownData(String promoSearchDropDownData) {
		this.promoSearchDropDownData = promoSearchDropDownData;
	}

	public String getFacetsSearchData() {
		return facetsSearchData;
	}

	public void setFacetsSearchData(String facetsSearchData) {
		this.facetsSearchData = facetsSearchData;
	}

	public String getFacetsInvalidSearchData() {
		return facetsInvalidSearchData;
	}

	public void setFacetsInvalidSearchData(String facetsInvalidSearchData) {
		this.facetsInvalidSearchData = facetsInvalidSearchData;
	}

	public String getFacetsSystemNameSearchData() {
		return facetsSystemNameSearchData;
	}

	public void setFacetsSystemNameSearchData(String FacetsSystemNameSearchData) {
		this.facetsSystemNameSearchData = FacetsSystemNameSearchData;
	}

	public String getFacetsSearchDropDownData() {
		return facetsSearchDropDownData;
	}

	public void setFacetsSearchDropDownData(String facetsSearchDropDownData) {
		this.facetsSearchDropDownData = facetsSearchDropDownData;
	}

	public String getFacetsSystemNameData() {
		return facetsSystemNameData;
	}

	public void setFacetsSystemNameData(String facetsSystemNameData) {
		this.facetsSystemNameData = facetsSystemNameData;
	}

	public String getFacetsDisplayNameData() {
		return facetsDisplayNameData;
	}

	public void setFacetsDisplayNameData(String facetsDisplayNameData) {
		this.facetsDisplayNameData = facetsDisplayNameData;
	}

	public String getFacetsAssetData() {
		return facetsAssetData;
	}

	public void setFacetsAssetData(String facetsAssetData) {
		this.facetsAssetData = facetsAssetData;
	}

	public String getFacetsUpdatedAssetData() {
		return facetsUpdatedAssetData;
	}

	public void setFacetsUpdatedAssetData(String facetsUpdatedAssetData) {
		this.facetsUpdatedAssetData = facetsUpdatedAssetData;
	}

	public String getFacetsMinValue() {
		return facetsMinValue;
	}

	public void setFacetsMinValue(String facetsMinValue) {
		this.facetsMinValue = facetsMinValue;
	}

	public String getFacetsMaxValue() {
		return facetsMaxValue;
	}

	public void setFacetsMaxValue(String facetsMaxValue) {
		this.facetsMaxValue = facetsMaxValue;
	}

	public String getFacetsUpdateDisplayNameData() {
		return facetsUpdateDisplayNameData;
	}

	public void setFacetsUpdateDisplayNameData(
			String facetsUpdateDisplayNameData) {
		this.facetsUpdateDisplayNameData = facetsUpdateDisplayNameData;
	}
	public String getFacetOrderingTextFieldData() {
		return facetOrderingTextFieldData;
	}

	public void setFacetOrderingTextFieldData(String facetOrderingTextFieldData) {
		this.facetOrderingTextFieldData = facetOrderingTextFieldData;
	}

	public String getFacetOrderingTextFieldData1() {
		return facetOrderingTextFieldData1;
	}

	public void setFacetOrderingTextFieldData1(String facetOrderingTextFieldData1) {
		this.facetOrderingTextFieldData1 = facetOrderingTextFieldData1;
	}


	public String getBbSearchData() {
		return bbSearchData;
	}

	public void setBbSearchData(String bbSearchData) {
		this.bbSearchData = bbSearchData;
	}

	public String getBbCreateSearchData() {
		return bbCreateSearchData;
	}

	public void setBbCreateSearchData(String bbCreateSearchData) {
		this.bbCreateSearchData = bbCreateSearchData;
	}

	public String getBbCreateSuccessfullText() {
		return bbCreateSuccessfullText;
	}

	public void setBbCreateSuccessfullText(String bbCreateSuccessfullText) {
		this.bbCreateSuccessfullText = bbCreateSuccessfullText;
	}

	public String getBbUpdatedSuccessfullText() {
		return bbUpdatedSuccessfullText;
	}

	public void setBbUpdatedSuccessfullText(String bbUpdatedSuccessfullText) {
		this.bbUpdatedSuccessfullText = bbUpdatedSuccessfullText;
	}

	public String getBbApprovedSuccessfullText() {
		return bbApprovedSuccessfullText;
	}

	public void setBbApprovedSuccessfullText(String bbApprovedSuccessfullText) {
		this.bbApprovedSuccessfullText = bbApprovedSuccessfullText;
	}

	public String getBbSearchDropDownData() {
		return bbSearchDropDownData;
	}

	public void setBbSearchDropDownData(String bbSearchDropDownData) {
		this.bbSearchDropDownData = bbSearchDropDownData;
	}

	public String getBbSearchNotAvailableData() {
		return bbSearchNotAvailableData;
	}

	public void setBbSearchNotAvailableData(String bbSearchNotAvailableData) {
		this.bbSearchNotAvailableData = bbSearchNotAvailableData;
	}

	public String getBbDeletedSuccessfullText() {
		return bbDeletedSuccessfullText;
	}

	public void setBbDeletedSuccessfullText(String bbDeletedSuccessfullText) {
		this.bbDeletedSuccessfullText = bbDeletedSuccessfullText;
	}

	public String getUserFirstNameUpdatedData() {
		return userFirstNameUpdatedData;
	}

	public void setUserFirstNameUpdatedData(String userFirstNameUpdatedData) {
		this.userFirstNameUpdatedData = userFirstNameUpdatedData;
	}

	public String getUserLastNameUpdatedData() {
		return userLastNameUpdatedData;
	}

	public void setUserLastNameUpdatedData(String userLastNameUpdatedData) {
		this.userLastNameUpdatedData = userLastNameUpdatedData;
	}

	public String getUpdatedUserId() {
		return updatedUserId;
	}

	public void setUpdatedUserId(String updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	public String getUserSearchData() {
		return userSearchData;
	}

	public void setUserSearchData(String userSearchData) {
		this.userSearchData = userSearchData;
	}

	public String getUserInvalidSearchData() {
		return userInvalidSearchData;
	}

	public void setUserInvalidSearchData(String userInvalidSearchData) {
		this.userInvalidSearchData = userInvalidSearchData;
	}

	public String getUserSearchDropDownData() {
		return userSearchDropDownData;
	}

	public void setUserSearchDropDownData(String userSearchDropDownData) {
		this.userSearchDropDownData = userSearchDropDownData;
	}

	public String getSynPrimaryColumnSearchFieldData() {
		return synPrimaryColumnSearchFieldData;
	}

	public void setSynPrimaryColumnSearchFieldData(
			String synPrimaryColumnSearchFieldData) {
		this.synPrimaryColumnSearchFieldData = synPrimaryColumnSearchFieldData;
	}

	public String getKrdTermsSearchFieldData() {
		return krdTermsSearchFieldData;
	}

	public void setKrdTermsSearchFieldData(String krdTermsSearchFieldData) {
		this.krdTermsSearchFieldData = krdTermsSearchFieldData;
	}

	public String getBannersBannersNameSearchData() {
		return bannersBannersNameSearchData;
	}

	public void setBannersBannersNameSearchData(
			String bannersBannersNameSearchData) {
		this.bannersBannersNameSearchData = bannersBannersNameSearchData;
	}

	public String getBannersKeywordSearch() {
		return bannersKeywordSearch;
	}

	public void setBannersKeywordSearch(String bannersKeywordSearch) {
		this.bannersKeywordSearch = bannersKeywordSearch;
	}

	public String getPromoNameSearchFieldData() {
		return promoNameSearchFieldData;
	}

	public void setPromoNameSearchFieldData(String promoNameSearchFieldData) {
		this.promoNameSearchFieldData = promoNameSearchFieldData;
	}

	public String getPromoNameInvalidSearch() {
		return promoNameInvalidSearch;
	}

	public void setPromoNameInvalidSearch(String promoNameInvalidSearch) {
		this.promoNameInvalidSearch = promoNameInvalidSearch;
	}

	public String getFacetsSystemNameSearchFieldData() {
		return facetsSystemNameSearchFieldData;
	}

	public void setFacetsSystemNameSearchFieldData(
			String facetsSystemNameSearchFieldData) {
		this.facetsSystemNameSearchFieldData = facetsSystemNameSearchFieldData;
	}

	public String getFacetsSystemNameInvalidSearchData() {
		return facetsSystemNameInvalidSearchData;
	}

	public void setFacetsSystemNameInvalidSearchData(
			String facetsSystemNameInvalidSearchData) {
		this.facetsSystemNameInvalidSearchData = facetsSystemNameInvalidSearchData;
	}

	public String getFacetsSystemNameData1() {
		return facetsSystemNameData1;
	}

	public void setFacetsSystemNameData1(String facetsSystemNameData1) {
		this.facetsSystemNameData1 = facetsSystemNameData1;
	}
	
	public String getFacetOrderValueSearchData() {
		return facetOrderValueSearchData;
	}

	public void setFacetOrderValueSearchData(String facetOrderValueSearchData) {
		this.facetOrderValueSearchData = facetOrderValueSearchData;
	}

	public String getFacetsDisplayNameData1() {
		return facetsDisplayNameData1;
	}

	public void setFacetsDisplayNameData1(String facetsDisplayNameData1) {
		this.facetsDisplayNameData1 = facetsDisplayNameData1;
	}



	public String getBbSearchTermsSearchFieldData() {
		return bbSearchTermsSearchFieldData;
	}

	public void setBbSearchTermsSearchFieldData(
			String bbSearchTermsSearchFieldData) {
		this.bbSearchTermsSearchFieldData = bbSearchTermsSearchFieldData;
	}

	public String getBbSearchInvalidTerms() {
		return bbSearchInvalidTerms;
	}

	public void setBbSearchInvalidTerms(String bbSearchInvalidTerms) {
		this.bbSearchInvalidTerms = bbSearchInvalidTerms;
	}

	public String getSynonymsValidationMsgCreate() {
		return synonymsValidationMsgCreate;
	}

	public void setSynonymsValidationMsgCreate(String synonymsValidationMsgCreate) {
		this.synonymsValidationMsgCreate = synonymsValidationMsgCreate;
	}

	public String getSynonymsValidationMsgUpdate() {
		return synonymsValidationMsgUpdate;
	}

	public void setSynonymsValidationMsgUpdate(String synonymsValidationMsgUpdate) {
		this.synonymsValidationMsgUpdate = synonymsValidationMsgUpdate;
	}

	public String getSynonymsValidationMsgApprove() {
		return synonymsValidationMsgApprove;
	}

	public void setSynonymsValidationMsgApprove(String synonymsValidationMsgApprove) {
		this.synonymsValidationMsgApprove = synonymsValidationMsgApprove;
	}

	public String getSynonymsValidationMsgReject() {
		return synonymsValidationMsgReject;
	}

	public void setSynonymsValidationMsgReject(String synonymsValidationMsgReject) {
		this.synonymsValidationMsgReject = synonymsValidationMsgReject;
	}

	public String getSynonymsValidationMsgDelete() {
		return synonymsValidationMsgDelete;
	}

	public void setSynonymsValidationMsgDelete(String synonymsValidationMsgDelete) {
		this.synonymsValidationMsgDelete = synonymsValidationMsgDelete;
	}

	public String getValidationMsgInvalidSearch() {
		return validationMsgInvalidSearch;
	}

	public void setValidationMsgInvalidSearch(String validationMsgInvalidSearch) {
		this.validationMsgInvalidSearch = validationMsgInvalidSearch;
	}


	public String getKeywordValidationMsgCreate() {
		return keywordValidationMsgCreate;
	}

	public void setKeywordValidationMsgCreate(String keywordValidationMsgCreate) {
		this.keywordValidationMsgCreate = keywordValidationMsgCreate;
	}

	public String getKeywordValidationMsgUpdate() {
		return keywordValidationMsgUpdate;
	}

	public void setKeywordValidationMsgUpdate(String keywordValidationMsgUpdate) {
		this.keywordValidationMsgUpdate = keywordValidationMsgUpdate;
	}

	public String getKeywordValidationMsgApprove() {
		return keywordValidationMsgApprove;
	}

	public void setKeywordValidationMsgApprove(String keywordValidationMsgApprove) {
		this.keywordValidationMsgApprove = keywordValidationMsgApprove;
	}

	public String getKeywordValidationMsgReject() {
		return keywordValidationMsgReject;
	}

	public void setKeywordValidationMsgReject(String keywordValidationMsgReject) {
		this.keywordValidationMsgReject = keywordValidationMsgReject;
	}

	public String getKeywordValidationMsgDelete() {
		return keywordValidationMsgDelete;
	}

	public void setKeywordValidationMsgDelete(String keywordValidationMsgDelete) {
		this.keywordValidationMsgDelete = keywordValidationMsgDelete;
	}

	public String getBannersValidationMsgCreate() {
		return bannersValidationMsgCreate;
	}

	public void setBannersValidationMsgCreate(String bannersValidationMsgCreate) {
		this.bannersValidationMsgCreate = bannersValidationMsgCreate;
	}

	public String getBannersValidationMsgUpdate() {
		return bannersValidationMsgUpdate;
	}

	public void setBannersValidationMsgUpdate(String bannersValidationMsgUpdate) {
		this.bannersValidationMsgUpdate = bannersValidationMsgUpdate;
	}

	public String getBannersValidationMsgApprove() {
		return bannersValidationMsgApprove;
	}

	public void setBannersValidationMsgApprove(String bannersValidationMsgApprove) {
		this.bannersValidationMsgApprove = bannersValidationMsgApprove;
	}

	public String getBannersValidationMsgReject() {
		return bannersValidationMsgReject;
	}

	public void setBannersValidationMsgReject(String bannersValidationMsgReject) {
		this.bannersValidationMsgReject = bannersValidationMsgReject;
	}

	public String getBannersValidationMsgDelete() {
		return bannersValidationMsgDelete;
	}

	public void setBannersValidationMsgDelete(String bannersValidationMsgDelete) {
		this.bannersValidationMsgDelete = bannersValidationMsgDelete;
	}

	public String getPromoValidationMsgCreate() {
		return promoValidationMsgCreate;
	}

	public void setPromoValidationMsgCreate(String promoValidationMsgCreate) {
		this.promoValidationMsgCreate = promoValidationMsgCreate;
	}

	public String getPromoValidationMsgUpdate() {
		return promoValidationMsgUpdate;
	}

	public void setPromoValidationMsgUpdate(String promoValidationMsgUpdate) {
		this.promoValidationMsgUpdate = promoValidationMsgUpdate;
	}

	public String getPromoValidationMsgApprove() {
		return promoValidationMsgApprove;
	}

	public void setPromoValidationMsgApprove(String promoValidationMsgApprove) {
		this.promoValidationMsgApprove = promoValidationMsgApprove;
	}

	public String getPromoValidationMsgReject() {
		return promoValidationMsgReject;
	}

	public void setPromoValidationMsgReject(String promoValidationMsgReject) {
		this.promoValidationMsgReject = promoValidationMsgReject;
	}

	public String getPromoValidationMsgDelete() {
		return promoValidationMsgDelete;
	}

	public void setPromoValidationMsgDelete(String promoValidationMsgDelete) {
		this.promoValidationMsgDelete = promoValidationMsgDelete;
	}

	public String getFacetsValidationMsgCreate() {
		return facetsValidationMsgCreate;
	}

	public void setFacetsValidationMsgCreate(String facetsValidationMsgCreate) {
		this.facetsValidationMsgCreate = facetsValidationMsgCreate;
	}

	public String getFacetsValidationMsgUpdate() {
		return facetsValidationMsgUpdate;
	}

	public void setFacetsValidationMsgUpdate(String facetsValidationMsgUpdate) {
		this.facetsValidationMsgUpdate = facetsValidationMsgUpdate;
	}

	public String getFacetsValidationMsgApprove() {
		return facetsValidationMsgApprove;
	}

	public void setFacetsValidationMsgApprove(String facetsValidationMsgApprove) {
		this.facetsValidationMsgApprove = facetsValidationMsgApprove;
	}

	public String getFacetsValidationMsgReject() {
		return facetsValidationMsgReject;
	}

	public void setFacetsValidationMsgReject(String facetsValidationMsgReject) {
		this.facetsValidationMsgReject = facetsValidationMsgReject;
	}

	public String getFacetsValidationMsgDelete() {
		return facetsValidationMsgDelete;
	}

	public void setFacetsValidationMsgDelete(String facetsValidationMsgDelete) {
		this.facetsValidationMsgDelete = facetsValidationMsgDelete;
	}

	public String getBnbValidationMsgCreate() {
		return bnbValidationMsgCreate;
	}

	public void setBnbValidationMsgCreate(String bnbValidationMsgCreate) {
		this.bnbValidationMsgCreate = bnbValidationMsgCreate;
	}

	public String getBnbValidationMsgUpdate() {
		return bnbValidationMsgUpdate;
	}

	public void setBnbValidationMsgUpdate(String bnbValidationMsgUpdate) {
		this.bnbValidationMsgUpdate = bnbValidationMsgUpdate;
	}

	public String getBnbValidationMsgApprove() {
		return bnbValidationMsgApprove;
	}

	public void setBnbValidationMsgApprove(String bnbValidationMsgApprove) {
		this.bnbValidationMsgApprove = bnbValidationMsgApprove;
	}

	public String getBnbValidationMsgReject() {
		return bnbValidationMsgReject;
	}

	public void setBnbValidationMsgReject(String bnbValidationMsgReject) {
		this.bnbValidationMsgReject = bnbValidationMsgReject;
	}

	public String getBnbValidationMsgDelete() {
		return bnbValidationMsgDelete;
	}

	public void setBnbValidationMsgDelete(String bnbValidationMsgDelete) {
		this.bnbValidationMsgDelete = bnbValidationMsgDelete;
	}

	public String getUserValidationMsgCreate() {
		return userValidationMsgCreate;
	}

	public void setUserValidationMsgCreate(String userValidationMsgCreate) {
		this.userValidationMsgCreate = userValidationMsgCreate;
	}

	public String getUserValidationMsgUpdate() {
		return userValidationMsgUpdate;
	}

	public void setUserValidationMsgUpdate(String userValidationMsgUpdate) {
		this.userValidationMsgUpdate = userValidationMsgUpdate;
	}







}
