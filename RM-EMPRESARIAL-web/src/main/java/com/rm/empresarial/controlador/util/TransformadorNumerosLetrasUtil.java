/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author DJimenez
 */
@Named(value = "transformadorNumerosLetrasUtil")
@Dependent
public class TransformadorNumerosLetrasUtil implements Serializable {

    public TransformadorNumerosLetrasUtil() {

    }

    public static StringBuilder transformador(String n) {
        String numero1 = new String();
        String numero2 = new String();
        StringBuilder result = new StringBuilder();
        StringBuilder result2 = new StringBuilder();
        String punto = "CON ";
        int aux = 0;
        for (int i = 0; i < n.length(); i++) {
            if (n.charAt(i) == '.') {
                for (int j = i + 1; j < n.length(); j++) {
                    aux++;
                }
                if (aux == 1) {
                    if (n.charAt(i + 1) != '0') {
                        n = n + "0";
                    }
                }
                if (aux >= 2) {
                    if (n.charAt(i + 1) == '0' && n.charAt(i + 2) == '0') {
                        result2 = new StringBuilder();
                    } else {
                        result2.append(punto);
                    }
                } else {
                    if (n.charAt(i + 1) == '0') {
                        result2 = new StringBuilder();
                    } else {
                        result2.append(punto);
                    }
                }

                numero2 = new String();
                for (int k = i + 1; k < n.length(); k++) {
                    numero2 = numero2 + n.charAt(k);
                }
                i = n.length();
            } else {
                numero1 = numero1 + n.charAt(i);
            }
        }

        System.out.print(numero1 + " CON " + numero2);
        int mil = (Integer.parseInt(numero1)) / 1000;
        int milcentena = mil / 100;
        int mildecena = (mil % 100) / 10;
        int milunidad = mil % 10;
        int tamañoNum1 = numero1.length();
        if (mil != 0) {
            numero1 = new String();
            for (int j = (tamañoNum1 - 3); j <= (tamañoNum1 - 1); j++) {
                numero1 = numero1 + n.charAt(j);
            }
        }
        int centenas1 = (Integer.parseInt(numero1)) / 100;
        int decenas1 = ((Integer.parseInt(numero1)) % 100) / 10;
        int unidades1 = ((Integer.parseInt(numero1)) % 10);
        int centenas2 = 0;
        int decenas2 = 0;
        int unidades2 = 0;
        if (0 != numero2.length()) {
            centenas2 = Integer.parseInt(numero2) / 100;
            decenas2 = ((Integer.parseInt(numero2)) % 100) / 10;
            unidades2 = ((Integer.parseInt(numero2)) % 10);
        }

        switch (milcentena) {
            case 0:
                break;
            case 1:
                if (mildecena == 0 && milunidad == 0) {
                    result.append("CIEN ");
                } else {
                    result.append("CIENTO ");
                }
                break;
            case 2:
                result.append("DOSCIENTOS ");
                break;
            case 3:
                result.append("TRESCIENTOS ");
                break;
            case 4:
                result.append("CUATROSCIENTOS ");
                break;
            case 5:
                result.append("QUINIENTOS ");
                break;
            case 6:
                result.append("SEISCIENTOS ");
                break;
            case 7:
                result.append("SETECIENTOS ");
                break;
            case 8:
                result.append("OCHOCIENTOS ");
                break;
            case 9:
                result.append("NOVECIENTOS ");
                break;
        }

        switch (mildecena) {
            case 0:
                break;
            case 1:
                if (milunidad == 0) {
                    result.append("DIEZ ");
                } else if (milunidad == 1) {
                    result.append("ONCE ");
                } else if (milunidad == 2) {
                    result.append("DOCE ");
                } else if (milunidad == 3) {
                    result.append("TRECE ");
                } else if (milunidad == 4) {
                    result.append("CATORCE ");
                } else if (milunidad == 5) {
                    result.append("QUINCE ");
                } else {
                    result.append("DIECI");
                }
                break;
            case 2:
                if (milunidad == 0) {
                    result.append("VEINTE ");
                } else {
                    result.append("VEINTI");
                }
                break;
            case 3:
                result.append("TREINTA ");
                break;
            case 4:
                result.append("CUARENTA ");
                break;
            case 5:
                result.append("CINCUENTA ");
                break;
            case 6:
                result.append("SESENTA ");
                break;
            case 7:
                result.append("SETENTA ");
                break;
            case 8:
                result.append("OCHENTA ");
                break;
            case 9:
                result.append("NOVENTA ");
                break;
        }

        if (mildecena > 2 && milunidad > 0) {
            result.append("Y ");
        }

        if ((milunidad != 1 && milunidad != 2 && milunidad != 3 && milunidad != 4 && milunidad != 5) || (mildecena != 1) || (milcentena != 0)) {
            switch (milunidad) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    result.append("DOS ");
                    break;
                case 3:
                    result.append("TRES ");
                    break;
                case 4:
                    result.append("CUATRO ");
                    break;
                case 5:
                    result.append("CINCO ");
                    break;
                case 6:
                    result.append("SEIS ");
                    break;
                case 7:
                    result.append("SIETE ");
                    break;
                case 8:
                    result.append("OCHO ");
                    break;
                case 9:
                    result.append("NUEVE ");
                    break;
            }

        }
        if (mil != 0) {
            result.append("MIL ");
        }

