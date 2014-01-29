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

/**
 * This matcher will returns <code>true</code> for all {@link SNIServerName}s.
 * 
 * @author <a href="mailto:dilip.kumar@imaginea.com">Dilip Kumar Gundu</a>
 * @version $Revision: $, $Date: $, $Author: $
 * @since Jan 29, 2014
 * 
 */
public class AcceptableSNIMatcher extends SNIMatcher {

	public AcceptableSNIMatcher() {
		super(StandardConstants.SNI_HOST_NAME);
	}

	@Override
	public boolean matches(SNIServerName serverName) {
		return true;
	}

}
