/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n1_simuladorBancario
 * Autor: Equipo Cupi2 2017
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
 */
package uniandes.cupi2.simuladorBancario.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uniandes.cupi2.simuladorBancario.mundo.SimuladorBancario;

/**
 * Clase usada para verificar que los métodos de la clase CuentaBancaria estén correctamente implementados.
 */
public class SimuladorBancarioTest
{
    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Cuenta bancaria usada para las pruebas.
     */
    private SimuladorBancario cuenta;

    // -----------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------

    /**
     * Escenario 1: Crea una cuenta bancaria sin CDT, sin cuenta de ahorros y sin cuenta corriente.
     */
    private void setupEscenario1( )
    {
        cuenta = new SimuladorBancario( "50.152.468", "Sergio López" );
    }

    /**
     * Escenario 2: Crea una cuenta bancaria con valores en todas las cuentas.
     */
    private void setupEscenario2( )
    {
        setupEscenario1( );
        cuenta.invertirCDT( 1000000, 10 );
        cuenta.consignarCuentaAhorros( 500000 );
        cuenta.consignarCuentaCorriente( 600000 );
    }

    /**
     * Escenario 3:Cre una cuenta bancaria con valores en todas las cuentas y retiros.
     */
    private void setupEscenario3( )
    {
        setupEscenario1( );
        cuenta.invertirCDT( 1000000, 10 );
        cuenta.consignarCuentaAhorros( 500000 );
        cuenta.retirarCuentaAhorros( 200000 );
        cuenta.consignarCuentaCorriente( 600000 );
        cuenta.retirarCuentaCorriente( 100000 );
    }

    /**
     * Prueba 1: Método que se encarga de verificar el método invertirCDT.<br>
     * <b> Métodos a probar: </b> <br>
     * invertirCDT<br>
     * darCDT<br>
     * <b> Casos de prueba: </b> <br>
     * 1. El CDT no tiene saldo..
     */
    @Test
    public void testInvertirCDT( )
    {
        setupEscenario2( );
        assertEquals( "El valor presente del CDT no es el esperado", 1000000, cuenta.darCDT( ).calcularValorPresente( 1 ), 2 );

    }

    /**
     * Prueba 2: Método que se encarga de verificar el método consignarCuentaCorriente.<br>
     * <b> Métodos a probar: </b> <br>
     * consignarCuentaCorriente<br>
     * darCuentaCorriente<br>
     * <b> Casos de prueba: </b> <br>
     * 1. La cuenta corriente no tiene saldo.
     */
    @Test
    public void testConsignarCuentaCorriente( )
    {
        setupEscenario2( );
        assertEquals( "El saldo de la cuenta corriente no es el esperado", 600000, cuenta.darCuentaCorriente( ).darSaldo( ), 2 );

    }

    /**
     * Prueba 3: Método que se encarga de verificar el método consignarCuentaAhorro.<br>
     * <b> Métodos a probar: </b> <br>
     * consignarCuentaAhorro<br>
     * darCuentaAhorro<br>
     * <b> Casos de prueba: </b> <br>
     * 1. La cuenta de ahorros no tiene saldo.
     */
    @Test
    public void testConsignarCuentaAhorros( )
    {
        setupEscenario2( );
        assertEquals( "El saldo de la cuenta de ahorros no es el esperado", 500000, cuenta.darCuentaAhorros( ).darSaldo( ), 2 );
    }

    /**
     * Prueba 4: Método que se encarga de verificar el método cerrarCDT.<br>
     * <b> Métodos a probar: </b> <br>
     * cerrarCDT<br>
     * darCDT<br>
     * calcularSaldoTotal<br>
     * <b> Casos de prueba: </b> <br>
     * 1. El CDT tiene saldo.
     */
    @Test
    public void testCerrarCDT( )
    {
        setupEscenario2( );
        double cerrado = cuenta.darCDT( ).calcularValorPresente( 1 );
        double saldo = 1100000 + cerrado;
        cuenta.cerrarCDT( );
        assertEquals( "El saldo de la cuenta bancaria no es el esperado", ( int )saldo, ( int )cuenta.calcularSaldoTotal( ), 0.01 );
    }

    /**
     * Prueba 5: Método que se encarga de verificar el método retirarCuentaCorriente.<br>
     * <b> Métodos a probar: </b> <br>
     * retirarCuentaCorriente<br>
     * darCuentaCorriente<br>
     * <b> Casos de prueba: </b> <br>
     * 1. La cuenta corriente tiene suficiente saldo para retirar.
     */
    @Test
    public void testRetirarCuentaCorriente( )
    {
        setupEscenario3( );
        assertEquals( "El saldo de la cuenta corriente no es el esperado", 500000, cuenta.darCuentaCorriente( ).darSaldo( ), 2 );
    }

    /**
     * Prueba 6: Método que se encarga de verificar el método retirarCuentaAhorro.<br>
     * <b> Métodos a probar: </b> <br>
     * retirarCuentaAhorro<br>
     * darCuentaAhorro<br>
     * <b> Casos de prueba: </b> <br>
     * 1. La cuenta ahorros tiene suficiente saldo para retirar.
     */
    @Test
    public void testRetirarCuentaAhorros( )
    {
        setupEscenario3( );
        assertEquals( "El saldo de la cuenta de ahorro no es el esperado", 300000, cuenta.darCuentaAhorros( ).darSaldo( ), 2 );
    }

    /**
     * Prueba 7: Método que se encarga de verificar el método calcularSaldoTotal.<br>
     * <b> Métodos a probar: </b> <br>
     * calcularSaldoTotal<br>
     * <b> Casos de prueba: </b> <br>
     * 1. El CDT, la cuenta ahorros y la cuenta corriente tienen saldo.
     */
    @Test
    public void testSaldoTotal( )
    {
        setupEscenario3( );
        assertEquals( 1800000, ( int )cuenta.calcularSaldoTotal( ) );
    }

}
