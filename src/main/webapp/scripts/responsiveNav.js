"use strict";

const toggle = document.getElementById("toggle");
const nav = document.getElementById("nav");

toggle.addEventListener("click", onToggleClick);

function onToggleClick() {
	if (nav.classList.contains("toggle-off")) {
		nav.classList.remove("toggle-off");
		nav.classList.add("toggle-on");
	}
	else {
		nav.classList.remove("toggle-on");
		nav.classList.add("toggle-off");
	}
}
