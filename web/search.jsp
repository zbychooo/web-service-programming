<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Hamster.net - Best File Storage service</title>
        <link href="default.css" rel="stylesheet" type="text/css" />
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
                    <br/>
                    <table class="contentTable" id="searchTable">
                        <thead>
                            <tr>
                                <td></td>
                                <td>
                                    Name
                                </td>
                                <td>
                                    File size
                                </td>
                                <td>
                                    Added
                                </td>
                                <td>
                                    Tag
                                </td>
                                <td>
                                    Owner
                                </td>
                                <td>
                                    Actions
                                </td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="counter" value="${0}" /> 
                            <c:if test="${searchResults.isEmpty()==false}" >
                                <c:forEach items="${searchResults}" var="file">
                                    <tr>
                                        <td>
                                            <c:out value="${counter+1}" />
                                        </td>
                                        <td >
                                            <c:out value="${file.fileName}" />
                                        </td>
                                        <td>
                                            <c:out value="${file.fileSize}" />
                                        </td>
                                        <td>
                                            <c:out value="${file.dateStamp}" />
                                        </td>
                                        <td>
                                            <c:out value="${file.tagName}" />
                                        </td>
                                        <td>
                                            <c:out value="${file.owner.login}" />
                                        </td>
                                        <td>
                                            <input type="button" id="download" value="D" 
                                                   onclick="document.location = 'rest/download/${file.owner.login}/${file.folderName}/${file.fileName}';" /> 
                                        </td>
                                    </tr>
                                    <c:set var="counter" value="${counter+1}" />
                                </c:forEach>
                            </c:if>
                            <c:if test="${searchResults.isEmpty()==true}">
                                <tr>
                                    <td colspan="5">
                                        There are no files found.
                                    </td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
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
