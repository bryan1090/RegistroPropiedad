package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Lindero;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.servicio.LinderoServicio;
import com.rm.empresarial.servicio.ParroquiaServicio;
import com.rm.empresarial.servicio.PropiedadServicio;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.swing.Popup;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorGeolocalizacion")
@ViewScoped
public class ControladorGeolocalizacion implements Serializable {

    @Inject
    @Getter
    @Setter
    private DataManagerSesion dataManagerSesion;

    @EJB
    private PropiedadServicio servicioPropiedad;

    @EJB
    private ParroquiaServicio servicioParroquia;

    @EJB
    private LinderoServicio servicioLindero;

    @Getter
    @Setter
    private Propiedad propiedad;

    @Getter
    @Setter
    private Propiedad propiedadSeleccionada;

    @Getter
    @Setter
    private Propiedad propiedadBuscada;

    @Getter
    @Setter
    private Parroquia parroquia;

    @Getter
    @Setter
    private List<Propiedad> listaPropiedades;

    @Getter
    @Setter
    private List<Parroquia> listaParroquias;

    @Getter
    @Setter
    private List<Lindero> listaLinderos;

    @Getter
    @Setter
    private MapModel geoModel;

    @Getter
    @Setter
    private Marker marcadorSeleccionado;

    @Getter
    @Setter
    private String centerGeoMap;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private int zoom;

    @Getter
    @Setter
    private String predio;

    @Getter
    @Setter
    private String catastro;

    @Getter
    @Setter
    private String direccion;

    @PostConstruct
    public void postControladorGeolocalizacion() {
        try {
            setListaPropiedades(servicioPropiedad.listarTodo());
            setListaParroquias(servicioParroquia.listarTodo());
            cargarPropiedades_A_Mapa();
        } catch (ServicioExcepcion e) {
            JsfUtil.addErrorMessage("No Existen Parroquias");
            System.out.print(e);
        }
    }

    public ControladorGeolocalizacion() {
        propiedad = new Propiedad();
        propiedadSeleccionada = new Propiedad();
        propiedadBuscada = new Propiedad();
        parroquia = new Parroquia();
        listaParroquias = new ArrayList<>();
        listaPropiedades = new ArrayList<>();
        listaLinderos = new ArrayList<>();
        zoom = 14;
        geoModel = new DefaultMapModel();
        centerGeoMap = "-0.2029,-78.4911";
    }

    public void onZoomAction(StateChangeEvent event) {
        setZoom(event.getZoomLevel());
    }

    public void onPointSelect(PointSelectEvent event) {
        setGeoModel(new DefaultMapModel());
        LatLng latlng = event.getLatLng();
        Marker point = new Marker(latlng);
        geoModel.addOverlay(point);
        setCenterGeoMap(latlng.getLat() + "," + latlng.getLng());
    }

    public void cargarPropiedades_A_Mapa() {
        setGeoModel(new DefaultMapModel());

        for (Propiedad propiedadSelect : listaPropiedades) {
            if (propiedadSelect.getPrdLatitud().equals("0") || propiedadSelect.getPrdLongitud().equals("0") || propiedadSelect.getPrdLongitud().equals("") || propiedadSelect.getPrdLongitud().equals("")) {
                System.out.print("Propiedad sin ubicación:" + propiedadSelect.getPrdMatricula());
            } else {
                LatLng latlng = new LatLng(Double.parseDouble(propiedadSelect.getPrdLatitud()), Double.parseDouble(propiedadSelect.getPrdLongitud()));
                String informacion = "N° Matrícula:" + propiedadSelect.getPrdMatricula() + "\n"
                        + "Predio:" + propiedadSelect.getPrdPredio() + "\n"
                        + "Catastro:" + propiedadSelect.getPrdCatastro();

                Marker point = new Marker(latlng, propiedadSelect.getPrdMatricula().toString(), informacion);
                geoModel.addOverlay(point);
            }
        }
    }

    public void localizarPorParroquia() throws IOException {
        try {
            String reemplazo = "%20";
            String replace = parroquia.getParNombre().replaceAll("\\s", reemplazo);

            setUrl("https://maps.googleapis.com/maps/api/geocode/json?address=Ecuador," + replace + "&key=AIzaSyC7R9us6s7z77X-uy5zaAc6DzUM6u0-7c4");
            URL dir = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) dir.openConnection();

            int responseCode = conn.getResponseCode();
            System.out.print(responseCode);
            if (responseCode == 0) {

            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject json1 = new JSONObject(response.toString());
                JSONArray jsonArray = json1.getJSONArray("results");
                JSONObject json2 = jsonArray.getJSONObject(0);
                JSONObject json3 = json2.getJSONObject("geometry");
                JSONObject jsonLocation = json3.getJSONObject("location");
                setCenterGeoMap(jsonLocation.getDouble("lat") + "," + jsonLocation.getDouble("lng"));
                setZoom(15);
                parroquia = new Parroquia();
                System.out.print(jsonLocation);
            }

        } catch (MalformedURLException | JSONException e) {
            JsfUtil.addErrorMessage("Parroquia no encontrada");
        }

    }

    public void localizarPorPropiedad() throws IOException {
        try {
            setPropiedadBuscada(servicioPropiedad.buscarPropiedadPor_predio_o_catastro(predio, catastro));
            if (propiedadBuscada != null) {
                setCenterGeoMap(propiedadBuscada.getPrdLatitud() + "," + propiedadBuscada.getPrdLongitud());
                setZoom(20);
                predio = "";
                catastro = "";
            } else {
                JsfUtil.addWarningMessage("Propiedad no encontrada");
            }
        } catch (Exception e) {
            System.out.print(e);
        }

    }

    public void localizarPorDireccion() throws IOException {
        try {
            String reemplazo = "%20";
            String replace = direccion.replaceAll("\\s", reemplazo);

            setUrl("https://maps.googleapis.com/maps/api/geocode/json?address=" + replace + ",Ecuador&key=AIzaSyC7R9us6s7z77X-uy5zaAc6DzUM6u0-7c4");
            URL dir = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) dir.openConnection();

            int responseCode = conn.getResponseCode();
            System.out.print(responseCode);
            if (responseCode == 400) {
                JsfUtil.addErrorMessage("Url inválido");
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject json1 = new JSONObject(response.toString());
                JSONArray jsonArray = json1.getJSONArray("results");
                JSONObject json2 = jsonArray.getJSONObject(0);
                JSONObject json3 = json2.getJSONObject("geometry");
                JSONObject jsonLocation = json3.getJSONObject("location");
                setCenterGeoMap(jsonLocation.getDouble("lat") + "," + jsonLocation.getDouble("lng"));
                setZoom(17);

                direccion = "";
                System.out.print(jsonLocation);
            }

        } catch (MalformedURLException | JSONException e) {
            JsfUtil.addWarningMessage("Dirección no encontrada");
        }

    }

    public void cargarInfoPropiedad(OverlaySelectEvent event) {
        try {
            marcadorSeleccionado = (Marker) event.getOverlay();
            setPropiedadSeleccionada(servicioPropiedad.encontrarPropiedadPorId(marcadorSeleccionado.getTitle()));
            setListaLinderos(servicioLindero.listarPorNumMatricula((marcadorSeleccionado.getTitle())));
        } catch (ServicioExcepcion e) {
            System.out.print(e);
        }

    }
}
