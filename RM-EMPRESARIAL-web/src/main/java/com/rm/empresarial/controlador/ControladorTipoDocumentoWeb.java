package com.rm.empresarial.controlador;


import com.rm.empresarial.modelo.TipoDocumentoWeb;
import com.rm.empresarial.servicio.TipoDocumentoWebServicio;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorTipoDocumentoWeb")
@ViewScoped
public class ControladorTipoDocumentoWeb implements Serializable {

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @EJB
    private TipoDocumentoWebServicio TipoDocumentoWebServicio;

    @Getter
    @Setter
    private String nombreDocumentoWeb;

    @Getter
    @Setter
    private List<TipoDocumentoWeb> listaTipoDocumentoWeb;

    @Getter
    @Setter
    private String renderRPC_01, renderRPC_02, renderSOLICITUDDECERTIFICADOSMERCANTILES;

    public ControladorTipoDocumentoWeb() {

    }

    @PostConstruct
    public void postTipoDocumentoWeb() {
        ListarTipoDocumentoWeb();
        nombreDocumentoWeb = new String();
        setRenderRPC_01("false");
        setRenderRPC_02("false");
        setRenderSOLICITUDDECERTIFICADOSMERCANTILES("false");
    }

    public void ListarTipoDocumentoWeb() {
        setListaTipoDocumentoWeb(TipoDocumentoWebServicio.listarTodo());
    }

    public void cambiarEstadoBotonSeleccion() throws IOException {
        switch (nombreDocumentoWeb) {
            case "RPC-01":
                setRenderRPC_01("true");
                setRenderRPC_02("false");
                setRenderSOLICITUDDECERTIFICADOSMERCANTILES("false");
                break;
            case "RPC-02":
                setRenderRPC_02("true");
                setRenderRPC_01("false");
                setRenderSOLICITUDDECERTIFICADOSMERCANTILES("false");
                break;
            case "SOLICITUD DE CERTIFICADOS MERCANTILES":
                setRenderSOLICITUDDECERTIFICADOSMERCANTILES("true");
                setRenderRPC_01("false");
                setRenderRPC_02("false");
                break;
            default:
                break;
        }
    }

}
