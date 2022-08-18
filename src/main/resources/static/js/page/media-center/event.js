(function(){

	var eventRestUrl = '/rest/event';
	var eventViewPageUrl = '/' + doosan.vars.subDirLanguage + '/media-center/event';
	var nextPageNumber = 0;

	$(document).ready(init);

	function init() {
		attachMoreEvent();
	}

	function attachMoreEvent() {
		var parameter = {
			language: doosan.vars.localeLanguage,
			page: nextPageNumber
		};

		$.get(eventRestUrl, parameter).done(attachHtmlString);

		function attachHtmlString(ajaxResult) {
			var eventList = ajaxResult.content;
			var isLast = ajaxResult.last;
			var currentTime = new Date();

			var appendHtml = '';
			for(var i=0; i<eventList.length; i++) {
				var event = eventList[i];
				var eventStartDate = new Date(event.eventStartDate);
				var eventEndDate = new Date(event.eventEndDate);

				var linkClass;
				var eventPhase;
				if(currentTime > eventEndDate) {
					linkClass = 'past-event';
					eventPhase = doosan.txt.mediaCenter.phasePast;
				} else if(currentTime < eventStartDate) {
					linkClass = '';
					eventPhase = doosan.txt.mediaCenter.phaseUpcoming;
				} else {
					linkClass = '';
					eventPhase = doosan.txt.mediaCenter.phaseOngoing;
				}

				var thumbnail = null;
				if(event.eventThumbnail != null) {
					thumbnail = '/file/event-thumbnail/view/' + event.eventThumbnail.eventThumbnailId;
				}

				appendHtml = appendHtml + '<li class="' + linkClass + '">';
				appendHtml = appendHtml + '<a href="' + eventViewPageUrl + '/' + event.eventId + '">';
				if(thumbnail == null) {
					appendHtml = appendHtml + '<div class="thumb"></div>';
				} else {
					appendHtml = appendHtml + '<div class="thumb" style="background-image: url(' + thumbnail + ')"></div>';
				}
				appendHtml = appendHtml + '<div class="txt-area">';
				appendHtml = appendHtml + '<em class="category">' + eventPhase + '</em>';
				appendHtml = appendHtml + '<div class="tit-area">';
				appendHtml = appendHtml + '<h2 class="tit">' + event.eventTitle + '</h2>';
				appendHtml = appendHtml + '</div>';
				appendHtml = appendHtml + '<div class="date">' + event.eventSchedule + '</div>';
				appendHtml = appendHtml + '<div class="place">' + event.eventLocation + '</div>';
				appendHtml = appendHtml + '</div>';
				appendHtml = appendHtml + '</a>';
				appendHtml = appendHtml + '</li>';
			}
			$('#event-list').append(appendHtml);

			nextPageNumber = nextPageNumber + 1;

			if(isLast) {
				$('.contents-more').hide();
			} else {
				$('.contents-more').show();
			}
		}
	}

})();
