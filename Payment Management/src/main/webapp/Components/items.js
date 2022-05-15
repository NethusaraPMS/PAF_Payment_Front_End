$(document).on("click", "#btnSave", function(event)
{ 
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide(); 
// Form validation-------------------
var status = validateItemForm(); 
if (status != true) 
 { 
 $("#alertError").text(status); 
 $("#alertError").show(); 
 return; 
 } 
// If valid------------------------
var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT"; 
 $.ajax( 
 { 
 url : "ItemsAPI", 
 type : type, 
 data : $("#formItem").serialize(), 
 dataType : "text", 
 complete : function(response, status) 
 { 
 onItemSaveComplete(response.responseText, status); 
 } 
 }); 
});

function onItemSaveComplete(response, status)
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully saved."); 
 $("#alertSuccess").show(); 
 $("#divItemsGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while saving."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while saving.."); 
 $("#alertError").show(); 
 }
$("#hidItemIDSave").val(""); 
$("#formItem")[0].reset(); 
}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event)
		{ 
		$("#hidItemIDSave").val($(this).data("pID")); 
		 $("#cusID").val($(this).closest("tr").find('td:eq(0)').text()); 
		 $("#pType").val($(this).closest("tr").find('td:eq(1)').text()); 
		 $("#pAmount").val($(this).closest("tr").find('td:eq(2)').text()); 
		 //$("#itemDesc").val($(this).closest("tr").find('td:eq(3)').text()); 
		});




$(document).on("click", ".btnRemove", function(event)
		{ 
		 $.ajax( 
		 { 
		 url : "ItemsAPI", 
		 type : "DELETE", 
		 data : "pID=" + $(this).data("pID"),
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 onItemDeleteComplete(response.responseText, status); 
		 } 
		 }); 
		});
		
function onItemDeleteComplete(response, status)
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully deleted."); 
 $("#alertSuccess").show(); 
 $("#divItemsGrid").html(resultSet.data); 
 } else if (resultSet.status.trim() == "error") 
 { 
 $("#alertError").text(resultSet.data); 
 $("#alertError").show(); 
 } 
 } else if (status == "error") 
 { 
 $("#alertError").text("Error while deleting."); 
 $("#alertError").show(); 
 } else
 { 
 $("#alertError").text("Unknown error while deleting.."); 
 $("#alertError").show(); 
 } 
}


// CLIENT-MODEL================================================================
function validateItemForm()
{
	// CODE
	if ($("#cusID").val().trim() == "")
	{
	return "Insert cusID.";
	}
	// NAME
	if ($("#pType").val().trim() == "")
	{
	return "Insert pType.";
}

// PRICE-------------------------------
if ($("#pAmount").val().trim() == ""){
	return "Insert pAmount.";
}
		// is numerical value
		var tmpPrice = $("#pAmount").val().trim();
		if (!$.isNumeric(tmpPrice))
	{
	return "Insert a numerical value for pAmount.";
	}
		
// convert to decimal price
$("#pAmount").val(parseFloat(tmpPrice).toFixed(2));


	return true;
}