//Clear txtSearch var, in case of refresh in Chrome.
$('#txtSearch').html('');
// From JSON, will contain brand, categories_facet, debug, documents, num_found
var myobject = [];
var myString = "";
// Contains start index for search results
var page;
var facetFields, rows, numberOfDocumentsInSearch = 0, currentPage = 1, pages, previousCategory, previousBrand, previousPrice, catValue, previousCollection;
var allHeaders = [];
var queryHeaders = [];
var sortBy = '';
var aPIurl = '';
var facets = [];
var selectedFacets = [];
var selectedFacetNames = [{}];
// Singleselect selections
var categorySelected;
/*******************************************************************************
 * Clear input, and refresh page
 ******************************************************************************/
function reload() {
	// TODO: clear form
	$('input').val('');
	$('input').attr('value', '');
	window.location.reload();
};

function init() {
	"use strict";
	createUi();
};

function createUi() {
	//Datepicker
	 $( "#datepicker" ).datepicker({ dateFormat: "yy-mm-dd" });
	// Auto Suggest
	$(function() {
		$.ajaxSetup({
			type : "post"
		});
		$("#txtSearch").autocomplete({
			// relative path
			source : "../externalWS/suggestQuery?ip=" + aPIurl
		// minLength: 2,
		});
	});
	// UI: Auto complete fields for Names of Fields to Query, and Names of
	// Fields to display. TODO: Remove or reimplement Autocomplete options non
	// static.
	"use strict";
	$(function() {
		var availableTags = [ "longprdlbl", "modelnumber", "skuid", "childskuforbundle", "UPC", "Artistname" ];

		function split(val) {
			return val.split(/,\s*/);
		}

		function extractLast(term) {
			return split(term).pop();
		}
		$("#fltext").bind("keydown", // don't navigate away from the field on
		// tab when selecting an item
		function(event) {
			if (event.keyCode == $.ui.keyCode.TAB && $(this).data("autocomplete").menu.active) {
				event.preventDefault();
			}
		}).autocomplete({
			minLength : 0,
			source : function(request, response) {
				// delegate back to auto complete, but extract the last
				// term
				response($.ui.autocomplete.filter(availableTags, extractLast(request.term)));
			},
			focus : function() {
				// prevent value inserted on focus
				return false;
			},
			select : function(event, ui) {
				var terms = split(this.value);
				// remove the current input
				terms.pop();
				// add the selected item
				terms.push(ui.item.value);
				// add placeholder to get the comma-and-space at the end
				terms.push("");
				this.value = terms.join(", ");
				return false;
			}
		});
		$("#qftext").bind("keydown",
		// don't navigate away from the field on tab when selecting an item

		function(event) {
			if (event.keyCode == $.ui.keyCode.TAB && $(this).data("autocomplete").menu.active) {
				event.preventDefault();
			}
		}).autocomplete({
			minLength : 0,
			source : function(request, response) {
				// delegate back to auto complete, but extract the last
				// term
				response($.ui.autocomplete.filter(availableTags, extractLast(request.term)));
			},
			focus : function() {
				// prevent value inserted on focus
				return false;
			},
			select : function(event, ui) {
				var terms = split(this.value);
				// remove the current input
				terms.pop();
				// add the selected item
				terms.push(ui.item.value);
				// add placeholder to get the comma-and-space at the end
				terms.push("");
				this.value = terms.join(", ");
				return false;
			}
		});
	});

	// UI: Create Slider for number of documents
	$(function() {
		$("#slider").slider({
			value : 15,
			min : 15,
			max : 99,
			step : 1,
			slide : function(event, ui) {
				$("#RowsField").val(ui.value);
			}
		});

		// Ensure value of RowsField is equil to the slider value.
		$("#RowsField").val = $("#slider").slider("value");
	});

	// UI: Create Accordion for left navigation
	$(function() {
		$(".queryOptions").accordion({
			collapsible : true
		});
		$("#reserved_space").accordion();
	});
	$("input[type='text'].url").change(function() {
		aPIurl = $("input[type='text'].url").val()
	});
}

function clearFields(action) {
	// Clear Search information, Search results. Show Search buttons after a
	// search is executed.
	$('div.queryResults').html(''); // Clear info and results containers on new
	// search.
	$('#results .query').html('');
	$('.header').html('');
	$('ul.facetfield').html('');
	$('.start').html('');
}

