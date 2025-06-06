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
package uniandes.cupi2.simuladorBancario.interfaz;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import uniandes.cupi2.simuladorBancario.mundo.*;

/**
 * Ventana principal de la aplicaci�n.
 */
@SuppressWarnings("serial")
public class InterfazSimulador extends JFrame
{

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Clase principal del mundo.
     */
    private SimuladorBancario cuenta;

    // -----------------------------------------------------------------
    // Atributos de Interfaz
    // -----------------------------------------------------------------

    /**
     * Panel con la imagen.
     */
    private PanelImagen panelImagen;

    /**
     * Panel de botones para transacciones de cuentas de ahorro.
     */
    private PanelAhorros panelAhorros;

    /**
     * Panel de botones para transacciones de cuentas corriente.
     */
    private PanelCorriente panelCorriente;

    /**
     * Panel de botones para transacciones de CDT.
     */
    private PanelCDT panelCDT;

    /**
     * Panel de botones con las opciones de las transacciones.
     */
    private PanelOpciones panelOpciones;

    /**
     * Panel de visualizacion de datos de las cuentas.
     */
    private PanelSaldos panelSaldos;

    /**
     * Panel de visualizaci�n de datos personales.
     */
    private PanelDatosCliente panelDatos;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Crea una nueva ventana principal del simulador de una cuenta bancaria. <br>
     * <b>post: </b> Se inicializ� la interfaz principal y sus paneles.
     */
    public InterfazSimulador( )
    {
        setTitle( "Simulador bancario" );
        setSize( 600, 580 );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        cuenta = new SimuladorBancario( "1.073.813.055", "Jose Hernandez" );

        panelCDT = new PanelCDT( this );
        panelAhorros = new PanelAhorros( this );
        panelCorriente = new PanelCorriente( this );
        panelOpciones = new PanelOpciones( this );

        panelSaldos = new PanelSaldos( this );
        panelDatos = new PanelDatosCliente( );
        panelImagen = new PanelImagen( );

        // Organizar el panel principal
        getContentPane( ).setLayout( new BorderLayout( ) );

        JPanel panelCentral = new JPanel( );
        panelCentral.setLayout( new BorderLayout( ) );
        JPanel panelTransacciones = new JPanel( );
        panelTransacciones.setLayout( new GridLayout( 4, 1 ) );
        panelTransacciones.setBorder( new CompoundBorder( new EmptyBorder( 0, 0, 5, 0 ), new TitledBorder( "Informaci�n Bancaria" ) ) );

        getContentPane( ).add( panelImagen, BorderLayout.NORTH );
        getContentPane( ).add( panelCentral, BorderLayout.CENTER );
        getContentPane( ).add( panelOpciones, BorderLayout.SOUTH );

        panelCentral.add( panelDatos, BorderLayout.NORTH );
        panelCentral.add( panelTransacciones, BorderLayout.CENTER );

        panelTransacciones.add( panelAhorros );
        panelTransacciones.add( panelCorriente );
        panelTransacciones.add( panelCDT );
        panelTransacciones.add( panelSaldos );

        setLocationRelativeTo( null );
        setResizable( false );

        actualizar( );
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Actualiza la interfaz con los datos de la cuenta. <br>
     * <b>post: </b> Se actualiz� los cambios en los paneles correspondientes.
     */
    public void actualizar( )
    {
        String nombre = cuenta.darNombre( );
        String cedula = cuenta.darCedula( );
        panelDatos.actualizarNombre( nombre );
        panelDatos.actualizarCedula( "" + cedula );
        panelSaldos.actualizarMes( cuenta.darMesActual( ) );
        panelSaldos.actualizarSaldoTotal( formatearValor( cuenta.calcularSaldoTotal( ) ) );

        panelCorriente.actualizarSaldoCorriente(formatearValor(cuenta.darCuentaCorriente().darSaldo()));
        panelAhorros.actualizarSaldoAhorros(formatearValor(cuenta.darCuentaAhorros().darSaldo()) + 
            "   [" + (cuenta.darCuentaAhorros().darInteresMensual() * 100) + "%]");
        
        // Modificar esta línea para separar el saldo y el interés
        CDT cdt = cuenta.darCDT();
        panelCDT.actualizarSaldoCDT(
            formatearValor(cdt.calcularValorPresente(cuenta.darMesActual())),
            cdt.darInteresMensual()
        );

    }

    /**
     * Invierte un monto de dinero en un CDT. <br>
     * <b>post: </b> Se realiz� la inversi�n en un CDT.
     * @param pMonto Monto de dinero a invertir en el CDT.
     * @param pInteres Valor de los intereses en porcentaje.
     */
    public void invertirCDT( String pMonto, String pInteres )
    {
        if( pMonto != null )
        {
            try
            {
                int monto = Integer.parseInt( pMonto );
                double interes = Double.parseDouble( pInteres );
                if( monto > 0 && interes > 0 )
                {
                    cuenta.invertirCDT( monto, interes / 100 );
                    actualizar();
                }
                else
                {
                    JOptionPane.showMessageDialog( this, "Debe  ingresar valores positivos.", "Invertir en CDT", JOptionPane.ERROR_MESSAGE );
                }
            }
            catch( Exception e )
            {
                JOptionPane.showMessageDialog( this, "Se ingres� un monto de dinero o inter�s inv�lido.", "Invertir en CDT", JOptionPane.ERROR_MESSAGE );
            }
        }
    }

    /**
     * Cierra el CDT del cliente. <br>
     * <b>post: </b> Se cerr� el CDT del cliente.
     */
    public void cerrarCDT( )
    {
        cuenta.cerrarCDT( );
        actualizar();
    }

    /**
     * Retira un monto de dinero de la cuenta de ahorros del cliente. <br>
     * <b>post: </b> Se retir� un monto de dinero de la cuenta de ahorros del cliente.
     * @param pMonto Monto de dinero a retirar de la cuenta de ahorros del cliente.
     */
    public void retirarAhorros( String pMonto )
    {
        try
        {
            double monto = Double.parseDouble( pMonto );
            if( monto < 0 )
            {
                JOptionPane.showMessageDialog( this, "Debe ingresar un valor  positivo.", "Retirar", JOptionPane.ERROR_MESSAGE );
            }
            else if( monto > cuenta.darCuentaAhorros( ).darSaldo( ) )
            {
                JOptionPane.showMessageDialog( this, "No tiene saldo suficente.", "Retirar", JOptionPane.ERROR_MESSAGE );

            }
            else
            {
                cuenta.retirarCuentaAhorros( monto );
                actualizar( );
            }
        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( this, "Se ingres� un monto de dinero inv�lido.", "Retirar", JOptionPane.ERROR_MESSAGE );

        }
    }

    /**
     * Consigna un monto de dinero en la cuenta de ahorros del cliente. <br>
     * <b>post: </b> Se consign� el monto de dinero a la cuenta de ahorros del cliente.
     * @param pMonto Monto de dinero a consignar en la cuenta de ahorros del cliente.
     */
    public void consignarAhorros( String pMonto )
    {
        try
        {
            double monto = Double.parseDouble( pMonto );

            if( monto >= 0 )
            {
                cuenta.consignarCuentaAhorros( Double.parseDouble( pMonto ) );
                actualizar();
            }
            else
            {
                JOptionPane.showMessageDialog( this, "Debe ingresar un valor positivo.", "Consignar", JOptionPane.ERROR_MESSAGE );
            }
        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( this, "Se ingres� un monto de dinero inv�lido.", "Consignar", JOptionPane.ERROR_MESSAGE );

        }
    }

    /**
     * Retira un monto de dinero de la cuenta corriente. <br>
     * <b>post: </b> Se retir� de la cuenta corriente del cliente el monto especificado.
     * @param pMonto Monto de dinero a retirar de la cuenta corriente.
     */
    public void retirarCorriente( String pMonto )
    {
        try
        {
            double monto = Double.parseDouble( pMonto );
            if( monto < 0 )
            {
                JOptionPane.showMessageDialog( this, "Debe ingresar un valor positivo.", "Retirar", JOptionPane.ERROR_MESSAGE );
            }
            else if( monto > cuenta.darCuentaCorriente( ).darSaldo( ) )
            {
                JOptionPane.showMessageDialog( this, "No tiene saldo suficente.", "Retirar", JOptionPane.ERROR_MESSAGE );

            }
            else
            {
                cuenta.retirarCuentaCorriente( monto );
                actualizar( );
            }
        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( this, "Se ingres� un monto de dinero inv�lido.", "Retirar", JOptionPane.ERROR_MESSAGE );
        }
    }

    /**
     * Consigna un monto de dinero en la cuenta corriente. <br>
     * <b>post: </b> Se consign� el monto especificado en la cuenta corriente del cliente.
     * @param pMonto Monto de dinero a consignar de la cuenta corriente.
     */
    public void consignarCorriente( String pMonto )
    {
        try
        {
            double monto = Double.parseDouble( pMonto );
            if( monto >= 0 )
            {
                cuenta.consignarCuentaCorriente( monto );
                actualizar( );
            }
            else
            {
                JOptionPane.showMessageDialog( this, "Debe ingresar un valor positivo.", "Consignar", JOptionPane.ERROR_MESSAGE );
            }
        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( this, "Se ingres� un monto de dinero inv�lido.", "Consignar", JOptionPane.ERROR_MESSAGE );

        }
    }

    /**
     * Formatea un valor num�rico para presentar en la interfaz.
     * @param pValor Valor num�rico a ser formateado. pValor > 0.
     * @return Retorna un string con el valor formateado con puntos y signos.
     */
    private String formatearValor( double pValor )
    {
        DecimalFormat df = ( DecimalFormat )NumberFormat.getInstance( );
        df.applyPattern( "$ ###,###.##" );
        df.setMinimumFractionDigits( 2 );
        return df.format( pValor );
    }

    /**
     * Avanza un mes en la simulaci�n del banco.
     */
    public void avanzarMesSimulacion( )
    {
        cuenta.avanzarMesSimulacion( );
        actualizar( );

    }

    // -----------------------------------------------------------------
    // Puntos de Extensi�n
    // -----------------------------------------------------------------

    /**
     * M�todo para la extensi�n 1.
     */
    public void reqFuncOpcion1( )
    {
        String[] opciones = {"Cuenta de Ahorros", "Cuenta Corriente", "CDT"};
        int seleccion = JOptionPane.showOptionDialog(
            this,
            "Seleccione la cuenta para calcular el saldo promedio:",
            "Calcular Saldo Promedio",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );
        
        if (seleccion != -1) {
            String respuesta = cuenta.calcularSaldoPromedio(seleccion + 1);
            JOptionPane.showMessageDialog(this, respuesta, "Saldo Promedio", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * M�todo para la extensi�n 2 - Mostrar resumen de transacciones
     */
    public void reqFuncOpcion2( )
    {
        String resumen = cuenta.generarResumenTransacciones();
        JOptionPane.showMessageDialog(this, resumen, 
            "Resumen de Transacciones - Mes Actual", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    // -----------------------------------------------------------------
    // Main
    // -----------------------------------------------------------------

    /**
     * Ejecuta la aplicaci�n.
     * @param pArgs Par�metros de la ejecuci�n. No son necesarios.
     */
    public static void main( String[] pArgs )
    {
        try
        {
            // Unifica la interfaz para Mac y para Windows.
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );

            InterfazSimulador ventana = new InterfazSimulador( );
            ventana.setVisible( true );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }
}
