define(["framework/WidgetWithTemplate", "login/listener/LoginListener"], function() {
	Clazz.createPackage("com.components.login.js");

	Clazz.com.components.login.js.Login = Clazz.extend(Clazz.WidgetWithTemplate, {
		loginEvent : null,
		loginHeader : null,
		// template URL, used to indicate where to get the template
		templateUrl: "../components/login/template/login.tmp",
		configUrl: "../components/login/config/config.json",
		name : window.commonVariables.login,
		localConfig: null,
		header: {
			contentType: null,
			requestMethod: null,
			dataType: null,
			requestPostBody: null,
			webserviceurl: null
		},
	
		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig){
			var self = this;
			this.onLoginEvent = new signals.Signal();
		},

		/***
		 * Called in once the login is success
		 *
		 */
		loadPage :function(){
			var loginListener = new Clazz.com.components.login.js.listener.LoginListener();
			this.onLoginEvent.add(loginListener.onLogin, loginListener);
			Clazz.navigationController.push(this);
		},
		
		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {			
		},

		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;
			
			$('#btnLogin').click(function(e){

				var username = $('#txtUsername').val();
				var password = $('#txtPassword').val();
				
				// translate click events to call the listeners
				if ( username == "" ) {
					   $("#txtUsername").focus();
					   $(".username-error").html('Please enter username');
				} else if( password == "" ) {
					   $("#txtPassword").focus();
					   $(".username-error").html('');
					   $(".password-error").html('Please enter password');
				}else {

					if(true) {
					   // translate click events to call the listeners
					   $(".password-error").html('');
					   $(".username-error").html('');
					   
					   if(window.commonVariables !== undefined) {

							self.header.requestPostBody = $("#loginForm").serialize();
							self.header.contentType = "application/x-www-form-urlencoded";
							self.header.requestMethod = "POST";
							//self.header.webserviceurl = window.commonVariables.webserviceurl + window.commonVariables.loginContext;
							self.header.webserviceurl = window.commonVariables.webserviceurl + "api/login";
						   
							self.onLoginEvent.dispatch(self.header);
					   }
					   
					} else {
					   $("#txtUsername").focus();
					   $(".password-error").html('')
					   $(".username-error").html('Enter only characters');
					} 
				}
			});
		}
	});

	return Clazz.com.components.login.js.Login;
});