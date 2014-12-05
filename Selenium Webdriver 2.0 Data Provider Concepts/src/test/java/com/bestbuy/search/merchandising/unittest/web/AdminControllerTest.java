package com.bestbuy.search.merchandising.unittest.web;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ResourceBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bestbuy.search.merchandising.common.ResourceBundleHandler;
import com.bestbuy.search.merchandising.common.exception.ServiceException;
import com.bestbuy.search.merchandising.service.AdminService;
import com.bestbuy.search.merchandising.unittest.common.UserTestData;
import com.bestbuy.search.merchandising.web.AdminController;
import com.bestbuy.search.merchandising.wrapper.IWrapper;
import com.bestbuy.search.merchandising.wrapper.MerchandisingBaseResponse;
import com.bestbuy.search.merchandising.wrapper.UserWrapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring/it-applicationContext*.xml"})
public class AdminControllerTest extends AbstractJUnit4SpringContextTests {

  AdminController adminController;
  MerchandisingBaseResponse<IWrapper> merchandisingBaseResponse;
  ResourceBundle errorMessages;
  ResourceBundle successMessages;
  ResourceBundleHandler resourceBundleHandler;
  AdminService adminService;

  @Before
  public void setUp() throws Exception {
    adminController = new AdminController();
    merchandisingBaseResponse = new MerchandisingBaseResponse<IWrapper>();
    adminService = mock(AdminService.class);
    resourceBundleHandler = mock(ResourceBundleHandler.class);
    resourceBundleHandler.setErrorMessagesProperties(errorMessages);
    resourceBundleHandler.setSuccessMessagesProperties(successMessages);
    merchandisingBaseResponse.setResourceBundleHandler(resourceBundleHandler);
    adminController.setAdminService(adminService);
    adminController.setMerchandisingBaseResponse(merchandisingBaseResponse);

  }

  @Test
  public void testLoadUsers() throws Exception {
    List<IWrapper> wrappers = UserTestData.getUserWrappers();
    when(adminService.load()).thenReturn(wrappers);
    when(resourceBundleHandler.getSuccessString("Listing.Success", "User")).thenReturn("User listing was successful");
    MerchandisingBaseResponse<IWrapper> response = adminController.loadUsers();
    assertTrue(response.getMessage().equals("User listing was successful"));

  }

  @Test
  public void testLoadUsersWithException() throws Exception {

    when(adminService.load()).thenThrow(new ServiceException());
    when(resourceBundleHandler.getErrorString("Listing.Error", "User")).thenReturn("Error while retrieving the User list");
    MerchandisingBaseResponse<IWrapper> response = adminController.loadUsers();
    assertTrue(response.getMessage().equals("Error while retrieving the User list"));

  }

  @Test
  public void testCreate() throws Exception {
    UserWrapper userWrapper = UserTestData.getUserWrapper();
    IWrapper wrapper = new UserWrapper();
    when(adminService.createUser(userWrapper)).thenReturn(wrapper);
    when(resourceBundleHandler.getSuccessString("Create.Success", "User")).thenReturn("User created successfully");
    MerchandisingBaseResponse<IWrapper> resp = adminController.create(userWrapper);
    assertNotNull(resp.getData());
  }

  @Test
  public void testUpdate() throws Exception {
    UserWrapper userWrapper = UserTestData.getUserWrapper();
    IWrapper wrapper = new UserWrapper();
    when(adminService.updateUser(userWrapper)).thenReturn(wrapper);
    when(resourceBundleHandler.getSuccessString("Update.Success", "User")).thenReturn("User updated successfully");
    MerchandisingBaseResponse<IWrapper> resp = adminController.update(userWrapper);
    assertNotNull(resp.getData());
  }

  @Test
  public void testEdit() throws Exception {

    UserWrapper userWrapper = UserTestData.getUserWrapper();
    when(adminService.getUser(101l)).thenReturn(userWrapper);
    when(resourceBundleHandler.getSuccessString("Retrive.Success", "User")).thenReturn("User retrieved successfully");
    MerchandisingBaseResponse<IWrapper> resp = adminController.edit(101l);
    assertNotNull(resp.getData());
  }

  @Test
  public void testDelete() throws Exception {
    UserWrapper wrapper = new UserWrapper();
    when(adminService.deleteUser(101l)).thenReturn(wrapper);
    when(resourceBundleHandler.getSuccessString("Delete.Success", "User")).thenReturn("User deleted successfully");
    MerchandisingBaseResponse<IWrapper> resp = adminController.delete(101l);
    assertTrue(resp.getMessage().equals("User deleted successfully"));
  }

}
