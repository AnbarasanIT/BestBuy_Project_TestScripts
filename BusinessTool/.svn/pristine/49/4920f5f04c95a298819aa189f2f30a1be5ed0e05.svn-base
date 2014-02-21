define(["framework/Widget", "facetorder/api/FacetOrderAPI", "common/Loading"], function() {

	Clazz.createPackage("com.components.facetorder.js.listener");

	Clazz.com.components.facetorder.js.listener.FacetOrderListener = Clazz.extend(Clazz.Widget, {
		loadingScreen : null,
		facetOrderAPI : null,

		initialize : function(config){
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
			this.facetOrderAPI = new Clazz.com.components.facetorder.js.api.FacetOrderAPI();
		},
		
		facetAction : function(header, callback){
			var self = this;
			
			try{
				this.loadingScreen.showLoading();
				self.facetOrderAPI.getFacetAction(header,
					function(response) {
						if(response != null && response.status == "SUCCESS"){
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
			} catch(exception){
				self.loadingScreen.removeLoading();
			}
		},
		
		getActionHeader : function (facetRequestBody, action, id) {
			var header = {
				contentType: "application/json",
				requestMethod: "POST",
				dataType: "json",
				requestPostBody: '',
				webserviceurl: ''
			}
			
			if(facetRequestBody != ""){
				header.requestPostBody = JSON.stringify(facetRequestBody);
			}
			
			if(action == "displayOrder"){
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.facetOrderContext + "/displayOrder/" + id;
			} else if(action == "dependantFacets"){
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.facetOrderContext + "/dependantFacets/" + id;
			} else if(action == "hiddenFacets"){
				header.requestMethod = "GET";
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.facetOrderContext + "/hiddenFacets/" + id;
			} else if(action == "save"){
				header.webserviceurl = commonVariables.webserviceurl + commonVariables.facetOrderContext + "/displayOrder";
			}

			return header;
		}
		
	});

	return Clazz.com.components.facetorder.js.listener.FacetOrderListener;
});