define(["framework/Base", "abstract/API"], function() {

	Clazz.createPackage("com.components.boostblock.js.api");

	Clazz.com.components.boostblock.js.api.BoostBlockAPI = Clazz.extend(Clazz.com.js.abstract.API, {
		getBoostBlockList : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		},
		getBoostBlockSearchList : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		},
		
		searchProductListing : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		},
		
		validateCategoryId : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		},
		
		getAllProducts : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		}	
	});

	return Clazz.com.components.boostblock.js.api.BoostBlockAPI;
});