(function(){

	var newsRestUrl = '/rest/news';
	var newsViewPageUrl = '/' + doosan.vars.subDirLanguage + '/media-center/news';
	var nextPageNumber = 0;

	$(document).ready(init);

	function init() {
		attachMoreNews();
	}

	function attachMoreNews() {
		var parameter = {
			language: doosan.vars.localeLanguage,
			page: nextPageNumber
		};

		$.get(newsRestUrl, parameter).done(attachHtmlString);

		function attachHtmlString(ajaxResult) {
			var newsList = ajaxResult.content;
			var isLast = ajaxResult.last;

			var appendHtml = '';
			for(var i=0; i<newsList.length; i++) {
				var news = newsList[i];

				var linkClass;
				var linkAddress;
				var linkTarget;
				if(news.newsType == 'Press Release') {
					linkClass = 'type1';
					linkAddress = news.newsLink;
					linkTarget = '_blank';
				} else {
					linkClass = 'type2';
					linkAddress = newsViewPageUrl + '/' + news.newsId;
					linkTarget = '';
				}

				var thumbnail = null;
				if(news.newsThumbnail != null) {
					thumbnail = '/file/news-thumbnail/view/' + news.newsThumbnail.newsThumbnailId;
				}

				var date = '';
				if(news.newsPressDate != null) {
					date = news.newsPressDate.toString().slice(0,10).replace(/-/g, '.');
				}

				appendHtml = appendHtml + '<li class="' + linkClass + '">';
				appendHtml = appendHtml + '<a href="' + linkAddress + '" target="' + linkTarget + '">';
				if(thumbnail == null) {
					appendHtml = appendHtml + '<div class="thumb"></div>';
				} else {
					appendHtml = appendHtml + '<div class="thumb" style="background-image: url(' + thumbnail + ')"></div>';
				}
				appendHtml = appendHtml + '<div class="txt-area">';
				appendHtml = appendHtml + '<em class="category">' + news.newsType + '</em>';
				appendHtml = appendHtml + '<div class="tit-area">';
				appendHtml = appendHtml + '<h2 class="tit">' + news.newsTitle + '</h2>';
				if(news.newsType == 'Press Release') {
					appendHtml = appendHtml + '<span class="new-window">new<em>window</em></span>';
				}
				appendHtml = appendHtml + '</div>';
				appendHtml = appendHtml + '<div class="sub-tit">' + news.newsSummary + '</div>';
				appendHtml = appendHtml + '<span>' + news.newsCompany + '</span>' + ' ';
				appendHtml = appendHtml + '<span>' + date + '</span>';
				appendHtml = appendHtml + '</div>';
				appendHtml = appendHtml + '</a>';
				appendHtml = appendHtml + '</li>';
			}
			$('#news-list').append(appendHtml);

			nextPageNumber = nextPageNumber + 1;

			if(isLast) {
				$('.contents-more').hide();
			} else {
				$('.contents-more').show();
			}
		}
	}

})();