        //Transforma numero antes de la coma//
        switch (centenas1) {
            case 0:
                break;
            case 1:
                if (decenas1 == 0 && unidades1 == 0) {
                    result.append("CIEN ");
                } else {
                    result.append("CIENTO ");
                }
                break;
            case 2:
                result.append("DOSCIENTOS ");
                break;
            case 3:
                result.append("TRESCIENTOS ");
                break;
            case 4:
                result.append("CUATROSCIENTOS ");
                break;
            case 5:
                result.append("QUINIENTOS ");
                break;
            case 6:
                result.append("SEISCIENTOS ");
                break;
            case 7:
                result.append("SETECIENTOS ");
                break;
            case 8:
                result.append("OCHOCIENTOS ");
                break;
            case 9:
                result.append("NOVECIENTOS ");
                break;
        }

        switch (decenas1) {
            case 0:
                break;
            case 1:
                if (unidades1 == 0) {
                    result.append("DIEZ ");
                } else if (unidades1 == 1) {
                    result.append("ONCE ");
                } else if (unidades1 == 2) {
                    result.append("DOCE ");
                } else if (unidades1 == 3) {
                    result.append("TRECE ");
                } else if (unidades1 == 4) {
                    result.append("CATORCE ");
                } else if (unidades1 == 5) {
                    result.append("QUINCE ");
                } else {
                    result.append("DIECI");
                }
                break;
            case 2:
                if (unidades1 == 0) {
                    result.append("VEINTE ");
                } else {
                    result.append("VEINTI");
                }
                break;
            case 3:
                result.append("TREINTA ");
                break;
            case 4:
                result.append("CUARENTA ");
                break;
            case 5:
                result.append("CINCUENTA ");
                break;
            case 6:
                result.append("SESENTA ");
                break;
            case 7:
                result.append("SETENTA ");
                break;
            case 8:
                result.append("OCHENTA ");
                break;
            case 9:
                result.append("NOVENTA ");
                break;
        }

        if (decenas1 > 2 && unidades1 > 0) {
            result.append("Y ");
        }

