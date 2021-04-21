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

