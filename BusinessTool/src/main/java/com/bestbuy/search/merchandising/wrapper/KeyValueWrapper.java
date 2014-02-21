/**
 * Sep 17, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.wrapper;

/**
 * Encapsulates the information for sending the options of a dropdown in the json response
 * @author Ramiro.Serrato
 *
 */
public class KeyValueWrapper implements IWrapper {
  
  
	private String key;
	private String value;
	
	public KeyValueWrapper() {
    super();
    // TODO Auto-generated constructor stub
  }

  public KeyValueWrapper(String key, String value) {
    super();
    this.key = key;
    this.value = value;
  }

  public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

	/* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("KeyValueWrapper [key=");
    builder.append(key);
    builder.append(", value=");
    builder.append(value);
    builder.append("]");
    return builder.toString();
  }

  /* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(IWrapper o) {
		return key.compareTo(((KeyValueWrapper)o).getKey());
	}

}
