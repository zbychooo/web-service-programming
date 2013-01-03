<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Register user</h1>
        <%= new java.util.Date() %>
        <form action="rest/userService/register" method="POST">
            login: <input type="text" name="login" value="" /><br />
            pass: <input type="password" name="password" value="" /><br />
            confirm pass: <input type="password" name="confirm_password" value="" /><br />
            your name: <input type="text" name="username" value="" /><br />
            <input type="submit" value="Register" />
        </form>
    </body>
</html>
