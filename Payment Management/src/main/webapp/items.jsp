<%@page import="com.Item"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="Views/bootstrap.min.css">
		<script src="Components/jquery-3.6.0.min.js"></script>
        <script src="Components/items.js"></script>
		<title>Payment Management</title>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col">
					<h1>Payment Management</h1>
					<form id="formItem" name="formItem" method="POST" action="items.jsp">
						Customer ID: 
						<input 
							id="cusID" 
							name="cusID" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						Payment Type: 
						<input 
							id="pType"
							name="pType" 
							type="text" 
							class="form-control form-control-sm"
						><br>

						Payment Amount: 
						<input 
							id="pAmount" 
							name="pAmount" 
							type="text" 
							class="form-control form-control-sm"
						><br>


						<input 
							id="btnSave" 
							name="btnSave" 
							type="button" 
							value="Save" 
							class="btn btn-primary"
						>

						<input type="hidden" name="hidItemIDSave" id="hidItemIDSave" value="">
					</form>
				
					<br>
					<div id="alertSuccess" class="alert alert-success">
						
					</div>
					<div id="alertError" class="alert alert-danger"></div>
					<br>
					<div id="divItemGrid">
					<%
						Item item = new Item(); 
						out.print(item.readItems());
					%>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>