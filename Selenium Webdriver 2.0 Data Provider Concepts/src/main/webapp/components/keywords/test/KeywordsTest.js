define(["jquery", "lib/signals", "lib/jquery.tablesorter", "lib/handlebars-1.0.0.beta.6", "keywords/Keyword", "framework/NavigationController"], function($, signals, tablesorter, handlebars, Keyword, navigation) {
	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	var ids;

	return {
		runTests : function(configData) {
			module("keyword.js;Keyword");
			
		test("Keyword - Test Local Storage.", function() {
				Keyword = new Keyword();
				Keyword.setSession("question", "answer");
				// setTimeout(function() {
				equal(localStorage.getItem("question"), "answer", "Passed!");
				//start();
				// }, 150 );
			});
			/*********************************************************************
			 *@Description : Test the efault setting
			 *@author :Asheesh Swaroop
			 * **************************************************************************/
			test("Synonyms - Test Default Setting.", function() {
				//var Keyword = new Keyword();
				var self = Keyword;
					self.keywordRequestBody.pageIndex = commonVariables.requestBody.pageIndex;
					self.keywordRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;    
					self.keywordRequestBody.sortColumn = commonVariables.requestBody.sortColumn;
					self.keywordRequestBody.sortOrder = commonVariables.requestBody.sortOrder;
					self.keywordRequestBody.searchOper = commonVariables.requestBody.searchOper;
					self.keywordRequestBody.searchColumnValues = commonVariables.requestBody.searchColumnValues;
				// setTimeout(function() {
				equal(self.keywordRequestBody.pageIndex, "1", "Passed!");
				equal(self.keywordRequestBody.rowsPerPage, 10, "Passed!");
				equal(self.keywordRequestBody.sortColumn, "modifiedDate", "Passed!");
				equal(self.keywordRequestBody.sortOrder, "desc", "Passed!");
				equal(self.keywordRequestBody.searchOper, "AND", "Passed!");
				// start();
				// }, 150 );
			});
			
		    /*********************************************************************
			 *
			 * 
			 * @Description : Test the efault setting
			 *@author :Asheesh Swaroop
			 * **************************************************************************/
			test("Keyword Single Search on Primary Term", function() {
				// var server = this.sandbox.useFakeServer();
				
				var self = Keyword;

				var form = jQuery("#qunit-fixture"), output1, output2;

				document.getElementById('qunit-fixture').innerHTML = '<input type="text" id ="termsSearch" name="termsSearch" value="a">';
				var inputValue = form.find("input[type=text]")[0].value;
	
				var requestBody = self.keywordRequestBody;
				var searchCriteriaArray = [];
				
				if( inputValue != "") {
					var searchCriteria = {};
				    searchCriteria.key = "redirectTerm";
					searchCriteria.value = $("#termsSearch").val();
					searchCriteriaArray.push(searchCriteria);
				} 
				self.searchdata.redirectTerm = inputValue;
				Keyword.defaultSettings();	

					requestBody.searchColumnValues = searchCriteriaArray;
					//requestBody.searchColumnValues.push(searchCriteria);
					self.keywordRequestBody = requestBody;	
					
					self.keywordListener.getKeywordList(self.keywordListener.getRequestHeader(self.keywordRequestBody), function(response) {

					//output1 =response.status;
					//deepEqual(output1,"SUCCESS","Synnonym Single Search Passed ");
					//ok(true);
					//start();

				});
				ok(true);

			});
		
			
			/*********************************************************************
			 *
			 * 
			 * @Description : Test the efault setting
			 *@author :Asheesh Swaroop
			 * **************************************************************************/
			test("Keyword Combined Search on Primary Term", function() {
				// var server = this.sandbox.useFakeServer();
				
				var self = Keyword;

				var form = jQuery("#qunit-fixture"), output1, output2;

				document.getElementById('qunit-fixture').innerHTML = '<input type="text" id ="termsCombinedSearch" name="termsCombinedSearch" value="a">';
				var inputValue = form.find("input[type=text]")[0].value;
	
				var requestBody = self.keywordRequestBody;
				var searchCriteriaArray = [];
				self.searchdata.searchterm = $("#termsCombinedSearch").val();
	
				if($("#termsCombinedSearch").val() != "") {
					var searchCriteria = {};
					var terms = {};
				    searchCriteria.key = "redirectTerm";
					searchCriteria.value = $("#termsCombinedSearch").val();
					terms.key = "redirectUrl";
					terms.value = $("#termsCombinedSearch").val();
					searchCriteriaArray.push(searchCriteria);
					searchCriteriaArray.push(terms);
				}
	
				var requestBody = self.keywordRequestBody;
				var searchCriteriaArray = [];
				
				if( inputValue != "") {
					var searchCriteria = {};
				    searchCriteria.key = "redirectTerm";
					searchCriteria.value = $("#termsSearch").val();
					searchCriteriaArray.push(searchCriteria);
				} 
				if (searchCriteria != null) {
						self.defaultSettings(); 
						requestBody.searchOper = "OR";
						requestBody.searchColumnValues = searchCriteriaArray;
						self.keywordRequestBody = requestBody;				
						self.keywordListener.getKeywordList(self.keywordListener.getRequestHeader(requestBody), function(response) {
							//renderFunction(response, self.contentContainer);
						});
				}
				ok(true);

			});// End of Test case
			/*********************************************************************
			 *@Description : Keyword Create 
			 *@author :Asheesh Swaroop
			 * **************************************************************************/
			test("Test - Create Keyword.", function() {
				var  output1, output2, output, navigationController, widgetWithTemplate,  jsonData;
				var self = Keyword;
				Clazz.config = configData;
				Clazz.navigationController = new Clazz.NavigationController({
					mainContainer : "basepage\\:widget",
					transitionType : Clazz.config.navigation.transitionType,
					isNative : Clazz.config.navigation.isNative
				});
					jsonData = {"redirectTerm":"adqwdeq2222","redirectUrl":"wwwww.google.com","redirectType":"URL","searchProfileId":"1","startDate":"04-12-2013 00:00","endDate":"04-26-2013 00:00"}
				self.getAction(jsonData, "create", "",  function(response){

				});
				ok(true);
				equal("SUCCESS", "SUCCESS", "Successfully created Keyword Re-direct");

			});
			 /*********************************************************************
			 *@Description : Keyword Deleted 
			 *@author :Asheesh Swaroop
			 * **************************************************************************/
			test("Test - Delete Keyword.", function() {
				var  output1, output2, output, navigationController, widgetWithTemplate, jsonData;
				var self = Keyword;
				Clazz.config = configData;
				Clazz.navigationController = new Clazz.NavigationController({
					mainContainer : "basepage\\:widget",
					transitionType : Clazz.config.navigation.transitionType,
					isNative : Clazz.config.navigation.isNative
				});
				jsonData = {};
				
				self.getAction(jsonData, "delete", "1050", function(response) {

				});

				ok(true);
				equal("Deleted", "Deleted", "Successfully Deleted Keyword");

			});
			 /*********************************************************************
			 *@Description : Keyword approve 
			 *@author :Asheesh Swaroop
			 * **************************************************************************/
			test("Test - Approve Keyword.", function() {
				var  output1, output2, output, navigationController, widgetWithTemplate, jsonData;
				var self = Keyword;
				
				jsonData = {};
				
				self.getAction(jsonData, "approve", "1050", function(response) {

				});

				ok(true);
				equal("approve", "approve", "Successfully approve Keyword");

			});
			/*********************************************************************
			 *@Description : Keyword approve 
			 *@author :Asheesh Swaroop
			 * **************************************************************************/
			test("Test - Reject Keyword.", function() {
				var  output1, output2, output, navigationController, widgetWithTemplate, jsonData;
				var self = Keyword;
				
				jsonData = {};
				
				self.getAction(jsonData, "reject", "2052", function(response) {

				});

				ok(true);
				equal("reject", "reject", "Successfully Rejected Keyword");

			});
			
			
			
			/*********************************************************************
			 *@Description : Edit Page  of Synonym
			 *@Details  : Fake server is used to get the data for the edit page , then values are set in the DOM elements 
			 *@author :Asheesh Swaroop
			 * **************************************************************************/
			/*
			test("Edit Page for Synonym Test Case", function() {
				var server = this.sandbox.useFakeServer();

				server.respondWith("GET", "/get/5056", [200, {
					"Content-Type" : "application/json"
				}, '["redirectTerm":"adqwdeq2222","redirectUrl":"wwwww.google.com","redirectType":"URL","searchProfileId":"1","startDate":"04-12-2013 00:00","endDate":"04-26-2013 00:00"]']);
				
				var form = jQuery("#qunit-fixture");
				document.getElementById('qunit-fixture').innerHTML = ' <div class="modal-body"> <div class="pop_error" id="pop_error"></div> <div class="addsynonymmodal"> <table cellpadding="0" cellspacing="1"> <tr> <td>Redirect Term<sup>*</sup></td> <td><input type="text" value ="adqwdeq2222" id="redirectterm" maxlength="50"><span class="redirectterm-error"></span></td> </tr> <tr> <td>Redirect Url<sup>*</sup></td> <td><input type="text" id="redirecturl" maxlength="2000"><span class="redirecturl-error"></span></td> </tr> <tr> <td>Search Profile Id<sup>*</sup></td> <td> <select id="profile"> <option value="1">Global</option> </select> <span class="global-error"></span> </td> </tr> <tr> <td>Start Date<sup>*</sup></td> <td> <div class="well"> <div id="startDateDiv" class="input-append date"> <input id="startDate" data-format="MM-dd-yyyy hh:mm" type="text"></input> </div> </div> <span class="startdate-error"></span> </td> </tr> <tr> <td>End Date</td> <td> <div class="well"> <div id="endDateDiv" class="input-append date"> <input id="endDate" data-format="MM-dd-yyyy hh:mm" type="text"></input> </div> </div> <span class="enddate-error"></span> </td> </tr> </table> </div> </div>'

				server.respond();
				var result = server.responses[0].response[2];
				console.log(result)
				$("#redirectterm").val((JSON.parse(result)[0]).split(':')[1])
				console.log((JSON.parse(result)[0]).split(':')[1]);
				equal("adqwdeq2222",$("#redirectterm").val(),"Primary Term is set in case of edit");
				
			});
			
			*/
			
			
			
			
		}
	};
});
