/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.37
 * Generated at: 2015-07-15 11:39:56 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp.admin.pdProperty;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class pdPropertyDetail_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

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

      out.write(" <style>\r\n");
      out.write(" \t.heading{\r\n");
      out.write("\t\ttext-transform: uppercase;\r\n");
      out.write("\t\ttext-decoration: underline;\r\n");
      out.write("\t\tfont-size: 20px!important;\r\n");
      out.write("\t}\r\n");
      out.write(" </style>\r\n");
      out.write(" <script src=\"");
      out.print(request.getContextPath());
      out.write("/resource/perpetual/admin/pdProperty/pdPropertyDetails.js\"></script>\r\n");
      out.write(" <script src=\"");
      out.print(request.getContextPath());
      out.write("/resource/perpetual/admin/property/property-validation.js\"></script>\r\n");
      out.write(" <script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resource/js/progress-bar/progressbar.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(request.getContextPath());
      out.write("/resource/perpetual/admin/property/property.js\"></script>\r\n");
      out.write("\r\n");
      out.write(" \r\n");
      out.write("<script type=\"text/javascript\" >\r\n");
      out.write("\t$(document).ready(function(){\r\n");
      out.write("\t\t//alert('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pdId}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("');\r\n");
      out.write("\t\tpdProperty.Add('");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pdId}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("');\r\n");
      out.write("\t});\r\n");
      out.write("</script> \r\n");
      out.write(" \r\n");
      out.write(" <div class=\"container-ar\">\r\n");
      out.write("  <section class=\"contant-area\">\r\n");
      out.write("  \t\t<div id=\"errorId\" class=\"alert alert-block alert-error fade in\" hidden=\"true\" >\r\n");
      out.write("            <button id=\"alert\" type=\"button\" class=\"close\"  ><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\"></span></button>\r\n");
      out.write("            <h4 class=\"alert-heading\" animation=\"true\" id=\"errorId\">You got an error!</h4>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div id=\"alertSuccessId\" class=\"alert alert-block alert-success fade in\" style=\"display: none;\" >\r\n");
      out.write("            <button id=\"alert\" type=\"button\" class=\"close\"  ><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\"></span></button>\r\n");
      out.write("            <h4 class=\"alert-heading\" animation=\"true\" id=\"successMessageId\"></h4>\r\n");
      out.write("      </div>\r\n");
      out.write("    <div class=\"button\"><button id=\"allPDPropertiesId\">Back</button>\r\n");
      out.write("    <span>&nbsp;&nbsp;&nbsp;</span><button id=\"viewDocumentsId\">View Documents</button>\r\n");
      out.write("    <button id=\"approvePropertyId\">Approve</button>\r\n");
      out.write("    <span>&nbsp;&nbsp;&nbsp;</span><button id=\"disApprovePropertyId\" style=\"display: none;\">Dis-Approve</button>\r\n");
      out.write("    <button id=\"viewPDConversationId\">View Conversation</button>\r\n");
      out.write("    <button id=\"uploadDocumentsId\">Upload Documents</button>\r\n");
      out.write("    <button id=\"viewPdPropertyMapId\">View Map</button>\r\n");
      out.write("    <button id=\"downloadPropertyFormId\">Download PropertyDetail as PDF</button>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div class=\"rgpinnew\">\r\n");
      out.write("          <ul>\r\n");
      out.write("            <li><strong class=\"heading\">Property Information</strong></li>\r\n");
      out.write("            <li><strong>Contact Person</strong><samp>:</samp><span id=\"contactPerson\"></span></li>\r\n");
      out.write("            <li><strong>Address</strong><samp>:</samp><span id=\"address\"></span></li>\r\n");
      out.write("            <li><strong>City</strong><samp>:</samp><span id=\"city\"></span></li>\r\n");
      out.write("            <li><strong>County</strong><samp>:</samp><span id=\"county\"></span></li>\r\n");
      out.write("            <li><strong>Post Code</strong><samp>:</samp><span id=postCode></span></li>\r\n");
      out.write("            <li><strong>Status</strong><samp>:</samp><span id=status></span></li> \r\n");
      out.write("            <li><strong>Created On</strong><samp>:</samp><span id=\"createdOn\"></span></li>\r\n");
      out.write("            <li><strong>Created By</strong><samp>:</samp><span id=\"createdBy\"></span></li>\r\n");
      out.write("            <li><strong>Modified On</strong><samp>:</samp><span id=\"modifiedOn\"></span></li>\r\n");
      out.write("            <li><strong>Modified By</strong><samp>:</samp><span id=\"modifiedBy\"></span></li>\r\n");
      out.write("            \r\n");
      out.write("          </ul>\r\n");
      out.write("          <input type=\"hidden\" id=propertyId></input>\r\n");
      out.write("          <div class=\"clearfix\"></div>\r\n");
      out.write("        </div> \r\n");
      out.write("        \r\n");
      out.write("        <div class=\"rgpinnew\">\r\n");
      out.write("          <ul>\r\n");
      out.write("            <li><strong class=\"heading\">Property Developer Information</strong></li>\r\n");
      out.write("            <li><strong>Name </strong><samp>:</samp><span id=\"pdName\"></span></li>\r\n");
      out.write("            <li><strong>UserName</strong><samp>:</samp><span id=\"pdUserName\"></span></li>\r\n");
      out.write("            <li><strong>Password</strong><samp>:</samp><span id=\"pdPassword\"></span></li>\r\n");
      out.write("            <li><strong>Reference</strong><samp>:</samp><span id=\"pdReference\"></span></li>\r\n");
      out.write("            <li><strong>Address</strong><samp>:</samp><span id=\"pdAddress\"></span></li>\r\n");
      out.write("            <li><strong>Post Code</strong><samp>:</samp><span id=\"pdPostCode\"></span></li>\r\n");
      out.write("            <li><strong>City</strong><samp>:</samp><span id=pdCity></span></li>\r\n");
      out.write("            <li><strong>County</strong><samp>:</samp><span id=\"pdCounty\"></span></li>\r\n");
      out.write("            <li><strong>Primary Phone Number</strong><samp>:</samp><span id=\"pdPrimaryPhoneNumber\"></span></li>\r\n");
      out.write("            <li><strong>Secondary Phone Number</strong><samp>:</samp><span id=\"pdSecondaryPhoneNumber\"></span></li>\r\n");
      out.write("            <li><strong>Primary Email Id</strong><samp>:</samp><span id=\"pdPrimaryEmailId\"></span></li>\r\n");
      out.write("            <li><strong>Secondary Email Id</strong><samp>:</samp><span id=\"pdSecondaryEmailId\"></span></li>\r\n");
      out.write("            </ul>\r\n");
      out.write("          <div class=\"clearfix\"></div>\r\n");
      out.write("        </div> \r\n");
      out.write("  </section>\r\n");
      out.write("  </div>");
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
