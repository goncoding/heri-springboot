(function() {

	$(window).on('resize', changeBodyUiClass);
	$(document).ready(documentReady);

	function documentReady() {
		changeBodyUiClass();
		initSearchArea();
		initLanguageChangeArea();
		initLanguageChangeMobileArea();
		initMobileMenu();
		initGnbArea();
		initMoveTopButton();
		initContentPageTab();
		initContentAppearAnimation();
	}

	function changeBodyUiClass() {
		var bodyUiClassName;
		if (isPcSize()) {
			bodyUiClassName = 'uiWeb';
		} else if(isTabletWideSize()) {
			bodyUiClassName = 'uiTab2';
		} else if(isTabletNarrowSize()) {
			bodyUiClassName = 'uiTab';
		} else {
			bodyUiClassName = 'uiMob';
		}
		$("body").removeClass().addClass(bodyUiClassName);
	}

	function isPcSize() {
		return $(window).width() > 1080;
	}

	function isTabletWideSize() {
		return $(window).width() > 768 && $(window).width() <= 1080;
	}

	function isTabletNarrowSize() {
		return $(window).width() > 480 && $(window).width() <= 768;
	}

	function isMobileSize() {
		return $(window).width() <= 480;
	}

	function initSearchArea() {
		$('.btn_show_search').on('click', eventSearchAreaOpenButton);
		$('.header-search > .close').on('click', eventSearchAreaCloseButton);
		$('.header-search .g_search').on('keydown', eventSearchAreaSearchFieldEnterKey);
		$('.header-search .btn_top_search').on('click', eventDoSearch);

		function eventSearchAreaOpenButton() {
			$('.subGnbBack').trigger('mouseleave');
			$('.header-search').delay(100).fadeIn();
			$('body').addClass("on");
			$('.header-search .close').fadeIn();
			$('.header-gnb').removeClass('open');
			$('.header-menu > button').removeClass('on');
		}

		function eventSearchAreaCloseButton() {
			$('#Gnb').show();
			$('body').removeClass('on');
			$('.header-search').fadeOut();
			$(this).hide().siblings('button').fadeIn();
		}

		function eventSearchAreaSearchFieldEnterKey(event) {
			if(event.keyCode === 13) {
				eventDoSearch(event);
			}
		}

		function eventDoSearch(event) {
			event.preventDefault();

			var searchKeyword = $('.header-search .g_search').val();
			if(searchKeyword.length > 0) {
				$("#gnbSearchForm").submit();
			} else {
				alert(doosan.txt.common.noSearchKeyword);
			}
		}
	}

	function initLanguageChangeArea() {
		var languageArea = $('#Language');
		var languageChangeLink = languageArea.children('a');

		$('.header-language > button').on('click', eventLanguageChangeAreaClick);
		$('.header-language').on('mouseleave', eventLanguageChangeAreaClose);
		languageChangeLink.on('click', eventChangeLanguage);

		function eventLanguageChangeAreaClick() {
			languageArea.slideDown(300);
		}

		function eventLanguageChangeAreaClose() {
			languageArea.slideUp(300);
		}

		function eventChangeLanguage(event) {
			event.preventDefault();

			var currentUrl = location.href;
			var selectedSubDir = $(this).data('sub-dir');
			location.href = currentUrl.replace('/' + doosan.vars.subDirLanguage, '/' + selectedSubDir);
		}
	}
	
	function initLanguageChangeMobileArea() {
		g_$langDropdownUl = $(".header-gnb .gnb-language");
		g_$langDropdownUl.find("a").on("click", function(){
	        var m_selectLang = $(this).attr("data-lang");
	        var m_urlLang = m_selectLang == "ko" ? "kr" : "en";
	        var m_url = location.href;
	        m_url = m_url.replace(/^(?:https?:\/\/[^\/]+)\/(en|kr)?/, "/" + m_urlLang);
	        m_url+=((m_url.indexOf("?") > 0)?"&":"?");
	        
	        $.get(m_url+"front_language="+m_selectLang, function(){
	            location.href = location.href.replace(/^(?:https?:\/\/[^\/]+)\/(en|kr)?/, "/" + m_urlLang);
	        });
	    })
	}

	function initMobileMenu() {
		$('.header-menu > button').on('click', eventMobileMenuOpenButton);
		$('.header-gnb .close').on('click', eventMobileMenuCloseButton);
		$('.gnb-dep01 > li > a.m-only').on('click', eventMobileMenuDepth2MenuToggleButton);

		function eventMobileMenuOpenButton() {
			$('.header-search > .close').trigger('click');
			$('.header-gnb').addClass('open');
		}

		function eventMobileMenuCloseButton() {
			$('.header-gnb').removeClass('open');
		}

		function eventMobileMenuDepth2MenuToggleButton() {
			$(this).removeClass('active');
			$(this).next().slideUp();
			if($(this).hasClass('active')) {
				$(this).removeClass('active');
				$(this).next().slideUp();
			} else {
				$(this).addClass('active');
				$(this).next().slideDown();
				$(this).next().delay(100).fadeIn();
			}
		}
	}

	function initGnbArea() {
		$('.gnb-dep01 > li > a').on('mouseenter focus', eventGnbSubMenuOpen);
		$('.subGnbBack, .gnb-dep01').on('mouseleave', eventGnbSubMenuClose);
		$('.gnb-dep01 > li:last-child .gnb-dep02 > li:last-child a').on('focusout', eventGnbSubMenuCloseWhenFocusedOutUsingTabKey);

		function eventGnbSubMenuOpen() {
			$('.subGnbBack, .gnb-dep02 > li > a').clearQueue();
			$('body').addClass('active');
			$('.header-gnb').addClass('active');
			$('.gnb-dep02').show().addClass('active');
			$('.subGnbBack').addClass('active');
			$('.gnb-dep01 > li').each(function() {
				$(this).find('.gnb-dep02 li').each(function(index) {
					$(this).delay(100*index).animate({'top': '0', 'opacity': '1'}, 200, 'easeOutCubic');
				});
			});
		}

		function eventGnbSubMenuClose() {
			if(!$(this).hasClass('searchActive')) {
				$('.subGnbBack, .gnb-dep02 > li > a').clearQueue();
				$('.subGnbBack').removeClass('active');
				$('.header-gnb').removeClass('active');
				$('.gnb-dep02').removeClass('active');
				$('.gnb-dep02 > li').stop(true,false).animate({'top': '114px', 'opacity': '0'}, 200);
				$('body').removeClass('active');
			}
		}

		function eventGnbSubMenuCloseWhenFocusedOutUsingTabKey() {
			$body.removeClass('active');
			$('.gnb-dep02').hide();
			$('.subGnbBack').removeClass('active');
		}
	}

	function initMoveTopButton() {
		$('.ico-top').on('click', eventMoveTopButtonClick);
		$(window).on('scroll', eventMoveTopButtonShowHide);

		function eventMoveTopButtonClick() {
			$('html, body').animate({scrollTop: 0}, 500);
			return false;
		}

		function eventMoveTopButtonShowHide() {
			var currentScroll = $(window).scrollTop();
			if(currentScroll >= (window.innerHeight) / 3) {
				 $('.ico-top').fadeIn(300);
			} else {
				 $('.ico-top').fadeOut(300);
			}
		}
	}

	function initContentPageTab() {
		initContentTabSwitchContentOnPage();
		initContentPageTabTopFixed();

		function initContentTabSwitchContentOnPage() {
			$('.tab-inner-wrap > a').not('.page .tab-inner-wrap > a').on('click', showTargetContentAndHideOtherContents);
			$('.current-tab-text + .tab-inner-wrap a').on('click', changeCurrentTabTextOnMobile);
			$('.current-tab-text').on('click', changeContentByItsId);

			function showTargetContentAndHideOtherContents() {
				$(this).addClass('on').siblings().removeClass('on');
				$($(this).attr('href')).fadeIn().siblings().hide();

				if($('.current-tab-text').is(':visible')) {
					$('.current-tab-text + .tab-inner-wrap').hide();
				}
				return false;
			}

			function changeCurrentTabTextOnMobile() {
				$('.current-tab-text').text($(this).text());
			}

			function changeContentByItsId() {
				$($(this).attr('href')).toggle();
				return false;
			}
		}

		function initContentPageTabTopFixed() {
			var anchorPoint = {};
			setTimeout(function() {
				calculatePageTabAnchorPoint();
			}, 500);

			$('.tab-item').on('click', eventContentPageTabClick);
			$(window).on('scroll', eventContentPageTabPageScroll);
			$(window).on('resize', function() {
				setTimeout(function() {
					eventContentPageTabPageScroll();
					calculatePageTabAnchorPoint();
				}, 500);
			});

			function calculatePageTabAnchorPoint() {
				anchorPoint = {};
				$('.tab-item').each(function() {
					var contentElementId = $(this).data('tab-target');
					if(contentElementId != null) {
						anchorPoint[contentElementId] = {
							targetElement: $(this),
							targetOffset: $('#' + contentElementId).offset().top
						};
					}
				});
			}

			function eventContentPageTabClick(event) {
				event.preventDefault();

				var contentElementId = $(this).data('tab-target');
				var elementInfo = anchorPoint[contentElementId];
				var moveOffset;
				if(isPcSize()) {
					moveOffset = elementInfo.targetOffset - 300;
				} else if(isTabletWideSize()) {
					moveOffset = elementInfo.targetOffset - 280;
				} else {
					moveOffset = elementInfo.targetOffset - 260;
				}

				if(elementInfo != null) {
					$('html, body').animate({scrollTop: moveOffset}, 300);
				}
			}

			function eventContentPageTabPageScroll() {
				var currentScroll = $(window).scrollTop();

				fixTabOnTopWhenVisualAreaScrolledOut();
				changeTabClassByScrollPosition();

				function fixTabOnTopWhenVisualAreaScrolledOut() {
					var visualHeight;
					if($('.subVisual').hasClass('sub-visual-none')) {
						visualHeight = $('#Header').height();
					} else {
						visualHeight = $('.subVisual').height() - $('#Header').height();
					}

					if(currentScroll >=  visualHeight) {
						$('#sub-gnb-wrap').addClass('fixed');
						$('#Header').addClass('hide');
					} else {
						$('#sub-gnb-wrap').removeClass('fixed');
						$('#Header').removeClass('hide');
					}
				}

				function changeTabClassByScrollPosition() {
					var elementIdList = Object.keys(anchorPoint);

					for(var i=0; i<elementIdList.length; i++) {
						var elementId = elementIdList[i];
						var elementInfo = anchorPoint[elementId];
						if(elementInfo != null) {
							var tabChangeScrollPoint;
							if(isPcSize()) {
								tabChangeScrollPoint = elementInfo.targetOffset - 305;
							} else if(isTabletWideSize()) {
								tabChangeScrollPoint = elementInfo.targetOffset - 285;
							} else {
								tabChangeScrollPoint = elementInfo.targetOffset - 265;
							}

							if(currentScroll >= tabChangeScrollPoint) {
								elementInfo.targetElement.addClass('on').siblings().removeClass('on');
							}
						}
					}
				}
			}
		}
	}

	function initContentAppearAnimation() {
		$(window).on("scroll", showAnimatedContent);

		function showAnimatedContent() {
			var currentScroll = $(window).scrollTop();
			var windowHeight = $(window).height();

			var contentAppearPoint = [];

			$('.sequential > *').each(function() {
				contentAppearPoint.push($(this).offset().top);
			});

			for(var i=0; i<contentAppearPoint.length; i++) {
				var appearScrollPoint = contentAppearPoint[i] - (windowHeight / 1.2) ;
				if(currentScroll > appearScrollPoint) {
					$('.sequential > *').eq(i).addClass('end');
				}
			}

			var contentTitleAppearPoint = [];
			$('.tit-sequential').each(function() {
				contentTitleAppearPoint.push($(this).offset().top);
			});

			for(var i=0; i<contentTitleAppearPoint.length; i++) {
				var appearScrollPoint = contentAppearPoint[i] - windowHeight;
				if(currentScroll > appearScrollPoint) {
					$('.tit-sequential').eq(i).addClass('tit-end');
				}
			}
		}
	}

})();
