package com.bestbuy.search.merchandising.wrapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Encapsulates the information for sending the various option via json response
 * These information will be used by the User Interface for pagination , Sorting of column and searching of a value .
 * Server side implementation needs to send these information while displaying the data in Grid Format
 * 
 * @author Asheesh.Swaroop
 */
public class PaginationWrapper implements IWrapper {

  // current page Index
  private Integer pageIndex = new Integer(1);

  // total pages to be displayed on UI
  private Integer rowsPerPage = new Integer(50);

  // total pages to be displayed on UI
  private Long totalCountOfPages = new Long(0l);

  // total number of records from database for a specific search
  private Long searchCount = new Long(0l);

  // total number of records from database
  private Long totalCount = new Long(0l);

  // column name on sorting needs to be done
  private String sortColumn;

  // Sort order for the data to be displayed on user interface
  private String sortOrder;

  // Various operations to be done while doing search like OR / AND condition
  private String searchOper;

  private List<KeyValueWrapper> searchColumnValues;

  public Integer getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  public Integer getRowsPerPage() {
    return rowsPerPage;
  }

  public void setRowsPerPage(Integer rowsPerPage) {
    this.rowsPerPage = rowsPerPage;
  }

  public Long getTotalCountOfPages() {
    return totalCountOfPages;
  }

  public void setTotalCountOfPages(Long totalCountOfPages) {
    this.totalCountOfPages = totalCountOfPages;
  }

  @JsonProperty("totalCountOfRecords")
  public Long getSearchCount() {
    return searchCount;
  }

  @JsonProperty("totalCountOfRecords")
  public void setSearchCount(Long totalCountOfRecords) {
    this.searchCount = totalCountOfRecords;
  }

  /**
   * @return the totalCount
   */
  public Long getTotalCount() {
    return totalCount;
  }

  /**
   * @param totalCount
   *          the totalCount to set
   */
  public void setTotalCount(Long totalCount) {
    this.totalCount = totalCount;
  }

  public String getSortColumn() {
    return sortColumn;
  }

  public void setSortColumn(String sortColumn) {
    this.sortColumn = sortColumn;
  }

  public String getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(String sortOrder) {
    this.sortOrder = sortOrder;
  }

  public String getSearchOper() {
    return searchOper;
  }

  public void setSearchOper(String searchOper) {
    this.searchOper = searchOper;
  }

  @Override
  public int compareTo(IWrapper arg0) {
    // TODO Auto-generated method stub
    return 0;
  }

  public List<KeyValueWrapper> getSearchColumnValues() {
    return searchColumnValues;
  }

  public void setSearchColumnValues(List<KeyValueWrapper> searchColumnValues) {
    this.searchColumnValues = searchColumnValues;
  }

}
