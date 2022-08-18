
var ContactUsForm =(function(){

	var g_submitChk = true;
    var _lang = CommonJs.getLocale();
    var alertMessage='';
    
    
    function init(){
    	$(".rule-scroll-box .scrollbar-inner").scrollbar();
    	addEvents();
    	fileCheckFun();
    }

    function addEvents(){

    	$("#qTypeText").val($("#lb-category option:selected").text());
    	
    	$("#lb-nation").on("change", function () {
    		$("#userCountry").val($("#lb-nation option:selected").text());
    		$("#userCountryName").val($("#lb-nation option:selected").text());
    	});
    	
    	$("#lb-category").on("change", function () {
    		$("#qTypeText").val($("#lb-category option:selected").text());
    	});
    	
    	$('#btn_apply').on('click', function () {
    		checkForm();
        });
    
    	
    }
    
    //폼 체크
    function checkForm(event) {
    	var alertMessageEmail = (_lang=='ko')?'이메일의 형식이 잘못되었습니다.':((_lang == 'en')?'Invalid e-mail type.':'邮箱格式无效。');
    	
    	if ($(".input_name").val() == '') {
            alertMessage = (_lang=='ko')?'이름을 입력해 주세요.':((_lang == 'en')?'Please enter your Name.':'请输入姓名。');
            alert(alertMessage);
            $(".input_name").focus();
            return;
        } else if ($(".input_mail_id").val() == '') {
            alertMessage = (_lang=='ko')?'이메일을 입력해 주세요.':((_lang == 'en')?'Please enter your e-mail address.':'请输入邮箱。');
            alert(alertMessage);
            $(".input_mail_id").focus();
            return;
        } else if (!ContactUsForm.isEmail($(".input_mail_id").val(), alertMessageEmail)) {
            return;
        } else if ($('.input_title').val() == '') {
            alertMessage = (_lang=='ko')?'제목을 입력해 주세요.':((_lang == 'en')?'Please enter title.':'请输入标题。');
            alert(alertMessage);
            $(".input_title").focus();
            return;
        } else if ($('.input_content').val() == '') {
            alertMessage = (_lang=='ko')?'내용을 입력해 주세요.':((_lang == 'en')?'Please enter the information.':'请输入内容。');
            alert(alertMessage);
            $(".input_content").focus();
            return;
        }
    	
    	
    	if(_lang=='ko'){
            if (!$('#check-1').is(':checked')) {
                alert('개인정보 수집 및 이용동의를 자세히 읽어 보신 후 동의해 주시기 바랍니다.');
                return;
            }
        }
    	
        if(g_submitChk) {

            g_submitChk	= false;

            $("#contactUsForm").on("submit", function (e) {
                e.preventDefault();
                g_submitChk	= true;

            });

            document.getElementById('contactUsForm').submit()
        } else {
            alertMessage = (_lang=='ko')?'잠시만 기다려주세요.':((_lang == 'en')?'Please wait.':'请稍等。');
            alert(alertMessage);
        }
    }
    
    //파일 체크
    function fileCheckFun() {
        $(document).on('change', '.upload-hidden', function () {

            if (window.FileReader) {
                var file = this.files[0];
                var fileSize = file.size;
                var fileKbytes = Math.round(parseInt(fileSize) / 1024);

                if($("#file").val() == "" ){
                    alertMessage = (_lang=='ko')?'파일을 선택해주세요.':((_lang == 'en')?'Select file.':'请选择文件。');
                    alert(alertMessage);
                    return;
                }else if( $("#file").val() != "" ) {
                    var ext = $('#file').val().split('.').pop().toLowerCase();
                    if ($.inArray(ext, ['gif', 'jpg', 'jpeg', 'png', 'pdf', 'xls', 'xlsx', 'doc', 'docx', 'hwp', 'zip']) == -1) {
                        $("#file").val('');
                        $(".upload-name").val('');

                        alertMessage = (_lang=='ko')?'등록할 수 없는 파일형태입니다.':((_lang == 'en')?'Not allowed file type to upload. Invalid file type.':'无法上传该格式附件。');
                        alert(alertMessage);
                        return;
                    }
                }

                if (fileKbytes > 9000) {
                    alertMessage = (_lang=='ko')?'첨부 가능한 파일의 최대 용량은 9MB입니다.':((_lang == 'en')?'The attachment should not exceed 30MB.':'附件上传最大支持9MB。');
                    alert(alertMessage);

                    $(this).siblings('.upload-name').val('');
                    $(this).val('');

                    return false;
                } else {
                    var filename = $(this)[0].files[0].name;
                }
            } else {
                var filename = $(this).val().split('/').pop().split('\\').pop();
            }

            $(this).parent().find('.upload-name').val(filename);
        })
    }

    //파일 삭제
    function fileDelete() {
        var agent = navigator.userAgent.toLowerCase();

        if (agent.indexOf("msie") != -1) {
            $("#file").replaceWith($("#file").clone(true));
        } else {
            $("#file").val('');
        }
        $('.upload-name').val('');
    }
    
    init();
    select();
    return {
    	checkLength: function () {
            var oElement = (arguments[0] != null) ? arguments[0] : this;

            var length = oElement.value.length;
            var maxLength = arguments[1];

            if (length > maxLength) {
                alertMessage = (_lang=='ko')?maxLength + '자 안에서 입력해 주세요.':((_lang == 'en') ? 'Enter a max. of "' + maxLength + '" words.' : '请在' + maxLength + '个字符以内输入。');
                alert(alertMessage);
                oElement.value = oElement.value.substr(0, maxLength - 2);
            }
        },
        isEmail: function () {
            var strEmail;
            var alertMessageEmail = (_lang=='ko')?'이메일의 형식이 잘못되었습니다.':((_lang == 'en')?'Invalid e-mail type.':'邮箱格式无效。');
            var strMessage = (arguments[2]) ? arguments[2] : alertMessageEmail;

            if (!arguments[0]) {
                alertMessage = (_lang=='ko')?'이메일을 입력해 주세요.':((_lang == 'en')?'Please enter your e-mail address.':'请输入邮箱。');
                alert(alertMessage);
                return false;

            } else {
                strEmail = arguments[0];
            }
            var re = /^[0-9a-zA-Z-_\.]*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
            if (re.test(strEmail)) {
                if (strEmail.length > 100) {
                    alertMessage = (_lang=='ko')?'이메일을 100자 이내로 입력 하세요.':((_lang == 'en')?'Enter e-mail body (max. of 100 words).':'电子邮件内容请勿超过100个字符。');
                    alert(alertMessage);
                    return false;
                }
                return true;

            } else {
                alert(strMessage);
                return false;
            }
        },
        emailSmall: function () {
            /* 이메일 소문자 변환 */
            $('.input_mail_id').keyup(function (e) {
                e.preventDefault();
                $(this).val($(this).val().toLowerCase());
                return false;
            });
        },
        isDigitStr: function () {
        	$('.input_tel').keyup(function (e) {
        		e.preventDefault();
                $(this).val($(this).val().replace(/[^0-9]/g,""));
                return false;
        	});
    	}
    }

    function select(){
    	$('select').selectric();
    }
   
    
})()
