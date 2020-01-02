/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.bb;
import com.rm.empresarial.modelo.CargaLaboral;
import com.rm.empresarial.modelo.Notaria;
import com.rm.empresarial.modelo.Parametros;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.RepositorioSRI;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.modelo.TramiteUsuario;
import com.rm.empresarial.modelo.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.faces.model.SelectItem;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Wilson
 */
@Dependent
public class RecepcionDocumentacionControladorBb implements Serializable {

    private static final long serialVersionUID = 499145567067647851L;

    @Getter
    @Setter
    private Persona persona;

    @Getter
    @Setter
    private String identificacion;
    
    @Getter
    @Setter
    private String identificacionConyugue;

    @Getter
    @Setter
    private List<Notaria> listaNotaria;

    @Getter
    @Setter
    private Date fechaEntrega;

    @Getter
    @Setter
    private Calendar fechaEntregaAux;

    @Getter
    @Setter
    private RepositorioSRI repositorioSRI;

    @Getter
    @Setter
    private List<SelectItem> notarias;
    
    @Getter
    @Setter
    private List<Notaria> listaNotarias;

    @Getter
    @Setter
    private Notaria notaria;

    @Getter
    @Setter
    private String valorNotaria;

    @Getter
    @Setter
    private List<SelectItem> sexo;

    @Getter
    @Setter
    private Parametros parametros;

    @Getter
    @Setter
    private Secuencia secuencia;

    @Getter
    @Setter
    private Calendar fecha = new GregorianCalendar();

    @Getter
    @Setter
    private Integer numeroTramite;

    @Getter
    @Setter
    private String observacion;

    @Getter
    @Setter
    private Tramite tramite;

    @Getter
    @Setter
    private Boolean estado = Boolean.FALSE;

    @Getter
    @Setter
    private CargaLaboral cargaLaboral;

    @Getter
    @Setter
    private String usuarioNombre;

    @Getter
    @Setter
    private Usuario usuario;

    @Getter
    @Setter
    private TramiteUsuario tramiteUsuario;

    @Getter
    @Setter
    private Boolean filtro = Boolean.FALSE;

    @Getter
    @Setter
    private TramiteAccion tramiteAccion;

    @Getter
    @Setter
    private Boolean varios = Boolean.FALSE;

    @Getter
    @Setter
    private Integer numeroTramites;

    @Getter
    @Setter
    private Integer numeroTramiteFinal;
    
    @Getter
    @Setter
    private Integer numeroTramiteInicial;

    
    
    /*
   metodo que permite inicializar el controlador
     */
    public void inicializar() {

        setPersona(new Persona());
        setRepositorioSRI(new RepositorioSRI());
        listaNotaria = (new ArrayList<>());
        setNotaria(new Notaria());
        setParametros(new Parametros());
        setTramite(new Tramite());
        setSecuencia(new Secuencia());
        setCargaLaboral(new CargaLaboral());
        setUsuario(new Usuario());
        setTramiteUsuario(new TramiteUsuario());
        setTramiteAccion(new TramiteAccion());

    }
}
