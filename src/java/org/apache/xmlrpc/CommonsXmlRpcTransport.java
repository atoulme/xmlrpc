package org.apache.xmlrpc;

/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "XML-RPC" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * Implementor of the XmlRpcTransport interface using the Apache
 * Commons HttpClient library v2.0 available at
 * http://jakarta.apache.org/commons/httpclient/
 *
 * Note: <b>Currently this transport is not thread safe</b>
 *
 * @author <a href="mailto:rhoegg@isisnetworks.net">Ryan Hoegg</a>
 * @version $Id$
 * @since 1.2
 */
public class CommonsXmlRpcTransport implements XmlRpcTransport {
    
    /** Creates a new instance of CommonsXmlRpcTransport */
    public CommonsXmlRpcTransport(URL url, HttpClient client) {
        this.url = url;
        if (client == null) {
            HttpClient newClient = new HttpClient();
            this.client = newClient;
        } else {
            this.client = client;
        }
    }
    
    public CommonsXmlRpcTransport(URL url) {
        this(url, null);
    }
    
    private URL url;
    private HttpClient client;
    private final Header userAgentHeader = new Header("User-Agent", XmlRpc.version);
    private boolean http11 = false; // defaults to HTTP 1.0
    
    public InputStream sendXmlRpc(byte[] request) throws IOException, XmlRpcClientException {
        PostMethod method = new PostMethod(url.toString());
        method.setHttp11(http11);
        method.setRequestHeader(new Header("Content-Type", "text/xml"));
        method.setRequestHeader(userAgentHeader);
        // TODO: authentication not implemented yet
        method.setRequestBody(new ByteArrayInputStream(request));
        URI hostURI = new URI(url);
        HostConfiguration hostConfig = new HostConfiguration();
        hostConfig.setHost(hostURI);
        client.executeMethod(hostConfig, method);
        return method.getResponseBodyAsStream();
    }
    
    public void setHttp11(boolean http11) {
        this.http11 = http11;
    }
    
    public void setUserAgent(String userAgent) {
        userAgentHeader.setValue(userAgent);
    }
}