/**
 * 
 */
package com.bestbuy.search.merchandising.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
/**
 * @author Kalaiselvi.Jaganathan
 *User entity has all the user related data
 */
public class Users implements java.io.Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "USER_ID")
  private Long userId;

  @Column(name = "LOGIN_NAME")
  private String loginName;

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "PASSWORD")
  private String password;

  @Column(name = "ACTIVE")
  private Boolean active;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "UPDATED_DATE", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
  private Date updatedDate;

  @Column(name = "UPDATED_BY")
  private String updatedBy;

  @OneToOne
  @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
  private Role role;

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
   * gets LoginName
   * 
   * @return
   */
  public String getLoginName() {
    return loginName;
  }

  /**
   * sets Login Name
   * 
   * @param loginName
   */
  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }

  /**
   * gets user FirstName
   * 
   * @return
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * sets user FirstName
   * 
   * @param firstName
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * gets user LastName
   * 
   * @return
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * sets User LastName
   * 
   * @param lastName
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * gets the user Password
   * 
   * @return
   */
  public String getPassword() {
    return password;
  }

  /**
   * sets the user Password
   * 
   * @param password
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * gets the user email
   * 
   * @return
   */
  public String getEmail() {
    return email;
  }

  /**
   * sets the user Email
   * 
   * @param email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the updatedDate
   */
  public Date getUpdatedDate() {
    return updatedDate;
  }

  /**
   * @param updatedDate
   *          the updatedDate to set
   */
  public void setUpdatedDate(Date updatedDate) {
    this.updatedDate = updatedDate;
  }

  /**
   * @return the updatedBy
   */
  public String getUpdatedBy() {
    return updatedBy;
  }

  /**
   * @param updatedBy
   *          the updatedBy to set
   */
  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  /**
   * @return the role
   */
  public Role getRole() {
    return role;
  }

  /**
   * @param role
   *          the role to set
   */
  public void setRole(Role role) {
    this.role = role;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Users [getUserId()=");
    builder.append(getUserId());
    builder.append(", getActive()=");
    builder.append(getActive());
    builder.append(", getLoginName()=");
    builder.append(getLoginName());
    builder.append(", getFirstName()=");
    builder.append(getFirstName());
    builder.append(", getLastName()=");
    builder.append(getLastName());
    builder.append(", getPassword()=");
    builder.append(getPassword());
    builder.append(", getEmail()=");
    builder.append(getEmail());
    builder.append(", getUpdatedDate()=");
    builder.append(getUpdatedDate());
    builder.append(", getUpdatedBy()=");
    builder.append(getUpdatedBy());
    builder.append("]");
    return builder.toString();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((active == null) ? 0 : active.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((loginName == null) ? 0 : loginName.hashCode());
    result = prime * result + ((password == null) ? 0 : password.hashCode());
    result = prime * result + ((role == null) ? 0 : role.hashCode());
    result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
    result = prime * result + ((updatedDate == null) ? 0 : updatedDate.hashCode());
    result = prime * result + ((userId == null) ? 0 : userId.hashCode());
    return result;
  }

  /* (non-Javadoc)
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
    Users other = (Users) obj;
    if (active == null) {
      if (other.active != null)
        return false;
    } else if (!active.equals(other.active))
      return false;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    if (loginName == null) {
      if (other.loginName != null)
        return false;
    } else if (!loginName.equals(other.loginName))
      return false;
    if (password == null) {
      if (other.password != null)
        return false;
    } else if (!password.equals(other.password))
      return false;
    if (role == null) {
      if (other.role != null)
        return false;
    } else if (!role.equals(other.role))
      return false;
    if (updatedBy == null) {
      if (other.updatedBy != null)
        return false;
    } else if (!updatedBy.equals(other.updatedBy))
      return false;
    if (updatedDate == null) {
      if (other.updatedDate != null)
        return false;
    } else if (!updatedDate.equals(other.updatedDate))
      return false;
    if (userId == null) {
      if (other.userId != null)
        return false;
    } else if (!userId.equals(other.userId))
      return false;
    return true;
  }

 

}
