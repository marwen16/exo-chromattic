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

import java.util.Collection;
import java.util.Map;

import org.chromattic.api.annotations.Create;
import org.chromattic.api.annotations.Id;
import org.chromattic.api.annotations.ManyToOne;
import org.chromattic.api.annotations.Name;
import org.chromattic.api.annotations.OneToMany;
import org.chromattic.api.annotations.Path;
import org.chromattic.api.annotations.PrimaryType;

/**
 * @author <a href="mailto:sondn@exoplatform.com">Ngoc Son Dang</a>
 * @since Jul 5, 2013
 * @version 
 * 
 * @tag 
 */
@PrimaryType(name = "nt:folder")
public abstract class Folder {

	/**
	 * Return the folder's ID
	 * 
	 * @return a string ID
	 */
	@Id
	public abstract String getId();
	
	/**
	 * Return the folder's name
	 * 
	 * @return a string name
	 */
	@Name
	public abstract String getName();
	
	/**
	 * Return the folder's path
	 * 
	 * @return a path
	 */
	@Path
	public abstract String getPath();
	
	/**
	 * Return the parent of folder
	 * 
	 * @return a folder object
	 */
	@ManyToOne
	public abstract Folder getParent();
	
	/**
	 * Factory method of gallery object
	 * 
	 * @return
	 */
	@Create
	protected abstract Gallery createGallery();
	
	/**
	 * Returns the gallery children of folder
	 * 
	 * @return a list
	 */
	@OneToMany
	protected abstract Map<String, Gallery> getGalleryMap();
	
	/**
	 * Returns the collection of gallery children
	 * 
	 * @return a collection
	 */
	public Collection<Gallery> getGalleries() {
		return getGalleryMap().values();
	}
	
	/**
	 * Return a specific gallery by name
	 * 
	 * @param name
	 * @return a gallery or null
	 */
	public Gallery getGallery(String name) {
		return getGalleryMap().get(name);
	}
	
	/**
	 * Create a gallery
	 * 
	 * @param name
	 * @return the created gallery
	 */
	public Gallery addGallery(String name) {
		Gallery gallery = createGallery();
		getGalleryMap().put(name, gallery);
		return gallery;
	}
}