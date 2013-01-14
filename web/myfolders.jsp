<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Hamster.net - Best File Storage service</title>
        <link href="default.css" rel="stylesheet" type="text/css" />
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
            
            function select()
            {
                document.getElementById("selectedall").checked=false
            }
            
            function selectAll(len)
            {
                if(document.getElementById('selectall').checked)
                    for (var i=0;i<len;i++)
                { 
                    document.getElementById(i).checked=true;
                }
                else
                    for (var i=0;i<len;i++)
                { 
                    document.getElementById(i).checked=false;
                }
            }
            
            function shareFolder()
            {
                var shareLogin = document.getElementsByName("friendLogin")[0].value;
                console.log("login: ", shareLogin);
                var folders = new Array();

            <c:set var="counter" value="${0}" /> 
            <c:forEach items="${foldersdm}" var="folder">

                        var i = ${counter};
                        var isChecked = document.getElementById(i).checked;
                        var name1 = "${folder.name}";
                        if(isChecked){
                            folders.push(name1);
                        }

                        console.log(i, " isChecked: ", isChecked, " name:", name1);
                <c:set var="counter" value="${counter+1}" />
            </c:forEach>
               
                        for(var j=0; j<folders.length; j++) {
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
                            var parameters="shareLogin="+shareLogin+"&filePath="+folders[j];
                            mypostrequest.open("POST", "rest/systemService/shareFolder", true);
                            mypostrequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                            mypostrequest.send(parameters);
                        }
                    }
                
                    function unshareFolder(folderName){
                        console.log("folderName: ", folderName);
                    
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
                        var parameters="filePath="+folderName;
                        mypostrequest.open("POST", "rest/systemService/unshareFolder", true);
                        mypostrequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                        mypostrequest.send(parameters);
                    
                    
                    }
                    
      function deleteFolder(folderName) {
                console.log("folderName ", folderName);
                
                var mygetrequest=new ajaxRequest();
                mygetrequest.onreadystatechange=function(){
                    if (mygetrequest.readyState==4){
                        if (mygetrequest.status==200 || window.location.href.indexOf("http")==-1){
                            document.getElementById("result").innerHTML=mygetrequest.responseText;
                        }
                        else{
                            alert("An error has occured making the request: " + mygetrequest.status);
                        }
                    }
                };
                var fileNameValue=encodeURIComponent(folderName);
                mygetrequest.open("GET", "rest/systemService/deleteFolder/"+fileNameValue, true);
                mygetrequest.send(null);
                
            }
        </script>
    </head>
    <body>
        <div id="header" >
            <div id="logo">
                <h1><a href="#">Hamster.net</a></h1>
                <h2>Best File Storage service</h2>
            </div>
            <div id="menu">
                <ul id="accountmenu">
                    <li>
                        <a href="#">${user.username}</a>
                        <ul>
                            <li><a href="rest/userService/logout">Logout</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
        <div id="wrapper">
            <!-- start page -->
            <div id="page">           
                <!-- start content -->
                <div id="content">
                    <div id="fileactions">
                        <form id="fileform" action="rest/systemService/createFolder" method="POST" >
                        <input type="text" name="folderName" value="" />
                        <input type="submit" id="newfoldermenu" value="New folder"/>
                        </form>
                        &nbsp; Free space: <c:out value="${remainingSpace}" /> MB &nbsp;
                        <br/>
                    </div>
                    <br/>
                    <table class="contentTable" name="mainTable">
                        <thead>
                            <tr>
                                <td>
                                    <input type="checkbox" id="selectall" onclick="selectAll(${foldersdm.size()})" />
                                </td>
                                <td class="myfoldersname">
                                    Name
                                </td>
                                <td>
                                    Added
                                </td>
                                <td>
                                    Actions
                                </td>
                            </tr>
                        </thead>
                        <tbody>

                            <c:set var="counter" value="${0}" /> 
                            <c:if test="${foldersdm.isEmpty()==false}" >
                                <c:forEach items="${foldersdm}" var="folder">
                                    <tr>
                                        <td>
                                            <input type="checkbox" id="${counter}" onclick="select()"/> 
                                        </td>
                                        <td onclick="document.location = 'rest/home/${counter}';" id="folder${counter}" class="myfoldersname">
                                            <c:out value="${folder.name}" />
                                        </td>
                                        <td onclick="document.location = 'rest/home/${counter}';">
                                            <c:out value="${folder.dateStamp}" />
                                        </td>
                                        <td>
                                            <input type="button" id="delete" value="X" onclick="deleteFolder('${folder.name}')"/> 
                                        </td>
                                    </tr>
                                    <c:set var="counter" value="${counter+1}" />
                                </c:forEach>
                            </c:if>
                            <c:if test="${foldersdm.isEmpty()==true}">
                                <tr>
                                    <td colspan="5">
                                        There are no folders in your account.
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                    <br/>        
                    Mark folders to share and give user's login you would like to share: <input type="text" name="friendLogin" value="" />
                    <input type="submit" id="sharefoldermenu" value="Share folder" onclick="shareFolder()" />
                    <div id="result"></div>
                </div>
                <!-- end content -->     
                <!-- start sidebar -->
                <div id="sidebar">
                    <ul>
                        <li>
                            <h2><strong>Menu</strong></h2>
                            <ul>
                                <li>
                                    <!-- click on "my folders" and the user should be redirected to the list of folders, where they can 
                                    add/delete/share/view them -->
                                    <h3><a href="rest/myfolders/all" >My folders </a></h3>
                                    <c:set var="counter" value="${0}" />
                                    <c:forEach items="${folders}" var="folder">
                                        <c:if test="${folder.user.login.equals(user.login)==true}" >
                                            <ul>
                                                <li><a href="rest/home/${counter}">${folder.name}</a></li>
                                            </ul>
                                        </c:if>
                                        <c:set var="counter" value="${counter+1}" />
                                    </c:forEach>
                                </li>
                                <li>
                                    <!-- click on "Shared folders" and the user should be redirected to the list of shared folders, 
                                    where they can share/hide/view them -->
                                    <h3><a href="rest/myfolders/shared">Shared folders</a></h3>
                                    <c:set var="counter" value="${0}" /> 
                                    <c:forEach items="${folders}" var="folder">
                                        <c:if test="${folder.shared.isEmpty()==false}" >
                                            <ul>
                                                <li><a href="rest/home/${counter}">${folder.name}</a> <button name="unshareButton" onclick="unshareFolder('${folder.name}')" >Unshare</button></li>
                                            </ul>
                                        </c:if>
                                        <c:set var="counter" value="${counter+1}" />
                                    </c:forEach>
                                </li>
                                <li>
                                    <!-- click on "Other available folders" and the user should be redirected to the list of 
                                    other available folders, that belong to other users -->
                                    <h3><a href="rest/myfolders/other">Other available folders</a></h3>
                                    <c:set var="counter" value="${0}" /> 
                                    <c:forEach items="${folders}" var="folder">
                                        <c:if test="${folder.user.login.equals(user.login)==false}" >
                                            <ul>
                                                <li><a href="rest/home/${counter}">${folder.name}</a></li>
                                            </ul>
                                        </c:if>
                                        <c:set var="counter" value="${counter+1}" />
                                    </c:forEach>
                                </li>
                            </ul>
                        </li>
                        <li id="search">
                            <h2><b class="text1"> Global search</b></h2>
                            <form action="rest/search" method="POST">
                                <fieldset>
                                    <input type="text" name="searchPhrase" value="" />
                                    <input type="submit" id="searchButton" value="Search" />
                                    <select id="searchby" name="searchby" >
                                        <option id="byfilename" value="Files" >Files</option>
                                        <option id="bytags" value="Tags" >Tags</option>
                                    </select>
                                </fieldset>
                            </form>
                        </li>
                    </ul>
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
