/*
 * Copyright (C) 2003-2013 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.chromattic;

import javax.portlet.PortletMode;

import org.exoplatform.webui.application.WebuiApplication;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.application.portlet.PortletRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.core.UIPopupContainer;
import org.exoplatform.webui.core.UIPortletApplication;
import org.exoplatform.webui.core.lifecycle.UIApplicationLifecycle;

/**
 * @author <a href="mailto:sondn@exoplatform.com">Ngoc Son Dang</a>
 * @since Jul 5, 2013
 * @version 
 * 
 * @tag 
 */
@ComponentConfig(lifecycle = UIApplicationLifecycle.class)
public class UIUploadPortlet extends UIPortletApplication {

	public UIUploadPortlet() throws Exception {
		super();
		
		addChild(UIPopupContainer.class, null, null);
	}

	public void processRender(WebuiApplication app, WebuiRequestContext context) throws Exception {
		PortletRequestContext pContext = (PortletRequestContext) context ;
        PortletMode currentMode = pContext.getApplicationMode() ;
        if(PortletMode.VIEW.equals(currentMode)) {
        	removeChild(UIUploadConfig.class);
            if (getChild(UIUploadForm.class) == null) addChild(UIUploadForm.class, null, null);
        } else {
        	removeChild(UIUploadForm.class);
            if (getChild(UIUploadConfig.class) == null) addChild(UIUploadConfig.class, null, null);
        }
        
        super.processRender(app, context) ;
	}
}
