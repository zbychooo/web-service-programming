<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Form to post currently playing track</title>
    </head>
    <body>
        <!-- Form is used in app2 -->
        <form action="./rest/music" method="POST">
            <label for="title">Title: </label>
            <input name="title" />
            <br/>
            <label for="artist">Artist: </label>
            <input name="artist" />
            <br/>
            <label for="album">Album: </label>
            <input name="album" />
            <br/>
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>
