  var table;
  var globalId;
  
  function setPMCUsers(pmcId){
	  globalSuperPMCId = pmcId;               // assigned to adminManager.jsp
	  $("#headingId").html("PMC Users");
	  $.fn.dataTable.TableTools.defaults.aButtons = [];
		$.fn.dataTableExt.sErrMode = 'console';
		 table = $('#pmcUserdataTableId').DataTable({            // takes id of data table
		        dom: 'Tfrtip',"ajax": {
		            "url": contextPath+"/admin/viewPMCUsers.htm?pmcId="+pmcId,
		            "type": "POST",
		            "dataType": "json"
		        },
		        tableTools: { "aButtons ": [ ] },
		        columns: [
		            { data: null, defaultContent: '<input type="checkbox" />', orderable: false },
		            { data: "pmc.createdOn" },
		            { data: "pmc.companyName" },
		            { data: "pmc.authorisedPerson" },
		            { data: "loginObj.enabled" },
		            { data: "detailsObj.emailId" },
		            { data: "detailsObj.phoneNumber" }
		        ],
		        "pageLength": 15,
		        fnRowCallback : function(nRow, aData, iDisplayIndex){
	                $("td:first", nRow).html(iDisplayIndex +1);
	                return nRow;
	            }
		    } );
		 
		$('#pmcUserdataTableId tbody').on( 'click', 'tr', function () {
		    if ($(this).hasClass('selected')) {
		        $(this).removeClass('selected');
		        $.colorbox.remove();
		    } else {
		        table.$('tr.selected').removeClass('selected');
		        $(this).addClass('selected');
		    }
		} );
		
		$( "#viewPMCUserDetailButtonId").click(function() {
			try{
				var pmcId = table.row('.selected').data().pmc.id;
			}catch (e) {
				$("#commonMessageErrorId").html("Please select a row to view");
				$("#commonMessageErrorId").fadeIn();
				setTimeout(function(){ $("#commonMessageErrorId").fadeOut(4000); }, 2000);
				return false;
			}
	        $.ajax({
	        	url:contextPath+"/admin/viewSubPMC.htm",
	        	method: "Post",
	        	contentType: "application/json",															
	        	success:function(response){
        				$("#containerId").html(response);
        				addSubPMCDetails(pmcId);
	        	},
	        	error: function(error){
      				console.log(error);
	        	}
	        });
	          
		});
		
		  
		$('#viewPMCUserDetailBackButtonId').click(function(){
			  $.ajax({
			      url: contextPath+"/admin/pmcTable.htm",
			      type: "post",
			      success: function(data){
			    	  $("#headingId").html("Property Management Company");
			    	  $("#containerId").html(data);
			      },
			      error:function(){
			          $("#containerId").html('There is error while submit');
			      }
			  });
		});
  }
  
  $(document).ready(function(){
			
/*			$( "#editButtonId").click(function() {
				 try{
					var pmcId = table.row('.selected').data().pmc.id;
					globalId = pmcId;
				}catch (e) {
					$("#commonMessageErrorId").html("Please select a row to edit");
					$("#commonMessageErrorId").fadeIn();
					setTimeout(function(){
						$("#commonMessageErrorId").fadeOut(2000);
					}, 2000);
					return false;
				} 
				validateAOP("#editPopupForm");
				 $.getJSON(contextPath+"/admin/viewPMCDetails.htm?pmcId="+pmcId,
						    function(response, textStatus, jqXHR){
					 
					  			if(response.success == true || response.success == 'true'){
					  				var detailsObj = response.data.detailsObj;
					  				var pmcObj = response.data.pmc;
					  				var addressObj = response.data.addressObj;
					  				
					  				$("#authorisedPerson").val(pmcObj.authorisedPerson);
					  				$("#companyName").val(pmcObj.companyName);
					  				$("#emailId").val(detailsObj.emailId);
					  				$("#phoneNumber").val(detailsObj.phoneNumber);
					  				$("#citiesCovered").val(pmcObj.citiesCovered);
					  				$("#addressLine1").val(addressObj.addressLine1);
					  				$("#addressLine2").val(addressObj.addressLine2);
					  				$("#postCode").val(addressObj.postCode);
					  				$("#city").val(addressObj.city);
					  				$("#county").val(addressObj.county);
					  				$("#editButtonId").colorbox({width:"580px", inline:true, href:"#edit"});
					  			}
						    }).fail(function(jqXHR, textStatus, errorThrown)
						{
						 console.log(errorThrown);
					}); 
			});*/
			
			
		  /* $("#editButtonId").colorbox({width:"580px", inline:true, href:"#edit"});
		   $("#createButtonId").colorbox({width:"950px", inline:true, href:"#create"});
		   $("#deleteButtonId").colorbox({width:"580px", inline:true, href:"#delete"}); 
		   */
		 /*  
		   $("#createButtonId").click(function(){
			   validateAOP("#createPopupFormId");
			   $("#userName").val("");
		   });*/
		   
			/*$('#delete_yes').click(function(){
				var jObj = table.row('.selected').data();
				delete jObj.modifiedOn;
				var jObj1 = JSON.stringify(jObj);
				if(jObj!=null){
				$.ajax({
						url:contextPath+"/admin/deletePMC.htm",
				  		data: jObj1,
				  		method: "Post",
				  		dataType: "json",
				  		contentType: "application/json",														
						success:function(result){
							if(result.success==true){
								$.colorbox.close();
								table.ajax.reload();
							}
					  }});
				}
			});

			$('#delete_no').click(function(){
				$.colorbox.close();
			});
			 */
       	   
       	    
       /*	 $("#createPmcBtnId").click(function(){
       		
       		var name = $("#userName").val();
       		if(name != null && $.trim(name) != ""){
       			userExists();
       			if(status == "false"){
       				$("#userErrorDivId").show();
       				return false;
       			}
       		 }
       		 if($("#createPopupFormId").valid()){
       			 var formData = form2object('createPopupFormId', '.', true,
              			function(node)
              			{
              				if (node.id && node.id.match(/callbackTest/))
              				{
              					return { name: node.id, value: node.innerHTML };
              				}
              			});
              	var jObj = JSON.stringify(formData, null, '\t');
              	 
              	$.ajax({
              		url:contextPath+"/admin/addPMC.htm",
              		data: jObj,
              		method: "Post",
              		dataType: "json",
              		contentType: "application/json",															
              		success:function(result){
              			if(result.success == true || result.success == 'true'){
              				$("#commonMessageSuccessId").html("Successfully Created");
              				$("#commonMessageSuccessId").show();
            				setTimeout(function(){
            					$("#commonMessageSuccessId").fadeOut(2000);
            				}, 2000);
              				$.colorbox.close();
              				table.ajax.reload();
              			}else{
              				$("#commonMessageErrorId").html(result.message);
            				$("#commonMessageErrorId").show();
            				setTimeout(function(){
            					$("#commonMessageErrorId").fadeOut(2000);
            				}, 2000);
              			}
              	  }});
       		 }
  	});*/
       	    
       	 
  /*     	 $("#updateBtnId").click(function(){
       		var cities = $("#editCityServiced").val();
      		 if(cities == null){
      			$("#cityErrorId").show();
   	 		return false;
      		 }
      		if($("#editPopupForm").valid()){
       		var formData = form2object('editPopupForm', '.', true,
       	  			function(node)
       	  			{
       	  				if (node.id && node.id.match(/callbackTest/))
       	  				{
       	  					return { name: node.id, value: node.innerHTML };
       	  				}
       	  			});
       	  	formData.id = globalId;
       	  	var jObj = JSON.stringify(formData, null, '\t');
       	  	$.ajax({
       	  		url:contextPath+"/admin/updatePMC.htm",
       	  		data: jObj,
       	  		method: "Post",
       	  		dataType: "json",
       	  		contentType: "application/json",															
       	  		success:function(result){
       	  			if(result.success==true){
       	  				$.colorbox.close();
       	  				table.ajax.reload();
       	  			$("#commonMessageSuccessId").html("Successfully Updated");
      				$("#commonMessageSuccessId").fadeIn();
    				setTimeout(function(){
    					$("#commonMessageSuccessId").fadeOut(2000);
    				}, 2000);
       	  				
       	  			}else{ 
       	  			$("#commonMessageErrorId").html("Error while updating");
    				$("#commonMessageErrorId").fadeIn();
    				setTimeout(function(){
    					//$("#commonMessageErrorId").fadeOut(2000);
    				}, 2000);
       	  			}
       	  	  }}); 
      		}
       	 });*/
       	 
  });
  	

 
 