(function() {

	$(document).ready(function() {
		var parameterYear = doosan.util.getQueryStringParameter('year');
		$('#search-year').val(parameterYear);
		$('#search-year').selectric();
		$('#search-ir-announce').click(searchIrAnnounce);
		$('#search-query').on('keydown', eventSearchAreaSearchFieldEnterKey);
	});

	function searchIrAnnounce() {
		var basePath = location.pathname;
		var parameterList = [];

		var searchYear = $('#search-year').val();
		var searchQuery = $('#search-query').val();

		if(searchYear !== '') {
			parameterList.push('year=' + searchYear);
		}
		if(searchQuery !== '') {
			parameterList.push('query=' + searchQuery);
		}

		if(parameterList.length > 0) {
			location.href = basePath + '?' + parameterList.join('&');
		} else {
			location.href = basePath;
		}
	}

	function eventSearchAreaSearchFieldEnterKey(event) {
		if(event.keyCode === 13) {
			event.preventDefault();
			searchIrAnnounce();
		}
	}

})();
