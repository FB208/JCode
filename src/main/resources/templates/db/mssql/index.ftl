<#include "shared/layout.ftl">

<@layout>
    <style>
        .panel-collapse{
            padding:20px;
        }
    </style>
    <div class="panel-group" id="accordion">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion"
                       href="#collapseOne">
                        选择数据库
                    </a>
                </h4>
            </div>
            <div id="collapseOne" class="panel-collapse collapse in">
                <div class="panel-body">
                    <form class="form-inline">
                        <div class="form-group">
                            <label for="ipt_connStr">链接字符串</label>
                            <input id="ipt_connStr"  list="slt_connStr" class="form-control form-group" />
                            <datalist  id="slt_connStr">
                                <#list connList as conn>
                                    <option value="${conn.serverName}">${conn.serverName}</option>
                                </#list>
                            </datalist>
                        </div>
                        <button type="button" class="btn btn-default" onclick="conn()">连接</button>
                        <div class="form-group">
                            <label for="ipt_db">数据库</label>
                            <input id="ipt_db" list="slt_db" class="form-control form-group"/>
                            <datalist  id="slt_db">

                            </datalist>
                        </div>
                        <button type="button" class="btn btn-default" onclick="openDb()">打开</button>
                    </form>


                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion"
                       href="#collapseTwo" id="collapse-2">
                        mybatis代码生成
                    </a>
                </h4>
            </div>
            <div id="collapseTwo" class="panel-collapse collapse">
                <input id="ipt_tableName" list="slt_tableName" class="form-control form-group" />
                <datalist  id="slt_tableName">
                    <#list tableNameList as tableName>
                        <option value="${tableName}">${tableName}</option>
                    </#list>
                </datalist>
                <div class="checkbox">
                    <label>
                        <input type="checkbox" id="cb_isSqlProvider" checked> 生成SqlProvider
                    </label>
                </div>
                <div class="btn-group form-group" role="group" aria-label="..." >
                    <button type="button" class="btn btn-default" onclick="doEntity()">entity</button>
                    <button type="button" class="btn btn-default" onclick="doMapper()">mapper</button>
                    <button type="button" class="btn btn-default" onclick="batch()">批量生成</button>
                </div>
                <div class="form-group">
                    <textarea class="form-control" id="txt_output1" rows="16"></textarea>
                </div>
                <div class="form-group">
                    <textarea class="form-control" id="txt_output2" rows="16"></textarea>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion"
                       href="#collapseThree">
                        点击我进行展开，再次点击我进行折叠。第 3 部分
                    </a>
                </h4>
            </div>
            <div id="collapseThree" class="panel-collapse collapse">
                <div class="panel-body">
                    Nihil anim keffiyeh helvetica, craft beer labore wes anderson
                    cred nesciunt sapiente ea proident. Ad vegan excepteur butcher
                    vice lomo.
                </div>
            </div>
        </div>
    </div>
    <script>
        var settings = {
            "url": "",
            "method": "POST",
            "timeout": 0,
            "headers": {
                "Content-Type": "application/json"
            },
            "data": JSON.stringify({
                "isSqlProvider": "true"
            }),
        };
        $(function(){
        })
        //读取配置文件
        function initOption(){
            var option = {
                isSqlProvider:$("#cb_isSqlProvider").prop("checked")
            }
            return option;
        }
        //链接数据库
        function conn(){
            var option = initOption();
            settings.url="/dbapi/mssql/conn?conn="+$("#ipt_connStr").val();
            settings.data=JSON.stringify(option);
            $.ajax(settings).done(function (response) {
                console.log(response.result1);
                response.result1.forEach(item=>{
                    $("#slt_db").append("<option value='"+item+"'>"+item+"</option>")
                })
            });
        }
        //打开数据库
        function openDb(){
            var option = initOption();
            settings.url="/dbapi/mssql/openDb?dbName="+$("#ipt_db").val();
            settings.data=JSON.stringify(option);
            $.ajax(settings).done(function (response) {
                $("#slt_tableName").empty();
                response.result1.forEach(item=>{
                    $("#slt_tableName").append("<option value='"+item+"'>"+item+"</option>")
                })
                $("#collapse-2").click();
            });
        }
        //生成实体
        function doEntity(){
            var option = initOption();
            settings.url="/dbapi/mssql/doEntity?dbName="+$("#ipt_db").val()+"&tableName="+$("#ipt_tableName").val();
            settings.data=JSON.stringify(option);
            $.ajax(settings).done(function (response) {
                $("#txt_output1").val(response.result1);
                $("#txt_output2").val(response.result2);
            });

        }
        //生成mapper
        function doMapper(){
            var option = initOption();
            settings.url="/dbapi/mssql/doMapper?dbName="+$("#ipt_db").val()+"&tableName="+$("#ipt_tableName").val();
            settings.data=JSON.stringify(option);
            $.ajax(settings).done(function (response) {
                $("#txt_output1").val(response.result1);
                $("#txt_output2").val(response.result2);
            });
        }
        //批量生产
        function batch(){
            var option = initOption();
            settings.url="/dbapi/mssql/batch?dbName="+$("#ipt_db").val();
            settings.data=JSON.stringify(option);
            $.ajax(settings).done(function (response) {
                alert("请前往此处下载: http://"+window.location.host+"/download?filePath="+response.path);
            });
        }
    </script>
</@layout>