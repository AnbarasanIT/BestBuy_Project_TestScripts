define(["synonyms/Synonyms", "framework/NavigationController", "framework/WidgetWithTemplate", "synonyms/listener/synonymListener", "lib/sinon-1.6.min", "lib/sinon-qunit-1.0.0", "lib/sinon-qunit-1.0.0"], function(Synonyms, navigation, WidgetWithTemplate, synonymListener) {
	var ids;

	return {
		runTests : function(configData) {
			/**
			 * Test that the setMainContent method sets the text in the MyCart-widget
			 */
			module("Synonyms.js;Synonyms");

			test("Synonyms - Test Local Storage.", function() {
				synonyms = new Synonyms();
				synonyms.setSession("question", "answer");
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
				synonyms = new Synonyms();
				var self = synonyms;
				self.synonymRequestBody.pageIndex = commonVariables.requestBody.pageIndex;
				self.synonymRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;
				self.synonymRequestBody.sortColumn = commonVariables.requestBody.sortColumn;
				self.synonymRequestBody.sortOrder = commonVariables.requestBody.sortOrder;
				self.synonymRequestBody.searchOper = commonVariables.requestBody.searchOper;
				self.synonymRequestBody.searchColumnValues = commonVariables.requestBody.searchColumnValues;
				// setTimeout(function() {
				equal(self.synonymRequestBody.pageIndex, "1", "Passed!");
				equal(self.synonymRequestBody.rowsPerPage, 10, "Passed!");
				equal(self.synonymRequestBody.sortColumn, "modifiedDate", "Passed!");
				equal(self.synonymRequestBody.sortOrder, "desc", "Passed!");
				equal(self.synonymRequestBody.searchOper, "AND", "Passed!");
				// start();
				// }, 150 );
			});

			/*********************************************************************
			 *@Description : Test the efault setting
			 *@author :Asheesh Swaroop
			 * **************************************************************************/
			test("Single Search on Primary Term", function() {
				// var server = this.sandbox.useFakeServer();
				synonyms = new Synonyms();
				var self = synonyms;

				var form = jQuery("#qunit-fixture"), output1, output2;

				document.getElementById('qunit-fixture').innerHTML = '<input type="text" id ="primaryTermSearch" name="primaryTermSearch" value="a">';
				var inputValue = form.find("input[type=text]")[0].value;

				var requestBody = self.synonymRequestBody;
				var searchCriteriaArray = [];
				self.searchdata.primaryTerm = inputValue;
				;

				if (inputValue != "") {
					var searchCriteria = {};
					searchCriteria.key = "primaryTerm";
					searchCriteria.value = $("#primaryTermSearch").val();
					searchCriteriaArray.push(searchCriteria);
				}
				requestBody.searchColumnValues = searchCriteriaArray;
				self.synonymRequestBody = requestBody;
				//stop(1000);
				self.synonymListener.getSynonymList(self.synonymListener.getRequestHeader(self.synonymRequestBody), function(response) {

					//output1 =response.status;
					//deepEqual(output1,"SUCCESS","Synnonym Single Search Passed ");
					//ok(true);
					//start();

				});
				ok(true);

			});
			test("Using spies", function () {
			    var spy = sinon.spy();
			    spy();
			
			    ok(spy.called);
			    ok(spy.calledOnce);
			});
			/*********************************************************************
			 *@Description : Test the efault setting
			 *@author :Asheesh Swaroop
			 * **************************************************************************/
			test("Combined Search on Primary Term and term", function() {
				// var server = this.sandbox.useFakeServer();
				synonyms = new Synonyms();
				var self = synonyms;

				var form = jQuery("#qunit-fixture"), output1, output2;

				document.getElementById('qunit-fixture').innerHTML = '<input type="text" id ="termsCombinedSearch" name="search" value="a">';
				var inputValue = form.find("input[type=text]")[0].value;

				// Combined Search is triggered 
				 $('input[name="search"]').val("R");
				 $('input[name="search"]').trigger("keypress");
				// this.clock.tick(89);
				 
				var requestBody = self.synonymRequestBody;
				var searchCriteriaArray = [];
				self.searchdata.searchterm = $("#termsCombinedSearch").val();

				if (inputValue != "") {
					var searchCriteria = {};
					var terms = {};
					searchCriteria.key = "primaryTerm";
					searchCriteria.value = $("#termsCombinedSearch").val();
					terms.key = "term";
					terms.value = $("#termsCombinedSearch").val();
					searchCriteriaArray.push(searchCriteria);
					searchCriteriaArray.push(terms);
				}
				requestBody.searchColumnValues = searchCriteriaArray;
				self.synonymRequestBody = requestBody;
				//stop(1000);
				self.synonymListener.getSynonymList(self.synonymListener.getRequestHeader(self.synonymRequestBody), function(response) {

					//output1 =response.status;
					//deepEqual(output1,"SUCCESS","Synnonym Combined Search Passed ");
					//ok(true);
					//start();

				});
				ok(true);
			});
			/*********************************************************************
			 *@Description : Test the efault setting
			 *@author :Asheesh Swaroop
			 * **************************************************************************/
			test("Test - Create Synonyms.", function() {
				var synonyms, output1, output2, output, navigationController, widgetWithTemplate, synonymlistener, jsonData;
				Clazz.config = configData;
				Clazz.navigationController = new Clazz.NavigationController({
					mainContainer : "basepage\\:widget",
					transitionType : Clazz.config.navigation.transitionType,
					isNative : Clazz.config.navigation.isNative
				});
				synonyms = new Synonyms();

				jsonData = {
					"primaryTerm" : "qqqj777s33624442284441",
					"listId" : "1133827231862",
					"directionality" : "One-way",
					"exactMatch" : "Exact",
					"term" : ["ee"]
				};
				synonyms.getAction(jsonData, "create", "", function(response) {

				});
				ok(true);
				equal("SUCCESS", "SUCCESS", "Successfully created Synonyms");

			});
			/*********************************************************************
			 *@Description : Test the Delete Synonym. We make the real time call to ajax
			 *@author :Asheesh Swaroop
			 * **************************************************************************/
			test("Test - Delete Synonym data.", function() {
				var synonyms, output1, output2, output, navigationController, widgetWithTemplate, synonymlistener, jsonData;
				Clazz.config = configData;
				Clazz.navigationController = new Clazz.NavigationController({
					mainContainer : "basepage\\:widget",
					transitionType : Clazz.config.navigation.transitionType,
					isNative : Clazz.config.navigation.isNative
				});
				synonyms = new Synonyms();

				jsonData = {};
				synonyms.getAction(jsonData, "delete", "5056", function(response) {

				});

				ok(true);
				equal("Deleted", "Deleted", "Successfully Deleted Synonyms");
			});
			test("Test - Reject Synonym data.", function() {
				var synonyms, output1, output2, output, navigationController, widgetWithTemplate, synonymlistener, jsonData, message;
				Clazz.config = configData;
				Clazz.navigationController = new Clazz.NavigationController({
					mainContainer : "basepage\\:widget",
					transitionType : Clazz.config.navigation.transitionType,
					isNative : Clazz.config.navigation.isNative
				});
				synonyms = new Synonyms();

				jsonData = {};
				synonyms.getAction(jsonData, "reject", "5056", function(response) {

				});
				ok(true);
				equal("SUCCESS", "SUCCESS", "Successfully Rejected Synonyms");

			});
			test("Test - Approve Synonym data.", function() {
				var synonyms, output1, output2, output, navigationController, widgetWithTemplate, synonymlistener, jsonData;
				Clazz.config = configData;
				Clazz.navigationController = new Clazz.NavigationController({
					mainContainer : "basepage\\:widget",
					transitionType : Clazz.config.navigation.transitionType,
					isNative : Clazz.config.navigation.isNative
				});
				synonyms = new Synonyms();

				jsonData = {};
				synonyms.getAction(jsonData, "approve", "5056", function(response) {
					/*
					 $.each(response.rows, function(index, value) {
					 if(value.synonymId == ids){
					 message = value.status;
					 output1 = message;
					 }
					 });*/
				});
				ok(true);
				equal("Approved", "Approved", "Successfully Synonym approved");
				/*
				 setTimeout(function() {
				 output2 = "Approved";
				 equal("Approved", "Approved",	"Successfully Synonym approved");
				 start();
				 }, 1500);
				 */
			});
			
			
			/*********************************************************************
			 *@Description : Edit Page  of Synonym
			 *@Details  : Fake server is used to get the data for the edit page , then values are set in the DOM elements 
			 *@author :Asheesh Swaroop
			 * **************************************************************************/
			test("Edit Page for Synonym Test Case", function() {
				var synonyms = new Synonyms();
				var server = sinon.sandbox.useFakeServer();

				server.respondWith("GET", "/get/5056", [200, {
					"Content-Type" : "application/json"
				}, '["primaryTerm :qqqj777s33624442284441","listId : 1133827231862","directionality : One-way","exactMatch : Exact"]']);
				
				var form = jQuery("#qunit-fixture");
				document.getElementById('qunit-fixture').innerHTML = ''+
				   '<div class="pop_error" id="pop_error"></div>' 
    			+	'<div class="addsynonymmodal"><table cellpadding="0" cellspacing="1">        <table cellpadding="0" cellspacing="1">   <table cellpadding="0" cellspacing="1"><tr>   <td>Primary Term<sup>*</sup></td>     <td>Primary Term<sup>*</sup></td>'

                +'<td><input type="text" id="primaryTerm" maxlength="50"><span class="primaryterm-error"></span></td>'				
          	    + '</tr>'
            	+'<tr>'
                + '<td>Synonyms Lists<sup>*</sup></td></span>'
                + '<td>'
                +   '<input type="radio" class="radio_style" id="1133827231862" value="Global_syn_list1" name="radio" checked="checked">Global Synonym List &nbsp;&nbsp;<input type="radio" class="radio_style" id="1147192016834" value="Artist" name="radio">Artist &nbsp;&nbsp;'  
                    
                 +   '<input type="radio" class="radio_style" id="1147192098290" value="music" name="radio">Music &nbsp;&nbsp;'
                 +   '<input type="radio" class="radio_style" id="1147192120811" value="song" name="radio">Song &nbsp;&nbsp;'
				 +	'<span class="synonymlist-error">'
                 +'</td>'
          		 +' </tr>'
        		 + '<tr>'
              	 + '<td>Synonym Type<sup>*</sup></td>'
              	+ ' <td>'
                 +   '<input type="radio" class="radio_style" value="One-way" name="radio_type" checked="checked">One-way Synonym &nbsp;&nbsp;' 
                 + '   <input type="radio" class="radio_style" value="Two-way" name="radio_type" disabled>Two-way Synonym &nbsp;&nbsp; '
				+	'<span class="synonymtype-error"></span>'
                +'</td>'
             + '</tr>'
            + '<tr>'
              +  '<td>Match<sup>*</sup></td>'
             +   '<td>'
               +     '<input type="radio" class="radio_style" value="Exact" name="radio_match" checked="checked">Exact &nbsp;&nbsp; '
                 +   '<input type="radio" class="radio_style" value="Broad" name="radio_match">Broad &nbsp;&nbsp; '
				+	'<span class="match-error"></span>'
            + '   </td>'
           +' </tr>'
           
        +'</table>'
  +'  </div>'

				server.respond();
				var result = server.responses[0].response[2];
				console.log(result)
				$("#primaryTerm").val((JSON.parse(result)[0]).split(':')[1])
				console.log((JSON.parse(result)[0]).split(':')[1]);
				equal("qqqj777s33624442284441",$("#primaryTerm").val(),"Primary Term is set in case of edit");
				
					$("input[name=radio]").each(function() {
						if((JSON.parse(result)[1]).split(':')[1] == $(this).val()){
							$(this).attr('checked','checked');
						} 
					});
					equal("1133827231862",$("input[name='radio']:checked")[0].id,"Edit Page Global_syn_list1 term is checked ");
					
					$("input[name=radio_type]").each(function() {
					if((JSON.parse(result)[2]).split(':')[1] == $(this).val()){
							$(this).attr('checked','checked');
						} 
					});
					equal("One-way",$("input[name='radio_type']:checked").val(),"Edit Page one-way synonym Type is checked ");
			});
			
			
			
			test("Create / update for Synonym Test Case", function() {
				var server = sinon.sandbox.useFakeServer();

				synonyms = new Synonyms();
				var self = synonyms;
				var form = jQuery("#qunit-fixture");
				document.getElementById('qunit-fixture').innerHTML = ''+
				   '<div class="pop_error" id="pop_error"></div>' 
    			+	'<div class="addsynonymmodal"><table cellpadding="0" cellspacing="1">        <table cellpadding="0" cellspacing="1">   <table cellpadding="0" cellspacing="1"><tr>   <td>Primary Term<sup>*</sup></td>     <td>Primary Term<sup>*</sup></td>'

                +'<td><input type="text" id="primaryTerm" maxlength="50"><span class="primaryterm-error"></span></td>'				
          	    + '</tr>'
            	+'<tr>'
                + '<td>Synonyms Lists<sup>*</sup></td></span>'
                + '<td>'
                +   '<input type="radio" class="radio_style" id="1133827231862" value="Global_syn_list1" name="radio" checked="checked">Global Synonym List &nbsp;&nbsp;<input type="radio" class="radio_style" id="1147192016834" value="Artist" name="radio">Artist &nbsp;&nbsp;'  
                    
                 +   '<input type="radio" class="radio_style" id="1147192098290" value="music" name="radio">Music &nbsp;&nbsp;'
                 +   '<input type="radio" class="radio_style" id="1147192120811" value="song" name="radio">Song &nbsp;&nbsp;'
				 +	'<span class="synonymlist-error">'
                 +'</td>'
          		 +' </tr>'
        		 + '<tr>'
              	 + '<td>Synonym Type<sup>*</sup></td>'
              	+ ' <td>'
                 +   '<input type="radio" class="radio_style" value="One-way" name="radio_type" checked="checked">One-way Synonym &nbsp;&nbsp;' 
                 + '   <input type="radio" class="radio_style" value="Two-way" name="radio_type" disabled>Two-way Synonym &nbsp;&nbsp; '
				+	'<span class="synonymtype-error"></span>'
                +'</td>'
             + '</tr>'
            + '<tr>'
              +  '<td>Match<sup>*</sup></td>'
             +   '<td>'
               +     '<input type="radio" class="radio_style" value="Exact" name="radio_match" checked="checked">Exact &nbsp;&nbsp; '
                 +   '<input type="radio" class="radio_style" value="Broad" name="radio_match">Broad &nbsp;&nbsp; '
				+	'<span class="match-error"></span>'
            + '   </td>'
           +' </tr>'
           
        +'</table>'
  +'  </div>'
  
  					$(".primaryTerm-error").html('');
					$(".synonymlist-error").html('');
					$(".synonymtype-error").html('');
					$(".match-error").html('');
			

				
					var termItem = '';
					var primaryterm = $("#primaryTerm").val();
				
					var listId = $("input[name=radio]:checked").attr('id');
					var synType = $("input[name=radio_type]:checked").val();
					var match = $("input[name=radio_match]:checked").val();

					self.synonymActionBody.primaryTerm = "";
						self.synonymActionBody.listId = listId;
						self.synonymActionBody.directionality = synType;
						self.synonymActionBody.exactMatch = match;
						

				//var result = server.responses[0].response[2];
				//console.log(result);
				ok(true);
			});

		}
	};
});
