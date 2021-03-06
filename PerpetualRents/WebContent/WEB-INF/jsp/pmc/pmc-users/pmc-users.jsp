<script  src="<%=request.getContextPath()%>/resource/js/jquery.colorbox.js"></script>
<style>
	.cityError{
	   	display: none;	
   }
</style>
   
<script type="text/javascript">
  var table;
  var globalId;
  var status;
  $(document).ready(function(){
			$.fn.dataTable.TableTools.defaults.aButtons = [];
			$.fn.dataTableExt.sErrMode = 'console';
			 table = $('#dataTableId').DataTable({
			        dom: 'Tfrtip',"ajax": {
			            "url": contextPath+"/pmc/loadAllPMCUsers.htm",
			            "type": "POST",
			            "dataType": "json"
			        },tableTools: {
			                "aButtons ": [
			                          ]
			        },
			        columns: [
			            { data: null, defaultContent: '<input type="checkbox" />', orderable: false },
			            { data: "pmc.createdOn" },
			            { data: "pmc.companyName" },
			            { data: "pmc.authorisedPerson" },
			            { data: "loginObj.enabled" },
			            { data: "detailsObj.emailId" },
			            { data: "detailsObj.phoneNumber" },
			        ],
			        fnRowCallback : function(nRow, aData, iDisplayIndex){
		                $("td:first", nRow).html(iDisplayIndex +1);
		               return nRow;
		            }
			    } );
			 
			$('#dataTableId tbody').on( 'click', 'tr', function () {
			    if ($(this).hasClass('selected')) {
			        $(this).removeClass('selected');
			        $.colorbox.remove();
			    }
			    else {
			        table.$('tr.selected').removeClass('selected');
			        $(this).addClass('selected');
			    }
			} );
			$( "#viewButtonId").click(function() {
				try{
					var pmcId = table.row('.selected').data().pmc.id;
				}catch (e) {
					$("#commonMessageErrorId").html("Please select a row to view");
					$("#commonMessageErrorId").fadeIn();
					setTimeout(function(){
						$("#commonMessageErrorId").fadeOut(4000);
					}, 2000);
					return false;
				}
		          $.ajax({
		        		url : contextPath+"/pmc/redirectViewPMCUser.htm",
		        		method: "Post",
		        		contentType: "application/json",															
		        		success:function(response){
		        				$("#containerId").html(response);
		        				addPMCDetails(pmcId);
		        	  },
		        	  error: function(error){
	        			console.log(error);
	        	  }});
		        });
			
			$( "#editButtonId").click(function() {
				 try{
					var pmcId = table.row('.selected').data().pmc.id;
					globalId = pmcId;
				}catch (e) {
					$("#commonMessageErrorId").html("Please select a row to edit");
					$("#commonMessageErrorId").fadeIn();
					setTimeout(function(){
						$("#commonMessageErrorId").fadeOut(4000);
					}, 2000);
					return false;
				} 
				validateAOP("#editPopupForm");
				 $.getJSON(contextPath+"/pmc/viewPMCUserDetail.htm?pmcId="+pmcId,
						    function(response, textStatus, jqXHR){
					  			if(response.success == true || response.success == 'true'){
					  				var detailsObj = response.data.detailsObj;
					  				var pmcObj = response.data.pmc;
					  				var addressObj = response.data.addressObj;
					  				
					  				$("#companyName").val(pmcObj.companyName);
					  				$("#authorisedPerson").val(pmcObj.authorisedPerson);
					  				$("#emailId").val(detailsObj.emailId);
					  				$("#phoneNumber").val(detailsObj.phoneNumber);
					  				$("#citiesCovered").val(pmcObj.citiesCovered);
					  				$("#addressLine1").val(addressObj.addressLine1);
					  				$("#addressLine2").val(addressObj.addressLine2);
					  				$("#postCode").val(addressObj.postCode);
					  				$("#city").val(addressObj.city);
					  				$("#county").val(addressObj.county);
					  				if(pmcObj.citiesCovered){
					  					var selectedIdArr = pmcObj.citiesCovered.split(',');
						  				if(selectedIdArr.length > 0){
						  					
						  				}
					  				}
					  				$("#editButtonId").colorbox({width:"580px", inline:true, href:"#edit"});
					  			}
						    }).fail(function(jqXHR, textStatus, errorThrown)
						{
						 console.log(errorThrown);
					}); 
			});
			
			
		   $("#editButtonId").colorbox({width:"580px", inline:true, href:"#edit"});
		   $("#createButtonId").colorbox({width:"950px", inline:true, href:"#create"});
		   $("#deleteButtonId").colorbox({width:"580px", inline:true, href:"#delete"}); 
		   
		   
		   $("#createButtonId").click(function(){
			   validateAOP("#createPopupFormId");
			   $("#userName").val("");
		   });

			$('#delete_no').click(function(){
				$.colorbox.close();
			});
			 
       	    $(".close").click(function(){
       	    	$("#createErrorId").hide();
       	    });
       	    
       	 $("#createPmcBtnId").click(function(){
       		
       		var name = $("#userName").val();
       		if(name != null && $.trim(name) != ""){
       			userExists();
       			if(status == "false"){
       				$("#userErrorDivId").show();
       				return false;
       			}
       		 }
       		//validateAOP("#createPopupFormId");
       		 if($("#createPopupFormId").valid()){
       			 var formData = form2object('createPopupFormId', '.', true, function(node){
              				if (node.id && node.id.match(/callbackTest/)){
              					return { name: node.id, value: node.innerHTML };
              				}
              			});
              	var jObj = JSON.stringify(formData, null, '\t');
              	 
              	$.ajax({
              		url: contextPath+"/pmc/createPMCUser.htm",
              		data: jObj,
              		method: "Post",
              		dataType: "json",
              		contentType: "application/json",															
              		success:function(result){
              			if(result.success == true || result.success == 'true'){
              				$("#commonMessageSuccessId").html("Successfully Created");
              				$("#commonMessageSuccessId").fadeIn();
            				setTimeout(function(){
            					$("#commonMessageSuccessId").fadeOut(4000);
            				}, 2000);
              				$.colorbox.close();
              				table.ajax.reload();
              			}else{
              				$("#commonMessageErrorId").html("Error while creating");
            				$("#commonMessageErrorId").fadeIn();
            				setTimeout(function(){
            					$("#commonMessageErrorId").fadeOut(4000);
            				}, 2000);
              			}
              	  }});
       		 }
  	});
       	 
       	 $("#updateBtnId").click(function(){
       		
       		var formData = form2object('editPopupForm', '.', true,
       	  			function(node){
       	  				if (node.id && node.id.match(/callbackTest/))
       	  				{
       	  					return { name: node.id, value: node.innerHTML };
       	  				}
       	  			});
       	  	formData.id = globalId;
       	  	var jObj = JSON.stringify(formData, null, '\t');
       	  	$.ajax({
       	  		url:contextPath+"/pmc/updatePMCUser.htm",
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
    					$("#commonMessageSuccessId").fadeOut(4000);
    				}, 2000);
       	  				
       	  			}else{ 
       	  			$("#commonMessageErrorId").html("Error while updating");
    				$("#commonMessageErrorId").fadeIn();
    				setTimeout(function(){
    					$("#commonMessageErrorId").fadeOut(4000);
    				}, 2000);
       	  			}
       	  	  }}); 
       	 });
  });
  	
	$('.close').click(function () {
	    $('#alertSuccessId').hide(200);
	    $('#alertErrorId').hide(200);
	    $('#mainsuccessId').hide(200);
	    
  	}); 
	
	 $(function() {
 	    $("#editPopupForm").validate({
 	        rules: {
 	        	"authorisedPerson": "required",
 	        	"contactDetailsObj.emailId" : {
 	                required: true,	
 	                email: true
 	            },
 	        	"contactDetailsObj.phoneNumber" : {
 	                required: true,
 	                minlength :10,
	                maxlength : 15
 	            },
 	        	"addressObj.addressLine1" : {
 	                required: true,
	                maxlength : 50
 	            },
 	        	"addressObj.postCode": "required",
 	        	"addressObj.city": "required",
 	        	"addressObj.county": "required",
 	        	"loginObj.username" : "required"
 	        }
 	    });
 	  });

 $(function() {
 	    $("#createPopupFormId").validate({
 	        rules: {
 	        	"authorisedPerson": "required",
 	        	"contactDetailsObj.emailId": {
 	                required: true,
 	                email: true
 	            },
 	        	"contactDetailsObj.phoneNumber" : {
 	                required: true,
 	                minlength :10,
 	                maxlength : 15
 	            },
 	            "citiesCoveredList"  : "required",
 	             "addressObj.addressLine1" : {
	                required: true,
	                maxlength : 50
	             },
 	        	"companyName" : "required",
 	        	"addressObj.postCode" : "required",
 	        	"addressObj.city" : "required",
 	        	"addressObj.county" : "required",
 	        	"loginObj.username" : "required"
 	        		
 	        }
 	    });
 	  });

 function validateAOP(formId){
 	  $(formId)[0].reset();
 	  var validator = $(formId).validate();
 	  validator.resetForm();
 	  $("label.error").remove();
 	  $(".error").removeClass();
 	  $("#cityErrorDivId").hide();
 }
 function userExists(){
	 var name = $("#userName").val();
	 if($.trim(name) != ""){
	 $.ajax({
	  		url:"pmc/validateUserName.htm?name="+name,
	  		method: "Post",
	  		dataType: "json",
	  		contentType: "application/json",															
	  		success:function(result){
	  			if(result.success == true){
	  				status = "true";
	  				$("#userErrorDivId").hide();
	  			}else{ 
	  				$("#userErrorDivId").show();
	  			//$("#commonMessageErrorId").html("Username already exists");
				//$("#commonMessageErrorId").fadeIn();
				/* setTimeout(function(){
					$("#commonMessageErrorId").fadeOut(1000);
				}, 2000); */
				status = "false";
	  			}
	  	  }});
	 }
 }
	
	
  </script> 
