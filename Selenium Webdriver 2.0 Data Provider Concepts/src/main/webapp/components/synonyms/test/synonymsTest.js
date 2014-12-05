define(["synonyms/Synonyms", "framework/NavigationController", "framework/WidgetWithTemplate", "synonyms/listener/synonymListener", "lib/sinon-1.6.min", "lib/sinon-qunit-1.0.0", "lib/sinon-qunit-1.0.0"], function(Synonyms, navigation, WidgetWithTemplate, synonymListener) {"use strict"

	var ids;

	return {
		runTests : function(configData) {

			module("Synonyms.js;Synonyms Test the Request by faking the server request for single Search", {
				setup : function() {
					this.requests = [];
					this.xhr = sinon.useFakeXMLHttpRequest();
					this.xhr.onCreate = $.proxy(function(xhr) {
						this.requests.push(xhr);
					}, this);

				},
				teardown : function() {
					//this.xhr.restore();
				}
			});
			/******************************************************************************************
			 * Single Search in synonym
			 ********************************************************************************************/
			test('Single Search', function() {

				var synonyms = new Synonyms();
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
				var callback = sinon.spy();

				var TestModel = Backbone.Model.extend({
					url : function() {
						return '/synonymgroups/loadSynonyms/';

					}
				});
				var model = new TestModel({
					"pageIndex" : "1",
					"rowsPerPage" : 50,
					"sortColumn" : "modifiedDate",
					"sortOrder" : "desc",
					"searchOper" : "AND",
					"searchColumnValues" : [{
						"key" : "primaryTerm",
						"value" : "Dummy"
					}]
				});
				model.save();
				var request = this.requests[0];
				equal(request.method, 'POST', 'Synonym Single Search POST');
				equal(request.url, '/synonymgroups/loadSynonyms/', '... to /synonymgroups/loadSynonyms endpoint');
				equal(JSON.parse(request.requestBody).searchColumnValues[0].value, 'Dummy', '... with valid Search json');
				this.xhr.restore();

				// TO DO Spy the real function so that we can test if that function was called now
				//var proxy = 	self.synonymListener.getSynonymList(self.synonymListener.getRequestHeader(self.synonymRequestBody),callback);

				//ok(proxy.called);

			});
			module("Synonyms.js;Synonyms Test the Request by faking the server for Create / Update Synonym and Combined Search", {
				setup : function() {
					this.requests = [];
					this.xhr = sinon.useFakeXMLHttpRequest();
					this.xhr.onCreate = $.proxy(function(xhr) {
						this.requests.push(xhr);
					}, this);

				},
				teardown : function() {
					//this.xhr.restore();
				}
			});
			/******************************************************************************************
			 * Create / update  in synonym
			 ********************************************************************************************/
			test('Create / Update ', function() {

				var synonyms = new Synonyms();
				var self = synonyms;
				var form = jQuery("#qunit-fixture"), output1, output2;

				document.getElementById('qunit-fixture').innerHTML = '' + '<div class="pop_error" id="pop_error"></div>' + '<div class="addsynonymmodal"><table cellpadding="0" cellspacing="1">        <table cellpadding="0" cellspacing="1">   <table cellpadding="0" cellspacing="1"><tr>   <td>Primary Term<sup>*</sup></td>     <td>Primary Term<sup>*</sup></td>' + '<td><input type="text" id="primaryTerm" maxlength="50"><span class="primaryterm-error"></span></td>' + '</tr>' + '<tr>' + '<td>Synonyms Lists<sup>*</sup></td></span>' + '<td>' + '<input type="radio" class="radio_style" id="1133827231862" value="Global_syn_list1" name="radio" checked="checked">Global Synonym List &nbsp;&nbsp;<input type="radio" class="radio_style" id="1147192016834" value="Artist" name="radio">Artist &nbsp;&nbsp;' + '<input type="radio" class="radio_style" id="1147192098290" value="music" name="radio">Music &nbsp;&nbsp;' + '<input type="radio" class="radio_style" id="1147192120811" value="song" name="radio">Song &nbsp;&nbsp;' + '<span class="synonymlist-error">' + '</td>' + ' </tr>' + '<tr>' + '<td>Synonym Type<sup>*</sup></td>' + ' <td>' + '<input type="radio" class="radio_style" value="One-way" name="radio_type" checked="checked">One-way Synonym &nbsp;&nbsp;' + '   <input type="radio" class="radio_style" value="Two-way" name="radio_type" disabled>Two-way Synonym &nbsp;&nbsp; ' + '<span class="synonymtype-error"></span>' + '</td>' + '</tr>' + '<tr>' + '<td>Match<sup>*</sup></td>' + '<td>' + '<input type="radio" class="radio_style" value="Exact" name="radio_match" checked="checked">Exact &nbsp;&nbsp; ' + '<input type="radio" class="radio_style" value="Broad" name="radio_match">Broad &nbsp;&nbsp; ' + '<span class="match-error"></span>' + '   </td>' + ' </tr>' + '</table>' + '  </div>'
				$(".primaryTerm-error").html('');
				$(".synonymlist-error").html('');
				$(".synonymtype-error").html('');
				$(".match-error").html('');
				$(".addterms-error").html('');

				var termItem = '';
				var primaryterm = $("#primaryTerm").val();
				var addTerms = $(".addterms").val();
				var text = $("#create").text();
				var listId = $("input[name=radio]:checked").attr('id');
				var synType = $("input[name=radio_type]:checked").val();
				var match = $("input[name=radio_match]:checked").val();
				$('.terms_eachmain').children().each(function(index, value) {
					if (index == 0) {
						termItem = $(value).text();
					} else {
						termItem += ',' + $(value).text();
					}
				});

				self.synonymActionBody = {};
				self.synonymActionBody.primaryTerm = primaryterm;
				self.synonymActionBody.listId = listId;
				self.synonymActionBody.directionality = synType;
				self.synonymActionBody.exactMatch = match;
				if (self.termsList.length > 0) {
					self.synonymActionBody.term = self.termsList;
				} else {
					self.synonymActionBody.term = null
				}

				var TestModel = Backbone.Model.extend({
					url : function() {
						return '/synonymgroups/update/';

					}
				});

				var model = new TestModel({
					"primaryTerm" : "asdasadsaadffvvv",
					"synonymId" : "5061",
					"statusId" : 3,
					"status" : "Approved",
					"listId" : "1133827231862",
					"directionality" : "One-way",
					"exactMatch" : "Exact",
					"term" : ["ee"]
				});
				model.save();
				var request = this.requests[0];
				equal(request.method, 'POST', 'Synonym Single Search POST');
				equal(request.url, '/synonymgroups/update/', '... to /synonymgroups/loadSynonyms endpoint');
				equal(JSON.parse(request.requestBody).primaryTerm, 'asdasadsaadffvvv', 'Value  for Update synonym is passed correctly Search Passed');
				this.xhr.restore();

				// TO DO Spy the real function so that we can test if that function was called now
				//var proxy = 	self.synonymListener.getSynonymList(self.synonymListener.getRequestHeader(self.synonymRequestBody),callback);

				//ok(proxy.called);

			});
			/******************************************************************************************
			 * Combined Search in synonym
			 ********************************************************************************************/
			test('Combined Search', function() {

				var synonyms = new Synonyms();
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
				var callback = sinon.spy();

				var TestModel = Backbone.Model.extend({
					url : function() {
						return '/synonymgroups/loadSynonyms/';

					}
				});
				var model = new TestModel({
					"pageIndex" : "1",
					"rowsPerPage" : 50,
					"sortColumn" : "modifiedDate",
					"sortOrder" : "desc",
					"searchOper" : "OR",
					"searchColumnValues" : [{
						"key" : "primaryTerm",
						"value" : "e"
					}, {
						"key" : "term",
						"value" : "e"
					}]
				});
				model.save();
				var request = this.requests[0];
				equal(request.method, 'POST', 'Synonym Single Search POST');
				equal(request.url, '/synonymgroups/loadSynonyms/', '... to /synonymgroups/loadSynonyms endpoint');
				equal(JSON.parse(request.requestBody).searchColumnValues[0].key, 'primaryTerm', 'Value of Key for Combined Search Passed');
				equal(JSON.parse(request.requestBody).searchColumnValues[0].value, 'e', 'Value  for Combined Search Passed');
				this.xhr.restore();

				// TO DO Spy the real function so that we can test if that function was called now
				//var proxy = 	self.synonymListener.getSynonymList(self.synonymListener.getRequestHeader(self.synonymRequestBody),callback);

				//ok(proxy.called);

			});
			/***********************************************New Module****************************************************************************************/
			module("Synonyms Faking response data", {
				setup : function() {
					var testData = {
						"status" : "SUCCESS",
						"message" : "Synonym deleted successfully",
						"errorCode" : null,
						"successCode" : "Delete.Success",
						"data" : {
							"primaryTerm" : "qqqj777s33624442284441",
							"directionality" : "One-way",
							"exactMatch" : "Exact",
							"synonymListType" : "Global_syn_list1",
							"startDate" : null,
							"endDate" : null,
							"id" : 0,
							"listId" : null,
							"synonymId" : 5063,
							"createdDate" : null,
							"createdBy" : null,
							"modifiedBy" : "Test.User",
							"modifiedDate" : "04-13-2013 08:46",
							"statusId" : null,
							"status" : "Deleted",
							"term" : ["ee"],
							"actions" : []
						},
						"page" : "1",
						"rows" : [],
						"generalPurposeMessage" : null
					};
					this.server = sinon.fakeServer.create();
					this.server.respondWith("GET", "/synonymgroups/delete/5063", [200, {
						"Content-Type" : "application/json"
					}, JSON.stringify(testData)]);
				},
				teardown : function() {
					this.server.restore();
				}
			});

			test("For deleting synonym", function() {

				var testData = {
					"status" : "SUCCESS",
					"message" : "Synonym deleted successfully",
					"errorCode" : null,
					"successCode" : "Delete.Success",
					"data" : {
						"primaryTerm" : "qqqj777s33624442284441",
						"directionality" : "One-way",
						"exactMatch" : "Exact",
						"synonymListType" : "Global_syn_list1",
						"startDate" : null,
						"endDate" : null,
						"id" : 0,
						"listId" : null,
						"synonymId" : 5063,
						"createdDate" : null,
						"createdBy" : null,
						"modifiedBy" : "Test.User",
						"modifiedDate" : "04-13-2013 08:46",
						"statusId" : null,
						"status" : "Deleted",
						"term" : ["ee"],
						"actions" : []
					},
					"page" : "1",
					"rows" : [],
					"generalPurposeMessage" : null
				};
				this.server = sinon.fakeServer.create();
				this.server.respondWith("GET", "/synonymgroups/delete/5063", [200, {
					"Content-Type" : "application/json"
				}, JSON.stringify(testData)]);

				var TestModel = Backbone.Model.extend({
					url : function() {
						return '/synonymgroups/delete' + (this.id ? '/' + this.id : '');
					}
				});

				var model = new TestModel({
					id : 5063
				});
				model.fetch();
				this.server.respond();

				equal(model.get('successCode'), 'Delete.Success', 'fetched model has expected attributes');
				this.server.restore();
			});

			/***********************************************New Module****************************************************************************************/
			module("Synonyms Faking response data", {
				setup : function() {
					var testData = {
						"status" : "SUCCESS",
						"message" : "Synonym deleted successfully",
						"errorCode" : null,
						"successCode" : "Delete.Success",
						"data" : {
							"primaryTerm" : "qqqj777s33624442284441",
							"directionality" : "One-way",
							"exactMatch" : "Exact",
							"synonymListType" : "Global_syn_list1",
							"startDate" : null,
							"endDate" : null,
							"id" : 0,
							"listId" : null,
							"synonymId" : 5063,
							"createdDate" : null,
							"createdBy" : null,
							"modifiedBy" : "Test.User",
							"modifiedDate" : "04-13-2013 08:46",
							"statusId" : null,
							"status" : "Deleted",
							"term" : ["ee"],
							"actions" : []
						},
						"page" : "1",
						"rows" : [],
						"generalPurposeMessage" : null
					};
					this.server = sinon.fakeServer.create();
					this.server.respondWith("GET", "/synonymgroups/delete/5063", [200, {
						"Content-Type" : "application/json"
					}, JSON.stringify(testData)]);
				},
				teardown : function() {
					this.server.restore();
				}
			});

			test("For Reject synonym", function() {

				var testData = {
					"status" : "SUCCESS",
					"message" : "Synonym rejected successfully",
					"errorCode" : null,
					"successCode" : "Reject.Success",
					"data" : {
						"primaryTerm" : "qqqj777s33624442284441",
						"directionality" : "One-way",
						"exactMatch" : "Exact",
						"synonymListType" : "Global_syn_list1",
						"startDate" : null,
						"endDate" : null,
						"id" : 0,
						"listId" : null,
						"synonymId" : 5063,
						"createdDate" : null,
						"createdBy" : null,
						"modifiedBy" : "Test.User",
						"modifiedDate" : "04-13-2013 08:46",
						"statusId" : null,
						"status" : "Rejected",
						"term" : ["ee"],
						"actions" : []
					},
					"page" : "1",
					"rows" : [],
					"generalPurposeMessage" : null
				};
				this.server = sinon.fakeServer.create();
				this.server.respondWith("GET", "/synonymgroups/reject/5063", [200, {
					"Content-Type" : "application/json"
				}, JSON.stringify(testData)]);

				var TestModel = Backbone.Model.extend({
					url : function() {
						return '/synonymgroups/reject' + (this.id ? '/' + this.id : '');
					}
				});

				var model = new TestModel({
					id : 5063
				});
				model.fetch();
				this.server.respond();

				equal(model.get('successCode'), 'Reject.Success', 'fetched model has expected attributes');
				this.server.restore();
			});
			/***********************************************New Module****************************************************************************************/
			module("Synonyms Faking response data", {
				setup : function() {

					var testData = {
						"status" : "SUCCESS",
						"message" : "The Synonym was approved",
						"errorCode" : null,
						"successCode" : "Approve.Success",
						"data" : {
							"primaryTerm" : "j777s3362444228444",
							"directionality" : "One-way",
							"exactMatch" : "Exact",
							"synonymListType" : "Global_syn_list1",
							"startDate" : null,
							"endDate" : null,
							"id" : 0,
							"listId" : null,
							"synonymId" : 5061,
							"createdDate" : null,
							"createdBy" : null,
							"modifiedBy" : "Test.User",
							"modifiedDate" : "04-13-2013 09:03",
							"statusId" : null,
							"status" : "Approved",
							"term" : ["ee"],
							"actions" : [{
								"key" : "Edit",
								"value" : "Edit"
							}, {
								"key" : "Reject",
								"value" : "Reject"
							}, {
								"key" : "Delete",
								"value" : "Delete"
							}]
						},
						"page" : "1",
						"rows" : [],
						"generalPurposeMessage" : null
					};
					this.server = sinon.fakeServer.create();

					this.server.respondWith("GET", "/synonymgroups/approve/5063", [200, {
						"Content-Type" : "application/json"
					}, JSON.stringify(testData)]);
				},
				teardown : function() {
					this.server.restore();
				}
			});

			test("For Approve synonym", function() {

				var testData = {
					"status" : "SUCCESS",
					"message" : "The Synonym was approved",
					"errorCode" : null,
					"successCode" : "Approve.Success",
					"data" : {
						"primaryTerm" : "j777s3362444228444",
						"directionality" : "One-way",
						"exactMatch" : "Exact",
						"synonymListType" : "Global_syn_list1",
						"startDate" : null,
						"endDate" : null,
						"id" : 0,
						"listId" : null,
						"synonymId" : 5061,
						"createdDate" : null,
						"createdBy" : null,
						"modifiedBy" : "Test.User",
						"modifiedDate" : "04-13-2013 09:03",
						"statusId" : null,
						"status" : "Approved",
						"term" : ["ee"],
						"actions" : [{
							"key" : "Edit",
							"value" : "Edit"
						}, {
							"key" : "Reject",
							"value" : "Reject"
						}, {
							"key" : "Delete",
							"value" : "Delete"
						}]
					},
					"page" : "1",
					"rows" : [],
					"generalPurposeMessage" : null
				};
				this.server = sinon.fakeServer.create();
				this.server.respondWith("GET", "/synonymgroups/approve/5063", [200, {
					"Content-Type" : "application/json"
				}, JSON.stringify(testData)]);

				var TestModel = Backbone.Model.extend({
					url : function() {
						return '/synonymgroups/approve' + (this.id ? '/' + this.id : '');
					}
				});

				var model = new TestModel({
					id : 5063
				});
				model.fetch();
				this.server.respond();

				equal(model.get('successCode'), 'Approve.Success', 'fetched model has expected attributes');
				this.server.restore();
			});

			module("Synonyms.js;Synonyms");

			test("Synonyms - Test Local Storage.", function() {
				var synonyms = new Synonyms();
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
				var synonyms = new Synonyms();
				var self = synonyms;
				//self.synonymRequestBody.pageIndex = commonVariables.requestBody.pageIndex;
				self.synonymRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;
				self.synonymRequestBody.sortColumn = commonVariables.requestBody.sortColumn;
				self.synonymRequestBody.sortOrder = commonVariables.requestBody.sortOrder;
				self.synonymRequestBody.searchOper = commonVariables.requestBody.searchOper;
				self.synonymRequestBody.searchColumnValues = commonVariables.requestBody.searchColumnValues;
				// setTimeout(function() {
				//	equal(self.synonymRequestBody.pageIndex, "1", "Passed!");
				equal(self.synonymRequestBody.rowsPerPage, 10, "Passed!");
				equal(self.synonymRequestBody.sortColumn, "modifiedDate", "Passed!");
				equal(self.synonymRequestBody.sortOrder, "desc", "Passed!");
				equal(self.synonymRequestBody.searchOper, "AND", "Passed!");
				// start();
				// }, 150 );
			});
			/*********************************************************************
			 *@Description : Edit Page  of Synonym
			 *@Details  : Fake server is used to get the data for the edit page , then values are set in the DOM elements
			 *@author :Asheesh Swaroop
			 * **************************************************************************/
			module("Synonyms.js;Synonyms");
			test("Edit Page for Synonym Test Case", function() {
				var synonyms = new Synonyms();
				var server = sinon.sandbox.useFakeServer();

				server.respondWith("GET", "/get/5056", [200, {
					"Content-Type" : "application/json"
				}, '["primaryTerm :qqqj777s33624442284441","listId : 1133827231862","directionality : One-way","exactMatch : Exact"]']);

				var form = jQuery("#qunit-fixture");
				document.getElementById('qunit-fixture').innerHTML = '' + '<div class="pop_error" id="pop_error"></div>' + '<div class="addsynonymmodal"><table cellpadding="0" cellspacing="1">        <table cellpadding="0" cellspacing="1">   <table cellpadding="0" cellspacing="1"><tr>   <td>Primary Term<sup>*</sup></td>     <td>Primary Term<sup>*</sup></td>' + '<td><input type="text" id="primaryTerm" maxlength="50"><span class="primaryterm-error"></span></td>' + '</tr>' + '<tr>' + '<td>Synonyms Lists<sup>*</sup></td></span>' + '<td>' + '<input type="radio" class="radio_style" id="1133827231862" value="Global_syn_list1" name="radio" checked="checked">Global Synonym List &nbsp;&nbsp;<input type="radio" class="radio_style" id="1147192016834" value="Artist" name="radio">Artist &nbsp;&nbsp;' + '<input type="radio" class="radio_style" id="1147192098290" value="music" name="radio">Music &nbsp;&nbsp;' + '<input type="radio" class="radio_style" id="1147192120811" value="song" name="radio">Song &nbsp;&nbsp;' + '<span class="synonymlist-error">' + '</td>' + ' </tr>' + '<tr>' + '<td>Synonym Type<sup>*</sup></td>' + ' <td>' + '<input type="radio" class="radio_style" value="One-way" name="radio_type" checked="checked">One-way Synonym &nbsp;&nbsp;' + '   <input type="radio" class="radio_style" value="Two-way" name="radio_type" disabled>Two-way Synonym &nbsp;&nbsp; ' + '<span class="synonymtype-error"></span>' + '</td>' + '</tr>' + '<tr>' + '<td>Match<sup>*</sup></td>' + '<td>' + '<input type="radio" class="radio_style" value="Exact" name="radio_match" checked="checked">Exact &nbsp;&nbsp; ' + '<input type="radio" class="radio_style" value="Broad" name="radio_match">Broad &nbsp;&nbsp; ' + '<span class="match-error"></span>' + '   </td>' + ' </tr>' + '</table>' + '  </div>'

				server.respond();
				var result = server.responses[0].response[2];
				console.log(result)
				$("#primaryTerm").val((JSON.parse(result)[0]).split(':')[1])
				console.log((JSON.parse(result)[0]).split(':')[1]);
				equal("qqqj777s33624442284441", $("#primaryTerm").val(), "Primary Term is set in case of edit");

				$("input[name=radio]").each(function() {
					if ((JSON.parse(result)[1]).split(':')[1] == $(this).val()) {
						$(this).attr('checked', 'checked');
					}
				});
				equal("1133827231862", $("input[name='radio']:checked")[0].id, "Edit Page Global_syn_list1 term is checked ");

				$("input[name=radio_type]").each(function() {
					if ((JSON.parse(result)[2]).split(':')[1] == $(this).val()) {
						$(this).attr('checked', 'checked');
					}
				});
				equal("One-way", $("input[name='radio_type']:checked").val(), "Edit Page one-way synonym Type is checked ");
			});
			
			/**********************************************************************************************
			 *@Description : Test Module for the view /HTML DOM elements in Synonym Page 
			 * 
			 *  ************************************************************************************************/
			//module("Synonyms.js;Synonyms");
			test("Test Module for the view /HTML DOM elements in Synonym Page ", function() {
				var synonyms = new Synonyms();


				var form_element = jQuery("#qunit-fixture");
				//document.getElementById('qunit-fixture').innerHTML = '' + '<div class="pop_error" id="pop_error"></div>' + '<div class="addsynonymmodal"><table cellpadding="0" cellspacing="1">        <table cellpadding="0" cellspacing="1">   <table cellpadding="0" cellspacing="1"><tr>   <td>Primary Term<sup>*</sup></td>     <td>Primary Term<sup>*</sup></td>' + '<td><input type="text" id="primaryTerm1" maxlength="50"><span class="primaryterm-error"></span></td>' + '</tr>' + '<tr>' + '<td>Synonyms Lists<sup>*</sup></td></span>' + '<td>' + '<input type="radio" class="radio_style" id="1133827231862" value="Global_syn_list1" name="radio" checked="checked">Global Synonym List &nbsp;&nbsp;<input type="radio" class="radio_style" id="1147192016834" value="Artist" name="radio">Artist &nbsp;&nbsp;' + '<input type="radio" class="radio_style" id="1147192098290" value="music" name="radio">Music &nbsp;&nbsp;' + '<input type="radio" class="radio_style" id="1147192120811" value="song" name="radio">Song &nbsp;&nbsp;' + '<span class="synonymlist-error">' + '</td>' + ' </tr>' + '<tr>' + '<td>Synonym Type<sup>*</sup></td>' + ' <td>' + '<input type="radio" class="radio_style" value="One-way" name="radio_type" checked="checked">One-way Synonym &nbsp;&nbsp;' + '   <input type="radio" class="radio_style" value="Two-way" name="radio_type" disabled>Two-way Synonym &nbsp;&nbsp; ' + '<span class="synonymtype-error"></span>' + '</td>' + '</tr>' + '<tr>' + '<td>Match<sup>*</sup></td>' + '<td>' + '<input type="radio" class="radio_style" value="Exact" name="radio_match" checked="checked">Exact &nbsp;&nbsp; ' + '<input type="radio" class="radio_style" value="Broad" name="radio_match">Broad &nbsp;&nbsp; ' + '<span class="match-error"></span>' + '   </td>' + ' </tr>' + '</table>' + '  </div>'


				//if ($("#primaryTerm1").val() == "" || $("#primaryTerm1").val() == null){
				//	$("#primaryTerm1").attr('placeholder','May not be empty');
			//	}
			//	var primaryTermEmpty = $("#primaryTerm").attr("placeholder");
				//same("May not be empty","May not be empty","Error Message is displayed if Primary Term is blank and DOM element placeholder is changed Passed")
				ok(true);
			});
			
		}
	};
});
