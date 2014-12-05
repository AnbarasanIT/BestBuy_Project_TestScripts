/**
 * Dec 13, 2012 : File created by Ramiro.Serrato for BestBuy-TCS
 */
package com.bestbuy.search.merchandising.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bestbuy.search.merchandising.common.BTLogger;
import com.bestbuy.search.merchandising.common.ErrorType;
import com.bestbuy.search.merchandising.domain.Users;
import com.bestbuy.search.merchandising.security.UserUtil;

/**
 * Previously the application was using only a static view resolving configured in webmvc-config.xml file by the use of the mvc:view-controller tag,
 * that was fine as we were not needing to pass any information to the freemarker views in a synchronous fashion (we are performing almost all the operations
 * using ajax). But now we need to be able to print information from the logged user in some part in the screen, that is why I created this controller in order to
 * be able to resolve the views dynamically and send information from the model to the UI in synchronous time.
 * @author Ramiro.Serrato
 *
 */
@Controller
public class DynamicViewController {
  
  private final static BTLogger log = BTLogger.getBTLogger(DynamicViewController.class.getName());
  
	private static final String LOGGED_IN_USER_DATA = "userdetails";
	
	@Autowired
	private UserUtil userUtil;

	public void setUserUtil(UserUtil userUtil) {
		this.userUtil = userUtil;
	}
	
	/**
	 * sets the userInfomation into the session
	 * @param model
	 * @param req
	 * @param resp
	 */
	@RequestMapping(value = "/authdata", method = RequestMethod.GET) 
	public void loadMasterPage( ModelMap model,HttpServletRequest req,HttpServletResponse resp){
		try{
			HttpSession session = req.getSession(true);
			session.setAttribute(LOGGED_IN_USER_DATA, userUtil.getUser());
			log.info("User data set to Session,redirecting to index");
			resp.sendRedirect("html/index.html");
		}catch(IOException io){
			log.error("DynamicViewController", io, ErrorType.APPLICATION, "Error while setting the userinfo to session");
		}
		
	}
	
	/**
	 * returns the user information as a json object
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET) 
	public @ResponseBody Users loadUserInfo(HttpServletRequest req,HttpServletResponse resp){
		Users user = (Users)req.getSession().getAttribute(LOGGED_IN_USER_DATA); 
		return user;
	}
}
