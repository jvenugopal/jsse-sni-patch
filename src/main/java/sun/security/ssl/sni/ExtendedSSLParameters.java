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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;

/**
 * {@link ExtendedSSLParameters} is the extended class of {@link SSLParameters}
 * to support SNI.
 * 
 * @author <a href="mailto:dilip.kumar@imaginea.com">Dilip Kumar Gundu</a>
 * @version $Revision: $, $Date: $, $Author: $
 * @since Jan 29, 2014
 * 
 */
public class ExtendedSSLParameters extends SSLParameters {
	private Map<Integer, SNIServerName> sniNames = null;
	private Map<Integer, SNIMatcher> sniMatchers = null;

	/**
	 * It will creates new/converts to {@link ExtendedSSLParameters} with given
	 * {@link SSLParameters} object.
	 * 
	 * @param params
	 */
	public ExtendedSSLParameters(SSLParameters params) {
		setAlgorithmConstraints(params.getAlgorithmConstraints());
		setCipherSuites(params.getCipherSuites());
		setEndpointIdentificationAlgorithm(params
				.getEndpointIdentificationAlgorithm());
		setNeedClientAuth(params.getNeedClientAuth());
		setProtocols(params.getProtocols());
		setWantClientAuth(params.getWantClientAuth());
	}

	/**
	 * Sets the desired {@link SNIServerName}s of the Server Name Indication
	 * (SNI) parameter.
	 * <P>
	 * This method is only useful to {@link SSLSocket}s or {@link SSLEngine}s
	 * operating in client mode.
	 * <P>
	 * Note that the {@code serverNames} list is cloned to protect against
	 * subsequent modification.
	 * 
	 * @param serverNames
	 *            the list of desired {@link SNIServerName}s (or null)
	 * 
	 * @throws NullPointerException
	 *             if the {@code serverNames} contains {@code null} element
	 * @throws IllegalArgumentException
	 *             if the {@code serverNames} contains more than one name of the
	 *             same name type
	 * 
	 * @see SNIServerName
	 * @see #getServerNames()
	 * 
	 * @since 1.8
	 */
	public final void setServerNames(List<SNIServerName> serverNames) {
		if (serverNames != null) {
			if (!serverNames.isEmpty()) {
				sniNames = new LinkedHashMap<>(serverNames.size());
				for (SNIServerName serverName : serverNames) {
					if (sniNames.put(serverName.getType(), serverName) != null) {
						throw new IllegalArgumentException(
								"Duplicated server name of type "
										+ serverName.getType());
					}
				}
			} else {
				sniNames = Collections.<Integer, SNIServerName> emptyMap();
			}
		} else {
			sniNames = null;
		}
	}

	/**
	 * Returns a {@link List} containing all {@link SNIServerName}s of the
	 * Server Name Indication (SNI) parameter, or null if none has been set.
	 * <P>
	 * This method is only useful to {@link SSLSocket}s or {@link SSLEngine}s
	 * operating in client mode.
	 * <P>
	 * For SSL/TLS connections, the underlying SSL/TLS provider may specify a
	 * default value for a certain server name type. In client mode, it is
	 * recommended that, by default, providers should include the server name
	 * indication whenever the server can be located by a supported server name
	 * type.
	 * <P>
	 * It is recommended that providers initialize default Server Name
	 * Indications when creating {@code SSLSocket}/{@code SSLEngine}s. In the
	 * following examples, the server name could be represented by an instance
	 * of {@link SNIHostName} which has been initialized with the hostname
	 * "www.example.com" and type {@link StandardConstants#SNI_HOST_NAME}.
	 * 
	 * <pre>
	 * Socket socket = sslSocketFactory.createSocket(&quot;www.example.com&quot;, 443);
	 * </pre>
	 * 
	 * or
	 * 
	 * <pre>
	 * SSLEngine engine = sslContext.createSSLEngine(&quot;www.example.com&quot;, 443);
	 * </pre>
	 * <P>
	 * 
	 * @return null or an immutable list of non-null {@link SNIServerName}s
	 * 
	 * @see List
	 * @see #setServerNames(List)
	 * 
	 * @since 1.8
	 */
	public final List<SNIServerName> getServerNames() {
		if (sniNames != null) {
			if (!sniNames.isEmpty()) {
				return Collections
						.<SNIServerName> unmodifiableList(new ArrayList<>(
								sniNames.values()));
			} else {
				return Collections.<SNIServerName> emptyList();
			}
		}

		return null;
	}

	/**
	 * Sets the {@link SNIMatcher}s of the Server Name Indication (SNI)
	 * parameter.
	 * <P>
	 * This method is only useful to {@link SSLSocket}s or {@link SSLEngine}s
	 * operating in server mode.
	 * <P>
	 * Note that the {@code matchers} collection is cloned to protect against
	 * subsequent modification.
	 * 
	 * @param matchers
	 *            the collection of {@link SNIMatcher}s (or null)
	 * 
	 * @throws NullPointerException
	 *             if the {@code matchers} contains {@code null} element
	 * @throws IllegalArgumentException
	 *             if the {@code matchers} contains more than one name of the
	 *             same name type
	 * 
	 * @see Collection
	 * @see SNIMatcher
	 * @see #getSNIMatchers()
	 * 
	 * @since 1.8
	 */
	public final void setSNIMatchers(Collection<SNIMatcher> matchers) {
		if (matchers != null) {
			if (!matchers.isEmpty()) {
				sniMatchers = new HashMap<>(matchers.size());
				for (SNIMatcher matcher : matchers) {
					if (sniMatchers.put(matcher.getType(), matcher) != null) {
						throw new IllegalArgumentException(
								"Duplicated server name of type "
										+ matcher.getType());
					}
				}
			} else {
				sniMatchers = Collections.<Integer, SNIMatcher> emptyMap();
			}
		} else {
			sniMatchers = null;
		}
	}

	/**
	 * Returns a {@link Collection} containing all {@link SNIMatcher}s of the
	 * Server Name Indication (SNI) parameter, or null if none has been set.
	 * <P>
	 * This method is only useful to {@link SSLSocket}s or {@link SSLEngine}s
	 * operating in server mode.
	 * <P>
	 * For better interoperability, providers generally will not define default
	 * matchers so that by default servers will ignore the SNI extension and
	 * continue the handshake.
	 * 
	 * @return null or an immutable collection of non-null {@link SNIMatcher}s
	 * 
	 * @see SNIMatcher
	 * @see #setSNIMatchers(Collection)
	 * 
	 * @since 1.8
	 */
	public final Collection<SNIMatcher> getSNIMatchers() {
		if (sniMatchers != null) {
			if (!sniMatchers.isEmpty()) {
				return Collections
						.<SNIMatcher> unmodifiableList(new ArrayList<>(
								sniMatchers.values()));
			} else {
				return Collections.<SNIMatcher> emptyList();
			}
		}

		return null;
	}
}
