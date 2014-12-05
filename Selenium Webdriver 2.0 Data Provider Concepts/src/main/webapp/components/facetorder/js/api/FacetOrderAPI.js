define(["framework/Base", "abstract/API"], function() {

	Clazz.createPackage("com.components.facetorder.js.api");

	Clazz.com.components.facetorder.js.api.FacetOrderAPI = Clazz.extend(Clazz.com.js.abstract.API, {

		getFacetAction : function(header, callbackFunction, errorHandler) {
			this.ajaxRequest(header, callbackFunction, errorHandler);
		}
	});

	return Clazz.com.components.facetorder.js.api.FacetOrderAPI;
});