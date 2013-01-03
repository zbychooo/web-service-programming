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
            </div>
            <div id="menu">
            </div>
        </div>
        <div id="wrapper">
            <!-- start page -->
            <div id="page">           
                <!-- start content -->
                <div id="content">
                    <form method="POST" action="j_security_check">
                        <table>
                            <tr>
                                <td>
                                    Login:
                                </td>
                                <td>
                                    <input type="text" name="j_username" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Password:
                                </td>
                                <td>
                                    <input type="password" name="j_password" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input type="submit" value="Log in" />
                                </td>
                            </tr>
                        </table>
                    </form>  
                    <br/>
                    <p>No account yet? <a href="register.jsp">Register</a> now for free</p><br/>

                </div>
                <!-- end content -->     
                <!-- start sidebar -->
                <div id="sidebar">
                    <ul>
                        <li id="search">
                            <h2><b class="text1"> Global search</b></h2>
                            <form method="get" action="">
                                <fieldset>
                                    <input type="text" id="s" name="s" value="" />
                                    <input type="submit" id="x" value="Search" />
                                </fieldset>
                                    <select id="searchby" >
                                        <option id="byusers" value="Users" >Users</option>
                                        <option id="bytags" value="Tags" >Tags</option>
                                    </select>
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
