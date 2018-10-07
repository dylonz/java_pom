<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    Hello,${key}!!!!!!
    <hr />
    ${goods.id}-${goods.title}-${goods.gimage}
    <hr />
    if用法
    <#if age<18>
        未成年
        <#elseif (age>=18 && age<40)>
            成年
        <#elseif (age>40)>
            中年
    </#if>
    <hr />
    循环用法
   <#list lists as good >
       ${good.id}-${good.title}-${good.gimage}<br />
   </#list>
    <hr />
    时间用法
    ${.now}<br />
   ${.now?string("yyyy-MM-dd HH:mm:ss")}
    <hr />
    ${money}<br />
    ${money?string("#")}<br />
    ${money?string("$#,###.##")}<br />
    <hr />
    <#if obj??>
        对象不为空
        <#else>
            对象为空
    </#if>
    <hr />
    ${obj!"默认值"}
    <hr />


</body>
</html>