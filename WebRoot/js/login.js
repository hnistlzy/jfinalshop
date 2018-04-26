$(function () {

    $(".errorClass").each(function () {
        showError($(this));
    });
   $(".al_Input").blur(function () {
       var id=$(this).attr("id");
       //要调用validateMehtod
       var method = "validate"+id.substring(0,1).toUpperCase()+id.substring(1)+"()";
       eval(method);
       
   });
   $(".al_Input").focus(function () {
       var id =$(this).attr("id");
       $("#"+id+"Error").text("");
       showError($("#"+id+"Error"));
   });
    $('#login span').eq(0).click(function() {
        location.href = "/reg"
    });

    $('#login span').eq(1).click(function() {
        $('.ts_wrong').remove();
        $('.the_input').removeClass('error');
        if ($('#txtName').val() == '') {
            var notice = ' <div class="ts_wrong"><span class="errrClass" id="txtNameError">请输入邮箱/手机号码！</span></div>';
            $('#txtName').parent().after(notice);
            $('#txtName').parent().addClass('error');
            return false;
        }

        if ($('#txtPwd').val() == '') {
            var notice = ' <div class="ts_wrong"><span class="errrClass" id="txtNameError">请输入密码！</span></div>';
            $('#txtPwd').parent().after(notice);
            $('#txtPwd').parent().addClass('error');
            return false;
        }

        $('form').submit();
    });


});

$(document).keyup(function(event){
    if(event.keyCode ==13){
        $('#login span').eq(1).click();
    }
});


/**
 * 显示错误信息的方法：
 * 如果没有错误信息，则隐藏span 和 它的父级div
 * 如果有错误信息则显示
 * @param obj 对象
 */
function  showError(obj) {
    var text=obj.text();
    if(!text){
        obj.css("display","none");
        obj.parent(".ts_wrong").css("display","none");
    }else{
        obj.css("display","");
        obj.parent(".ts_wrong").css("display","");
    }
}
function validateTxtName() {
    var id = "txtName";
    var text = $("#" + id).val();
    var bool=true;
    if (!text) {
        $("#" + id + "Error").text("请输入邮箱/手机号码!");
        showError($("#" + id + "Error"));
        bool=false;
    }
    return bool;
}
function validateTxtPwd() {
    var id = "txtPwd";
    var text = $("#" + id).val();
    var bool=true;
    if (!text) {
        $("#" + id + "Error").text("密码不能为空！");
        showError($("#" + id + "Error"));
        bool=false;
    }
   return bool;
}