define(["framework/Widget", "banners/api/BannersAPI", "common/Loading"], function() {

	Clazz.createPackage("com.components.banners.js.listener");

	Clazz.com.components.banners.js.listener.BannersListener = Clazz.extend(Clazz.Widget, {
	
		loadingScreen : null,
		bannersAPI : null,
		
		bannerRequestBody: commonVariables.requestBody,
		
		/**
         * Initializes the API and loading Screen
         * @config: configuarion for initializing the class
         */
		initialize : function(config){
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
			this.bannersAPI = new Clazz.com.components.banners.js.api.BannersAPI();
		},
		
		/**
         * Constructs the request header structure with the request headers
         * @keywordRequestBody: JSON request body object
         */
		getRequestHeader : function (keywordRequestBody) {
			var header = {
				contentType: "application/json",
				requestMethod: "POST",
				dataType: "json",
				requestPostBody: JSON.stringify(keywordRequestBody),
				webserviceurl: commonVariables.webserviceurl + commonVariables.bannersContext + "/loadBanners"
			}

			return header;
		},
		
		/**
         * Peforms the approve/reject/delete options for Banner object
         * @header: Request header to be sent
         * @callback: Callback method to handle the request
         */
		bannerAction : function(header, callback) {
			var self = this;
			try {
				$("#pop_error").hide();
				this.loadingScreen.showLoading("#Add_banners");
				self.bannersAPI.banner(header,
					function(response) {
						if(response != null && response.status == "SUCCESS" && bcheck == true){
							$("#Add_banners").modal("hide");
							$("#successSpan").addClass("sucess_msg");
							$("#successSpan").text(""+ response.message +"");
							setTimeout(function() {
								$("#successSpan").empty();
								$("#successSpan").removeClass("sucess_msg");
							}, 5000);
						
							self.getBannerList(self.getRequestHeader(self.bannerRequestBody), function(callbackVal){
								callback(callbackVal);
							});
						} else if(bcheck == true){
							$("#pop_error").show();
							$("#pop_error").text(""+ response.message +"");
							/*$("#successSpan").removeClass("sucess_msg");
							$("#successSpan").addClass("error_msg");
							$("#successSpan").text(""+ response.message +"");
							callback({ "status" : "service failure"});*/
							if (callback !== undefined) {
								callback(response);
							}
						} else{
							callback(response);
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
		
		
		/***
		 * checks whether the banner name is exists or not
		 *
		 * @header: request header for the banner call
		 * @callback: once the request is completed the response will be sent to this callback
		 */
		checkNameExists : function(bannerName, mode, currentValue, callback) {
			var self = this;
			try {
				self.bannersAPI.requestHandler(self.getNameExistsHeader(bannerName, mode, currentValue),
					function(response) {
						if (response != null) {
							callback(response);
							if (response.status == "SUCCESS") {
								$("#pop_error").show();
								$("#pop_error").text(""+ response.message +"");
								setTimeout(function() {
									$("#pop_error").hide();
								}, 5000);
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
		
		
		/***
		 * provides the Name exists header json for both add and edit page
		 *
		 * @bannerName: new name value of the banner
		 * @mode: which mode the call from
		 * @currentValue: currentvalue of the banner only applicable on edit page
		 */
		getNameExistsHeader : function (bannerName, mode, currentValue) {
			if (currentValue === bannerName) {
				mode = bannerName;
			} else {
				mode = "";
			}

			var requestBody = {};
			requestBody.currentValue = mode;
			requestBody.newValue = bannerName;
			requestBody.inputName = "bannerName";

			var header = {};
			header.requestPostBody = JSON.stringify(requestBody);
			header.contentType = "application/json";
			header.requestMethod = "POST";
			header.dataType = "json";
			header.webserviceurl = commonVariables.webserviceurl + commonVariables.bannersContext + "/exists";

			return header;
		},
		
		
		 /**
         * Creates the request header based on the action and request
         * @keywordRequestBody: Request body to sent as the request
         * @action: Action to be performed, create/edit/approve. reject, delete
         * @id: id of the object
         */
		getActionHeader : function (bannerRequestBody, action, id) {
		
			var self=this, header = {
				contentType: "application/json",
				requestMethod: "POST",
				dataType: "json",
				requestPostBody: '',
				webserviceurl: ''
			}
			
			if(bannerRequestBody != ""){
				header.requestPostBody = JSON.stringify(bannerRequestBody);
			}
			
			if(action == "create"){
				bcheck = true;
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.bannersContext + "/create";
			} else if(action == "update"){
				bcheck = true;
				header.requestMethod = "PUT";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.bannersContext + "/update";
			} else if(action == "approve"){
				bcheck = true;
				header.requestMethod = "PUT";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.bannersContext + "/approve/" + id;
			} else if(action == "reject"){
				bcheck = true;
				header.requestMethod = "PUT";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.bannersContext + "/reject/" + id;
			} else if(action == "delete"){
				bcheck = true;
				header.requestMethod = "PUT";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.bannersContext + "/delete/" + id;
			} else if(action == "exists"){
				bcheck = false;
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.bannersContext + "/exists";
			} else if(action == "editData"){
				bcheck = false;
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.bannersContext + "/get/" + id;
			} else if(action == "categoryFacet"){
				bcheck = false;
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + "categoryFacet/" + commonVariables.bannersContext + "/" + id;
			} else if(action == "attributeValue"){
				bcheck = false;
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.facetsContext + "/attributeValue/" + id;
			} else if(action == "contextdelete"){
				bcheck = true;
				header.requestMethod = "PUT";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.bannersContext + "/delete/context/" + id;
			}

			return header;
		},
		
		 /**
		 * Calls the service via SPI to get the keyword list
		 * @header: Header to be sent for the Service call
		 * @callback: Callback method
		 */
		
		getBannerList : function(header, callback) {
			var self = this;
			this.loadingScreen.showLoading();
			self.bannersAPI.getBannerList(header,
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

	return Clazz.com.components.banners.js.listener.BannersListener;
});