async function checkForm() {
	let url = "/api/users"
	let response = await fetch(url)
	let users
	if (response.ok) {
		users = await response.json()
	} else {
		console.log(response.status)
	}

	//проверить, что username и email свободен
	let username = document.getElementById("username").value
	let email = document.getElementById("email").value
	for (user of users) {
		if (user.username == username) {
			alert("Such username exists!")
			return false
		}
		if (user.email == email) {
			alert("Such email exists!")
			return false
		}
	}

	let password = document.getElementById("psw").value
	let confirmPassword = document.getElementById("confirm-psw").value
	if (!password.match("[A-Za-z0-9]{8,}")) {
		alert("Password should contain at least 8 letters or digits!")
		return false
	}
	if (password != confirmPassword) {
		alert("Password is not confirmed!")
		return false
	}
	return true
}

async function submitForm() {
	let form = document.getElementById("signup_form")

	//дождаться проверки формы и отправить форму вручную
	if (await checkForm()) {
		//отправлю форму
		form.submit()
	}
}