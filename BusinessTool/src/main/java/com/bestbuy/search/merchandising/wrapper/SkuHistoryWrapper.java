/**
 * 
 */
package com.bestbuy.search.merchandising.wrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.BeanUtils;

import com.bestbuy.search.merchandising.common.JsonDateSerializer;
import com.bestbuy.search.merchandising.domain.SkuHistory;

/**
 * @author Sreedhar Patlola
 */
public class SkuHistoryWrapper implements IWrapper {

  private String skuId;

  private String userId;

  private String action;

  private Date time;

  /**
   * @return the skuId
   */
  public String getSkuId() {
    return skuId;
  }

  /**
   * @param skuId
   *          the skuId to set
   */
  public void setSkuId(String skuId) {
    this.skuId = skuId;
  }

  /**
   * @return the userId
   */
  public String getUserId() {
    return userId;
  }

  /**
   * @param userId
   *          the userId to set
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * @return the action
   */
  public String getAction() {
    return action;
  }

  /**
   * @param action
   *          the action to set
   */
  public void setAction(String action) {
    this.action = action;
  }

  /**
   * @return the time
   */
  @JsonSerialize(using = JsonDateSerializer.class)
  public Date getTime() {
    return time;
  }

  /**
   * @param time
   *          the time to set
   */
  public void setTime(Date time) {
    this.time = time;
  }

  @Override
  public int compareTo(IWrapper arg0) {
    // TODO Auto-generated method stub
    return 0;
  }

  public static List<IWrapper> getSkus(List<SkuHistory> skus) {

    List<IWrapper> skuHistoryWrappers = new ArrayList<IWrapper>();
    for (SkuHistory skuHistory : skus) {
      skuHistoryWrappers.add(getSkuHistory(skuHistory));
    }

    return skuHistoryWrappers;
  }

  public static IWrapper getSkuHistory(SkuHistory skuHistory) {
    SkuHistoryWrapper skuHistoryWrapper = new SkuHistoryWrapper();
    BeanUtils.copyProperties(skuHistory, skuHistoryWrapper);
    skuHistoryWrapper.setUserId(skuHistory.getAddedBy().getLoginName());
    return skuHistoryWrapper;

  }

}
