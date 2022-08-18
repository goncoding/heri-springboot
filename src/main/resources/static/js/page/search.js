var Search = (function () {

	$(document).ready(init);

	var config = {
		allTabNewsCount: 3,
		allTabWebCount: 3,
		newsTabPageSize: 10,
		webTabPageSize: 10,
		relatedKeywordCount: 5,
		paginationSize: 10
	};

	var language = '';
	var query = '';
	var refineQuery = '';
	var searchCompany = '';
	var currentPage = 0;
	var currentType = 'all';

	var resultNewsCount = 0;
	var resultWebCount = 0;

	var restUrl = {
		search: '/rest/search',
		searchCount: '/rest/search/count',
		searchSubsidiary: '/rest/search/subsidiary',
		relatedKeywords: '/rest/search/related-keywords',
		subsidiaryThumbail: '/rest/search/subsidiary-thumb'
	};

	var elementId = {
		searchQuery: 'searchQuery',
		searchButton: 'btn_search',
		refineSearchCheckbox: 'chkSaveSearch',
		subsidiaryList: 'subsidiaryList',
		allListDiv: 'allListDiv',
		allNewsListDiv: 'result_news',
		allNewsList: 'allNewsList',
		allWebListDiv: 'result_web',
		allWebList: 'allWebList',
		newsOnlyListDiv: 'newsOnlyListDiv',
		newsOnlyList: 'newsOnlyList',
		webOnlyListDiv: 'webOnlyListDiv',
		webOnlyList: 'webOnlyList',
		detailResultList: 'detail_list_inner',
		resultDiv: 'result_div',
		noAllResult: 'no_all_result',
		showAllButton: 'showAllButton',
		showNewsButton: 'showNewsButton',
		showWebButton: 'showWebButton',
		companySelectBox: 'companySelectBox',
		pagination: 'pagination',
		subsidiaryArea: 'subsidiaryArea',
		swiperContainer: 'swiper-container',
		subsidiaryPaging: 'subsidiaryPaging',
		subsidiaryList: 'subsidiaryList'
	};

	var jqueryObject = {
		searchQuery: null,
		searchButton: null,
		refineSearchCheckbox: null,
		subsidiaryList: null,
		allNewsListDiv: null,
		allNewsList: null,
		allWebListDiv: null,
		allWebList: null,
		newsOnlyListDiv: null,
		newsOnlyList: null,
		webOnlyListDiv: null,
		webOnlyList: null,
		resultList: null,
		resultDiv: null,
		noAllResult: null,
		showAllButton: null,
		showNewsButton: null,
		showWebButton: null,
		companySelectBox: null,
		pagination: null,
		subsidiaryArea: null,
		swiperContainer: null,
		subsidiaryPaging: null,
		subsidiaryList: null
	};

	var subsidiarySlider;

	function init() {

		initParameters();
		initJqueryObject();
		initQuery();
		initAllResults();
		initSelectBox();
		initEvents();

		function initParameters() {
			language = getLanguageFromUrl();

			function getLanguageFromUrl() {
				var url = window.location.href;
				url = url.replace(/([a-zA-Z0-9]+):\/\//g, '');

				var results = url.split('/');
				if(results[1]) {
					if(checkAvailableLanguage(results[1])) {
						var lang = results[1];
					}
				}

				return lang;

				function checkAvailableLanguage(lang) {
					switch(lang) {
						case 'kr':
						case 'en':
							return true;
						default:
							return false;
					}
				}
			}
		}

		function initJqueryObject() {
			for(var key in elementId) {
				jqueryObject[key] = $('#' + elementId[key]);
			}
		}

		function initQuery() {
			query = getParameterByName('query');
			applyCurrentQueryOnHtml();
		}

		function initAllResults() {
			getAndPrintFirstPage();
		}

		function initSelectBox() {
//			CommonJs.selectBox('sort');
			jqueryObject.companySelectBox.find('a').click(companyClickEventHandler);

			function companyClickEventHandler() {
				Search.setSearchCompany($(this).data('code'));
				getAndPrintFirstPage();
			}
		}

		function initEvents() {
			jqueryObject.showAllButton.click(showAllTab);
			jqueryObject.showNewsButton.click(showNewsTab);
			jqueryObject.showWebButton.click(showWebTab);

			jqueryObject.searchQuery.on('keydown', function(event) {
				if(event.keyCode === 13) {
					event.preventDefault();
					changeSearchQueryAndPrintFirstPage();
				}
			});
			jqueryObject.searchButton.click(function() {
				changeSearchQueryAndPrintFirstPage();
			});

			attachShowAllResultLinkEvent();

			function attachShowAllResultLinkEvent() {
				$('.btn_loadMore_news a').click(showNewsTab);
				$('.btn_loadMore_web a').click(showWebTab);
			}
		}
	}

	function applyCurrentQueryOnHtml() {
		var text = query;
		if(jqueryObject.refineSearchCheckbox.is(':checked')) {
			text = query + ' & ' + refineQuery;
			jqueryObject.searchQuery.val(refineQuery);
		} else {
			jqueryObject.searchQuery.val(text);
		}

		$('span.searchQuery').text(text);
	}

	function getAndPrintDetailedPages(page) {
		if(currentType === 'all' || currentType === 'news') {
			callRestResultSearch('news', searchCompany, currentPage, config.newsTabPageSize, getShowRestResultFunction(jqueryObject.newsOnlyList, 'news'));
		}
		if(currentType === 'all' || currentType === 'web') {
			callRestResultSearch('web', searchCompany, currentPage, config.webTabPageSize, getShowRestResultFunction(jqueryObject.webOnlyList, 'web'));
		}
	}

	function getAndPrintFirstPage() {
		currentPage = 0;

		if(query != null && query.length > 0) {
			getAndShowSubsidary();
			async.parallel([
				function (callback) {
					callRestResultCountSearch('news', searchCompany, function (newsCount) {
						Search.setResultNewsCount(0);
						var parsedCount = parseInt(newsCount);
						if(parsedCount > 0) {
							Search.setResultNewsCount(parsedCount);
							$('input.newsResultCount').val(parsedCount);
							$('span.newsResultCount').text(parsedCount);
						} else {
							$('input.newsResultCount').val(0);
							$('span.newsResultCount').text('0');
						}
						callback(null, Search.getResultNewsCount());
					});
				},
				function (callback) {
					callRestResultCountSearch('web', searchCompany, function (webCount) {
						Search.setResultWebCount(0);
						var parsedCount = parseInt(webCount);
						if(parsedCount > 0) {
							Search.setResultWebCount(parsedCount);
							$('input.webResultCount').val(parsedCount);
							$('span.webResultCount').text(parsedCount);
						} else {
							$('input.webResultCount').val(0);
							$('span.webResultCount').text('0');
						}
						callback(null, Search.getResultWebCount());
					});
				}
			], function(error, result) {
				if((result instanceof Array) && result.length >= 2) {
					applyCurrentQueryOnHtml();

					var allResultCount = result[0] + result[1];
					$('input.allResultCount').val(allResultCount);
					$('span.allResultCount').text(allResultCount);

					if(allResultCount > 0) {
						jqueryObject.resultDiv.show();
						jqueryObject.noAllResult.hide();

						showAllTab();

						getAndShowRelatedKeywords();

						callRestResultSearch('news', searchCompany, 0, config.allTabNewsCount, getShowRestResultFunction(jqueryObject.allNewsList, 'news', jqueryObject.allNewsListDiv));
						callRestResultSearch('web', searchCompany, 0, config.allTabWebCount, getShowRestResultFunction(jqueryObject.allWebList, 'web', jqueryObject.allWebListDiv));

						getAndPrintDetailedPages(0, searchCompany);
						printPagination();

					} else {
						jqueryObject.resultDiv.hide();
						jqueryObject.noAllResult.show();
					}
				}
			});
		} else {
			jqueryObject.searchQuery.focus();
		}
	}

	function getAndShowRelatedKeywords() {
		callRestSearch(restUrl.relatedKeywords, null, searchCompany, null, null, function(data) {
			if(!(data instanceof Array)) {
				return;
			}

			if(data.length <= 0) {
				$('.chain_nochain').addClass('on');
				$('.chain').html('');
				return;
			}

			$('.chain_nochain').removeClass('on');

			var innerHtml = '';
			for (var i=0; i<config.relatedKeywordCount; i++) {
				if(data[i] == null) {
					break;
				}
				innerHtml = innerHtml + '<a href="javascript:;" class="related-search">' + data[i] + '</a>';
			}
			$('.chain').html(innerHtml);
			$('.chain .related-search').click(relatedKeywordClickEventHandler);

			function relatedKeywordClickEventHandler() {
				jqueryObject.refineSearchCheckbox.prop('checked', false);
				changeSearchQueryAndPrintFirstPage($(this).text());
			}
		});
	}

	function getAndShowSubsidary() {
		if(subsidiarySlider) {
			subsidiarySlider.destroy();
		}
		callRestSubsidiarySearch(addHtmlToSubsidaryArea);

		function addHtmlToSubsidaryArea(companyList) {
			jqueryObject.subsidiaryArea.addClass('off').hide();

			if(!(companyList instanceof Array)) {
				companyList = [];
			}

			var innerHtml = '';
			for(var i=0; i<companyList.length; i++) {
				innerHtml = innerHtml 
					+ '<div class="slide swiper-slide">'
					+	'<div id="company-image-' + companyList[i].id + '" class="slide_img" style="background: transparent center center / cover no-repeat;">'
					+ 		'<img src="/images/search/top_empty.png" alt="">'
					+	'</div>'
					+	'<div class="slide_text_wrap">'
					+		'<span class="slide_title">' + companyList[i].companyName + '</span>'
					+		'<p class="slide_desc">' + companyList[i].description + '</p>';

				if(companyList[i].siteUrl != null) {
					innerHtml = innerHtml 
						+		 '<div class="gu_btn_sm_box gu_btn_arrow">'
						+			'<a href="' + companyList[i].siteUrl + '" target="_blank" class="kr_sd_500"><span>' + springMessages.goToSiteButtonText + '</span></a>'
						+		'</div>';

				}

				innerHtml = innerHtml
					+	'</div>'
					+ '</div>';
			}

			jqueryObject.subsidiaryList.html(innerHtml);

			innerHtml = '';
			for(var i=0; i<companyList.length; i++) {
				innerHtml = innerHtml + '<li></li>';
			}
			jqueryObject.subsidiaryPaging.html(innerHtml);

			for(var i=0; i<companyList.length; i++) {
				callRestSubsidiaryThumbnail(companyList[i].id, applyCompanyThumbnail);
			}

			initSlider();
			if(companyList.length > 0) {
				jqueryObject.subsidiaryArea.removeClass('off').show();
			}

			function applyCompanyThumbnail(doosanUpload) {
				if(doosanUpload == null) {
					return;
				}

				var imageId = doosanUpload.parentId;
				$('#company-image-' + imageId).css('background-image', 'url(/file/view/' + doosanUpload.uuid + ')');
			}
		}

		function initSlider() {
			subsidiarySlider = new Swiper('#' + elementId.swiperContainer, {
				pagination: {
					el: '.slide_paging',
					renderBullet: function (index, className) {
						return '<li class="' + className + '"> '+ index +'</li>';
					}
				},
				navigation: {
					nextEl: '.swiper-button-next',
					prevEl: '.swiper-button-prev'
				}
			});

			if($('.swiper-container .swiper-slide').length < 2) {
				$('.subsidiary_inner .subsidiary_inner_paging, .subsidiary_inner .btn_arrow').hide();
			} else {
				$('.subsidiary_inner .subsidiary_inner_paging, .subsidiary_inner .btn_arrow').show();
			}
		}
	}

	function callRestResultSearch(type, company, page, pageSize, successCallback) {
		callRestSearch(restUrl.search, type, company, page, pageSize, successCallback);
	}

	function callRestResultCountSearch(type, company, successCallback) {
		callRestSearch(restUrl.searchCount, type, company, null, null, successCallback);
	}

	function callRestSubsidiarySearch(successCallback) {
		callRestSearch(restUrl.searchSubsidiary, null, null, null, null, successCallback);
	}

	function callRestSubsidiaryThumbnail(companyId, successCallback) {
		if(companyId == null) {
			console.warn('No company id was passed!');
			return;
		}

		$.ajax(restUrl.subsidiaryThumbail + '/' + companyId).success(successCallback);
	}

	function callRestSearch(url, type, company, page, pageSize, successCallback, failCallback) {
		var ajaxParam = {
			query: query,
			language: language
		};

		if(type != null) {
			ajaxParam.type = type;
		}
		if(url !== restUrl.relatedKeywords && company != null && company.length > 0) {
			ajaxParam.pCompany = company;
		}
		if(page != null) {
			ajaxParam.page = page;
		}
		if(pageSize != null) {
			ajaxParam.pageSize = pageSize;
		}

		if(jqueryObject.refineSearchCheckbox.is(':checked')) {
			if(url === restUrl.search || url === restUrl.searchCount) {
				ajaxParam.query = query + ' ' + refineQuery;
			}
		}
		
		$.ajax({
			url: url,
			data: ajaxParam
		}).success(successCallback).fail(failCallback);
	}

	function getShowRestResultFunction(jqueryTarget, type, displayingTarget, noResultDiv) {

		return showRestResultOnTarget;

		function showRestResultOnTarget(result) {
			if(!(result instanceof Array) || jqueryTarget == null) {
				return;
			}

			if(displayingTarget != null) {
				displayingTarget.removeClass('on').addClass('off');
				displayingTarget.hide();
			}

			if(noResultDiv != null && result.length === 0) {
				noResultDiv.show();
				return;
			}

			var innerHtml = '';
			for(var i=0; i<result.length; i++) {
				innerHtml = innerHtml
					+ '<li>'
					+	'<a href="' + getUrlFromResultItem(result[i]) + '" class="kr_sd_500" target="_blank" title="Open new window">' + getTitleFromResultItem(result[i]) + '</a>'
					+	'<p>' + getContentFromResultItem(result[i]) + '</p>';
				if(type === 'news') {
					var pressDate = getPressDateFromResultItem(result[i]);

					if(pressDate != null) {
						innerHtml = innerHtml + '<span class="date">' + dateToDotNotationString(pressDate) + '</span>'
					}
				}
				
				innerHtml= innerHtml
					+	'<span>'+ getCompanyNameFromResultItem(result[i]) +'</span>'
					+ '</li>';
			}
			jqueryTarget.html(innerHtml);

			if(result.length > 0 && displayingTarget != null) {
				displayingTarget.addClass('on').removeClass('off');
				displayingTarget.show();
			}
			printPagination();
		}
	}

	function changeSearchQueryAndPrintFirstPage(newQuery) {
		if(newQuery == null) {
			newQuery = jqueryObject.searchQuery.val();
		}

		if(newQuery.length <= 0) {
			jqueryObject.searchQuery.val(query);
		} else {
			if(jqueryObject.refineSearchCheckbox.is(':checked')) {
				refineQuery = newQuery;
			} else {
				query = newQuery;
			}
			jqueryObject.searchQuery.val(newQuery);
			
			getAndPrintFirstPage();
		}
	}

	function getCompanyNameFromResultItem(resultItem) {
		if(resultItem == null) {
			return '';
		} else if(resultItem.pcompany != null) {
			return resultItem.pcompany;
		} else if(resultItem.companyname != null) {
			return resultItem.companyname;
		} else {
			return '';
		}
	}

	function getUrlFromResultItem(resultItem) {
		if(resultItem == null) {
			return "#";
		} else if(resultItem.sitepathurl != null) {
			return resultItem.sitepathurl;
		} else if(resultItem.urls instanceof Array && resultItem.urls[0] != null) {
			return resultItem.urls[0];
		} else if(resultItem.url != null) {
			return resultItem.url;
		} else {
			return "#";
		}
	}

	function getPressDateFromResultItem(resultItem) {
		var pressDate = resultItem.pressdate
		if(pressDate == null) {
			pressDate = resultItem.docdatetime;
		}
		return new Date(pressDate);
	}

	function getContentFromResultItem(resultItem) {
		if(resultItem == null) {
			return '';
		} else if(resultItem.body != null) {
			return resultItem.body;
		} else {
			return getUrlFromResultItem(resultItem);
		}
	}

	function getTitleFromResultItem(resultItem) {
		if(resultItem == null) {
			return '';
		} else if(resultItem.title != null) {
			return resultItem.title;
		} else {
			return getUrlFromResultItem(resultItem);
		}
	}

	function getParameterByName(name) {
		var url = window.location.href;
		name = name.replace(/[\[\]]/g, '\\$&');

		var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)');
		var results = regex.exec(url);

		if (!results) {
			return null;
		}
		if (!results[2]) {
			return '';
		}

		return decodeURIComponent(results[2].replace(/\+/g, ' '));
	}

	function dateToDotNotationString(date) {
		if(date == null || isNaN(date.getTime())) {
			return '';
		}

		var year = date.getFullYear();
		var month = date.getMonth() + 1;
		var dayOfMonth = date.getDate();

		var monthString = (month < 10) ? '0'+month : month;
		var dayOfMonthString = (dayOfMonth < 10) ? '0'+dayOfMonth : dayOfMonth;

		return `${year}.${monthString}.${dayOfMonthString}`;
	}

	function showAllTab() {
		currentType = 'all';
		currentPage = 0;

		$('.tabs a').removeClass('on');
		jqueryObject.showAllButton.addClass('on');

		jqueryObject.newsOnlyListDiv.removeClass('on').addClass('off').hide();
		jqueryObject.webOnlyListDiv.removeClass('on').addClass('off').hide();
		jqueryObject.allListDiv.removeClass('off').addClass('on').show();
		printPagination();
	}

	function showNewsTab() {
		if(resultNewsCount <= 0) {
			return;
		}

		currentType = 'news';
		currentPage = 0;

		$('.tabs a').removeClass('on');
		jqueryObject.showNewsButton.addClass('on');

		jqueryObject.webOnlyListDiv.removeClass('on').addClass('off').hide();
		jqueryObject.allListDiv.removeClass('on').addClass('off').hide();
		jqueryObject.newsOnlyListDiv.removeClass('off').addClass('on').show();
		printPagination();
	}

	function showWebTab() {
		if(resultWebCount <= 0) {
			return;
		}

		currentType = 'web';
		currentPage = 0;

		$('.tabs a').removeClass('on');
		jqueryObject.showWebButton.addClass('on');

		jqueryObject.newsOnlyListDiv.removeClass('on').addClass('off').hide();
		jqueryObject.allListDiv.removeClass('on').addClass('off').hide();
		jqueryObject.webOnlyListDiv.removeClass('off').addClass('on').show();
		printPagination();
	}

	function printPagination() {
		var totalPages = 0;
		if(currentType === 'news') {
			totalPages = Math.ceil(resultNewsCount / config.newsTabPageSize);
		} else if(currentType === 'web') {
			totalPages = Math.ceil(resultWebCount / config.webTabPageSize);
		}

		if(totalPages <= 0) {
			jqueryObject.pagination.html('');
			return;
		}

		var innerHtml = '';
		var startPageNumber = Math.floor(currentPage / config.paginationSize) * config.paginationSize;
		var hasNextPagination = (totalPages - 1) > (startPageNumber + config.paginationSize);

		if(startPageNumber !== 0) {
			innerHtml = innerHtml + '<a href="javascript:void(0);" class="btn_arrow prev" title="previous" data-paging="' + (startPageNumber - 1) + '"></a>';
		}

		for(var i=0; i<config.paginationSize; i++) {
			var printingPage = startPageNumber + i;
			if(printingPage >= totalPages) {
				break;
			}
			innerHtml = innerHtml + '<span><a href="javascript:void(0);" data-paging="' + (printingPage) + '"';
			if(printingPage === currentPage) {
				innerHtml = innerHtml + ' class="on"';
			}
			innerHtml = innerHtml + '>' + (printingPage + 1) + '</a></span>';
		}

		if(hasNextPagination) {
			innerHtml = innerHtml + '<a href="javascript:void(0);" class="btn_arrow next" data-paging="' + (startPageNumber + config.paginationSize) + '" title="next"></a>';
		}

		jqueryObject.pagination.html(innerHtml);
		jqueryObject.pagination.find('a').click(function() {
			var clickedPage = parseInt($(this).data('paging'));
			if(isNaN(clickedPage)) {
				return;
			}

			Search.setCurrentPage(clickedPage);
			getAndPrintDetailedPages(clickedPage);
		});
	}

	function getResultNewsCount() {
		return resultNewsCount;
	}
	function setResultNewsCount(count) {
		resultNewsCount = count;
	}

	function getResultWebCount() {
		return resultWebCount;
	}
	function setResultWebCount(count) {
		resultWebCount = count;
	}

	function getSearchCompany() {
		return searchCompany;
	}
	function setSearchCompany(companyName) {
		searchCompany = companyName;
	}

	function getCurrentPage() {
		return currentPage;
	}
	function setCurrentPage(pageNum) {
		currentPage = pageNum;
	}



	function getRefineQuery() {
		return refineQuery;
	}
	function setRefineQuery(newQuery) {
		refineQuery = newQuery;
	}


	return {
		getResultNewsCount: getResultNewsCount,
		setResultNewsCount: setResultNewsCount,
		getResultWebCount: getResultWebCount,
		setResultWebCount: setResultWebCount,
		getSearchCompany: getSearchCompany,
		setSearchCompany: setSearchCompany,
		getCurrentPage: getCurrentPage,
		setCurrentPage: setCurrentPage,
		getRefineQuery: getRefineQuery,
		setRefineQuery: setRefineQuery
	};

})();
