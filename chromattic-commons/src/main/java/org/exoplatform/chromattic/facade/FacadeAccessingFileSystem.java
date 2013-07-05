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
package org.exoplatform.chromattic.facade;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;

import org.chromattic.api.ChromatticSession;
import org.exoplatform.chromattic.entities.Gallery;

/**
 * @author <a href="mailto:sondn@exoplatform.com">Ngoc Son Dang</a>
 * @since Jul 5, 2013
 * @version 
 * 
 * @tag 
 */
public class FacadeAccessingFileSystem {
	
	private ChromatticSession _session;
	
	
	public FacadeAccessingFileSystem() {}
	
	public FacadeAccessingFileSystem(ChromatticSession chromatticSession) {
		this._session = chromatticSession;
	}
	
	/**
	 * Save chromattic session
	 */
	public void saveSession() {
		_session.save();
	}
	
	/**
	 * Close chromattic session
	 */
	public void closeSession() {
		_session.close();
	}
	
	public Gallery getGallery(String folderPath) throws Exception {
		Node folder = _session.getJCRSession().getRootNode().getNode(folderPath);
		if (folder == null) {
			throw new PathNotFoundException("Folder path does not exist.");
		}

		Node galleryNode = null;
		if (!folder.hasNode("gallery")) {
			galleryNode = folder.addNode("gallery", "lab:gallery");
			
			// Save chromattic session
			_session.save();
		} else {
			galleryNode = folder.getNode("gallery");
		}

		return _session.findByNode(Gallery.class, galleryNode);
	}
}
