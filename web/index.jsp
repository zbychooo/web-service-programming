<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
                <ul>
                    <li ><a href="#">my account</a></li>
                </ul>
            </div>
        </div>
        <div id="wrapper">
            <!-- start page -->
            <div id="page">           
                <!-- start content -->
                <div id="content">
                    <div id="fileactions">

                        <input type="button" id="uploadfilemenu" value="Upload" />
                        <form id="fileform" action="rest/systemService/createFolder" method="POST" >
                            <input type="text" name="foldername" value="Folder Name" />
                            <input type="submit" id="newfoldermenu" value="New folder" />
                        </form>
                        <input type="button" id="sharefoldermenu" value="Share folder" />  
                        &nbsp; Free space: 6.66 MB &nbsp;
                        <input type="text" id="searchinlistinput" />
                        <input type="button" id="searchinlisttrigger" value="search" />

                        <br/>
                        <p>
                            <strong>
                                Current folder: Folder #1
                            </strong>
                        </p>
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
                                    Size
                                </td>
                                <td>
                                    Added
                                </td>
                                <td>
                                    Tag
                                </td>
                                <td>
                                    Shared
                                </td>
                                <td colspan="2">

                                </td>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <a href="#">
                                    <td>
                                        <input type="checkbox" id="select" />                                    
                                    </td>
                                    <td>
                                        01 Stone Letter.mp3
                                    </td>
                                    <td>
                                        7.43 MB
                                    </td>
                                    <td>
                                        2012-10-10 03:57
                                    </td>
                                    <td>
                                        MUSIC
                                    </td>
                                    <td>
                                        <input type="checkbox" id="share" checked="true" disabled="true" /> 
                                    </td>
                                    <td>
                                        <input type="button" id="download" value="D" /> 
                                    </td>
                                    <td>
                                        <input type="button" id="delete" value="X" /> 
                                    </td>
                                </a>
                            </tr>
                        </tbody>
                    </table>
                    <br/>

                    <a href="register.jsp">REGISTER</a><br/>
                    <a href="rest/userService/login">LOGIN</a> <br/>
                    <a href="rest/systemService/createSpace">INIT PERSONAL SPACE</a> <br/>
                    
                    <a href="rest/systemService/getAvailableStorageSize">space size</a><br/>

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
                                    <h3><a href="#" >My folders </a></h3>
                                    <ul>
                                        <li class="activelink"><a href="#">Folder #1</a></li>
                                        <li><a href="#">Folder #2</a></li>
                                    </ul>
                                </li>
                                <li>
                                    <!-- click on "Shared folders" and the user should be redirected to the list of shared folders, 
                                    where they can share/hide/view them -->
                                    <h3><a href="#">Shared folders</a></h3>
                                    <ul>
                                        <li><a href="#">Folder #1</a></li>
                                    </ul>
                                </li>
                                <li><a href="#">Watch porn</a></li>
                            </ul>
                        </li>
                        <li id="search">
                            <h2><b class="text1"> Global search</b></h2>
                            <form method="get" action="">
                                <fieldset>
                                    <input type="text" id="s" name="s" value="" />
                                    <input type="submit" id="x" value="Search" />
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
