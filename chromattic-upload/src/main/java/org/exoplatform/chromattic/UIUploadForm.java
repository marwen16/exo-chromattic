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

import java.io.File;
import java.io.FileInputStream;

import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.xml.namespace.QName;

import org.exoplatform.chromattic.entities.Gallery;
import org.exoplatform.chromattic.entities.Photo;
import org.exoplatform.chromattic.facade.FacadeAccessingFileSystem;
import org.exoplatform.chromattic.services.ChromatticService;
import org.exoplatform.chromattic.validate.MimeTypesValidator;
import org.exoplatform.services.wcm.utils.WCMCoreUtils;
import org.exoplatform.upload.UploadResource;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.application.portlet.PortletRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.ComponentConfigs;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.lifecycle.UIFormLifecycle;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.exoplatform.webui.form.UIForm;
import org.exoplatform.webui.form.UIFormStringInput;
import org.exoplatform.webui.form.UIFormUploadInput;

/**
 * @author <a href="mailto:sondn@exoplatform.com">Ngoc Son Dang</a>
 * @since Jul 5, 2013
 * @version 
 * 
 * @tag 
 */
@ComponentConfigs({ 
	@ComponentConfig(
			lifecycle = UIFormLifecycle.class, 
			template = "classpath:groovy/webui/UIUploadForm.gtmpl", 
			events = { @EventConfig(listeners = UIUploadForm.SaveActionListener.class) }
			) 
	}
)
public class UIUploadForm extends UIForm {

	public UIUploadForm() throws Exception {
		addUIFormInput(new UIFormStringInput("name", "name", null));
		UIFormUploadInput uploadInput = new UIFormUploadInput("upload",	"upload");
		uploadInput.setAutoUpload(true);
		uploadInput.addValidator(MimeTypesValidator.class, "image/jpeg");
		addUIFormInput(uploadInput);
	}
	
	public static class SaveActionListener extends EventListener<UIUploadForm> {

		@Override
		public void execute(Event<UIUploadForm> event) throws Exception {
			UIUploadForm uiForm = event.getSource();
			UIFormStringInput uiName = (UIFormStringInput) uiForm.getUIInput("name");
			UIFormUploadInput uiUploadInput = (UIFormUploadInput) uiForm.getUIInput("upload");
			
			UploadResource uploadResource = uiUploadInput.getUploadResource();			
			if (uploadResource != null && uploadResource.getUploadedSize() > 0) {
				String photoName = uploadResource.getFileName();
				if (uiName.getValue() != null && uiName.getValue().length() > 0) {
					photoName = uiName.getValue();
				}
				
				PortletRequestContext portletRequestContext = WebuiRequestContext.getCurrentInstance();
		        PortletPreferences preferences = portletRequestContext.getRequest().getPreferences();
		        String folderPath = preferences.getValue("galleryPath", "sites content/live/acme/documents");
		        ChromatticService chromatticService = WCMCoreUtils.getService(ChromatticService.class);
		        FacadeAccessingFileSystem faFileSystem = chromatticService.getFacadeAccessingFileSystem();
		        Gallery gallery = faFileSystem.getGallery(folderPath);
				
				if (!gallery.getPhotoMap().containsKey(photoName)) {
					Photo photo = gallery.createPhoto();
					photo.setName(photoName);
					gallery.getPhotoMap().put(photo.getName(), photo);
					photo.update(uploadResource.getMimeType(), new FileInputStream(new File(uploadResource.getStoreLocation())));
				}
				
				uiName.reset();
				uiForm.removeChild(UIFormUploadInput.class);
				UIFormUploadInput uploadInput = new UIFormUploadInput("upload",	"upload");
				uploadInput.setAutoUpload(true);
				uploadInput.addValidator(MimeTypesValidator.class, "image/jpeg");
				uiForm.addUIFormInput(uploadInput);

			}
			
			ActionResponse actResponse = event.getRequestContext().getResponse();
	        actResponse.setEvent(new QName("NewPhotoUploaded"), null);
		}
	}
}
