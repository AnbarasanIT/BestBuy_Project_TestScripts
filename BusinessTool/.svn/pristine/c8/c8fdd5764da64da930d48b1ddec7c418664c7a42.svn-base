
package com.bestbuy.search.merchandising.category;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.bestbuy.search.merchandising.domain.Category;

/**
 * Class that is used for Category Tree JaxB Transformation
 * @author Lakshmi.Valluripalli
 */
@XmlRootElement(name="Categories")
public class Categories {
	
	private List<Category> nodes = new ArrayList<Category>();
	
	/**
	 * List of Categories Transformed by the JaxB will 
	 * be available here.
	 * @return
	 */
	@XmlElement(name="Category")
	public List<Category> getNodes() {
		return nodes;
	}
	
	/**
	 * sets the list of categories transformed by the JaxB
	 * @param nodes
	 */
	public void setNodes(List<Category> nodes) {
		this.nodes = nodes;
	}
}
