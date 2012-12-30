<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login</h1>

<form method="POST" action="j_security_check">
   <input type="text" name="j_username">
   <input type="password" name="j_password">
   <input type="submit" value="Log in">
</form>

    </body>
</html>
