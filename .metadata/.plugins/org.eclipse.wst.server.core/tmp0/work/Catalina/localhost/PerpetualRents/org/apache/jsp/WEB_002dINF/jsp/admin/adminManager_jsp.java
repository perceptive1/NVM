/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.37
 * Generated at: 2015-07-14 13:14:45 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class adminManager_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/WEB-INF/jsp/common/common.jsp", Long.valueOf(1436246875542L));
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<title>The No Voids Man</title>\r\n");
      out.write("<!-- ---------------- Style Sheet --------------------------------->\r\n");
      out.write("<link rel=\"shortcut icon\" href=\"");
      out.print(request.getContextPath());
      out.write("/resource/images/favicon.ico\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.print(request.getContextPath());
      out.write("/resource/datatables/css/font-awesome.min.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" target=\"_blank\" media=\"screen\" href=\"");
      out.print(request.getContextPath());
      out.write("/resource/datatables/css/popup.css\" />\r\n");
      out.write("<link type=\"text/css\" rel=\"stylesheet\" href=\"");
      out.print(request.getContextPath());
      out.write("/resource/net/jquery.dataTables.css\"></link>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(request.getContextPath());
      out.write("/resource/net/bootstrap.min.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(request.getContextPath());
      out.write("/resource/net/dataTables.bootstrap.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(request.getContextPath());
      out.write("/resource/datatables/css/tableTools.css\"/>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(request.getContextPath());
      out.write("/resource/datatables/css/admin-style.css\"></link>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(request.getContextPath());
      out.write("/resource/css/logout-popup-elements.css\"></link> \r\n");
      out.write("<link rel=\"stylesheet\" rel=\"stylesheet\" href=\"");
      out.print(request.getContextPath());
      out.write("/resource/api/chosen/chosen-prism.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" rel=\"stylesheet\" href=\"");
      out.print(request.getContextPath());
      out.write("/resource/api/chosen/chosen.css\" />\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(request.getContextPath());
      out.write("/resource/fancybox/jquery.fancybox.css\" type=\"text/css\" media=\"screen\" />\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<!-- ---------------- JavaScript --------------------------------->\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resource/js/jquery.js\"></script>\r\n");
      out.write("<link rel=\"shortcut icon\" href=\"");
      out.print(request.getContextPath());
      out.write("/resource/images/favicon.ico\" />\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resource/js/logout-popup.js\"></script>\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("(function() {\n");
      out.write("\t  // Union of Chrome, Firefox, IE, Opera, and Safari console methods\n");
      out.write("\t  var methods = [\"assert\", \"assert\", \"cd\", \"clear\", \"count\", \"countReset\",\n");
      out.write("\t    \"debug\", \"dir\", \"dirxml\", \"dirxml\", \"dirxml\", \"error\", \"error\", \"exception\",\n");
      out.write("\t    \"group\", \"group\", \"groupCollapsed\", \"groupCollapsed\", \"groupEnd\", \"info\",\n");
      out.write("\t    \"info\", \"log\", \"log\", \"markTimeline\", \"profile\", \"profileEnd\", \"profileEnd\",\n");
      out.write("\t    \"select\", \"table\", \"table\", \"time\", \"time\", \"timeEnd\", \"timeEnd\", \"timeEnd\",\n");
      out.write("\t    \"timeEnd\", \"timeEnd\", \"timeStamp\", \"timeline\", \"timelineEnd\", \"trace\",\n");
      out.write("\t    \"trace\", \"trace\", \"trace\", \"trace\", \"warn\"];\n");
      out.write("\t  var length = methods.length;\n");
      out.write("\t  var console = (window.console = window.console || {});\n");
      out.write("\t  var method;\n");
      out.write("\t  var noop = function() {};\n");
      out.write("\t  while (length--) {\n");
      out.write("\t    method = methods[length];\n");
      out.write("\t    // define undefined methods as noops to prevent errors\n");
      out.write("\t    if (!console[method])\n");
      out.write("\t      console[method] = noop;\n");
      out.write("\t  }\n");
      out.write("\t})();\n");
      out.write("</script>\n");
      out.write("<script>\n");
      out.write("    var contextPath ;\n");
      out.write("\t$(document).ready(function(){\n");
      out.write("\t\tcontextPath = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("';\n");
      out.write("\t\t\t$(\"#logoutBtnId\").click(function(){\n");
      out.write("\t\t\t\t$.ajax({\n");
      out.write("\t\t\t\t\t  url: contextPath+\"/home/userLogout.htm\",\n");
      out.write("\t\t\t\t      type: \"get\",\n");
      out.write("\t\t\t\t      dataType : \"json\",\n");
      out.write("\t\t\t\t      success: function(response){\n");
      out.write("\t\t\t\t    \t  \n");
      out.write("\t\t\t\t    \t if(response.success == 'true' || response.success == true){\n");
      out.write("\t\t\t\t    \t\t window.location.href = contextPath+\"/j_spring_security_logout\";\n");
      out.write("\t\t\t\t    \t }\n");
      out.write("\t\t\t\t      },\n");
      out.write("\t\t\t\t      error:function(){\n");
      out.write("\t\t\t\t    \t  console.log('Some error is there.');\n");
      out.write("\t\t\t\t      }\n");
      out.write("\t\t\t\t  });\n");
      out.write("\t\t\t});\n");
      out.write("\t\t\t$(\"#continueBtnId\").click(function(){\n");
      out.write("\t\t\t\twindow.location.href = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/home.htm\";\n");
      out.write("\t\t\t});\n");
      out.write("\t\tconsole.log(contextPath);\n");
      out.write("\t\t});\n");
      out.write("\tfunction getContextPath() {\n");
      out.write("\t\t   return \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("\";\n");
      out.write("\t\t}\n");
      out.write("</script>\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("\n");
      out.write("$(document).ready(function(){\n");
      out.write("\t  var homemaxage = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${homemaxage}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("';\n");
      out.write("\t  \n");
      out.write("\t var stop = true;\n");
      out.write("  \t var maxage =\"\";\n");
      out.write("  \t var name = \"maxage\" + \"=\";\n");
      out.write("  \t var intval = 1000;\n");
      out.write("  \t \n");
      out.write("  \t setInterval(function (){\n");
      out.write("  \t\t \n");
      out.write("  \t   \t var cdata = document.cookie.split(';');\n");
      out.write("        // console.log(cdata);\n");
      out.write("         \n");
      out.write("  \t   \t  for(var i=0; i<cdata.length; i++){\n");
      out.write("\n");
      out.write("  \t   \t  \tvar c = cdata[i].trim();\n");
      out.write("  \t   \t  \tif (c.indexOf(\"maxage\")==0){\n");
      out.write("  \t   \t  \t\t\tmaxage = c.substring(name.length,c.length);\n");
      out.write("  \t   \t  \t\t\tif(maxage == homemaxage){\n");
      out.write("  \t   \t  \t\t\t\tdocument.cookie ='maxage='+maxage;\n");
      out.write("  \t   \t  \t\t\t\tstop = true;\n");
      out.write("  \t   \t  \t\t\t}\n");
      out.write("  \t   \t  \t    \tbreak;\t\n");
      out.write("  \t   \t \t\t}\n");
      out.write("  \t   \t  }\n");
      out.write("  \t   \t \n");
      out.write("  \t   \t  if(maxage != null && maxage!=\"\"){\n");
      out.write("  \t   \t\t  maxage = parseInt(maxage)-1000;\n");
      out.write("  \t   \t\t  if(parseInt(maxage) <= 0.00){\n");
      out.write("  \t   \t\t\t  document.cookie =name+\";expires=\"+new Date();\n");
      out.write("  \t   \t\t\t$.ajax({\n");
      out.write("    \t\t\t\t  url: contextPath+\"/home/applicationLogout.htm\",\n");
      out.write("    \t\t\t      type: \"GET\",\n");
      out.write("    \t\t\t      dataType : \"json\",\n");
      out.write("    \t\t\t      success: function(response){\n");
      out.write("    \t\t\t    \t if(response.success == 'true' || response.success == true){\n");
      out.write("    \t\t\t    \t\twindow.location.href = contextPath+'/j_spring_security_logout';\n");
      out.write("    \t\t\t    \t }\n");
      out.write("    \t\t\t      },\n");
      out.write("    \t\t\t      error:function(){\n");
      out.write("    \t\t\t         console.log('Some error is there.');\n");
      out.write("    \t\t\t      }\n");
      out.write("    \t\t\t  });\n");
      out.write("  \t   \t\t\t  \n");
      out.write("  \t   \t\t\t  \n");
      out.write("  \t   \t\t  }\n");
      out.write("  \t   \t\t  document.cookie ='maxage='+maxage;\n");
      out.write("  \t     \t\t  if(parseInt(maxage) < (2*60*1000)){\n");
      out.write("  \t     \t\t\t  if(stop){\n");
      out.write("  \t     \t\t\t\t var logoutStr = getLogoutTime();\n");
      out.write("  \t     \t\t\t\tconsole.log(logoutStr);\n");
      out.write("  \t     \t\t\t\t$(\"#timeLeftId\").html(logoutStr);\n");
      out.write("  \t     \t\t\t\tlogoutPopUp('bodyId', 'logoutWindowId');\n");
      out.write("  \t     \t\t\t\tstop =false;\n");
      out.write("  \t     \t\t\t\t\n");
      out.write("  \t     \t\t\t  }\n");
      out.write("  \t     \t\t\t var logoutStr = getLogoutTime();\n");
      out.write("\t     \t\t\t\tconsole.log(\"appending : \" + logoutStr);\n");
      out.write("\t     \t\t\t$(\"#timeLeftId\").html(logoutStr);\n");
      out.write("  \t     \t\t  }\n");
      out.write("  \t   \t  }\n");
      out.write("  \t    }, intval);\n");
      out.write("  \t \n");
      out.write(" \n");
      out.write("  \tfunction getLogoutTime(){\n");
      out.write("        var ch  =  (((parseInt(maxage)/(60*1000)).toFixed(2)).toString()).split(\".\");   \n");
      out.write("        if(ch[0]>0){\n");
      out.write("     \t   return \"\"+ch[0]+\" min(s) \"+((parseInt(maxage)-(ch[0]*60*1000))/1000)+\" sec(s)\";\n");
      out.write("        }else{\n");
      out.write("     \t   return \"\"+ch[0]+\" min(s) \"+(parseInt(maxage)/1000)+\" sec(s)\";   \n");
      out.write("        }\n");
      out.write("    };\n");
      out.write(" }); \n");
      out.write(" \n");
      out.write("$(document).ready (function(){\n");
      out.write("\t$('.effect').click(function(){\n");
      out.write("\t\t$(this).addClass('add-effect');\n");
      out.write("\t});\n");
      out.write("\t$('.effect').blur(function(){\n");
      out.write("\t\t$(this).removeClass('add-effect');\n");
      out.write("\t});\n");
      out.write("});\n");
      out.write("</script>");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resource/js/form/form2Object.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resource/js/form/json2.js\"></script>\r\n");
      out.write("<script  src=\"");
      out.print(request.getContextPath());
      out.write("/resource/js/jquery.colorbox.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resource/fancybox/jquery.fancybox.pack.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resource/net/jquery.dataTables.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resource/net/dataTables.bootstrap.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resource/datatables/js/tableTools.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resource/js/jquery.carouFredSel-5.6.4-packed.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resource/perpetual/admin.js\"></script>\r\n");
      out.write("<script src=\"http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resource/js/logout-popup.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resource/perpetual/common.js\"></script>\r\n");
      out.write(" <script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resource/js/progress-bar/progressbar.js\"></script>\r\n");
      out.write(" \r\n");
      out.write("<script type=\"text/javascript\" charset=\"utf-8\">\r\n");
      out.write("\tvar pdProperty;\r\n");
      out.write("</script>\r\n");
      out.write("<style>\r\n");
      out.write("label.error{\r\n");
      out.write("\tcolor: #FF0000;\r\n");
      out.write("\tfloat: right;\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("</style>\r\n");
      out.write("<!-- ---------------- Fonts Link --------------------------------->\r\n");
      out.write("<link href=\"http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700\" rel=\"stylesheet\" type=\"text/css\" />\r\n");
      out.write("<script>\r\n");
      out.write("var fade = 2000;\r\n");
      out.write("\t$(document).ready(function(){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t$(\"#logouthrefId\").click(function(){\r\n");
      out.write("\t\t\t$.ajax({\r\n");
      out.write("\t\t\t\t  url: contextPath+\"/home/userLogout.htm\",\r\n");
      out.write("\t\t\t      type: \"get\",\r\n");
      out.write("\t\t\t      dataType : \"json\",\r\n");
      out.write("\t\t\t      success: function(response){\r\n");
      out.write("\t\t\t    \t if(response.success == 'true' || response.success == true){\r\n");
      out.write("\t\t\t    \t\t window.location.href = contextPath+\"/j_spring_security_logout\";\r\n");
      out.write("\t\t\t    \t }\r\n");
      out.write("\t\t\t      },\r\n");
      out.write("\t\t\t      error:function(){\r\n");
      out.write("\t\t\t         console.log('Some error is there.');\r\n");
      out.write("\t\t\t      }\r\n");
      out.write("\t\t\t  });\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\tvar globalPropertyId;\r\n");
      out.write("\tvar globalSuperPMCId;\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body id=\"bodyId\">\r\n");
      out.write("<div id=\"maskLoader\" class=\"loader\" ></div>\r\n");
      out.write("<header>\r\n");
      out.write("\t<span style=\"position: absolute; top:60px; margin: 0 auto; height: 35px; border-radius:6px; width: 20%; border:1px solid #ccc; left:0; right:0;\r\n");
      out.write("\t line-height: 35px; background:#66FF99 url('resource/images/ok-icon.png') no-repeat 25px 5px; background-size: 25px 25px; z-index:99999; text-align: center; display:none;\" id=\"commonMessageSuccessId\">Successfully Saved...</span>\r\n");
      out.write("\t <span id=\"commonMessageErrorId\" style=\"display:none; position: absolute; top:60px; margin: 0 auto; min-height: 35px; border-radius:6px; width: 20%; border:1px solid #ccc; left:0; right:0; line-height: 20px;\r\n");
      out.write("  \t\tbackground:#FFD6D6 url('resource/images/not-ok-icon.png') no-repeat 25px 5px; background-size: 25px 25px; z-index:99999; text-align: center; text-align: left; padding: 7px 0 0 58px; \" ></span>\r\n");
      out.write("  \r\n");
      out.write("  <div class=\"logo\"><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/home.htm\"><img src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/resource/images/logo22.png\" width=\"205\" height=\"22\" /></a></div>\r\n");
      out.write("  <section class=\"login-sec\"> \r\n");
      out.write("          <div class=\"user-bt\"><a id=\"logouthrefId\" href=\"#\">Logout</a></div>\r\n");
      out.write("          \r\n");
      out.write("        </section>\r\n");
      out.write("   <div class=\"username\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${loginName}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("</div>\r\n");
      out.write("   <div class=\"login\"><a href=\"#\"></a></div>\r\n");
      out.write("  <div class=\"clear\"></div>\r\n");
      out.write("\r\n");
      out.write("  <section class=\"head\">\r\n");
      out.write("    <h1 id=\"headingId\" style=\"margin-top: 0px; margin-bottom: 5px;\">Property</h1>\r\n");
      out.write("    <ul class=\"bread-crum\">\r\n");
      out.write("<!--       <li><a href=\"#\"><i class=\"icon-sm fa fa-dashboard\"></i>Home</a></li>\r\n");
      out.write("      <li><a href=\"#\" class=\"selected\">Dashboard</a></li> -->\r\n");
      out.write("    </ul>\r\n");
      out.write("    <div class=\"clear\"></div>\r\n");
      out.write("  </section>\r\n");
      out.write("  \r\n");
      out.write("  <div class=\"logoutWindowPopUp\" id=\"logoutWindowId\" onclick = \"check(event)\" style=\"margin-top: 7%;\">\r\n");
      out.write("  <div class=\"popupContact1\">\r\n");
      out.write("    <div class=\"formClass1\">\r\n");
      out.write("      <img src=\"resource/images/cross-image.png\" id=\"close\" style=\"display: none;\"/> \r\n");
      out.write("      <div class=\"h21\" style=\" padding:23px 35px;\">\r\n");
      out.write("        <h1 style=\"text-align:center; font:20; font-size: 22px;\">Logout Window</h1>\r\n");
      out.write("      </div>\r\n");
      out.write("      <div class=\"pop-p1\">\r\n");
      out.write("        <p>Your Session is going to expire in</p>\r\n");
      out.write("        <p id=\"timeLeftId\"> next 15 minutes</p>\r\n");
      out.write("        <p>Do You want to continue or logout??</p>\r\n");
      out.write("      </div>\r\n");
      out.write("      <div class=\"h21\" style=\"border-top:1px solid #CCC; border-radius:0 0 6px 6px; padding: 8px 18px 12px 18px\">\r\n");
      out.write("        <button id=\"logoutBtnId\" class=\"button-file1\">Logout</button>\r\n");
      out.write("        <button id=\"continueBtnId\" class=\"button-file1\" style=\"margin-right:50px;\">Continue</button>\r\n");
      out.write("      </div>\r\n");
      out.write("    </div>\r\n");
      out.write("  </div>\r\n");
      out.write("</div>\r\n");
      out.write("  \r\n");
      out.write("</header>\r\n");
      out.write("<aside>\r\n");
      out.write("  <nav>\r\n");
      out.write("    <ul id=\"menu\">\r\n");
      out.write("      <!-- <li><a href=\"#\"><i class=\"icon fa fa-dashboard\"></i>Dashboard</a></li> -->\r\n");
      out.write("      <li> <a href=\"#property\" id=\"property\"><i class=\"icon fa fa-th\"></i>Property </a>\r\n");
      out.write("      </li>\r\n");
      out.write("       <li> <a href=\"#proposedDevelopment\" id=\"proposedDevelopment\"><i class=\"icon fa fa-bar-chart-o\"></i>Property Development</a>\r\n");
      out.write("      </li>\r\n");
      out.write("      <li> <a href=\"#landlord\" id=\"landlord\"><i class=\"icon fa fa-bar-chart-o\"></i>Landlord </a>\r\n");
      out.write("      </li>\r\n");
      out.write("      <li> <a href=\"#contractor\" id=\"contractor\"><i class=\"icon fa fa-laptop\"></i>Contractor </a>\r\n");
      out.write("      </li>\r\n");
      out.write("      <li> <a href=\"#users\" id=\"users\"><i class=\"icon fa fa-laptop\"></i>Users </a>\r\n");
      out.write("      </li>\r\n");
      out.write("      <li> <a href=\"#pmc\" id=\"pmc\"><i class=\"icon fa fa-edit\"></i>Property Management Company </a>\r\n");
      out.write("      </li>\r\n");
      out.write("      <li> <a href=\"#\" id=\"allJobs\"><i class=\"icon fa fa-edit\"></i>All Jobs</a>\r\n");
      out.write("      </li>\r\n");
      out.write("      <li> <a href=\"#anonymous\" id=\"anonymous\"><i class=\"icon fa fa-edit\"></i>Contacts </a>\r\n");
      out.write("      </li>\r\n");
      out.write("      <li> <a href=\"#profile\" id=\"profile\"><i class=\"icon fa fa-edit\"></i>My Profile </a>\r\n");
      out.write("      </li>\r\n");
      out.write("      <li> <a href=\"#settings\" id=\"settings\"><i class=\"icon fa fa-edit\"></i>Settings </a>\r\n");
      out.write("      </li>\r\n");
      out.write("       <li> <a href=\"#uploadFiles\" id=\"uploadFiles\"><i class=\"icon fa fa-edit\"></i>Upload Files </a>\r\n");
      out.write("      </li>\r\n");
      out.write("      <!-- <li> <a href=\"#settings\" id=\"settings\" onclick=\"div_show('bodyId', 'logoutWindowId')\"><i class=\"icon fa fa-edit\"></i>Settings </a>\r\n");
      out.write("      </li> -->\r\n");
      out.write("    </ul>\r\n");
      out.write("  </nav>\r\n");
      out.write("</aside>\r\n");
      out.write("<div id=\"containerId\">\r\n");
      out.write("\t\r\n");
      out.write("</div>\r\n");
      out.write(" \r\n");
      out.write("<script>\r\n");
      out.write("$(document).ready(function(){\r\n");
      out.write("$.ajax({\r\n");
      out.write("\turl:contextPath+\"/admin/adminType.htm\",\r\n");
      out.write("\t\tmethod: \"Post\",\r\n");
      out.write("\t\tdataType: \"json\",\r\n");
      out.write("\t\tcontentType: \"application/json\",\t\t\t\t\t\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\tsuccess:function(result){\r\n");
      out.write("\t\tif(result.success==true){\r\n");
      out.write("\t\t\tif(result.show==false){\r\n");
      out.write("\t\t\t\t$(\"#users\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t$(\"#settings\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t\t//$(\"#pmc\").css(\"display\", \"none\");\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t} else{\r\n");
      out.write("\t\t\talert(\"failure\");\r\n");
      out.write("\t\t}\r\n");
      out.write("  }});\r\n");
      out.write("  \r\n");
      out.write("\t$.ajax({\r\n");
      out.write("\t    url: contextPath+\"/admin/redirectProperty.htm\",\r\n");
      out.write("\t    type: \"post\",\r\n");
      out.write("\t    success: function(data){\r\n");
      out.write("\t  \t  $(\"#containerId\").html(data);\r\n");
      out.write("\t    },\r\n");
      out.write("\t    error:function(){\r\n");
      out.write("\t        $(\"#containerId\").html('There is error while submit');\r\n");
      out.write("\t    }\r\n");
      out.write("\t});\r\n");
      out.write("});\r\n");
      out.write("</script>\r\n");
      out.write(" <script src=\"");
      out.print(request.getContextPath());
      out.write("/resource/api/chosen/chosen.jquery.js\" type=\"text/javascript\"></script>\r\n");
      out.write("   <script src=\"");
      out.print(request.getContextPath());
      out.write("/resource/api/chosen/chosen.prism.js\" type=\"text/javascript\" charset=\"utf-8\"></script>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("\r\n");
      out.write("\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
