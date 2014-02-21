define(["framework/WidgetWithTemplate", "banners/listener/BannersListener", "navigation/Navigation", "lib/handlebars-1.0.0.beta.6"] , function() {

    Clazz.createPackage("com.components.banners.js");

    Clazz.com.components.banners.js.Banners = Clazz.extend(Clazz.WidgetWithTemplate, {
    
        bannerEvent : null,
        bannerHeader : null,
        updateData : null,
        terms : null,
        localConfig : null,
        globalConfig : null,
        tagText : null,
        termItem : null,
        bannerListener : null,
        renderFunction: null,
        pagination: null,
        bannerActionBody: {},
        sortColumn : null,
        header: {
            contentType: null,
            requestMethod: null,
            dataType: null,
            requestPostBody: null,
            webserviceurl: null
        },  
        
        bannerRequestBody: {},
        searchdata: {},
        templateUrl: "../components/banners/template/banners.tmp",
        configUrl: "../../componens/banners/config/config.json",
        name : commonVariables.banners,
        headerContainer : commonVariables.headerPlaceholder,
        contentContainer : commonVariables.contentPlaceholder,
        footerContainer : commonVariables.footerPlaceholder,
        basewidget :  commonVariables.basePlaceholder,
		mode : "Add",
		currentValue: null,
		hasError: false,
		
		onFocusOutEvent : null,
        
        /***
         * Called in initialization time of this class 
         *
         * @globalConfig: global configurations for this class
         */
        initialize : function(globalConfig) {
            var self = this;
            self.globalConfig = globalConfig;
            self.bannerListener = new Clazz.com.components.banners.js.listener.BannersListener();
            self.registerEvents(self.bannerListener);
			self.defaultSettings();
			
            Handlebars.registerHelper('actionBanners', function(data) {
                var actionHtml, edit = '<img name="EditDisable" title="Edit" class="block_sym" src="../themes/default/images/bestbuy/edit_disabled.png"/>', approve = '<img name="approvalDisable" title="Approve" class="block_sym" src="../themes/default/images/bestbuy/approve_disabled.png" />', reject = '<img name="rejectDisable" title="Reject" class="block_sym" src="../themes/default/images/bestbuy/reject_disabled.png" />', deleted = '<img name="deleteDisable" title="Delete" class="block_sym" src="../themes/default/images/bestbuy/delete_disabled.png" />';            
                $.each(data, function(index, value) {
                    if(value.value == "Edit") {
                        edit = '<img name="editIcon" title="Edit" class="hand_sym" src="../themes/default/images/bestbuy/edit_icon.png"/>';
                    } else if(value.value == "Approve") {
                        approve = '<img name="approval" title="Approve" class="hand_sym" src="../themes/default/images/bestbuy/approve_blue.png" />';
                    } else if(value.value == "Reject") {
                        reject = '<img name="reject" title="Reject" class="hand_sym" src="../themes/default/images/bestbuy/reject_blue.png" />';
                    } else if(value.value == "Delete") {
                        deleted = '<img name="delete" title="Delete" class="hand_sym" src="../themes/default/images/bestbuy/delete_blue.png" />';   
                    }
                });
                actionHtml = approve +' <a href="#Add_banners" data-toggle="modal">'+ edit+'</a> '+ reject +'<a data-toggle="modal" href="#deletemsg">'+deleted+'</a>';
                return actionHtml;
            });
			
			Handlebars.registerHelper('categoryTree', function(data) {
				
				if(data != null){
					$.each(data, function(index, value){
						data = data.replace("%20", " ");
					});
				}
				return data;
			});
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
			self.sortColumn = null;
            self.bannerListener.getBannerList(self.bannerListener.getRequestHeader(self.bannerRequestBody), function(response) {
                self.pageRefresh(response);
            });
        },
		
		/***
         * Called after the preRender() and bindUI() completes. 
         *
         * @element: Element as the result of the template + data binding
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
			self.fillSearchData();
            this.setSession("pageName", this.name);     
			window.onresize = function(event) {
			self.sizeModal();
			};
    
        },
		
		/***
		 * Called once to register all the events 
		 *
		 * @bannerListener: bannerListener methods getting registered
		 */
		registerEvents : function (bannerListener) {
			var self = this;
			self.onFocusOutEvent = new signals.Signal();
			self.onFocusOutEvent.add(bannerListener.checkNameExists, bannerListener);
		},
        
        /**
         * Called in once when this page is initialized
         */
        loadPage :function() {
            var bannersListener = new Clazz.com.components.banners.js.listener.BannersListener();
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
				if (self.bannerRequestBody.sortOrder == "desc" ) {
					$('.tabheader').each(function(){
						if($(this).parent().attr('tabname') == self.sortColumn) {
							$("#sortcolor").children().children().eq(0).attr('src','../themes/default/images/bestbuy/small.png');
							$(this).parent().addClass('sortclr');
							$(this).find('img').attr('src', '../themes/default/images/bestbuy/asc.png');
						} 
					});
				} else if (self.bannerRequestBody.sortOrder == "asc") {
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
		 
        getAction : function(actionBody, action, id, callback){
			var self = this;
			self.bannerListener.bannerAction(self.bannerListener.getActionHeader(actionBody, action, id), function(response) {
				if(action != "editData" && action != "exists" && action != "categoryFacet" && action != "attributeValue" && response.status != "ERROR") {
					self.pageRefresh(response);
				} else {
					callback(response);
				}
			});
		}, 

        /**
         * Add terms to the keywords
         */
        setTermsFunction : function() {
        
            var self = this;
   			$(".greenfont").unbind('click');
				$(".greenfont").click(function(e) {
	   			var selectTermId = $(this).parent().parent().attr('id');
				//console.log('selectTermId:');
				//console.log(selectTermId);
				var tagCloudRequestBody = {};
				tagCloudRequestBody.error = $('#'+$(this).parent().parent().attr('id')+" .addterms-error");
				//tagCloudRequestBody.error.selector
			//console.log('Create error selector:  tagCloudRequestBody.error.selector');
			//console.log(tagCloudRequestBody.error.selector);
				tagCloudRequestBody.userInput = $('#'+selectTermId+" .addterms");

				tagCloudRequestBody.submitButton = $('#'+selectTermId+" .greenfont");
				tagCloudRequestBody.self = self;
			//console.log('Create submitButton selector:  tagCloudRequestBody.submitButton.selector');
			//console.log(tagCloudRequestBody.submitButton.selector);
			
				tagCloudRequestBody.removable = "true";


				tagCloudRequestBody.sortable = "false";

				//User entered terms
				tagCloudRequestBody.term =$('#'+selectTermId+' .addterms').val();
			//console.log('Create term selector:  tagCloudRequestBody.term');
			//console.log(tagCloudRequestBody.term);


				var termsSelector = $("#"+selectTermId+ " .terms_eachtab");
			//console.log('Search for terms selector:  ' + termsSelector.selector);
			//console.log(termsSelector.selector);
			//console.log(termsSelector);
				var tempTerms=[];
				if (termsSelector != '' && termsSelector != null && termsSelector != undefined)
				{
				for (var i=0,j=termsSelector.length; i<j; i++){
					var tempVal = termsSelector[i].innerHTML;
					tempVal = tempVal.replace('<img name="closebtn"  src="../themes/default/images/bestbuy/close.png">','');
					var regex = /(<([^>]+)>)/ig;
					var result = tempVal.replace(regex, "");
					//console.log(result);
     				tempTerms[i]=result;
	     			//console.log('Insert clean selector into new grid: tempVal');
				};
					//tempTerms = tempTerms.join(",");
				};
   			//console.log('Clean grid: tempTerms');
			//console.log(tempTerms);
				tagCloudRequestBody.termsList =tempTerms;			

				//Previously entered terms
				tagCloudRequestBody.termsList = tempTerms;				
				tagCloudRequestBody.enteredTerms = $('#'+selectTermId+ " .terms_eachmain");
			//console.log(tagCloudRequestBody.enteredTerms);
				tagCloudRequestBody.buttonImage = $('#'+selectTermId+" img[name='closebtn']");
				self.submitTerms(tagCloudRequestBody);
			});

			$('.addterms').bind('keyup', function(e) {
			var selectTermId = $(this).parent().attr('id');
		    if ( e.keyCode === 13 ) { // 13 is enter key
		    	$("#"+selectTermId+" .greenfont").click();
		    	}
		    });

        },

        /**
         * Sets the default values for the Server call parameters
         */
		
		defaultSettings : function() {
			var self = this;
			self.bannerRequestBody.pageIndex = commonVariables.requestBody.pageIndex;
			self.bannerRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;    
			self.bannerRequestBody.sortColumn = commonVariables.requestBody.sortColumn;
			self.bannerRequestBody.sortOrder = commonVariables.requestBody.sortOrder;
			self.bannerRequestBody.searchOper = commonVariables.requestBody.searchOper;
			self.bannerRequestBody.searchColumnValues = commonVariables.requestBody.searchColumnValues;
		},
		
		/**
         * Rendering of tree with jstree 
         */
		treeRendering : function() {
			
			var self = this;
			var htmlStr = $(document.createElement('div')).attr("class", "TextBoxDiv").css("border-bottom","1px solid #a1a1a1"); 
				
			htmlStr.html('<table cellpadding="0" cellspacing="1"><tr><td id="">Keywords<sup>*</sup></td><td><div class="terms_main"><div class="terms_tabs"><div class="terms_eachmain"></div><a class="greenfont" name="greenfont" href="#">Add Terms</a></div><textarea class="addterms" name="addterms" maxlength="5000"></textarea><span class="addtermssmallerror"></span></div></td></tr><tr><td>Search Profile</td><td><select id="searchprofile"><option value="1">Global</option></select></td>' + 
			'<span class="searchprofile-error"></span></tr></table><div id="treeviewContent"><span>Category TreeView Loading... <img id="catLoading" src="../themes/default/images/bestbuy/cat_tree_loader.gif"></span></div><div class="sub_cat"><b>Selected Category:<sup>*</sup></b><span class="categorypath" id="categorypath"></span> <input type="text" id="categoryId" placeholder="" readonly="readonly"><br><input type="checkbox" id="applyToSubCategories">&nbsp;Apply to Sub-Category</div><div class="facetContent" id="facetContent"></div><div class="addremovebutt flt_left" id="facetbtn"><a href="#" class="btn bbbluebtn" name="addFacet">ADD FACET</a></div><div class="remove_cat"><a href="#" class="btn bbbluebtn" value="Remove Button" title="Remove Category" name="removeButton">REMOVE CATEGORY</a></div><div class="clear"></div>');
			
			var treeView = $('<div class="bbtreeview"><ul id="filetree" class="filetree"><li><span><strong>Click here to edit category</strong></span>' + commonVariables.categoryTreeContent + '</li></ul></div>');
			
			self.templateShowandHide();
			
			setTimeout(function() {
				$(treeView).jstree({
				}).bind("init.jstree", function(event, data){ 
				}).bind("loaded.jstree", function (event, data) {
					$(htmlStr).find("#treeviewContent").children().remove();
					$(htmlStr).find("#treeviewContent").append(treeView);
					$(htmlStr).find("#categorypath").text(commonVariables.categoryPath);
					$(htmlStr).find("#categoryId").attr("placeholder", commonVariables.categoryId);
					self.treeViewClickEvent();
				});
			}, 200);
			
			$("#TextBoxes").append(htmlStr);
			self.removeCategory();
		},
        
		/**
         * Retrieves the value of the tree
         */
		treeViewClickEvent : function() {
		
			var self = this, treeid, path;
			
			$("span.folder").click(function(e){
			
				$("span.folder a").removeClass("selected");
				$(this).find("a").addClass("selected");
				
				treeid = $(this).parent().attr('id');
				path = $(this).parent().attr('path');
				path = path.replace(/%20/g,' ');
				
				var temp = e.currentTarget;
				
				$(temp).parents("div#treeviewContent").siblings('div.sub_cat').children().eq(1).text(path);
				$(temp).parents("div#treeviewContent").siblings('div.sub_cat').children().eq(2).removeClass("error_text");
				$(temp).parents("div#treeviewContent").siblings('div.sub_cat').children().eq(2).attr('placeholder', treeid);
				$(temp).parents("div#treeviewContent").parent().children("div.addremovebutt").children().eq(0).show();
				
				setTimeout(function() {
					$(".bbtreeview").jstree('close_all');
				}, 500);
			});
		},
		
		/***
		 * Remove Category Event
		 */
		removeCategory: function() {
			$("a[name=removeButton]").die('click');
			$("a[name=removeButton]").live('click', function (e) {
				var category = e.currentTarget;
				$(category).parents('.TextBoxDiv').remove();
			});
		},
		
		/***
		 * Remove Facet Event
		 */
		removeFacet: function() {
			$("img[name=removeFacet]").die('click');
			$("img[name=removeFacet]").live('click', function(e){
				var facetdiv = e.currentTarget;
				$(facetdiv).parents('.banner_border').remove();
			});
		}, 
				
        /**
         * Method to handle the edit function
         */
        editEvent : function() {
		
           var self = this;
			
			$("img[name='editIcon']").unbind('click');
			$("img[name='editIcon']").click(function(e) {
				self.mode = "Edit";
				$("#bannerLabel").html('Edit Banner');
				$("#create").html('UPDATE');
				$("#create").attr('title','Update');
				$(".terms_eachmain").empty();
				$("textarea[name=addterms]").val("").removeClass("error_text");
				self.updateData = $(this).closest("tr");
				var bannerId = self.updateData.attr('id');
				self.currentValue = self.updateData.find("#bannerName").html();
				var contextPathId = "";
				
				self.bannerActionBody = {};
				self.getAction(self.bannerActionBody,"editData", bannerId, function(callback) {
					
					self.editvalue = callback.data;
				
					$("#bannername").val(self.editvalue.bannerName).removeClass("error_text"); 
					$("#startDate").val(self.editvalue.startDate);
					$("#endDate").val(self.editvalue.endDate);
					$("#bannerheadselect").removeClass("error_text");
					$("#assetId").removeClass("error_text");
					$("#position option").each(function() {
						if(self.editvalue.bannerType == $(this).val()){
							$(this).attr('selected','selected');
						}
					});
					$("#priority option").each(function() {
						if(self.editvalue.bannerPriority == $(this).val()){
							$(this).attr('selected','selected');
						} 
					});
					
					$("#TextBoxes").empty();
					$(".bannerTemplate").empty();
					
					var template = $(document.createElement('div'));
					template.html('<div class="datatable"><table cellpadding="0" cellspacing="1"><tr><td>Banner Template<sup>*</sup></td><td><select id="bannerheadselect"><option value="HTML_ONLY">HTML_ONLY</option><option value="1HEADER_3SKU">1HEADER_3SKU</option><option value="3Header_3SKU">3Header_3SKU</option></select><span class="bannerheadselect-error"></span></td></tr></table><div id="simplehtmlbanner"><br><table><tr><td>Asset Id<sup>*</sup></td><td><input type="text" id="assetId" maxlength="15"><a class="greenfontAsset" href="#" id="assettest">click here to test</a><span class="assetId-error"></span></td></tr></table></div><div id="oneheadbanner" style="display:none;"><input type="text" placeholder="Header" id="header"><br><div class="bannerselectdata" id="bannerselectdata0"><div class="terms_main"><div class="terms_tabs"><div class="terms_eachmain" id="terms_eachmainheader0"></div><a class="greenfont greenfont-bannertpl" name="greenfont" href="#">Add Terms</a></div><textarea class="addterms addterms-bannertemplate" id="addterms_oneheader1" name="addterms"></textarea><span class="addtermssmallerror"></span></div><br><div class="terms_main"><div class="terms_tabs"><div class="terms_eachmain" id="terms_eachmainheader1"></div><a class="greenfont greenfont-bannertpl" name="greenfont" href="#">Add Terms</a></div><textarea class="addterms addterms-bannertemplate" id="addterms_oneheader2" name="addterms"></textarea><span class="addtermssmallerror"></span></div><br><div class="terms_main"><div class="terms_tabs"><div class="terms_eachmain" id="terms_eachmainheader2"></div><a class="greenfont greenfont-bannertpl" name="greenfont" href="#">Add Terms</a></div><textarea class="addterms addterms-bannertemplate" id="addterms_oneheader3" name="addterms"></textarea><span class="addtermssmallerror"></span></div></div></div><div id="threeheadbanner" style="display:none;"><div class="threeheaddata" id="threeheaddata0"><input type="text" placeholder="Header" id="header0"><br><div class="terms_main"><div class="terms_tabs"><div class="terms_eachmain" id="eachmainheader0"></div><a class="greenfont greenfont-bannertpl" name="greenfont" href="#">Add Terms</a></div><textarea class="addterms addterms-bannertemplate" id="addterms_threeheader1" name="addterms"></textarea><span class="addtermssmallerror"></span></div></div><div class="threeheaddata" id="threeheaddata1"><input type="text" placeholder="Header" id="header1"><br><div class="terms_main"><div class="terms_tabs"><div class="terms_eachmain" id="eachmainheader1"></div><a class="greenfont greenfont-bannertpl" name="greenfont" href="#">Add Terms</a></div><textarea class="addterms addterms-bannertemplate" id="addterms_threeheader2" name="addterms"></textarea><span class="addtermssmallerror"></span></div></div><div class="threeheaddata" id="threeheaddata2"><input type="text" placeholder="Header" id="header2"><br><div class="terms_main"><div class="terms_tabs"><div class="terms_eachmain" id="eachmainheader2"></div><a class="greenfont greenfont-bannertpl" name="greenfont" href="#">Add Terms</a></div><textarea class="addterms addterms-bannertemplate" id="addterms_threeheader3" name="addterms"></textarea><span class="addtermssmallerror"></span></div></div></div></div>');
					$(".bannerTemplate").append(template);
					
					self.templateShowandHide();
					
					if(self.editvalue != null) {
						$.each(self.editvalue.contexts, function(index,value) {
							
							var terms_eachmain = "terms_eachmain" + index;
							var categorypath = "categorypath" + index;
							var category = "category" + index;
							var facetContent = "facetContent" + index;
							var htmlStr = $(document.createElement('div')).attr("id", 'TextBoxDiv' +index).attr('class', 'TextBoxDiv').css("border-bottom","1px solid #a1a1a1"); 
							var contextId = self.editvalue.contexts[index].contextId;
							
							htmlStr.html('<table cellpadding="0" cellspacing="1"><tr><td>Keywords<sup>*</sup></td><td><div class="terms_main"><div class="terms_tabs"><div class="terms_eachmain" id="'+ terms_eachmain +'"></div><a class="greenfont" name="greenfont" href="#">Add Terms</a></div><textarea class="addterms" name="addterms" maxlength="5000"></textarea><span class="addtermssmallerror"></span></div></td></tr><tr><td>Search Profile</td><td><select id="searchprofile"><option value="1">Global</option></select></td>' + 
							'<span class="searchprofile-error"></span></tr></table><div id="treeviewContent"><span>Category TreeView Loading... <img id="catLoading" src="../themes/default/images/bestbuy/cat_tree_loader.gif"></span></div><div class="sub_cat"> <b>Selected Category<sup>*</sup>:</b><span class="categorypath" id="'+categorypath+'"></span> <input type="text" readonly="readonly" id="'+category+'" placeholder=""><br><input type="checkbox" id="applyToSubCategories">&nbsp;Apply to Sub-Category</div><div class="facetContent" id="'+facetContent+'"></div><div class="addremovebutt flt_left"><a href="#" class="btn bbbluebtn" name="addFacet">ADD FACET</a></div></div><div class="remove_cat"><a href="#" class="btn bbbluebtn" value="Remove Button" title="Remove Category" name="removeButton">REMOVE CATEGORY</a></div><div class="clear"></div>');
							
							var treeView = $('<div class="bbtreeview"><ul id="filetree" class="filetree"><li><span><strong>Click here to edit category</strong></span>' + commonVariables.categoryTreeContent + '</li></ul></div>');
							
							$("#TextBoxes").children().eq(0).children('.remove_cat').hide();
							
							setTimeout(function() {
								$(treeView).jstree({
								}).bind("init.jstree", function(event, data){ 
								}).bind("loaded.jstree", function (event, data) {
									$(htmlStr).find("#treeviewContent").children().remove();
									$(htmlStr).find("#treeviewContent").append(treeView);
									self.treeViewClickEvent();
									self.facetEvent();
								});
							}, 200);
							
							$("#TextBoxes").append(htmlStr);
							
							self.removeCategory();
							
							
							$.each(self.editvalue.contexts[index].keywords.split(','), function(index,value){
								$("#"+terms_eachmain).append('<div class="terms_eachtab">'+ value +'<img name="closebtn"  src="../themes/default/images/bestbuy/close.png"></div>');
							});
							
							$(".terms_eachtab").click(function(e){
								$(this).remove();
							});
							
							self.setTermsFunction();
							
							$("#searchprofile option").each(function() {
								if(self.editvalue.contexts[index].searchProfileId == $(this).val()){
									$(this).attr('selected','selected');
								} 
							});
							/*Check if page being rendered has apply to subcategory checked.
							If it does, max the checkbox as selected.
							console.log(self.editvalue.contexts[index].inherit);*/
							if(self.editvalue.contexts[index].inherit != 0) {
							var checkindex = index+1;
							$(':checkbox').eq(checkindex).prop('checked', true);
							}							
							
							if(self.editvalue.contexts[index].categoryPath != null) {
								$("#"+categorypath).text(self.editvalue.contexts[index].categoryPath.replace(/%20/g,' '));
							}
							if(self.editvalue.contexts[index].contextPathId != null) {
								contextPathId = self.editvalue.contexts[index].contextPathId;
								$("#"+category).attr('placeholder', self.editvalue.contexts[index].contextPathId);
							}
							
							/********* Facet Data Binding******/	
							if(self.editvalue.contexts[index].contextFacetWrapper != null) {
							
								$.each(self.editvalue.contexts[index].contextFacetWrapper, function(index, value){
									
									$("#"+facetContent).show();
									
									var facetId = value.facetId;
									var attributeValueId =  value.attributeValueId;
									var facetDiv = $(document.createElement('div')).attr("id", 'facet' + index).attr("class","banner_border"); 
									var option = "";
									
									self.bannerActionBody = {};
									self.getAction(self.bannerActionBody,"categoryFacet", contextPathId, function(response) {
									
										$.each(response.rows, function(index, value){
											option += '<option value="'+value.facetId+'">'+ value.facetName+'</option>';	
											facetDiv.html('<div class="flt_right"><img name="removeFacet" src="../themes/default/images/bestbuy/close.png"/></div><strong>Select Facet Display Name:</strong><br><em>Grayed out facets are dependent on this facet and cannot be selected.</em><select><option>Please Select an Option</option>'+ option +'</select><input type="text" id="attributeName" class="attributeName"></input>'); 
											
											self.removeFacet();
											$(facetDiv).appendTo("#"+facetContent);
										});
										
										$(facetDiv).find('#attributeName').bind('keypress', function() {
											$(this).removeAttr('key');
										});
										/******** Binding facet attributeValue on view*******/
										
										$(facetDiv).find("option").each(function(index,value){
											if( $(value).val() == facetId) {
												$(value).attr("selected", "selected");
												self.bannerActionBody = {};
												self.getAction(self.bannerActionBody,"attributeValue", facetId, function(response){ 
													var autocompleteval = [];
													$.each(response.rows, function(index, value){
														autocompleteval.push({"key":value.attributeValueId,"value":value.attributeValue});
														$(".attributeName" ).autocomplete({
															source: autocompleteval
														});
													});
													$.each(autocompleteval, function(index, value){
														if(attributeValueId == value.key) {
															$(facetDiv).find('input').attr("key",value.key);
															$(facetDiv).find('input').val(value.value);	
														}
													});
	
													$(".attributeName" ).on( "autocompleteselect", function( event, ui ){
														$(facetDiv).find('input').val('');
														$(this).attr("key",ui.item.key);
													});													
												});
											}
										});
										
										/******** Binding facet attributeValue on change *******/
										
										$(facetDiv).find('select').change(function() {
							
											$(facetDiv).find('#attributeName').removeAttr('placeholder').removeClass("error_text");
											value = $(this).val();
											self.bannerActionBody = {};
											self.getAction(self.bannerActionBody, "attributeValue", value, function(response){ 
												var autocompleteval = [];
												$.each(response.rows, function(index, value){
													autocompleteval.push({"key":value.attributeValueId,"value":value.attributeValue});
													$(".attributeName" ).autocomplete({
														source: autocompleteval
													});
												});
												
												$(facetDiv).find('input').val('');	
												$(".attributeName" ).on( "autocompleteselect", function( event, ui ) {
													$(this).attr("key",ui.item.key);
												});
												
											});
											
										});
										
										$(facetDiv).find('#attributeName').focusout(function() {
											var attributeValue = $(this).attr("key");
											if(attributeValue == undefined) {
												self.hasError = true;
												$(this).focus();
												$(this).addClass("error_text");
												$(this).val('');
												$(this).attr('placeholder', 'Invalid facet attribute');
												$(this).live('keypress', function() {
													$(this).removeAttr('placeholder');
													$(this).removeClass("error_text");
												});
											} else {
												self.hasError = false;
											}
										});
										
									});
								});	
							} 
							/*********** EOF Facet Data Binding**********/
							
							/********* Template Data Binding********/
							
							$("#bannerheadselect option").each( function(index, value) {
							
								if( self.editvalue.bannerTemplate != null && self.editvalue.bannerTemplate == $(this).val()){
									$(this).attr('selected','selected');
									if ($(this).val() == 'HTML_ONLY') {
										$("#assetId").val(self.editvalue.documentId);
										$("#simplehtmlbanner").css("display","block");
										$("#oneheadbanner").css("display","none");
										$("#threeheadbanner").css("display","none");
									}
									 
									if ($(this).val() == '1HEADER_3SKU') {
										$("#simplehtmlbanner").css("display","none");
										$("#oneheadbanner").css("display","block");
										$("#threeheadbanner").css("display","none");
										var terms_eachmainheader = "";
										$.each(self.editvalue.templates,function(index, value){
											$("#header").val(value.bannerHeader);
											$.each(value.bannerSlotSkuLists, function(index,value){
												terms_eachmainheader = "terms_eachmainheader" + index;
												$("#"+terms_eachmainheader).empty();
												$.each(value.skuIds.split(','), function(index, value){
													$("#"+terms_eachmainheader).append('<div class="terms_eachtab">'+ value +'<img name="closebtn"  src="../themes/default/images/bestbuy/close.png"></div>');
												})
											});
											
											$(".terms_eachtab").click(function(e){
												$(this).remove();
											});
										});
									}

									if ($(this).val() == '3Header_3SKU') {
										$("#simplehtmlbanner").css("display","none");
										$("#oneheadbanner").css("display","none");
										$("#threeheadbanner").css("display","block");
										var eachmainheader = "";
										$.each(self.editvalue.templates, function(index, value){
											var header = "header" + index;
											eachmainheader = "eachmainheader" + index;
											$("#"+eachmainheader).empty();
											$("#"+header).val(value.bannerHeader);
											$.each( value.bannerSlotSkuLists[0].skuIds.split(','), function(index, value){
												$("#"+eachmainheader).append('<div class="terms_eachtab">'+ value +'<img name="closebtn"  src="../themes/default/images/bestbuy/close.png"></div>');
											});
											
											$(".terms_eachtab").click(function(e){
												$(this).remove();
											});
										});
										
									}
								}
							});
											self.countTerms();
											
							/*********EOF TemplateData Binding ********/
						});
					}		
				});
				setTimeout(function() {
					self.sizeModal();
				}, 500);
			}); 
        },
        
        /**
         * Trigger the delete call to the Service
         */
        deleteEvent: function() {
            var self = this;
            
            $("img[name='delete']").unbind('click');
            $("img[name='delete']").click(function(e) { 
				self.updateData = $(this).closest("tr");
				var deleteBanner = self.updateData.children().eq(2).text();
                var _self = this, bannerid;
                self.navigation = new Clazz.com.components.navigation.js.Navigation();
                $(".greenfont").attr('href', '#deletemsg');
				$('#deletemsg').show();
				self.navigation.alertBox("Delete", "Are you sure you want to delete <span class='delete_color'>"+ deleteBanner + " </span>?", function(callback){
                    if(callback) {
                        bannerid = $(_self).closest('tr').attr('id');
                        self.bannerActionBody = {};
                        self.getAction(self.bannerActionBody, "delete", bannerid);
                    }   
                }); 
            });
			
			/* $("img[name='contextdel']").unbind('click');
			$("img[name='contextdel']").click(function(e) {
				var _self = this, contextid;
				contextid = $(_self).parent().parent().parent().attr('id');
				self.navigation = new Clazz.com.components.navigation.js.Navigation();
                $(".greenfont").attr('href', '#deletemsg');
				$('#deletemsg').show();
                self.navigation.alertBox("Delete", "Are you sure you want to delete this?", function(callback) {
                    if(callback) {
                        self.bannerActionBody = {};
                        self.getAction(self.bannerActionBody, "contextdelete", contextid);
                    }   
                });
			}); */	
        
        },

        /**
         * Trigger the approval call to the Service
         */
        approveEvent : function() {
            var self = this;
            $("img[name='approval']").unbind('click');
            $("img[name='approval']").click(function(e) {
                self.bannerActionBody = {};
                self.getAction(self.bannerActionBody, "approve", $(this).closest('tr').attr("id"));
            });
        },
        
        /**
         * Trigger the reject call to the Service
         */
        rejectEvent : function() {
            var self = this;
            $("img[name='reject']").unbind('click');
            $("img[name='reject']").click(function(e) {
                self.bannerActionBody = {};
                self.getAction(self.bannerActionBody, "reject", $(this).closest('tr').attr("id"));
            });
        
        },
        
        /**
         * Hides the selected categories during landing of the page
         */
        rowhide: function() {
            var clickedobj;
			var currentRow;
            $(".data").hide();
            $(".tabopen").unbind('click');
            $(".tabopen").click(function(e) {
                  clickedobj = e.currentTarget;
                  if ($(clickedobj).attr("dataflag") == "true") {
                    $(clickedobj).removeClass("iconclose");
                    $(clickedobj).addClass("iconopen");
					currentRow = $(clickedobj).parent().parent().next();
					currentRow.find(".data").show();
					while(currentRow != null && currentRow.length > 0 && currentRow.attr('id') == undefined) {
						currentRow.find(".data").show();
						if(currentRow != null && currentRow.next('tr').length > 0) {
							currentRow = currentRow.next('tr');
						}else {currentRow = null}
					}
					
                    $(clickedobj).attr("dataflag", "false");
                  } else {
                    $(clickedobj).removeClass("iconopen");
                    $(clickedobj).addClass("iconclose");
					currentRow = $(clickedobj).parent().parent().next();
					currentRow.find(".data").hide();
					while(currentRow != null && currentRow.length > 0 && currentRow.attr('id') == undefined) {
						currentRow.find(".data").hide();
						if(currentRow != null && currentRow.next('tr').length > 0) {
							currentRow = currentRow.next('tr');
						}else {currentRow = null}
					}
                    $(clickedobj).attr("dataflag", "true");          
                  }
            });
        },
        
		/**
         * Add Facet Event based on category selected  
         */
        facetEvent : function() {
		
            var self = this;
			
			$("a[name=addFacet]").unbind('click');
			$("a[name=addFacet]").click(function(e){
			
				var option = "";
				var temp = e.currentTarget.parentNode;
				
				var id = $(temp).siblings('div.sub_cat').children().eq(2).attr('placeholder');
				var htmlStr = $(document.createElement('div')).attr("class","banner_border"); 
				
				$(temp).siblings('div.facetContent').show();
				
				self.getAction(self.bannerActionBody,"categoryFacet", id, function(response) {
				
					if(response.rows != null) {
					
						$.each(response.rows, function(index, value) {
						
							option += '<option value="'+value.facetId+'">'+ value.facetName+'</option>';	
							htmlStr.html('<div class="flt_right"><img name="removeFacet" src="../themes/default/images/bestbuy/close.png"/></div><strong>Select Facet Display Name:</strong><br><em>Grayed out facets are dependent on this facet and cannot be selected.</em><select><option>Please Select an Option</option>'+ option +'</select>&nbsp;&nbsp;<input type="text" class="attributeName" id="attributeName" style="display:none;">&nbsp;&nbsp;');
							
							self.removeFacet();
							
							$(htmlStr).find('select').change(function() {
								$(htmlStr).find('#attributeName').removeAttr('placeholder').removeClass("error_text");
								$(htmlStr).find("#attributeName").show();
								value = $(this).val();
								self.getAction(self.bannerActionBody, "attributeValue", value, function(response){
									var autocompleteval = [];
									$.each(response.rows, function(index, value){
										autocompleteval.push({"key":value.attributeValueId,"value":value.attributeValue});
										$(".attributeName" ).autocomplete({
											source: autocompleteval
										});
									});
									
									$(".attributeName" ).on( "autocompleteselect", function(event, ui ) {
										$(this).attr("key",ui.item.key);
									});
								});
							});
							
							$(htmlStr).find('#attributeName').focusout(function() {
								var attributeValue = $(this).attr("key");
								if(attributeValue == undefined) {
									self.hasError = true;
									$(this).focus();
									$(this).addClass("error_text");
									$(this).val('');
									$(this).attr('placeholder', 'invalid facet attribute');
									$(this).live('keypress', function() {
										$(this).removeAttr('placeholder');
										$(this).removeClass("error_text");
									});
								} else {
									self.hasError = false;
								}
							});
						});
						
						if(response.rows.length != 0) {
							if($(temp).siblings('div.facetContent').text() == "No Facet available for selected category") {
								$(temp).siblings('div.facetContent').html('');
							}
							$(temp).siblings('div.facetContent').append(htmlStr);
						} else {
							$(temp).siblings('div.facetContent').text('No Facet available for selected category');
						}
					} else {
						$(temp).siblings('div.facetContent').text('No Facet available for selected category');
					}
				});
				
			});
			
        },
        
		/**
         * Construction of post data Json  
         */
        generateTermsandOptionsvalue : function(text) {
            
            var self = this;
			var termsjson = {};
			var searchprofileId = "";
			var facetId = "";
			var attributeValueId = "";
			var contextId = "";
			var contextIdArray = [];
			var facetjson = {};
			var completeJson = [];
			var facetjsonArray = [];
			
			if(self.updateData != null) {
				var currentRow = self.updateData.next();
				while(currentRow != null && currentRow.length > 0 && currentRow.attr('id') == undefined) {
					contextIdArray.push($(currentRow).children().children().children().children().children().eq(0).attr('id'));
					if(currentRow != null && currentRow.next('tr').length > 0) {
						currentRow = currentRow.next('tr');
					}else {currentRow = null}
				}
			}	
			
			$.each($("#TextBoxes").children(), function(index,value) {
				
				termsjson = {};
				if(text == "UPDATE") {
					contextId = Number(contextIdArray[index]);
				} else {
					contextId = "";
				}
				
				$.each($(value).children().eq(1).children().eq(0).children(), function(index, value) {     // Keywords json      
					
					if( index == 0 ) {
					
						$.each($(value).children().eq(1).children().eq(0).children().eq(0), function(index, value){
							if($(value).children().eq(0).text() == "") {
								self.hasError = true;
								$(value).siblings('.addterms').focus();
								$(value).siblings('.addterms').addClass("error_text");
								$(value).siblings('.addterms').attr('placeholder', 'keywords may not be empty');
								$(value).siblings('.addterms').bind('keypress', function() {
									$(this).removeClass("error_text").removeAttr('placeholder');
								});
							}
						});
					
						var terms = [];
						
						$.each($(value).children().eq(1).children().eq(0).children().eq(0).children().eq(0).children(), function(index, value){
							terms.push($(value).text());
						});
						
						var content = "";
						var newcontent = "";
						$.each(terms, function(index, value){
							content += ','+ value;
							newcontent = content.replace(/^,/, '');
						});	
						
						if(contextId == "undefined" || contextId == undefined || contextId == "null" || contextId == null) {
							contextId = "";
						} 
						
						termsjson = {"contextId":contextId, "keywords":newcontent,"inherit":0,"categoryPath":"", "contextPathId":"", "searchProfileId":""};
						
					}
					
					if( index == 1 ) {													// SearchProfile Id	json								
						
						$.each($(value).children().eq(1).children(), function(index, value) {
							searchprofileId = Number($(value).val());
						});
						
						termsjson.searchProfileId = searchprofileId;
						
					} 
					
				});
				
				
				var categoryPath = $(value).children().siblings('.sub_cat').children().eq(1).text();
				var contextPathId = $(value).children().siblings('.sub_cat').children().eq(2).attr('placeholder');
				var inherit = $(value).children().siblings('.sub_cat').children().eq(4)[0].checked;

				if(categoryPath == "") {
					$(value).children().siblings('.sub_cat').children().eq(2).focus();
					$(value).children().siblings('.sub_cat').children().eq(2).addClass("error_text")
					$(value).children().siblings('.sub_cat').children().eq(2).attr('placeholder','category must be selected');
				}
				termsjson.categoryPath = categoryPath;
				termsjson.contextPathId = contextPathId;
				if (inherit == true){inherit =1;}else{inherit =0;};
				termsjson.inherit = inherit;
				
				facetjsonArray = [];
				$.each($(value).children().siblings('.facetContent').children(), function(index, value) {
				
					var facet = $(value).children().eq(5).val();
					if(facet == "") {
						$(value).children().eq(5).focus();
						$(value).children().eq(5).addClass("error_text");
						$(value).children().eq(5).attr('placeholder','Facet Attribute value may not be empty');
						$(value).children().eq(5).bind('keypress', function() {
							$(this).removeClass("error_text").removeAttr('placeholder');
						});
					}
					
					facetId = Number($(value).children().eq(4).val());
					attributeValueId = Number($(value).find("#attributeName").attr('key'));
					if(facetId != "" && attributeValueId!= "" && facetId != null && attributeValueId != null && isNaN(attributeValueId) == false) {
						facetjson = {"facetId":facetId, "attributeValueId":attributeValueId};
						facetjsonArray.push(facetjson);
					}	
				});
				
				if(facetjsonArray.length != 0) {
					termsjson.contextFacetWrapper = facetjsonArray;
				} else {
					termsjson.contextFacetWrapper = [];
				}	
				
				completeJson.push(termsjson);
			});
			
			return completeJson;
        
        },

                
        /**
         * Generates the template 
         * @bannerTemplate - type of banner template
         */
        generateTemplate : function(bannerTemplate) {
            var templatedata = [];
			var skudata = [];
			var templateJSON = [];
			var newskudata = [];
			
			
			if(bannerTemplate == "1HEADER_3SKU") {	
				
				var skuPosition = 0;
				var header = $("#header").val();
				
				$.each($(".datatable").children().eq(2).children(), function(index, value){
					
					if(index == 2) {
						$.each($(value).children(), function(index,value){
							$.each($(value).children().eq(0).children().eq(0), function(index, value){
								
								var terms = [];
								
								$.each($(value).children() , function(index, value){
									terms.push($(value).text());
								});	
								
								var content = "";
								var newcontent = "";
								$.each(terms, function(index, value){
									content += ','+ value;
									newcontent = content.replace(/^,/, '');
								});
								
								skudata.push({"skuIds":newcontent, "skuPosition":skuPosition});
								skuPosition += 1;
								
							});
							
						});
					}
					
				});
				
				templatedata = [{"bannerHeader":header,"bannerSlotSkuLists":skudata}];            
			}
			
				
			if(bannerTemplate == "3Header_3SKU") {

				$.each($(".datatable").children().eq(3).children(), function(index, value){
					$.each($(value).children(), function(index, value){
						
						if(index == 0) {
							var header = $(value).eq(0).val();
							templateJSON.push({"bannerHeader":header, "bannerSlotSkuLists":[]})
						}
						
						if(index == 2) {
						
							$.each($(value).children().eq(0).children().eq(0), function(index, value) {
								
								var terms = [];
								
								$.each($(value).children() , function(index, value){
									terms.push($(value).text());
								});
								
								var content = "";
								var newcontent = "";
								
								$.each(terms, function(index, value){
									content += ','+ value;
									newcontent = content.replace(/^,/, '');
									
								});
								
								newskudata.push({"skuIds":newcontent, "skuPosition":0});           
								
							});
							
						}
						
					});
				});
				
				for (var i = 0; i < newskudata.length; i++) {					
					object = templateJSON[i];
					var temp = [];
					temp.push(newskudata[i])
					for (var property in object) {
						object['bannerSlotSkuLists'] = temp; 
					}
				}
				
				templatedata = templateJSON;
			}
			
			return templatedata;
        },

        /**
         * Triggers the search call and returns the data
         * @txtSearch - text to be searched
         */
        search : function(txtSearch) {
            var self = this;
            var requestBody = self.bannerRequestBody;
            if (txtSearch != null) {
                self.defaultSettings();
                requestBody.searchColumnValues = [];
                requestBody.searchColumnValues.push(txtSearch);
                self.bannerRequestBody = requestBody;               
                self.bannerListener.getBannerList(self.bannerListener.getRequestHeader(requestBody), function(response) {
                    self.pageRefresh(response);
                });
            }
        },
		
		columnSearch : function() {
			var self = this;
			var renderFunction = $.proxy(self.renderTemplate, self);
			var requestBody = self.bannerRequestBody;
			var searchCriteriaArray = [];
			self.searchdata.bannerName = $("#bannerNameSearch").val();
			self.searchdata.bannerType = $("#typeSearch").val();
			self.searchdata.modifiedBy = $("#lastModifiedBySearch").val();
			self.searchdata.status = $("#statusSearch").val();
			
			if($("#bannerNameSearch").val() != "") {
				var searchCriteria = {};
			    searchCriteria.key = "bannerName";
				searchCriteria.value = $("#bannerNameSearch").val();
				searchCriteriaArray.push(searchCriteria);
			} 
			
			if($("#typeSearch").val() != "") {
				var searchCriteria = {};
				searchCriteria.key = "bannerType";
				searchCriteria.value = $("#typeSearch").val();
				searchCriteriaArray.push(searchCriteria);
			} 
			
			if( $("#lastModifiedBySearch").val() != "") {
				var searchCriteria = {};
				searchCriteria.key = "modifiedBy";
				searchCriteria.value = $("#lastModifiedBySearch").val();
				searchCriteriaArray.push(searchCriteria);
			} 
			
			if( $("#statusSearch").val() != "") {
				var searchCriteria = {};
				searchCriteria.key = "status";
				searchCriteria.value = $("#statusSearch").val();
				searchCriteriaArray.push(searchCriteria);
			} 	
	
			if (searchCriteria != null) {
				self.defaultSettings(); 
				requestBody.searchColumnValues = searchCriteriaArray;
				self.bannerRequestBody = requestBody;				
				self.bannerListener.getBannerList(self.bannerListener.getRequestHeader(requestBody), function(response) {
					renderFunction(response, self.contentContainer);
				});
			} else {
				var self = this;            
				self.defaultSettings();
				self.sortColumn = null;
				self.bannerListener.getBannerList(self.bannerListener.getRequestHeader(self.bannerRequestBody), function(response) {
					self.pageRefresh(response);
				});
			}
		},
		
		keywordsSearch : function() {
			var self = this;
			var renderFunction = $.proxy(self.renderTemplate, self);
			var requestBody = self.bannerRequestBody;
			var searchCriteriaArray = [];
			self.searchdata.keywords = $("#keywordsSearch").val();
			
			if($("#keywordsSearch").val() != "") {
				var searchCriteria = {};
			    searchCriteria.key = "keyword";
				searchCriteria.value = $("#keywordsSearch").val();
				searchCriteriaArray.push(searchCriteria);
			}
			
			if (searchCriteria != null) {
				self.defaultSettings(); 
				requestBody.searchColumnValues = searchCriteriaArray;
				self.bannerRequestBody = requestBody;				
				self.bannerListener.getBannerList(self.bannerListener.getRequestHeader(requestBody), function(response) {
					renderFunction(response, self.contentContainer);
				});
			} else {
				var self = this;            
				self.defaultSettings();
				self.sortColumn = null;
				self.bannerListener.getBannerList(self.bannerListener.getRequestHeader(self.bannerRequestBody), function(response) {
					self.pageRefresh(response);
				});
			}
		},
		
		fillSearchData : function() {
			var self = this;
			$("#bannerNameSearch").val(self.searchdata.bannerName);
			$("#typeSearch").val(self.searchdata.bannerType);
			$("#lastModifiedBySearch").val(self.searchdata.modifiedBy);
			$("#statusSearch").val(self.searchdata.status);
			$("#keywordsSearch").val(self.searchdata.keywords);
		},
        
        /**
         * Triggers the pagination actions 
         * @txtSearch - text to be searched
         * @pageIndex - index of the page 
         */
        doPagination : function(txtSearch, pageIndex) {
            var self = this;
            var listener = self.bannerRequestBody;
            var requestBody = this.bannerRequestBody;
            requestBody.pageIndex = pageIndex;
            self.bannerRequestBody = requestBody;

            self.bannerListener.getBannerList(self.bannerListener.getRequestHeader(requestBody), function(response) {
                self.pageRefresh(response);
            });
        },
		
		/***
		 * Banner Template Generation based on banner Template selected
		 */
		templateShowandHide: function() {
			
			$("#bannerheadselect").change(function () {      
                if ($(this).val() == 'HTML_ONLY') {
                    $("#simplehtmlbanner").css("display","block");
                    $("#oneheadbanner").css("display","none");
                    $("#threeheadbanner").css("display","none");
                }
                 
                if ($(this).val() == '1HEADER_3SKU') {
                    $("#simplehtmlbanner").css("display","none");
                    $("#oneheadbanner").css("display","block");
                    $("#threeheadbanner").css("display","none");
                }

                if ($(this).val() == '3Header_3SKU') {
                    $("#simplehtmlbanner").css("display","none");
                    $("#oneheadbanner").css("display","none");
                    $("#threeheadbanner").css("display","block");
                }
            });
			
			/***
			* Click to Here Test - AssetId Functionality
			*/
            $("#assettest").click(function(e) {
				$(".assetId-error").html('');
				if($("#simplehtmlbanner").css('display') == "block") {
					if($("#assetId").val() == "") {
						$("#assetId").focus();
						$(".assetId-error").html('AssetId cannot be empty');
					} else {
						$(this).attr('TARGET', '_blank').attr('href', 'http://preview.bestbuy.com/site/cseeidvalidation.jsp?id='+$("#assetId").val());
					}
				}	
            });
			
			/***
			 * Allowing only numbers during keypress of AssetId Field
			 */
			$("#assetId").keypress(function (e) {
				if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
					return false;
				}
			});
		
		},
		
        /***
         * Bind the action listeners. The bindUI() is called automatically after the render is complete 
         */
        bindUI : function() {
		
            var self = this;
            
            self.editEvent();
            self.deleteEvent();
            self.rowhide();
            self.approveEvent();
            self.rejectEvent();
            
			/***
			 *  Table sorting
			 */
			$('.tabheader').die("click");
			$('.tabheader').live("click", function() {
				var sortOrder = "";
				self.sortColumn = $(this).parent().attr('tabname');
				if (self.bannerRequestBody.sortOrder == "desc") {
					sortOrder = "asc";
				} else if (self.bannerRequestBody.sortOrder == "asc") {
					sortOrder = "desc";
				}
				self.bannerRequestBody.pageIndex = commonVariables.requestBody.pageIndex;
				self.bannerRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;    
				self.bannerRequestBody.sortColumn = self.sortColumn;
				self.bannerRequestBody.sortOrder = sortOrder;
				self.bannerRequestBody.searchOper = commonVariables.requestBody.searchOper;
				self.bannerRequestBody.searchColumnValues = commonVariables.requestBody.searchColumnValues;
				self.bannerListener.getBannerList(self.bannerListener.getRequestHeader(self.bannerRequestBody), function(response) {
					self.pageRefresh(response);
				});
			});
			
			$('input[name="search"]').keypress(function(e){
				var id = $(this).attr('id');
				if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
					code= (e.keyCode ? e.keyCode : e.which);
					if (code == 13) {
						if(id == "keywordsSearch") {
							self.keywordsSearch();
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
					self.escapePopup($("#Add_banners"));

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
					self.escapePopup($("#Add_banners"));

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
            
			/***
			* Add New Banner Event
			*/
            $("#AddIcon").click(function(e) {
				self.escapePopup($("#Add_banners"));
				setTimeout(function() {
					$('#bannername').focus();
				}, 2000);
				self.mode = "Add";
				self.currentValue = null;
                $("#pop_error").hide();
                $("#TextBoxes").empty();
				$(".bannerTemplate").empty();
				var d = new Date();
				var curr_date = ((d.getDate()+"").length === 1 ? "0" : "") + d.getDate()+"";
				var month = d.getMonth()+1;
				var curr_month = ((month+"").length === 1 ? "0" : "") + month+""; ; //Months are zero based
				var curr_year = d.getFullYear();
				/* var curr_hh = ((d.getHours()+"").length === 1 ? "0" : "") + d.getHours()+"";
				var curr_min = ((d.getMinutes()+"").length === 1 ? "0" : "") + d.getMinutes()+""; */
				var hours = 0;
				var curr_hh = ((hours+"").length === 1 ? "0" : "") + hours +""; 
				var minutes = 0;
				var curr_min = ((minutes+"").length === 1 ? "0" : "") + minutes +"";
				var newdate = curr_month + "-" + curr_date + "-" + curr_year + " " + curr_hh + ":" + curr_min;
				$("#startDate").val(newdate);
				var template = $(document.createElement('div'));
				template.html('<div class="datatable"><table cellpadding="0" cellspacing="1"><tr><td>Banner Template<sup>*</sup></td><td><select id="bannerheadselect"><option value="HTML_ONLY">HTML_ONLY</option><option value="1HEADER_3SKU">1HEADER_3SKU</option><option value="3Header_3SKU">3Header_3SKU</option></select><span class="bannerheadselect-error"></span></td></tr></table><div id="simplehtmlbanner"><br><table><tr><td>Asset Id<sup>*</sup></td><td><input type="text" id="assetId" maxlength="15"><a class="greenfont" href="#" id="assettest">click here to test</a><span class="assetId-error"></span></td></tr></table></div><div id="oneheadbanner" style="display:none;"><input type="text" placeholder="Header" id="header"><br><div class="bannerselectdata" id="bannerselectdata0"><div class="terms_main"><div class="terms_tabs"><div class="terms_eachmain" id="terms_eachmainheader0"></div><a class="greenfont greenfont-bannertpl" name="greenfont" href="#">Add Terms</a></div><textarea class="addterms addterms-bannertemplate" id="addterms_oneheader1" name="addterms"></textarea><span class="addtermssmallerror"></span></div><br><div class="terms_main"><div class="terms_tabs"><div class="terms_eachmain" id="terms_eachmainheader1"></div><a class="greenfont greenfont-bannertpl" name="greenfont" href="#">Add Terms</a></div><textarea class="addterms addterms-bannertemplate" id="addterms_oneheader2" name="addterms"></textarea><span class="addtermssmallerror"></span></div><br><div class="terms_main"><div class="terms_tabs"><div class="terms_eachmain" id="terms_eachmainheader2"></div><a class="greenfont greenfont-bannertpl" name="greenfont" href="#">Add Terms</a></div><textarea class="addterms addterms-bannertemplate" id="addterms_oneheader3" name="addterms"></textarea><span class="addtermssmallerror"></span></div></div></div><div id="threeheadbanner" style="display:none;"><div class="threeheaddata" id="threeheaddata0"><input type="text" placeholder="Header" id="header0"><br><div class="terms_main"><div class="terms_tabs"><div class="terms_eachmain" id="eachmainheader0"></div><a class="greenfont greenfont-bannertpl" name="greenfont" href="#">Add Terms</a></div><textarea class="addterms addterms-bannertemplate" id="addterms_threeheader1" name="addterms"></textarea><span class="addtermssmallerror"></span></div></div><div class="threeheaddata" id="threeheaddata1"><input type="text" placeholder="Header" id="header1"><br><div class="terms_main"><div class="terms_tabs"><div class="terms_eachmain" id="eachmainheader1"></div><a class="greenfont greenfont-bannertpl" name="greenfont" href="#">Add Terms</a></div><textarea class="addterms addterms-bannertemplate" id="addterms_threeheader2" name="addterms"></textarea><span class="addtermssmallerror"></span></div></div><div class="threeheaddata" id="threeheaddata2"><input type="text" placeholder="Header" id="header2"><br><div class="terms_main"><div class="terms_tabs"><div class="terms_eachmain" id="eachmainheader2"></div><a class="greenfont greenfont-bannertpl" name="greenfont" href="#">Add Terms</a></div><textarea class="addterms addterms-bannertemplate" id="addterms_threeheader3" name="addterms"></textarea><span class="addtermssmallerror"></span></div></div></div></div>');
				
				$(".bannerTemplate").append(template);
				
				self.treeRendering();
				self.deleteEvent();
				self.setTermsFunction();
				self.facetEvent();
				self.templateShowandHide();

				$("#TextBoxes").children().eq(0).children('.remove_cat').hide();
				$("#bannerLabel").html('Create Banner');
				$("#create").html('CREATE');
				$("#create").attr('title','Create');
				$("#bannername").val('').removeClass("error_text");
				$("#priority").val('');
				$("#startDate").attr('placeholder', 'Click to select date.');
				$("#endDate").attr('placeholder', 'Click to select date.');
				$(".addterms").val('').removeClass("error_text");
				$(".terms_eachmain").empty().removeClass("error_text"); 
				$("#bannerheadselect").removeClass("error_text");
				$("#assetId").val('').removeClass("error_text");
				self.termsList = [];
				self.terms = [];
				$(".startdate-error").empty();
				$(".enddate-error").empty();
				
				setTimeout(function() {
					$(".modal-body").scrollTop(0);
				}, 200);
				self.countTerms();
				setTimeout(function() {
					self.sizeModal();
				}, 500);
            });
			
			$(".ui-datepicker-trigger, #startDate, #endDate").click(function() {
				$("#endDate").removeClass("error_text").removeAttr("placeholder");
				$('.startdate-error').text('');
				$('.enddate-error').text('');
			});
            
			/***
			* Show All Banner Event
			*/
            $("#banner_showall").click(function(e) {
				$("#banner_hideall").parent().show();
				$("#banner_showall").parent().hide();
				$(".data").show();
                $(".tabopen").removeClass("iconclose");
                $(".tabopen").addClass("iconopen");
                $(".tabopen").attr("dataflag", "false");
            });
			
            
			/***
			* Hide All Banner Event
			*/
            $("#banner_hideall").click(function(e) {
				$("#banner_hideall").parent().hide();
				$("#banner_showall").parent().show();
				$(".data").hide();
                $(".tabopen").removeClass("iconopen");
                $(".tabopen").addClass("iconclose");
                $(".tabopen").attr("dataflag", "true");
            });
            
			/***
			* Add New Category Event
			*/
			$("#addButton").click(function (e) {
				self.treeRendering(); 
				self.setTermsFunction();
				self.facetEvent();
				self.countTerms();
				self.setTermsFunction();
			});
			
			
            /***
			* Name Already Exist Event
			*/
            $("#bannername").focusout(function() {
				var bannername = $("#bannername");
				if (bannername.val().replace(/^\s+|\s+$/g,'') != '') {
					self.onFocusOutEvent.dispatch(bannername.val(), self.mode, self.currentValue, function(response) {
						if (response.status == "ERROR" && response.generalPurposeMessage !=null) {
							bannername.val('');
							var res = response.generalPurposeMessage[0].split(':');
							bannername.attr('placeholder', res[1]);
							bannername.addClass("error_text");
							bannername.live('input', function() {
								$(this).removeClass("error_text").removeAttr('placeholder');
							});
						}
					});
				} else {
					$("#bannername").val('');
				}
			               
            });
			
			/***
			 * Create New Banner Event
			 */
            $("#create").click( function(e) {
                var bannername = $("#bannername").val();
                var proirity = Number($("#priority").val());
                var position = Number($("#position").val());
                var searchprofile = $("#searchprofile").val();
                var bannertemplate = $("#bannerheadselect").val();
                var startdate = $("#startDate").val();
                var enddate = $("#endDate").val();
                var assetId = $("#assetId").val();
                var text = $("#create").text();
                var templatevalue = self.generateTemplate(bannertemplate);
                
				if (!validate()) { 
				
                    self.bannerActionBody = {};
					
                    if( text == "CREATE") {
						
						if(bannertemplate == "HTML_ONLY") { 
							
							var postvalue = self.generateTermsandOptionsvalue(text);
							self.bannerActionBody.bannerId = "";
							self.bannerActionBody.bannerName = bannername;
							self.bannerActionBody.bannerType = position;
							self.bannerActionBody.bannerPriority = proirity;
							self.bannerActionBody.startDate = startdate;
							self.bannerActionBody.endDate = enddate;
							self.bannerActionBody.contexts = postvalue;
							self.bannerActionBody.documentId = assetId;
							self.bannerActionBody.bannerTemplate = bannertemplate;
							self.getAction(self.bannerActionBody,"create", "", function(response){
							if(response.generalPurposeMessage!=null){	
								$.each(response.generalPurposeMessage, function(index,value) {
									if(value == "bannerName:May not be empty") {
									   var bannerName = value.split(':');
										var msg = bannerName[1];
										$("#bannername").attr('placeholder', msg);
										$("#bannername").addClass("error_text");
										$("#bannername").live('input', function() {
											$(this).removeClass("error_text");
										});
									}
									if(value == "endDate:May not be empty") {
									   var endDate = value.split(':');
										var msg = endDate[1];
										$("#endDate").attr('placeholder', msg);
										$("#endDate").addClass("error_text");
										$("#endDate").live('input', function() {
											$(this).removeClass("error_text");
										});
									}
									if(value == "assetId:May not be empty") {
										   var endDate = value.split(':');
											var msg = endDate[1];
											$("#assetId").attr('placeholder', msg);
											$("#assetId").addClass("error_text");
											$("#assetId").live('input', function() {
												$(this).removeClass("error_text");
											});
										}
									/* if(value == "bannerName:BannerName is a duplicate and cannot be used") {
									   var bannerName = value.split(':');
										var msg = bannerName[1];
										$("#bannername").attr('placeholder', msg);
										$("#bannername").addClass("error_text");
										$("#bannername").live('input', function() {
											$(this).removeClass("error_text");
										});
									} */
								});
							  }	
							});	
						
						} else {
						
							var postvalue = self.generateTermsandOptionsvalue(text);
							self.bannerActionBody.bannerId = "";
							self.bannerActionBody.bannerName = bannername;
							self.bannerActionBody.bannerType = position;
							self.bannerActionBody.bannerPriority = proirity;
							self.bannerActionBody.startDate = startdate;
							self.bannerActionBody.endDate = enddate;
							self.bannerActionBody.contexts = postvalue;
							self.bannerActionBody.templates = templatevalue;
							self.bannerActionBody.bannerTemplate = bannertemplate;
							self.bannerActionBody.documentId = "";
							self.getAction(self.bannerActionBody,"create", "", function(response){
							if(response.generalPurposeMessage!=null){
								$.each(response.generalPurposeMessage, function(index,value) {
									if(value == "bannerName:May not be empty") {
									    var bannerName = value.split(':');
										var msg = bannerName[1];
										$("#bannername").attr('placeholder', msg);
										$("#bannername").addClass("error_text");
										$("#bannername").live('input', function() {
											$(this).removeClass("error_text");
										});
									}
									
									if(value == "endDate:May not be empty") {
									    var endDate = value.split(':');
										var msg = endDate[1];
										$("#endDate").attr('placeholder', msg);
										$("#endDate").addClass("error_text");
										$("#endDate").live('input', function() {
											$(this).removeClass("error_text");
										});
									}
									
									/* if(value == "bannerName:BannerName is a duplicate and cannot be used") {
										var bannerName = value.split(':');
										var msg = bannerName[1];
										$("#bannername").attr('placeholder', msg);
										$("#bannername").addClass("error_text");
										$("#bannername").live('input', function() {
											$(this).removeClass("error_text");
										});
									} */
								});
							  }	
							});	

						} 
						
					} else {
					
						if(bannertemplate == "HTML_ONLY") {
						
							var postvalue = self.generateTermsandOptionsvalue(text);
							var bannerId = Number(self.updateData.attr("id"));
							self.bannerActionBody.bannerId = bannerId;
							self.bannerActionBody.bannerName = bannername;
							self.bannerActionBody.bannerPriority = proirity;
							self.bannerActionBody.bannerTemplate = bannertemplate;
							self.bannerActionBody.bannerType = position;
							self.bannerActionBody.startDate = startdate;
							self.bannerActionBody.endDate = enddate;
							self.bannerActionBody.contexts = postvalue;
							self.bannerActionBody.documentId = assetId;
							self.getAction(self.bannerActionBody,"update", "", function(response){
								if(response.generalPurposeMessage!=null){	
								$.each(response.generalPurposeMessage, function(index,value) {
									if(value == "bannerName:May not be empty") {
									    var bannerName = value.split(':');
										var msg = bannerName[1];
										$("#bannername").attr('placeholder', msg);
										$("#bannername").addClass("error_text");
										$("#bannername").live('input', function() {
											$(this).removeClass("error_text");
										});
									}
									
									if(value == "endDate:May not be empty") {
									    var endDate = value.split(':');
										var msg = endDate[1];
										$("#endDate").attr('placeholder', msg);
										$("#endDate").addClass("error_text");
										$("#endDate").live('input', function() {
											$(this).removeClass("error_text");
										});
									}
									
									/* if(value == "bannerName:BannerName is a duplicate and cannot be used") {
										var bannerName = value.split(':');
										var msg = bannerName[1];
										$("#bannername").attr('placeholder', msg);
										$("#bannername").addClass("error_text");
										$("#bannername").live('input', function() {
											$(this).removeClass("error_text");
										});
									} */
								});
							  }	
							});	
							
						} else {
							
							var postvalue = self.generateTermsandOptionsvalue(text);
							var bannerId = Number(self.updateData.attr("id"));
							self.bannerActionBody.bannerId = bannerId;
							self.bannerActionBody.bannerName = bannername;
							self.bannerActionBody.bannerPriority = proirity;
							self.bannerActionBody.bannerTemplate = bannertemplate;
							self.bannerActionBody.bannerType = position;
							self.bannerActionBody.startDate = startdate;
							self.bannerActionBody.endDate = enddate;
							self.bannerActionBody.contexts = postvalue;
							self.bannerActionBody.templates = templatevalue;
							self.getAction(self.bannerActionBody,"update", "", function(response){
							if(response.generalPurposeMessage!=null){	
								$.each(response.generalPurposeMessage, function(index,value) {
									if(value == "bannerName:May not be empty") {
									   var bannerName = value.split(':');
										var msg = bannerName[1];
										$("#bannername").attr('placeholder', msg);
										$("#bannername").addClass("error_text");
										$("#bannername").live('input', function() {
											$(this).removeClass("error_text");
										});
									}
									if(value == "endDate:May not be empty") {
									   var endDate = value.split(':');
										var msg = endDate[1];
										$("#endDate").attr('placeholder', msg);
										$("#endDate").addClass("error_text");
										$("#endDate").live('input', function() {
											$(this).removeClass("error_text");
										});
									}
									/* if(value == "bannerName:BannerName is a duplicate and cannot be used") {
									   var bannerName = value.split(':');
										var msg = bannerName[1];
										$("#bannername").attr('placeholder', msg);
										$("#bannername").addClass("error_text");
										$("#bannername").live('input', function() {
											$(this).removeClass("error_text");
										});
									} */
								});
							  }		
							});
						}
					}
				}		
            });
			
			/***
			 * Validate the mandatory fields
			 *
			 */
			 function validate() {
				
				/* var bannername = $("#bannername").val();
                var proirity = $("#priority").val();
                var position = $("#position").val();
                var searchprofile = $("#searchprofile").val();
                var bannertemplate = $("#bannerheadselect").val(); */
                var startdate = $("#startDate").val();
                var enddate = $("#endDate").val();
                /* var assetId = $("#assetId").val();
                var text = $("#create").text();
                var templatevalue = self.generateTemplate(bannertemplate); */
				
				/* $.each($("#TextBoxes").children(), function(index,value) {
					$.each($(value).children().eq(1).children().eq(0).children(), function(index, value) {
						if(index == 0 ) {
							$.each($(value).children().eq(1).children().eq(0).children().eq(0), function(index, value){
								if($(value).children().eq(0).text() == "") {
									self.hasError = true;
									$(value).siblings('.addterms').focus();
									$(value).siblings('.addterms').addClass("error_text");
									$(value).siblings('.addterms').attr('placeholder', 'May not be empty');
									$(value).siblings('.addterms').bind('keypress', function() {
										$(this).removeClass("error_text");
									});
								}
							});
						}
					});
					
					
					
					$.each($(value).children().siblings('.facetContent').children(), function(index, value) {
						var facet = $(value).children().eq(5).val();
						if(facet == "") {
							self.hasError = true;
							$(value).children().eq(5).focus();
							$(value).children().eq(5).addClass("error_text");
							$(value).children().eq(5).attr('placeholder','Facet Attribute value may not be empty');
							$(value).children().eq(5).bind('keypress', function() {
								$(this).removeClass("error_text");
							});
						}
					});		
					
				}); */
				
				//Fixes users being able to submit white space as a valid entry 
                /* if( bannername.replace(/^\s+|\s+$/g,'') == "") {
                    $("#bannername").focus();
                    $("#bannername").attr('placeholder','Please enter banner name');
                    $("#bannername").addClass("error_text");
                    $("#bannername").live('input', function() {
                        $(this).removeClass("error_text");
                    });
					self.hasError = true;
                } else if(startdate == "Start Date" || startdate == "") {
					$('.startdate-error').text('Enter Start Date');
					self.hasError = true;
                } else if(enddate == "End Date" || enddate == "") {
					$('.enddate-error').text('Enter End Date');
					self.hasError = true;
				} else if($("#searchprofile option:selected").length == 0) {
                    $("#searchprofile").focus();
                    $(".searchprofile-error").html('search profile must be selected');
                    self.hasError = true;
                } else if($("#simplehtmlbanner").css('display') == "block") {
					if(assetId == "") {
						$("#assetId").focus();
						$("#assetId").attr('placeholder', 'AssetId cannot be empty');
						$("#assetId").addClass("error_text");
						$("#assetId").live('input', function() {
							$(this).removeClass("error_text");
						});
						self.hasError = true;
					}
					
				} else if($("#oneheadbanner").css('display') == "block") {
					if($("#header").val() == "") {
						$("#header").focus();
						$("#header").attr('placeholder', 'Header cannot be empty');
						$("#header").addClass("error_text");
						$("#header").live('input', function() {
							$(this).removeClass("error_text");
						});
					    self.hasError = true;
					} else if($("#terms_eachmainheader0").text() == "") {
						$("#header").removeAttr('placeholder');
						$("#addterms_oneheader1").focus();
						$("#addterms_oneheader1").attr('placeholder', 'SKU Terms cannot be empty');
						$("#addterms_oneheader1").addClass("error_text");
						$("#addterms_oneheader1").bind('keypress', function() {
							$(this).removeClass("error_text");
						});
						self.hasError = true;
					} else if($("#terms_eachmainheader1").text() == "") {
						$("#addterms_oneheader1").removeAttr('placeholder');
						$("#addterms_oneheader2").focus();
						$("#addterms_oneheader2").attr('placeholder', 'SKU Terms cannot be empty');
						$("#addterms_oneheader2").addClass("error_text");
						$("#addterms_oneheader2").bind('keypress', function() {
							$(this).removeClass("error_text");
						});
						self.hasError = true;
					} else if($("#terms_eachmainheader2").text() == "") {
						$("#addterms_oneheader2").removeAttr('placeholder');
						$("#addterms_oneheader3").focus();
						$("#addterms_oneheader3").attr('placeholder', 'SKU Terms cannot be empty');
						$("#addterms_oneheader3").addClass("error_text");
						$("#addterms_oneheader3").bind('keypress', function() {
							$(this).removeClass("error_text");
						});
						self.hasError = true;
					} 					
				} else if($("#threeheadbanner").css('display') == "block") {
					if($("#header0").val() == "") {
						$("#header0").focus();
						$("#header0").attr('placeholder', 'Header cannot be empty');
						$("#header0").addClass("error_text");
						$("#header0").live('input', function() {
							$(this).removeClass("error_text");
						});
					    self.hasError = true;
					} else if($("#eachmainheader0").text() == "") {
						$("#header0").attr('placeholder', '');
						$("#addterms_threeheader1").focus();
						$("#addterms_threeheader1").attr('placeholder', 'SKU Terms cannot be empty');
						$("#addterms_threeheader1").addClass("error_text");
						$("#addterms_threeheader1").bind('keypress', function() {
							$(this).removeClass("error_text");
						});
						self.hasError = true;
					} else if($("#header1").val() == "") {
						$("#addterms_threeheader1").attr('placeholder', '');
						$("#header1").focus();
						$("#header1").attr('placeholder', 'Header cannot be empty');
						$("#header1").addClass("error_text");
						$("#header1").live('input', function() {
							$(this).removeClass("error_text");
						});
					    self.hasError = true;
					} else if($("#eachmainheader1").text() == "") {
						$("#header1").attr('placeholder', '');
						$("#addterms_threeheader2").focus();
						$("#addterms_threeheader2").attr('placeholder', 'SKU Terms cannot be empty');
						$("#addterms_threeheader2").addClass("error_text");
						$("#addterms_threeheader2").bind('keypress', function() {
							$(this).removeClass("error_text");
						});
						self.hasError = true;
					} else if($("#header2").val() == "") {
						$("#addterms_threeheader2").attr('placeholder', '');
						$("#header2").focus();
						$("#header2").attr('placeholder', 'Header cannot be empty');
						$("#header2").addClass("error_text");
						$("#header2").live('input', function() {
							$(this).removeClass("error_text");
						});
					    self.hasError = true;
					} else if($("#eachmainheader2").text() == ""){
						$("#header2").attr('placeholder', '');
						$("#addterms_threeheader3").focus();
						$("#addterms_threeheader3").attr('placeholder', 'SKU Terms cannot be empty');
						$("#addterms_threeheader3").addClass("error_text");
						$("#addterms_threeheader3").bind('keypress', function() {
							$(this).removeClass("error_text");
						});
						self.hasError = true;
					} 
				} else {
					var errorJson = self.validateStartEndDate(startdate, enddate);
                    if (errorJson.hasError) {
                        if (errorJson.errorIn == "startDate") {
                            $('.startdate-error').text(errorJson.errorMsg);
                        } else if (errorJson.errorIn == "endDate") {
                            $('.enddate-error').text(errorJson.errorMsg);
                        }
						self.hasError = true;
                    }
				} */ 
				/*
				var errorJson = self.validateStartEndDate(startdate, enddate);
				if (errorJson.hasError) {
					if (errorJson.errorIn == "startDate") {
						$('.startdate-error').text(errorJson.errorMsg);
					} else if (errorJson.errorIn == "endDate") {
						$('.enddate-error').text(errorJson.errorMsg);
					}
					self.hasError = true;
				} else {
					self.hasError = false;
				}
				
				return self.hasError;*/
			}
        }
    });

    return Clazz.com.components.banners.js.Banners;
});