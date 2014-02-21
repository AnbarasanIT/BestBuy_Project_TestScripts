define(["framework/WidgetWithTemplate", "lib/signals", "synonyms/listener/synonymListener", "header/Header", "navigation/Navigation", "footer/Footer", "lib/handlebars-1.0.0.beta.6"] , function(template, signals) {

	Clazz.createPackage("com.components.synonyms.js"); 

	Clazz.com.components.synonyms.js.Synonyms = Clazz.extend(Clazz.WidgetWithTemplate, {
		synonymEvent : null,
		synonymHeader : null,
		localConfig: null,
		updateData: null,
		termsList: [],
		synonymListener: null,
		navigation : null,
		txtSearch :  null,
		pagination : null,
		synList : null,
		synType : null,
		editValue : null,
		sortColumn : null,
		paginationContainer :  commonVariables.paginationPlaceholder,
		synonymActionBody: {},
		header: {
			contentType: null,
			requestMethod: null,
			dataType: null,
			requestPostBody: null,
			webserviceurl: null
		},
		synonymRequestBody: {},
		searchdata: {},
		mode : "Add",
		currentValue: null,

		// template URL, used to indicate where to get the template
		templateUrl: commonVariables.contexturl + "/components/synonyms/template/synonyms.tmp",
		configUrl: "../../componens/synonyms/config/config.json",
		name : commonVariables.synonyms,
		headerContainer : commonVariables.headerPlaceholder,
		contentContainer : commonVariables.contentPlaceholder,
		footerContainer : commonVariables.footerPlaceholder,
		basewidget :  commonVariables.basePlaceholder,

		//Events, to fire a function
		onFocusOutEvent: null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig) {
			var self = this;
			self.defaultSettings();
			self.globalConfig = globalConfig;
			self.synonymListener = new Clazz.com.components.synonyms.js.listener.SynonymListener(globalConfig);
			self.navigation = new Clazz.com.components.navigation.js.Navigation();
			self.registerHandlebars();
			self.registerEvents(self.synonymListener);
		},

		/****
		 * Called before the render() function, override and add any preRender functionality
		 * here
		 * 
		 * @whereToRender The node where we are going to render the UI
		 * @renderFunction The renderTemplate function by default, use to render the UI and call bindUI afterwards
		 */
		preRender: function(whereToRender, renderFunction){
			var self = this;
			self.defaultSettings();
			self.sortColumn = null;
			self.synonymListener.getSynonymList(self.synonymListener.getRequestHeader(self.synonymRequestBody), function(response) {
				self.pageRefresh(response);
			});
		},

		/***
		 * Called after the renderTemplate() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			var self = this;
			var value = this.setSession("pageName", this.name);
			self.disableActionStyle();
			self.fillSearchData();
			if(self.sortColumn == null) {
				$("#sortcolor").addClass('sortclr');
			} else {
				$("#sortcolor").removeClass('sortclr');
			}
			$("#synonymscontent tr td").css('vertical-align','middle');
			$('.tabheader').each(function(){
				$(this).parents('th#sortcolor').children().eq(0).children().eq(0).attr('src','../themes/default/images/bestbuy/asc.png');
			});
			window.onresize = function(event) {
				self.sizeModal();
				};

		},

		/***
		 * Called once to register all the events 
		 *
		 * @synonymListener: synonymListener methods getting registered
		 */
		registerEvents : function (synonymListener) {
			var self = this;
			self.onFocusOutEvent = new signals.Signal();
			self.onFocusOutEvent.add(synonymListener.checkNameExists, synonymListener);
		},

		/***
		 * Registering handle bars to template 
		 *
		 */
		registerHandlebars : function () {
			Handlebars.registerHelper('actionSynonym', function(data) {
				var actionHtml, edit = '<img name="EditDisable" title="Edit" class="block_sym" src="../themes/default/images/bestbuy/edit_disabled.png"/>', approve = '<img name="approvalDisable" title="Approve" class="block_sym" src="../themes/default/images/bestbuy/approve_disabled.png" />', reject = '<img name="rejectDisable" title="Reject" class="block_sym" src="../themes/default/images/bestbuy/reject_disabled.png" />', deleted = '<img name="deleteDisable" title="Delete" class="block_sym" src="../themes/default/images/bestbuy/delete_disabled.png" />';				
				$.each(data, function(index, value){
					if(value.value == commonVariables.edit){
						edit = '<img name="editIcon" title="Edit" class="hand_sym" src="../themes/default/images/bestbuy/edit_icon.png"/>';
					} else if(value.value == commonVariables.approve){
						approve = '<img name="approval" title="Approve" class="hand_sym" src="../themes/default/images/bestbuy/approve_blue.png" />';
					} else if(value.value == commonVariables.reject){
						reject = '<img name="reject" title="Reject" class="hand_sym" src="../themes/default/images/bestbuy/reject_blue.png" />';
					} else if(value.value == commonVariables.deleted){
						deleted = '<img name="delete" title="Delete" class="hand_sym" src="../themes/default/images/bestbuy/delete_blue.png" />';	
					}
				});

				actionHtml = approve +' <a href="#Add_synonyms" data-toggle="modal">'+edit+'</a> '+ reject +'<a data-toggle="modal" href="#deletemsg">'+deleted+'</a>';

				return actionHtml;
			});
		},

		/***
		 * Called in once to create the synonym listener
		 *
		 */
		loadPage :function(){
			self.synonymListener = new Clazz.com.components.synonyms.js.listener.SynonymListener();
			Clazz.navigationController.push(this);
		},

		/***
		 * Setting the key value pair in the session (Localstorage)
		 *
		 */
		setSession : function(key, value) {
			if(key !== '') {
				localStorage.setItem(key, value);
			}
		},

		/***
		 * Called whenever the search is happened 
		 *
		 * @searchCriteria: Contains search text and the search type
		 */
		search : function(searchCriteria) {
			var self = this;
			var requestBody = self.synonymRequestBody;
			if (searchCriteria != null) {
				self.defaultSettings();
				requestBody.searchColumnValues = [];
				requestBody.searchColumnValues.push(searchCriteria);
				self.synonymRequestBody = requestBody;				
				self.synonymListener.getSynonymList(self.synonymListener.getRequestHeader(requestBody), function(response) {
					self.pageRefresh(response);
				});
			}
		},
		
		columnSearch : function() {
			var self = this;
			var requestBody = self.synonymRequestBody;
			var searchCriteriaArray = [];
			var renderFunction = $.proxy(self.renderTemplate, self);
			self.searchdata.primaryTerm = $("#primaryTermSearch").val();
			self.searchdata.term = $("#termSearch").val();
			self.searchdata.synonymListType = $("#synListSearch").val();
			self.searchdata.modifiedBy = $("#lastModifiedBySearch").val();
			self.searchdata.status = $("#statusSearch").val();
			
			if($("#primaryTermSearch").val() != "") {
				var searchCriteria = {};
			    searchCriteria.key = "primaryTerm";
				searchCriteria.value = $("#primaryTermSearch").val();
				searchCriteriaArray.push(searchCriteria);
			} 
			
			if($("#termSearch").val() != "") {
				var searchCriteria = {};
				searchCriteria.key = "term";
				searchCriteria.value = $("#termSearch").val();
				searchCriteriaArray.push(searchCriteria);
			} 
			
			if($("#synListSearch").val() != "") {
				var searchCriteria = {};
				searchCriteria.key = "synonymListType";
				searchCriteria.value = $("#synListSearch").val();
				searchCriteriaArray.push(searchCriteria);
			} 
			
			if( $("#lastModifiedBySearch").val() != "") {
				var searchCriteria = {};
				searchCriteria.key =  "modifiedBy";
				searchCriteria.value = $("#lastModifiedBySearch").val();
				searchCriteriaArray.push(searchCriteria);
			} 
			
			if( $("#statusSearch").val() != "") {
				var searchCriteria = {};
				searchCriteria.key =  "status";
				searchCriteria.value = $("#statusSearch").val();
				searchCriteriaArray.push(searchCriteria);
			} 	
	
			if (searchCriteria != null) {
				self.defaultSettings(); 
				requestBody.searchColumnValues = searchCriteriaArray;
				self.synonymRequestBody = requestBody;				
				self.synonymListener.getSynonymList(self.synonymListener.getRequestHeader(requestBody), function(response) {
					renderFunction(response, self.contentContainer);
				});
			} else {
				var self = this;
				self.defaultSettings();
				self.sortColumn = null;
				self.synonymListener.getSynonymList(self.synonymListener.getRequestHeader(self.synonymRequestBody), function(response) {
					self.pageRefresh(response);
				});
			} 
		},
		
		termsCombinedSearch : function() {
			var self = this;
			var renderFunction = $.proxy(self.renderTemplate, self);
			var requestBody = self.synonymRequestBody;
			var searchCriteriaArray = [];
			self.searchdata.searchterm = $("#termsCombinedSearch").val();
			
			if($("#termsCombinedSearch").val() != "") {
				var searchCriteria = {};
				var terms = {};
			    searchCriteria.key = "primaryTerm";
				searchCriteria.value = $("#termsCombinedSearch").val();
				terms.key = "term";
				terms.value = $("#termsCombinedSearch").val();
				searchCriteriaArray.push(searchCriteria);
				searchCriteriaArray.push(terms);
			}
			
			if (searchCriteria != null) {
				self.defaultSettings(); 
				requestBody.searchOper = "OR";
				requestBody.searchColumnValues = searchCriteriaArray;
				self.synonymRequestBody = requestBody;				
				self.synonymListener.getSynonymList(self.synonymListener.getRequestHeader(requestBody), function(response) {
					renderFunction(response, self.contentContainer);
				});
			} else {
				var self = this;
				self.defaultSettings();
				self.sortColumn = null;
				self.synonymListener.getSynonymList(self.synonymListener.getRequestHeader(self.synonymRequestBody), function(response) {
					self.pageRefresh(response);
				});
			}
		},
		
		fillSearchData : function() {
			var self = this;
			$("#primaryTermSearch").val(self.searchdata.primaryTerm);
			$("#termSearch").val(self.searchdata.term);
			$("#synListSearch").val(self.searchdata.synonymListType);
			$("#lastModifiedBySearch").val(self.searchdata.modifiedBy);
			$("#statusSearch").val(self.searchdata.status);
			$("#termsCombinedSearch").val(self.searchdata.searchterm);
		},

		/***
		 * Called whenever the pagination happened 
		 *
		 * @searchCriteria: Contains search text and the search type
		 * @pageIndex: indicates which page user has to navigate
		 */
		doPagination : function(searchCriteria, pageIndex,rowsPerPage) {
			var self = this;
			var listener = self.synonymListener;
			var requestBody = this.synonymRequestBody;
			requestBody.pageIndex = pageIndex;
			requestBody.rowsPerPage = rowsPerPage;
			self.synonymRequestBody = requestBody;

			self.synonymListener.getSynonymList(self.synonymListener.getRequestHeader(requestBody), function(response) {
				self.pageRefresh(response);
			});
		},

		/***
		 * Called whenever the action (Approve, Reject, Edit or Delete) is triggered  
		 *
		 * @actionBody: contains the request body
		 * @action: which action needs to be triggered
		 * @id: id of the synonyms
		 * @callback: once success or failure this callback will be fired
		 */
		getAction : function(actionBody, action, id, callback) {
			var self = this;
			self.synonymListener.synonymAction(self.synonymListener.getActionHeader(actionBody, action, id), function(response) {
				
				if(action != "editData" && response.status != "ERROR") {
					self.pageRefresh(response);
				} else {
					callback(response);
				}
				
				if (callback !== undefined) {
					callback(response);
				}
			});
		},

		/***
		 * Called whenever the pageRefresh needed  
		 *
		 * @response: service response which contains the response data
		 */
		pageRefresh : function(response) {
			var self = this;
			var renderFunction = $.proxy(self.renderTemplate, self);
			renderFunction(response, self.contentContainer);
			$("#totalCount").text("(0 Records Total)");
			
			if(response.status == "SUCCESS" && response.data.totalCountOfRecords > 0) {
				$("#totalCount").text("("+ response.data.totalCountOfRecords +" Records Total)");
				if(self.pagination != null){
					self.pagination.pageRefresh(response.data);
				}
			}
			
			setTimeout(function() {
				
				if (self.synonymRequestBody.sortOrder == "desc" ) {
					$('.tabheader').each(function(){
						if($(this).parent().attr('tabname') == self.sortColumn) {
							$("#sortcolor").children().children().eq(0).attr('src','../themes/default/images/bestbuy/small.png');
							$(this).parent().addClass('sortclr');
							$(this).find('img').attr('src', '../themes/default/images/bestbuy/asc.png');
						} 
					});
				} else if (self.synonymRequestBody.sortOrder == "asc") {
					$('.tabheader').each(function(){
						if($(this).parent().attr('tabname') == self.sortColumn) {
							$("#sortcolor").children().children().eq(0).attr('src','../themes/default/images/bestbuy/small.png');
							$(this).parent().addClass('sortclr');
							$(this).find('img').attr('src', '../themes/default/images/bestbuy/desc.png');
						} 
					});
				
				}
			}, 200);
		},
		
			
		/***
		 * Called once for the default settings of the request body 
		 *
		 */
		defaultSettings : function() {
			var self = this;
			self.synonymRequestBody.pageIndex = commonVariables.requestBody.pageIndex;
			self.synonymRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;    
			self.synonymRequestBody.sortColumn = commonVariables.requestBody.sortColumn;
			self.synonymRequestBody.sortOrder = commonVariables.requestBody.sortOrder;
			self.synonymRequestBody.searchOper = commonVariables.requestBody.searchOper;
			self.synonymRequestBody.searchColumnValues = commonVariables.requestBody.searchColumnValues;
		},

		/***
		 * Called whenever the add button is clicked 
		 *
		 */
		clearElementValues : function () {
			var self = this;
			self.mode = "Add";
			self.currentValue = null;
			$("#myModalLabel1").html('Create Synonym');
			$("#create").html('CREATE');
			$("#create").attr('title','Create');
			$("#primaryTerm").val('').attr('placeholder','Type Term name');
			$($("input[name=radio]")[0]).attr("checked", "checked");
			$($("input[name=radio_type]")[0]).attr("checked", "checked");
			$($("input[name=radio_match]")[0]).attr("checked", "checked");
			$("#primaryTerm").removeClass("error_text");
			$(".addterms").val('').removeClass("error_text").attr('placeholder','Type Term(s)');
			$(".terms_eachmain").empty();
			$(".addterms-error").html('');
			self.termsList = [];
			$("#pop_error").hide();

		},

		/***
		 * Called once to set the action events
		 *
		 */
		actionEvent : function(){
			var self = this;

			$("#AddIcon").unbind('click');
			$("#AddIcon").click(function(e) {
				self.escapePopup($("#Add_synonyms"));
				setTimeout(function() {
					$('#primaryTerm').focus();
				}, 1000);
				self.clearElementValues();
				self.sizeModal();
			});

			$(".greenfont").unbind('click');
				$(".greenfont").click(function(e) {
				var tagCloudRequestBody = {};
				tagCloudRequestBody.error = $(".addterms-error");
				tagCloudRequestBody.userInput = $(".addterms");
				tagCloudRequestBody.submitButton = $(".greenfont");
				tagCloudRequestBody.self = self;
				tagCloudRequestBody.removable = "true";
				tagCloudRequestBody.sortable = "false";
				tagCloudRequestBody.term =$('.addterms').val();
				tagCloudRequestBody.termsList = self.termsList;
				tagCloudRequestBody.enteredTerms = $('.terms_eachmain');
				tagCloudRequestBody.buttonImage = $(".closebtn");
				self.submitTerms(tagCloudRequestBody);

			});

			$('.addterms').bind('keyup', function(e) {
		    if ( e.keyCode === 13 ) { // 13 is enter key
		        // Execute code here.
		    	$(".greenfont").click();
		    	}
		    });



			$("img[name='delete']").unbind('click');
			$("img[name='delete']").click(function(e) {
				self.updateData = $(this).closest("tr");
				var deleteSynonyms = self.updateData.children().eq(1).text();
				var _self = this;
				self.navigation.alertBox("Delete", "Are you sure you want to delete <span class='delete_color'>"+ deleteSynonyms + " </span>?", function(callback){
					if(callback){
						self.synonymActionBody = {};
						self.getAction(self.synonymActionBody,"delete", $(_self).closest('tr').attr("id"));
					}
				});
			});
			
			$("img[name='approval']").unbind('click');
			$("img[name='approval']").click(function(e) {
				self.synonymActionBody = {};
				self.getAction(self.synonymActionBody,"approve", $(this).closest('tr').attr("id"));
			});
			
			$("img[name='reject']").unbind('click');
			$("img[name='reject']").click(function(e) {
				self.synonymActionBody = {};
				self.getAction(self.synonymActionBody,"reject", $(this).closest('tr').attr("id"));
			});
			
			$("img[name='editIcon']").unbind('click');
			$("img[name='editIcon']").click(function(e) {
				$(".addterms-error").html('');
				$("#create").html('UPDATE');
				$("#create").attr('title','Update');
				$("#myModalLabel1").html('Edit Synonym');
				self.mode = "Edit";

				self.updateData = $(this).closest("tr");
				var editSynonymId = self.updateData.attr('id');
				self.currentValue = self.updateData.find("#primaryName").html();

				self.synonymActionBody = {};
				self.getAction(self.synonymActionBody,"editData", editSynonymId, function(callback) {
					self.editValue = callback.data;
					$("#primaryTerm").val(self.editValue.primaryTerm).removeClass("error_text").removeAttr('placeholder');
					
					$("input[name=radio]").each(function() {
						if(self.editValue.synonymListType == $(this).val()){
							$(this).attr('checked','checked');
						} 
					});
					
					$("input[name=radio_type]").each(function() {
						if(self.editValue.directionality == $(this).val()){
							$(this).attr('checked','checked');
						} 
					});
					$("input[name=radio_match]").each(function() {				
						if(self.editValue.exactMatch == $(this).val()){
							$(this).attr('checked','checked');
						} 
					});
					
					$(".terms_eachmain").empty();
					$(".addterms").val("").removeClass("error_text").removeAttr("placeholder");
					self.termsList = self.editValue.term;
					$.each(self.termsList, function(index, value) {
						$('.terms_eachmain').append('<div class="terms_eachtab">' + value + '<img class="closebtn" name="closebtn" src="../themes/default/images/bestbuy/close_icon.png"></div>');
					});
		
					$("img[name='closebtn']").die();
					$("img[name='closebtn']").live("click", function(e){
						self.termsList.splice(self.termsList.indexOf($(this).parent().text()), 1);
						$(this).parent().remove();
					});
				});
			});
		},
		
		/***
		 * control validation 
		 */
		validation : function(){
			/* var bCheck = false;
			try{
				//Fixes users being able to submit white space as a valid entry 
				if(($("#primaryTerm").val().replace(/^\s+|\s+$/g,'')) == "") {
						
					$("#primaryTerm").focus();
					$("#primaryTerm").attr('placeholder','May not be empty');
					$("#primaryTerm").addClass("error_text");
					$("#primaryTerm").live('input', function() {
						$(this).removeClass("error_text");
					});
					
				} else if ($("input[name=radio]:checked").length == 0) {
					
					$("input[name=radio]").focus();
					$(".primaryterm-error").html('');
					$(".synonymlist-error").html('Synonym List must be selected');
				
				} else if($("input[name=radio_type]:checked").length == 0) {
					
					$(".primaryterm-error").html('');
					$(".synonymlist-error").html('');
					$("input[name=radio_type]").focus();
					$(".synonymtype-error").html('Synonym Type must be selected');
				
				} else if($("input[name=radio_match]:checked").length == 0) {
					
					$(".primaryterm-error").html('');
					$(".synonymlist-error").html('');
					$(".synonymtype-error").html('');
					$("input[name=radio_match]").focus();
					$(".match-error").html('Match Term must be selected');
				
				} else if($(".terms_eachmain").text() == "") {
					
					$(".addterms").focus();
					if( $.trim($(".addterms").val()) != "" ){
						$(".addterms-error").html('Add terms cannot be empty');
					} else {
						$(".addterms").val('');
					}
					$(".addterms").attr('placeholder','May not be empty');
					$(".addterms").addClass("error_text");
					$(".addterms").live('input', function() {
						$(this).removeClass("error_text");
					});
				
				} else { bCheck = true;}
					
				return bCheck;
				
			}catch(ex){
				 return bCheck;
			} */
			
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete
		 *
		 */
		bindUI : function(){	
			var self = this;
		
			$('.tabheader').die("click");
			$('.tabheader').live("click", function() {
				var sortOrder = "";
				self.sortColumn = $(this).parent().attr('tabname');
				if (self.synonymRequestBody.sortOrder == "desc") {
					sortOrder = "asc";
				} else if (self.synonymRequestBody.sortOrder == "asc") {
					sortOrder = "desc";
				}
				self.synonymRequestBody.pageIndex = commonVariables.requestBody.pageIndex;
				self.synonymRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;    
				self.synonymRequestBody.sortColumn = self.sortColumn;
				self.synonymRequestBody.sortOrder = sortOrder;
				self.synonymRequestBody.searchOper = commonVariables.requestBody.searchOper;
				self.synonymRequestBody.searchColumnValues = commonVariables.requestBody.searchColumnValues;
				self.synonymListener.getSynonymList(self.synonymListener.getRequestHeader(self.synonymRequestBody), function(response) {
					self.pageRefresh(response);
				});
			});
			
			$('input[name="search"]').keypress(function(e){
				var id = $(this).attr('id');
				if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
					code= (e.keyCode ? e.keyCode : e.which);
					if (code == 13) {
						if(id == "termsCombinedSearch") {
							self.termsCombinedSearch();
						} else {
							self.columnSearch();
						}
						
					}
				}
			});

			$("#primaryTerm").focusout(function() {
				var primaryTerm = $("#primaryTerm");
				if (primaryTerm.val().replace(/^\s+|\s+$/g,'') != '') {
					self.onFocusOutEvent.dispatch(primaryTerm.val(), self.mode, self.currentValue, function(response) {
						if (response.status == "ERROR") {
							primaryTerm.val('');
							var res = response.generalPurposeMessage[0].split(':');
							primaryTerm.attr('placeholder', res[1]);
							primaryTerm.addClass("error_text");
							primaryTerm.live('input', function() {
								$(this).removeClass("error_text").removeAttr('placeholder');
							});
						}
					});
				} else {
					$("#primaryTerm").val('');
				}
			});
			
			$("#create").click(function(e) {
				/* if(self.validation()){ */
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
					
					$('.terms_eachmain').children().each(function(index, value){
						if(index == 0){termItem = $(value).text();}else{termItem +=','+$(value).text();}
					});
					
					self.synonymActionBody = {};
					
					if( text === "CREATE") {	
						
						self.synonymActionBody.primaryTerm = primaryterm;
						self.synonymActionBody.listId = listId;
						self.synonymActionBody.directionality = synType;
						self.synonymActionBody.exactMatch = match;
						if(self.termsList.length > 0){self.synonymActionBody.term = self.termsList;}else{self.synonymActionBody.term = null}
						self.getAction(self.synonymActionBody, "create", "", function(response) {
							$.each(response.generalPurposeMessage, function(index,value){
								if( value == "primaryTerm:May not be empty" ) {
									var primaryTerm = value.split(':');
									var primary = primaryTerm[0];
									var msg = primaryTerm[1];
									$("#primaryTerm").attr('placeholder', msg);
									$("#primaryTerm").addClass("error_text");
									$("#primaryTerm").live('input', function() {
										$(this).removeClass("error_text").removeAttr('placeholder');
									});
										
								}
								if( value == "term:May not be empty" ) {
									var Terms = value.split(':');
									var msg = Terms[1];
									if( $.trim($(".addterms").val()) != "" ){
										$(".addterms-error").html('Add terms cannot be empty');
									} else {
										$(".addterms").val('');
									}
									$(".addterms").attr('placeholder', msg);
									$(".addterms").addClass("error_text");
									$(".addterms").live('input', function() {
										$(this).removeClass("error_text").removeAttr('placeholder');
									});
									
								}
							});
						});
						
					} else {
						var editSynonymId = self.updateData.attr('id');
						self.synonymActionBody.primaryTerm = primaryterm;
						self.synonymActionBody.synonymId = editSynonymId;
						self.synonymActionBody.statusId = self.editValue.statusId;
						self.synonymActionBody.status = self.editValue.status;
						self.synonymActionBody.listId = listId;
						self.synonymActionBody.directionality = synType;
						self.synonymActionBody.exactMatch = match;
						if(self.termsList.length > 0){self.synonymActionBody.term = self.termsList;}else{self.synonymActionBody.term = null}
						self.getAction(self.synonymActionBody,"update", "", function(response){
							$.each(response.generalPurposeMessage, function(index,value){
								if( value == "primaryTerm:May not be empty" ) {
									var primaryTerm = value.split(':');
									var primary = primaryTerm[0];
									var msg = primaryTerm[1];
									$("#primaryTerm").attr('placeholder', msg);
									$("#primaryTerm").addClass("error_text");
									$("#primaryTerm").live('input', function() {
										$(this).removeClass("error_text");
									});
										
								}
								if( value == "term:May not be empty" ) {
									var Terms = value.split(':');
									var msg = Terms[1];
									if( $.trim($(".addterms").val()) != "" ){
										$(".addterms-error").html('Add terms cannot be empty');
									} else {
										$(".addterms").val('');
									}
									$(".addterms").attr('placeholder', msg);
									$(".addterms").addClass("error_text");
									$(".addterms").live('input', function() {
										$(this).removeClass("error_text");
									});
									
								}
							});
						});
					}
				/* } */
			});
			self.actionEvent();
		}
	});
	return Clazz.com.components.synonyms.js.Synonyms;
});