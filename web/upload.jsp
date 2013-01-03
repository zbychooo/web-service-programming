<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP !TEST! Page</title>
    </head>
    <body>
        <h1>Upload:</h1>

        <form action="rest/systemService/uploadFile" method="POST" enctype="multipart/form-data">
            file: <input type="file" name="file" /><br />
            tag: <input type="text" name="tag" size="50" value="" /><br />
            dest. path: <select name="path">
                <option>baluty</option>
                <option>widzew</option>
                <option>polesie</option>
            </select><br />
            <input type="submit" value="upload" />
        </form>
        
        <hr /><h1>CreateFolder:</h1>

        <form action="rest/systemService/createFolder" method="POST">
            folder name: <input type="text" name="folderName" size="50" value="" /><br />
            <input type="submit" value="create" />
        </form>
        
        <hr /><h1>Menu:</h1>
        <ul>
            <li><a href="rest/systemService/deleteFile/widzew/zaal.txt">delete zaal.txt</a></li>
            <li><a href="rest/systemService/deleteFolder/baluty">delete folder baluty</a></li>
        </ul>
        
        
        <hr /><h1>ShareFolder:</h1>

        <form action="rest/systemService/shareFolder" method="POST">
            file path [format: {folderGłówny}//{nazwaPodfolderu}]: <input type="text" name="filePath" size="50" value="" /><br />
            share login: <input type="text" name="shareLogin" size="50" value="" /><br />
            <input type="submit" value="share" />
        </form>
    </body>
</html>
