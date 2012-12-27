<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login</h1>

        <form action="rest/userService/login" method="POST">
            login: <input type="text" name="login" value="" /><br />
            pass: <input type="password" name="password" value="" /><br />
            <input type="submit" value="Register" />
        </form>

    </body>
</html>
