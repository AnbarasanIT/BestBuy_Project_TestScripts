define(["framework/Widget", "framework/TemplateProvider"], function() {

	Clazz.WidgetWithTemplate = Clazz.extend(
		Clazz.Widget, {
			// the url of the template
			templateUrl: null,
			localConfig: null,
			
			// default the provider to handlebar
			templateProvider: TemplateProvider != undefined ? TemplateProvider.HANDLE_BAR : '',
			
			// data for data binding process. If you don't override preRender(), this is the default
			// data that it will use
			data : {},
			
			// the last result of the element after the element has been bounded and rendered
			element: null,
			
			// default container if render place is not specified
			// for example: <app:account-list>
			defaultContainer : null,
			
			showingDatePicker : false,
			
			doMore: function(element) {}, 
			
			/***
			 * Called after the renderTemplate() and bindUI() completes. 
			 * Override and add any preRender functionality here
			 *
			 * @element: Element as the result of the template + data binding
			 */
			postRender : function(element) {
			
			if (commonVariables.userInfo.role.name == "ROLE_ADMIN"){
			$('.userpermissions').addclass('show'); 
			}
			},
			 
			/****
			 * Called before the render() function, override and add any preRender functionality
			 * here
			 * 
			 * @whereToRender The node where we are going to render the UI
			 * @renderFunction The renderTemplate function by default, use to render the UI and call bindUI afterwards
			 */
			preRender: function(whereToRender, renderFunction) {
				// default implementation just call renderFunction
				renderFunction(this.data, whereToRender);
			},
			
			/***
			 * A call to render the UI fragment implemented in renderUI method
			 * @param whereToRender A placeholder to hold the fragment, this would typically
			 * be a div tag or other valid HTML element. 
			 * 
			 * @whereToRender The node where we are going to render the UI
			 */
			render : function(whereToRender) {
				if(whereToRender == null) {
					whereToRender = $(this.defaultContainer);
				}
				
				this.preRender(whereToRender, $.proxy(this.renderTemplate, this));
			},
			
			/***
			 * Render using the template url specified with the template engine
			 * 
			 * @data The data that is used for binding with the template
			 * @whereToRender The node where we are going to render the UI
			 */
			renderTemplate: function(data, whereToRender) {
				if(this.templateUrl != null) {
					var self = this;
										
					var templateProvider = new Clazz.TemplateProvider({ templateEngine : this.templateProvider});
					
					templateProvider.merge(this.templateUrl, data, function(element) {
						$(whereToRender).html(element);
						self.bindUI();
						self.postRender(element);
						self.element = element;
						self.doMore(element);
					});
				}
			},
		 
			/***
			 *  Method to get the HTML String representation, can be used to include
			 *  the part of the Widget to other template
			 *  
			 *  @return The HTML string from the result of data binding with the template
			 */
			getHtmlString: function() {
				if(this.element != null) {
					return $(this.element)[0].outerHTML;
				}
			},
			
			/**
			 * Method to initiate any event handling
			 * called after the renderTemplate() completes
			 */
			handleEvent : function(element){
				this.bindUI();
				this.postRender(element);
				this.element = element;
			},
			
			renderTheme : function(module, localtheme) {
				if(Clazz.config.theme !== undefined && Clazz.config.theme !== null && Clazz.config.theme !== '' &&
				  module !== undefined && module !== null && module !== '') {
					$('head').append('<link rel="stylesheet" href="../components/'+ module +'/themes/'+ Clazz.config.theme +'/css/' + Clazz.config.theme + '.css">');
				} else {
					if(localtheme !== undefined && localtheme !== null && localtheme !== '' &&
					module !== undefined && module !== null && module !== '') {
						$('head').append('<link rel="stylesheet" href="../components/'+ module +'/themes/'+ localtheme +'/css/' + localtheme + '.css">');
					}
				}
			},

			validateStartEndDate : function(startDateTime, endDateTime) {
				var errorJson = {};
				var startDate = startDateTime.substring(0, 10);
				var startDateSplits = startDate.split('-');
				var startMonth = Number(startDateSplits[0]);
				var startDay = Number(startDateSplits[1]);
				var startYear = Number(startDateSplits[2]);

				var endDate = endDateTime.substring(0, 10);
				var endDateSplits = endDate.split('-');
				var endMonth = Number(endDateSplits[0]);
				var endDay = Number(endDateSplits[1]);
				var endYear = Number(endDateSplits[2]);

				var now = new Date();
				var nowDay = now.getDate();
				var nowMonth = now.getMonth() + 1;
				var nowYear = now.getFullYear();
				errorJson.hasError = false;
				if (startYear < nowYear) {
					errorJson.errorIn = "startDate";
					errorJson.errorMsg = "Select valid start date";
					errorJson.hasError = true;
				} else if (startYear == nowYear && startMonth < nowMonth) {
					errorJson.errorIn = "startDate";
					errorJson.errorMsg = "Select valid start date";
					errorJson.hasError = true;
				} else if (startYear == nowYear && startMonth == nowMonth && startDay < nowDay) {
					errorJson.errorIn = "startDate";
					errorJson.errorMsg = "Select valid start date";
					errorJson.hasError = true;
				} else if (endYear == startYear && endMonth == startMonth && endDay == startDay) {
					errorJson.errorIn = "endDate";
					errorJson.errorMsg = "Select valid end date";
					errorJson.hasError = true;
				} else if (endYear < startYear && endMonth < startMonth && endDay < startDay) {
					errorJson.errorIn = "endDate";
					errorJson.errorMsg = "Start date greater than the end date";
					errorJson.hasError = true;
				} else if (endYear == startYear && endMonth < startMonth) {
					errorJson.errorIn = "endDate";
					errorJson.errorMsg = "Start date greater than the end date";
					errorJson.hasError = true;
				} else if (endYear == startYear && endMonth == startMonth && endDay < startDay) {
					errorJson.errorIn = "endDate";
					errorJson.errorMsg = "Start date greater than the end date";
					errorJson.hasError = true;
				} 
				return errorJson;
			},
			
			headerStyle : function() {
				$('.tab_table th:first-child').css('width','95px');
				$('.tab_table td:first-child').css('width','95px');
			},
			
			disableActionStyle : function() {
				$(".block_sym").die('mouseover');
				$(".block_sym").live('mouseover', function() {
					$(".block_sym").css('cursor', 'auto');
				});
			},

			escapePopup : function(id) {
				var self = this;
				$(id).on( 'keydown', function ( e ) {
					if ( e.keyCode === 27 && !self.showingDatePicker) { // ESC
						$(id).modal('hide');
					}
				});
			},
			
			/**
	         * Set maximum modal window size
	         */
			sizeModal : function (tagCloudRequestBody) {
				var windowHeight = (window.innerHeight - 154);
				//console.log('innerHeight: '+window.innerHeight);
				//console.log('modal height: '+windowHeight);
			$('.modal-body').css({ "max-height": windowHeight + 'px' });
			$('.modal.fade.in').css({ "top": '10px' });
			},

			renderCount : function () {
							var countChild = ('' + ' .number');
							var selectChild = $(countChild);
							var countParent = '#' + '' + 'terms_eachmain';
							$.each((countParent), function() {
							var counter = 0;
								$.each((selectChild), function() {
									$(this).html('#' + (counter + 1) + '');
									// console.log(this);
									counter++;
								});
							});
						},
						
			countTerms : function (){
				var terms = $('.terms_main');
				//console.log(terms);
				for (var i = 0; i < terms.length; i++) {
				  // Do something with element i.
				var theid = "tagCloud"+i;
				$(terms[i]).attr("id", theid);
				//console.log(terms[i]);
				}
			},

			/**
	         * Tag Cloud Renderer
	         * tagCloudRequestBody: JSON request body object
	         */
			submitTerms : function (tagCloudRequestBody) {
				//console.log('Start submit Terms');
					var term = tagCloudRequestBody.term;
					
								//Set paramaters for terms
								var button= "", sort="", numbered='';
								//if removable not set, default to true.
								tagCloudRequestBody.removable = (typeof tagCloudRequestBody.removable === 'undefined') ? "true" : tagCloudRequestBody.removable;
								if (tagCloudRequestBody.removable == 'true')
									{button = '<img class="closebtn" name="closebtn" src="../themes/default/images/bestbuy/close.png">';}
								//if sortable not set, default to true.
								tagCloudRequestBody.sortable = (typeof tagCloudRequestBody.sortable === 'undefined') ? "false" : tagCloudRequestBody.sortable;
								if (tagCloudRequestBody.sortable == 'true')
									{sort = 'sort';}
								tagCloudRequestBody.numbered = (typeof tagCloudRequestBody.numbered === 'undefined') ? "false" : tagCloudRequestBody.numbered;
								if (tagCloudRequestBody.numbered == 'true')
									{numbered = '<span class="number" style=" color: grey; vertical-align: super; font-size: 10px;">'+numbered+'</span>';}
 
					if (term != undefined && term != null && tagCloudRequestBody.termsList != null) {
						//Not Null check
						var tepmVal = [];
						tepmVal.push(term);
						
						if(term.indexOf(",") >= 0){
							tepmVal = term.split(",");
						}
							
						$.each(tepmVal, function(index, value) {
							$(".addterms").val('').attr('placeholder','Type Term(s)');
							$(".addterms").focus();
							
								//Check if term is a duplicate
							value=value.trim();	
								if (value != undefined && value != null && value != '' && $.inArray(value,tagCloudRequestBody.termsList) <= -1) {
									//if term is not a duplicate, add it.
									tagCloudRequestBody.enteredTerms.append('<div class="'+sort+' terms_eachtab">'+numbered+value+button+'</div>');
									tagCloudRequestBody.termsList.push(value);
									//console.log("Values:");
									//console.log(tagCloudRequestBody.termsList);

								}
							});
						} else {
						//if term is a duplicate or null, do nothing.
						}
						//bind close button to close action
						$("img[name='closebtn']").die();
						$("img[name='closebtn']").live("click", function(e){
							var termValToReplace = $(this).parent().text().replace($(this).parent().find('span').text(),"");
							//console.log("Value of removed term: "+termValToReplace);
							var removedTerm = tagCloudRequestBody.termsList.indexOf(termValToReplace);
							tagCloudRequestBody.termsList.splice(removedTerm, 1);
							$(this).parent().remove();

							//render item count on countable terms
							var countParent = '#' + '' + 'terms_eachmain';
							var countChild = (' .number');
							var selectChild = $(countChild);
							$.each((countParent), function() {
							var counter = 0;
								$.each((selectChild), function() {
									$(this).html('#' + (counter + 1) + '');
									// console.log(this);
									counter++;
								});
				});
						});   
					tagCloudRequestBody.userInput.removeClass("error_text");
					tagCloudRequestBody.error.html('');

		var countParent = '#' + '' + 'terms_eachmain';
		var countChild = ('' + ' .number');
		var selectChild = $(countChild);

		$.each((countParent), function() {
		var counter = 0;
			$.each((selectChild), function() {
					$(this).html('#' + (counter + 1) + '');
					// console.log(this);
					counter++;
					});
				});
			
			},

		}
	);

	return Clazz.WidgetWithTemplate;
});