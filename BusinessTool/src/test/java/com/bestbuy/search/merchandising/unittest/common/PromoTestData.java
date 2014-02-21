package com.bestbuy.search.merchandising.unittest.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bestbuy.search.merchandising.domain.Promo;
import com.bestbuy.search.merchandising.domain.PromoSku;
import com.bestbuy.search.merchandising.domain.Status;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.PromoWrapper;

/**
 * @author Chanchal.Kumari
 * Test Data for testing Promo
 * Date - 4th Oct 2012
 */
public class PromoTestData {
	
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
		 * Created the User Object
		 * @return Users
		 */
		public static Users getUsers(){
			Users user = new Users();
			user.setLoginName("A1003132");
			user.setFirstName("Chanchal");
			user.setLastName("Kumari");
			user.setEmail("Chanchal.Kumari@bestbuy.com");
			return user;
		}
	 
	 public static List<Promo> getPromoList(){
		 List<Promo> promoList = new ArrayList<Promo>();
		 promoList.add(getPromo());
		 return promoList;
	 }
	
	 public static List<IWrapper> getPromoWrappers()
	 {
		List<IWrapper> promoWrappers = new ArrayList<IWrapper>();
		PromoWrapper promoWrapper = new PromoWrapper();
		promoWrapper.setPromoName("test");
		promoWrappers.add(promoWrapper);
		return promoWrappers;
	 }
	 
	 public static PromoWrapper getPromoWrapper(){
		 
		 PromoWrapper promoWrapper = new PromoWrapper();
		 List<String> skuIds = new ArrayList<String>();
		 skuIds.add("2");
		 promoWrapper.setPromoId(1l);
		 promoWrapper.setPromoName("testPromoInTestCases0000000");
		 promoWrapper.setSkuIds(skuIds);
		 promoWrapper.setStatus("Draft");
		 promoWrapper.setCreatedBy("A1003132");
		 promoWrapper.setCreatedDate(new Date());
		 promoWrapper.setModifiedBy("A1003132");
		 promoWrapper.setModifiedDate(new Date());
		 promoWrapper.setStartDate(new Date());
		 promoWrapper.setEndDate(new Date());
		 return promoWrapper;
	 }
	 
	 public static Promo getPromo(){
		 
		 Promo promo = new Promo();
		 promo.setPromoId(1l);
		 promo.setPromoName("test");
		 List<PromoSku> promoSkus = new ArrayList<PromoSku>();
		 PromoSku promoSku = new PromoSku();
		 Promo skuPromo = new Promo();
		 skuPromo.setPromoId(1l);
		 promoSku.setPromo(skuPromo);
		 promoSku.setSkuId("2l");
		 promoSku.setId(1l);
		 promoSkus.add(promoSku);
		 promo.setPromoSku(promoSkus);
		 promo.setStatus(getStatus());
		 promo.setStartDate(new Date());
		 promo.setEndDate(new Date());
		 promo.setCreatedDate(new Date());
		 promo.setCreatedBy(getUsers());
		 promo.setUpdatedDate(new Date());
		 promo.setUpdatedBy(getUsers());
		
		 return promo;
	 }
	 
	 public static List<PromoSku> getPromoSkus(){
		 	List<PromoSku> promoSkus = new ArrayList<PromoSku>();
			PromoSku promoSku = new PromoSku();
			promoSku.setId(1l);
			promoSku.setPromo(getPromo());
			promoSku.setSkuId("2l");
			promoSkus.add(promoSku);
			return promoSkus;
		}
}
