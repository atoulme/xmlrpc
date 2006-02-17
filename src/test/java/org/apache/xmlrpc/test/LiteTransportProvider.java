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
package org.apache.xmlrpc.test;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcLiteHttpTransportFactory;
import org.apache.xmlrpc.client.XmlRpcTransportFactory;
import org.apache.xmlrpc.server.XmlRpcHandlerMapping;


/** Provider for testing the
 * {@link org.apache.xmlrpc.client.XmlRpcLiteHttpTransport}.
 */
public class LiteTransportProvider extends WebServerProvider {
	/** Creates a new instance.
	 * @param pMapping The test servers handler mapping.
	 * @param pContentLength Whether a Content-Length header is required.
	 */
	public LiteTransportProvider(XmlRpcHandlerMapping pMapping,
								 boolean pContentLength) {
		super(pMapping, pContentLength);
	}

	protected XmlRpcTransportFactory getTransportFactory(XmlRpcClient pClient) {
		return new XmlRpcLiteHttpTransportFactory(pClient);
	}
}