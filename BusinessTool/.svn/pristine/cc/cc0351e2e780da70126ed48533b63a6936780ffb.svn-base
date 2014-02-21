define(["framework/WidgetWithTemplate", "pagination/listener/PaginationListener"] , function() {

	Clazz.createPackage("com.components.pagination.js");

	Clazz.com.components.pagination.js.Pagination = Clazz.extend(Clazz.WidgetWithTemplate, {
		facetsEvent : null,
		facetsHeader : null,
		localConfig: null,
		// template URL, used to indicate where to get the template
		templateUrl: "../components/pagination/template/pagination.tmp",
		configUrl: "../componens/pagination/config/config.json",
		name : commonVariables.pagination,
		paginationContainer :  commonVariables.paginationPlaceholder,
		currentObj : null,

		/***
		 * Called in initialization time of this class 
		 *
		 * @globalConfig: global configurations for this class
		 */
		initialize : function(globalConfig) {
			var self = this;
			this.registerHelperPaginationData(self.data);
			
			Handlebars.registerHelper('rowsOption', function(data) {
				var optionVal50, optionVal100, optionVal250, optionVal500, optionValAll, returnVal;
				
				if(data == 50){optionVal50 = '<option value="50" selected="true">50</option>'}else{optionVal50 ='<option value="50">50</option>'}
				
				if(data == 100){optionVal100 = '<option value="100" selected="true">100</option>'}else{optionVal100 ='<option value="100">100</option>'}
				
				if(data == 250){optionVal250 = '<option value="250" selected="true">250</option>'}else{optionVal250 ='<option value="250">250</option>'}
				
				if(data == 500){optionVal500 = '<option value="500" selected="true">500</option>'}else{optionVal500 ='<option value="500">500</option>'}
				
				if(data == 1000000){optionValAll = '<option value="1000000" selected="true">All</option>'}else{optionValAll ='<option value="1000000">All</option>'}
				
				returnVal = optionVal50 + optionVal100 + optionVal250 + optionVal500 + optionValAll;
				
				return returnVal;
			});
		},

		/***
		 * Called in once the page loads
		 *
		 */
		loadPage :function(){
			var paginationListener = new Clazz.com.components.pagination.js.listener.PaginationListener();
			Clazz.navigationController.push(this);			
		},

		/***
		 * Called after the preRender() and bindUI() completes. 
		 * Override and add any preRender functionality here and pushes the pagination data
		 *
		 * @element: Element as the result of the template + data binding
		 */
		postRender : function(element) {
			this.pagination(this.data.pageIndex);
		},

		/***
		 * pageRefresh for refreshing the page
		 *
		 * @data: result to populate the pagination data
		 */
		pageRefresh : function (data) {
			var self = this;
			var renderFunction = $.proxy(self.renderTemplate, self);
			
			self.data = data;
			self.registerHelperPaginationData(data);
			renderFunction(data, self.paginationContainer);
			$("#rowsPerPage option[value='"+data.rowsPerPage+"']").attr("selected", "selected");
		},

		/***
		 * registerHelperPaginationData Handlebars for compare feature
		 *
		 * @data: result to populate the pagination data
		 */
		registerHelperPaginationData : function(data) {
			var self = this;

			Handlebars.registerHelper('compare', function (lvalue, operator, rvalue, options) {
			    var operators, result;

			    if (arguments.length < 3) {
			        throw new Error("Handlerbars Helper 'compare' needs 2 parameters");
			    }
			    
			    if (options === undefined) {
			        options = rvalue;
			        rvalue = operator;
			        operator = "===";
			    }
			    
			    operators = {
			        '==': function (l, r) { return l == r; },
			        '===': function (l, r) { return l === r; },
			        '!=': function (l, r) { return l != r; },
			        '!==': function (l, r) { return l !== r; },
			        '<': function (l, r) { return l < r; },
			        '>': function (l, r) { return l > r; },
			        '<=': function (l, r) { return l <= r; },
			        '>=': function (l, r) { return l >= r; },
			        'typeof': function (l, r) { return typeof l == r; }
			    };
			    
			    if (!operators[operator]) {
			        throw new Error("Handlerbars Helper 'compare' doesn't know the operator " + operator);
			    }
			    
			    result = operators[operator](lvalue, rvalue);
			    
			    if (result) {
			        return options.fn(this);
			    } else {
			        return options.inverse(this);
			    }

			});

			this.pagination(data.pageIndex);
		},

		/***
		 * pagination to hide and show the next and preview buttons
		 *
		 * @pagenumber: based on the page number the pagination buttons disables
		 */
		pagination : function(pagenumber) {
			var data = this.data;
			//var pageIndex = data.pageIndex;
			var pageIndex = pagenumber;
			var rowsPerPage = data.rowsPerPage;
			var totalCountOfRecords = data.totalCountOfRecords;
			var totalCountOfPages = data.totalCountOfPages;
			
			if (pageIndex == 1 && totalCountOfPages == 1) {
				$("#prevouspage").attr("disabled", "disabled");
				$("#prevouspage").addClass("btn_disable");
				$("#nextpage").attr("disabled", "disabled");
				$("#nextpage").addClass("btn_disable");
			} else if ((pageIndex > 1) && (pageIndex < totalCountOfPages)) { 
				$("#prevouspage").removeAttr("disabled");
				$("#prevouspage").removeClass("btn_disable");
				$("#nextpage").removeAttr("disabled");
				$("#nextpage").removeClass("btn_disable");
			} else if ((pageIndex > 1) && (pageIndex == totalCountOfPages)) {
				$("#prevouspage").removeAttr("disabled");
				$("#prevouspage").removeClass("btn_disable");
				$("#nextpage").attr("disabled", "disabled");
				$("#nextpage").addClass("btn_disable");
			} else {
				$("#prevouspage").attr("disabled", "disabled");
				$("#prevouspage").addClass("btn_disable");
			}
		},

		/***
		 * PageNumberValidation to validate the pageNumber
		 *
		 * @pageNumber: paginations current pagenumber
		 * @totalPage: total page of the result
		 */
		PageNumberValidation : function(pageNumber, totalPage) {

			if(pageNumber >= 1 && pageNumber <= totalPage) {
				return pageNumber;
			} else {
				if(pageNumber < 1) {
					return 1;
				} else if(pageNumber > totalPage) {
					return totalPage;
				}
			}
		},

		/***
		 * search to execute the search functionality
		 *
		 * @pagenumber: pagenumber to show the page
		 */
		search : function(pageNumber) {
			var self = this;
			var searchCriteria = {};
			searchCriteria.key = $("#searchOption").val();
			searchCriteria.value = $("#txtSearch").val();
			var rowsPerPage=$('#rowsPerPage').val();
			self.currentObj.doPagination(searchCriteria, pageNumber,rowsPerPage);
		},
		
		/***
		 * Bind the action listeners. The bindUI() is called automatically after the render is complete 
		 *
		 */
		bindUI : function(){
			var self = this;
			var data = self.data;
			$('#nextpage').click(function (e) {

			var pagenumber = $("#paignation").val();
				pagenumber++;
				
				pagenumber = self.PageNumberValidation(pagenumber, self.data.totalCountOfPages);
				self.pagination(pagenumber);
				self.search(pagenumber);
			});
			$('#prevouspage').click(function () {

				var pagenumber = $("#paignation").val();
				pagenumber--;
				
				pagenumber = self.PageNumberValidation(pagenumber, self.data.totalCountOfPages);
				self.pagination(pagenumber);
				self.search(pagenumber);
			});
			
			
			$("#paignation").keypress(function(e){
				if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
					code= (e.keyCode ? e.keyCode : e.which);
					if (code == 13) {
						self.search(self.PageNumberValidation($("#paignation").val(), self.data.totalCountOfPages));
					}
					return false;
				}
				
			});
			$('#rowsPerPage').change(function () {
				var pagenumber = 1;
				self.search(pagenumber);
			});
		}
	});

	return Clazz.com.components.pagination.js.Pagination;
});