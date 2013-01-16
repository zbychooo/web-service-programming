<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP !TEST! Page</title>
        <script type="text/javascript">
            function ajaxRequest(){
                var activexmodes=["Msxml2.XMLHTTP", "Microsoft.XMLHTTP"] //activeX versions to check for in IE
                if (window.ActiveXObject){ //Test for support for ActiveXObject in IE first (as XMLHttpRequest in IE7 is broken)
                    for (var i=0; i<activexmodes.length; i++){
                        try{
                            return new ActiveXObject(activexmodes[i])
                        }
                        catch(e){
                            //suppress error
                        }
                    }
                }
                else if (window.XMLHttpRequest) // if Mozilla, Safari etc
                    return new XMLHttpRequest();
                else
                    return false
            }
            
            function search(){
                var searchword = document.getElementsByName("searchPhrase")[0].value;
                console.log("search word:", searchword);
                
                 var mypostrequest=new ajaxRequest();
                            mypostrequest.onreadystatechange=function(){
                                if (mypostrequest.readyState==4){
                                    if (mypostrequest.status==200 || window.location.href.indexOf("http")==-1){
                                        document.getElementById("result").innerHTML=mypostrequest.responseText;
                                    }
                                    else{
                                        alert("An error has occured making the request");
                                    }
                                }
                            }
                            var parameters="searchPhrase="+searchword;
                            mypostrequest.open("POST", "rest/systemService/search", true);
                            mypostrequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                            mypostrequest.send(parameters);
            }
        </script>
    </head>
    <body>
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
<!--        <form action="rest/systemService/search" method="POST">-->
            <input type="text" name="searchPhrase" size="50" value="" />
            <input type="submit" value="search" onclick="search()"/>
<!--        </form>-->
    </body>
</html>
