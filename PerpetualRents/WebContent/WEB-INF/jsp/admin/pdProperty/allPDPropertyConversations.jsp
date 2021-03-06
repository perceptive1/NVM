<style>
.tab-menu{ margin:-42px 9px 0 0;}
.tab_content1{ border: 1px solid #cccccc!important;}
ul.menu, ul.menu ul { list-style:none; margin:0; padding:0; width:100%;}
ul.menu a { display:block; text-decoration:none;border-bottom:1px solid #ccc; position:relative; border-left:5px solid #f8f8f8;}
ul.menu a:hover { border-color:#FFDCD3;}
ul.menu a span { font-size:22px; font-weight:normal; position:absolute; right:25px; top:6px; display:none;}
ul.menu li a {color:inherit; font-size:13px; font-weight:400; padding:14px 0 14px 10px;}
ul.menu li a:hover, .hyperlink-selected { background:rgba(204,204,204,0.2); border-color:#ff000f!important;}
ul.menu li ul li a { border-left:0px #ccc solid; color:#000; padding-left:20px; font-weight:400; border-left:10px solid #f8f8f8;}
ul.menu li ul li ul li a { padding-left:35px; border-left:15px solid #f8f8f8;}
ul.menu li ul li a:hover, .hyperlink-selected {list-style:none; background:none; border-color:#ff000f!important;}
</style>
<script type="text/javascript"  src="<%=request.getContextPath()%>/resource/perpetual/admin/pdProperty/pdPropertyDetails.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath()%>/resource/perpetual/admin/pdProperty/pdproperty-pmc-conversation.js"></script> 

<script type="text/javascript">
$(document).ready(function() {
	
	$(".tab_content1").hide(); //Hide all content
	$(".left-inbox li:first").addClass("selected").show(); //Activate first tab
	$(".tab_content1:first").show(); //Show first tab content
	//On Click Event
	$(".tab-menu li").click(function() {
		$(".tab-menu li").removeClass("selected"); //Remove any "active" class
		$(this).addClass("selected"); //Add "active" class to selected tab
		$(".tab_content1").hide(); //Hide all tab content
		var activeTab = $(this).find("a").attr("href"); //Find the rel attribute value to identify the active tab + content
		if(activeTab == "#pmcChat"){
			callPmc();
		}
		$(activeTab).fadeIn(); //Fade in the active content
		
		return false;
	});
});

</script>

<div class="container-ar cont-new">
  <section class="contant-area" style="">  
     
		<div class="pd-back-btn">
		<div class="button"><button id="backFromDocumentViewId">Back</button></div> 
		</div>
		<br/><br/><br/>
		<div class="tab-menu">
    	 <ul>
      	 	<li id="pmcChatliID"><a id="pmcChatHREFId" href="#pmcChat" style="color: #E33A0C;">PMC Chat</a></li>
    	 </ul>
		</div>

    <div class="tab_content1 email-area" id="pmcChat">
      <div class="left-inbox">
        <nav class="tabs" id="pdCharityList">
        </nav>
      </div>
      <div class="right-index">
         <div class="" id="pdCharityMailDivisionId" style="margin-right: 12px; ">
          <h1 class="heading4 margin-top15" id="charitySubjectId"></h1>
          <div class="blog-sec" id="pdCharityMailContentId"> </div>
      </div>
    </div>
    </div>
  </section>
</div>
