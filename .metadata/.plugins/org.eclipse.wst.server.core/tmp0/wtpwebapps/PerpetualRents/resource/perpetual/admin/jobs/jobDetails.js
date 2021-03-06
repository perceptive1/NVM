  
function addJobDetails(assignModelId){
	 $.ajax({
 		url:contextPath+"/admin/loadJobDetail.htm",
		data: {assignModelId : assignModelId},
 		method: "Post",
 		 dataType : "json", 
 		success:function(response){
 			if(response.success == 'true' || response.success == true){
 				$("#headingId").html("Job Details");
 				var dataObj = response.data;
 				setJobsData(dataObj);
 			}
 	  },
 	  error: function(error){
				console.log(error);
				alert(error);
	  }});
}

	function setJobsData(data){
		var landlord = data.landlord;
		var property = data.property;
		var contractor = data.contractor;
		var assignModel = data.assignModel;
		var uploadedDocuments = data.uploadedDocuments;
		if(assignModel){
			$("#jobName").html(assignModel.jobName);
			$("#jobDescription").html(assignModel.jobDescription);
			$("#estimatedCost").html(assignModel.estimatedCost);
			$("#assignedDate").html(assignModel.createdOn);
			$("#estimatedCompletionDate").html(assignModel.estimatedCompletionDate);
			$("#statusStr").html(assignModel.statusStr);
			$("#remarks").html(assignModel.remarks);
		}
		
		if(landlord){
			$("#firstName").html(landlord.firstName);
			$("#lastName").html(landlord.lastName);
			$("#primaryEmailId").html(landlord.primaryEmailId);
			$("#secondaryEmailId").html(landlord.secondaryEmailId);
			$("#primaryPhoneNumber").html(landlord.primaryPhoneNumber);
			$("#secondaryPhoneNumber").html(landlord.secondaryPhoneNumber);
			$("#lUniqueReference").html(landlord.uniqueReference);
			$("#lCreatedOn").html(landlord.createdOn);
		}
		if(property){
			var htmlStr = '<div style="width: 25%; margin-top: 14px !important;" id="progressBar'+property.id+'" class="round-pink1" ><div></div></div>';
			$("#statusId").html(htmlStr);
			$("#contactPerson").html(property.contactPerson);
			$("#pUniqueReference").html(property.uniqueReference);
			$("#pCreatedOn").html(property.createdOn);
			var address = property.address;
			if(address){
				$("#addressLine1").html(address.addressLine1);
				$("#addressLine2").html(address.addressLine2);
				$("#city").html(address.city);
				$("#county").html(address.county);
				$("#postCode").html(address.postCode);
			}
			setProgress(property);
		}
		if(contractor){
			$("#companyName").html(contractor.companyName);
			$("#cUniqueReference").html(contractor.uniqueReference);
			$("#status").html(contractor.status);
			$("#lUniqueReference").html(contractor.uniqueReference);
			$("#cCreatedOn").html(contractor.createdOn);
			var contactDetails = contractor.contactDetails;
			if(contactDetails){
				$("#phoneNumber").html(contactDetails.phoneNumber);
				$("#emailId").html(contactDetails.emailId);
			}
		}
		if(uploadedDocuments){
			$("#alertErrorId2").hide();
			var i = 0;
			var html = "";
			$.each(uploadedDocuments, function(index, value){
				 if(i == 0){
	 					html += formatHtml1();
	 					html += formatHtml(value);
	 				 } else
	 					 html += formatHtml(value);
	 				 i++;
			});
			$("#documentId").empty().html(html);
		}else{
			$("#alertErrorId2").show();
		}
		
		
	}
	
	function setProgress(property){
	      progressBar(property.statusPercentage, $('#progressBar'+property.id));
	}

  $('#jobDetailsBack').click(function(){
		$("#headingId").html("All Assigned Jobs");
		  $.ajax({
		      url: contextPath+"/admin/allJobsTable.htm",
		      type: "post",
		      success: function(data){
		    	  $("#containerId").html(data);
		      },
		      error:function(){
		          $("#containerId").html('There is error while submit');
		      }
		  });
	});
  
  function formatHtml1(){
		 var htmlStr = '<tr style="width: 100%">'
			 		 + '<th style="width:20%">Name</th>'
	                 + '<th style="width:20%">Uploaded On</th>'
	                 + '<th style="width:20%">Status</th>'
	                 + '<th style="width:20%">Document Type</th>'
	                 + '<th style="width:20%">Action</th>'
	                 + '</tr>';
		 
		 return htmlStr;
	 	}
	 
	 function formatHtml(documentObj){
		 var file = documentObj.file;
		 var htmlStr = '<tr>'
			 		 + '<td>' + file.fileName + '</td>'
		             + '<td>' + documentObj.createdOn + '</td>'
		             + ' <td>' + documentObj.documentTypeStr + '</td>'
		             + ' <td>' + documentObj.statusStr + '</td>'
		             + '<td>'
		             + '<a href="javascript:downloadFile('+file.id+')" style="margin-top: 5px;"><img style="vertical-align: text-top!important;" src="resource/images/downloadIcon.png"></a>'
		             + '&nbsp;&nbsp;&nbsp;&nbsp;'
		             + '<a id = "removehrefId" href="#" onclick ="div_show(removehrefId, rejectPopUp);" style="margin-top: 5px;display: none;"><img src="resource/images/removeIcon.png"></a>'
					 + '</td>'
					 + '</tr>';
					 
		return htmlStr;
	 }
	 
	 function downloadFile(fileId){
		 window.location.href = contextPath+"/download/uploadedDocuments.htm?fileId="+fileId; 
	 }
 