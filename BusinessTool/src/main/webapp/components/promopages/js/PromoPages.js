define(["framework/WidgetWithTemplate", "promopages/listener/PromoPagesListener","header/Header", "navigation/Navigation", "footer/Footer", "lib/handlebars-1.0.0.beta.6"] , function() {

    Clazz.createPackage("com.components.promopages.js");

    Clazz.com.components.promopages.js.PromoPages = Clazz.extend(Clazz.WidgetWithTemplate, {
        promoPageEvent : null,
        promoPageHeader : null,
        localConfig: null,
        updateData: null,
        termsList: [],
        promoPageListener: null,
        navigation : null,
        pagination : null,
        paginationContainer :  commonVariables.paginationPlaceholder,
        promoActionBody : {},
        editValue : null,
        sortColumn : null,
        header: {
            contentType: null,
            requestMethod: null,
            dataType: null,
            requestPostBody: null,
            webserviceurl: null
        },
        
        promoRequestBody: {},
        searchdata: {},
        // template URL, used to indicate where to get the template
        templateUrl: "../components/promopages/template/promoPages.tmp",
        configUrl: "../../componens/promopages/config/config.json",
        name : commonVariables.promo,
        headerContainer : commonVariables.headerPlaceholder,
        contentContainer : commonVariables.contentPlaceholder,
        footerContainer : commonVariables.footerPlaceholder,
        basewidget :  commonVariables.basePlaceholder,
        mode : "Add",
        currentValue: null,
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
            self.promoRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;
            self.promoPageListener = new Clazz.com.components.promopages.js.listener.PromoPagesListener(globalConfig);
            self.navigation = new Clazz.com.components.navigation.js.Navigation();
            self.registerHandlebars();
            self.registerEvents(self.promoPageListener);
            self.defaultSettings();
        },

        /***
         * Called whenever the page is invoked
         * Sets the detaSettings
         * @whereToRender: the place to render the component
         * @renderFunction: the function which renders the component
         */
        preRender: function(whereToRender, renderFunction) {
            var self = this;
            self.defaultSettings();
            self.sortColumn = null;
            self.promoPageListener.promoPage(self.promoPageListener.getRequestHeader(self.promoRequestBody), function(response) {
                self.pageRefresh(response);
            });
        },

        /**
         * Called in once when this page is initialized
         */
        loadPage :function() {
            var promoPagesListener = new Clazz.com.components.promopages.js.listener.PromoPagesListener();
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
         * Add terms to the keywords
         */
      
        setTermsFunction : function() {

            var self = this;
            $(".greenfont").unbind('click');
            $(".greenfont").click(function(e) {
            var tagCloudRequestBody = {};
            tagCloudRequestBody.error = $(".addterms-error");
            tagCloudRequestBody.userInput = $(".addterms");
            tagCloudRequestBody.submitButton = $(".greenfont");
            tagCloudRequestBody.self = self;
            tagCloudRequestBody.removable = "true";
            tagCloudRequestBody.numbered = "true";
            tagCloudRequestBody.sortable = "false";
            tagCloudRequestBody.term =$('.addterms').val();
            tagCloudRequestBody.termsList = self.termsList;
            tagCloudRequestBody.enteredTerms = $('.terms_eachmain');
            tagCloudRequestBody.buttonImage = $("img[name='closebtn']");
            self.submitTerms(tagCloudRequestBody);
            function sortTerm(element, parent) {
                $( parent ).sortable({
                      stop: function (event, ui) {
                        self.renderCount();
                        self.termsList = Array();
                            $('.terms_eachmain').children().each(function( index ) {
                                var termSkuVal = $(this).remove('.number').text();
                                var indexToBeRemoved = index + 1;
                                termSkuVal = termSkuVal.replace("#"+indexToBeRemoved,"");
                                self.termsList[index] = termSkuVal;
                            })
                        
                 },
                    placeholder: "ui-state-highlight",
                    helper: 'original'
                    });
                };
            $(".addterms").attr('placeholder', 'Type sku & press enter to add');
            sortTerm('.terms_eachtab','.terms_eachmain')

        });

        $('.addterms').bind('keyup', function(e) {
        if ( e.keyCode === 13 ) { // 13 is enter key
            // Execute code here.
            $(".greenfont").click();
            }
        });

            $(".addterms-error").html('');
            $(".addterms").removeClass("error_text");
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
            self.headerStyle();
            self.disableActionStyle();
            this.setSession("pageName", this.name);         
            window.onresize = function(event) {
                self.sizeModal();
                };

        },

        /***
         * Called once to register all the events 
         *
         * @promoPageListener: promoPageListener methods getting registered
         */
        registerEvents : function (promoPageListener) {
            var self = this;
            self.onFocusOutEvent = new signals.Signal();
            self.onFocusOutEvent.add(promoPageListener.checkNameExists, promoPageListener);
        },

        /***
         * Registering handle bars to template for actions
         *
         */
        registerHandlebars : function () {
            Handlebars.registerHelper('actionPromo', function(data) {
                var actionHtml, edit = '<img name="EditDisable" title="Edit" class="block_sym" src="../themes/default/images/bestbuy/edit_disabled.png"/>';
                var approve = '<img name="approvalDisable" title="Approve" class="block_sym" src="../themes/default/images/bestbuy/approve_disabled.png" />'; 
                var reject = '<img name="rejectDisable" title="Reject" class="block_sym" src="../themes/default/images/bestbuy/reject_disabled.png" />';
                var deleted = '<img name="deleteDisable" title="Delete" class="block_sym" src="../themes/default/images/bestbuy/delete_disabled.png" />';           
                
                $.each(data, function(index, value) {
                    if(value.value == commonVariables.edit) {
                        edit = '<img name="editIcon" title="Edit" class="hand_sym" src="../themes/default/images/bestbuy/edit_icon.png"/>';
                    } else if(value.value == commonVariables.approve) {
                        approve = '<img name="approval" title="Approve" class="hand_sym" src="../themes/default/images/bestbuy/approve_blue.png" />';
                    } else if(value.value == commonVariables.reject) {
                        reject = '<img name="reject" title="Reject" class="hand_sym" src="../themes/default/images/bestbuy/reject_blue.png" />';
                    } else if(value.value == commonVariables.deleted) {
                        deleted = '<img name="delete" title="Delete" class="hand_sym" src="../themes/default/images/bestbuy/delete_blue.png" />';   
                    }
                });

                actionHtml = approve +' <a href="#Add_promo" data-toggle="modal">'+ edit+'</a> '+ reject +'<a data-toggle="modal" href="#deletemsg">'+deleted+'</a>';
                return actionHtml;
            });
        },

        /**
         * Triggers the search call and returns the data
         * @searchCriteria - criteria to be searched
         */     
        search : function(searchCriteria) {
            var self = this;
            var requestBody = self.promoRequestBody;
            if (searchCriteria != null) {
                self.defaultSettings();
                requestBody.searchColumnValues = [];
                requestBody.searchColumnValues.push(searchCriteria);
                self.promoRequestBody = requestBody;                
                self.promoPageListener.promoPage(self.promoPageListener.getRequestHeader(requestBody), function(response) {
                    self.pageRefresh(response);
                });
            }
        },
        
        columnSearch : function() {
            var self = this;
            var renderFunction = $.proxy(self.renderTemplate, self);
            var requestBody = self.promoRequestBody;
            var searchCriteriaArray = [];
            self.searchdata.promoName = $("#promoNamSearch").val();
            self.searchdata.skuIds = $("#skuIdSearch").val();
            self.searchdata.modifiedBy = $("#lastModifiedBySearch").val();
            self.searchdata.status = $("#statusSearch").val();
            
            if($("#promoNamSearch").val() != "") {
                var searchCriteria = {};
                searchCriteria.key = "promoName";
                searchCriteria.value = $("#promoNamSearch").val();
                searchCriteriaArray.push(searchCriteria);
            } 
            
            if($("#skuIdSearch").val() != "") {
                var searchCriteria = {};
                searchCriteria.key = "skuIds";
                searchCriteria.value = $("#skuIdSearch").val();
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
                self.promoRequestBody = requestBody;                
                self.promoPageListener.promoPage(self.promoPageListener.getRequestHeader(requestBody), function(response) {
                    renderFunction(response, self.contentContainer);
                });
            } else {
                var self = this;
                self.defaultSettings();
                self.sortColumn = null;
                self.promoPageListener.promoPage(self.promoPageListener.getRequestHeader(self.promoRequestBody), function(response) {
                    self.pageRefresh(response);
                });
            }
        },
        
        fillSearchData : function() {
            var self = this;
            $("#promoNamSearch").val(self.searchdata.promoName);
            $("#skuIdSearch").val(self.searchdata.skuIds);
            $("#lastModifiedBySearch").val(self.searchdata.modifiedBy);
            $("#statusSearch").val(self.searchdata.status);
        },
        
        /**
         * Triggers the pagination actions 
         * @searchCriteria - criteria to be searched
         * @pageIndex - index of the page 
         * 
         * Change done at SPT-4212 SPT-3791 Pagination bar should have a dropdown that allows the user
         *  to select more rows. Options: 50, 100, 250, 500, All.
         */
        doPagination : function(searchCriteria,pageIndex,rowsPerPage) {
            var self = this;
            var listener = self.promoPageListener;
            var requestBody = this.promoRequestBody;
            requestBody.pageIndex = pageIndex;
            requestBody.rowsPerPage = rowsPerPage;
            self.promoRequestBody = requestBody;

            self.promoPageListener.promoPage(self.promoPageListener.getRequestHeader(requestBody), function(response) {
                self.pageRefresh(response);
            });
        },

        /**
         * Sets the default values for the Server call parameters
         */
       
        defaultSettings : function() {
            var self = this;
            self.promoRequestBody.pageIndex = "1";
            self.promoRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;    
            self.promoRequestBody.sortColumn = commonVariables.requestBody.sortColumn;
            self.promoRequestBody.sortOrder = commonVariables.requestBody.sortOrder;
            self.promoRequestBody.searchOper = commonVariables.requestBody.searchOper;
            self.promoRequestBody.searchColumnValues = commonVariables.requestBody.searchColumnValues;
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
            self.promoPageListener.promoAction(self.promoPageListener.getActionHeader(actionBody, action, id), function(response) {
                if(action != "editData" && response.status != "ERROR") {
                    self.pageRefresh(response);
                } else {
                    callback(response);
                }
            });
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
                if (self.promoRequestBody.sortOrder == "desc" ) {
                    $('.tabheader').each(function(){
                        if($(this).parent().attr('tabname') == self.sortColumn) {
                            $("#sortcolor").children().children().eq(0).attr('src','../themes/default/images/bestbuy/small.png');
                            $(this).parent().addClass('sortclr');
                            $(this).find('img').attr('src', '../themes/default/images/bestbuy/asc.png');
                        } 
                    });
                } else if (self.promoRequestBody.sortOrder == "asc") {
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
         * Called whenever the add button is clicked 
         *
         */
        clearElementValues : function () {
            var self = this;
            self.mode = "Add";
            self.currentValue = null;
            $("#promoname").val('').removeClass("error_text").attr("placeholder",'Enter the Promo Name');
            $(".addterms").val('').removeClass("error_text").attr("placeholder",'Type sku & press enter to add');
            $(".terms_eachmain").empty();
            $(".addterms-error").html('');
            self.termsList = [];               
            $("#startDate").val('').attr("placeholder",'Click to select date.');
            $("#endDate").val('').attr("placeholder",'Click to select date.');
            $("#promoLabel").html('Create Promo');
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
                self.escapePopup($("#Add_promo"));
                setTimeout(function() {
                    $('#promoname').focus();
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
                var deletePromo = self.updateData.children().eq(1).text();
                var _self = this;
                self.navigation = new Clazz.com.components.navigation.js.Navigation();
                self.navigation.alertBox("Delete", "Are you sure you want to delete <span class='delete_color'>"+ deletePromo + " </span>?", function(callback){
                    if(callback) {
                        self.promoActionBody = {};
                        self.getAction(self.promoActionBody,"delete", $(_self).closest('tr').attr("id"));                               
                    }   
                });
            });
            
            $("img[name='approval']").unbind('click');
            $("img[name='approval']").click(function(e) {
                self.promoActionBody = {};
                self.getAction(self.promoActionBody,"approve", $(this).closest('tr').attr("id"));
            });
            
            $("img[name='reject']").unbind('click');
            $("img[name='reject']").click(function(e) {
                self.promoActionBody = {};
                self.getAction(self.promoActionBody,"reject", $(this).closest('tr').attr("id"));
            });
            
            $("img[name='editIcon']").unbind('click');
            $("img[name='editIcon']").click(function(e) {
                $(".addterms-error").html('');
                $("#promoLabel").html('Edit Promo');
                $("#create").html('UPDATE');
                $("#create").attr('title','Update');
                self.updateData = $(this).closest("tr"); 
                var promoId = self.updateData.attr('id');
                self.currentValue = self.updateData.find("#promoNameTerm").html();

                self.promoActionBody = {};
                self.getAction(self.promoActionBody,"editData", promoId, function(callback) {
                    self.editValue = callback.data;
                    $("#promoname").val(self.editValue.promoName).removeClass("error_text").removeAttr("placeholder");
                    $(".terms_eachmain").empty();
                    $(".addterms").removeClass("error_text").attr("placeholder", "Type sku & press enter to add");
                    self.termsList = self.editValue.skuIds;
                    var count = 0;
                    $.each(self.termsList, function(index, value) {
                        count++;
                        $('.terms_eachmain').append('<div class="terms_eachtab"><span class="number" style=" color: grey; vertical-align: super; font-size: 10px;">#'+count+'</span>' + value + '<img class="closebtn" name="closebtn" src="../themes/default/images/bestbuy/close_icon.png"></div>');
                    });
                    
                    $("img[name='closebtn']").unbind('click');
                    $("img[name='closebtn']").click(function(e) {
                        //var termlistArray = String(self.termsList).split(",");
                        var termValToReplace = $(this).parent().text().replace($(this).parent().find('span').text(),"");

                        //console.log( termlistArray+"    "+termValToReplace+"   "+self.termsList.indexOf(termValToReplace));
                        self.termsList.splice(self.termsList.indexOf(termValToReplace), 1);
                        $(this).parent().remove();
                    });
                    function sortTerm(element, parent) {
                        $( parent ).sortable({
                              stop: function (event, ui) {
                                self.renderCount();
                                self.termsList = Array();
                                $('.terms_eachmain').children().each(function( index ) {
                                    var termSkuVal = $(this).remove('.number').text();
                                    var indexToBeRemoved = index + 1;
                                    termSkuVal = termSkuVal.replace("#"+indexToBeRemoved,"");
                                    self.termsList[index] = termSkuVal;
                                })
                         },
                            placeholder: "ui-state-highlight",
                            helper: 'original'
                            });
                        };    
                        sortTerm('.terms_eachtab','.terms_eachmain')                
                    $("#startDate").val(self.editValue.startDate);
                    $("#endDate").val(self.editValue.endDate);

                
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
            /* $("#myTable").tablesorter({ 
                // pass the headers argument and assing a object 
                headers: { 
                    7: { 
                        // disable it by setting the property sorter to false 
                        sorter: false 
                    }                   
                } 
            });  */
            $('.tabheader').die("click");
            $('.tabheader').live("click", function() {
                var sortOrder = "";
                self.sortColumn = $(this).parent().attr('tabname');
                if (self.promoRequestBody.sortOrder == "desc") {
                    sortOrder = "asc";
                } else if (self.promoRequestBody.sortOrder == "asc") {
                    sortOrder = "desc";
                }
                self.promoRequestBody.pageIndex = commonVariables.requestBody.pageIndex;
                self.promoRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;    
                self.promoRequestBody.sortColumn = self.sortColumn;
                self.promoRequestBody.sortOrder = sortOrder;
                self.promoRequestBody.searchOper = commonVariables.requestBody.searchOper;
                self.promoRequestBody.searchColumnValues = commonVariables.requestBody.searchColumnValues;
                self.promoPageListener.promoPage(self.promoPageListener.getRequestHeader(self.promoRequestBody), function(response) {
                    self.pageRefresh(response);
                });
            });
            
            $('input[name="search"]').keypress(function(e){
                if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                    code= (e.keyCode ? e.keyCode : e.which);
                    if (code == 13) {
                        self.columnSearch();
                    }
                }
            });
            
            $("#promoname").focusout(function() {
                var promoName = $("#promoname");
                if (promoName.val().replace(/^\s+|\s+$/g,'') != '') {
                    self.onFocusOutEvent.dispatch(promoName.val(), self.mode, self.currentValue, function(response) {
                        if (response.status == "ERROR") {
                            promoName.val('');
                            var res = response.generalPurposeMessage[0].split(':');
                            promoName.attr('placeholder', res[1]);
                            promoName.addClass("error_text");
                            promoName.live('input', function() {
                                $(this).removeClass("error_text").removeAttr('placeholder');
                            });
                        }
                    });
                } else {
                    $("#promoname").val('');
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
                    self.escapePopup($("#Add_promo"));

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
                    self.escapePopup($("#Add_promo"));

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
  /*         
            $(".addterms").keypress(function (e) {
                if (e.which != 8 && e.which != 0 && e.which != 44 && (e.which < 48 || e.which > 57)) {
                    return false;
                }
            }); */
            
            $(".ui-datepicker-trigger, #startDate, #endDate").click(function() {
                $("#endDate").removeClass("error_text").removeAttr("placeholder");
                $('.startdate-error').text('');
                $('.enddate-error').text('');
            });

            $("#create").click(function(e) {
                var text = $("#create").text();
                var promoName = $("#promoname").val();
                var startDate = $("#startDate").val();
                var endDate = $("#endDate").val();
                //if (!validate()) {
                    if(text == "CREATE") {
                        //console.log(self.termsList);
                        self.promoActionBody.promoName = promoName;
                        if(self.termsList.length > 0){self.promoActionBody.skuIds = self.termsList;}else{self.promoActionBody.skuIds = null;}
                        self.promoActionBody.startDate = startDate;
                        self.promoActionBody.endDate = endDate;
                        self.getAction(self.promoActionBody,"create", "", function(response){
                            $.each(response.generalPurposeMessage, function(index,value){
                                if(value == "promoName:May not be empty") {
                                    var promoName = value.split(':');
                                    var msg = promoName[1];
                                    $("#promoname").attr('placeholder', msg);
                                    $("#promoname").addClass("error_text");
                                    $("#promoname").live('input', function() {
                                        $(this).removeClass("error_text").removeAttr('placeholder');
                                    });
                                }   
                                if(value == "skuIds:May not be empty") {
                                    var skuIds = value.split(':');
                                    var msg = skuIds[1];
                                    if( $.trim($(".addterms").val()) != "" ){
                                        $(".addterms-error").html('Terms should be added');
                                    } else {
                                        $(".addterms").val('');
                                    }
                                    $(".addterms").attr('placeholder', msg);
                                    $(".addterms").addClass("error_text");
                                    $(".addterms").live('input', function() {
                                        $(this).removeClass("error_text").removeAttr('placeholder');
                                    });
                                }
                                if(value == "endDate:May not be empty") {
                                    var endDate = value.split(':');
                                    var msg = endDate[1];
                                    $("#endDate").attr('placeholder', msg);
                                    $("#endDate").addClass("error_text");
                                    $("#endDate").live('input', function() {
                                        $(this).removeClass("error_text").removeAttr('placeholder');
                                    });
                                }
                            });
                        });
                        self.actionEvent();
                    } else {
                        var promoId = self.updateData.attr('id');
                        self.promoActionBody.promoName = promoName;
                        self.promoActionBody.promoId = promoId;
                        self.promoActionBody.statusId = self.editValue.statusId;
                        self.promoActionBody.status =  self.editValue.status;
                        self.promoActionBody.modifiedBy = self.editValue.modifiedBy;
                        self.promoActionBody.modifiedDate = self.editValue.modifiedDate;
                        self.promoActionBody.createdBy = self.editValue.createdBy;
                        self.promoActionBody.createdDate = self.editValue.createdDate;
                        if(self.termsList.length > 0){self.promoActionBody.skuIds = self.termsList;}else{self.promoActionBody.skuIds = null;}
                        self.promoActionBody.startDate = startDate;
                        self.promoActionBody.endDate = endDate;
                        self.getAction(self.promoActionBody, "update", "", function(response){
                            $.each(response.generalPurposeMessage, function(index,value){
                                if(value == "promoName:May not be empty") {
                                    var promoName = value.split(':');
                                    var msg = promoName[1];
                                    $("#promoname").attr('placeholder', msg);
                                    $("#promoname").addClass("error_text");
                                    $("#promoname").live('input', function() {
                                        $(this).removeClass("error_text");
                                    });
                                }   
                                if(value == "skuIds:May not be empty") {
                                    var skuIds = value.split(':');
                                    var msg = skuIds[1];
                                    if( $.trim($(".addterms").val()) != "" ){
                                        $(".addterms-error").html('Terms should be added');
                                    } else {
                                        $(".addterms").val('');
                                    }
                                    $(".addterms").attr('placeholder', msg);
                                    $(".addterms").addClass("error_text");
                                    $(".addterms").live('input', function() {
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
                            });
                        });
                    }
                //}
            });
            
            /***
             * Validate the mandatory fields
             *
             */
            /* function validate() {
                var text = $("#create").text();
                var promoName = $("#promoname").val();
                var startDate = $("#startDate").val();
                var endDate = $("#endDate").val();
                var hasError = false;
                
                //Fixes users being able to submit white space as a valid entry 
                if(promoName.replace(/^\s+|\s+$/g,'') === "") {                  
                    $("#promoname").focus();    
                    $("#promoname").attr('placeholder', 'May not be empty');
                    $("#promoname").addClass("error_text");
                    $("#promoname").live('input', function() {
                        $(this).removeClass("error_text");
                    });
                    hasError = true;                    
                } else if($(".terms_eachmain").text() == "") {                                  
                    $(".addterms").focus();
                    if( $.trim($(".addterms").val()) != "" ){
                        $(".addterms-error").html('Terms should be added');
                    } else {
                        $(".addterms").val('');
                    }
                    $(".addterms").attr('placeholder', 'May not be empty');
                    $(".addterms").addClass("error_text");
                    $(".addterms").live('input', function() {
                        $(this).removeClass("error_text");
                    });
                    hasError = true;
                } else if(startDate === "Start Date" || startDate == ""){
                    $('.startdate-error').text('Enter Start Date');
                    hasError = true;
                } else if(endDate === "End Date" || endDate === ""){                  
                    $('.enddate-error').text('Enter End Date');
                    hasError = true;
                } else {
                    var errorJson = self.validateStartEndDate(startDate, endDate);
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
            
            self.setTermsFunction();
            self.actionEvent();
        }
    });

    return Clazz.com.components.promopages.js.PromoPages;
});