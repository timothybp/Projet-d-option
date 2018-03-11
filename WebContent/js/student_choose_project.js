function courseName_clicked(jsonStr){
	var newJsonStr = jsonStr.split("$").join("\"");
	var jsonObj = JSON.parse(newJsonStr);
	var memberAmount="";
	if(parseInt(jsonObj.memberAmount) != 1) {
		memberAmount = (parseInt(jsonObj.memberAmount) - 1).toString() + "-" + jsonObj.memberAmount + " personnes";
	}
	else {
		memberAmount = jsonObj.memberAmount + " personne";
	}
	var content = "--Nom de cours: " + jsonObj.nom.split("^").join("'") + "<br/>" 
					+ "--Responsable: " + jsonObj.responsable + "<br/>" 
					+ "--Date de début: " + jsonObj.beginDate + "<br/>" 
					+ "--Date de fin: " + jsonObj.endDate + "<br/>" 
					+ "--Expiration de choix: " + jsonObj.choosingDeadline + "<br/>" 
					+ "--Heures totales: " + jsonObj.heures + "H<br/>" 
					+ "--Poids UE: " + jsonObj.poids + "<br/>" 
					+ "--Nombre de membres de groupe: " + memberAmount;
	document.getElementById("u2").innerHTML=content;
	document.getElementById("courseName").value=jsonObj.nom.replace("^", "'");
	var tableCell = "<table class=\"gridtable\" style=\"width: 100%\">" +
						"<tr class=\"header\">" +
							"<th width=\"50\">Numéro</th>" +
							"<th width=\"80\">Id</th>" +
							"<th width=\"200\">Sujet</th>" +
							"<th>Description</th>" +
							"<th width=\"120\">Encadrant.e.s</th>" +
							"<th width=\"70\">Entreprise</th></tr>";
	for(var i = 0; i < jsonObj.projects.length; i++) {
		if (i%2 == 0)
			{tableCell += "<tr>";}
		else
			{tableCell += "<tr class=\"pair\">";}
		tableCell += "<td>" + (i+1).toString() + "</td>" +
					 "<td>" + jsonObj.projects[i].idProject + "</td>" +
					 "<td>" + jsonObj.projects[i].subject + "</td>" +
					 "<td>" + jsonObj.projects[i].description + "</td>" +
					 "<td>" + jsonObj.projects[i].supervisors + "</td>" +
					 "<td>" + jsonObj.projects[i].enterprise + "</td>" +
					 "</tr>";
	}
	document.getElementById("u3").innerHTML=tableCell;
}