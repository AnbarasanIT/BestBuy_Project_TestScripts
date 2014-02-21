
var commonVariables = {
	globalconfig : "",
	webserviceurl : "",
	contexturl : "..",
	
	login : "login",
	loginContext : "login",
	
	header : "header",
	headerContext : "headergroups",
	
	footer : "footer",
	footerContext : "footergroups",
	
	synonyms : "synonyms",
	synonymsContext : "synonymgroups",
	synonymsTitle : "Synonyms",

	keywords : "keywords",
	keywordsContext : "redirect",
	keywordsTitle: "Keyword Redirects",
	
	banners : "banners",
	bannersContext : "banner",
	bannersTitle : "Banners",
	
	promo : "promo",
	promoContext : "promo",
	promoTitle : "Promo Pages",
	
	facets : "facets",
	facetsContext : "facets",
	facetsTitle : "Facets",
	
	facetOrder : "facetOrder",
	facetOrderContext : "categoryFacet",
	
	boostblock : "boostblock",
	boostblockContext : "boostAndBlock",
	boostblockTitle : "Boost and Block",
	
	pagination : "pagination",
	paginationContext : "",
	
	useradmin : "useradmin",
	useradminContext : "admin",
	useradminTitle : "Users",
	
	search : "search",
	searchTitle : "Search",
	
	navigation : "navigation",
	navigationContext : "",

	categoryTree : "category",
	categoryTreeContent : null,
	userInfo : {},
	
	requestBody : {
		pageIndex : "1",
		rowsPerPage: 50,
		sortColumn : "modifiedDate",
		sortOrder : "desc",
		searchColumnValues : [],
		searchOper: "AND"
	},
	
	edit : "Edit",
	approve : "Approve",
	reject : "Reject",
	deleted : "Delete",
	
	basePlaceholder : "basepage\\:widget",
	headerPlaceholder : "header\\:widget",
	contentPlaceholder : "content\\:widget",
	footerPlaceholder : "footer\\:widget",
	paginationPlaceholder : "pagination\\:widget"
};

$(document).ready(function(){
	// fastclick 
	new FastClick(document.body);

	$.get($("basepage\\:widget").attr("config"), function(data) {
		commonVariables.globalconfig = data;
		//commonVariables.webserviceurl = data.environments.environment.webservice.protocol + "://" + data.environments.environment.webservice.host + ":" + 
		//data.environments.environment.webservice.port + "/" + data.environments.environment.webservice.context + "/";
		commonVariables.webserviceurl = "../";
		configJson = {
			// comment out the below line for production, this one is so require doesn't cache the result
			urlArgs: "time=" +  (new Date()).getTime(),
			baseUrl: "../js/",
			
			paths : {
				abstract : "abstract",
				framework : "framework",
				listener : "common_components/listener",
				api : "api",
				lib : "../lib",
				common : "common_components/common",
				modules: "common_components/modules",
				footer: "common_components/modules/footer",
				header: "common_components/modules/header",
				Clazz : "framework/Class",
				components: "../components"				
			}
		};

		$.each(commonVariables.globalconfig.components, function(index, value){
			configJson.paths[index] = value.path;
		});

		// setup require.js
		requirejs.config(configJson);

		$.getJSON('../userInfo', function(userInfo) {
			commonVariables.userInfo = userInfo;
		});
		
		require(["framework/Class", "framework/NavigationController", "login/Login"],	function () {
		 	Clazz.config = data;
			Clazz.navigationController = new Clazz.NavigationController({
				mainContainer : "basepage\\:widget",
				transitionType : Clazz.config.navigation.transitionType,
				isNative : Clazz.config.navigation.isNative
			});
			
			//var loginView = new Clazz.com.components.login.js.Login();
			//loginView.loadPage();
			var navigation = new Clazz.com.components.navigation.js.Navigation();
			navigation.loadPage();
		});
	}, "json");
});