function setQuery(query) {
	$("#txtSearch").val(query);
	performQuery('search');
}
// Starts query
function performQuery(action, categoryParameter, brandParameter, pageNumber) {
	allHeaders = [];
	var categoryFacetFieldsParameter = '', brandFacetFieldsParameter = '', collectionFacetFieldsParameter = '';
	$(".sortBy").attr("style", "visibility: visible");
	// Actions executed if brand facet is selected
	if (action == 'brand') {
		// Store Brand for use in a Category/Price/DND Facet
		previousBrand = brandParameter;
		// Append FQ brand to RestUrl
		if (previousBrand != '' && previousBrand != null) {
			// A new search or next page, with brand.
			brandFacetFieldsParameter = "brand_facet=" + previousBrand;
			catValue = categorySelected;
			catValue = catValue.concat(" > " + previousBrand);
		} else {
			brandFacetFieldsParameter = "";
		}
		// Append category to RestUrl
		if (previousCategory != null && previousCategory != '') {

			categoryFacetFieldsParameter = "categories=" + previousCategory;
		} else {
			categoryFacetFieldsParameter = "";
		}
		// Append Price to RestUrl
		if (previousPrice != null && previousPrice != '') {
			priceFacetFieldsParameter = "currentprice_facet=" + previousPrice;
		} else {
			priceFacetFieldsParameter = "";
		}
		// Append Collection to RestUrl
		if (previousCollection != null && previousCollection != '') {
			collectionFacetFieldsParameter = "collection_facet=" + previousCollection;
		} else {
			collectionFacetFieldsParameter = "";
		}
	}
	// Actions executed if category facet is selected
	if (action == 'category') {
		categorySelected = "categories=" + categoryParameter;
	}

	if (action == 'price') {
		previousPrice = categoryParameter;
		// Append FQ brand to RestUrl
		if (previousBrand != '' && previousBrand != null) {
			// A new search or next page, with brand.
			brandFacetFieldsParameter = "brand_facet=" + previousBrand;
		} else {
			brandFacetFieldsParameter = "";
		}
		// Append category to RestUrl
		if (previousCategory != null && previousCategory != '') {

			categoryFacetFieldsParameter = "categories=" + previousCategory;
		} else {
			categoryFacetFieldsParameter = "";
		}
		// Append Price to RestUrl
		if (previousPrice != null && previousPrice != '') {
			priceFacetFieldsParameter = "currentprice_facet=" + previousPrice + "";
			catValue = catValue.concat(" > " + previousPrice);
		} else {
			priceFacetFieldsParameter = "";
		}
		// Append Collection to RestUrl
		if (previousCollection != null && previousCollection != '') {
			collectionFacetFieldsParameter = "collection_facet=" + previousCollection;
		} else {
			collectionFacetFieldsParameter = "";
		}
	}
	if (action == 'collection') {
		previousCollection = categoryParameter;
		// Append FQ brand to RestUrl
		if (previousBrand != '' && previousBrand != null) {
			// A new search or next page, with brand.
			brandFacetFieldsParameter = "brand_facet=" + previousBrand;
		} else {
			brandFacetFieldsParameter = "";
		}
		// Append category to RestUrl
		if (previousCategory != null && previousCategory != '') {
			categoryFacetFieldsParameter = "categoryname=" + previousCategory;
		} else {
			categoryFacetFieldsParameter = "";
		}
		// Append Price to RestUrl
		if (previousPrice != null && previousPrice != '') {
			priceFacetFieldsParameter = "currentprice_facet=" + previousPrice + "";
		} else {
			priceFacetFieldsParameter = "";
		}
		// Append Collection to RestUrl
		if (previousCollection != null && previousCollection != '') {
			collectionFacetFieldsParameter = "collection_facet=" + previousCollection;
		} else {
			collectionFacetFieldsParameter = "";
		}
	}
	// calculateFQs();

	// Actions executed if search or debug buttons are pressed
	if (action == 'search' || action == 'debug') {
		// empty previous fields for new search.
		previousCategory = '';
		previousBrand = '';
		previousPrice = '';
		previousCollection = '';
		catValue = "All Categories";
		currentPage = '1';
		// Append fq category to RestUrl
		categoryFacetFieldsParameter = "";
		brandFacetFieldsParameter = "";
		currentPriceParameter = '';
		// collectionFacetFieldsParameter= '';
		priceFacetFieldsParameter = '';
	}
	// Actions executed if debug buttons are pressed
	if (action == 'debug') {
		// Set query field for url
		query = categoryParameter;
	}

	// Actions executed if previous or next button is pressed
	if (action == 'next' || action == 'previous') {
		if (previousPrice != '' && previousPrice != null) {
			currentPriceParameter = "categories=" + previousPrice;
		} else {
			currentPriceParameter = '';
		}

		// Append FQ brand to RestUrl
		if (previousBrand != '' && previousBrand != null) {
			// A new search or next page, with brand.
			brandFacetFieldsParameter = "brand_facet=" + previousBrand;
		} else {
			brandFacetFieldsParameter = "";
		}
		// Append category to RestUrl
		if (previousCategory != null && previousCategory != '') {
			categoryFacetFieldsParameter = "categories=" + previousCategory;
		} else {
			categoryFacetFieldsParameter = "";
		}
		// Append Price to RestUrl
		if (previousPrice != null && previousPrice != '') {
			priceFacetFieldsParameter = "currentprice_facet=" + previousPrice + "";
		} else {
			priceFacetFieldsParameter = "";
		}
		// Append Collection to RestUrl
		if (previousCollection != null && previousCollection != '') {
			collectionFacetFieldsParameter = "collection_facet=" + previousCollection;
		} else {
			collectionFacetFieldsParameter = "";
		}
	}

	// Actions executed if previous button is pressed
	if (action == 'previous') {
		if (categoryParameter == '' || categoryParameter == null) {
			categoryParameter = previousCategory;
		}
		// Store Brand for use in a Category Facet
		previousBrand = brandParameter;
		categoryFacetFieldsParameter = previousCategory;
		// Append FQ brand to RestUrl
		if (previousBrand != '' && previousBrand != null) {
			// A Search, Next Page, no brand and no category.
			brandFacetFieldsParameter = "brand_facet=" + brandParameter;
		} else {
			brandFacetFieldsParameter = "";
		}
		// Append category to RestUrl
		if (previousCategory != null && previousCategory != '') {

			categoryFacetFieldsParameter = "categories=" + previousCategory;
		} else {
			categoryFacetFieldsParameter = '';
		}
	}

	// Compute REST URL based on whether user requested search, previous, or
	// next.
	/* Rest API Call */
	var baseRestUrl = location.host + "../externalWS/searchSite/";
	baseRestUrl = baseRestUrl.replace("index.html", "");
	// queryRestUrl = queryRestUrl.replace("index.html", "");

	var query = $("#" + "txtSearch").val();
	startElement = document.getElementById("startField");
	var startStr = startElement.value;
	var pageParameter = '';
	startStr = parseInt(startStr, 10);

	// Null search now searches all options
	if (query == null || query == '') {
		query = "*";
	}

	if (startStr != null && startStr != '') {

		if (startStr >= 1) {
			pageParameter = '&page=' + escape(startStr);
			page = escape(startStr);
			// Clear starField value after it is processed
			$("#startField").replaceWith('<input type="text" onkeydown="Javascript: if (event.keyCode==13) performQuery(\'search\');" size="3" id="startField" name="startField">');

		} // else {alert("please enter valid Start parameter."); }

	}

	// Fetch the rows input field.
	var rowsElement = document.getElementById("RowsField"), rowsStr = rowsElement.value, rowsParameter = '', debug = '', newPage, returnFacetFieldsParameter = '';
	if (rowsStr != null && rowsStr != '') {
		if (pageParameter == null || pageParameter == '') {
			rowsParameter = '&rows=' + escape(rowsStr);
		} else {
			rowsParameter = 'rows=' + escape(rowsStr);
		}
	}
		// Capture all selected Facets

		$('.facetContainer input:checked').each(function() {
		// Parent Name
		var facetName = $(this).parent().parent().attr('class');
		// Facet Value
		var selectValue = $(this).attr('name');
		// Check if facet exists
		if (selectedFacetNames[0][facetName] == undefined) {
			// If facet does not exist, make facet then populate value.
			selectedFacetNames[0][facetName] = [ selectValue ];
		} else {
			if (selectedFacetNames[0][facetName].selectValue == undefined) {
				// Populate value.
				selectedFacetNames[0][facetName].push(selectValue);
			}
			;
		}
		;
		// selectedFacetNames = removeDuplicates(selectedFacetNames);
	});
	
	// Return all fields if in debug mode.
	if (action == 'debug') {
		query = categoryParameter;
		debug = "&debug=true";
	}
	// Fetch & Compute the facet fields parameter ("ff")
	// TODO: Build Checkbox Constructor for Facets that interacts with UI.
	if (facetFields != null && factFields != '') {
		returnFacetFieldsParameter = '';
	} else {
		returnFacetFieldsParameter = ""; // If collections come back add
		// &ff=dndcollectionenrich
	}

	if (pageNumber != '' && pageNumber != null) {
		page = pageNumber;
		pageParameter = '&page=' + pageNumber;
	}

	// Update Rows
	rows = parseInt(rowsElement.value, 10);

	// Force integer values
	// page = parseInt(page, 10);
	// rows = parseInt(rows, 10);
	// Compute start for next & previous page.
	if (action == 'previous') {
		newPage = (parseInt(page, 10)) - 1;
		if (newPage < 1) {
			newPage = 1;
		} else {
			page = newPage;
		}
		if (newPage != 1) {
			pageParameter = '&page=' + newPage;
		} else {
			pageParameter = '&page=1';
		}

	} else if (action == 'next') {
		// Compute page for next page.
		newPage = (parseInt(page, 10)) + 1;
		if (newPage > numberOfDocumentsInSearch) {
			newPage = page;
		} else {
			page = newPage;
		}
		pageParameter = '&page=' + page;

	} else if (action == 'change') {
		// Compute page action.
		page = pageNumber;
		// Append FQ brand to RestUrl
		if (previousBrand != '' && previousBrand != null) {
			// A new search or next page, with brand.
			brandFacetFieldsParameter = "brand_facet=" + previousBrand;
		} else {
			brandFacetFieldsParameter = "";
		}
		// Append category to RestUrl
		if (previousCategory != null && previousCategory != '') {
			categoryFacetFieldsParameter = "categories=" + previousCategory;
		} else {
			categoryFacetFieldsParameter = "";
		}
		// Append Price to RestUrl
		if (previousPrice != null && previousPrice != '') {
			priceFacetFieldsParameter = "currentprice_facet=" + previousPrice + "";
		} else {
			priceFacetFieldsParameter = "";
		}
		// Append Collection to RestUrl
		if (previousCollection != null && previousCollection != '') {
			collectionFacetFieldsParameter = "collection_facet=" + previousCollection;
		} else {
			collectionFacetFieldsParameter = "";
		}
	}

	// Reset Page Counts for Facets
	if (action == 'category') {
		page = 1;
		currentPage = 0;
	}
	if (action == 'brand') {
		page = 1;
		currentPage = 0;
	}
	if (action == 'price') {
		page = 1;
		currentPage = 0;
	}
	// Reset Page Counts for new search

	if (action == 'search' || action == 'debug') {
		if (page == "" || page == null) {
			page = 1;
		}
		currentPage = 0;
		// Clear fq field for a new search
		categoryFacetFieldsParameter = '';
		// Clear fq field for a new search
		brandFacetFieldsParameter = '';
	}
	var sortBy = "sort=" + $("#Sort :selected").val();
	// alert(sortBy);
	currentPage = page;
	var question = '';

	
	//old
	
	// Construct the Rest Url
	query = (query.replace('%20', ' ')).replace(/\s+/g, '-');
	var queryRestUrl = query;
	var displayUrl = aPIurl + "/search-api/search/";
	// New Singleselect category method
	if (categorySelected != '' && categorySelected != null) {
		categorySelected = categorySelected.replace("&", "%26");
	}

/*	// New Multiselect methods
	var addBrand = createAttribute('brand_facet');
	if (categorySelected != '' && categorySelected != null) {
		categorySelected = categorySelected.replace("&", "%26");
	}
	var addCollection = createAttribute('collection_facet');
	if (addCollection != '' && addCollection != null) {
		addCollection = addCollection.replace("&", "%26");
	}
	var addoffers = createAttribute('currentoffers_facet');
	if (addoffers != '' && addoffers != null) {
		addoffers = addoffers.replace("&", "%26");
	}
	var addPrice = createAttribute('currentprice_facet');

	//queryRestUrl = queryRestUrl + priceFacetFieldsParameter;
	// queryRestUrl = queryRestUrl + currentPriceParameter;
*/
	//new
	
	//render all facets
	var i=0;
	var facetNumber=0;
	for(key in selectedFacetNames[0])
	{
		facetNumber++;
		var facetValue = selectedFacetNames[0][key];
		for (i=0; i < facetValue.length; i++){
			var urlSafeKey=$('.'+key+' [name='+facetValue[i]+']').next().html();
			urlSafeKey = urlSafeKey.replace(/\(\d*\)/, '');
			urlSafeKey = encodeSpecialCharicters(urlSafeKey);			
			urlSafeKey=$.trim(urlSafeKey);
			//TODO: apply lowercase.
			//urlSafeKey = urlSafeKey.toLowerCase();

			//console.log(urlSafeKey);
			//If the first facet Value
			if (i==0){
				//If not the first facet
				if(facetNumber>1){
					queryRestUrl = queryRestUrl+"&";	
				}
				queryRestUrl = queryRestUrl +'&'+ key +'='+urlSafeKey;
			}
			//If not the first facet Value
			if (i>0){
				queryRestUrl = queryRestUrl +','+urlSafeKey;
				//If the last facet Value
				if((i+1)==facetValue.length){
					//queryRestUrl = queryRestUrl + facetValue[i];	
				}else{
					//If not the last facet Value
					queryRestUrl = queryRestUrl +',';					
				}
			}
		}
	}

	
	// TODO: Add New platform facet, platform_facet.
	// Query options
	queryRestUrl = queryRestUrl + question;
	if ($('#debugfield').attr('checked')) {
		debug = '&debug=true&debug.explain.structured=true';
	}
	var queryOptions = pageParameter + debug + rowsParameter;
	if (sortBy != null && sortBy != '') {
		/* if (rowsParameter != null && rowsParameter != ''){ */
		queryOptions = queryOptions + '&' + sortBy;
		//console.log(sortBy);
		/*
		 * }else{ queryOptions = queryOptions +sortBy; }
		 */
	}
	queryRestUrl = queryRestUrl + queryOptions;
	queryRestUrl = queryRestUrl.replace(/,,/g, ",");
	queryRestUrl = queryRestUrl.replace('?&', '?');
	encodeURIComponent(queryRestUrl);

	/*
	 * queryRestUrl = queryRestUrl.replace(' ,', ','); queryRestUrl =
	 * queryRestUrl.replace(' ?', '?'); queryRestUrl =
	 * queryRestUrl.replace(',?', '?'); queryRestUrl = queryRestUrl.replace('
	 * //', '/'); queryRestUrl = queryRestUrl.replace(' //?', '/?');
	 * queryRestUrl = queryRestUrl.replace(' /&', '/'); queryRestUrl =
	 * queryRestUrl.replace( "= ", "=");
	 */
	displayUrl = displayUrl + queryRestUrl;
	$.trim(aPIurl);
	aPIurl = $("input[type='text'].url").val();
	if (aPIurl == '') {
		aPIurl = null;
	}
	;
	var querydate = $("input[name='querydate']").val();
	if (querydate != null) {
		querydate = "&querydate="+querydate;
	}
	else {
		querydate ='';
	};
	if (aPIurl != null) {
		aPIurl = aPIurl.replace("http://", "");

		queryRestUrl = "../externalWS/searchSite/?ip=" + aPIurl + "&query=" + queryRestUrl+querydate;
	} else {
		queryRestUrl = "../externalWS/searchSite/?query=" + queryRestUrl+querydate;
	};
	if (aPIurl == '') {
		aPIurl = null;
	};

	
	// Clear fields before search.
	clearFields(action);
	// alert(queryRestUrl);
	$
			.ajax({

				url : queryRestUrl,
				type : 'POST',
				datatype : 'json',
				success : function(data) {

					myobject = jQuery.parseJSON(data);

					// Feature: Redirect
					if (myobject.redirect[0] != '' && myobject.redirect[0] != null)
						if (myobject.redirect[0] == 'none') {
						} else {
							{
								window.location = myobject.redirect[0];
							}
						}

					// Capture numberOfDocumentsInSearch from JSON return &
					// calculate number of pages.
					numberOfDocumentsInSearch = myobject.num_found;
					pages = Math.ceil(numberOfDocumentsInSearch / rows);

					// Get number of documents actually returned
					var myarray = myobject.documents, myarraylen = myarray.length;
					renderNavigationTop();
					renderNavigationBottom();
					// Process Documents

					// Clear old Facets values
					$(".leftnav").html('');

					$(".queryResults:last").prepend("<table id='results' class='tablesorter' ><thead>");
					$("ul.categories:last").append('<label for="RowsField">Categories :</label>');
					if (myobject.categoriesseq != '' && myobject.categoriesseq != null) {
						$(".leftnav:last").append('<div class="queryOptions"><h3>Categories</h3><div><ul class="categories facetfield"></ul></div></div>');
					}
					;
					if (myobject.brand != '' && myobject.brand != null) {
						$(".leftnav:last").append('<div class="queryOptions"><h3>Brand</h3><div><ul class="brand facetfield"></ul></div></div>');
					}
					;
					if (myobject.currentprice != '' && myobject.currentprice != null) {
						$(".leftnav:last").append('<div class="queryOptions"><h3>Price</h3><div><ul class="prices facetfield"></ul></div></div>');
					};
					if (myobject.dndcollectionenrich != '' && myobject.dndcollectionenrich != null) {
						$(".leftnav:last").append('<div class="queryOptions"><h3>Collections</h3><div><ul class="collections facetfield"></ul></div></div>');
					};
					if (myobject.currentoffers != '' && myobject.currentoffers != null) {
						$(".leftnav:last").append('<div class="queryOptions"><h3>Current Offers</h3><div><ul class="offers facetfield"></ul></div></div>');
					};
					$(".leftnav:last")
							.append(
									'<div class="queryOptions"><h3>Advanced Index options</h3><div><ul class="advanced"><li><label for="debugfield">Debug</label><input type="checkbox" value="15" id="debugfield" name="debugfield" size=50 onKeydown="Javascript: if (event.keyCode==13) performQuery(\'search\');"/></li><li><input type="text" class="disabled-nav" name="startField" id="startField" size=3 onKeydown="Javascript: if (event.keyCode==13) performQuery(\'search\');" /></li><li><label for="RowsField"># of documents to return :</label><input type="text" value="15" id="RowsField" name="RowsField" size=50 onKeydown="Javascript: if (event.keyCode==13) performQuery(\'search\');"/><div id="slider" onKeydown="Javascript: if (event.keyCode==13) performQuery(\'search\');"></div></li><!--<li><label for="qftext">Names Of Fields To Query :</label><input type="text" name="qftext" id="qftext" size=50 onKeydown="Javascript: if (event.keyCode==13) performQuery(\'search\');" /></li><li><label for="fltext">Names Of Fields To Display :</label><input type="text" name="fltext" id="fltext" size=50 onKeydown="Javascript: if (event.keyCode==13) performQuery(\'search\');" /></li>--></ul></div></div>');
					$(".queryOptions").accordion({
						collapsible : true
					});

					// Render Banners TODO: Update banner contract
					var banners = myobject["banners:"];
					if (banners != '' && banners != null) {
						for (i = 0; i < banners.length; i++) {
							var showbanner = myobject.banners[0].url;
							$(".queryResults:last").prepend("<p class='banner url'>" + displayUrl + "</p>");
						}
					}

					$("#reserved_space").accordion();
					renderTopNav();

					// Render Facets
					getFacets();

					// Select Rendered Facets
					selectFacets();

					// Compile queries
					$('#results:last').append("</thead><tbody class='resultsclass'>");
					$('#results:last').append("</thead><tbody>");
					if (action != 'debug') {
						// renderStaticHeader();
						renderDynamicHeader();
						renderDynamicResults();
					}
					/* Pass category in debug mode */
					if (action == 'debug') {
						renderDynamicHeader();
						renderDynamicQueryResults(categoryParameter);
					}
					$('#results tbody:last').append("</tbody>");

					/* Clean Category Value */
					for ( var i = 0; i < catValue.length; i++) {
						catValue = catValue.replace("-_-", "/");
						catValue = catValue.replace("%20", " ");
					}
					// Feature: Render Build Number
					var buildNumber = 'Build Number: Undefined';
					if (myobject.buildnumber != undefined){
					if (myobject.buildnumber != '' && myobject.buildnumber != null)
						{
						buildNumber = 'Build Number: '+myobject.buildnumber;
						}
					} 	
					/* Render Category Value */
					$(".queryResults:last").prepend("<p>URL Used to get the search Results : </p><br><p id='url' class='url'>" + displayUrl
							+ "</p><br><p class='url field'> Number of search results (on page):<span id='numberofDocumentsOnPage'> " + myarraylen
							+ "</span></p><p class='url field'>Number of search results (total): <span id='totalNumberofSearchResults'>" + myobject.num_found + "</span></p>"
							+"<p id='cat' class = 'url field'>" + myobject.num_found + " items found for " + query + " in " + catValue + " </p>"+"<p id='cat' class = 'url field'>" + buildNumber+"</p>");


					// Render Did you mean
					var didYouMean = myobject.suggestQueryInfo;
					if (didYouMean.suggestQuery != null && didYouMean.suggestQuery != '') {
						var didYouMeanValue = didYouMean.suggestQuery;
						$(".queryResults:last").prepend(
								"<p class='didYouMean'>Showing Results for: <a class='didYouMean url' onClick=\"Javascript: setQuery(\'" + didYouMeanValue + "\');\">" + didYouMeanValue
										+ "</a></p></br>");

					}

					// Call the tablesorter plugin
					$("#results").tablesorter({
						widthFixed : true,
						widgets : [ 'zebra' ]
					});

					function renderNavigationBottom() {
						'use strict';
						var i;
						$(".queryResults:last").append("<table class='pageNumber'><thead><tr class='queryheaders'>" + "<td class='pagination'>Page: " + page + " / " + pages + "</td></tr>");
						// Append Previous Button
						$(".queryResults:last tr:last").append("<td class='searchNavigation'></td>");

						if (page > 1) {
							$(".queryResults:last tr:last td:last").append("<input type='button' class='previous' value='Previous Page' onClick='performQuery(\"previous\");'>");
							$(".pageNumberTop:last").append("<input type='button' class='previous' value='Previous Page' onClick='performQuery(\"previous\");'>");
						}

						// create search page links
						var startNavigation = parseInt(page) - 5;
						if (startNavigation < 0) {
							startNavigation = 0;
						}
						for (i = startNavigation; i < pages && i < (startNavigation + 10); i++) {
							var pageNumber = i + 1;
							$(".queryResults:last tr:last td:last").append(
									"<input type='button' class='pages' value='" + pageNumber + "' onClick='performQuery(\"change\",\"\",\"\",\"" + (i + 1) + "\" );'>");
							$(".pageNumberTop:last").append("<input type='button' class='pages' value='" + pageNumber + "' onClick='performQuery(\"change\",\"\",\"\",\"" + (i + 1) + "\" );'>");
						}
						if ((parseInt(page, 10)) != pages) {
							// Append Next Button
							$(".queryResults:last .queryheaders:last td:last").append("<input type='button' class='next' value='Next Page' onClick='performQuery(\"next\");' >");
							$(".pageNumberTop:last").append("<input type='button' class='next' value='Next Page' onClick='performQuery(\"next\");' >");
						}
					}
					function renderNavigationTop() {
						'use strict';
						var i;
						$(".pageNumberTop:last").append("<td class='searchNavigation'></td>");
						$(".queryResults #results").append("<table class='pageNumber'><thead><tr class='queryheaders'>" + "<td class='pagination'>Page: " + page + " / " + pages + "</td></tr>");
						// Append Previous Button
						$(".pageNumber:last").append("<td class='searchNavigation'></td>");

						if (page > 1) {
							$(".pageNumber .queryResults:last tr:last td:last").append("<input type='button' class='previous' value='Previous Page' onClick='performQuery(\"previous\");'>");
						}

						// create search page links
						var startNavigation = parseInt(page) - 5;
						if (startNavigation < 0) {
							startNavigation = 0;
						}
						for (i = startNavigation; i < pages && i < (startNavigation + 10); i++) {
							var pageNumber = i + 1;
							$(".pageNumber .queryResults tr:last td:last").append(
									"<input type='button' class='pages' value='" + pageNumber + "' onClick='performQuery(\"change\",\"\",\"\",\"" + (i + 1) + "\" );'>");
						}
						if ((parseInt(page, 10)) != pages) {
							// Append Next Button
							$(".pageNumber .queryResults .queryheaders:last td:last").append("<input type='button' class='next' value='Next Page' onClick='performQuery(\"next\");' >");
						}
					}

				},
				error : function() {
					alert('Error please try again (ajax call)!');
				},
			// beforeSend: setHeader
			});

	document.close();
}

