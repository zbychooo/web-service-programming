<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP !TEST! Page</title>
        <script type="text/javascript">
            function startDownload()          
            {
                var url='D:\\RESTCloudStorage\\admin\\widzew\\test1filee.txt';  
                window.open(url,'Download');
            }
        </script>
    </head>
    <body>
        <script type="text/javascipt">
setTimeout("startDownload()",5000); //starts download after 5 seconds
</script>
        <div id="result">testowanie</div>
        <!--        <h1>Upload:</h1>
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
                </form>-->

        <hr /><h1>Menu:</h1>
        <ul>
            <!--            <li><a href="rest/systemService/deleteFile/baluty/zaal.txt">delete zaal.txt</a></li>
                        <li><a href="rest/systemService/deleteFolder/baluty">delete folder baluty</a></li>-->
            <li><a href="rest/systemService/downloadFile/baluty/zaal.txt">download zaal.txt</a></li>
            <li><button onclick="startDownload()">download</button></li>
        </ul>

        <!--        
                <hr /><h1>ShareFolder:</h1>
                <form action="rest/systemService/shareFolder" method="POST">
                    file path [format: {folderGłówny}//{nazwaPodfolderu}]: <input type="text" name="filePath" size="50" value="" /><br />
                    share login: <input type="text" name="shareLogin" size="50" value="" /><br />
                    <input type="submit" value="share" />
                </form>
                
                <h1><u>UN</u>ShareFolder:</h1>
                <form action="rest/systemService/unshareFolder" method="POST">
                    file path [format: {folderGłówny}//{nazwaPodfolderu}]: <input type="text" name="filePath" size="50" value="" /><br />
                    <input type="submit" value="UNshare" />
                </form>-->

        <hr /><h1>Search:</h1>
        <form action="rest/systemService/search" method="POST">
            search: <input type="text" name="searchPhrase" size="50" value="" />
            <input type="submit" value="search" />
        </form>
    </body>
</html>
