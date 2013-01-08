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
            function shareFolder()
            {
                //get selected rows (first column checked
                //for each of them invoke the shareFolder method
                //refresh the site, so UI would include changes
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
                            <input type="text" name="folderName" value="Folder Name" />
                            <input type="submit" id="newfoldermenu" value="New folder" />
                        </form>
                        <input type="submit" id="sharefoldermenu" value="Share folder" onclick="shareFolder()" />
                        &nbsp; Free space: <c:out value="${remainingSpace}" /> MB &nbsp;
                        <br/>
                    </div>
                    <br/>
                    <table class="contentTable">
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
                                    Shared
                                </td>
                                <td>
                                    Actions
                                </td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="counter" value="${0}" /> 
                            <c:if test="${folders.isEmpty()==false}" >
                            <c:forEach items="${foldersdm}" var="folder">
                                <tr>
                                    <td>
                                        <input type="checkbox" id="${counter}" onclick="select(${counter})" /> 
                                    </td>
                                    <td onclick="document.location = 'rest/home/${counter}';" class="myfoldersname">
                                        <c:out value="${folder.name}" />
                                    </td>
                                    <td onclick="document.location = 'rest/home/${counter}';">
                                        <c:out value="${folder.dateStamp}" />
                                    </td>
                                    <td onclick="document.location = 'rest/home/${counter}';">
                                        <input type="checkbox" id="share" checked="${folder.shared.isEmpty()==false}" disabled="true" /> 
                                    </td>
                                    <td >
                                        <input type="button" id="delete" value="X" /> 
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
                    <a href="upload.jsp">upload</a>
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
