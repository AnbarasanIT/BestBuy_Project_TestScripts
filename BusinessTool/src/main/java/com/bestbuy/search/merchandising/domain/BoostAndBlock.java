/**
 * 
 */
package com.bestbuy.search.merchandising.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity Mapping class for the table BOOSTS_AND_BLOCKS.
 * 
 * @author Sreedhar Patlola
 */
@Entity
@Table(name = "BOOSTS_AND_BLOCKS")
@SequenceGenerator(sequenceName = "BOOSTS_AND_BLOCKS_SEQ", name = "BOOSTS_AND_BLOCKS_SEQ_GEN")
public class BoostAndBlock extends BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(generator = "BOOSTS_AND_BLOCKS_SEQ_GEN", strategy = GenerationType.SEQUENCE)
  @Column(name = "ID", length = 19)
  private Long id;

  @Column(name = "TERM", unique = false, nullable = false, length = 255)
  private String term;

  @OneToOne
  @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_NODE_ID")
  private Category category;

  @OneToOne
  @JoinColumn(name = "SEARCH_PROFILE_ID", referencedColumnName = "SEARCH_PROFILE_ID")
  private SearchProfile searchProfile;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "boostAndBlock",orphanRemoval=true)
  private List<BoostAndBlockProduct> boostAndBlockProducts = new ArrayList<BoostAndBlockProduct>();

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
   * @return the term
   */
  public String getTerm() {
    return term;
  }

  /**
   * @param term
   *          the term to set
   */
  public void setTerm(String term) {
    this.term = term;
  }

  /**
   * @return the categoryId
   */
  public Category getCategoryId() {
    return category;
  }

  /**
   * @param categoryId
   *          the categoryId to set
   */
  public void setCategoryId(Category categoryId) {
    this.category = categoryId;
  }

  /**
   * @return the searchProfileId
   */
  public SearchProfile getSearchProfileId() {
    return searchProfile;
  }

  /**
   * @param searchProfileId
   *          the searchProfileId to set
   */
  public void setSearchProfileId(SearchProfile searchProfileId) {
    this.searchProfile = searchProfileId;
  }
  
  /**
   * @return the boostAndBlockProducts
   */
  public List<BoostAndBlockProduct> getBoostAndBlockProducts() {
    return boostAndBlockProducts;
  }

  /**
   * @param boostAndBlockProducts
   *          the boostAndBlockProducts to set
   */
  public void setBoostAndBlockProducts(List<BoostAndBlockProduct> boostAndBlockProducts) {
    this.boostAndBlockProducts = boostAndBlockProducts;
  }

  /**
   * @param boostAndBlockProduct
   *          adds the boostAndBlockProduct to current list
   */
  public void addBoostAndBlockProduct(BoostAndBlockProduct boostAndBlockProduct) {
    this.boostAndBlockProducts.add(boostAndBlockProduct);
  }
}
