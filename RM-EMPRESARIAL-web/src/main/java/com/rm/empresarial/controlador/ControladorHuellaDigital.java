/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import javax.inject.Named;
import javax.faces.view.ViewScoped;
import com.nitgen.SDK.BSP.*;
import com.rm.empresarial.controlador.util.JsfUtil;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Dany
 */
@Named(value = "controladorHuellaDigital")
@ViewScoped
public class ControladorHuellaDigital implements Serializable {

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
    private NBioBSPJNI.FIR_TEXTENCODE textCapturedFIR;

    @Getter
    @Setter
    private NBioBSPJNI.FIR fullSavedFIR;

    public ControladorHuellaDigital() {

    }

    public void cargarHuella() {
        textSavedFIR = bsp.new FIR_TEXTENCODE();
        textSavedFIR.TextFIR = "AQAAABQAAAAEAQAAAQASAAEAYQAAAAAA9AAAAAjNk//YZZXDA4L374QqY5s8mHWXisBHiAVJ2skM76jttFez2ZRPcTxTOQ4sT*/UrPHzEsZ52qBKRRKikd*95eUHpQm6FTZudCnTPumUEoMtKCoC2qC4HDOP0GeWtPNsKNd05/*NiXuseffz67Mx4NM0RDJcUOVtYbgPPrdUslssrIfEJEiFsyFn98C55dibS/gxrP2ZOnQDbwYzo6973Mv5LtIj*SRAzhsWUi0EFxDYqa/CTrZky*OHo2ZicBJqT4*IyD0LrQU6K0of/OqBTfnxx3e3H7y2agKEVRnhNIcLrVxHSOzRyh8S7avNp00Ztty/WtqufMM3rqcsBSu86DA";
    }

    public void abrirLector() {
        bsp = new NBioBSPJNI();
        deviceEnumInfo = bsp.new DEVICE_ENUM_INFO();
        bsp.EnumerateDevice(deviceEnumInfo);
        try {
            bsp.OpenDevice(deviceEnumInfo.DeviceInfo[0].NameID, deviceEnumInfo.DeviceInfo[0].Instance);
            System.err.println("dispositivo Abierto:" + deviceEnumInfo.DeviceInfo[0].Name + "|" + deviceEnumInfo.DeviceInfo[0].NameID + deviceEnumInfo.DeviceInfo[0].Instance);
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Dispositivo desconectado");
            System.err.println("dispositivo No conectado");
        }
    }

    public void cerrarLector() {
        bsp.dispose();
        bsp.CloseDevice(deviceEnumInfo.DeviceInfo[0].NameID, deviceEnumInfo.DeviceInfo[0].Instance);
        System.err.println("dispositivo Liberado y cerrado");
    }

    public void registrarHuellas() {
        abrirLector();
        if (deviceEnumInfo.DeviceCount > 0) {
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
    }

    public void capturarHuella() {
        abrirLector();
        if (deviceEnumInfo.DeviceCount > 0) {
            hSavedFIR = bsp.new FIR_HANDLE();
            bsp.Capture(hSavedFIR);

            //obtener texto FIR
            if (bsp.IsErrorOccured() == false) {
                textSavedFIR = bsp.new FIR_TEXTENCODE();
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
    }

    public void verificarHuella() {
        abrirLector();
        cargarHuella();
        NBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
        Boolean bResult = Boolean.FALSE;
        NBioBSPJNI.FIR_PAYLOAD payload = bsp.new FIR_PAYLOAD();

        //Establecer los datos textFIR almacenados
        inputFIR.SetTextFIR(textSavedFIR);
        bsp.Verify(inputFIR, bResult, payload);
        if (bsp.IsErrorOccured() == false) {
            if (bResult) {
                System.out.print("Verify OK - Payload: ");
                cerrarLector();
                JsfUtil.addSuccessMessage("Huella verificada");
            } else {
                System.out.print("Verify Failed");
                cerrarLector();
                JsfUtil.addErrorMessage("Huella Erronea");
            }
        } else {
            System.out.print("Verify Failed");
            cerrarLector();
            JsfUtil.addErrorMessage("Huella Erronea");
        }
    }

    public void verificarHuellasCargadas() {
        NBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
        NBioBSPJNI.INPUT_FIR inputFIR2 = bsp.new INPUT_FIR();
        Boolean bResult = Boolean.FALSE;
        NBioBSPJNI.FIR_PAYLOAD payload = bsp.new FIR_PAYLOAD();
        inputFIR.SetTextFIR(textSavedFIR);
        inputFIR2.SetTextFIR(textCapturedFIR);
        bsp.VerifyMatch(inputFIR, inputFIR2, bResult, payload);
        if (bsp.IsErrorOccured() == false) {
            if (bResult) {
                JsfUtil.addSuccessMessage("Huella verificada");
            } else {
                JsfUtil.addErrorMessage("Huella Erronea");
            }
        }
        cerrarLector();
    }

}
