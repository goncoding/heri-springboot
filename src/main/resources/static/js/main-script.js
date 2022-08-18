$(document).ready(function(e) {

	$(".solution-wrap .article").on("mouseenter focus", function(){
		$(this).addClass("on").siblings().removeClass("on");
	}).on("mouseleave focusout", function(){ 
		$(this).first().trigger("mouseenter"); 
	}).first().trigger("mouseenter");

	$(".accCont").on("mouseenter focus", ".col", function(){
		$.$(this).addClass("active").siblings().removeClass("active");
	}).on("mouseleave blur", function(){ 
		$.$(this).find(".col").first().trigger("mouseenter"); 
	}).find(".col").first().trigger("mouseenter");



	//about solus
	$(".uiWeb .main-block03-tbl > div .textbox").on("mouseenter", function() {
		$(".main-block03-tbl > div").addClass("active");
		$(".main-block03-tbl .textbox").removeClass("active slideUp");
		$(this).addClass("active slideUp");
		var num = $(this).parent().index();
		$(".main-block03-back >  div").eq(num).addClass("active").siblings().removeClass("active");
	});

	$(".uiWeb .main-block03-tbl > div .textbox").on("mouseleave", function() {
		$(".main-block03-tbl .textbox").removeClass("active slideUp");
		$(".main-block03-tbl > div").removeClass("active");
	});
});



/************* DO NOT ALTER ANYTHING BELOW THIS LINE ! **************/
//cookie
function setCookie(name, value, expiredays){
	var todayDate = new Date();
	todayDate.setDate(todayDate.getDate() + expiredays);
	document.cookie=name + "=" + escape(value) + "; path=/; expires=" + todayDate.toGMTString() + ";"
}
function getCookie(name){
	var nameOfCookie = name + "=";
	var x =0;
	while (x<=document.cookie.length){
		var y = (x+nameOfCookie.length);
		if(document.cookie.substring(x,y) == nameOfCookie){
			if((endOfCookie=document.cookie.indexOf(";",y))==-1){
				endOfCookie = document.cookie.length;
			}

			return unescape(document.cookie.substring(y, endOfCookie));
		}
		x=document.cookie.indexOf(" ",x) +1;
		if(x==0){
			break;
		}
	}
	return "";
}

function delete_cookie( name ) {
	document.cookie = name + '=; expires=Wed, 23 May 1970 04:13:59 GMT;';
	$.cookie(name,null, {expire:'Wed, 23 May 1970 04:13:59 GMT',domain:'.mm.doosan.com',path:'/'});
}

function excuteCookie(){
	//if user agree cookie policy, excute adobe cookie or not delete all browser cookie
	if(getCookie("agree_cookie") == "agree_cookie"){
		var s_code=s.t();if(s_code)document.write(s_code);
	}else{
		var cookies = document.cookie.split(";");
		for (var i = 0; i < cookies.length; i++){
			delete_cookie(cookies[i].split("=")[0].trim());
		}
	}
}

$(document).ready(function(){
	if(getCookie("agree_cookie") != "agree_cookie"){
		$('#ENG_COOKIE').show();
	}else{
		$('#ENG_COOKIE').hide();
	}
	$('#ENG_COOKIE .btn_cookie_accept').on('click', function(){
		setCookie('agree_cookie','agree_cookie',7);
		excuteCookie();
		$('#ENG_COOKIE .btn_close').trigger('click');
	});
	$('#ENG_COOKIE .btn_close').on('click',function(){
		$('#ENG_COOKIE').hide();
	});
});
