<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Etudiant</title>
	<link href="css/header_footer.css" rel="stylesheet" type="text/css" />
</head>

<body>
    <div class="wrap">
        <div class="min">
            <header>
            	<img src="images/logo.png" height="100%" width="15%" />
            	<font size="6" color="white" style="position:relative;top:-20px;">Le système de choisir les projets</font>
            	<input type="button" value="Se déconnecter" onclick="window.location='/DistributionDeProjets/login.jsp'" style="position:relative; top:23px; float:right;" />
            	<img src="<%=(String)request.getAttribute("photoPath")%>" height="40" width="40" 
            			style="position:relative; float:right; margin-right:200px;top:12px;"/>
            	<font size="3" color="white" style="position:relative;float:right; margin-right:-150px; top:23px;">
            		<%=(String)request.getAttribute("name") %>&nbsp
            		<%=(String)request.getAttribute("surname") %>&nbsp
            		[<%=(String)request.getAttribute("dept+grade") %>]
            	</font>
            </header>
            
            <section class="content">
                <div id="u0" 
                	class="titleLable" 
                	style="position:relative;top:30px;text-align:center;">
                	<h1>Choisir votre projet</h1>
        		</div>
        	
            </section>
        </div>
    </div>
    <footer class="footer"><p>© 2018 Développé par BI Peng &amp; ZENG Kai</p></footer>
</body>
</html>