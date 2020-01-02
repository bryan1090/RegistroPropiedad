/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import com.nitgen.SDK.BSP.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Dany
 */
@Named(value = "controladorHuellaDigitalUtil")
@Dependent
public class ControladorHuellaDigitalUtil implements Serializable {

    @Getter
    @Setter
    private NBioBSPJNI bsp;

    @Getter
    @Setter
    private NBioBSPJNI.FINGER_ID idDedo;

    @Getter
    @Setter
    private NBioBSPJNI.DEVICE_ENUM_INFO deviceEnumInfo;

    @Getter
    @Setter
    private NBioBSPJNI.FIR_HANDLE hSavedFIR;

    @Getter
    @Setter
    private NBioBSPJNI.FIR_TEXTENCODE textSavedFIR;

    @Getter
    @Setter
    private NBioBSPJNI.FIR fullSavedFIR;
    
    @Getter
    @Setter
    private String textFIR;

    public ControladorHuellaDigitalUtil() {
    }

    public void cargarHuella(String textFIR) {
        textSavedFIR = bsp.new FIR_TEXTENCODE();
        textSavedFIR.TextFIR = textFIR;
    }

    public void abrirLector() {
        bsp = new NBioBSPJNI();
        deviceEnumInfo = bsp.new DEVICE_ENUM_INFO();
        bsp.EnumerateDevice(deviceEnumInfo);
        bsp.OpenDevice(deviceEnumInfo.DeviceInfo[0].NameID, deviceEnumInfo.DeviceInfo[0].Instance);

        System.err.println("dispositivo Abierto:" + deviceEnumInfo.DeviceInfo[0].Name + "|" + deviceEnumInfo.DeviceInfo[0].NameID + deviceEnumInfo.DeviceInfo[0].Instance);
    }

    public void cerrarLector() {
        bsp.dispose();
        bsp.CloseDevice();
        System.err.println("dispositivo Liberado y cerrado");
    }

    public void registrarHuellas() {
        abrirLector();
        hSavedFIR = bsp.new FIR_HANDLE();
        bsp.Enroll(hSavedFIR, null);

        //obtener texto FIR
        if (bsp.IsErrorOccured() == false) {
            textSavedFIR = bsp.new FIR_TEXTENCODE();
            bsp.GetTextFIRFromHandle(hSavedFIR, textSavedFIR);
            System.out.print("Texto FIR:" + textSavedFIR);
        }

        //obtener binario FIR
        if (bsp.IsErrorOccured() == false) {
            fullSavedFIR = bsp.new FIR();
            bsp.GetFIRFromHandle(hSavedFIR, fullSavedFIR);
            System.out.print("Binario FIR:" + fullSavedFIR);
        }
        cerrarLector();
    }

    public void capturarHuella() {
        abrirLector();
        hSavedFIR = bsp.new FIR_HANDLE();
        bsp.Capture(hSavedFIR);

        //obtener texto FIR
        if (bsp.IsErrorOccured() == false) {
            textSavedFIR = bsp.new FIR_TEXTENCODE();
            setTextFIR(textSavedFIR.TextFIR);
            bsp.GetTextFIRFromHandle(hSavedFIR, textSavedFIR);
            System.out.print("Texto FIR:" + textSavedFIR.TextFIR);
        }

        //obtener binario FIR
        if (bsp.IsErrorOccured() == false) {
            fullSavedFIR = bsp.new FIR();
            bsp.GetFIRFromHandle(hSavedFIR, fullSavedFIR);
            System.out.print("Binario FIR:" + fullSavedFIR.toString());
        }
        JsfUtil.addSuccessMessage("Huella Capturada");
        cerrarLector();
    }

    public void verificarHuella() {
        String textFIR = "";
        abrirLector();
        cargarHuella(textFIR);
        NBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
        Boolean bResult = Boolean.FALSE;
        NBioBSPJNI.FIR_PAYLOAD payload = bsp.new FIR_PAYLOAD();

        //Establecer los datos textFIR almacenados
        inputFIR.SetTextFIR(textSavedFIR);
        bsp.Verify(inputFIR, bResult, payload);
        if (bsp.IsErrorOccured() == false) {
            if (bResult) {
                System.out.print("Verify OK - Payload: ");
                JsfUtil.addSuccessMessage("Huella verificada");
            } else {
                System.out.print("Verify Failed");
                JsfUtil.addErrorMessage("Huella Erronea");
            }
        } else {
            System.out.print("Verify Failed");
            JsfUtil.addErrorMessage("Huella Erronea");
        }
        cerrarLector();
    }

}
