define(["framework/WidgetWithTemplate", "lib/signals", "keywords/listener/KeywordListener", "lib/handlebars-1.0.0.beta.6"] , function(template, signals) {

    Clazz.createPackage("com.components.keywords.js");

    Clazz.com.components.keywords.js.Keyword = Clazz.extend(Clazz.WidgetWithTemplate, {
        keywordEvent : null,
        keywordHeader : null,
        localConfig: null,
        updateData: null,
        termsList: null,
        keywordListener: null,
        navigation : null,
        whereToRender : null,
        renderFunction: null,
        searchText: null,
        pagination : null,
        editValue : [],
		sortColumn : null,
        paginationContainer :  commonVariables.paginationPlaceholder,
        
        header: {
            contentType: null,
            requestMethod: null,
            dataType: null,
            requestPostBody: null,
            webserviceurl: null
        },
        
        keywordRequestBody : {},
		searchdata : {},
        mode : "Add",
        currentValue: null,
        
        // template URL, used to indicate where to get the template
        templateUrl: "../components/keywords/template/keyword.tmp",
        configUrl: "../../componens/keywords/config/config.json",
        name : commonVariables.keywords,
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
            self.globalConfig = globalConfig;
            self.keywordListener = new Clazz.com.components.keywords.js.listener.KeywordListener(globalConfig);
            self.keywordRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;
            self.registerHandlebars();
            self.registerEvents(self.keywordListener);
			self.defaultSettings();
        },

        /***
         * Registering handle bars to template 
         *
         * @return action HTML which contains edit, approve, reject and delete icons
         */
        registerHandlebars : function () {
            Handlebars.registerHelper('actionKeyword', function(data) {
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

                actionHtml = approve +' <a href="#Add_keywords" data-toggle="modal">'+ edit+'</a> '+ reject +'<a data-toggle="modal" href="#deletemsg">'+deleted+'</a>';
                return actionHtml;
            });
            Handlebars.registerHelper('trunc', function (context, options) {
               var chars = options.hash.chars || 20;
               var result = context;
                if (context.length > chars) {
                 result = result.substring(0, chars);
                if (options.hash.append) {
                    result += options.hash.append;
                 }
                }
                return new Handlebars.SafeString(result);
            });
        },

        /***
         * Called whenever the page is invocated
         * Sets the detaSettings
         * @element: Element as the result of the template + data binding
         */     
        preRender: function(whereToRender, renderFunction) {
            var self = this;
			self.sortColumn = null;
            self.defaultSettings();
            self.keywordListener.getKeywordList(self.keywordListener.getRequestHeader(self.keywordRequestBody), function(response) {
                self.pageRefresh(response);
            });
        },

        /***
         * Called after the preRender() and bindUI() completes. 
         *
         * @element: Element as the result of the template + data binding
         */
        postRender : function(element) {
			$('.tabheader').each(function(){
				$(this).parents('th#sortcolor').children().eq(0).children().eq(0).attr('src','../themes/default/images/bestbuy/asc.png');
			});
            var self = this;
			if(self.sortColumn == null) {
				$("#sortcolor").addClass('sortclr');
			} else {
				$("#sortcolor").removeClass('sortclr');
			}
			self.headerStyle();	
			self.fillSearchData();
			self.disableActionStyle();
            var value = self.setSession("pageName",self.name);
        },

        /***
         * Called once to register all the events 
         *
         * @keywordListener: keywordListener methods getting registered
         */
        registerEvents : function (keywordListener) {
            var self = this;
            self.onFocusOutEvent = new signals.Signal();
            self.onFocusOutEvent.add(keywordListener.checkNameExists, keywordListener);
        },

        /**
         * Called during the page refresh, displays the message, and tomat number of records
         * @response: response from the service
         */
		
		pageRefresh : function(response) {
			var self = this;
			var renderFunction = $.proxy(self.renderTemplate, self);
			
			renderFunction(response, self.contentContainer);
			$("#totalCount").text("(0 Records Total)");
			
			if(response.status == "SUCCESS" && response.data.totalCountOfRecords > 0) {
				$("#totalCount").text("("+ response.data.totalCountOfRecords +" Records Total)");
				self.pagination.pageRefresh(response.data);
			}
			setTimeout(function() {
				if (self.keywordRequestBody.sortOrder == "desc" ) {
					$('.tabheader').each(function(){
						if($(this).parent().attr('tabname') == self.sortColumn) {
							$("#sortcolor").children().children().eq(0).attr('src','../themes/default/images/bestbuy/small.png');
							$(this).parent().addClass('sortclr');
							$(this).find('img').attr('src', '../themes/default/images/bestbuy/asc.png');
						} 
					});
				} else if (self.keywordRequestBody.sortOrder == "asc") {
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

        /**
         * Triggers the action and refreshes the page
         * @actionBody: body of the request for this action
         * @action: action to be performed
         * @id: id of the object
         * @callback: callback method which should should be triggered
         */
        getAction : function(actionBody, action, id, callback) {
            var self = this;
            self.keywordListener.keywordAction(self.keywordListener.getActionHeader(actionBody, action, id), function(response) {
                if(action != "editData" && response.status != "ERROR") {
                    self.pageRefresh(response);
                } else {
                    callback(response);
                }
            });
        },  
        
        /**
         * Called in once when this page is initialized
         */
        loadPage :function() {
            var keywordListener = new Clazz.com.compnents.keywords.js.listener.KeywordListener();       
            Clazz.navigationController.push(this);
        },
        
        /**
         * Stores the session into local storage
         * @key: key for the storage
         * @value: value to be stored into the session
         */
        setSession : function(key, value) {
            if(key !== '') {
                localStorage.setItem(key,value);
            }
        },

        /**
         * Returns the value from the local storage
         * @key: key for the storage
         */
        getSession : function(key) {
            if (key !== '') {
                return localStorage.getItem(key);
            }
        },
        
        /**
         * DefaultSettings for the AJAX requests
         */
        defaultSettings : function() {
			var self = this;
			self.keywordRequestBody.pageIndex = commonVariables.requestBody.pageIndex;
			self.keywordRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;    
			self.keywordRequestBody.sortColumn = commonVariables.requestBody.sortColumn;
			self.keywordRequestBody.sortOrder = commonVariables.requestBody.sortOrder;
			self.keywordRequestBody.searchOper = commonVariables.requestBody.searchOper;
			self.keywordRequestBody.searchColumnValues = commonVariables.requestBody.searchColumnValues;
		},

        /***
         * Called whenever the add button is clicked 
         *
         */
        clearElementValues : function () {
            var self = this;
            self.mode = "Add";
            $("#redirectterm").val('').removeClass("error_text").attr('placeholder', 'Enter a redirect term:');
            $("#redirecturl").val('').removeClass("error_text").attr('placeholder', 'Enter a redirect URL');
            $("#startDate").val('').attr('placeholder','Click to select date.');
            $("#endDate").val('').attr('placeholder','Click to select date.');;
            $("#keywordLabel").html('Create Keyword Redirect');
            $("#create").html('CREATE');
			$("#create").attr('title','Create');
			$("#pop_error").hide();
			$(".startdate-error").empty();
			$(".enddate-error").empty();
        },

        /**
         * Events for each of the actions
         */
        actionEvent : function() {
            var self = this;
            
            $("#AddIcon").unbind('click');
            $("#AddIcon").click(function(e) {
				self.escapePopup($("#Add_keywords"));
				setTimeout(function() {
					$('#redirectterm').focus();
				}, 1000);
                self.clearElementValues();
				
				var d = new Date();
				var curr_date = ((d.getDate()+"").length === 1 ? "0" : "") + d.getDate()+"";
				var month = d.getMonth()+1;
				var curr_month = ((month+"").length === 1 ? "0" : "") + month+"";  //Months are zero based
				var curr_year = d.getFullYear();
				/* var curr_hh = ((d.getHours()+"").length === 1 ? "0" : "") + d.getHours()+"";
				var curr_min = ((d.getMinutes()+"").length === 1 ? "0" : "") + d.getMinutes()+""; */
				var hours = 0;
				var curr_hh = ((hours+"").length === 1 ? "0" : "") + hours +""; 
				var minutes = 0;
				var curr_min = ((minutes+"").length === 1 ? "0" : "") + minutes +"";
				var newdate = curr_month + "-" + curr_date + "-" + curr_year + " " + curr_hh + ":" + curr_min;
				$("#startDate").val(newdate);
				
            });
            
            $("img[name='delete']").unbind('click');
            $("img[name='delete']").click(function(e) { 
				self.updateData = $(this).closest("tr");
				var deleteKeywords = self.updateData.children().eq(1).text();
                var _self = this, keywordid;
                self.navigation = new Clazz.com.components.navigation.js.Navigation();
                self.header.contentType = "application/json";
                self.header.requestMethod = "PUT";
                self.header.requestPostBody = "";
                self.header.dataType = "json";
                keywordid = $(_self).closest('tr').attr('id');
                    
                self.navigation.alertBox("Delete", "Are you sure you want to delete <span class='delete_color'>"+ deleteKeywords + " </span>?", function(callback){
                    if(callback) {                      
                        self.keywordActionBody = {};
                        self.getAction(self.keywordActionBody, "delete", keywordid);                            
                    }   
                });
            });
            
            $("img[name='approval']").unbind('click');
            $("img[name='approval']").click(function(e) {
                self.keywordActionBody = {};
                self.getAction(self.keywordActionBody, "approve", $(this).closest('tr').attr("id"));
            });
            
            $("img[name='reject']").unbind('click');
            $("img[name='reject']").click(function(e) {
                self.keywordActionBody = {};
                self.getAction(self.keywordActionBody, "reject", $(this).closest('tr').attr("id"));
            });
            
            $("img[name='editIcon']").unbind('click');
            $("img[name='editIcon']").click(function(e) {
                self.mode = "Edit";
                $("#keywordLabel").html('Edit Keyword Redirect');
                $("#create").html('UPDATE');
				$("#create").attr('title','Update');
                self.updateData = $(this).closest("tr"); 
                var redirectId = self.updateData.attr('id');
                self.currentValue = self.updateData.find("#redirectTerm").html();

                self.keywordActionBody = {};
                self.getAction(self.keywordActionBody, "editData", redirectId, function(callback) {
                    self.editValue = callback.rows;
                    $("#redirectterm").val(self.editValue[0].redirectTerm).removeClass("error_text").removeAttr('placeholder'); 
                    $("#redirecturl").val(self.editValue[0].redirectUrl).removeClass("error_text").removeAttr('placeholder');
                    $("#profile").val(self.editValue[0].searchProfileType).removeClass("error_text");               
                    $("#startDate").val(self.editValue[0].startDate);
                    $("#endDate").val(self.editValue[0].endDate);
                    $("#profile").each(function() {
                        if(self.editValue[0].searchProfileType == $(this).val()) {
                            $(this).attr('selected','selected');
                        } 
                    });
                });
				$(".startdate-error").empty();
				$(".enddate-error").empty();
            });                 
        },
        
        /***
         * Bind the action listeners. The bindUI() is called automatically after the render is complete 
         */
        bindUI : function() {
            var self = this;
            $('.tabheader').die("click");
			$('.tabheader').live("click", function() {
				var sortOrder = "";
				self.sortColumn = $(this).parent().attr('tabname');
				if (self.keywordRequestBody.sortOrder == "desc") {
					sortOrder = "asc";
				} else if (self.keywordRequestBody.sortOrder == "asc") {
					sortOrder = "desc";
				}
				self.keywordRequestBody.pageIndex = commonVariables.requestBody.pageIndex;
				self.keywordRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;    
				self.keywordRequestBody.sortColumn = self.sortColumn;
				self.keywordRequestBody.sortOrder = sortOrder;
				self.keywordRequestBody.searchOper = commonVariables.requestBody.searchOper;
				self.keywordRequestBody.searchColumnValues = commonVariables.requestBody.searchColumnValues;
				self.keywordListener.getKeywordList(self.keywordListener.getRequestHeader(self.keywordRequestBody), function(response) {
					self.pageRefresh(response);
				});
			});
            
            $("#redirectterm").focusout(function() {
                var redirectTerm = $("#redirectterm");

                if (redirectTerm.val().replace(/^\s+|\s+$/g,'') != '') {
                    self.onFocusOutEvent.dispatch(redirectTerm.val(), self.mode, self.currentValue, function(response) {
                        if (response.status == "ERROR") {
                            redirectTerm.val('');
                            var res = response.generalPurposeMessage[0].split(':');
							redirectTerm.attr('placeholder', res[1]);
                            redirectTerm.addClass("error_text");
                            redirectTerm.live('input', function() {
                                $(this).removeClass("error_text").removeAttr('placeholder');
                            });
                        }
                    });
                } else {
					$("#redirectterm").val('');
				}
            });
			
			$("#redirecturl").focusout(function() {
				if ($(this).val().replace(/^\s+|\s+$/g,'') == "") {
					$(this).val('');
				}
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
            
			/***
			 * Date time picker for start date
			 */
			$("#startDate").datetimepicker({
				minDate: 0,
				dateFormat: 'mm-dd-yy',
                changeMonth: true,
				showOn: "both",
				beforeShow: function() {
					self.showingDatePicker = true;
					setTimeout(function(){
						$('.ui-datepicker').css('z-index', 1050);
					}, 0);
                    var oldDate = $("#startDate").val();
                    $("#startDate").datepicker("option", "maxDate",$('#endDate').datepicker('getDate'));
                    $("#startDate").val(oldDate);

				},
				onClose: function() {
					self.showingDatePicker = false;
					self.escapePopup($("#Add_keywords"));

                    startDateTime = parseInt($("#startDate").val().substr(-5).replace(':',''));
                    endDateTime = parseInt($("#endDate").val().substr(-5).replace(':',''));

                    startDateVal = $("#startDate").val().replace($("#startDate").val().substr(-5), '');
                    endDateVal = $("#endDate").val().replace($("#endDate").val().substr(-5), '');


                    if((startDateVal == endDateVal) && (startDateTime >= endDateTime) ){

                        $("#startDate").val($("#startDate").val().replace($("#startDate").val().substr(-5), '00:00'));
                        $(".startdate-error").text("Start Time must come before the End Time, both times have been reset.");

                        $("#endDate").val($("#endDate").val().replace($("#endDate").val().substr(-5), '12:00'));
                    }
				}
			});
			
			/***
			 * Date time picker for end date
			 */
			$("#endDate").datetimepicker({
				dateFormat: 'mm-dd-yy',
                changeMonth: true,
				showOn: "both",
				beforeShow: function() {
					self.showingDatePicker = true;
					var $endDate = $('#startDate').datepicker('getDate');
					if($endDate != null){
						var currEndValu = $("#endDate").val();
						$endDate.setDate($endDate.getDate());
						$("#endDate").datepicker("option", "minDate", $endDate);
						$("#endDate").val(currEndValu);
					}
					setTimeout(function(){
						$('.ui-datepicker').css('z-index', 1050);
					}, 0);
				},
				onClose: function(selectedDate) {
					self.showingDatePicker = false;
					self.escapePopup($("#Add_keywords"));

                    var oldDate = $("#startDate").val();
                    $("#startDate").datepicker("option", "maxDate",selectedDate);

                    if($("#startDate").val() != ""){
                            $("#startDate").val($("#startDate").val()+" "+oldDate.substr(-5));
                    }
                    $("#startDate").val(oldDate);

                    startDateTime = parseInt($("#startDate").val().substr(-5).replace(':',''));
                    endDateTime = parseInt($("#endDate").val().substr(-5).replace(':',''));

                    startDateVal = $("#startDate").val().replace($("#startDate").val().substr(-5), '');
                    endDateVal = $("#endDate").val().replace($("#endDate").val().substr(-5), '');

                    if((startDateVal == endDateVal) && (startDateTime >= endDateTime) ){

                        $("#startDate").val($("#startDate").val().replace($("#startDate").val().substr(-5), '00:00'));
                        $("#endDate").val($("#endDate").val().replace($("#endDate").val().substr(-5), '12:00'));
                        $(".enddate-error").text("End Time must come after the Start Time, both times have been reset.");
                    }
				}
			});
			
            $("#create").click( function(e) {
				/* if (!validate()) { */
					var redirectterm = $("#redirectterm").val();
					var redirecturl = $("#redirecturl").val();
					var startdate = $("#startDate").val();
					var enddate = $("#endDate").val();
					var profileId = $("#profile").val();
					var text = $("#create").text();		
					$('.startdate-error').text('');
					$('.enddate-error').text('');
					
					self.keywordActionBody = {};
					
					if (text == "CREATE") {                      
						self.keywordActionBody.redirectTerm = redirectterm;
						self.keywordActionBody.redirectUrl = redirecturl;
						self.keywordActionBody.redirectType = "URL";
						self.keywordActionBody.searchProfileId = profileId;
						self.keywordActionBody.startDate = startdate;
						self.keywordActionBody.endDate = enddate;
						self.getAction(self.keywordActionBody,"create", "", function(response){
							$.each(response.generalPurposeMessage, function(index,value){
								if(value == "redirectTerm:May not be empty") {
									var redirectTerm = value.split(':');
									var msg = redirectTerm[1];
									$("#redirectterm").attr('placeholder', msg);
									$("#redirectterm").addClass("error_text");
									$("#redirectterm").live('input', function() {
										$(this).removeClass("error_text").removeAttr('placeholder');
									});
								}
								if(value == "redirectUrl:May not be empty") {
									var redirectUrl = value.split(':');
									var msg = redirectUrl[1];
									$("#redirecturl").attr('placeholder', msg);
									$("#redirecturl").addClass("error_text");
									$("#redirecturl").live('input', function() {
										$(this).removeClass("error_text").removeAttr('placeholder');
									});
								}
							});
						});
					} else {
						redirectId = self.updateData.attr('id');
						self.keywordActionBody.redirectTerm = redirectterm;
						self.keywordActionBody.redirectUrl = redirecturl;
						self.keywordActionBody.redirectId = redirectId;
						self.keywordActionBody.redirectType = self.editValue[0].redirectType;
						self.keywordActionBody.searchProfileId = profileId;
						self.keywordActionBody.status = self.editValue[0].status;
						self.keywordActionBody.statusId = self.editValue[0].statusId;
						self.keywordActionBody.startDate = startdate;
						self.keywordActionBody.endDate = enddate;
						self.getAction(self.keywordActionBody, "update", "", function(response){
							$.each(response.generalPurposeMessage, function(index,value){
								if(value == "redirectTerm:May not be empty") {
									var redirectTerm = value.split(':');
									var msg = redirectTerm[1];
									$("#redirectterm").attr('placeholder', msg);
									$("#redirectterm").addClass("error_text");
									$("#redirectterm").live('input', function() {
										$(this).removeClass("error_text");
									});
								}
								if(value == "redirectUrl:May not be empty") {
									var redirectUrl = value.split(':');
									var msg = redirectUrl[1];
									$("#redirecturl").attr('placeholder', msg);
									$("#redirecturl").addClass("error_text");
									$("#redirecturl").live('input', function() {
										$(this).removeClass("error_text");
									});
								}
							});
						});
					}
				/* } */
            });
			
			/***
			 * Validate the mandatory fields
			 *
			 */
			$(".ui-datepicker-trigger, #startDate, #endDate").click(function() {
				$('.startdate-error').text('');
				$('.enddate-error').text('');
			});
			 
			/* function validate() {
				var hasError = false;
				var redirectterm = $("#redirectterm").val();
				var redirecturl = $("#redirecturl").val();
				var startdate = $("#startDate").val();
				var enddate = $("#endDate").val();
				var profileId = $("#profile").val();
				var text = $("#create").text();
				
				//Fixes users being able to submit white space as a valid entry 
				if (redirectterm.replace(/^\s+|\s+$/g,'') == "") {
					$("#redirectterm").focus();
					$("#redirectterm").attr('placeholder','May not be empty');
					$("#redirectterm").addClass("error_text");
					$("#redirectterm").live('input', function() {
						$(this).removeClass("error_text");
					});
					hasError = true;
				} else if ($.trim(redirecturl) == "") {
					$("#redirecturl").val('');
					$("#redirecturl").focus();
					$("#redirecturl").attr('placeholder','May not be empty');
					$("#redirecturl").addClass("error_text");
					$("#redirecturl").live('input', function() {
						$(this).removeClass("error_text");
					});
					hasError = true;
				} else if ($.trim(profileId) == "") {                
					$("#profile").val('');
					$("#profile").focus();
					$(".redirectterm-error").html('');
					$(".redirecturl-error").html('');
					$(".global-error").html('Profile Id must be selected');
					hasError = true;
				} else if (startdate == "Start Date" || startdate == "") {
					$('.startdate-error').text('Enter Start Date');
					hasError = true;
				} else {
                    var errorJson = self.validateStartEndDate(startdate, enddate);
                    if (errorJson.hasError) {
                        if (errorJson.errorIn == "startDate") {
                            $('.startdate-error').text(errorJson.errorMsg);
                        } else if (errorJson.errorIn == "endDate") {
                            $('.enddate-error').text(errorJson.errorMsg);
                        }
						hasError = true;
                    }
                }
				
				return hasError;
			} */
			
			self.actionEvent();
        },

        /**
         * Triggers the search call and returns the data
         * @txtSearch - text to be searched
         */
        search :function (txtSearch) {
            var self = this;
            var requestBody = self.keywordRequestBody;
            if (txtSearch != null) {
                self.defaultSettings();
                requestBody.searchColumnValues = [];
                requestBody.searchColumnValues.push(txtSearch);
                self.keywordRequestBody = requestBody;              
                self.keywordListener.getKeywordList(self.keywordListener.getRequestHeader(requestBody), function(response) {
                    self.pageRefresh(response);
                });
            }
        },
		
		columnSearch : function() {
			var self = this;
			var renderFunction = $.proxy(self.renderTemplate, self);
			var requestBody = self.keywordRequestBody;
			var searchCriteriaArray = [];
			
			self.searchdata.redirectTerm = $("#termsSearch").val();
			self.searchdata.redirectUrl = $("#urlSearch").val();
			self.searchdata.searchProfileType = $("#searchProfileSearch").val();
			self.searchdata.modifiedBy = $("#lastModifiedBySearch").val();
			self.searchdata.status = $("#statusSearch").val();
			
			if( $("#termsSearch").val() != "") {
				var searchCriteria = {};
			    searchCriteria.key = "redirectTerm";
				searchCriteria.value = $("#termsSearch").val();
				searchCriteriaArray.push(searchCriteria);
			} 
			
			if($("#urlSearch").val() != "") {
				var searchCriteria = {};
				searchCriteria.key = "redirectUrl";
				searchCriteria.value = $("#urlSearch").val();
				searchCriteriaArray.push(searchCriteria);
			} 
			
			if($("#searchProfileSearch").val() != "") {
				var searchCriteria = {};
				searchCriteria.key = "searchProfileType";
				searchCriteria.value = $("#searchProfileSearch").val();
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
				//requestBody.searchColumnValues.push(searchCriteria);
				self.keywordRequestBody = requestBody;				
				self.keywordListener.getKeywordList(self.keywordListener.getRequestHeader(requestBody), function(response) {
					renderFunction(response, self.contentContainer);
				});
			} else {
				var self = this;
				self.sortColumn = null;
				self.defaultSettings();
				self.keywordListener.getKeywordList(self.keywordListener.getRequestHeader(self.keywordRequestBody), function(response) {
					self.pageRefresh(response);
				});
			}
		},
		
		termsCombinedSearch : function() {
			var self = this;
			var renderFunction = $.proxy(self.renderTemplate, self);
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
			
			if (searchCriteria != null) {
				self.defaultSettings(); 
				requestBody.searchOper = "OR";
				requestBody.searchColumnValues = searchCriteriaArray;
				self.keywordRequestBody = requestBody;				
				self.keywordListener.getKeywordList(self.keywordListener.getRequestHeader(requestBody), function(response) {
					renderFunction(response, self.contentContainer);
				});
			} else {
				var self = this;
				self.sortColumn = null;
				self.defaultSettings();
				self.keywordListener.getKeywordList(self.keywordListener.getRequestHeader(self.keywordRequestBody), function(response) {
					self.pageRefresh(response);
				});
			}
		},
		
		fillSearchData : function() {
			var self = this;
			$("#termsSearch").val(self.searchdata.redirectTerm);
			$("#urlSearch").val(self.searchdata.redirectUrl);
			$("#searchProfileSearch").val(self.searchdata.searchProfileType);
			$("#lastModifiedBySearch").val(self.searchdata.modifiedBy);
			$("#statusSearch").val(self.searchdata.status);
			$("#termsCombinedSearch").val(self.searchdata.searchterm);
		},
        
        /**
         * Triggers the pagination actions 
         * @txtSearch - text to be searched
         * @pageIndex - index of the page 
         * Change done at SPT-4212 SPT-3791 Pagination bar should have a dropdown that allows the user
         *  to select more rows. Options: 50, 100, 250, 500, All.
         */
        doPagination : function(txtSearch,pageIndex,rowsPerPage) {
            var self = this;
            var listener = self.keywordRequestBody;
            var requestBody = this.keywordRequestBody;
            requestBody.pageIndex = pageIndex;
            requestBody.rowsPerPage = rowsPerPage;
            self.keywordRequestBody = requestBody;

            self.keywordListener.getKeywordList(self.keywordListener.getRequestHeader(requestBody), function(response) {
                self.pageRefresh(response);
            });
        }
    
    });

    return Clazz.com.components.keywords.js.Keyword;
});