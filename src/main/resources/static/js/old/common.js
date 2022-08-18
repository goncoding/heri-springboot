var CommonJs = (function(){
	return{
        isMobile:function(){
            var UserAgent = navigator.userAgent;

            if (UserAgent.match(/iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null || UserAgent.match(/LG|SAMSUNG|Samsung/) != null)
            {
                return true;
            }else{
                return false;
            }
        },	
        getURLParameter : function(_name){
            return decodeURI(
                (RegExp(_name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
            );
        },
		dateForm : function() {
            $(".dateForm").each(function() {
                var $this = $(this);
                $this.html($this.text().replace(/(\d{4})(\d{2})(\d{2})/gi, "$1.$2.$3"));
            });
            $(".mainDateForm").each(function() {
                var $this = $(this);
                $this.html($this.text().replace(/(\d{4})(\d{2})(\d{2})/gi, "<em>$1.$2</em><strong>$3</strong>"));
            });
        },
        getCookie:function(name) {
            name += "=";
            var arr = decodeURIComponent(document.cookie).split(';');
            for (var i = 0; i < arr.length; i++) {
                var c = arr[i];
                while (c.charAt(0) == ' ') c = c.substring(1);
                if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
            }
            return ""
        },
        getLocale:function(){
            //return CommonJs.getCookie('front_language');
        	var subPath = location.pathname;
            var currentLocale = "en";
            if(subPath.length >= 2) {
				var languagePart = location.pathname.substr(1,2);
				switch(languagePart) {
	        		case 'kr':
	        			currentLocale = 'ko';
	        			break;
	        		default:
	        			currentLocale = 'en';
	        			break;
				}
            }
            // return CommonJs.getCookie('front_language');
            return currentLocale;
        }
	}
})();

var responsive;

function setResponsive() {
    if ($('div#media-480').css('display') == 'block') responsive = 1;
    else if ($('div#media-766').css('display') == 'block') responsive = 2;
    else if ($('div#media-1080').css('display') == 'block') responsive = 3;
    else if ($('div#media-1081').css('display') == 'block') responsive = 0;
    else responsive = 4;

	if(responsive==1 || responsive==4) $("body").removeClass().addClass("uiMob");
	else if(responsive==2) $("body").removeClass().addClass("uiTab");
	else if(responsive==3) $("body").removeClass().addClass("uiTab2");
	else $("body").removeClass().addClass("uiWeb");
}
  
$(window).on('load', function () {
    setResponsive();
});
  
$(window).on('resize', function () {
    setResponsive();
});

$(document).ready(function(e) {
	setResponsive();
	
	var $body = $("body")

	// header search
	$(".btn_show_search").on("click", function() {
		$(".subGnbBack").trigger("mouseleave");
		$(".header-search").delay(100).fadeIn();
		$body.addClass("on");
		$(".header-search .close").fadeIn();
		$(".header-gnb").removeClass("open");
		$(".header-menu > button").removeClass("on");
	});
	$(".header-search > .close").on("click", function() {
		$("#Gnb").show();
		$body.removeClass("on");
		$(".header-search").fadeOut();
		$(this).hide().siblings("button").fadeIn();

	});
	
	//header language
	$(".header-language > button").on("click", function() {
		$(this).next().slideDown(300);
	});
	$(".header-language ").on("mouseleave", function() {
		$(".Language").slideUp(300);
	});

	//total menu(max-width:1080px)
	$(".header-menu > button").on("click", function() {
		$(".header-search > .close").trigger("click");
		$(".header-gnb").addClass("open");
		//$(".subGnbBack").trigger("mouseleave");
		//if($(".current-snb").length>0 && $(".current-snb").hasClass("on")) $(".current-snb.on").trigger("click");

		/*if($(this).hasClass("on")){
			$(".header-gnb").removeClass("open");
			$(this).removeClass("on");
		} else {
			$(".header-gnb").addClass("open");
			$(this).addClass("on");
		}*/
	});
	$(".header-gnb .close").on("click", function() {
		$(".header-gnb").removeClass("open");
	});
	
	//header gnb
	var gnb_pos =  $("#wrap");
	if(gnb_pos) {
		if(responsive==0){
			$(".gnb-dep01 > li > a").on("mouseenter focus", function() {
				$(".subGnbBack, .gnb-dep02 > li > a").clearQueue();
				$body.addClass("active");
				$(".header-gnb").addClass("active");
				$(".gnb-dep02").show().addClass("active");
				$(".subGnbBack").addClass("active");
				$(".gnb-dep01 > li").each(function(){
					$(this).find('.gnb-dep02 li').each(function(index){
						$(this).delay(100*index).animate({
							'top':'0',
							'opacity':'1'
						},200,'easeOutCubic')
					})
				})
			});
			$(".subGnbBack, .gnb-dep01").on("mouseleave",function() {
				if(!$(this).hasClass("searchActive")) {
					$(".subGnbBack, .gnb-dep02 > li > a").clearQueue();
					$(".subGnbBack").removeClass("active");
					$(".header-gnb").removeClass("active");
					$(".gnb-dep02").removeClass("active");
					//$(".gnb-dep02-div").stop().slideDown(100);
					$(".gnb-dep02 > li").stop(true,false).animate({
						'top':'114px',
						'opacity':'0'
					},200);
					$body.removeClass("active");
				}
			});
		}

		if($(".gnb-dep01 > li .gnb-dep02-div").length > 0){
			$(".gnb-dep01 > li .gnb-dep02-div").each(function() {
				$(this).prev().addClass("on")
			});
			
		}
		var $gnbDepth1MobBtn = $(".gnb-dep01 > li > a.m-only");
		$gnbDepth1MobBtn.on("click", function() {
			$gnbDepth1MobBtn.removeClass("active");
			$gnbDepth1MobBtn.next().slideUp();
			if($(this).hasClass("active")) {
				$(this).removeClass("active");
				$(this).next().slideUp();
			} else {
				$(this).addClass("active");
				$(this).next().slideDown();
				$(this).next().delay(100).fadeIn();
			}
		});
	}

	//(min-width:1081px)
	$(".gnb-dep01 > li:last-child .gnb-dep02 > li:last-child a").on("focusout", function() {
		$body.removeClass("active");
		$(".gnb-dep02").hide();
		$(".subGnbBack").removeClass("active");
	});

	var Gnb =(function(){
	    var g_$searchArea = $('.header-search'),
	        g_$searchInput = g_$searchArea.find('.g_search'),
	        g_$searchBtn = g_$searchArea.find('.btn_top_search');
	    
	    /* GNB search */
	    $(g_$searchInput).on('keydown', function(e){
	        if(e.keyCode == 13){
	            e.preventDefault();
	            searchGnb();
	        }
	    });

	    g_$searchBtn.on('click',function(e){
	        e.preventDefault();
	        searchGnb();
	    });

	    function searchGnb(){
	        var searchKeyword = g_$searchInput.val();
	        var _lang = CommonJs.getLocale();
	        var alertMessage = '';
	        var agent = navigator.userAgent.toLowerCase();
	        
	        if ($.trim(searchKeyword) == '') {
	            alertMessage = (_lang=='ko')?'검색어를 입력하세요.':((_lang == 'en')?'Input Keyword':'搜索');
	            alert(alertMessage);
	            return;
	        } else {
	            switch(_lang) {
	                case "ko": _lang = "kr";break;
	                default:
	                    _lang = "en";
	            }
	            if ( (navigator.appName == 'Netscape' && agent.indexOf('trident') != -1) || (agent.indexOf("msie") != -1)) {
	            	$("#gnbSearchForm").attr("action", '/' + _lang + '/search2');
		            $("#gnbSearchForm").submit();
	            } else {
	            	$("#gnbSearchForm").attr("action", '/' + _lang + '/search');
		            $("#gnbSearchForm").submit();
	            }

	        }
	    }
	})();    
	
	var translateStr;

	/** TRANSLATOR */
	var Translator = (function(){
		
	    var g_langData, g_defaultLang,
	        g_$trn = $(".trn, .trna, .trnt, .trnp, .trns"), g_trnLength = g_$trn.size(),
	        g_$langDropdownUl = $("#gnbHeader .header-language, .lang, .header-gnb .gnb-language");

	    function init(_data, _lang){
	    	
	        if(!_lang || _lang == "") _lang = "ko";

	        g_langData = _data;
	        g_defaultLang = CommonJs.getLocale();
	        
	        setLangEl();
	        setBtns();
	    }
	    
	    function setLangEl(){
	    	
	        var m_krStr = '<a href="javascript:;" data-lang="ko" class="ko">한국어</a>',
	            m_enStr = '<a href="javascript:;" data-lang="en" class="en">English</a>',
	            m_currentLi,
	            m_appendStr = "";

	        switch(g_defaultLang){
				case "ko" : m_appendStr = m_krStr+m_enStr; break;
				case "en" : m_appendStr = m_enStr+m_krStr; break;
	        }
	        
	        g_$langDropdownUl.find(".Language").html(m_appendStr);
	        m_currentLi = g_$langDropdownUl.find("."+g_defaultLang);
	        m_currentLi.addClass("on");
	    }

	    function setBtns(){
	    	
	        g_$langDropdownUl.find("a").on("click", function(){
	            var m_selectLang = $(this).attr("data-lang");
	            var m_urlLang = m_selectLang == "ko" ? "kr" : "en";
	            var m_url = location.href;
	            m_url = m_url.replace(/^(?:https?:\/\/[^\/]+)\/(en|kr)?/, "/" + m_urlLang);
	            m_url+=((m_url.indexOf("?") > 0)?"&":"?");
	            
	            $.get(m_url+"front_language="+m_selectLang, function(){
	                location.href = location.href.replace(/^(?:https?:\/\/[^\/]+)\/(en|kr)?/, "/" + m_urlLang);
	            })
	        })
		}
		
		function setTranslate(){
	        for(var i = 0; i < g_trnLength; ++i){
	            var m_trn = g_$trn.eq(i),
	                m_trnType, m_trnProperties,
	                m_alt = $(m_trn).attr("alt"),
	                m_placeholder = $(m_trn).attr("placeholder"),
	                m_title = $(m_trn).attr("title"),
	                m_trnPropertiesStr = "",
	                m_dataTrn = $(m_trn).attr("data-trn"),
	                m_src = $(m_trn).attr("src");

	            if(m_dataTrn == "" || m_dataTrn == undefined){
	                if(m_alt != undefined && m_alt != ""){
	                    m_trnType = "alt";
	                    m_trnPropertiesStr = m_alt;
	                }else if(m_placeholder != undefined && m_placeholder != ""){
	                    m_trnType = "placeholder";
	                    m_trnPropertiesStr = m_placeholder;
	                }else if(m_title != undefined && m_title != ""){
	                    m_trnType = "title";
	                    m_trnPropertiesStr = m_title;
	                } else if (m_src != undefined && m_src != "") {
	                    m_trnType = "src";
	                    m_trnPropertiesStr = m_src;
	                }else{
	                    m_trnType = "el";
	                    m_trnPropertiesStr = m_trn.html();
	                }

	                m_trnProperties = (m_trnPropertiesStr).replace(/\r\n|\r|\n|\{|\}|\ /gi, "");

	                m_trn.attr("data-trn", m_trnProperties);
	                m_trn.attr("data-trn-type", m_trnType);
	            }
	        }

	        lang(g_defaultLang);
	    }

	    function lang(_lang){
	        var m_lang = (!_lang)?g_defaultLang:_lang;
	        
	        console.log(m_lang);
	        
	        for(var i = 0; i < g_trnLength; ++i) {
	            var trn = g_$trn.eq(i);

	            changeLangString(trn, m_lang);
	        }
	    }

	    function changeLangString(_trn, _m_lang){
	        var getProperties = _trn.attr("data-trn").split("."),
	            getType = _trn.attr("data-trn-type"),
	            currentLangStr,
	            depthProperties = g_langData;

	        for (var z = 0, zLen = getProperties.length; z < zLen; ++z) {
	            depthProperties = depthProperties[getProperties[z]];
	        }

	        currentLangStr = depthProperties[_m_lang];
	        switch (getType) {
	            case "el" :
	                _trn.html(currentLangStr);
	                break;
	            case "alt" :
	                _trn.attr("alt", currentLangStr);
	                break;
	            case "placeholder" :
	                _trn.attr("placeholder", currentLangStr);
	                break;
	            case "title" :
	                _trn.attr("title", currentLangStr);
	                break;
	            case "src" :
	                _trn.attr("src", currentLangStr);
	                break;
	            case "href" :
	                _trn.attr("href", currentLangStr);
	                break;
	        }
	    }

	    function reload(_lang){
	    	console.log("reload");
	        g_$trn = $(".trn, .trna, .trnt, .trnp, .trns");
	        g_trnLength = g_$trn.size();
	        if(!_lang || _lang == "") _lang = "ko";
	        g_defaultLang = _lang;

	        setTranslate();
	        //changeLangString(g_$trn,_lang)
	    }

	    function targetChangeLang(_targetArr, _lang){
	        for(var i = 0, len = _targetArr.length; i < len; ++i){
	            var m_trn = $(_targetArr[i]);

	            changeLangString(m_trn, _lang);
	        }
	    }

	    return {
	        init:init,
	        lang:lang,
	        targetChangeLang:targetChangeLang,
	        reload:reload,
	        setTranslate:setTranslate
	    }
	})();

	Translator.init(translateStr,CommonJs.getLocale());


	//move page top
	$(".ico-top").on("click", function() {
		$("html, body").animate({
			scrollTop : 0
		},500);
		return false;
	});

	
	$(window).on("scroll", function() {
		var scroll = $(window).scrollTop();
		if(scroll >= (window.innerHeight)/3) {
			 $(".ico-top").fadeIn(300);
		} else {
			 $(".ico-top").fadeOut(300);
		}
	});

});