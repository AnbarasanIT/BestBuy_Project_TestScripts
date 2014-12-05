define(["framework/Widget", "promopages/api/PromoPagesAPI", "common/Loading"], function() {

    Clazz.createPackage("com.components.promopages.js.listener");

    Clazz.com.components.promopages.js.listener.PromoPagesListener = Clazz.extend(Clazz.Widget, {       
        loadingScreen : null,
        promoPagesAPI : null,
        contentContainer : commonVariables.contentPlaceholder,
        paginationPlaceholder :  commonVariables.paginationPlaceholder,
        promoRequestBody: commonVariables.requestBody,

        /**
         * Initializes the API and loading Screen
         * @config: configuarion for initializing the class
         */
        initialize : function(globalConfig) {
            this.promoRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;
            this.loadingScreen = new Clazz.com.js.widget.common.Loading();
            this.promoPagesAPI = new Clazz.com.components.promopages.js.api.PromoPagesAPI();
        },
        
        /**
         * Calls the API for the list of promos and shows it in the page
         */
        promoPage : function(header, callback) {
            var self = this;
            $(self.paginationPlaceholder).children().remove();
            
            try {
                this.loadingScreen.showLoading(self.contentContainer);
                self.promoPagesAPI.requestPromos(header,
                    function(response) {
                        if(response != null) {
                            callback(response);
                        } else {
                            callback({ "status" : "service failure"});
                        }
                        self.loadingScreen.removeLoading(self.contentContainer);
                    },
                    function(textStatus) {
                        self.loadingScreen.removeLoading(self.contentContainer);                    
                    }
                );
            } catch(exception) {
                self.loadingScreen.removeLoading(self.contentContainer);
            }
        },

        /**
         * Constructs the request header structure with the request headers
         * @keywordRequestBody: JSON request body object
         */
        getRequestHeader : function (promoRequestBody) {
            var header = {  
                contentType: "application/json",
                requestMethod: "POST",
                dataType: "json",
                requestPostBody: JSON.stringify(promoRequestBody),
                webserviceurl: commonVariables.webserviceurl + commonVariables.promoContext + "/searchPromo"
            }

            return header;
        },
        
        promoAction : function(header, callback) {
            var self = this;
            $(self.paginationPlaceholder).children().remove();
            
            try {
                $("#pop_error").hide();
                this.loadingScreen.showLoading("#Add_promo");
                self.promoPagesAPI.requestPromos(header,
                    function(response) {
                        if(response != null && response.status == "SUCCESS" && self.bCheck == true) {
                            $("#Add_promo").modal("hide");
                            $("#successSpan").addClass("sucess_msg");
                            $("#successSpan").text(""+ response.message +"");
                            setTimeout(function() {
                                $("#successSpan").empty();
                                $("#successSpan").removeClass("sucess_msg");
                            }, 5000);
                        
                            self.promoPage(self.getRequestHeader(self.promoRequestBody), function(callbackVal) {
                                callback(callbackVal);
                            });
                        } else if(self.bCheck == true) {
                            $("#pop_error").show();
                            $("#pop_error").text(""+ response.message +"");
                           /* $("#successSpan").removeClass("sucess_msg");
                            $("#successSpan").addClass("error_msg");
                            $("#successSpan").text(""+ response.message +"");
                        
                            callback({ "status" : "service failure"});*/
							if (callback !== undefined) {
								callback(response);
							}
                        } else {
                            callback(response);
                        }
                        self.loadingScreen.removeLoading(self.contentContainer);
                    },
                    
                    function(textStatus) {
                        self.loadingScreen.removeLoading(self.contentContainer);                    
                    }
                );
            } catch(exception) {
                self.loadingScreen.removeLoading(self.contentContainer);
            }
        },

        /***
         * checks whether the promo name is exists or not
         *
         * @header: request header for the promo call
         * @callback: once the request is completed the response will be sent to this callback
         */
        checkNameExists : function(primaryTerm, mode, currentValue, callback) {
            var self = this;
            try {
                self.promoPagesAPI.requestHandler(self.getNameExistsHeader(primaryTerm, mode, currentValue),
                    function(response) {
                        if (response != null) {
                            callback(response);
							if (response.status == "SUCCESS") {
								$("#pop_error").show();
								$("#pop_error").text(""+ response.message +"");
								setTimeout(function() {
									$("#pop_error").hide();
								}, 5000);
							}
                        } else {
                            callback({ "status" : "service failure"});
                        }
                    }
                );
            } catch(exception) {
                callback({ "status" : exception});
            }
        },

        /***
         * provides the Name exists header json for both add and edit page
         *
         * @primaryTerm: new name value of the promo
         * @mode: which mode the call from
         * @currentValue: currentvalue of the promolist only applicable on edit page
         */
        getNameExistsHeader : function (promoName, mode, currentValue) {
            if (currentValue === promoName) {
                mode = promoName;
            } else {
                mode = "";
            }

            var requestBody = {};
            requestBody.currentValue = mode;
            requestBody.newValue = promoName;
            requestBody.inputName = "promoName";

            var header = {};
            header.requestPostBody = JSON.stringify(requestBody);
            header.contentType = "application/json";
            header.requestMethod = "POST";
            header.dataType = "json";
            header.webserviceurl = commonVariables.webserviceurl + commonVariables.promoContext + "/exists";

            return header;
        },

        /**
         * Creates the request header based on the action and request
         * @promoRequestBody: Request body to sent as the request
         * @action: Action to be performed, create/edit/approve. reject, delete
         * @id: id of the object
         */
        getActionHeader : function (promoRequestBody, action, id) {
            var self = this, header = {
                contentType: "application/json",
                requestMethod: "POST",
                dataType: "json",
                requestPostBody: '',
                webserviceurl: ''
            }
            
            if(promoRequestBody != "") {
                header.requestPostBody = JSON.stringify(promoRequestBody);
            }
            
            if(action == "create") {
                self.bCheck = true;
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.promoContext + "/create";
            } else if(action == "update") {
                self.bCheck = true;
                header.requestMethod = "PUT";
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.promoContext + "/update";
            } else if(action == "approve") {
                self.bCheck = true;
                header.requestMethod = "PUT";
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.promoContext + "/approve/" + id;
            } else if(action == "reject") {
                self.bCheck = true;
                header.requestMethod = "PUT";
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.promoContext + "/reject/" + id;
            }else if(action == "editData") {
                self.bCheck = false;
                header.requestMethod = "GET";
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.promoContext + "/get/" + id;
            }else if(action == "delete") {
                self.bCheck = true;
                header.requestMethod = "PUT";
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.promoContext + "/delete/"+ id;
            }

            return header;
        }

    }); 

    return Clazz.com.components.promopages.js.listener.PromoPagesListener;
});