// Check Parameters for illegal charicters before constructing URL
function checkProperty(property) {
	// property = property.replace("Rocketfish&#153;","Rocketfish™");
	// alert (property);
	if (property.substring(property.length - 1) == " ") {
		property = property.substring(0, property.length - 1);
	}
	while (property.charAt(0) == ' ') {
		property = property.substr(1);
	}
	property = escape(property);

	property = property.replace("/", "-_-");
	return property;
}

function removeDuplicates(array) {
    var temp = {};
    for (var i = 0; i < array.length; i++)
        temp[array[i]] = true;
    var r = [];
    for (var k in temp)
        r.push(k);
    return r;
}

function renderTopNav() {
	$(".queryResults:last").prepend("<table class='pageNumberTop'><thead><tr class='queryheaders'>" + "<td class='pagination'>Page: " + page + " / " + pages + "</td></tr>");
	var store = [];
	$(".pageNumberTop").html('');
	store = $('.pageNumber').html();
	$(".pageNumberTop").append(store);
}
function getFacets() {
	// Get all facets

	if (myobject.facets != null && myobject.facets != '') {
		for ( var i = 0; myobject.facets.length > i; i++) {
			if (myobject.facets[i].facetDisplay == "Yes"){
			// Define current facet
			var facet = myobject.facets[i];

			// Variables sent from SAS
			facet.depFacetField;
			facet.depFacetFieldValue;
			facet.displayContext;
			facet.displayDepFacet;
			facet.displayFacetRemoveLink;
			facet.displayMobileFacet;
			facet.displayMobileFacetRemoveLink;
			var facetDisplayName = facet.displayName;
			//facetDisplayName = decodeSpecialCharicters(facetDisplayName);
		
			facet.displayRecurrence;
			facet.facetDisplay;
			var facetName = encodeURI(facet.facetField);
			var facetContainer = facetName + 'Container';
			facet.fieldValueInclusion;

			// Get number of brand for Facets, Proccess brand
			var facetLength = facet.values.length;

			$(".queryOptions:last")
					.before(
							"<div class='"
									+ facetContainer
									+ " facetContainer ui-accordion ui-widget ui-helper-reset ui-accordion-icons' role=\"tablist\"><h3 class=\"ui-accordion-header ui-helper-reset ui-state-default ui-state-active ui-corner-top ui-state-focus\" role=\"tab\" aria-expanded=\"true\" aria-selected=\"true\" tabindex=\"0\"><span class=\"ui-icon ui-icon-triangle-1-s\"></span>"
									+ facetDisplayName
									+ "</h3><div class=\"ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom ui-accordion-content-active\" style=\"height: 99px;\" role=\"tabpanel\"><ul class=\""
									+ facetName + "\"></ul></div></div");

			for ( var j = 0; j < facetLength; j++) {
				var facetDisplayValue = facet.values[j].value;
				facets += facetDisplayValue;
				var facetCount = facet.values[j].count;
				var facetValue = facetDisplayValue.replace(/ /g, "_");
				facetValue = facetValue.replace(/[^\w\s]/gi, '');
				var show = '';
				if (j < 5) {
					show = "show";
				}
				$("div." + facetContainer + " ul." + facetName + ":last").append(
						"<li> <input type='checkbox' onClick=\"Javascript: performQuery(\'search\');\" class=\"" + show + " " + facetValue + " hiddenList\" name=\"" + facetValue
								+ "\" value='brand'> <label class=\"hiddenList " + show + "\" href=\"javascript:performQuery(\'brand\',\'\',\'" + escape(facetDisplayValue) + "\')\">"
								+ facetDisplayValue + ' (' + facetCount + ")</label></li>");
				show = "";
			}
			$("div." + facetContainer + " ul." + facetName + ":last").append("<li><a class=\"show menu\" href=\"javascript:showMore(\'ul." + facetName + " li\') \" >Show More...</a></li>");
		}
	}
	}
	// UI: Create Accordion for Facets in left navigation
	$(".facetContainer").accordion({
		collapsible : true
	});
}
function decodeSpecialCharicters(data)
{
	data=data.replace(/<0x2c>/g, ',');
	data=data.replace(/<0x26>/g, '&');
	data=data.replace(/<0x2f>/g, '/');
	data=data.replace(/<0x5c>/g, '/\/' );
	data=data.replace(/<0x3d>/g, '=');
	return data;
}
function encodeSpecialCharicters(data)
{
	data=data.replace(/,/g, '<0x2c>');
	data=data.replace(/&amp;/g, '<0x26>');
	data=data.replace(/&/g, '<0x26>');
	data=data.replace('/', '<0x2f>');
	data=data.replace('/\/', '<0x5c>' );
	data=data.replace(/=/g, '<0x3d>');
	return data;
}
function renderDynamicHeader() {
	// get query types
	for (i = 0; i < myobject.documents.length; i++) {
		allHeaders[i] = $.map(myobject.documents[i], function(value, id) {
			if (id.length > 1) {
				return id;
			}
		});
	}
	// Create Dynamic Headers
	for (i = 0; i < allHeaders.length; ++i) {
		$.merge(allHeaders[0], allHeaders[i]);
	}

	allHeaders = allHeaders[0];
	allHeaders = eliminateDuplicates(allHeaders);

	// Query Header
	$('#results thead:last').append("<tr class='dynamicheader'>");
	// if you put your dynamic text fields in a container it'll be easier to get
	// them
	if (allHeaders != null && allHeaders != '') {
		for ( var i = 0; i < allHeaders.length; ++i) {
			$('<th>' + allHeaders[i] + '</th>').appendTo('.queryResults thead .dynamicheader:last');
		}
	}
}

