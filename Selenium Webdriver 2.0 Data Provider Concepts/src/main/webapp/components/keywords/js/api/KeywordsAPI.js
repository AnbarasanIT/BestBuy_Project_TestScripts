define(["framework/Base", "abstract/API"], function() {

    Clazz.createPackage("com.components.keywords.js.api");

    Clazz.com.components.keywords.js.api.KeywordsAPI = Clazz.extend(Clazz.com.js.abstract.API, {
        /***
         * Ajax request handler for Keywords API
         *
         * @header: constructed header for each call
         * @callbackFunction: callback function to fire once gets the response
         * @errorHandler: error handler function of the response is not success
         */
        getKeywordRedirects : function(header, callbackFunction, errorHandler) {
            this.ajaxRequest(header, callbackFunction, errorHandler);
        },

        /***
         * handles request with callback
         *
         * @header: constructed header for each call
         * @callbackFunction: callback function to fire once gets the response
         * @errorHandler: error handler function if the response is not success
         */
        requestHandler : function(header, callbackFunction, errorHandler) {
            this.ajaxRequest(header, callbackFunction, errorHandler);
        }

    });

    return Clazz.com.components.keywords.js.api.KeywordsAPI;
});