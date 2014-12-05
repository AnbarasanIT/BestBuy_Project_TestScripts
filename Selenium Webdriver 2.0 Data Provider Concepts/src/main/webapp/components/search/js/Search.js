define(["framework/WidgetWithTemplate", "search/listener/SearchListener", "search/searchPage"] , function() {

    Clazz.createPackage("com.components.search.js");

    Clazz.com.components.search.js.Search = Clazz.extend(Clazz.WidgetWithTemplate, {
        localConfig: null,
        searchListener: null,
        navigation : null,
        txtSearch :  null,
        pagination : null,
        paginationContainer :  commonVariables.paginationPlaceholder,
        header: {
            contentType: null,
            requestMethod: null,
            dataType: null,
            requestPostBody: null,
            webserviceurl: null
        },
        searchRequestBody: {},
		
        // template URL, used to indicate where to get the template
        templateUrl: "../components/search/template/search.tmp",
        configUrl: "../../componens/search/config/config.json",
        name : commonVariables.search,
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
            self.searchListener = new Clazz.com.components.search.js.listener.SearchListener();
            self.registerHandlebars();
			self.defaultSettings();
        },
        
        registerHandlebars : function () {
            /*Handlebars.registerHelper('actionUserAdmin', function(data) {
                var actionHtml, edit = '<img name="EditDisable" title="Edit" class="block_sym" src="../themes/default/images/bestbuy/edit_disabled.png"/>';
                var deleted = '<img name="deleteDisable" title="Delete" class="block_sym" src="../themes/default/images/bestbuy/delete_disabled.png" />';
                $.each(data, function(index, value) {
                    if(value.value == "Edit"){
                        edit = '<img name="editIcon" title="Edit" class="hand_sym" src="../themes/default/images/bestbuy/edit_icon.png"/>';
                    } else if(value.value == "Delete") {
                        deleted = '<img name="delete" title="Delete" class="hand_sym" src="../themes/default/images/bestbuy/delete_blue.png" />';   
                    }
                });             
                actionHtml = '<td class="center_td"><a href="#Add_useradmin" data-toggle="modal">'+ edit +'</a></td><td class="center_td">'+ deleted +'</td>';
                return actionHtml;
            });*/
        },

        /**
         * Called in once when this page is initialized
         */
        loadPage :function() {
            var searchListener = new Clazz.com.components.search.js.listener.SearchListener();
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
         * DefaultSettings for the AJAX requests
         */
        defaultSettings : function() {
            var self = this;
            self.searchRequestBody.pageIndex = commonVariables.requestBody.pageIndex;           
			self.searchRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;    
			self.searchRequestBody.sortColumn = commonVariables.requestBody.sortColumn;
			self.searchRequestBody.sortOrder = commonVariables.requestBody.sortOrder;
			self.searchRequestBody.searchOper = commonVariables.requestBody.searchOper;
			self.searchRequestBody.searchColumnValues = commonVariables.requestBody.searchColumnValues;
        },
        
        /**
         * Called during the page refresh, displays the message, and tomat number of records
         * @response: response from the service
         */
        pageRefresh : function(response) {

            var self = this;
            
        },
		
		search : function(searchCriteria){
			performQuery('search');
		},

        /***
         * Called after the preRender() and bindUI() completes. 
         *
         * @element: Element as the result of the template + data binding
         */
        postRender : function(element) {
            this.setSession("pageName", this.name);          
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
            self.searchListener.searchAction(self.searchListener.getActionHeader(actionBody, action, id), function(response) {
                if(action != "editData") {
                    self.pageRefresh(response);
                } else {
                    callback(response);
                }
            });
        },
        
        /***
         * Bind the action listeners. The bindUI() is called automatically after the render is complete 
         */
        bindUI : function() {
            var self = this;
			self.escapePopup($("#previewDate"));
            /***
             * Date time picker for preview date
             */
			 $("#previewDate").datetimepicker({
				minDate: 0,
				dateFormat: 'mm-dd-yy',
				showOn: "both",
				beforeShow: function() {
					self.showingDatePicker = true;
					setTimeout(function(){
						$('.ui-datepicker').css('z-index', 1050);
					}, 0);
				},
				onClose: function() {
					self.showingDatePicker = false;
					self.escapePopup($("#previewDate"));
				}
			});
			
            /***
             * To hide the preview date based on the selected facing type
             */
            $('#facingType').bind("change", function() {
                var selectedFacing = $(this).val();
                if ("cf" === selectedFacing) {
                    $('.preview_date').hide();
                } else {
                    $('.preview_date').show();
                }
            });

            /***
             * To reset the field values on clicking the clear search btn
             */
            $('#clear_search').bind("click", function() {
                $("#txtSearch").val("");
                $("#urlAndPort").val("");
                $("#previewDate").text("Review Date");
                $("#sortOption option:first").attr("selected", true);
                $("#facingType option:first").attr("selected", true);
                $('.preview_date').hide();
            });
        }
    });

    return Clazz.com.components.search.js.Search;
});