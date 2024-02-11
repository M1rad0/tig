<%@ page import="transfer.InfoFichier" %>
<% 
    InfoFichier[] files=(InfoFichier[])request.getAttribute("files");
    InfoFichier position= files[0];
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste de fichiers</title>
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        .file-list {
            list-style: none;
            padding: 0;
        }

        .file-item {
            border: 1px solid #ddd;
            padding: 10px;
            margin-bottom: 5px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .file-name {
            flex-grow: 1;
            margin-right: 10px;
        }

        .file-actions {
            display: flex;
            align-items: center;
        }

        .file-actions a {
            margin-left: 10px;
            text-decoration: none;
            color: #007BFF;
        }
    </style>
</head>
<body>
    
    <div class="container">
    <form action="ServletInsertFile">
        <!-- <input type="file" name="toSend"> -->
        <input type="text" name="toSend">
        <input type="hidden" name="path" value="<%= position.getRelativePath().replace('\\','*') %>">
        <!-- <input type="file" webkitdirectory="" directory="" /> -->
        <input type="submit" value="Confirmer">
    </form>
    <h2><%= position.getName() %></h2>
    <ul class="file-list">
        <% int i=0; for(InfoFichier fichier : files) { String path=fichier.getRelativePath().replace('\\','*'); %>
        <li class="file-item">
            <% if(i!=0) { 
                if(fichier.isDirectory()){ %>
                    <a href="dossier?path=<%= path  %>"><span class="file-name"><%= fichier.getName() %></span></a>
                    <div class="file-actions">
                        <a href="upload?path=<%= path %>&return=<%= position.getRelativePath().replace('\\','*') %>">Pull</a>
                    </div>
            <%  } 
                else{ %>
                    <a href="upload?path=<%= path  %>&return=<%= position.getRelativePath().replace('\\','*') %>"><span class="file-name"><%= fichier.getName() %></span></a>
            <% } %>
            <% } %>
        </li>
        <% i++; } %>
        <!-- Ajoutez d'autres éléments de liste en fonction de vos besoins -->
    </ul>    
    </div>
</body>
</html>
