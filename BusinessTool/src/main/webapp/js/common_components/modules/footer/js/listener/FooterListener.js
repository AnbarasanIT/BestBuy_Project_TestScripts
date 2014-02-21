define(["framework/Widget", "footer/api/FooterAPI", "common/Loading"], function() {

	Clazz.createPackage("com.common_components.modules.footer.js.listener");

	Clazz.com.common_components.modules.footer.js.listener.FooterListener = Clazz.extend(Clazz.Widget, {
		loadingScreen : null,

		initialize : function(config){
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
			this.footerAPI = new Clazz.com.components.footer.js.api.FooterAPI();
		},
		
		/***
		 * provides the request header
		 *
		 * @footerRequestBody: request body of Footer
		 * @return: returns the constructed header
		 */
		getRequestHeader : function(footerRequestBody) {
			var header = {
				contentType: "application/json",
				requestMethod: "GET",
				dataType: "json",
				requestPostBody: JSON.stringify(footerRequestBody),
				webserviceurl: "../health"
			}

			return header;
		},
		
		/***
		 * gets all the footer list
		 *
		 * @header: request header for the footer call
		 * @callback: once the request is completed the response will be sent to this callback
		 */
		getFooterInfo : function(header, callback) {
			var self = this;
		
			try {
				this.loadingScreen.showLoading();
				self.footerAPI.getFooterInfoAPI(header,
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
		}
		
	});

	return Clazz.com.common_components.modules.footer.js.listener.FooterListener;
});