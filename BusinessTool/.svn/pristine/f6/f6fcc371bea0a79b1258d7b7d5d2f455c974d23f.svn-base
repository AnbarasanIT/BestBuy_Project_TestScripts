define(["framework/Base", "abstract/API"], function() {

	Clazz.createPackage("com.components.banners.js.api");
	
	Clazz.com.components.banners.js.api.BannersAPI = Clazz.extend(Clazz.com.js.abstract.API, {
		getBannerList : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		},
		
		banner : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		},
		
		/***
         * handles request with callback
         *
         * @header: constructed header for each call
         * @callbackFunction: callback function to fire once gets the response
         * @errorHandler: error handler function if the response is not success
         */
        requestHandler : function(header, callbackFunction, errorHandler) {
            this.ajaxRequest(header, callbackFunction, errorHandler);
        }
		
	});
	
	return Clazz.com.components.banners.js.api.BannersAPI; 	
});