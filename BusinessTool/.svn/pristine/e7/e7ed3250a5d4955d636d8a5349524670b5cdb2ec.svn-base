/**
 * 
 */
package com.bestbuy.search.merchandising.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity Mapping class for the table BOOSTS_AND_BLOCKS_PRODUCTS.
 * 
 * @author Sreedhar Patlola
 */
@Entity
@Table(name = "BOOSTS_AND_BLOCKS_PRODUCTS")
@SequenceGenerator(sequenceName = "BOOSTS_AND_BLOCKS_PRODUCTS_SEQ", name = "BOOSTS_AND_BLOCKS_PRODUCTS_SEQ_GEN")
public class BoostAndBlockProduct implements Serializable {

  private static final String COLUMN_POSITION = "POSITION";

  private static final String COLUMN_SKU_ID = "SKU_ID";

  private static final String COLUMN_PRODUCT_NAME = "PRODUCT_NAME";

  private static final String COLUMN_ID = "ID";

  private static final String COLUMN_BOOSTS_AND_BLOCKS_ID = "BOOST_AND_BLOCK_ID";

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "BOOSTS_AND_BLOCKS_PRODUCTS_SEQ_GEN", strategy = GenerationType.SEQUENCE)
  @Column(name = COLUMN_ID, length = 19)
  private Long id;

  @ManyToOne
  @JoinColumn(name = COLUMN_BOOSTS_AND_BLOCKS_ID, referencedColumnName = COLUMN_ID)
  private BoostAndBlock boostAndBlock;

  @Column(name = COLUMN_PRODUCT_NAME, unique = false, nullable = true, insertable = true, updatable = true, length = 255)
  private String productName;

  @Column(name = COLUMN_SKU_ID)
  private Long skuId;

  @Column(name = COLUMN_POSITION)
  private Integer position;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the boostAndBlock
   */
  public BoostAndBlock getBoostAndBlock() {
    return boostAndBlock;
  }

  /**
   * @param boostAndBlock
   *          the boostAndBlock to set
   */
  public void setBoostAndBlock(BoostAndBlock boostAndBlock) {
    this.boostAndBlock = boostAndBlock;
  }

  /**
   * @return the productName
   */
  public String getProductName() {
    return productName;
  }

  /**
   * @param productName
   *          the productName to set
   */
  public void setProductName(String productName) {
    this.productName = productName;
  }

  /**
   * @return the skuId
   */
  public Long getSkuId() {
    return skuId;
  }

  /**
   * @param skuId
   *          the skuId to set
   */
  public void setSkuId(Long skuId) {
    this.skuId = skuId;
  }

  /**
   * @return the position
   */
  public Integer getPosition() {
    return position;
  }

  /**
   * @param position
   *          the position to set
   */
  public void setPosition(Integer position) {
    this.position = position;
  }
}
