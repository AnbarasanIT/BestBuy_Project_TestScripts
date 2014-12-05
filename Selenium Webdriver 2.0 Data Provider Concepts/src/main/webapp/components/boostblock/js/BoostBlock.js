define(["framework/WidgetWithTemplate", "boostblock/listener/BoostBlockListener"] , function() {

	Clazz.createPackage("com.components.boostblock.js");

	Clazz.com.components.boostblock.js.Boostblock = Clazz.extend(Clazz.WidgetWithTemplate, {
		boostBlockEvent : null,
		boostBlockHeader : null,
		localConfig: null,
		updateData: null,
		termsList: null,
		keywordListener: null,
		navigation : null,
		txtSearch :  null,
		pagination : null,
		paginationContainer :  commonVariables.paginationPlaceholder,
		boostblockActionBody: {},
		searchQuery : {},
		editBoostandBlock : {},
		popupPagination : null,
		sortColumn : null,
		emptyBoostMessage : "There are currently no boosted products. To boost a product, see the product listing below.",
		emptyBlockMessage : "There are currently no blocked products. To block a product, see the product listing below.",
		header: {
			contentType: null,
			requestMethod: null,
			dataType: null,
			requestPostBody: null,
			webserviceurl: null
		},
		boostblockRequestBody: {},
		searchdata: {},
		// template URL, used to indicate where to get the template
		templateUrl: "../components/boostblock/template/boostBlock.tmp",
		configUrl: "../../componens/boostblock/config/config.json",
		name : commonVariables.boostblock,
		headerContainer : commonVariables.headerPlaceholder,
		contentContainer : commonVariables.contentPlaceholder,
		footerContainer : commonVariables.footerPlaceholder,
		basewidget :  commonVariables.basePlaceholder,
		onNextEvent: null,
		onNextValidationEvent: null,
		
		
		initialize : function(globalConfig){
			var self = this;
			self.globalConfig = globalConfig;		
			self.boostBlockListener = new Clazz.com.components.boostblock.js.listener.BoostBlockListener();
			self.popupPagination = new Clazz.com.components.pagination.js.Pagination();
			self.registerEvents(self.boostBlockListener);
			self.registerHandlebars();
			self.defaultSettings();
		},
		
		preRender: function(whereToRender, renderFunction){
			var self = this;
			self.boostblockRequestBody = {};
			self.defaultSettings();
			self.sortColumn = null;
			self.boostBlockListener.getBoostBlock(self.boostBlockListener.getRequestHeader(self.boostblockRequestBody), function(response) {
				self.pageRefresh(response);
			});
		},
		
		registerEvents : function (boostBlockListener) {
			var self = this;
			self.onNextEvent = new signals.Signal();
			self.onNextValidationEvent = new signals.Signal();
			self.onNextValidationEvent.add(boostBlockListener.validateCategory, boostBlockListener);
			self.onNextEvent.add(boostBlockListener.searchProductListing, boostBlockListener);
		},

		registerHandlebars : function () {
			var self = this;

			Handlebars.registerHelper('actionBoostBlock', function(data) {
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
				actionHtml = approve +' <a href="#Add_boostblock" data-toggle="modal">'+ edit+'</a> '+ reject +'<a data-toggle="modal" href="#deletemsg">'+deleted+'</a>';
				return actionHtml;
			});
			
			Handlebars.registerHelper('categoryBlock', function(data) {
				$.each(data, function(index, value){
					data = data.replace("%20", " ");
				});
				return data;
			});			
		},
		
		loadPage :function(){
			var boostBlockListener = new Clazz.com.components.boostblock.js.listener.BoostBlockListener();
			Clazz.navigationController.push(this);
		},
		setSession : function(key,value) {
			if(key !== '') {
				localStorage.setItem(key,value);
			}
		},
		
		search : function(searchCriteria) {
			var self = this;
			var requestBody = self.boostblockRequestBody;
			if (searchCriteria != null) {
				self.defaultSettings();
				requestBody.searchColumnValues = [];
				requestBody.searchColumnValues.push(searchCriteria);
				self.boostblockRequestBody = requestBody;				
				self.boostBlockListener.getBoostBlock(self.boostBlockListener.getRequestHeader(requestBody), function(response) {
					self.pageRefresh(response);
				});
			}
		},
		
		doPagination : function(searchCriteria,pageIndex,rowsPerPage) {
			var self = this;
			var listener = self.boostBlockListener;
			var requestBody = this.boostblockRequestBody;
			requestBody.pageIndex = pageIndex;
			requestBody.rowsPerPage = rowsPerPage;
			self.boostblockRequestBody = requestBody;
			self.boostBlockListener.getBoostBlock(self.boostBlockListener.getRequestHeader(requestBody), function(response) {
				self.pageRefresh(response);
			});
		},
		
		columnSearch : function() {
			var self = this;
			var renderFunction = $.proxy(self.renderTemplate, self);
			self.defaultSettings(); 
			self.boostblockRequestBody={};
			var requestBody = self.boostblockRequestBody;
			var searchCriteriaArray = [];
			self.searchdata.searchTerm = $("#searchTermSearch").val();
			self.searchdata.searchProfileName = $("#searchProfileNameSearch").val();
			self.searchdata.categoryPath = $("#categoryPathSearch").val();
			self.searchdata.modifiedBy = $("#lastModifiedBySearch").val();
			self.searchdata.status = $("#statusSearch").val();
			
			if($("#searchTermSearch").val() != "") {
				var searchCriteria = {};
			    searchCriteria.key = "searchTerm";
				searchCriteria.value = $("#searchTermSearch").val();
				searchCriteriaArray.push(searchCriteria);
			} 
			
			if($("#searchProfileNameSearch").val() != "") {
				var searchCriteria = {};
				searchCriteria.key = "searchProfileName";
				searchCriteria.value = $("#searchProfileNameSearch").val();
				searchCriteriaArray.push(searchCriteria);
			} 
			
			if($("#categoryPathSearch").val() != "") {
				var searchCriteria = {};
				var searchValue = $("#categoryPathSearch").val();
				searchValue = searchValue.replace(" ", "%20");
				searchCriteria.key = "categoryPath";
				searchCriteria.value = searchValue
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
				
				requestBody.searchColumnValues = searchCriteriaArray;
				self.boostblockRequestBody = requestBody;				
				self.boostBlockListener.getBoostBlock(self.boostBlockListener.getRequestHeader(requestBody), function(response) {
					renderFunction(response, self.contentContainer);
				});
			} else {
				var self = this;	
			
				self.sortColumn = null;
				self.boostBlockListener.getBoostBlock(self.boostBlockListener.getRequestHeader(self.boostblockRequestBody), function(response) {
					self.pageRefresh(response);
				});
			}
		},
		
		fillSearchData : function() {
			var self = this;
			$("#searchTermSearch").val(self.searchdata.searchTerm);
			$("#searchProfileNameSearch").val(self.searchdata.searchProfileName);
			$("#categoryPathSearch").val(self.searchdata.categoryPath);
			$("#lastModifiedBySearch").val(self.searchdata.modifiedBy);
			$("#statusSearch").val(self.searchdata.status);
		},
		
		productSearch : function() {
		
			var self = this;
			var searchCriteriaArray = [];
			
				var searchCriteria = {}; 
			    searchCriteria.key = "productName";
				searchCriteria.value = $("#productnameSearch").val();
				searchCriteriaArray.push(searchCriteria);
				self.searchQuery.searchTerm=$('#boostBlockName').text();
			if (searchCriteria != null) {
				self.defaultSettings(); 
				self.searchQuery.searchColumnValues = searchCriteriaArray;
				self.searchQuery.searchTerm = self.searchQuery.searchTerm + " " + $("#productnameSearch").val();
		
				var pageNumber = 1;
				var rowsPerPage =50
				self.onNextEvent.dispatch(self.searchQuery, self, self.renderProductSKUs, pageNumber,rowsPerPage);	
				
		
			}
		},
		
		getAction : function(actionBody, action, id){
			var self = this;
			self.boostBlockListener.boostblockAction(self.boostBlockListener.getActionHeader(actionBody, action, id), function(response) {
				self.pageRefresh(response);
			});
		},
		
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
				if (self.boostblockRequestBody.sortOrder == "desc" ) {
					$('.tabheader').each(function(){
						if($(this).parent().attr('tabname') == self.sortColumn) {
							$("#sortcolor").children().children().eq(0).attr('src','../themes/default/images/bestbuy/small.png');
							$(this).parent().addClass('sortclr');
							$(this).find('img').attr('src', '../themes/default/images/bestbuy/asc.png');
						} 
					});
				} else if (self.boostblockRequestBody.sortOrder == "asc") {
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
		
		defaultSettings : function(){
			var self = this;		
			self.boostblockRequestBody.pageIndex = commonVariables.requestBody.pageIndex;
			self.boostblockRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;    
			self.boostblockRequestBody.sortColumn = commonVariables.requestBody.sortColumn;
			self.boostblockRequestBody.sortOrder = commonVariables.requestBody.sortOrder;
			self.boostblockRequestBody.searchOper = commonVariables.requestBody.searchOper;
			self.boostblockRequestBody.searchColumnValues = commonVariables.requestBody.searchColumnValues;
		},

		actionEvent : function(){
			var self = this;

			$("#AddIcon").unbind('click');
			$("#AddIcon").click(function(e) {
				self.escapePopup($("#Add_boostblock"));
				setTimeout(function() {
					$('#searchTerm').focus();
				}, 1000);
				$("#pop_error").hide();
				$("#categorySpan").html('');
				$("#selCategory").html('');
				$("#myModalLabel1").html('Create Boost and Block');
				$("#create").html('CREATE');
				$("#create").attr('title','Create');
				$("#boostBlockBack").show();
				self.postRender();
				$("#searchTerm").val("").removeAttr("placeholder");
				$("#searchTerm").removeClass("error_text");
				$(".boostblockone").css("display","block");
				$(".boostblocktwo").css("display","none");
				$(".boostblockthree").css("display","none");
				$("#bottombuttons").hide();
				$("#boostblocknextdiv").show();
				$("#clrBtn").css("cursor", "default");
			
				self.searchQuery = {};
				self.searchQuery.category = "";
				self.searchQuery.categoryId = "";
				categoryId = commonVariables.categoryId;
				category = commonVariables.category;
				category = self.categoryReplace(category);
				category = decodeURI(category);

 				self.searchQuery.category = category;
				self.searchQuery.categoryId = categoryId;
				self.searchQuery.categoryPath = commonVariables.categoryPath;
				$("#selCategory").text(commonVariables.categoryPath);		

			});
			
			 $("#searchTerm").focusout(function() {
				if ($(this).val().replace(/^\s+|\s+$/g,'') == "") {
					$(this).val('');
				}
			 });
			/*
			 *@Description: Click on REMOVE ALL BOOSTS removes all the rows from Boosted Table
			 * @Author Asheesh
			 * */
			$("#clrBtn").unbind('click');
			$("#clrBtn").live("click", function(e) {
				var _self = this;
				var boostTableLength = $("#boostedProduct tr").length;
				if(boostTableLength > 0){
					 $("#boostedProduct tr").each(function() {
						
								var skuId = $(this).closest('tr').children().eq(1).text();
								$("#boost_"+skuId).show()
								$("#block_"+skuId).show();
								$("#status_"+skuId).text("").hide();
								for(var y=0; y <  self.searchQuery.boostProduct.length; y++){
									if(self.searchQuery.boostProduct[y].skuId == skuId){
										self.searchQuery.boostProduct.splice(y, 1);
										break;
									}
								}
								$(this).closest('tr').remove();
					});
					$('#boostedProduct').append("<tr class='empty_message'><td>"+self.emptyBoostMessage+"</td></tr>");
					if(boostTableLength == 0){
						$("#clrBtn").css("cursor", "default");
					}
				}
			});
			/*
			 *@Description: Click on REMOVE ALL BLocks removes all the rows from Blocked Table
			 * @Author Asheesh
			 * */
			$("#clrBtnRemoveBlock").unbind('click');
			$("#clrBtnRemoveBlock").live("click", function(e) {
				var _self = this;
				var blockTableLength = $("#blockedProduct tr").length;
				if(blockTableLength > 0){
					 $("#blockedProduct tr").each(function() {
						
								var skuId = $(this).closest('tr').children().eq(1).text();
								$("#boost_"+skuId).show()
								$("#block_"+skuId).show();
								$("#status_"+skuId).text("").hide();
								for(var y=0; y <  self.searchQuery.blockProduct.length; y++){
									if(self.searchQuery.blockProduct[y].skuId == skuId){
										self.searchQuery.blockProduct.splice(y, 1);
										break;
									}
								}
								$(this).closest('tr').remove();
					});
					$('#blockedProduct').append("<tr class='empty_message'><td>"+self.emptyBlockMessage+"</td></tr>");
					if(blockTableLength == 0){
						$("#clrBtnRemoveBlock").css("cursor", "default");
					}
				}
			});

						
			$("img[name='delete']").unbind('click');
			$("img[name='delete']").click(function(e) {
				self.updateData = $(this).closest("tr");
				var deleteBB = self.updateData.children().eq(1).text();
				var _self = this, boostblockId;
				self.navigation = new Clazz.com.components.navigation.js.Navigation();
				var boostblockId = $(_self).closest('tr').attr('id');				
				self.navigation.alertBox("Delete", "Are you sure you want to delete <span class='delete_color'>"+ deleteBB + " </span>?", function(callback){
					if(callback){						
						self.boostblockActionBody = {};					
						self.getAction(self.boostblockActionBody,"delete", boostblockId);
					}	
				});
			});
			
			$("img[name='approval']").unbind('click');
			$("img[name='approval']").click(function(e) {
				self.boostblockActionBody = {};
				self.getAction(self.boostblockActionBody,"approve", $(this).closest('tr').attr("id"));
			});
			
			$("img[name='reject']").unbind('click');
			$("img[name='reject']").click(function(e) {
				var boostblockId = $(this).closest('tr').attr('id');
				self.boostblockActionBody = {};					
				self.getAction(self.boostblockActionBody,"reject", boostblockId);
			});
			
			$("img[name='editIcon']").unbind('click');
			$("img[name='editIcon']").click(function(e) {
				$("#myModalLabel1").text('Edit Boost and Block');
				$("#create").html('UPDATE');
				$("#create").attr('title','Update');
				$("#searchTerm").val("").removeClass("error_text");
				$(".boostblockone").css("display","none");
				$(".boostblocktwo").css("display","block");
				$(".boostblockthree").css("display","none");
				$("#boostblocknextdiv").hide();
				$("#boostBlockBack").hide();
				$("#bottombuttons").show();
				$("#categorySpan").html('');
				//$("#create").attr("disabled","disabled");
				var boostblockId = $(this).closest('tr').attr('id');
				self.searchQuery = {};

				self.searchQuery.boostBlockId = boostblockId;
				self.boostBlockListener.getAllProducts(boostblockId , function(response){
					self.searchQuery.searchTerm = response.data.searchTerm;
					self.searchQuery.categoryId = response.data.categoryId;
					
					var category = response.data.categoryPath;
					
					category = decodeURI(category);
					category = category.replace("&" , "<0x26>");
					category = category.replace("," , "<0x2C>");
					category = category.replace("(" , "<0x28>");
					category = category.replace(")" , "<0x29>");
					category=category.split('|');
					category = category[category.length-1];
					self.searchQuery.category = category; 

					var categoryPath = response.data.categoryPath;
					categoryPath = decodeURI(categoryPath);
					categoryPath = categoryPath.replace("%20", " ");
					var boostedProduct = $("#boostedProduct");
					var blockedProduct = $("#blockedProduct");
					var productList = $("#productsList");
					boostedProduct.empty();
					blockedProduct.empty();
					productList.empty();
				
					$("#boostBlockName").text(response.data.searchTerm);
					$("#createdSpan").show();
					$("#mdfdSpan").show();	
					$("#itemBlockedBoosted").show();	
			
				
					$("#catPath").text(categoryPath);
					$("#cretdOn").text(response.data.createdDate);
					$("#crtdBy").text(response.data.createdBy);
					$("#mdfdOn").text(response.data.modifiedDate);
					$("#mdfdBy").text(response.data.modifiedBy);
					self.searchQuery.boostProduct = response.data.boostProduct;
					self.searchQuery.blockProduct = response.data.blockProduct;
					$("#itemBloosted").text("#"+response.data.boostProduct.length);
					$("#itemBlocked").text("#"+response.data.blockProduct.length);
					var pageNumber = 1;
					var rowsPerPage =50;
					self.onNextEvent.dispatch(self.searchQuery, self, self.renderProductSKUs, pageNumber,rowsPerPage);			
				});
			});
			
			$("a[name='details']").unbind('click');
			$("a[name='details']").live("click" , function(e) {
				$("#myModalLabel1").html('Read Only Boost and Block');
				$("#create").html('Close');
				$(".boostblockone").css("display","none");
				$(".boostblocktwo").css("display","none");
				$(".boostblockthree").css("display","block");
				$("#boostblocknextdiv").hide();
				$("#bottombuttons").hide();
				var boostblockId = $(this).closest('tr').attr('id');
				var viewQuery = {};
				
				viewQuery.boostBlockId = boostblockId;
				self.boostBlockListener.getAllProducts(boostblockId , function(response){
					viewQuery.searchTerm = response.data.searchTerm;
					viewQuery.categoryId = response.data.categoryId;
					
					var category = response.data.categoryPath;
					
					category = decodeURI(category);
					category = category.replace("&" , "<0x26>");
					category = category.replace("," , "<0x2C>");
					category = category.replace("(" , "<0x28>");
					category = category.replace(")" , "<0x29>");
					category=category.split('|');
					category = category[category.length-1];
					viewQuery.category = category; 

					var categoryPath = response.data.categoryPath;
					categoryPath = decodeURI(categoryPath);
					categoryPath = categoryPath.replace("%20", " ");
					var boostedProduct = $("#boostedProduct");
					var blockedProduct = $("#blockedProduct");
					var productList = $("#productsList");
					boostedProduct.empty();
					blockedProduct.empty();
					productList.empty();
				
					$("#boostBlockName1").text(response.data.searchTerm);
					$("#createdSpan1").show();
					$("#mdfdSpan1").show();	
	
					$("#catPath1").text(categoryPath);
					$("#cretdOn1").text(response.data.createdDate);
					$("#crtdBy1").text(response.data.createdBy);
					$("#mdfdOn1").text(response.data.modifiedDate);
					$("#mdfdBy1").text(response.data.modifiedBy);
					viewQuery.boostProduct = response.data.boostProduct;
					viewQuery.blockProduct = response.data.blockProduct;
					$("#itemBlockedBoostedRead").show();
					$("#itemBloostedRead").text("#"+response.data.boostProduct.length);
					$("#itemBlockedRead").text("#"+response.data.blockProduct.length);
					var pageNumber = 1;
					var rowsPerPage = 50;
					self.onNextEvent.dispatch(viewQuery, self, self.viewProducts, pageNumber,rowsPerPage);			
				}); 
			});
			
			
		},
		
		/***
		 * 
		 */
		postRender : function(element) {
			var self = this;
			$('.tabheader').each(function(){
				$(this).parents('th#sortcolor').children().eq(0).children().eq(0).attr('src','../themes/default/images/bestbuy/asc.png');
			});
			if(self.sortColumn == null) {
				$("#sortcolor").addClass('sortclr');
			} else {
				$("#sortcolor").removeClass('sortclr');
			}
			self.headerStyle();
			self.fillSearchData();
			self.disableActionStyle();
			self.setSession("pageName", self.name);
			var treeView = $('<div class="bbtreeview"><ul id="filetree" class="filetree"><li><span><strong>Click here to edit category</strong></span><span id="categorySpan"></span>' + commonVariables.categoryTreeContent + '</li></ul></div>');
			
			setTimeout(function() {
				$(treeView).jstree({
				}).bind("init.jstree", function(event, data){ 
				}).bind("loaded.jstree", function (event, data) {
					$("#treeviewContent").children().remove();
					$("#treeviewContent").append(treeView);
					self.treeViewClickEvent();
				});
			}, 200);
		},
		
		treeViewClickEvent : function(){
			var self = this, categoryId, path, category, infoData = {};
			$("span.folder").click(function(){
				self.searchQuery = {};
				self.searchQuery.category = "";
				self.searchQuery.categoryId = "";
				$("span.folder a").removeClass("selected");
				$(this).find("a").addClass("selected");
				$("#categorySpan").removeClass("error_text");
				$("#categorySpan").text('');
				path = $(this).parent().attr('path');
				path = decodeURI(path);
				categoryId = $(this).parent().attr('id');
				category = $(this).children().eq(0).text();
				category = self.categoryReplace(category);
				category = decodeURI(category);

 				self.searchQuery.category = category;
				self.searchQuery.categoryId = categoryId;
				self.searchQuery.categoryPath = path;
				$("#selCategory").text(path);
				
				setTimeout(function() {
					$(".bbtreeview").jstree('close_all');
				}, 500);
			});	
		},
		
		categoryReplace : function(category){
			category = category.replace("&" , "<0x26>");
			category = category.replace("," , "<0x2C>");
			category = category.replace("(" , "<0x28>");
			category = category.replace(")" , "<0x29>");
			return category;
		},
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 */
		bindUI : function(){
			var self = this;
			
			$('.tabheader').die("click");
			$('.tabheader').live("click", function() {
				var sortOrder = "";
				self.sortColumn = $(this).parent().attr('tabname');
				if (self.boostblockRequestBody.sortOrder == "desc") {
					sortOrder = "asc";
				} else if (self.boostblockRequestBody.sortOrder == "asc") {
					sortOrder = "desc";
				}
				self.boostblockRequestBody.pageIndex = commonVariables.requestBody.pageIndex;
				self.boostblockRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;    
				self.boostblockRequestBody.sortColumn = self.sortColumn;
				self.boostblockRequestBody.sortOrder = sortOrder;
				self.boostblockRequestBody.searchOper = commonVariables.requestBody.searchOper;
				self.boostblockRequestBody.searchColumnValues = commonVariables.requestBody.searchColumnValues;
				self.boostBlockListener.getBoostBlock(self.boostBlockListener.getRequestHeader(self.boostblockRequestBody), function(response) {
					self.pageRefresh(response);
				});
			});
			
			$('input[name="search"]').keypress(function(e) {
				if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
					code= (e.keyCode ? e.keyCode : e.which);
					if (code == 13) {
						self.columnSearch();
					}
				}
			});
			
			$("#boostblocknext").click(function () {
				/* var searchTerm =  $("#searchTerm").val();
				var characterReg = /^(?!\s*$)[-a-zA-Z0-9_:,.' ']{1,100}$/;
				if ($.trim(searchTerm) == ""){
					$("#searchTerm").focus();
					$("#searchTerm").val('');
					$("#searchTerm").attr('placeholder','cannot be empty');
					$("#searchTerm").addClass("error_text");
					$("#searchTerm").live('input', function() {
						$(this).removeClass("error_text").removeAttr('placeholder');
					});
				} else if(!characterReg.test(searchTerm)) {
					$("#searchTerm").focus();
					$("#searchTerm").attr('placeholder','cannot be empty');
					$("#searchTerm").addClass("error_text");
					$("#searchTerm").live('input', function() {
						$(this).removeClass("error_text").removeAttr('placeholder');
					});
				} else if(self.searchQuery.category == undefined){
					$("#categorySpan").text('Please select category');
					$("#categorySpan").addClass("error_text");
					$("#filetree").live('click', function() {
						$("#categorySpan").removeClass("error_text");
						$("#categorySpan").text('');
					});			
				} else { */
					
				self.searchQuery.searchTerm = $("#searchTerm").val();
				self.searchQuery.boostBlockId = "";
				$("#boostBlockName").text( $("#searchTerm").val());
				$("#catPath").text(self.searchQuery.categoryPath);
				$("#createdSpan").hide();
				$("#cretdOn").text('');
				$("#crtdBy").text('');
				$("#mdfdSpan").hide();
				$("#mdfdOn").text('');
				$("#mdfdBy").text('');	 
				$("#itemBlockedBoosted").hide();
				
				
				self.onNextValidationEvent.dispatch(self.searchQuery, self, function(response) {
					if(response.status == "SUCCESS"){
						self.defaultSettings();
						$(".boostblockone").css("display","none");
						$(".boostblocktwo").css("display","block");
						$(".boostblockthree").css("display","none");
						$("#boostblocknextdiv").hide();
						$("#bottombuttons").show();
						var pageNumber = 1;
						var rowsPerPage=50;
						self.onNextEvent.dispatch(self.searchQuery, self, self.renderProductSKUs, pageNumber,rowsPerPage);
					} else {
					
						$.each(response.generalPurposeMessage, function(index,value){
							if(value == "SearchTerm:cannot be empty"){
								var SearchTerm = value.split(':');
								var msg = SearchTerm[1];
								$("#searchTerm").attr('placeholder', msg);
								$("#searchTerm").addClass("error_text");
								$("#searchTerm").live('input', function() {
									$(this).removeClass("error_text").removeAttr('placeholder');
								});
							}
							if(value == "SearchTerm:This Search Term, Profile and Category combination is already in use.") {
								var SearchTerm = value.split(':');
								var msg = SearchTerm[1];
								$("#categorySpan").text(msg);
								$("#categorySpan").addClass("error_text");
								$("#filetree").live('click', function() {
									$("#categorySpan").removeClass("error_text");
									$("#categorySpan").text('');
								});
							}
						});
						/* $("#searchTerm").focus();
						var currentValue = $("#searchTerm").val();
						$("#searchTerm").val('');
						$("#searchTerm").attr('placeholder',currentValue);
						$("#searchTerm").addClass("error_text");
						$("#categorySpan").text(response.message);
						$("#categorySpan").addClass("error_text");
						$("#filetree").live('click', function() {
							$("#categorySpan").removeClass("error_text");
							$("#categorySpan").text('');
						});	

						$("#searchTerm").live('input', function() {
							$(this).removeClass("error_text");
							$("#categorySpan").removeClass("error_text");
							$("#categorySpan").text('');
						}); */
						
						
					}
				});
				/* } */
				setTimeout(function() {
					$(".modal-body").scrollTop(0);
				}, 200);
			});

			$('.checkall').click(function () {
				$(this).parent().parent().parent().next().find('input').attr('checked', this.checked);
			});
			
			$("#boostBlockBack").click(function () {
				$(".boostblockone").css("display","block");
				$(".boostblocktwo").css("display","none");
				$(".boostblockthree").css("display","none");
				$("#boostblocknextdiv").show();
				/* self.searchQuery = {};
				self.searchQuery.category = "";
				self.searchQuery.categoryId = ""; */
				$("#bottombuttons").hide();
			});
			
			$("#create").click(function(e) {
				var text = $("#create").text();
				delete self.searchQuery['category'];
				self.searchQuery.searchProfileId = 1;
				//SPT 3560 BT: Boost and Block: Should not be allowed to create/edit a boost and block without at least one boost or block 
				if((self.searchQuery.boostProduct != undefined && self.searchQuery.boostProduct.length > 0 ) || ( self.searchQuery.blockProduct != undefined &&  self.searchQuery.blockProduct.length >0 )){
					  
						if( text === "CREATE") {
							self.getAction(self.searchQuery,"create", "");
							self.searchQuery = {};
						} else {
							//if(!$("#create").attr('disabled')) {
								self.getAction(self.searchQuery,"update", "");
								self.searchQuery = {};
							//}	
						}	
				}else{
					self.navigation = new Clazz.com.components.navigation.js.Navigation();
					// Alert Box is displayed
					self.navigation.alertBox("Confirmation", "You did not make any changes for this search term. This will not be saved. Do you want to add any Boosts or Blocks to this term? ", function(callback){
						if(callback){
							
							// If Clicked Yes
							//$(".boostblockone").focus();
							
						}else{
							
								//If clicked No, then we close all the Grid 
								$(".boostblockone").css("display","block");
								$(".boostblocktwo").css("display","none");
								$(".boostblockthree").css("display","none");
								$("#boostblocknextdiv").show();
								/* self.searchQuery = {};
								self.searchQuery.category = "";
								self.searchQuery.categoryId = ""; */
								$("#bottombuttons").hide();
						}
					});
				}// End of 3560
			});
			
			self.actionEvent();
		},

		renderProductSKUs :function (response , searchQuery, self, pageNumber, rowsPerPage) {
			var _self = this;
			if(response.documents ==null){
		 		return false;
			}
			self.boostblockRequestBody.rowsPerPage = rowsPerPage ;
			var productSKUs = response.documents;
			var htmlTable = "";
			var boostArray = [];
			var boostProduct = {};
			var blockArray = [];
			var blockProduct = {};
			
			var boostedProduct = $("#boostedProduct");
			var blockedProduct = $("#blockedProduct");
			var productList = $("#productsList");
			boostedProduct.empty();
			blockedProduct.empty();
			boostedProduct.append("<tr class='empty_message'><td>"+self.emptyBoostMessage+"</td></tr>");
			blockedProduct.append("<tr class='empty_message'><td>"+self.emptyBoostMessage+"</td></tr>");
			/* Product Listing */
			productList.empty();
			self.defaultSettings();
			/*Creating the header with search box  */
			var headertr = $("<tr><td>&nbsp;</td><td><input type=\"text\" class=\"search-txtbox\" id=\"productnameSearch\" name=\"productnameSearch\"></td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>");
			productList.append(headertr);
		
			for (var i = 0; i < productSKUs.length; i++) {
				var tr = $("<tr></tr>");
					//Calculate actual row count
					tr.append("<td>" + (i+1+ (50*(pageNumber-1))) + "</td>");
					tr.append("<td>" + productSKUs[i].productname + "</td>");
					tr.append("<td>" + productSKUs[i].skuid + "</td>");
					tr.append("<td class=\"center_td\" id=boost_"+productSKUs[i].skuid+"><a name=\'boostProduct\' class=\"btn bbbluebtn\">BOOST</a></td>");
					tr.append("<td class=\"center_td\" id=block_"+productSKUs[i].skuid+"><a name=\"blockProduct\" class=\"btn bbbluebtn\">BLOCK</a></td>");
					tr.append("<td colspan=\"2\" class=\"center_td\" style=\"display:none; text-align:left;\" id=status_"+productSKUs[i].skuid+"></td>");
				productList.append(tr);
			}
			
				var totalCountOfPages = (response.num_found)/50; //50 needs to be rowsPerPage in the future;
				//Round Number of pages up.
				totalCountOfPages = Math.ceil(totalCountOfPages);
				
				self.boostblockRequestBody.totalCountOfRecords = response.num_found;
				self.boostblockRequestBody.totalCountOfPages = totalCountOfPages;
				self.boostblockRequestBody.pageIndex = pageNumber;
				self.boostblockRequestBody.rowsPerPage = rowsPerPage;    
				self.boostblockRequestBody.rows = response.documents;    
				self.popupPagination.data = self.boostblockRequestBody; 
				self.popupPagination.currentObj = self.popupPagination;

				Clazz.com.components.pagination.js.Pagination.prototype.doPagination  = function(searchCriteria, pageIndex,rowsPerPage) {
					self.boostblockRequestBody.pageIndex = pageIndex;
					self.boostblockRequestBody.rowsPerPage = rowsPerPage;
					self.onNextEvent.dispatch(searchQuery, self, self.renderProductSKUs, pageIndex,rowsPerPage);
				};

				self.popupPagination.render("#tablefooter");

				$('#tablefooter > div').waitUntilExists(function(){
					$('#tablefooter').children().css({'position':'relative', 'bottom':'0' });
						//Hide Dropdown
						$("#rowsPerPage").addClass("hide");
				});



			/* Boost Product Listing  - Update */
			
			if(searchQuery.boostProduct != undefined){
				boostedProduct.empty();
				for(var k = 0; k < searchQuery.boostProduct.length; k++){
					var bstTr = $("<tr></tr>");
						bstTr.append("<td>"+searchQuery.boostProduct[k].productName+"</td>");
						bstTr.append("<td class=\"center_td\">"+searchQuery.boostProduct[k].skuId+"</td>");
						bstTr.append("<td class=\"index center_td\" name=\"boostOrder\">"+(k+1)+"</td>");
						bstTr.append("<td name=\"removeBoost\" class=\"center_td\"><a class=\"btn bbbluebtn\">REMOVE BOOST</a></td>");
					boostedProduct.append(bstTr);
					$("#boost_"+searchQuery.boostProduct[k].skuId).hide();
					$("#block_"+searchQuery.boostProduct[k].skuId).hide();
					$("#status_"+searchQuery.boostProduct[k].skuId).text("Boosted").show();
				}
				if(searchQuery.boostProduct.length > 0){
					$("#clrBtn").css("cursor", "pointer");
				} else {
					$("#clrBtn").css("cursor", "default");
				}
				// Duplicate for remove item - need to be removed
				$("td[name='removeBoost']").click(function() {
					$("#create").removeAttr('disabled');
				
					var skuId = $(this).closest('tr').children().eq(1).text();
					$("#boost_"+skuId).show();
					$("#block_"+skuId).show();
					$("#status_"+skuId).text("").hide();
					for(var j=0; j<searchQuery.boostProduct.length; j++){
						if(searchQuery.boostProduct[j].skuId == skuId){
							searchQuery.boostProduct.splice(j, 1);
							break;
						}
					}
					$(this).closest('tr').remove();
					var boostTabLength = $("#boostedProduct tr").length;
					for(var m = 0; m < boostTabLength; m++){
						$("#boostedProduct tr").eq(m).children().eq(2).text((m + 1));
					}
					
					if(boostTabLength == 0){
						$("#clrBtn").css("cursor", "default");
						boostedProduct.append("<tr class='empty_message'><td>"+self.emptyBoostMessage+"</td></tr>");
					}
				});
				
				var fixHelperModified = function(e, tr) {
					var $originals = tr.children();
					var $helper = tr.clone();
					$helper.children().each(function(index) {
						$(this).width($originals.eq(index).width())
					});
					return $helper;
				},
				
				updateIndex = function(e, ui) {
					$('td.index', ui.item.parent()).each(function (i) {
						$(this).html(i + 1);
					});
					$('td.index', ui.item.parent()).each(function (i) {
						$(this).html(i + 1);
						
						self.searchQuery.boostProduct[i].position = i +1;
						self.searchQuery.boostProduct[i].productName = $(this).parent().find('td').html();
						self.searchQuery.boostProduct[i].skuId = $(this).parent().find('.center_td').html();

					});
				};

				$("#boostTable tbody").sortable({
					helper: fixHelperModified,
					stop: updateIndex
				}).disableSelection();				
			}
				
			
			/* Blocked Product Listing  - Update */		
			if(searchQuery.blockProduct != undefined){
				blockedProduct.empty();

				for(var l = 0; l < searchQuery.blockProduct.length; l++){
					var blkdTr = $("<tr></tr>");
						blkdTr.append("<td>"+searchQuery.blockProduct[l].productName+"</td>");
						blkdTr.append("<td class=\"center_td\">"+searchQuery.blockProduct[l].skuId+"</td>");
						blkdTr.append("<td name=\"removeBlock\" class=\"center_td\"><a class=\"btn bbbluebtn\">REMOVE BLOCK</a></td>");
					blockedProduct.append(blkdTr);
					$("#boost_"+searchQuery.blockProduct[l].skuId).hide();
					$("#block_"+searchQuery.blockProduct[l].skuId).hide();
					$("#status_"+searchQuery.blockProduct[l].skuId).text("Blocked").show();					
				}
				
				if(searchQuery.blockProduct.length > 0){
					$("#clrBtnRemoveBlock").css("cursor", "pointer");
				} else {
					$("#clrBtnRemoveBlock").css("cursor", "default");
				}
				// Druplicate code - need to remove
				$("td[name='removeBlock']").click(function() {
					$("#create").removeAttr('disabled');
					var skuId = $(this).closest('tr').children().eq(1).text();
					$("#boost_"+skuId).show();
					$("#block_"+skuId).show();
					$("#status_"+skuId).text("").hide();
					for(var v=0; v<searchQuery.blockProduct.length; v++){
						if(searchQuery.blockProduct[v].skuId == skuId){
							searchQuery.blockProduct.splice(v, 1);
							break;
						}
					}
					$(this).closest('tr').remove();
					
					if(blockedProduct.children().text() == ""){
						blockedProduct.append("<tr class='empty_message'><td>"+self.emptyBlockMessage+"</td></tr>");
					}
				});		
			}
			
			$("a[name='boostProduct']").click(function() {
				if($("#boostTable tr").length < 21)
				{	
					if(boostedProduct.children().hasClass('empty_message')){
						boostedProduct.empty();
					}
					$("#create").removeAttr('disabled');
					var bproducts = {};
					var skuId = $(this).closest('tr').children().eq(2).text();
					var productName = $(this).closest('tr').children().eq(1).text();
					position = $("#boostTable tr").length;
	
					var boostTr = $("<tr></tr>");
						boostTr.append("<td>"+productName+"</td>");
						boostTr.append("<td class=\"center_td\">"+skuId+"</td>");
						boostTr.append("<td class=\"index center_td\" name=\"boostOrder\">"+position+"</td>");
						boostTr.append("<td name=\"removeBoost\" class=\"center_td\"><a class=\"btn bbbluebtn\">REMOVE BOOST</a></td>");
					boostedProduct.append(boostTr);
					$("#boost_"+skuId).hide();
					$("#block_"+skuId).hide();
					$("#status_"+skuId).text("Boosted").show();
					$("#clrBtn").css("cursor", "pointer");
					
					bproducts.productName = productName;
					bproducts.skuId = skuId;
					bproducts.position = position;
					if(searchQuery.boostProduct == undefined){
						boostArray.push(bproducts);
						searchQuery.boostProduct = boostArray;
					} else {
						searchQuery.boostProduct.push(bproducts);
					}	
	
					$("td[name='removeBoost']").click(function() {
						var skuId = $(this).closest('tr').children().eq(1).text();
						$("#boost_"+skuId).show()
						$("#block_"+skuId).show();
						$("#status_"+skuId).text("").hide();
						for(var y=0; y<searchQuery.boostProduct.length; y++){
							if(searchQuery.boostProduct[y].skuId == skuId){
								searchQuery.boostProduct.splice(y, 1);
								break;
							}
						}
						$(this).closest('tr').remove();
						//For arranging boost order
						var boostTabLength = $("#boostedProduct tr").length;
						for(var m = 0; m < boostTabLength; m++){
							$("#boostedProduct tr").eq(m).children().eq(2).text((m + 1));
						}
						
						if(boostTabLength == 0){
							$("#clrBtn").css("cursor", "default");
						}
						if(boostedProduct.children().text() == ""){
							boostedProduct.append("<tr class='empty_message'><td>"+self.emptyBoostMessage+"</td></tr>");
						}
					});
					
					var fixHelperModified = function(e, tr) {
						var $originals = tr.children();
						var $helper = tr.clone();
						$helper.children().each(function(index) {
							$(this).width($originals.eq(index).width())
						});
						return $helper;
					},
					
					updateIndex = function(e, ui) {
						$('td.index', ui.item.parent()).each(function (i) {
							$(this).html(i + 1);
						});
						
						$('td.index', ui.item.parent()).each(function (i) {
							$(this).html(i + 1);
							
							self.searchQuery.boostProduct[i].position = i +1;
							self.searchQuery.boostProduct[i].productName = $(this).parent().find('td').html();
							self.searchQuery.boostProduct[i].skuId = $(this).parent().find('.center_td').html();

						});
					};
	
					$("#boostTable tbody").sortable({
						helper: fixHelperModified,
						stop: updateIndex
					}).disableSelection();				
				}
			});	

			$("a[name='blockProduct']").unbind('click');
			$("a[name='blockProduct']").click(function() {
				if(blockedProduct.children().hasClass('empty_message')){
					blockedProduct.empty();
				}
				if($("#blockTable tr").length < 11)
				{
					$("#create").removeAttr('disabled');
					var blkroducts = {};
					var skuId = $(this).closest('tr').children().eq(2).text();
					var productName = $(this).closest('tr').children().eq(1).text();
					blockSno = $("#blockTable tr").length;

					var boostTr = $("<tr></tr>");
						boostTr.append("<td>"+productName+"</td>");
						boostTr.append("<td class=\"center_td\">"+skuId+"</td>");
						boostTr.append("<td name=\"removeBlock\" class=\"center_td\"><a class=\"btn bbbluebtn\">REMOVE BLOCK</a></td>");
					blockedProduct.append(boostTr);
					$("#boost_"+skuId).hide();
					$("#block_"+skuId).hide();
					$("#status_"+skuId).text("Blocked").show();
					$("#clrBtnRemoveBlock").css("cursor", "pointer");
					
					blkroducts.productName = productName;
					blkroducts.skuId = skuId;
					blkroducts.position = -1;
					if(searchQuery.blockProduct == undefined){
						blockArray.push(blkroducts);
						searchQuery.blockProduct = blockArray;
					} else {
						searchQuery.blockProduct.push(blkroducts);
					}
					
					if(searchQuery.blockProduct.length > 0){
						$("#clrBtnRemoveBlock").css("cursor", "pointer");
					} else {
						$("#clrBtnRemoveBlock").css("cursor", "default");
					}
					
					$("td[name='removeBlock']").click(function() {
						var skuId = $(this).closest('tr').children().eq(1).text();
						$("#boost_"+skuId).show();
						$("#block_"+skuId).show();
						$("#status_"+skuId).text("").hide();
						for(var z=0; z<searchQuery.blockProduct.length; z++){
							if(searchQuery.blockProduct[z].skuId == skuId){
								searchQuery.blockProduct.splice(z, 1);
								break;
							}
						}
						$(this).closest('tr').remove();
						
						var blockTabLength = $("#blockedProduct tr").length;
						if(blockTabLength == 0){
							$("#clrBtnRemoveBlock").css("cursor", "default");
						}
						if(blockedProduct.children().text() == ""){
							blockedProduct.append("<tr class='empty_message'><td>"+self.emptyBlockMessage+"</td></tr>");
						}
					});
				}
			});
			$('input[name="productnameSearch"]').keypress(function(e) {
					if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
						code= (e.keyCode ? e.keyCode : e.which);
						if (code == 13) {
							self.productSearch();
						}
					}
				});
		},
		
	viewProducts :function (response , searchQuery) {
			var self = this;
			var productSKUs = response.documents;
			var htmlTable = "";
			var boostArray = [];
			var boostProduct = {};
			var blockArray = [];
			var blockProduct = {};

			var boostedProduct = $("#boostedProduct1");
			var blockedProduct = $("#blockedProduct1");
			boostedProduct.empty();
			blockedProduct.empty();
			
			/* Boost Product Listing  - Update */
			if(searchQuery.boostProduct != undefined){
				boostedProduct.empty();
				for(var k = 0; k < searchQuery.boostProduct.length; k++){
					var bstTr = $("<tr></tr>");
						bstTr.append("<td>"+searchQuery.boostProduct[k].productName+"</td>");
						bstTr.append("<td class=\"center_td\">"+searchQuery.boostProduct[k].skuId+"</td>");
						bstTr.append("<td class=\"index center_td\" name=\"boostOrder\">"+(k+1)+"</td>");
						bstTr.append("<td name=\"removeBoost\" class=\"center_td\">BOOSTED</td>");
					boostedProduct.append(bstTr);
				}
			}
			
			/* Blocked Product Listing  - Update */		
			if(searchQuery.blockProduct != undefined){
				blockedProduct.empty();
				for(var l = 0; l < searchQuery.blockProduct.length; l++){
					var blkdTr = $("<tr></tr>");
						blkdTr.append("<td>"+searchQuery.blockProduct[l].productName+"</td>");
						blkdTr.append("<td class=\"center_td\">"+searchQuery.blockProduct[l].skuId+"</td>");
						blkdTr.append("<td name=\"removeBlock\" class=\"center_td\">BLOCKED</td>");
					blockedProduct.append(blkdTr);
				}
			}
		}
		

	});

	return Clazz.com.components.boostblock.js.BoostBlock;
});