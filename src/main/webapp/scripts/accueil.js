"use strict";

const radioAchat = document.getElementById("achat");
const radioVente = document.getElementById("vente");
const listeCkboxAchat = document.querySelectorAll(".achats");
const listeCkboxVente = document.querySelectorAll(".ventes");
const checked = document.querySelectorAll(".checked");
const unchecked = document.querySelectorAll(".unchecked");

if (checked.length > 0) {
	checked.forEach((elmt) => {
		elmt.setAttribute("checked", "");
		if (elmt.value == "achat") {
			enable(listeCkboxAchat);
			disable(listeCkboxVente);
		}
		else if (elmt.value == "vente") {
			enable(listeCkboxVente);
			disable(listeCkboxAchat);
			
		}
	});
}
if (unchecked.length > 0) {
	const target = unchecked[0];
	target.removeAttribute("checked");
}

if (radioAchat && radioVente) {
	radioAchat.addEventListener("change", onRadioChange);
	radioVente.addEventListener("change", onRadioChange);
}

function onRadioChange(e) {
	if (e.target.value == "achat") {
		enable(listeCkboxAchat);
		disable(listeCkboxVente);
	} 
	else {
		enable(listeCkboxVente);
		disable(listeCkboxAchat);
	}
}

function disable(listeCkbox) {
	listeCkbox.forEach((box) => {
		box.setAttribute("disabled", "");
		box.parentElement.classList.add("disabled");
	});
}

function enable(listeCkbox) {
	listeCkbox.forEach((box) => {
		box.removeAttribute("disabled");
		box.parentElement.classList.remove("disabled");
	});
}
