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
package org.exoplatform.chromattic.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.exoplatform.upload.UploadResource;
import org.exoplatform.web.application.ApplicationMessage;
import org.exoplatform.webui.exception.MessageException;
import org.exoplatform.webui.form.UIFormInput;
import org.exoplatform.webui.form.UIFormUploadInput;
import org.exoplatform.webui.form.validator.Validator;

/**
 * @author <a href="mailto:sondn@exoplatform.com">Ngoc Son Dang</a>
 * @since Jul 5, 2013
 * @version 
 * 
 * @tag 
 */
public class MimeTypesValidator implements Validator {

	private String mimeTypes_;

	public MimeTypesValidator() {
	}

	public MimeTypesValidator(String mimeTypes) {
		this.mimeTypes_ = mimeTypes;
	}
	
	@Override
	public void validate(UIFormInput uiInput) throws Exception {
		if (uiInput instanceof UIFormUploadInput) {
			UIFormUploadInput uploadInput = UIFormUploadInput.class.cast(uiInput);
			UploadResource uploadResource = uploadInput.getUploadResource();
        	String mimeTypeInput = uploadResource == null ? null : uploadResource.getMimeType();
        	
        	if (mimeTypes_ != null && mimeTypeInput != null) {
				Pattern pattern = Pattern.compile(mimeTypes_.replace("*", ".*"));
				Matcher matcher = pattern.matcher(mimeTypeInput);
				if (!matcher.find()) {
					Object[] args = { mimeTypeInput, uploadInput.getName() };
					throw new MessageException(new ApplicationMessage("UploadFileMimeTypesValidator.msg.wrong-mimetype", args, ApplicationMessage.WARNING));
				}
			}
		}
	}
}
