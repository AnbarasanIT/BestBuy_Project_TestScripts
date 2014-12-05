define(["framework/WidgetWithTemplate", "useradmin/listener/UseradminListener", "navigation/Navigation"] , function() {

    Clazz.createPackage("com.components.useradmin.js");

    Clazz.com.components.useradmin.js.Useradmin = Clazz.extend(Clazz.WidgetWithTemplate, {
        userAdminEvent : null,
        userAdminHeader : null,
        localConfig: null,
        updateData: null,
        termsList: null,
        userAdminListener: null,
        navigation : null,
        txtSearch :  null,
        pagination : null,
        editValue : null,
        paginationContainer :  commonVariables.paginationPlaceholder,
        useradminActionBody: {},        
        header: {
            contentType: null,
            requestMethod: null,
            dataType: null,
            requestPostBody: null,
            webserviceurl: null
        },
        useradminRequestBody: {},
		
        // template URL, used to indicate where to get the template
        templateUrl: "../components/useradmin/template/useradmin.tmp",
        configUrl: "../../componens/useradmin/config/config.json",
        name : commonVariables.useradmin,
        headerContainer : commonVariables.headerPlaceholder,
        contentContainer : commonVariables.contentPlaceholder,
        footerContainer : commonVariables.footerPlaceholder,
        basewidget :  commonVariables.basePlaceholder,
        
        /***
         * Called in initialization time of this class 
         *
         * @globalConfig: global configurations for this class
         */
        initialize : function(globalConfig) {
            var self = this;
            self.globalConfig = globalConfig;       
            self.userAdminListener = new Clazz.com.components.useradmin.js.listener.UseradminListener();
            self.registerHandlebars();
			self.defaultSettings();
        },
        
        registerHandlebars : function () {
            Handlebars.registerHelper('actionUserAdmin', function(data) {
                var actionHtml, edit = '<img name="EditDisable" title="Edit" class="block_sym" src="../themes/default/images/bestbuy/edit_disabled.png"/>';
                var deleted = '<img name="deleteDisable" title="Delete" class="block_sym" src="../themes/default/images/bestbuy/delete_disabled.png" />';
                $.each(data, function(index, value) {
                    if(value.value == "Edit"){
                        edit = '<img name="editIcon" title="Edit" class="hand_sym" src="../themes/default/images/bestbuy/edit_icon.png"/>';
                    }
                });             
                actionHtml = '<td class="center_td"><a href="#Add_useradmin" data-toggle="modal">'+ edit +'</a></td>';
                return actionHtml;
            });
        },

        /**
         * Called in once when this page is initialized
         */
        loadPage :function() {
            var useradminListener = new Clazz.com.components.useradmin.js.listener.UseradminListener();
            Clazz.navigationController.push(this);
        },

        /**
         * Stores the session into local storage
         * @key: key for the storage
         * @value: value to be stored into the session
         */
        setSession : function(key, value) {
            if(key !== '') {
                localStorage.setItem(key, value);
            }
        },
               
        /***
         * Called whenever the page is invocated
         * Sets the detaSettings
         * @whereToRender: the place to render the component
         * @renderFunction: the function which renders the component
         */
        preRender: function(whereToRender, renderFunction) {
            var self = this;
            self.defaultSettings();
            self.userAdminListener.getUserAdmin(self.userAdminListener.getRequestHeader(self.useradminActionBody), function(response) {
                self.pageRefresh(response);
            });         
        },

        /**
         * Triggers the search call and returns the data
         * @txtSearch - text to be searched
         */
        search : function(searchCriteria) {
            var self = this;
            var requestBody = self.useradminRequestBody;
            if (searchCriteria != null) {
                self.defaultSettings();
                requestBody.searchColumnValues = [];
                requestBody.searchColumnValues.push(searchCriteria);
                self.useradminRequestBody = requestBody;                
                self.userAdminListener.getUserAdmin(self.userAdminListener.getRequestHeader(self.useradminActionBody), function(response) {
                    self.pageRefresh(response);
                });
            }
        },

        /**
         * DefaultSettings for the AJAX requests
         */
        defaultSettings : function() {
            var self = this;
            self.useradminRequestBody.pageIndex = commonVariables.requestBody.pageIndex;           
			self.useradminRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;    
			self.useradminRequestBody.sortColumn = commonVariables.requestBody.sortColumn;
			self.useradminRequestBody.sortOrder = commonVariables.requestBody.sortOrder;
			self.useradminRequestBody.searchOper = commonVariables.requestBody.searchOper;
			self.useradminRequestBody.searchColumnValues = commonVariables.requestBody.searchColumnValues;
        },
        
        /**
         * Triggers the pagination actions 
         * @txtSearch - text to be searched
         * @pageIndex - index of the page 
         */
        doPagination : function(searchCriteria, pageIndex) {
            var self = this;
            var listener = self.userAdminListener;
            var requestBody = this.useradminRequestBody;
            requestBody.pageIndex = pageIndex;
            self.useradminRequestBody = requestBody;
            self.userAdminListener.getUserAdmin(self.userAdminListener.getRequestHeader(requestBody), function(response) {
                self.pageRefresh(response);
            });
        },

        /**
         * Called during the page refresh, displays the message, and tomat number of records
         * @response: response from the service
         */
        pageRefresh : function(response) {
            var self = this;
            var renderFunction = $.proxy(self.renderTemplate, self);
            if(response.status == "SUCCESS" && response.rows.length > 0) {
                renderFunction(response, self.contentContainer);
                $("#totalCount").text("("+ response.rows.length +" Records Total)");
                //self.pagination.pageRefresh(response.data);
            } else {
				renderFunction(response, self.contentContainer);
                self.noDataAvailable(self.contentContainer);
            }
        },

        /***
         * Called after the preRender() and bindUI() completes. 
         *
         * @element: Element as the result of the template + data binding
         */
        postRender : function(element) {
			var self = this;
			self.headerStyle();
			self.disableActionStyle();
            this.setSession("pageName",this.name);
			$("#sortcolor").addClass('sortclr');
			$(".header").bind("click", function() {
				$("#sortIcon").hide();
				$("#sortcolor").removeClass('sortclr');
			});         
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
            self.userAdminListener.useradminAction(self.userAdminListener.getActionHeader(actionBody, action, id), function(response) {
                if(action != "editData" && action != "deleteSku" && response.status != "ERROR") {
                    self.pageRefresh(response);
                } else {
                    callback(response);
                }
            });
        },
        
        /**
         * Events for each of the actions
         */
        actionEvent : function() {
            var self = this;

            $("#AddIcon").unbind('click');
            $("#AddIcon").click(function(e) {   
				self.escapePopup($("#Add_useradmin"));            
                $("#myModalLabel1").html('Create User');
                $("#user_create").html('CREATE');
                $("#fname").val("").removeClass("error_text").attr("placeholder", "First Name");
                $("#lname").val("").removeClass("error_text").attr("placeholder", "Last Name");
                $("#uid").val("").removeClass("error_text").attr("placeholder", "User ID");
				$("#pop_error").hide();
                self.termsList = [];
            });
            
            $("img[name='editIcon']").unbind('click');
            $("img[name='editIcon']").click(function(e) {
                $("#myModalLabel1").html('Edit User');
                $("#user_create").html('UPDATE');           
                self.updateData = $(this).closest("tr");
                var userId = self.updateData.attr('id');
                self.useradminActionBody = {};
                self.getAction(self.useradminActionBody,"editData", userId, function(callback) {
                    self.editValue = callback.data;
                    $("#fname").val(self.editValue.firstName).removeClass("error_text").removeAttr("placeholder");
                    $("#lname").val(self.editValue.lastName).removeClass("error_text").removeAttr("placeholder");
                    $("#uid").val(self.editValue.loginName).removeClass("error_text").removeAttr("placeholder");
                    $("input[name=role]").each(function() {
                        if(self.editValue.roleId == $(this).val()) {
                            $(this).attr('checked','checked');
                        } 
                    });

                    $("input[name=userStatus]").each(function() {
                        var boo_value = $(this).val() == "true" ? true : false;
                        if(self.editValue.active == boo_value){
                            $(this).attr('checked','checked');
                        } 
                    });
                });
            });
			
			$("#removeSku").unbind('click');
			$("#removeSku").click(function(e) {
				self.userAdminListener.getUserAdmin(self.userAdminListener.getSkuRequestHeader(), function(response) {
					var sku = {};
					sku.removeSku = response.rows;
					Clazz.HandleBarProvider.merge("../components/useradmin/template/removesku.tmp", sku, function(callback){
						$("#removeSkuData").html(callback);
						
						$("img[name=deleteSku]").unbind('click');
						$("img[name=deleteSku]").click(function(e) { 
							var _self = this;
							self.updateData = $(this).closest("tr");
							var deleteId = self.updateData.children().eq(0).text();
							$(".greenfont").attr('href', '#deletemsg');
							$('#deletemsg').show();
							self.navigation = new Clazz.com.components.navigation.js.Navigation();
							self.navigation.alertBox("Remove SKU", "Are you sure you want to remove SKU #"+deleteId+" from the SOLR index?", function(callback) {
								if(callback) {
									 self.useradminActionBody = {};
									self.getAction(self.useradminActionBody,"deleteSku", deleteId);                                
								}
							});
						});
						 $("#removeSkuTable").tablesorter({ 
							// pass the headers argument and assing a object 
							headers: { 
								3: { 
									// disable it by setting the property sorter to false 
									sorter: false 
								},
								3: { 
									// disable it by setting the property sorter to false 
									sorter: false 
								}
							} 
						}); 
					});
					
				});
			});
			
			$("input[name='search']").keypress(function(e){
				var idValue = $(this).attr('id');
				var index = $(this).attr('index');
				if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
					code= (e.keyCode ? e.keyCode : e.which);
					if (code == 13) {
						useradminSearch(idValue, index);
					}
				}
			});
			
			$("#fname").focusout(function() {
				if ($(this).val().replace(/^\s+|\s+$/g,'') == "") {
					$(this).val('');
				}
			});
			
			$("#lname").focusout(function() {
				if ($(this).val().replace(/^\s+|\s+$/g,'') == "") {
					$(this).val('');
				}
			});
			
			$("#uid").focusout(function() {
				if ($(this).val().replace(/^\s+|\s+$/g,'') == "") {
					$(this).val('');
				}
			});
			
			function useradminSearch(idValue, index){
				$("#alert_msg").hide();
				$("#myTable").show();
				$("#no_record").hide();
				var txtSearch = $("#"+idValue).val().toLowerCase();
				if (txtSearch != "") {
					$("#usercontent tr").hide();//To hide all the tr
					var hasRecord = false;
					$("#myTable thead tr th").each(function() {//To iterate the th and check for the selected searchOption
							var i=0;
							$("#usercontent tr").each(function() {//To search for the txtSearch and search option thru all td
								var td = $(this).find('td').eq(index);
								var tdText = td.text().toLowerCase();
								if (tdText.match(txtSearch)) {
									$(this).show();
									hasRecord = true;
									i++;
								}
							});
							$("#totalCount").text("("+i+" Record(s) Total)");
							return false;
						//}
					});
					if (hasRecord == false) {
						$("#no_record").show();
						$("#no_record").text("No Record Available");
						$("#totalCount").text("(0 Record Total)");
					}
				} else {
					$("#usercontent tr").show();
				}
			}
        },      
        
        /***
         * Bind the action listeners. The bindUI() is called automatically after the render is complete 
         */
        bindUI : function() {
            var self = this;
            $("#myTable").tablesorter({ 
                // pass the headers argument and assing a object 
                headers: { 
                    0: { 
                        // disable it by setting the property sorter to false 
                        sorter: false 
                    },
                    8: { 
                        // disable it by setting the property sorter to false 
                        sorter: false 
                    }
                } 
            }); 
            
            $("#user_create").click(function(e) {

                /************* control validation **************/
                var termItem = '';
                var fname = $("#fname").val();
                var lname = $("#lname").val();
                var uid = $("#uid").val();
                var usersatus = $("input[name=userStatus]:checked").val();
                var role = $("input[name=role]:checked").val();
			    var termItem = '';
                var text = $("#user_create").text();
                    
				self.useradminActionBody = {};
				
				if (text === "CREATE") {
					var loginName = uid;
					self.useradminActionBody.firstName = fname;
					self.useradminActionBody.lastName = lname;
					self.useradminActionBody.loginName = loginName;
					self.useradminActionBody.active = usersatus;
					self.useradminActionBody.roleId = role;
					self.getAction(self.useradminActionBody, "create", "", function(response){
						$.each(response.generalPurposeMessage, function(index,value){
							if(value == "firstName:May not be empty") {
								var firstName = value.split(':');
								var msg = firstName[1];
								$("#fname").attr('placeholder', msg);
								$("#fname").addClass("error_text");
								$("#fname").live('input', function() {
									$(this).removeClass("error_text").removeAttr('placeholder');
								});
							}
							if(value == "lastName:May not be empty") {
								var lastName = value.split(':');
								var msg = lastName[1];
								$("#lname").attr('placeholder', msg);
								$("#lname").addClass("error_text");
								$("#lname").live('input', function() {
									$(this).removeClass("error_text").removeAttr('placeholder');
								});
							}
							if(value == "loginName:May not be empty") {
								var loginName = value.split(':');
								var msg = loginName[1];
								$("#uid").attr('placeholder', msg);
								$("#uid").addClass("error_text");
								$("#uid").live('input', function() {
									$(this).removeClass("error_text").removeAttr('placeholder');
								});
							}
						});
					});
					
				} else {
					var loginName = uid;
					var userId = self.updateData.attr('id');                        
					self.useradminActionBody.userId = userId;
					self.useradminActionBody.firstName = fname;
					self.useradminActionBody.lastName = lname;
					self.useradminActionBody.loginName = loginName;
					self.useradminActionBody.active = usersatus;
					self.useradminActionBody.roleId = role;
					self.getAction(self.useradminActionBody, "update", "", function(response){
						$.each(response.generalPurposeMessage, function(index,value){
							if(value == "firstName:May not be empty") {
								var firstName = value.split(':');
								var msg = firstName[1];
								$("#fname").attr('placeholder', msg);
								$("#fname").addClass("error_text");
								$("#fname").live('input', function() {
									$(this).removeClass("error_text").removeAttr('placeholder');
								});
							}
							if(value == "lastName:May not be empty") {
								var lastName = value.split(':');
								var msg = lastName[1];
								$("#lname").attr('placeholder', msg);
								$("#lname").addClass("error_text");
								$("#lname").live('input', function() {
									$(this).removeClass("error_text").removeAttr('placeholder');
								});
							}
							if(value == "loginName:May not be empty") {
								var loginName = value.split(':');
								var msg = loginName[1];
								$("#uid").attr('placeholder', msg);
								$("#uid").addClass("error_text");
								$("#uid").live('input', function() {
									$(this).removeClass("error_text").removeAttr('placeholder');
								});
							}
						});
					});
				}
                
                /* if($.trim(fname) === "") {
                    $("#fname").val('');
                    $("#fname").focus();
                    $("#fname").attr('placeholder','May not be empty');
                    $("#fname").addClass("error_text");
                    $("#fname").live('input', function() {
                        $(this).removeClass("error_text");
                    });
                
                } else if($.trim(lname) === "") {
                    $("#lname").val('');
                    $("#lname").focus();
                    $("#lname").attr('placeholder','May not be empty');
                    $("#lname").addClass("error_text");
                    $("#lname").live('input', function() {
                        $(this).removeClass("error_text");
                    });
                
                } else if($.trim(uid) === "") {
                    $("#uid").val('');
                    $("#uid").focus();
                    $("#uid").attr('placeholder','May not be empty');
                    $("#uid").addClass("error_text");
                    $("#uid").live('input', function() {
                        $(this).removeClass("error_text");
                    });
                
                } else if ($("input[name=userStatus]:checked").length == 0) {
                    $("input[name=userStatus]").focus();
                    $(".userStatus-error").html('UserStatus must be selected');
                
                } else if ($("input[name=role]:checked").length == 0) {
                    $("input[name=role]").focus();
                    $(".role-error").html('Rolse must be selected');
                
                } else {
                    $(".fname-error").html('');
                    $(".lname-error").html('');
                    $(".uid-error").html('');
                    $(".role-error").html('');
                    
                   
                } */
                    /************* control validation end **************/
            });

            self.actionEvent();         
        }
    });

    return Clazz.com.components.useradmin.js.Useradmin;
});