        if ((unidades1 != 1 && unidades1 != 2 && unidades1 != 3 && unidades1 != 4 && unidades1 != 5) && (decenas1 != 1) && (centenas1 != 0)) {
            switch (unidades1) {
                case 0:
                    break;
                case 1:
                    result.append("UNO ");
                    break;
                case 2:
                    result.append("DOS ");
                    break;
                case 3:
                    result.append("TRES ");
                    break;
                case 4:
                    result.append("CUATRO ");
                    break;
                case 5:
                    result.append("CINCO ");
                    break;
                case 6:
                    result.append("SEIS ");
                    break;
                case 7:
                    result.append("SIETE ");
                    break;
                case 8:
                    result.append("OCHO ");
                    break;
                case 9:
                    result.append("NUEVE ");
                    break;
            }
        } else if ((unidades1 != 1 && unidades1 != 2 && unidades1 != 3 && unidades1 != 4 && unidades1 != 5) && (decenas1 == 1) && (centenas1 != 0)) {
            switch (unidades1) {
                case 0:
                    break;
                case 1:
                    result.append("UNO ");
                    break;
                case 2:
                    result.append("DOS ");
                    break;
                case 3:
                    result.append("TRES ");
                    break;
                case 4:
                    result.append("CUATRO ");
                    break;
                case 5:
                    result.append("CINCO ");
                    break;
                case 6:
                    result.append("SEIS ");
                    break;
                case 7:
                    result.append("SIETE ");
                    break;
                case 8:
                    result.append("OCHO ");
                    break;
                case 9:
                    result.append("NUEVE ");
                    break;
            }
        } else if ((unidades1 != 1 && unidades1 != 2 && unidades1 != 3 && unidades1 != 4 && unidades1 != 5) && (decenas1 == 1) && (centenas1 == 0)) {
            switch (unidades1) {
                case 0:
                    break;
                case 1:
                    result.append("UNO ");
                    break;
                case 2:
                    result.append("DOS ");
                    break;
                case 3:
                    result.append("TRES ");
                    break;
                case 4:
                    result.append("CUATRO ");
                    break;
                case 5:
                    result.append("CINCO ");
                    break;
                case 6:
                    result.append("SEIS ");
                    break;
                case 7:
                    result.append("SIETE ");
                    break;
                case 8:
                    result.append("OCHO ");
                    break;
                case 9:
                    result.append("NUEVE ");
                    break;
            }
        } else if (unidades1 != 0 && decenas1 == 0 && centenas1 == 0) {
            switch (unidades1) {
                case 0:
                    break;
                case 1:
                    result.append("UNO ");
                    break;
                case 2:
                    result.append("DOS ");
                    break;
                case 3:
                    result.append("TRES ");
                    break;
                case 4:
                    result.append("CUATRO ");
                    break;
                case 5:
                    result.append("CINCO ");
                    break;
                case 6:
                    result.append("SEIS ");
                    break;
                case 7:
                    result.append("SIETE ");
                    break;
                case 8:
                    result.append("OCHO ");
                    break;
                case 9:
                    result.append("NUEVE ");
                    break;
            }
        } else if (unidades1 != 0 && decenas1 != 1 && centenas1 == 0) {
            switch (unidades1) {
                case 0:
                    break;
                case 1:
                    result.append("UNO ");
                    break;
                case 2:
                    result.append("DOS ");
                    break;
                case 3:
                    result.append("TRES ");
                    break;
                case 4:
                    result.append("CUATRO ");
                    break;
                case 5:
                    result.append("CINCO ");
                    break;
                case 6:
                    result.append("SEIS ");
                    break;
                case 7:
                    result.append("SIETE ");
                    break;
                case 8:
                    result.append("OCHO ");
                    break;
                case 9:
                    result.append("NUEVE ");
                    break;
            }
        } else if (unidades1 != 0 && decenas1 != 1 && centenas1 != 0) {
            switch (unidades1) {
                case 0:
                    break;
                case 1:
                    result.append("UNO ");
                    break;
                case 2:
                    result.append("DOS ");
                    break;
                case 3:
                    result.append("TRES ");
                    break;
                case 4:
                    result.append("CUATRO ");
                    break;
                case 5:
                    result.append("CINCO ");
                    break;
                case 6:
                    result.append("SEIS ");
                    break;
                case 7:
                    result.append("SIETE ");
                    break;
                case 8:
                    result.append("OCHO ");
                    break;
                case 9:
                    result.append("NUEVE ");
                    break;
            }
        }

