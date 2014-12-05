/*global require */
	data = {
			"apiAddress" : {
				"host" : "http://localhost",
				"port" : "8034"
			},
			
			"webSocketAddress" : {
				"host" : "ws://localhost",
				"port" : "8034"
			},
			
			"pagination" : {
				"rowsPerPage" : 10
			},
			
			"navigation" : {
				"transitionType" : 1,
				"isNative" : false
			},

			"components" : {
				"login" : {
					"moduleName":"login",
					"host":"http://localhost",
					"port":"8034",
					"path" : "../components/login/js"
				},
			},
		};
		
		configJson = {
			
			paths : {
				abstract : "js/abstract",
				framework : "js/framework",
				listener : "widget/listener",
				api : "js/api",
				lib : "lib",
				Clazz : "framework/Class", 
				common : "js/common_components/common",
				components: "../components",
				login: "components/login/js",
				header: "js/common_components/modules/header/js",
				footer: "js/common_components/modules/footer/js",
				navigation: "components/navigation/js",
				synonyms: "components/synonyms/js",
				pagination: "components/pagination/js",
				banners: "components/banners/js",
				keywords: "components/keywords/js",
				promopages: "components/promopages/js",
				facetorder: "components/facetorder/js",
				facets: "components/facets/js",
				boostblock: "components/boostblock/js",
				useradmin: "components/useradmin/js"
			}
		};
		
		var commonVariables = {
			globalconfig : "",
			webserviceurl : "",
			
			login : "login",
			loginContext : "login",
			
			header : "header",
			headerContext : "synonymgroups",
			
			footer : "footer",
			footerContext : "synonymgroups",
			
			synonyms : "synonyms",
			synonymsContext : "synonymgroups",
			synonymsTitle : "Synonyms List",

			keywords : "keywords",
			keywordsContext : "redirect",
			keywordsTitle: "Keyword List",
			
			banners : "banners",
			bannersContext : "banner",
			bannersTitle : "Banners List",
			
			promo : "promo",
			promoContext : "promo",
			promoTitle : "Promolist List",
			
			facets : "facets",
			facetsContext : "facets",
			facetsTitle : "Facets List",
			
			facetOrder : "facetOrder",
			facetOrderContext : "",
			
			boostblock : "boostblock",
			boostblockContext : "boostAndBlock",
			boostblockTitle : "Boost and Block",
			
			pagination : "pagination",
			paginationContext : "",
			
			useradmin : "useradmin",
			useradminContext : "admin",
			useradminTitle : "Users",
			
			navigation : "navigation",
			navigationContext : "",

			categoryTree : "category",
			
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

		// setup require.js
		requirejs.config(configJson);
		
require([ "jquery", "lib/signals", "lib/jquery.tablesorter", "lib/dust-full-0.3.0", "lib/handlebars-1.0.0.beta.6", "banners/Banners", "framework/NavigationController"], function($, signals, tablesorter, dust, handlebars, Banners, navigation) {
	/**
	 * Test that the setMainContent method sets the text in the MyCart-widget
	 */
	  module("Banners.js;Banners");
	asyncTest("Test Banners with same Data.", function() {
		var banners, output1, output2, output, navigationController, widgetWithTemplate, jsonData;
			
				Clazz.config = data;
				Clazz.navigationController = new Clazz.NavigationController({
					mainContainer : "basepage\\:widget",
					transitionType : Clazz.config.navigation.transitionType,
					isNative : Clazz.config.navigation.isNative
				});
				
				banners = new Banners();
				jsonData ={"status":"SUCCESS","message":"Synonym listing was successful","errorCode":null,"successCode":"Listing.Success","data":{"pageIndex":1,"rowsPerPage":10,"totalCountOfPages":5,"totalCount":43,"sortColumn":"modifiedDate","sortOrder":"desc","searchOper":"AND","searchColumnValues":[],"totalCountOfRecords":43},"page":"1","rows":[{"primaryTerm":"on1232","directionality":"One-way","exactMatch":"Exact","synonymListType":"Global_syn_list1","startDate":null,"endDate":null,"id":0,"listId":null,"synonymId":64802,"createdDate":null,"createdBy":null,"modifiedBy":"MerchandisingUI.BestBuy","modifiedDate":"02-01-2013 17:06","statusId":null,"status":"Draft","term":["ui01"],"actions":[{"key":"Approve","value":"Approve"},{"key":"Edit","value":"Edit"},{"key":"Reject","value":"Reject"},{"key":"Delete","value":"Delete"}]},{"primaryTerm":"iu11","directionality":"One-way","exactMatch":"Exact","synonymListType":"Global_syn_list1","startDate":null,"endDate":null,"id":1,"listId":null,"synonymId":64801,"createdDate":null,"createdBy":null,"modifiedBy":"MerchandisingUI.BestBuy","modifiedDate":"02-01-2013 17:06","statusId":null,"status":"Draft","term":["11"],"actions":[{"key":"Approve","value":"Approve"},{"key":"Edit","value":"Edit"},{"key":"Reject","value":"Reject"},{"key":"Delete","value":"Delete"}]},{"primaryTerm":"ui11","directionality":"One-way","exactMatch":"Exact","synonymListType":"Global_syn_list1","startDate":null,"endDate":null,"id":2,"listId":null,"synonymId":64800,"createdDate":null,"createdBy":null,"modifiedBy":"MerchandisingUI.BestBuy","modifiedDate":"02-01-2013 17:05","statusId":null,"status":"Draft","term":["11"],"actions":[{"key":"Approve","value":"Approve"},{"key":"Edit","value":"Edit"},{"key":"Reject","value":"Reject"},{"key":"Delete","value":"Delete"}]},{"primaryTerm":"two","directionality":"One-way","exactMatch":"Exact","synonymListType":"Global_syn_list1","startDate":null,"endDate":null,"id":3,"listId":null,"synonymId":64303,"createdDate":null,"createdBy":null,"modifiedBy":"MerchandisingUI.BestBuy","modifiedDate":"02-01-2013 15:20","statusId":null,"status":"Approved","term":["two"],"actions":[{"key":"Edit","value":"Edit"},{"key":"Reject","value":"Reject"},{"key":"Delete","value":"Delete"}]},{"primaryTerm":"cvvvv","directionality":"One-way","exactMatch":"Exact","synonymListType":"Global_syn_list1","startDate":null,"endDate":null,"id":4,"listId":null,"synonymId":64351,"createdDate":null,"createdBy":null,"modifiedBy":"MerchandisingUI.BestBuy","modifiedDate":"02-01-2013 15:01","statusId":null,"status":"Approved","term":["vv"],"actions":[{"key":"Edit","value":"Edit"},{"key":"Reject","value":"Reject"},{"key":"Delete","value":"Delete"}]},{"primaryTerm":"Test_Sat","directionality":"One-way","exactMatch":"Broad","synonymListType":"Global_syn_list1","startDate":null,"endDate":null,"id":5,"listId":null,"synonymId":64750,"createdDate":null,"createdBy":null,"modifiedBy":"MerchandisingUI.BestBuy","modifiedDate":"02-01-2013 15:01","statusId":null,"status":"Rejected","term":["gt"],"actions":[{"key":"Approve","value":"Approve"},{"key":"Edit","value":"Edit"},{"key":"Delete","value":"Delete"}]},{"primaryTerm":"rex3","directionality":"One-way","exactMatch":"Exact","synonymListType":"Global_syn_list1","startDate":null,"endDate":null,"id":6,"listId":null,"synonymId":64603,"createdDate":null,"createdBy":null,"modifiedBy":"MerchandisingUI.BestBuy","modifiedDate":"01-31-2013 16:27","statusId":null,"status":"Draft","term":["fff"],"actions":[{"key":"Approve","value":"Approve"},{"key":"Edit","value":"Edit"},{"key":"Reject","value":"Reject"},{"key":"Delete","value":"Delete"}]},{"primaryTerm":"sec3","directionality":"One-way","exactMatch":"Exact","synonymListType":"Global_syn_list1","startDate":null,"endDate":null,"id":7,"listId":null,"synonymId":64602,"createdDate":null,"createdBy":null,"modifiedBy":"MerchandisingUI.BestBuy","modifiedDate":"01-31-2013 16:14","statusId":null,"status":"Draft","term":["fff"],"actions":[{"key":"Approve","value":"Approve"},{"key":"Edit","value":"Edit"},{"key":"Reject","value":"Reject"},{"key":"Delete","value":"Delete"}]},{"primaryTerm":"hheeh","directionality":"One-way","exactMatch":"Exact","synonymListType":"Global_syn_list1","startDate":null,"endDate":null,"id":8,"listId":null,"synonymId":64601,"createdDate":null,"createdBy":null,"modifiedBy":"MerchandisingUI.BestBuy","modifiedDate":"01-31-2013 16:03","statusId":null,"status":"Draft","term":["hhh"],"actions":[{"key":"Approve","value":"Approve"},{"key":"Edit","value":"Edit"},{"key":"Reject","value":"Reject"},{"key":"Delete","value":"Delete"}]},{"primaryTerm":"hhh","directionality":"One-way","exactMatch":"Exact","synonymListType":"Global_syn_list1","startDate":null,"endDate":null,"id":9,"listId":null,"synonymId":64600,"createdDate":null,"createdBy":null,"modifiedBy":"MerchandisingUI.BestBuy","modifiedDate":"01-31-2013 16:02","statusId":null,"status":"Draft","term":["hhh"],"actions":[{"key":"Approve","value":"Approve"},{"key":"Edit","value":"Edit"},{"key":"Reject","value":"Reject"},{"key":"Delete","value":"Delete"}]}],"generalPurposeMessage":null}

				
				setTimeout(function() {
					start();
					output1 = jsonData.status;
					output2 = "SUCCESS";
					equal(output1, output2,	"Success case for Banners");
				}, 500);
				
		
	});
	
	asyncTest("Test Banners with different Data.", function() {
		var banners, output1, output2, output, navigationController, widgetWithTemplate, jsonData;
			
				Clazz.config = data;
				Clazz.navigationController = new Clazz.NavigationController({
					mainContainer : "basepage\\:widget",
					transitionType : Clazz.config.navigation.transitionType,
					isNative : Clazz.config.navigation.isNative
				});
				
				banners = new Banners();
				jsonData = {"status":"ERROR","message":"Error while retrieving the Banner list","errorCode":"Listing.Error","successCode":null,"data":null,"page":"1","rows":null,"generalPurposeMessage":null}

				setTimeout(function() {
					start();
					output1 = jsonData.status;
					output2 = "ERROR";
					equal(output1, output2,	"Success case for Banners");
				}, 500);
				
		
	});
});
