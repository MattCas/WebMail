<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>sender</title>
</head>
<body>
	<form action="SenderServlet" method="get">
		Recipient: <input name="recipient"><br>
		Subject: <input name="subject"><br>
		Body: <textarea name="body" rows=8 cols=40></textarea><br>
		<input type ="submit" value = "Send">
	</form>
	<form action="LogoutServlet" method="get">
		<input type ="submit" name ="logout" value = "Logout">
	</form>
</body>
</html>