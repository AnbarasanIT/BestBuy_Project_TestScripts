/**
 * 
 */
package com.bestbuy.search.merchandising.wrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.BeanUtils;

import com.bestbuy.search.merchandising.common.JsonDateDeSerializer;
import com.bestbuy.search.merchandising.common.JsonDateSerializer;
import com.bestbuy.search.merchandising.domain.Users;

/**
 * JSon Wrapper class for the User.
 * 
 * @author Sreedhar Patlola
 */
public class UserWrapper implements IWrapper {

  private int id;

  private Long userId;

  @NotNull @Size(min=1,message="Field.NotEmpty")
  private String loginName;

  @NotNull @Size(min=1,message="Field.NotEmpty")
  private String firstName;

  @NotNull @Size(min=1,message="Field.NotEmpty")
  private String lastName;

  private Boolean active = true;

  private String email;

  private String updatedBy;

  private Date updatedDate;

  private Integer roleId;

  private String roleName;
  
  private List<KeyValueWrapper> actions;

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * @return the userId
   */

  public Long getUserId() {
    return userId;
  }

  /**
   * @param userId
   *          the userId to set
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * @return the loginName
   */
  public String getLoginName() {
    return loginName;
  }

  /**
   * @param loginName
   *          the loginName to set
   */
  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }

  /**
   * @return the firstName
   */
  @JsonProperty("firstName")
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName
   *          the firstName to set
   */
  @JsonProperty("firstName")
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  @JsonProperty("lastName")
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName
   *          the lastName to set
   */
  @JsonProperty("lastName")
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the active
   */
  public Boolean getActive() {
    return active;
  }

  /**
   * @param active
   *          the active to set
   */
  public void setActive(Boolean active) {
    this.active = active;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email
   *          the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the modifiedBy
   */
  @JsonProperty("lastModifiedBy")
  public String getUpdatedBy() {
    return updatedBy;
  }

  /**
   * @param modifiedBy
   *          the modifiedBy to set
   */
  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  /**
   * @return the modifiedDate
   */
  @JsonProperty("lastModified")
  @JsonSerialize(using=JsonDateSerializer.class)
  public Date getUpdatedDate() {
    return updatedDate;
  }

  /**
   * @param modifiedDate
   *          the modifiedDate to set
   */
  @JsonDeserialize(using = JsonDateDeSerializer.class)
  public void setUpdatedDate(Date updatedDate) {
    this.updatedDate = updatedDate;
  }

  /**
   * @return the roleId
   */
  public Integer getRoleId() {
    return roleId;
  }

  /**
   * @param roleId
   *          the roleId to set
   */
  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  /**
   * @return the roleName
   */
  public String getRoleName() {
    return roleName;
  }

  /**
   * @param roleName
   *          the roleName to set
   */
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  /**
   * @param userWrappers
   * @return
   */
  public static List<IWrapper> sortRowIds(List<IWrapper> userWrappers) {
    int i = 0;

    for (IWrapper wrapper : userWrappers) {
      UserWrapper userWrapper = (UserWrapper) wrapper;
      userWrapper.setId(i);
      userWrappers.set(i, userWrapper);
      i++;
    }

    return userWrappers;
  }

  /**
   * @param actions
   * @return
   */
  public static List<KeyValueWrapper> getValidActions(Set<String> actions) {
    List<KeyValueWrapper> dropdowns = new ArrayList<KeyValueWrapper>();

    for (String action : actions) {
      KeyValueWrapper dropdown = new KeyValueWrapper();
      dropdown.setKey(action);
      dropdown.setValue(action);
      dropdowns.add(dropdown);
    }

    return dropdowns;
  }

  @Override
  public int compareTo(IWrapper arg0) {
    // TODO Auto-generated method stub
    return 0;
  }

  public static List<IWrapper> getUsers(List<Users> users) {

    List<IWrapper> userWrappers = new ArrayList<IWrapper>();
    for (Users user : users) {
      userWrappers.add(getUser(user));
    }

    return userWrappers;
  }

  public static UserWrapper getUser(Users user) {
    UserWrapper userWrapper = new UserWrapper();
    BeanUtils.copyProperties(user, userWrapper);
    if (user != null && user.getRole() != null) {
      userWrapper.setRoleId(user.getRole().getId());
      userWrapper.setRoleName(user.getRole().getName());
    }
    return userWrapper;

  }

  /**
   * @return the actions
   */
  public List<KeyValueWrapper> getActions() {
    return actions;
  }

  /**
   * @param actions the actions to set
   */
  public void setActions(List<KeyValueWrapper> actions) {
    this.actions = actions;
  }

}
