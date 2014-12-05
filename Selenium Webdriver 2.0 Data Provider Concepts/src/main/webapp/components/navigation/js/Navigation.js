define(["framework/WidgetWithTemplate", "navigation/listener/NavigationListener", "synonyms/Synonyms", "header/Header","footer/Footer", "pagination/Pagination", "keywords/listener/KeywordListener" ,"lib/signals"] , function(template, navigation, synonyms, header, footer, pagnation, keywords, signals) {

	Clazz.createPackage("com.components.navigation.js");

	Clazz.com.components.navigation.js.Navigation = Clazz.extend(Clazz.WidgetWithTemplate, {
		navigationEvent : null,
		RenderPageEvent: null,
		currentObj : null,
		localConfig: null,
		name : commonVariables.navigation,
		header: {
			contentType: null,
			requestMethod: null,
			dataType: null,
			requestPostBody: null,
			webserviceurl: null
		},
		keywordContainer : null,
		onKeywordPageEvent : null,
		keywordObject : null,
		navigationListener: null,
		loadingScreen: null,
		synonymObj : null,
		paginationWidget : null,

		// template URL, used to indicate where to get the template
		templateUrl: "../components/navigation/template/navigation.tmp",
		configUrl: "../components/navigation/config/config.json",
		headerContainer : commonVariables.headerPlaceholder,
		contentContainer : commonVariables.contentPlaceholder,
		footerContainer : commonVariables.footerPlaceholder,
		paginationContainer :  commonVariables.paginationPlaceholder,

		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			this.onRenderPageEvent = new signals.Signal();
			this.onKeywordPageEvent = new signals.Signal();

		},

		/***
		 * getting the session value from the local storage
		 *
		 * @key: key to get the value from local storage
		 */
		getSession : function(key) {
			if (key !== '') {
				return localStorage.getItem(key);
			}
		},

		/***
		 * Called in once the page loads
		 *
		 */
		loadPage : function() {
			var self = this;
			var navigationListener = new Clazz.com.components.navigation.js.listener.NavigationListener();
			self.navigationListener = navigationListener;
			this.onRenderPageEvent.add(navigationListener.onRenderPage, navigationListener);
			Clazz.navigationController.push(this);
		},

		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 * @renderFunction: callback to render the response data
		 */
		postRender : function(element, renderFunction) {
			var self = this;
			var headerWidget = self.navigationListener.header;
			var synonymsWidget = self.navigationListener.synonyms;
			var footerWidget = self.navigationListener.footer;

			headerWidget.render(self.headerContainer);
			self.onRenderPageEvent.dispatch();
			footerWidget.render(self.footerContainer);
			$("#main").removeClass("login_bg");
		},

		/***
		 * alertbox for delete message confirmation
		 *
		 * @header: Navigation header page (html)
		 * @text: message for the confirmation
		 * @callback: callback to render the response data
		 */
		alertBox : function(header, text, callback) {
			$("#alertHeader").html(header);
			$("#alertText").html(text);
			$("#deletemsg").removeClass("hide");
			$("#deletemsg").removeAttr("style");
			
			if(text == "Are you sure you want to perform this action?"){
				$("#alertOk").text("Ok")
			}else{
				$("#alertOk").text("Delete");
			}
			if(header == "Confirmation"){
				$("#alertOk").text("Yes")
				$("#alertCancel").text("No")
			}
			
			$("#alertOk").unbind('click');	
			$("#alertOk").click(function(e){
				$('#deletemsg').hide();
				callback(true);
			});
			
			$("#alertCancel").click(function(e){
				$('#deletemsg').hide();
				callback(false);
			});

			$(".close").click(function(e){
				$('#deletemsg').hide();
				callback(false);
			});
		},

		/***
		 * getting the appropriate component
		 *
		 * @componentName: based on the componentName gets the appropriate component
		 */
		getComponent : function(componentName) {

			var self = this;
			if (componentName == commonVariables.header) {
				self.currentObj = new Clazz.com.common_components.modules.header.js.Header();
			} else if(componentName == commonVariables.footer) {
				self.currentObj = new Clazz.com.common_components.modules.footer.js.Footer();
			} else if(componentName == commonVariables.pagination) {
				self.currentObj = new Clazz.com.components.pagination.js.Pagination();
			}

			return self.currentObj;
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete
		 *
		 */
		bindUI : function(){
			var self = this;

			$("#extruderLeft").buildMbExtruder({
				position:"left",
				width:200,
				extruderOpacity:.8,
				hidePanelsOnClose:false,
				accordionPanels:false,
				onExtOpen:function(){
					$("#extruderLeft .nav").click(function() {
						$("#extruderLeft").closeMbExtruder(true);
					});
				},

				onExtContentLoad:function(){$("#extruderLeft").openPanel();},
				onExtClose:function(){

				}
			});

			$("#myTab li").click(function() {
				$(self.contentContainer).children().remove();
				$("#AddIcon").attr("href", "#Add_" + this.id + "");
				self.onRenderPageEvent.dispatch(this.id);
				$("#successSpan").text("");
				$("#successSpan").removeClass("sucess_msg");
				$("#successSpan").removeClass("error_msg");


				if(this.id == commonVariables.keywords) {
					$("#searchOption").children().remove();
					$("#searchOption").append('<option value="redirectTerm">Term</option><option value="redirectUrl">URL</option><option value="searchProfileType">Search Profile</option><option value="status">Status</option><option value="modifiedBy">Last Modified By</option>');
				} else if(this.id == commonVariables.synonyms) {
					$("#searchOption").children().remove();
					$("#searchOption").append('<option value="primaryTerm">Primary Term</option><option value="term">Terms</option><option value="modifiedBy">Last Modified By</option><option value="status">Status</option><option value="synonymListType">Syn List</option>');
				} else if(this.id == commonVariables.promo) {
					$("#searchOption").children().remove();
					$("#searchOption").append('<option value="promoName">PromoPage Name</option><option value="skuIds">SKU IDs</option><option value="modifiedBy">Last Modified By</option><option value="status">Status</option>');
				} else if(this.id == commonVariables.boostblock) {
					$("#searchOption").children().remove();
					$("#searchOption").append('<option value="searchTerm">Search Term</option><option value="searchProfileName">Search Profile</option><option value="modifiedBy">Last Modified By</option><option value="categoryPath">Category</option><option value="status">Status</option>');
				} else if(this.id == commonVariables.useradmin) {
					$("#searchOption").children().remove();
					$("#searchOption").append('<option value="firstName">First Name</option><option value="lastName">Last Name</option><option value="userId">User Id</option>');
				} else if(this.id == commonVariables.banners) {
					$("#searchOption").children().remove();
					$("#searchOption").append('<option value="bannerName">Banner Name</option><option value="bannerType">Type</option><option value="modifiedBy">Last Modified By</option><option value="status">Status</option>');
					/* $("#banner_hideall").parent().show();
					$("#banner_showall").parent().show(); */
				} else if(this.id == commonVariables.facets) {
					$("#searchOption").children().remove();
					$("#searchOption").append('<option value="systemName">System Name</option><option value="displayName">Display Name</option><option value="attributeName">Attribute</option><option value="editedBy">Last Modified By</option><option value="status">Status</option>');
				}


				if(this.id == commonVariables.banners || this.id == commonVariables.facets) {
					//$("#banner_hideall").parent().show();
					$("#banner_showall").parent().show();
				} else {
					$("#banner_hideall").parent().hide();
					$("#banner_showall").parent().hide();
				}

				//Title action show & hide
				if(this.id == commonVariables.facetOrder){ // || this.id == commonVariables.useradmin
					$("#title_actions").hide();
				}else{
					$("#title_actions").show();
				}
				
				//Pagination action show & hide
				if(this.id == commonVariables.facetOrder || this.id == commonVariables.useradmin) { 
					$(".pagination_div").hide();
				} else {
					$(".pagination_div").show();
				}
				
				/* if(this.id == commonVariables.useradmin) { 
					$("#skuRemove").show();
				} else {
					$("#skuRemove").hide();
				} */
			});

			$("#refresh").click(function() {
				
				var getPageValue = self.getSession("pageName");
				
				if(getPageValue != commonVariables.header && getPageValue != commonVariables.footer &&
					getPageValue != commonVariables.pagination){
					self.navigationListener.onRenderPage(getPageValue);
				} 
			});

			$('#searchtext').click(function(e){
				var currentPage = self.getSession("pageName");
				if (currentPage == "useradmin") {
					useradminSearch();
				} else {
					searchFortheRecord();
				}
			});
			
			$("#txtSearch, #searchOption").keypress(function(e){
				var currentPage = self.getSession("pageName");
				if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
					code= (e.keyCode ? e.keyCode : e.which);
					if (code == 13) {
						if (currentPage == "useradmin") {
							useradminSearch();
						} else {
							searchFortheRecord();
						}	
					}
				}
			});
			
			function useradminSearch(){
				$("#alert_msg").hide();
				$("#myTable").show();
				$("#no_record").hide();
				var txtSearch = $("#txtSearch").val().toLowerCase();
				var searchOption = $("#searchOption").val().toLowerCase().replace(/\s+/g, '');
				if (txtSearch != "") {
					$("#usercontent tr").hide();//To hide all the tr
					var hasRecord = false;
					$("#myTable thead tr th").each(function() {//To iterate the th and check for the selected searchOption
						var thText = $(this).text().toLowerCase().replace(/\s+/g, '');
						if (thText == searchOption) {
							var thIndex = $(this).index();
							var i=0;
							$("#usercontent tr").each(function() {//To search for the txtSearch and search option thru all td
								var td = $(this).find('td').eq(thIndex);
								var tdText = td.text().toLowerCase();
								if (tdText.match("^" + txtSearch)) {
									$(this).show();
									hasRecord = true;
									i++;
								}
							});
							$("#totalCount").text("("+i+" Record(s) Total)");
							return false;
						}
					});
					if (hasRecord == false) {
						$("#no_record").show();
						$("#no_record").text("No Record Available");
						$("#totalCount").text("(0 Record Total)");
						$("#myTable").hide();
					}
				} else {
					$("#usercontent tr").show();
				}
			}
			
			function searchFortheRecord() {
				var searchCriteria = {};
				searchCriteria.key = $("#searchOption").val();
				searchCriteria.value = $("#txtSearch").val();
				self.navigationListener.currentObj.search(searchCriteria);
			}
		}
	});

	return Clazz.com.components.navigation.js.Navigation;
});