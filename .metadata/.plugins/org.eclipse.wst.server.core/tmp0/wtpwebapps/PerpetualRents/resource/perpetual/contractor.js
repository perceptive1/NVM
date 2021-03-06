$(document).ready(function(){
	 $("#contractorSubmitId").click(function(event){
		 var cities = $("#cityServiced").val();
		 	if(cities == null){
		 		$("#cityErrorId").show();
		 		return false;
		 	}
		 var functionalities = $("#functionality").val();
		 	if(functionalities == null){
		 		$("#functionalityErrorId").show();
		 		return false;
		 	}
		 if($("#fRegisterContractor").valid()){
		  var formData = form2object('fRegisterContractor', '.', true,function(node)
		          {
		                if (node.id && node.id.match(/callbackTest/))
		                {
		                        return { name: node.id, value: node.innerHTML };
		                }
		        });
		  var postData = JSON.stringify(formData, null, '\t');
			$.ajax({
				url : contextPath+"/register/createContractor.htm",
			    type: "POST",
			    data : postData,
			    dataType : "json",
			    contentType: 'application/json',
			    success:function(response, textStatus, jqXHR){
			    	if(response.success == true || response.success == 'true'){
			    		$.get(contextPath+"/register/successfullContractorRegisteration.htm", function(data) {
				    		 $("#containerId").html(data);
				    		 $('#alertSuccessId').show(200);
				    		 $("#successMessageId").html("You have been successfully registered. Please do login.");
				    	 });
			    	}else{
			    		$("#commonMessageErrorId").show();
			    		$("#commonMessageErrorId").focus();
			    		$("#commonMessageErrorId").html(response.message);
			    		setTimeout(function(){
			    			$("#commonMessageErrorId").fadeOut(4000);
			    		}, 1000);
			    	}
			    },
			    error: function(jqXHR, textStatus, errorThrown){
			        alert("fail");  
			    }
			});
			event.preventDefault();
			event.stopImmediatePropagation();
		 }
		 else{
			 
		 }
	  });
  
  $("#contractorUpdate").click(function(event){
	  if($("#fUpdateContractor").valid() == true){
	  var formData = form2object('fUpdateContractor', '.', true,function(node)
	          {
	                if (node.id && node.id.match(/callbackTest/))
	                {
	                        return { name: node.id, value: node.innerHTML };
	                }
	        });
	  var postData = JSON.stringify(formData, null, '\t');
	  console.log('data : ' + postData);
		$.ajax(
		{
		    url : contextPath+"/contractor/updateProfile.htm",
		    type: "POST",
		    data : postData,
		    dataType : "json",
		    contentType: 'application/json',
		    success:function(data, textStatus, jqXHR) 
		    {
		       if(data.success == true){
		    	   $.get(contextPath+"/contractor/redirectProfileView.htm", function(data) {
			    		 $("#containerId").html(data);
			    		 $('#alertSuccessId').show(200);
			    		 $("#successMessageId").html("Your Profile has been successfully updated.");
			    		 setTimeout(function() {   
			    			 $('#alertSuccessId').hide(100);
			    		 }, 2500);
			    	 });
		       }
		    },
		    error: function(jqXHR, textStatus, errorThrown){
		        alert("fail");  
		    }
		});
		event.preventDefault();
		event.stopImmediatePropagation();
	  }
  });
  
  	$("#generalInfoSaveBtn" ).click(function(event) {
  		 if($("#fContractorgeneralInformationId").valid() == true){
  			var formData = form2object('fContractorgeneralInformationId', '.', true,function(node)
  	  	          {
  	  	                if (node.id && node.id.match(/callbackTest/))
  	  	                {
  	  	                        return { name: node.id, value: node.innerHTML };
  	  	                }
  	  	        });
  			console.log('formData : ' + formData);
  	  	  var postData = JSON.stringify(formData, null, '\t');
  	  	  var fun = formData.functionality;
	  	  	if (typeof fun === "undefined" || fun.length == 0) {
	  	  	$("#functionalityLabel").show();
	  	  	setTimeout(function(){
				 $("#functionalityLabel").fadeOut(4000);
			 }, 4000);
			 return false;
	  	  	}
	  	  	var cities = formData.cities;
	  	  if (typeof cities === "undefined" || cities.length == 0) {
	  		$("#citiesLabel").show();
	  		setTimeout(function(){
				 $("#citiesLabel").fadeOut(4000);
			 }, 4000);
				 return false;
		  	}
	  	  console.log('postData : ' + postData);
	  	
  	  		$.ajax({
  	  		    url : contextPath+"/register/contractorGeneralInformation.htm",
  	  		    type: "POST",
  	  		    data : postData,
  	  		    dataType : "json",
  	  		    contentType: 'application/json',
  	  		    success:function(data, textStatus, jqXHR){
  	  		       if(data.success == true || data.success == 'true'){
  	  		    	   window.location.href = contextPath+"/register/redirectcontractorRegisterationStep2.htm";
  	  		       }else{
			    		$("#commonMessageErrorId").show();
			    		$("#commonMessageErrorId").html(data.message);
			    		//$("#commonMessageErrorId").focus();
			    		$(window).scrollTop($('#commonMessageErrorId').offset().top);
			    		setTimeout(function(){
			    			$("#commonMessageErrorId").fadeOut(4000);
			    		}, 1000);
			    	
  	  		       }
  	  		    },
  	  		    error: function(jqXHR, textStatus, errorThrown){
  	  		        alert("fail");  
  	  		    }
  	  		});
  	  		event.stopImmediatePropagation();
  		 }
	});
  	
  	$('#usernameId').blur(function(){
		validateUser();
	});
	
  	
  	$("#contractorRegStep2Btn" ).click(function(event) {
  		 if($("#fContractorRegStep2").valid() == true){
  			 var formData = form2object('fContractorRegStep2', '.', true,function(node)
  	    	          {
  	    	                if (node.id && node.id.match(/callbackTest/))
  	    	                {
  	    	                        return { name: node.id, value: node.innerHTML };
  	    	                }
  	    	        });
  			 var cType = formData.companyTypeId;
  			 if (typeof cType === "undefined" || cType.length == 0) {
 				$("#companyTypeLabel").show();
 				 return false;
 		  	}else{
 		  		$("#companyTypeLabel").hide();
 		  	}
  	    	  var postData = JSON.stringify(formData, null, '\t');
  	    		$.ajax({
  	    		    url : contextPath+"/register/contractorRegisterationStep2.htm",
  	    		    type: "POST",
  	    		    data : postData,
  	    		    dataType : "json",
  	    		    contentType: 'application/json',
  	    		    success:function(data, textStatus, jqXHR){
  	    		       if(data.success == true){
  	    		    	   window.location.href = contextPath+"/register/redirectcontractorRegisterationStep3.htm";
  	    		       }
  	    		    },
  	    		    error: function(jqXHR, textStatus, errorThrown){
  	    		        alert("fail");  
  	    		    }
  	    		});
  	    		event.stopImmediatePropagation();
  		 }
  	});
  	
	$("#contractorRegStep3Btn1" ).click(function(event) {
		 var otherFormData = $("#parentDiv").toObject({skipEmpty:true});
	   	   var postData = JSON.stringify(otherFormData);
	   	   console.log('postData : ' + postData);
	   	   var costSheet = otherFormData.costSheet;
	   	   var writtenHealthPolicyResponse = otherFormData.writtenHealthPolicy;
	   	   if(writtenHealthPolicyResponse == 'true' || writtenHealthPolicyResponse == true){
	   		 var wFile = $('#writtenFile').val();
			 if(wFile == "" ){
				 $("#writtenHealthPolicyFileIDLbl").show();
				 $("#writtenHealthPolicyFileIDLbl").html("Please select a file.");
				 return false;
			 }else{
					$("#writtenHealthPolicyFileIDLbl").hide();
				}
	  		    
			 var maxSize = 10000000;
	  		    var fsize = $('#writtenFile')[0].files[0].size;
				 if((fsize > maxSize) == 'true' || (fsize > maxSize) == true){
					$("#writtenHealthPolicyFileIDLbl").show();
					$("#writtenHealthPolicyFileIDLbl").html("Max size is 10Mb");
					$('#writtenHealthPolicyFile').val(null);
					return false;
				}else{
					$("#writtenHealthPolicyFileIDLbl").hide();
				}
	   	   }
	   	   
	   	 var equalityPolicyResponse = otherFormData.equalityPolicy;
	   	   if(equalityPolicyResponse == 'true' || equalityPolicyResponse == true){
	   		 var eFile = $('#equalityFile').val();
			 if(eFile == "" ){
				 $("#equalityPolicyFileIDLbl").show();
				 $("#equalityPolicyFileIDLbl").html("Please select a file.");
				 return false;
			 }else{
					$("#equalityPolicyFileIDLbl").hide();
				}
	  		    
			 var maxSize = 10000000;
	  		    var eSize = $('#equalityFile')[0].files[0].size;
				 if((eSize > maxSize) == 'true' || (eSize > maxSize) == true){
					$("#equalityPolicyFileIDLbl").show();
					$("#equalityPolicyFileIDLbl").html("Max size is 10Mb");
					$('#equalityFile').val(null);
					return false;
				}else{
					$("#writtenHealthPolicyFileIDLbl").hide();
				}
	   	   }
	   	   
	   	   var isComplete;
	   	   $.each(costSheet, function(id, value) {
			   		var funId = value.functionalityId;
			   		var callOutMonToFri = value.callOutMonToFri;
			   		var callOutWeekends = value.callOutWeekends;
			   		var hourlyRate = value.hourlyRate;
			   		var hourlyRateWeekends = value.hourlyRateWeekends;
			   	    isComplete = false;
	  	        	 if(typeof funId == "undefined"){
	  	        		 return isComplete;
	  	        	 }
	  	        	if(typeof callOutMonToFri == "undefined"){
	  	        		return isComplete;
	  	        	 }
	  	  	        if(typeof callOutWeekends == "undefined"){
	  	        		return isComplete;
	  	        	 }
		  	  	    if(typeof hourlyRate == "undefined"){
	  	        		return isComplete;
	  	        	 }
			  	  	if(typeof hourlyRateWeekends == "undefined"){
	  	        		return isComplete;
	  	        	 }
			  	  isComplete = true;
  	          });
	   	 if(isComplete == false){
	   		 $("#costLabel").show();
	   		 setTimeout(function(){
	   			 $("#costLabel").fadeOut(4000);
	   		 }, 4000);
	   		 return false;
	   	 }
		$("#fileData").submit();
	});
  	
  	
  	$("#fileData").submit(function(event){
  			var formObj = $(this);
  		    var formURL = formObj.attr("action");
  		    var formData = new FormData(this);
  		    var postData = JSON.stringify(formData, null, '\t');
  		    $.ajax({
  		    	beforeSend: function(){
					$(".loader").css('height', $(document).height());
					$(".loader").css('display', 'block');				    
				},
  			    url: formURL,
  			    type: 'POST',
  			    data:  formData,
  			    mimeType:"multipart/form-data",
  			    dataType : "json",
  			    contentType: false ,
  			    contentTransferEncoding: "base64",
  		        cache: false,
  		        processData:false,
  		        success: function(response, textStatus, jqXHR){
  		        	//$(".loader").css('display', 'none');
  		 			if(response.success == true || response.success == 'true'){
  		 				var obj = response.data;
  		 				$("#writtenHealthPolicyFileId").val(obj.writtenPolicyFileId);
  		 				$("#equalityPolicyFileId").val(obj.equalityPolicyFileId);
  		 				
  		 			   var otherFormData = $("#parentDiv").toObject({skipEmpty:true});
  				   	   var postData = JSON.stringify(otherFormData);
  				   	   console.log('postData : ' + postData);
  				   	   var costSheet = otherFormData.costSheet;
  				   	   var isComplete;
  				   	   $.each(costSheet, function(id, value) {
		  				   		var funId = value.functionalityId;
		  				   		var callOutMonToFri = value.callOutMonToFri;
		  				   		var callOutWeekends = value.callOutWeekends;
		  				   		var hourlyRate = value.hourlyRate;
		  				   		var hourlyRateWeekends = value.hourlyRateWeekends;
		  				   	    isComplete = false;
			  	  	        	 if(typeof funId == "undefined"){
			  	  	        		 return isComplete;
			  	  	        	 }
			  	  	        	if(typeof callOutMonToFri == "undefined"){
			  	  	        		return isComplete;
			  	  	        	 }
				  	  	        if(typeof callOutWeekends == "undefined"){
			  	  	        		return isComplete;
			  	  	        	 }
					  	  	    if(typeof hourlyRate == "undefined"){
			  	  	        		return isComplete;
			  	  	        	 }
						  	  	if(typeof hourlyRateWeekends == "undefined"){
			 	  	        		return isComplete;
			 	  	        	 }
						  	  isComplete = true;
		  	  	          });
  				   	 if(isComplete == false){
  				   		 $("#costLabel").show();
  				   		 setTimeout(function(){
  				   			 $("#costLabel").fadeOut(4000);
  				   		 }, 4000);
  				   		 return false;
  	  	        	 }
  				   		$.ajax({
  				   		    url : contextPath+"/register/contractorRegisterationStep3.htm",
  				   		    type: "POST",
  				   		    data : postData,
  				   		    dataType : "json",
  				   		    contentType: 'application/json',
  				   		    success:function(data, textStatus, jqXHR){
  				   		    	$(".loader").css('display', 'none');
  				   		       if(data.success == true){
  				   		    	   window.location.href = contextPath+"/register/redirectcontractorRegisterationStep4.htm";
  				   		       }
  				   		    },
  				   		    error: function(jqXHR, textStatus, errorThrown){
  				   		        alert("fail");  
  				   		    }
  				   		});
  				   		event.stopImmediatePropagation();
  			 		}
  		        },
  			    error: function(jqXHR, textStatus, errorThrown){
  			    	 
  			     }          
  		    });
  		    event.preventDefault(); //Prevent Default action.
  		    event.stopImmediatePropagation();
	}); 
  	
  	$("#contractorRegStep4Btn").click(function(event) {
  		if($("#fContractorRegStep4").valid() == true){
  			 var formData = form2object('fContractorRegStep4', '.', true,function(node){
	                if (node.id && node.id.match(/callbackTest/)){
	                        return { name: node.id, value: node.innerHTML };
	                }
	        });
  			 var postData = JSON.stringify(formData, null, '\t');
  			 $.ajax({
			    url : contextPath+"/register/contractorRegisterationStep4.htm",
			    type: "POST",
			    data : postData,
			    dataType : "json",
			    contentType: 'application/json',
			    success:function(data, textStatus, jqXHR){
		       if(data.success == true || data.success == 'true'){
		    	   window.location.href = contextPath+"/register/contractorRegisterationSuccess.htm";
		    	   $("#containerId").html(data);
		    	   $('#alertSuccessId').show(200);
		    	   $("#successMessageId").html("");
		       }
		    },
		    error: function(jqXHR, textStatus, errorThrown){
		        alert("fail");  
		    }
		});
		event.stopImmediatePropagation();
 		 }
  	});
  	
  	
  	
  $("#updatePassword").click(function(event){
	  var postData = JSON.stringify($('#fChangePassword').serializeObject());
		$.ajax({
		    url : contextPath+"/contractor/updatePassword.htm",
		    type: "POST",
		    data : postData,
		    dataType : "json",
		    contentType: 'application/json',
		    success:function(response, textStatus, jqXHR){
		    	
		    	 $('#alertSuccessId').hide();
		    	 $('#alertSuccessId').hide();
		    	 $('#alertErrorId').hide();
		    	if(response.success == true){
		    		 $.get(contextPath+"/contractor/redirectProfileView.htm", function(data) {
			    		 $("#containerId").html(data);
			    		 $('#alertSuccessId').show(200);
			    		 $("#successMessageId").html("Your Profile has been successfully updated.");
			    		 setTimeout(function() {   
			    			 $('#alertSuccessId').hide(100);
			    		 }, 2500);
			    	 });
		    		 $('#fChangePassword').trigger("reset");
		    	}else{
		    		 $('#alertErrorId').show(200);
		    		 $("#errorMessageId").html(response.message);
		    		 $('#fChangePassword').trigger("reset");
		    	}
		    },
		    error: function(jqXHR, textStatus, errorThrown) {
		        alert("fail");  
		    }
		});
		event.preventDefault();
		event.stopImmediatePropagation();
  });
  
   
  // ---------------------- Common Function -----------------------
  
  $.fn.serializeObject = function()
  {
      var o = {};
      var a = this.serializeArray();
      $.each(a, function() {
          if (o[this.name] !== undefined) {
              if (!o[this.name].push) {
                  o[this.name] = [o[this.name]];
              }
              o[this.name].push(this.value || '');
          } else {
              o[this.name] = this.value || '';
          }
      });
      return o;
  	};
	

// ---------------------- Common Function -----------------------

	//----------- Page Redirection Only --------------
  	

    /*$("#regContractor").click(function(){
  	  $.ajax({
  	      url: contextPath+"/register/redirectContractorRegisteration",
  	      type: "post",
  	      success: function(data){
  	    	  $("#containerId").html(data);
  	      },
  	      error:function(){
  	          $("#containerId").html('There is error while submit');
  	      }
  	  });
    });*/
    
    $("#regContractor").click(function(){
    	window.location.href = contextPath+"/register/redirectRegisterContractor.htm";
    });
    
    /*$("#regContractorhref").click(function(){
    	window.location.href = contextPath+"/register/redirectRegisterContractor.htm";
    });
  	*/
    
    $("#regContractorhref").click(function(){
  	  $.ajax({
  		  url: contextPath+"/register/redirectRegisterContractor.htm",
  	      type: "post",
  	      success: function(data){
  	    	  $("#containerId").html(data);
  	      },
  	      error:function(){
  	          $("#containerId").html('There is error while submit');
  	      }
  	  });
    });
    
    
    
    
	$("#contractorProfile").click(function(){
	  $.ajax({
	      url: contextPath+"/contractor/redirectProfileView.htm",
	      type: "post",
	      success: function(data){
	    	  $("#containerId").html(data);
	      },
	      error:function(){
	          $("#containerId").html('There is error while submit');
	      }
	  });
	});

	$("#editContractor").click(function(){
	  $.ajax({
	      url: contextPath+"/contractor/redirectEditContractor.htm",
	      type: "post",
	      success: function(data){
	    	  $("#containerId").html(data);
	      },
	      error:function(){
	          $("#containerId").html('There is error while submit');
	      }
	  });
	});

	$("#changeContractorPassword").click(function(){
	  $.ajax({
	      url: contextPath+"/contractor/redirectChangePassword.htm",
	      type: "post",
	      success: function(data){
	    	  $("#containerId").html(data);
	      },
	      error:function(){
	          $("#containerId").html('There is error while submit');
	      }
	  });
	});
	
	$("#allJobs").click(function(){
		  $.ajax({
		      url: contextPath+"/contractor/redirectAllAssignedJobs.htm",
		      type: "post",
		      success: function(data){
		    	  $("#containerId").html(data);
		      },
		      error:function(){
		          $("#containerId").html('There is error while submit');
		      }
		  });
	});
	
	$("#allMails").click(function(){
		$.getJSON(contextPath+"/conversation/loadContractorJobsDetails.htm",
			    function(response, textStatus, jqXHR){
		 if(response.success == true){
			 $.ajax({
			      url: contextPath+"/contractor/redirectContractorMails.htm",
			      type: "post",
			      success: function(data){
			    	  $("#containerId").html(data);
			      },
			      error:function(){
			          $("#containerId").html('There is error while submit');
			      }
			  });
		 }else{
			 showErrorMessage("There are no mails yet", "250px");
			 return false;
		 }
		});
	});
	
	function validateUser(){
    	var name = $("#usernameId").val();
    	if(name.trim()){
	    $("#lbl").html("");
    	$.ajax({
    		url:contextPath+"/register/validateUser.htm",
    		method: "Post",
    		dataType: "json",
    		data : {user : name},
    		success:function(data){
    			if(data.success == true){
    				$("#generalInfoSaveBtn").removeAttr('disabled');
    				$("#generalInfoSaveBtn").css('cursor', 'pointer');
    				$("#generalInfoSaveBtn").css('background', '#FF0000');
    				console.log("Username Available");
    			}
    			if(data.success == false){	  
    				$("#generalInfoSaveBtn").attr('disabled','true');
    				$("#generalInfoSaveBtn").css('cursor', 'no-drop');
    				$("#generalInfoSaveBtn").css('background', '#707070');
    				console.log("Username not available");
    				$("#lbl").html("Username Not Available");
    			}
    	  },
    	  error: function(error){
				console.log("Can't Validate the Username");
	  		}
    	  });
    	}
    }
	
	/*$("#allExpertise").click(function(){
		  $.ajax({
		      url: "contractor.htm?action=308",
		      type: "post",
		      success: function(data){
		    	  $("#containerId").html(data);
		      },
		      error:function(){
		          $("#containerId").html('There is error while submit');
		      }
		  });
	});
	

	$("#addExpertise").click(function(){
		  $.ajax({
		      url: "contractor.htm?action=309",
		      type: "post",
		      success: function(data){
		    	  $("#containerId").html(data);
		      },
		      error:function(){
		          $("#containerId").html('There is error while submit');
		      }
		  });
	});*/
}); 