function renderDynamicQueryResults(myid) {
	// Construct Search Results
	// Get all documents
	var queryModeArray = myobject.documents;
	// var queryModeArray = myobject.debug;
	// queryModeArray = queryModeArray.myid;
	// alert (myid);
	var i = 0;
	for (i = 0; i < queryModeArray.length; ++i) {
		$('.resultsclass:last').append("<tr class='query'>");
		var queryMode = queryModeArray[i];
		// Append Header Data from JSON
		for ( var queryModeHeader in queryMode) {
			var queryModeContent = queryMode[queryModeHeader];
			queryModeContent = removeStartingBracket(queryModeContent);
			queryModeContent = removeEndingBracket(queryModeContent);
			$('#results .resultsclass:last .query:last').append('<td class=' + queryModeHeader + '></td>');
			$('<p>' + queryModeContent + '</p>').appendTo('#results .resultsclass:last .' + queryModeHeader + ':last');
		}

		// Close Row
		$('.resultsclass td:last').append("</tr>");
	}
}

for (i = 0; i < allHeaders.length; ++i) {
	$(allHeaders[i]);
}

function renderDynamicResults() {
	if (allHeaders != null && allHeaders != '') {
		$('<th>Debug</th></tr>').appendTo('.queryResults thead .dynamicheader:last');
		// var myobject.documents = myobject.documents;
		var depth = myobject.documents.length;

		for (i = 0; i < myobject.documents.length; i++) {
			var myqueryarray = myobject.documents;
			var queryMode = myqueryarray[i];
			var getValue;
			$('.resultsclass:last').append("<tr class='query'></tr>");
			// for (var j = 0; j < allHeaders.length; ++j) {

			// Select Header
			for (j = 0; j < allHeaders.length; ++j) {
				// Render Header to console
				var displayType = allHeaders[j];
				//console.log(allHeaders[j]);
				if ((myobject.documents[i][allHeaders[j]]) != '' && (myobject.documents[i][allHeaders[j]]) != null) {
					//console.log(myobject.documents[i][allHeaders[j]]);
					var displayName = myobject.documents[i][allHeaders[j]];
					if (displayType.lastIndexOf('url') == displayType.length - 'url'.length) {
						$('#results .query:last').append("<td class=" + "'" + displayType + "' alt='" + displayType + "' ><img src='" + displayName + "'></img><br><p>" + displayName + "</p></td>")
					} else {
						$('#results .query:last').append("<td class=" + "'" + displayType + "' alt='" + displayType + "' ><p>" + displayName + "</p></td>")
					}
				} else {
					$('#results .query:last').append("<td class=" + "'" + displayType + "'> </td>")
				}
			}

			var myskuID = $('.skuid:last').text();
			$('.resultsclass .query:last').append("<td><input type='button' class='debug' value='Debug' onClick='performQuery(\"debug\",\"" + myskuID + "\");\'></td>").hide().fadeIn(200).delay(50);
		} // forloop
		// }
	}
}
/* Utility functions used throughout page */

