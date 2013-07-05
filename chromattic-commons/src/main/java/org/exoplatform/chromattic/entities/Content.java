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

import org.chromattic.api.annotations.OneToOne;
import org.chromattic.api.annotations.MappedBy;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.Property;

/**
 * @author <a href="mailto:sondn@exoplatform.com">Ngoc Son Dang</a>
 * @since Jul 5, 2013
 * @version 
 * 
 * @tag 
 */
@PrimaryType(name = "nt:resource")
public abstract class Content {
	
	
	/**
	 * 
	 * @return the content encoding
	 */
	@Property(name = "jcr:encoding")
	public abstract String getEncoding();
	
	/**
	 * Set the content encoding
	 * 
	 * @param encoding
	 */
	public abstract void setEncoding(String encoding);
	
	/**
	 * 
	 * @return the content mime type
	 */
	@Property(name = "jcr:mimeType")
	public abstract String getMimeType();
	
	/**
	 * Set the content mime type
	 * 
	 * @param mimeType
	 */
	public abstract void setMimeType(String mimeType);
	
	/**
	 * 
	 * @return the content data
	 */
	@Property(name = "jcr:data")
	public abstract InputStream getData();
	
	/**
	 * Set the content data
	 * 
	 * @param data
	 */
	public abstract void setData(InputStream data);
	
	/**
	 * 
	 * @return the last modified date
	 */
	@Property(name = "jcr:lastModified")
	public abstract Date getLastModified();
	
	/**
	 * Set the last modified date
	 * 
	 * @param lastModified
	 */
	public abstract void setLastModified(Date lastModified);
	
	/**
	 * 
	 * @return the object related to the content object
	 */
	@OneToOne
	@MappedBy("jcr:content")
	public abstract Photo getPhoto();
}
