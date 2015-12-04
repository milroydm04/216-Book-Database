<%-- 
    Document   : createRecord
    Created on : Nov 20, 2015, 5:19:26 PM
    Author     : Dylan Lozo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Video Game Library</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="mystyle.css">
    </head>
    <body>
        <h1><a href="home.html">216 Book Library</a></h1>
        <h2>Create Book Entry</h2>
        <form action="create" method="get">

            <!-- Used the new HTML5 email type to force the user to enter a name.-->
            Book Name: <input type="name" name="bookName" size="30"  
                          placeholder="Enter Book's name here" required>
            <br><br>
            
            Book Author: <input type="name" name="bookAuthor" size="30"  
                          placeholder="Enter Author's name here" required>
            <br><br>

            Book Section: <br>
            <textarea  name="bookSection" maxlength="200" cols="60" rows="3"></textarea>
            <br><br>
            
            ISBN: <input type="name" name="iSBN" size="30"  
                          placeholder="Book's ISBN number" required>
            <br><br>
            
            <!-- Used the new HTML5 number type to force the user to enter a number.-->
            Copyright Year: <input type="number" name="copyrightYear" value='1' min="1" required>
            <br><br>

            <input type="hidden" name="action" value="createRecord">

            <input type="submit" name="submit" value="Submit">
            <br><br>
        </form>
    </body>
</html>

