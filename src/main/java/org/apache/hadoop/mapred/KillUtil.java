package org.apache.hadoop.mapred;

import java.io.IOException;
import java.security.PrivilegedExceptionAction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.hadoop.security.AccessControlException;
import org.apache.hadoop.security.UserGroupInformation;

public class KillUtil {
	public static void killAction(HttpServletRequest request,
			HttpServletResponse response, final JobTracker tracker)
			throws IOException, InterruptedException, ServletException {
		String user = request.getRemoteUser();
		if (!JSPUtil.privateActionsAllowed(tracker.conf)) {
			String jobId = request.getParameter("jobId");
			if (jobId != null) {
				boolean notAuthorized = false;
				String errMsg = "User " + user
						+ " failed to kill the following job(s)!<br><br>";
					final JobID jobID = JobID.forName(jobId);
					if (user != null) {
						UserGroupInformation ugi = UserGroupInformation
								.createRemoteUser(user);
						try {
							ugi.doAs(new PrivilegedExceptionAction<Void>() {
								public Void run() throws IOException {

									tracker.killJob(jobID);// checks job modify
															// permission
									return null;
								}
							});
						} catch (AccessControlException e) {
							errMsg = errMsg.concat("<br>" + e.getMessage());
							notAuthorized = true;
							// We don't return right away so that we can try
							// killing other
							// jobs that are requested to be killed.
						}
					} else {// no authorization needed
						tracker.killJob(jobID);
					}
				if (notAuthorized) {// user is not authorized to kill some/all
									// of jobs
					errMsg = errMsg
							.concat("<br><hr><a href=\"jobtracker.jsp\">Go back to JobTracker</a><br>");
					setErrorAndForward(errMsg, request, response);
					return;
				}
				response.sendRedirect("/killjob");
			}
		}
	}

	public static void setErrorAndForward(String errMsg,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("error.msg", errMsg);
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/job_authorization_error.jsp");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		dispatcher.forward(request, response);
	}

}
