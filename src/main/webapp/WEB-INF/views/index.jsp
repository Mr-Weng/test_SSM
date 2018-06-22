<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" import="java.util.*" %>
<html>
<body>
<h2>Hello World!</h2>
<c:forEach items="${list}" var="list">
    <div>姓名：${list.name}</div>
    <div>性别：${list.sex}</div>
    <div>年龄：${list.age}</div>
</c:forEach>
<form id="iqform">
    请输入姓名：<input type="text" name="name" id="name" />
    请输入性别：<input type="text" name="sex" id="sex" />
    请输入年龄：<input type="text" name="age" id="age" />
    <input type="button" value="查询" id="selectStu" onclick="Qsubmit(this.id)"/>
    <input type="button" value="插入" id="insertAll" onclick="Qsubmit(this.id)"/>
    <input type="button" value="删除" id="deleteStu" onclick="Qsubmit(this.id)"/>
    <input type="button" value="修改" id="updateStu" onclick="Qsubmit(this.id)"/>
</form>
<div style="color: red">${error}</div>

<script>
    var Qsubmit = function (id) {
        if(document.getElementById("name").value == "" && id !="selectStu"){
            alert("名字不能为空");
        }
        else{
            document.getElementById("iqform").action = "/test/"+ id;
            document.getElementById("iqform").method = "post";
            document.getElementById("iqform").submit();//提交
        }
    };
    // var Qsubmit = function () {
    //     document.getElementById("iqform").action = "/test/selectStu";
    //     document.getElementById("iqform").method = "POST";
    //     document.getElementById("iqform").submit();//提交
    // };
    // var Isubmit = function () {
    //     if(document.getElementById("name").value == ""){
    //         alert("名字不能为空");
    //     }
    //     else{
    //         document.getElementById("iqform").action = "/test/insertAll";
    //         document.getElementById("iqform").method = "post";
    //         document.getElementById("iqform").submit();//提交
    //     }
    // };
    // var Dsubmit = function () {
    //     if(document.getElementById("name").value == ""){
    //         alert("名字不能为空");
    //     }
    //     else{
    //         document.getElementById("iqform").action = "/test/deleteStu";
    //         document.getElementById("iqform").method = "post";
    //         document.getElementById("iqform").submit();//提交
    //     }
    // };
    // var Usubmit = function () {
    //     if(document.getElementById("name").value == ""){
    //         alert("名字不能为空");
    //     }
    //     else{
    //         document.getElementById("iqform").action = "/test/updateStu";
    //         document.getElementById("iqform").method = "post";
    //         document.getElementById("iqform").submit();//提交
    //     }
    // }
</script>

<%--<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>--%>
<%--<script type="text/javascript">--%>
    <%--$(document).ready(function(){--%>
        <%--var iButton = $("#iButton");--%>
        <%--var qButton = $("#qButton");--%>

        <%--qButton.click(function () {--%>
            <%--alert("查询点击");--%>
            <%--$("#iqform").attr('action',"/test/getStu");--%>
            <%--$("#iqform").attr("method","get");--%>
            <%--$("#iqform").submit();--%>
        <%--});--%>
    <%--})--%>
<%--</script>--%>

</body>
</html>