// Remove starting [ from string data
function removeStartingBracket(myString) {
	if (myString != '' && myString != null) {
		while (myString.charAt(0) == '[') {
			myString = myString.substr(1);
		}
		return myString;
	}
}
// Remove ending ] from string data
function removeEndingBracket(myString) {
	if (myString != '' && myString != null) {
		if (myString.substring(myString.length - 1) == "]") {
			myString = myString.substring(0, myString.length - 1);
		}
	}
	return myString;
}

function appendEndingComma(myString, myResult) {
	if (myString != '' && myString != null) {
		if (myResult.substring(myResult.length - 1) == ",") {
			myResult = myResult + myString;
		} else if (myResult.substring(myResult.length - 1) == "/") {
			myResult = myResult + myString;
		} else {
			myResult = myResult + "," + myString;
		}
	}
	return myResult;
}

function selectFacets() {
	var i=0;
	for(key in selectedFacetNames[0])
	{
		i++;
		var facetValue = selectedFacetNames[0][key];
		for (var i=0; i < facetValue.length; i++){
			$('.'+key+' [name='+facetValue[i]+']').attr('checked', true);	
			//console.log("facet " + key + " has value " + facetValue[0]);
			}
	}		
	/*
	if (selectedFacetNames[0] != '' && selectedFacetNames[0] != null) {
		for (i = 0; i < selectedFacetNames[0].length; i++) {
			var selectedFacet = selectedFacetNames[0][i];
			if (selectedFacet == '' || selectedFacet == null) {
			} else {
				
				$('.'+selectedFacet+' [name=]').attr('checked', true);
			}
		}
	} */
	selectedFacetNames = [{}];	
}

