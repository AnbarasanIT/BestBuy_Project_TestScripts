define(["framework/WidgetWithTemplate", "header/listener/HeaderListener"] , function(template) {

	Clazz.createPackage("com.common_components.modules.header");

	Clazz.com.common_components.modules.header.js.Header = Clazz.extend(Clazz.WidgetWithTemplate, {
		headerEvent : null,
		
		// template URL, used to indicate where to get the template
		templateUrl: "../js/common_components/modules/header/template/header.tmp",
		configUrl: "../../js/common_components/modules/header/config/config.json",
		name : "header",
		localConfig: null,
		globalConfig: null,
		maincontent: "basepage\\:widget",
		contentContainer : commonVariables.contentPlaceholder,
		currentObj : null,
		keywordObj : null,
		pagination : null,
		titleStr : null,
		useradmin: null,	
		//Events, to fire a function
		onButtonClick: null,
		
		initialize : function(globalConfig){
			var self = this;
			self.globalConfig = globalConfig;
			self.userAdminListener = new Clazz.com.components.useradmin.js.listener.UseradminListener();
			self.headerListener = new Clazz.com.common_components.modules.header.js.listener.HeaderListener(); 
			self.registerEvents(self.headerListener);
		},
		
		loadPage :function(){
			var self = this;
		},
		
		/***
		 * 
		 */
		postRender : function(element) {
			$('#firstname').html(commonVariables.userInfo.firstName);
			$('#lastname').html(commonVariables.userInfo.lastName);
			$('#username').html(commonVariables.userInfo.loginName);
			/**
			 * @Description : If user has a role of admin, give enable admin permissions
			 * @author Levi Tomes
			 * 
			 * **/
			if (commonVariables.userInfo.role.name == "ROLE_ADMIN"){
				$('.userpermissions').addClass('show'); 
				}
			},
		
		/***
         * Called once to register all the events 
         *
         * @facetsListener: HeaderListener methods getting registered
         */
        registerEvents : function (headerListener) {
            var self = this;
            self.onButtonClick = new signals.Signal();
            self.onButtonClick.add(headerListener.onButtonClick, headerListener);
        },
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 */
		bindUI : function(){
			var self = this;
			 /************************************************************************************************************
			*@author a1003305
			*@Description Call Category Service

			* Administrators can call a services to refresh the category tree from DaaS.
			***********************************************************************************************************/
			//Signals Example
			$("#getcategorytree").click(function(e){
				//onButtonClick method is in HeaderListener.js
                self.onButtonClick.dispatch( function(response) {
                	//Server Side Error
                    if (response.status == "ERROR") {
                    	if (response.generalPurposeMessage != null){
                    	//Render General Purpose Errors
                    	}
                    }
 					//Success
					$("#categorytreeerrormsg").empty();
                    $("#getcategorytree").after("<span style='display:block;' id='categorytreeerrormsg'>"+ response.message +"</span>")
                    $("#getcategorytree").next().delay(30000).slideUp(300).fadeIn(400);
                });			
			});
			
			$("#logout").click(function(e){
				localStorage.clear();
				location.assign("../index.html");
			});
			
			$("#useradmin").click(function() {
				$("#no_record").hide();
				$("#myTable").show();
				$(self.contentContainer).children().remove();
				$("#AddIcon").attr("href", "#Add_" + this.id + "");
				$("#successSpan").text("");
				$("#successSpan").removeClass("sucess_msg");
				$("#successSpan").removeClass("error_msg");
				$("#searchOption").children().remove();
				$("#title_actions").show();
				$("#searchOption").append('<option value="firstName">First Name</option><option value="lastName">Last Name</option><option value="userId">User Id</option><option value="modifiedBy">Last Modified By</option><option value="status">Status</option>');
				self.currentObj = new Clazz.com.components.useradmin.js.Useradmin();
				self.currentObj.render(self.contentContainer);
				$("#title").html(commonVariables.useradminTitle);
				$("#txtSearch").val("");
				$("#skuRemove").show();				
				$(".pagination_div").hide();

				$('#facingTypeDiv').hide();
				$('#sortbyDiv').hide();
				$('.preview_date').hide();
				$('#urlportDiv').hide();
				$('#clearSearchDiv').hide();
				$('#searchOptDiv').show();
				$('#refresh').show();
				$('#addIconDiv').show();
				
				$('#synonyms').removeClass('active');				
				$('#keywords').removeClass('active');				
				$('#banners').removeClass('active');				
				$('#promo').removeClass('active');				
				$('#facets').removeClass('active');				
				$('#facetOrder').removeClass('active');				
				$('#boostblock').removeClass('active');				
				$('#search').removeClass('active');	
				$("#banner_hideall").parent().hide();
				$("#banner_showall").parent().hide();		
				

			});
			
			/**
			 * @Description : As soon as Remove SKU link is clicked, the dialog box opens 
			 * @author asheesh swaroop
			 * 
			 * **/
			$("#removeSku").unbind('click');
			$("#removeSku").click(function(e) {
					
					     // When user enters in sku input box
						 $("#skuRemoveID").die('keydown');

						 $("#skuRemoveID").live('keydown', function(event){
							 // Allow only numeric values
							if ( event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 27 || event.keyCode == 13 || 
								(event.keyCode == 65 && event.ctrlKey === true) || (event.keyCode == 67 && event.ctrlKey === true) || 
								(event.keyCode == 86 && event.ctrlKey === true) || (event.keyCode >= 35 && event.keyCode <= 39) || 
								(event.metaKey == true && event.keyCode == 86)) {
									 return;
							}
							else {
								// Ensure that it is a number and stop the keypress
								if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
									event.preventDefault(); 
								}   
							}

					
						 });
				
				
						// on the click remove button
						$("#submitRemoveSku").unbind('click');
						$("#submitRemoveSku").click(function(e) { 
							
									var _self = this;
									var deleteId = $("#skuRemoveID").val() + "/D";
									var deleteIdTitle = $("#skuRemoveID").val(); 
									$(".greenfont").attr('href', '#deletemsg');
									$('#deletemsg').show();
									self.navigation = new Clazz.com.components.navigation.js.Navigation();
									self.navigation.alertBox("Remove SKU", "Are you sure you want to remove SKU #"+deleteIdTitle+" from the SOLR index?", function(callback) {
										if(callback) {
											 self.useradminActionBody = {};
											
											   self.userAdminListener.useradminAction(self.userAdminListener.getActionHeader(self.useradminActionBody,"deleteSku", deleteId), function(response) {
		             								loadSKU();
		          								});                              
										}
										$("#skuRemoveID").val("");
									});
						});
				loadSKU();
			});
			
			function loadSKU(){
					$("#removeSkuTable tbody").empty();
					self.userAdminListener.getUserAdmin(self.userAdminListener.getSkuRequestHeader(), function(response) {
					var sku = {};
					if(response.rows !=null && response.rows !=""){
						$("#removeSKUTitle").html("Removed SKUs");
					}
					sku.removeSku = response.rows;

					Clazz.HandleBarProvider.merge("../components/useradmin/template/removesku.tmp", sku, function(callback){
						
						
						$("#removeSkuData").html(callback);

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
			}
		}
	});

	return Clazz.com.common_components.modules.header.js.Header;
});