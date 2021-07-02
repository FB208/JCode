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
                    Nihil anim keffiyeh helvetica, craft beer labore wes anderson
                    cred nesciunt sapiente ea proident. Ad vegan excepteur butcher
                    vice lomo.
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a data-toggle="collapse" data-parent="#accordion"
                       href="#collapseTwo">
                        mybatis代码生成
                    </a>
                </h4>
            </div>
            <div id="collapseTwo" class="panel-collapse collapse">
                <select class="form-control form-group" id="slt_tableName">
                    <#list tableNameList as tableName>
                        <option value="${tableName}">${tableName}</option>
                    </#list>
                </select>
                <div class="btn-group form-group" role="group" aria-label="..." >
                    <button type="button" class="btn btn-default" onclick="doEntity()">entity</button>
                    <button type="button" class="btn btn-default">mapper</button>
                    <button type="button" class="btn btn-default">批量生成</button>
                </div>
                <div class="form-group">
                    <textarea class="form-control" id="txt_output"></textarea>
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
        $(function(){

        })
        function doEntity(){
            $.ajax({
                type:"POST",
                url:"/db/mssql/doEntity",
                data:{tableName:$("#slt_tableName").val()},
                //dataType:"json",
                success:function(data){
                    debugger;
                    console.log(data);
                    $("#txt_output").val(data);
                },
                complete:function(XMLHttpRequest, textStatus){
                    debugger;
                }
            })
        }
    </script>
</@layout>