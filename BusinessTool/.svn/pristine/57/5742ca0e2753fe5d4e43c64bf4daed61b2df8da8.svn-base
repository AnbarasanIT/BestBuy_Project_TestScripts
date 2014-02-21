define(["framework/Widget", "login/api/LoginAPI", "common/Loading", "navigation/Navigation"], function() {

	Clazz.createPackage("com.components.login.js.listener");

	Clazz.com.components.login.js.listener.LoginListener = Clazz.extend(Clazz.Widget, {
		localStorageAPI : null,
		loadingScreen : null,
		navigation : null,
		basePlaceholder :  window.commonVariables.basePlaceholder,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: configurations for this listener
		 */
		initialize : function(config) {
			this.loadingScreen = new Clazz.com.js.widget.common.Loading();
			this.navigation = new Clazz.com.components.navigation.js.Navigation();
		},
		
		/***
		 * Verify the login and navigate to dashboard page if the login is successful
		 * 
		 * @header: constructed header for each call
		 */
		onLogin : function(header) {			
			var self = this;
	
			this.loadingScreen.showLoading();
			var loginAPI = new Clazz.com.components.login.js.api.LoginAPI();
			
			loginAPI.doLogin(header,
				function(response) {
					$(self.basePlaceholder).children().remove();
					self.navigation.loadPage();
					self.loadingScreen.removeLoading();
				}
			);
		}
	});

	return Clazz.com.components.login.js.listener.LoginListener;
});