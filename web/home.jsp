<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Hamster.net - Best File Storage service</title>
        <link href="default.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript">
            function select(id)
            {
                if(document.getElementById(id).checked)
                    document.getElementById(id).checked=true;
                else
                    document.getElementById(id).checked=false;
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
                        <form id="fileform" action="rest/systemService/uploadFile" method="POST" enctype="multipart/form-data">
                            <input type="file" name="file" /> 
                            <input type="text" name="tag" size="50" value="" placeholder="tag" /> 
                            <input type="hidden" name="path" value="${folders.get(currentFolderIndex).name}" />
                            <input type="submit" value="upload" />
                        </form>
                        <input type="button" id="sharefoldermenu" value="Share this folder" />
                        &nbsp; Free space: <c:out value="${remainingSpace}" /> MB &nbsp;
                        <br/>
                        <p><strong>Current folder: ${folders.get(currentFolderIndex).name}</strong></p>
                    </div>
                    <br/>
                    <table class="contentTable">
                        <thead>
                            <tr>
                                <td>
                                    <input type="checkbox" id="selectall" />
                                </td>
                                <td class="homefilenamecell" >
                                    Name
                                </td>
                                <td>
                                    Size
                                </td>
                                <td>
                                    Added
                                </td>
                                <td>
                                    Tags
                                </td>
                                <td colspan="2">
                                    Actions
                                </td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${folders.get(currentFolderIndex).files.isEmpty()==false}" >
                            <c:forEach items="${folders.get(currentFolderIndex).files}" var="file">
                                <tr>
                                    <td>
                                        <input type="checkbox" id="select" /> 
                                    </td>
                                    <td class="homefilenamecell">
                                        <c:out value="${file.fileName}" />
                                    </td>
                                    <td>
                                        <c:out value="${file.fileSize/1000000}" /> MB
                                    </td>
                                    <td>
                                        <c:out value="${file.dateStamp}" />
                                    </td>
                                    <td>
                                        <c:out value="${file.tagName}" />
                                    </td>
                                    <td>
                                        <input type="button" id="download" value="D" onclick="document.location = 'rest/download/${user.login}/${file.fileName}';" /> 
                                    </td>
                                    <td>
                                        <input type="button" id="delete" value="X" /> 
                                    </td>
                                </tr>
                            </c:forEach>
                            </c:if>
                            <c:if test="${folders.get(currentFolderIndex).files.isEmpty()==true}">
                                <tr>
                                    <td colspan="5">
                                        There are no files in this folder.
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                    <br/>        
                    <a href="upload.jsp">upload</a> |                    
                    <a href="rest/systemService/getRemainingStorageSize">get total space size</a> |
                    <a href="rest/userService/getUserLogin">get User login</a>
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
                                                <li><a href="rest/home/${counter}">${folder.name}</a></li>
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
                            <form action="rest/systemService/search" method="POST">
				<fieldset>
                                    <input type="text" name="searchPhrase" value="" />
                                    <input type="submit" id="searchButton" value="Search" />
                                    <select id="searchby" >
                                        <option id="byusers" value="Users" >Users</option>
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
