function btnClick(event) {
	let el = event.target
	el.classList.toggle("active")
	el.classList.toggle("disabled")
	if (el.classList.contains("active")) {
		el.innerText = "Active"
	} else {
		el.innerText = "Disabled"
	}
}


function drawButton() {
	let button = document.createElement("div")
	button.classList.add("btn1")
	button.classList.add("active")
	button.innerText = "Active"
	button.addEventListener("click", btnClick)
	document.querySelector("body").appendChild(button)
}
drawButton()