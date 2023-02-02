/*******************************************************************************
 * Copyright (C) 2023 the Eclipse BaSyx Authors
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * SPDX-License-Identifier: MIT
 ******************************************************************************/

package org.eclipse.digitaltwin.basyx.submodelservice;

import java.util.Collection;

import org.eclipse.digitaltwin.aas4j.v3.model.Property;
import org.eclipse.digitaltwin.aas4j.v3.model.Submodel;
import org.eclipse.digitaltwin.aas4j.v3.model.SubmodelElement;
import org.eclipse.digitaltwin.basyx.core.exceptions.ElementDoesNotExistException;

/**
 * Implements the SubmodelService as in-memory variant
 * 
 * @author schnicke
 * 
 */
public class InMemorySubmodelService implements SubmodelService {

	private Submodel submodel;

	/**
	 * Creates the InMemory SubmodelService containing the passed Submodel
	 * 
	 * @param submodel
	 */
	public InMemorySubmodelService(Submodel submodel) {
		this.submodel = submodel;
	}

	@Override
	public Submodel getSubmodel() {
		return submodel;
	}

	@Override
	public Collection<SubmodelElement> getSubmodelElements() {
		return submodel.getSubmodelElements();
	}

	@Override
	public SubmodelElement getSubmodelElement(String idShort) {
		return submodel.getSubmodelElements()
				.stream()
				.filter(sme -> sme.getIdShort()
						.equals(idShort))
				.findAny()
				.orElseThrow(() -> new ElementDoesNotExistException(idShort));
	}

	@Override
	public Object getSubmodelElementValue(String idShort) throws ElementDoesNotExistException {
		Property property = (Property) getSubmodelElement(idShort);
		return property.getValue();
	}

	@Override
	public void setSubmodelElementValue(String idShort, Object value) throws ElementDoesNotExistException {
		Property property = (Property) getSubmodelElement(idShort);
		property.setValue((String) value);
	}

}