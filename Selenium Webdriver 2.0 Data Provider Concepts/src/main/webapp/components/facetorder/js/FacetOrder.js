define(["framework/WidgetWithTemplate", "facetorder/listener/FacetOrderListener", "navigation/Navigation",] , function() {

	Clazz.createPackage("com.components.facetorder.js");

	Clazz.com.components.facetorder.js.FacetOrder = Clazz.extend(Clazz.WidgetWithTemplate, {
		facetOrderEvent : null,
		facetOrderHeader : null,
		// template URL, used to indicate where to get the template
		templateUrl: "../components/facetorder/template/facetOrder.tmp",
		configUrl: "../../components/facetorder/config/config.json",
		name : commonVariables.facetOrder,
		localConfig: null,
		globalConfig: null,
		paginationContainer:commonVariables.paginationPlaceholder,
		facetOrderListener : null,
		categoryJson : null,
		rootItem: null,
		navigation : null,
		categoryId : null,
		categoryPath : null,
		facetActionBody: {},
		
		initialize : function(globalConfig){
			var self = this;
			self.globalConfig = globalConfig;
			self.navigation = new Clazz.com.components.navigation.js.Navigation();
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
			this.facetOrderListener = new Clazz.com.components.facetorder.js.listener.FacetOrderListener();
		},
		
		loadPage :function(){
			Clazz.navigationController.push(this);
		},

		setSession : function(key,value) {
			if(key !== '') {
				window.localStorage.setItem(key,value);
			}
		},

		postRender : function(element) {
			var self = this;
			self.setSession("pageName",this.name);
			
			setTimeout(function() {
				var treeView = $('<div class="bbtreeview"><ul id="filetree" class="filetree"><li><span><strong>Click here to edit category</strong></span>' + commonVariables.categoryTreeContent + '</li></ul></div>');
				
				$(treeView).jstree({
				}).bind("init.jstree", function(event, data){ 
				}).bind("loaded.jstree", function (event, data) {
					$(".facet_left_cont").children().remove();
					$(".facet_left_cont").append(treeView);
					self.treeViewClickEvent();
					$("span.folder").first().trigger('click');
				});
				/*.bind("select_node.jstree", function(event, data) {
					 // data.inst is the tree object, and data.rslt.obj is the node
					 return data.inst.toggle_node(data.rslt.obj);
				});*/
			}, 200);
		},
		
		getTableValue : function(){
			var self = this,infoData = {};
			
			infoData.tableId = "facetorder1";
			infoData.divId = "facetDiv1";
			infoData.btnId = "btnFacet1";
			infoData.serviceContext = "displayOrder";
			infoData.treeId = self.categoryId;
			self.loadData(infoData, function(callback){
				infoData.tableId = "facetorder2";
				infoData.divId = "facetDiv2";
				infoData.btnId = "btnFacet2";
				infoData.serviceContext = "dependantFacets";
				infoData.treeId = self.categoryId;
				self.loadData(infoData, function(callback){
					infoData.tableId = "facetorder3";
					infoData.divId = "facetDiv3";
					infoData.btnId = "btnFacet3";
					infoData.serviceContext = "hiddenFacets";
					infoData.treeId = self.categoryId;
					self.loadData(infoData, function(callback){});
				});
			});
			self.categoryPath = self.categoryPath.replace(/\|/g,' / ');
			$("#facetPath").text("Facet Ordering for: " + decodeURI(self.categoryPath) + " (" + self.categoryId + ")");
		}, 

		treeViewClickEvent : function(){
			var self = this;
			
			$("span.folder").click(function(){
				var currentobj = $(this);
				
				if($("#btnFacet1").text() == "Save"){
					self.navigation.alertBox("Facet Ordering", "Are you sure you want to perform this action?", function(callback){
						if(callback){
							$("span.folder a").removeClass("selected");
							currentobj.find("a").addClass("selected");
							self.categoryId = currentobj.parent().attr('id');
							self.categoryPath = currentobj.parent().attr('path');
							self.getTableValue();
							$("#btnFacetCancel").attr('style', 'display:none;');
							$("#btnFacet1").text('Edit');
						}
					});
				}else{
					$("span.folder a").removeClass("selected");
					currentobj.find("a").addClass("selected");
					self.categoryId = $(this).parent().attr('id');
					self.categoryPath = $(this).parent().attr('path');
					self.getTableValue();
					$("#btnFacetCancel").attr('style', 'display:none;');
					$("#btnFacet1").text('Edit');
				}
			});
			
			$("#btnFacetCancel").click(function(event){
				$("#facetorder1 tbody").sortable('disable');
				$("#btnFacet1").text("Edit");
				$(this).attr('style', 'display:none;');
				self.getTableValue();
			});
		},
		
		getAction : function(actionBody, action, id, callback){
			var self = this;
			self.facetOrderListener.facetAction(self.facetOrderListener.getActionHeader(actionBody, action, id), function(response){
				callback(response);
			});
		},
		
		loadData : function(infoData, callback){
			var self = this;
				
			self.facetActionBody = {};
			self.getAction(self.facetActionBody, infoData.serviceContext, infoData.treeId, function(response){
				
				$("#" + infoData.tableId + " tbody").children().remove();
				
				if(response != null && response.status == "SUCCESS" && response.rows.length > 0){
					var dataRow = "",
					fixHelperModified = function(e, tr) {
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
								$(this).closest('tr').find('input[class=order_input]').val(i+1);
								$(this).closest('tr').find('input[name=hiddenSortOrder]').val(i+1);
						});
					};
					$.each(response.rows, function(index,value){ //(value.displayOrder != null? value.displayOrder : "")
						dataRow += '<tr id="' + (value.categoryFacetId != null? value.categoryFacetId : "") + '" systemName="'+ (value.systemName != null? value.systemName : "") +'"><td class="index center_td">'+ (index+1) +'</td><td>'+ (value.displayName != null? value.displayName : "" ) +' ( '+ (value.systemName != null? value.systemName : "") +') </td><td>'+ (value.values != null? value.values : "") +'</td><td class="center_td">'+ (value.status != null? value.status : "") +'</td><td><div class="action_icons"><input name="txtOrder" type="text" class="order_input" placeholder="Enter order number" disabled="disabled" maxlength="3"></div></td><input type="hidden" class="position" name="hiddenSortOrder" value="" ></tr>';
					});
					
					$("#" + infoData.tableId + " tbody").children().remove();
					$("#" + infoData.tableId + " tbody").append(dataRow);
					
					if(infoData.serviceContext == "displayOrder"){
						
						$("#" + infoData.btnId).unbind('click');
						$("#" + infoData.btnId).bind("click", {id : ("#" + infoData.tableId) }, function(event){
							if($(this).text() == "Edit"){
								$(event.data.id + " tbody").sortable({
									helper: fixHelperModified,
									stop: updateIndex
								});
								
								$(event.data.id + " tbody").sortable('enable');
								$(event.data.id + " input[name=txtOrder]").prop('disabled', false);
								$("#btnFacetCancel").removeAttr('style');
								$(this).text("Save");
								
							}else{
							
								var sendData = [], facetBody = {};
								//collect data from facetorder1 table to save DB
								$("#facetorder1 tbody").children().each(function(index, value){
									facetBody = {};
									facetBody.categoryFacetId = $(value).attr('id');
									facetBody.displayOrder = $(value).children().eq(0).text();
									facetBody.categoryNodeId = self.categoryId;
									sendData.push(facetBody);
								});
								// call Data save service
								self.getAction(sendData, "save", "", function(response){
									dataRow = "";
									if(response.status == "SUCCESS"){
										$.each(response.rows, function(index,value){ 
											dataRow += '<tr id="' + (value.categoryFacetId != null? value.categoryFacetId : "") + '" systemName="'+ (value.systemName != null? value.systemName : "") +'"><td class="index center_td">'+ (index+1) +'</td><td>'+ (value.displayName != null? value.displayName : "") +' ( '+ (value.systemName != null? value.systemName : "") +') </td><td>'+ (value.values != null? value.values : "") +'</td><td class="center_td">'+ (value.status != null? value.status : "") +'</td><td><div class="action_icons"><input name="txtOrder" type="text" class="order_input" placeholder="Enter order number" disabled="disabled" maxlength="50"></div></td><input type="hidden" class="position" name="hiddenSortOrder" value="" ></tr>';
										});
									}
									
									$("#facetorder1 tbody").children().remove();
									$("#facetorder1 tbody").append(dataRow);
									$("#facetorder1  tbody").sortable('disable');
									$("#btnFacetCancel").attr('style', 'display:none;');
									$("#btnFacet1").text("Edit");
								});
							}
						});

						$("#" + infoData.divId).show();
					}
					
				}else { $("#" + infoData.divId).hide();}
				
				callback("true");
			});
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 */
		bindUI : function(){
			var self = this;
			 /************************************************************************************************************
			 * @author a1008498
			 * @Description This method captures following key event . This method is to capture the functionality of "Rules of Sort"
			 * 				a.) Enter Key 
			 * 				b.)Tab Key 
			 * 
			 * 				 After user enters some value into the cell 
			 * 				Step 1:  Get the Old Value in the input box 
			 * 				Step 2:  Get the new value 
			 * 				Step3 :  Whenever we insert a row between two rows , we have to make sure that the new row value is less than second row and greater than first row .
			 *								For example : First row is 1 , second row is 2 , then if we insert a row between 1 and 2 . Its values should 1.5 . Eg : 1, 1.5, 2. This will sort the rows correcting
							Step 4.) 	 If we user gives blank value , then box is given large value as 100000
							Step 5.)     Using the table sort plugin , sort the numeric values in asc order , After sorting give the row index to the re-ordered rows
			 * 
			 * @return   Sorted tabular data in facetvalue order Grid
			 ***********************************************************************************************************/
			$(".order_input").die('keydown');
			$(".order_input").live('keydown', function(e){
				
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
					var sorting = [[4,0]]; 
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
					
							$('#facetorder1').find('input[class=order_input]').each(function(){
							      var inputValue = $(this)[0].value; 
							      if(inputValue === null || inputValue === "" || inputValue === undefined){
											  $(this)[0].value='100000';
							      }
			   
			   				 });
							
							//Modified by : Kavinraj.M Date : 08/03/2013 
							// clear cash vale from tablesorter function
							$("#facetorder1").unbind('appendCache applyWidgetId applyWidgets sorton update updateCell')
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
									if (!$c.hasClass('order_input')) {
										$c
										.addClass('order_input')
										.bind('keyup', function() {
											$(table).trigger('updateCell', [cell, false]); // false to prevent resort
										});
									}
									return $c.find('input').val();
								},
								type: 'numeric'
							});			
							$("#facetorder1").tablesorter({headers:{4:{sorter:'inputs'}}});
						
							// sort on the Sortt Order column 
							$("#facetorder1").trigger("sorton",[sorting]); 	
								  
							// After the Sorting of whole table has completed , 
							//Get the rowIndex of each row and copy the rowIndex into the input box	  
							$('#facetorder1').find('input[class=order_input]').each(function(){
							       var inputValue = $(this)[0].value;
							      if(inputValue !== null && inputValue !== "" && inputValue !== undefined){
							    	  // Give the Displat Order Column the value of Row Index
							      		($(this).closest('tr').find('td')[0]).innerHTML= $(this).closest('tr')[0].rowIndex;
							      		
							      		// If the value is blank
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

   				 e.preventDefault();
			   }// End of Enter or Tab key
			 }// End of Event Code 
			});			
		}
	});

	return Clazz.com.components.facetorder.js.FacetOrder;
});