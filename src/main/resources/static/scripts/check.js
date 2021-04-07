function checkForm() {
	let password = document.getElementById("psw").value
	let confirmPassword = document.getElementById("confirm-psw").value
	if (!password.match("[A-Za-z0-9]{8,}")) {
		alert("Password should contain at least 8 letters or digits!")
		return false
	}
	if (password == confirmPassword) {
		console.log("Password confirmed!")
		return true
	} else {
		alert("Password is not confirmed!")
		return false
	}
}