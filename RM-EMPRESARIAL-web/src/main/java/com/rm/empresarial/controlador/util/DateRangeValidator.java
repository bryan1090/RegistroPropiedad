/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Prueba
 */
@FacesValidator(value = "dateRangeValidator")
public class DateRangeValidator implements Validator{
    
  @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return; // Let required="true" handle.
        }

        UIInput startDateComponent = (UIInput) component.getAttributes().get("startDateComponent");

        if (!startDateComponent.isValid()) {
            return; // Already invalidated. Don't care about it then.
        }

        Date startDate = (Date) startDateComponent.getValue();

        if (startDate == null) {
            return; // Let required="true" handle.
        }

        Date endDate = (Date) value;

        if (startDate.after(endDate)) {
            startDateComponent.setValid(false);
            throw new ValidatorException(new FacesMessage(
                FacesMessage.SEVERITY_ERROR, "Start date may not be after end date.", null));
        }
    }

    
}
