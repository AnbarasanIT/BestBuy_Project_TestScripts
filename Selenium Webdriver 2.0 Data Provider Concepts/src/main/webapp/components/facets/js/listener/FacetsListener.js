define(["framework/Widget"], function() {

	Clazz.createPackage("com.components.facets.js.listener");

	Clazz.com.components.facets.js.listener.FacetsListener = Clazz.extend(Clazz.Widget, {
		loadingScreen : null,
		facetsAPI : null,
		contentContainer : window.commonVariables.contentPlaceholder,
		paginationPlaceholder :  window.commonVariables.paginationPlaceholder,
		
		
		facetsRequestBody: commonVariables.requestBody,
		
		initialize : function(config){
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
			this.facetsAPI = new Clazz.com.components.facets.js.api.FacetsAPI();
		},
		
		getFacetsList : function(header, callback){
			var self = this;
			$(self.paginationPlaceholder).children().remove();			
			try {
				this.loadingScreen.showLoading(self.contentContainer);
				//this.loadingScreen.showLoading("myTable");
				self.facetsAPI.getFacetsAll(header,
					function(response) {
						if(response != null){
							callback(response);
						} else {
							callback({ "status" : "service failure"});
						}
						self.loadingScreen.removeLoading(self.contentContainer);
					},
					function(textStatus) {
						self.loadingScreen.removeLoading(self.contentContainer);					
					}
				);
			}catch(exception){
				self.loadingScreen.removeLoading(self.contentContainer);
			}
		},
		
		checkNameExists : function(systemname, mode, currentValue, callback) {
			var self = this;
			try {
				self.facetsAPI.getFacetsAll(self.getNameExistsHeader(systemname, mode, currentValue),
					function(response) {
						if (response != null) {
							callback(response);
							if (response.status == "SUCCESS") {
								//User should not see a Success message in an error box.
								//$("#pop_error").show();
								//$("#pop_error").html('Congrats! <span class="popupText"> This is a unique name and available for use.</span>');
								//$("#pop_error").focus();
								setTimeout(function() {
									$("#pop_error").hide();
								}, 10000);
							} else if(response.status == "ERROR"){
								$("#pop_error").show();
								$("#pop_error").focus();
								$("#pop_error").html('Sorry! <span class="popupText"> This name is already in use. Please select a different name.</span>');
								setTimeout(function() {
									$("#pop_error").hide();
								}, 10000);
							}
						} else {
							callback({ "status" : "service failure"});
						}
					}
				);
			} catch(exception) {
				callback({ "status" : exception});
			}
		},
		
		getNameExistsHeader : function (systemname, mode, currentValue) {
			if (currentValue === systemname) {
				mode = systemname;
			} else {
				mode = "";
			}

			var requestBody = {};
			requestBody.currentValue = mode;
			requestBody.newValue = systemname;
			requestBody.inputName = "systemName";

			var header = {};
			header.requestPostBody = JSON.stringify(requestBody);
			header.contentType = "application/json";
			header.requestMethod = "POST";
			header.dataType = "json";
			header.webserviceurl = commonVariables.webserviceurl + commonVariables.facetsContext + "/exists";

			return header;
		},
		
		facetsAction : function(header, callback){			
			var self = this;			
			try{
				this.loadingScreen.showLoading("#Add_facets");
				$("#pop_error").hide();
				self.facetsAPI.getFacetsAll(header,
					function(response) {
						if(response != null && response.status == "SUCCESS" && self.bCheck == true){
							$("#Add_facets").modal("hide");
							$("#successSpan").addClass("sucess_msg");
							$("#successSpan").text(""+ response.message +"");
							setTimeout(function() {
								$("#successSpan").empty();
								$("#successSpan").removeClass("sucess_msg");
							}, 5000);
							self.getFacetsList(self.getRequestHeader(self.facetsRequestBody), function(callbackVal){
								callback(callbackVal);
							});
						} else if(self.bCheck == true){
							$("#pop_error").show();
							$("#pop_error").text(""+ response.message +"");
							/*$("#successSpan").removeClass("sucess_msg");
							$("#successSpan").addClass("error_msg");
							$("#successSpan").text(""+ response.message +"");
							callback({ "status" : "service failure"});*/
							if (callback !== undefined) {
								callback(response);
							}
						} else {
							callback(response);
						}
						self.loadingScreen.removeLoading(self.contentContainer);
						//$('div').removeClass('modal-backdrop fade in');
					},
					
					function(textStatus) {
						self.loadingScreen.removeLoading(self.contentContainer);					
					}
				);
			} catch(exception){
				self.loadingScreen.removeLoading(self.contentContainer);
			}
		},
		
		getAttributes: function(header, callback) {
			var self = this;
			/* this.loadingScreen.showLoading(); */
			self.facetsAPI.getattributes(header,
				function(response) {
					if(response != null){
						callback(response);
					} else {
						callback({ "status" : "service failure"});
					}
					/* self.loadingScreen.removeLoading(); */
				},
				function(textStatus) {
					/* self.loadingScreen.removeLoading();	 */				
				}
			);
		},
		
		getAttributevalues : function(header, callback) {
			var self = this;
			/* this.loadingScreen.showLoading(); */
			self.facetsAPI.getattributes(header,
				function(response) {
					if(response != null){
						callback(response);
					} else {
						callback({ "status" : "service failure"});
					}
					/* self.loadingScreen.removeLoading(); */
				},
				function(textStatus) {
					/* self.loadingScreen.removeLoading();	 */				
				}
			);
		},

		getRequestHeader : function (facetsRequestBody) {
			var header = {
				contentType: "application/json",
				requestMethod: "POST",
				dataType: "json",
				requestPostBody: JSON.stringify(facetsRequestBody),
				webserviceurl: window.commonVariables.webserviceurl + window.commonVariables.facetsContext + "/searchFacets"
			}

			return header;
		},
		
		getActionHeader : function (facetsRequestBody, action, id) {
		
			var self = this, header = {
				contentType: "application/json",
				requestMethod: "POST",
				dataType: "json",
				requestPostBody: '',
				webserviceurl: ''
			}
			
			if(facetsRequestBody != ""){
				header.requestPostBody = JSON.stringify(facetsRequestBody);
			}
			
			if(action == "create"){
				self.bCheck = true;
				header.webserviceurl = window.commonVariables.webserviceurl + window.commonVariables.facetsContext + "/create";
			} else if(action == "update"){
				self.bCheck = true;
				header.requestMethod = "POST";
				header.webserviceurl = window.commonVariables.webserviceurl + window.commonVariables.facetsContext + "/update";
			} else if(action == "approve"){
				self.bCheck = true;
				header.requestMethod = "PUT";
				header.webserviceurl = window.commonVariables.webserviceurl + window.commonVariables.facetsContext + "/approve/?id=" + id;
			} else if(action == "reject"){
				self.bCheck = true;
				header.requestMethod = "PUT";
				header.webserviceurl = window.commonVariables.webserviceurl + window.commonVariables.facetsContext + "/reject/?id=" + id;
			} else if(action == "delete"){
				self.bCheck = true;
				header.requestMethod = "PUT";
				header.webserviceurl = window.commonVariables.webserviceurl + window.commonVariables.facetsContext + "/delete/" + id;
			}  else if(action == "load"){
				self.bCheck = false;
				header.requestMethod = "POST";
				header.webserviceurl = window.commonVariables.webserviceurl + "attributeValue/loadAttrValue";
			} else if(action == "editData"){
				self.bCheck = false;
				header.requestMethod = "GET";
				header.webserviceurl = window.commonVariables.webserviceurl + window.commonVariables.facetsContext + "/get/" + id;
			} 

			return header;
		},
		
		getfacets : function(header, callback) {
			var self = this;
			this.loadingScreen.showLoading();
			/* var header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				requestPostBody: '',
				webserviceurl: window.commonVariables.webserviceurl + "categoryFacet/"+id;	
			} */
			self.facetsAPI.getfacet(header,
				function(response) {
					if(response != null){
						callback(response);
					} else {
						callback({ "status" : "service failure"});
					}
					self.loadingScreen.removeLoading();
				},
				function(textStatus) {
					self.loadingScreen.removeLoading();					
				}
			);
		}
	});

	return Clazz.com.components.facets.js.listener.FacetsListener;
});