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

import javax.portlet.PortletPreferences;

import org.exoplatform.ecm.webui.selector.UISelectable;
import org.exoplatform.wcm.webui.Utils;
import org.exoplatform.wcm.webui.selector.content.UIContentSelector;
import org.exoplatform.wcm.webui.selector.content.folder.UIContentBrowsePanelFolder;
import org.exoplatform.wcm.webui.selector.content.folder.UIContentSelectorFolder;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.application.portlet.PortletRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.UIPopupContainer;
import org.exoplatform.webui.core.lifecycle.UIFormLifecycle;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.exoplatform.webui.form.UIForm;
import org.exoplatform.webui.form.UIFormStringInput;

/**
 * @author <a href="mailto:sondn@exoplatform.com">Ngoc Son Dang</a>
 * @since Jul 5, 2013
 * @version 
 * 
 * @tag 
 */
@ComponentConfig(
		lifecycle = UIFormLifecycle.class,
	    template = "classpath:groovy/webui/UIUploadConfig.gtmpl",
	    events = {
	        @EventConfig(listeners = UIUploadConfig.SaveActionListener.class),
	        @EventConfig(listeners = UIUploadConfig.SelectFolderPathActionListener.class)
	    }
	)
public class UIUploadConfig extends UIForm implements UISelectable {

	/**
	 * This is popup's ID
	 */
	private String popupId;
	
	
	public String getPopupId() {
		return popupId;
	}

	public void setPopupId(String popupId) {
		this.popupId = popupId;
	}
	
	public UIUploadConfig() throws Exception {
		UIFormStringInput formStringInput = new UIFormStringInput("path", "path", null);
		addUIFormInput(formStringInput);
		
		PortletRequestContext portletRequestContext = WebuiRequestContext.getCurrentInstance();
        PortletPreferences portletPreferences = portletRequestContext.getRequest().getPreferences();
        String galleryPath = portletPreferences.getValue("galleryPath", "");
        
        formStringInput.setValue(galleryPath);
	}
	
	public static class SaveActionListener extends EventListener<UIUploadConfig> {

		@Override
		public void execute(Event<UIUploadConfig> event) throws Exception {
			UIUploadConfig uploadConfig = event.getSource();
            UIFormStringInput formStringInput = uploadConfig.getUIStringInput("path");
			
            PortletRequestContext portletRequestContext = WebuiRequestContext.getCurrentInstance();
            PortletPreferences portletPreferences = portletRequestContext.getRequest().getPreferences();
            portletPreferences.setValue("galleryPath", formStringInput.getValue());            
            portletPreferences.store();
		}
	}
	
	public static class SelectFolderPathActionListener extends EventListener<UIUploadConfig> {
		@Override
		public void execute(Event<UIUploadConfig> event) throws Exception {
			UIUploadConfig uploadConfig = event.getSource();
			UIContentSelectorFolder contentSelectorFolder = uploadConfig.createUIComponent(UIContentSelectorFolder.class, null, null, null);
			
			contentSelectorFolder.init("collaboration", "/");
			contentSelectorFolder.getChild(UIContentBrowsePanelFolder.class).setSourceComponent(uploadConfig, new String[] {"path"});
		
			// Create a popup windown
			Utils.createPopupWindow(uploadConfig, contentSelectorFolder, UIContentSelector.CORRECT_CONTENT_SELECTOR_POPUP_WINDOW, 800);
			UIUploadConfig uiUploadConfig = event.getSource();
			uiUploadConfig.setPopupId(UIContentSelector.CORRECT_CONTENT_SELECTOR_POPUP_WINDOW);
		}
	}

	@Override
	public void doSelect(String selectField, Object value) throws Exception {
		String[] params = value.toString().split(":");
		String path = "";
		if (params != null && params.length > 0) {
		  path = params[params.length - 1];
		  path = path.substring(1);
		}
		
		getUIStringInput(selectField).setValue(path);
		
		UIUploadPortlet categoryNavigationPortlet = getAncestorOfType(UIUploadPortlet.class);
	    UIPopupContainer popupContainer = categoryNavigationPortlet.getChild(UIPopupContainer.class);
	    Utils.closePopupWindow(popupContainer, this.popupId);
	}
}