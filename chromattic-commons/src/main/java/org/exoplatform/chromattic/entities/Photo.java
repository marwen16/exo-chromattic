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
package org.exoplatform.chromattic.entities;

import java.io.InputStream;
import java.util.Date;

import org.chromattic.api.annotations.Create;
import org.chromattic.api.annotations.Id;
import org.chromattic.api.annotations.ManyToOne;
import org.chromattic.api.annotations.MappedBy;
import org.chromattic.api.annotations.Name;
import org.chromattic.api.annotations.OneToOne;
import org.chromattic.api.annotations.Owner;
import org.chromattic.api.annotations.Path;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.Property;

/**
 * @author <a href="mailto:sondn@exoplatform.com">Ngoc Son Dang</a>
 * @since Jul 5, 2013
 * @version 
 * 
 * @tag 
 */
@PrimaryType(name = "lab:photo")
public abstract class Photo {

	/**
	 * 
	 * @return the photo's ID 
	 */
	@Id
	public abstract String getId();
	
	/**
	 * 
	 * @return the photo's path
	 */
	@Path
	public abstract String getPath();
	
	/**
	 * 
	 * @return the photo's name
	 */
	@Name
	public abstract String getName();
	
	/**
	 * Set the photo name
	 * 
	 * @param name
	 */
	public abstract void setName(String name);
	
	/**
	 * 
	 * @return the created date of photo
	 */
	@Property(name = "jcr:created")
	public abstract Date getCreated();
	
	/**
	 * Set the create date for photo
	 * 
	 * @param created
	 */
	public abstract void setCreated(Date created);
	
	/**
	 * 
	 * @return the parent of photo
	 */
	@ManyToOne
	public abstract Gallery getParent();
	
	/**
	 * 
	 * @return Returns the photo's content or null if no content is associated with photo
	 */
	@OneToOne
	@Owner
	@MappedBy("jcr:content")
	public abstract Content getContent();
	
	/**
	 * Set the content of photo
	 * 
	 * @param content
	 */
	public abstract void setContent(Content content);
	
	/**
	 * Factory method of content object
	 * 
	 * @return a blank content object
	 */
	@Create
	protected abstract Content createContent();
	
	/**
	 * Updates the current photo with the specified mime type and data
	 * 
	 * @param mimeType
	 * @param data
	 */
	public void update(String mimeType, InputStream data) {
		Content content = getContent();
		if (content == null) {
			content = createContent();
			setContent(content);
		}
		content.setData(data);
		content.setMimeType(mimeType);
		content.setLastModified(new Date());
	}
}
