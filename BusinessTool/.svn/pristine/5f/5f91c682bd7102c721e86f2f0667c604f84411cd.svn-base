define(["framework/WidgetWithTemplate", "footer/listener/FooterListener"] , function(template) {

	Clazz.createPackage("com.common_components.modules.footer");

	Clazz.com.common_components.modules.footer.js.Footer = Clazz.extend(Clazz.WidgetWithTemplate, {
		headerEvent : null,
		
		// template URL, used to indicate where to get the template
		templateUrl: "../js/common_components/modules/footer/template/footer.tmp",
		configUrl: "../../js/common_components/modules/footer/config/config.json",
		name : "footer",
		localConfig: null,
		globalConfig: null,
		
		initialize : function(globalConfig){
			var self = this;
			self.globalConfig = globalConfig;
		},
		
		loadPage :function(){
			var footerListener = new Clazz.com.common_components.modules.footer.js.listener.FooterListener();
		},
		
		/***
		 * 
		 */
		   postRender : function(element) {
	            var healthUrl= "../health";
	      $.ajax({     url: healthUrl,
	                dataType: "json",
	                success: function (data) {
	                  data.Version = (typeof data.Version === 'undefined') ? "No Version Sent" : data.Version;
	                   data.Revision = (typeof data.Revision === 'undefined') ? "No Revision Sent" : data.Revision;
	                  $('.footer').html( "&copy; 2013, Best Buy Co. All rights Reserved Version: "+data.Version + " Revision: "+data.Revision);
	                }
	      });      
	      
	    },
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 */
		bindUI : function(){
			
		}
	});

	return Clazz.com.common_components.modules.footer.js.Footer;
});