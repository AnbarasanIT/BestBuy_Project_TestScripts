define(["framework/WidgetWithTemplate", "facets/listener/FacetsListener", "navigation/Navigation", "facets/api/FacetsAPI", "lib/jquery.waituntilexists"] , function() {

  Clazz.createPackage("com.components.facets.js");

  Clazz.com.components.facets.js.Facets = Clazz.extend(Clazz.WidgetWithTemplate, {
  
    facetsEvent : null,
    facetsHeader : null,
    localConfig: null,
    updateData: null,
    termsList: null,
    facetsListener: null,
    navigation : null,
    txtSearch :  null,
    pagination : null,
    responseCount : null,
    loadingScreen : null,
    selectedFacetId : null,
    selectedFacetName : null,
    paginationContainer :  window.commonVariables.paginationPlaceholder,
    facetsActionBody: {},
    facetsActionBodyPagnation: {},
    popupPagination : null,
    categoryWrapper : [],
    promoteList : [],
    sortType : null,
    loadingScreen : null,
    sortColumn : null,
    editValue : {},
    depfacetname : null,
    fCheck : 0,
    depFacetIdText : null,
    checkAll : false,
    checkedItem : [], 
    existingCategoryWrapper : [],
    viewModel :null,
    edit : 0,
    header: {
      contentType: null,
      requestMethod: null,
      dataType: null,
      requestPostBody: null,
      webserviceurl: null
    },
    Pages: "",
    displayFacet: "",
    facetsRequestBody: {},
    searchdata: {},
    // template URL, used to indicate where to get the template
    templateUrl: "../components/facets/template/facets.tmp",
    configUrl: "../../componens/facets/config/config.json",
    name : window.commonVariables.facets,
    headerContainer : window.commonVariables.headerPlaceholder,
    contentContainer : window.commonVariables.contentPlaceholder,
    footerContainer : window.commonVariables.footerPlaceholder,
    basewidget :  window.commonVariables.basePlaceholder, 
    mode : "Add",
        currentValue: null,
        //variable to hold the attribute search value
    attributeValueSearch:null,
    //Events, to fire a function
        onFocusOutEvent: null,
    
    initialize : function(globalConfig){
      var self = this;
      self.defaultSettings();
      self.facetsRequestBody.pageIndex = commonVariables.requestBody.pageIndex;
      self.facetsRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;
      self.facetsRequestBody.sortColumn = commonVariables.requestBody.sortColumn;
      self.facetsRequestBody.sortOrder = commonVariables.requestBody.sortOrder;
      self.facetsRequestBody.searchColumnValues = commonVariables.requestBody.searchColumnValues;
      self.facetsRequestBody.searchOper = commonVariables.requestBody.searchOper;
      
      
      self.globalConfig = globalConfig;
      self.facetsListener = new Clazz.com.components.facets.js.listener.FacetsListener();
      self.popupPagination = new Clazz.com.components.pagination.js.Pagination(); 
      self.loadingScreen = new Clazz.com.js.widget.common.Loading();
      self.registerEvents(self.facetsListener);
      Handlebars.registerHelper('actionFacets', function(data) {
        var actionHtml, edit = '<img name="EditDisable" title="Edit" class="block_sym" src="../themes/default/images/bestbuy/edit_disabled.png"/>', approve = '<img name="approvalDisable" title="Approve" class="block_sym" src="../themes/default/images/bestbuy/approve_disabled.png" />', reject = '<img name="rejectDisable" title="Reject" class="block_sym" src="../themes/default/images/bestbuy/reject_disabled.png" />', deleted = '<img name="deleteDisable" title="Delete" class="block_sym" src="../themes/default/images/bestbuy/delete_disabled.png" />';      
        $.each(data, function(index, value){
          if(value.value == "Edit"){
            edit = '<img name="editIcon" title="Edit" class="hand_sym" src="../themes/default/images/bestbuy/edit_icon.png"/>';
          } else if(value.value == "Approve"){
            approve = '<img name="approval" title="Approve" class="hand_sym" src="../themes/default/images/bestbuy/approve_blue.png" />';
          } else if(value.value == "Reject"){
            reject = '<img name="reject" title="Reject" class="hand_sym" src="../themes/default/images/bestbuy/reject_blue.png" />';
          } else if(value.value == "Delete"){
            deleted = '<img name="delete" title="Delete" class="hand_sym" src="../themes/default/images/bestbuy/delete_blue.png" />'; 
          }
        });       
        actionHtml = approve +' <a href="#Add_facets" data-toggle="modal">'+ edit+'</a> '+ reject +'<a data-toggle="modal" href="#deletemsg">'+deleted+'</a>';
        return actionHtml;
      });
      Handlebars.registerHelper('categoryBlock', function(data) {
        $.each(data, function(index, value){
          data = data.replace("%20", " ");
        });
        return data;
      }); 

      
  },
    
    preRender: function(whereToRender, renderFunction){
      var self = this;
      self.defaultSettings();
      self.sortColumn = null;
      self.facetsListener.getFacetsList(self.facetsListener.getRequestHeader(self.facetsRequestBody), function(response) {
        self.pageRefresh(response);
      });
    },
    
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
      self.disableActionStyle();
      this.setSession("pageName",this.name);
      this.setCategoryTreeView('');
      window.onresize = function(event) {
      self.sizeModal();
      };
      Pages = new Backbone.Collection([]);
      displayFacet = ko.observable("Y");
      // use knockback.js to bridge knockout data-binding with backbone models
      // read_only is set to false to make the binding two-way.
      // note the use of the "kb" api for knockback.
       self.viewModel = {
        Pages : kb.collectionObservable(Pages),
        displayFacet : displayFacet
      
      };

/*kb.viewModel(Pages, {
    read_only: false
});*/
      self.viewModel.addPage = function() {
        self.setCategoryTreeView((Pages.length || 0));

        Pages.push({
          applySubCategory: false,
          categoryId: "cat00000",
          categoryPath: "Best%20Buy",
          depFacetId: null,
          displayDepFacet: "N",
          displayContext: "Y",
          depFacetText: "",
          facetAttributeValueId: null
          //facetOrder: 1,
          //valid: "Y"
      })
      
        //use a class, since your ID gets mangled
      $('div').removeClass("selectedPage");
      $('.page').last().addClass("selectedPage");  

      };
      
      self.viewModel.removePage = function(data, item) {
        self.viewModel.Pages.remove(item);
      }
// at this point, knockback has given us a viewModel which is compatible
// with the normal knockout api.  so we can use the "ko" api now.
ko.applyBindings(self.viewModel);
    },
    
    /***
         * Called once to register all the events 
         *
         * @facetsListener: promoPageListener methods getting registered
         */
        registerEvents : function (facetsListener) {
            var self = this;
            self.onFocusOutEvent = new signals.Signal();
            self.onFocusOutEvent.add(facetsListener.checkNameExists, facetsListener);
        },
    
    setSession : function(key,value) {
      if(key !== '') {
        window.localStorage.setItem(key,value);
      }
    },
    
    loadPage : function(){
      var facetsListener = new Clazz.com.components.facets.js.listener.FacetsListener();
      Clazz.navigationController.push(this);
    },
    
    getAction : function(actionBody, action, id, callback){
      var self = this;
      self.facetsListener.facetsAction(self.facetsListener.getActionHeader(actionBody, action, id), function(response) {
        //donot refers the page loadAttributes and edit data
        if(action != "load" && action != "editData" && response.status != "ERROR"){   
          self.pageRefresh(response);         
        } else{
          callback(response);
        }
      });
    },
    
    actionEvent : function() {  
  
      var self = this; 
      
      $("img[name='delete']").unbind('click');
      $("img[name='delete']").click(function(e) { 
        self.updateData = $(this).closest("tr");
        var deleteFacet = self.updateData.children().eq(2).text();
        var _self = this, facetid;
        self.navigation = new Clazz.com.components.navigation.js.Navigation();
        self.navigation.alertBox("Delete", "Are you sure you want to delete <span class='delete_color'>"+ deleteFacet + " </span>?", function(callback){
          if(callback){
            facetid = $(_self).closest('tr').attr('id');
            self.facetsActionBody = {};
            self.getAction(self.facetsActionBody, "delete", facetid);
          } 
        });
      });
      
      $("img[name='approval']").unbind('click');
      $("img[name='approval']").click(function(e) {
        self.facetsActionBody = {};
        self.getAction(self.facetsActionBody, "approve", $(this).closest('tr').attr('id'));
      });
      
      $("img[name='reject']").unbind('click');
      $("img[name='reject']").click(function(e) {
        self.facetsActionBody = {};
        self.getAction(self.facetsActionBody,"reject", $(this).closest('tr').attr("id"));
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
        if (self.facetsRequestBody.sortOrder == "desc" ) {
          $('.tabheader').each(function(){
            if($(this).parent().attr('tabname') == self.sortColumn) {
              $("#sortcolor").children().children().eq(0).attr('src','../themes/default/images/bestbuy/small.png');
              $(this).parent().addClass('sortclr');
              $(this).find('img').attr('src', '../themes/default/images/bestbuy/asc.png');
            } 
          });
        } else if (self.facetsRequestBody.sortOrder == "asc") {
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
    
    defaultSettings : function() {
      var self = this;
      self.facetsRequestBody.pageIndex = "1";
      self.facetsRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;    
      self.facetsRequestBody.sortColumn = commonVariables.requestBody.sortColumn;
      self.facetsRequestBody.sortOrder = commonVariables.requestBody.sortOrder;
      self.facetsRequestBody.searchOper = commonVariables.requestBody.searchOper;
      self.facetsRequestBody.searchColumnValues = commonVariables.requestBody.searchColumnValues;
    },
    
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

    search : function(txtSearch) {
      var self = this;
      var requestBody = self.facetsRequestBody;
      if (txtSearch != null) {
        self.defaultSettings();
        requestBody.searchColumnValues = [];
        requestBody.searchColumnValues.push(txtSearch);
        self.facetsRequestBody = requestBody;       
        self.facetsListener.getFacetsList(self.facetsListener.getRequestHeader(requestBody), function(response) {
          self.pageRefresh(response);
        });
      }
    },

    doPagination : function(searchCriteria,pageIndex,rowsPerPage) {
      var self = this;
      var requestBody = this.facetsRequestBody;
      requestBody.searchColumnValues.splice(0, requestBody.searchColumnValues.length);
      requestBody.searchColumnValues.push(searchCriteria);
      requestBody.pageIndex = pageIndex;
      requestBody.rowsPerPage = rowsPerPage;
      self.facetsRequestBody = requestBody;       
      self.facetsListener.getFacetsList(self.facetsListener.getRequestHeader(requestBody), function(response) {
        self.pageRefresh(response);
      });
    },
    
    columnSearch : function() {
      var self = this;
      var renderFunction = $.proxy(self.renderTemplate, self);
      var requestBody = self.facetsRequestBody;
      var searchCriteriaArray = [];
      self.searchdata.systemName = $("#systemNameSearch").val();
      self.searchdata.displayName = $("#displayNameSearch").val();
      self.searchdata.attributeName = $("#attributeSearch").val();
      self.searchdata.modifiedBy = $("#lastModifiedBySearch").val();
      self.searchdata.status = $("#statusSearch").val();
      
      if($("#systemNameSearch").val() != "") {
        var searchCriteria = {};
          searchCriteria.key = "systemName";
        searchCriteria.value = $("#systemNameSearch").val();
        searchCriteriaArray.push(searchCriteria);
      } 
      
      if($("#displayNameSearch").val() != "") {
        var searchCriteria = {};
        searchCriteria.key = "displayName";
        searchCriteria.value = $("#displayNameSearch").val();
        searchCriteriaArray.push(searchCriteria);
      } 
      
      if($("#attributeSearch").val() != "") {
        var searchCriteria = {};
        searchCriteria.key = "attributeName";
        searchCriteria.value = $("#attributeSearch").val();
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
        self.facetsRequestBody = requestBody;       
        self.facetsListener.getFacetsList(self.facetsListener.getRequestHeader(requestBody), function(response) {
          renderFunction(response, self.contentContainer);
        });
      } else {
        var self = this;
        self.defaultSettings();
        self.sortColumn = null;
        self.facetsListener.getFacetsList(self.facetsListener.getRequestHeader(self.facetsRequestBody), function(response) {
          self.pageRefresh(response);
        });     
      }
    },
    
    combinedSearch : function() {
      var self = this;
      var renderFunction = $.proxy(self.renderTemplate, self);
      var requestBody = self.facetsRequestBody;
      var searchCriteriaArray = [];
      self.searchdata.searchterm = $("#combinedSearch").val();
      
      if($("#combinedSearch").val() != "") {
        var searchCriteria = {};
        var displayName = {};
        var atrributes = {};
          searchCriteria.key = "systemName";
        searchCriteria.value = $("#combinedSearch").val();
        displayName.key = "displayName";
        displayName.value = $("#combinedSearch").val();
        atrributes.key = "attributeName";
        atrributes.value = $("#combinedSearch").val();
        searchCriteriaArray.push(searchCriteria);
        searchCriteriaArray.push(displayName);
        searchCriteriaArray.push(atrributes);
      }
      
      if (searchCriteria != null) {
        self.defaultSettings(); 
        requestBody.searchOper = "OR";
        requestBody.searchColumnValues = searchCriteriaArray;
        self.facetsRequestBody = requestBody;       
        self.facetsListener.getFacetsList(self.facetsListener.getRequestHeader(requestBody), function(response) {
          renderFunction(response, self.contentContainer);
        });
      } else {
        var self = this;
        self.defaultSettings();
        self.sortColumn = null;
        self.facetsListener.getFacetsList(self.facetsListener.getRequestHeader(self.facetsRequestBody), function(response) {
          self.pageRefresh(response);
        });     
      }
    
    },
    
    fillSearchData : function() {
      var self = this;
      $("#systemNameSearch").val(self.searchdata.systemName);
      $("#displayNameSearch").val(self.searchdata.displayName);
      $("#attributeSearch").val(self.searchdata.attributeName);
      $("#lastModifiedBySearch").val(self.searchdata.modifiedBy);
      $("#statusSearch").val(self.searchdata.status);
      $("#combinedSearch").val(self.searchdata.searchterm);
    },
    
    setCategoryTreeView : function(indexVal){
      var self = this;
      setTimeout(function() {
        var treeView = $('<div class="bbtreeview"><ul id="filetree" class="filetree"><li><span><strong>Click here to edit pages</strong></span>' + commonVariables.categoryTreeContent + '</li></ul></div>');

        $(treeView).jstree({
        }).bind("init.jstree", function(event, data){ 
        }).bind("loaded.jstree", function (event, data) {
          if(indexVal >= 0){
            $("#treeviewContent"+indexVal).children().remove();
            $("#treeviewContent"+indexVal).append(treeView);
          } else {
            $("#treeviewContent").children().remove();
            $("#treeviewContent").append(treeView);
          }
          console.log($("#treeviewContent"+indexVal).attr("id"));
            self.treeViewClickEvent($("#treeviewContent"+indexVal).attr("id"));
          
        });
      }, 200);
    },    
    treeViewRefresh : function(){
      setTimeout(function() {
        $(".bbtreeview").jstree("refresh");
        $(".bbtreeview").jstree("close_all");
      }, 100);
    },
    
    //function created by kavinraj.M 28/02/2013
    attributesclickevent : function(){
      var self = this;
      $("#attributes").removeAttr('readonly');
      $("#attributes").unbind('click'); 
      $("#attributes").click(function(e){
        $("#left").empty();
        $("#right").empty();
        $(this).removeClass("error_text").removeAttr('placeholder');
        var rightid;
        //$("#attributes").attr("href", "#Facet_attr");
        $("#Facet_attr").show();
        
        var popupDiv = $("#Facet_attr");
        popupDiv.attr("tabindex",-1).focus();
        if (popupDiv.isShown && popupDiv.options.keyboard) {
          if (!popupDiv.$element.attr('tabindex')) popupDiv.attr('tabindex', -1);

          popupDiv.on('keyup.dismiss.modal', function (e) {
            e.which == 27 && $(this).hide();
          });
        } else if (!popupDiv.isShown) {
          popupDiv.off('keyup.dismiss.modal')
        }
          
        $("#Facet_attr").on('keyup.dismiss.modal', function (e) {
          e.which == 27 && $(this).hide();
        });
        self.header.contentType = "application/json";
        self.header.requestMethod = "GET";
        self.header.dataType = "json";
        self.header.webserviceurl = window.commonVariables.webserviceurl + "attributes";
        self.facetsListener.getAttributes(self.header, function(response){
          if(response.status == "SUCCESS") {
            var content = "";
            var id = "";
            var contentValue = [];
            
            /* var htmlStr = $(document.createElement('ul')).attr('class', 'flt_left'); */
            $.each( response.rows, function(index,value) {
              id = value.id;
              content += '<li id='+ value.id +'>'+ value.name +'</li>';
            });
            $("#left").append(content);
            $("#loadAttributeValue").die();
            $("#loadAttributeValue").live("input",function(){
              categorysearch();
            });

            function categorysearch(){
              var txtSearch = $("#loadAttributeValue").val();         
              if (txtSearch != "") {
                $("#left li").hide();
                var hasRecord = false;
                $("#left li").each(function(index, value) {//To search for the txtSearch and search option thru all td
                  var litext = $(this).text(); 
                  if (litext.match(txtSearch)) {
                    $(this).show();
                    hasRecord = true;
                  } 
                });
              } else {                
                $("#left li").show();
              }
            }
        
            $(".flt_left li").live("click", function(){
              rightid = $(this).attr('id');
              $("#loadAttributeValue").val($(this).text());
              $(".flt_left li").each(function() {
                $(this).removeClass("field_act");
              });
              $("#"+rightid).addClass("field_act");
              self.loadingScreen.showLoading("#left");
              self.selectedFacetName = $(this).text();
              self.selectedFacetId = rightid;
              $("#right").empty();
              self.header.webserviceurl = window.commonVariables.webserviceurl + "attributes/values/" + rightid;
              self.facetsListener.getAttributevalues(self.header, function(response){
              $("#right").empty();
              self.responseCount = response.rows.length;
                if(response.status == "SUCCESS") {
                  var newcontent = "";
                  $.each( response.rows, function(index,value) {
                    newcontent += '<li id="'+ value.attributeValueId +'">'+ value.attributeValue +'</li>'   
                  });
                  $("#right").append(newcontent);
                }
              });
              self.loadingScreen.removeLoading("#left");
            });
            
          }
        }); 
        
      });
    },
    
    createTableRow : function(facetidval, value){
      var displayContextText = "Displayed";
      if(value.displayContext == "Y") {
        displayContextText = "Displayed";
      } else if(value.displayContext == "N") {
        displayContextText = "Hidden";
      }
      var tr = $("<tr></tr>");
      tr.append("<td><b>Page</b> : "+value.categoryId+"<input type='hidden' name='rmcatId' value="+value.categoryId+"></td>");
      tr.append("<td><b>Display Status</b> : "+displayContextText+"</td>");
      tr.append("<td>"+ facetidval +"</td>");                               
      tr.append("<td name='removeFacet' title='Remove Facet' style='cursor:pointer;' align='right'><img src='../themes/default/images/bestbuy/close.png'></td>");
      return tr;
    },
    
    /**************Writen by Kavinraj.M Date - 17/3/2013 start here ****************/
    
    addRemoveChkValue : function(value){
      var self = this, excludeValues = {}, removePosition, indicate = true, rowId= $(value).closest('tr').attr('id'),
      rowValue = $(value).closest('tr').children().eq(0).text();
      
      if(self.checkedItem.length > 0){
        $.each(self.checkedItem, function(index, currentItem){
          if(currentItem.attributeValueId == rowId){
            indicate =false;
            removePosition = index;
          }
        });
      }
      self.appendChkData(value.checked, indicate, rowId, rowValue, removePosition);
    },
    
    appendChkData :function(checked, indicate, rowId, rowValue, removePosition){
      var self = this;
      if(checked && indicate){
        var excludeValues = {};
        excludeValues.attributeValueId = rowId;
        excludeValues.attributeValue = rowValue;
        excludeValues.attrValuedisplay = "Y";  
        excludeValues.sortOrder = "";  
        self.checkedItem.push(excludeValues);
      } else if(!checked && !indicate){
        self.checkedItem.splice(removePosition, 1);
      }
    },
    
    chkValue : function(current, callbackVal){
      var self = this, indicate = true, removePosition;
      if(self.checkedItem.length > 0){
        $.each(self.checkedItem, function(index, currentItem){
          if(currentItem.attributeValueId == current){
            indicate =false;
            removePosition = index;
          }
        });
      }
      callbackVal(indicate);
    },

    /**************Writen by Kavinraj.M Date - 17/3/2013 end here ****************/
    
    /***
     * Bind the action listeners. The bindUI() is called automatically after the render is complete 
     */
     
    bindUI : function() {
      
      var self = this;
      self.rowhide();
      self.actionEvent();
      var htmlStr;
      
            $(".page").live('click',function() {
        //use a class, since your ID gets mangled
      $('div').removeClass("selectedPage");
      $(this).addClass("selectedPage");  
      });
      
      $("#assetid").keypress(function (e) {
        if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
          return false;
        }
      });
      
      $('.tabheader').die("click");
      $('.tabheader').live("click", function() {
        var sortOrder = "";
        self.sortColumn = $(this).parent().attr('tabname');
        if (self.facetsRequestBody.sortOrder == "desc") {
          sortOrder = "asc";
        } else if (self.facetsRequestBody.sortOrder == "asc") {
          sortOrder = "desc";
        }
        self.facetsRequestBody.pageIndex = commonVariables.requestBody.pageIndex;
        self.facetsRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;    
        self.facetsRequestBody.sortColumn = self.sortColumn;
        self.facetsRequestBody.sortOrder = sortOrder;
        self.facetsRequestBody.searchOper = commonVariables.requestBody.searchOper;
        self.facetsRequestBody.searchColumnValues = commonVariables.requestBody.searchColumnValues;
        self.facetsListener.getFacetsList(self.facetsListener.getRequestHeader(self.facetsRequestBody), function(response) {
          self.pageRefresh(response);
        });
      });
   /************************************************************************************************************
   * @author a1008498
   * @Description This method captures following key event . This method is to capture the functionality of "Rules of Sort"
   *        a.) Enter Key 
   *        b.)Tab Key 
   * 
   *         After user enters some value into the cell 
   *        Step 1:  Get the Old Value in the input box 
   *        Step 2:  Get the new value 
   *        Step3 :  Whenever we insert a row between two rows , we have to make sure that the new row value is less than second row and greater than first row .
   *                For example : First row is 1 , second row is 2 , then if we insert a row between 1 and 2 . Its values should 1.5 . Eg : 1, 1.5, 2. This will sort the rows correcting
          Step 4.)   If we user gives blank value , then box is given large value as 100000
          Step 5.)     Using the table sort plugin , sort the numeric values in asc order , After sorting give the row index to the re-ordered rows
   * 
   * @return   Sorted tabular data in facetvalue order Grid
   ***********************************************************************************************************/
      $(".sortOrder").die('keydown');
      $(".sortOrder").live('keydown', function(e){
        
         var charCode = (e.which) ? e.which : e.keyCode
         if (charCode > 31 && (charCode < 48 || charCode > 57)){
          this.value="";
          return false;
         }
    
         // Capture the keyboard event
        if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
          code= (e.keyCode ? e.keyCode : e.which);
          if (code == 13 || code == 9) {
            
            //Get the old value in the Sort Order Input box 
            // This is hidden field
          var currentPosition = $(this).closest('tr').find('input[name=hiddenSortOrder]').val();
          //Get the new value as given in the input box of sort order
          var inputPosition = this.value;
          // Define the sorting column and order ( 1 means Column name and 0 means acs)
          var sorting = [[1,0]]; 
           if(inputPosition !== undefined){

             // Logic to subtract the value of cell by .5 :  Whenever we insert a row between two rows , we have to make sure that the new row value is less than second row and greater than first row .
             // For example : First row is 1 , second row is 2 , then if we insert a row between 1 and 2 . Its values should 1.5 . Eg : 1, 1.5, 2. This will sort the rows correcting

               if((inputPosition !=null || inputPosition != ""  || currentPosition !=null || currentPosition != "" ) && (parseInt(inputPosition) > parseInt(currentPosition))){
                // We enter data which is greater than current cell value, then value should be added by .5
                inputPosition = inputPosition + '.5';
              }else{
                inputPosition = inputPosition - '.5';
              }
              if(inputPosition < parseInt("0")){
                this.value = '100000';
          
              }else{
                this.value = inputPosition;
      
              }

              
              $('#facetvalueorder').find('input[class=sortOrder]').each(function(){
                    var inputValue = $(this)[0].value; 
                    if(inputValue === null || inputValue === "" || inputValue === undefined){
                        $(this)[0].value='100000';
                    }
         
                 }) 
                          // clear cash vale from tablesorter function
              $("#facetvalueorder").unbind('appendCache applyWidgetId applyWidgets sorton update updateCell')
              .removeClass('tablesorter').find('thead th').unbind('click mousedown').removeClass('header headerSortDown headerSortUp');
              // add parser through the tablesorter addParser method
              $.tablesorter.addParser({
                id: 'inputs',
                is: function(s) {
                  return false;
                },
                format: function(s, table, cell, cellIndex) {
                  var $c = $(cell);
                  // return 1 for true, 2 for false, so true sorts before false
                  if (!$c.hasClass('sortOrder')) {
                    $c
                    .addClass('sortOrder')
                    .bind('keyup', function() {
                      $(table).trigger('updateCell', [cell, false]); // false to prevent resort
                    });
                  }
                  return $c.find('input').val();
                },
                type: 'numeric'
              });     
              $("#facetvalueorder").tablesorter({headers:{1:{sorter:'inputs'}}});
            
              // sort on the Sortt Order column 
              $("#facetvalueorder").trigger("sorton",[sorting]);  
                  
              // After the Sorting of whole table has completed , 
              //Get the rowIndex of each row and copy the rowIndex into the input box   
              $('#facetvalueorder').find('input[class=sortOrder]').each(function(){
                     var inputValue = $(this)[0].value;
                    if(inputValue !== null && inputValue !== "" && inputValue !== undefined){
                        if(inputValue ==='100000'){
                          $(this)[0].value ="";
                          $(this).closest('tr').find('input[name=hiddenSortOrder]').val("");
                        }else{
                          //Copy the rowindex value into input box
                          $(this)[0].value =$(this).closest('tr')[0].rowIndex;
                          $(this).closest('tr').find('input[name=hiddenSortOrder]').val($(this).closest('tr')[0].rowIndex);
                        }
                    }
         
                 }) 
           }  
         $("th[name=removeAllTh]").unbind('click');
           e.preventDefault();
          }//End of Tab or Enter Key event
           }//End of Capture of Event 
      });
      
      $('input[name="search"]').keypress(function(e){
        var id = $(this).attr('id');
        if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
          code= (e.keyCode ? e.keyCode : e.which);
          if (code == 13) {
            if(id == "combinedSearch") {
              self.combinedSearch();
            } else {
              self.columnSearch();
            } 
          }
        }
      });
      
      self.attributesclickevent();
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
          self.escapePopup($("#Add_facets"));

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
          self.escapePopup($("#Add_facets"));

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
      
       $("#systemname").focusout(function() {
                var systemname = $("#systemname");
                if (systemname.val().replace(/^\s+|\s+$/g,'') != '') {
                    self.onFocusOutEvent.dispatch(systemname.val(), self.mode, self.currentValue, function(response) {
                        if (response.status == "ERROR") {
                            systemname.val('');
                            var res = response.generalPurposeMessage[0].split(':');
                            systemname.attr('placeholder', res[1]);
                            systemname.addClass("error_text");
                            systemname.live('input', function() {
                            $(this).removeClass("error_text").removeAttr('placeholder');
                            $(this).removeClass("success_text").removeAttr('placeholder');   
                            });
                        }
                        if (response.status == "SUCCESS") {
                            systemname.live('input', function() {
                            $(this).removeClass("error_text").removeAttr('placeholder');
                            $(this).removeClass("success_text").removeAttr('placeholder');                                
                            });
                        }
                    });
                } else {
          $("#systemname").val('');
        }
            });
      
       $("#displayname").focusout(function() {
        if ($(this).val().replace(/^\s+|\s+$/g,'') == "") {
          $(this).val('');
        }
       });
      
      
      $("#banner_showall").click(function(e) {
        $("#banner_hideall").parent().show();
        $("#banner_showall").parent().hide();
        $(".data").show();
        $(".tabopen").removeClass("iconclose");
        $(".tabopen").addClass("iconopen");
        $(".tabopen").attr("dataflag", "false");
      });
      
      $("#banner_hideall").click(function(e) {
        $("#banner_hideall").parent().hide();
        $("#banner_showall").parent().show();
        $(".data").hide();
        $(".tabopen").removeClass("iconopen");
        $(".tabopen").addClass("iconclose");
        $(".tabopen").attr("dataflag", "true");
      });
      
      $("#closePage").click(function(e) {
        self.fCheck = 0;
        $(".sub_cat_facet").css("display", "none");
        $("#addFacetButton1").show();
        $("#addFacetButton").hide();
      });

      $("#submit").click(function(){
        // If no attribute value is selected by the user then do nothing 
        // change done by asheesh for the bug 3966
        $("#attributeValueSearch").val("");
        self.attributeValueSearch="";
        if($("#loadAttributeValue").val() != null && $("#loadAttributeValue").val() !=""){
          $("#loadAttributeValue").val("");
          self.loadAttribute();
        }else{
          // Close the pop -up
          $("#Facet_attr").hide();
        }
                
      });

      /**************Writen by Kavinraj.M Date - 17/3/2013 start here ****************/
      
      $("#selectall").live("click",function(){
        $('.check').attr('checked', this.checked);        
        $(".check").each(function(index, value){
          self.addRemoveChkValue(value);
        });
        self.checkAll = this.checked;
      });
      
      /**************Writen by Kavinraj.M Date - 17/3/2013 end here ****************/
      
      /***********************************************************************************************************************
       * @author Asheesh Swaroop
       * @Description :  On the click of remove all Button as given in the facet value order , all the attributes sorted will be removed 
       * @Event : click 
       * @bug No 4115
       * **************************************************************************************************************/
  
          $("th[name=removeAllTh]").unbind('click');
      $("#removeall").live('click', function(e){
         $("#bodycontentSorter tr").each(function() {
            var trId = $(this).closest('tr').attr("id");
            $(this).closest('tr').remove();
            $("#btn_"+trId).show();
            $("#btn_"+trId).removeClass("btnClshide");
            $("#btn_"+trId).addClass("btnClsshow");
            $("#span_"+trId).text("");

          });
          self.editValue.promoteList = [];
      });
      
      /**************Writen by Kavinraj.M Date - 17/3/2013 start here ****************/
      
      $(".check").live("click",function(){ 
        self.addRemoveChkValue(this);
        if($(".check").length == $(".check:checked").length) {
          $("#selectall").attr("checked", "checked");
          self.checkAll = true;
        } else {
          $("#selectall").removeAttr("checked");
          self.checkAll = false;
        }    
      });
          
      /**************Writen by Kavinraj.M Date - 17/3/2013 end here ****************/   
          
      $('.removeTd').live('click', function() {
        var trId = $(this).closest('tr').attr("id");
        $(this).closest('tr').remove();
        $("#btn_"+trId).show();
        $("#btn_"+trId).removeClass("btnClshide");
        $("#btn_"+trId).addClass("btnClsshow");
        $("#span_"+trId).text("");
        
        var boostTabLength = $("#bodycontentSorter tr").length;
                for(var m = 0; m < boostTabLength; m++){
                  // Change done for SPT 3921 or 4009 Start 
                   // $("#bodycontentSorter tr").eq(m).children().eq(1).text((m + 1));
                    $("#bodycontentSorter tr").eq(m).children().eq(1).find('input').val((m + 1));
                    // Change done for SPT 3921 or 4009 End 
                }       
        
      }); 
      
      $('input[name="sortType"]').click(function() {
        if ($(this).val() === "specificOrderSequence") {
          $('.ctrlOrder').show();
        } else {
          $('.ctrlOrder').hide();
        }
      });
      
      $('#attributes').keyup(function(e) {
        var code = e.keyCode || e.which;
        if (code == '9') {
           $('#attributes').click();
          }
      });
      
      $("#AddIcon").click(function(e) {
        Pages.reset();
        // Create and Select first page
          $('.addPageButton').click();
        self.checkAll = false;
        self.checkedItem = [];
        self.mode = "Add";
        self.currentValue = null;
        self.escapePopup($("#Add_facets"));
        setTimeout(function() {         
          //$('#attributes').click();
          //$('#loadAttributeValue').focus();
          $('#attributes').focus();
        }, 2000); 
        self.attributesclickevent();
        self.categoryWrapper = [];
        self.fCheck = 0;
        $("input:radio[id='num_res_least']").attr('checked', 'checked');
        $("input[name=sortTypeOrder]").attr("checked", false);
        $("input[name=sortTypeOrder]").attr("disabled", true);
        $("#minimim").val(2);
        $("#maximum").val(8);
        $("#addFacetButton1").show();
        $("#addFacetButton").hide();
        $("#pop_error").hide();
        $("#facetname").empty();
        $("#systemname").val('').removeClass("error_text").removeAttr("placeholder");
        $("#displayname").val('').removeClass("error_text").removeAttr("placeholder");
        $("#attributes").val('').removeClass("error_text").removeAttr("placeholder");
        $("#startDate").val('').attr("placeholder",'Click to select date.');
        $("#endDate").val('').attr("placeholder",'Click to select date.');
        $("#mode").val('').removeClass("error_text");
        $("#mode").val('').removeClass("error_text");
        $("#assetid").val('').removeClass("error_text").removeAttr("placeholder");
        $("#categoryId").val('').removeClass("error_text").removeAttr("placeholder");
        $("#facetvalueordercontent").empty();
        $("#facetvaluescontent").empty();
        $("#tablefooter").empty();
        $("#facetvaluesTitle").html("");
        $("#facetvalueorderTiltle").html("");
        $("#addedDependentFacets").html("");
        $(".addFacetButton-error").html("");
        $("#minmax-error").html('');
        $("#minimim").removeClass("error_text");
        $("#maximim").removeClass("error_text");
        self.categoryWrapper.length = 0;
        self.editValue = {}; // To clear previous edit values
        $("[name=displayContext]").attr('checked', true)
        $("[name=displayContext]").attr("disabled", false);
        $($("input[name=facetDisplay]")[0]).attr("checked", "checked");
        $("#facetLabel").html('Create Facets');
        $("#create").html('CREATE');
        $("#create").attr('title','Create');
        $(".startdate-error").empty();
        $(".enddate-error").empty();
        $(".sub_cat_facet").css("display", "none");
        // Value in the Search box in the facet value section should be removed or made blank
        self.attributeValueSearch ="";
        $("#attributeValueSearch").val("");
        self.treeViewRefresh();
        
        setTimeout(function() {
          $(".modal-body").scrollTop(0);
        }, 300);
        
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
          setTimeout(function() {
        self.sizeModal();
        }, 500);
        
      });
      
      $("#categoryTree").unbind('click');
      $("#categoryTree").click(function(e) {
        catId = abcat0100000;
        self.getAction("","getCatChildren", "", function(responseData){
          self.updateFacetValues (responseData);
          $('#tablefooter').find("input").val(pageIndex);
        });
      });

      $("img[name='editIcon']").unbind('click');
      $("img[name='editIcon']").click(function(e) {

        //Attribute Search value should be made blank as soon as the page is opened 
        Pages.reset();
        self.attributeValueSearch ="";
        $("#attributeValueSearch").val("");
        $("#attributes").attr('readonly', true);
        $("#attributes").unbind('click'); //edit by kavinraj.M
        $(".addFacetButton-error").html("");
        $("#facetLabel").html('Edit Facet');        
        $("#create").html('UPDATE');
        $("#create").attr('title','Update');        
        $("input[name=sortTypeOrder]").attr("disabled", false);
        $("#addFacetButton1").hide();
        $("#addFacetButton").show();

        //treeview
        self.checkAll = false;
        self.checkedItem = [];
        self.treeViewRefresh();
        self.mode = "Edit";
        self.updateData = $(this).closest("tr");
        self.currentValue = self.updateData.find("#systemName").html();
        var editFacetId = self.updateData.attr('id');
        self.facetsActionBody = {};
        self.editValue = {}; // To clear previous edit values
        $("#bodycontentSorter").empty();
        self.getAction(self.facetsActionBody,"editData", editFacetId, function(callback) {          
          //Get facet data from server.
          self.editValue = callback.data;
          
          /***************** Writen by Kavinraj.M Date: 19/3/2013 start ******************/
          
          if(self.editValue != null && self.editValue.excludeList != null && self.editValue.excludeList.length > 0){
            $.each(self.editValue.excludeList, function(index, current){
              self.chkValue(current.attributeValueId, function(callbackInnerVal){
              if(callbackInnerVal)
                self.appendChkData(true, true, current.attributeValueId, current.attributeValue, null);
              });
            });
          }
          
          /***************** Writen by Kavinraj.M Date: 19/3/2013 end ******************/
          
          $("#facetname").text(self.editValue.systemName);
          displayFacet(self.editValue.facetDisplay);
          // Change done by Asheesh for SPT 4044
          //the title of the Page should be "Edit Facet : <Facet Name>" 
          $("#facetLabel").html('Edit Facet :' + self.editValue.systemName);
          $("#systemname").val(self.editValue.systemName).removeClass("error_text").removeAttr("placeholder");
          $("#displayname").val(self.editValue.displayName).removeClass("error_text").removeAttr("placeholder");
          $("#attributes").val(self.editValue.attributeName).removeClass("error_text").removeAttr("placeholder");
          self.selectedFacetName = self.editValue.attributeName;
          self.selectedFacetId = self.editValue.attributeId;
          //if (self.editValue.promoteList.length > 0) {
            self.loadAttribute();
          //} 
          $("input[name=facetDisplay]").each(function() {
            if(self.editValue.facetDisplay == $(this).val()){
              $(this).attr('checked','checked');
            } 
          });
          $("#startDate").val(self.editValue.startDate);
          $("#endDate").val(self.editValue.endDate);
          $("select#mode option").each(function(){
             if($(this).val() == self.editValue.displayMode){
              $(this).attr("selected","selected");    
            }
          });
          $("#assetid").val(self.editValue.glossaryItem).removeClass("error_text").removeAttr("placeholder");
          $("input[name=displayRecurrence]").each(function() {
            if(self.editValue.displayRecurrence == $(this).val()){
              $(this).attr('checked','checked');
            } 
          });
          $("input[name=displayFacetRemoveLink]").each(function() {
            if(self.editValue.displayFacetRemoveLink == $(this).val()){
              $(this).attr('checked','checked');
            } 
          });
          $("input[name=displayMobileFacet]").each(function() {
            if(self.editValue.displayMobileFacet == $(this).val()){
              $(this).attr('checked','checked');
            } 
          });
          $("input[name=displayMobileFacetRemoveLink]").each(function() {
            if(self.editValue.displayMobileFacetRemoveLink == $(this).val()){
              $(this).attr('checked','checked');
            } 
          });
          $("input[name=sortType]").each(function() {
            if(self.editValue.sortType == $(this).val()){
              $(this).attr('checked','checked');
              $("input[name=sortTypeOrder]").attr("disabled", true);
            } 
          });
          
          $("input[name=sortTypeOrder]").each(function() {
            if(self.editValue.sortType == $(this).val()){
              $("#sortType").attr('checked','checked');
              $(this).attr("disabled", false).attr('checked', 'checked');             
            } 
          });
          $("select#minimim option").each(function(){
            if($(this).text() == self.editValue.minValue){
              $(this).attr("selected","selected");    
            }
          });
          $("select#maximum option").each(function(){
            if (self.editValue.maxValue == null){
              self.editValue.maxValue = "All";
            }
            if($(this).text() == self.editValue.maxValue){
              $(this).attr("selected","selected");    
            }
          });
          console.log(Pages);
          if (self.editValue.categoryWrapper.length > 0) {
            //Pages.removeall();
            for(var x =0; x < self.editValue.categoryWrapper.length; x++)
            {
              if(self.editValue.categoryWrapper[x].applySubCategory == "Y"){
                self.editValue.categoryWrapper[x].applySubCategory = true;
              } else {
                self.editValue.categoryWrapper[x].applySubCategory = false;
              }
              Pages.push(self.editValue.categoryWrapper[x]);
              edit='1';
            }
              


            //Parse JSON
            $(".sub_cat_facet").css("display", "block");
            $("#categoryId").val(self.editValue.categoryWrapper[0].categoryId).removeClass("error_text");
            self.categoryWrapper = self.editValue.categoryWrapper;
            self.categoryWrapperPages();
          } else {
            $("#addedDependentFacets").html("");
            Pages=[];
          }
        });
        $("#minmax-error").html('');
        $("#minimim").removeClass("error_text");
        $("#maximim").removeClass("error_text");
        $(".startdate-error").empty();
        $(".enddate-error").empty();
        setTimeout(function() {
          self.sizeModal();
        }, 500);
      });

      $("#assetid").keypress(function (e) {
        if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
          return false;
        }
      });
      self.sortType = $("input[name=sortType]:checked").val();
      $("input[name=sortType]").click(function(e) {
        if (this.id == "sortType") {
          $("input[name=sortTypeOrder]").attr("disabled", false).attr('checked', true);
          $("#sortTypeOrder").attr('checked', true);
          self.sortType = $("input[name=sortTypeOrder]:checked").val();
        } else {
          $("input[name=sortTypeOrder]").attr("disabled", true).attr('checked', false);
          self.sortType = $("input[name=sortType]:checked").val();        
        }
      });
    
      $("#num_res_least, #alphabatical").click(function(e) {
        self.tblbodyempty();
        // SPT 2200
        //If in the Create or Edit Facet pages, when the user changes the sort order from specific order sequence to either Alphabetical order (A-Z) or
        // Number of results sorted most results to least, then any boxes that have a specific order sequence should be cleared (as there can't be a specific order for Alphabetical order (A-Z) or Number of results sorted most results to least). 
        if($("#bodycontent tr").length >0){
          self.loadAttribute();
        }
        
        
         //@TO DO : We need to sort the data in botton grid in alphabatical order 
      });
      $("input[name=sortTypeOrder]").click(function(e) {
        self.sortType = $("input[name=sortTypeOrder]:checked").val();
      });
      
      
      /**
       * @Description 
       *        If clicking the Return to Facets Home button.
       *        Then the Data Source modal dialog will close, as well as closing the Create/Edit Facet window. 
       *        Bringing the user back to the Facets Master list. 
       * @author Asheesh Swaroop for SPT 4122
       * @return : Close the the window
       * */
      $("#btclose").click(function(e) {
        $("#Facet_attr").hide();
        $('#cancel').trigger('click');
      });
      
      $('input[name="displayDepFacet"]').bind("change", function(e){
        var id = $(this).attr('attrDep');
        var temp = e.currentTarget.parentNode;
        var categoryId = $(temp).siblings("input#categoryId").val();
        if($('input[name="displayDepFacet"]:checked').val() === "Y"){
          self.depententTreeVal(categoryId, "catFacetDname");
          $("#" + id).show();
        }else {
          $("#" + id).hide();
        }
      });

       /*******************************************************************************
       * Max Dropdown : This drop down includes numbers listed out from 1 through 25 and then 'All.' By default, 8 is selected. Maximum must be greater than or equal to the minimum number. 
       *Immediate validation on this field along with the message: The miniumum number of facet values must be equal to or less than the maximum number of facet values. Please update. 
       * Min Dropdown : This dropdown includes numbers 1 through 10 and by default, 2 is selected. Minimum value must be equal or lesser than the maximum value. 
       *Immediate validation on this field along with the message: The mininum number of facet  
       ***************************************************************************************/
      var minB = $('#minimim')[0],
          maxB = $('#maximum')[0],
           min, max;
           
     

      function setLimits(limit, opts, lower, upper) {
          for (var i = 0; i < limit; i++) {
              opts[i].disabled = lower;
          }
          for (var i = limit; i < opts.length; i++) {
              opts[i].disabled = upper;
          }
      
      }
      
      function setLowerLimit(limit, elem) {
          var opts = elem.getElementsByTagName('option');
          setLimits(limit, opts, 'disabled', false);
      }
      
      function setUpperLimit(limit, elem) {
          var opts = elem.getElementsByTagName('option');
          setLimits(limit+1, opts, false, 'disabled');
      }
      
      
      minB.onchange = function() {
        var minimim = parseInt($("#minimim option:selected").val());
        var maximum = parseInt( $("#maximum option:selected").val()); 
        if(minimim > maximum  || minimim === maximum ){
          $("#maximum").val(minimim + 1);
        }
          setLowerLimit(minB.selectedIndex, maxB);
      };
      maxB.onchange = function() {
          var minimim = parseInt($("#minimim option:selected").val());
         var maximum = parseInt( $("#maximum option:selected").val());  
         if(minimim === maximum ){
          $("#minimim").val(minimim - 1);
        }
        // As the values of minimun dropdown is from 1-10 which is less than Max dropdown 1- 25 .
        // if the selected values in max dropdown is greater than 10 , then we have to display all the min dropdown values
        if(maxB.selectedIndex > minB.length ){
          setUpperLimit((minB.length) -1, minB);
        }else{
          setUpperLimit(maxB.selectedIndex, minB);
        }
          
      };
      //**********************************************End : Min and Max Dropdown Values Population Method ****************************/
      var counter = 2;    
      $("#addFacetButton1").click(function () {
        $(".sub_cat_facet").css("display", "block");
        $("#addFacetButton").show();
        $("#categoryId").val(commonVariables.categoryId);
        $("#categorypathText").val(commonVariables.categoryPath);
        $("#addFacetButton1").hide();
        $(".addFacetButton-error").html("");
      });
      
      $("#addFacetButton").click(function () {
        $("#addFacetButton1").hide();
        var facetJson = {};
        var addedDependentFacets = $("#addedDependentFacets");
        var subCat = (($("#applySubCategory").is(':checked')) ? "Y" : "N" );
        var pushTrigger = 0;
          if($(".sub_cat_facet").css("display") == "block"){
            var categoryId = $("#categoryId").val();
            facetJson.applySubCategory = subCat;
            facetJson.displayDepFacet = $("input[name=displayDepFacet]:checked").val();
            facetJson.displayContext = $("input[name=displayContext]:checked").val();
            facetJson.categoryId = categoryId;
            facetJson.depFacetId =  $("#depFacetId").val();
            facetJson.depFacetText= $("#depFacetId option:selected").text();
            facetJson.facetAttributeValueId = $("#facetAttributeValueId").val();
            facetJson.categoryPath = $("#categorypathText").val();
              $.each(self.categoryWrapper, function(index, value) {
                if(value.categoryId == categoryId){
                  pushTrigger =1;
                }
              });
            if(pushTrigger == 0){
              self.categoryWrapper.push(facetJson);
            } else {
              $("#addFacetButton").focus();
              $(".addFacetButton-error").html('Selected category already added');
            }
          } else {
            $.each(self.categoryWrapper, function(index, value) {
              if(index == $("#currentEditFacet").val()){
                var editTrigger = 0;
                $.each(self.categoryWrapper, function(row, field) {
                  if(index != row && field.categoryId == $("#categoryId_"+index).val()){
                    editTrigger = 1;
                  }
                });
                if(editTrigger == 0){
                  var subCat = (($("#applySubCategory_"+index).is(':checked')) ? "Y" : "N" );
                  if($("input[name=displayDepFacet"+index+"]:checked").val() == "Y" && self.categoryWrapper[index].displayDepFacet == "N"){
                    var depFacetId = $("#depFacetId").val();
                    var depFacetText = $("#depFacetId option:selected").text();
                    var facetAttributeValueId = $("#facetAttributeValueId").val();
                  } else if($("input[name=displayDepFacet"+index+"]:checked").val() == "N" && self.categoryWrapper[index].displayDepFacet == "Y"){
                    var depFacetId = '';
                    var depFacetText = '';
                    var facetAttributeValueId = '';
                  }else {
                    var depFacetId = $("#depFacetId_"+index).val();
                    var depFacetText = $("#depFacetId_"+index+" option:selected").text();
                    var facetAttributeValueId = $("#attributeName_"+index).attr("key");
                  }
                  self.categoryWrapper[index].displayDepFacet = $("input[name=displayDepFacet"+index+"]:checked").val();
                  self.categoryWrapper[index].displayContext = $("input[name=displayContext"+index+"]:checked").val();
                  self.categoryWrapper[index].applySubCategory = subCat;
                  self.categoryWrapper[index].categoryId = $("#categoryId_"+index).val();
                  self.categoryWrapper[index].categoryPath = $("#categorypathText_"+index).val();
                  self.categoryWrapper[index].depFacetId = depFacetId;
                  self.categoryWrapper[index].depFacetText = depFacetText;
                  self.categoryWrapper[index].facetAttributeValueId = facetAttributeValueId;
                  $("#addFacetButton").hide();
                  $("#addFacetButton1").show();
                  self.setCategoryTreeView('');
                } else {
                  $("#addFacetButton").focus();
                  $(".addFacetButton-error").html('Selected category already exists');
                } 
              }

            });
          }
          
          addedDependentFacets.empty();
          $.each(self.categoryWrapper, function(index, value) {
            value.categoryPath = value.categoryPath;
            var categoryId = '';
            var depFacetId = '';
            var facetAttributeValueId = '';
            var depFacetValueId = '';
            
            categoryId = value.categoryId;
            depFacetId = value.depFacetId;
            facetAttributeValueId = value.facetAttributeValueId;
            depFacetValueId = value.depFacetId;
            
            var addtr = $("<tr class='dependentfacet' id="+index+"></tr>");
            var newtr = $("<tr class='addPage' style='display:none;' id="+index+"></tr>");
            var attrDep = "catFacetDname"+index;
        
            var displayContextText = "";
            if(value.displayContext == "Y") {
              displayContextText = "Displayed";
            } else if(value.displayContext == "N") {
              displayContextText = "Hidden";
            } 

            var applySubCategory = "";
            if(value.depFacetId != "" && value.depFacetId != null){
              self.header.contentType = "application/json";
              self.header.requestMethod = "GET";
              self.header.dataType = "json";
              if(self.editValue != null && self.editValue != ''){
                self.header.webserviceurl = window.commonVariables.webserviceurl + "categoryFacet/"+ value.categoryId;
              }else{
                self.header.webserviceurl = window.commonVariables.webserviceurl + "categoryFacet/depFacetsForCategory/"+ value.categoryId +"/"+self.editValue.facetId;
              }
              self.facetsListener.getfacets(self.header, function(response) {
                $.each(response.rows, function(index, value) {
                  if (depFacetValueId ==  value.facetId) {
                    self.depFacetIdText = value.facetName;                    
                  }
                }); 

                addtr.append("<td><b>Page</b> : "+value.categoryPath+" | "+value.categoryId+"<input type='hidden' name='rmcatId' value="+value.categoryId+"></td>");
                addtr.append("<td><b>Display Status</b> : "+displayContextText+"</td>");
                addtr.append("<td><b>Facet Display ID</b> : "+self.depFacetIdText +"</td>");
                addtr.append("<td title='Remove Facet' style='cursor:pointer;' align='right'><span name='removeFacet'><img src='../themes/default/images/bestbuy/close.png'></span></td>");
                newtr.append('<td colspan="4" title="Remove Facet"><div class="sub_cat_facet_dynamic_div" style="padding:0;"><div class="flt_right" id='+index+' name="closePage"><img  style="cursor:pointer;margin-top:10px;" src="../themes/default/images/bestbuy/close.png"></div><div id=treeviewContent_'+index+'><span>Category TreeView Loading... <img id="catLoading" src="../themes/default/images/bestbuy/cat_tree_loader.gif"></span></div>Selected Category <sup>*</sup> <input type="text" id=categorypathText_'+index+' value='+value.categoryPath+' readonly>&nbsp; | &nbsp;<input type="text" id=categoryId_'+index+' name="categoryId" maxlength="50" value='+categoryId+' readonly><br><input type="checkbox" name=applySubCategory_'+index+' id=applySubCategory_'+index+'>&nbsp;Apply to sub-pages <br><div class="flt_left">Dependent Upon Another Facet?</div> &nbsp;<div id="displayDepFacetYes" class="flt_left"><input type="radio" value="Y" name=displayDepFacet'+index+' attrDep="' + attrDep + '" id="Yes'+ index + '">Yes <input type="radio" value="N" name=displayDepFacet'+index+ ' attrDep="' + attrDep + '" id="No'+ index + '">No</div><br><div id="' + attrDep + '"></div>Display or Hide in this Page? <label class="radio inlineFacet" id="DisplayHideYes"><input type="radio" name=displayContext'+index+' value="Y">Yes</label><label class="radio inlineFacet" id="DisplayHideNo"><input type="radio" name=displayContext'+index+' value="N">No</label><br></div></td>');                  
                addedDependentFacets.append(addtr);
                addedDependentFacets.append(newtr);
              });
            } else {
              addtr.append("<td><b>Page</b> : "+value.categoryPath+" | "+value.categoryId+"<input type='hidden' name='rmcatId' value="+value.categoryId+"></td>");
              addtr.append("<td><b>Display Status</b> : "+displayContextText+"</td>");
              addtr.append("<td>&nbsp;</td>");
              addtr.append("<td title='Remove Facet' style='cursor:pointer;' align='right'><span name='removeFacet'><img src='../themes/default/images/bestbuy/close.png'></span></td>");
              newtr.append('<td colspan="4" title="Remove Facet"><div class="sub_cat_facet_dynamic_div" style="padding:0;"><div class="flt_right" id='+index+' name="closePage"><img  style="cursor:pointer;margin-top:10px;" src="../themes/default/images/bestbuy/close.png"></div><div id=treeviewContent_'+index+'><span>Category TreeView Loading... <img id="catLoading" src="../themes/default/images/bestbuy/cat_tree_loader.gif"></span></div>Selected Category <sup>*</sup> <input type="text" id=categorypathText_'+index+' value='+value.categoryPath+' readonly>&nbsp; | &nbsp;<input type="text" id=categoryId_'+index+' name="categoryId" maxlength="50" value='+categoryId+' readonly><br><input type="checkbox" name=applySubCategory_'+index+' id=applySubCategory_'+index+'>&nbsp;Apply to sub-pages <br><div class="flt_left">Dependent Upon Another Facet?</div> &nbsp;<div class="flt_left" id="displayDepFacetYes"><input type="radio" value="Y" name=displayDepFacet'+index+' attrDep="' + attrDep + '" id="Yes'+ index + '">Yes <input type="radio" value="N" name=displayDepFacet'+index +' attrDep="' + attrDep + '" id="No'+ index + '">No</div><br><div id="' + attrDep + '"></div>Display or Hide in this Page? <label class="radio inlineFacet" id="DisplayHideYes"><input type="radio" name=displayContext'+index+' value="Y">Display</label><label class="radio inlineFacet" id="DisplayHideNo"><input type="radio" name=displayContext'+index+' value="N">Hide</label><br></div></td>');              
              addedDependentFacets.append(addtr);
              addedDependentFacets.append(newtr);
            }
      
          $("span[name='removeFacet']").unbind("click");
          $("span[name='removeFacet']").bind("click",function() {
            var rmindexId = $(this).closest('tr').attr("id");
            $.each(self.categoryWrapper, function(index, value){
              if(index == rmindexId){
                self.categoryWrapper.splice(index, 1);
                return false;
              }
            });
            $(this).closest('tr').next().remove();
            $(this).closest('tr').remove();
          });

          $("div[name='closePage']").unbind("click");
          $("div[name='closePage']").bind("click",function() {
            var rmindexId = $(this).closest('tr').attr("id");
            $.each(self.categoryWrapper, function(index, value){
              if(index == rmindexId){
                self.categoryWrapper.splice(index, 1);
                return false;
              }
            });
            $(this).closest('tr').prev().remove();
            $(this).closest('tr').remove();
          });

            $(addtr).unbind("click");
            $(addtr).bind("click", function() {
               $("#addFacetButton1").hide();
               $("#addFacetButton").show();
              $(".addFacetButton-error").html('');
               
              var outerindex = $(this).attr("id");
              $("#currentEditFacet").val(outerindex);
              var currentCategoryId = $(this).children().eq(0).children().eq(1).val();
              $(".sub_cat_facet").css("display", "none");
                          
            if(self.existingCategoryWrapper.length > 0 ){
              $.each(self.existingCategoryWrapper, function(row, val) {
                if(val.categoryId != currentCategoryId){
                  self.setCategoryTreeView(outerindex);
                }else {
                  $("#treeviewContent_"+outerindex).css("display" , "none");
                }
              });
            }else {
              self.setCategoryTreeView(outerindex);
            }

              var displayStatus = $(this).children().eq(1).text();
              var DisplayDepFacetStausValue = displayStatus.split(":");
              var checkedflag = DisplayDepFacetStausValue[1].replace(/^\s+|\s+$/g,'');
              var val = $(this).children().eq(2).text().replace(/^\s+|\s+$/g,'');
              
              var subcategory = $(addtr).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children("#applySubCategory_"+outerindex+" :checked").val();
              var radiobtn = $(addtr).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children("#displayDepFacetYes").children("input[name^='displayDepFacet"+outerindex+"']");
              var checkedstatus = $(addtr).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children("#displayDepFacetYes").children("input[name^='displayDepFacet"+outerindex+"']:checked").val();
              
              //var subpagesstatus = $(addtr).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children("input[name^='applySubCategory_"+outerindex+"']:selected");
              if(val.length == 0){
                var radioNButton = $(this).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children("#displayDepFacetYes").children("input[name^='displayDepFacet"+outerindex+"'][value='N']");
                $(radioNButton).attr('checked', true);
                var idToHide = $(radioNButton).attr('attrDep');
                $("#" + idToHide).hide();
                
              } else {
                //var facetvalue = val.split(':');
                var  DependentFacet = $(this).children().eq(2).text();
                var  DependentFacetValue = DependentFacet.split(":");
                var DependentFacetValueFlag = DependentFacetValue[1].replace(/^\s+|\s+$/g,'');
                if (DependentFacetValueFlag != ""){
                  var radioYButton = $(this).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children("#displayDepFacetYes").children("input[name^='displayDepFacet"+outerindex+"'][value='Y']");
                  $(radioYButton).attr('checked', true);
                  
                  var idToHide = $(radioYButton).attr('attrDep');
                  $("#" + idToHide).show();
                }
              }
              
              if(value.applySubCategory == "Y") {
                $(addtr).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children("#applySubCategory_"+outerindex).attr("checked", "checked");
              }
              
              if($(addtr).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children("#displayDepFacetYes").children("input[name^='displayDepFacet"+outerindex+"']:checked").val() == "Y") {
                
                var categoryId = $(radiobtn).parent().siblings("input[name='categoryId']").val();
                self.header.contentType = "application/json";
                self.header.requestMethod = "GET";
                self.header.dataType = "json";
                
                self.header.webserviceurl = window.commonVariables.webserviceurl + "categoryFacet/"+ categoryId;
                
                self.facetsListener.getfacets(self.header, function(response) {     
                  var facetDiv = $(document.createElement('div')).attr("class","banner_border").attr("display","none");
                  $(facetDiv).empty();
                  var option = '';
                  $.each(response.rows, function(index, value) {
                    option += '<option value="'+value.facetId+'">'+ value.facetName+'</option>';  
                    facetDiv.html('<div class="flt_right"></div><strong>Select Facet Display Name:</strong><br><em>Grayed out facets are dependent on this facet and cannot be selected.</em><select id=depFacetId_'+outerindex+'><option>Please Select an Option</option>'+ option +'</select>&nbsp;&nbsp;<input type="text" class="attributeName" id=attributeName_'+outerindex+'><input type="hidden" id="facetAttributeValueId" name="facetAttributeValueId"></input>&nbsp;&nbsp;');
                    $(facetDiv).appendTo($(newtr).find("#"+attrDep));
                  });
                  
                  $(facetDiv).find("option").each(function(index,value){
                    if($(value).val() == depFacetId) {
                      $(value).attr("selected", "selected");
                      self.header.contentType = "application/json";
                      self.header.requestMethod = "GET";
                      self.header.dataType = "json";
                      self.header.webserviceurl = window.commonVariables.webserviceurl + commonVariables.facetsContext + "/attributeValue/" + depFacetId;
                      
                      self.facetsListener.getfacets(self.header, function(response){ 
                        var autocompleteval = [];
                        $.each(response.rows, function(index, value){
                          autocompleteval.push({"key":value.attributeValueId,"value":value.attributeValue});
                        });
                        
                        $(".attributeName" ).autocomplete({
                          source: autocompleteval
                        });
                        
                        $(".attributeName" ).on( "autocompleteselect", function( event, ui ) {
                          $(this).attr("key",ui.item.key);
                        });

                        $.each(autocompleteval, function(index, value){
                          if(facetAttributeValueId == value.key) {
                            $(facetDiv).find('input').attr("key",value.key);
                            $(facetDiv).find('input').val(value.value); 
                          }
                        }); 
                      });
                    }
                  });
                  
                  $(facetDiv).find('select').change(function() {
                  
                    var id = $(this).val();
                    self.header.contentType = "application/json";
                    self.header.requestMethod = "GET";
                    self.header.dataType = "json";
                    self.header.webserviceurl = window.commonVariables.webserviceurl + commonVariables.facetsContext + "/attributeValue/" + id;
                    
                    self.facetsListener.getfacets(self.header, function(response){  
                    
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
                });
                /* self.facetTemplateEdit(categoryId, $(newtr).find("#"+attrDep), depFacetId, facetAttributeValueId); */
                $("#" + idToHide).show();
              } 
              
              $(radiobtn).bind("change", function(e) {
                var id = $(this).attr('attrDep');
                var temp = e.currentTarget.parentNode;
                var categoryId = $(temp).siblings("input[name='categoryId']").val();
                
                if($(this).val() == "Y"){
                  self.depententTreeVal(categoryId, idToHide);
                  $("#" + idToHide).show();
                } else {
                  $("#" + idToHide).hide();
                }
              });
              
              if(checkedflag == "Hidden") {
                $(this).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children("#DisplayHideNo").children("input[name='displayContext"+outerindex+"']").attr("checked", "checked");
              } else {
                $(this).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children("#DisplayHideYes").children("input[name='displayContext"+outerindex+"']").attr("checked", "checked"); 
              }  
              
              $(".dependentfacet").show();
              $(".addPage").hide();
              $(this).next(".addPage").show();
              $(this).hide();
            });
            $(addtr).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children("#displayDepFacetYes").children("input[name='displayDepFacet']").trigger('change');
            
          });
          $("#categoryId").val(commonVariables.categoryId);
          $("#categorypathText").val(commonVariables.categoryPath);         
          $("#catFacetDname").hide();
          $('input[name="displayDepFacet"][value=N]').attr('checked', true);
          $("#depFacetId").val("");
          $("#facetAttributeValueId").val("");

        //}
        

        $("img[name='removeCurrentFacet']").die();
        $("img[name='removeCurrentFacet']").live("click",function() {
          $(this).parent().parent().prev("tr").remove();
          $(this).parent().parent().remove();
        });

           });
      
      $(".ui-datepicker-trigger, #startDate, #endDate").click(function() {
        $('.startdate-error').text('');
        $('.enddate-error').text('');
      });
      
      $("#removeFacetButton").click(function () {
        if(counter==2){
            return false;
        }   
        counter--;
        $("#TextBoxDivFacet" + counter).remove();
            });
      
      $("[name=facetDisplay]").click(function() {
        var val = $("[name=facetDisplay]:checked").val();
        if (val == "N") {
          $("[name=displayContext]").attr('checked', false);
          $("[name=displayContext]").attr("disabled", true);
          $("[name=displayContext]").attr("title", "This entire facet is currently hidden. To show the facet on this page, you must make the entire facet 'displayed'.");
          displayFacet("N");
        } else {
          $("[name=displayContext]").removeAttr("title");
          $("[name=displayContext]").attr('checked', true)
          $("[name=displayContext]").attr("disabled", false);
          displayFacet("Y");
        }
      
      });
      
      $("#create").click( function(e) {
        
        /************** validation ***************/
        
        var systemname = $("#systemname").val();
        var displayname = $("#displayname").val();
        var attributes = $("#attributes").val();
        var facetDisplay = $("input[name=facetDisplay]:checked").val();
        var position = $("#position option:selected").text();
        var startdate = $("#startDate").val();
        var enddate = $("#endDate").val();
        var assetid = $("#assetid").val();        
        var minimim = Number($("#minimim").val());
        var maximum = Number($("#maximum").val());

        var displayRecurrence = $("input[name=displayRecurrence]:checked").val();
        var displayFacetRemoveLink = $("input[name=displayFacetRemoveLink]:checked").val();
        var displayMobileFacet = $("input[name=displayMobileFacet]:checked").val();       
        var displayMobileFacetRemoveLink = $("input[name=displayMobileFacetRemoveLink]:checked").val();             
          
        var dependentFacet = $("input[name=dependentFacet]:checked").val();       
        var displayPage = $("input[name=displayPage]:checked").val();         
        var categoryId = $("#categoryId").val();          
        var mode = $("#mode option:selected").val();
        var text = $("#create").text();
        $("#minmax-error").html('');
        
        var tbodylength = $("#bodycontentSorter tr").length;
          var promoteList = [];
          $('#bodycontentSorter tr').each(function() {
            var promoteValues = {};
            var attributeValueId = $(this).attr("id");    
            var attributeValue = $(this).children().eq(0).text();   
            // Change done for SPT 3921  - Start 
            //var sortOrder = $(this).children().eq(1).text();
            var sortOrder = $(this).children().eq(1).find('input').val();
            // Change done for SPT 3921  - End 
            var attrValuedisplay = "N"; 
            
            promoteValues.attributeValueId = attributeValueId;
            promoteValues.attributeValue = attributeValue;
            promoteValues.sortOrder = sortOrder;
            promoteValues.attrValuedisplay = attrValuedisplay;
            promoteList.push(promoteValues);
          });
          
          var bodycontentlength = $("#bodycontent tr").length;
          var excludeList = [];
      
          // Add page when user is not clicking the 'Add page' button second time
          if($(".sub_cat_facet").css("display") == "block"){
            var editTrigger = 0;  // trigger for checking the category wrapper
            var categoryId = $("#categoryId").val();
            var facetJson = {};

            $.each(self.categoryWrapper, function(row, field) {
              if(field.categoryId == categoryId){
                editTrigger = 1;
              }
            });
            if(editTrigger == 0){
              var subCat = (($("#applySubCategory").is(':checked')) ? "Y" : "N" );
              facetJson.applySubCategory = subCat;
              facetJson.displayDepFacet = $("input[name=displayDepFacet]:checked").val();
              facetJson.displayContext = $("input[name=displayContext]:checked").val();
              facetJson.categoryId = categoryId;
              facetJson.depFacetId =  $("#depFacetId").val();
              facetJson.depFacetText= $("#depFacetId option:selected").text();
              facetJson.facetAttributeValueId = $("#facetAttributeValueId").val();
              facetJson.categoryPath = $("#categorypathText").val();
              //self.categoryWrapper.push(facetJson);
              console.log("old json");
              console.log(facetJson);

              self.categoryWrapper = Pages.toJSON();
            }
          } else {
            $.each(self.categoryWrapper, function(index, value) {
              if(index == $("#currentEditFacet").val()){
                var updateTrigger = 0;
                $.each(self.categoryWrapper, function(row, field) {
                  if(index != row && field.categoryId == $("#categoryId_"+index).val()){
                    updateTrigger = 1;
                  }
                });
                if(updateTrigger == 0){
                  var subCat = (($("#applySubCategory_"+index).is(':checked')) ? "Y" : "N" );
                  if($("input[name=displayDepFacet"+index+"]:checked").val() == "N"){
                    var depFacetId = '';
                    var depFacetText = '';
                    var facetAttributeValueId = '';
                  }else{
                    if($("#depFacetId_"+index).val() != undefined){
                      var depFacetId = $("#depFacetId_"+index).val();
                      var depFacetText = $("#depFacetId_"+index+" option:selected").text();
                      var facetAttributeValueId = $("#attributeName_"+index).attr("key");
                      var attributeName = $("#attributeName_"+index).val();
                      
                    } else {
                      var depFacetId = $("#depFacetId").val();
                      var depFacetText = $("#depFacetId option:selected").text();
                      var facetAttributeValueId = $("#attributeName").attr("key");
                      var attributeName = $("#attributeName").val();
                    }
                  }
                  var attributeName = $("#attributeName").val();
                  self.categoryWrapper[index].displayDepFacet = $("input[name=displayDepFacet"+index+"]:checked").val();
                  self.categoryWrapper[index].displayContext = $("input[name=displayContext"+index+"]:checked").val();
                  self.categoryWrapper[index].applySubCategory = subCat;
                  self.categoryWrapper[index].categoryId = $("#categoryId_"+index).val();
                  self.categoryWrapper[index].categoryPath = $("#categorypathText_"+index).val();
                  self.categoryWrapper[index].depFacetId = depFacetId;
                  self.categoryWrapper[index].depFacetText = depFacetText;
                  self.categoryWrapper[index].facetAttributeValueId = facetAttributeValueId;
                  $("#addFacetButton").hide();
                  $("#addFacetButton1").show();
                  self.setCategoryTreeView('');
                } else {
                  $("#addFacetButton").focus();
                  $(".addFacetButton-error").html('Selected category already exists');
                } 
              }

            });         
          }
          

        //if (!validate()) {  
          if(text == "CREATE") {
            self.categoryWrapper = Pages.toJSON();

            // Remove deptFacetName from self.categoryWrapper
            for (i in self.categoryWrapper) {
              delete self.categoryWrapper[i].depFacetText;
              self.categoryWrapper[i].applySubCategory = (self.categoryWrapper[i].applySubCategory) ? "Y" : "N";
              delete self.categoryWrapper[i].depFacetId;
              self.categoryWrapper[i].depFacetId=null;
              self.categoryWrapper[i].valid=null;

              if(displayFacet() == "N"){
                self.categoryWrapper[i].displayDepFacet = "N";
              }
            }
            self.facetsActionBody.excludeList = self.checkedItem;
            self.facetsActionBody.promoteList = promoteList;
            self.facetsActionBody.systemName = systemname;
            self.facetsActionBody.displayName = displayname;
            self.facetsActionBody.facetId = "";
            self.facetsActionBody.sortType = self.sortType;
            self.facetsActionBody.minValue = minimim;
            self.facetsActionBody.maxValue = maximum;
            self.facetsActionBody.facetDisplay = facetDisplay;
            self.facetsActionBody.attributeId = self.selectedFacetId;
            self.facetsActionBody.attributeName = attributes;
            self.facetsActionBody.startDate = startdate;
            self.facetsActionBody.endDate = enddate;
            self.facetsActionBody.displayMode = mode;
            self.facetsActionBody.glossaryItem = assetid;
            self.facetsActionBody.displayFacetRemoveLink = displayFacetRemoveLink;
            self.facetsActionBody.displayMobileFacet = displayMobileFacet;
            self.facetsActionBody.displayMobileFacetRemoveLink = displayMobileFacetRemoveLink;
            self.facetsActionBody.displayRecurrence = displayRecurrence;
            self.facetsActionBody.categoryWrapper = self.categoryWrapper;       
            self.getAction(self.facetsActionBody,"create", "", function(response){
              if(response.generalPurposeMessage !=null){
                $.each(response.generalPurposeMessage, function(index,value){
                  if(value == "displayName:May not be empty"){
                    var displayName = value.split(':');
                    var msg = displayName[1];
                    $("#displayname").focus();
                    $("#displayname").attr('placeholder', msg);
                    $("#displayname").addClass("error_text");
                    $("#displayname").live('input', function() {
                      $(this).removeClass("error_text").removeAttr('placeholder');
                    });
                  }
                  if(value == "systemName:May not be empty"){
                    var systemName = value.split(':');
                    var msg = systemName[1];
                    $("#systemname").focus();
                    $("#systemname").attr('placeholder', msg);
                    $("#systemname").addClass("error_text");
                    $("#systemname").live('input', function() {
                      $(this).removeClass("error_text").removeAttr('placeholder');
                    });
                  }
                  if(value == "addPages:Pages may not be empty"){
                    var addPages = value.split(':');
                    var msg = addPages[1];
                    $("#addFacetButton1").show();
                    $("#addFacetButton").hide();
                    $("#addFacetButton1").focus();
                    $(".addFacetButton-error").html(msg);
                  }
                  if(value == "attributeId:May not be empty"){
                    var attributes = value.split(':');
                    var msg = attributes[1];
                    $("#attributes").focus();
                    $("#attributes").attr('placeholder', msg);
                    $("#attributes").addClass("error_text");
                    $("#attributes").live('input', function() {
                        $(this).removeClass("error_text").removeAttr('placeholder');
                    });
                                }
                });
              }

            });
          } else {
            self.categoryWrapper = Pages.toJSON();
            // Remove deptFacetName from self.categoryWrapper
            for (i in self.categoryWrapper) {
              delete self.categoryWrapper[i].depFacetText;
              self.categoryWrapper[i].applySubCategory = (self.categoryWrapper[i].applySubCategory) ? "Y" : "N";

              if(displayFacet() == "N"){
                self.categoryWrapper[i].displayDepFacet = "N";
              }
            }
            var editFacitId = self.updateData.attr('id');
            var categoryWrapper = [];
            var categoryvalue = {};
            self.facetsActionBody.excludeList = self.checkedItem;
            self.facetsActionBody.promoteList = promoteList;
            self.facetsActionBody.systemName = systemname;
            self.facetsActionBody.displayName = displayname;
            self.facetsActionBody.facetId = editFacitId;
            self.facetsActionBody.sortType = self.sortType;
            self.facetsActionBody.minValue = minimim;
            self.facetsActionBody.maxValue = maximum;
            self.facetsActionBody.facetDisplay = facetDisplay;
            self.facetsActionBody.attributeId = self.editValue.attributeId;
            self.facetsActionBody.attributeName = attributes;
            self.facetsActionBody.startDate = startdate;
            self.facetsActionBody.endDate = enddate;
            self.facetsActionBody.displayMode = mode;
            self.facetsActionBody.glossaryItem = assetid;         
            self.facetsActionBody.displayFacetRemoveLink = displayFacetRemoveLink;
            self.facetsActionBody.displayMobileFacet = displayMobileFacet;
            self.facetsActionBody.displayMobileFacetRemoveLink = displayMobileFacetRemoveLink;
            self.facetsActionBody.displayRecurrence = displayRecurrence;          
            self.facetsActionBody.categoryWrapper = self.categoryWrapper;
            self.getAction(self.facetsActionBody,"update", "", function(response){
              if(response.generalPurposeMessage == null){
                response.generalPurposeMessage={}
              }
              $.each(response.generalPurposeMessage, function(index,value){
                if(value == "displayName:May not be empty"){
                  var displayName = value.split(':');
                  var msg = displayName[1];
                  $("#displayname").attr('placeholder', msg);
                  $("#displayname").addClass("error_text");
                  $("#displayname").live('input', function() {
                    $(this).removeClass("error_text").removeAttr('placeholder');
                  });
                }
                if(value == "systemName:May not be empty"){
                  var systemName = value.split(':');
                  var msg = systemName[1];
                  $("#systemname").attr('placeholder', msg);
                  $("#systemname").addClass("error_text");
                  $("#systemname").live('input', function() {
                    $(this).removeClass("error_text").removeAttr('placeholder');
                  });
                }
                if(value == "addPages:Pages may not be empty"){
                  var addPages = value.split(':');
                  var msg = addPages[1];
                  $("#addFacetButton1").show();
                  $("#addFacetButton").hide();
                  $("#addFacetButton1").focus();
                  $(".addFacetButton-error").html(msg);
                }
              });
            });
          } 
        //} 
      });
      
      $("#cancel").click(function() {
        self.tblbodyempty();
      });
      
      // Add cancel Button in the Find for Faceting Field / Data Source  and attached cancel      
      $("#cancelAttributes").click(function() {
        $("#Facet_attr").hide();
      });

    }, 
    
    updateFacetValues : function(responseData) {
      var self = this;
      var tbody = $('<tbody id="bodycontent">');
      var SortedText ="";
      $.each(responseData.rows, function(index, value) {
        var sorterbtn, btnclass;
          sorterbtn = 'none';
          //btnclass = 'btnClsshow';
        
        //SPT 2200BT - FACELIFT Facets - Specified sort order numbers should be removed on the Create and Edit Facet pages when switching sort order. 
        //If in the Create or Edit Facet pages, when the user changes the sort order from specific order sequence to either Alphabetical order (A-Z) or Number of results sorted most results to least, then any boxes that have
        // a specific order sequence should be cleared (as there can't be a specific order for Alphabetical order (A-Z) or Number of results sorted most results to least).
        if($('input:radio[name=sortType]:checked').val() =="specificOrderSequence"){
          if(self.editValue.promoteList != undefined && self.editValue.promoteList.length > 0 ){
            for(var i =0; i < self.editValue.promoteList.length; i++){
              if(self.editValue.promoteList[i].attributeValueId == value.attributeValueId){
                sorterbtn = 'block';
                btnclass = 'btnClshide';
                SortedText ="Sorted";
                break;
              }else{            
                sorterbtn = 'none';
                btnclass = 'btnClsshow';
              }
            }
          }
        }
        //SPT 2200BT - FACELIFT Facets - Specified sort order numbers should be removed on the Create and Edit Facet pages when switching sort order. 
        //If in the Create or Edit Facet pages, when the user changes the sort order from specific order sequence to either Alphabetical order (A-Z) or Number of results sorted most results to least, then any boxes that have
        // a specific order sequence should be cleared (as there can't be a specific order for Alphabetical order (A-Z) or Number of results sorted most results to least).
        if($('input:radio[name=sortType]:checked').val() =="specificOrderSequence"){
        // Code Changes by Asheesh for the Bug SPT-3285 - Start
          if($("#bodycontentSorter tr") != undefined && $("#bodycontentSorter tr").length > 0){
            
            $("#bodycontentSorter tr").each(function() {
              var trId = $(this).closest('tr').attr("id");
              if(trId == value.attributeValueId){
                sorterbtn = 'block';
                btnclass = 'btnClshide';          
                SortedText ="Sorted";
                return false;
              }else{            
                sorterbtn = 'none';
                btnclass = 'btnClsshow';
              }
  
            });
          }
        }
        // Code Changes by Asheesh for the Bug SPT-3285 - End
        
        /**************Writen by Kavinraj.M Date - 17/3/2013 start here ****************/
        
        var valueCheck ='';
        if(self.editValue != null && self.editValue.excludeList != null && self.editValue.excludeList.length > 0){
          self.chkValue(value.attributeValueId, function(callbackVal){
            if(!callbackVal)
              valueCheck = 'checked = "true"';
          });
        }else{
          self.chkValue(value.attributeValueId, function(callbackVal){
            if(!callbackVal)
              valueCheck = 'checked = "true"';
          });
        }
          
        var tr = $('<tr id='+ value.attributeValueId +'><td>'+ value.attributeValue +'</td><td class="center_td"><input type="checkbox" '+ valueCheck +' name="check_'+value.attributeValueId+'" class="check"></td><td><input id="btn_'+value.attributeValueId+'" class="button ctrlOrder btn bbbluebtn '+btnclass+'" type="button" value="Control Order" ><span id="span_'+value.attributeValueId+'" style="display:'+sorterbtn+';">'+SortedText+'</span></td></tr>');
        tbody.append(tr);         
        
        /**************Writen by Kavinraj.M Date - 17/3/2013 end here ****************/
      });
      
      var thead = $('<thead><tr><td class="center_td">Name<img src="../themes/default/images/bestbuy/sort_arrow.png"></td><td class="center_td">Do not Display(only this page)<input type="checkbox" id="selectall"></td><td class="center_td">Display Order</td></tr></thead>');
      
      var headertr = $("<tr><td><input type=\"text\" class=\"search-txtbox\" id=\"attributeValueSearch\" name=\"attributeValueSearch\"></td><td>&nbsp;</td><td>&nbsp;</td></tr>");
      
      var facetContent =  $("#facetvaluescontent");
      facetContent.empty();
      $('#facetvaluesTitle').html("Facet Values")
    
      //facetContent.before("<h4 id='facetvaluesTitle'>Facet Values</h4>");
      var facetValues = $('<table class="table table-striped table-bordered tab_table" cellpadding="0" cellspacing="0" border="0" id="facetvalues">');
      facetValues.append(thead);
      facetValues.append(headertr);
      facetValues.append(tbody);
      facetValues.appendTo(facetContent);
      var sortTypeValue = $('input[name="sortType"]:checked').val();
      if (sortTypeValue === "specificOrderSequence") {
        $('.ctrlOrder').show();
      } else {
        $('.ctrlOrder').hide();
      }
      
      // When user enters a value in a search box and clicks on enter 
      // in that case , we will search the attribute and update the values 
      $('input[name="attributeValueSearch"]').keypress(function(e) {
        
          if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
            code= (e.keyCode ? e.keyCode : e.which);
            if (code == 13) {
                
                $("#loadAttributeValue").val("");
                self.attributeValueSearch=$("#attributeValueSearch").val();
                self.facetsActionBodyPagnation = {};
                self.facetsActionBodyPagnation.pageIndex = 1;
                self.facetsActionBodyPagnation.rowsPerPage = commonVariables.requestBody.rowsPerPage;
                self.facetsActionBodyPagnation.sortColumn = "attributeValue";
                self.facetsActionBodyPagnation.sortOrder = commonVariables.requestBody.sortOrder;
                var attributeValue = $("#attributeValueSearch").val();
                if(attributeValue === undefined || attributeValue === "" ){
                  attributeValue="";
                }
                // Call the ajax method
                self.facetsActionBodyPagnation.searchColumnValues = [{"key":"attributeId","value": ""+self.selectedFacetId +""},{"key":"attributeValue","value": ""+attributeValue+"" }];
                self.getAction(self.facetsActionBodyPagnation,"load", "", function(responseData){
                  self.updateFacetValues (responseData);
                  $("#attributeValueSearch").val(self.attributeValueSearch);
                  self.popupPagination.data = responseData.data; 
                  self.popupPagination.currentObj = self.popupPagination;
                  self.popupPagination.render("#tablefooter");

                });
            }
          }
      });
      // $("#attributeValueSearch").val(self.attributeValueSearch);
       
       if(self.checkAll){
        $("#selectall").attr('checked', true);
        $("#selectall").click();
      }
    },
    // In the case of Edit facet page. All the promote list are sorted on the position 
    sortFacetOrderValues: function(){
        
              var sorting =[[1,0]];
                  // clear cash vale from tablesorter function
              $("#facetvalueorder").unbind('appendCache applyWidgetId applyWidgets sorton update updateCell')
              .removeClass('tablesorter').find('thead th').unbind('click mousedown').removeClass('header headerSortDown headerSortUp');
              // add parser through the tablesorter addParser method
              $.tablesorter.addParser({
                id: 'inputs',
                is: function(s) {
                  return false;
                },
                format: function(s, table, cell, cellIndex) {
                  var $c = $(cell);
                  // return 1 for true, 2 for false, so true sorts before false
                  if (!$c.hasClass('sortOrder')) {
                    $c
                    .addClass('sortOrder')
                    .bind('keyup', function() {
                      $(table).trigger('updateCell', [cell, false]); // false to prevent resort
                    });
                  }
                  return $c.find('input').val();
                },
                type: 'numeric'
              });     
              $("#facetvalueorder").tablesorter({headers:{1:{sorter:'inputs'}}});
            
              // sort on the Sortt Order column 
              $("#facetvalueorder").trigger("sorton",[sorting]);  
               $("th[name=removeAllTh]").unbind('click');
    },
    loadAttribute : function() {
      var self = this;
      $("#attributes").val(self.selectedFacetName);     
      $("#Facet_attr").hide();
      var buttonInTD = "";
      self.facetsActionBodyPagnation = {};
      self.facetsActionBodyPagnation.pageIndex = 1;
      self.facetsActionBodyPagnation.rowsPerPage = commonVariables.requestBody.rowsPerPage;
      self.facetsActionBodyPagnation.sortColumn = "attributeValue";
      self.facetsActionBodyPagnation.sortOrder = commonVariables.requestBody.sortOrder;
      var attributeValue = $("#attributeValueSearch").val();
      if(attributeValue === undefined || attributeValue === "" ){
        attributeValue="";
      }
      
      self.facetsActionBodyPagnation.searchColumnValues = [{"key":"attributeId","value": ""+self.selectedFacetId +""},{"key":"attributeValue","value": ""+attributeValue+"" }];
      var thead = $('<thead id="dragablehead"><tr><th class="center_td">Name</th><th class="center_td">Sort Order</th><th class="center_td" name="removeAllTh"><input type="button" value="Remove All" class="btn btn-danger" id="removeall"></th></tr></thead>');
      var facetOrderContent =  $("#facetvalueordercontent");
      facetOrderContent.empty();      
      var facetOrder = $('<table class="table table-striped table-bordered tab_table" cellpadding="0" cellspacing="0" border="0" id="facetvalueorder">'); 
        if($("#dragablehead tr").length == 0){ // To append thead only once
          $("#facetvalueorderTiltle").remove();
          facetOrderContent.before("<h4 id='facetvalueorderTiltle'>Facet Value Order</h4>");
          facetOrder.append(thead);
        } 
      var facetOrderBody = $('<tbody id="bodycontentSorter">');
      facetOrderBody.empty();
      var facetOrderDataTable = null; 
      if(self.editValue != undefined){
        if(self.editValue.promoteList != undefined) {
          //$("#bodycontentSorter tr").empty();
          $.each(self.editValue.promoteList, function(index, value){    
            var tr =  $('<tr id='+value.attributeValueId+'></tr>');
              tr.append('<td>' + value.attributeValue + '</td>');
              //tr.append('<td class="index">'+value.sortOrder+'</td>');
              //tr.append("<td><input class='removeTd btn btn-danger'  type='button'  value='Remove'></td>");
              // Change done for 4009 or 3921- Start 
              tr.append('<td class="index"><input class=\"sortOrder\"  type=\"text\" value='+value.sortOrder+'></td>');
              tr.append("<td><input class='removeTd btn btn-danger' type='button' value='Remove'></td>");
              tr.append('<input type=\"hidden\" class=\"position\" name=\"hiddenSortOrder\" value='+value.sortOrder+' >');
              // Change done for 4009 - End 
            facetOrderBody.append(tr);        
          });
        }
      } 
      facetOrder.append(facetOrderBody);      
      self.getAction(self.facetsActionBodyPagnation,"load", "", function(responseData){
        self.updateFacetValues (responseData);
        facetOrder.appendTo(facetOrderContent);
        self.dragAndDrop();
        //SPT-4148 BT: FACELIFT:Create /Edit Facet Order of attributes displayed under Facet Value Order section changes in Edit Facet Page 
        if(self.editValue != undefined){
          if(self.editValue.promoteList != undefined && self.editValue.promoteList.length >0) { 
            // in the case of Edit facet page. All the promote list are sorted on the position 
             self.sortFacetOrderValues()  
            }
         }      
        self.popupPagination.data = responseData.data; 
        self.popupPagination.currentObj = self.popupPagination;
        Clazz.com.components.pagination.js.Pagination.prototype.doPagination  = function(searchCriteria, pageIndex,rowsPerPage) {
          self.facetsActionBodyPagnation.pageIndex = pageIndex;
          self.facetsActionBodyPagnation.rowsPerPage = rowsPerPage;
          self.getAction(self.facetsActionBodyPagnation,"load", "", function(responseData){
            self.updateFacetValues (responseData);
            $('#tablefooter').find("input").val(pageIndex);
            $('#totalCountOfPages').text(responseData.data.totalCountOfPages);
          });
        };
        self.popupPagination.render("#tablefooter");
        $('#tablefooter > div').waitUntilExists(function(){
          $('#tablefooter').children().css({'position':'relative', 'bottom':'0' });
        });
        //facetOrderDataTable = $('#facetvalueorder').dataTable(); 
      });
      
      $(".ctrlOrder").die('click');
      $(".ctrlOrder").live('click', function(){
        position = $("#facetvalueorder tr").length;
        var trId = $(this).closest('tr').attr("id");
        var tr =  $('<tr id='+$(this).closest('tr').attr("id")+'></tr>');
        //tr.attr('id',$('#facetvalueorder tr').length);
        tr.append('<td>' + $(this).closest('tr').children().eq(0).text() + '</td>');
        // Change done for 4009 or 3921- Start 
        tr.append('<td class="index"><input class=\"sortOrder\" type=\"text\" value='+position+'></td>');
        tr.append("<td><input class='removeTd btn btn-danger' type='button' value='Remove'></td>");
        tr.append('<input type=\"hidden\" class=\"position\" name=\"hiddenSortOrder\" value='+position+' >');
        // Change done for 4009 - End 
        $(this).hide();
        $(this).removeClass("btnClsshow");
        $(this).addClass("btnClshide");
        //$(this).parent().children().eq(1).text("Sorted");
        $("#span_"+trId).css("display","block");
        $("#span_"+trId).text("Sorted");          
        $(this).parent().children().eq(1).text("Sorted");
      facetOrderBody.append(tr);
      self.dragAndDrop();
      });

    },
    
    dragAndDrop : function(){
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
          //$(this).html(i + 1);
          // Change done for 4009 or 3921 - Start 
          ($(this)[0].children)[0].value = i+1;
          $(this).closest('tr').find('input[name=hiddenSortOrder]').val(i+1);
          // Change done for 4009 or 3921 - End 
        });
      };

      $("#facetvalueorder tbody").sortable({
        helper: fixHelperModified,
        stop: updateIndex
      });
      //.disableSelection();      
    },
    
    facetTemplateEdit : function(categoryId, newtr, depFacetId, facetAttributeValueId) {
      var self = this;
      var option = "";      
      self.header.contentType = "application/json";
      self.header.requestMethod = "GET";
      self.header.dataType = "json";

      self.header.webserviceurl = window.commonVariables.webserviceurl + "categoryFacet/depFacetsForCategory/"+ categoryId +"/"+ self.editValue.facetId;
      
      self.facetsListener.getfacets(self.header, function(response) {     
        var facetDiv = $(document.createElement('div')).attr("class","banner_border").attr("display","none");
          $.each(response.rows, function(index, value) {
          option += '<option value="'+value.facetId+'">'+ value.facetName+'</option>';  
          facetDiv.html('<div class="flt_right"></div><strong>Select Facet Display Name:</strong><br><em>Grayed out facets are dependent on this facet and cannot be selected.</em><select id="depFacetId"><option>Please Select an Option</option>'+ option +'</select>&nbsp;&nbsp;<input type="text" class="attributeName" id="attributeName">&nbsp;&nbsp;');
          
          $(facetDiv).appendTo(newtr);
        });
        
        $(facetDiv).find("option").each(function(index,value){
          if($(value).val() == depFacetId) {
            $(value).attr("selected", "selected");
            self.header.contentType = "application/json";
            self.header.requestMethod = "GET";
            self.header.dataType = "json";
            self.header.webserviceurl = window.commonVariables.webserviceurl + commonVariables.facetsContext + "/attributeValue/" + depFacetId;
            
            self.facetsListener.getfacets(self.header, function(response){ 
              var autocompleteval = [];
              $.each(response.rows, function(index, value){
                autocompleteval.push({"key":value.attributeValueId,"value":value.attributeValue});
              });
              
              $(".attributeName" ).autocomplete({
                source: autocompleteval
              });
              
              $(".attributeName" ).on( "autocompleteselect", function( event, ui ) {
                $(this).attr("key",ui.item.key);
              });

              $.each(autocompleteval, function(index, value){
                if(facetAttributeValueId == value.key) {
                  $(facetDiv).find('input').attr("key",value.key);
                  $(facetDiv).find('input').val(value.value); 
                }
              }); 
            });
          }
        });
        
        $(facetDiv).find('select').change(function() {
        
          var id = $(this).val();
          self.header.contentType = "application/json";
          self.header.requestMethod = "GET";
          self.header.dataType = "json";
          self.header.webserviceurl = window.commonVariables.webserviceurl + commonVariables.facetsContext + "/attributeValue/" + id;
          
          self.facetsListener.getfacets(self.header, function(response){  
          
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
      }); 
    
    },
    
    trClickevent : function(obj) {
      var self =this;   
      var displayStatus = $(obj).children().eq(1).text();
      var DisplayDepFacetStausValue = displayStatus.split(":");
      var outerindex = $(obj).attr("id");
      $("#currentEditFacet").val(outerindex);
      var checkedflag = DisplayDepFacetStausValue[1].replace(/^\s+|\s+$/g,'');
      addtr = obj;

      var val = $(obj).children().eq(2).text().replace(/^\s+|\s+$/g,'');
      var radiobtn = $(obj).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children("#displayDepFacetYes").children("input[name^='displayDepFacet']");

      if(val.length == 0){
        var radioNButton = $(obj).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children("#displayDepFacetYes").children("input[name^='displayDepFacet'][value='N']");
        $(radioNButton).attr('checked', true);
        var idToHide = $(radioNButton).attr('attrDep');
        $("#" + idToHide).hide();
        
      } else {
        var  DependentFacet = $(obj).children().eq(2).text();
        var  DependentFacetValue = DependentFacet.split(":");
        var DependentFacetValueFlag = DependentFacetValue[1].replace(/^\s+|\s+$/g,'');
        if (DependentFacetValueFlag != ""){
          var radioYButton = $(obj).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children("#displayDepFacetYes").children("input[name^='displayDepFacet'][value='Y']");
          $(radioYButton).attr('checked', true);
          
          var idToHide = $(radioYButton).attr('attrDep');
          $("#" + idToHide).show();
        }
      }
      if($(addtr).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children("#displayDepFacetYes").children("input[name^='displayDepFacet"+outerindex+"']:checked").val() == "Y") {
        var categoryId = $(radiobtn).parent().siblings("input#categoryId_"+outerindex).val();
        var newtr = $("<tr class='addPage' id="+outerindex+" style='display:none;'></tr>");
        var attrDep = "catFacetDname"+outerindex;
        
        self.header.contentType = "application/json";
        self.header.requestMethod = "GET";
        self.header.dataType = "json";

        self.header.webserviceurl = window.commonVariables.webserviceurl + "categoryFacet/"+ categoryId;
        
        self.facetsListener.getfacets(self.header, function(response) {     
          var facetDiv = $(document.createElement('div')).attr("class","banner_border").attr("display","none");
          $(facetDiv).empty();
          var option = '';
          $.each(response.rows, function(index, value) {
            option += '<option value="'+value.facetId+'">'+ value.facetName+'</option>';  
            facetDiv.html('<div class="flt_right"></div><strong>Select Facet Display Name:</strong><br><em>Grayed out facets are dependent on this facet and cannot be selected.</em><select id=depFacetId_'+outerindex+'><option>Please Select an Option</option>'+ option +'</select>&nbsp;&nbsp;<input type="text" class="attributeName" id=attributeName_'+outerindex+'><input type="hidden" id="facetAttributeValueId" name="facetAttributeValueId"></input>&nbsp;&nbsp;');
            $(facetDiv).appendTo($(newtr).find("#"+attrDep));
          });
          
          $(facetDiv).find("option").each(function(index,value){
            if($(value).val() == depFacetId) {
              $(value).attr("selected", "selected");
              self.header.contentType = "application/json";
              self.header.requestMethod = "GET";
              self.header.dataType = "json";
              self.header.webserviceurl = window.commonVariables.webserviceurl + commonVariables.facetsContext + "/attributeValue/" + depFacetId;
              
              self.facetsListener.getfacets(self.header, function(response){ 
                var autocompleteval = [];
                $.each(response.rows, function(index, value){
                  autocompleteval.push({"key":value.attributeValueId,"value":value.attributeValue});
                });
                
                $(".attributeName" ).autocomplete({
                  source: autocompleteval
                });
                
                $(".attributeName" ).on( "autocompleteselect", function( event, ui ) {
                  $(this).attr("key",ui.item.key);
                });

                $.each(autocompleteval, function(index, value){
                  if(facetAttributeValueId == value.key) {
                    $(facetDiv).find('input').attr("key",value.key);
                    $(facetDiv).find('input').val(value.value); 
                  }
                }); 
              });
            }
          });

          $(facetDiv).find('select').change(function() {
              var id = $(this).val();
              self.header.contentType = "application/json";
              self.header.requestMethod = "GET";
              self.header.dataType = "json";
              self.header.webserviceurl = window.commonVariables.webserviceurl + commonVariables.facetsContext + "/attributeValue/" + id;
              
              self.facetsListener.getfacets(self.header, function(response){  
              
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
          });
          /* self.facetTemplateEdit(categoryId, $(newtr).find("#"+attrDep), depFacetId, facetAttributeValueId); */
          $("#" + idToHide).show();
        } 
              
      $(radiobtn).bind("change", function(e) {
        var id = $(this).attr('attrDep');
        var temp = e.currentTarget.parentElement;
        var categoryId = $(temp).siblings("input#categoryId_"+outerindex).val();
        if($(this).val() === "Y"){
          self.depententTreeVal(categoryId, id);
          $("#" + id).show();
        }else {
          $("#" + id).hide();
        }
      });
        
      /*$(radiobtn).bind("change", function(e) {
        if($(this).val() == "Y"){
          $("#" + idToHide).show();
        } else {
          $("#" + idToHide).hide();
        }
      });*/
      if(checkedflag == "Hidden") {
        $(obj).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children("#DisplayHideNo").children("input[name='displayContext"+outerindex+"']").attr("checked", "checked");
      } else {
        $(obj).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children("#DisplayHideYes").children("input[name='displayContext"+outerindex+"']").attr("checked", "checked"); 
      } 
      
      $(".dependentfacet").show();
      $(".addPage").hide();
      $(obj).next(".addPage").show();
      $(obj).hide();
    
    },
    
    categoryWrapperPages : function(){
      var counter = 2;
      var self = this;      
      var addedDependentFacets = $("#addedDependentFacets");
      var subCat = (($("#applySubCategory").is(':checked')) ? "Y" : "N" );
      $(".sub_cat_facet").hide();
      addedDependentFacets.empty();
      $.each(self.categoryWrapper, function(index, value) {
        var facetJson = {};
        value.categoryPath = value.categoryPath.replace(/%20/g ,'');
        var categoryId = value.categoryId;
        var depFacetId = value.depFacetId;
        var facetAttributeValueId = value.facetAttributeValueId;
        var depFacetValueId = value.depFacetId;
        var tr = $("<tr class='dependentfacet' id="+index+"></tr>");
        var newtr = $("<tr class='addPage' id="+index+" style='display:none;'></tr>");
        var attrDep = "catFacetDname" + index;
        
        facetJson.applySubCategory = value.applySubCategory;
        facetJson.displayDepFacet = value.displayDepFacet;
        facetJson.displayContext = value.displayContext;
        facetJson.categoryId = value.categoryId;
        facetJson.depFacetId =  value.depFacetId;
        facetJson.depFacetText = value.depFacetText;
        facetJson.facetAttributeValueId = value.facetAttributeValueId;
        facetJson.categoryPath = value.categoryPath;
        self.existingCategoryWrapper.push(facetJson);
        if(value.depFacetId != "" && value.depFacetId != null) {
          self.header.contentType = "application/json";
          self.header.requestMethod = "GET";
          self.header.dataType = "json";
          self.header.webserviceurl = window.commonVariables.webserviceurl + "categoryFacet/depFacetsForCategory/"+ categoryId +"/"+ self.editValue.facetId;
          
          self.facetsListener.getfacets(self.header, function(response) {
            $.each(response.rows, function(index, value) {
              if (depFacetValueId ==  value.facetId) {
                self.depFacetIdText = value.facetName;
              }
            }); 
              var displayContextText = "Displayed";
                if(value.displayContext == "Y") {
                  displayContextText = "Displayed";
                } else if(value.displayContext == "N") {
                  displayContextText = "Hidden";
                }
              tr.append("<td>Page : "+value.categoryPath+"<input type='hidden' name='rmcatId' value="+value.categoryId+"></td>");
              tr.append("<td>Display Status: "+displayContextText+"</td>");
              tr.append("<td>Facet Display ID : "+self.depFacetIdText+"</td>");
              tr.append("<td title='Remove Facet' style='cursor:pointer;' align='right'><span name='removeFacet'><img src='../themes/default/images/bestbuy/close.png'></span></td>");
              
              newtr.append('<td colspan="4" title="Remove Facet"><div class="sub_cat_facet_dynamic_div" style="padding:0;"><div class="flt_right" id='+index+' name="closePage"><img  style="cursor:pointer;margin-top:10px;" src="../themes/default/images/bestbuy/close.png"></div>Selected Category <sup>*</sup><input type="text" id=categorypathText_'+index+' maxlength="50" value="'+ value.categoryPath +'" readonly>|<input type="text" id=categoryId_'+index+' maxlength="50" value="'+ categoryId +'"readonly><br><input type="checkbox" id=applySubCategory_'+index+'>&nbsp;Apply to Sub-Category <br><div class="flt_left">Dependent Upon Another Facet ?</div> &nbsp;<div id="displayDepFacetYes" class="flt_left"><input type="radio" value="Y" name=displayDepFacet'+index+' attrDep="' + attrDep + '" id="Yes'+ index + '">Yes <input type="radio" value="N" name=displayDepFacet'+index+' attrDep="' + attrDep + '" id="No'+ index + '">No</div><br><div id="' + attrDep + '"></div>Display or Hide in this Page ?<label class="radio inlineFacet" id="DisplayHideYes"><input type="radio" name=displayContext'+index+' value="Y" checked="">Yes</label><label class="radio inlineFacet" id="DisplayHideNo"><input type="radio" name=displayContext'+index+' value="N" checked="">No</label><br></div></td>');
              
              addedDependentFacets.append(tr);
              addedDependentFacets.append(newtr);
              
              self.facetTemplateEdit(categoryId, $(newtr).find("#"+attrDep), depFacetId, facetAttributeValueId);
            
            
          });
          
        } else {
          var displayContextText = "Displayed";
          if(value.displayContext == "Y") {
            displayContextText = "Displayed";
          } else if(value.displayContext == "N") {
            displayContextText = "Hidden";
          }
          tr.append("<td>Page : "+value.categoryPath+"<input type='hidden' name='rmcatId' value="+value.categoryId+"></td>");
          tr.append("<td>Display Status : "+displayContextText+"</td>");
          tr.append("<td>&nbsp</td>");
          tr.append("<td title='Remove Facet' style='cursor:pointer;' align='right'><span name='removeFacet'><img src='../themes/default/images/bestbuy/close.png'></span></td>");
            
          newtr.append('<td colspan="4" title="Remove Facet"><div class="sub_cat_facet_dynamic_div"><div class="flt_right" id='+index+' name="closePage"><img  style="cursor:pointer;margin-top:10px;" src="../themes/default/images/bestbuy/close.png"></div>Selected Category <sup>*</sup><input type="text" id=categorypathText_'+index+' maxlength="50" value="'+ value.categoryPath +'" readonly>|<input type="text" id=categoryId_'+index+' maxlength="50" value="'+ categoryId +'" readonly><br><input type="checkbox" id=applySubCategory_'+index+'>&nbsp;Apply to Sub-Category <br><div class="flt_left">Dependent Upon Another Facet ?</div> &nbsp; <div class="flt_left" id="displayDepFacetYes"><input type="radio" value="Y" name=displayDepFacet'+index+' attrDep="' + attrDep + '" id="Yes'+ index + '">Yes <input type="radio" value="N" name=displayDepFacet'+index+'  attrDep="' + attrDep + '" id="No'+ index + '">No</div><br><div id="' + attrDep + '"></div>  Display or Hide in this Page ?<label class="radio inlineFacet" id="DisplayHideYes" ><input type="radio" name=displayContext'+index+' value="Y" checked="">Yes</label><label class="radio inlineFacet" id="DisplayHideNo"><input type="radio" name=displayContext'+index+' value="N" checked="">No</label><br></div></td>');
          
          addedDependentFacets.append(tr);
          addedDependentFacets.append(newtr);
          
          //self.facetTemplateEdit(categoryId, $(newtr).find("#"+attrDep), depFacetId, facetAttributeValueId);
          $(tr).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children('div#catFacetDname').hide();
          
        }
        
        $("span[name='removeFacet']").die();
        $("span[name='removeFacet']").live("click",function() {
          var rmindexId = $(this).closest('tr').attr("id");
          $.each(self.categoryWrapper, function(index, value){
            if(index == rmindexId){
              self.categoryWrapper.splice(index, 1);
              return false;
            }
          });
          $(this).closest('tr').next().remove();
          $(this).closest('tr').remove();
        });
        
        $("div[name='closePage']").die();
        $("div[name='closePage']").live("click",function() {
          var rmindexId = $(this).closest('tr').attr("id");
          $.each(self.categoryWrapper, function(index, value){
            if(index == rmindexId){
              self.categoryWrapper.splice(index, 1);
              return false;
            }
          });
          $(this).closest('tr').prev().remove();
          $(this).closest('tr').remove();
        });
        
        if($(tr).attr('id') == 0) {
          setTimeout(function() {
            self.trClickevent($(tr));
          }, 1000);
        }
        
        $(tr).unbind("click");
        $(tr).bind("click", function() {
          self.trClickevent($(this));
        });
        
        
        $("img[name='removeCurrentFacet']").die();
        $("img[name='removeCurrentFacet']").live("click",function() {
          $(this).parent().parent().prev("tr").remove();
          $(this).parent().parent().remove();
          
        });
        $(tr).next(".addPage").children().children('.sub_cat_facet_dynamic_div').children("#displayDepFacetYes").children("input[name='displayDepFacet']").trigger('change');
      });
      
      
    },
    
    
    treeViewClickEvent : function(indexVal){
      var self = this, treeid, path, infoData = {};
      var self = this;
      var catId = "";
      var path = "";
      var option = "";
      var htmlStr = "";
       
      $("span.folder").die(); 
      $("span.folder").live('click', function(e){
        $("span.folder a").removeClass("selected");
        $(this).find("a").addClass("selected");
        
        treeid = $(this).parent().attr('id');
        path = $(this).parent().attr('path');
        $(".addFacetButton-error").html('');
        
        indexVal = $(this).closest("div").parent().attr('id').replace("treeviewContent","");
        console.log($(this).closest("div").parent().attr('id'));
        if(indexVal >= 0){
          $("#categoryPath"+indexVal).val(path.replace(/%20/g ,'')).change();
          $("#categoryId"+indexVal).val(treeid).change();
        } else {
          $("#categoryPath").val(path.replace(/%20/g ,'')).change();
          $("#categoryId").val(treeid).change();
        }
        
        path = path.replace(/\|/g,' / ');
        //$("#categoryId").text("Facet Ordering for: " + decodeURI(path));
        
        var temp = e.currentTarget;
        $(temp).parents("div#treeviewContent").siblings("input#categoryId").val(treeid);
        catId = $(temp).parents("div#treeviewContent").siblings("input#categoryId").val();
        
        if( $("input[name=displayDepFacet]:checked").val() == "Y") {
          self.depententTreeVal(catId, "catFacetDname");
        }
        
        setTimeout(function() {
          $(".bbtreeview").jstree('close_all');
        }, 500);
      });
    },
    
    tblbodyempty : function(){
     $("#bodycontentSorter tr").each(function() {
        var trId = $(this).closest('tr').attr("id");
        $(this).closest('tr').remove();
      });
      $("tbody#bodycontent").children().each(function (index, value){
        if($(value).children().eq(2).text() == "Sorted"){
          $(value).children().eq(2).children().eq(0).removeClass("btnClshide");
          $(value).children().eq(2).children().eq(0).show();
          $(value).children().eq(2).children().eq(1).empty();           
        }
      });
    },
    
    depententTreeVal : function(treeid, id){
      
      var self =this;
      var counter = 2;
      var htmlStr = $(document.createElement('div')).attr("id", 'facet' + counter).attr("class","facet_border"); 
      self.header.contentType = "application/json";
      self.header.requestMethod = "GET";
      self.header.dataType = "json";
      self.header.webserviceurl = window.commonVariables.webserviceurl + "categoryFacet/"+treeid;
      self.facetsListener.getfacets(self.header, function(response) {
        option = '';
        $.each(response.rows, function(index, value){
          option += '<option value="'+value.facetId+'">'+ value.facetName+'</option>';  
          htmlStr.html('<strong>Select Facet Display Name:</strong><br><em>Grayed out facets are dependent on this facet and cannot be selected.</em><br><select id="depFacetId" class="input-mini"><option value="">Please Select an Option</option>'+ option +'</select>&nbsp;<input type="text" id="attributeName" class="attributeName" maxlength="50"><input type="hidden" id="facetAttributeValueId" name="facetAttributeValueId"></input>');
          
          $(htmlStr).find('select').change(function() {
            value = $(this).val();
            self.header.contentType = "application/json";
            self.header.requestMethod = "GET";
            self.header.dataType = "json";
            self.header.webserviceurl = commonVariables.webserviceurl + commonVariables.facetsContext + "/attributeValue/" + value;
            self.facetsListener.getfacets(self.header, function(response){
              var autocompleteval = [];
              $.each(response.rows, function(index, value){
                autocompleteval.push({"key":value.attributeValueId,"value":value.attributeValue});
                $(".attributeName" ).autocomplete({
                  source: autocompleteval
                });
              });
              
              $(".attributeName" ).on( "autocompleteselect", function( event, ui ) {
                var self = this;
                $("#facetAttributeValueId").val(ui.item.key);
                //self.facetdata.push({"facetId":value,"attributeValueId":ui.item.key});
              });
              
            });
            
          });
        });
      });
      
      $("#" + id).empty();
      $("#" + id).append(htmlStr);
      counter++;
    }
    
  });

  return Clazz.com.components.facets.js.Facets;
});