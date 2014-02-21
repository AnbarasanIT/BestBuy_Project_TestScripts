define(["framework/Widget", "boostblock/api/BoostBlockAPI", "common/Loading", "api/LocalStorageAPI"], function() {

	Clazz.createPackage("com.components.boostblock.js.listener");

	Clazz.com.components.boostblock.js.listener.BoostBlockListener = Clazz.extend(Clazz.Widget, {
		loadingScreen : null,
		boostBlockAPI : null,
		paginationPlaceholder :  commonVariables.paginationPlaceholder,
		localStorageAPI: null,

		boostblockRequestBody: commonVariables.requestBody,		

		initialize : function(config){			
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
			this.boostBlockAPI = new Clazz.com.components.boostblock.js.api.BoostBlockAPI();
			this.localStorageAPI = new Clazz.com.js.api.LocalStorageAPI();
		},

		getBoostBlock : function(header, callback) {
			var self = this;
			this.loadingScreen.showLoading();
			self.boostBlockAPI.getBoostBlockList(header,
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
		},
		
		boostblockAction : function(header, callback){
			var self = this;
			$(self.paginationPlaceholder).children().remove();
			
			try{
				this.loadingScreen.showLoading("#Add_boostblock");
				$("#pop_error").hide();
				self.boostBlockAPI.getBoostBlockList(header,
					function(response) {
						if(response != null && response.status == "SUCCESS"){
							$("#Add_boostblock").modal("hide");
							$("#successSpan").addClass("sucess_msg");
							$("#successSpan").text(""+ response.message +"");
							setTimeout(function() {
								$("#successSpan").empty();
								$("#successSpan").removeClass("sucess_msg");
							}, 5000);
							self.getBoostBlock(self.getRequestHeader(self.boostblockRequestBody), function(callbackVal){
								callback(callbackVal);
							});
						} else {
							$("#pop_error").show();
							$("#pop_error").text(""+ response.message +"");
							/*$("#successSpan").removeClass("sucess_msg");
							$("#successSpan").addClass("error_msg");
							$("#successSpan").text(""+ response.message +"");
							callback({ "status" : "service failure"});*/
						}
						self.loadingScreen.removeLoading();
					},
					
					function(textStatus) {
						self.loadingScreen.removeLoading();					
					}
				);
			} catch(exception){
				self.loadingScreen.removeLoading();
			}
		},

		getRequestHeader : function (boostblockRequestBody) {
			var header = {
				contentType: "application/json",
				requestMethod: "POST",
				dataType: "json",
				requestPostBody: JSON.stringify(boostblockRequestBody),
				webserviceurl: commonVariables.webserviceurl + commonVariables.boostblockContext + "/searchBoostBlock"
			}
			return header;
		},
		
		getActionHeader : function (boostblockRequestBody, action, id) {
			var header = {
				contentType: "application/json",
				requestMethod: "POST",
				dataType: "json",
				requestPostBody: '',
				webserviceurl: ''
			}
			
			if(boostblockRequestBody != ""){
				header.requestPostBody = JSON.stringify(boostblockRequestBody);
			}
			
			if(action == "create"){
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.boostblockContext + "/create";
			} else if(action == "update"){
				header.requestMethod = "POST";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.boostblockContext + "/update";
			} else if(action == "approve"){
				header.requestMethod = "PUT";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.boostblockContext + "/approve/" + id;
			} else if(action == "reject"){
				header.requestMethod = "PUT";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.boostblockContext + "/reject/" + id;
			} else if(action == "delete"){
				header.requestMethod = "PUT";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.boostblockContext + "/delete/" + id;
			} else if(action == "get"){
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.boostblockContext + "/get/" + id;
			}

			return header;
		},

		searchProductListing : function(searchQuery, boostblock, callback, pageNumber,rowsPerPage) {
			var self = this;
			var header = {
				contentType: "application/json",
				requestMethod: "POST",
				dataType: "json",
				requestPostBody: '',
				webserviceurl: ''
			}
			
			header.webserviceurl = commonVariables.webserviceurl + "externalWS/searchBoostBlockSite?category_facet="+ searchQuery.categoryId +"&query=" + searchQuery.searchTerm + "&page="+pageNumber+"&enableelevation=false" + "&rows=" + rowsPerPage ;

			self.boostBlockAPI.searchProductListing(header,
				function(response) {
					if(response != null) {
						callback(response , searchQuery, boostblock, pageNumber);
					} else {
						callback({ "status" : "service failure"});
					}
					self.loadingScreen.removeLoading();
				},
				function(textStatus) {
					self.loadingScreen.removeLoading();					
				}
			);

		},		
		
		validateCategory : function(searchQuery, boostblock, callback) {
			var self = this;

			var header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				requestPostBody: '',
				webserviceurl: ''
			}
			
			header.webserviceurl = commonVariables.webserviceurl + commonVariables.boostblockContext + "/validation?searchTerm=" + searchQuery.searchTerm + "&searchProfileId=1&categoryId=" + searchQuery.categoryId + "&=1358858833678";

			self.boostBlockAPI.validateCategoryId(header,
				function(response) {
					if(response != null) {
						callback(response , searchQuery);
					} else {
						callback({ "status" : "service failure"});
					}
					self.loadingScreen.removeLoading();
				},
				function(textStatus) {
					self.loadingScreen.removeLoading();					
				}
			);

		},
		
		getAllProducts : function(id, callback) {
			var self = this;

			var header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				requestPostBody: '',
				webserviceurl: ''
			}

			header.webserviceurl = commonVariables.webserviceurl + commonVariables.boostblockContext + "/get/" + id;
			self.boostBlockAPI.getAllProducts(header,
				function(response) {
					if(response != null) {
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

	return Clazz.com.components.boostblock.js.listener.BoostBlockListener;
});