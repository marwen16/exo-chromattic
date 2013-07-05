/**
 * 
 */
package org.exoplatform.chromattic;

import java.util.Map;

import javax.portlet.PortletPreferences;

import org.exoplatform.chromattic.entities.Gallery;
import org.exoplatform.chromattic.entities.Photo;
import org.exoplatform.chromattic.facade.FacadeAccessingFileSystem;
import org.exoplatform.chromattic.services.ChromatticService;
import org.exoplatform.portal.webui.container.UIContainer;
import org.exoplatform.services.wcm.utils.WCMCoreUtils;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.application.portlet.PortletRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.core.lifecycle.Lifecycle;

/**
 * @author macos
 *
 */
@ComponentConfig(
	    lifecycle = Lifecycle.class,
	    template = "classpath:groovy/webui/UIGalleryViewerContainer.gtmpl"
	)
public class UIGalleryViewerContainer extends UIContainer {
	
	private Map<String, Photo> photoMap;

	public UIGalleryViewerContainer() throws Exception {
		super();
	}

	public String getDateTimeFormat() {
		return getAncestorOfType(UIGalleryViewerPortlet.class).getDateTimeFormat();
	}

	public Map<String, Photo> getPhotoMap() {
		return photoMap;
	}

	public void setPhotoMap(Map<String, Photo> photoMap) {
		this.photoMap = photoMap;
	}

	public void processRender(WebuiRequestContext context) throws Exception {
		PortletRequestContext portletRequestContext = WebuiRequestContext.getCurrentInstance();
		PortletPreferences preferences = portletRequestContext.getRequest().getPreferences();
		String folderPath = preferences.getValue("galleryPath", "sites content/live/acme/documents");
		ChromatticService chromatticService = WCMCoreUtils.getService(ChromatticService.class);
        FacadeAccessingFileSystem faFileSystem = chromatticService.getFacadeAccessingFileSystem();
        Gallery gallery = faFileSystem.getGallery(folderPath);
        
		this.photoMap = gallery.getPhotoMap();
		super.processRender(context);
	}
}
