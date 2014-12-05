define(["framework/Base", "abstract/API"], function() {

    Clazz.createPackage("com.components.synonyms.js.api");

    Clazz.com.components.synonyms.js.api.SynonymsAPI = Clazz.extend(Clazz.com.js.abstract.API, {

        /***
         * gets the synonyms list
         *
         * @header: constructed header for each call
         * @callbackFunction: callback function to fire once gets the response
         * @errorHandler: error handler function if the response is not success
         */
        getSynList : function(header, callbackFunction, errorHandler) {
            this.ajaxRequest(header, callbackFunction, errorHandler);
        },

        /***
         * create the synonym
         *
         * @header: constructed header for each call
         * @callbackFunction: callback function to fire once gets the response
         * @errorHandler: error handler function if the response is not success
         */
    	createSyn : function(header, callbackFunction, errorHandler) {
                this.ajaxRequest(header, callbackFunction, errorHandler);
    	},	

        /***
         * gets the synonyms list when search happens
         *
         * @header: constructed header for each call
         * @callbackFunction: callback function to fire once gets the response
         * @errorHandler: error handler function if the response is not success
         */
    	getSynonymsSearchList : function(header, callbackFunction, errorHandler) {
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

    return Clazz.com.components.synonyms.js.api.SynonymsAPI;
});