define(["framework/Widget", "common/Loading", "synonyms/Synonyms", "keywords/Keyword", "banners/Banners", "promopages/PromoPages", "facets/Facets", "facetorder/FacetOrder", "boostblock/BoostBlock", "useradmin/Useradmin", "search/Search", "pagination/Pagination", "navigation/api/NavigationAPI", "api/LocalStorageAPI"], function() {

	Clazz.createPackage("com.components.navigation.js.listener");

	Clazz.com.components.navigation.js.listener.NavigationListener = Clazz.extend(Clazz.Widget, {
		loadingScreen : null,
		contentContainer : commonVariables.contentPlaceholder,
		currentObj : null,
		keywordObj : null,
		pagination : null,
		titleStr : null,
	
		header: null,
		footer: null,
		keywords: null,
		banners: null,
		promopages: null,
		facets: null,
		facetorder: null,
		useradmin: null,
		boostblock: null,
		synonyms: null,
		search: null,

		navigationAPI: null,
		localStorageAPI: null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @config: global configurations for this class and initializes all the components
		 */
		initialize : function(config) {
			this.pagination = new Clazz.com.components.pagination.js.Pagination();	
			this.keywords = new Clazz.com.components.keywords.js.Keyword();
			this.synonyms = new Clazz.com.components.synonyms.js.Synonyms();
			this.header = new Clazz.com.common_components.modules.header.js.Header();
			this.footer = new Clazz.com.common_components.modules.footer.js.Footer();
			this.navigationAPI = new Clazz.com.components.navigation.js.api.NavigationAPI();
			this.localStorageAPI = new Clazz.com.js.api.LocalStorageAPI();
			this.initializeCategoryTree();
		},

		/***
		 * Called when the tab is clicked 
		 *
		 * @keyword: selected tab to display
		 */
		onRenderPage : function(keyword) {
			var self = this;

			if (keyword == commonVariables.keywords) {
				if (self.keywords === null) {
					self.keywords = new Clazz.com.components.keywords.js.Keyword();
				}
				self.currentObj = self.keywords
				self.titleStr = commonVariables.keywordsTitle;
				showOthrTabContents();
			} else if(keyword == commonVariables.banners) { 
				if (self.banners === null) {
					self.banners = new Clazz.com.components.banners.js.Banners();
				}
				self.currentObj = self.banners;
				self.titleStr = commonVariables.bannersTitle;
				showOthrTabContents();
			} else if(keyword == commonVariables.promo) {
				if (this.promopages === null) {
					this.promopages = new Clazz.com.components.promopages.js.PromoPages();
				}
				self.currentObj = self.promopages;
				self.titleStr = commonVariables.promoTitle;
				showOthrTabContents();
			} else if(keyword == commonVariables.facets) {
				if (this.facets === null) {
					this.facets = new Clazz.com.components.facets.js.Facets();
				}
				self.currentObj = self.facets;
				self.titleStr = commonVariables.facetsTitle;
				showOthrTabContents();
			} else if(keyword == commonVariables.facetOrder) {
				if (this.facetorder === null) {
					this.facetorder = new Clazz.com.components.facetorder.js.FacetOrder();
				}
				self.currentObj = self.facetorder;
				showOthrTabContents();
			} else if(keyword == commonVariables.useradmin) {
				if (this.useradmin === null) {
					this.useradmin = new Clazz.com.components.useradmin.js.Useradmin();
				}
				self.currentObj = self.useradmin;
				self.titleStr = commonVariables.useradminTitle;
				showOthrTabContents();
			} else if(keyword == commonVariables.boostblock) {
				if (this.boostblock === null) {
					this.boostblock = new Clazz.com.components.boostblock.js.Boostblock();
				}
				self.currentObj = self.boostblock;
				self.titleStr = commonVariables.boostblockTitle;
				showOthrTabContents();
			} else if(keyword == commonVariables.search) {
				if (this.search === null) {
					this.search = new Clazz.com.components.search.js.Search();
				}
				self.currentObj = self.search;
				self.titleStr = commonVariables.searchTitle;
				showSearchTabContents();
			} else {
				if (self.synonyms === null) {
					this.synonyms = new Clazz.com.components.synonyms.js.Synonyms();
				}
				
				self.currentObj = self.synonyms;
				self.titleStr = commonVariables.synonymsTitle;
				showOthrTabContents();
			}

			//To show/hide the required navigation template content for search tab
			function showSearchTabContents() {
				$('#facingTypeDiv').show();
				$('#sortbyDiv').show();
				$('#urlportDiv').show();
				$('#clearSearchDiv').show();
				$('#searchOptDiv').hide();
				$('#refresh').hide();
				$('#addIconDiv').hide();
				$('#debug').show();
			}

			//To show/hide the required navigation template content for other tabs
			function showOthrTabContents() {
				$('#facingTypeDiv').hide();
				$('#sortbyDiv').hide();
				$('.preview_date').hide();
				$('#urlportDiv').hide();
				$('#clearSearchDiv').hide();
				$('#searchOptDiv').show();
				$('#refresh').show();
				$('#addIconDiv').show();
				$('#debug').hide();
			}

			self.pagination.currentObj = self.currentObj;
			self.currentObj.pagination = self.pagination;
			self.currentObj.searchdata = {};
			self.currentObj.render(self.contentContainer);
			$("#title").html(self.titleStr);
			$("#txtSearch").val("");
		},
		
		getTreeViewHtml : function (ItemList, callback) {
			var self= this, strItems ="", strCollection = "";
			$(ItemList).each(function(index, value){
				if($(value).children().length > 0){
					if(index == 0){}else{
						self.getTreeViewHtml($(value).children(),function(callback){
							try{
								var nodeNameVal = '';
								if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion.substr(0,1) <= 4){
									nodeNameVal = decodeURI($(value.childNodes[0]).text());
								} else{
									nodeNameVal = decodeURI($(value.childNodes[1]).text())
								}

								strItems += '<li id="'+ $(value).attr('id') +'" path="'+ $(value).attr('path') +'"><span class="folder"><a>' + nodeNameVal + '</a></span>'+ callback +'</li>';
								
							}catch(ex){
							}
						});
					}
				}
			});
			
			if(strItems != ""){
				strCollection = '<ul>'+ strItems +'</ul>';
			}
			callback(strCollection);
		},
		
		//In future need to remove this code and load from handlebar js.(Kavinraj.M - 28/01/2013)
		initializeCategoryTree : function(){
			var self = this;
			try {
				var categoryURL = commonVariables.webserviceurl + commonVariables.categoryTree
				self.navigationAPI.loadCategoryTree(categoryURL,
					function(response) {
						var rootItem = $(response).contents().children().children();
						self.getTreeViewHtml(rootItem, function(returnValue){
							commonVariables.categoryId = $(rootItem).parent().attr('id');
							commonVariables.categoryPath = $(rootItem).parent().attr('path').replace(/%20/g ,'');
							commonVariables.category = decodeURI($($(rootItem)[0].getElementsByTagName("name")).text());
							commonVariables.categoryTreeContent = '<ul><li id="'+ $(rootItem).parent().attr('id') +'" path="'+ $(rootItem).parent().attr('path') +'"><span class="folder"><a>'+ decodeURI($($(rootItem)[0].getElementsByTagName("name")).text()) +'</a></span>'+ returnValue +'</li></ul>';
						});
					},

					function(textStatus) {
						commonVariables.categoryTreeContent = null;
					}
				);
			} catch(exception) {
				commonVariables.categoryTreeContent = null;
			}
		}
	});

	return Clazz.com.components.navigation.js.listener.NavigationListener;
});