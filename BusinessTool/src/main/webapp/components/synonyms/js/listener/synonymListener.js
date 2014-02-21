define(["framework/Widget", "synonyms/api/SynonymsAPI", "common/Loading"], function() {

	Clazz.createPackage("com.components.synonyms.js.listener");

	Clazz.com.components.synonyms.js.listener.SynonymListener = Clazz.extend(Clazz.Widget, {
		paginationPlaceholder :  commonVariables.paginationPlaceholder,
		loadingScreen : null,
		synonymsAPI : null,
		synonymRequestBody: commonVariables.requestBody,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: global configurations for this class
		 */
		initialize : function(globalConfig) {
			this.synonymRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
			this.synonymsAPI = new Clazz.com.components.synonyms.js.api.SynonymsAPI();
		},

		/***
		 * gets all the synonyms list
		 *
		 * @header: request header for the synonyms call
		 * @callback: once the request is completed the response will be sent to this callback
		 */
		getSynonymList : function(header, callback) {
			var self = this;
			$(self.paginationPlaceholder).children().remove();
			
			try {
				this.loadingScreen.showLoading();
				self.synonymsAPI.getSynList(header,
					function(response) {
						if (response != null) {
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
			} catch(exception) {
				self.loadingScreen.removeLoading();
			}
		},

		/***
		 * checks whether the synonym name is exists or not
		 *
		 * @header: request header for the synonyms call
		 * @callback: once the request is completed the response will be sent to this callback
		 */
		checkNameExists : function(primaryTerm, mode, currentValue, callback) {
			var self = this;
			try {
				self.synonymsAPI.requestHandler(self.getNameExistsHeader(primaryTerm, mode, currentValue),
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
		 * @primaryTerm: new name value of the synonym
		 * @mode: which mode the call from
		 * @currentValue: currentvalue of the synonym only applicable on edit page
		 */
		getNameExistsHeader : function (primaryTerm, mode, currentValue) {
			if (currentValue === primaryTerm) {
				mode = primaryTerm;
			} else {
				mode = "";
			}

			var requestBody = {};
			requestBody.currentValue = mode;
			requestBody.newValue = primaryTerm;
			requestBody.inputName = "primaryTerm";

			var header = {};
			header.requestPostBody = JSON.stringify(requestBody);
			header.contentType = "application/json";
			header.requestMethod = "POST";
			header.dataType = "json";
			header.webserviceurl = commonVariables.webserviceurl + commonVariables.synonymsContext + "/checkIfExists";

			return header;
		},

		/***
		 * this method is to create the synonum action
		 *
		 * @header: request header for the synonyms call
		 * @callback: once the request is completed the response will be sent to this callback
		 */
		synonymAction : function(header, callback) {
			var self = this;
			//$(self.paginationPlaceholder).children().remove();
			
			try {
				this.loadingScreen.showLoading("#Add_synonyms");
				$("#pop_error").hide();
				self.synonymsAPI.createSyn(header,
					function(response) {
						if(response != null && response.status == "SUCCESS" && self.bCheck == true){
							$("#Add_synonyms").modal("hide");
							$("#successSpan").addClass("sucess_msg");
							$("#successSpan").text(""+ response.message +"");
							setTimeout(function() {
								$("#successSpan").empty();
								$("#successSpan").removeClass("sucess_msg");
							}, 5000);
							self.getSynonymList(self.getRequestHeader(self.synonymRequestBody), function(callbackVal){
								callback(callbackVal);
							});
						} else if(self.bCheck == true) {
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
						self.loadingScreen.removeLoading();
					},
					
					function(textStatus) {
						self.loadingScreen.removeLoading();					
					}
				);
			} catch(exception) {
				self.loadingScreen.removeLoading();
			}
		},

		/***
		 * provides the request header
		 *
		 * @synonymRequestBody: request body of synonym
		 * @return: returns the contructed header
		 */
		getRequestHeader : function(synonymRequestBody) {
			var header = {
				contentType: "application/json",
				requestMethod: "POST",
				dataType: "json",
				requestPostBody: JSON.stringify(synonymRequestBody),
				webserviceurl: commonVariables.webserviceurl + commonVariables.synonymsContext + "/loadSynonyms"
			}

			return header;
		},

		/***
		 * constructs the action header based on the need
		 *
		 * @synonymRequestBody: request body of synonym
		 * @return: returns the contructed header
		 */
		getActionHeader : function(synonymRequestBody, action, id) {
			var self = this, header = {
				contentType: "application/json",
				requestMethod: "POST",
				dataType: "json",
				requestPostBody: '',
				webserviceurl: ''
			}
			
			if(synonymRequestBody != "") {
				header.requestPostBody = JSON.stringify(synonymRequestBody);
			}
			if(action == "create"){
				self.bCheck = true;
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.synonymsContext + "/create";
			} else if(action == "update") {
				self.bCheck = true;
				header.requestMethod = "PUT";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.synonymsContext + "/update";
			} else if(action == "approve") {
				self.bCheck = true;
				header.requestMethod = "PUT";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.synonymsContext + "/approve/" + id;
			} else if(action == "reject") {
				self.bCheck = true;
				header.requestMethod = "PUT";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.synonymsContext + "/reject/" + id;
			} else if(action == "delete") {
				self.bCheck = true;
				header.requestMethod = "PUT";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.synonymsContext + "/delete/" + id;
			} else if(action == "editData") {
				self.bCheck = false;
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.synonymsContext + "/get/" + id;
			}

			return header;
		}
	});

	return Clazz.com.components.synonyms.js.listener.SynonymListener;
});