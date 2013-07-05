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
package org.exoplatform.chromattic.services;

import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticSession;
import org.exoplatform.chromattic.facade.FacadeAccessingFileSystem;
import org.exoplatform.commons.chromattic.ChromatticManager;

/**
 * @author <a href="mailto:sondn@exoplatform.com">Ngoc Son Dang</a>
 * @since Jul 5, 2013
 * @version 
 * 
 * @tag 
 */
public class ChromatticService {

	private ChromatticManager chromatticManager;
	
	
	public ChromatticService(ChromatticManager chromatticManager) {
		this.chromatticManager = chromatticManager;
	}
	
	/**
	 * Get chromattic manager object
	 * 
	 * @return a chromattic object
	 */
	public ChromatticManager getChromatticManager() {
		return chromatticManager;
	}
	
	/**
	 * Get chromattic session
	 * 
	 * @return a chromattic session
	 */
	public ChromatticSession getChromatticSession() {
		return chromatticManager.getLifeCycle("lab").getContext().getSession();
	}
	
	/**
	 * Get a facade accessing to the virtual file system
	 * 
	 * @return a facade accessing object
	 */
	public FacadeAccessingFileSystem getFacadeAccessingFileSystem() {
		Chromattic chromattic = chromatticManager.getLifeCycle("lab").getChromattic();
		ChromatticSession chromatticSession = chromattic.openSession();
	    return new FacadeAccessingFileSystem(chromatticSession);
	}
}
