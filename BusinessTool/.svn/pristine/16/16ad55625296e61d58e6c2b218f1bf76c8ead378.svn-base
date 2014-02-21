define(["framework/Base"], function(){	
	
	Clazz.createPackage("com.js.abstract");

	Clazz.com.js.abstract.API = Clazz.extend(Clazz.Base, {

		categoryTree : null,

		//to solve caching issue for iOS 6
		//always send current time to differ every request.
		getTimeStamp : function(){
			var date = new Date();
			return date.getTime(); 
		},

		// This method is used to send the request to web service.
		// module is the identical key that is used to be inserted in the apiHost.
		// requestMethod is the method to send web service. It can be "POST" or "GET".
		// requestPostBody is the request parameter and value to be sent.
		// callbackFunction is the method sent to handle the response.
		// errorHandler is used to handle the error happened.
		ajaxRequest : function(header, callbackFunction, errorHandler){
			$.ajax({
				url: header.webserviceurl,
				type : header.requestMethod,
				dataType : header.dataType,
				header : "Access-Control-Allow-Headers: x-requested-with",
				contentType : header.contentType,
				data : header.requestPostBody,
				timeout: 90000,
				//crossDomain: true,
				cache: true,
				async: true,
				
				success : function(response, e ,xhr) {
					if (callbackFunction) {
						callbackFunction(response);
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
					location.reload();
					/* if (errorHandler) {
						errorHandler(textStatus);
					} */
				}
			});
		},
		
		loadCategoryTree : function(categoryURL, callbackFunction, errorHandler) {
			var self = this;
			if (self.categoryTree === null) {
				self.ajaxRequest(self.getCategoryTreeRequestHeader(categoryURL), function (response) {
					if(response != null) {
						var xmlDoc = $.parseXML(response);
						callbackFunction (xmlDoc);
					} else {
						errorHandler({ "status" : "Category load failure"})
					}
				}, errorHandler);
			} else {
				callbackFunction(self.categoryTree);
			}
		},
		
		getCategoryTreeRequestHeader : function (categoryURL) {
			var header = {
				contentType: "text/xml; charset=utf-8",
				requestMethod: "GET",
				webserviceurl: categoryURL
			}

			return header;
		}
	});

	return Clazz.com.js.abstract.API;
});