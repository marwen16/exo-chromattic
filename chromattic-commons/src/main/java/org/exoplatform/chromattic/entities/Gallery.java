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
@PrimaryType(name = "lab:gallery")
public abstract class Gallery {

	/**
	 * Return the name of gallery
	 * 
	 * @return a name
	 */
	@Name
	public abstract String getName();
	
	/**
	 * Return the gallery's path
	 * 
	 * @return a path
	 */
	@Path 
	public abstract String getPath();
	
	/**
	 * Return a list photo in the gallery
	 * 
	 * @return a list
	 */
	@OneToMany
	public abstract Map<String, Photo> getPhotoMap();
	
	/**
	 * Factory method for create photo object
	 * 
	 * @return a blank photo object
	 */
	@Create
	public abstract Photo createPhoto();
	
	/**
	 * Return a collection of photo in the gallery
	 * 
	 * @return a collection
	 */
	public Collection<Photo> getPhotos() {
		return getPhotoMap().values();
	}
	
	/**
	 * Remove a photo in the collection
	 * 
	 * @param name
	 */
	public void removePhoto(String name) {
		getPhotoMap().remove(name);
	}
}