 <%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/form/form2Object.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/form/json2.js"></script>


<link rel="shortcut icon" href="<%=request.getContextPath()%>/resource/images/favicon.ico" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resource/datatables/css/font-awesome.min.css" />
<link type="text/css" rel="stylesheet" target="_blank" media="screen" href="<%=request.getContextPath()%>/resource/datatables/css/popup.css" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resource/net/jquery.dataTables.css"></link>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/net/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/net/dataTables.bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/datatables/css/tableTools.css"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/datatables/css/admin-style.css"></link>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/css/logout-popup-elements.css"></link> 
<link rel="stylesheet" rel="stylesheet" href="<%=request.getContextPath()%>/resource/api/chosen/chosen-prism.css" />
<link rel="stylesheet" rel="stylesheet" href="<%=request.getContextPath()%>/resource/api/chosen/chosen.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resource/fancybox/jquery.fancybox.css" type="text/css" media="screen" />
<%-- <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resource/css/style.css" /> --%>



<!-- ---------------- JavaScript --------------------------------->

<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.js"></script>
<%@include file="/WEB-INF/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/form/form2Object.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/form/json2.js"></script>
<script  src="<%=request.getContextPath()%>/resource/js/jquery.colorbox.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/fancybox/jquery.fancybox.pack.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/net/jquery.dataTables.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/net/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/datatables/js/tableTools.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.carouFredSel-5.6.4-packed.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/perpetual/admin.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/logout-popup.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/perpetual/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/progress-bar/progressbar.js"></script>


<script src="<%=request.getContextPath()%>/resource/api/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resource/api/chosen/chosen.prism.js" type="text/javascript" charset="utf-8"></script>



	<script type="text/javascript" >
	var fade = 2000;
		$(document).ready(function(){
			$("#logouthrefId").click(function(){
				$.ajax({
					  url: contextPath+"/home/userLogout.htm",
				      type: "get",
				      dataType : "json",
				      success: function(response){
				    	 if(response.success == 'true' || response.success == true){
				    		 window.location.href = contextPath+"/j_spring_security_logout";
				    	 }
				      },
				      error:function(){
				         console.log('Some error is there.');
				      }
				  });
			});
			$("#uploadPropertyDocumentBtn").click(function(){
				$( "#uploadPropertyDocument" ).submit();
			}); 
			/*$( "#BackButtonId" ).click(function() {
				 alert("sfssfskk");
					
			      });*/
			
			$('#BackButtonId1').click(function() {
				/* url:contextPath+"/admin/viewPropertyDeveloper.htm?pdId=${property.id}", */
				//alert("${propertyId}");
				
				$.ajax({
			   		url:contextPath+"/admin/viewProperty.htm?propertyId=${propertyId}",
			   		method: "Post",
			   		contentType: "application/json",															
			   		success:function(data){
			   			window.parent.$("#containerId").html(data);
					},
			   	  	error: function(error){}
			     });
			});
			      
			      
			      
			
		});
		
		function removeDocument(docId) {
			$.ajax({
					beforeSend: function(){
						$(".loader").css('height', $(document).height());
						$(".loader").css('display', 'block');				    
					},
				    url:contextPath+"/admin/removePropertyDocuments.htm?docId=" + docId + "&propertyId=${propertyId}",
			  		method: "Post",
			  		contentType: "application/json",															
			  		success:function(data){
			  			$(".loader").css('display', 'none');
			  			location.href = "${contextPath}/admin/viewPropertyAdminUploadedDocuments.htm?propertyId=${propertyId}";
					},
			  	  	error: function(error){}
			    });
		 }
		
		
			
		
		 
		 
		 
	</script>
	<div class="container-ar">
		<div class="rgpinnew newRGPinner">
			<ul> 
            	<li>
            		  <div class="button"><button id="BackButtonId1">Back</button></div>  
  
      		 		<table width="100%" border="0" cellspacing="0" cellpadding="0">
      		 			<tr>
      		 				<td>
      		 						<section class="AttachFile">
									<c:if test="${propertyObj != null}">
	               						<c:set var="adminUploadedDoc" value="${propertyObj.getDocuments()}"></c:set>
	               						<c:forEach var="AdminUploadedDocObj" items="${adminUploadedDoc}" >
	               							<div class="AttachDoc"><a href="${contextPath}/download/uploadedDocuments.htm?fileId=${AdminUploadedDocObj.getFileId()}" target="_blank">${AdminUploadedDocObj.getFileName()}</a>
	               								<span onclick="removeDocument('${AdminUploadedDocObj.getId()}')"></span>
	               							</div>
	               						</c:forEach>
                					</c:if>   
								</section>
							</td>
						</tr>
						<tr>
							<td>
								<form action="${contextPath}/admin/viewPropertyAdminUploadedDocuments.htm?propertyId=${propertyId}" id="uploadPropertyDocument" method="post" enctype="multipart/form-data" >			
									<table class="formTable" width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td><label id="uploadBankDocumentLbl">Select File:</label></td>
											<td>
												<input type="file" id = "uploadBankDocumentFile" name = "attachFiles" value = "Select File" multiple="multiple"/>
												<div class="errorfile">Please Select file</div>
											</td>
										</tr>
										<tr>
											<td></td>
											<td><textarea class="prpsedtxt" rows="20" cols="15" name="propertyDescription" placeholder="Description..."></textarea></td>
										</tr>
										<tr>
											<td></td>
											<td><input type="button" id = "uploadPropertyDocumentBtn" name="bankDocumentDiv"  value = "Submit" /></td>
										</tr>
									</table>
				 				</form>
							</td>
      		 			</tr>
      		 		</table>
            	</li>
            </ul>
            <div class="clearfix"></div>
		</div>
	</div>
 
 