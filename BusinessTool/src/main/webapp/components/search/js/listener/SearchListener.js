define(["framework/Widget", "search/api/SearchAPI", "common/Loading"], function() {

    Clazz.createPackage("com.components.search.js.listener");

    Clazz.com.components.search.js.listener.SearchListener = Clazz.extend(Clazz.Widget, {
        loadingScreen : null,
        searchAPI : null,
        contentContainer : commonVariables.contentPlaceholder,
        paginationPlaceholder :  commonVariables.paginationPlaceholder,
        searchRequestBody: commonVariables.requestBody,

        /**
         * Initializes the API and loading Screen
         * @config: configuarion for initializing the class
         */
        initialize : function(config) {
            this.loadingScreen = new Clazz.com.js.widget.common.Loading();
            this.searchAPI = new Clazz.com.components.search.js.api.SearchAPI();
        },

        /**
         * Peforms the operations on the User object
         * @header: Request header to be sent
         * @callback: Callback method to handle the request
         */
        searchAction : function(header, callback) {
            var self = this;
        },

        /**
         * Constructs the request header structure with the request headers
         * @useradminActionBody: JSON request body object
         */
        getRequestHeader : function (searchActionBody) {
            /*var header = {
                contentType: "application/json",
                requestMethod: "GET",
                dataType: "json",
                requestPostBody: JSON.stringify(searchActionBody),
                webserviceurl: commonVariables.webserviceurl + commonVariables.useradminContext
            }

            return header;*/
        },
        
        /**
         * Creates the request header based on the action and request
         * @keywordRequestBody: Request body to sent as the request
         * @action: Action to be performed, create/edit/approve. reject, delete
         * @id: id of the object
         */
        getActionHeader : function (searchActionBody, action, id) {
            var self = this, header = {
                contentType: "application/json",
                requestMethod: "POST",
                dataType: "json",
                requestPostBody: '',
                webserviceurl: ''
            }
            
            if(searchActionBody != "") {
                header.requestPostBody = JSON.stringify(searchActionBody);
            }
            
            if(action == "create") {
                self.bCheck = true;
                header.webserviceurl = commonVariables.webserviceurl + commonVariables.searchContext + "/create";
            }

            return header;
        }
    });

    return Clazz.com.components.search.js.listener.SearchListener;
});