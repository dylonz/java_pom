//ajax进行登录验证
$(function () {
    $.ajax({
        url:"http://localhost:8085/sso/islogin",
        dataType:"jsonp"
    });
});
function callback(data) {
    if(data!=null){

        $("#login_id").html(data.name+"您好，欢迎来到<b><a href=\"/\">ShopCZ商城</a>[<a href=\"http://localhost:8085/sso/logout\">注销</a>]");

    }else{
        $("#login_id").html("[<a href=\"javascript:login();\">登录</a>][<a href=\"\">注册</a>]");


    }
};

function login() {
    //获得当前路径的url
    var returnUrl=location.href;
    alert("当前页面的url"+returnUrl);
    //解决乱码
     returnUrl=encodeURI(returnUrl);
    // //将&替换为*(解决解析出现的问题)
     returnUrl=returnUrl.replace("&","*");
    // //跳转到登录页面
     location.href="http://localhost:8085/sso/tologin?returnUrl="+returnUrl;

}

