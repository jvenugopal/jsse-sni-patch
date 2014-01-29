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

import java.net.Socket;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

/**
 * Utility class for reading sni information from {@link Socket} and {@link SSLEngine}.
 * @author <a href="mailto:dilip.kumar@imaginea.com">Dilip Kumar Gundu</a>
 * @version $Revision:   $, $Date:   $, $Author:  $
 * @since Jan 29, 2014
 *
 */
public class Utils {

	public Utils() {

	}

	/**
	 * Returns the list of {@link SNIServerName}s from session.
	 * 
	 * @param socket
	 * @return
	 */
	public static List<SNIServerName> getRequestedServerNames(Socket socket) {
		if (socket != null && socket.isConnected()
				&& socket instanceof SSLSocket) {
			SSLSocket sslSocket = (SSLSocket) socket;
			SSLSession session = sslSocket.getHandshakeSession();

			if (session != null && (session instanceof SNIExtendedSSLSession)) {
				SNIExtendedSSLSession extSession = (SNIExtendedSSLSession) session;
				return extSession.getRequestedServerNames();
			}
		}

		return Collections.<SNIServerName> emptyList();
	}

	/**
	 * Returns the list of {@link SNIServerName}s from session.
	 * 
	 * @param engine
	 * @return
	 */
	public static List<SNIServerName> getRequestedServerNames(SSLEngine engine) {
		if (engine != null) {
			SSLSession session = engine.getHandshakeSession();

			if (session != null && (session instanceof SNIExtendedSSLSession)) {
				SNIExtendedSSLSession extSession = (SNIExtendedSSLSession) session;
				return extSession.getRequestedServerNames();
			}
		}

		return Collections.<SNIServerName> emptyList();
	}
}
