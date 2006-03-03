/*
 * Copyright 1999,2006 The Apache Software Foundation.
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
package org.apache.xmlrpc.metadata;

import org.apache.xmlrpc.XmlRpcException;


/** This class implements the various "system" calls,
 * as specifies by {@link XmlRpcListableHandlerMapping}.
 * Suggested use is to create an instance and add it to
 * the handler mapping with the "system" prefix.
 */
public class XmlRpcSystemImpl {
	private XmlRpcListableHandlerMapping mapping;

	/** Creates a new instance, which provides meta data
	 * for the given handler mappings methods.
	 */
	public XmlRpcSystemImpl(XmlRpcListableHandlerMapping pMapping) {
		mapping = pMapping;
	}

	/** Implements the "system.methodSignature" call.
	 * @see XmlRpcListableHandlerMapping#getMethodSignature(String)
	 */
	public String[][] methodSignature(String methodName) throws XmlRpcException {
		return mapping.getMethodSignature(methodName);
	}

	/** Implements the "system.methodHelp" call.
	 * @see XmlRpcListableHandlerMapping#getMethodHelp(String)
	 */
	public String methodHelp(String methodName) throws XmlRpcException {
		return mapping.getMethodHelp(methodName);
	}

	/** Implements the "system.listMethods" call.
	 * @see XmlRpcListableHandlerMapping#getListMethods()
	 */
	public String[] listMethods() throws XmlRpcException {
		return mapping.getListMethods();
	}
}
