define(["framework/Widget", "useradmin/api/UserAdminAPI", "common/Loading"], function() {

    Clazz.createPackage("com.components.useradmin.js.listener");

    Clazz.com.components.useradmin.js.listener.UseradminListener = Clazz.extend(Clazz.Widget, {
        loadingScreen : null,
        userAdminAPI : null,
        contentContainer : commonVariables.contentPlaceholder,
        paginationPlaceholder :  commonVariables.paginationPlaceholder,
        useradminRequestBody: commonVariables.requestBody,

        /**
         * Initializes the API and loading Screen
         * @config: configuarion for initializing the class
         */
        initialize : function(config) {
            this.loadingScreen = new Clazz.com.js.widget.common.Loading();
            this.userAdminAPI = new Clazz.com.components.useradmin.js.api.UserAdminAPI();
        },

        /**
         * Calls the service via SPI to get the user list
         * @header: Header to be sent for the Service call
         * @callback: Callback method
         */
        getUserAdmin : function(header, callback) {
            var self = this;
            this.loadingScreen.showLoading();
            self.userAdminAPI.requestUserAdmin(header,
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
        },
        
        /**
         * Peforms the operations on the User object
         * @header: Request header to be sent
         * @callback: Callback method to handle the request
         */
        useradminAction : function(header, callback) {
            var self = this;
            $(self.paginationPlaceholder).children().remove();
            
            try {
                this.loadingScreen.showLoading("#Add_useradmin");
                $("#pop_error").hide();
                self.userAdminAPI.requestUserAdmin(header,
                    function(response) {
                        if(response != null && response.status == "SUCCESS" && self.bCheck == true) {
                            $("#Add_useradmin").modal("hide");
                            $("#successSpan").addClass("sucess_msg");
                            $("#successSpan").text(""+ response.message +"");
                            setTimeout(function() {
                                $("#successSpan").empty();
                                $("#successSpan").removeClass("sucess_msg");
                            }, 5000);
                        
                            self.getUserAdmin(self.getRequestHeader(self.useradminActionBody), function(callbackVal) {
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
         * @useradminActionBody: JSON request body object
         */
        getRequestHeader : function (useradminActionBody) {
            var header = {
                contentType: "application/json",
                requestMethod: "GET",
                dataType: "json",
                requestPostBody: JSON.stringify(useradminActionBody),
                webserviceurl: commonVariables.webserviceurl + commonVariables.useradminContext
            }

            return header;
        },
        
		getSkuRequestHeader : function () {
            var header = {
                contentType: "application/json",
                requestMethod: "GET",
                dataType: "json",
                webserviceurl: commonVariables.webserviceurl + commonVariables.useradminContext + "/loadSkus"
            }

            return header;
        },
        /**
         * Creates the request header based on the action and request
         * @keywordRequestBody: Request body to sent as the request
         * @action: Action to be performed, create/edit/approve. reject, delete
         * @id: id of the object
         */
        getActionHeader : function (useradminActionBody, action, id) {
            var self = this, header = {
                contentType: "application/json",
                requestMethod: "POST",
                dataType: "json",
                requestPostBody: '',
                webserviceurl: ''
            }
            
            if(useradminActionBody != "") {
                header.requestPostBody = JSON.stringify(useradminActionBody);
            }
            
            if(action == "create") {
                self.bCheck = true;
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.useradminContext + "/create";
            } else if(action == "update") {
                self.bCheck = true;
                header.requestMethod = "PUT";
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.useradminContext + "/update";
            } else if(action == "delete") {
                self.bCheck = true;
                header.requestMethod = "PUT";
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.useradminContext + "/delete/" + id;
            } else if(action == "editData") {
                self.bCheck = false;
                header.requestMethod = "GET";
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.useradminContext + "/get/" + id;
            } else if(action == "deleteSku") {
                self.bCheck = false;
                header.requestMethod = "POST";
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.useradminContext + "/addSku/" + id;
            }

            return header;
        }
    });

    return Clazz.com.components.useradmin.js.listener.UseradminListener;
});