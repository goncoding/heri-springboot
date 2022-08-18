doosan.util = (function() {

	function getQueryStringParameter(parameterName) {
		var parameterValue = location.search.split(new RegExp(parameterName + '=(.+?)(&|$)'))[1];
		if (parameterValue === undefined) {
			return null;
		} else {
			return decodeURI(parameterValue);
		}
	}

	return {
		getQueryStringParameter: getQueryStringParameter
	};

}());
