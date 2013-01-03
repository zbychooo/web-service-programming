<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Hamster.xxx - Best File Storage service</title>
        <link href="default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <div id="header" >
            <div id="logo">
                <h1><a href="#">Hamster.xxx</a></h1>
                <h2>CSS Absolutely NOT designed by FreeCSSTemplates AND BRUTALLY MODIFIED BY ME WITHOUT GIVING THEM ANY CREDITS</h2>
            </div>
            <div id="menu">
                <ul id="accountmenu">
                    <li >
                        <a href="#">${user.username}</a>
                        <ul>
                            <li><a href="#">Account details</a></li>
                            <li><a href="#">Logout</a></li>
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
                        <input type="button" id="uploadfilemenu" value="Upload" 
                               onclick="window.location.href='upload.jsp'"/>
                        <form id="fileform" action="rest/systemService/createFolder" method="POST" >
                            <input type="text" name="foldername" value="Folder Name" />
                            <input type="submit" id="newfoldermenu" value="New folder" />
                        </form>
                        <input type="button" id="sharefoldermenu" value="Share folder" />
                        &nbsp; Free space: <c:out value="${remainingSpace}" /> MB &nbsp;
                        <input type="text" id="searchinlistinput" />
                        <input type="button" id="searchinlisttrigger" value="search" />
                        <br/>
<!--                        <p><strong>Current folder: {folders.get(currentFolderIndex).name}</strong></p>-->
                    </div>
                    <br/>
                    <table>
                        <thead>
                            <tr>
                                <td>
                                    <input type="checkbox" id="selectall" />
                                </td>
                                <td>
                                    Name
                                </td>
                                <td>
                                    Added
                                </td>
                                <td>
                                    Shared
                                </td>
                                <td>
                                    Actions
                                </td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${folders.isEmpty()==false}" >
                            <c:forEach items="${folders}" var="folder">
                                <tr>
                                    <td>
                                        <input type="checkbox" id="select" /> 
                                    </td>
                                    <td>
                                        <c:out value="${folder.name}" />
                                    </td>
                                    <td>
                                        <c:out value="${folder.dateStamp}" />
                                    </td>
                                    <td>
                                        <input type="checkbox" id="share" checked="${folder.shared.isEmpty()!=true}" disabled="true" /> 
                                    </td>
                                    <td>
                                        <input type="button" id="delete" value="X" /> 
                                    </td>
                                </tr>
                            </c:forEach>
                            </c:if>
                            <c:if test="${folders.isEmpty()==true}">
                                <tr>
                                    <td colspan="5">
                                        There are no folders in your account.
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
                                        <ul>
                                            <li><a href="rest/home/${counter}">${folder.name}</a></li>
                                        </ul>
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
                            </ul>
			</li>
			<li id="search">
                            <h2><b class="text1"> Global search</b></h2>
                            <form method="get" action="">
				<fieldset>
                                    <input type="text" id="s" name="s" value="" />
                                    <input type="submit" id="x" value="Search" />
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
            <p id="legal">(c) 2012-2013 Hamster.xxx. Designed by Sebastian Pasiecznik and Zbyszko Natkański.</p>
        </div>
    </body>
</html>
