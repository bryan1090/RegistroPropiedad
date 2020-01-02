/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.dao.CalendarioDao;
import com.rm.empresarial.modelo.Calendario;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "controladorCalendarioCrear")
@ViewScoped
public class ControladorCalendarioCrear implements Serializable {

    @Getter
    @Setter
    private Date añoSeleccionado;

    @Getter
    @Setter
    private Calendario calendario;

    @EJB
    private CalendarioDao calendarioServicio;
    
    public ControladorCalendarioCrear() {
    }
    
    public void guardarFechas(LocalDate start, LocalDate end) {
        try {
            for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
                System.out.println("fecha: " + date.toString());

                String diaFestivo="";
                if(date.getDayOfWeek()==DayOfWeek.SATURDAY || date.getDayOfWeek()==DayOfWeek.SUNDAY)
                {
                    diaFestivo="S";
                }
                else
                {
                    diaFestivo="N";
                }
                calendario = new Calendario(0L,
                        (short) date.getYear(), (short) date.getMonth().getValue(),
                        (short) date.getDayOfMonth(), java.sql.Date.valueOf(date),
                         diaFestivo);
                calendarioServicio.edit(calendario);

            }
            JsfUtil.addSuccessMessage("Calendario generado");
        } catch (Exception e) {
            JsfUtil.addWarningMessage("Advertencia, calendario ya está generado");

            e.printStackTrace();
        }

    }

    public String guardarCalendario() {
        LocalDate d1 = añoSeleccionado.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate d2 = d1.plusYears(1);
        
        guardarFechas(d1, d2);
        System.out.println("año: " + añoSeleccionado.toString());
        return("/paginas/MANTENIMIENTOS/calendario/List.xhtml?faces-redirect=true");

    }

    
}
