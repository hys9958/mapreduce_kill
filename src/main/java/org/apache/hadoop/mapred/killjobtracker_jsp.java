package org.apache.hadoop.mapred;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.SkipPageException;

import org.apache.hadoop.util.ServletUtil;
import org.apache.hadoop.util.StringUtils;

public class killjobtracker_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

	private static final long serialVersionUID = 1L;  
  private static java.util.List _jspx_dependants;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      out.write('\n');
      out.write('\n');
      out.write('\n');
      
  JobTracker tracker = (JobTracker) application.getAttribute("job.tracker");
  String trackerName = 
           StringUtils.simpleHostname(tracker.getJobTrackerMachine());

  List<JobInProgress> runningJobs = tracker.getRunningJobs();
      out.write('\n');
      out.write("\n\n\n<html>\n<head>\n<title>");
      out.print( trackerName );
      out.write(" Job Kill Manager</title>\n<link rel=\"stylesheet\" type=\"text/css\" href=\"/static/hadoop.css\">\n\n</head>\n<body>\n\n");
      KillUtil.killAction(request, response, tracker);
      out.write("\n\n<h1>");
      out.print( trackerName );
      out.write(" Job Kill Manager</h1>");
      out.write("\n<hr>\n\n<h2 id=\"running_jobs\">Running Jobs</h2>\n");
      String running = JSPUtil.generateJobTable("Running", runningJobs, 30, 0, tracker.conf);
      String runningTable[] = running.split("</tr>");
      int i = 0;
      if(runningTable.length<3 || JSPUtil.privateActionsAllowed(tracker.conf)){
    	  out.print(running);
      }else{
    	  List<JobID> jobId = new ArrayList<JobID>();
          for (Iterator<JobInProgress> it = runningJobs.iterator(); it.hasNext();) {
              JobInProgress job = it.next();
              JobProfile profile = job.getProfile();
              jobId.add(profile.getJobID());
          }
          int rowId = 0;
          while(i<runningTable.length){
        	  if(0==i){
        		  out.print(runningTable[i]+"<td><b>Job Kill</b></td></tr>");
        	  }else if(i%3 == 0){
        		  out.print(runningTable[i]+"<td><input type='button' style=font-size:9pt;height:25; value='kill!' onclick=\"JavaScript:location.href='/killjob?jobId="+jobId.get(rowId)+"'\" /></td></tr>");
        		  rowId++;
        	  }else {
        		  out.print(runningTable[i]+"</tr>");
        	  }
        	  i++;
          }  
      }
      out.write("\n\n");
      out.println(ServletUtil.htmlFooter() + "AND hys9958");
      out.write('\n');
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}

