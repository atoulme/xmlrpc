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
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcTransportFactory;
import org.apache.xmlrpc.server.XmlRpcHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;


/** Abstract base implementation of {@link org.apache.xmlrpc.test.ClientProvider}.
 */
public abstract class ClientProviderImpl implements ClientProvider {
	protected final XmlRpcHandlerMapping mapping;

	protected abstract XmlRpcTransportFactory getTransportFactory(XmlRpcClient pClient);

	/** Creates a new instance.
	 * @param pMapping The test servers handler mapping.
	 */
	protected ClientProviderImpl(XmlRpcHandlerMapping pMapping) {
		mapping = pMapping;
	}

	protected XmlRpcServer getXmlRpcServer() throws Exception {
		XmlRpcServer server = new XmlRpcServer();
		server.setHandlerMapping(mapping);
		return server;
	}

	public XmlRpcClientConfigImpl getConfig() throws Exception {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		return config;
	}

	public XmlRpcClient getClient() {
		XmlRpcClient client = new XmlRpcClient();
		client.setTransportFactory(getTransportFactory(client));
		return client;
	}

}