/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.37
 * Generated at: 2015-07-20 12:38:42 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.jsp.user.landlord.property;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class savePropertyDetails_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("   <link media=\"screen\" rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(request.getContextPath());
      out.write("/resource/css/ui-progress-bar.css\"/>\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resource/js/progress-bar/progressbar.js\"></script>\n");
      out.write("\t<script type=\"text/javascript\" src=\"");
      out.print(request.getContextPath());
      out.write("/resource/perpetual/property.js\"></script>\n");
      out.write("  <style>\n");
      out.write("  \n");
      out.write("\tlabel.error{\n");
      out.write("\t\tcolor: #FF0000;\n");
      out.write("\t\tfloat: right;\n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("</style> \n");
      out.write("  <script type=\"text/javascript\">\n");
      out.write("\t  $(document).ready(function(){\n");
      out.write("\t  \t $.getJSON(contextPath+\"/register/loadLandlordCities.htm\", function(options) {\n");
      out.write("\t   \t      var dropdown = $('#cityDropdown');\n");
      out.write("\t   \t      if (options) {\n");
      out.write("\t   \t    \t  var opts = options.data;\n");
      out.write("\t   \t    \t      dropdown.append($('<option/>').val('').text('Select City'));\n");
      out.write("\t   \t          $.each(opts, function(id, city) {\n");
      out.write("\t   \t        \t  dropdown.append($('<option/>').val(this.id).text(this.city));\n");
      out.write("\t   \t          });\n");
      out.write("\t   \t      \t\t $(\".loader\").css('height', $(document).height());\n");
      out.write("\t\t\t\t\t$(\".loader\").css('display', 'block');\n");
      out.write("\t\t   \t       setTimeout(function() {\n");
      out.write("\t\t   \t    \t\t setPropertyStatus();\n");
      out.write("\t\t   \t \t\t }, 500);\n");
      out.write("\t   \t      }\n");
      out.write("\t   \t });\n");
      out.write("\t  });\n");
      out.write("\t  \n");
      out.write("\t  function setPropertyStatus(){\n");
      out.write("\t\t  if(globalPropertyId){\n");
      out.write("\t\t\t    $.getJSON(contextPath+\"/property/propertyStatus.htm?propertyId=\"+globalPropertyId,\n");
      out.write("\t\t\t\t\t    function(response, textStatus, jqXHR){\n");
      out.write("\t\t\t    \t\t$(\".loader\").css('display', 'none');\n");
      out.write("\t\t\t  \t\t\t\tif(response){\n");
      out.write("\t\t\t  \t\t\t\t\tvar obj = response.status;\n");
      out.write("\t\t\t  \t\t\t\t\tprogressBar(obj, $('#progressBar'));\n");
      out.write("\t\t\t  \t\t\t\t\tif(obj == 100){\n");
      out.write("\t\t\t  \t\t\t\t\t\t$(\"#submitProperty\").show();\n");
      out.write("\t\t\t  \t\t\t\t\t}\n");
      out.write("\t\t\t  \t\t\t\t}\n");
      out.write("\t\t\t\t\t    }).fail(function(jqXHR, textStatus, errorThrown)\n");
      out.write("\t\t\t\t\t{\n");
      out.write("\t\t\t\t\t console.log(errorThrown);\n");
      out.write("\t\t\t\t});\n");
      out.write("\t\t}else{\n");
      out.write("\t\t\t$.getJSON(contextPath+\"/property/propertyStatus.htm\",\n");
      out.write("\t\t\t    function(response, textStatus, jqXHR){\n");
      out.write("\t\t\t\t\t$(\".loader\").css('display', 'none');\n");
      out.write("\t  \t\t\t\tif(response){\n");
      out.write("\t  \t\t\t\t\tvar obj = response.status;\n");
      out.write("\t  \t\t\t\t\tprogressBar(obj, $('#progressBar'));\t\t\t\t\t\t\t\t\n");
      out.write("\t  \t\t\t\t}\n");
      out.write("\t\t\t    });\n");
      out.write("\t}\n");
      out.write("\t  }\n");
      out.write("\t  \n");
      out.write("\t  $(function() {\n");
      out.write("\t\t    $(\"#fPropertyDetails\").validate({\n");
      out.write("\t\t        rules: {\n");
      out.write("\t\t        \t\"contactPerson\": \"required\",\n");
      out.write("\t\t        \t\"addressObj.addressLine1\" : \"required\",\n");
      out.write("\t\t        \t\"addressObj.city\" : \"required\",\n");
      out.write("\t\t        \t\"addressObj.county\" : \"required\",\n");
      out.write("\t\t        \t\"addressObj.postCode\" : \"required\",\n");
      out.write("    \t        \t/* \"addressObj.city\": {\n");
      out.write("    \t                required: {\n");
      out.write("    \t                    depends: function(element) {\n");
      out.write("    \t                       if(element.value == 0){\n");
      out.write("    \t                    \t   $(\"#cityErrorId\").show();\n");
      out.write("    \t                    \t   return false;\n");
      out.write("    \t                       }\n");
      out.write("    \t                    \t\n");
      out.write("    \t                    }\n");
      out.write("    \t                }\n");
      out.write("    \t            } */\n");
      out.write("    \t        \t\n");
      out.write("\t\t        },\n");
      out.write("\t\t    messages: {\n");
      out.write("\t\t    \t\"addressObj.city\": {\n");
      out.write("\t\t         required: \"Please Select City.\",\n");
      out.write("\t\t        },\n");
      out.write("\t\t       }\n");
      out.write("\t\t    });\n");
      out.write("\t\t  });\n");
      out.write("\t  \n");
      out.write("\t</script>\n");
      out.write("\n");
      out.write("    <div class=\"row\">\n");
      out.write("      <div class=\"span10\">\n");
      out.write("        <form name=\"fPropertyDetails\" id=\"fPropertyDetails\">\n");
      out.write("          <h1 class=\"heading3 margin-top15\">Property Details</h1>\n");
      out.write("          <div class=\"rg-p\">\n");
      out.write("            <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"regis\">\n");
      out.write("              <tr>\n");
      out.write("                <td><label>Contact Person</label>\n");
      out.write("                <input type=\"hidden\" value=\"\" id=\"propId\">\n");
      out.write("                  <input type=\"text\" placeholder=\"Contact Person\"  class=\"\" name=\"contactPerson\" id=\"contactPerson\" /></td>\n");
      out.write("                <td><label>Address Line 1</label>\n");
      out.write("                  <input type=\"text\" placeholder=\"Enter your address\" class=\"\" name=\"addressObj.addressLine1\" id=\"addressLine1\" /></td>\n");
      out.write("              </tr>\n");
      out.write("              <tr>\n");
      out.write("                <td><label>Address Line 2</label>\n");
      out.write("                  <input type=\"text\" placeholder=\"Enter your address\" class=\"\" name=\"addressObj.addressLine2\" id=\"addressLine2\" /></td>\n");
      out.write("                <td><label>City</label>\n");
      out.write("                <input type=\"text\" value=\"\" placeholder=\"Enter your city\" class=\"required\" readonly=\"readonly\" name=\"addressObj.city\" id=\"cityId\" style=\"display: none; background-color: #E0E0E0\" />\n");
      out.write("                  <select name=\"addressObj.city\" id=\"cityDropdown\" >\n");
      out.write("\t              </select>\n");
      out.write("\t            </td>\n");
      out.write("              </tr>\n");
      out.write("              <tr>\n");
      out.write("                  <td><label>County</label>\n");
      out.write("                  <input type=\"text\" placeholder=\"Enter your County\" class=\"\" name=\"addressObj.county\" id=\"county\" />\n");
      out.write("                  </td>\n");
      out.write("                <td><label>Post Code</label>\n");
      out.write("                  <input type=\"text\" placeholder=\"Enter your zip code\" class=\"\" name=\"addressObj.postCode\" id=\"postCode\" style=\"text-transform: uppercase;\" /></td>\n");
      out.write("              </tr>\n");
      out.write("            </table>\n");
      out.write("          </div>\n");
      out.write("          <div class=\"form-mg\"></div>\n");
      out.write("          <h1 class=\"heading3 margin-top15\">Agents Details (If Applicable)</h1>\n");
      out.write("          <div class=\"rg-p\">\n");
      out.write("            <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"regis\">\n");
      out.write("              <tr>\n");
      out.write("                <td><label>Agents Name</label>\n");
      out.write("                  <input type=\"text\" placeholder=\"Enter your agents name\" class=\"\" name=\"agentName\" id=\"agentName\" /></td>\n");
      out.write("                <td><label>Agents Address</label>\n");
      out.write("                  <input type=\"text\" placeholder=\"Enter your agents address\" class=\"\" name=\"agentAddressLine\" id=\"agentAddressLine\" /></td>\n");
      out.write("              </tr>\n");
      out.write("              <tr>\n");
      out.write("                <td><label>Agents Phone No.</label>\n");
      out.write("                  <input type=\"text\" placeholder=\"Enter your agents contact no.\" class=\"\" name=\"agentContactNumber\" id=\"agentContactNumber\" /></td>\n");
      out.write("                <td>&nbsp;</td>\n");
      out.write("              </tr>\n");
      out.write("            </table>\n");
      out.write("          </div>\n");
      out.write("          <div class=\"form-mg\"></div>\n");
      out.write("          <h1 style=\"display: none;\" id=\"statusHeadingId\" class=\"heading3 margin-top15\">Status</h1>\n");
      out.write("         \n");
      out.write("            <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
      out.write("              <tr id=\"statusBarId\" style=\"display: none;\">\n");
      out.write("                <td style=\"width: 92%\"> <div id=\"progressBar\" class=\"round-pink\" ><div></div></div></td>\n");
      out.write("                <td style=\" width: 8%\">\n");
      out.write("                \t <input style=\"margin:-5px -3px -23px 0 !important; float: right!important; padding: 8px 18px;\" type=\"button\" id=\"updatePropertyDetails\" class=\"regis-co\" value=\"Edit\" >\n");
      out.write("        \t\t</td>\n");
      out.write("              </tr>\n");
      out.write("            </table>\n");
      out.write("          \n");
      out.write("           <div class=\"form-mg\"></div>\n");
      out.write("           <input type=\"button\" id=\"submitProperty\" class=\"regis-co\" value=\"Submit and Review\" style=\"display:none;\">\n");
      out.write("          <input type=\"button\" id=\"savePropertyDetails\" class=\"regis-co\" value=\"Submit and Continue\">\n");
      out.write("        </form>\n");
      out.write("      </div>\n");
      out.write("    </div>\n");
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
