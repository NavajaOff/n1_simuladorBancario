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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * Panel con las opciones de la aplicaci�n.
 */
@SuppressWarnings("serial")
public class PanelCDT extends JPanel implements ActionListener
{

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Constante para abrir un CDT.
     */
    private final static String ABRIR_CDT = "ABRIR CDT";

    /**
     * Constante para cerrar un CDT.
     */
    private final static String CERRAR_CDT = "CERRAR CDT";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Ventana principal del simulador.
     */
    private InterfazSimulador principal;

    // -----------------------------------------------------------------
    // Atributos de Interfaz
    // -----------------------------------------------------------------

    /**
     * Etiqueta del saldo del CDT.
     */
    private JLabel etiquetaSaldoCdt;

    /**
     * Campo donde se visualiza el saldo del CDT.
     */
    private JTextField txtSaldoCdt;

    /**
     * Boton para abrir un nuevo CDT.
     */
    private JButton btnAbrirCDT;

    /**
     * Boton para cancelar un CDT.
     */
    private JButton btnCerrarCDT;

    /**
     * Etiqueta del interés del CDT.
     */
    private JLabel etiquetaInteresCdt;

    /**
     * Campo donde se visualiza el interés del CDT.
     */
    private JTextField txtInteresCdt;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Crea un nuevo panel e inicializa sus elementos. <br>
     * <b>post: </b> Se inicializ� el panel.
     * @param pPrincipal Ventana principal de la aplicaci�n. pPrincipal != null.
     */
    public PanelCDT( InterfazSimulador pPrincipal )
    {

        // Inicializa los elementos del panel
        principal = pPrincipal;

        etiquetaSaldoCdt = new JLabel( "Saldo CDT: " );
        txtSaldoCdt = new JTextField( 14 );
        txtSaldoCdt.setEditable( false );

        // Agregar campos para el interés
        etiquetaInteresCdt = new JLabel( "Interés (%): " );
        txtInteresCdt = new JTextField( 14 );
        txtInteresCdt.setEditable( false );

        btnAbrirCDT = new JButton( "Abrir" );
        btnAbrirCDT.setActionCommand( ABRIR_CDT );
        btnAbrirCDT.addActionListener( this );
        btnAbrirCDT.setPreferredSize( new Dimension( 100, 0 ) );

        btnCerrarCDT = new JButton( "Cerrar" );
        btnCerrarCDT.setActionCommand( CERRAR_CDT );
        btnCerrarCDT.addActionListener( this );
        btnCerrarCDT.setPreferredSize( new Dimension( 100, 0 ) );

        // Modificar el panel de información para mostrar los dos campos
        JPanel panelInfo = new JPanel( );
        panelInfo.setLayout( new GridLayout( 2, 2, 5, 5 ) );
        panelInfo.setBorder( new EmptyBorder( 0, 0, 5, 0 ) );
        JPanel panelBotones = new JPanel( );
        panelBotones.setLayout( new BorderLayout( ) );
        panelBotones.setBorder( new EmptyBorder( 0, 5, 5, 5 ) );
        panelBotones.setPreferredSize( new Dimension( 215, 0 ) );

        // Ubica los elementos en el panel
        panelInfo.add( etiquetaSaldoCdt );
        panelInfo.add( txtSaldoCdt );
        panelInfo.add( etiquetaInteresCdt );
        panelInfo.add( txtInteresCdt );
        panelBotones.add( btnAbrirCDT, BorderLayout.WEST );
        panelBotones.add( new JLabel( " " ), BorderLayout.CENTER );
        panelBotones.add( btnCerrarCDT, BorderLayout.EAST );

        setLayout( new BorderLayout( ) );
        add( panelInfo, BorderLayout.CENTER );
        add( panelBotones, BorderLayout.EAST );
        setBorder( new CompoundBorder( new EmptyBorder( 0, 3, 5, 3 ), new TitledBorder( "CDT" ) ) );
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Actualiza el saldo y el interés del CDT en la interfaz
     * @param pSaldo El saldo formateado como String
     * @param pInteres El interés como valor decimal (ejemplo: 0.06 para 6%)
     */
    public void actualizarSaldoCDT(String pSaldo, double pInteres) {
        // Extraer solo el valor numérico del saldo (eliminar el texto adicional)
        String saldoLimpio = pSaldo;
        if (pSaldo.contains("[")) {
            saldoLimpio = pSaldo.substring(0, pSaldo.indexOf("[")).trim();
        }
        
        txtSaldoCdt.setText(saldoLimpio);
        txtInteresCdt.setText(String.format("%.2f", pInteres * 100));
    }

    /**
     * Manejo de los eventos de los botones.
     * @param pEvento Evento de click sobre un bot�n. pEvento != null.
     */
    public void actionPerformed( ActionEvent pEvento )
    {
        String command = pEvento.getActionCommand( );
        if( command.equals( ABRIR_CDT ) )
        {
            String strValor = JOptionPane.showInputDialog( principal, "Introduzca el monto de dinero de la inversion", "Abrir CDT", JOptionPane.QUESTION_MESSAGE );
            if( strValor != null )
            {
                String strInteres = JOptionPane.showInputDialog( principal, "Introduzca el interes mensual en porcentaje", "Abrir CDT", JOptionPane.QUESTION_MESSAGE );
                if( strInteres != null )
                {
                    principal.invertirCDT( strValor, strInteres );
                }
            }
        }
        else if( command.equals( CERRAR_CDT ) )
        {
            principal.cerrarCDT( );
        }


    }

}
