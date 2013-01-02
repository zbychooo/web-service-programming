<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Upload:</h1>

        <form action="rest/systemService/uploadFile" method="POST" enctype="multipart/form-data">
            file: <input type="file" name="file" /><br />
            tags (comma seperated): <input type="text" name="tags" size="50" value="" /><br />
            dest. path: <select name="path">
                <option>1</option>
                <option>2</option>
            </select><br />
            <input type="submit" value="upload" />
        </form>
    </body>
</html>
