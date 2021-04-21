async function saveNote() {
	let title = document.getElementById("title").value
	let message = document.getElementById("message").value
	let note = { title: title, message: message }
	//отправка пост запроса на сохранение этой заметки
	let url = "/api/notes"
	let response = await fetch(url, {
		method: "POST",
		headers: {
			'Content-Type': 'application/json;charset=utf-8'
		},
		body: JSON.stringify(note)
	})
	let result = await response.json()
	return result
}

async function fillNotes() {
	let url = "/api/notes"
	let response = await fetch(url)
	let notes = await response.json()
	let root = document.getElementById("notes")
	notes.forEach(n => {
		let elem = document.createElement("div")
		let title = document.createElement("div")
		title.innerText = n.title
		let message = document.createElement("div")
		message.innerText = n.message
		let infoRef = document.createElement("a")
		infoRef.href  = n.links[0].href
		infoRef.innerText = "Info"
		elem.appendChild(title)
		elem.appendChild(message)
		elem.appendChild(infoRef)
		elem.classList.add("item")
		root.appendChild(elem)
	})
}

fillNotes()
