/*
 * Copyright 1999,2005 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.xmlrpc.webserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.xmlrpc.server.XmlRpcStreamServer;
import org.apache.xmlrpc.util.ThreadPool;


/** A subclass of {@link WebServer}, which emulates a servlet
 * container. Mainly useful for debugging.
 */
public class ServletWebServer extends WebServer {
	/** This exception is thrown by the request handling classes,
	 * advising the server, that it should return an error response.
	 */
	public static class Exception extends IOException {
		private static final long serialVersionUID = 49879832748972394L;
		private final int statusCode;
		private final String description;

		/** Creates a new instance.
		 * @param pStatusCode The HTTP status code being sent to the client.
		 * @param pMessage The HTTP status message being sent to the client.
		 * @param pDescription The error description being sent to the client
		 * in the response body.
		 */
		public Exception(int pStatusCode, String pMessage, String pDescription) {
			super(pMessage);
			statusCode = pStatusCode;
			description = pDescription;
		}

		public String getMessage() { return statusCode + " " + super.getMessage(); }

		/** Returns the error description. The server will send the description
		 * as plain text in the response body.
		 * @return The error description.
		 */
		public String getDescription() { return description; }

		/** Returns the HTTP status code.
		 * @return The status code.
		 */
		public int getStatusCode() { return statusCode; }
	}

	private final HttpServlet servlet;

	/** Creates a new instance, which is listening on all
	 * local IP addresses and the given port.
	 * @param pServlet The servlet, which is handling requests.
	 * @param pPort The servers port number; 0 for a random
	 * port being choosen.
	 * @throws ServletException Initializing the servlet failed.
	 */
	public ServletWebServer(HttpServlet pServlet, int pPort) throws ServletException {
		this(pServlet, pPort, null);
	}

	/** Creates a new instance, which is listening on the
	 * given IP address and the given port.
	 * @param pServlet The servlet, which is handling requests.
	 * @param pPort The servers port number; 0 for a random
	 * port being choosen.
	 * @param pAddr The servers IP address.
	 * @throws ServletException Initializing the servlet failed.
	 */
	public ServletWebServer(HttpServlet pServlet, int pPort, InetAddress pAddr)
			throws ServletException {
		super(pPort, pAddr);
		servlet = pServlet;
		servlet.init(new ServletConfig(){
			public String getServletName() { return servlet.getClass().getName(); }
			public ServletContext getServletContext() {
				throw new IllegalStateException("Context not available");
			}
			public String getInitParameter(String pArg0) {
				return null;
			}
		
			public Enumeration getInitParameterNames() {
				return new Enumeration(){
					public boolean hasMoreElements() { return false; }
					public Object nextElement() {
						throw new NoSuchElementException();
					}
				};
			}
			
		});
	}

	protected ThreadPool.Task newTask(WebServer pWebServer,
									  XmlRpcStreamServer pXmlRpcServer,
									  Socket pSocket) throws IOException {
		return new ServletConnection(servlet, pSocket);
	}
}