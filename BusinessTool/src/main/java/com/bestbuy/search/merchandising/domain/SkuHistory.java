/**
 * 
 */
package com.bestbuy.search.merchandising.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author Sreedhar Patlola
 */
@Entity
@Table(name = "SKU_HISTORY")
@SequenceGenerator(sequenceName = "SKU_HISTORY_SEQ", name = "SKU_HISTORY_SEQ_GEN")
public class SkuHistory implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "SKU_HISTORY_SEQ_GEN", strategy = GenerationType.SEQUENCE)
  @Column(name = "ID", length = 19)
  private Integer id;

  @Column(name = "SKU_ID")
  private String skuId;

  @OneToOne
  @JoinColumn(name = "ADDED_BY", referencedColumnName = "USER_ID")
  private Users addedBy;

  @Column(name = "ACTION")
  private String action;

  @Column(name = "TIME")
  private Date time;

  /**
   * @return the id
   */
  public Integer getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Integer id) {
    this.id = id;
  }

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
   * @return the addedBy
   */
  public Users getAddedBy() {
    return addedBy;
  }

  /**
   * @param addedBy
   *          the addedBy to set
   */
  public void setAddedBy(Users addedBy) {
    this.addedBy = addedBy;
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

  /*
   * (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SkuHistory other = (SkuHistory) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("SkuHistory [id=");
    builder.append(id);
    builder.append(", skuId=");
    builder.append(skuId);
    builder.append(", addedBy=");
    builder.append(addedBy);
    builder.append(", action=");
    builder.append(action);
    builder.append(", time=");
    builder.append(time);
    builder.append("]");
    return builder.toString();
  }

}
