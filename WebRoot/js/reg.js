$(function(){
    //切换tab
    $(".tits .ph").click(function (event) {
        $(this).addClass('focus').siblings().removeClass('focus')
        $(".photo_show").eq(0).show()
        $(".photo_show").eq(1).hide()
    });
    $(".tits .em").click(function (event) {
        $(this).addClass('focus').siblings().removeClass('focus')
        $(".photo_show").eq(1).show()
        $(".photo_show").eq(0).hide()
    });
    $("#phone").blur(function () {
        console.log("111");
        if(!validatePhone()){
            console.log("手机号已经被注册");
            $("#phoneCode").attr("disabled","disabled").css("background-color","#dddddd");
        }
    });
    $("#phone").focus(function () {
            $("#phoneCode").attr("disabled",false).css("background-color","#FFF");

    });
    /**
     * 循环遍历每一个span 隐藏的错误信息。有则显示，无则隐藏
     */
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
    /**
     * 用户提交表单时进行校验
     */
        $("#form_second").submit(function () {
            var bool=true;
            if(!validateEmail()){
                bool =false;
            }
            if (!validatePassword()){
                bool =false;
            }
            if (!validateRepwd()){
                bool =false;
            }
            return bool;
        });
        $(document).keyup(function(event){
        if(event.keyCode ==13){
            $("#form_second").submit();
        }
    });

    /**
     * 手机注册，点击发送验证码时。。。。
     */
    $("#phoneCode").click(function () {
        //1. 启动一个定时器。让点击按钮变暗。并将显示内容修改成“请60秒后再发送验证码”
        var time=60;
        timer=null;
        //每过1秒钟显示数字-1，
        timer=setInterval(function () {
            time--;
            // alert(time);
            $("#phoneCode").val("请"+time+"秒后重试");
            $("#phoneCode").attr("disabled","disabled").css("background-color","#dddddd");
            // $("#phoneCode").css("background-color","");
            if(time<=0){
                // alert("111111");
                $("#phoneCode").val("获取验证码");
                $("#phoneCode").attr("disabled",false).css("background-color","#FFF");
                clearInterval(timer);
                time=60;
            }
        },1000);
        //2.用AJAX发送验证码。
        var value=$("#phone").val();
        $.ajax({
            url:"/user/ajaxGetPhoneCode",
            data:{phone:value},
            dataType: "json",
            async: false,//是否异步请求，如果是异步，那么不会等服务器返回，我们这个函数就向下运行了。
            cache: false,
            success: function (result) {
                console.log(typeof result);
                console.log(result);
                if(!result){
                    $("#phoneError").text("验证码发送失败！请检查手机号!");
                    showError($("#phoneError"));
                    bool=false;
                }
            }
        })
    });
    $("#form_first").submit(function () {
        var bool=true;
        if(!validatePhone()){
            bool=false;
        }
        if(!validatePhone_pwd()){
            bool=false;
        }
        if(!validatePhone_pwd_confirm()){
            bool=false;
        }
        return bool;

    })

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
function validateEmail() {
    //非空校验
    var id = "email";
    var text = $("#" + id).val();
    var bool=true;
    if (!text) {
        $("#" + id + "Error").text("邮箱不能为空！");
        showError($("#" + id + "Error"));
        bool=false;
    }
    //email格式校验

    if (!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(text)) {
        $("#" + id + "Error").text("邮箱格式不正确！");
        showError($("#" + id + "Error"));
        bool=false;
    }
    //是否注册校验

    $.ajax({
        url: "/user/ajaxValidateEmail",//要请求的servlet
        data: {email: text},//给服务器的参数
        type: "POST",
        dataType: "json",
        async: false,//是否异步请求，如果是异步，那么不会等服务器返回，我们这个函数就向下运行了。
        cache: false,
        success: function (result) {
            if (!result) {//如果校验失败
                $("#" + id + "Error").text("Email已被注册！");
                showError($("#" + id + "Error"));
                bool=false;
            }

        }

    });
    return bool;
}
/**
     * 密码校验
     */
function validatePassword() {
        //非空校验
        var id = "password";
        var text=$("#"+id).val();
        var bool=true;
        if(!text){
            $("#"+id+"Error").text("密码不能为空！");
            showError($("#"+id+"Error"));
           bool =false;
        }
        //长度校验
       else if(text.length>=16 ||text.length<=3){
            $("#"+id+"Error").text("密码长度应在3~16之间！");
            showError($("#"+id+"Error"));
            bool =false;
        }
        return bool;
    }
/**
 * 确认密码校验
 */
function validateRepwd() {
        //非空校验
        var id = "repwd";
        var text=$("#"+id).val();
        var bool=true;
        if(!text){
            $("#repwdError").text("确认密码不能为空！");
            showError( $("#repwdError"));
            bool =false;

        }
        //长度校验
       else if(text.length>=16 ||text.length<=3){
            $("#"+id+"Error").text("确认密码长度应在3~16之间！");
            showError($("#"+id+"Error"));
            bool =false;
        }
        var pwd=$("#password").val();
        if (text != pwd) {
            $("#"+id+"Error").text("两次密码输入不一致");
            showError($("#"+id+"Error"));
            bool =false;
        }
        return bool;
    }
/**
 * 验证码校验
 */
// function validateVerifyCode() {
//    // 长度校验
//     var id = "verifyCode";
//     var text=$("#"+id).val();
//     var bool=true;
//     if(text.length!=4){
//         $("#"+id+"Error").text("验证码格式错误！");
//         showError($("#"+id+"Error"));
//         bool =false;
//     }
//    // ajax校验
//     $.ajax({
//         url:"/user/ajaxValidateVerifyCode",
//         data:{pageVerifyCode:text},//给服务器的参数
//         type:"POST",
//         dataType:"json",
//         async:false,//是否异步请求，如果是异步，那么不会等服务器返回，我们这个函数就向下运行了。
//         cache:false,
//         success:function(result) {
//             if(!result) {//如果校验失败
//                 $("#" + id + "Error").text("验证码错误！");
//                 showError($("#" + id + "Error"));
//                 bool =false;
//             }
//         }
//     });
//     return bool;
//  }
function  validatePhone() {
    var id = "phone";
    var text=$("#"+id).val();
    var bool=true;
    if(!text){
        $("#"+id+"Error").text("手机号不能为空！");
        showError($("#"+id+"Error"));
        bool =false;
    }
    //正则表达式校验，随后在加
    //ajax异步校验
    $.ajax({
        url: "/user/ajaxValidatePhoneNum",//要请求的servlet
        data: {phone: text},//给服务器的参数
        type: "POST",
        dataType: "json",
        async: false,//是否异步请求，如果是异步，那么不会等服务器返回，我们这个函数就向下运行了。
        cache: false,
        success: function (result) {
            if (!result) {//如果校验失败
                $("#" + id + "Error").text("手机号已被注册！");
                showError($("#" + id + "Error"));
                bool=false;
            }
        }
    });
    return bool;
}
function validatePhone_pwd() {
    var id = "phone_pwd";
    var text=$("#"+id).val();
    var bool=true;
    if(!text){
        $("#"+id+"Error").text("密码不能为空！");
        showError($("#"+id+"Error"));
        bool =false;
    }else if(text.length<3||text.length>16){
        $("#"+id+"Error").text("密码长度不正确！");
        showError($("#"+id+"Error"));
        bool =false;
    }
    return bool;
}
function validatePhone_pwd_confirm() {

    var id = "phone_pwd_confirm";
    var text=$("#"+id).val();
    var bool=true;
    if(!text){
        $("#"+id+"Error").text("确认密码不能为空！");
        showError($("#"+id+"Error"));
        bool =false;
    } else if(text.length<3||text.length>16){
        $("#"+id+"Error").text("确认密码长度不正确！");
        showError($("#"+id+"Error"));
        bool =false;
    }
    var phone_pwd_val=$("#phone_pwd").val();
    if(phone_pwd_val!=text){
        $("#"+id+"Error").text("两次输入不一致！");
        showError($("#"+id+"Error"));
        bool =false;
    }
    return bool;
}




