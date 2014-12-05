define(["framework/Widget", "common/Loading", "header/api/HeaderAPI",], function() {

	Clazz.createPackage("com.common_components.modules.header.js.listener");

	Clazz.com.common_components.modules.header.js.listener.HeaderListener = Clazz.extend(Clazz.Widget, {
		loadingScreen : null,

		initialize : function(config){
			
			 this.headerAPI = new Clazz.com.common_components.modules.header.js.api.HeaderAPI;
		
		},
		onButtonClick : function(callback) {
			var self = this;
			try {
				self.headerAPI.callCategories(self.callCategoriesOptions(),
					function(response) {
						if (response != null) {
							callback(response);
							if (response.status == "SUCCESS") {
								$("#successSpan").addClass("sucess_msg");
								$("#successSpan").text(""+ response.message +"");
								setTimeout(function() {
									$("#successSpan").empty();
									$("#successSpan").removeClass("sucess_msg");
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
		
		callCategoriesOptions : function () {
			var requestBody = {};
			var header = {};
			header.requestPostBody = JSON.stringify(requestBody);
			header.contentType = "application/json";
			header.requestMethod = "GET";
			header.dataType = "json";
			header.webserviceurl = window.commonVariables.webserviceurl + "category/loadTree";
			return header;
		},
		
	});

	return Clazz.com.common_components.modules.header.js.listener.HeaderListener;
});