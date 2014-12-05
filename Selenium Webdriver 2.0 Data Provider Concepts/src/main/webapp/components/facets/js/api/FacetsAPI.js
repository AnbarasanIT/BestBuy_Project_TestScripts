define(["framework/Base", "abstract/API"], function() {

	Clazz.createPackage("com.components.facets.js.api"); 

	Clazz.com.components.facets.js.api.FacetsAPI = Clazz.extend(Clazz.com.js.abstract.API, {
	
		getFacetsAll : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		},
		
		getattributes : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		},
		
		getAttributevalues : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		},
		
		getxmlcontent : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		},
		
		getfacet : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		}
	});

	return Clazz.com.components.facets.js.api.FacetsAPI;
});