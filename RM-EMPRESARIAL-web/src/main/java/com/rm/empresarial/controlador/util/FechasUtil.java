/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Bryan_Mora
 */
public class FechasUtil {

//    TransformadorNumerosLetrasUtil transformadorNumerosLetrasUtil;
    private static final String MES[] = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};
    private static final String DIASEMANA[] = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
    private static final String DIA[] = {"UNO", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE",
        "OCHO", "NUEVE", "DIEZ", "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE", "DIECISÉIS", "DIECISIETE,",
        "DIECIOCHO", "DIECINUEVE", "VEINTE", "VEINTIUNO", "VEINTIDÓS", "VEINTITRES", "VEINTICUATRO",
        "VEINTICINCO", "VEINTISÉIS", "VEINTISIETE", "VEINTIOCHO", "VEINTINUEVE", "TREINTA",
        "TREINTA Y UNO"};
    
    private static final String MES_NUMERO[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    private static final String DIA_NUMERO[] = {"01", "02", "03", "04", "05", "06", "07",
                            "08", "09", "10", "11", "12", "13", "14", "15", "16", "17,",
                            "18", "19", "20", "21", "22", "23", "24",
                            "25", "26", "27", "28", "29", "30",
                            "31"};


    public static String convertir_A_letras(int dia, int mes, int año) {
//        transformadorNumerosLetrasUtil = new TransformadorNumerosLetrasUtil();
        String txtAño = "";
        String fechaLetras = "";
        txtAño = (TransformadorNumerosLetrasUtil.transformador(String.valueOf(año))).toString();
        fechaLetras = DIA[dia - 1] + " DE " + MES[mes] + " DEL " + txtAño;
        return fechaLetras;
    }

    //fecha en letra. Ejemplo: TREINTA DE ENEERO DEL DOS MIL DIESCINUEVE
    public static String convertirFecha_A_letras(Date fecha) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        String txtAño = "";
        String fechaLetras = "";
        txtAño = (TransformadorNumerosLetrasUtil.transformador(String.valueOf(calendar.get(Calendar.YEAR)))).toString();
        fechaLetras = (DIA[calendar.get(Calendar.DAY_OF_MONTH) - 1] + " DE " + MES[calendar.get(Calendar.MONTH)] + " DEL " + txtAño);

        return fechaLetras;
    }
    //fecha en letra. Ejemplo: 10/11/2019 15:50:38
    public static String convertirFechaA_ddMMAAAA_hhmmss(Date fecha) {
        
        String fechaFmt = "";
        Calendar calendar = Calendar.getInstance();
         calendar.setTime(fecha);

                        fechaFmt = (DIA_NUMERO[calendar.get(Calendar.DAY_OF_MONTH) - 1] + "/" + MES_NUMERO[calendar.get(Calendar.MONTH)] + "/" + calendar.get(Calendar.YEAR))
                                + " " + String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))
                                + ":" + String.valueOf(calendar.get(Calendar.MINUTE))
                                + ":" + String.valueOf(calendar.get(Calendar.SECOND));


        return fechaFmt;
    }
    
    //Convertir fecha ejemplo: Viernes, 21 de junio 2019, 11:10:27 AM
    public static String convertirDiaSemana_dia_mesLetras_anio_hhmmss_am_pm(Date fecha){
            String fechaFmt = "";
        Calendar calendar = Calendar.getInstance();
         calendar.setTime(fecha);
         Date hora = calendar.getTime();
         String strDateFormat = "hh:mm:ss a";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            String formattedDate = dateFormat.format(hora);
           fechaFmt = DIASEMANA[calendar.get(Calendar.DAY_OF_WEEK) - 1] + ", "
                    + calendar.get(Calendar.DAY_OF_MONTH) + " "
                    + MES[calendar.get(Calendar.MONTH)] + " "
                    + calendar.get(Calendar.YEAR) + ", "
                    + formattedDate;
           
           return fechaFmt;
    
}
    //CONVERTIR FECHA A UN SOLO STRING. EJEMPLO: DE: 10 Nov 2019 15:50:38 CONVIERTE A 10112019155038
    public static String convertirFechaA_ddMMAAAAhhmmss(Date fecha) {
        
        String fechaFmt = "";
        Calendar calendar = Calendar.getInstance();
         calendar.setTime(fecha);

                        fechaFmt = (DIA_NUMERO[calendar.get(Calendar.DAY_OF_MONTH) - 1]  
                                + MES_NUMERO[calendar.get(Calendar.MONTH)]
                                + calendar.get(Calendar.YEAR))
                                + String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))
                                + String.valueOf(calendar.get(Calendar.MINUTE))
                                + String.valueOf(calendar.get(Calendar.SECOND));


        return fechaFmt;
    }
    //fecha en letra. Ejemplo: miercoles, 06/02/2019 15:50:38
    public static String convertirFechaA_diaSemana_ddMMAAAA_hhmmss(Date fecha) {
        
        String fechaFmt = "";
        String diaSemana = "";
        String fechaCompleta = "";
        Calendar calendar = Calendar.getInstance();
         calendar.setTime(fecha);
         diaSemana = DIASEMANA[calendar.get(Calendar.DAY_OF_WEEK) - 1];

                        fechaFmt = (DIA_NUMERO[calendar.get(Calendar.DAY_OF_MONTH) - 1] + "/" + MES_NUMERO[calendar.get(Calendar.MONTH)] + "/" + calendar.get(Calendar.YEAR))
                                + " " + String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))
                                + ":" + String.valueOf(calendar.get(Calendar.MINUTE))
                                + ":" + String.valueOf(calendar.get(Calendar.SECOND));
                        
                        fechaCompleta = diaSemana + ", " + fechaFmt;

                        
        return fechaCompleta;
    }

    //hora en letra. Ejemplo: QUINCE horas, y CINCUENTA minutos
    public static String convertirHora_A_letras(Date fecha) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);

        String horaLetras = "";
        String minutosLetras = "";
        String txtHoraLetras = "";
        horaLetras = (TransformadorNumerosLetrasUtil.transformador(String.valueOf(calendar.get(Calendar.HOUR)))).toString();
        minutosLetras = (TransformadorNumerosLetrasUtil.transformador(String.valueOf(calendar.get(Calendar.MINUTE)))).toString();
        txtHoraLetras = horaLetras + " horas, y " + minutosLetras + " minutos";
        return txtHoraLetras;
    }

    public static String convertirAño_A_Letras(int año) {
        return (TransformadorNumerosLetrasUtil.transformador(String.valueOf(año))).toString();
    }

    public static String convertirMes_A_Letras(int mes) {
        return MES[mes];
    }

    public static String convertirDia_A_Letras(int dia) {
        return DIA[dia - 1];
    }

    public static String convertirDiaSemana_A_Letras(int dia) {
        return DIASEMANA[dia];
    }
    
    
}
