define(["framework/Widget", "keywords/api/KeywordsAPI", "common/Loading" ], function() {

    Clazz.createPackage("com.components.keywords.js.listener");

    Clazz.com.components.keywords.js.listener.KeywordListener = Clazz.extend(Clazz.Widget, {
        loadingScreen : null,
        keywordsAPI : null,
        keywordRequestBody: commonVariables.requestBody,
        
        /**
         * Initializes the API and loading Screen
         * @config: configuarion for initializing the class
         */
        initialize : function(globalConfig) {
            this.keywordRequestBody.rowsPerPage = commonVariables.requestBody.rowsPerPage;
            this.loadingScreen = new Clazz.com.js.widget.common.Loading();
            this.keywordsAPI = new Clazz.com.components.keywords.js.api.KeywordsAPI();
        },  
        
        /**
         * Constructs the request header structure with the request headers
         * @keywordRequestBody: JSON request body object
         */
        getRequestHeader : function (keywordRequestBody) {
            var header = {
                contentType: "application/json",
                requestMethod: "POST",
                dataType: "json",
                requestPostBody: JSON.stringify(keywordRequestBody),
                webserviceurl: commonVariables.webserviceurl + commonVariables.keywordsContext + "/loadRedirects"
            }

            return header;
        },

        /**
         * Peforms the approve/reject/delete options for Keywords object
         * @header: Request header to be sent
         * @callback: Callback method to handle the request
         */
        keywordAction : function(header, callback) {
            var self = this;
            $(self.paginationPlaceholder).children().remove();
            
            try {
                $("#pop_error").hide();
                this.loadingScreen.showLoading("#Add_keywords");
                self.keywordsAPI.getKeywordRedirects(header,
                    function(response) {
                        if(response != null && response.status == "SUCCESS" && self.bCheck == true) {
                            $("#Add_keywords").modal("hide");
                            $("#successSpan").addClass("sucess_msg");
                            $("#successSpan").text(""+ response.message +"");
                            setTimeout(function() {
                                $("#successSpan").empty();
                                $("#successSpan").removeClass("sucess_msg");
                            }, 5000);
                        
                            self.getKeywordList(self.getRequestHeader(self.keywordRequestBody), function(callbackVal) {
                                callback(callbackVal);
                            });
                        } else if(self.bCheck == true) {
                            $("#pop_error").show();
                            $("#pop_error").text(""+ response.message +"");
                            /*$("#successSpan").removeClass("sucess_msg");
                            $("#successSpan").addClass("error_msg");
                            $("#successSpan").text(""+ response.message +"");
                            callback({ "status" : "service failure"});*/
							if (callback !== undefined) {
								callback(response);
							}
                        } else {
                            callback(response);
                        }
                        self.loadingScreen.removeLoading();
                    },
                    
                    function(textStatus) {
                        self.loadingScreen.removeLoading();                 
                    }
                );
            } catch(exception) {
                self.loadingScreen.removeLoading();
            }
        },
        
        /**
         * Creates the request header based on the action and request
         * @keywordRequestBody: Request body to sent as the request
         * @action: Action to be performed, create/edit/approve. reject, delete
         * @id: id of the object
         */
        getActionHeader : function (keywordRequestBody, action, id) {
            var self = this;
            var header = {
                contentType: "application/json",
                requestMethod: "POST",
                dataType: "json",
                requestPostBody: '',
                webserviceurl: ''
            };
            
            if(keywordRequestBody != "") {
                header.requestPostBody = JSON.stringify(keywordRequestBody);
            }
            
            if(action == "create") {
                self.bCheck = true;
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.keywordsContext + "/create";
            } else if(action == "update") {
                self.bCheck = true;
                header.requestMethod = "PUT";
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.keywordsContext + "/update";
            } else if(action == "approve") {
                self.bCheck = true;
                header.requestMethod = "PUT";
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.keywordsContext + "/approve/" + id;
            } else if(action == "reject") {
                self.bCheck = true;
                header.requestMethod = "PUT";
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.keywordsContext + "/reject/" + id;
            } else if(action == "delete") {
                self.bCheck = true;
                header.requestMethod = "PUT";
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.keywordsContext + "/delete/" + id;
            } else if(action == "editData") {
                self.bCheck = false;
                header.requestMethod = "GET";
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.keywordsContext + "/loadRedirect/" + id;
            }

            return header;
        },      
        
        /**
         * Creates the request header based on the action and request
         * @header: Header to be sent for the Service call
         * @callback: Callback method
         */
        getKeywordList : function(header, callback) {
            var self = this;
            $(self.paginationPlaceholder).children().remove();
            
            try {
                this.loadingScreen.showLoading();
                self.keywordsAPI.getKeywordRedirects(header,
                    function(response) {
                        if(response != null) {
                            callback(response);                         
                        } else {
                            callback({ "status" : "service failure"});
                        }
                        self.loadingScreen.removeLoading();
                    },
                    function(textStatus) {
                        self.loadingScreen.removeLoading();                 
                    }
                );
            } catch(exception) {
                self.loadingScreen.removeLoading();
            }
        },

        /***
         * checks whether the keyword name is exists or not
         *
         * @header: request header for the synonyms call
         * @callback: once the request is completed the response will be sent to this callback
         */
        checkNameExists : function(redirectTerm, mode, currentValue, callback) {
            var self = this;
            try {
                self.keywordsAPI.requestHandler(self.getNameExistsHeader(redirectTerm, mode, currentValue),
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
         * @primaryTerm: new name value of the synonym
         * @mode: which mode the call from
         * @currentValue: currentvalue of the synonym only applicable on edit page
         */
        getNameExistsHeader : function (redirectTerm, mode, currentValue) {
            if (currentValue === redirectTerm) {
                currentValue = redirectTerm;
            } else {
                currentValue = "";
            }

            var requestBody = {};
            requestBody.currentValue = currentValue;
            requestBody.newValue = redirectTerm;
            requestBody.inputName = "redirectTerm";

            var header = {};
            header.requestPostBody = JSON.stringify(requestBody);
            header.contentType = "application/json";
            header.requestMethod = "POST";
            header.dataType = "json";
            header.webserviceurl = commonVariables.webserviceurl + commonVariables.keywordsContext + "/exists";

            return header;
        },
    });

    return Clazz.com.components.keywords.js.listener.KeywordListener;
});