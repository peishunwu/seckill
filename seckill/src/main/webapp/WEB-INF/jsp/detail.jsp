<%--
  Created by IntelliJ IDEA.
  User: psw
  Date: 2018/6/5
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="common/tag.jsp"%>
<html>
<head>
    <title>秒杀详情页</title>
    <%@include file="common/head.jsp"%>

</head>
<body>
    <div class="container">
        <div class="panel panel-default text-center"><%--面板--%>
            <div class="panel-heading">
                <h3>${seckill.name}</h3>
            </div>

            <div class="panel-body">
                <h2 class="text-primary">
                    <%--显示time图标--%>
                    <span class="glyphicon glyphicon-time"></span>
                        <%--展示倒计时--%>
                    <span class="glyphicon" id="seckill-box"></span>
                </h2>
            </div>
        </div>

    </div>

    <!-- 按钮触发模态框 -->
    <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#killPhoneModal">
        开始演示模态框
    </button>
<%--登录弹出模态框--%>
<div id="killPhoneModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone"></span>秒杀电话：
                </h3>
            </div>

            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" id="killPhoneKey"
                               placeholder="填写手机号^o^" class="form-control">
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <%--验证信息--%>
                <span id="killPhoneMessage" class="glyphicon"> </span>
                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>
                    提交
                </button>
            </div>
        </div>
    </div>
</div>
</body>
<%--jQery文件,务必在bootstrap.min.js之前引入--%>
<%--<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>--%>

<%--使用CDN 获取公共js http://www.bootcdn.cn/
	CDN不需要去网站获取插件
	可使WEB加速。
--%>
<%--jQuery Cookie操作插件--%>
<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<%--jQuery countDown倒计时插件--%>
<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>

<!-- 开始编写交互逻辑 -->
<script src="/seckill/resource/js/seckill.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {//调用函数
        //使用EL表达式传入参数
        seckill.detail.init({
            seckillId:${seckill.seckillId},
            startTime:${seckill.startTime.time},//把Date转换为系统的毫秒时间
            endTime:${seckill.endTime.time}
        });
    })
</script>
</html>
