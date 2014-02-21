define(["abstract/API"], function(){
	Clazz.createPackage("com.js.api");

	Clazz.com.js.api.LocalStorageAPI = Clazz.extend(Clazz.com.js.abstract.API, {
		emptyString : "",
		emptyArray : [],
		keyCategoryTree: "keyCategoryTree",

		/******************* Temporary treeview function start *********************/
		
		setCategoryTree : function(categoryTree) {
			localStorage.removeItem(this.keyCategoryTree);
			localStorage.setItem(this.keyCategoryTree, categoryTree);
		},

		getCategoryTree : function() {
			var categoryTreeString = localStorage.getItem(this.keyCategoryTree);
			return categoryTreeString;
		},

		clearCategoryTree : function() {
			this.setCategoryTree(null);
		}
		
		/******************* Temporary treeview function end *********************/
		
	});
	
	return Clazz.com.js.api.LocalStorageAPI;
});