function union_arrays(x, y) {
	var obj = {};
	for ( var i = x.length - 1; i >= 0; --i)
		obj[x[i]] = x[i];
	for ( var i = y.length - 1; i >= 0; --i)
		obj[y[i]] = y[i];
	var res = []
	for ( var k in obj) {
		if (obj.hasOwnProperty(k)) // <-- optional
			res.push(obj[k]);
	}
	return res;
}

function showMore(hideClass) {
	$(hideClass + ' label').addClass("show");
	$(hideClass + ' input').addClass("show");
	$(hideClass + ' a').addClass("show");

}

function calculateFQs() {
	// Append Price to RestUrl
	if (previousPrice != null && previousPrice != '') {
		priceFacetFieldsParameter = "currentprice_facet=" + previousPrice + "";
	} else {
		priceFacetFieldsParameter = "";
	}

	// Append Collection to RestUrl
	if (previousCollection != null && previousCollection != '') {
		collectionFacetFieldsParameter = "collection_facet=" + previousCollection;
	} else {
		collectionFacetFieldsParameter = "";
	}
}
function eliminateDuplicates(arr) {
	if (arr != null && arr != '') {
		var i, len = arr.length, out = [], obj = {};

		for (i = 0; i < len; i++) {
			obj[arr[i]] = 0;
		}
		for (i in obj) {
			out.push(i);
		}

		for ( var k = 0; out.length; ++k) {
			if (out[k].length < 2) {
				var itemtoRemove = out[k].value;

				out = jQuery.grep(allHeaders, function(value) {
					return value != itemtoRemove;
				});

			}
			return out;
		}
	}
}
/*
 * function setHeader(xhr) { xhr.setRequestHeader('XXRequstorID', 'Dotcom'); }
 */
function createAttribute(type) {
	var allPeram = '';
	var checked = $("#" + type + " input:checked");
	var devider = type + '=';
	var deleneate = ',';
	for ( var i = 0; i < checked.length; i++) {
		var peram = devider + $("#" + type + " input:checked")[i].name;
		var peram = peram.replace(/_/g, '%20');
		allPeram = allPeram + peram + ',';
	}
	/*
	 * if (allPeram != '' && allPeram != null){ if
	 * (allPeram.substring(allPeram.length - 1) == ",") { allPeram =
	 * allPeram.substring(0, allPeram.length - 1); } }
	 */
	allPeram = allPeram.replace(type + " ", type);
	allPeram = allPeram.replace("= ", "=");
	return allPeram;
}