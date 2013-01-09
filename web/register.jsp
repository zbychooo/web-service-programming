<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Hamster.net - Best File Storage service</title>
        <link href="default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <div id="header" >
            <div id="logo">
                <h1><a href="#">Hamster.net</a></h1>
                <h2>Best File Storage service</h2>
            </div>
            <div id="menu">
            </div>
        </div>
        <div id="wrapper">
            <!-- start page -->
            <div id="page">           
                <!-- start content -->
                <div id="content">                    
                    <h1>Register user</h1>
                    <!--%= new java.util.Date() %>-->
                    <form action="rest/userService/register" method="POST">
                        <table>
                            <tr>
                                <td>
                                    login:
                                </td>
                                <td>
                                    <input type="text" name="login" value="" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Password:
                                </td>
                                <td>
                                    <input type="password" name="password" value="" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Confirm password:
                                </td>
                                <td>
                                    <input type="password" name="confirm_password" value="" />
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Your name:
                                </td>
                                <td>
                                    <input type="text" name="username" value="" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input type="submit" value="Register" />
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
                <!-- end content -->     
                <!-- start sidebar -->
                <div id="sidebar">
                </div>
                <!-- end sidebar -->
                <div style="clear: both;">&nbsp;</div>
            </div>            
        </div>
        <div id="footer">
            <p id="legal">(c) 2012-2013 Hamster.net. Designed by Sebastian Pasiecznik and Zbyszko Natkański.</p>
        </div>
    </body>
</html>