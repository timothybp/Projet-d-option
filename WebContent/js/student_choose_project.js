function courseName_clicked(nom,responsable,beginDate,endDate, choosingDeadline, heures,poids,memberAmount){
	var content = "--Nom de cours: " + nom.replace("^", "'") + "<br/>" 
					+ "--Responsable: " + responsable + "<br/>" 
					+ "--Date de début: " + beginDate + "<br/>" 
					+ "--Date de fin: " + endDate + "<br/>" 
					+ "--Expiration de choix: " + choosingDeadline + "<br/>" 
					+ "--Heures totales: " + heures + "H<br/>" 
					+ "--Poids UE: " + poids + "<br/>" 
					+ "--Nombre de membres de groupe: " + memberAmount + " personnes en moyen";
	document.getElementById("u2").innerHTML=content;
}