        //Transformar numero despues de la coma//
        if (numero2.length() != 0) {
            switch (centenas2) {
                case 0:
                    break;
                case 1:
                    if (decenas2 == 0 && unidades2 == 0) {
                        result2.append("CIEN ");
                    } else {
                        result2.append("CIENTO ");
                    }
                    break;
                case 2:
                    result2.append("DOSCIENTOS ");
                    break;
                case 3:
                    result2.append("TRESCIENTOS ");
                    break;
                case 4:
                    result2.append("CUATROSCIENTOS ");
                    break;
                case 5:
                    result2.append("QUINIENTOS ");
                    break;
                case 6:
                    result2.append("SEISCIENTOS ");
                    break;
                case 7:
                    result2.append("SETECIENTOS ");
                    break;
                case 8:
                    result2.append("OCHOCIENTOS ");
                    break;
                case 9:
                    result2.append("NOVECIENTOS ");
                    break;
            }

            switch (decenas2) {
                case 0:
                    break;
                case 1:
                    if (unidades2 == 0) {
                        result2.append("DIEZ ");
                    } else if (unidades2 == 1) {
                        result2.append("ONCE ");
                    } else if (unidades2 == 2) {
                        result2.append("DOCE ");
                    } else if (unidades2 == 3) {
                        result2.append("TRECE ");
                    } else if (unidades2 == 4) {
                        result2.append("CATORCE ");
                    } else if (unidades2 == 5) {
                        result2.append("QUINCE ");
                    } else {
                        result2.append("DIECI");
                    }
                    break;
                case 2:
                    if (unidades2 == 0) {
                        result2.append("VEINTE ");
                    } else {
                        result2.append("VEINTI");
                    }
                    break;
                case 3:
                    result2.append("TREINTA ");
                    break;
                case 4:
                    result2.append("CUARENTA ");
                    break;
                case 5:
                    result2.append("CINCUENTA ");
                    break;
                case 6:
                    result2.append("SESENTA ");
                    break;
                case 7:
                    result2.append("SETENTA ");
                    break;
                case 8:
                    result2.append("OCHENTA ");
                    break;
                case 9:
                    result2.append("NOVENTA ");
                    break;
            }

            if (decenas2 > 2 && unidades2 > 0) {
                result2.append("Y ");
            }

            if (decenas2 == 0) {
                result2.append("CERO ");
            }

            if ((unidades2 != 1 && unidades2 != 2 && unidades2 != 3 && unidades2 != 4 && unidades2 != 5) && (decenas2 != 1) && (centenas2 != 0)) {
                switch (unidades2) {
                    case 0:
                        break;
                    case 1:
                        result2.append("UNO ");
                        break;
                    case 2:
                        result2.append("DOS ");
                        break;
                    case 3:
                        result2.append("TRES ");
                        break;
                    case 4:
                        result2.append("CUATRO ");
                        break;
                    case 5:
                        result2.append("CINCO ");
                        break;
                    case 6:
                        result2.append("SEIS ");
                        break;
                    case 7:
                        result2.append("SIETE ");
                        break;
                    case 8:
                        result2.append("OCHO ");
                        break;
                    case 9:
                        result2.append("NUEVE ");
                        break;
                }
            } else if ((unidades2 != 1 && unidades2 != 2 && unidades2 != 3 && unidades2 != 4 && unidades2 != 5) && (decenas2 == 1) && (centenas2 != 0)) {
                switch (unidades2) {
                    case 0:
                        break;
                    case 1:
                        result2.append("UNO ");
                        break;
                    case 2:
                        result2.append("DOS ");
                        break;
                    case 3:
                        result2.append("TRES ");
                        break;
                    case 4:
                        result2.append("CUATRO ");
                        break;
                    case 5:
                        result2.append("CINCO ");
                        break;
                    case 6:
                        result2.append("SEIS ");
                        break;
                    case 7:
                        result2.append("SIETE ");
                        break;
                    case 8:
                        result2.append("OCHO ");
                        break;
                    case 9:
                        result2.append("NUEVE ");
                        break;
                }
            } else if ((unidades2 != 1 && unidades2 != 2 && unidades2 != 3 && unidades2 != 4 && unidades2 != 5) && (decenas2 == 1) && (centenas2 == 0)) {
                switch (unidades2) {
                    case 0:
                        break;
                    case 1:
                        result2.append("UNO ");
                        break;
                    case 2:
                        result2.append("DOS ");
                        break;
                    case 3:
                        result2.append("TRES ");
                        break;
                    case 4:
                        result2.append("CUATRO ");
                        break;
                    case 5:
                        result2.append("CINCO ");
                        break;
                    case 6:
                        result2.append("SEIS ");
                        break;
                    case 7:
                        result2.append("SIETE ");
                        break;
                    case 8:
                        result2.append("OCHO ");
                        break;
                    case 9:
                        result2.append("NUEVE ");
                        break;
                }
            } else if (unidades2 != 0 && decenas2 == 0 && centenas2 == 0) {
                switch (unidades2) {
                    case 0:
                        break;
                    case 1:
                        result2.append("UNO ");
                        break;
                    case 2:
                        result2.append("DOS ");
                        break;
                    case 3:
                        result2.append("TRES ");
                        break;
                    case 4:
                        result2.append("CUATRO ");
                        break;
                    case 5:
                        result2.append("CINCO ");
                        break;
                    case 6:
                        result2.append("SEIS ");
                        break;
                    case 7:
                        result2.append("SIETE ");
                        break;
                    case 8:
                        result2.append("OCHO ");
                        break;
                    case 9:
                        result2.append("NUEVE ");
                        break;
                }
            } else if (unidades2 != 0 && decenas2 != 1 && centenas2 == 0) {
                switch (unidades2) {
                    case 0:
                        break;
                    case 1:
                        result2.append("UNO ");
                        break;
                    case 2:
                        result2.append("DOS ");
                        break;
                    case 3:
                        result2.append("TRES ");
                        break;
                    case 4:
                        result2.append("CUATRO ");
                        break;
                    case 5:
                        result2.append("CINCO ");
                        break;
                    case 6:
                        result2.append("SEIS ");
                        break;
                    case 7:
                        result2.append("SIETE ");
                        break;
                    case 8:
                        result2.append("OCHO ");
                        break;
                    case 9:
                        result2.append("NUEVE ");
                        break;
                }
            } else if (unidades2 != 0 && decenas2 != 1 && centenas2 != 0) {
                switch (unidades2) {
                    case 0:
                        break;
                    case 1:
                        result2.append("UNO ");
                        break;
                    case 2:
                        result2.append("DOS ");
                        break;
                    case 3:
                        result2.append("TRES ");
                        break;
                    case 4:
                        result2.append("CUATRO ");
                        break;
                    case 5:
                        result2.append("CINCO ");
                        break;
                    case 6:
                        result2.append("SEIS ");
                        break;
                    case 7:
                        result2.append("SIETE ");
                        break;
                    case 8:
                        result2.append("OCHO ");
                        break;
                    case 9:
                        result2.append("NUEVE ");
                        break;
                }
            }
        }

        if (result2.length() != 0) {
            result.append(result2);
        }

        return result;
    }
}
