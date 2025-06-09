/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n1_simuladorBancario
 * Autor: Equipo Cupi2 2017
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */
package uniandes.cupi2.simuladorBancario.mundo;

/**
 * Clase que representa el simulador bancario para las tres cuentas de un cliente.
 */
public class SimuladorBancario
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * C�dula del cliente.
     */
    private String cedula;

    /**
     * Nombre del cliente.
     */
    private String nombre;

    /**
     * Mes actual.
     */
    private int mesActual;

    /**
     * Cuenta corriente del cliente.
     */
    private CuentaCorriente corriente;

    /**
     * Cuenta de ahorros del cliente.
     */
    private CuentaAhorros ahorros;

    /**
     * CDT del cliente.
     */
    private CDT inversion;

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Inicializa el simulador con la informaci�n del cliente. <br>
     * <b>post: </b> El mes fue inicializado en 1, y las tres cuentas (CDT, corriente y de ahorros) fueron inicializadas como vac�as. <br>
     * @param pCedula C�dula del nuevo cliente. pCedula != null && pCedula != "".
     * @param pNombre Nombre del nuevo cliente. pNombre != null && pNombre != "".
     */
    public SimuladorBancario( String pCedula, String pNombre )
    {
        // Inicializa los atributos personales del cliente
        nombre = pNombre;
        cedula = pCedula;
        // Inicializa el mes en el 1
        mesActual = 1;
        // Inicializa las tres cuentas en vac�o
        corriente = new CuentaCorriente( );
        ahorros = new CuentaAhorros( );
        inversion = new CDT( );
    }

    /**
     * Retorna el nombre del cliente.
     * @return Nombre del cliente.
     */
    public String darNombre( )
    {
        return nombre;
    }

    /**
     * Retorna la c�dula del cliente.
     * @return C�dula del cliente.
     */
    public String darCedula( )
    {
        return cedula;
    }

    /**
     * Retorna la cuenta corriente del cliente.
     * @return Cuenta corriente del cliente.
     */
    public CuentaCorriente darCuentaCorriente( )
    {
        return corriente;
    }

    /**
     * Retorna la cuenta de ahorros del cliente.
     * @return Cuenta de ahorros del cliente.
     */
    public CuentaAhorros darCuentaAhorros( )
    {
        return ahorros;
    }

    /**
     * Retorna el CDT del cliente.
     * @return CDT del cliente.
     */
    public CDT darCDT( )
    {
        return inversion;
    }

    /**
     * Retorna el mes en el que se encuentra la simulaci�n.
     * @return Mes actual.
     */
    public int darMesActual( )
    {
        return mesActual;
    }

    /**
     * Calcula el saldo total de las cuentas del cliente.
     * @return Saldo total de las cuentas del cliente.
     */
    public double calcularSaldoTotal( )
    {
        return corriente.darSaldo( ) + ahorros.darSaldo( ) + inversion.calcularValorPresente( mesActual );
    }

    /**
     * Invierte un monto de dinero en un CDT. <br>
     * <b>post: </b> Invirti� un monto de dinero en un CDT.
     * @param pMonto Monto de dinero a invertir en un CDT. pMonto > 0.
     * @param pInteresMensual Inter�s del CDT elegido por el cliente. pInteresMensual > 0.
     */
    public void invertirCDT( double pMonto, double pInteresMensual )
    {
        inversion.invertir( pMonto, pInteresMensual, mesActual );
    }

    /**
     * Consigna un monto de dinero en la cuenta corriente. <br>
     * <b>post: </b> Consign� un monto de dinero en la cuenta corriente.
     * @param pMonto Monto de dinero a consignar en la cuenta. pMonto > 0.
     */
    public void consignarCuentaCorriente( double pMonto )
    {
        corriente.consignarMonto( pMonto );
    }

    /**
     * Consigna un monto de dinero en la cuenta de ahorros. <br>
     * * <b>post: </b> Consign� un monto de dinero en la cuenta de ahorros.
     * @param pMonto Monto de dinero a consignar en la cuenta. pMonto > 0.
     */
    public void consignarCuentaAhorros( double pMonto )
    {
        ahorros.consignarMonto( pMonto );
    }

    /**
     * Retira un monto de dinero de la cuenta corriente. <br>
     * <b>post: </b> Se redujo el saldo de la cuenta en el monto especificado.
     * @param pMonto Monto de dinero a retirar de la cuenta. pMonto > 0.
     */
    public void retirarCuentaCorriente( double pMonto )
    {
        corriente.retirarMonto( pMonto );
    }

    /**
     * Retira un monto de dinero de la cuenta de ahorros. <br>
     * <b>post: </b> Se redujo el saldo de la cuenta en el monto especificado.
     * @param pMonto Monto de dinero a retirar de la cuenta. pMonto > 0.
     */
    public void retirarCuentaAhorros( double pMonto )
    {
        ahorros.retirarMonto( pMonto );
    }

    /**
     * Avanza en un mes la simulaci�n. <br>
     * <b>post: </b> Se avanz� el mes de la simulaci�n en 1. Se actualiz� el saldo de la cuenta de ahorros.
     */
    public void avanzarMesSimulacion( )
    {
        mesActual += 1;
        ahorros.actualizarSaldoPorPasoMes( );
    }

    /**
     * Cierra el CDT, pasando el saldo a la cuenta corriente. <br>
     * <b>pre: </b> La cuenta corriente y el CDT han sido inicializados. <br>
     * <b>post: </b> El CDT qued� cerrado y con valores en 0, y la cuenta corriente aument� su saldo en el valor del cierre del CDT.
     */
    public void cerrarCDT( )
    {
        double valorCierreCDT = inversion.cerrar( mesActual );
        corriente.consignarMonto( valorCierreCDT );
    }

    /**
     * Calcula el saldo promedio según el tipo de cuenta
     * @param tipoCuenta Tipo de cuenta (1: Ahorros, 2: Corriente, 3: CDT)
     * @return Mensaje con el resultado del cálculo
     */
    public String calcularSaldoPromedio(int tipoCuenta) {
        double saldo = 0;
        String nombreCuenta = "";
        
        switch(tipoCuenta) {
            case 1:
                saldo = ahorros.darSaldo();
                nombreCuenta = "Cuenta de Ahorros";
                break;
            case 2:
                saldo = corriente.darSaldo();
                nombreCuenta = "Cuenta Corriente";
                break;
            case 3:
                saldo = inversion.calcularValorPresente(mesActual);
                nombreCuenta = "CDT";
                break;
        }
        
        return String.format("%s\nSaldo actual: $%.2f", nombreCuenta, saldo);
    }

    public String calcularSaldoPromedio(int tipoCuenta, int mesInicio, int mesFin) {
        if (mesInicio > mesFin || mesInicio < 1 || mesFin > mesActual) {
            return "Error: Periodo invalido";
        }

        double saldoPromedio = 0;
        String nombreCuenta = "";
        int mesesTranscurridos = mesFin - mesInicio + 1;
        
        switch(tipoCuenta) {
            case 1:
                double saldoAhorros = ahorros.darSaldo();
                double interesMensual = ahorros.darInteresMensual();
                saldoPromedio = saldoAhorros * (1 + interesMensual * mesesTranscurridos);
                nombreCuenta = "Cuenta de Ahorros";
                break;
                
            case 2:
                saldoPromedio = corriente.darSaldo();
                nombreCuenta = "Cuenta Corriente";
                break;
                
            case 3:
                if (inversion != null) {
                    saldoPromedio = inversion.calcularValorPromedio(mesInicio, mesFin);
                    nombreCuenta = "CDT";
                } else {
                    return "Error: No hay un CDT activo";
                }
                break;
        }
        
        StringBuilder resultado = new StringBuilder();
        resultado.append("Resultados del calculo:\n\n");
        resultado.append("Cuenta: ").append(nombreCuenta).append("\n");
        resultado.append("Periodo: Mes ").append(mesInicio).append(" - Mes ").append(mesFin).append("\n");
        resultado.append("Numero de meses: ").append(mesesTranscurridos).append("\n");
        resultado.append(String.format("Saldo promedio: $%,.2f", saldoPromedio));
        
        return resultado.toString();
    }

    /**
     * Método para la extensión 1
     */
    public String metodo1() {
        return "SELECCIONAR_CUENTA";
    }

    /**
     * Retorna el resultado de la extensi�n 2.
     * @return Respuesta 2.
     */
    public String metodo2( )
    {
        return "Respuesta 2";
    }

    /**
     * Genera un resumen de todas las transacciones del mes actual
     * @return String con el resumen de transacciones
     */
    public String generarResumenTransacciones() {
        StringBuilder resumen = new StringBuilder();
        resumen.append("RESUMEN DE TRANSACCIONES - MES ").append(mesActual).append("\n\n");
        
        // Resumen Cuenta de Ahorros
        resumen.append("CUENTA DE AHORROS\n");
        resumen.append("Saldo actual: $").append(String.format("%.2f", ahorros.darSaldo())).append("\n");
        resumen.append("Interes mensual: ").append(String.format("%.2f%%", ahorros.darInteresMensual() * 100)).append("\n\n");
        
        // Resumen Cuenta Corriente
        resumen.append("CUENTA CORRIENTE\n");
        resumen.append("Saldo actual: $").append(String.format("%.2f", corriente.darSaldo())).append("\n\n");
        
        // Resumen CDT
        if (inversion != null) {
            resumen.append("CDT\n");
            resumen.append("Valor actual: $").append(String.format("%.2f", inversion.calcularValorPresente(mesActual))).append("\n");
            resumen.append("Interes mensual: ").append(String.format("%.2f%%", inversion.darInteresMensual() * 100)).append("\n\n");
        }
        
        // Saldo total
        double saldoTotal = ahorros.darSaldo() + corriente.darSaldo();
        if (inversion != null) {
            saldoTotal += inversion.calcularValorPresente(mesActual);
        }
        resumen.append("SALDO TOTAL: $").append(String.format("%.2f", saldoTotal));
        
        return resumen.toString();
    }
}