<div class="container">
  <sections class="contant-area">
    <div class="button">
     <button id="createButtonId">Create</button>
     <button id="editButtonId">Edit</button>
     <button style="display: none;" id="deleteButtonId">Delete</button>
     <button id="viewButtonId">View</button>
    </div>
   	 <div id="alertErrorId" class="alert alert-block alert-error fade in" style="display: none;">
            <button id="alert" type="button" class="close"  ><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
            <h4 class="alert-heading" animation="true" id="errorId">You got an error!</h4>
        </div>
        <div id="mainsuccessId" class="alert alert-block alert-success fade in"  style="display: none;" >
            <button id="alert" type="button" class="close"  ><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
            <h4 class="alert-heading" animation="true" id="mainsuccessMessageId">You got an error!</h4>
        </div>
        <table id="dataTableId" class="table display table-bordered"  cellspacing="0">
    <thead>
        <tr>
            	<th>S.No.</th>
            	<th>Created On </th>
	            <th>Company Name</th>
	            <th>Authorised Person</th>
	            <th>Enable</th>
	            <th>Email</th>
	            <th>Phone Number</th>
        </tr>
    </thead>
</table>
  </sections>
</div>

<div style="display:none;">
  <div id='edit'>
    <form id="editPopupForm">
      <h1>Edit Details</h1>
     	<input type="hidden" value="" name="companyName" id="companyName">
      <div class="form-element">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="40%"><label>Authorised Person:</label></td>
            <td width="60%"><input type="text" value="" class="" name="authorisedPerson" id="authorisedPerson"/></td>
          </tr>         
          <tr>
            <td><label>Email Id</label></td>
            <td><input type="text" value="" class="" name="contactDetailsObj.emailId" id="emailId" readonly="readonly" style="background-color: #E0E0E0"/></td>
          </tr>
          <tr>
            <td><label>Contact Number:</label></td>
            <td><input type="text" value="" class="" name="contactDetailsObj.phoneNumber" id="phoneNumber"/></td>
          </tr>
          <tr>
            <td><label>Address Line1:</label></td>
            <td><input type="text" value="" class="" name="addressObj.addressLine1" id="addressLine1"/></td>
          </tr>
          <tr>
            <td><label>Address Line2:</label></td>
            <td><input type="text" value="" class="" name="addressObj.addressLine2" id="addressLine2"/></td>
          </tr>
          <tr>
            <td><label>City:</label></td>
            <td><input type="text" value="" class="" name="addressObj.city" id="city"/></td>
          </tr>
          <tr>
            <td><label>Post Code:</label></td>
            <td><input type="text" value="" class="" name="addressObj.postCode" id="postCode" style="text-transform: uppercase;"/></td>
          </tr>
          <tr>
            <td><label>County:</label></td>
            <td><input type="text" value="" class="" name="addressObj.county" id="county"/></td>
          </tr>
        </table>
      </div>
      <div id="edit_footer"><input type="button" id="updateBtnId" value="Update" /></div>
    </form>
  </div>
 
   <div id='create'>
    <form id="createPopupFormId" >
      <h1>Register Property Management Company</h1>
       <div id="createErrorId" class="alert alert-block alert-error fade in" hidden="true" >
            <button id="alert" type="button" class="close"  ><span aria-hidden="true">&times;</span><span class="sr-only"></span></button>
            <h4 class="alert-heading" animation="true" id="createErrorMessageId">You got an error!</h4>
        </div>
      <div class="form-element1">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="25%"><label>Company Name:</label>
            <input type="text" value="" class="" name="companyName" /></td>
            <td width="25%"><label>Authorised Person</label>
            <input type="text" value="" class="" name="authorisedPerson" /></td>
          </tr>
          <tr>
           <td><label>Web URL</label>
            <input type="text" value="" class="" name="weburl" /></td>
            <td><label>UserName</label>
            <input type="text" value="pmc1" class="" id="userName" name="loginObj.username" onblur="userExists()"/>
            <label id="userErrorDivId" class="userError" style="color: #FF0000; float: right; display: none;">Username exists.</label>
            </td>
          </tr>
          <tr>
            <td><label>Contact Number</label>
            <input type="text" value="" class="" name="contactDetailsObj.phoneNumber" /></td>
            <td><label>Email Id:</label>
            <input type="text" value="" class="" name="contactDetailsObj.emailId" /></td>
          </tr>
          <tr>
            <td><label>Address Line1</label>
            <input type="text" value="" class="" name="addressObj.addressLine1" /></td>
            <td><label>Address Line2</label>
            <input type="text" value="" class="" name="addressObj.addressLine2" /></td>
          </tr>
          <tr>
            <td><label>City</label>
            <input type="text" value="" class="" name="addressObj.city" /></td>
            <td><label>Post Code</label>
            <input type="text" value="" class="" name="addressObj.postCode" style="text-transform: uppercase;  " /></td>
          </tr>
          <tr>
            <td><label>County</label>
            <input type="text" value="" class="" name="addressObj.county" /></td>
            <td><label>Landmark</label>
            <input type="text" value="" class="" name="addressObj.landmark" /></td>
          </tr>
        </table>
      </div>
      <div id="create_footer"><input type="button" id="createPmcBtnId" value="Create" /></div>
    </form>
  </div>
     <div id='delete'>
   	 <form id="deletePopupFormId" action="javascript:editPopupForm()">
      <h1>Delete Landlord</h1>
      
      <div id="delete_footer"><input id="delete_yes" type="button" value="Yes" style="background-color:#E33A0C"/>&nbsp&nbsp<input id="delete_no" type="button" value="No" /></div>
    </form>
  </div> 
</div>