<#macro layout>
    <html>
    <head>
        <title>JCODE</title>
        <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
        <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

        <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
        <script src="https://cdn.staticfile.org/jquery/2.1.1/jquery.min.js"></script>
        <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
        <script src="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <!-- 抽屉组件 ：https://www.dowebok.com/166.html -->
        <script src="http://qiniu.bigdudu.cn/js-simpler-sidebar.min.js"></script>

        <style>
            /*抽屉组件样式*/
            .sidebar-wrapper {
                position: relative;
                height: 100%;
                overflow: auto;
                color:white;
                font-size: 16px;
                padding:10px;
            }
            #btn-help{
                position: fixed;
                right: 20px;
                top: 60px;
            }
        </style>
    </head>
    <body>
    <div id="btn-help">
        <!-- 打开右边抽屉 -->
        <svg t="1626058740989" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1696" width="32" height="32"><path d="M512 512m-512 0a512 512 0 1 0 1024 0 512 512 0 1 0-1024 0Z" p-id="1697"></path><path d="M514.3552 285.9008q-67.584 0-107.52 38.7072-39.936 38.0928-39.936 106.9056h70.0416a100.46464 100.46464 0 0 1 15.36-60.2112q17.19296-25.1904 57.7536-25.1904a66.56 66.56 0 0 1 49.152 17.2032 70.37952 70.37952 0 0 1 0.6144 91.5456l-7.3728 8.6016q-60.23168 53.4528-71.8848 78.0288a131.15392 131.15392 0 0 0-12.288 60.2112v8.6016h70.656v-8.6016a90.4192 90.4192 0 0 1 33.792-70.656q43.008-37.4784 52.8384-48.5376a121.91744 121.91744 0 0 0 22.7328-75.5712 110.88896 110.88896 0 0 0-36.864-88.4736q-36.27008-32.5632-97.0752-32.5632z m-11.0592 354.5088a48.35328 48.35328 0 0 0-33.792 12.9024 47.47264 47.47264 0 0 0 0 66.3552 48.55808 48.55808 0 0 0 33.792 13.5168 47.03232 47.03232 0 0 0 33.792-12.9024 43.64288 43.64288 0 0 0 14.1312-33.792 45.14816 45.14816 0 0 0-13.5168-33.1776 47.84128 47.84128 0 0 0-34.4064-12.9024z" fill="#EBBA50" p-id="1698"></path></svg>
    </div>
    <div class="container-fluid">
        <div class="row">
            <div class=".col-md-12">
                <nav class="navbar navbar-inverse navbar-fixed-top">
                    <div class="container-fluid">
                        <!-- Brand and toggle get grouped for better mobile display -->
                        <div class="navbar-header">
                            <a class="navbar-brand" href="#">JCode</a>
                        </div>

                        <!-- Collect the nav links, forms, and other content for toggling -->
                        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                            <ul class="nav navbar-nav">
                                <li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li>
                                <li><a href="#">Link</a></li>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                       aria-haspopup="true" aria-expanded="false">代码生成 <span class="caret"></span></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="/db/mysql">MySql</a></li>
                                        <li><a href="/db/mssql">SqlServer</a></li>
                                    </ul>
                                </li>
                            </ul>
                            <form class="navbar-form navbar-left">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Search">
                                </div>
                                <button type="submit" class="btn btn-default">Submit</button>
                            </form>
                            <ul class="nav navbar-nav navbar-right">
                                <li><a href="#">Link</a></li>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="#">Action</a></li>
                                        <li><a href="#">Another action</a></li>
                                        <li><a href="#">Something else here</a></li>
                                        <li role="separator" class="divider"></li>
                                        <li><a href="#">Separated link</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </div><!-- /.navbar-collapse -->
                    </div><!-- /.container-fluid -->
                </nav>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class=".col-md-12" style="margin-top:50px;">
                    <#nested />
                </div>
            </div>
        </div>
    </div>
<script>
    $(function(){
        $('#dowebok').simplerSidebar({
            opener: '#btn-help',
            sidebar: {
                align: 'right',
                width: 350,
                css:{
                    backgroundColor:'#101010'
                }
            }
        });
    })
</script>
    </body>
    </html>
</#macro>