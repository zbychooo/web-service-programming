<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Hamster.xxx - Best File Storage service</title>
        <link href="default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <div id="header" >
            <div id="logo">
                <h1><a href="#">Hamster.xxx</a></h1>
                <h2>CSS Absolutely NOT designed by FreeCSSTemplates AND BRUTALLY MODIFIED BY ME WITHOUT GIVING THEM ANY CREDITS</h2>
            </div><!--
            <div id="menu">
                <ul id="accountmenu">
                    <li >
                        <a href="#">my account</a>
                        <ul>
                            <li><a href="#">Account details</a></li>
                            <li><a href="#">Logout</a></li>
                        </ul>
                    </li>
                </ul>
            </div>-->
        </div>
        <div id="wrapper">
            <!-- start page -->
            <div id="page">           
                <!-- start content -->
                <div id="content">
                    <form action="rest/userService/login" method="POST">
                        login: <input type="text" name="login" value="" /><br />
                        pass: <input type="password" name="password" value="" /><br />
                        <input type="submit" value="Register" />
                    </form>                    
                    
                    <a href="register.jsp">REGISTER</a><br/>
                    <a href="login.jsp">LOGIN</a> <br/>
                    <a href="rest/hello/sayHello">say Hello</a> <br/>
                    <a href="rest/hello/sayBye">say Bye</a><br/>
                    
                </div>
                <!-- end content -->     
                <!-- start sidebar -->
                <div id="sidebar">
                    <ul>
                        <li>
                            <h2><strong>Menu</strong></h2>
                            <ul>
                                <li>
                                    <!-- click on "my folders" and the user should be redirected to the list of folders, where they can 
                                    add/delete/share/view them -->
                                    <h3><a href="#" >My folders </a></h3>
                                    <ul>
                                        <li class="activelink"><a href="#">Folder #1</a></li>
                                        <li><a href="#">Folder #2</a></li>
                                    </ul>
                                </li>
				<li>
                                    <!-- click on "Shared folders" and the user should be redirected to the list of shared folders, 
                                    where they can share/hide/view them -->
                                    <h3><a href="#">Shared folders</a></h3>
                                    <ul>
                                        <li><a href="#">Folder #1</a></li>
                                    </ul>
                                </li>
				<li><a href="#">Watch porn</a></li>
                            </ul>
			</li>
			<li id="search">
                            <h2><b class="text1"> Global search</b></h2>
                            <form method="get" action="">
				<fieldset>
                                    <input type="text" id="s" name="s" value="" />
                                    <input type="submit" id="x" value="Search" />
                                    <select id="searchby" >
                                        <option id="byusers" value="Users" >Users</option>
                                        <option id="bytags" value="Tags" >Tags</option>
                                    </select>
				</fieldset>
                            </form>
			</li>
                    </ul>
                </div>
                <!-- end sidebar -->
                <div style="clear: both;">&nbsp;</div>
            </div>            
        </div>
        <div id="footer">
            <p id="legal">(c) 2012-2013 Hamster.xxx. Designed by Sebastian Pasiecznik and Zbyszko Natkański.</p>
        </div>
    </body>
</html>
