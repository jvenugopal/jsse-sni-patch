/*
 * Copyright (c) 2006 Pramati Technologies Private Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Pramati
 * Technologies. You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the source code
 * license agreement you entered into with Pramati Technologies.
 *
 */

package sun.security.ssl.sni;

import java.util.List;

import javax.net.ssl.ExtendedSSLSession;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;

/**
 * {@link SNIExtendedSSLSession} for support of SNI fields in ssl session.
 * 
 * @author <a href="mailto:dilip.kumar@imaginea.com">Dilip Kumar Gundu</a>
 * @version $Revision: $, $Date: $, $Author: $
 * @since Jan 29, 2014
 * 
 */
public abstract class SNIExtendedSSLSession extends ExtendedSSLSession {
	/**
	 * Obtains a {@link List} containing all {@link SNIServerName}s of the
	 * requested Server Name Indication (SNI) extension.
	 * <P>
	 * In server mode, unless the return {@link List} is empty, the server
	 * should use the requested server names to guide its selection of an
	 * appropriate authentication certificate, and/or other aspects of security
	 * policy.
	 * <P>
	 * In client mode, unless the return {@link List} is empty, the client
	 * should use the requested server names to guide its endpoint
	 * identification of the peer's identity, and/or other aspects of security
	 * policy.
	 * 
	 * @return a non-null immutable list of {@link SNIServerName}s of the
	 *         requested server name indications. The returned list may be empty
	 *         if no server name indications were requested.
	 * @throws UnsupportedOperationException
	 *             if the underlying provider does not implement the operation
	 * 
	 * @see SNIServerName
	 * @see X509ExtendedTrustManager
	 * @see X509ExtendedKeyManager
	 * 
	 * @since 1.8
	 */
	public List<SNIServerName> getRequestedServerNames() {
		throw new UnsupportedOperationException();
	}
}
