
import com.toedter.calendar.JCalendar;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resources;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.Certificate;
import javax.security.cert.X509Certificate;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.RepaintManager;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Álvaro
 */
public class Principal extends javax.swing.JFrame {

    private String token = "";
    private int tipoUsuario; //se aplican valores: 1 Admin, 2 Chef, 3 Cliente
    private DefaultTableModel dtm; //Se crea el modelo de tabla para las listas
    private String ID; //ID del usuario
    private String filtoComboPoblacion;
    private HttpsURLConnection connection;    
    private String idTarifa;
    private String idDisponibilidad;

    /**
     * Creates new form Principal
     */
    public Principal() {

        /*Se carga el certificado si se hace por localhost. En Cloud no hace falta. 
        String path = "src/resources/ClientTrustStore.jks";         
        System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
        System.setProperty("javax.net.ssl.trustStore", path);
         */
        //Se inicializan los componentes Swing
        initComponents();
        jPanel_crearCuenta.setEnabled(false);
        //Se centra la aplicación en pantalla
        centrarAplicacion();
        //Se aplica el logo en la app
        setIconImage(getIconImage());
        setImageLabel(jLabel_logo_login, "src/images/Logo_Chef_Deluxe.jpg");
    }

    public String getToken() {
        return token;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public JButton getjButton1() {
        return jButton1;
    }

    public JButton getjButton_aplicarCambiosDisponibilidad() {
        return jButton_aplicarCambiosDisponibilidad;
    }

    public JButton getjButton_crearCuenta() {
        return jButton_crearCuenta;
    }

    public JButton getjButton_editarCuenta() {
        return jButton_editarCuenta;
    }

    public JButton getjButton_eliminarBorrarUsuario() {
        return jButton_eliminarBorrarUsuario;
    }

    public JButton getjButton_eliminarEliminarCuenta() {
        return jButton_eliminarEliminarCuenta;
    }

    public JButton getjButton_exit() {
        return jButton_exit;
    }

    public JButton getjButton_exit1() {
        return jButton_exit1;
    }

    public JButton getjButton_login() {
        return jButton_login;
    }

    public JButton getjButton_nuevaCuenta() {
        return jButton_nuevaCuenta;
    }

    public JButton getjButton_usuario() {
        return jButton_usuario;
    }

    public JButton getjButton_volverBorrarUsuario() {
        return jButton_volverBorrarUsuario;
    }

    public JButton getjButton_volverEliminarCuenta() {
        return jButton_volverEliminarCuenta;
    }

    public JButton getjButton_volverListadoChefsDisponibles() {
        return jButton_volverListadoChefsDisponibles;
    }

    public JButton getjButton_volverListarUsuarios() {
        return jButton_volverListarUsuarios;
    }

    public JCheckBox getjCheckBox_disponible() {
        return jCheckBox_disponible;
    }

    public JComboBox<String> getjComboBox_editarTipoUsuario() {
        return jComboBox_editarTipoUsuario;
    }

    public JComboBox<String> getjComboBox_filtroAZ() {
        return jComboBox_filtroAZ;
    }

    public JComboBox<String> getjComboBox_filtroAZ1() {
        return jComboBox_filtroAZ1;
    }

    public JComboBox<String> getjComboBox_poblacion() {
        return jComboBox_poblacion;
    }

    public JComboBox<String> getjComboBox_poblacion1() {
        return jComboBox_poblacion1;
    }

    public JComboBox<String> getjComboBox_tipoUsuario() {
        return jComboBox_tipoUsuario;
    }

    public JPasswordField getjPasswordField_password() {
        return jPasswordField_password;
    }

    public JRadioButton getjRadioButton_filtradoListaChefs() {
        return jRadioButton_filtradoListaChefs;
    }

    public JTextField getjTextField_IBAN() {
        return jTextField_IBAN;
    }

    public JTextField getjTextField_apellidos() {
        return jTextField_apellidos;
    }

    public JTextField getjTextField_codigoPostal() {
        return jTextField_codigoPostal;
    }

    public JTextField getjTextField_contraseña() {
        return jTextField_contraseña;
    }

    public JTextField getjTextField_direccion() {
        return jTextField_direccion;
    }

    public JTextField getjTextField_edad() {
        return jTextField_edad;
    }

    public JTextField getjTextField_editarApellidos() {
        return jTextField_editarApellidos;
    }

    public JTextField getjTextField_editarCodigoPostal() {
        return jTextField_editarCodigoPostal;
    }

    public JTextField getjTextField_editarDireccion() {
        return jTextField_editarDireccion;
    }

    public JTextField getjTextField_editarEdad() {
        return jTextField_editarEdad;
    }

    public JTextField getjTextField_editarEmail() {
        return jTextField_editarEmail;
    }

    public JTextField getjTextField_editarIBAN() {
        return jTextField_editarIBAN;
    }

    public JTextField getjTextField_editarNacionalidad() {
        return jTextField_editarNacionalidad;
    }

    public JTextField getjTextField_editarNombre() {
        return jTextField_editarNombre;
    }

    public JTextField getjTextField_editarPoblacion() {
        return jTextField_editarPoblacion;
    }

    public JTextField getjTextField_editarTelefono() {
        return jTextField_editarTelefono;
    }

    public JTextField getjTextField_editarUsername() {
        return jTextField_editarUsername;
    }

    public JTextField getjTextField_email() {
        return jTextField_email;
    }

    public JTextField getjTextField_nacionalidad() {
        return jTextField_nacionalidad;
    }

    public JTextField getjTextField_nombre() {
        return jTextField_nombre;
    }

    public JTextField getjTextField_poblacion() {
        return jTextField_poblacion;
    }

    public JTextField getjTextField_telefono() {
        return jTextField_telefono;
    }

    public JTextField getjTextField_username() {
        return jTextField_username;
    }

    public JTextField getjTextField_usuario() {
        return jTextField_usuario;
    }

    public JTextField getjTextField_usuarioBorrar() {
        return jTextField_usuarioBorrar;
    }

    public JToggleButton getjToggleButton_bajaChef() {
        return jToggleButton_bajaChef;
    }

    public JToggleButton getjToggleButton_bajaCliente() {
        return jToggleButton_bajaCliente;
    }

    public JToggleButton getjToggleButton_borrarUsuarioAdmin() {
        return jToggleButton_borrarUsuarioAdmin;
    }

    public JToggleButton getjToggleButton_disponibilidadChef() {
        return jToggleButton_disponibilidadChef;
    }

    public JToggleButton getjToggleButton_editarDatosAdmin() {
        return jToggleButton_editarDatosAdmin;
    }

    public JToggleButton getjToggleButton_editarDatosChef() {
        return jToggleButton_editarDatosChef;
    }

    public JToggleButton getjToggleButton_editarDatosCliente() {
        return jToggleButton_editarDatosCliente;
    }

    public JToggleButton getjToggleButton_salirOpcionesAdmin() {
        return jToggleButton_salirOpcionesAdmin;
    }

    public JToggleButton getjToggleButton_salirOpcionesChef() {
        return jToggleButton_salirOpcionesChef;
    }

    public JToggleButton getjToggleButton_salirOpcionesCliente() {
        return jToggleButton_salirOpcionesCliente;
    }

    public JToggleButton getjToggleButton_verDatosAdmin() {
        return jToggleButton_verDatosAdmin;
    }

    public JToggleButton getjToggleButton_verDatosChef() {
        return jToggleButton_verDatosChef;
    }

    public JToggleButton getjToggleButton_verDatosCliente() {
        return jToggleButton_verDatosCliente;
    }

    public JToggleButton getjToggleButton_verListaAdmin() {
        return jToggleButton_verListaAdmin;
    }

    public JToggleButton getjToggleButton_verListaCliente() {
        return jToggleButton_verListaCliente;
    }

    public String getFiltoComboPoblacion() {
        return filtoComboPoblacion;
    }

    public JLabel getjLabel_telefonoUsuario() {
        return jLabel_usernameUsuario;
    }

    public String getID() {
        return ID;
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public JButton getjButton_aceptarReservaChef() {
        return jButton_aceptarReservaChef;
    }

    public JButton getjButton_confirmarPago() {
        return jButton_confirmarPago;
    }

    public JButton getjButton_eliminarReserva() {
        return jButton_eliminarReserva;
    }

    public JButton getjButton_rechazarReservaChef() {
        return jButton_rechazarReservaChef;
    }

    public JButton getjButton_solicitarReserva() {
        return jButton_solicitarReserva;
    }

    public JButton getjButton_voverReserva() {
        return jButton_voverReserva;
    }

    public JCalendar getjCalendar_fechaCalendarioReserva() {
        return jCalendar_fechaCalendarioReserva;
    }

    public JComboBox<String> getjComboBox_chefReserva() {
        return jComboBox_chefReserva;
    }

    public JComboBox<String> getjComboBox_filtroAZReserva() {
        return jComboBox_filtroAZReserva;
    }

    public JComboBox<String> getjComboBox_horaFinReserva() {
        return jComboBox_horaFinReserva;
    }

    public JComboBox<String> getjComboBox_horaInicioReserva() {
        return jComboBox_horaInicioReserva;
    }

    public JComboBox<String> getjComboBox_minutosFinReserva() {
        return jComboBox_minutosFinReserva;
    }

    public JComboBox<String> getjComboBox_minutosInicioReserva() {
        return jComboBox_minutosInicioReserva;
    }

    public JComboBox<String> getjComboBox_poblacionReserva() {
        return jComboBox_poblacionReserva;
    }

    public JTextField getjTextField_IDReservaChef() {
        return jTextField_IDReservaChef;
    }

    public JTextField getjTextField_IDReservaEliminar() {
        return jTextField_IDReservaEliminar;
    }

    public JTextField getjTextField_chefReservaEliminar() {
        return jTextField_chefReservaEliminar;
    }

    public JTextField getjTextField_clienteReservaChef() {
        return jTextField_clienteReservaChef;
    }

    public JTextField getjTextField_comensalesReserva() {
        return jTextField_comensalesReserva;
    }

    public JTextField getjTextField_comensalesReservaChef() {
        return jTextField_comensalesReservaChef;
    }

    public JTextField getjTextField_comensalesReservaEliminar() {
        return jTextField_comensalesReservaEliminar;
    }

    public JTextField getjTextField_diaReservaEliminar() {
        return jTextField_diaReservaEliminar;
    }

    public JTextField getjTextField_estadoReservaChef() {
        return jTextField_estadoReservaChef;
    }

    public JTextField getjTextField_estadoReservaEliminar() {
        return jTextField_estadoReservaEliminar;
    }

    public JTextField getjTextField_fechaReservaChef() {
        return jTextField_fechaReservaChef;
    }

    public JTextField getjTextField_finReservaChef() {
        return jTextField_finReservaChef;
    }

    public JTextField getjTextField_finReservaEliminar() {
        return jTextField_precioReservaEliminar;
    }

    public JTextField getjTextField_inicioReservaChef() {
        return jTextField_inicioReservaChef;
    }

    public JTextField getjTextField_inicioReservaEliminar() {
        return jTextField_inicioReservaEliminar;
    }

    public JTextField getjTextField_precioReserva() {
        return jTextField_precioReserva;
    }

    public JTextField getjTextField_precioReservaChef() {
        return jTextField_precioReservaChef;
    }

    public JTextField getjTextField_precioReservaEliminar() {
        return jTextField_finReservaEliminar;
    }

    public JToggleButton getjToggleButton_altaNuevaReserva() {
        return jToggleButton_altaNuevaReserva;
    }

    public JToggleButton getjToggleButton_eliminarReserva() {
        return jToggleButton_eliminarReserva;
    }

    public JToggleButton getjToggleButton_indicarTarifa() {
        return jToggleButton_indicarTarifa;
    }

    public JToggleButton getjToggleButton_revisarReservasChef() {
        return jToggleButton_revisarReservasChef;
    }

    public JTextField getjTextField_nuevaContraseña() {
        return jTextField_nuevaContraseña;
    }

    public JButton getjButton_cambiarContraseñaUsuario() {
        return jButton_cambiarContraseñaUsuario;
    }

    public JButton getjButton_modificarContraseña() {
        return jButton_modificarContraseña;
    }

    public JButton getjButton_modificarTarifa() {
        return jButton_modificarTarifa;
    }

    public String getIdTarifa() {
        return idTarifa;
    }

    public JButton getjButton_calcularPrecio() {
        return jButton_calcularPrecio;
    }

    public JButton getjButton_pagoReserva() {
        return jButton_pagoReserva;
    }

    public JTextField getjTextField_indicarTarifa() {
        return jTextField_indicarTarifa;
    }

    public String getIdDisponibilidad() {
        return idDisponibilidad;
    }

    public JTable getjTable_listaReservasCliente() {
        return jTable_listaReservasCliente;
    }

    //Cambiar icono de aplicación
    @Override
    public Image getIconImage() {
        Image icono = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("images/Logo_Chef_Deluxe.jpg"));
        return icono;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_background = new javax.swing.JPanel();
        jLayeredPane_contenedor = new javax.swing.JLayeredPane();
        jPanel_login = new javax.swing.JPanel();
        jButton_login = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextField_usuario = new javax.swing.JTextField();
        jLabel_mensajeLogin = new javax.swing.JLabel();
        jButton_nuevaCuenta = new javax.swing.JButton();
        jPasswordField_password = new javax.swing.JPasswordField();
        jLabel_logo_login = new javax.swing.JLabel();
        jPanel_Usuario = new javax.swing.JPanel();
        jButton_usuario = new javax.swing.JButton();
        jLabel_nombre1 = new javax.swing.JLabel();
        jLabel_apellidos1 = new javax.swing.JLabel();
        jLabel_direccion1 = new javax.swing.JLabel();
        jLabel_codigoPostal1 = new javax.swing.JLabel();
        jLabel_poblacion1 = new javax.swing.JLabel();
        jLabel_nacionalidad1 = new javax.swing.JLabel();
        jLabel_edad1 = new javax.swing.JLabel();
        jLabel_tipoUsuario1 = new javax.swing.JLabel();
        jLabel_nombreUsuario = new javax.swing.JLabel();
        jLabel_apellidosUsuario = new javax.swing.JLabel();
        jLabel_direccionUsuario = new javax.swing.JLabel();
        jLabel_codigoPostalUsuario = new javax.swing.JLabel();
        jLabel_poblacionUsuario = new javax.swing.JLabel();
        jLabel_nacionalidadUsuario = new javax.swing.JLabel();
        jLabel_edadUsuario = new javax.swing.JLabel();
        jLabel_tipoUsuarioUsuario = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel_telefonoUsuario = new javax.swing.JLabel();
        jLabel_emailUsuario = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel_usernameUsuario = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel_IBANUsuario = new javax.swing.JLabel();
        jPanel_crearCuenta = new javax.swing.JPanel();
        jButton_crearCuenta = new javax.swing.JButton();
        jTextField_nombre = new javax.swing.JTextField();
        jTextField_apellidos = new javax.swing.JTextField();
        jTextField_direccion = new javax.swing.JTextField();
        jTextField_codigoPostal = new javax.swing.JTextField();
        jTextField_poblacion = new javax.swing.JTextField();
        jTextField_nacionalidad = new javax.swing.JTextField();
        jTextField_edad = new javax.swing.JTextField();
        jComboBox_tipoUsuario = new javax.swing.JComboBox<>();
        jLabel_nombre = new javax.swing.JLabel();
        jLabel_apellidos = new javax.swing.JLabel();
        jLabel_direccion = new javax.swing.JLabel();
        jLabel_codigoPostal = new javax.swing.JLabel();
        jLabel_poblacion = new javax.swing.JLabel();
        jLabel_nacionalidad = new javax.swing.JLabel();
        jLabel_edad = new javax.swing.JLabel();
        jLabel_tipoUsuario = new javax.swing.JLabel();
        jLabel_telefono = new javax.swing.JLabel();
        jTextField_telefono = new javax.swing.JTextField();
        jLabel_email = new javax.swing.JLabel();
        jTextField_email = new javax.swing.JTextField();
        jButton_exit = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTextField_username = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField_contraseña = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField_IBAN = new javax.swing.JTextField();
        jPanel_listarUsuarios = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_usuarios = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jButton_volverListarUsuarios = new javax.swing.JButton();
        jPanel_editarCuenta = new javax.swing.JPanel();
        jButton_editarCuenta = new javax.swing.JButton();
        jTextField_editarNombre = new javax.swing.JTextField();
        jTextField_editarApellidos = new javax.swing.JTextField();
        jTextField_editarDireccion = new javax.swing.JTextField();
        jTextField_editarCodigoPostal = new javax.swing.JTextField();
        jTextField_editarPoblacion = new javax.swing.JTextField();
        jTextField_editarNacionalidad = new javax.swing.JTextField();
        jTextField_editarEdad = new javax.swing.JTextField();
        jComboBox_editarTipoUsuario = new javax.swing.JComboBox<>();
        jLabel_nombre2 = new javax.swing.JLabel();
        jLabel_apellidos2 = new javax.swing.JLabel();
        jLabel_direccion2 = new javax.swing.JLabel();
        jLabel_codigoPostal2 = new javax.swing.JLabel();
        jLabel_poblacion2 = new javax.swing.JLabel();
        jLabel_nacionalidad2 = new javax.swing.JLabel();
        jLabel_edad2 = new javax.swing.JLabel();
        jLabel_tipoUsuario2 = new javax.swing.JLabel();
        jLabel_telefono1 = new javax.swing.JLabel();
        jTextField_editarTelefono = new javax.swing.JTextField();
        jLabel_email1 = new javax.swing.JLabel();
        jTextField_editarEmail = new javax.swing.JTextField();
        jButton_exit1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextField_editarUsername = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField_editarIBAN = new javax.swing.JTextField();
        jButton_cambiarContraseñaUsuario = new javax.swing.JButton();
        jPanel_opcionesAdmin = new javax.swing.JPanel();
        jToggleButton_salirOpcionesAdmin = new javax.swing.JToggleButton();
        jToggleButton_verDatosAdmin = new javax.swing.JToggleButton();
        jToggleButton_verListaAdmin = new javax.swing.JToggleButton();
        jToggleButton_borrarUsuarioAdmin = new javax.swing.JToggleButton();
        jToggleButton_editarDatosAdmin = new javax.swing.JToggleButton();
        jPanel_opcionesCliente = new javax.swing.JPanel();
        jToggleButton_salirOpcionesCliente = new javax.swing.JToggleButton();
        jToggleButton_verDatosCliente = new javax.swing.JToggleButton();
        jToggleButton_verListaCliente = new javax.swing.JToggleButton();
        jToggleButton_bajaCliente = new javax.swing.JToggleButton();
        jToggleButton_editarDatosCliente = new javax.swing.JToggleButton();
        jToggleButton_altaNuevaReserva = new javax.swing.JToggleButton();
        jToggleButton_eliminarReserva = new javax.swing.JToggleButton();
        jPanel_opcionesChef = new javax.swing.JPanel();
        jToggleButton_salirOpcionesChef = new javax.swing.JToggleButton();
        jToggleButton_verDatosChef = new javax.swing.JToggleButton();
        jToggleButton_disponibilidadChef = new javax.swing.JToggleButton();
        jToggleButton_bajaChef = new javax.swing.JToggleButton();
        jToggleButton_editarDatosChef = new javax.swing.JToggleButton();
        jToggleButton_revisarReservasChef = new javax.swing.JToggleButton();
        jToggleButton_indicarTarifa = new javax.swing.JToggleButton();
        jPanel_borrarUsuario = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jTextField_usuarioBorrar = new javax.swing.JTextField();
        jButton_eliminarBorrarUsuario = new javax.swing.JButton();
        jButton_volverBorrarUsuario = new javax.swing.JButton();
        jPanel_disponibilidad = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jCheckBox_disponible = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jComboBox_poblacion = new javax.swing.JComboBox<>();
        jComboBox_filtroAZ = new javax.swing.JComboBox<>();
        jButton_aplicarCambiosDisponibilidad = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel_listaChefsDisponibles = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jButton_volverListadoChefsDisponibles = new javax.swing.JButton();
        jComboBox_filtroAZ1 = new javax.swing.JComboBox<>();
        jComboBox_poblacion1 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_listaChefsEstado = new javax.swing.JTable();
        jRadioButton_filtradoListaChefs = new javax.swing.JRadioButton();
        jPanel_eliminarPropiaCuenta = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jButton_eliminarEliminarCuenta = new javax.swing.JButton();
        jButton_volverEliminarCuenta = new javax.swing.JButton();
        jPanel_altaReserva = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jCalendar_fechaCalendarioReserva = new com.toedter.calendar.JCalendar();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jComboBox_filtroAZReserva = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        jComboBox_poblacionReserva = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jComboBox_chefReserva = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        jButton_solicitarReserva = new javax.swing.JButton();
        jButton_voverReserva = new javax.swing.JButton();
        jComboBox_horaInicioReserva = new javax.swing.JComboBox<>();
        jComboBox_horaFinReserva = new javax.swing.JComboBox<>();
        jComboBox_minutosInicioReserva = new javax.swing.JComboBox<>();
        jComboBox_minutosFinReserva = new javax.swing.JComboBox<>();
        jTextField_precioReserva = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jTextField_comensalesReserva = new javax.swing.JTextField();
        jButton_calcularPrecio = new javax.swing.JButton();
        jPanel_editarReserva = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_listaReservasCliente = new javax.swing.JTable();
        jLabel37 = new javax.swing.JLabel();
        jTextField_estadoReservaEliminar = new javax.swing.JTextField();
        jTextField_chefReservaEliminar = new javax.swing.JTextField();
        jTextField_inicioReservaEliminar = new javax.swing.JTextField();
        jTextField_precioReservaEliminar = new javax.swing.JTextField();
        jTextField_finReservaEliminar = new javax.swing.JTextField();
        jButton_eliminarReserva = new javax.swing.JButton();
        jButton_volverEliminarReserva = new javax.swing.JButton();
        jTextField_diaReservaEliminar = new javax.swing.JTextField();
        jTextField_IDReservaEliminar = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jTextField_comensalesReservaEliminar = new javax.swing.JTextField();
        jButton_pagoReserva = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea_infoPagoReserva = new javax.swing.JTextArea();
        jPanel_aceptarReservaChef = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_listaReservasChef = new javax.swing.JTable();
        jButton_aceptarReservaChef = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel46 = new javax.swing.JLabel();
        jTextField_IDReservaChef = new javax.swing.JTextField();
        jTextField_estadoReservaChef = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        jTextField_clienteReservaChef = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jTextField_fechaReservaChef = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jTextField_inicioReservaChef = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jTextField_finReservaChef = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        jTextField_comensalesReservaChef = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        jTextField_precioReservaChef = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jButton_confirmarPago = new javax.swing.JButton();
        jButton_rechazarReservaChef = new javax.swing.JButton();
        jPanel_modificarTarifa = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jTextField_indicarTarifa = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        jButton_modificarTarifa = new javax.swing.JButton();
        jButton_volverTarifa = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel_cambiarContraseña = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        jTextField_nuevaContraseña = new javax.swing.JTextField();
        jButton_modificarContraseña = new javax.swing.JButton();
        jButton_volverModificarContraseña = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chef Deluxe");

        jPanel_background.setPreferredSize(new java.awt.Dimension(930, 597));

        jLayeredPane_contenedor.setPreferredSize(new java.awt.Dimension(930, 597));

        jPanel_login.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel_login.setPreferredSize(new java.awt.Dimension(930, 597));

        jButton_login.setText("Entrar");
        jButton_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_loginActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Inserte su usuario y contraseña:");

        jTextField_usuario.setText("Usuario");
        jTextField_usuario.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextField_usuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField_usuarioMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextField_usuarioMouseExited(evt);
            }
        });

        jLabel_mensajeLogin.setForeground(new java.awt.Color(255, 51, 51));
        jLabel_mensajeLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jButton_nuevaCuenta.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jButton_nuevaCuenta.setText("¿No tienes cuenta?, crea una nueva aquí");
        jButton_nuevaCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_nuevaCuentaActionPerformed(evt);
            }
        });

        jPasswordField_password.setText("Contraseña");
        jPasswordField_password.setMinimumSize(new java.awt.Dimension(4, 20));
        jPasswordField_password.setSelectionStart(0);
        jPasswordField_password.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPasswordField_passwordMouseClicked(evt);
            }
        });
        jPasswordField_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField_passwordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_loginLayout = new javax.swing.GroupLayout(jPanel_login);
        jPanel_login.setLayout(jPanel_loginLayout);
        jPanel_loginLayout.setHorizontalGroup(
            jPanel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_loginLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPasswordField_password, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_usuario)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                    .addComponent(jLabel_mensajeLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(300, 300, 300))
            .addGroup(jPanel_loginLayout.createSequentialGroup()
                .addGroup(jPanel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_loginLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel_logo_login, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(151, 151, 151)
                        .addComponent(jButton_login, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_loginLayout.createSequentialGroup()
                        .addGap(327, 327, 327)
                        .addComponent(jButton_nuevaCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(323, Short.MAX_VALUE))
        );
        jPanel_loginLayout.setVerticalGroup(
            jPanel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_loginLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel_loginLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jTextField_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jPasswordField_password, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel_mensajeLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(jButton_login, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel_logo_login, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(60, 60, 60)
                .addComponent(jButton_nuevaCuenta)
                .addContainerGap(141, Short.MAX_VALUE))
        );

        jPanel_Usuario.setPreferredSize(new java.awt.Dimension(930, 597));

        jButton_usuario.setText("Volver");
        jButton_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_usuarioActionPerformed(evt);
            }
        });

        jLabel_nombre1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_nombre1.setText("Nombre:");

        jLabel_apellidos1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_apellidos1.setText("Apellidos:");

        jLabel_direccion1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_direccion1.setText("Dirección:");

        jLabel_codigoPostal1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_codigoPostal1.setText("Código Postal:");

        jLabel_poblacion1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_poblacion1.setText("Población:");

        jLabel_nacionalidad1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_nacionalidad1.setText("Nacionalidad:");

        jLabel_edad1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_edad1.setText("Edad:");

        jLabel_tipoUsuario1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_tipoUsuario1.setText("Tipo de usuario:");

        jLabel_nombreUsuario.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel_nombreUsuario.setForeground(new java.awt.Color(255, 51, 51));

        jLabel_apellidosUsuario.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel_apellidosUsuario.setForeground(new java.awt.Color(255, 51, 51));

        jLabel_direccionUsuario.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel_direccionUsuario.setForeground(new java.awt.Color(255, 51, 51));

        jLabel_codigoPostalUsuario.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel_codigoPostalUsuario.setForeground(new java.awt.Color(255, 51, 51));

        jLabel_poblacionUsuario.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel_poblacionUsuario.setForeground(new java.awt.Color(255, 51, 51));

        jLabel_nacionalidadUsuario.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel_nacionalidadUsuario.setForeground(new java.awt.Color(255, 51, 51));

        jLabel_edadUsuario.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel_edadUsuario.setForeground(new java.awt.Color(255, 51, 51));

        jLabel_tipoUsuarioUsuario.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel_tipoUsuarioUsuario.setForeground(new java.awt.Color(255, 51, 51));

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel1.setText("Teléfono:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel2.setText("Email:");

        jLabel_telefonoUsuario.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel_telefonoUsuario.setForeground(new java.awt.Color(255, 51, 51));

        jLabel_emailUsuario.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel_emailUsuario.setForeground(new java.awt.Color(255, 51, 51));

        jLabel3.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel3.setText("Nombre de usuario:");

        jLabel_usernameUsuario.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel_usernameUsuario.setForeground(new java.awt.Color(255, 51, 51));

        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel8.setText("IBAN:");

        jLabel_IBANUsuario.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel_IBANUsuario.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel_UsuarioLayout = new javax.swing.GroupLayout(jPanel_Usuario);
        jPanel_Usuario.setLayout(jPanel_UsuarioLayout);
        jPanel_UsuarioLayout.setHorizontalGroup(
            jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_UsuarioLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                        .addComponent(jLabel_telefonoUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(34, 949, Short.MAX_VALUE))
                    .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                        .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel_nombre1)
                            .addComponent(jLabel_direccion1)
                            .addComponent(jLabel_codigoPostal1)
                            .addComponent(jLabel_codigoPostalUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                                    .addComponent(jLabel_usernameUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(49, 49, 49)
                                    .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel_IBANUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                                        .addComponent(jLabel_emailUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                                    .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel_nacionalidadUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel_nacionalidad1))
                                    .addGap(86, 86, 86)
                                    .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                                            .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel_edadUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel_edad1)
                                                .addComponent(jLabel_poblacion1))
                                            .addGap(135, 135, 135)
                                            .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel_tipoUsuario1)
                                                .addComponent(jLabel_tipoUsuarioUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(jLabel_poblacionUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                                    .addComponent(jLabel_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(52, 52, 52)
                                    .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel_apellidos1)
                                        .addComponent(jLabel_apellidosUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jLabel_direccionUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                .addGap(428, 428, 428)
                .addComponent(jButton_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel_UsuarioLayout.setVerticalGroup(
            jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_nombre1)
                    .addComponent(jLabel_apellidos1))
                .addGap(18, 18, 18)
                .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_apellidosUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel_direccion1)
                .addGap(18, 18, 18)
                .addComponent(jLabel_direccionUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_poblacion1)
                    .addComponent(jLabel_codigoPostal1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                        .addComponent(jLabel_codigoPostalUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_nacionalidad1)
                            .addComponent(jLabel_edad1)
                            .addComponent(jLabel_tipoUsuario1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                        .addComponent(jLabel_poblacionUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(32, 32, 32)))
                .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                        .addComponent(jLabel_nacionalidadUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)))
                    .addComponent(jLabel_edadUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_tipoUsuarioUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel_emailUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jLabel_telefonoUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel_IBANUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                    .addComponent(jLabel_usernameUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(33, 33, 33)
                .addComponent(jButton_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );

        jPanel_crearCuenta.setEnabled(false);
        jPanel_crearCuenta.setPreferredSize(new java.awt.Dimension(930, 597));

        jButton_crearCuenta.setText("Crear cuenta");
        jButton_crearCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_crearCuentaActionPerformed(evt);
            }
        });

        jTextField_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_nombreActionPerformed(evt);
            }
        });

        jComboBox_tipoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Cliente", "Chef", "Administrador" }));
        jComboBox_tipoUsuario.setToolTipText("");
        jComboBox_tipoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_tipoUsuarioActionPerformed(evt);
            }
        });

        jLabel_nombre.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_nombre.setText("Nombre:");

        jLabel_apellidos.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_apellidos.setText("Apellidos:");

        jLabel_direccion.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_direccion.setText("Dirección:");

        jLabel_codigoPostal.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_codigoPostal.setText("Código Postal:");

        jLabel_poblacion.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_poblacion.setText("Población:");

        jLabel_nacionalidad.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_nacionalidad.setText("Nacionalidad:");

        jLabel_edad.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_edad.setText("Edad:");

        jLabel_tipoUsuario.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_tipoUsuario.setText("Tipo de usuario:");

        jLabel_telefono.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_telefono.setText("Teléfono:");

        jLabel_email.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_email.setText("Email:");

        jButton_exit.setText("Salir");
        jButton_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_exitActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel5.setText("Nombre de usuario:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel6.setText("Contraseña:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel7.setText("IBAN:");

        javax.swing.GroupLayout jPanel_crearCuentaLayout = new javax.swing.GroupLayout(jPanel_crearCuenta);
        jPanel_crearCuenta.setLayout(jPanel_crearCuentaLayout);
        jPanel_crearCuentaLayout.setHorizontalGroup(
            jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_crearCuentaLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel_crearCuentaLayout.createSequentialGroup()
                        .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_nacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_codigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_crearCuentaLayout.createSequentialGroup()
                                .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel_crearCuentaLayout.createSequentialGroup()
                                        .addGap(49, 49, 49)
                                        .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField_edad, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel_edad))
                                        .addGap(120, 120, 120)
                                        .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel_tipoUsuario)
                                            .addComponent(jComboBox_tipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_crearCuentaLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton_crearCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(176, 176, 176)))
                                .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField_telefono, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                    .addComponent(jLabel_telefono)
                                    .addComponent(jLabel5)
                                    .addComponent(jTextField_username, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton_exit, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_crearCuentaLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_poblacion)
                                    .addComponent(jTextField_poblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jLabel_nacionalidad)
                    .addComponent(jLabel_codigoPostal)
                    .addComponent(jLabel_direccion)
                    .addComponent(jTextField_email, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_email)
                    .addGroup(jPanel_crearCuentaLayout.createSequentialGroup()
                        .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_nombre)
                            .addComponent(jTextField_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(73, 73, 73)
                        .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_apellidos)
                            .addComponent(jTextField_apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jTextField_direccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 831, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_crearCuentaLayout.createSequentialGroup()
                        .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel_crearCuentaLayout.createSequentialGroup()
                                .addComponent(jTextField_contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(180, 180, 180))
                            .addGroup(jPanel_crearCuentaLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(306, 306, 306)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_IBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 55, Short.MAX_VALUE))
        );
        jPanel_crearCuentaLayout.setVerticalGroup(
            jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_crearCuentaLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_nombre)
                    .addComponent(jLabel_apellidos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addComponent(jLabel_direccion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_codigoPostal)
                    .addComponent(jLabel_poblacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_codigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_poblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_nacionalidad)
                    .addComponent(jLabel_edad)
                    .addComponent(jLabel_tipoUsuario)
                    .addComponent(jLabel_telefono))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_nacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_edad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_tipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_email)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_email, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_username, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addGap(18, 41, Short.MAX_VALUE)
                .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_contraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_IBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel_crearCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_crearCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        jPanel_listarUsuarios.setPreferredSize(new java.awt.Dimension(930, 597));

        jTable_usuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Apellidos", "Edad", "Dirección", "C. Postal", "Población", "Nacionalidad", "Usuario", "Tipo Usuario", "Email", "Teléfono", "IBAN"
            }
        ));
        jScrollPane1.setViewportView(jTable_usuarios);
        if (jTable_usuarios.getColumnModel().getColumnCount() > 0) {
            jTable_usuarios.getColumnModel().getColumn(2).setPreferredWidth(40);
        }

        jLabel12.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel12.setText("Lista de usuarios:");

        jButton_volverListarUsuarios.setText("Volver");
        jButton_volverListarUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_volverListarUsuariosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_listarUsuariosLayout = new javax.swing.GroupLayout(jPanel_listarUsuarios);
        jPanel_listarUsuarios.setLayout(jPanel_listarUsuariosLayout);
        jPanel_listarUsuariosLayout.setHorizontalGroup(
            jPanel_listarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_listarUsuariosLayout.createSequentialGroup()
                .addContainerGap(383, Short.MAX_VALUE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(301, 301, 301)
                .addComponent(jButton_volverListarUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(jPanel_listarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 930, Short.MAX_VALUE))
        );
        jPanel_listarUsuariosLayout.setVerticalGroup(
            jPanel_listarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_listarUsuariosLayout.createSequentialGroup()
                .addGroup(jPanel_listarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_listarUsuariosLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jButton_volverListarUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_listarUsuariosLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(512, Short.MAX_VALUE))
            .addGroup(jPanel_listarUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_listarUsuariosLayout.createSequentialGroup()
                    .addContainerGap(142, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        jPanel_editarCuenta.setEnabled(false);
        jPanel_editarCuenta.setPreferredSize(new java.awt.Dimension(930, 597));

        jButton_editarCuenta.setText("Modificar");
        jButton_editarCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_editarCuentaActionPerformed(evt);
            }
        });

        jTextField_editarNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_editarNombreActionPerformed(evt);
            }
        });

        jTextField_editarApellidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_editarApellidosActionPerformed(evt);
            }
        });

        jTextField_editarCodigoPostal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_editarCodigoPostalActionPerformed(evt);
            }
        });

        jTextField_editarEdad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_editarEdadActionPerformed(evt);
            }
        });

        jComboBox_editarTipoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Cliente", "Chef", "Administrador" }));
        jComboBox_editarTipoUsuario.setToolTipText("");
        jComboBox_editarTipoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_editarTipoUsuarioActionPerformed(evt);
            }
        });

        jLabel_nombre2.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_nombre2.setText("Nombre:");

        jLabel_apellidos2.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_apellidos2.setText("Apellidos:");

        jLabel_direccion2.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_direccion2.setText("Dirección:");

        jLabel_codigoPostal2.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_codigoPostal2.setText("Código Postal:");

        jLabel_poblacion2.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_poblacion2.setText("Población:");

        jLabel_nacionalidad2.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_nacionalidad2.setText("Nacionalidad:");

        jLabel_edad2.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_edad2.setText("Edad:");

        jLabel_tipoUsuario2.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_tipoUsuario2.setText("Tipo de usuario:");

        jLabel_telefono1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_telefono1.setText("Teléfono:");

        jLabel_email1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel_email1.setText("Email:");

        jButton_exit1.setText("Volver");
        jButton_exit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_exit1ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel9.setText("Nombre de usuario:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel11.setText("IBAN:");

        jButton_cambiarContraseñaUsuario.setText("Cambiar Contraseña");
        jButton_cambiarContraseñaUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_cambiarContraseñaUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_editarCuentaLayout = new javax.swing.GroupLayout(jPanel_editarCuenta);
        jPanel_editarCuenta.setLayout(jPanel_editarCuentaLayout);
        jPanel_editarCuentaLayout.setHorizontalGroup(
            jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_editarCuentaLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_editarCuentaLayout.createSequentialGroup()
                        .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel_editarCuentaLayout.createSequentialGroup()
                                .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_nombre2)
                                    .addComponent(jTextField_editarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(39, 39, 39)
                                .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel_apellidos2)
                                    .addComponent(jTextField_editarApellidos)))
                            .addComponent(jTextField_editarDireccion)
                            .addGroup(jPanel_editarCuentaLayout.createSequentialGroup()
                                .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField_editarNacionalidad, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                                        .addComponent(jTextField_editarCodigoPostal)
                                        .addComponent(jLabel_codigoPostal2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel_nacionalidad2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel_editarCuentaLayout.createSequentialGroup()
                                        .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField_editarEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel_edad2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(71, 71, 71)
                                        .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jComboBox_editarTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel_tipoUsuario2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jTextField_editarPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel_poblacion2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel_direccion2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(104, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_editarCuentaLayout.createSequentialGroup()
                        .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton_editarCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_editarIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel_editarCuentaLayout.createSequentialGroup()
                        .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_editarEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_email1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_editarCuentaLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jButton_cambiarContraseñaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton_exit1)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel_editarCuentaLayout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextField_editarUsername, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                        .addComponent(jTextField_editarTelefono, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel_telefono1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        jPanel_editarCuentaLayout.setVerticalGroup(
            jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_editarCuentaLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_nombre2)
                    .addComponent(jLabel_apellidos2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_editarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_editarApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel_direccion2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField_editarDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 36, Short.MAX_VALUE)
                .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_codigoPostal2)
                    .addComponent(jLabel_poblacion2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_editarPoblacion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_editarCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_edad2)
                    .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel_tipoUsuario2)
                        .addComponent(jLabel_telefono1)
                        .addComponent(jLabel_nacionalidad2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_editarNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_editarEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_editarTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_editarTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_email1)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField_editarUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jTextField_editarEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_editarIBAN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addGroup(jPanel_editarCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_editarCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_exit1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_cambiarContraseñaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        jPanel_opcionesAdmin.setPreferredSize(new java.awt.Dimension(930, 597));

        jToggleButton_salirOpcionesAdmin.setText("Salir de la cuenta");
        jToggleButton_salirOpcionesAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_salirOpcionesAdminActionPerformed(evt);
            }
        });

        jToggleButton_verDatosAdmin.setText("Ver tus datos");
        jToggleButton_verDatosAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_verDatosAdminActionPerformed(evt);
            }
        });

        jToggleButton_verListaAdmin.setText("Ver lista de usuarios");
        jToggleButton_verListaAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_verListaAdminActionPerformed(evt);
            }
        });

        jToggleButton_borrarUsuarioAdmin.setText("Borrar usuario");
        jToggleButton_borrarUsuarioAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_borrarUsuarioAdminActionPerformed(evt);
            }
        });

        jToggleButton_editarDatosAdmin.setText("Editar tus datos");
        jToggleButton_editarDatosAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_editarDatosAdminActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_opcionesAdminLayout = new javax.swing.GroupLayout(jPanel_opcionesAdmin);
        jPanel_opcionesAdmin.setLayout(jPanel_opcionesAdminLayout);
        jPanel_opcionesAdminLayout.setHorizontalGroup(
            jPanel_opcionesAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_opcionesAdminLayout.createSequentialGroup()
                .addGap(297, 297, 297)
                .addGroup(jPanel_opcionesAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jToggleButton_salirOpcionesAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton_borrarUsuarioAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton_verListaAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton_editarDatosAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButton_verDatosAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(311, Short.MAX_VALUE))
        );
        jPanel_opcionesAdminLayout.setVerticalGroup(
            jPanel_opcionesAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_opcionesAdminLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jToggleButton_verDatosAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jToggleButton_editarDatosAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jToggleButton_verListaAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jToggleButton_borrarUsuarioAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 253, Short.MAX_VALUE)
                .addComponent(jToggleButton_salirOpcionesAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
        );

        jPanel_opcionesCliente.setPreferredSize(new java.awt.Dimension(930, 597));

        jToggleButton_salirOpcionesCliente.setText("Salir de la cuenta");
        jToggleButton_salirOpcionesCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_salirOpcionesClienteActionPerformed(evt);
            }
        });

        jToggleButton_verDatosCliente.setText("Ver tus datos");
        jToggleButton_verDatosCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_verDatosClienteActionPerformed(evt);
            }
        });

        jToggleButton_verListaCliente.setText("Ver lista de Chefs");
        jToggleButton_verListaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_verListaClienteActionPerformed(evt);
            }
        });

        jToggleButton_bajaCliente.setText("Dar de baja mi cuenta");
        jToggleButton_bajaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_bajaClienteActionPerformed(evt);
            }
        });

        jToggleButton_editarDatosCliente.setText("Editar tus datos");
        jToggleButton_editarDatosCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_editarDatosClienteActionPerformed(evt);
            }
        });

        jToggleButton_altaNuevaReserva.setText("Alta nueva reserva");
        jToggleButton_altaNuevaReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_altaNuevaReservaActionPerformed(evt);
            }
        });

        jToggleButton_eliminarReserva.setText("Gestionar reservas existentes");
        jToggleButton_eliminarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_eliminarReservaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_opcionesClienteLayout = new javax.swing.GroupLayout(jPanel_opcionesCliente);
        jPanel_opcionesCliente.setLayout(jPanel_opcionesClienteLayout);
        jPanel_opcionesClienteLayout.setHorizontalGroup(
            jPanel_opcionesClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_opcionesClienteLayout.createSequentialGroup()
                .addGroup(jPanel_opcionesClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_opcionesClienteLayout.createSequentialGroup()
                        .addGap(330, 330, 330)
                        .addGroup(jPanel_opcionesClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jToggleButton_salirOpcionesCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jToggleButton_bajaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jToggleButton_verListaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jToggleButton_editarDatosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jToggleButton_verDatosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel_opcionesClienteLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel_opcionesClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jToggleButton_altaNuevaReserva, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                            .addComponent(jToggleButton_eliminarReserva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(348, Short.MAX_VALUE))
        );
        jPanel_opcionesClienteLayout.setVerticalGroup(
            jPanel_opcionesClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_opcionesClienteLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jToggleButton_verDatosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jToggleButton_editarDatosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jToggleButton_verListaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jToggleButton_altaNuevaReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jToggleButton_eliminarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jToggleButton_bajaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 116, Short.MAX_VALUE)
                .addComponent(jToggleButton_salirOpcionesCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );

        jPanel_opcionesChef.setPreferredSize(new java.awt.Dimension(930, 597));

        jToggleButton_salirOpcionesChef.setText("Salir de la cuenta");
        jToggleButton_salirOpcionesChef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_salirOpcionesChefActionPerformed(evt);
            }
        });

        jToggleButton_verDatosChef.setText("Ver tus datos");
        jToggleButton_verDatosChef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_verDatosChefActionPerformed(evt);
            }
        });

        jToggleButton_disponibilidadChef.setText("Indicar disponibilidad");
        jToggleButton_disponibilidadChef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_disponibilidadChefActionPerformed(evt);
            }
        });

        jToggleButton_bajaChef.setText("Dar de baja mi cuenta");
        jToggleButton_bajaChef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_bajaChefActionPerformed(evt);
            }
        });

        jToggleButton_editarDatosChef.setText("Editar tus datos");
        jToggleButton_editarDatosChef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_editarDatosChefActionPerformed(evt);
            }
        });

        jToggleButton_revisarReservasChef.setText("Gestionar reservas");
        jToggleButton_revisarReservasChef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_revisarReservasChefActionPerformed(evt);
            }
        });

        jToggleButton_indicarTarifa.setText("Gestionar tarifa");
        jToggleButton_indicarTarifa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton_indicarTarifaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_opcionesChefLayout = new javax.swing.GroupLayout(jPanel_opcionesChef);
        jPanel_opcionesChef.setLayout(jPanel_opcionesChefLayout);
        jPanel_opcionesChefLayout.setHorizontalGroup(
            jPanel_opcionesChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_opcionesChefLayout.createSequentialGroup()
                .addGap(334, 334, 334)
                .addGroup(jPanel_opcionesChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jToggleButton_indicarTarifa, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                    .addComponent(jToggleButton_revisarReservasChef, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToggleButton_verDatosChef, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToggleButton_editarDatosChef, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToggleButton_disponibilidadChef, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToggleButton_bajaChef, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                    .addComponent(jToggleButton_salirOpcionesChef, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(430, Short.MAX_VALUE))
        );
        jPanel_opcionesChefLayout.setVerticalGroup(
            jPanel_opcionesChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_opcionesChefLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jToggleButton_verDatosChef, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jToggleButton_editarDatosChef, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jToggleButton_indicarTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jToggleButton_disponibilidadChef, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jToggleButton_revisarReservasChef, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jToggleButton_bajaChef, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addComponent(jToggleButton_salirOpcionesChef, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );

        jPanel_borrarUsuario.setPreferredSize(new java.awt.Dimension(930, 597));

        jLabel13.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel13.setText("Indicar el usuario a eliminar:");

        jButton_eliminarBorrarUsuario.setText("Eliminar");
        jButton_eliminarBorrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_eliminarBorrarUsuarioActionPerformed(evt);
            }
        });

        jButton_volverBorrarUsuario.setText("Volver");
        jButton_volverBorrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_volverBorrarUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_borrarUsuarioLayout = new javax.swing.GroupLayout(jPanel_borrarUsuario);
        jPanel_borrarUsuario.setLayout(jPanel_borrarUsuarioLayout);
        jPanel_borrarUsuarioLayout.setHorizontalGroup(
            jPanel_borrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_borrarUsuarioLayout.createSequentialGroup()
                .addGap(380, 380, 380)
                .addGroup(jPanel_borrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_eliminarBorrarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_usuarioBorrar)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(401, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_borrarUsuarioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_volverBorrarUsuario)
                .addGap(126, 126, 126))
        );
        jPanel_borrarUsuarioLayout.setVerticalGroup(
            jPanel_borrarUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_borrarUsuarioLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jTextField_usuarioBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84)
                .addComponent(jButton_eliminarBorrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 222, Short.MAX_VALUE)
                .addComponent(jButton_volverBorrarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
        );

        jPanel_disponibilidad.setPreferredSize(new java.awt.Dimension(930, 597));

        jLabel14.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel14.setText("y la población donde desea efectar los servicios.");

        jLabel15.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel15.setText("Tenga en cuanta que podrá recibir solicitudes mientras la disponibilidad esté activa.");

        jCheckBox_disponible.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jCheckBox_disponible.setText("Activar / Desactivar la disponibilidad para la población seleccionada");
        jCheckBox_disponible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox_disponibleActionPerformed(evt);
            }
        });

        jButton1.setText("Volver");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel16.setText("Población de realización de servicios:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel17.setText("Es posible que clientes de zonas cercanas a la población indicada también le remitan solicitudes");

        jComboBox_poblacion.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox_poblacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_poblacionActionPerformed(evt);
            }
        });

        jComboBox_filtroAZ.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox_filtroAZ.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "De la A a la F", "De la G a la S", "De la T a la Z" }));
        jComboBox_filtroAZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_filtroAZActionPerformed(evt);
            }
        });

        jButton_aplicarCambiosDisponibilidad.setText("Aplicar Cambios");
        jButton_aplicarCambiosDisponibilidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_aplicarCambiosDisponibilidadActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel18.setText("Filtro:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel19.setText("Población:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel20.setText("Aquí puede indicar si está disponible para recibir solicitudes de clientes");

        jLabel24.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel24.setText("Puede, si lo desea, activar su disponibilidad en varias poblaciones.");

        javax.swing.GroupLayout jPanel_disponibilidadLayout = new javax.swing.GroupLayout(jPanel_disponibilidad);
        jPanel_disponibilidad.setLayout(jPanel_disponibilidadLayout);
        jPanel_disponibilidadLayout.setHorizontalGroup(
            jPanel_disponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_disponibilidadLayout.createSequentialGroup()
                .addContainerGap(162, Short.MAX_VALUE)
                .addGroup(jPanel_disponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_disponibilidadLayout.createSequentialGroup()
                        .addGroup(jPanel_disponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel17)
                            .addGroup(jPanel_disponibilidadLayout.createSequentialGroup()
                                .addGap(90, 90, 90)
                                .addComponent(jLabel14))
                            .addComponent(jLabel24)
                            .addGroup(jPanel_disponibilidadLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel20)))
                        .addGap(198, 198, 198))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_disponibilidadLayout.createSequentialGroup()
                        .addComponent(jButton_aplicarCambiosDisponibilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(185, 185, 185)
                        .addComponent(jButton1)
                        .addGap(165, 165, 165))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_disponibilidadLayout.createSequentialGroup()
                        .addGroup(jPanel_disponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox_disponible, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(228, 228, 228))))
            .addGroup(jPanel_disponibilidadLayout.createSequentialGroup()
                .addGap(286, 286, 286)
                .addGroup(jPanel_disponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18))
                .addGap(22, 22, 22)
                .addGroup(jPanel_disponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox_filtroAZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_poblacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel_disponibilidadLayout.setVerticalGroup(
            jPanel_disponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_disponibilidadLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox_disponible, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel_disponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_disponibilidadLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_disponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox_filtroAZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addGap(50, 50, 50))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_disponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(jComboBox_poblacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addGap(40, 40, 40)
                .addGroup(jPanel_disponibilidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_aplicarCambiosDisponibilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47))
        );

        jPanel_listaChefsDisponibles.setPreferredSize(new java.awt.Dimension(930, 597));

        jLabel21.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel21.setText("Filtro:");

        jLabel22.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel22.setText("Población:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel23.setText("Listado de Chefs disponibles por población");

        jButton_volverListadoChefsDisponibles.setText("Volver");
        jButton_volverListadoChefsDisponibles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_volverListadoChefsDisponiblesActionPerformed(evt);
            }
        });

        jComboBox_filtroAZ1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox_filtroAZ1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "De la A a la F", "De la G a la S", "De la T a la Z" }));
        jComboBox_filtroAZ1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_filtroAZ1ActionPerformed(evt);
            }
        });

        jComboBox_poblacion1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox_poblacion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_poblacion1ActionPerformed(evt);
            }
        });

        jTable_listaChefsEstado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre usuario", "Población", "Disponibilidad"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable_listaChefsEstado);

        jRadioButton_filtradoListaChefs.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jRadioButton_filtradoListaChefs.setText("Activar/Desactivar filtrado por población");
        jRadioButton_filtradoListaChefs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_filtradoListaChefsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_listaChefsDisponiblesLayout = new javax.swing.GroupLayout(jPanel_listaChefsDisponibles);
        jPanel_listaChefsDisponibles.setLayout(jPanel_listaChefsDisponiblesLayout);
        jPanel_listaChefsDisponiblesLayout.setHorizontalGroup(
            jPanel_listaChefsDisponiblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_listaChefsDisponiblesLayout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel_listaChefsDisponiblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel22)
                    .addGroup(jPanel_listaChefsDisponiblesLayout.createSequentialGroup()
                        .addComponent(jRadioButton_filtradoListaChefs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21)))
                .addGap(40, 40, 40)
                .addGroup(jPanel_listaChefsDisponiblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox_poblacion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_filtroAZ1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(428, 428, 428))
            .addGroup(jPanel_listaChefsDisponiblesLayout.createSequentialGroup()
                .addGroup(jPanel_listaChefsDisponiblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_listaChefsDisponiblesLayout.createSequentialGroup()
                        .addGap(221, 221, 221)
                        .addComponent(jLabel23)
                        .addGap(82, 82, 82)
                        .addComponent(jButton_volverListadoChefsDisponibles, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_listaChefsDisponiblesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 913, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_listaChefsDisponiblesLayout.setVerticalGroup(
            jPanel_listaChefsDisponiblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_listaChefsDisponiblesLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel_listaChefsDisponiblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jButton_volverListadoChefsDisponibles, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel_listaChefsDisponiblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jComboBox_filtroAZ1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton_filtradoListaChefs))
                .addGap(18, 18, 18)
                .addGroup(jPanel_listaChefsDisponiblesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jComboBox_poblacion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 389, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        jPanel_eliminarPropiaCuenta.setPreferredSize(new java.awt.Dimension(930, 597));

        jLabel25.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel25.setText("Si desea eliminar definitivamente su cuenta, pulse el botón \"Eliminar\", en caso contrario pulse \"Volver\".");

        jLabel26.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel26.setText("La eliminación de la cuenta es una acción irreversible. Deberá crear otra para volver a acceder al programa.");

        jButton_eliminarEliminarCuenta.setText("Eliminar");
        jButton_eliminarEliminarCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_eliminarEliminarCuentaActionPerformed(evt);
            }
        });

        jButton_volverEliminarCuenta.setText("Volver");
        jButton_volverEliminarCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_volverEliminarCuentaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_eliminarPropiaCuentaLayout = new javax.swing.GroupLayout(jPanel_eliminarPropiaCuenta);
        jPanel_eliminarPropiaCuenta.setLayout(jPanel_eliminarPropiaCuentaLayout);
        jPanel_eliminarPropiaCuentaLayout.setHorizontalGroup(
            jPanel_eliminarPropiaCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_eliminarPropiaCuentaLayout.createSequentialGroup()
                .addContainerGap(177, Short.MAX_VALUE)
                .addGroup(jPanel_eliminarPropiaCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jLabel25))
                .addGap(191, 191, 191))
            .addGroup(jPanel_eliminarPropiaCuentaLayout.createSequentialGroup()
                .addGap(254, 254, 254)
                .addComponent(jButton_eliminarEliminarCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(173, 173, 173)
                .addComponent(jButton_volverEliminarCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_eliminarPropiaCuentaLayout.setVerticalGroup(
            jPanel_eliminarPropiaCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_eliminarPropiaCuentaLayout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel26)
                .addGap(132, 132, 132)
                .addGroup(jPanel_eliminarPropiaCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_eliminarEliminarCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(jButton_volverEliminarCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(281, Short.MAX_VALUE))
        );

        jPanel_altaReserva.setPreferredSize(new java.awt.Dimension(930, 597));

        jLabel27.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel27.setText("Indique fecha deseada:");

        jLabel28.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel28.setText("Indique la hora de inicio:");

        jLabel29.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel29.setText("Indique la hora de finalización:");

        jLabel30.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel30.setText("Indique la población:");

        jComboBox_filtroAZReserva.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox_filtroAZReserva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "De la A a la F", "De la G a la S", "De la T a la Z" }));
        jComboBox_filtroAZReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_filtroAZReservaActionPerformed(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel31.setText("Filtro:");

        jComboBox_poblacionReserva.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox_poblacionReserva.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox_poblacionReservaItemStateChanged(evt);
            }
        });
        jComboBox_poblacionReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_poblacionReservaActionPerformed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel32.setText("Población:");

        jLabel33.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel33.setText("Seleccione uno de los Chefs disponibles en la población indicada:");

        jLabel34.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel34.setText("Si lo desea puede comprobar el precio a pagar por el servicio:");

        jButton_solicitarReserva.setText("Solicitar Reserva");
        jButton_solicitarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_solicitarReservaActionPerformed(evt);
            }
        });

        jButton_voverReserva.setText("Volver");
        jButton_voverReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_voverReservaActionPerformed(evt);
            }
        });

        jComboBox_horaInicioReserva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));

        jComboBox_horaFinReserva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));

        jComboBox_minutosInicioReserva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "15", "30", "45" }));

        jComboBox_minutosFinReserva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "15", "30", "45" }));

        jTextField_precioReserva.setText("00");
        jTextField_precioReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_precioReservaActionPerformed(evt);
            }
        });
        jTextField_precioReserva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_precioReservaKeyTyped(evt);
            }
        });

        jLabel35.setText("€");

        jLabel54.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel54.setText("Indique el número de comensales:");

        jTextField_comensalesReserva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_comensalesReservaKeyTyped(evt);
            }
        });

        jButton_calcularPrecio.setText("Calcular precio");
        jButton_calcularPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_calcularPrecioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_altaReservaLayout = new javax.swing.GroupLayout(jPanel_altaReserva);
        jPanel_altaReserva.setLayout(jPanel_altaReservaLayout);
        jPanel_altaReservaLayout.setHorizontalGroup(
            jPanel_altaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_altaReservaLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel_altaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel27)
                    .addComponent(jCalendar_fechaCalendarioReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addGroup(jPanel_altaReservaLayout.createSequentialGroup()
                        .addComponent(jComboBox_horaInicioReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(jComboBox_minutosInicioReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_altaReservaLayout.createSequentialGroup()
                        .addComponent(jComboBox_horaFinReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(jComboBox_minutosFinReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel29))
                .addGap(86, 86, 86)
                .addGroup(jPanel_altaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34)
                    .addComponent(jComboBox_chefReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30)
                    .addGroup(jPanel_altaReservaLayout.createSequentialGroup()
                        .addGroup(jPanel_altaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel32)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_altaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox_filtroAZReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_poblacionReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel_altaReservaLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jButton_calcularPrecio)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField_precioReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField_comensalesReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel_altaReservaLayout.createSequentialGroup()
                .addGap(396, 396, 396)
                .addComponent(jButton_solicitarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_voverReserva)
                .addGap(166, 166, 166))
        );
        jPanel_altaReservaLayout.setVerticalGroup(
            jPanel_altaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_altaReservaLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel_altaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jLabel30))
                .addGap(18, 18, 18)
                .addGroup(jPanel_altaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_altaReservaLayout.createSequentialGroup()
                        .addGroup(jPanel_altaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(jComboBox_filtroAZReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel_altaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(jComboBox_poblacionReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(jLabel54)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField_comensalesReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jCalendar_fechaCalendarioReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel_altaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(jLabel28))
                .addGroup(jPanel_altaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_altaReservaLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jComboBox_chefReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_altaReservaLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_altaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox_minutosInicioReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_horaInicioReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(46, 46, 46)
                .addGroup(jPanel_altaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(jLabel29))
                .addGap(18, 18, 18)
                .addGroup(jPanel_altaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox_horaFinReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_altaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox_minutosFinReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField_precioReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton_calcularPrecio)))
                .addGap(57, 57, 57)
                .addGroup(jPanel_altaReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_solicitarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_voverReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(82, 82, 82))
        );

        jPanel_editarReserva.setPreferredSize(new java.awt.Dimension(930, 597));

        jLabel36.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel36.setText("Seleccione en la tabla la reserva de la que desee informar del pago o que desee eliminar:");

        jTable_listaReservasCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Estado", "Chef", "Día", "Hora Inicio", "Hora Fin", "Comensales", "Precio", "InfoPago"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_listaReservasCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_listaReservasClienteMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable_listaReservasCliente);
        if (jTable_listaReservasCliente.getColumnModel().getColumnCount() > 0) {
            jTable_listaReservasCliente.getColumnModel().getColumn(8).setMinWidth(0);
            jTable_listaReservasCliente.getColumnModel().getColumn(8).setPreferredWidth(0);
            jTable_listaReservasCliente.getColumnModel().getColumn(8).setMaxWidth(0);
        }

        jLabel37.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel37.setText("Reserva seleccionada:");

        jTextField_estadoReservaEliminar.setEditable(false);

        jTextField_chefReservaEliminar.setEditable(false);

        jTextField_inicioReservaEliminar.setEditable(false);
        jTextField_inicioReservaEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_inicioReservaEliminarActionPerformed(evt);
            }
        });

        jTextField_precioReservaEliminar.setEditable(false);

        jTextField_finReservaEliminar.setEditable(false);

        jButton_eliminarReserva.setText("Eliminar Reserva");
        jButton_eliminarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_eliminarReservaActionPerformed(evt);
            }
        });

        jButton_volverEliminarReserva.setText("Volver");
        jButton_volverEliminarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_volverEliminarReservaActionPerformed(evt);
            }
        });

        jTextField_diaReservaEliminar.setEditable(false);

        jTextField_IDReservaEliminar.setEditable(false);

        jLabel38.setText("ID Reserva:");

        jLabel39.setText("Estado Reserva:");

        jLabel40.setText("Chef:");

        jLabel41.setText("Día Reservado:");

        jLabel42.setText("Hora de Inicio:");

        jLabel43.setText("Hora de fin:");

        jLabel44.setText("Precio Reserva:");

        jLabel55.setText("Comensales:");

        jTextField_comensalesReservaEliminar.setEditable(false);
        jTextField_comensalesReservaEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_comensalesReservaEliminarActionPerformed(evt);
            }
        });

        jButton_pagoReserva.setText("Reserva Pagada");
        jButton_pagoReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_pagoReservaActionPerformed(evt);
            }
        });

        jTextArea_infoPagoReserva.setEditable(false);
        jTextArea_infoPagoReserva.setBackground(new java.awt.Color(240, 240, 240));
        jTextArea_infoPagoReserva.setColumns(20);
        jTextArea_infoPagoReserva.setRows(5);
        jTextArea_infoPagoReserva.setFocusable(false);
        jTextArea_infoPagoReserva.setOpaque(false);
        jTextArea_infoPagoReserva.setPreferredSize(new java.awt.Dimension(164, 40));
        jTextArea_infoPagoReserva.setRequestFocusEnabled(false);
        jScrollPane5.setViewportView(jTextArea_infoPagoReserva);

        javax.swing.GroupLayout jPanel_editarReservaLayout = new javax.swing.GroupLayout(jPanel_editarReserva);
        jPanel_editarReserva.setLayout(jPanel_editarReservaLayout);
        jPanel_editarReservaLayout.setHorizontalGroup(
            jPanel_editarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_editarReservaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel_editarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_editarReservaLayout.createSequentialGroup()
                        .addComponent(jButton_volverEliminarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(143, 143, 143))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_editarReservaLayout.createSequentialGroup()
                        .addComponent(jButton_pagoReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(jButton_eliminarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(374, 374, 374))))
            .addGroup(jPanel_editarReservaLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(jPanel_editarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel36)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 797, Short.MAX_VALUE)
                    .addComponent(jScrollPane5))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel_editarReservaLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel_editarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_editarReservaLayout.createSequentialGroup()
                        .addGroup(jPanel_editarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_IDReservaEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel_editarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39)
                            .addComponent(jTextField_estadoReservaEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel_editarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_chefReservaEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_editarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel41)
                            .addComponent(jTextField_diaReservaEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_editarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField_inicioReservaEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_editarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_finReservaEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel43))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel_editarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_comensalesReservaEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel55))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_editarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44)
                            .addComponent(jTextField_precioReservaEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel_editarReservaLayout.setVerticalGroup(
            jPanel_editarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_editarReservaLayout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel36)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_editarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jLabel39)
                    .addComponent(jLabel40)
                    .addComponent(jLabel41)
                    .addComponent(jLabel42)
                    .addComponent(jLabel55)
                    .addComponent(jLabel44)
                    .addComponent(jLabel43))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_editarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_IDReservaEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_estadoReservaEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_chefReservaEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_diaReservaEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_inicioReservaEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_finReservaEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_comensalesReservaEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_precioReservaEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addGroup(jPanel_editarReservaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_eliminarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_pagoReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_volverEliminarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        jPanel_aceptarReservaChef.setPreferredSize(new java.awt.Dimension(930, 597));

        jLabel45.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel45.setText("Seleccione en la tabla la reserva que desee aceptar, rechazar o confirmar la recepción del pago:");

        jTable_listaReservasChef.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Estado", "Cliente", "Fecha Reserva", "Hora Inicio", "Hora Fin", "Comensales", "Precio"
            }
        ));
        jTable_listaReservasChef.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_listaReservasChefMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable_listaReservasChef);

        jButton_aceptarReservaChef.setText("Aceptar Reserva");
        jButton_aceptarReservaChef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_aceptarReservaChefActionPerformed(evt);
            }
        });

        jButton3.setText("Volver");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel46.setText("ID Reserva:");

        jTextField_IDReservaChef.setEditable(false);

        jTextField_estadoReservaChef.setEditable(false);

        jLabel47.setText("Estado:");

        jTextField_clienteReservaChef.setEditable(false);
        jTextField_clienteReservaChef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_clienteReservaChefActionPerformed(evt);
            }
        });

        jLabel48.setText("Cliente:");

        jTextField_fechaReservaChef.setEditable(false);

        jLabel49.setText("Fecha de la Reserva:");

        jTextField_inicioReservaChef.setEditable(false);

        jLabel50.setText("Hora de Inicio:");

        jTextField_finReservaChef.setEditable(false);

        jLabel51.setText("Hora de Fin:");

        jTextField_comensalesReservaChef.setEditable(false);

        jLabel52.setText("Nº Comensales:");

        jTextField_precioReservaChef.setEditable(false);

        jLabel53.setText("Precio Reserva:");

        jLabel56.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel56.setText("Reserva seleccionada:");

        jButton_confirmarPago.setText("Confirmar Pago Recibido");
        jButton_confirmarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_confirmarPagoActionPerformed(evt);
            }
        });

        jButton_rechazarReservaChef.setText("Rechazar Reserva");
        jButton_rechazarReservaChef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_rechazarReservaChefActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_aceptarReservaChefLayout = new javax.swing.GroupLayout(jPanel_aceptarReservaChef);
        jPanel_aceptarReservaChef.setLayout(jPanel_aceptarReservaChefLayout);
        jPanel_aceptarReservaChefLayout.setHorizontalGroup(
            jPanel_aceptarReservaChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_aceptarReservaChefLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel_aceptarReservaChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel56)
                    .addComponent(jLabel45)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 822, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_aceptarReservaChefLayout.createSequentialGroup()
                        .addGroup(jPanel_aceptarReservaChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel_aceptarReservaChefLayout.createSequentialGroup()
                                .addGap(101, 101, 101)
                                .addComponent(jButton_aceptarReservaChef, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(jButton_rechazarReservaChef, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_aceptarReservaChefLayout.createSequentialGroup()
                                .addGroup(jPanel_aceptarReservaChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField_IDReservaChef)
                                    .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel_aceptarReservaChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel_aceptarReservaChefLayout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(jTextField_estadoReservaChef, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel_aceptarReservaChefLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel47)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel_aceptarReservaChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_clienteReservaChef, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel48))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel_aceptarReservaChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField_fechaReservaChef))))
                        .addGroup(jPanel_aceptarReservaChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_aceptarReservaChefLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel_aceptarReservaChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField_inicioReservaChef))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel_aceptarReservaChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField_finReservaChef)
                                    .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel_aceptarReservaChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField_comensalesReservaChef))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel_aceptarReservaChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel53)
                                    .addComponent(jTextField_precioReservaChef, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel_aceptarReservaChefLayout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(jButton_confirmarPago)
                                .addGap(86, 86, 86)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel_aceptarReservaChefLayout.setVerticalGroup(
            jPanel_aceptarReservaChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_aceptarReservaChefLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel45)
                .addGap(28, 28, 28)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jLabel56)
                .addGap(18, 18, 18)
                .addGroup(jPanel_aceptarReservaChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_aceptarReservaChefLayout.createSequentialGroup()
                        .addGroup(jPanel_aceptarReservaChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_aceptarReservaChef, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_confirmarPago, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_rechazarReservaChef, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(59, 59, 59))
                    .addGroup(jPanel_aceptarReservaChefLayout.createSequentialGroup()
                        .addGroup(jPanel_aceptarReservaChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46)
                            .addComponent(jLabel47)
                            .addComponent(jLabel48)
                            .addComponent(jLabel49)
                            .addComponent(jLabel50)
                            .addComponent(jLabel51)
                            .addComponent(jLabel52)
                            .addComponent(jLabel53))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_aceptarReservaChefLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_estadoReservaChef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_IDReservaChef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_clienteReservaChef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_fechaReservaChef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_inicioReservaChef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_finReservaChef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_comensalesReservaChef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_precioReservaChef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))))
        );

        jPanel_modificarTarifa.setPreferredSize(new java.awt.Dimension(930, 597));

        jLabel57.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel57.setText("Puede dar de alta su tarifa o modificar la misma si así lo desea");

        jTextField_indicarTarifa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_indicarTarifaActionPerformed(evt);
            }
        });
        jTextField_indicarTarifa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField_indicarTarifaKeyTyped(evt);
            }
        });

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel58.setText("€");

        jButton_modificarTarifa.setText("Modificar Tarifa");
        jButton_modificarTarifa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_modificarTarifaActionPerformed(evt);
            }
        });

        jButton_volverTarifa.setText("Volver");
        jButton_volverTarifa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_volverTarifaActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel10.setText("(Indique el importe que desea recibir por cada hora del servicio de cada comensal)");

        javax.swing.GroupLayout jPanel_modificarTarifaLayout = new javax.swing.GroupLayout(jPanel_modificarTarifa);
        jPanel_modificarTarifa.setLayout(jPanel_modificarTarifaLayout);
        jPanel_modificarTarifaLayout.setHorizontalGroup(
            jPanel_modificarTarifaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_modificarTarifaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton_volverTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(160, 160, 160))
            .addGroup(jPanel_modificarTarifaLayout.createSequentialGroup()
                .addGroup(jPanel_modificarTarifaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_modificarTarifaLayout.createSequentialGroup()
                        .addGap(397, 397, 397)
                        .addComponent(jTextField_indicarTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel58))
                    .addGroup(jPanel_modificarTarifaLayout.createSequentialGroup()
                        .addGap(373, 373, 373)
                        .addComponent(jButton_modificarTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_modificarTarifaLayout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_modificarTarifaLayout.createSequentialGroup()
                        .addGap(237, 237, 237)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(236, Short.MAX_VALUE))
        );
        jPanel_modificarTarifaLayout.setVerticalGroup(
            jPanel_modificarTarifaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_modificarTarifaLayout.createSequentialGroup()
                .addGap(120, 120, 120)
                .addComponent(jLabel57)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(26, 26, 26)
                .addGroup(jPanel_modificarTarifaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_indicarTarifa, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                .addComponent(jButton_modificarTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jButton_volverTarifa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );

        jPanel_cambiarContraseña.setPreferredSize(new java.awt.Dimension(930, 597));

        jLabel59.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel59.setText("Indique la nueva contraseña para aceceder a la plataforma partir de ahora:");

        jButton_modificarContraseña.setText("Modificar Contraseña");
        jButton_modificarContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_modificarContraseñaActionPerformed(evt);
            }
        });

        jButton_volverModificarContraseña.setText("Volver");
        jButton_volverModificarContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_volverModificarContraseñaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_cambiarContraseñaLayout = new javax.swing.GroupLayout(jPanel_cambiarContraseña);
        jPanel_cambiarContraseña.setLayout(jPanel_cambiarContraseñaLayout);
        jPanel_cambiarContraseñaLayout.setHorizontalGroup(
            jPanel_cambiarContraseñaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_cambiarContraseñaLayout.createSequentialGroup()
                .addGap(373, 373, 373)
                .addGroup(jPanel_cambiarContraseñaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton_modificarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_nuevaContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel_cambiarContraseñaLayout.createSequentialGroup()
                .addGap(192, 192, 192)
                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(202, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_cambiarContraseñaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_volverModificarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(154, 154, 154))
        );
        jPanel_cambiarContraseñaLayout.setVerticalGroup(
            jPanel_cambiarContraseñaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_cambiarContraseñaLayout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(jLabel59)
                .addGap(79, 79, 79)
                .addComponent(jTextField_nuevaContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 165, Short.MAX_VALUE)
                .addComponent(jButton_modificarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jButton_volverModificarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
        );

        jLayeredPane_contenedor.setLayer(jPanel_login, javax.swing.JLayeredPane.PALETTE_LAYER);
        jLayeredPane_contenedor.setLayer(jPanel_Usuario, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane_contenedor.setLayer(jPanel_crearCuenta, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane_contenedor.setLayer(jPanel_listarUsuarios, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane_contenedor.setLayer(jPanel_editarCuenta, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane_contenedor.setLayer(jPanel_opcionesAdmin, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane_contenedor.setLayer(jPanel_opcionesCliente, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane_contenedor.setLayer(jPanel_opcionesChef, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane_contenedor.setLayer(jPanel_borrarUsuario, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane_contenedor.setLayer(jPanel_disponibilidad, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane_contenedor.setLayer(jPanel_listaChefsDisponibles, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane_contenedor.setLayer(jPanel_eliminarPropiaCuenta, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane_contenedor.setLayer(jPanel_altaReserva, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane_contenedor.setLayer(jPanel_editarReserva, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane_contenedor.setLayer(jPanel_aceptarReservaChef, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane_contenedor.setLayer(jPanel_modificarTarifa, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane_contenedor.setLayer(jPanel_cambiarContraseña, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane_contenedorLayout = new javax.swing.GroupLayout(jLayeredPane_contenedor);
        jLayeredPane_contenedor.setLayout(jLayeredPane_contenedorLayout);
        jLayeredPane_contenedorLayout.setHorizontalGroup(
            jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_login, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE)
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_Usuario, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane_contenedorLayout.createSequentialGroup()
                    .addComponent(jPanel_crearCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane_contenedorLayout.createSequentialGroup()
                    .addComponent(jPanel_listarUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_editarCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_opcionesAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_opcionesCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_opcionesChef, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_borrarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_disponibilidad, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_listaChefsDisponibles, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_eliminarPropiaCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_altaReserva, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_editarReserva, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_aceptarReservaChef, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_modificarTarifa, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_cambiarContraseña, javax.swing.GroupLayout.DEFAULT_SIZE, 960, Short.MAX_VALUE))
        );
        jLayeredPane_contenedorLayout.setVerticalGroup(
            jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_login, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_Usuario, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_crearCuenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_listarUsuarios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_editarCuenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_opcionesAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_opcionesCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_opcionesChef, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_borrarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_disponibilidad, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_listaChefsDisponibles, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_eliminarPropiaCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_altaReserva, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_editarReserva, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_aceptarReservaChef, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_modificarTarifa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
            .addGroup(jLayeredPane_contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel_cambiarContraseña, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel_backgroundLayout = new javax.swing.GroupLayout(jPanel_background);
        jPanel_background.setLayout(jPanel_backgroundLayout);
        jPanel_backgroundLayout.setHorizontalGroup(
            jPanel_backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 950, Short.MAX_VALUE)
            .addGroup(jPanel_backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLayeredPane_contenedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel_backgroundLayout.setVerticalGroup(
            jPanel_backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 597, Short.MAX_VALUE)
            .addGroup(jPanel_backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel_backgroundLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLayeredPane_contenedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_background, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_usuarioActionPerformed
        // TODO add your handling code here:
        if (tipoUsuario == 1) {
            switchPanels(jPanel_opcionesAdmin);
        } else if (tipoUsuario == 2) {
            switchPanels(jPanel_opcionesChef);
        } else if (tipoUsuario == 3) {
            switchPanels(jPanel_opcionesCliente);
        }

    }//GEN-LAST:event_jButton_usuarioActionPerformed

    private void jButton_crearCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_crearCuentaActionPerformed
        // TODO add your handling code here:
        //Deben enviarse los datos a la BBDD
        String tipoUsuario = jComboBox_tipoUsuario.getSelectedItem().toString();        
        //Se comprueba que el usuario haya introducido datos
        if ((!jTextField_nombre.getText().isEmpty()
                && !jTextField_apellidos.getText().isEmpty() && !jTextField_direccion.getText().isEmpty()
                && !jTextField_codigoPostal.getText().isEmpty() && !jTextField_poblacion.getText().isEmpty()
                && !jTextField_nacionalidad.getText().isEmpty() && !jTextField_edad.getText().isEmpty()
                && !tipoUsuario.isEmpty() && !jTextField_telefono.getText().isEmpty() && !jTextField_email.getText().isEmpty()) /*&& comprobacionTarifa*/) {

            //Se realiza el POST a la API rest:
            String apiUrl = "https://render-chef-deluxe.onrender.com/api/auth/registrar"; //Dirección en Cloud           
            //String apiUrl = "https://localhost:8080/api/auth/registrar"; // Dirección en localhost
            URL url;
            try {
                url = new URL(apiUrl);
                connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                //connection.setSSLSocketFactory(SSLContext.getDefault().getSocketFactory());
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                //connection.setRequestProperty("Authorization", "Bearer " + token);                 
                //String payload = "{\"usernameOrEmail\": \"pepe2\", \"password\": \"password\"}";// This should be your json body i.e. {"Name" : "Mohsin"}
                //Se crea la variable del perfil adecuada (ROLE_) para hacer después el BODY
                String perfil = "";
                if (jComboBox_tipoUsuario.getSelectedItem().toString().equals("Cliente")) {
                    perfil = "ROLE_CLIENT";
                }
                if (jComboBox_tipoUsuario.getSelectedItem().toString().equals("Chef")) {
                    perfil = "ROLE_CHEF";
                }
                if (jComboBox_tipoUsuario.getSelectedItem().toString().equals("Administrador")) {
                    perfil = "ROLE_ADMIN";
                }
                //Se crea el BODY con los valores String de los label del panel Usuario

                String payload = "{\"nombre\": \"" + jTextField_nombre.getText() + "\","
                        + " \"email\": \"" + jTextField_email.getText() + "\","
                        + " \"username\": \"" + jTextField_username.getText() + "\","
                        + " \"password\": \"" + jTextField_contraseña.getText() + "\","
                        + " \"perfil\": \"" + perfil + "\","
                        + " \"apellidos\": \"" + jTextField_apellidos.getText() + "\","
                        + " \"direccion\": \"" + jTextField_direccion.getText() + "\","
                        + " \"codigoPostal\": \"" + jTextField_codigoPostal.getText() + "\","
                        + " \"poblacion\": \"" + jTextField_poblacion.getText() + "\","
                        + " \"nacionalidad\": \"" + jTextField_nacionalidad.getText() + "\","
                        + " \"edad\": \"" + jTextField_edad.getText() + "\","
                        + " \"telefono\": \"" + jTextField_telefono.getText() + "\","
                        + " \"iban\": \"" + jTextField_IBAN.getText() + "\"}";

                byte[] out = payload.getBytes(StandardCharsets.UTF_8);
                OutputStream stream = connection.getOutputStream();
                stream.write(out);
                if (connection.getResponseCode() != 200) {
                    JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());
                } else {

                    //Leyendo la respuesta de la API
                    StringBuilder sb = new StringBuilder();
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String linea;
                    while ((linea = in.readLine()) != null) {
                        sb.append(linea).append("\n");
                    }
                    in.close();
                    //Se recibe el mensaje de la API conforme se crea o si está duplicado
                    String mensajeRecibido = sb.toString();
                    //Se avisa de la creación de la cuenta al usuario:
                    JOptionPane.showMessageDialog(null, mensajeRecibido);
                    //Se cambia el panel
                    switchPanels(jPanel_login);
                }
                connection.disconnect();

            } catch (MalformedURLException ex) {
                JOptionPane.showMessageDialog(null, "MalformedURLException Error en la conexión con el servidor: " + ex);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "IOException Error en la conexión con el servidor: " + ex);
            }

        } else {
            //Se avisa al usuario que le faltan introducir datos para crear la cuenta
            JOptionPane.showMessageDialog(null, "Por favor, introduzca los datos necesarios");
        }

    }//GEN-LAST:event_jButton_crearCuentaActionPerformed

    private void jComboBox_tipoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_tipoUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_tipoUsuarioActionPerformed

    private void jButton_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_exitActionPerformed
        // TODO add your handling code here:
        token = "";
        switchPanels(jPanel_login);
    }//GEN-LAST:event_jButton_exitActionPerformed

    private void jTextField_nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_nombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_nombreActionPerformed

    private void jButton_editarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_editarCuentaActionPerformed
        // TODO add your handling code here:
        //Deben enviarse los datos a la BBDD
        String tipoDeUsuario = jComboBox_editarTipoUsuario.getSelectedItem().toString();
        //Se comprueba que el usuario haya introducido datos        
        if ((!jTextField_editarNombre.getText().isEmpty()
                && !jTextField_editarApellidos.getText().isEmpty() && !jTextField_editarDireccion.getText().isEmpty()
                && !jTextField_editarCodigoPostal.getText().isEmpty() && !jTextField_editarPoblacion.getText().isEmpty()
                && !jTextField_editarNacionalidad.getText().isEmpty() && !jTextField_editarEdad.getText().isEmpty()
                && !tipoDeUsuario.isEmpty() && !jTextField_editarTelefono.getText().isEmpty()
                && !jTextField_editarEmail.getText().isEmpty())) {

            //Se realiza el PUT a la API rest: 
            try {
                URL url;
                String urlString = "https://render-chef-deluxe.onrender.com/api/users/update/user"; //Dirección en Cloud
                //String urlString = "https://localhost:8080/api/users/update/user"; //Dirección en local
                urlString = urlString + "?usernameOrEmail=" + jTextField_usuario.getText();
                //      + "&password=password"; //+jPasswordField_password.getPassword().toString();
                String apiUrl = urlString; //api/http link   

                url = new URL(apiUrl);
                connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestMethod("PUT");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + token);
                //String payload = "{\"usernameOrEmail\": \"pepe2\", \"password\": \"password\"}";// This should be your json body i.e. {"Name" : "Mohsin"}
                //Se crea la variable del perfil adecuada (ROLE_) para hacer después el BODY
                String perfil = "";
                if (jComboBox_editarTipoUsuario.getSelectedItem().toString().equals("Cliente")) {
                    perfil = "ROLE_CLIENT";
                }
                if (jComboBox_editarTipoUsuario.getSelectedItem().toString().equals("Chef")) {
                    perfil = "ROLE_CHEF";
                }
                if (jComboBox_editarTipoUsuario.getSelectedItem().toString().equals("Administrador")) {
                    perfil = "ROLE_ADMIN";
                }
                //Se crea el BODY con los valores String de los label del panel Usuario

                String payload = "{\"nombre\": \"" + jTextField_editarNombre.getText() + "\","
                        + " \"username\": \"" + jTextField_editarUsername.getText() + "\","
                        + " \"email\": \"" + jTextField_editarEmail.getText() + "\","
                        + " \"password\": \"" + jPasswordField_password.getPassword().toString() + "\","
                        + " \"id\": \"" + ID + "\","
                        + " \"apellidos\": \"" + jTextField_editarApellidos.getText() + "\","
                        + " \"direccion\": \"" + jTextField_editarDireccion.getText() + "\","
                        + " \"codigoPostal\": \"" + jTextField_editarCodigoPostal.getText() + "\","
                        + " \"poblacion\": \"" + jTextField_editarPoblacion.getText() + "\","
                        + " \"nacionalidad\": \"" + jTextField_editarNacionalidad.getText() + "\","
                        + " \"edad\": \"" + jTextField_editarEdad.getText() + "\","
                        + " \"telefono\": \"" + jTextField_editarTelefono.getText() + "\","
                        + " \"iban\": \"" + jTextField_editarIBAN.getText() + "\","
                        + " \"perfil\": \"" + perfil + "\"}";

                byte[] out = payload.getBytes(StandardCharsets.UTF_8);
                OutputStream stream = connection.getOutputStream();
                stream.write(out);
                if (connection.getResponseCode() != 200) {
                    JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());
                } else {
                    JOptionPane.showMessageDialog(null, "Datos modificados correctamente");

                    //Se cambia el panel
                    switchPanels(jPanel_login);
                    if (tipoUsuario == 1) {
                        switchPanels(jPanel_opcionesAdmin);
                    } else if (tipoUsuario == 2) {
                        switchPanels(jPanel_opcionesChef);
                    } else if (tipoUsuario == 3) {
                        switchPanels(jPanel_opcionesCliente);
                    }
                }
                connection.disconnect();

            } catch (MalformedURLException ex) {
                JOptionPane.showMessageDialog(null, "Error en la conexión con el servidor: " + ex);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error en la conexión con el servidor: " + ex);
            }

        } else {
            //Se avisa al usuario que le faltan introducir datos para editar la cuenta
            JOptionPane.showMessageDialog(null, "Por favor, introduzca los datos necesarios");
        }

    }//GEN-LAST:event_jButton_editarCuentaActionPerformed

    private void jTextField_editarNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_editarNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_editarNombreActionPerformed

    private void jComboBox_editarTipoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_editarTipoUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox_editarTipoUsuarioActionPerformed

    private void jButton_exit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_exit1ActionPerformed
        // TODO add your handling code here:
        if (tipoUsuario == 1) {
            switchPanels(jPanel_opcionesAdmin);
        } else if (tipoUsuario == 2) {
            switchPanels(jPanel_opcionesChef);
        } else if (tipoUsuario == 3) {
            switchPanels(jPanel_opcionesCliente);
        }
    }//GEN-LAST:event_jButton_exit1ActionPerformed

    private void jToggleButton_verDatosAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_verDatosAdminActionPerformed
        // TODO add your handling code here:
        obtenerDatosUsuario();
        switchPanels(jPanel_Usuario);
        jToggleButton_verDatosAdmin.setSelected(false);
    }//GEN-LAST:event_jToggleButton_verDatosAdminActionPerformed

    private void jToggleButton_verDatosClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_verDatosClienteActionPerformed
        // TODO add your handling code here:
        RepaintManager.setCurrentManager(null);
        obtenerDatosUsuario();
        switchPanels(jPanel_Usuario);
        jToggleButton_verDatosCliente.setSelected(false);
    }//GEN-LAST:event_jToggleButton_verDatosClienteActionPerformed

    private void jToggleButton_verDatosChefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_verDatosChefActionPerformed
        // TODO add your handling code here:
        RepaintManager.setCurrentManager(null);
        obtenerDatosUsuario();
        switchPanels(jPanel_Usuario);
        jToggleButton_verDatosChef.setSelected(false);
    }//GEN-LAST:event_jToggleButton_verDatosChefActionPerformed

    private void jToggleButton_salirOpcionesAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_salirOpcionesAdminActionPerformed
        // TODO add your handling code here:
        token = "";
        jPasswordField_password.setText("----------");
        jTextField_usuario.setText("Usuario");
        jToggleButton_salirOpcionesAdmin.setSelected(false);
        switchPanels(jPanel_login);
    }//GEN-LAST:event_jToggleButton_salirOpcionesAdminActionPerformed

    private void jToggleButton_salirOpcionesClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_salirOpcionesClienteActionPerformed
        // TODO add your handling code here:
        token = "";
        jPasswordField_password.setText("----------");
        jTextField_usuario.setText("Usuario");
        jToggleButton_salirOpcionesCliente.setSelected(false);
        switchPanels(jPanel_login);
    }//GEN-LAST:event_jToggleButton_salirOpcionesClienteActionPerformed

    private void jToggleButton_salirOpcionesChefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_salirOpcionesChefActionPerformed
        // TODO add your handling code here:
        token = "";
        jPasswordField_password.setText("----------");
        jTextField_usuario.setText("Usuario");
        jToggleButton_salirOpcionesChef.setSelected(false);
        switchPanels(jPanel_login);
    }//GEN-LAST:event_jToggleButton_salirOpcionesChefActionPerformed

    private void jToggleButton_editarDatosAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_editarDatosAdminActionPerformed
        // TODO add your handling code here:
        obtenerDatosUsuario();
        jToggleButton_editarDatosAdmin.setSelected(false);
        switchPanels(jPanel_editarCuenta);
    }//GEN-LAST:event_jToggleButton_editarDatosAdminActionPerformed

    private void jToggleButton_editarDatosClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_editarDatosClienteActionPerformed
        // TODO add your handling code here:
        obtenerDatosUsuario();
        jToggleButton_editarDatosCliente.setSelected(false);
        switchPanels(jPanel_editarCuenta);
    }//GEN-LAST:event_jToggleButton_editarDatosClienteActionPerformed

    private void jToggleButton_editarDatosChefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_editarDatosChefActionPerformed
        // TODO add your handling code here:
        obtenerDatosUsuario();
        jToggleButton_editarDatosChef.setSelected(false);
        switchPanels(jPanel_editarCuenta);
    }//GEN-LAST:event_jToggleButton_editarDatosChefActionPerformed

    private void jToggleButton_verListaAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_verListaAdminActionPerformed
        // TODO add your handling code here:
        //Se crea el objeto tabla
        dtm = (DefaultTableModel) jTable_usuarios.getModel();
        //Se realiza la conexión y se obtiene un array JSon
        JSONArray respuesta = obtenerListaUsers();
        JSONObject user;
        JSONArray roles = null;
        //Se añaden los campos a la tabla
        for (int i = 0; i < respuesta.length(); i++) {
            user = respuesta.getJSONObject(i);
            roles = new JSONArray(user.get("roles").toString());
            JSONObject rol = roles.getJSONObject(0);
            dtm.addRow(new Object[]{user.getString("nombre"),
                user.get("apellidos").toString(),
                user.get("edad").toString(),
                user.get("direccion").toString(),
                user.get("codigoPostal").toString(),
                user.get("poblacion").toString(),
                user.get("nacionalidad").toString(),
                user.get("username").toString(),
                rol.get("role").toString(),
                user.get("email").toString(),
                user.get("telefono").toString(),
                user.get("iban").toString()

            });

        }
        jToggleButton_verListaAdmin.setSelected(false); //Se reinicia el botón
        switchPanels(jPanel_listarUsuarios); //Se cambia de panel

    }//GEN-LAST:event_jToggleButton_verListaAdminActionPerformed

    private void jButton_volverListarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_volverListarUsuariosActionPerformed
        // TODO add your handling code here:
        if (tipoUsuario == 1) {
            switchPanels(jPanel_opcionesAdmin);
        } else if (tipoUsuario == 2) {
            switchPanels(jPanel_opcionesChef);
        } else if (tipoUsuario == 3) {
            switchPanels(jPanel_opcionesCliente);
        }
        //Se borra la tabla
        dtm.setRowCount(0);
    }//GEN-LAST:event_jButton_volverListarUsuariosActionPerformed

    private void jToggleButton_verListaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_verListaClienteActionPerformed
        // TODO add your handling code here:
        RepaintManager.setCurrentManager(null);
        //Se crea el objeto tabla
        dtm = (DefaultTableModel) jTable_listaChefsEstado.getModel();
        //Se realiza la conexión y se obtiene un array JSon
        JSONArray respuesta = obtenerChefsEstado();
        JSONObject chef;
        //Se añaden los campos a la tabla
        for (int i = 0; i < respuesta.length(); i++) {
            chef = respuesta.getJSONObject(i);
            if (chef.get("estado").toString().equals("Activo")) {
                dtm.addRow(new Object[]{chef.getString("usernameOrEmail"),
                    chef.get("poblacion").toString(),
                    chef.get("estado").toString()

                });
            }

        }
        //Se crea un filtro en blanco para que aparezcan todos los resultados
        TableRowSorter trs = new TableRowSorter(dtm);
        trs.setModel(dtm);
        jTable_listaChefsEstado.setRowSorter(trs);//Se aplica el nuevo filtro
        jToggleButton_verListaCliente.setSelected(false); //Se reinicia el botón
        jComboBox_filtroAZ1.setSelectedItem("De la A a la F");
        jComboBox_poblacion1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Ababuj", "Abades", "Abadía", "Abadín", "Abadiño", "Abáigar", "Abajas", "Ábalos", "Abaltzisketa", "Abánades", "Abanilla", "Abanto", "Abanto y Ciérvana-Abanto Zierbena", "Abarán", "Abarca de Campos", "Abárzuza/Abartzuza", "Abaurregaina/Abaurrea Alta", "Abaurrepea/Abaurrea Baja", "Abegondo", "Abejar", "Abejuela", "Abella de la Conca", "Abengibre", "Abenójar", "Aberin", "Abertura", "Abezames", "Abia de la Obispalía", "Abia de las Torres", "Abiego", "Abizanda", "Abla", "Ablanque", "Ablitas", "Abrera", "Abrucena", "Abusejo", "Acebeda, La", "Acebedo", "Acebo", "Acebrón, El", "Acedera", "Acehúche", "Aceituna", "Acered", "Aceuchal", "Adahuesca", "Adalia", "Adamuz", "Adanero", "Adeje", "Ademuz", "Adiós", "Adobes", "Ador", "Adra", "Adrada de Haza", "Adrada de Pirón", "Adrada, La", "Adradas", "Adrados", "Adsubia", "Aduna", "Agaete", "Agallas", "Àger", "Agolada", "Agón", "Agoncillo", "Agost", "Agramunt", "Ágreda", "Agres", "Agrón", "Aguadulce", "Aguarón", "Aguas Cándidas", "Aguasal", "Aguatón", "Aguaviva", "Agudo", "Agüero", "Aguilafuente", "Aguilar de Bureba", "Aguilar de Campoo", "Aguilar de Campos", "Aguilar de Codés", "Aguilar de la Frontera", "Aguilar de Segarra", "Aguilar del Alfambra", "Aguilar del Río Alhama", "Águilas", "Aguilón", "Agüimes", "Agullana", "Agullent", "Agulo", "Ahigal", "Ahigal de los Aceiteros", "Ahigal de Villarino", "Ahillones", "Aia", "Aibar/Oibar", "Aielo de Malferit", "Aielo de Rugat", "Aiguafreda", "Aiguamúrcia", "Aiguaviva", "Aigües", "Aín", "Aínsa-Sobrarbe", "Ainzón", "Aisa", "Aitona", "Aizarnazabal", "Ajalvir", "Ajamil de Cameros", "Ajangiz", "Ajofrín", "Alacant/Alicante", "Alacón", "Aladrén", "Alaejos", "Alagón", "Alagón del Río", "Alaior", "Alájar", "Alajeró", "Alameda", "Alameda de Gardón, La", "Alameda de la Sagra", "Alameda del Valle", "Alamedilla", "Alamedilla, La", "Alamillo", "Alaminos", "Álamo, El", "Alamús, Els", "Alange", "Alanís", "Alaquàs", "Alar del Rey", "Alaraz", "Alarba", "Alarcón", "Alarilla", "Alaró", "Alàs i Cerc", "Alatoz", "Alba", "Alba de Cerrato", "Alba de Tormes", "Alba de Yeltes", "Albacete", "Albagés, L", "Albaida", "Albaida del Aljarafe", "Albal", "Albalá", "Albaladejo", "Albaladejo del Cuende", "Albalat de la Ribera", "Albalat dels Sorells", "Albalat dels Tarongers", "Albalate de Cinca", "Albalate de las Nogueras", "Albalate de Zorita", "Albalate del Arzobispo", "Albalatillo", "Albánchez", "Albanchez de Mágina", "Albanyà", "Albares", "Albarracín", "Albarreal de Tajo", "Albatana", "Albatàrrec", "Albatera", "Albelda", "Albelda de Iregua", "Albendea", "Albendiego", "Albentosa", "Alberca de Záncara, La", "Alberca, La", "Alberguería de Argañán, La", "Alberic", "Alberite", "Alberite de San Juan", "Albero Alto", "Albero Bajo", "Alberuela de Tubo", "Albesa", "Albeta", "Albi, L", "Albillos", "Albinyana", "Albiol, L", "Albiztur", "Albocàsser", "Alboloduy", "Albolote", "Albondón", "Albons", "Alborache", "Alboraya", "Alborea", "Alborge", "Albornos", "Albox", "Albudeite", "Albuera, La", "Albuixech", "Albuñán", "Albuñol", "Albuñuelas", "Alburquerque", "Alcabón", "Alcadozo", "Alcaine", "Alcalá de Ebro", "Alcalá de Guadaíra", "Alcalá de Gurrea", "Alcalá de Henares", "Alcalá de la Selva", "Alcalá de la Vega", "Alcalá de los Gazules", "Alcalá de Moncayo", "Alcalà de Xivert", "Alcalá del Júcar", "Alcalá del Obispo", "Alcalá del Río", "Alcalá del Valle", "Alcalá la Real", "Alcalalí", "Alcampell", "Alcanadre", "Alcanar", "Alcanó", "Alcántara", "Alcantarilla", "Alcàntera de Xúquer", "Alcantud", "Alcañices", "Alcañiz", "Alcañizo", "Alcaracejos", "Alcaraz", "Alcarràs", "Alcàsser", "Alcaucín", "Alcaudete", "Alcaudete de la Jara", "Alcázar de San Juan", "Alcázar del Rey", "Alcazarén", "Alcázares, Los", "Alcoba", "Alcobendas", "Alcocer", "Alcocer de Planes", "Alcocero de Mola", "Alcohujate", "Alcolea", "Alcolea de Calatrava", "Alcolea de Cinca", "Alcolea de las Peñas", "Alcolea de Tajo", "Alcolea del Pinar", "Alcolea del Río", "Alcoleja", "Alcoletge", "Alcollarín", "Alconaba", "Alconada", "Alconada de Maderuelo", "Alconchel", "Alconchel de Ariza", "Alconchel de la Estrella", "Alconera", "Alcóntar", "Alcora, l", "Alcorcón", "Alcorisa", "Alcoroches", "Alcover", "Alcoy/Alcoi", "Alcubierre", "Alcubilla de Avellaneda", "Alcubilla de las Peñas", "Alcubilla de Nogales", "Alcubillas", "Alcublas", "Alcúdia", "Alcúdia de Crespins, l", "Alcudia de Monteagud", "Alcudia de Veo", "Alcúdia, l", "Alcuéscar", "Aldaia", "Aldea de San Miguel", "Aldea de San Nicolás, La", "Aldea del Cano", "Aldea del Fresno", "Aldea del Obispo", "Aldea del Obispo, La", "Aldea del Rey", "Aldea en Cabo", "Aldea Real", "Aldea, L", "Aldeacentenera", "Aldeacipreste", "Aldeadávila de la Ribera", "Aldealafuente", "Aldealcorvo", "Aldealengua", "Aldealengua de Pedraza", "Aldealengua de Santa María", "Aldealices", "Aldealpozo", "Aldealseñor", "Aldeamayor de San Martín", "Aldeanueva de Barbarroya", "Aldeanueva de Ebro", "Aldeanueva de Figueroa", "Aldeanueva de Guadalajara", "Aldeanueva de la Serrezuela", "Aldeanueva de la Sierra", "Aldeanueva de la Vera", "Aldeanueva de San Bartolomé", "Aldeanueva de Santa Cruz", "Aldeanueva del Camino", "Aldeanueva del Codonal", "Aldeaquemada", "Aldearrodrigo", "Aldearrubia", "Aldeaseca", "Aldeaseca de Alba", "Aldeaseca de la Frontera", "Aldeasoña", "Aldeatejada", "Aldeavieja de Tormes", "Aldehorno", "Aldehuela de Jerte", "Aldehuela de la Bóveda", "Aldehuela de Liestos", "Aldehuela de Periáñez", "Aldehuela de Yeltes", "Aldehuela del Codonal", "Aldehuela, La", "Aldehuelas, Las", "Aldeire", "Aldeonte", "Aldover", "Aledo", "Alegia", "Alegría-Dulantzi", "Aleixar, L", "Alella", "Alentisque", "Alerre", "Alesanco", "Alesón", "Alfacar", "Alfafar", "Alfafara", "Alfajarín", "Alfambra", "Alfamén", "Alfántega", "Alfara de Carles", "Alfara de la Baronia", "Alfara del Patriarca", "Alfaraz de Sayago", "Alfarnate", "Alfarnatejo", "Alfaro", "Alfarp", "Alfarràs", "Alfarrasí", "Alfàs del Pi, l", "Alfauir", "Alfés", "Alfondeguilla", "Alforja", "Alforque", "Alfoz", "Alfoz de Bricia", "Alfoz de Lloredo", "Alfoz de Quintanadueñas", "Alfoz de Santa Gadea", "Algaba, La", "Algadefe", "Algaida", "Algámitas", "Algar", "Algar de Mesa", "Algar de Palancia", "Algarinejo", "Algarra", "Algarrobo", "Algatocín", "Algeciras", "Algemesí", "Algerri", "Algete", "Algimia de Alfara", "Algimia de Almonacid", "Alginet", "Algodonales", "Algodre", "Algora", "Algorfa", "Alguaire", "Alguazas", "Algueña", "Alhabia", "Alhama de Almería", "Alhama de Aragón", "Alhama de Granada", "Alhama de Murcia", "Alhambra", "Alhaurín de la Torre", "Alhaurín el Grande", "Alhendín", "Alhóndiga", "Alía", "Aliaga", "Aliaguilla", "Alicún", "Alicún de Ortega", "Alija del Infantado", "Alins", "Alió", "Alique", "Aliseda", "Aliud", "Aljaraque", "Aljucén", "Alkiza", "Allande", "Allariz", "Allepuz", "Aller", "Allín/Allin", "Allo", "Alloza", "Allueva", "Almacelles", "Almáchar", "Almadén", "Almadén de la Plata", "Almadenejos", "Almadrones", "Almagro", "Almajano", "Almaluez", "Almansa", "Almanza", "Almaraz", "Almaraz de Duero", "Almarcha, La", "Almargen", "Almarza", "Almarza de Cameros", "Almàssera", "Almatret", "Almazán", "Almazora/Almassora", "Almazul", "Almedíjar", "Almedina", "Almedinilla", "Almegíjar", "Almeida de Sayago", "Almenar", "Almenar de Soria", "Almenara", "Almenara de Adaja", "Almenara de Tormes", "Almendra", "Almendral", "Almendral de la Cañada", "Almendralejo", "Almendro, El", "Almendros", "Almensilla", "Almería", "Almiserà", "Almochuel", "Almócita", "Almodóvar del Campo", "Almodóvar del Pinar", "Almodóvar del Río", "Almogía", "Almoguera", "Almohaja", "Almoharín", "Almoines", "Almolda, La", "Almonacid de la Cuba", "Almonacid de la Sierra", "Almonacid de Toledo", "Almonacid de Zorita", "Almonacid del Marquesado", "Almonaster la Real", "Almonte", "Almoradí", "Almorox", "Almoster", "Almudaina", "Almudévar", "Almunia de Doña Godina, La", "Almunia de San Juan", "Almuniente", "Almuñécar", "Almuradiel", "Almussafes", "Alobras", "Alocén", "Alonsotegi", "Álora", "Alòs de Balaguer", "Alosno", "Alovera", "Alozaina", "Alp", "Alpandeire", "Alpanseque", "Alpartir", "Alpedrete", "Alpens", "Alpeñés", "Alpera", "Alpicat", "Alpuente", "Alpujarra de la Sierra", "Alqueria dAsnar, l", "Alqueria de la Comtessa, l", "Alquerías del Niño Perdido", "Alquézar", "Alquife", "Alsodux", "Alt Àneu", "Altable", "Altafulla", "Altarejos", "Altea", "Altorricón", "Altos, Los", "Altsasu/Alsasua", "Altura", "Altzaga", "Altzo", "Alustante", "Alzira", "Amavida", "Amayuelas de Arriba", "Ambel", "Ambite", "Amer", "Ames", "Améscoa Baja", "Ametlla de Mar, L", "Ametlla del Vallès, L", "Ameyugo", "Amezketa", "Amieva", "Amoeiro", "Amorebieta-Etxano", "Amoroto", "Ampolla, L", "Amposta", "Ampudia", "Ampuero", "Amurrio", "Amusco", "Amusquillo", "Anadón", "Anaya", "Anaya de Alba", "Anchuelo", "Anchuras", "Ancín/Antzin", "Andavías", "Andilla", "Andoain", "Andorra", "Andosilla", "Andratx", "Andújar", "Anento", "Anglès", "Anglesola", "Angón", "Anguciana", "Angüés", "Anguiano", "Anguita", "Anguix", "Anievas", "Aniñón", "Anna", "Anoeta", "Anquela del Ducado", "Anquela del Pedregal", "Ansó", "Ansoáin/Antsoain", "Antas", "Antas de Ulla", "Antella", "Antequera", "Antigua", "Antigua, La", "Antigüedad", "Antillón", "Antzuola", "Anue", "Añana", "Añe", "Añón de Moncayo", "Añora", "Añorbe", "Añover de Tajo", "Añover de Tormes", "Aoiz/Agoitz", "Arabayona de Mógica", "Aracena", "Arafo", "Aragüés del Puerto", "Arahal", "Arahuetes", "Araitz", "Arakaldo", "Arakil", "Arama", "Aramaio", "Aranarache/Aranaratxe", "Arancón", "Aranda de Duero", "Aranda de Moncayo", "Arándiga", "Arandilla", "Arandilla del Arroyo", "Aranga", "Aranguren", "Aranjuez", "Arano", "Arantza", "Arantzazu", "Aranzueque", "Arañuel", "Arapiles", "Aras", "Aras de los Olmos", "Arauzo de Miel", "Arauzo de Salce", "Arauzo de Torre", "Arbancón", "Arbeca", "Arbeteta", "Arbizu", "Arbo", "Arboç, L", "Arboleas", "Arbolí", "Arbúcies", "Arcas del Villar", "Arce/Artzi", "Arcediano", "Arcenillas", "Archena", "Árchez", "Archidona", "Arcicóllar", "Arco, El", "Arconada", "Arcones", "Arcos", "Arcos de Jalón", "Arcos de la Frontera", "Arcos de la Polvorosa", "Arcos de la Sierra", "Arcos de las Salinas", "Arcos, Los", "Ardales", "Ardisa", "Ardón", "Areatza", "Arellano", "Arén", "Arenal, El", "Arenales de San Gregorio", "Arenas", "Arenas de Iguña", "Arenas de San Juan", "Arenas de San Pedro", "Arenas del Rey", "Arenillas", "Arenillas de Riopisuerga", "Arens de Lledó", "Arenys de Mar", "Arenys de Munt", "Arenzana de Abajo", "Arenzana de Arriba", "Ares", "Ares del Maestrat", "Areso", "Aretxabaleta", "Arevalillo", "Arevalillo de Cega", "Arévalo", "Arévalo de la Sierra", "Argamasilla de Alba", "Argamasilla de Calatrava", "Arganda del Rey", "Arganza", "Argañín", "Argavieso", "Argecilla", "Argelaguer", "Argelita", "Argençola", "Argente", "Argentera, L", "Argentona", "Argés", "Argoños", "Arguedas", "Arguis", "Arguisuelas", "Argujillo", "Aria", "Ariany", "Aribe", "Arico", "Arija", "Ariño", "Ariza", "Arjona", "Arjonilla", "Arlanzón", "Armallones", "Armañanzas", "Armentera, L", "Armenteros", "Armilla", "Armiñón", "Armuña", "Armuña de Almanzora", "Armuña de Tajuña", "Arnedillo", "Arnedo", "Arnes", "Arnoia, A", "Arnuero", "Aroche", "Arona", "Arquillinos", "Arquillos", "Arrabalde", "Arraia-Maeztu", "Arrancacepas", "Arrankudiaga", "Arrasate/Mondragón", "Arratzu", "Arraya de Oca", "Arrazua-Ubarrundia", "Arrecife", "Arredondo", "Arres", "Arriate", "Arrieta", "Arrigorriaga", "Arroba de los Montes", "Arróniz", "Arroyo de la Encomienda", "Arroyo de la Luz", "Arroyo de las Fraguas", "Arroyo de San Serván", "Arroyo del Ojanco", "Arroyomolinos", "Arroyomolinos", "Arroyomolinos de la Vera", "Arroyomolinos de León", "Arruazu", "Arrúbal", "Arsèguel", "Artà", "Artajona", "Artana", "Artazu", "Artea", "Arteixo", "Artenara", "Artés", "Artesa de Lleida", "Artesa de Segre", "Artieda", "Artzentales", "Artziniega", "Arucas", "Arzúa", "Ascó", "Asín", "Aspa", "Aspariegos", "Asparrena", "Aspe", "Asteasu", "Astigarraga", "Astillero, El", "Astorga", "Astudillo", "Asturianos", "Atajate", "Atalaya", "Atalaya del Cañavate", "Atalaya, La", "Atanzón", "Atapuerca", "Ataquines", "Atarfe", "Ataun", "Atazar, El", "Atea", "Ateca", "Atez", "Atienza", "Atxondo", "Atzeneta dAlbaida", "Atzeneta del Maestrat", "Aulesti", "Auñón", "Auritz/Burguete", "Ausejo", "Ausejo de la Sierra", "Ausines, Los", "Autilla del Pino", "Autillo de Campos", "Autol", "Aveinte", "Avellaneda", "Avellanes i Santa Linya, Les", "Avellanosa de Muñó", "Avià", "Ávila", "Avilés", "Avinyó", "Avinyonet de Puigventós", "Avinyonet del Penedès", "Avión", "Ayala/Aiara", "Ayamonte", "Ayegui/Aiegi", "Ayerbe", "Ayllón", "Ayna", "Ayódar", "Ayoó de Vidriales", "Ayora", "Ayuela", "Azagra", "Azaila", "Azanuy-Alins", "Azara", "Azkoitia", "Azlor", "Aznalcázar", "Aznalcóllar", "Azofra", "Azpeitia", "Azuaga", "Azuara", "Azuébar", "Azuelo", "Azuqueca de Henares", "Azután", "Babilafuente", "Bacares", "Badajoz", "Badalona", "Badarán", "Bádenas", "Badia del Vallès", "Badolatosa", "Badules", "Baélls", "Baena", "Baeza", "Bagà", "Báguena", "Bagüés", "Bahabón", "Bahabón de Esgueva", "Baides", "Bailén", "Bailo", "Baiona", "Baix Pallars", "Bakaiku", "Bakio", "Balaguer", "Balazote", "Balbases, Los", "Balboa", "Balconchán", "Baldellou", "Baleira", "Balenyà", "Baliarrain", "Ballestero, El", "Ballesteros de Calatrava", "Ballobar", "Balmaseda", "Balones", "Balsa de Ves", "Balsareny", "Baltanás", "Baltar", "Banastás", "Bande", "Banyalbufar", "Banyeres de Mariola", "Banyeres del Penedès", "Banyoles", "Baña, A", "Bañares", "Bañeza, La", "Bañobárez", "Bañón", "Baños de Ebro/Mañueta", "Baños de la Encina", "Baños de Molgas", "Baños de Montemayor", "Baños de Río Tobía", "Baños de Rioja", "Baños de Tajo", "Baños de Valdearados", "Bañuelos", "Bañuelos de Bureba", "Baquerín de Campos", "Barajas de Melo", "Barakaldo", "Baralla", "Barañain", "Baraona", "Barásoain", "Barbadás", "Barbadillo", "Barbadillo de Herreros", "Barbadillo del Mercado", "Barbadillo del Pez", "Barbalos", "Barbarin", "Barbastro", "Barbate", "Barbens", "Barberà de la Conca", "Barberà del Vallès", "Bárboles", "Barbolla", "Barbués", "Barbuñales", "Barca", "Bárcabo", "Barcarrota", "Barcelona", "Bárcena de Campos", "Bárcena de Cicero", "Bárcena de Pie de Concha", "Barceo", "Barchín del Hoyo", "Barcial de la Loma", "Barcial del Barco", "Barcience", "Barco de Ávila, El", "Barco de Valdeorras, O", "Barcones", "Bardallur", "Bareyo", "Bargas", "Bargota", "Barillas", "Barjas", "Barlovento", "Baronia de Rialb, La", "Barracas", "Barrachina", "Barraco, El", "Barrado", "Barrax", "Barreiros", "Barrika", "Barrio de Muñó", "Barriopedro", "Barrios de Bureba, Los", "Barrios de Colina", "Barrios de Luna, Los", "Barrios, Los", "Barro", "Barromán", "Barruecopardo", "Barruelo de Santullán", "Barruelo del Valle", "Barrundia", "Barx", "Barxeta", "Basaburua", "Basardilla", "Basauri", "Bàscara", "Basconcillos del Tozo", "Báscones de Ojeda", "Bascuñana", "Bascuñana de San Pedro", "Bassella", "Bastida, La", "Batea", "Baterno", "Batres", "Bausen", "Bayárcal", "Bayarque", "Bayubas de Abajo", "Bayubas de Arriba", "Baza", "Baztan", "Bea", "Beade", "Beamud", "Beariz", "Beas", "Beas de Granada", "Beas de Guadix", "Beas de Segura", "Beasain", "Becedas", "Becedillas", "Beceite", "Becerreá", "Becerril de Campos", "Becerril de la Sierra", "Becilla de Valderaduey", "Bédar", "Bedia", "Bedmar y Garcíez", "Begíjar", "Begonte", "Begues", "Begur", "Beintza-Labaien", "Beire", "Beires", "Beizama", "Béjar", "Bejís", "Belalcázar", "Belascoáin", "Belauntza", "Belbimbre", "Belchite", "Beleña", "Bèlgida", "Belianes", "Belinchón", "Bell-lloc dUrgell", "Bellaguarda", "Bellcaire dEmpordà", "Bellcaire dUrgell", "Bellmunt del Priorat", "Bellmunt dUrgell", "Bello", "Bellprat", "Bellpuig", "Bellreguard", "Bellús", "Bellvei", "Bellver de Cerdanya", "Bellvís", "Belmez", "Bélmez de la Moraleda", "Belmonte", "Belmonte de Campos", "Belmonte de Gracián", "Belmonte de Miranda", "Belmonte de San José", "Belmonte de Tajo", "Belmontejo", "Belorado", "Belver de Cinca", "Belver de los Montes", "Belvís de la Jara", "Belvís de Monroy", "Bembibre", "Benabarre", "Benacazón", "Benadalid", "Benafarces", "Benafer", "Benafigos", "Benagéber", "Benaguasil", "Benahadux", "Benahavís", "Benalauría", "Benalmádena", "Benalúa", "Benalúa de las Villas", "Benalup-Casas Viejas", "Benamargosa", "Benamaurel", "Benamejí", "Benamocarra", "Benaocaz", "Benaoján", "Benarrabá", "Benasal", "Benasau", "Benasque", "Benatae", "Benavent de Segrià", "Benavente", "Benavides", "Benavites", "Benegiles", "Beneixama", "Beneixida", "Benejúzar", "Benetússer", "Benferri", "Beniarbeig", "Beniardá", "Beniarjó", "Beniarrés", "Beniatjar", "Benicarló", "Benicasim/Benicàssim", "Benicolet", "Benicull de Xúquer", "Benidoleig", "Benidorm", "Beniel", "Benifaió", "Benifairó de la Valldigna", "Benifairó de les Valls", "Benifallet", "Benifallim", "Benifato", "Beniflá", "Benigánim", "Benigembla", "Benijófar", "Benilloba", "Benillup", "Benimantell", "Benimarfull", "Benimassot", "Benimeli", "Benimodo", "Benimuslem", "Beniparrell", "Benirredrà", "Benisanó", "Benissa", "Benissanet", "Benissoda", "Benisuera", "Benitachell/Poble Nou de Benitatxell, el", "Benitagla", "Benizalón", "Benlloch", "Benquerencia", "Benquerencia de la Serena", "Bentarique", "Benuza", "Bera", "Berango", "Berantevilla", "Beranuy", "Berastegi", "Beratón", "Berbegal", "Berberana", "Berbinzana", "Berceo", "Bercero", "Berceruelo", "Bérchules", "Bercial", "Bercial de Zapardiel", "Bercianos del Páramo", "Bercianos del Real Camino", "Bercimuel", "Berdejo", "Berga", "Bergara", "Bergasa", "Bergasillas Bajera", "Berge", "Bergondo", "Beriáin", "Berja", "Berlanas, Las", "Berlanga", "Berlanga de Duero", "Berlanga del Bierzo", "Berlangas de Roa", "Bermellar", "Bermeo", "Bermillo de Sayago", "Bernardos", "Bernedo", "Berninches", "Bernuy de Porreros", "Bernuy-Zapardiel", "Berriatua", "Berrioplano/Berriobeiti", "Berriozar", "Berriz", "Berrobi", "Berrocal", "Berrocal de Huebra", "Berrocal de Salvatierra", "Berrocalejo", "Berrocalejo de Aragona", "Berrueces", "Berrueco", "Berrueco, El", "Bertizarana", "Berzocana", "Berzosa de Bureba", "Berzosa del Lozoya", "Berzosilla", "Besalú", "Bescanó", "Betancuria", "Betanzos", "Betelu", "Bétera", "Beteta", "Betxí", "Beuda", "Bezares", "Bezas", "Biar", "Bicorp", "Bidaurreta", "Bidegoian", "Biel", "Bielsa", "Bienservida", "Bienvenida", "Bierge", "Biescas", "Bigastro", "Bigues i Riells", "Bijuesca", "Bilbao", "Bimenes", "Binaced", "Binéfar", "Binissalem", "Biosca", "Biota", "Bisaurri", "Bisbal de Falset, La", "Bisbal del Penedès, La", "Bisbal dEmpordà, La", "Biscarrués", "Bisimbre", "Biure", "Biurrun-Olcoz", "Blacos", "Blanca", "Blancafort", "Blancas", "Blancos, Os", "Blanes", "Blascomillán", "Blasconuño de Matacabras", "Blascosancho", "Blázquez, Los", "Blecua y Torres", "Blesa", "Bliecos", "Boada", "Boada de Campos", "Boadella i les Escaules", "Boadilla de Rioseco", "Boadilla del Camino", "Boadilla del Monte", "Boal", "Boalo, El", "Bobadilla", "Bobadilla del Campo", "Boborás", "Boca de Huérgano", "Bocairent", "Boceguillas", "Bocigas", "Bocos de Duero", "Bodera, La", "Bodón, El", "Bodonal de la Sierra", "Boecillo", "Bogajo", "Bogarra", "Bohodón, El", "Bohonal de Ibor", "Bohoyo", "Boimorto", "Boiro", "Bola, A", "Bolaños de Calatrava", "Bolaños de Campos", "Bolbaite", "Bollullos de la Mitación", "Bollullos Par del Condado", "Bolo, O", "Boltaña", "Bolulla", "Bolvir", "Bonansa", "Bonares", "Bonastre", "Bonete", "Boniches", "Bonilla de la Sierra", "Bonillo, El", "Bonrepòs i Mirambell", "Boñar", "Boqueixón", "Boquiñeni", "Borau", "Bordalba", "Bòrdes, Es", "Bordils", "Bordón", "Borge, El", "Borges Blanques, Les", "Borges del Camp, Les", "Borja", "Borjabad", "Bormujos", "Bornos", "Borobia", "Borox", "Borrassà", "Borredà", "Borrenes", "Borriana/Burriana", "Borriol", "Bosque, El", "Bossòst", "Bot", "Botarell", "Botija", "Botorrita", "Bouza, La", "Bóveda", "Bóveda de Toro, La", "Bóveda del Río Almar", "Bovera", "Bozoó", "Brabos", "Bràfim", "Brahojos de Medina", "Brañosera", "Braojos", "Brazacorta", "Brazatortas", "Brazuelo", "Brea de Aragón", "Brea de Tajo", "Breda", "Brenes", "Breña Alta", "Breña Baja", "Bretó", "Bretocino", "Brieva", "Brieva de Cameros", "Brihuega", "Brime de Sog", "Brime de Urz", "Brincones", "Briñas", "Brión", "Briones", "Briviesca", "Bronchales", "Broto", "Brozas", "Bruc, El", "Brull, El", "Brunete", "Brunyola", "Buberos", "Bubierca", "Bubión", "Buciegas", "Budia", "Buenache de Alarcón", "Buenache de la Sierra", "Buenamadre", "Buenaventura", "Buenavista", "Buenavista de Valdavia", "Buenavista del Norte", "Buendía", "Bueña", "Bueu", "Bufali", "Bugarra", "Bugedo", "Búger", "Buitrago", "Buitrago del Lozoya", "Bujalance", "Bujalaro", "Bujaraloz", "Bularros", "Bulbuente", "Bullas", "Buniel", "Bunyola", "Buñol", "Buñuel", "Burbáguena", "Burela", "Bureta", "Burganes de Valverde", "Burgo de Ebro, El", "Burgo de Osma-Ciudad de Osma", "Burgo Ranero, El", "Burgo, El", "Burgohondo", "Burgos", "Burgui/Burgi", "Burguillos", "Burguillos de Toledo", "Burguillos del Cerro", "Burjassot", "Burlada/Burlata", "Burón", "Burujón", "Busot", "Busquístar", "Bustares", "Bustarviejo", "Buste, El", "Bustillo de Chaves", "Bustillo de la Vega", "Bustillo del Oro", "Bustillo del Páramo", "Bustillo del Páramo de Carrión", "Busto de Bureba", "Busto, El", "Busturia", "Cabacés", "Cabaco, El", "Caballar", "Cabana de Bergantiños", "Cabanabona", "Cabanas", "Cabanelles", "Cabanes", "Cabanes", "Cabanillas", "Cabanillas de la Sierra", "Cabanillas del Campo", "Cabanyes, Les", "Cabañas de Ebro", "Cabañas de la Sagra", "Cabañas de Polendos", "Cabañas de Sayago", "Cabañas de Yepes", "Cabañas del Castillo", "Cabañas Raras", "Cabañes de Esgueva", "Cabeza de Béjar, La", "Cabeza del Buey", "Cabeza del Caballo", "Cabeza la Vaca", "Cabezabellosa", "Cabezabellosa de la Calzada", "Cabezamesada", "Cabezarados", "Cabezarrubias del Puerto", "Cabezas de Alambre", "Cabezas de San Juan, Las", "Cabezas del Pozo", "Cabezas del Villar", "Cabezas Rubias", "Cabezón de Cameros", "Cabezón de la Sal", "Cabezón de la Sierra", "Cabezón de Liébana", "Cabezón de Pisuerga", "Cabezón de Valderaduey", "Cabezuela", "Cabezuela del Valle", "Cabizuela", "Cabó", "Cabolafuente", "Cabra", "Cabra de Mora", "Cabra del Camp", "Cabra del Santo Cristo", "Cabrales", "Cabranes", "Cabredo", "Cabrejas del Campo", "Cabrejas del Pinar", "Cabrera dAnoia", "Cabrera de Mar", "Cabrera, La", "Cabrerizos", "Cabrero", "Cabreros del Monte", "Cabreros del Río", "Cabrillanes", "Cabrillas", "Cabrils", "Cabuérniga", "Cacabelos", "Cáceres", "Cachorrilla", "Cacín", "Cadalso", "Cadalso de los Vidrios", "Cadaqués", "Cádiar", "Cádiz", "Cadreita", "Cadrete", "Cájar", "Cala", "Calabazas de Fuentidueña", "Calaceite", "Calaf", "Calafell", "Calahorra", "Calahorra de Boedo", "Calahorra, La", "Calamocha", "Calamonte", "Calanda", "Calañas", "Calasparra", "Calatañazor", "Calatayud", "Calatorao", "Calcena", "Caldas de Reis", "Caldearenas", "Calders", "Caldes de Malavella", "Caldes de Montbui", "Caldes dEstrac", "Calella", "Calera de León", "Calera y Chozas", "Caleruega", "Caleruela", "Calicasas", "Càlig", "Calldetenes", "Calles", "Callosa de Segura", "Callosa dEn Sarrià", "Callús", "Calmarza", "Calomarde", "Calonge", "Calonge de Segarra", "Calp", "Caltojar", "Calvarrasa de Abajo", "Calvarrasa de Arriba", "Calvià", "Calvos de Randín", "Calzada de Béjar, La", "Calzada de Calatrava", "Calzada de Don Diego", "Calzada de los Molinos", "Calzada de Oropesa", "Calzada de Valdunciel", "Calzada del Coto", "Calzadilla", "Calzadilla de los Barros", "Calzadilla de Tera", "Camaleño", "Camañas", "Camarasa", "Camarena", "Camarena de la Sierra", "Camarenilla", "Camargo", "Camarillas", "Camariñas", "Camarles", "Camarma de Esteruelas", "Camarzana de Tera", "Camas", "Cambados", "Cambil", "Cambre", "Cambrils", "Caminomorisco", "Caminreal", "Camós", "Campana, La", "Campanario", "Campanet", "Campaspero", "Campazas", "Campdevànol", "Campelles", "Campello, el", "Campezo/Kanpezu", "Campillo de Altobuey", "Campillo de Aragón", "Campillo de Aranda", "Campillo de Arenas", "Campillo de Azaba", "Campillo de Deleitosa", "Campillo de Dueñas", "Campillo de la Jara, El", "Campillo de Llerena", "Campillo de Ranas", "Campillo, El", "Campillo, El", "Campillos", "Campillos-Paravientos", "Campillos-Sierra", "Campins", "Campisábalos", "Campllong", "Campo", "Campo de Criptana", "Campo de Mirra/Camp de Mirra, el", "Campo de Peñaranda, El", "Campo de San Pedro", "Campo de Villavidel", "Campo Lameiro", "Campo Lugar", "Campo Real", "Campofrío", "Campolara", "Camponaraya", "Campoo de Enmedio", "Campoo de Yuso", "Camporredondo", "Camporrélls", "Camporrobles", "Campos", "Campos del Paraíso", "Campos del Río", "Campotéjar", "Camprodon", "Camprovín", "Camuñas", "Canal de Berdún", "Canalejas de Peñafiel", "Canalejas del Arroyo", "Canales", "Canales de la Sierra", "Canals", "Candamo", "Candasnos", "Candelaria", "Candelario", "Candeleda", "Candilichera", "Candín", "Canejan", "Canena", "Canencia", "Canet dAdri", "Canet de Mar", "Canet dEn Berenguer", "Canet lo Roig", "Canfranc", "Cangas", "Cangas de Onís", "Cangas del Narcea", "Canicosa de la Sierra", "Caniles", "Canillas de Abajo", "Canillas de Aceituno", "Canillas de Albaida", "Canillas de Esgueva", "Canillas de Río Tuerto", "Canjáyar", "Canonja, La", "Canovelles", "Cànoves i Samalús", "Canredondo", "Cantabrana", "Cantagallo", "Cantalapiedra", "Cantalejo", "Cantallops", "Cantalojas", "Cantalpino", "Cantaracillo", "Cantavieja", "Cantillana", "Cantimpalos", "Cantiveros", "Cantoria", "Canyelles", "Cañada", "Cañada de Benatanduz", "Cañada de Calatrava", "Cañada de Verich, La", "Cañada del Hoyo", "Cañada Juncosa", "Cañada Rosal", "Cañada Vellida", "Cañamaque", "Cañamares", "Cañamero", "Cáñar", "Cañas", "Cañavate, El", "Cañaveral", "Cañaveral de León", "Cañaveras", "Cañaveruelas", "Cañete", "Cañete de las Torres", "Cañete la Real", "Cañiza, A", "Cañizal", "Cañizar", "Cañizar del Olivar", "Cañizares", "Cañizo", "Capafonts", "Caparroso", "Capçanes", "Capdepera", "Capdesaso", "Capela, A", "Capella", "Capellades", "Capileira", "Capilla", "Capillas", "Capmany", "Capolat", "Carabantes", "Carabaña", "Caracena", "Caracuel de Calatrava", "Carataunas", "Caravaca de la Cruz", "Caravia", "Carazo", "Carbajales de Alba", "Carbajo", "Carbajosa de la Sagrada", "Carballeda de Avia", "Carballeda de Valdeorras", "Carballedo", "Carballiño, O", "Carballo", "Carbellino", "Carboneras", "Carboneras de Guadazaón", "Carbonero el Mayor", "Carboneros", "Carcaboso", "Carcabuey", "Carcaixent", "Cárcar", "Carcastillo", "Carcedo de Bureba", "Carcedo de Burgos", "Carcelén", "Càrcer", "Cárcheles", "Cardedeu", "Cárdenas", "Cardenete", "Cardeña", "Cardeñadijo", "Cardeñajimeno", "Cardeñosa", "Cardeñosa de Volpejera", "Cardeñuela Riopico", "Cardiel de los Montes", "Cardona", "Cardoso de la Sierra, El", "Carenas", "Cariñena", "Cariño", "Carlet", "Carlota, La", "Carme", "Carmena", "Cármenes", "Carmona", "Carmonita", "Carnota", "Carolina, La", "Carpio", "Carpio de Azaba", "Carpio de Tajo, El", "Carpio, El", "Carracedelo", "Carral", "Carranque", "Carrascal de Barregas", "Carrascal del Obispo", "Carrascal del Río", "Carrascalejo", "Carrascalejo, El", "Carrascosa", "Carrascosa de Abajo", "Carrascosa de Haro", "Carrascosa de la Sierra", "Carratraca", "Carreño", "Carrera, La", "Carrias", "Carriches", "Carrícola", "Carrión de Calatrava", "Carrión de los Céspedes", "Carrión de los Condes", "Carrizo", "Carrizosa", "Carrocera", "Cartagena", "Cartajima", "Cártama", "Cartaya", "Cartelle", "Cartes", "Carucedo", "Casa de Uceda", "Casabermeja", "Casafranca", "Casalarreina", "Casar de Cáceres", "Casar de Escalona, El", "Casar de Palomero", "Casar, El", "Casarabonela", "Casarejos", "Casares", "Casares de las Hurdes", "Casariche", "Casarrubios del Monte", "Casarrubuelos", "Casas Altas", "Casas Bajas", "Casas de Benítez", "Casas de Don Antonio", "Casas de Don Gómez", "Casas de Don Pedro", "Casas de Fernando Alonso", "Casas de Garcimolina", "Casas de Guijarro", "Casas de Haro", "Casas de Juan Núñez", "Casas de Lázaro", "Casas de los Pinos", "Casas de Millán", "Casas de Miravete", "Casas de Reina", "Casas de San Galindo", "Casas de Ves", "Casas del Castañar", "Casas del Conde, Las", "Casas del Monte", "Casas del Puerto", "Casas-Ibáñez", "Casasbuenas", "Casaseca de Campeán", "Casaseca de las Chanas", "Casasimarro", "Casasola", "Casasola de Arión", "Casatejada", "Casavieja", "Casbas de Huesca", "Cascajares de Bureba", "Cascajares de la Sierra", "Cascante", "Cascante del Río", "Cáseda", "Caseres", "Casillas", "Casillas de Coria", "Casillas de Flores", "Casinos", "Casla", "Caso", "Caspe", "Caspueñas", "Cassà de la Selva", "Casserres", "Castalla", "Castañar de Ibor", "Castañares de Rioja", "Castañeda", "Castaño del Robledo", "Cástaras", "Castejón", "Castejón", "Castejón de Alarba", "Castejón de Henares", "Castejón de las Armas", "Castejón de Monegros", "Castejón de Sos", "Castejón de Tornos", "Castejón de Valdejasa", "Castejón del Puente", "Castel de Cabra", "Castelflorite", "Castell de Cabres", "Castell de Castells", "Castell de Guadalest, el", "Castell de lAreny", "Castell de Mur", "Castell-Platja dAro", "Castell, Es", "Castellanos de Castro", "Castellanos de Moriscos", "Castellanos de Villiquera", "Castellanos de Zapardiel", "Castellar", "Castellar de la Frontera", "Castellar de la Muela", "Castellar de la Ribera", "Castellar de nHug", "Castellar de Santiago", "Castellar del Riu", "Castellar del Vallès", "Castellar, El", "Castellbell i el Vilar", "Castellbisbal", "Castellcir", "Castelldans", "Castelldefels", "Castellet i la Gornal", "Castellfollit de la Roca", "Castellfollit de Riubregós", "Castellfollit del Boix", "Castellfort", "Castellgalí", "Castellnou de Bages", "Castellnou de Seana", "Castellnovo", "Castelló de Farfanya", "Castelló de Rugat", "Castelló dEmpúries", "Castellolí", "Castellón de la Plana/Castelló de la Plana", "Castellonet de la Conquesta", "Castellote", "Castellserà", "Castellterçol", "Castellvell del Camp", "Castellví de la Marca", "Castellví de Rosanes", "Castelnou", "Castelserás", "Castielfabib", "Castiello de Jaca", "Castigaleu", "Castil de Peones", "Castil de Vela", "Castilblanco", "Castilblanco de los Arroyos", "Castildelgado", "Castilfalé", "Castilforte", "Castilfrío de la Sierra", "Castiliscar", "Castillazuelo", "Castilleja de Guzmán", "Castilleja de la Cuesta", "Castilleja del Campo", "Castilléjar", "Castillejo de Iniesta", "Castillejo de Martín Viejo", "Castillejo de Mesleón", "Castillejo de Robledo", "Castillejo-Sierra", "Castillo de Bayuela", "Castillo de Garcimuñoz", "Castillo de las Guardas, El", "Castillo de Locubín", "Castillo de Villamalefa", "Castillo-Albaráñez", "Castillonroy", "Castillonuevo", "Castilnuevo", "Castilruiz", "Castraz", "Castrejón de la Peña", "Castrejón de Trabancos", "Castrelo de Miño", "Castrelo do Val", "Castril", "Castrillo de Cabrera", "Castrillo de Don Juan", "Castrillo de Duero", "Castrillo de la Guareña", "Castrillo de la Reina", "Castrillo de la Valduerna", "Castrillo de la Vega", "Castrillo de Onielo", "Castrillo de Riopisuerga", "Castrillo de Villavega", "Castrillo del Val", "Castrillo Matajudíos", "Castrillo-Tejeriego", "Castrillón", "Castro Caldelas", "Castro de Filabres", "Castro de Fuentidueña", "Castro de Rei", "Castro del Río", "Castro-Urdiales", "Castrobol", "Castrocalbón", "Castrocontrigo", "Castrodeza", "Castrogonzalo", "Castrojeriz", "Castrojimeno", "Castromembibre", "Castromocho", "Castromonte", "Castronuevo", "Castronuevo de Esgueva", "Castronuño", "Castropodame", "Castropol", "Castroponce", "Castroserna de Abajo", "Castroserracín", "Castrotierra de Valmadrigal", "Castroverde", "Castroverde de Campos", "Castroverde de Cerrato", "Castroviejo", "Castuera", "Catadau", "Catarroja", "Catí", "Catllar, El", "Catoira", "Catral", "Caudete", "Caudete de las Fuentes", "Caudiel", "Cava", "Cavia", "Cayuela", "Cazalegas", "Cazalilla", "Cazalla de la Sierra", "Cazorla", "Cazurra", "Cea", "Cebanico", "Cebolla", "Cebrecos", "Cebreros", "Cebrones del Río", "Ceclavín", "Cedeira", "Cedillo", "Cedillo de la Torre", "Cedillo del Condado", "Cedrillas", "Cee", "Cehegín", "Ceinos de Campos", "Celada del Camino", "Celadas", "Celanova", "Cella", "Cellera de Ter, La", "Cellorigo", "Celrà", "Cendea de Olza/Oltza Zendea", "Cendejas de Enmedio", "Cendejas de la Torre", "Cenes de la Vega", "Cenicero", "Cenicientos", "Cenizate", "Cenlle", "Centelles", "Centenera", "Centenera de Andaluz", "Cepeda", "Cepeda la Mora", "Cerbón", "Cerceda", "Cercedilla", "Cercs", "Cerdà", "Cerdanyola del Vallès", "Cerdedo", "Cerdido", "Cereceda de la Sierra", "Cerecinos de Campos", "Cerecinos del Carrizal", "Cerezal de Peñahorcada", "Cerezo", "Cerezo de Abajo", "Cerezo de Arriba", "Cerezo de Río Tirón", "Cernadilla", "Cerollera, La", "Cerralbo", "Cerralbos, Los", "Cerratón de Juarros", "Cerro de Andévalo, El", "Cerro, El", "Cervantes", "Cervatos de la Cueza", "Cervelló", "Cervera", "Cervera de Buitrago", "Cervera de la Cañada", "Cervera de los Montes", "Cervera de Pisuerga", "Cervera del Llano", "Cervera del Maestre", "Cervera del Río Alhama", "Cerveruela", "Cervià de les Garrigues", "Cervià de Ter", "Cervillego de la Cruz", "Cervo", "Cespedosa de Tormes", "Cesuras", "Cetina", "Ceuta", "Ceutí", "Cevico de la Torre", "Cevico Navero", "Chagarcía Medianero", "Chalamera", "Chamartín", "Chandrexa de Queixa", "Chantada", "Chañe", "Chapinería", "Chauchina", "Checa", "Cheles", "Chella", "Chelva", "Chequilla", "Chera", "Chercos", "Chert/Xert", "Cheste", "Chía", "Chiclana de la Frontera", "Chiclana de Segura", "Chilches/Xilxes", "Chillarón de Cuenca", "Chillarón del Rey", "Chillón", "Chilluévar", "Chiloeches", "Chimeneas", "Chimillas", "Chinchilla de Monte-Aragón", "Chinchón", "Chipiona", "Chiprana", "Chirivel", "Chiva", "Chodes", "Chodos/Xodos", "Chóvar", "Chozas de Abajo", "Chozas de Canales", "Chucena", "Chueca", "Chulilla", "Chumillas", "Churriana de la Vega", "Ciadoncha", "Cidamón", "Cidones", "Ciempozuelos", "Cierva, La", "Cieza", "Cieza", "Cifuentes", "Cigales", "Cigudosa", "Ciguñuela", "Cihuela", "Cihuri", "Cijuela", "Cillán", "Cillaperlata", "Cilleros", "Cilleros de la Bastida", "Cilleruelo de Abajo", "Cilleruelo de Arriba", "Cilleruelo de San Mamés", "Cillorigo de Liébana", "Cimanes de la Vega", "Cimanes del Tejar", "Cimballa", "Cinco Olivas", "Cincovillas", "Cinctorres", "Cintruénigo", "Cipérez", "Cirat", "Cirauqui/Zirauki", "Ciria", "Ciriza/Ziritza", "Ciruelas", "Ciruelos", "Ciruelos de Cervera", "Ciruelos del Pinar", "Cirueña", "Cirujales del Río", "Cisla", "Cisneros", "Cistella", "Cistérniga", "Cistierna", "Ciudad Real", "Ciudad Rodrigo", "Ciutadella de Menorca", "Ciutadilla", "Cizur", "Clarés de Ribota", "Clariana de Cardener", "Clavijo", "Coaña", "Cóbdar", "Cobeja", "Cobeña", "Cobeta", "Cobisa", "Cobos de Cerrato", "Cobos de Fuentidueña", "Cobreros", "Coca", "Coca de Alba", "Cocentaina", "Codo", "Codoñera, La", "Codorniz", "Codos", "Codosera, La", "Cofrentes", "Cogeces de Íscar", "Cogeces del Monte", "Cogollor", "Cogollos", "Cogollos de Guadix", "Cogollos de la Vega", "Cogolludo", "Cogul, El", "Coín", "Coirós", "Colera", "Coles", "Colilla, La", "Colindres", "Coll de Nargó", "Collado", "Collado de Contreras", "Collado del Mirón", "Collado Hermoso", "Collado Mediano", "Collado Villalba", "Collazos de Boedo", "Collbató", "Colldejou", "Collsuspina", "Colmenar", "Colmenar de Montemayor", "Colmenar de Oreja", "Colmenar del Arroyo", "Colmenar Viejo", "Colmenarejo", "Colomera", "Colomers", "Colunga", "Colungo", "Coma i la Pedra, La", "Comares", "Comillas", "Cómpeta", "Conca de Dalt", "Condado de Castilnovo", "Condado de Treviño", "Condemios de Abajo", "Condemios de Arriba", "Conesa", "Confrides", "Congosto", "Congosto de Valdavia", "Congostrina", "Conil de la Frontera", "Conquista", "Conquista de la Sierra", "Consell", "Constantí", "Constantina", "Constanzana", "Consuegra", "Contamina", "Contreras", "Coomonte", "Copernal", "Copons", "Corbalán", "Corbera", "Corbera de Llobregat", "Corbera dEbre", "Corbillos de los Oteros", "Corbins", "Corçà", "Corcos", "Corcubión", "Córdoba", "Cordobilla de Lácara", "Cordovilla", "Cordovilla la Real", "Cordovín", "Corduente", "Corella", "Corera", "Coreses", "Corgo, O", "Coria", "Coria del Río", "Coripe", "Coristanco", "Cornago", "Cornellà de Llobregat", "Cornellà del Terri", "Cornudella de Montsant", "Coronada, La", "Coronil, El", "Corpa", "Corporales", "Corral de Almaguer", "Corral de Ayllón", "Corral de Calatrava", "Corral-Rubio", "Corrales de Buelna, Los", "Corrales de Duero", "Corrales del Vino", "Corrales, Los", "Corte de Peleas", "Corteconcepción", "Cortegada", "Cortegana", "Cortelazor", "Cortes", "Cortes de Aragón", "Cortes de Arenoso", "Cortes de Baza", "Cortes de la Frontera", "Cortes de Pallás", "Cortes y Graena", "Cortijos, Los", "Corullón", "Coruña del Conde", "Coruña, A", "Corvera de Asturias", "Corvera de Toranzo", "Cosa", "Coscurita", "Coslada", "Cospeito", "Costitx", "Costur", "Cosuenda", "Cotanes del Monte", "Cotes", "Cotillas", "Cotobade", "Covaleda", "Covarrubias", "Covelo", "Coves de Vinromà, les", "Cox", "Cózar", "Cozuelos de Fuentidueña", "Crecente", "Creixell", "Crémenes", "Crespià", "Crespos", "Cretas", "Crevillent", "Cristina", "Cristóbal", "Crivillén", "Cruïlles, Monells i Sant Sadurní de lHeura", "Cuacos de Yuste", "Cuadros", "Cualedro", "Cuarte de Huerva", "Cuba, La", "Cubas de la Sagra", "Cubel", "Cubelles", "Cubells", "Cubilla", "Cubillas de Cerrato", "Cubillas de los Oteros", "Cubillas de Rueda", "Cubillas de Santa Marta", "Cubillo", "Cubillo de Uceda, El", "Cubillo del Campo", "Cubillos", "Cubillos del Sil", "Cubla", "Cubo de Benavente", "Cubo de Bureba", "Cubo de Don Sancho, El", "Cubo de la Solana", "Cubo de Tierra del Vino, El", "Cucalón", "Cudillero", "Cuelgamures", "Cuéllar", "Cuenca", "Cuenca de Campos", "Cuerlas, Las", "Cuerva", "Cuervo de Sevilla, El", "Cuervo, El", "Cueva de Ágreda", "Cueva de Roa, La", "Cueva del Hierro", "Cuevas Bajas", "Cuevas de Almudén", "Cuevas de Provanco", "Cuevas de San Clemente", "Cuevas de San Marcos", "Cuevas del Almanzora", "Cuevas del Becerro", "Cuevas del Campo", "Cuevas del Valle", "Cuevas Labradas", "Culla", "Cúllar", "Cúllar Vega", "Cullera", "Culleredo", "Cumbre, La", "Cumbres de Enmedio", "Cumbres de San Bartolomé", "Cumbres Mayores", "Cunit", "Cuntis", "Curiel de Duero", "Curtis", "Cútar", "Cuzcurrita de Río Tirón", "Daganzo de Arriba", "Daimiel", "Daimús", "Dalías", "Darnius", "Daroca", "Daroca de Rioja", "Darro", "Das", "Daya Nueva", "Daya Vieja", "Deba", "Degaña", "Dehesa de Montejo", "Dehesa de Romanos", "Dehesas de Guadix", "Deifontes", "Deleitosa", "Deltebre", "Dénia", "Derio", "Descargamaría", "Desojo", "Destriana", "Dévanos", "Deyá", "Deza", "Dicastillo", "Diego del Carpio", "Diezma", "Dílar", "Dima", "Dios le Guarde", "Dodro", "Dólar", "Dolores", "Domeño", "Domingo García", "Domingo Pérez", "Don Álvaro", "Don Benito", "Donamaria", "Doneztebe/Santesteban", "Donhierro", "Donjimeno", "Donostia-San Sebastián", "Donvidas", "Doña Mencía", "Doñinos de Ledesma", "Doñinos de Salamanca", "Dos Aguas", "Dos Hermanas", "Dos Torres", "Dosbarrios", "Dosrius", "Dozón", "Driebes", "Dúdar", "Dueñas", "Duesaigües", "Dumbría", "Durango", "Dúrcal", "Durón", "Duruelo", "Duruelo de la Sierra", "Ea", "Echarri", "Écija", "Egüés", "Eibar", "Eivissa", "Ejea de los Caballeros", "Ejeme", "Ejido, El", "Ejulve", "Elantxobe", "Elburgo/Burgelu", "Elche de la Sierra", "Elche/Elx", "Elciego", "Elda", "Elduain", "Elgeta", "Elgoibar", "Elgorriaga", "Eliana, l", "Eljas", "Elorrio", "Elvillar/Bilar", "Embid", "Embid de Ariza", "Emperador", "Encina de San Silvestre", "Encina, La", "Encinacorba", "Encinas", "Encinas de Abajo", "Encinas de Arriba", "Encinas de Esgueva", "Encinas Reales", "Encinasola", "Encinasola de los Comendadores", "Encinedo", "Encinillas", "Encío", "Enciso", "Endrinal", "Enériz/Eneritz", "Enguera", "Enguídanos", "Enix", "Ènova, l", "Entrala", "Entrambasaguas", "Entrena", "Entrimo", "Entrín Bajo", "Épila", "Erandio", "Eratsun", "Ercina, La", "Ereño", "Ergoiena", "Erla", "Ermua", "Errenteria", "Errezil", "Erriberagoitia/Ribera Alta", "Errigoiti", "Erro", "Erustes", "Escacena del Campo", "Escala, L", "Escalante", "Escalona", "Escalona del Prado", "Escalonilla", "Escamilla", "Escañuela", "Escarabajosa de Cabezas", "Escariche", "Escatrón", "Escobar de Campos", "Escobar de Polendos", "Escobosa de Almazán", "Escopete", "Escorca", "Escorial, El", "Escorihuela", "Escucha", "Escurial", "Escurial de la Sierra", "Escúzar", "Esgos", "Esguevillas de Esgueva", "Eskoriatza", "Eslava", "Eslida", "Espadaña", "Espadañedo", "Espadilla", "Esparragalejo", "Esparragosa de la Serena", "Esparragosa de Lares", "Esparreguera", "Espartinas", "Esparza de Salazar/Espartza Zaraitzu", "Espeja", "Espeja de San Marcelino", "Espejo", "Espejón", "Espelúy", "Espera", "Espiel", "Espinar, El", "Espinelves", "Espino de la Orbada", "Espinosa de Cerrato", "Espinosa de Cervera", "Espinosa de Henares", "Espinosa de los Caballeros", "Espinosa de los Monteros", "Espinosa de Villagonzalo", "Espinosa del Camino", "Espinoso del Rey", "Espirdo", "Esplegares", "Espluga Calba, L", "Espluga de Francolí, L", "Esplugues de Llobregat", "Esplús", "Espolla", "Esponellà", "Esporles", "Espot", "Espronceda", "Espunyola, L", "Esquivias", "Establés", "Estada", "Estadilla", "Estamariu", "Estany, L", "Estaràs", "Estella-Lizarra", "Estellencs", "Estepa", "Estepa de San Juan", "Estépar", "Estepona", "Estercuel", "Esteribar", "Esterri dÀneu", "Esterri de Cardós", "Estivella", "Estollo", "Estopiñán del Castillo", "Estrada, A", "Estrella, La", "Estremera", "Estriégana", "Estubeny", "Etayo", "Etxalar", "Etxarri-Aranatz", "Etxauri", "Etxebarri", "Etxebarria", "Eulate", "Ezcabarte", "Ezcaray", "Ezcároz/Ezkaroze", "Ezkio-Itsaso", "Ezkurra", "Ezprogui", "Fabara", "Fabero", "Facheca", "Fago", "Falces", "Falset", "Famorca", "Fanlo", "Fanzara", "Far dEmpordà, El", "Faraján", "Faramontanos de Tábara", "Fariza", "Farlete", "Farrera", "Fasnia", "Fatarella, La", "Faura", "Favara", "Fayón", "Fayos, Los", "Febró, La", "Felanitx", "Felix", "Fene", "Férez", "Feria", "Fermoselle", "Fernán Caballero", "Fernán-Núñez", "Ferreira", "Ferreras de Abajo", "Ferreras de Arriba", "Ferreries", "Ferreruela", "Ferreruela de Huerva", "Ferrol", "Figaró-Montmany", "Fígols", "Fígols i Alinyà", "Figuera, La", "Figueres", "Figuerola del Camp", "Figueroles", "Figueruela de Arriba", "Figueruelas", "Fines", "Finestrat", "Fiñana", "Firgas", "Fiscal", "Fisterra", "Fitero", "Flaçà", "Flix", "Flores de Ávila", "Floresta, La", "Florida de Liébana", "Fogars de la Selva", "Fogars de Montclús", "Foios", "Foixà", "Folgoso de la Ribera", "Folgoso do Courel", "Folgueroles", "Fombellida", "Fombuena", "Fompedraza", "Foncea", "Fondarella", "Fondó de les Neus, el/Hondón de las Nieves", "Fondón", "Fonelas", "Fonfría", "Fonfría", "Fonollosa", "Fonsagrada, A", "Font de la Figuera, la", "Font dEn Carròs, la", "Font-rubí", "Fontanals de Cerdanya", "Fontanar", "Fontanarejo", "Fontanars dels Alforins", "Fontanilles", "Fontcoberta", "Fontellas", "Fontihoyuelo", "Fontioso", "Fontiveros", "Fonz", "Fonzaleche", "Foradada", "Foradada del Toscar", "Forallac", "Forcall", "Forcarei", "Forès", "Forfoleda", "Formentera", "Formentera del Segura", "Formiche Alto", "Fornalutx", "Fornells de la Selva", "Fornelos de Montes", "Fórnoles", "Fortaleny", "Fortanete", "Fortià", "Fortuna", "Forua", "Foz", "Foz-Calanda", "Frades", "Frades de la Sierra", "Fraga", "Frago, El", "Frailes", "Franco, El", "Frandovínez", "Franqueses del Vallès, Les", "Frasno, El", "Frechilla", "Frechilla de Almazán", "Fregenal de la Sierra", "Fregeneda, La", "Freginals", "Freila", "Fréscano", "Fresneda de Altarejos", "Fresneda de Cuéllar", "Fresneda de la Sierra", "Fresneda de la Sierra Tirón", "Fresneda, La", "Fresnedilla", "Fresnedillas de la Oliva", "Fresnedoso", "Fresnedoso de Ibor", "Fresneña", "Fresnillo de las Dueñas", "Fresno Alhándiga", "Fresno de Cantespino", "Fresno de Caracena", "Fresno de la Fuente", "Fresno de la Polvorosa", "Fresno de la Ribera", "Fresno de la Vega", "Fresno de Río Tirón", "Fresno de Rodilla", "Fresno de Sayago", "Fresno de Torote", "Fresno del Río", "Fresno el Viejo", "Fresno, El", "Frías", "Frías de Albarracín", "Friera de Valverde", "Frigiliana", "Friol", "Frómista", "Frontera", "Frontera, La", "Fruiz", "Frumales", "Fuembellida", "Fuencaliente", "Fuencaliente de la Palma", "Fuencemillán", "Fuendejalón", "Fuendetodos", "Fuenferrada", "Fuengirola", "Fuenlabrada", "Fuenlabrada de los Montes", "Fuenllana", "Fuenmayor", "Fuensaldaña", "Fuensalida", "Fuensanta", "Fuensanta de Martos", "Fuente Álamo de Murcia", "Fuente de Cantos", "Fuente de Pedro Naharro", "Fuente de Piedra", "Fuente de San Esteban, La", "Fuente de Santa Cruz", "Fuente del Arco", "Fuente del Maestre", "Fuente el Fresno", "Fuente el Olmo de Fuentidueña", "Fuente el Olmo de Íscar", "Fuente el Saúz", "Fuente el Saz de Jarama", "Fuente el Sol", "Fuente Encalada", "Fuente la Lancha", "Fuente la Reina", "Fuente Obejuna", "Fuente Palmera", "Fuente Vaqueros", "Fuente-Álamo", "Fuente-Olmedo", "Fuente-Tójar", "Fuentealbilla", "Fuentearmegil", "Fuentebureba", "Fuentecambrón", "Fuentecantos", "Fuentecén", "Fuenteguinaldo", "Fuenteheridos", "Fuentelahiguera de Albatages", "Fuentelapeña", "Fuentelcésped", "Fuentelencina", "Fuentelespino de Haro", "Fuentelespino de Moya", "Fuenteliante", "Fuentelisendo", "Fuentelmonge", "Fuentelsaz", "Fuentelsaz de Soria", "Fuentelviejo", "Fuentemolinos", "Fuentenava de Jábaga", "Fuentenebro", "Fuentenovilla", "Fuentepelayo", "Fuentepinilla", "Fuentepiñel", "Fuenterrebollo", "Fuenterroble de Salvatierra", "Fuenterrobles", "Fuentes", "Fuentes Calientes", "Fuentes Claras", "Fuentes de Andalucía", "Fuentes de Año", "Fuentes de Ayódar", "Fuentes de Béjar", "Fuentes de Carbajal", "Fuentes de Ebro", "Fuentes de Jiloca", "Fuentes de León", "Fuentes de Magaña", "Fuentes de Nava", "Fuentes de Oñoro", "Fuentes de Ropel", "Fuentes de Rubielos", "Fuentes de Valdepero", "Fuentesaúco", "Fuentesaúco de Fuentidueña", "Fuentesecas", "Fuentesoto", "Fuentespalda", "Fuentespina", "Fuentespreadas", "Fuentestrún", "Fuentidueña", "Fuentidueña de Tajo", "Fuerte del Rey", "Fuertescusa", "Fueva, La", "Fuliola, La", "Fulleda", "Funes", "Fustiñana"}));
        jRadioButton_filtradoListaChefs.setSelected(false);
        switchPanels(jPanel_listaChefsDisponibles); //Se cambia de panel
    }//GEN-LAST:event_jToggleButton_verListaClienteActionPerformed

    private void jToggleButton_bajaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_bajaClienteActionPerformed
        // TODO add your handling code here:
        jToggleButton_bajaCliente.setSelected(false); //Se reinicia el botón
        switchPanels(jPanel_eliminarPropiaCuenta); //Se cambia de panel
    }//GEN-LAST:event_jToggleButton_bajaClienteActionPerformed

    private void jToggleButton_borrarUsuarioAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_borrarUsuarioAdminActionPerformed
        // TODO add your handling code here:
        jToggleButton_borrarUsuarioAdmin.setSelected(false); //Se reinicia el botón
        switchPanels(jPanel_borrarUsuario); //Se cambia de panel

    }//GEN-LAST:event_jToggleButton_borrarUsuarioAdminActionPerformed

    private void jButton_eliminarBorrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_eliminarBorrarUsuarioActionPerformed
        // TODO add your handling code here:
        //HttpURLConnection connection = null;
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/users/delete/user"; //Dirección en Cloud
            //String urlString = "https://localhost:8080/api/users/delete/user"; //Dirección en localhost
            urlString = urlString + "?usernameOrEmail=" + jTextField_usuarioBorrar.getText();
            //      + "&password=password"; //+jPasswordField_password.getPassword().toString();
            String apiUrl = urlString; //api/http link                                    
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            if (connection.getResponseCode() != 200) {
                if (connection.getResponseCode() == 500) {
                    JOptionPane.showMessageDialog(null, "Usuario no encontrado");
                } else {
                    JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());
                }

            } else {
                //Leyendo la respuesta de la API
                StringBuilder sb = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linea;
                while ((linea = in.readLine()) != null) {
                    sb.append(linea).append("\n");
                }
                in.close();
                JOptionPane.showMessageDialog(null, sb.toString());

            }
            connection.disconnect();

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }//GEN-LAST:event_jButton_eliminarBorrarUsuarioActionPerformed

    private void jButton_volverBorrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_volverBorrarUsuarioActionPerformed
        // TODO add your handling code here:
        if (tipoUsuario == 1) {
            switchPanels(jPanel_opcionesAdmin);
        } else if (tipoUsuario == 2) {
            switchPanels(jPanel_opcionesChef);
        } else if (tipoUsuario == 3) {
            switchPanels(jPanel_opcionesCliente);
        }
        jTextField_usuarioBorrar.setText("");
    }//GEN-LAST:event_jButton_volverBorrarUsuarioActionPerformed

    private void jToggleButton_disponibilidadChefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_disponibilidadChefActionPerformed
        // TODO add your handling code here:
        jToggleButton_disponibilidadChef.setSelected(false); //Se reinicia el botón        
        jComboBox_filtroAZ.setSelectedItem("De la A a la F");
        jComboBox_poblacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Ababuj", "Abades", "Abadía", "Abadín", "Abadiño", "Abáigar", "Abajas", "Ábalos", "Abaltzisketa", "Abánades", "Abanilla", "Abanto", "Abanto y Ciérvana-Abanto Zierbena", "Abarán", "Abarca de Campos", "Abárzuza/Abartzuza", "Abaurregaina/Abaurrea Alta", "Abaurrepea/Abaurrea Baja", "Abegondo", "Abejar", "Abejuela", "Abella de la Conca", "Abengibre", "Abenójar", "Aberin", "Abertura", "Abezames", "Abia de la Obispalía", "Abia de las Torres", "Abiego", "Abizanda", "Abla", "Ablanque", "Ablitas", "Abrera", "Abrucena", "Abusejo", "Acebeda, La", "Acebedo", "Acebo", "Acebrón, El", "Acedera", "Acehúche", "Aceituna", "Acered", "Aceuchal", "Adahuesca", "Adalia", "Adamuz", "Adanero", "Adeje", "Ademuz", "Adiós", "Adobes", "Ador", "Adra", "Adrada de Haza", "Adrada de Pirón", "Adrada, La", "Adradas", "Adrados", "Adsubia", "Aduna", "Agaete", "Agallas", "Àger", "Agolada", "Agón", "Agoncillo", "Agost", "Agramunt", "Ágreda", "Agres", "Agrón", "Aguadulce", "Aguarón", "Aguas Cándidas", "Aguasal", "Aguatón", "Aguaviva", "Agudo", "Agüero", "Aguilafuente", "Aguilar de Bureba", "Aguilar de Campoo", "Aguilar de Campos", "Aguilar de Codés", "Aguilar de la Frontera", "Aguilar de Segarra", "Aguilar del Alfambra", "Aguilar del Río Alhama", "Águilas", "Aguilón", "Agüimes", "Agullana", "Agullent", "Agulo", "Ahigal", "Ahigal de los Aceiteros", "Ahigal de Villarino", "Ahillones", "Aia", "Aibar/Oibar", "Aielo de Malferit", "Aielo de Rugat", "Aiguafreda", "Aiguamúrcia", "Aiguaviva", "Aigües", "Aín", "Aínsa-Sobrarbe", "Ainzón", "Aisa", "Aitona", "Aizarnazabal", "Ajalvir", "Ajamil de Cameros", "Ajangiz", "Ajofrín", "Alacant/Alicante", "Alacón", "Aladrén", "Alaejos", "Alagón", "Alagón del Río", "Alaior", "Alájar", "Alajeró", "Alameda", "Alameda de Gardón, La", "Alameda de la Sagra", "Alameda del Valle", "Alamedilla", "Alamedilla, La", "Alamillo", "Alaminos", "Álamo, El", "Alamús, Els", "Alange", "Alanís", "Alaquàs", "Alar del Rey", "Alaraz", "Alarba", "Alarcón", "Alarilla", "Alaró", "Alàs i Cerc", "Alatoz", "Alba", "Alba de Cerrato", "Alba de Tormes", "Alba de Yeltes", "Albacete", "Albagés, L", "Albaida", "Albaida del Aljarafe", "Albal", "Albalá", "Albaladejo", "Albaladejo del Cuende", "Albalat de la Ribera", "Albalat dels Sorells", "Albalat dels Tarongers", "Albalate de Cinca", "Albalate de las Nogueras", "Albalate de Zorita", "Albalate del Arzobispo", "Albalatillo", "Albánchez", "Albanchez de Mágina", "Albanyà", "Albares", "Albarracín", "Albarreal de Tajo", "Albatana", "Albatàrrec", "Albatera", "Albelda", "Albelda de Iregua", "Albendea", "Albendiego", "Albentosa", "Alberca de Záncara, La", "Alberca, La", "Alberguería de Argañán, La", "Alberic", "Alberite", "Alberite de San Juan", "Albero Alto", "Albero Bajo", "Alberuela de Tubo", "Albesa", "Albeta", "Albi, L", "Albillos", "Albinyana", "Albiol, L", "Albiztur", "Albocàsser", "Alboloduy", "Albolote", "Albondón", "Albons", "Alborache", "Alboraya", "Alborea", "Alborge", "Albornos", "Albox", "Albudeite", "Albuera, La", "Albuixech", "Albuñán", "Albuñol", "Albuñuelas", "Alburquerque", "Alcabón", "Alcadozo", "Alcaine", "Alcalá de Ebro", "Alcalá de Guadaíra", "Alcalá de Gurrea", "Alcalá de Henares", "Alcalá de la Selva", "Alcalá de la Vega", "Alcalá de los Gazules", "Alcalá de Moncayo", "Alcalà de Xivert", "Alcalá del Júcar", "Alcalá del Obispo", "Alcalá del Río", "Alcalá del Valle", "Alcalá la Real", "Alcalalí", "Alcampell", "Alcanadre", "Alcanar", "Alcanó", "Alcántara", "Alcantarilla", "Alcàntera de Xúquer", "Alcantud", "Alcañices", "Alcañiz", "Alcañizo", "Alcaracejos", "Alcaraz", "Alcarràs", "Alcàsser", "Alcaucín", "Alcaudete", "Alcaudete de la Jara", "Alcázar de San Juan", "Alcázar del Rey", "Alcazarén", "Alcázares, Los", "Alcoba", "Alcobendas", "Alcocer", "Alcocer de Planes", "Alcocero de Mola", "Alcohujate", "Alcolea", "Alcolea de Calatrava", "Alcolea de Cinca", "Alcolea de las Peñas", "Alcolea de Tajo", "Alcolea del Pinar", "Alcolea del Río", "Alcoleja", "Alcoletge", "Alcollarín", "Alconaba", "Alconada", "Alconada de Maderuelo", "Alconchel", "Alconchel de Ariza", "Alconchel de la Estrella", "Alconera", "Alcóntar", "Alcora, l", "Alcorcón", "Alcorisa", "Alcoroches", "Alcover", "Alcoy/Alcoi", "Alcubierre", "Alcubilla de Avellaneda", "Alcubilla de las Peñas", "Alcubilla de Nogales", "Alcubillas", "Alcublas", "Alcúdia", "Alcúdia de Crespins, l", "Alcudia de Monteagud", "Alcudia de Veo", "Alcúdia, l", "Alcuéscar", "Aldaia", "Aldea de San Miguel", "Aldea de San Nicolás, La", "Aldea del Cano", "Aldea del Fresno", "Aldea del Obispo", "Aldea del Obispo, La", "Aldea del Rey", "Aldea en Cabo", "Aldea Real", "Aldea, L", "Aldeacentenera", "Aldeacipreste", "Aldeadávila de la Ribera", "Aldealafuente", "Aldealcorvo", "Aldealengua", "Aldealengua de Pedraza", "Aldealengua de Santa María", "Aldealices", "Aldealpozo", "Aldealseñor", "Aldeamayor de San Martín", "Aldeanueva de Barbarroya", "Aldeanueva de Ebro", "Aldeanueva de Figueroa", "Aldeanueva de Guadalajara", "Aldeanueva de la Serrezuela", "Aldeanueva de la Sierra", "Aldeanueva de la Vera", "Aldeanueva de San Bartolomé", "Aldeanueva de Santa Cruz", "Aldeanueva del Camino", "Aldeanueva del Codonal", "Aldeaquemada", "Aldearrodrigo", "Aldearrubia", "Aldeaseca", "Aldeaseca de Alba", "Aldeaseca de la Frontera", "Aldeasoña", "Aldeatejada", "Aldeavieja de Tormes", "Aldehorno", "Aldehuela de Jerte", "Aldehuela de la Bóveda", "Aldehuela de Liestos", "Aldehuela de Periáñez", "Aldehuela de Yeltes", "Aldehuela del Codonal", "Aldehuela, La", "Aldehuelas, Las", "Aldeire", "Aldeonte", "Aldover", "Aledo", "Alegia", "Alegría-Dulantzi", "Aleixar, L", "Alella", "Alentisque", "Alerre", "Alesanco", "Alesón", "Alfacar", "Alfafar", "Alfafara", "Alfajarín", "Alfambra", "Alfamén", "Alfántega", "Alfara de Carles", "Alfara de la Baronia", "Alfara del Patriarca", "Alfaraz de Sayago", "Alfarnate", "Alfarnatejo", "Alfaro", "Alfarp", "Alfarràs", "Alfarrasí", "Alfàs del Pi, l", "Alfauir", "Alfés", "Alfondeguilla", "Alforja", "Alforque", "Alfoz", "Alfoz de Bricia", "Alfoz de Lloredo", "Alfoz de Quintanadueñas", "Alfoz de Santa Gadea", "Algaba, La", "Algadefe", "Algaida", "Algámitas", "Algar", "Algar de Mesa", "Algar de Palancia", "Algarinejo", "Algarra", "Algarrobo", "Algatocín", "Algeciras", "Algemesí", "Algerri", "Algete", "Algimia de Alfara", "Algimia de Almonacid", "Alginet", "Algodonales", "Algodre", "Algora", "Algorfa", "Alguaire", "Alguazas", "Algueña", "Alhabia", "Alhama de Almería", "Alhama de Aragón", "Alhama de Granada", "Alhama de Murcia", "Alhambra", "Alhaurín de la Torre", "Alhaurín el Grande", "Alhendín", "Alhóndiga", "Alía", "Aliaga", "Aliaguilla", "Alicún", "Alicún de Ortega", "Alija del Infantado", "Alins", "Alió", "Alique", "Aliseda", "Aliud", "Aljaraque", "Aljucén", "Alkiza", "Allande", "Allariz", "Allepuz", "Aller", "Allín/Allin", "Allo", "Alloza", "Allueva", "Almacelles", "Almáchar", "Almadén", "Almadén de la Plata", "Almadenejos", "Almadrones", "Almagro", "Almajano", "Almaluez", "Almansa", "Almanza", "Almaraz", "Almaraz de Duero", "Almarcha, La", "Almargen", "Almarza", "Almarza de Cameros", "Almàssera", "Almatret", "Almazán", "Almazora/Almassora", "Almazul", "Almedíjar", "Almedina", "Almedinilla", "Almegíjar", "Almeida de Sayago", "Almenar", "Almenar de Soria", "Almenara", "Almenara de Adaja", "Almenara de Tormes", "Almendra", "Almendral", "Almendral de la Cañada", "Almendralejo", "Almendro, El", "Almendros", "Almensilla", "Almería", "Almiserà", "Almochuel", "Almócita", "Almodóvar del Campo", "Almodóvar del Pinar", "Almodóvar del Río", "Almogía", "Almoguera", "Almohaja", "Almoharín", "Almoines", "Almolda, La", "Almonacid de la Cuba", "Almonacid de la Sierra", "Almonacid de Toledo", "Almonacid de Zorita", "Almonacid del Marquesado", "Almonaster la Real", "Almonte", "Almoradí", "Almorox", "Almoster", "Almudaina", "Almudévar", "Almunia de Doña Godina, La", "Almunia de San Juan", "Almuniente", "Almuñécar", "Almuradiel", "Almussafes", "Alobras", "Alocén", "Alonsotegi", "Álora", "Alòs de Balaguer", "Alosno", "Alovera", "Alozaina", "Alp", "Alpandeire", "Alpanseque", "Alpartir", "Alpedrete", "Alpens", "Alpeñés", "Alpera", "Alpicat", "Alpuente", "Alpujarra de la Sierra", "Alqueria dAsnar, l", "Alqueria de la Comtessa, l", "Alquerías del Niño Perdido", "Alquézar", "Alquife", "Alsodux", "Alt Àneu", "Altable", "Altafulla", "Altarejos", "Altea", "Altorricón", "Altos, Los", "Altsasu/Alsasua", "Altura", "Altzaga", "Altzo", "Alustante", "Alzira", "Amavida", "Amayuelas de Arriba", "Ambel", "Ambite", "Amer", "Ames", "Améscoa Baja", "Ametlla de Mar, L", "Ametlla del Vallès, L", "Ameyugo", "Amezketa", "Amieva", "Amoeiro", "Amorebieta-Etxano", "Amoroto", "Ampolla, L", "Amposta", "Ampudia", "Ampuero", "Amurrio", "Amusco", "Amusquillo", "Anadón", "Anaya", "Anaya de Alba", "Anchuelo", "Anchuras", "Ancín/Antzin", "Andavías", "Andilla", "Andoain", "Andorra", "Andosilla", "Andratx", "Andújar", "Anento", "Anglès", "Anglesola", "Angón", "Anguciana", "Angüés", "Anguiano", "Anguita", "Anguix", "Anievas", "Aniñón", "Anna", "Anoeta", "Anquela del Ducado", "Anquela del Pedregal", "Ansó", "Ansoáin/Antsoain", "Antas", "Antas de Ulla", "Antella", "Antequera", "Antigua", "Antigua, La", "Antigüedad", "Antillón", "Antzuola", "Anue", "Añana", "Añe", "Añón de Moncayo", "Añora", "Añorbe", "Añover de Tajo", "Añover de Tormes", "Aoiz/Agoitz", "Arabayona de Mógica", "Aracena", "Arafo", "Aragüés del Puerto", "Arahal", "Arahuetes", "Araitz", "Arakaldo", "Arakil", "Arama", "Aramaio", "Aranarache/Aranaratxe", "Arancón", "Aranda de Duero", "Aranda de Moncayo", "Arándiga", "Arandilla", "Arandilla del Arroyo", "Aranga", "Aranguren", "Aranjuez", "Arano", "Arantza", "Arantzazu", "Aranzueque", "Arañuel", "Arapiles", "Aras", "Aras de los Olmos", "Arauzo de Miel", "Arauzo de Salce", "Arauzo de Torre", "Arbancón", "Arbeca", "Arbeteta", "Arbizu", "Arbo", "Arboç, L", "Arboleas", "Arbolí", "Arbúcies", "Arcas del Villar", "Arce/Artzi", "Arcediano", "Arcenillas", "Archena", "Árchez", "Archidona", "Arcicóllar", "Arco, El", "Arconada", "Arcones", "Arcos", "Arcos de Jalón", "Arcos de la Frontera", "Arcos de la Polvorosa", "Arcos de la Sierra", "Arcos de las Salinas", "Arcos, Los", "Ardales", "Ardisa", "Ardón", "Areatza", "Arellano", "Arén", "Arenal, El", "Arenales de San Gregorio", "Arenas", "Arenas de Iguña", "Arenas de San Juan", "Arenas de San Pedro", "Arenas del Rey", "Arenillas", "Arenillas de Riopisuerga", "Arens de Lledó", "Arenys de Mar", "Arenys de Munt", "Arenzana de Abajo", "Arenzana de Arriba", "Ares", "Ares del Maestrat", "Areso", "Aretxabaleta", "Arevalillo", "Arevalillo de Cega", "Arévalo", "Arévalo de la Sierra", "Argamasilla de Alba", "Argamasilla de Calatrava", "Arganda del Rey", "Arganza", "Argañín", "Argavieso", "Argecilla", "Argelaguer", "Argelita", "Argençola", "Argente", "Argentera, L", "Argentona", "Argés", "Argoños", "Arguedas", "Arguis", "Arguisuelas", "Argujillo", "Aria", "Ariany", "Aribe", "Arico", "Arija", "Ariño", "Ariza", "Arjona", "Arjonilla", "Arlanzón", "Armallones", "Armañanzas", "Armentera, L", "Armenteros", "Armilla", "Armiñón", "Armuña", "Armuña de Almanzora", "Armuña de Tajuña", "Arnedillo", "Arnedo", "Arnes", "Arnoia, A", "Arnuero", "Aroche", "Arona", "Arquillinos", "Arquillos", "Arrabalde", "Arraia-Maeztu", "Arrancacepas", "Arrankudiaga", "Arrasate/Mondragón", "Arratzu", "Arraya de Oca", "Arrazua-Ubarrundia", "Arrecife", "Arredondo", "Arres", "Arriate", "Arrieta", "Arrigorriaga", "Arroba de los Montes", "Arróniz", "Arroyo de la Encomienda", "Arroyo de la Luz", "Arroyo de las Fraguas", "Arroyo de San Serván", "Arroyo del Ojanco", "Arroyomolinos", "Arroyomolinos", "Arroyomolinos de la Vera", "Arroyomolinos de León", "Arruazu", "Arrúbal", "Arsèguel", "Artà", "Artajona", "Artana", "Artazu", "Artea", "Arteixo", "Artenara", "Artés", "Artesa de Lleida", "Artesa de Segre", "Artieda", "Artzentales", "Artziniega", "Arucas", "Arzúa", "Ascó", "Asín", "Aspa", "Aspariegos", "Asparrena", "Aspe", "Asteasu", "Astigarraga", "Astillero, El", "Astorga", "Astudillo", "Asturianos", "Atajate", "Atalaya", "Atalaya del Cañavate", "Atalaya, La", "Atanzón", "Atapuerca", "Ataquines", "Atarfe", "Ataun", "Atazar, El", "Atea", "Ateca", "Atez", "Atienza", "Atxondo", "Atzeneta dAlbaida", "Atzeneta del Maestrat", "Aulesti", "Auñón", "Auritz/Burguete", "Ausejo", "Ausejo de la Sierra", "Ausines, Los", "Autilla del Pino", "Autillo de Campos", "Autol", "Aveinte", "Avellaneda", "Avellanes i Santa Linya, Les", "Avellanosa de Muñó", "Avià", "Ávila", "Avilés", "Avinyó", "Avinyonet de Puigventós", "Avinyonet del Penedès", "Avión", "Ayala/Aiara", "Ayamonte", "Ayegui/Aiegi", "Ayerbe", "Ayllón", "Ayna", "Ayódar", "Ayoó de Vidriales", "Ayora", "Ayuela", "Azagra", "Azaila", "Azanuy-Alins", "Azara", "Azkoitia", "Azlor", "Aznalcázar", "Aznalcóllar", "Azofra", "Azpeitia", "Azuaga", "Azuara", "Azuébar", "Azuelo", "Azuqueca de Henares", "Azután", "Babilafuente", "Bacares", "Badajoz", "Badalona", "Badarán", "Bádenas", "Badia del Vallès", "Badolatosa", "Badules", "Baélls", "Baena", "Baeza", "Bagà", "Báguena", "Bagüés", "Bahabón", "Bahabón de Esgueva", "Baides", "Bailén", "Bailo", "Baiona", "Baix Pallars", "Bakaiku", "Bakio", "Balaguer", "Balazote", "Balbases, Los", "Balboa", "Balconchán", "Baldellou", "Baleira", "Balenyà", "Baliarrain", "Ballestero, El", "Ballesteros de Calatrava", "Ballobar", "Balmaseda", "Balones", "Balsa de Ves", "Balsareny", "Baltanás", "Baltar", "Banastás", "Bande", "Banyalbufar", "Banyeres de Mariola", "Banyeres del Penedès", "Banyoles", "Baña, A", "Bañares", "Bañeza, La", "Bañobárez", "Bañón", "Baños de Ebro/Mañueta", "Baños de la Encina", "Baños de Molgas", "Baños de Montemayor", "Baños de Río Tobía", "Baños de Rioja", "Baños de Tajo", "Baños de Valdearados", "Bañuelos", "Bañuelos de Bureba", "Baquerín de Campos", "Barajas de Melo", "Barakaldo", "Baralla", "Barañain", "Baraona", "Barásoain", "Barbadás", "Barbadillo", "Barbadillo de Herreros", "Barbadillo del Mercado", "Barbadillo del Pez", "Barbalos", "Barbarin", "Barbastro", "Barbate", "Barbens", "Barberà de la Conca", "Barberà del Vallès", "Bárboles", "Barbolla", "Barbués", "Barbuñales", "Barca", "Bárcabo", "Barcarrota", "Barcelona", "Bárcena de Campos", "Bárcena de Cicero", "Bárcena de Pie de Concha", "Barceo", "Barchín del Hoyo", "Barcial de la Loma", "Barcial del Barco", "Barcience", "Barco de Ávila, El", "Barco de Valdeorras, O", "Barcones", "Bardallur", "Bareyo", "Bargas", "Bargota", "Barillas", "Barjas", "Barlovento", "Baronia de Rialb, La", "Barracas", "Barrachina", "Barraco, El", "Barrado", "Barrax", "Barreiros", "Barrika", "Barrio de Muñó", "Barriopedro", "Barrios de Bureba, Los", "Barrios de Colina", "Barrios de Luna, Los", "Barrios, Los", "Barro", "Barromán", "Barruecopardo", "Barruelo de Santullán", "Barruelo del Valle", "Barrundia", "Barx", "Barxeta", "Basaburua", "Basardilla", "Basauri", "Bàscara", "Basconcillos del Tozo", "Báscones de Ojeda", "Bascuñana", "Bascuñana de San Pedro", "Bassella", "Bastida, La", "Batea", "Baterno", "Batres", "Bausen", "Bayárcal", "Bayarque", "Bayubas de Abajo", "Bayubas de Arriba", "Baza", "Baztan", "Bea", "Beade", "Beamud", "Beariz", "Beas", "Beas de Granada", "Beas de Guadix", "Beas de Segura", "Beasain", "Becedas", "Becedillas", "Beceite", "Becerreá", "Becerril de Campos", "Becerril de la Sierra", "Becilla de Valderaduey", "Bédar", "Bedia", "Bedmar y Garcíez", "Begíjar", "Begonte", "Begues", "Begur", "Beintza-Labaien", "Beire", "Beires", "Beizama", "Béjar", "Bejís", "Belalcázar", "Belascoáin", "Belauntza", "Belbimbre", "Belchite", "Beleña", "Bèlgida", "Belianes", "Belinchón", "Bell-lloc dUrgell", "Bellaguarda", "Bellcaire dEmpordà", "Bellcaire dUrgell", "Bellmunt del Priorat", "Bellmunt dUrgell", "Bello", "Bellprat", "Bellpuig", "Bellreguard", "Bellús", "Bellvei", "Bellver de Cerdanya", "Bellvís", "Belmez", "Bélmez de la Moraleda", "Belmonte", "Belmonte de Campos", "Belmonte de Gracián", "Belmonte de Miranda", "Belmonte de San José", "Belmonte de Tajo", "Belmontejo", "Belorado", "Belver de Cinca", "Belver de los Montes", "Belvís de la Jara", "Belvís de Monroy", "Bembibre", "Benabarre", "Benacazón", "Benadalid", "Benafarces", "Benafer", "Benafigos", "Benagéber", "Benaguasil", "Benahadux", "Benahavís", "Benalauría", "Benalmádena", "Benalúa", "Benalúa de las Villas", "Benalup-Casas Viejas", "Benamargosa", "Benamaurel", "Benamejí", "Benamocarra", "Benaocaz", "Benaoján", "Benarrabá", "Benasal", "Benasau", "Benasque", "Benatae", "Benavent de Segrià", "Benavente", "Benavides", "Benavites", "Benegiles", "Beneixama", "Beneixida", "Benejúzar", "Benetússer", "Benferri", "Beniarbeig", "Beniardá", "Beniarjó", "Beniarrés", "Beniatjar", "Benicarló", "Benicasim/Benicàssim", "Benicolet", "Benicull de Xúquer", "Benidoleig", "Benidorm", "Beniel", "Benifaió", "Benifairó de la Valldigna", "Benifairó de les Valls", "Benifallet", "Benifallim", "Benifato", "Beniflá", "Benigánim", "Benigembla", "Benijófar", "Benilloba", "Benillup", "Benimantell", "Benimarfull", "Benimassot", "Benimeli", "Benimodo", "Benimuslem", "Beniparrell", "Benirredrà", "Benisanó", "Benissa", "Benissanet", "Benissoda", "Benisuera", "Benitachell/Poble Nou de Benitatxell, el", "Benitagla", "Benizalón", "Benlloch", "Benquerencia", "Benquerencia de la Serena", "Bentarique", "Benuza", "Bera", "Berango", "Berantevilla", "Beranuy", "Berastegi", "Beratón", "Berbegal", "Berberana", "Berbinzana", "Berceo", "Bercero", "Berceruelo", "Bérchules", "Bercial", "Bercial de Zapardiel", "Bercianos del Páramo", "Bercianos del Real Camino", "Bercimuel", "Berdejo", "Berga", "Bergara", "Bergasa", "Bergasillas Bajera", "Berge", "Bergondo", "Beriáin", "Berja", "Berlanas, Las", "Berlanga", "Berlanga de Duero", "Berlanga del Bierzo", "Berlangas de Roa", "Bermellar", "Bermeo", "Bermillo de Sayago", "Bernardos", "Bernedo", "Berninches", "Bernuy de Porreros", "Bernuy-Zapardiel", "Berriatua", "Berrioplano/Berriobeiti", "Berriozar", "Berriz", "Berrobi", "Berrocal", "Berrocal de Huebra", "Berrocal de Salvatierra", "Berrocalejo", "Berrocalejo de Aragona", "Berrueces", "Berrueco", "Berrueco, El", "Bertizarana", "Berzocana", "Berzosa de Bureba", "Berzosa del Lozoya", "Berzosilla", "Besalú", "Bescanó", "Betancuria", "Betanzos", "Betelu", "Bétera", "Beteta", "Betxí", "Beuda", "Bezares", "Bezas", "Biar", "Bicorp", "Bidaurreta", "Bidegoian", "Biel", "Bielsa", "Bienservida", "Bienvenida", "Bierge", "Biescas", "Bigastro", "Bigues i Riells", "Bijuesca", "Bilbao", "Bimenes", "Binaced", "Binéfar", "Binissalem", "Biosca", "Biota", "Bisaurri", "Bisbal de Falset, La", "Bisbal del Penedès, La", "Bisbal dEmpordà, La", "Biscarrués", "Bisimbre", "Biure", "Biurrun-Olcoz", "Blacos", "Blanca", "Blancafort", "Blancas", "Blancos, Os", "Blanes", "Blascomillán", "Blasconuño de Matacabras", "Blascosancho", "Blázquez, Los", "Blecua y Torres", "Blesa", "Bliecos", "Boada", "Boada de Campos", "Boadella i les Escaules", "Boadilla de Rioseco", "Boadilla del Camino", "Boadilla del Monte", "Boal", "Boalo, El", "Bobadilla", "Bobadilla del Campo", "Boborás", "Boca de Huérgano", "Bocairent", "Boceguillas", "Bocigas", "Bocos de Duero", "Bodera, La", "Bodón, El", "Bodonal de la Sierra", "Boecillo", "Bogajo", "Bogarra", "Bohodón, El", "Bohonal de Ibor", "Bohoyo", "Boimorto", "Boiro", "Bola, A", "Bolaños de Calatrava", "Bolaños de Campos", "Bolbaite", "Bollullos de la Mitación", "Bollullos Par del Condado", "Bolo, O", "Boltaña", "Bolulla", "Bolvir", "Bonansa", "Bonares", "Bonastre", "Bonete", "Boniches", "Bonilla de la Sierra", "Bonillo, El", "Bonrepòs i Mirambell", "Boñar", "Boqueixón", "Boquiñeni", "Borau", "Bordalba", "Bòrdes, Es", "Bordils", "Bordón", "Borge, El", "Borges Blanques, Les", "Borges del Camp, Les", "Borja", "Borjabad", "Bormujos", "Bornos", "Borobia", "Borox", "Borrassà", "Borredà", "Borrenes", "Borriana/Burriana", "Borriol", "Bosque, El", "Bossòst", "Bot", "Botarell", "Botija", "Botorrita", "Bouza, La", "Bóveda", "Bóveda de Toro, La", "Bóveda del Río Almar", "Bovera", "Bozoó", "Brabos", "Bràfim", "Brahojos de Medina", "Brañosera", "Braojos", "Brazacorta", "Brazatortas", "Brazuelo", "Brea de Aragón", "Brea de Tajo", "Breda", "Brenes", "Breña Alta", "Breña Baja", "Bretó", "Bretocino", "Brieva", "Brieva de Cameros", "Brihuega", "Brime de Sog", "Brime de Urz", "Brincones", "Briñas", "Brión", "Briones", "Briviesca", "Bronchales", "Broto", "Brozas", "Bruc, El", "Brull, El", "Brunete", "Brunyola", "Buberos", "Bubierca", "Bubión", "Buciegas", "Budia", "Buenache de Alarcón", "Buenache de la Sierra", "Buenamadre", "Buenaventura", "Buenavista", "Buenavista de Valdavia", "Buenavista del Norte", "Buendía", "Bueña", "Bueu", "Bufali", "Bugarra", "Bugedo", "Búger", "Buitrago", "Buitrago del Lozoya", "Bujalance", "Bujalaro", "Bujaraloz", "Bularros", "Bulbuente", "Bullas", "Buniel", "Bunyola", "Buñol", "Buñuel", "Burbáguena", "Burela", "Bureta", "Burganes de Valverde", "Burgo de Ebro, El", "Burgo de Osma-Ciudad de Osma", "Burgo Ranero, El", "Burgo, El", "Burgohondo", "Burgos", "Burgui/Burgi", "Burguillos", "Burguillos de Toledo", "Burguillos del Cerro", "Burjassot", "Burlada/Burlata", "Burón", "Burujón", "Busot", "Busquístar", "Bustares", "Bustarviejo", "Buste, El", "Bustillo de Chaves", "Bustillo de la Vega", "Bustillo del Oro", "Bustillo del Páramo", "Bustillo del Páramo de Carrión", "Busto de Bureba", "Busto, El", "Busturia", "Cabacés", "Cabaco, El", "Caballar", "Cabana de Bergantiños", "Cabanabona", "Cabanas", "Cabanelles", "Cabanes", "Cabanes", "Cabanillas", "Cabanillas de la Sierra", "Cabanillas del Campo", "Cabanyes, Les", "Cabañas de Ebro", "Cabañas de la Sagra", "Cabañas de Polendos", "Cabañas de Sayago", "Cabañas de Yepes", "Cabañas del Castillo", "Cabañas Raras", "Cabañes de Esgueva", "Cabeza de Béjar, La", "Cabeza del Buey", "Cabeza del Caballo", "Cabeza la Vaca", "Cabezabellosa", "Cabezabellosa de la Calzada", "Cabezamesada", "Cabezarados", "Cabezarrubias del Puerto", "Cabezas de Alambre", "Cabezas de San Juan, Las", "Cabezas del Pozo", "Cabezas del Villar", "Cabezas Rubias", "Cabezón de Cameros", "Cabezón de la Sal", "Cabezón de la Sierra", "Cabezón de Liébana", "Cabezón de Pisuerga", "Cabezón de Valderaduey", "Cabezuela", "Cabezuela del Valle", "Cabizuela", "Cabó", "Cabolafuente", "Cabra", "Cabra de Mora", "Cabra del Camp", "Cabra del Santo Cristo", "Cabrales", "Cabranes", "Cabredo", "Cabrejas del Campo", "Cabrejas del Pinar", "Cabrera dAnoia", "Cabrera de Mar", "Cabrera, La", "Cabrerizos", "Cabrero", "Cabreros del Monte", "Cabreros del Río", "Cabrillanes", "Cabrillas", "Cabrils", "Cabuérniga", "Cacabelos", "Cáceres", "Cachorrilla", "Cacín", "Cadalso", "Cadalso de los Vidrios", "Cadaqués", "Cádiar", "Cádiz", "Cadreita", "Cadrete", "Cájar", "Cala", "Calabazas de Fuentidueña", "Calaceite", "Calaf", "Calafell", "Calahorra", "Calahorra de Boedo", "Calahorra, La", "Calamocha", "Calamonte", "Calanda", "Calañas", "Calasparra", "Calatañazor", "Calatayud", "Calatorao", "Calcena", "Caldas de Reis", "Caldearenas", "Calders", "Caldes de Malavella", "Caldes de Montbui", "Caldes dEstrac", "Calella", "Calera de León", "Calera y Chozas", "Caleruega", "Caleruela", "Calicasas", "Càlig", "Calldetenes", "Calles", "Callosa de Segura", "Callosa dEn Sarrià", "Callús", "Calmarza", "Calomarde", "Calonge", "Calonge de Segarra", "Calp", "Caltojar", "Calvarrasa de Abajo", "Calvarrasa de Arriba", "Calvià", "Calvos de Randín", "Calzada de Béjar, La", "Calzada de Calatrava", "Calzada de Don Diego", "Calzada de los Molinos", "Calzada de Oropesa", "Calzada de Valdunciel", "Calzada del Coto", "Calzadilla", "Calzadilla de los Barros", "Calzadilla de Tera", "Camaleño", "Camañas", "Camarasa", "Camarena", "Camarena de la Sierra", "Camarenilla", "Camargo", "Camarillas", "Camariñas", "Camarles", "Camarma de Esteruelas", "Camarzana de Tera", "Camas", "Cambados", "Cambil", "Cambre", "Cambrils", "Caminomorisco", "Caminreal", "Camós", "Campana, La", "Campanario", "Campanet", "Campaspero", "Campazas", "Campdevànol", "Campelles", "Campello, el", "Campezo/Kanpezu", "Campillo de Altobuey", "Campillo de Aragón", "Campillo de Aranda", "Campillo de Arenas", "Campillo de Azaba", "Campillo de Deleitosa", "Campillo de Dueñas", "Campillo de la Jara, El", "Campillo de Llerena", "Campillo de Ranas", "Campillo, El", "Campillo, El", "Campillos", "Campillos-Paravientos", "Campillos-Sierra", "Campins", "Campisábalos", "Campllong", "Campo", "Campo de Criptana", "Campo de Mirra/Camp de Mirra, el", "Campo de Peñaranda, El", "Campo de San Pedro", "Campo de Villavidel", "Campo Lameiro", "Campo Lugar", "Campo Real", "Campofrío", "Campolara", "Camponaraya", "Campoo de Enmedio", "Campoo de Yuso", "Camporredondo", "Camporrélls", "Camporrobles", "Campos", "Campos del Paraíso", "Campos del Río", "Campotéjar", "Camprodon", "Camprovín", "Camuñas", "Canal de Berdún", "Canalejas de Peñafiel", "Canalejas del Arroyo", "Canales", "Canales de la Sierra", "Canals", "Candamo", "Candasnos", "Candelaria", "Candelario", "Candeleda", "Candilichera", "Candín", "Canejan", "Canena", "Canencia", "Canet dAdri", "Canet de Mar", "Canet dEn Berenguer", "Canet lo Roig", "Canfranc", "Cangas", "Cangas de Onís", "Cangas del Narcea", "Canicosa de la Sierra", "Caniles", "Canillas de Abajo", "Canillas de Aceituno", "Canillas de Albaida", "Canillas de Esgueva", "Canillas de Río Tuerto", "Canjáyar", "Canonja, La", "Canovelles", "Cànoves i Samalús", "Canredondo", "Cantabrana", "Cantagallo", "Cantalapiedra", "Cantalejo", "Cantallops", "Cantalojas", "Cantalpino", "Cantaracillo", "Cantavieja", "Cantillana", "Cantimpalos", "Cantiveros", "Cantoria", "Canyelles", "Cañada", "Cañada de Benatanduz", "Cañada de Calatrava", "Cañada de Verich, La", "Cañada del Hoyo", "Cañada Juncosa", "Cañada Rosal", "Cañada Vellida", "Cañamaque", "Cañamares", "Cañamero", "Cáñar", "Cañas", "Cañavate, El", "Cañaveral", "Cañaveral de León", "Cañaveras", "Cañaveruelas", "Cañete", "Cañete de las Torres", "Cañete la Real", "Cañiza, A", "Cañizal", "Cañizar", "Cañizar del Olivar", "Cañizares", "Cañizo", "Capafonts", "Caparroso", "Capçanes", "Capdepera", "Capdesaso", "Capela, A", "Capella", "Capellades", "Capileira", "Capilla", "Capillas", "Capmany", "Capolat", "Carabantes", "Carabaña", "Caracena", "Caracuel de Calatrava", "Carataunas", "Caravaca de la Cruz", "Caravia", "Carazo", "Carbajales de Alba", "Carbajo", "Carbajosa de la Sagrada", "Carballeda de Avia", "Carballeda de Valdeorras", "Carballedo", "Carballiño, O", "Carballo", "Carbellino", "Carboneras", "Carboneras de Guadazaón", "Carbonero el Mayor", "Carboneros", "Carcaboso", "Carcabuey", "Carcaixent", "Cárcar", "Carcastillo", "Carcedo de Bureba", "Carcedo de Burgos", "Carcelén", "Càrcer", "Cárcheles", "Cardedeu", "Cárdenas", "Cardenete", "Cardeña", "Cardeñadijo", "Cardeñajimeno", "Cardeñosa", "Cardeñosa de Volpejera", "Cardeñuela Riopico", "Cardiel de los Montes", "Cardona", "Cardoso de la Sierra, El", "Carenas", "Cariñena", "Cariño", "Carlet", "Carlota, La", "Carme", "Carmena", "Cármenes", "Carmona", "Carmonita", "Carnota", "Carolina, La", "Carpio", "Carpio de Azaba", "Carpio de Tajo, El", "Carpio, El", "Carracedelo", "Carral", "Carranque", "Carrascal de Barregas", "Carrascal del Obispo", "Carrascal del Río", "Carrascalejo", "Carrascalejo, El", "Carrascosa", "Carrascosa de Abajo", "Carrascosa de Haro", "Carrascosa de la Sierra", "Carratraca", "Carreño", "Carrera, La", "Carrias", "Carriches", "Carrícola", "Carrión de Calatrava", "Carrión de los Céspedes", "Carrión de los Condes", "Carrizo", "Carrizosa", "Carrocera", "Cartagena", "Cartajima", "Cártama", "Cartaya", "Cartelle", "Cartes", "Carucedo", "Casa de Uceda", "Casabermeja", "Casafranca", "Casalarreina", "Casar de Cáceres", "Casar de Escalona, El", "Casar de Palomero", "Casar, El", "Casarabonela", "Casarejos", "Casares", "Casares de las Hurdes", "Casariche", "Casarrubios del Monte", "Casarrubuelos", "Casas Altas", "Casas Bajas", "Casas de Benítez", "Casas de Don Antonio", "Casas de Don Gómez", "Casas de Don Pedro", "Casas de Fernando Alonso", "Casas de Garcimolina", "Casas de Guijarro", "Casas de Haro", "Casas de Juan Núñez", "Casas de Lázaro", "Casas de los Pinos", "Casas de Millán", "Casas de Miravete", "Casas de Reina", "Casas de San Galindo", "Casas de Ves", "Casas del Castañar", "Casas del Conde, Las", "Casas del Monte", "Casas del Puerto", "Casas-Ibáñez", "Casasbuenas", "Casaseca de Campeán", "Casaseca de las Chanas", "Casasimarro", "Casasola", "Casasola de Arión", "Casatejada", "Casavieja", "Casbas de Huesca", "Cascajares de Bureba", "Cascajares de la Sierra", "Cascante", "Cascante del Río", "Cáseda", "Caseres", "Casillas", "Casillas de Coria", "Casillas de Flores", "Casinos", "Casla", "Caso", "Caspe", "Caspueñas", "Cassà de la Selva", "Casserres", "Castalla", "Castañar de Ibor", "Castañares de Rioja", "Castañeda", "Castaño del Robledo", "Cástaras", "Castejón", "Castejón", "Castejón de Alarba", "Castejón de Henares", "Castejón de las Armas", "Castejón de Monegros", "Castejón de Sos", "Castejón de Tornos", "Castejón de Valdejasa", "Castejón del Puente", "Castel de Cabra", "Castelflorite", "Castell de Cabres", "Castell de Castells", "Castell de Guadalest, el", "Castell de lAreny", "Castell de Mur", "Castell-Platja dAro", "Castell, Es", "Castellanos de Castro", "Castellanos de Moriscos", "Castellanos de Villiquera", "Castellanos de Zapardiel", "Castellar", "Castellar de la Frontera", "Castellar de la Muela", "Castellar de la Ribera", "Castellar de nHug", "Castellar de Santiago", "Castellar del Riu", "Castellar del Vallès", "Castellar, El", "Castellbell i el Vilar", "Castellbisbal", "Castellcir", "Castelldans", "Castelldefels", "Castellet i la Gornal", "Castellfollit de la Roca", "Castellfollit de Riubregós", "Castellfollit del Boix", "Castellfort", "Castellgalí", "Castellnou de Bages", "Castellnou de Seana", "Castellnovo", "Castelló de Farfanya", "Castelló de Rugat", "Castelló dEmpúries", "Castellolí", "Castellón de la Plana/Castelló de la Plana", "Castellonet de la Conquesta", "Castellote", "Castellserà", "Castellterçol", "Castellvell del Camp", "Castellví de la Marca", "Castellví de Rosanes", "Castelnou", "Castelserás", "Castielfabib", "Castiello de Jaca", "Castigaleu", "Castil de Peones", "Castil de Vela", "Castilblanco", "Castilblanco de los Arroyos", "Castildelgado", "Castilfalé", "Castilforte", "Castilfrío de la Sierra", "Castiliscar", "Castillazuelo", "Castilleja de Guzmán", "Castilleja de la Cuesta", "Castilleja del Campo", "Castilléjar", "Castillejo de Iniesta", "Castillejo de Martín Viejo", "Castillejo de Mesleón", "Castillejo de Robledo", "Castillejo-Sierra", "Castillo de Bayuela", "Castillo de Garcimuñoz", "Castillo de las Guardas, El", "Castillo de Locubín", "Castillo de Villamalefa", "Castillo-Albaráñez", "Castillonroy", "Castillonuevo", "Castilnuevo", "Castilruiz", "Castraz", "Castrejón de la Peña", "Castrejón de Trabancos", "Castrelo de Miño", "Castrelo do Val", "Castril", "Castrillo de Cabrera", "Castrillo de Don Juan", "Castrillo de Duero", "Castrillo de la Guareña", "Castrillo de la Reina", "Castrillo de la Valduerna", "Castrillo de la Vega", "Castrillo de Onielo", "Castrillo de Riopisuerga", "Castrillo de Villavega", "Castrillo del Val", "Castrillo Matajudíos", "Castrillo-Tejeriego", "Castrillón", "Castro Caldelas", "Castro de Filabres", "Castro de Fuentidueña", "Castro de Rei", "Castro del Río", "Castro-Urdiales", "Castrobol", "Castrocalbón", "Castrocontrigo", "Castrodeza", "Castrogonzalo", "Castrojeriz", "Castrojimeno", "Castromembibre", "Castromocho", "Castromonte", "Castronuevo", "Castronuevo de Esgueva", "Castronuño", "Castropodame", "Castropol", "Castroponce", "Castroserna de Abajo", "Castroserracín", "Castrotierra de Valmadrigal", "Castroverde", "Castroverde de Campos", "Castroverde de Cerrato", "Castroviejo", "Castuera", "Catadau", "Catarroja", "Catí", "Catllar, El", "Catoira", "Catral", "Caudete", "Caudete de las Fuentes", "Caudiel", "Cava", "Cavia", "Cayuela", "Cazalegas", "Cazalilla", "Cazalla de la Sierra", "Cazorla", "Cazurra", "Cea", "Cebanico", "Cebolla", "Cebrecos", "Cebreros", "Cebrones del Río", "Ceclavín", "Cedeira", "Cedillo", "Cedillo de la Torre", "Cedillo del Condado", "Cedrillas", "Cee", "Cehegín", "Ceinos de Campos", "Celada del Camino", "Celadas", "Celanova", "Cella", "Cellera de Ter, La", "Cellorigo", "Celrà", "Cendea de Olza/Oltza Zendea", "Cendejas de Enmedio", "Cendejas de la Torre", "Cenes de la Vega", "Cenicero", "Cenicientos", "Cenizate", "Cenlle", "Centelles", "Centenera", "Centenera de Andaluz", "Cepeda", "Cepeda la Mora", "Cerbón", "Cerceda", "Cercedilla", "Cercs", "Cerdà", "Cerdanyola del Vallès", "Cerdedo", "Cerdido", "Cereceda de la Sierra", "Cerecinos de Campos", "Cerecinos del Carrizal", "Cerezal de Peñahorcada", "Cerezo", "Cerezo de Abajo", "Cerezo de Arriba", "Cerezo de Río Tirón", "Cernadilla", "Cerollera, La", "Cerralbo", "Cerralbos, Los", "Cerratón de Juarros", "Cerro de Andévalo, El", "Cerro, El", "Cervantes", "Cervatos de la Cueza", "Cervelló", "Cervera", "Cervera de Buitrago", "Cervera de la Cañada", "Cervera de los Montes", "Cervera de Pisuerga", "Cervera del Llano", "Cervera del Maestre", "Cervera del Río Alhama", "Cerveruela", "Cervià de les Garrigues", "Cervià de Ter", "Cervillego de la Cruz", "Cervo", "Cespedosa de Tormes", "Cesuras", "Cetina", "Ceuta", "Ceutí", "Cevico de la Torre", "Cevico Navero", "Chagarcía Medianero", "Chalamera", "Chamartín", "Chandrexa de Queixa", "Chantada", "Chañe", "Chapinería", "Chauchina", "Checa", "Cheles", "Chella", "Chelva", "Chequilla", "Chera", "Chercos", "Chert/Xert", "Cheste", "Chía", "Chiclana de la Frontera", "Chiclana de Segura", "Chilches/Xilxes", "Chillarón de Cuenca", "Chillarón del Rey", "Chillón", "Chilluévar", "Chiloeches", "Chimeneas", "Chimillas", "Chinchilla de Monte-Aragón", "Chinchón", "Chipiona", "Chiprana", "Chirivel", "Chiva", "Chodes", "Chodos/Xodos", "Chóvar", "Chozas de Abajo", "Chozas de Canales", "Chucena", "Chueca", "Chulilla", "Chumillas", "Churriana de la Vega", "Ciadoncha", "Cidamón", "Cidones", "Ciempozuelos", "Cierva, La", "Cieza", "Cieza", "Cifuentes", "Cigales", "Cigudosa", "Ciguñuela", "Cihuela", "Cihuri", "Cijuela", "Cillán", "Cillaperlata", "Cilleros", "Cilleros de la Bastida", "Cilleruelo de Abajo", "Cilleruelo de Arriba", "Cilleruelo de San Mamés", "Cillorigo de Liébana", "Cimanes de la Vega", "Cimanes del Tejar", "Cimballa", "Cinco Olivas", "Cincovillas", "Cinctorres", "Cintruénigo", "Cipérez", "Cirat", "Cirauqui/Zirauki", "Ciria", "Ciriza/Ziritza", "Ciruelas", "Ciruelos", "Ciruelos de Cervera", "Ciruelos del Pinar", "Cirueña", "Cirujales del Río", "Cisla", "Cisneros", "Cistella", "Cistérniga", "Cistierna", "Ciudad Real", "Ciudad Rodrigo", "Ciutadella de Menorca", "Ciutadilla", "Cizur", "Clarés de Ribota", "Clariana de Cardener", "Clavijo", "Coaña", "Cóbdar", "Cobeja", "Cobeña", "Cobeta", "Cobisa", "Cobos de Cerrato", "Cobos de Fuentidueña", "Cobreros", "Coca", "Coca de Alba", "Cocentaina", "Codo", "Codoñera, La", "Codorniz", "Codos", "Codosera, La", "Cofrentes", "Cogeces de Íscar", "Cogeces del Monte", "Cogollor", "Cogollos", "Cogollos de Guadix", "Cogollos de la Vega", "Cogolludo", "Cogul, El", "Coín", "Coirós", "Colera", "Coles", "Colilla, La", "Colindres", "Coll de Nargó", "Collado", "Collado de Contreras", "Collado del Mirón", "Collado Hermoso", "Collado Mediano", "Collado Villalba", "Collazos de Boedo", "Collbató", "Colldejou", "Collsuspina", "Colmenar", "Colmenar de Montemayor", "Colmenar de Oreja", "Colmenar del Arroyo", "Colmenar Viejo", "Colmenarejo", "Colomera", "Colomers", "Colunga", "Colungo", "Coma i la Pedra, La", "Comares", "Comillas", "Cómpeta", "Conca de Dalt", "Condado de Castilnovo", "Condado de Treviño", "Condemios de Abajo", "Condemios de Arriba", "Conesa", "Confrides", "Congosto", "Congosto de Valdavia", "Congostrina", "Conil de la Frontera", "Conquista", "Conquista de la Sierra", "Consell", "Constantí", "Constantina", "Constanzana", "Consuegra", "Contamina", "Contreras", "Coomonte", "Copernal", "Copons", "Corbalán", "Corbera", "Corbera de Llobregat", "Corbera dEbre", "Corbillos de los Oteros", "Corbins", "Corçà", "Corcos", "Corcubión", "Córdoba", "Cordobilla de Lácara", "Cordovilla", "Cordovilla la Real", "Cordovín", "Corduente", "Corella", "Corera", "Coreses", "Corgo, O", "Coria", "Coria del Río", "Coripe", "Coristanco", "Cornago", "Cornellà de Llobregat", "Cornellà del Terri", "Cornudella de Montsant", "Coronada, La", "Coronil, El", "Corpa", "Corporales", "Corral de Almaguer", "Corral de Ayllón", "Corral de Calatrava", "Corral-Rubio", "Corrales de Buelna, Los", "Corrales de Duero", "Corrales del Vino", "Corrales, Los", "Corte de Peleas", "Corteconcepción", "Cortegada", "Cortegana", "Cortelazor", "Cortes", "Cortes de Aragón", "Cortes de Arenoso", "Cortes de Baza", "Cortes de la Frontera", "Cortes de Pallás", "Cortes y Graena", "Cortijos, Los", "Corullón", "Coruña del Conde", "Coruña, A", "Corvera de Asturias", "Corvera de Toranzo", "Cosa", "Coscurita", "Coslada", "Cospeito", "Costitx", "Costur", "Cosuenda", "Cotanes del Monte", "Cotes", "Cotillas", "Cotobade", "Covaleda", "Covarrubias", "Covelo", "Coves de Vinromà, les", "Cox", "Cózar", "Cozuelos de Fuentidueña", "Crecente", "Creixell", "Crémenes", "Crespià", "Crespos", "Cretas", "Crevillent", "Cristina", "Cristóbal", "Crivillén", "Cruïlles, Monells i Sant Sadurní de lHeura", "Cuacos de Yuste", "Cuadros", "Cualedro", "Cuarte de Huerva", "Cuba, La", "Cubas de la Sagra", "Cubel", "Cubelles", "Cubells", "Cubilla", "Cubillas de Cerrato", "Cubillas de los Oteros", "Cubillas de Rueda", "Cubillas de Santa Marta", "Cubillo", "Cubillo de Uceda, El", "Cubillo del Campo", "Cubillos", "Cubillos del Sil", "Cubla", "Cubo de Benavente", "Cubo de Bureba", "Cubo de Don Sancho, El", "Cubo de la Solana", "Cubo de Tierra del Vino, El", "Cucalón", "Cudillero", "Cuelgamures", "Cuéllar", "Cuenca", "Cuenca de Campos", "Cuerlas, Las", "Cuerva", "Cuervo de Sevilla, El", "Cuervo, El", "Cueva de Ágreda", "Cueva de Roa, La", "Cueva del Hierro", "Cuevas Bajas", "Cuevas de Almudén", "Cuevas de Provanco", "Cuevas de San Clemente", "Cuevas de San Marcos", "Cuevas del Almanzora", "Cuevas del Becerro", "Cuevas del Campo", "Cuevas del Valle", "Cuevas Labradas", "Culla", "Cúllar", "Cúllar Vega", "Cullera", "Culleredo", "Cumbre, La", "Cumbres de Enmedio", "Cumbres de San Bartolomé", "Cumbres Mayores", "Cunit", "Cuntis", "Curiel de Duero", "Curtis", "Cútar", "Cuzcurrita de Río Tirón", "Daganzo de Arriba", "Daimiel", "Daimús", "Dalías", "Darnius", "Daroca", "Daroca de Rioja", "Darro", "Das", "Daya Nueva", "Daya Vieja", "Deba", "Degaña", "Dehesa de Montejo", "Dehesa de Romanos", "Dehesas de Guadix", "Deifontes", "Deleitosa", "Deltebre", "Dénia", "Derio", "Descargamaría", "Desojo", "Destriana", "Dévanos", "Deyá", "Deza", "Dicastillo", "Diego del Carpio", "Diezma", "Dílar", "Dima", "Dios le Guarde", "Dodro", "Dólar", "Dolores", "Domeño", "Domingo García", "Domingo Pérez", "Don Álvaro", "Don Benito", "Donamaria", "Doneztebe/Santesteban", "Donhierro", "Donjimeno", "Donostia-San Sebastián", "Donvidas", "Doña Mencía", "Doñinos de Ledesma", "Doñinos de Salamanca", "Dos Aguas", "Dos Hermanas", "Dos Torres", "Dosbarrios", "Dosrius", "Dozón", "Driebes", "Dúdar", "Dueñas", "Duesaigües", "Dumbría", "Durango", "Dúrcal", "Durón", "Duruelo", "Duruelo de la Sierra", "Ea", "Echarri", "Écija", "Egüés", "Eibar", "Eivissa", "Ejea de los Caballeros", "Ejeme", "Ejido, El", "Ejulve", "Elantxobe", "Elburgo/Burgelu", "Elche de la Sierra", "Elche/Elx", "Elciego", "Elda", "Elduain", "Elgeta", "Elgoibar", "Elgorriaga", "Eliana, l", "Eljas", "Elorrio", "Elvillar/Bilar", "Embid", "Embid de Ariza", "Emperador", "Encina de San Silvestre", "Encina, La", "Encinacorba", "Encinas", "Encinas de Abajo", "Encinas de Arriba", "Encinas de Esgueva", "Encinas Reales", "Encinasola", "Encinasola de los Comendadores", "Encinedo", "Encinillas", "Encío", "Enciso", "Endrinal", "Enériz/Eneritz", "Enguera", "Enguídanos", "Enix", "Ènova, l", "Entrala", "Entrambasaguas", "Entrena", "Entrimo", "Entrín Bajo", "Épila", "Erandio", "Eratsun", "Ercina, La", "Ereño", "Ergoiena", "Erla", "Ermua", "Errenteria", "Errezil", "Erriberagoitia/Ribera Alta", "Errigoiti", "Erro", "Erustes", "Escacena del Campo", "Escala, L", "Escalante", "Escalona", "Escalona del Prado", "Escalonilla", "Escamilla", "Escañuela", "Escarabajosa de Cabezas", "Escariche", "Escatrón", "Escobar de Campos", "Escobar de Polendos", "Escobosa de Almazán", "Escopete", "Escorca", "Escorial, El", "Escorihuela", "Escucha", "Escurial", "Escurial de la Sierra", "Escúzar", "Esgos", "Esguevillas de Esgueva", "Eskoriatza", "Eslava", "Eslida", "Espadaña", "Espadañedo", "Espadilla", "Esparragalejo", "Esparragosa de la Serena", "Esparragosa de Lares", "Esparreguera", "Espartinas", "Esparza de Salazar/Espartza Zaraitzu", "Espeja", "Espeja de San Marcelino", "Espejo", "Espejón", "Espelúy", "Espera", "Espiel", "Espinar, El", "Espinelves", "Espino de la Orbada", "Espinosa de Cerrato", "Espinosa de Cervera", "Espinosa de Henares", "Espinosa de los Caballeros", "Espinosa de los Monteros", "Espinosa de Villagonzalo", "Espinosa del Camino", "Espinoso del Rey", "Espirdo", "Esplegares", "Espluga Calba, L", "Espluga de Francolí, L", "Esplugues de Llobregat", "Esplús", "Espolla", "Esponellà", "Esporles", "Espot", "Espronceda", "Espunyola, L", "Esquivias", "Establés", "Estada", "Estadilla", "Estamariu", "Estany, L", "Estaràs", "Estella-Lizarra", "Estellencs", "Estepa", "Estepa de San Juan", "Estépar", "Estepona", "Estercuel", "Esteribar", "Esterri dÀneu", "Esterri de Cardós", "Estivella", "Estollo", "Estopiñán del Castillo", "Estrada, A", "Estrella, La", "Estremera", "Estriégana", "Estubeny", "Etayo", "Etxalar", "Etxarri-Aranatz", "Etxauri", "Etxebarri", "Etxebarria", "Eulate", "Ezcabarte", "Ezcaray", "Ezcároz/Ezkaroze", "Ezkio-Itsaso", "Ezkurra", "Ezprogui", "Fabara", "Fabero", "Facheca", "Fago", "Falces", "Falset", "Famorca", "Fanlo", "Fanzara", "Far dEmpordà, El", "Faraján", "Faramontanos de Tábara", "Fariza", "Farlete", "Farrera", "Fasnia", "Fatarella, La", "Faura", "Favara", "Fayón", "Fayos, Los", "Febró, La", "Felanitx", "Felix", "Fene", "Férez", "Feria", "Fermoselle", "Fernán Caballero", "Fernán-Núñez", "Ferreira", "Ferreras de Abajo", "Ferreras de Arriba", "Ferreries", "Ferreruela", "Ferreruela de Huerva", "Ferrol", "Figaró-Montmany", "Fígols", "Fígols i Alinyà", "Figuera, La", "Figueres", "Figuerola del Camp", "Figueroles", "Figueruela de Arriba", "Figueruelas", "Fines", "Finestrat", "Fiñana", "Firgas", "Fiscal", "Fisterra", "Fitero", "Flaçà", "Flix", "Flores de Ávila", "Floresta, La", "Florida de Liébana", "Fogars de la Selva", "Fogars de Montclús", "Foios", "Foixà", "Folgoso de la Ribera", "Folgoso do Courel", "Folgueroles", "Fombellida", "Fombuena", "Fompedraza", "Foncea", "Fondarella", "Fondó de les Neus, el/Hondón de las Nieves", "Fondón", "Fonelas", "Fonfría", "Fonfría", "Fonollosa", "Fonsagrada, A", "Font de la Figuera, la", "Font dEn Carròs, la", "Font-rubí", "Fontanals de Cerdanya", "Fontanar", "Fontanarejo", "Fontanars dels Alforins", "Fontanilles", "Fontcoberta", "Fontellas", "Fontihoyuelo", "Fontioso", "Fontiveros", "Fonz", "Fonzaleche", "Foradada", "Foradada del Toscar", "Forallac", "Forcall", "Forcarei", "Forès", "Forfoleda", "Formentera", "Formentera del Segura", "Formiche Alto", "Fornalutx", "Fornells de la Selva", "Fornelos de Montes", "Fórnoles", "Fortaleny", "Fortanete", "Fortià", "Fortuna", "Forua", "Foz", "Foz-Calanda", "Frades", "Frades de la Sierra", "Fraga", "Frago, El", "Frailes", "Franco, El", "Frandovínez", "Franqueses del Vallès, Les", "Frasno, El", "Frechilla", "Frechilla de Almazán", "Fregenal de la Sierra", "Fregeneda, La", "Freginals", "Freila", "Fréscano", "Fresneda de Altarejos", "Fresneda de Cuéllar", "Fresneda de la Sierra", "Fresneda de la Sierra Tirón", "Fresneda, La", "Fresnedilla", "Fresnedillas de la Oliva", "Fresnedoso", "Fresnedoso de Ibor", "Fresneña", "Fresnillo de las Dueñas", "Fresno Alhándiga", "Fresno de Cantespino", "Fresno de Caracena", "Fresno de la Fuente", "Fresno de la Polvorosa", "Fresno de la Ribera", "Fresno de la Vega", "Fresno de Río Tirón", "Fresno de Rodilla", "Fresno de Sayago", "Fresno de Torote", "Fresno del Río", "Fresno el Viejo", "Fresno, El", "Frías", "Frías de Albarracín", "Friera de Valverde", "Frigiliana", "Friol", "Frómista", "Frontera", "Frontera, La", "Fruiz", "Frumales", "Fuembellida", "Fuencaliente", "Fuencaliente de la Palma", "Fuencemillán", "Fuendejalón", "Fuendetodos", "Fuenferrada", "Fuengirola", "Fuenlabrada", "Fuenlabrada de los Montes", "Fuenllana", "Fuenmayor", "Fuensaldaña", "Fuensalida", "Fuensanta", "Fuensanta de Martos", "Fuente Álamo de Murcia", "Fuente de Cantos", "Fuente de Pedro Naharro", "Fuente de Piedra", "Fuente de San Esteban, La", "Fuente de Santa Cruz", "Fuente del Arco", "Fuente del Maestre", "Fuente el Fresno", "Fuente el Olmo de Fuentidueña", "Fuente el Olmo de Íscar", "Fuente el Saúz", "Fuente el Saz de Jarama", "Fuente el Sol", "Fuente Encalada", "Fuente la Lancha", "Fuente la Reina", "Fuente Obejuna", "Fuente Palmera", "Fuente Vaqueros", "Fuente-Álamo", "Fuente-Olmedo", "Fuente-Tójar", "Fuentealbilla", "Fuentearmegil", "Fuentebureba", "Fuentecambrón", "Fuentecantos", "Fuentecén", "Fuenteguinaldo", "Fuenteheridos", "Fuentelahiguera de Albatages", "Fuentelapeña", "Fuentelcésped", "Fuentelencina", "Fuentelespino de Haro", "Fuentelespino de Moya", "Fuenteliante", "Fuentelisendo", "Fuentelmonge", "Fuentelsaz", "Fuentelsaz de Soria", "Fuentelviejo", "Fuentemolinos", "Fuentenava de Jábaga", "Fuentenebro", "Fuentenovilla", "Fuentepelayo", "Fuentepinilla", "Fuentepiñel", "Fuenterrebollo", "Fuenterroble de Salvatierra", "Fuenterrobles", "Fuentes", "Fuentes Calientes", "Fuentes Claras", "Fuentes de Andalucía", "Fuentes de Año", "Fuentes de Ayódar", "Fuentes de Béjar", "Fuentes de Carbajal", "Fuentes de Ebro", "Fuentes de Jiloca", "Fuentes de León", "Fuentes de Magaña", "Fuentes de Nava", "Fuentes de Oñoro", "Fuentes de Ropel", "Fuentes de Rubielos", "Fuentes de Valdepero", "Fuentesaúco", "Fuentesaúco de Fuentidueña", "Fuentesecas", "Fuentesoto", "Fuentespalda", "Fuentespina", "Fuentespreadas", "Fuentestrún", "Fuentidueña", "Fuentidueña de Tajo", "Fuerte del Rey", "Fuertescusa", "Fueva, La", "Fuliola, La", "Fulleda", "Funes", "Fustiñana"}));
        switchPanels(jPanel_disponibilidad); //Se cambia de panel
    }//GEN-LAST:event_jToggleButton_disponibilidadChefActionPerformed

    private void jPasswordField_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField_passwordActionPerformed

    private void jPasswordField_passwordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPasswordField_passwordMouseClicked
        // TODO add your handling code here:
        jPasswordField_password.setText("");
    }//GEN-LAST:event_jPasswordField_passwordMouseClicked

    private void jButton_nuevaCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_nuevaCuentaActionPerformed
        // TODO add your handling code here:
        jPanel_crearCuenta.setEnabled(true);
        jPanel_crearCuenta.setVisible(true);
        switchPanels(jPanel_crearCuenta);
    }//GEN-LAST:event_jButton_nuevaCuentaActionPerformed

    private void jTextField_usuarioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_usuarioMouseExited
        // TODO add your handling code here:
        if (jTextField_usuario.getText().isEmpty()) {
            jTextField_usuario.setText("Usuario");
        }
    }//GEN-LAST:event_jTextField_usuarioMouseExited

    private void jTextField_usuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_usuarioMouseClicked
        // TODO add your handling code here:
        if (jTextField_usuario.getText().equals("Usuario")) {
            jTextField_usuario.setText("");
        }
    }//GEN-LAST:event_jTextField_usuarioMouseClicked

    private void jButton_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_loginActionPerformed
        // TODO add your handling code here:
        /*Codigo anterior realizado al principio del TEA2 para login con datos hardcodeados
        boolean comprobacion = comprobarLogin(jTextField_usuario.getText(), jPasswordField_password.getPassword());
        if (comprobacion) {
            switchPanels(jPanel_Usuario);
        } else {
            jLabel_mensajeLogin.setText("Error en el usuario o contraseña introducidos");
        }
         */
        //Código para login con conexión a API rest
        //Consulta POST
        try {
            String apiUrl = "https://render-chef-deluxe.onrender.com/api/auth/iniciarSesion"; // Dirección en Cloud
            //String apiUrl = "https://localhost:8080/api/auth/iniciarSesion"; // Dirección en localhost
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            //connection.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString((userName + ":" + password).getBytes()));
            //String payload = "{\"usernameOrEmail\": \"pepe2\", \"password\": \"password\"}";// This should be your json body i.e. {"Name" : "Mohsin"}
            String contraseña = String.valueOf(jPasswordField_password.getPassword());
            String payload = "{\"usernameOrEmail\": \"" + jTextField_usuario.getText() + "\", \"password\": \"" + contraseña + "\"}";
            byte[] out = payload.getBytes(StandardCharsets.UTF_8);
            OutputStream stream = connection.getOutputStream();
            stream.write(out);

            if (connection.getResponseCode() != 200) {
                if (connection.getResponseCode() != 500) {
                    JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());
                } else {
                    jLabel_mensajeLogin.setText("Error en el usuario o contraseña introducidos");
                }

            } else {
                //Leyendo la respuesta de la API
                StringBuilder sb = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linea;
                while ((linea = in.readLine()) != null) {
                    sb.append(linea).append("\n");
                }
                in.close();
                //Pasando la respuesta a objeto JSON para poder usarla
                JSONArray jsonArray = new JSONArray("[" + sb.toString() + "]");
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                token = jsonObject.getString("tokenDeAcceso");
                //Se limpia un label de error de contraseña y se cambia al panel Usuario
                jLabel_mensajeLogin.setText("");
                // Se realiza una solicitud GET para recibir los datos del usuario
                obtenerDatosUsuario();
            }
            connection.disconnect();

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }//GEN-LAST:event_jButton_loginActionPerformed

    private void jRadioButton_filtradoListaChefsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_filtradoListaChefsActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelo = dtm;
        TableRowSorter trs = new TableRowSorter(modelo);
        if (jRadioButton_filtradoListaChefs.isSelected()) {
            trs.setRowFilter(RowFilter.regexFilter(jComboBox_poblacion1.getSelectedItem().toString(), 1));
            jTable_listaChefsEstado.setRowSorter(trs);
        }
        if (!jRadioButton_filtradoListaChefs.isSelected()) {
            trs.setModel(dtm);
            jTable_listaChefsEstado.setRowSorter(trs);
        }

    }//GEN-LAST:event_jRadioButton_filtradoListaChefsActionPerformed

    private void jComboBox_poblacion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_poblacion1ActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelo = dtm;
        TableRowSorter trs = new TableRowSorter(modelo);
        if (jRadioButton_filtradoListaChefs.isSelected()) {
            trs.setRowFilter(RowFilter.regexFilter(jComboBox_poblacion1.getSelectedItem().toString(), 1));
            jTable_listaChefsEstado.setRowSorter(trs);
        }

    }//GEN-LAST:event_jComboBox_poblacion1ActionPerformed

    private void jComboBox_filtroAZ1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_filtroAZ1ActionPerformed
        // TODO add your handling code here:
        if (jComboBox_filtroAZ1.getSelectedItem().toString().equals("De la A a la F")) {
            jComboBox_poblacion1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Ababuj", "Abades", "Abadía", "Abadín", "Abadiño", "Abáigar", "Abajas", "Ábalos", "Abaltzisketa", "Abánades", "Abanilla", "Abanto", "Abanto y Ciérvana-Abanto Zierbena", "Abarán", "Abarca de Campos", "Abárzuza/Abartzuza", "Abaurregaina/Abaurrea Alta", "Abaurrepea/Abaurrea Baja", "Abegondo", "Abejar", "Abejuela", "Abella de la Conca", "Abengibre", "Abenójar", "Aberin", "Abertura", "Abezames", "Abia de la Obispalía", "Abia de las Torres", "Abiego", "Abizanda", "Abla", "Ablanque", "Ablitas", "Abrera", "Abrucena", "Abusejo", "Acebeda, La", "Acebedo", "Acebo", "Acebrón, El", "Acedera", "Acehúche", "Aceituna", "Acered", "Aceuchal", "Adahuesca", "Adalia", "Adamuz", "Adanero", "Adeje", "Ademuz", "Adiós", "Adobes", "Ador", "Adra", "Adrada de Haza", "Adrada de Pirón", "Adrada, La", "Adradas", "Adrados", "Adsubia", "Aduna", "Agaete", "Agallas", "Àger", "Agolada", "Agón", "Agoncillo", "Agost", "Agramunt", "Ágreda", "Agres", "Agrón", "Aguadulce", "Aguarón", "Aguas Cándidas", "Aguasal", "Aguatón", "Aguaviva", "Agudo", "Agüero", "Aguilafuente", "Aguilar de Bureba", "Aguilar de Campoo", "Aguilar de Campos", "Aguilar de Codés", "Aguilar de la Frontera", "Aguilar de Segarra", "Aguilar del Alfambra", "Aguilar del Río Alhama", "Águilas", "Aguilón", "Agüimes", "Agullana", "Agullent", "Agulo", "Ahigal", "Ahigal de los Aceiteros", "Ahigal de Villarino", "Ahillones", "Aia", "Aibar/Oibar", "Aielo de Malferit", "Aielo de Rugat", "Aiguafreda", "Aiguamúrcia", "Aiguaviva", "Aigües", "Aín", "Aínsa-Sobrarbe", "Ainzón", "Aisa", "Aitona", "Aizarnazabal", "Ajalvir", "Ajamil de Cameros", "Ajangiz", "Ajofrín", "Alacant/Alicante", "Alacón", "Aladrén", "Alaejos", "Alagón", "Alagón del Río", "Alaior", "Alájar", "Alajeró", "Alameda", "Alameda de Gardón, La", "Alameda de la Sagra", "Alameda del Valle", "Alamedilla", "Alamedilla, La", "Alamillo", "Alaminos", "Álamo, El", "Alamús, Els", "Alange", "Alanís", "Alaquàs", "Alar del Rey", "Alaraz", "Alarba", "Alarcón", "Alarilla", "Alaró", "Alàs i Cerc", "Alatoz", "Alba", "Alba de Cerrato", "Alba de Tormes", "Alba de Yeltes", "Albacete", "Albagés, L", "Albaida", "Albaida del Aljarafe", "Albal", "Albalá", "Albaladejo", "Albaladejo del Cuende", "Albalat de la Ribera", "Albalat dels Sorells", "Albalat dels Tarongers", "Albalate de Cinca", "Albalate de las Nogueras", "Albalate de Zorita", "Albalate del Arzobispo", "Albalatillo", "Albánchez", "Albanchez de Mágina", "Albanyà", "Albares", "Albarracín", "Albarreal de Tajo", "Albatana", "Albatàrrec", "Albatera", "Albelda", "Albelda de Iregua", "Albendea", "Albendiego", "Albentosa", "Alberca de Záncara, La", "Alberca, La", "Alberguería de Argañán, La", "Alberic", "Alberite", "Alberite de San Juan", "Albero Alto", "Albero Bajo", "Alberuela de Tubo", "Albesa", "Albeta", "Albi, L", "Albillos", "Albinyana", "Albiol, L", "Albiztur", "Albocàsser", "Alboloduy", "Albolote", "Albondón", "Albons", "Alborache", "Alboraya", "Alborea", "Alborge", "Albornos", "Albox", "Albudeite", "Albuera, La", "Albuixech", "Albuñán", "Albuñol", "Albuñuelas", "Alburquerque", "Alcabón", "Alcadozo", "Alcaine", "Alcalá de Ebro", "Alcalá de Guadaíra", "Alcalá de Gurrea", "Alcalá de Henares", "Alcalá de la Selva", "Alcalá de la Vega", "Alcalá de los Gazules", "Alcalá de Moncayo", "Alcalà de Xivert", "Alcalá del Júcar", "Alcalá del Obispo", "Alcalá del Río", "Alcalá del Valle", "Alcalá la Real", "Alcalalí", "Alcampell", "Alcanadre", "Alcanar", "Alcanó", "Alcántara", "Alcantarilla", "Alcàntera de Xúquer", "Alcantud", "Alcañices", "Alcañiz", "Alcañizo", "Alcaracejos", "Alcaraz", "Alcarràs", "Alcàsser", "Alcaucín", "Alcaudete", "Alcaudete de la Jara", "Alcázar de San Juan", "Alcázar del Rey", "Alcazarén", "Alcázares, Los", "Alcoba", "Alcobendas", "Alcocer", "Alcocer de Planes", "Alcocero de Mola", "Alcohujate", "Alcolea", "Alcolea de Calatrava", "Alcolea de Cinca", "Alcolea de las Peñas", "Alcolea de Tajo", "Alcolea del Pinar", "Alcolea del Río", "Alcoleja", "Alcoletge", "Alcollarín", "Alconaba", "Alconada", "Alconada de Maderuelo", "Alconchel", "Alconchel de Ariza", "Alconchel de la Estrella", "Alconera", "Alcóntar", "Alcora, l", "Alcorcón", "Alcorisa", "Alcoroches", "Alcover", "Alcoy/Alcoi", "Alcubierre", "Alcubilla de Avellaneda", "Alcubilla de las Peñas", "Alcubilla de Nogales", "Alcubillas", "Alcublas", "Alcúdia", "Alcúdia de Crespins, l", "Alcudia de Monteagud", "Alcudia de Veo", "Alcúdia, l", "Alcuéscar", "Aldaia", "Aldea de San Miguel", "Aldea de San Nicolás, La", "Aldea del Cano", "Aldea del Fresno", "Aldea del Obispo", "Aldea del Obispo, La", "Aldea del Rey", "Aldea en Cabo", "Aldea Real", "Aldea, L", "Aldeacentenera", "Aldeacipreste", "Aldeadávila de la Ribera", "Aldealafuente", "Aldealcorvo", "Aldealengua", "Aldealengua de Pedraza", "Aldealengua de Santa María", "Aldealices", "Aldealpozo", "Aldealseñor", "Aldeamayor de San Martín", "Aldeanueva de Barbarroya", "Aldeanueva de Ebro", "Aldeanueva de Figueroa", "Aldeanueva de Guadalajara", "Aldeanueva de la Serrezuela", "Aldeanueva de la Sierra", "Aldeanueva de la Vera", "Aldeanueva de San Bartolomé", "Aldeanueva de Santa Cruz", "Aldeanueva del Camino", "Aldeanueva del Codonal", "Aldeaquemada", "Aldearrodrigo", "Aldearrubia", "Aldeaseca", "Aldeaseca de Alba", "Aldeaseca de la Frontera", "Aldeasoña", "Aldeatejada", "Aldeavieja de Tormes", "Aldehorno", "Aldehuela de Jerte", "Aldehuela de la Bóveda", "Aldehuela de Liestos", "Aldehuela de Periáñez", "Aldehuela de Yeltes", "Aldehuela del Codonal", "Aldehuela, La", "Aldehuelas, Las", "Aldeire", "Aldeonte", "Aldover", "Aledo", "Alegia", "Alegría-Dulantzi", "Aleixar, L", "Alella", "Alentisque", "Alerre", "Alesanco", "Alesón", "Alfacar", "Alfafar", "Alfafara", "Alfajarín", "Alfambra", "Alfamén", "Alfántega", "Alfara de Carles", "Alfara de la Baronia", "Alfara del Patriarca", "Alfaraz de Sayago", "Alfarnate", "Alfarnatejo", "Alfaro", "Alfarp", "Alfarràs", "Alfarrasí", "Alfàs del Pi, l", "Alfauir", "Alfés", "Alfondeguilla", "Alforja", "Alforque", "Alfoz", "Alfoz de Bricia", "Alfoz de Lloredo", "Alfoz de Quintanadueñas", "Alfoz de Santa Gadea", "Algaba, La", "Algadefe", "Algaida", "Algámitas", "Algar", "Algar de Mesa", "Algar de Palancia", "Algarinejo", "Algarra", "Algarrobo", "Algatocín", "Algeciras", "Algemesí", "Algerri", "Algete", "Algimia de Alfara", "Algimia de Almonacid", "Alginet", "Algodonales", "Algodre", "Algora", "Algorfa", "Alguaire", "Alguazas", "Algueña", "Alhabia", "Alhama de Almería", "Alhama de Aragón", "Alhama de Granada", "Alhama de Murcia", "Alhambra", "Alhaurín de la Torre", "Alhaurín el Grande", "Alhendín", "Alhóndiga", "Alía", "Aliaga", "Aliaguilla", "Alicún", "Alicún de Ortega", "Alija del Infantado", "Alins", "Alió", "Alique", "Aliseda", "Aliud", "Aljaraque", "Aljucén", "Alkiza", "Allande", "Allariz", "Allepuz", "Aller", "Allín/Allin", "Allo", "Alloza", "Allueva", "Almacelles", "Almáchar", "Almadén", "Almadén de la Plata", "Almadenejos", "Almadrones", "Almagro", "Almajano", "Almaluez", "Almansa", "Almanza", "Almaraz", "Almaraz de Duero", "Almarcha, La", "Almargen", "Almarza", "Almarza de Cameros", "Almàssera", "Almatret", "Almazán", "Almazora/Almassora", "Almazul", "Almedíjar", "Almedina", "Almedinilla", "Almegíjar", "Almeida de Sayago", "Almenar", "Almenar de Soria", "Almenara", "Almenara de Adaja", "Almenara de Tormes", "Almendra", "Almendral", "Almendral de la Cañada", "Almendralejo", "Almendro, El", "Almendros", "Almensilla", "Almería", "Almiserà", "Almochuel", "Almócita", "Almodóvar del Campo", "Almodóvar del Pinar", "Almodóvar del Río", "Almogía", "Almoguera", "Almohaja", "Almoharín", "Almoines", "Almolda, La", "Almonacid de la Cuba", "Almonacid de la Sierra", "Almonacid de Toledo", "Almonacid de Zorita", "Almonacid del Marquesado", "Almonaster la Real", "Almonte", "Almoradí", "Almorox", "Almoster", "Almudaina", "Almudévar", "Almunia de Doña Godina, La", "Almunia de San Juan", "Almuniente", "Almuñécar", "Almuradiel", "Almussafes", "Alobras", "Alocén", "Alonsotegi", "Álora", "Alòs de Balaguer", "Alosno", "Alovera", "Alozaina", "Alp", "Alpandeire", "Alpanseque", "Alpartir", "Alpedrete", "Alpens", "Alpeñés", "Alpera", "Alpicat", "Alpuente", "Alpujarra de la Sierra", "Alqueria dAsnar, l", "Alqueria de la Comtessa, l", "Alquerías del Niño Perdido", "Alquézar", "Alquife", "Alsodux", "Alt Àneu", "Altable", "Altafulla", "Altarejos", "Altea", "Altorricón", "Altos, Los", "Altsasu/Alsasua", "Altura", "Altzaga", "Altzo", "Alustante", "Alzira", "Amavida", "Amayuelas de Arriba", "Ambel", "Ambite", "Amer", "Ames", "Améscoa Baja", "Ametlla de Mar, L", "Ametlla del Vallès, L", "Ameyugo", "Amezketa", "Amieva", "Amoeiro", "Amorebieta-Etxano", "Amoroto", "Ampolla, L", "Amposta", "Ampudia", "Ampuero", "Amurrio", "Amusco", "Amusquillo", "Anadón", "Anaya", "Anaya de Alba", "Anchuelo", "Anchuras", "Ancín/Antzin", "Andavías", "Andilla", "Andoain", "Andorra", "Andosilla", "Andratx", "Andújar", "Anento", "Anglès", "Anglesola", "Angón", "Anguciana", "Angüés", "Anguiano", "Anguita", "Anguix", "Anievas", "Aniñón", "Anna", "Anoeta", "Anquela del Ducado", "Anquela del Pedregal", "Ansó", "Ansoáin/Antsoain", "Antas", "Antas de Ulla", "Antella", "Antequera", "Antigua", "Antigua, La", "Antigüedad", "Antillón", "Antzuola", "Anue", "Añana", "Añe", "Añón de Moncayo", "Añora", "Añorbe", "Añover de Tajo", "Añover de Tormes", "Aoiz/Agoitz", "Arabayona de Mógica", "Aracena", "Arafo", "Aragüés del Puerto", "Arahal", "Arahuetes", "Araitz", "Arakaldo", "Arakil", "Arama", "Aramaio", "Aranarache/Aranaratxe", "Arancón", "Aranda de Duero", "Aranda de Moncayo", "Arándiga", "Arandilla", "Arandilla del Arroyo", "Aranga", "Aranguren", "Aranjuez", "Arano", "Arantza", "Arantzazu", "Aranzueque", "Arañuel", "Arapiles", "Aras", "Aras de los Olmos", "Arauzo de Miel", "Arauzo de Salce", "Arauzo de Torre", "Arbancón", "Arbeca", "Arbeteta", "Arbizu", "Arbo", "Arboç, L", "Arboleas", "Arbolí", "Arbúcies", "Arcas del Villar", "Arce/Artzi", "Arcediano", "Arcenillas", "Archena", "Árchez", "Archidona", "Arcicóllar", "Arco, El", "Arconada", "Arcones", "Arcos", "Arcos de Jalón", "Arcos de la Frontera", "Arcos de la Polvorosa", "Arcos de la Sierra", "Arcos de las Salinas", "Arcos, Los", "Ardales", "Ardisa", "Ardón", "Areatza", "Arellano", "Arén", "Arenal, El", "Arenales de San Gregorio", "Arenas", "Arenas de Iguña", "Arenas de San Juan", "Arenas de San Pedro", "Arenas del Rey", "Arenillas", "Arenillas de Riopisuerga", "Arens de Lledó", "Arenys de Mar", "Arenys de Munt", "Arenzana de Abajo", "Arenzana de Arriba", "Ares", "Ares del Maestrat", "Areso", "Aretxabaleta", "Arevalillo", "Arevalillo de Cega", "Arévalo", "Arévalo de la Sierra", "Argamasilla de Alba", "Argamasilla de Calatrava", "Arganda del Rey", "Arganza", "Argañín", "Argavieso", "Argecilla", "Argelaguer", "Argelita", "Argençola", "Argente", "Argentera, L", "Argentona", "Argés", "Argoños", "Arguedas", "Arguis", "Arguisuelas", "Argujillo", "Aria", "Ariany", "Aribe", "Arico", "Arija", "Ariño", "Ariza", "Arjona", "Arjonilla", "Arlanzón", "Armallones", "Armañanzas", "Armentera, L", "Armenteros", "Armilla", "Armiñón", "Armuña", "Armuña de Almanzora", "Armuña de Tajuña", "Arnedillo", "Arnedo", "Arnes", "Arnoia, A", "Arnuero", "Aroche", "Arona", "Arquillinos", "Arquillos", "Arrabalde", "Arraia-Maeztu", "Arrancacepas", "Arrankudiaga", "Arrasate/Mondragón", "Arratzu", "Arraya de Oca", "Arrazua-Ubarrundia", "Arrecife", "Arredondo", "Arres", "Arriate", "Arrieta", "Arrigorriaga", "Arroba de los Montes", "Arróniz", "Arroyo de la Encomienda", "Arroyo de la Luz", "Arroyo de las Fraguas", "Arroyo de San Serván", "Arroyo del Ojanco", "Arroyomolinos", "Arroyomolinos", "Arroyomolinos de la Vera", "Arroyomolinos de León", "Arruazu", "Arrúbal", "Arsèguel", "Artà", "Artajona", "Artana", "Artazu", "Artea", "Arteixo", "Artenara", "Artés", "Artesa de Lleida", "Artesa de Segre", "Artieda", "Artzentales", "Artziniega", "Arucas", "Arzúa", "Ascó", "Asín", "Aspa", "Aspariegos", "Asparrena", "Aspe", "Asteasu", "Astigarraga", "Astillero, El", "Astorga", "Astudillo", "Asturianos", "Atajate", "Atalaya", "Atalaya del Cañavate", "Atalaya, La", "Atanzón", "Atapuerca", "Ataquines", "Atarfe", "Ataun", "Atazar, El", "Atea", "Ateca", "Atez", "Atienza", "Atxondo", "Atzeneta dAlbaida", "Atzeneta del Maestrat", "Aulesti", "Auñón", "Auritz/Burguete", "Ausejo", "Ausejo de la Sierra", "Ausines, Los", "Autilla del Pino", "Autillo de Campos", "Autol", "Aveinte", "Avellaneda", "Avellanes i Santa Linya, Les", "Avellanosa de Muñó", "Avià", "Ávila", "Avilés", "Avinyó", "Avinyonet de Puigventós", "Avinyonet del Penedès", "Avión", "Ayala/Aiara", "Ayamonte", "Ayegui/Aiegi", "Ayerbe", "Ayllón", "Ayna", "Ayódar", "Ayoó de Vidriales", "Ayora", "Ayuela", "Azagra", "Azaila", "Azanuy-Alins", "Azara", "Azkoitia", "Azlor", "Aznalcázar", "Aznalcóllar", "Azofra", "Azpeitia", "Azuaga", "Azuara", "Azuébar", "Azuelo", "Azuqueca de Henares", "Azután", "Babilafuente", "Bacares", "Badajoz", "Badalona", "Badarán", "Bádenas", "Badia del Vallès", "Badolatosa", "Badules", "Baélls", "Baena", "Baeza", "Bagà", "Báguena", "Bagüés", "Bahabón", "Bahabón de Esgueva", "Baides", "Bailén", "Bailo", "Baiona", "Baix Pallars", "Bakaiku", "Bakio", "Balaguer", "Balazote", "Balbases, Los", "Balboa", "Balconchán", "Baldellou", "Baleira", "Balenyà", "Baliarrain", "Ballestero, El", "Ballesteros de Calatrava", "Ballobar", "Balmaseda", "Balones", "Balsa de Ves", "Balsareny", "Baltanás", "Baltar", "Banastás", "Bande", "Banyalbufar", "Banyeres de Mariola", "Banyeres del Penedès", "Banyoles", "Baña, A", "Bañares", "Bañeza, La", "Bañobárez", "Bañón", "Baños de Ebro/Mañueta", "Baños de la Encina", "Baños de Molgas", "Baños de Montemayor", "Baños de Río Tobía", "Baños de Rioja", "Baños de Tajo", "Baños de Valdearados", "Bañuelos", "Bañuelos de Bureba", "Baquerín de Campos", "Barajas de Melo", "Barakaldo", "Baralla", "Barañain", "Baraona", "Barásoain", "Barbadás", "Barbadillo", "Barbadillo de Herreros", "Barbadillo del Mercado", "Barbadillo del Pez", "Barbalos", "Barbarin", "Barbastro", "Barbate", "Barbens", "Barberà de la Conca", "Barberà del Vallès", "Bárboles", "Barbolla", "Barbués", "Barbuñales", "Barca", "Bárcabo", "Barcarrota", "Barcelona", "Bárcena de Campos", "Bárcena de Cicero", "Bárcena de Pie de Concha", "Barceo", "Barchín del Hoyo", "Barcial de la Loma", "Barcial del Barco", "Barcience", "Barco de Ávila, El", "Barco de Valdeorras, O", "Barcones", "Bardallur", "Bareyo", "Bargas", "Bargota", "Barillas", "Barjas", "Barlovento", "Baronia de Rialb, La", "Barracas", "Barrachina", "Barraco, El", "Barrado", "Barrax", "Barreiros", "Barrika", "Barrio de Muñó", "Barriopedro", "Barrios de Bureba, Los", "Barrios de Colina", "Barrios de Luna, Los", "Barrios, Los", "Barro", "Barromán", "Barruecopardo", "Barruelo de Santullán", "Barruelo del Valle", "Barrundia", "Barx", "Barxeta", "Basaburua", "Basardilla", "Basauri", "Bàscara", "Basconcillos del Tozo", "Báscones de Ojeda", "Bascuñana", "Bascuñana de San Pedro", "Bassella", "Bastida, La", "Batea", "Baterno", "Batres", "Bausen", "Bayárcal", "Bayarque", "Bayubas de Abajo", "Bayubas de Arriba", "Baza", "Baztan", "Bea", "Beade", "Beamud", "Beariz", "Beas", "Beas de Granada", "Beas de Guadix", "Beas de Segura", "Beasain", "Becedas", "Becedillas", "Beceite", "Becerreá", "Becerril de Campos", "Becerril de la Sierra", "Becilla de Valderaduey", "Bédar", "Bedia", "Bedmar y Garcíez", "Begíjar", "Begonte", "Begues", "Begur", "Beintza-Labaien", "Beire", "Beires", "Beizama", "Béjar", "Bejís", "Belalcázar", "Belascoáin", "Belauntza", "Belbimbre", "Belchite", "Beleña", "Bèlgida", "Belianes", "Belinchón", "Bell-lloc dUrgell", "Bellaguarda", "Bellcaire dEmpordà", "Bellcaire dUrgell", "Bellmunt del Priorat", "Bellmunt dUrgell", "Bello", "Bellprat", "Bellpuig", "Bellreguard", "Bellús", "Bellvei", "Bellver de Cerdanya", "Bellvís", "Belmez", "Bélmez de la Moraleda", "Belmonte", "Belmonte de Campos", "Belmonte de Gracián", "Belmonte de Miranda", "Belmonte de San José", "Belmonte de Tajo", "Belmontejo", "Belorado", "Belver de Cinca", "Belver de los Montes", "Belvís de la Jara", "Belvís de Monroy", "Bembibre", "Benabarre", "Benacazón", "Benadalid", "Benafarces", "Benafer", "Benafigos", "Benagéber", "Benaguasil", "Benahadux", "Benahavís", "Benalauría", "Benalmádena", "Benalúa", "Benalúa de las Villas", "Benalup-Casas Viejas", "Benamargosa", "Benamaurel", "Benamejí", "Benamocarra", "Benaocaz", "Benaoján", "Benarrabá", "Benasal", "Benasau", "Benasque", "Benatae", "Benavent de Segrià", "Benavente", "Benavides", "Benavites", "Benegiles", "Beneixama", "Beneixida", "Benejúzar", "Benetússer", "Benferri", "Beniarbeig", "Beniardá", "Beniarjó", "Beniarrés", "Beniatjar", "Benicarló", "Benicasim/Benicàssim", "Benicolet", "Benicull de Xúquer", "Benidoleig", "Benidorm", "Beniel", "Benifaió", "Benifairó de la Valldigna", "Benifairó de les Valls", "Benifallet", "Benifallim", "Benifato", "Beniflá", "Benigánim", "Benigembla", "Benijófar", "Benilloba", "Benillup", "Benimantell", "Benimarfull", "Benimassot", "Benimeli", "Benimodo", "Benimuslem", "Beniparrell", "Benirredrà", "Benisanó", "Benissa", "Benissanet", "Benissoda", "Benisuera", "Benitachell/Poble Nou de Benitatxell, el", "Benitagla", "Benizalón", "Benlloch", "Benquerencia", "Benquerencia de la Serena", "Bentarique", "Benuza", "Bera", "Berango", "Berantevilla", "Beranuy", "Berastegi", "Beratón", "Berbegal", "Berberana", "Berbinzana", "Berceo", "Bercero", "Berceruelo", "Bérchules", "Bercial", "Bercial de Zapardiel", "Bercianos del Páramo", "Bercianos del Real Camino", "Bercimuel", "Berdejo", "Berga", "Bergara", "Bergasa", "Bergasillas Bajera", "Berge", "Bergondo", "Beriáin", "Berja", "Berlanas, Las", "Berlanga", "Berlanga de Duero", "Berlanga del Bierzo", "Berlangas de Roa", "Bermellar", "Bermeo", "Bermillo de Sayago", "Bernardos", "Bernedo", "Berninches", "Bernuy de Porreros", "Bernuy-Zapardiel", "Berriatua", "Berrioplano/Berriobeiti", "Berriozar", "Berriz", "Berrobi", "Berrocal", "Berrocal de Huebra", "Berrocal de Salvatierra", "Berrocalejo", "Berrocalejo de Aragona", "Berrueces", "Berrueco", "Berrueco, El", "Bertizarana", "Berzocana", "Berzosa de Bureba", "Berzosa del Lozoya", "Berzosilla", "Besalú", "Bescanó", "Betancuria", "Betanzos", "Betelu", "Bétera", "Beteta", "Betxí", "Beuda", "Bezares", "Bezas", "Biar", "Bicorp", "Bidaurreta", "Bidegoian", "Biel", "Bielsa", "Bienservida", "Bienvenida", "Bierge", "Biescas", "Bigastro", "Bigues i Riells", "Bijuesca", "Bilbao", "Bimenes", "Binaced", "Binéfar", "Binissalem", "Biosca", "Biota", "Bisaurri", "Bisbal de Falset, La", "Bisbal del Penedès, La", "Bisbal dEmpordà, La", "Biscarrués", "Bisimbre", "Biure", "Biurrun-Olcoz", "Blacos", "Blanca", "Blancafort", "Blancas", "Blancos, Os", "Blanes", "Blascomillán", "Blasconuño de Matacabras", "Blascosancho", "Blázquez, Los", "Blecua y Torres", "Blesa", "Bliecos", "Boada", "Boada de Campos", "Boadella i les Escaules", "Boadilla de Rioseco", "Boadilla del Camino", "Boadilla del Monte", "Boal", "Boalo, El", "Bobadilla", "Bobadilla del Campo", "Boborás", "Boca de Huérgano", "Bocairent", "Boceguillas", "Bocigas", "Bocos de Duero", "Bodera, La", "Bodón, El", "Bodonal de la Sierra", "Boecillo", "Bogajo", "Bogarra", "Bohodón, El", "Bohonal de Ibor", "Bohoyo", "Boimorto", "Boiro", "Bola, A", "Bolaños de Calatrava", "Bolaños de Campos", "Bolbaite", "Bollullos de la Mitación", "Bollullos Par del Condado", "Bolo, O", "Boltaña", "Bolulla", "Bolvir", "Bonansa", "Bonares", "Bonastre", "Bonete", "Boniches", "Bonilla de la Sierra", "Bonillo, El", "Bonrepòs i Mirambell", "Boñar", "Boqueixón", "Boquiñeni", "Borau", "Bordalba", "Bòrdes, Es", "Bordils", "Bordón", "Borge, El", "Borges Blanques, Les", "Borges del Camp, Les", "Borja", "Borjabad", "Bormujos", "Bornos", "Borobia", "Borox", "Borrassà", "Borredà", "Borrenes", "Borriana/Burriana", "Borriol", "Bosque, El", "Bossòst", "Bot", "Botarell", "Botija", "Botorrita", "Bouza, La", "Bóveda", "Bóveda de Toro, La", "Bóveda del Río Almar", "Bovera", "Bozoó", "Brabos", "Bràfim", "Brahojos de Medina", "Brañosera", "Braojos", "Brazacorta", "Brazatortas", "Brazuelo", "Brea de Aragón", "Brea de Tajo", "Breda", "Brenes", "Breña Alta", "Breña Baja", "Bretó", "Bretocino", "Brieva", "Brieva de Cameros", "Brihuega", "Brime de Sog", "Brime de Urz", "Brincones", "Briñas", "Brión", "Briones", "Briviesca", "Bronchales", "Broto", "Brozas", "Bruc, El", "Brull, El", "Brunete", "Brunyola", "Buberos", "Bubierca", "Bubión", "Buciegas", "Budia", "Buenache de Alarcón", "Buenache de la Sierra", "Buenamadre", "Buenaventura", "Buenavista", "Buenavista de Valdavia", "Buenavista del Norte", "Buendía", "Bueña", "Bueu", "Bufali", "Bugarra", "Bugedo", "Búger", "Buitrago", "Buitrago del Lozoya", "Bujalance", "Bujalaro", "Bujaraloz", "Bularros", "Bulbuente", "Bullas", "Buniel", "Bunyola", "Buñol", "Buñuel", "Burbáguena", "Burela", "Bureta", "Burganes de Valverde", "Burgo de Ebro, El", "Burgo de Osma-Ciudad de Osma", "Burgo Ranero, El", "Burgo, El", "Burgohondo", "Burgos", "Burgui/Burgi", "Burguillos", "Burguillos de Toledo", "Burguillos del Cerro", "Burjassot", "Burlada/Burlata", "Burón", "Burujón", "Busot", "Busquístar", "Bustares", "Bustarviejo", "Buste, El", "Bustillo de Chaves", "Bustillo de la Vega", "Bustillo del Oro", "Bustillo del Páramo", "Bustillo del Páramo de Carrión", "Busto de Bureba", "Busto, El", "Busturia", "Cabacés", "Cabaco, El", "Caballar", "Cabana de Bergantiños", "Cabanabona", "Cabanas", "Cabanelles", "Cabanes", "Cabanes", "Cabanillas", "Cabanillas de la Sierra", "Cabanillas del Campo", "Cabanyes, Les", "Cabañas de Ebro", "Cabañas de la Sagra", "Cabañas de Polendos", "Cabañas de Sayago", "Cabañas de Yepes", "Cabañas del Castillo", "Cabañas Raras", "Cabañes de Esgueva", "Cabeza de Béjar, La", "Cabeza del Buey", "Cabeza del Caballo", "Cabeza la Vaca", "Cabezabellosa", "Cabezabellosa de la Calzada", "Cabezamesada", "Cabezarados", "Cabezarrubias del Puerto", "Cabezas de Alambre", "Cabezas de San Juan, Las", "Cabezas del Pozo", "Cabezas del Villar", "Cabezas Rubias", "Cabezón de Cameros", "Cabezón de la Sal", "Cabezón de la Sierra", "Cabezón de Liébana", "Cabezón de Pisuerga", "Cabezón de Valderaduey", "Cabezuela", "Cabezuela del Valle", "Cabizuela", "Cabó", "Cabolafuente", "Cabra", "Cabra de Mora", "Cabra del Camp", "Cabra del Santo Cristo", "Cabrales", "Cabranes", "Cabredo", "Cabrejas del Campo", "Cabrejas del Pinar", "Cabrera dAnoia", "Cabrera de Mar", "Cabrera, La", "Cabrerizos", "Cabrero", "Cabreros del Monte", "Cabreros del Río", "Cabrillanes", "Cabrillas", "Cabrils", "Cabuérniga", "Cacabelos", "Cáceres", "Cachorrilla", "Cacín", "Cadalso", "Cadalso de los Vidrios", "Cadaqués", "Cádiar", "Cádiz", "Cadreita", "Cadrete", "Cájar", "Cala", "Calabazas de Fuentidueña", "Calaceite", "Calaf", "Calafell", "Calahorra", "Calahorra de Boedo", "Calahorra, La", "Calamocha", "Calamonte", "Calanda", "Calañas", "Calasparra", "Calatañazor", "Calatayud", "Calatorao", "Calcena", "Caldas de Reis", "Caldearenas", "Calders", "Caldes de Malavella", "Caldes de Montbui", "Caldes dEstrac", "Calella", "Calera de León", "Calera y Chozas", "Caleruega", "Caleruela", "Calicasas", "Càlig", "Calldetenes", "Calles", "Callosa de Segura", "Callosa dEn Sarrià", "Callús", "Calmarza", "Calomarde", "Calonge", "Calonge de Segarra", "Calp", "Caltojar", "Calvarrasa de Abajo", "Calvarrasa de Arriba", "Calvià", "Calvos de Randín", "Calzada de Béjar, La", "Calzada de Calatrava", "Calzada de Don Diego", "Calzada de los Molinos", "Calzada de Oropesa", "Calzada de Valdunciel", "Calzada del Coto", "Calzadilla", "Calzadilla de los Barros", "Calzadilla de Tera", "Camaleño", "Camañas", "Camarasa", "Camarena", "Camarena de la Sierra", "Camarenilla", "Camargo", "Camarillas", "Camariñas", "Camarles", "Camarma de Esteruelas", "Camarzana de Tera", "Camas", "Cambados", "Cambil", "Cambre", "Cambrils", "Caminomorisco", "Caminreal", "Camós", "Campana, La", "Campanario", "Campanet", "Campaspero", "Campazas", "Campdevànol", "Campelles", "Campello, el", "Campezo/Kanpezu", "Campillo de Altobuey", "Campillo de Aragón", "Campillo de Aranda", "Campillo de Arenas", "Campillo de Azaba", "Campillo de Deleitosa", "Campillo de Dueñas", "Campillo de la Jara, El", "Campillo de Llerena", "Campillo de Ranas", "Campillo, El", "Campillo, El", "Campillos", "Campillos-Paravientos", "Campillos-Sierra", "Campins", "Campisábalos", "Campllong", "Campo", "Campo de Criptana", "Campo de Mirra/Camp de Mirra, el", "Campo de Peñaranda, El", "Campo de San Pedro", "Campo de Villavidel", "Campo Lameiro", "Campo Lugar", "Campo Real", "Campofrío", "Campolara", "Camponaraya", "Campoo de Enmedio", "Campoo de Yuso", "Camporredondo", "Camporrélls", "Camporrobles", "Campos", "Campos del Paraíso", "Campos del Río", "Campotéjar", "Camprodon", "Camprovín", "Camuñas", "Canal de Berdún", "Canalejas de Peñafiel", "Canalejas del Arroyo", "Canales", "Canales de la Sierra", "Canals", "Candamo", "Candasnos", "Candelaria", "Candelario", "Candeleda", "Candilichera", "Candín", "Canejan", "Canena", "Canencia", "Canet dAdri", "Canet de Mar", "Canet dEn Berenguer", "Canet lo Roig", "Canfranc", "Cangas", "Cangas de Onís", "Cangas del Narcea", "Canicosa de la Sierra", "Caniles", "Canillas de Abajo", "Canillas de Aceituno", "Canillas de Albaida", "Canillas de Esgueva", "Canillas de Río Tuerto", "Canjáyar", "Canonja, La", "Canovelles", "Cànoves i Samalús", "Canredondo", "Cantabrana", "Cantagallo", "Cantalapiedra", "Cantalejo", "Cantallops", "Cantalojas", "Cantalpino", "Cantaracillo", "Cantavieja", "Cantillana", "Cantimpalos", "Cantiveros", "Cantoria", "Canyelles", "Cañada", "Cañada de Benatanduz", "Cañada de Calatrava", "Cañada de Verich, La", "Cañada del Hoyo", "Cañada Juncosa", "Cañada Rosal", "Cañada Vellida", "Cañamaque", "Cañamares", "Cañamero", "Cáñar", "Cañas", "Cañavate, El", "Cañaveral", "Cañaveral de León", "Cañaveras", "Cañaveruelas", "Cañete", "Cañete de las Torres", "Cañete la Real", "Cañiza, A", "Cañizal", "Cañizar", "Cañizar del Olivar", "Cañizares", "Cañizo", "Capafonts", "Caparroso", "Capçanes", "Capdepera", "Capdesaso", "Capela, A", "Capella", "Capellades", "Capileira", "Capilla", "Capillas", "Capmany", "Capolat", "Carabantes", "Carabaña", "Caracena", "Caracuel de Calatrava", "Carataunas", "Caravaca de la Cruz", "Caravia", "Carazo", "Carbajales de Alba", "Carbajo", "Carbajosa de la Sagrada", "Carballeda de Avia", "Carballeda de Valdeorras", "Carballedo", "Carballiño, O", "Carballo", "Carbellino", "Carboneras", "Carboneras de Guadazaón", "Carbonero el Mayor", "Carboneros", "Carcaboso", "Carcabuey", "Carcaixent", "Cárcar", "Carcastillo", "Carcedo de Bureba", "Carcedo de Burgos", "Carcelén", "Càrcer", "Cárcheles", "Cardedeu", "Cárdenas", "Cardenete", "Cardeña", "Cardeñadijo", "Cardeñajimeno", "Cardeñosa", "Cardeñosa de Volpejera", "Cardeñuela Riopico", "Cardiel de los Montes", "Cardona", "Cardoso de la Sierra, El", "Carenas", "Cariñena", "Cariño", "Carlet", "Carlota, La", "Carme", "Carmena", "Cármenes", "Carmona", "Carmonita", "Carnota", "Carolina, La", "Carpio", "Carpio de Azaba", "Carpio de Tajo, El", "Carpio, El", "Carracedelo", "Carral", "Carranque", "Carrascal de Barregas", "Carrascal del Obispo", "Carrascal del Río", "Carrascalejo", "Carrascalejo, El", "Carrascosa", "Carrascosa de Abajo", "Carrascosa de Haro", "Carrascosa de la Sierra", "Carratraca", "Carreño", "Carrera, La", "Carrias", "Carriches", "Carrícola", "Carrión de Calatrava", "Carrión de los Céspedes", "Carrión de los Condes", "Carrizo", "Carrizosa", "Carrocera", "Cartagena", "Cartajima", "Cártama", "Cartaya", "Cartelle", "Cartes", "Carucedo", "Casa de Uceda", "Casabermeja", "Casafranca", "Casalarreina", "Casar de Cáceres", "Casar de Escalona, El", "Casar de Palomero", "Casar, El", "Casarabonela", "Casarejos", "Casares", "Casares de las Hurdes", "Casariche", "Casarrubios del Monte", "Casarrubuelos", "Casas Altas", "Casas Bajas", "Casas de Benítez", "Casas de Don Antonio", "Casas de Don Gómez", "Casas de Don Pedro", "Casas de Fernando Alonso", "Casas de Garcimolina", "Casas de Guijarro", "Casas de Haro", "Casas de Juan Núñez", "Casas de Lázaro", "Casas de los Pinos", "Casas de Millán", "Casas de Miravete", "Casas de Reina", "Casas de San Galindo", "Casas de Ves", "Casas del Castañar", "Casas del Conde, Las", "Casas del Monte", "Casas del Puerto", "Casas-Ibáñez", "Casasbuenas", "Casaseca de Campeán", "Casaseca de las Chanas", "Casasimarro", "Casasola", "Casasola de Arión", "Casatejada", "Casavieja", "Casbas de Huesca", "Cascajares de Bureba", "Cascajares de la Sierra", "Cascante", "Cascante del Río", "Cáseda", "Caseres", "Casillas", "Casillas de Coria", "Casillas de Flores", "Casinos", "Casla", "Caso", "Caspe", "Caspueñas", "Cassà de la Selva", "Casserres", "Castalla", "Castañar de Ibor", "Castañares de Rioja", "Castañeda", "Castaño del Robledo", "Cástaras", "Castejón", "Castejón", "Castejón de Alarba", "Castejón de Henares", "Castejón de las Armas", "Castejón de Monegros", "Castejón de Sos", "Castejón de Tornos", "Castejón de Valdejasa", "Castejón del Puente", "Castel de Cabra", "Castelflorite", "Castell de Cabres", "Castell de Castells", "Castell de Guadalest, el", "Castell de lAreny", "Castell de Mur", "Castell-Platja dAro", "Castell, Es", "Castellanos de Castro", "Castellanos de Moriscos", "Castellanos de Villiquera", "Castellanos de Zapardiel", "Castellar", "Castellar de la Frontera", "Castellar de la Muela", "Castellar de la Ribera", "Castellar de nHug", "Castellar de Santiago", "Castellar del Riu", "Castellar del Vallès", "Castellar, El", "Castellbell i el Vilar", "Castellbisbal", "Castellcir", "Castelldans", "Castelldefels", "Castellet i la Gornal", "Castellfollit de la Roca", "Castellfollit de Riubregós", "Castellfollit del Boix", "Castellfort", "Castellgalí", "Castellnou de Bages", "Castellnou de Seana", "Castellnovo", "Castelló de Farfanya", "Castelló de Rugat", "Castelló dEmpúries", "Castellolí", "Castellón de la Plana/Castelló de la Plana", "Castellonet de la Conquesta", "Castellote", "Castellserà", "Castellterçol", "Castellvell del Camp", "Castellví de la Marca", "Castellví de Rosanes", "Castelnou", "Castelserás", "Castielfabib", "Castiello de Jaca", "Castigaleu", "Castil de Peones", "Castil de Vela", "Castilblanco", "Castilblanco de los Arroyos", "Castildelgado", "Castilfalé", "Castilforte", "Castilfrío de la Sierra", "Castiliscar", "Castillazuelo", "Castilleja de Guzmán", "Castilleja de la Cuesta", "Castilleja del Campo", "Castilléjar", "Castillejo de Iniesta", "Castillejo de Martín Viejo", "Castillejo de Mesleón", "Castillejo de Robledo", "Castillejo-Sierra", "Castillo de Bayuela", "Castillo de Garcimuñoz", "Castillo de las Guardas, El", "Castillo de Locubín", "Castillo de Villamalefa", "Castillo-Albaráñez", "Castillonroy", "Castillonuevo", "Castilnuevo", "Castilruiz", "Castraz", "Castrejón de la Peña", "Castrejón de Trabancos", "Castrelo de Miño", "Castrelo do Val", "Castril", "Castrillo de Cabrera", "Castrillo de Don Juan", "Castrillo de Duero", "Castrillo de la Guareña", "Castrillo de la Reina", "Castrillo de la Valduerna", "Castrillo de la Vega", "Castrillo de Onielo", "Castrillo de Riopisuerga", "Castrillo de Villavega", "Castrillo del Val", "Castrillo Matajudíos", "Castrillo-Tejeriego", "Castrillón", "Castro Caldelas", "Castro de Filabres", "Castro de Fuentidueña", "Castro de Rei", "Castro del Río", "Castro-Urdiales", "Castrobol", "Castrocalbón", "Castrocontrigo", "Castrodeza", "Castrogonzalo", "Castrojeriz", "Castrojimeno", "Castromembibre", "Castromocho", "Castromonte", "Castronuevo", "Castronuevo de Esgueva", "Castronuño", "Castropodame", "Castropol", "Castroponce", "Castroserna de Abajo", "Castroserracín", "Castrotierra de Valmadrigal", "Castroverde", "Castroverde de Campos", "Castroverde de Cerrato", "Castroviejo", "Castuera", "Catadau", "Catarroja", "Catí", "Catllar, El", "Catoira", "Catral", "Caudete", "Caudete de las Fuentes", "Caudiel", "Cava", "Cavia", "Cayuela", "Cazalegas", "Cazalilla", "Cazalla de la Sierra", "Cazorla", "Cazurra", "Cea", "Cebanico", "Cebolla", "Cebrecos", "Cebreros", "Cebrones del Río", "Ceclavín", "Cedeira", "Cedillo", "Cedillo de la Torre", "Cedillo del Condado", "Cedrillas", "Cee", "Cehegín", "Ceinos de Campos", "Celada del Camino", "Celadas", "Celanova", "Cella", "Cellera de Ter, La", "Cellorigo", "Celrà", "Cendea de Olza/Oltza Zendea", "Cendejas de Enmedio", "Cendejas de la Torre", "Cenes de la Vega", "Cenicero", "Cenicientos", "Cenizate", "Cenlle", "Centelles", "Centenera", "Centenera de Andaluz", "Cepeda", "Cepeda la Mora", "Cerbón", "Cerceda", "Cercedilla", "Cercs", "Cerdà", "Cerdanyola del Vallès", "Cerdedo", "Cerdido", "Cereceda de la Sierra", "Cerecinos de Campos", "Cerecinos del Carrizal", "Cerezal de Peñahorcada", "Cerezo", "Cerezo de Abajo", "Cerezo de Arriba", "Cerezo de Río Tirón", "Cernadilla", "Cerollera, La", "Cerralbo", "Cerralbos, Los", "Cerratón de Juarros", "Cerro de Andévalo, El", "Cerro, El", "Cervantes", "Cervatos de la Cueza", "Cervelló", "Cervera", "Cervera de Buitrago", "Cervera de la Cañada", "Cervera de los Montes", "Cervera de Pisuerga", "Cervera del Llano", "Cervera del Maestre", "Cervera del Río Alhama", "Cerveruela", "Cervià de les Garrigues", "Cervià de Ter", "Cervillego de la Cruz", "Cervo", "Cespedosa de Tormes", "Cesuras", "Cetina", "Ceuta", "Ceutí", "Cevico de la Torre", "Cevico Navero", "Chagarcía Medianero", "Chalamera", "Chamartín", "Chandrexa de Queixa", "Chantada", "Chañe", "Chapinería", "Chauchina", "Checa", "Cheles", "Chella", "Chelva", "Chequilla", "Chera", "Chercos", "Chert/Xert", "Cheste", "Chía", "Chiclana de la Frontera", "Chiclana de Segura", "Chilches/Xilxes", "Chillarón de Cuenca", "Chillarón del Rey", "Chillón", "Chilluévar", "Chiloeches", "Chimeneas", "Chimillas", "Chinchilla de Monte-Aragón", "Chinchón", "Chipiona", "Chiprana", "Chirivel", "Chiva", "Chodes", "Chodos/Xodos", "Chóvar", "Chozas de Abajo", "Chozas de Canales", "Chucena", "Chueca", "Chulilla", "Chumillas", "Churriana de la Vega", "Ciadoncha", "Cidamón", "Cidones", "Ciempozuelos", "Cierva, La", "Cieza", "Cieza", "Cifuentes", "Cigales", "Cigudosa", "Ciguñuela", "Cihuela", "Cihuri", "Cijuela", "Cillán", "Cillaperlata", "Cilleros", "Cilleros de la Bastida", "Cilleruelo de Abajo", "Cilleruelo de Arriba", "Cilleruelo de San Mamés", "Cillorigo de Liébana", "Cimanes de la Vega", "Cimanes del Tejar", "Cimballa", "Cinco Olivas", "Cincovillas", "Cinctorres", "Cintruénigo", "Cipérez", "Cirat", "Cirauqui/Zirauki", "Ciria", "Ciriza/Ziritza", "Ciruelas", "Ciruelos", "Ciruelos de Cervera", "Ciruelos del Pinar", "Cirueña", "Cirujales del Río", "Cisla", "Cisneros", "Cistella", "Cistérniga", "Cistierna", "Ciudad Real", "Ciudad Rodrigo", "Ciutadella de Menorca", "Ciutadilla", "Cizur", "Clarés de Ribota", "Clariana de Cardener", "Clavijo", "Coaña", "Cóbdar", "Cobeja", "Cobeña", "Cobeta", "Cobisa", "Cobos de Cerrato", "Cobos de Fuentidueña", "Cobreros", "Coca", "Coca de Alba", "Cocentaina", "Codo", "Codoñera, La", "Codorniz", "Codos", "Codosera, La", "Cofrentes", "Cogeces de Íscar", "Cogeces del Monte", "Cogollor", "Cogollos", "Cogollos de Guadix", "Cogollos de la Vega", "Cogolludo", "Cogul, El", "Coín", "Coirós", "Colera", "Coles", "Colilla, La", "Colindres", "Coll de Nargó", "Collado", "Collado de Contreras", "Collado del Mirón", "Collado Hermoso", "Collado Mediano", "Collado Villalba", "Collazos de Boedo", "Collbató", "Colldejou", "Collsuspina", "Colmenar", "Colmenar de Montemayor", "Colmenar de Oreja", "Colmenar del Arroyo", "Colmenar Viejo", "Colmenarejo", "Colomera", "Colomers", "Colunga", "Colungo", "Coma i la Pedra, La", "Comares", "Comillas", "Cómpeta", "Conca de Dalt", "Condado de Castilnovo", "Condado de Treviño", "Condemios de Abajo", "Condemios de Arriba", "Conesa", "Confrides", "Congosto", "Congosto de Valdavia", "Congostrina", "Conil de la Frontera", "Conquista", "Conquista de la Sierra", "Consell", "Constantí", "Constantina", "Constanzana", "Consuegra", "Contamina", "Contreras", "Coomonte", "Copernal", "Copons", "Corbalán", "Corbera", "Corbera de Llobregat", "Corbera dEbre", "Corbillos de los Oteros", "Corbins", "Corçà", "Corcos", "Corcubión", "Córdoba", "Cordobilla de Lácara", "Cordovilla", "Cordovilla la Real", "Cordovín", "Corduente", "Corella", "Corera", "Coreses", "Corgo, O", "Coria", "Coria del Río", "Coripe", "Coristanco", "Cornago", "Cornellà de Llobregat", "Cornellà del Terri", "Cornudella de Montsant", "Coronada, La", "Coronil, El", "Corpa", "Corporales", "Corral de Almaguer", "Corral de Ayllón", "Corral de Calatrava", "Corral-Rubio", "Corrales de Buelna, Los", "Corrales de Duero", "Corrales del Vino", "Corrales, Los", "Corte de Peleas", "Corteconcepción", "Cortegada", "Cortegana", "Cortelazor", "Cortes", "Cortes de Aragón", "Cortes de Arenoso", "Cortes de Baza", "Cortes de la Frontera", "Cortes de Pallás", "Cortes y Graena", "Cortijos, Los", "Corullón", "Coruña del Conde", "Coruña, A", "Corvera de Asturias", "Corvera de Toranzo", "Cosa", "Coscurita", "Coslada", "Cospeito", "Costitx", "Costur", "Cosuenda", "Cotanes del Monte", "Cotes", "Cotillas", "Cotobade", "Covaleda", "Covarrubias", "Covelo", "Coves de Vinromà, les", "Cox", "Cózar", "Cozuelos de Fuentidueña", "Crecente", "Creixell", "Crémenes", "Crespià", "Crespos", "Cretas", "Crevillent", "Cristina", "Cristóbal", "Crivillén", "Cruïlles, Monells i Sant Sadurní de lHeura", "Cuacos de Yuste", "Cuadros", "Cualedro", "Cuarte de Huerva", "Cuba, La", "Cubas de la Sagra", "Cubel", "Cubelles", "Cubells", "Cubilla", "Cubillas de Cerrato", "Cubillas de los Oteros", "Cubillas de Rueda", "Cubillas de Santa Marta", "Cubillo", "Cubillo de Uceda, El", "Cubillo del Campo", "Cubillos", "Cubillos del Sil", "Cubla", "Cubo de Benavente", "Cubo de Bureba", "Cubo de Don Sancho, El", "Cubo de la Solana", "Cubo de Tierra del Vino, El", "Cucalón", "Cudillero", "Cuelgamures", "Cuéllar", "Cuenca", "Cuenca de Campos", "Cuerlas, Las", "Cuerva", "Cuervo de Sevilla, El", "Cuervo, El", "Cueva de Ágreda", "Cueva de Roa, La", "Cueva del Hierro", "Cuevas Bajas", "Cuevas de Almudén", "Cuevas de Provanco", "Cuevas de San Clemente", "Cuevas de San Marcos", "Cuevas del Almanzora", "Cuevas del Becerro", "Cuevas del Campo", "Cuevas del Valle", "Cuevas Labradas", "Culla", "Cúllar", "Cúllar Vega", "Cullera", "Culleredo", "Cumbre, La", "Cumbres de Enmedio", "Cumbres de San Bartolomé", "Cumbres Mayores", "Cunit", "Cuntis", "Curiel de Duero", "Curtis", "Cútar", "Cuzcurrita de Río Tirón", "Daganzo de Arriba", "Daimiel", "Daimús", "Dalías", "Darnius", "Daroca", "Daroca de Rioja", "Darro", "Das", "Daya Nueva", "Daya Vieja", "Deba", "Degaña", "Dehesa de Montejo", "Dehesa de Romanos", "Dehesas de Guadix", "Deifontes", "Deleitosa", "Deltebre", "Dénia", "Derio", "Descargamaría", "Desojo", "Destriana", "Dévanos", "Deyá", "Deza", "Dicastillo", "Diego del Carpio", "Diezma", "Dílar", "Dima", "Dios le Guarde", "Dodro", "Dólar", "Dolores", "Domeño", "Domingo García", "Domingo Pérez", "Don Álvaro", "Don Benito", "Donamaria", "Doneztebe/Santesteban", "Donhierro", "Donjimeno", "Donostia-San Sebastián", "Donvidas", "Doña Mencía", "Doñinos de Ledesma", "Doñinos de Salamanca", "Dos Aguas", "Dos Hermanas", "Dos Torres", "Dosbarrios", "Dosrius", "Dozón", "Driebes", "Dúdar", "Dueñas", "Duesaigües", "Dumbría", "Durango", "Dúrcal", "Durón", "Duruelo", "Duruelo de la Sierra", "Ea", "Echarri", "Écija", "Egüés", "Eibar", "Eivissa", "Ejea de los Caballeros", "Ejeme", "Ejido, El", "Ejulve", "Elantxobe", "Elburgo/Burgelu", "Elche de la Sierra", "Elche/Elx", "Elciego", "Elda", "Elduain", "Elgeta", "Elgoibar", "Elgorriaga", "Eliana, l", "Eljas", "Elorrio", "Elvillar/Bilar", "Embid", "Embid de Ariza", "Emperador", "Encina de San Silvestre", "Encina, La", "Encinacorba", "Encinas", "Encinas de Abajo", "Encinas de Arriba", "Encinas de Esgueva", "Encinas Reales", "Encinasola", "Encinasola de los Comendadores", "Encinedo", "Encinillas", "Encío", "Enciso", "Endrinal", "Enériz/Eneritz", "Enguera", "Enguídanos", "Enix", "Ènova, l", "Entrala", "Entrambasaguas", "Entrena", "Entrimo", "Entrín Bajo", "Épila", "Erandio", "Eratsun", "Ercina, La", "Ereño", "Ergoiena", "Erla", "Ermua", "Errenteria", "Errezil", "Erriberagoitia/Ribera Alta", "Errigoiti", "Erro", "Erustes", "Escacena del Campo", "Escala, L", "Escalante", "Escalona", "Escalona del Prado", "Escalonilla", "Escamilla", "Escañuela", "Escarabajosa de Cabezas", "Escariche", "Escatrón", "Escobar de Campos", "Escobar de Polendos", "Escobosa de Almazán", "Escopete", "Escorca", "Escorial, El", "Escorihuela", "Escucha", "Escurial", "Escurial de la Sierra", "Escúzar", "Esgos", "Esguevillas de Esgueva", "Eskoriatza", "Eslava", "Eslida", "Espadaña", "Espadañedo", "Espadilla", "Esparragalejo", "Esparragosa de la Serena", "Esparragosa de Lares", "Esparreguera", "Espartinas", "Esparza de Salazar/Espartza Zaraitzu", "Espeja", "Espeja de San Marcelino", "Espejo", "Espejón", "Espelúy", "Espera", "Espiel", "Espinar, El", "Espinelves", "Espino de la Orbada", "Espinosa de Cerrato", "Espinosa de Cervera", "Espinosa de Henares", "Espinosa de los Caballeros", "Espinosa de los Monteros", "Espinosa de Villagonzalo", "Espinosa del Camino", "Espinoso del Rey", "Espirdo", "Esplegares", "Espluga Calba, L", "Espluga de Francolí, L", "Esplugues de Llobregat", "Esplús", "Espolla", "Esponellà", "Esporles", "Espot", "Espronceda", "Espunyola, L", "Esquivias", "Establés", "Estada", "Estadilla", "Estamariu", "Estany, L", "Estaràs", "Estella-Lizarra", "Estellencs", "Estepa", "Estepa de San Juan", "Estépar", "Estepona", "Estercuel", "Esteribar", "Esterri dÀneu", "Esterri de Cardós", "Estivella", "Estollo", "Estopiñán del Castillo", "Estrada, A", "Estrella, La", "Estremera", "Estriégana", "Estubeny", "Etayo", "Etxalar", "Etxarri-Aranatz", "Etxauri", "Etxebarri", "Etxebarria", "Eulate", "Ezcabarte", "Ezcaray", "Ezcároz/Ezkaroze", "Ezkio-Itsaso", "Ezkurra", "Ezprogui", "Fabara", "Fabero", "Facheca", "Fago", "Falces", "Falset", "Famorca", "Fanlo", "Fanzara", "Far dEmpordà, El", "Faraján", "Faramontanos de Tábara", "Fariza", "Farlete", "Farrera", "Fasnia", "Fatarella, La", "Faura", "Favara", "Fayón", "Fayos, Los", "Febró, La", "Felanitx", "Felix", "Fene", "Férez", "Feria", "Fermoselle", "Fernán Caballero", "Fernán-Núñez", "Ferreira", "Ferreras de Abajo", "Ferreras de Arriba", "Ferreries", "Ferreruela", "Ferreruela de Huerva", "Ferrol", "Figaró-Montmany", "Fígols", "Fígols i Alinyà", "Figuera, La", "Figueres", "Figuerola del Camp", "Figueroles", "Figueruela de Arriba", "Figueruelas", "Fines", "Finestrat", "Fiñana", "Firgas", "Fiscal", "Fisterra", "Fitero", "Flaçà", "Flix", "Flores de Ávila", "Floresta, La", "Florida de Liébana", "Fogars de la Selva", "Fogars de Montclús", "Foios", "Foixà", "Folgoso de la Ribera", "Folgoso do Courel", "Folgueroles", "Fombellida", "Fombuena", "Fompedraza", "Foncea", "Fondarella", "Fondó de les Neus, el/Hondón de las Nieves", "Fondón", "Fonelas", "Fonfría", "Fonfría", "Fonollosa", "Fonsagrada, A", "Font de la Figuera, la", "Font dEn Carròs, la", "Font-rubí", "Fontanals de Cerdanya", "Fontanar", "Fontanarejo", "Fontanars dels Alforins", "Fontanilles", "Fontcoberta", "Fontellas", "Fontihoyuelo", "Fontioso", "Fontiveros", "Fonz", "Fonzaleche", "Foradada", "Foradada del Toscar", "Forallac", "Forcall", "Forcarei", "Forès", "Forfoleda", "Formentera", "Formentera del Segura", "Formiche Alto", "Fornalutx", "Fornells de la Selva", "Fornelos de Montes", "Fórnoles", "Fortaleny", "Fortanete", "Fortià", "Fortuna", "Forua", "Foz", "Foz-Calanda", "Frades", "Frades de la Sierra", "Fraga", "Frago, El", "Frailes", "Franco, El", "Frandovínez", "Franqueses del Vallès, Les", "Frasno, El", "Frechilla", "Frechilla de Almazán", "Fregenal de la Sierra", "Fregeneda, La", "Freginals", "Freila", "Fréscano", "Fresneda de Altarejos", "Fresneda de Cuéllar", "Fresneda de la Sierra", "Fresneda de la Sierra Tirón", "Fresneda, La", "Fresnedilla", "Fresnedillas de la Oliva", "Fresnedoso", "Fresnedoso de Ibor", "Fresneña", "Fresnillo de las Dueñas", "Fresno Alhándiga", "Fresno de Cantespino", "Fresno de Caracena", "Fresno de la Fuente", "Fresno de la Polvorosa", "Fresno de la Ribera", "Fresno de la Vega", "Fresno de Río Tirón", "Fresno de Rodilla", "Fresno de Sayago", "Fresno de Torote", "Fresno del Río", "Fresno el Viejo", "Fresno, El", "Frías", "Frías de Albarracín", "Friera de Valverde", "Frigiliana", "Friol", "Frómista", "Frontera", "Frontera, La", "Fruiz", "Frumales", "Fuembellida", "Fuencaliente", "Fuencaliente de la Palma", "Fuencemillán", "Fuendejalón", "Fuendetodos", "Fuenferrada", "Fuengirola", "Fuenlabrada", "Fuenlabrada de los Montes", "Fuenllana", "Fuenmayor", "Fuensaldaña", "Fuensalida", "Fuensanta", "Fuensanta de Martos", "Fuente Álamo de Murcia", "Fuente de Cantos", "Fuente de Pedro Naharro", "Fuente de Piedra", "Fuente de San Esteban, La", "Fuente de Santa Cruz", "Fuente del Arco", "Fuente del Maestre", "Fuente el Fresno", "Fuente el Olmo de Fuentidueña", "Fuente el Olmo de Íscar", "Fuente el Saúz", "Fuente el Saz de Jarama", "Fuente el Sol", "Fuente Encalada", "Fuente la Lancha", "Fuente la Reina", "Fuente Obejuna", "Fuente Palmera", "Fuente Vaqueros", "Fuente-Álamo", "Fuente-Olmedo", "Fuente-Tójar", "Fuentealbilla", "Fuentearmegil", "Fuentebureba", "Fuentecambrón", "Fuentecantos", "Fuentecén", "Fuenteguinaldo", "Fuenteheridos", "Fuentelahiguera de Albatages", "Fuentelapeña", "Fuentelcésped", "Fuentelencina", "Fuentelespino de Haro", "Fuentelespino de Moya", "Fuenteliante", "Fuentelisendo", "Fuentelmonge", "Fuentelsaz", "Fuentelsaz de Soria", "Fuentelviejo", "Fuentemolinos", "Fuentenava de Jábaga", "Fuentenebro", "Fuentenovilla", "Fuentepelayo", "Fuentepinilla", "Fuentepiñel", "Fuenterrebollo", "Fuenterroble de Salvatierra", "Fuenterrobles", "Fuentes", "Fuentes Calientes", "Fuentes Claras", "Fuentes de Andalucía", "Fuentes de Año", "Fuentes de Ayódar", "Fuentes de Béjar", "Fuentes de Carbajal", "Fuentes de Ebro", "Fuentes de Jiloca", "Fuentes de León", "Fuentes de Magaña", "Fuentes de Nava", "Fuentes de Oñoro", "Fuentes de Ropel", "Fuentes de Rubielos", "Fuentes de Valdepero", "Fuentesaúco", "Fuentesaúco de Fuentidueña", "Fuentesecas", "Fuentesoto", "Fuentespalda", "Fuentespina", "Fuentespreadas", "Fuentestrún", "Fuentidueña", "Fuentidueña de Tajo", "Fuerte del Rey", "Fuertescusa", "Fueva, La", "Fuliola, La", "Fulleda", "Funes", "Fustiñana"}));
        }
        if (jComboBox_filtroAZ1.getSelectedItem().toString().equals("De la G a la S")) {
            jComboBox_poblacion1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Gabaldón", "Gabias, Las", "Gabiria", "Gádor", "Gaià", "Gaianes", "Gaibiel", "Gaintza", "Gajanejos", "Gajates", "Galapagar", "Galápagos", "Galar", "Galaroza", "Galbarros", "Galbárruli", "Galdakao", "Galdames", "Gáldar", "Galende", "Galera", "Galera, La", "Galilea", "Galindo y Perahuy", "Galinduste", "Galisancho", "Galisteo", "Gallardos, Los", "Gallega, La", "Gallegos", "Gallegos de Altamiros", "Gallegos de Argañán", "Gallegos de Hornija", "Gallegos de Sobrinos", "Gallegos de Solmirón", "Gallegos del Pan", "Gallegos del Río", "Gallifa", "Gallinero de Cameros", "Gallipienzo/Galipentzu", "Gallocanta", "Gallués/Galoze", "Gallur", "Galve", "Galve de Sorbe", "Gálvez", "Gamiz-Fika", "Gamones", "Gandesa", "Gandia", "Garaballa", "Garachico", "Garafía", "Garai", "Garaioa", "Garbayuela", "Garcia", "Garciaz", "Garcibuey", "Garcihernández", "Garcillán", "Garciotum", "Garcirrey", "Garde", "Gargallo", "Garganta de los Montes", "Garganta del Villar", "Garganta la Olla", "Garganta, La", "Gargantilla", "Gargantilla del Lozoya y Pinilla de Buitrago", "Gargüera", "Garidells, Els", "Garínoain", "Garlitos", "Garrafe de Torío", "Garralda", "Garray", "Garriga, La", "Garrigàs", "Garrigoles", "Garriguella", "Garrobo, El", "Garrovilla, La", "Garrovillas de Alconétar", "Garrucha", "Garvín", "Gascones", "Gascueña", "Gascueña de Bornova", "Gastor, El", "Gata", "Gata de Gorgos", "Gatika", "Gatón de Campos", "Gátova", "Gaucín", "Gautegiz Arteaga", "Gavà", "Gavarda", "Gavet de la Conca", "Gavilanes", "Gaztelu", "Gea de Albarracín", "Gejuelo del Barro", "Geldo", "Gelida", "Gelsa", "Gelves", "Gema", "Gemuño", "Genalguacil", "Génave", "Genevilla", "Genovés", "Ger", "Gerena", "Gérgal", "Geria", "Gerindote", "Gernika-Lumo", "Gestalgar", "Getafe", "Getaria", "Getxo", "Gibraleón", "Gijón", "Gil García", "Gilbuena", "Gilena", "Gilet", "Gimenells i el Pla de la Font", "Gimialcón", "Gimileo", "Ginebrosa, La", "Gines", "Ginestar", "Gineta, La", "Girona", "Gironella", "Gisclareny", "Gistaín", "Gizaburuaga", "Gobernador", "Godall", "Godella", "Godelleta", "Godojos", "Goizueta", "Gójar", "Golmayo", "Golmés", "Golosalvo", "Golpejas", "Gómara", "Gombrèn", "Gomecello", "Gomesende", "Gomezserracín", "Gondomar", "Goñi", "Gor", "Gorafe", "Gordaliza del Pino", "Gordexola", "Gordo, El", "Gordoncillo", "Gorga", "Gorliz", "Gormaz", "Gósol", "Gotarrendura", "Gotor", "Gozón", "Gradefes", "Grado", "Grado, El", "Graja de Campalbo", "Graja de Iniesta", "Grajal de Campos", "Grajera", "Granada", "Granada de Río-Tinto, La", "Granada, La", "Granadella, La", "Granadilla de Abona", "Granado, El", "Granátula de Calatrava", "Grandas de Salime", "Grandes y San Martín", "Granera", "Granja de la Costera, la", "Granja de Moreruela", "Granja de Rocamora", "Granja de Torrehermosa", "Granja dEscarp, La", "Granja, La", "Granjuela, La", "Granollers", "Granucillo", "Granyanella", "Granyena de les Garrigues", "Granyena de Segarra", "Grañén", "Grañón", "Gratallops", "Graus", "Grávalos", "Grazalema", "Griegos", "Grijalba", "Grijota", "Griñón", "Grisaleña", "Grisel", "Grisén", "Grove, O", "Guadahortuna", "Guadalajara", "Guadalaviar", "Guadalcanal", "Guadalcázar", "Guadalix de la Sierra", "Guadalmez", "Guadalupe", "Guadamur", "Guadarrama", "Guadasséquies", "Guadassuar", "Guadix", "Guadramiro", "Guajares, Los", "Gualba", "Gualchos", "Gualta", "Guancha, La", "Guarda, A", "Guardamar de la Safor", "Guardamar del Segura", "Guardia de Jaén, La", "Guardia, La", "Guardiola de Berguedà", "Guardo", "Guareña", "Guaro", "Guarrate", "Guarromán", "Guaza de Campos", "Gúdar", "Gudiña, A", "Güejar Sierra", "Güeñes", "Güesa/Gorza", "Guesálaz/Gesalatz", "Güevéjar", "Guía de Isora", "Guiamets, Els", "Guijo de Ávila", "Guijo de Coria", "Guijo de Galisteo", "Guijo de Granadilla", "Guijo de Santa Bárbara", "Guijo, El", "Guijuelo", "Guillena", "Guils de Cerdanya", "Güímar", "Guimerà", "Guingueta dÀneu, La", "Guirguillano", "Guisando", "Guissona", "Guitiriz", "Guixers", "Gumiel de Izán", "Gumiel de Mercado", "Guntín", "Gurb", "Guriezo", "Gurrea de Gállego", "Gusendos de los Oteros", "Gutierre-Muñoz", "Haba, La", "Hacinas", "Harana/Valle de Arana", "Haría", "Haro", "Haza", "Hazas de Cesto", "Helechosa de los Montes", "Hellín", "Henarejos", "Henche", "Heras de Ayuso", "Herbés", "Herce", "Herencia", "Herencias, Las", "Herguijuela", "Herguijuela de Ciudad Rodrigo", "Herguijuela de la Sierra", "Herguijuela del Campo", "Hermandad de Campoo de Suso", "Hérmedes de Cerrato", "Hermigua", "Hermisende", "Hernán-Pérez", "Hernani", "Hernansancho", "Hernialde", "Herradón de Pinares", "Herramélluri", "Herrera", "Herrera de Alcántara", "Herrera de los Navarros", "Herrera de Pisuerga", "Herrera de Soria", "Herrera de Valdecañas", "Herrera del Duque", "Herrera, La", "Herrería", "Herrerías", "Herreros de Suso", "Herreruela", "Herreruela de Oropesa", "Herrín de Campos", "Herrumblar, El", "Hervás", "Hervías", "Hiendelaencina", "Higuera", "Higuera de Calatrava", "Higuera de la Serena", "Higuera de la Sierra", "Higuera de las Dueñas", "Higuera de Llerena", "Higuera de Vargas", "Higuera la Real", "Higueras", "Higueruela", "Higueruelas", "Hija de Dios, La", "Híjar", "Hijes", "Hiniesta, La", "Hinojal", "Hinojales", "Hinojares", "Hinojos", "Hinojosa de Duero", "Hinojosa de Jarque", "Hinojosa de San Vicente", "Hinojosa del Campo", "Hinojosa del Duque", "Hinojosa del Valle", "Hinojosa, La", "Hinojosas de Calatrava", "Hinojosos, Los", "Hiriberri/Villanueva de Aezkoa", "Hiruela, La", "Hita", "Hito, El", "Holguera", "Hombrados", "Hondarribia", "Hondón de los Frailes", "Honrubia", "Honrubia de la Cuesta", "Hontalbilla", "Hontanar", "Hontanares de Eresma", "Hontanas", "Hontanaya", "Hontangas", "Hontecillas", "Hontoba", "Hontoria de Cerrato", "Hontoria de la Cantera", "Hontoria de Valdearados", "Hontoria del Pinar", "Horcajada, La", "Horcajo de la Sierra-Aoslos", "Horcajo de las Torres", "Horcajo de los Montes", "Horcajo de Montemayor", "Horcajo de Santiago", "Horcajo Medianero", "Horcajuelo de la Sierra", "Horche", "Hormazas, Las", "Hormigos", "Hormilla", "Hormilleja", "Hornachos", "Hornachuelos", "Hornillo, El", "Hornillos de Cameros", "Hornillos de Cerrato", "Hornillos de Eresma", "Hornillos del Camino", "Hornos", "Hornos de Moncalvillo", "Horra, La", "Horta de Sant Joan", "Hortezuela de Océn", "Hortigüela", "Hospital de Órbigo", "Hospitalet de Llobregat, L", "Hostalets de Pierola, Els", "Hostalric", "Hoya-Gonzalo", "Hoya, La", "Hoyales de Roa", "Hoyo de Manzanares", "Hoyo de Pinares, El", "Hoyocasero", "Hoyorredondo", "Hoyos", "Hoyos de Miguel Muñoz", "Hoyos del Collado", "Hoyos del Espino", "Hoz de Jaca", "Hoz de la Vieja, La", "Hoz y Costean", "Huarte/Uharte", "Huecas", "Huécija", "Huélaga", "Huélago", "Huélamo", "Huelma", "Huelva", "Huelves", "Huéneja", "Huércal de Almería", "Huércal-Overa", "Huércanos", "Huerce, La", "Huérguina", "Huérmeces", "Huérmeces del Cerro", "Huerta", "Huerta de Arriba", "Huerta de la Obispalía", "Huerta de Rey", "Huerta de Valdecarábanos", "Huerta del Marquesado", "Huertahernando", "Huerto", "Huertos, Los", "Huesa", "Huesa del Común", "Huesca", "Huéscar", "Huete", "Huétor de Santillán", "Huétor Tájar", "Huétor Vega", "Hueva", "Huévar del Aljarafe", "Humada", "Humanes", "Humanes de Madrid", "Humilladero", "Hurones", "Hurtumpascual", "Husillos", "Ibahernando", "Ibargoiti", "Ibarra", "Ibarrangelu", "Ibdes", "Ibeas de Juarros", "Ibi", "Ibias", "Ibieca", "Ibrillos", "Ibros", "Icod de los Vinos", "Idiazabal", "Igantzi", "Igea", "Iglesiarrubia", "Iglesias", "Iglesuela del Cid, La", "Iglesuela, La", "Igorre", "Igriés", "Igualada", "Igualeja", "Igüeña", "Igúzquiza", "Ikaztegieta", "Ilche", "Illa de Arousa, A", "Illán de Vacas", "Illana", "Illano", "Illar", "Illas", "Illescas", "Illora", "Illueca", "Imotz", "Inca", "Incio, O", "Ingenio", "Iniesta", "Iniéstola", "Instinción", "Inviernas, Las", "Irañeta", "Irixo, O", "Irixoa", "Iruela, La", "Iruelos", "Irueste", "Irun", "Iruña Oka/Iruña de Oca", "Irura", "Iruraiz-Gauna", "Irurtzun", "Isaba/Izaba", "Isábena", "Isar", "Íscar", "Isla Cristina", "Isla Mayor", "Isona i Conca Dellà", "Isòvol", "Ispaster", "Istán", "Isuerre", "Itero de la Vega", "Itero del Castillo", "Itrabo", "Itsasondo", "Ituero de Azaba", "Ituero y Lama", "Ituren", "Iturmendi", "Iurreta", "Ivars de Noguera", "Ivars dUrgell", "Ivorra", "Iza/Itza", "Izagaondoa", "Izagre", "Izalzu/Itzaltzu", "Iznájar", "Iznalloz", "Iznate", "Iznatoraf", "Izurtza", "Jabaloyas", "Jabalquinto", "Jabugo", "Jaca", "Jacarilla", "Jadraque", "Jaén", "Jafre", "Jalance", "Jalón de Cameros", "Jambrina", "Jamilena", "Jana, la", "Jaraba", "Jarafuel", "Jaraicejo", "Jaraíz de la Vera", "Jaramillo de la Fuente", "Jaramillo Quemado", "Jarandilla de la Vera", "Jarilla", "Jarque", "Jarque de la Val", "Jasa", "Jatiel", "Jaulín", "Jaurrieta", "Jávea/Xàbia", "Javier", "Jayena", "Jerez de la Frontera", "Jerez de los Caballeros", "Jerez del Marquesado", "Jérica", "Jerte", "Jete", "Jijona/Xixona", "Jimena", "Jimena de la Frontera", "Jimera de Líbar", "Jirueque", "Joarilla de las Matas", "Jódar", "Jonquera, La", "Jorba", "Jorcas", "Jorquera", "Josa", "Josa i Tuixén", "Joyosa, La", "Juarros de Riomoros", "Juarros de Voltoya", "Jubrique", "Juià", "Jumilla", "Jun", "Junciana", "Juncosa", "Juneda", "Junta de Traslaloma", "Junta de Villalba de Losa", "Jurisdicción de Lara", "Jurisdicción de San Zadornil", "Juslapeña", "Justel", "Juviles", "Juzbado", "Júzcar", "Karrantza Harana/Valle de Carranza", "Kortezubi", "Kripan", "Kuartango", "Labajos", "Labastida/Bastida", "Labores, Las", "Labuerda", "Láchar", "Ladrillar", "Lagartera", "Lagartos", "Lagata", "Lagrán", "Laguardia", "Lagueruela", "Laguna Dalga", "Laguna de Cameros", "Laguna de Contreras", "Laguna de Duero", "Laguna de Negrillos", "Laguna del Marquesado", "Lagunaseca", "Lagunilla", "Lagunilla del Jubera", "Lahiguera", "Lakuntza", "Lalín", "Laluenga", "Lalueza", "Lama, A", "Lamasón", "Lana", "Lanaja", "Láncara", "Lanciego/Lantziego", "Landete", "Lanestosa", "Langa", "Langa de Duero", "Langa del Castillo", "Langayo", "Langreo", "Languilla", "Lanjarón", "Lantadilla", "Lantarón", "Lanteira", "Lantejuela, La", "Lantz", "Lanzahíta", "Lanzuela", "Lapa, La", "Laperdiguera", "Lapoblación", "Lapuebla de Labarca", "Laracha, A", "Lardero", "Laredo", "Larouco", "Laroya", "Larrabetzu", "Larraga", "Larraona", "Larraul", "Larraun", "Larrodrigo", "Larva", "Lasarte-Oria", "Lascellas-Ponzano", "Lascuarre", "Laspaúles", "Laspuña", "Lastras de Cuéllar", "Lastras del Pozo", "Lastrilla, La", "Laudio/Llodio", "Láujar de Andarax", "Laukiz", "Laviana", "Laxe", "Layana", "Layos", "Laza", "Lazagurría", "Lazkao", "Leaburu", "Leache", "Lebrija", "Lécera", "Lechón", "Leciñena", "Lecrín", "Ledanca", "Ledaña", "Ledesma", "Ledesma de la Cogolla", "Ledigos", "Ledrada", "Leganés", "Leganiel", "Legarda", "Legaria", "Legazpi", "Legorreta", "Legutio", "Leintz-Gatzaga", "Leioa", "Leiro", "Leitza", "Leiva", "Lekeitio", "Lekunberri", "Lemoa", "Lemoiz", "Lena", "Lentegí", "León", "Leoz/Leotz", "Lepe", "Lerga", "Lerín", "Lerma", "Les", "Lesaka", "Letur", "Letux", "Leza", "Leza de Río Leza", "Lezama", "Lezáun", "Lezo", "Lezuza", "Librilla", "Libros", "Liceras", "Lidón", "Liédena", "Liendo", "Liérganes", "Liétor", "Líjar", "Lillo", "Limpias", "Linares", "Linares de la Sierra", "Linares de Mora", "Linares de Riofrío", "Línea de la Concepción, La", "Linyola", "Litago", "Lituénigo", "Lizartza", "Lizoáin-Arriasgoiti", "Llacuna, La", "Lladó", "Lladorre", "Lladurs", "Llagosta, La", "Llagostera", "Llamas de la Ribera", "Llambilles", "Llanars", "Llançà", "Llanera", "Llanera de Ranes", "Llanes", "Llano de Bureba", "Llano de Olmedo", "Llanos de Aridane, Los", "Llanos de Tormes, Los", "Llanos del Caudillo", "Llardecans", "Llaurí", "Llavorsí", "Lledó", "Lleida", "Llera", "Llerena", "Llers", "Lles de Cerdanya", "Llíber", "Lliçà dAmunt", "Lliçà de Vall", "Llimiana", "Llinars del Vallès", "Llíria", "Llívia", "Lloar, El", "Llobera", "Llocnou de la Corona", "Llocnou de Sant Jeroni", "Llocnou dEn Fenollet", "Llombai", "Llorac", "Llorenç del Penedès", "Lloret de Mar", "Lloret de Vistalegre", "Llosa de Ranes, la", "Llosa, la", "Lloseta", "Llosses, Les", "Llubí", "Lluçà", "Llucmajor", "Llutxent", "Loarre", "Lobeira", "Lobera de Onsella", "Lobios", "Lobón", "Lobras", "Lodosa", "Loeches", "Logroño", "Logrosán", "Loiu", "Loja", "Loma de Ucieza", "Lomas", "Lominchar", "Lomoviejo", "Longares", "Longás", "Lónguida/Longida", "Lopera", "Loporzano", "Lora de Estepa", "Lora del Río", "Loranca de Tajuña", "Lorca", "Lorcha/Orxa, l", "Loriguilla", "Lorquí", "Losa del Obispo", "Losa, La", "Losacino", "Losacio", "Losar de la Vera", "Losar del Barco, El", "Loscorrales", "Loscos", "Losilla, La", "Lourenzá", "Lousame", "Lozoya", "Lozoyuela-Navas-Sieteiglesias", "Lubián", "Lubrín", "Lucainena de las Torres", "Lúcar", "Lucena", "Lucena de Jalón", "Lucena del Cid", "Lucena del Puerto", "Luceni", "Luciana", "Lucillo", "Lucillos", "Ludiente", "Luelmo", "Luena", "Luesia", "Luesma", "Lugo", "Lugros", "Luisiana, La", "Lújar", "Lumbier", "Lumbrales", "Lumbreras", "Lumpiaque", "Luna", "Lupiana", "Lupiñén-Ortilla", "Lupión", "Luque", "Luquin", "Luyego", "Luzaga", "Luzaide/Valcarlos", "Luzón", "Macael", "Maçanet de Cabrenys", "Maçanet de la Selva", "Macastre", "Maceda", "Machacón", "Macharaviaya", "Macotera", "Madarcos", "Maderal, El", "Maderuelo", "Madremanya", "Madrid", "Madridanos", "Madridejos", "Madrigal de la Vera", "Madrigal de las Altas Torres", "Madrigal del Monte", "Madrigalejo", "Madrigalejo del Monte", "Madrigueras", "Madroñal", "Madroñera", "Madroño, El", "Maella", "Maello", "Magacela", "Magallón", "Magán", "Magaña", "Magaz de Cepeda", "Magaz de Pisuerga", "Maguilla", "Mahamud", "Mahide", "Mahora", "Maià de Montcal", "Maials", "Maicas", "Maíllo, El", "Mainar", "Maire de Castroponce", "Mairena del Alcor", "Mairena del Aljarafe", "Majadahonda", "Majadas", "Majadas, Las", "Majaelrayo", "Maján", "Málaga", "Málaga del Fresno", "Malagón", "Malaguilla", "Malahá, La", "Malanquilla", "Malcocinado", "Maldà", "Maleján", "Malgrat de Mar", "Malla", "Mallabia", "Mallén", "Malón", "Malpartida", "Malpartida de Cáceres", "Malpartida de Corneja", "Malpartida de la Serena", "Malpartida de Plasencia", "Malpica de Bergantiños", "Malpica de Tajo", "Maluenda", "Malva", "Mamblas", "Mambrilla de Castrejón", "Mambrillas de Lara", "Mamolar", "Manacor", "Mancera de Abajo", "Mancera de Arriba", "Mancha Real", "Manchita", "Manchones", "Manciles", "Mancor de la Vall", "Mandayona", "Manganeses de la Lampreana", "Manganeses de la Polvorosa", "Manilva", "Manises", "Manjabálago", "Manjarrés", "Manlleu", "Manquillos", "Manresa", "Mansilla de la Sierra", "Mansilla de las Mulas", "Mansilla Mayor", "Mantiel", "Mantinos", "Manuel", "Manzanal de Arriba", "Manzanal de los Infantes", "Manzanal del Barco", "Manzanares", "Manzanares de Rioja", "Manzanares el Real", "Manzaneda", "Manzaneque", "Manzanera", "Manzanilla", "Manzanillo", "Manzano, El", "Mañaria", "Mañeru", "Mañón", "Maó", "Maqueda", "Mara", "Maracena", "Maranchón", "Maraña", "Marañón", "Marazoleja", "Marazuela", "Marbella", "Marçà", "Marchagaz", "Marchal", "Marchamalo", "Marchena", "Marcilla", "Marcilla de Campos", "Margalef", "Marganell", "María", "María de Huerva", "Maria de la Salut", "Mariana", "Marín", "Marina de Cudeyo", "Marinaleda", "Marines", "Marines, Los", "Marjaliza", "Markina-Xemein", "Marlín", "Marmolejo", "Marracos", "Marratxí", "Marrupe", "Martiago", "Martiherrero", "Martín de la Jara", "Martín de Yeltes", "Martín del Río", "Martín Miguel", "Martín Muñoz de la Dehesa", "Martín Muñoz de las Posadas", "Martinamor", "Martínez", "Martorell", "Martorelles", "Martos", "Marugán", "Maruri-Jatabe", "Marzales", "Mas de Barberans", "Mas de las Matas", "Masarac", "Mascaraque", "Masdenverge", "Masegosa", "Masegoso", "Masegoso de Tajuña", "Maside", "Masies de Roda, Les", "Masies de Voltregà, Les", "Masllorenç", "Masnou, El", "Masó, La", "Maspujols", "Masquefa", "Masroig, El", "Massalavés", "Massalcoreig", "Massalfassar", "Massamagrell", "Massanassa", "Massanes", "Massoteres", "Masueco", "Mata de Alcántara", "Mata de Cuéllar", "Mata de Ledesma, La", "Mata de los Olmos, La", "Mata de Morella, la", "Mata, La", "Matabuena", "Matadeón de los Oteros", "Matadepera", "Matalebreras", "Matallana de Torío", "Matamala de Almazán", "Matanza", "Matanza de Acentejo, La", "Matapozuelos", "Mataró", "Matarrubia", "Matet", "Matilla de Arzón", "Matilla de los Caños", "Matilla de los Caños del Río", "Matilla la Seca", "Matilla, La", "Matillas", "Matute", "Maya, La", "Mayalde", "Mayorga", "Mazaleón", "Mazarambroz", "Mazarete", "Mazaricos", "Mazariegos", "Mazarrón", "Mazcuerras", "Mazuecos", "Mazuecos de Valdeginate", "Mazuela", "Meaño", "Mecerreyes", "Meco", "Medellín", "Mediana de Aragón", "Mediana de Voltoya", "Medina de las Torres", "Medina de Pomar", "Medina de Rioseco", "Medina del Campo", "Medina-Sidonia", "Medinaceli", "Medinilla", "Medio Cudeyo", "Mediona", "Medranda", "Medrano", "Megeces", "Megina", "Meira", "Meis", "Mejorada", "Mejorada del Campo", "Melgar de Abajo", "Melgar de Arriba", "Melgar de Fernamental", "Melgar de Tera", "Melgar de Yuso", "Meliana", "Mélida", "Melide", "Melilla", "Melón", "Melque de Cercos", "Membibre de la Hoz", "Membribe de la Sierra", "Membrilla", "Membrillera", "Membrío", "Menàrguens", "Menasalbas", "Mendaro", "Mendata", "Mendavia", "Mendaza", "Mendexa", "Mendigorría", "Meneses de Campos", "Mengabril", "Mengamuñoz", "Mengíbar", "Méntrida", "Meñaka", "Mequinenza", "Meranges", "Merca, A", "Mercadal, Es", "Mérida", "Merindad de Cuesta-Urria", "Merindad de Montija", "Merindad de Río Ubierna", "Merindad de Sotoscueva", "Merindad de Valdeporres", "Merindad de Valdivielso", "Meruelo", "Mesas de Ibor", "Mesas, Las", "Mesegar de Corneja", "Mesegar de Tajo", "Mesía", "Mesones de Isuela", "Mestanza", "Metauten", "Mezalocha", "Mezquita de Jarque", "Mezquita, A", "Miajadas", "Mianos", "Micereces de Tera", "Micieces de Ojeda", "Miedes de Aragón", "Miedes de Atienza", "Miengo", "Miera", "Mieres", "Mieres", "Mierla, La", "Mieza", "Migjorn Gran, Es", "Miguel Esteban", "Migueláñez", "Miguelturra", "Mijares", "Mijas", "Milà, El", "Milagro", "Milagros", "Milano, El", "Millana", "Millanes", "Millares", "Millena", "Milles de la Polvorosa", "Milmarcos", "Minas de Riotinto", "Minaya", "Minglanilla", "Mingorría", "Miño", "Miño de Medinaceli", "Miño de San Esteban", "Miñosa, La", "Mira", "Mirabel", "Mirabueno", "Miraflores de la Sierra", "Mirafuentes", "Miralcamp", "Miralrío", "Miramar", "Mirambel", "Miranda de Arga", "Miranda de Azán", "Miranda de Ebro", "Miranda del Castañar", "Mirandilla", "Miraveche", "Miravet", "Miravete de la Sierra", "Mirón, El", "Mironcillo", "Mirueña de los Infanzones", "Mislata", "Moaña", "Mocejón", "Mochales", "Moclín", "Moclinejo", "Modúbar de la Emparedada", "Moeche", "Mogán", "Mogarraz", "Mogente/Moixent", "Moguer", "Mohedas de Granadilla", "Mohedas de la Jara", "Mohernando", "Moià", "Mojácar", "Mojados", "Mojonera, La", "Molacillos", "Molar, El", "Molar, El", "Molares, Los", "Molezuelas de la Carballeda", "Molina de Aragón", "Molina de Segura", "Molinaseca", "Molinicos", "Molinillo", "Molinos", "Molinos de Duero", "Molinos, Los", "Molins de Rei", "Molledo", "Mollerussa", "Mollet de Peralada", "Mollet del Vallès", "Mollina", "Molló", "Molsosa, La", "Molvízar", "Mombeltrán", "Momblona", "Mombuey", "Monachil", "Monasterio", "Monasterio de la Sierra", "Monasterio de Rodilla", "Monasterio de Vega", "Moncada", "Moncalvillo", "Moncofa", "Monda", "Mondariz", "Mondariz-Balneario", "Mondéjar", "Mondoñedo", "Monegrillo", "Monesma y Cajigar", "Monesterio", "Moneva", "Monfarracinos", "Monfero", "Monflorite-Lascasas", "Monforte de la Sierra", "Monforte de Lemos", "Monforte de Moyuela", "Monforte del Cid", "Monistrol de Calders", "Monistrol de Montserrat", "Monleón", "Monleras", "Monóvar/Monòver", "Monreal de Ariza", "Monreal del Campo", "Monreal del Llano", "Monreal/Elo", "Monroy", "Monroyo", "Monsagro", "Monsalupe", "Mont-ral", "Mont-ras", "Mont-roig del Camp", "Montagut i Oix", "Montalbán", "Montalbán de Córdoba", "Montalbanejo", "Montalbo", "Montalvos", "Montamarta", "Montán", "Montánchez", "Montanejos", "Montanuy", "Montarrón", "Montaverner", "Montblanc", "Montbrió del Camp", "Montcada i Reixac", "Montclar", "Monteagudo", "Monteagudo de las Salinas", "Monteagudo de las Vicarías", "Monteagudo del Castillo", "Montealegre de Campos", "Montealegre del Castillo", "Montearagón", "Montederramo", "Montefrío", "Montehermoso", "Montejaque", "Montejícar", "Montejo", "Montejo de Arévalo", "Montejo de la Sierra", "Montejo de la Vega de la Serrezuela", "Montejo de Tiermes", "Montellà i Martinet", "Montellano", "Montemayor", "Montemayor de Pililla", "Montemayor del Río", "Montemolín", "Montenegro de Cameros", "Monterde", "Monterde de Albarracín", "Monterrei", "Monterroso", "Monterrubio", "Monterrubio de Armuña", "Monterrubio de la Demanda", "Monterrubio de la Serena", "Monterrubio de la Sierra", "Montesa", "Montesclaros", "Montesinos, Los", "Montesquiu", "Montferrer i Castellbò", "Montferri", "Montgai", "Montgat", "Montiel", "Montijo", "Montilla", "Montillana", "Montitxelvo/Montichelvo", "Montizón", "Montmajor", "Montmaneu", "Montmell, El", "Montmeló", "Montoliu de Lleida", "Montoliu de Segarra", "Montón", "Montorio", "Montornès de Segarra", "Montornès del Vallès", "Montoro", "Montroy", "Montseny", "Montserrat", "Montuïri", "Monturque", "Monzón", "Monzón de Campos", "Mora", "Mora de Rubielos", "Móra dEbre", "Móra la Nova", "Moradillo de Roa", "Moral de Calatrava", "Moral de Hornuez", "Moral de la Reina", "Moral de Sayago", "Moraleda de Zafayona", "Moraleja", "Moraleja de Enmedio", "Moraleja de las Panaderas", "Moraleja de Matacabras", "Moraleja de Sayago", "Moraleja del Vino", "Morales de Campos", "Morales de Rey", "Morales de Toro", "Morales de Valverde", "Morales del Vino", "Moralina", "Moralzarzal", "Moraña", "Morasverdes", "Morata de Jalón", "Morata de Jiloca", "Morata de Tajuña", "Moratalla", "Moratilla de los Meleros", "Moratinos", "Morcillo", "Morcín", "Moreda de Álava/Moreda Araba", "Morelábor", "Morell, El", "Morella", "Morenilla", "Morentin", "Morera de Montsant, La", "Morera, La", "Moreruela de los Infanzones", "Moreruela de Tábara", "Morés", "Morga", "Moriles", "Morille", "Moríñigo", "Moriscos", "Morón de Almazán", "Morón de la Frontera", "Moronta", "Moros", "Mos", "Moscardón", "Mosqueruela", "Móstoles", "Mota de Altarejos", "Mota del Cuervo", "Mota del Marqués", "Motilla del Palancar", "Motilleja", "Motril", "Moya", "Moya", "Moyuela", "Mozárbez", "Mozoncillo", "Mozota", "Mucientes", "Mudá", "Mudarra, La", "Muduex", "Muel", "Muela, La", "Muelas de los Caballeros", "Muelas del Pan", "Mues", "Muga de Sayago", "Mugardos", "Muíños", "Mula", "Mundaka", "Munébrega", "Munera", "Mungia", "Muniesa", "Munilla", "Munitibar-Arbatzegi Gerrikaitz", "Muntanyola", "Muñana", "Muñico", "Muñogalindo", "Muñogrande", "Muñomer del Peco", "Muñopedro", "Muñopepe", "Muñosancho", "Muñotello", "Muñoveros", "Mura", "Muras", "Murchante", "Murcia", "Murero", "Murias de Paredes", "Muriel", "Muriel de la Fuente", "Muriel Viejo", "Murieta", "Murillo de Gállego", "Murillo de Río Leza", "Murillo el Cuende", "Murillo el Fruto", "Murla", "Muro", "Muro de Aguas", "Muro de Alcoy", "Muro en Cameros", "Muros", "Muros de Nalón", "Murtas", "Murueta", "Muruzábal", "Museros", "Muskiz", "Mutiloa", "Mutriku", "Mutxamel", "Muxía", "Muxika", "Nabarniz", "Nacimiento", "Nafría de Ucero", "Nájera", "Nalda", "Nalec", "Nambroca", "Náquera", "Narboneta", "Narón", "Narrillos del Álamo", "Narrillos del Rebollar", "Narros", "Narros de Matalayegua", "Narros de Saldueña", "Narros del Castillo", "Narros del Puerto", "Naut Aran", "Nava", "Nava de Arévalo", "Nava de Béjar", "Nava de Francia", "Nava de la Asunción", "Nava de Ricomalillo, La", "Nava de Roa", "Nava de Santiago, La", "Nava de Sotrobal", "Nava del Barco", "Nava del Rey", "Nava, La", "Navacarros", "Navacepedilla de Corneja", "Navacerrada", "Navaconcejo", "Navadijos", "Navaescurial", "Navafría", "Navahermosa", "Navahondilla", "Navajas", "Navajún", "Naval", "Navalacruz", "Navalafuente", "Navalagamella", "Navalcán", "Navalcarnero", "Navaleno", "Navales", "Navalilla", "Navalmanzano", "Navalmoral", "Navalmoral de Béjar", "Navalmoral de la Mata", "Navalmoralejo", "Navalmorales, Los", "Navalonguilla", "Navalosa", "Navalperal de Pinares", "Navalperal de Tormes", "Navalpino", "Navalucillos, Los", "Navaluenga", "Navalvillar de Ibor", "Navalvillar de Pela", "Navamorales", "Navamorcuende", "Navaquesera", "Navarcles", "Navardún", "Navares de Ayuso", "Navares de Enmedio", "Navares de las Cuevas", "Navaridas", "Navarredonda de Gredos", "Navarredonda de la Rinconada", "Navarredonda y San Mamés", "Navarredondilla", "Navarrés", "Navarrete", "Navarrevisca", "Navàs", "Navas de Bureba", "Navas de Estena", "Navas de Jadraque, Las", "Navas de Jorquera", "Navas de la Concepción, Las", "Navas de Oro", "Navas de Riofrío", "Navas de San Antonio", "Navas de San Juan", "Navas del Madroño", "Navas del Marqués, Las", "Navas del Rey", "Navascués/Nabaskoze", "Navasfrías", "Navata", "Navatalgordo", "Navatejares", "Navès", "Navezuelas", "Navia", "Navia de Suarna", "Navianos de Valverde", "Nazar", "Nebreda", "Neda", "Negredo", "Negreira", "Negrilla de Palencia", "Negueira de Muñiz", "Neila", "Neila de San Miguel", "Nepas", "Nerja", "Nerpio", "Nerva", "Nestares", "Nevada", "Neves, As", "Niebla", "Nieva", "Nieva de Cameros", "Nigrán", "Nigüelas", "Nigüella", "Niharra", "Níjar", "Nívar", "Noáin (Valle de Elorz)/Noain (Elortzibar)", "Noalejo", "Noblejas", "Noceda del Bierzo", "Noez", "Nogais, As", "Nogal de las Huertas", "Nogales", "Nogueira de Ramuín", "Noguera de Albarracín", "Nogueras", "Nogueruelas", "Noia", "Noja", "Nolay", "Nombela", "Nombrevilla", "Nonaspe", "Noreña", "Nou de Berguedà, La", "Nou de Gaià, La", "Novales", "Novallas", "Novelda", "Novelé/Novetlè", "Novés", "Noviercas", "Novillas", "Nucia, la", "Nueno", "Nueva Carteya", "Nueva Villa de las Torres", "Nuévalos", "Nuevo Baztán", "Nuez de Ebro", "Nules", "Nulles", "Numancia de la Sagra", "Nuño Gómez", "Nuñomoral", "Obanos", "Obejo", "Obón", "Ocaña", "Ocentejo", "Ochagavía/Otsagabia", "Ochánduri", "Oco", "Ocón", "Odèn", "Òdena", "Odieta", "Odón", "Oencia", "Ogassa", "Ogíjares", "Ohanes", "Oia", "Oiartzun", "Oímbra", "Oitz", "Ojacastro", "Ojén", "Ojós", "Ojos Negros", "Ojos-Albos", "Okondo", "Olaberria", "Olaibar", "Olazti/Olazagutía", "Olba", "Olea de Boedo", "Oleiros", "Olejua", "Olèrdola", "Olesa de Bonesvalls", "Olesa de Montserrat", "Oliana", "Olías del Rey", "Oliete", "Oliola", "Olite/Erriberri", "Olius", "Oliva", "Oliva de la Frontera", "Oliva de Mérida", "Oliva de Plasencia", "Oliva, La", "Olivar, El", "Olivares", "Olivares de Duero", "Olivares de Júcar", "Olivella", "Olivenza", "Ollauri", "Olleria, l", "Ollo", "Olmeda de Cobeta", "Olmeda de Jadraque, La", "Olmeda de la Cuesta", "Olmeda de las Fuentes", "Olmeda del Rey", "Olmedilla de Alarcón", "Olmedilla de Eliz", "Olmedillo de Roa", "Olmedo", "Olmedo de Camaces", "Olmillos de Castro", "Olmillos de Muñó", "Olmos de Esgueva", "Olmos de Ojeda", "Olmos de Peñafiel", "Olmos, Los", "Olocau", "Olocau del Rey", "Olombrada", "Olóriz/Oloritz", "Olost", "Olot", "Oluges, Les", "Olula de Castro", "Olula del Río", "Olvan", "Ólvega", "Olvena", "Olvera", "Olvés", "Omañas, Las", "Omellons, Els", "Omells de na Gaia, Els", "Oncala", "Onda", "Ondara", "Ondarroa", "Onil", "Onís", "Ontígola", "Ontinyent", "Ontiñena", "Ontur", "Onzonilla", "Oña", "Oñati", "Oquillas", "Orba", "Orbada, La", "Orbaizeta", "Orbaneja Riopico", "Orbara", "Orbita", "Orcajo", "Orce", "Orcera", "Ordes", "Ordial, El", "Ordis", "Ordizia", "Orea", "Orejana", "Orellana de la Sierra", "Orellana la Vieja", "Orendain", "Orera", "Orés", "Orexa", "Organyà", "Orgaz", "Órgiva", "Oria", "Orihuela", "Orihuela del Tremedal", "Orio", "Orís", "Orísoain", "Oristà", "Orkoien", "Ormaiztegi", "Oronz/Orontze", "Oropesa", "Oropesa del Mar/Orpesa", "Oroso", "Orotava, La", "Oroz-Betelu/Orotz-Betelu", "Orozko", "Orpí", "Orreaga/Roncesvalles", "Orrios", "Òrrius", "Ortigosa de Cameros", "Ortigosa de Pestaño", "Ortigosa del Monte", "Ortigueira", "Ortuella", "Orusco de Tajuña", "Orxeta", "Os de Balaguer", "Osa de la Vega", "Oseja", "Oseja de Sajambre", "Osera de Ebro", "Oso, El", "Osor", "Osornillo", "Osorno la Mayor", "Ossa de Montiel", "Osso de Cinca", "Ossó de Sió", "Osuna", "Oteiza", "Otero", "Otero de Bodas", "Otero de Herreros", "Otívar", "Otos", "Otura", "Otxandio", "Ourense", "Ourol", "Outeiro de Rei", "Outes", "Oviedo", "Oyón-Oion", "Oza dos Ríos", "Pacs del Penedès", "Paderne", "Paderne de Allariz", "Padiernos", "Padilla de Abajo", "Padilla de Arriba", "Padrenda", "Padrón", "Padrones de Bureba", "Padul", "Padules", "Paiporta", "Pájara", "Pajarejos", "Pajares de Adaja", "Pajares de la Laguna", "Pajares de la Lampreana", "Pajares de los Oteros", "Pajarón", "Pajaroncillo", "Palacios de Goda", "Palacios de la Sierra", "Palacios de la Valduerna", "Palacios de Riopisuerga", "Palacios de Sanabria", "Palacios del Arzobispo", "Palacios del Pan", "Palacios del Sil", "Palacios y Villafranca, Los", "Palaciosrubios", "Palafolls", "Palafrugell", "Palamós", "Palanques", "Palas de Rei", "Palau dAnglesola, El", "Palau de Santa Eulàlia", "Palau-sator", "Palau-saverdera", "Palau-solità i Plegamans", "Palazuelo de Vedija", "Palazuelos de Eresma", "Palazuelos de la Sierra", "Palazuelos de Muñó", "Palencia", "Palencia de Negrilla", "Palenciana", "Palenzuela", "Pallaresos, Els", "Pallejà", "Palma", "Palma de Cervelló, La", "Palma de Gandía", "Palma dEbre, La", "Palma del Condado, La", "Palma del Río", "Pálmaces de Jadraque", "Palmas de Gran Canaria, Las", "Palmera", "Palo", "Palol de Revardit", "Palomar de Arroyos", "Palomar, el", "Palomares del Campo", "Palomares del Río", "Palomas", "Palomeque", "Palomera", "Palomero", "Palos de la Frontera", "Pals", "Pampaneira", "Pampliega", "Pamplona/Iruña", "Pancorbo", "Pancrudo", "Paniza", "Panticosa", "Pantoja", "Pantón", "Papatrigo", "Papiol, El", "Paracuellos", "Paracuellos de Jarama", "Paracuellos de Jiloca", "Paracuellos de la Ribera", "Parada de Arriba", "Parada de Rubiales", "Parada de Sil", "Paradas", "Paradela", "Paradinas de San Juan", "Páramo de Boedo", "Páramo del Sil", "Páramo, O", "Parauta", "Parcent", "Pardilla", "Pardines", "Pardos", "Paredes", "Paredes de Escalona", "Paredes de Nava", "Paredes de Sigüenza", "Pareja", "Parets del Vallès", "Parla", "Parlavà", "Parra de las Vegas, La", "Parra, La", "Parral, El", "Parras de Castellote, Las", "Parres", "Parrilla, La", "Parrillas", "Partaloa", "Partido de la Sierra en Tobalina", "Pasaia", "Pasarón de la Vera", "Pascualcobo", "Paso, El", "Passanant i Belltall", "Pastores", "Pastoriza, A", "Pastrana", "Pastriz", "Paterna", "Paterna de Rivera", "Paterna del Campo", "Paterna del Madera", "Paterna del Río", "Patones", "Pau", "Paüls", "Pavías", "Paymogo", "Payo de Ojeda", "Payo, El", "Pazos de Borbén", "Pazuengos", "Peal de Becerro", "Pechina", "Pedernoso, El", "Pedrafita do Cebreiro", "Pedraja de Portillo, La", "Pedrajas de San Esteban", "Pedralba", "Pedralba de la Pradería", "Pedraza", "Pedraza de Alba", "Pedraza de Campos", "Pedregal, El", "Pedreguer", "Pedrera", "Pedret i Marzà", "Pedrezuela", "Pedro Abad", "Pedro Bernardo", "Pedro Martínez", "Pedro Muñoz", "Pedro-Rodríguez", "Pedroche", "Pedrola", "Pedroñeras, Las", "Pedrosa de Duero", "Pedrosa de la Vega", "Pedrosa de Río Úrbel", "Pedrosa del Páramo", "Pedrosa del Príncipe", "Pedrosa del Rey", "Pedrosas, Las", "Pedrosillo de Alba", "Pedrosillo de los Aires", "Pedrosillo el Ralo", "Pedroso", "Pedroso de Acim", "Pedroso de la Armuña, El", "Pedroso, El", "Pegalajar", "Pego", "Pego, El", "Peguerinos", "Pelabravo", "Pelahustán", "Pelarrodríguez", "Pelayos", "Pelayos de la Presa", "Pelayos del Arroyo", "Peleagonzalo", "Peleas de Abajo", "Peligros", "Penagos", "Penàguila", "Penelles", "Peníscola/Peñíscola", "Peña, La", "Peñacaballera", "Peñacerrada-Urizaharra", "Peñafiel", "Peñaflor", "Peñaflor de Hornija", "Peñalba", "Peñalba de Ávila", "Peñalén", "Peñalsordo", "Peñalver", "Peñamellera Alta", "Peñamellera Baja", "Peñaparda", "Peñaranda de Bracamonte", "Peñaranda de Duero", "Peñarandilla", "Peñarroya de Tastavins", "Peñarroya-Pueblonuevo", "Peñarrubia", "Peñas de Riglos, Las", "Peñas de San Pedro", "Peñascosa", "Peñausende", "Pepino", "Peque", "Pera, La", "Peracense", "Perafita", "Perafort", "Peral de Arlanza", "Peral, El", "Peralada", "Peraleda de la Mata", "Peraleda de San Román", "Peraleda del Zaucejo", "Peraleja, La", "Peralejos", "Peralejos de Abajo", "Peralejos de Arriba", "Peralejos de las Truchas", "Perales", "Perales de Tajuña", "Perales del Alfambra", "Perales del Puerto", "Peralta de Alcofea", "Peralta de Calasanz", "Peralta/Azkoien", "Peraltilla", "Peralveche", "Peramola", "Peranzanes", "Perarrúa", "Perdigón, El", "Perdiguera", "Pereiro de Aguiar, O", "Perelló, El", "Pereña de la Ribera", "Pereruela", "Periana", "Perilla de Castro", "Pernía, La", "Peromingo", "Perosillo", "Peroxa, A", "Pertusa", "Pesaguero", "Pescueza", "Pesga, La", "Pesoz", "Pesquera", "Pesquera de Duero", "Pesquera, La", "Petilla de Aragón", "Petín", "Petra", "Petrer", "Petrés", "Pétrola", "Peza, La", "Pezuela de las Torres", "Pías", "Picanya", "Picassent", "Picazo, El", "Picón", "Piedrabuena", "Piedrahíta", "Piedrahita de Castro", "Piedralaves", "Piedramillera", "Piedras Albas", "Piedratajada", "Piélagos", "Piera", "Piérnigas", "Pilar de la Horadada", "Pilas", "Piles", "Piles, Les", "Piloña", "Pina de Ebro", "Pina de Montalgrao", "Pinar de El Hierro, El", "Pinar, El", "Pinarejo", "Pinarejos", "Pinarnegrillo", "Pineda de Gigüela", "Pineda de la Sierra", "Pineda de Mar", "Pineda Trasmonte", "Pinedas", "Pinell de Brai, El", "Pinell de Solsonès", "Pinet", "Pinilla de Jadraque", "Pinilla de los Barruecos", "Pinilla de los Moros", "Pinilla de Molina", "Pinilla de Toro", "Pinilla del Campo", "Pinilla del Valle", "Pinilla Trasmonte", "Pinillos", "Pino de Tormes, El", "Pino del Oro", "Pino del Río", "Pino, O", "Pinofranqueado", "Pinós", "Pinos Genil", "Pinos Puente", "Pinós, el/Pinoso", "Pinseque", "Pintanos, Los", "Pinto", "Piña de Campos", "Piña de Esgueva", "Píñar", "Piñel de Abajo", "Piñel de Arriba", "Piñero, El", "Piñor", "Piñuécar-Gandullas", "Piornal", "Pioz", "Piqueras", "Piqueras del Castillo", "Pira", "Piracés", "Pitarque", "Pitiegua", "Pitillas", "Pizarra", "Pizarral", "Pla de Santa Maria, El", "Pla del Penedès, El", "Plan", "Planes", "Planes dHostoles, Les", "Planoles", "Plans de Sió, Els", "Plasencia", "Plasencia de Jalón", "Plasenzuela", "Pleitas", "Plenas", "Plentzia", "Pliego", "Plou", "Poal, El", "Pobla de Benifassà, la", "Pobla de Cérvoles, La", "Pobla de Claramunt, La", "Pobla de Farnals, la", "Pobla de Lillet, La", "Pobla de Mafumet, La", "Pobla de Massaluca, La", "Pobla de Montornès, La", "Pobla de Segur, La", "Pobla de Vallbona, la", "Pobla del Duc, la", "Pobla Llarga, la", "Pobla Tornesa, la", "Pobla, Sa", "Población de Arroyo", "Población de Campos", "Población de Cerrato", "Pobladura de Pelayo García", "Pobladura de Valderaduey", "Pobladura del Valle", "Poblete", "Poblets, els", "Pobo de Dueñas, El", "Pobo, El", "Poboleda", "Pobra de Trives, A", "Pobra do Brollón, A", "Pobra do Caramiñal, A", "Poio", "Pol", "Pola de Gordón, La", "Polaciones", "Polán", "Polanco", "Polentinos", "Poleñino", "Polícar", "Polinyà", "Polinyà de Xúquer", "Pollença", "Pollos", "Polop", "Polopos", "Pomar de Valdivia", "Pomer", "Ponferrada", "Ponga", "Pont dArmentera, El", "Pont de Bar, El", "Pont de Molins", "Pont de Suert, El", "Pont de Vilomara i Rocafort, El", "Ponte Caldelas", "Ponteareas", "Ponteceso", "Pontecesures", "Pontedeume", "Pontedeva", "Pontenova, A", "Pontes de García Rodríguez, As", "Pontevedra", "Pontils", "Pontons", "Pontós", "Ponts", "Porcuna", "Porqueira", "Porqueres", "Porrera", "Porreres", "Porriño, O", "Port de la Selva, El", "Portaje", "Portalrubio de Guadamejud", "Portas", "Portbou", "Portell de Morella", "Portella, La", "Portellada, La", "Portezuelo", "Portilla", "Portillo", "Portillo de Soria", "Portillo de Toledo", "Porto", "Porto do Son", "Portomarín", "Portugalete", "Pórtugos", "Porzuna", "Posada de Valdeón", "Posadas", "Potes", "Potríes", "Poveda", "Poveda de la Sierra", "Poveda de las Cintas", "Póveda de Soria, La", "Povedilla", "Poyales del Hoyo", "Poyatos", "Poza de la Sal", "Poza de la Vega", "Pozal de Gallinas", "Pozaldez", "Pozalmuro", "Pozán de Vero", "Pozanco", "Pozo Alcón", "Pozo Cañada", "Pozo de Almoguera", "Pozo de Guadalajara", "Pozo de Urama", "Pozo-Lorente", "Pozoamargo", "Pozoantiguo", "Pozoblanco", "Pozohondo", "Pozondón", "Pozorrubielos de la Mancha", "Pozorrubio", "Pozos de Hinojo", "Pozuel de Ariza", "Pozuel del Campo", "Pozuelo", "Pozuelo de Alarcón", "Pozuelo de Aragón", "Pozuelo de Calatrava", "Pozuelo de la Orden", "Pozuelo de Tábara", "Pozuelo de Zarzón", "Pozuelo del Páramo", "Pozuelo del Rey", "Pozuelo, El", "Pozuelos de Calatrava, Los", "Pradales", "Prádanos de Bureba", "Prádanos de Ojeda", "Pradejón", "Pradell de la Teixeta", "Prádena", "Prádena de Atienza", "Prádena del Rincón", "Prades", "Pradilla de Ebro", "Pradillo", "Prado", "Prado de la Guzpeña", "Prado del Rey", "Pradoluengo", "Prados Redondos", "Pradosegar", "Prat de Comte", "Prat de Llobregat, El", "Pratdip", "Prats de Lluçanès", "Prats de Rei, Els", "Prats i Sansor", "Pravia", "Preixana", "Preixens", "Préjano", "Premià de Dalt", "Premià de Mar", "Presencio", "Preses, Les", "Priaranza del Bierzo", "Priego", "Priego de Córdoba", "Prioro", "Proaza", "Provencio, El", "Prullans", "Pruna", "Puçol", "Puebla de Albortón", "Puebla de Alcocer", "Puebla de Alfindén, La", "Puebla de Almenara", "Puebla de Almoradiel, La", "Puebla de Arenoso", "Puebla de Arganzón, La", "Puebla de Azaba", "Puebla de Beleña", "Puebla de Castro, La", "Puebla de Cazalla, La", "Puebla de Don Fadrique", "Puebla de Don Rodrigo", "Puebla de Guzmán", "Puebla de Híjar, La", "Puebla de la Calzada", "Puebla de la Reina", "Puebla de la Sierra", "Puebla de Lillo", "Puebla de los Infantes, La", "Puebla de Montalbán, La", "Puebla de Obando", "Puebla de Pedraza", "Puebla de San Medel", "Puebla de San Miguel", "Puebla de Sanabria", "Puebla de Sancho Pérez", "Puebla de Valdavia, La", "Puebla de Valles", "Puebla de Valverde, La", "Puebla de Yeltes", "Puebla del Maestre", "Puebla del Príncipe", "Puebla del Prior", "Puebla del Río, La", "Puebla del Salvador", "Pueblanueva, La", "Pueblica de Valverde", "Pueblonuevo del Guadiana", "Puendeluna", "Puente de Domingo Flórez", "Puente de Génave", "Puente de Montañana", "Puente del Arzobispo, El", "Puente del Congosto", "Puente Genil", "Puente la Reina de Jaca", "Puente la Reina/Gares", "Puente Viesgo", "Puentedura", "Puentes Viejas", "Puerta de Segura, La", "Puertas", "Puerto Castilla", "Puerto de Béjar", "Puerto de la Cruz", "Puerto de San Vicente", "Puerto de Santa Cruz", "Puerto de Santa María, El", "Puerto del Rosario", "Puerto Lápice", "Puerto Lumbreras", "Puerto Moral", "Puerto Real", "Puerto Seguro", "Puerto Serrano", "Puértolas", "Puertollano", "Puertomingalvo", "Pueyo", "Pueyo de Araguás, El", "Pueyo de Santa Cruz", "Puig", "Puig-reig", "Puigcerdà", "Puigdàlber", "Puiggròs", "Puigpelat", "Puigpunyent", "Puigverd dAgramunt", "Puigverd de Lleida", "Pujalt", "Pujerra", "Pulgar", "Pulianas", "Pulpí", "Punta Umbría", "Puntagorda", "Puntallana", "Punxín", "Puras", "Purchena", "Purujosa", "Purullena", "Quar, La", "Quart", "Quart de les Valls", "Quart de Poblet", "Quartell", "Quatretonda", "Quatretondeta", "Quel", "Quemada", "Quéntar", "Quer", "Queralbs", "Quero", "Querol", "Quesa", "Quesada", "Quicena", "Quijorna", "Quintana de la Serena", "Quintana del Castillo", "Quintana del Marco", "Quintana del Pidio", "Quintana del Puente", "Quintana Redonda", "Quintana y Congosto", "Quintanabureba", "Quintanaélez", "Quintanaortuño", "Quintanapalla", "Quintanar de la Orden", "Quintanar de la Sierra", "Quintanar del Rey", "Quintanas de Gormaz", "Quintanavides", "Quintanilla de Arriba", "Quintanilla de la Mata", "Quintanilla de Onésimo", "Quintanilla de Onsoña", "Quintanilla de Trigueros", "Quintanilla de Urz", "Quintanilla del Agua y Tordueles", "Quintanilla del Coco", "Quintanilla del Molar", "Quintanilla del Monte", "Quintanilla del Olmo", "Quintanilla San García", "Quintanilla Vivar", "Quintanillas, Las", "Quintela de Leirado", "Quinto", "Quiñonería", "Quiroga", "Quirós", "Quiruelas de Vidriales", "Quismondo", "Rábade", "Rabanales", "Rabanera", "Rabanera del Pinar", "Rábano", "Rábano de Aliste", "Rábanos", "Rábanos, Los", "Rabé de las Calzadas", "Rabós", "Rada de Haro", "Rafal", "Ráfales", "Rafelbuñol/Rafelbunyol", "Rafelcofer", "Rafelguaraf", "Ràfol dAlmúnia, el", "Ráfol de Salem", "Rágama", "Rágol", "Rairiz de Veiga", "Rajadell", "Ramales de la Victoria", "Rambla, La", "Ramirás", "Ramiro", "Rapariegos", "Rascafría", "Rasillo de Cameros, El", "Rasines", "Rasquera", "Rasueros", "Real", "Real de Gandía", "Real de la Jara, El", "Real de San Vicente, El", "Real Sitio de San Ildefonso", "Realejos, Los", "Rebollar", "Rebollar", "Rebolledo de la Torre", "Rebollo", "Rebollosa de Jadraque", "Recas", "Recueja, La", "Recuenco, El", "Recuerda", "Redal, El", "Redecilla del Camino", "Redecilla del Campo", "Redonda, La", "Redondela", "Redován", "Redueña", "Regencós", "Regueras de Arriba", "Regueras, Las", "Regumiel de la Sierra", "Reíllo", "Reina", "Reinosa", "Reinoso", "Reinoso de Cerrato", "Relleu", "Rellinars", "Rello", "Remolinos", "Remondo", "Rena", "Renau", "Renedo de Esgueva", "Renedo de la Vega", "Renera", "Renieblas", "Reocín", "Requejo", "Requena", "Requena de Campos", "Respenda de la Peña", "Retamal de Llerena", "Retamoso de la Jara", "Retascón", "Retiendas", "Retortillo", "Retortillo de Soria", "Retuerta", "Retuerta del Bullaque", "Reus", "Revellinos", "Revenga de Campos", "Revilla de Collazos", "Revilla del Campo", "Revilla Vallejera", "Revilla y Ahedo, La", "Revillarruz", "Reyero", "Rezmondo", "Reznos", "Riaguas de San Bartolomé", "Rialp", "Rianxo", "Riaño", "Riaza", "Riba de Escalote, La", "Riba de Saelices", "Riba-roja de Túria", "Riba-roja dEbre", "Riba, La", "Ribadavia", "Ribadedeva", "Ribadeo", "Ribadesella", "Ribadumia", "Ribaforada", "Ribafrecha", "Ribamontán al Mar", "Ribamontán al Monte", "Ribas de Campos", "Ribas de Sil", "Ribatejada", "Ribeira", "Ribeira de Piquín", "Ribera Baja/Erribera Beitia", "Ribera de Arriba", "Ribera del Fresno", "Ribera dOndara", "Ribera dUrgellet", "Riberos de la Cueza", "Ribes de Freser", "Ribesalbes", "Ribota", "Ricla", "Ricote", "Riego de la Vega", "Riello", "Riells i Viabrea", "Rielves", "Riera de Gaià, La", "Rillo", "Rillo de Gallo", "Rincón de la Victoria", "Rincón de Soto", "Rinconada de la Sierra, La", "Rinconada, La", "Riner", "Riocabado", "Riocavado de la Sierra", "Riodeva", "Riofrío", "Riofrío de Aliste", "Riofrío de Riaza", "Riofrío del Llano", "Riogordo", "Rioja", "Riola", "Riolobos", "Rionansa", "Rionegro del Puente", "Riópar", "Riós", "Riosa", "Rioseco de Soria", "Rioseco de Tapia", "Riotorto", "Riotuerto", "Ripoll", "Ripollet", "Risco", "Riu de Cerdanya", "Riudarenes", "Riudaura", "Riudecanyes", "Riudecols", "Riudellots de la Selva", "Riudoms", "Riumors", "Rivas-Vaciamadrid", "Rivilla de Barajas", "Roa", "Roales", "Roales de Campos", "Robla, La", "Robladillo", "Robleda", "Robleda-Cervantes", "Robledillo de Gata", "Robledillo de la Jara", "Robledillo de la Vera", "Robledillo de Mohernando", "Robledillo de Trujillo", "Robledo", "Robledo de Chavela", "Robledo de Corpes", "Robledo del Mazo", "Robledo, El", "Robledollano", "Robliza de Cojos", "Robregordo", "Robres", "Robres del Castillo", "Roca de la Sierra, La", "Roca del Vallès, La", "Rocafort", "Rocafort de Queralt", "Rociana del Condado", "Roda de Andalucía, La", "Roda de Barà", "Roda de Eresma", "Roda de Ter", "Roda, La", "Rodeiro", "Ródenas", "Rodezno", "Rodonyà", "Roelos de Sayago", "Rois", "Rojales", "Rojas", "Rollamienta", "Rollán", "Romana, la", "Romangordo", "Romanillos de Atienza", "Romanones", "Romanos", "Romanzado", "Romeral, El", "Roncal/Erronkari", "Ronda", "Ronquillo, El", "Roperuelos del Páramo", "Roquetas de Mar", "Roquetes", "Rosal de la Frontera", "Rosal, O", "Rosalejo", "Rosario, El", "Roses", "Rosinos de la Requejada", "Rossell", "Rosselló", "Rota", "Rotglà i Corberà", "Rótova", "Roturas", "Rourell, El", "Royo, El", "Royuela", "Royuela de Río Franco", "Rozalén del Monte", "Rozas de Madrid, Las", "Rozas de Puerto Real", "Rozas de Valdearroyo, Las", "Rúa, A", "Ruanes", "Rubena", "Rubí", "Rubí de Bracamonte", "Rubiá", "Rubiales", "Rubielos de la Cérida", "Rubielos de Mora", "Rubió", "Rubio, El", "Rubite", "Rublacedo de Abajo", "Rucandio", "Rueda", "Rueda de Jalón", "Rueda de la Sierra", "Ruente", "Ruesca", "Ruesga", "Rugat", "Ruidera", "Ruiloba", "Rupià", "Rupit i Pruit", "Rus", "Rute", "Sabadell", "Sabero", "Sabiñán", "Sabiñánigo", "Sabiote", "Sacañet", "Sacecorbo", "Saceda-Trasierra", "Sacedón", "Saceruela", "Sacramenia", "Sada", "Sada", "Sádaba", "Saelices", "Saelices de la Sal", "Saelices de Mayorga", "Saelices el Chico", "Sagàs", "Sagra", "Sagrada, La", "Sagunto/Sagunt", "Sahagún", "Sahugo, El", "Sahún", "Sajazarra", "Salamanca", "Salar", "Salares", "Salas", "Salas Altas", "Salas Bajas", "Salas de Bureba", "Salas de los Infantes", "Salàs de Pallars", "Salce", "Salceda de Caselas", "Salcedillo", "Saldaña", "Saldaña de Burgos", "Saldeana", "Saldes", "Saldías", "Saldón", "Salduero", "Salem", "Sales de Llierca", "Salillas", "Salillas de Jalón", "Salinas", "Salinas de Oro/Jaitz", "Salinas de Pisuerga", "Salinas del Manzano", "Salines, Ses", "Salinillas de Bureba", "Sallent", "Sallent de Gállego", "Salmerón", "Salmeroncillos", "Salmoral", "Salobral", "Salobre", "Salobreña", "Salomó", "Salorino", "Salou", "Salt", "Salteras", "Salvacañete", "Salvadiós", "Salvador de Zapardiel", "Salvaleón", "Salvaterra de Miño", "Salvatierra de Esca", "Salvatierra de los Barros", "Salvatierra de Santiago", "Salvatierra de Tormes", "Salvatierra/Agurain", "Salzadella, la", "Samaniego", "Samboal", "Samir de los Caños", "Samos", "Samper de Calanda", "Samper del Salz", "San Adrián", "San Adrián de Juarros", "San Adrián del Valle", "San Agustín", "San Agustín del Guadalix", "San Agustín del Pozo", "San Amaro", "San Andrés del Congosto", "San Andrés del Rabanedo", "San Andrés del Rey", "San Andrés y Sauces", "San Antonio de Benagéber", "San Asensio", "San Bartolomé", "San Bartolomé de Béjar", "San Bartolomé de Corneja", "San Bartolomé de la Torre", "San Bartolomé de las Abiertas", "San Bartolomé de Pinares", "San Bartolomé de Tirajana", "San Carlos del Valle", "San Cebrián de Campos", "San Cebrián de Castro", "San Cebrián de Mazote", "San Cebrián de Mudá", "San Cibrao das Viñas", "San Clemente", "San Cristóbal de Boedo", "San Cristóbal de Cuéllar", "San Cristóbal de Entreviñas", "San Cristóbal de la Cuesta", "San Cristóbal de La Laguna", "San Cristóbal de la Polantera", "San Cristóbal de la Vega", "San Cristóbal de Segovia", "San Cristovo de Cea", "San Emiliano", "San Esteban de Gormaz", "San Esteban de la Sierra", "San Esteban de Litera", "San Esteban de los Patos", "San Esteban de Nogales", "San Esteban de Zapardiel", "San Esteban del Molar", "San Esteban del Valle", "San Felices", "San Felices de Buelna", "San Felices de los Gallegos", "San Fernando", "San Fernando de Henares", "San Fulgencio", "San García de Ingelmos", "San Isidro", "San Javier", "San José del Valle", "San Juan de Aznalfarache", "San Juan de Gredos", "San Juan de la Encinilla", "San Juan de la Nava", "San Juan de la Rambla", "San Juan de Plan", "San Juan del Molinillo", "San Juan del Monte", "San Juan del Olmo", "San Juan del Puerto", "San Justo", "San Justo de la Vega", "San Leonardo de Yagüe", "San Llorente", "San Lorenzo de Calatrava", "San Lorenzo de El Escorial", "San Lorenzo de la Parrilla", "San Lorenzo de Tormes", "San Mamés de Burgos", "San Mamés de Campos", "San Martín de Boniches", "San Martín de la Vega", "San Martín de la Vega del Alberche", "San Martín de la Virgen de Moncayo", "San Martín de Montalbán", "San Martín de Oscos", "San Martín de Pusa", "San Martín de Rubiales", "San Martín de Trevejo", "San Martín de Unx", "San Martín de Valdeiglesias", "San Martín de Valderaduey", "San Martín de Valvení", "San Martín del Castañar", "San Martín del Pimpollar", "San Martín del Rey Aurelio", "San Martín del Río", "San Martín y Mudrián", "San Mateo de Gállego", "San Miguel de Abona", "San Miguel de Aguayo", "San Miguel de Bernuy", "San Miguel de Corneja", "San Miguel de la Ribera", "San Miguel de Salinas", "San Miguel de Serrezuela", "San Miguel de Valero", "San Miguel del Arroyo", "San Miguel del Cinca", "San Miguel del Pino", "San Miguel del Robledo", "San Miguel del Valle", "San Millán de la Cogolla", "San Millán de Lara", "San Millán de los Caballeros", "San Millán de Yécora", "San Millán/Donemiliaga", "San Morales", "San Muñoz", "San Nicolás del Puerto", "San Pablo de la Moraleja", "San Pablo de los Montes", "San Pascual", "San Pedro", "San Pedro Bercianos", "San Pedro de Ceque", "San Pedro de Gaíllos", "San Pedro de la Nave-Almendra", "San Pedro de Latarce", "San Pedro de Mérida", "San Pedro de Rozados", "San Pedro del Arroyo", "San Pedro del Pinatar", "San Pedro del Romeral", "San Pedro del Valle", "San Pedro Manrique", "San Pedro Palmiches", "San Pelayo", "San Pelayo de Guareña", "San Rafael del Río", "San Román de Cameros", "San Román de Hornija", "San Román de la Cuba", "San Román de los Montes", "San Roque", "San Roque de Riomiera", "San Sadurniño", "San Salvador", "San Sebastián de la Gomera", "San Sebastián de los Ballesteros", "San Sebastián de los Reyes", "San Silvestre de Guzmán", "San Tirso de Abres", "San Torcuato", "San Vicente de Alcántara", "San Vicente de Arévalo", "San Vicente de la Barquera", "San Vicente de la Cabeza", "San Vicente de la Sonsierra", "San Vicente del Palacio", "San Vicente del Raspeig/Sant Vicent del Raspeig", "San Vicente del Valle", "San Vitero", "San Xoán de Río", "Sanaüja", "Sancedo", "Sanchidrián", "Sanchón de la Ribera", "Sanchón de la Sagrada", "Sanchonuño", "Sanchorreja", "Sanchotello", "Sancti-Spíritus", "Sancti-Spíritus", "Sandiás", "Sando", "Sanet y Negrals", "Sangarcía", "Sangarrén", "Sangüesa/Zangoza", "Sanlúcar de Barrameda", "Sanlúcar de Guadiana", "Sanlúcar la Mayor", "Sansol", "Sant Adrià de Besòs", "Sant Agustí de Lluçanès", "Sant Andreu de la Barca", "Sant Andreu de Llavaneres", "Sant Andreu Salou", "Sant Aniol de Finestres", "Sant Antoni de Portmany", "Sant Antoni de Vilamajor", "Sant Bartomeu del Grau", "Sant Boi de Llobregat", "Sant Boi de Lluçanès", "Sant Carles de la Ràpita", "Sant Cebrià de Vallalta", "Sant Celoni", "Sant Climent de Llobregat", "Sant Climent Sescebes", "Sant Cugat del Vallès", "Sant Cugat Sesgarrigues", "Sant Esteve de la Sarga", "Sant Esteve de Palautordera", "Sant Esteve Sesrovires", "Sant Feliu de Buixalleu", "Sant Feliu de Codines", "Sant Feliu de Guíxols", "Sant Feliu de Llobregat", "Sant Feliu de Pallerols", "Sant Feliu Sasserra", "Sant Ferriol", "Sant Fost de Campsentelles", "Sant Fruitós de Bages", "Sant Gregori", "Sant Guim de Freixenet", "Sant Guim de la Plana", "Sant Hilari Sacalm", "Sant Hipòlit de Voltregà", "Sant Iscle de Vallalta", "Sant Jaume de Frontanyà", "Sant Jaume de Llierca", "Sant Jaume dels Domenys", "Sant Jaume dEnveja", "Sant Joan", "Sant Joan dAlacant", "Sant Joan de Labritja", "Sant Joan de les Abadesses", "Sant Joan de Mollet", "Sant Joan de Moró", "Sant Joan de Vilatorrada", "Sant Joan Despí", "Sant Joan les Fonts", "Sant Joanet", "Sant Jordi Desvalls", "Sant Jordi/San Jorge", "Sant Josep de sa Talaia", "Sant Julià de Cerdanyola", "Sant Julià de Ramis", "Sant Julià de Vilatorta", "Sant Julià del Llor i Bonmatí", "Sant Just Desvern", "Sant Llorenç de la Muga", "Sant Llorenç de Morunys", "Sant Llorenç des Cardassar", "Sant Llorenç dHortons", "Sant Llorenç Savall", "Sant Lluís", "Sant Martí dAlbars", "Sant Martí de Centelles", "Sant Martí de Llémena", "Sant Martí de Riucorb", "Sant Martí de Tous", "Sant Martí Sarroca", "Sant Martí Sesgueioles", "Sant Martí Vell", "Sant Mateu", "Sant Mateu de Bages", "Sant Miquel de Campmajor", "Sant Miquel de Fluvià", "Sant Mori", "Sant Pau de Segúries", "Sant Pere de Ribes", "Sant Pere de Riudebitlles", "Sant Pere de Torelló", "Sant Pere de Vilamajor", "Sant Pere Pescador", "Sant Pere Sallavinera", "Sant Pol de Mar", "Sant Quintí de Mediona", "Sant Quirze de Besora", "Sant Quirze del Vallès", "Sant Quirze Safaja", "Sant Ramon", "Sant Sadurní dAnoia", "Sant Sadurní dOsormort", "Sant Salvador de Guardiola", "Sant Vicenç de Castellet", "Sant Vicenç de Montalt", "Sant Vicenç de Torelló", "Sant Vicenç dels Horts", "Santa Amalia", "Santa Ana", "Santa Ana de Pusa", "Santa Ana la Real", "Santa Bàrbara", "Santa Bárbara de Casa", "Santa Brígida", "Santa Cecilia", "Santa Cecília de Voltregà", "Santa Cecilia del Alcor", "Santa Cilia", "Santa Clara de Avedillo", "Santa Coloma", "Santa Coloma de Cervelló", "Santa Coloma de Farners", "Santa Coloma de Gramenet", "Santa Coloma de Queralt", "Santa Colomba de Curueño", "Santa Colomba de las Monjas", "Santa Colomba de Somoza", "Santa Comba", "Santa Cristina dAro", "Santa Cristina de la Polvorosa", "Santa Cristina de Valmadrigal", "Santa Croya de Tera", "Santa Cruz de Bezana", "Santa Cruz de Boedo", "Santa Cruz de Grío", "Santa Cruz de la Palma", "Santa Cruz de la Salceda", "Santa Cruz de la Serós", "Santa Cruz de la Sierra", "Santa Cruz de la Zarza", "Santa Cruz de los Cáñamos", "Santa Cruz de Marchena", "Santa Cruz de Moncayo", "Santa Cruz de Moya", "Santa Cruz de Mudela", "Santa Cruz de Nogueras", "Santa Cruz de Paniagua", "Santa Cruz de Pinares", "Santa Cruz de Tenerife", "Santa Cruz de Yanguas", "Santa Cruz del Comercio", "Santa Cruz del Retamar", "Santa Cruz del Valle", "Santa Cruz del Valle Urbión", "Santa Elena", "Santa Elena de Jamuz", "Santa Engracia del Jubera", "Santa Eufemia", "Santa Eufemia del Arroyo", "Santa Eufemia del Barco", "Santa Eugènia", "Santa Eugènia de Berga", "Santa Eulalia", "Santa Eulalia Bajera", "Santa Eulalia de Gállego", "Santa Eulalia de Oscos", "Santa Eulàlia de Riuprimer", "Santa Eulàlia de Ronçana", "Santa Eulalia del Río", "Santa Fe", "Santa Fe de Mondújar", "Santa Fe del Penedès", "Santa Gadea del Cid", "Santa Inés", "Santa Llogaia dÀlguema", "Santa Lucía de Tirajana", "Santa Magdalena de Pulpis", "Santa Margalida", "Santa Margarida de Montbui", "Santa Margarida i els Monjos", "Santa Maria de Besora", "Santa María de Cayón", "Santa Maria de Corcó", "Santa María de Dulcis", "Santa María de Guía de Gran Canaria", "Santa María de Huerta", "Santa María de la Alameda", "Santa María de la Isla", "Santa María de la Vega", "Santa María de las Hoyas", "Santa María de los Caballeros", "Santa María de los Llanos", "Santa Maria de Martorelles", "Santa Maria de Merlès", "Santa Maria de Miralles", "Santa María de Ordás", "Santa Maria de Palautordera", "Santa María de Sando", "Santa María de Valverde", "Santa María del Arroyo", "Santa María del Berrocal", "Santa María del Camí", "Santa María del Campo", "Santa María del Campo Rus", "Santa María del Cubillo", "Santa María del Invierno", "Santa María del Mercadillo", "Santa María del Monte de Cea", "Santa María del Páramo", "Santa María del Tiétar", "Santa María del Val", "Santa Maria dOló", "Santa María la Real de Nieva", "Santa María Rivarredonda", "Santa Marina del Rey", "Santa Marta", "Santa Marta de Magasca", "Santa Marta de Tormes", "Santa Marta del Cerro", "Santa Olalla", "Santa Olalla de Bureba", "Santa Olalla del Cala", "Santa Oliva", "Santa Pau", "Santa Perpètua de Mogoda", "Santa Pola", "Santa Susanna", "Santa Úrsula", "Santacara", "Santaella", "Santaliestra y San Quílez", "Santander", "Santanyí", "Santas Martas", "Santed", "Santervás de Campos", "Santervás de la Vega", "Santiago de Alcántara", "Santiago de Calatrava", "Santiago de Compostela", "Santiago de la Puebla", "Santiago del Campo", "Santiago del Collado", "Santiago del Teide", "Santiago del Tormes", "Santiago Millas", "Santiago-Pontones", "Santibáñez de Béjar", "Santibáñez de Ecla", "Santibáñez de Esgueva", "Santibáñez de la Peña", "Santibáñez de la Sierra", "Santibáñez de Tera", "Santibáñez de Valcorba", "Santibáñez de Vidriales", "Santibáñez del Val", "Santibáñez el Alto", "Santibáñez el Bajo", "Santillana del Mar", "Santiponce", "Santiso", "Santisteban del Puerto", "Santiurde de Reinosa", "Santiurde de Toranzo", "Santiuste", "Santiuste de Pedraza", "Santiuste de San Juan Bautista", "Santiz", "Santo Adriano", "Santo Domingo de la Calzada", "Santo Domingo de las Posadas", "Santo Domingo de Pirón", "Santo Domingo de Silos", "Santo Domingo-Caudilla", "Santo Tomé", "Santo Tomé de Zabarcos", "Santo Tomé del Puerto", "Santomera", "Santoña", "Santorcaz", "Santos de la Humosa, Los", "Santos de Maimona, Los", "Santos, Los", "Santovenia", "Santovenia de la Valdoncina", "Santovenia de Pisuerga", "Santoyo", "Santpedor", "Santurde de Rioja", "Santurdejo", "Santurtzi", "Sanxenxo", "Sanzoles", "Sardón de Duero", "Sardón de los Frailes", "Sargentes de la Lora", "Sariego", "Sariegos", "Sariñena", "Saro", "Sarracín", "Sarral", "Sarratella", "Sarreaus", "Sarria", "Sarrià de Ter", "Sarriés/Sartze", "Sarrión", "Sarroca de Bellera", "Sarroca de Lleida", "Sartaguda", "Sartajada", "Sasamón", "Sástago", "Saúca", "Saucedilla", "Saucejo, El", "Saucelle", "Sauquillo de Cabezas", "Saus, Camallera i Llampaies", "Sauzal, El", "Savallà del Comtat", "Saviñao, O", "Sax", "Sayalonga", "Sayatón", "Sebúlcor", "Seca, La", "Secastilla", "Secuita, La", "Sedaví", "Sedella", "Sediles", "Segart", "Segorbe", "Segovia", "Segura", "Segura de la Sierra", "Segura de León", "Segura de los Baños", "Segura de Toro", "Segurilla", "Seira", "Selas", "Selaya", "Sella", "Sellent", "Selva", "Selva de Mar, La", "Selva del Camp, La", "Semillas", "Sempere", "Sena", "Sena de Luna", "Senan", "Sencelles", "Senés", "Senés de Alcubierre", "Sénia, La", "Senija", "Seno", "Senterada", "Sentiu de Sió, La", "Sentmenat", "Senyera", "Sepulcro-Hilario", "Sepúlveda", "Sequera de Fresno", "Sequera de Haza, La", "Sequeros", "Serinyà", "Serna del Monte, La", "Serna, La", "Serón", "Serón de Nágima", "Seròs", "Serra", "Serra de Daró", "Serrada", "Serrada, La", "Serradilla", "Serradilla del Arroyo", "Serradilla del Llano", "Serranillos", "Serranillos del Valle", "Serrejón", "Sesa", "Seseña", "Sesma", "Sestao", "Sestrica", "Sesué", "Setcases", "Setenil de las Bodegas", "Setiles", "Seu dUrgell, La", "Seva", "Sevilla", "Sevilla la Nueva", "Sevilleja de la Jara", "Sidamon", "Sienes", "Siero", "Sierpe, La", "Sierra de Fuentes", "Sierra de Luna", "Sierra de Yeguas", "Sierra Engarcerán", "Sierro", "Siétamo", "Siete Aguas", "Siete Iglesias de Trabancos", "Sieteiglesias de Tormes", "Sigeres", "Sigüenza", "Sigüés", "Siles", "Silla", "Silleda", "Silos, Los", "Sils", "Simancas", "Simat de la Valldigna", "Sinarcas", "Sineu", "Singra", "Sinlabajos", "Siruela", "Sisamón", "Sisante", "Sitges", "Siurana", "Soba", "Sober", "Sobradiel", "Sobradillo", "Sobrado", "Sobrado", "Sobremunt", "Sobrescobio", "Socovos", "Socuéllamos", "Sojuela", "Solana de Ávila", "Solana de los Barros", "Solana de Rioalmar", "Solana del Pino", "Solana, La", "Solanillos del Extremo", "Solarana", "Solera de Gabaldón", "Soleràs, El", "Soliedra", "Solivella", "Sollana", "Sóller", "Solórzano", "Solosancho", "Solsona", "Somiedo", "Somolinos", "Somontín", "Somosierra", "Somozas, As", "Son Servera", "Sondika", "Soneja", "Sonseca", "Sopeira", "Sopelana", "Soportújar", "Sopuerta", "Sora", "Soraluze-Placencia de las Armas", "Sorbas", "Sordillos", "Soria", "Soriguera", "Sorihuela", "Sorihuela del Guadalimar", "Sorlada", "Sort", "Sorvilán", "Sorzano", "Sos del Rey Católico", "Soses", "Sot de Chera", "Sot de Ferrer", "Sotalbo", "Sotés", "Sotillo", "Sotillo de la Adrada", "Sotillo de la Ribera", "Sotillo de las Palomas", "Sotillo del Rincón", "Sotillo, El", "Soto de Cerrato", "Soto de la Vega", "Soto del Barco", "Soto del Real", "Soto en Cameros", "Soto y Amío", "Sotobañado y Priorato", "Sotodosos", "Sotonera, La", "Sotorribas", "Sotosalbos", "Sotoserrano", "Sotragero", "Sotresgudo", "Soutomaior", "Suances", "Subirats", "Sudanell", "Sueca", "Suellacabras", "Sueras/Suera", "Suflí", "Sukarrieta", "Sumacàrcer", "Sunbilla", "Sunyer", "Súria", "Susinos del Páramo", "Susqueda"}));
        }
        if (jComboBox_filtroAZ1.getSelectedItem().toString().equals("De la T a la Z")) {
            jComboBox_poblacion1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Tabanera de Cerrato", "Tabanera de Valdavia", "Tabanera la Luenga", "Tábara", "Tabera de Abajo", "Tabernas", "Taberno", "Taboada", "Taboadela", "Tabuenca", "Tacoronte", "Tafalla", "Tagamanent", "Taha, La", "Tahal", "Tajahuerce", "Tajueco", "Tala, La", "Talamanca", "Talamanca de Jarama", "Talamantes", "Talarn", "Talarrubias", "Talaván", "Talavera", "Talavera de la Reina", "Talavera la Real", "Talaveruela de la Vera", "Talayuela", "Talayuelas", "Tales", "Táliga", "Tallada dEmpordà, La", "Talveila", "Tamajón", "Tamames", "Támara de Campos", "Tamarite de Litera", "Tamariz de Campos", "Tamarón", "Tamurejo", "Tanque, El", "Tapia de Casariego", "Tapioles", "Taradell", "Taragudo", "Taramundi", "Tarancón", "Taravilla", "Tarazona", "Tarazona de Guareña", "Tarazona de la Mancha", "Tàrbena", "Tardáguila", "Tardajos", "Tardelcuende", "Tardienta", "Tariego de Cerrato", "Tarifa", "Taroda", "Tarragona", "Tàrrega", "Tarrés", "Tarroja de Segarra", "Tartanedo", "Tauste", "Tavernes Blanques", "Tavernes de la Valldigna", "Tavèrnoles", "Tavertet", "Tazacorte", "Teba", "Tébar", "Tegueste", "Teguise", "Teià", "Teixeira, A", "Tejada", "Tejadillos", "Tejado", "Tejado, El", "Tejeda", "Tejeda de Tiétar", "Tejeda y Segoyuela", "Telde", "Tella-Sin", "Tembleque", "Tendilla", "Tenebrón", "Teo", "Teresa", "Teresa de Cofrentes", "Térmens", "Teror", "Terque", "Terrades", "Terradillos", "Terradillos de Esgueva", "Terrassa", "Terrateig", "Terrer", "Terriente", "Terrinches", "Terroba", "Teruel", "Terzaga", "Teulada", "Teverga", "Tiana", "Tías", "Tibi", "Tiebas-Muruarte de Reta", "Tiedra", "Tielmes", "Tiemblo, El", "Tierga", "Tierz", "Tierzo", "Tijarafe", "Tíjola", "Tinajas", "Tinajo", "Tineo", "Tinieblas de la Sierra", "Tiñosillos", "Tirapu", "Tirgo", "Tírig", "Tírvia", "Titaguas", "Titulcia", "Tiurana", "Tivenys", "Tivissa", "Toba, La", "Tobar", "Tobarra", "Tobed", "Tobía", "Toboso, El", "Tocina", "Todolella", "Toén", "Toga", "Tojos, Los", "Tolbaños", "Toledo", "Tollos", "Tolocirio", "Tolosa", "Tolox", "Tolva", "Tomares", "Tomelloso", "Tomiño", "Tona", "Topas", "Toques", "Torà", "Toral de los Guzmanes", "Toral de los Vados", "Torás", "Tordehumos", "Tordellego", "Tordelrábano", "Tordera", "Tordesillas", "Tordesilos", "Tordillos", "Tordoia", "Tordómar", "Torelló", "Toreno", "Torija", "Toril", "Toril y Masegoso", "Torla", "Torlengua", "Tormantos", "Tormellas", "Tormón", "Tormos", "Torms, Els", "Tornabous", "Tornadizo, El", "Tornadizos de Ávila", "Tornavacas", "Torno, El", "Tornos", "Toro", "Toro, El", "Torquemada", "Torralba", "Torralba de Aragón", "Torralba de Calatrava", "Torralba de los Frailes", "Torralba de los Sisones", "Torralba de Oropesa", "Torralba de Ribota", "Torralba del Pinar", "Torralba del Río", "Torralbilla", "Torre Alháquime", "Torre de Arcas", "Torre de Cabdella, La", "Torre de Claramunt, La", "Torre de Don Miguel", "Torre de Esgueva", "Torre de Esteban Hambrán, La", "Torre de Fontaubella, La", "Torre de Juan Abad", "Torre de las Arcas", "Torre de lEspanyol, La", "Torre de Miguel Sesmero", "Torre de Peñafiel", "Torre de Santa María", "Torre del Bierzo", "Torre del Burgo", "Torre del Campo", "Torre del Compte", "Torre del Valle, La", "Torre dEn Besora, la", "Torre den Doménec, la", "Torre en Cameros", "Torre la Ribera", "Torre los Negros", "Torre Val de San Pedro", "Torre-Cardela", "Torre-Pacheco", "Torre-serona", "Torre, La", "Torreadrada", "Torrebaja", "Torrebesses", "Torreblacos", "Torreblanca", "Torreblascopedro", "Torrecaballeros", "Torrecampo", "Torrechiva", "Torrecilla de Alcañiz", "Torrecilla de la Abadesa", "Torrecilla de la Jara", "Torrecilla de la Orden", "Torrecilla de la Torre", "Torrecilla de los Ángeles", "Torrecilla del Monte", "Torrecilla del Pinar", "Torrecilla del Rebollar", "Torrecilla en Cameros", "Torrecilla sobre Alesanco", "Torrecillas de la Tiesa", "Torrecuadrada de Molina", "Torrecuadradilla", "Torredembarra", "Torredonjimeno", "Torrefarrera", "Torrefeta i Florejacs", "Torregalindo", "Torregamones", "Torregrossa", "Torrehermosa", "Torreiglesias", "Torrejón de Ardoz", "Torrejón de la Calzada", "Torrejón de Velasco", "Torrejón del Rey", "Torrejón el Rubio", "Torrejoncillo", "Torrejoncillo del Rey", "Torrelacárcel", "Torrelaguna", "Torrelameu", "Torrelapaja", "Torrelara", "Torrelavega", "Torrelavit", "Torrella", "Torrellas", "Torrelles de Foix", "Torrelles de Llobregat", "Torrelobatón", "Torrelodones", "Torremanzanas", "Torremayor", "Torremejía", "Torremenga", "Torremocha", "Torremocha de Jadraque", "Torremocha de Jarama", "Torremocha de Jiloca", "Torremocha del Campo", "Torremocha del Pinar", "Torremochuela", "Torremolinos", "Torremontalbo", "Torremormojón", "Torrent", "Torrent", "Torrente de Cinca", "Torrenueva", "Torreorgaz", "Torrepadre", "Torreperogil", "Torrequemada", "Torres", "Torres de Albánchez", "Torres de Albarracín", "Torres de Alcanadre", "Torres de Barbués", "Torres de Berrellén", "Torres de Cotillas, Las", "Torres de la Alameda", "Torres de Segre", "Torres del Carrizal", "Torres del Río", "Torres Torres", "Torresandino", "Torrescárcela", "Torresmenudas", "Torrevelilla", "Torrevieja", "Torrico", "Torrijas", "Torrijo de la Cañada", "Torrijo del Campo", "Torrijos", "Torroella de Fluvià", "Torroella de Montgrí", "Torroja del Priorat", "Torrox", "Torrubia", "Torrubia de Soria", "Torrubia del Campo", "Torrubia del Castillo", "Tortellà", "Tórtola de Henares", "Tórtoles", "Tórtoles de Esgueva", "Tortosa", "Tortuera", "Tortuero", "Torvizcón", "Tosantos", "Toses", "Tosos", "Tossa de Mar", "Totalán", "Totana", "Totanés", "Touro", "Tous", "Trabada", "Trabadelo", "Trabanca", "Trabazos", "Tragacete", "Traíd", "Traiguera", "Tramacastiel", "Tramacastilla", "Tramaced", "Trasierra", "Trasmiras", "Trasmoz", "Trasobares", "Traspinedo", "Trazo", "Trebujena", "Trefacio", "Tremedal de Tormes", "Tremp", "Tres Cantos", "Tres Villas, Las", "Trescasas", "Tresjuncos", "Trespaderne", "Tresviso", "Trévago", "Trevélez", "Treviana", "Triacastela", "Tribaldos", "Tricio", "Trigueros", "Trigueros del Valle", "Trijueque", "Trillo", "Triollo", "Tronchón", "Truchas", "Trucios-Turtzioz", "Trujillanos", "Trujillo", "Tubilla del Agua", "Tubilla del Lago", "Tudanca", "Tudela", "Tudela de Duero", "Tudelilla", "Tuéjar", "Tui", "Tuineje", "Tulebras", "Turcia", "Turégano", "Turís", "Turleque", "Turón", "Turre", "Turrillas", "Úbeda", "Ubide", "Ubrique", "Ucar", "Uceda", "Ucero", "Uclés", "Udías", "Ugao-Miraballes", "Ugena", "Ugíjar", "Uharte-Arakil", "Ujados", "Ujué", "Ulea", "Uleila del Campo", "Ullà", "Ullastrell", "Ullastret", "Ulldecona", "Ulldemolins", "Ultramort", "Ultzama", "Umbrete", "Umbrías", "Uncastillo", "Unciti", "Undués de Lerda", "Unión de Campos, La", "Unión, La", "Unzué/Untzue", "Uña", "Uña de Quintana", "Úrbel del Castillo", "Urda", "Urdazubi/Urdax", "Urdiain", "Urdiales del Páramo", "Urduliz", "Urduña/Orduña", "Urkabustaiz", "Urnieta", "Urones de Castroponce", "Urrácal", "Urraul Alto", "Urraul Bajo", "Urrea de Gaén", "Urrea de Jalón", "Urretxu", "Urriés", "Urrotz", "Urroz-Villa", "Urueña", "Urueñas", "Uruñuela", "Urús", "Urzainqui/Urzainki", "Usagre", "Used", "Useras/Useres, les", "Usurbil", "Utande", "Utebo", "Uterga", "Utiel", "Utrera", "Utrillas", "Uztárroz/Uztarroze", "Vacarisses", "Vadillo", "Vadillo de la Guareña", "Vadillo de la Sierra", "Vadocondes", "Vajol, La", "Val de San Lorenzo", "Val de San Martín", "Val de San Vicente", "Val do Dubra", "Valacloche", "Valadouro, O", "Valbona", "Valbuena de Duero", "Valbuena de Pisuerga", "Valcabado", "Valdáliga", "Valdaracete", "Valdarachas", "Valdastillas", "Valde-Ucieza", "Valdealgorfa", "Valdeande", "Valdearcos de la Vega", "Valdearenas", "Valdeavellano", "Valdeavellano de Tera", "Valdeavero", "Valdeaveruelo", "Valdecaballeros", "Valdecañas de Tajo", "Valdecarros", "Valdecasa", "Valdecolmenas, Los", "Valdeconcha", "Valdecuenca", "Valdefinjas", "Valdefresno", "Valdefuentes", "Valdefuentes de Sangusín", "Valdefuentes del Páramo", "Valdeganga", "Valdegeña", "Valdegovía/Gaubea", "Valdegrudas", "Valdehijaderos", "Valdehorna", "Valdehúncar", "Valdelacalzada", "Valdelacasa", "Valdelacasa de Tajo", "Valdelageve", "Valdelagua del Cerro", "Valdelaguna", "Valdelarco", "Valdelcubo", "Valdelinares", "Valdelosa", "Valdeltormo", "Valdelugueros", "Valdemadera", "Valdemaluque", "Valdemanco", "Valdemanco del Esteras", "Valdemaqueda", "Valdemeca", "Valdemierque", "Valdemora", "Valdemorales", "Valdemorillo", "Valdemorillo de la Sierra", "Valdemoro", "Valdemoro-Sierra", "Valdenebro", "Valdenebro de los Valles", "Valdenuño Fernández", "Valdeobispo", "Valdeolea", "Valdeolivas", "Valdeolmillos", "Valdeolmos-Alalpardo", "Valdepeñas", "Valdepeñas de Jaén", "Valdepeñas de la Sierra", "Valdepiélago", "Valdepiélagos", "Valdepolo", "Valdeprado", "Valdeprado del Río", "Valdeprados", "Valderas", "Valderrábano", "Valderrebollo", "Valderredible", "Valderrey", "Valderrobres", "Valderrodilla", "Valderrodrigo", "Valderrueda", "Valdés", "Valdesamario", "Valdescorriel", "Valdesotos", "Valdestillas", "Valdetorres", "Valdetorres de Jarama", "Valdetórtola", "Valdevacas de Montejo", "Valdevacas y Guijar", "Valdeverdeja", "Valdevimbre", "Valdezate", "Valdilecha", "Valdorros", "Valdoviño", "Valdunciel", "Valdunquillo", "Valencia", "Valencia de Alcántara", "Valencia de Don Juan", "Valencia de las Torres", "Valencia del Mombuey", "Valencia del Ventoso", "Valencina de la Concepción", "Valenzuela", "Valenzuela de Calatrava", "Valeras, Las", "Valero", "Valfarta", "Valfermoso de Tajuña", "Valga", "Valgañón", "Valhermoso", "Valhermoso de la Fuente", "Valjunquera", "Vall dAlba", "Vall dAlcalà, la", "Vall de Almonacid", "Vall de Bianya, La", "Vall de Boí, La", "Vall de Cardós", "Vall de Gallinera", "Vall de Laguar, la", "Vall dEbo, la", "Vall den Bas, La", "Vall dUixó, la", "Vall-llobrega", "Vallada", "Valladolid", "Vallanca", "Vallarta de Bureba", "Vallat", "Vallbona dAnoia", "Vallbona de les Monges", "Vallcebre", "Vallclara", "Valldemossa", "Valle de Abdalajís", "Valle de Altomira, El", "Valle de Bardají", "Valle de Cerrato", "Valle de Hecho", "Valle de la Serena", "Valle de las Navas", "Valle de Lierp", "Valle de Losa", "Valle de Manzanedo", "Valle de Matamoros", "Valle de Mena", "Valle de Oca", "Valle de Santa Ana", "Valle de Santibáñez", "Valle de Sedano", "Valle de Tabladillo", "Valle de Tobalina", "Valle de Trápaga-Trapagaran", "Valle de Valdebezana", "Valle de Valdelaguna", "Valle de Valdelucio", "Valle de Villaverde", "Valle de Yerri/Deierri", "Valle de Zamanzas", "Valle del Retortillo", "Valle del Zalabí", "Valle Gran Rey", "Valle, El", "Vallecillo", "Vallecillo, El", "Vallehermoso", "Vallejera", "Vallejera de Riofrío", "Vallelado", "Valleruela de Pedraza", "Valleruela de Sepúlveda", "Vallés", "Valles de Palenzuela", "Vallesa de la Guareña", "Valleseco", "Vallfogona de Balaguer", "Vallfogona de Ripollès", "Vallfogona de Riucorb", "Vallgorguina", "Vallibona", "Vallirana", "Vallmoll", "Vallromanes", "Valls", "Valls dAguilar, Les", "Valls de Valira, Les", "Valluércanes", "Valmadrid", "Valmala", "Valmojado", "Válor", "Valoria la Buena", "Valpalmas", "Valsalabroso", "Valsalobre", "Valseca", "Valsequillo", "Valsequillo de Gran Canaria", "Valtablado del Río", "Valtajeros", "Valtiendas", "Valtierra", "Valtorres", "Valverde", "Valverde de Alcalá", "Valverde de Burguillos", "Valverde de Campos", "Valverde de Júcar", "Valverde de la Vera", "Valverde de la Virgen", "Valverde de Leganés", "Valverde de Llerena", "Valverde de los Arroyos", "Valverde de Mérida", "Valverde de Valdelacasa", "Valverde del Camino", "Valverde del Fresno", "Valverde del Majano", "Valverde-Enrique", "Valverdejo", "Valverdón", "Vandellòs i lHospitalet de lInfant", "Vansa i Fórnols, La", "Vara de Rey", "Veciana", "Vecilla, La", "Vecinos", "Vedra", "Vega de Espinareda", "Vega de Infanzones", "Vega de Liébana", "Vega de Pas", "Vega de Ruiponce", "Vega de San Mateo", "Vega de Santa María", "Vega de Tera", "Vega de Tirados", "Vega de Valcarce", "Vega de Valdetronco", "Vega de Villalobos", "Vega del Codorno", "Vegacervera", "Vegadeo", "Vegalatrave", "Veganzones", "Vegaquemada", "Vegas de Matute", "Vegas del Condado", "Vegas del Genil", "Vegaviana", "Veguillas de la Sierra", "Veguillas, Las", "Veiga, A", "Vejer de la Frontera", "Velada", "Velamazán", "Velascálvaro", "Velayos", "Velefique", "Vélez de Benaudalla", "Vélez-Blanco", "Vélez-Málaga", "Vélez-Rubio", "Velilla", "Velilla de Cinca", "Velilla de Ebro", "Velilla de Jiloca", "Velilla de la Sierra", "Velilla de los Ajos", "Velilla de San Antonio", "Velilla del Río Carrión", "Vellés, La", "Vellisca", "Velliza", "Vellón, El", "Vencillón", "Vendrell, El", "Venialbo", "Venta de Baños", "Venta del Moro", "Ventalló", "Ventas con Peña Aguilera, Las", "Ventas de Huelma", "Ventas de Retamosa, Las", "Ventas de San Julián, Las", "Ventosa", "Ventosa de la Cuesta", "Ventosa del Río Almar", "Ventosilla y Tejadilla", "Ventrosa", "Venturada", "Vera", "Vera de Moncayo", "Verdú", "Verea", "Verger, el", "Verges", "Verín", "Vertavillo", "Vespella de Gaià", "Vezdemarbán", "Viacamp y Litera", "Viana", "Viana de Cega", "Viana de Duero", "Viana de Jadraque", "Viana do Bolo", "Viandar de la Vera", "Vianos", "Viator", "Vic", "Vícar", "Vicedo, O", "Vicién", "Victoria de Acentejo, La", "Victoria, La", "Vid de Bureba, La", "Vid de Ojeda, La", "Vid y Barrios, La", "Vidángoz/Bidankoze", "Vidayanes", "Videmala", "Vídola, La", "Vidrà", "Vidreres", "Vielha e Mijaran", "Vierlas", "Vigo", "Viguera", "Vila de Cruces", "Vila-real", "Vila-rodona", "Vila-sacra", "Vila-sana", "Vila-seca", "Vilabella", "Vilabertran", "Vilablareix", "Vilaboa", "Vilada", "Viladamat", "Viladasens", "Viladecans", "Viladecavalls", "Vilademuls", "Viladrau", "Vilafamés", "Vilafant", "Vilaflor", "Vilafranca de Bonany", "Vilafranca del Penedès", "Vilagarcía de Arousa", "Vilagrassa", "Vilajuïga", "Vilalba", "Vilalba dels Arcs", "Vilalba Sasserra", "Vilaller", "Vilallonga de Ter", "Vilallonga del Camp", "Vilamacolum", "Vilamalla", "Vilamaniscle", "Vilamarín", "Vilamartín de Valdeorras", "Vilamarxant", "Vilamòs", "Vilanant", "Vilanova dAlcolea", "Vilanova de Arousa", "Vilanova de Bellpuig", "Vilanova de la Barca", "Vilanova de lAguda", "Vilanova de Meià", "Vilanova de Prades", "Vilanova de Sau", "Vilanova de Segrià", "Vilanova del Camí", "Vilanova del Vallès", "Vilanova dEscornalbou", "Vilanova i la Geltrú", "Vilaplana", "Vilar de Barrio", "Vilar de Canes", "Vilar de Santos", "Vilardevós", "Vilariño de Conso", "Vilarmaior", "Vilasantar", "Vilassar de Dalt", "Vilassar de Mar", "Vilaür", "Vilavella, la", "Vilaverd", "Vilches", "Vilella Alta, La", "Vilella Baixa, La", "Vileña", "Villa de Don Fadrique, La", "Villa de Mazo", "Villa de Ves", "Villa del Campo", "Villa del Prado", "Villa del Rey", "Villa del Río", "Villabáñez", "Villabaruz de Campos", "Villabasta de Valdavia", "Villablanca", "Villablino", "Villabona", "Villabrágima", "Villabraz", "Villabrázaro", "Villabuena de Álava/Eskuernaga", "Villabuena del Puente", "Villacañas", "Villacarralón", "Villacarriedo", "Villacarrillo", "Villacastín", "Villacid de Campos", "Villacidaler", "Villaciervos", "Villaco", "Villaconancio", "Villaconejos", "Villaconejos de Trabaque", "Villada", "Villadangos del Páramo", "Villademor de la Vega", "Villadepera", "Villadiego", "Villadoz", "Villaeles de Valdavia", "Villaescusa", "Villaescusa", "Villaescusa de Haro", "Villaescusa de Roa", "Villaescusa la Sombría", "Villaespasa", "Villafáfila", "Villafeliche", "Villaferrueña", "Villaflor", "Villaflores", "Villafrades de Campos", "Villafranca", "Villafranca de Córdoba", "Villafranca de Duero", "Villafranca de Ebro", "Villafranca de la Sierra", "Villafranca de los Barros", "Villafranca de los Caballeros", "Villafranca del Bierzo", "Villafranca del Campo", "Villafranca del Cid/Vilafranca", "Villafranca Montes de Oca", "Villafrechós", "Villafruela", "Villafuerte", "Villafufre", "Villagalijo", "Villagarcía de Campos", "Villagarcía de la Torre", "Villagarcía del Llano", "Villagatón", "Villageriz", "Villagómez la Nueva", "Villagonzalo", "Villagonzalo de Tormes", "Villagonzalo Pedernales", "Villahán", "Villaharta", "Villahermosa", "Villahermosa del Campo", "Villahermosa del Río", "Villaherreros", "Villahoz", "Villajoyosa/Vila Joiosa, la", "Villalaco", "Villalán de Campos", "Villalar de los Comuneros", "Villalazán", "Villalba de Duero", "Villalba de Guardo", "Villalba de la Lampreana", "Villalba de la Loma", "Villalba de la Sierra", "Villalba de los Alcores", "Villalba de los Barros", "Villalba de los Llanos", "Villalba de Perejil", "Villalba de Rioja", "Villalba del Alcor", "Villalba del Rey", "Villalbarba", "Villalbilla", "Villalbilla de Burgos", "Villalbilla de Gumiel", "Villalcampo", "Villalcázar de Sirga", "Villalcón", "Villaldemiro", "Villalengua", "Villalgordo del Júcar", "Villalgordo del Marquesado", "Villalmanzo", "Villalobar de Rioja", "Villalobón", "Villalobos", "Villalón de Campos", "Villalonga", "Villalonso", "Villalpando", "Villalpardo", "Villalube", "Villaluenga de la Sagra", "Villaluenga de la Vega", "Villaluenga del Rosario", "Villamalea", "Villamalur", "Villamandos", "Villamanín", "Villamanrique", "Villamanrique de la Condesa", "Villamanrique de Tajo", "Villamanta", "Villamantilla", "Villamañán", "Villamartín", "Villamartín de Campos", "Villamartín de Don Sancho", "Villamayor", "Villamayor de Calatrava", "Villamayor de Campos", "Villamayor de Gállego", "Villamayor de los Montes", "Villamayor de Monjardín", "Villamayor de Santiago", "Villamayor de Treviño", "Villambistia", "Villamediana", "Villamediana de Iregua", "Villamedianilla", "Villamejil", "Villamena", "Villameriel", "Villamesías", "Villamiel", "Villamiel de la Sierra", "Villamiel de Toledo", "Villaminaya", "Villamol", "Villamontán de la Valduerna", "Villamor de los Escuderos", "Villamoratiel de las Matas", "Villamoronta", "Villamuelas", "Villamuera de la Cueza", "Villamuriel de Campos", "Villamuriel de Cerrato", "Villán de Tordesillas", "Villanázar", "Villangómez", "Villanova", "Villanúa", "Villanubla", "Villanueva de Alcardete", "Villanueva de Alcorón", "Villanueva de Algaidas", "Villanueva de Argaño", "Villanueva de Argecilla", "Villanueva de Ávila", "Villanueva de Azoague", "Villanueva de Bogas", "Villanueva de Cameros", "Villanueva de Campeán", "Villanueva de Carazo", "Villanueva de Castellón", "Villanueva de Córdoba", "Villanueva de Duero", "Villanueva de Gállego", "Villanueva de Gómez", "Villanueva de Gormaz", "Villanueva de Guadamejud", "Villanueva de Gumiel", "Villanueva de Huerva", "Villanueva de Jiloca", "Villanueva de la Cañada", "Villanueva de la Concepción", "Villanueva de la Condesa", "Villanueva de la Fuente", "Villanueva de la Jara", "Villanueva de la Reina", "Villanueva de la Serena", "Villanueva de la Sierra", "Villanueva de la Torre", "Villanueva de la Vera", "Villanueva de las Cruces", "Villanueva de las Manzanas", "Villanueva de las Peras", "Villanueva de las Torres", "Villanueva de los Caballeros", "Villanueva de los Castillejos", "Villanueva de los Infantes", "Villanueva de los Infantes", "Villanueva de Oscos", "Villanueva de Perales", "Villanueva de San Carlos", "Villanueva de San Juan", "Villanueva de San Mancio", "Villanueva de Sigena", "Villanueva de Tapia", "Villanueva de Teba", "Villanueva de Viver", "Villanueva del Aceral", "Villanueva del Ariscal", "Villanueva del Arzobispo", "Villanueva del Campillo", "Villanueva del Campo", "Villanueva del Conde", "Villanueva del Duque", "Villanueva del Fresno", "Villanueva del Pardillo", "Villanueva del Rebollar", "Villanueva del Rebollar de la Sierra", "Villanueva del Rey", "Villanueva del Río Segura", "Villanueva del Río y Minas", "Villanueva del Rosario", "Villanueva del Trabuco", "Villanueva Mesía", "Villanuño de Valdavia", "Villaobispo de Otero", "Villaornate y Castro", "Villapalacios", "Villaprovedo", "Villaquejida", "Villaquilambre", "Villaquirán de la Puebla", "Villaquirán de los Infantes", "Villar de Argañán", "Villar de Arnedo, El", "Villar de Cañas", "Villar de Ciervo", "Villar de Corneja", "Villar de Domingo García", "Villar de Fallaves", "Villar de Gallimazo", "Villar de la Encina", "Villar de la Yegua", "Villar de los Navarros", "Villar de Olalla", "Villar de Peralonso", "Villar de Plasencia", "Villar de Rena", "Villar de Samaniego", "Villar de Torre", "Villar del Ala", "Villar del Arzobispo", "Villar del Buey", "Villar del Campo", "Villar del Cobo", "Villar del Humo", "Villar del Infantado", "Villar del Olmo", "Villar del Pedroso", "Villar del Pozo", "Villar del Rey", "Villar del Río", "Villar del Salz", "Villar y Velasco", "Villaralbo", "Villaralto", "Villarcayo de Merindad de Castilla la Vieja", "Villardeciervos", "Villardefrades", "Villardiegua de la Ribera", "Villárdiga", "Villardompardo", "Villardondiego", "Villarejo", "Villarejo de Fuentes", "Villarejo de la Peñuela", "Villarejo de Montalbán", "Villarejo de Órbigo", "Villarejo de Salvanés", "Villarejo del Valle", "Villarejo-Periesteban", "Villares de Jadraque", "Villares de la Reina", "Villares de Órbigo", "Villares de Soria, Los", "Villares de Yeltes", "Villares del Saz", "Villares, Los", "Villargordo del Cabriel", "Villariezo", "Villarino de los Aires", "Villarluengo", "Villarmayor", "Villarmentero de Campos", "Villarmentero de Esgueva", "Villarmuerto", "Villarquemado", "Villarrabé", "Villarramiel", "Villarrasa", "Villarreal de Huerva", "Villarrín de Campos", "Villarrobledo", "Villarrodrigo", "Villarroya", "Villarroya de la Sierra", "Villarroya de los Pinares", "Villarroya del Campo", "Villarrubia de los Ojos", "Villarrubia de Santiago", "Villarrubio", "Villarta", "Villarta de los Montes", "Villarta de San Juan", "Villarta-Quintana", "Villas de la Ventosa", "Villasabariego", "Villasandino", "Villasarracino", "Villasayas", "Villasbuenas", "Villasbuenas de Gata", "Villasdardo", "Villaseca de Arciel", "Villaseca de Henares", "Villaseca de la Sagra", "Villaseca de Uceda", "Villaseco de los Gamitos", "Villaseco de los Reyes", "Villaseco del Pan", "Villaselán", "Villasequilla", "Villasexmir", "Villasila de Valdavia", "Villasrubias", "Villastar", "Villasur de Herreros", "Villatobas", "Villatoro", "Villatorres", "Villatoya", "Villatuelda", "Villatuerta", "Villaturde", "Villaturiel", "Villaumbrales", "Villava/Atarrabia", "Villavaliente", "Villavaquerín", "Villavelayo", "Villavellid", "Villavendimio", "Villaverde de Guadalimar", "Villaverde de Guareña", "Villaverde de Íscar", "Villaverde de Medina", "Villaverde de Montejo", "Villaverde de Rioja", "Villaverde del Monte", "Villaverde del Río", "Villaverde y Pasaconsol", "Villaverde-Mogina", "Villaveza de Valverde", "Villaveza del Agua", "Villavicencio de los Caballeros", "Villaviciosa", "Villaviciosa de Córdoba", "Villaviciosa de Odón", "Villavieja de Yeltes", "Villavieja del Lozoya", "Villaviudas", "Villayerno Morquillas", "Villayón", "Villazala", "Villazanzo de Valderaduey", "Villazopeque", "Villegas", "Villeguillo", "Villel", "Villel de Mesa", "Villena", "Villerías de Campos", "Villodre", "Villodrigo", "Villoldo", "Víllora", "Villores", "Villoria", "Villoruebo", "Villoruela", "Villoslada de Cameros", "Villota del Páramo", "Villovieco", "Vilobí del Penedès", "Vilobí dOnyar", "Vilopriu", "Viloria", "Viloria de Rioja", "Vilosell, El", "Vilueña, La", "Vilvestre", "Vilviestre del Pinar", "Vimbodí i Poblet", "Vimianzo", "Vinaceite", "Vinaixa", "Vinalesa", "Vinaròs", "Vindel", "Vinebre", "Viniegra de Abajo", "Viniegra de Arriba", "Vinuesa", "Vinyols i els Arcs", "Viñas", "Viñegra de Moraña", "Viñuela", "Viñuelas", "Visiedo", "Viso de San Juan, El", "Viso del Alcor, El", "Viso del Marqués", "Viso, El", "Vistabella", "Vistabella del Maestrazgo", "Vita", "Vitigudino", "Vitoria-Gasteiz", "Viveiro", "Vivel del Río Martín", "Viver", "Viver i Serrateix", "Viveros", "Vizcaínos", "Vizmanos", "Víznar", "Voto", "Vozmediano", "Wamba", "Xaló", "Xàtiva", "Xeraco", "Xeresa", "Xermade", "Xerta", "Xinzo de Limia", "Xirivella", "Xove", "Xunqueira de Ambía", "Xunqueira de Espadanedo", "Yaiza", "Yanguas", "Yanguas de Eresma", "Yátova", "Yébenes, Los", "Yebes", "Yebra", "Yebra de Basa", "Yecla", "Yecla de Yeltes", "Yécora/Iekora", "Yélamos de Abajo", "Yélamos de Arriba", "Yeles", "Yelo", "Yémeda", "Yepes", "Yernes y Tameza", "Yesa", "Yesa, La", "Yésero", "Yeste", "Yuncler", "Yunclillos", "Yuncos", "Yunquera", "Yunquera de Henares", "Yunta, La", "Zabalza/Zabaltza", "Zael", "Zafarraya", "Zafra", "Zafra de Záncara", "Zafrilla", "Zagra", "Zahara", "Zahínos", "Zaida, La", "Zaidín", "Zalamea de la Serena", "Zalamea la Real", "Zaldibar", "Zaldibia", "Zalduondo", "Zalla", "Zamarra", "Zamayón", "Zambrana", "Zamora", "Zamudio", "Zaorejas", "Zapardiel de la Cañada", "Zapardiel de la Ribera", "Zaragoza", "Zarapicos", "Zaratamo", "Zaratán", "Zarautz", "Zarra", "Zarratón", "Zarza de Granadilla", "Zarza de Montánchez", "Zarza de Pumareda, La", "Zarza de Tajo", "Zarza la Mayor", "Zarza-Capilla", "Zarza, La", "Zarza, La", "Zarzalejo", "Zarzosa", "Zarzosa de Río Pisuerga", "Zarzuela", "Zarzuela de Jadraque", "Zarzuela del Monte", "Zarzuela del Pinar", "Zas", "Zazuar", "Zeanuri", "Zeberio", "Zegama", "Zerain", "Zestoa", "Zierbena", "Zigoitia", "Ziordia", "Ziortza-Bolibar", "Zizur Mayor/Zizur Nagusia", "Zizurkil", "Zoma, La", "Zorita", "Zorita de la Frontera", "Zorita de los Canes", "Zorita del Maestrazgo", "Zorraquín", "Zotes del Páramo", "Zubia, La", "Zubieta", "Zucaina", "Zuera", "Zufre", "Zugarramurdi", "Zuheros", "Zuia", "Zújar", "Zumaia", "Zumarraga", "Zuñeda", "Zúñiga", "Zurgena"}));

        }
    }//GEN-LAST:event_jComboBox_filtroAZ1ActionPerformed

    private void jButton_volverListadoChefsDisponiblesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_volverListadoChefsDisponiblesActionPerformed
        // TODO add your handling code here:
        //Se borra el ComboBox
        jComboBox_poblacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{}));
        //Se borra la tabla
        dtm.setRowCount(0);
        //Se vuelve al panel anterior según tipo de usuario
        if (tipoUsuario == 1) {
            switchPanels(jPanel_opcionesAdmin);
        } else if (tipoUsuario == 2) {
            switchPanels(jPanel_opcionesChef);
        } else if (tipoUsuario == 3) {
            switchPanels(jPanel_opcionesCliente);
        }
    }//GEN-LAST:event_jButton_volverListadoChefsDisponiblesActionPerformed

    private void jButton_aplicarCambiosDisponibilidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_aplicarCambiosDisponibilidadActionPerformed
        // TODO add your handling code here:
        String estado = null;
        String comprobacion = obtenerDisponibilidadPoblacion(jTextField_usuario.getText(), jComboBox_poblacion.getSelectedItem().toString());

        if (jCheckBox_disponible.isSelected() && comprobacion == null) {
            estado = "Activo";
            altaDisponibilidad(estado);
        }
        if (jCheckBox_disponible.isSelected() && comprobacion != null) {
            estado = "Activo";
            String poblacion = jComboBox_poblacion.getSelectedItem().toString();
            modificarDisponibilidad(estado, poblacion);
        }

        if (!jCheckBox_disponible.isSelected() && comprobacion != null) {
            estado = "Inactivo";
            String poblacion = jComboBox_poblacion.getSelectedItem().toString();
            modificarDisponibilidad(estado, poblacion);
        }

    }//GEN-LAST:event_jButton_aplicarCambiosDisponibilidadActionPerformed

    private void jComboBox_filtroAZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_filtroAZActionPerformed
        // TODO add your handling code here:
        if (jComboBox_filtroAZ.getSelectedItem().toString().equals("De la A a la F")) {
            jComboBox_poblacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Ababuj", "Abades", "Abadía", "Abadín", "Abadiño", "Abáigar", "Abajas", "Ábalos", "Abaltzisketa", "Abánades", "Abanilla", "Abanto", "Abanto y Ciérvana-Abanto Zierbena", "Abarán", "Abarca de Campos", "Abárzuza/Abartzuza", "Abaurregaina/Abaurrea Alta", "Abaurrepea/Abaurrea Baja", "Abegondo", "Abejar", "Abejuela", "Abella de la Conca", "Abengibre", "Abenójar", "Aberin", "Abertura", "Abezames", "Abia de la Obispalía", "Abia de las Torres", "Abiego", "Abizanda", "Abla", "Ablanque", "Ablitas", "Abrera", "Abrucena", "Abusejo", "Acebeda, La", "Acebedo", "Acebo", "Acebrón, El", "Acedera", "Acehúche", "Aceituna", "Acered", "Aceuchal", "Adahuesca", "Adalia", "Adamuz", "Adanero", "Adeje", "Ademuz", "Adiós", "Adobes", "Ador", "Adra", "Adrada de Haza", "Adrada de Pirón", "Adrada, La", "Adradas", "Adrados", "Adsubia", "Aduna", "Agaete", "Agallas", "Àger", "Agolada", "Agón", "Agoncillo", "Agost", "Agramunt", "Ágreda", "Agres", "Agrón", "Aguadulce", "Aguarón", "Aguas Cándidas", "Aguasal", "Aguatón", "Aguaviva", "Agudo", "Agüero", "Aguilafuente", "Aguilar de Bureba", "Aguilar de Campoo", "Aguilar de Campos", "Aguilar de Codés", "Aguilar de la Frontera", "Aguilar de Segarra", "Aguilar del Alfambra", "Aguilar del Río Alhama", "Águilas", "Aguilón", "Agüimes", "Agullana", "Agullent", "Agulo", "Ahigal", "Ahigal de los Aceiteros", "Ahigal de Villarino", "Ahillones", "Aia", "Aibar/Oibar", "Aielo de Malferit", "Aielo de Rugat", "Aiguafreda", "Aiguamúrcia", "Aiguaviva", "Aigües", "Aín", "Aínsa-Sobrarbe", "Ainzón", "Aisa", "Aitona", "Aizarnazabal", "Ajalvir", "Ajamil de Cameros", "Ajangiz", "Ajofrín", "Alacant/Alicante", "Alacón", "Aladrén", "Alaejos", "Alagón", "Alagón del Río", "Alaior", "Alájar", "Alajeró", "Alameda", "Alameda de Gardón, La", "Alameda de la Sagra", "Alameda del Valle", "Alamedilla", "Alamedilla, La", "Alamillo", "Alaminos", "Álamo, El", "Alamús, Els", "Alange", "Alanís", "Alaquàs", "Alar del Rey", "Alaraz", "Alarba", "Alarcón", "Alarilla", "Alaró", "Alàs i Cerc", "Alatoz", "Alba", "Alba de Cerrato", "Alba de Tormes", "Alba de Yeltes", "Albacete", "Albagés, L", "Albaida", "Albaida del Aljarafe", "Albal", "Albalá", "Albaladejo", "Albaladejo del Cuende", "Albalat de la Ribera", "Albalat dels Sorells", "Albalat dels Tarongers", "Albalate de Cinca", "Albalate de las Nogueras", "Albalate de Zorita", "Albalate del Arzobispo", "Albalatillo", "Albánchez", "Albanchez de Mágina", "Albanyà", "Albares", "Albarracín", "Albarreal de Tajo", "Albatana", "Albatàrrec", "Albatera", "Albelda", "Albelda de Iregua", "Albendea", "Albendiego", "Albentosa", "Alberca de Záncara, La", "Alberca, La", "Alberguería de Argañán, La", "Alberic", "Alberite", "Alberite de San Juan", "Albero Alto", "Albero Bajo", "Alberuela de Tubo", "Albesa", "Albeta", "Albi, L", "Albillos", "Albinyana", "Albiol, L", "Albiztur", "Albocàsser", "Alboloduy", "Albolote", "Albondón", "Albons", "Alborache", "Alboraya", "Alborea", "Alborge", "Albornos", "Albox", "Albudeite", "Albuera, La", "Albuixech", "Albuñán", "Albuñol", "Albuñuelas", "Alburquerque", "Alcabón", "Alcadozo", "Alcaine", "Alcalá de Ebro", "Alcalá de Guadaíra", "Alcalá de Gurrea", "Alcalá de Henares", "Alcalá de la Selva", "Alcalá de la Vega", "Alcalá de los Gazules", "Alcalá de Moncayo", "Alcalà de Xivert", "Alcalá del Júcar", "Alcalá del Obispo", "Alcalá del Río", "Alcalá del Valle", "Alcalá la Real", "Alcalalí", "Alcampell", "Alcanadre", "Alcanar", "Alcanó", "Alcántara", "Alcantarilla", "Alcàntera de Xúquer", "Alcantud", "Alcañices", "Alcañiz", "Alcañizo", "Alcaracejos", "Alcaraz", "Alcarràs", "Alcàsser", "Alcaucín", "Alcaudete", "Alcaudete de la Jara", "Alcázar de San Juan", "Alcázar del Rey", "Alcazarén", "Alcázares, Los", "Alcoba", "Alcobendas", "Alcocer", "Alcocer de Planes", "Alcocero de Mola", "Alcohujate", "Alcolea", "Alcolea de Calatrava", "Alcolea de Cinca", "Alcolea de las Peñas", "Alcolea de Tajo", "Alcolea del Pinar", "Alcolea del Río", "Alcoleja", "Alcoletge", "Alcollarín", "Alconaba", "Alconada", "Alconada de Maderuelo", "Alconchel", "Alconchel de Ariza", "Alconchel de la Estrella", "Alconera", "Alcóntar", "Alcora, l", "Alcorcón", "Alcorisa", "Alcoroches", "Alcover", "Alcoy/Alcoi", "Alcubierre", "Alcubilla de Avellaneda", "Alcubilla de las Peñas", "Alcubilla de Nogales", "Alcubillas", "Alcublas", "Alcúdia", "Alcúdia de Crespins, l", "Alcudia de Monteagud", "Alcudia de Veo", "Alcúdia, l", "Alcuéscar", "Aldaia", "Aldea de San Miguel", "Aldea de San Nicolás, La", "Aldea del Cano", "Aldea del Fresno", "Aldea del Obispo", "Aldea del Obispo, La", "Aldea del Rey", "Aldea en Cabo", "Aldea Real", "Aldea, L", "Aldeacentenera", "Aldeacipreste", "Aldeadávila de la Ribera", "Aldealafuente", "Aldealcorvo", "Aldealengua", "Aldealengua de Pedraza", "Aldealengua de Santa María", "Aldealices", "Aldealpozo", "Aldealseñor", "Aldeamayor de San Martín", "Aldeanueva de Barbarroya", "Aldeanueva de Ebro", "Aldeanueva de Figueroa", "Aldeanueva de Guadalajara", "Aldeanueva de la Serrezuela", "Aldeanueva de la Sierra", "Aldeanueva de la Vera", "Aldeanueva de San Bartolomé", "Aldeanueva de Santa Cruz", "Aldeanueva del Camino", "Aldeanueva del Codonal", "Aldeaquemada", "Aldearrodrigo", "Aldearrubia", "Aldeaseca", "Aldeaseca de Alba", "Aldeaseca de la Frontera", "Aldeasoña", "Aldeatejada", "Aldeavieja de Tormes", "Aldehorno", "Aldehuela de Jerte", "Aldehuela de la Bóveda", "Aldehuela de Liestos", "Aldehuela de Periáñez", "Aldehuela de Yeltes", "Aldehuela del Codonal", "Aldehuela, La", "Aldehuelas, Las", "Aldeire", "Aldeonte", "Aldover", "Aledo", "Alegia", "Alegría-Dulantzi", "Aleixar, L", "Alella", "Alentisque", "Alerre", "Alesanco", "Alesón", "Alfacar", "Alfafar", "Alfafara", "Alfajarín", "Alfambra", "Alfamén", "Alfántega", "Alfara de Carles", "Alfara de la Baronia", "Alfara del Patriarca", "Alfaraz de Sayago", "Alfarnate", "Alfarnatejo", "Alfaro", "Alfarp", "Alfarràs", "Alfarrasí", "Alfàs del Pi, l", "Alfauir", "Alfés", "Alfondeguilla", "Alforja", "Alforque", "Alfoz", "Alfoz de Bricia", "Alfoz de Lloredo", "Alfoz de Quintanadueñas", "Alfoz de Santa Gadea", "Algaba, La", "Algadefe", "Algaida", "Algámitas", "Algar", "Algar de Mesa", "Algar de Palancia", "Algarinejo", "Algarra", "Algarrobo", "Algatocín", "Algeciras", "Algemesí", "Algerri", "Algete", "Algimia de Alfara", "Algimia de Almonacid", "Alginet", "Algodonales", "Algodre", "Algora", "Algorfa", "Alguaire", "Alguazas", "Algueña", "Alhabia", "Alhama de Almería", "Alhama de Aragón", "Alhama de Granada", "Alhama de Murcia", "Alhambra", "Alhaurín de la Torre", "Alhaurín el Grande", "Alhendín", "Alhóndiga", "Alía", "Aliaga", "Aliaguilla", "Alicún", "Alicún de Ortega", "Alija del Infantado", "Alins", "Alió", "Alique", "Aliseda", "Aliud", "Aljaraque", "Aljucén", "Alkiza", "Allande", "Allariz", "Allepuz", "Aller", "Allín/Allin", "Allo", "Alloza", "Allueva", "Almacelles", "Almáchar", "Almadén", "Almadén de la Plata", "Almadenejos", "Almadrones", "Almagro", "Almajano", "Almaluez", "Almansa", "Almanza", "Almaraz", "Almaraz de Duero", "Almarcha, La", "Almargen", "Almarza", "Almarza de Cameros", "Almàssera", "Almatret", "Almazán", "Almazora/Almassora", "Almazul", "Almedíjar", "Almedina", "Almedinilla", "Almegíjar", "Almeida de Sayago", "Almenar", "Almenar de Soria", "Almenara", "Almenara de Adaja", "Almenara de Tormes", "Almendra", "Almendral", "Almendral de la Cañada", "Almendralejo", "Almendro, El", "Almendros", "Almensilla", "Almería", "Almiserà", "Almochuel", "Almócita", "Almodóvar del Campo", "Almodóvar del Pinar", "Almodóvar del Río", "Almogía", "Almoguera", "Almohaja", "Almoharín", "Almoines", "Almolda, La", "Almonacid de la Cuba", "Almonacid de la Sierra", "Almonacid de Toledo", "Almonacid de Zorita", "Almonacid del Marquesado", "Almonaster la Real", "Almonte", "Almoradí", "Almorox", "Almoster", "Almudaina", "Almudévar", "Almunia de Doña Godina, La", "Almunia de San Juan", "Almuniente", "Almuñécar", "Almuradiel", "Almussafes", "Alobras", "Alocén", "Alonsotegi", "Álora", "Alòs de Balaguer", "Alosno", "Alovera", "Alozaina", "Alp", "Alpandeire", "Alpanseque", "Alpartir", "Alpedrete", "Alpens", "Alpeñés", "Alpera", "Alpicat", "Alpuente", "Alpujarra de la Sierra", "Alqueria dAsnar, l", "Alqueria de la Comtessa, l", "Alquerías del Niño Perdido", "Alquézar", "Alquife", "Alsodux", "Alt Àneu", "Altable", "Altafulla", "Altarejos", "Altea", "Altorricón", "Altos, Los", "Altsasu/Alsasua", "Altura", "Altzaga", "Altzo", "Alustante", "Alzira", "Amavida", "Amayuelas de Arriba", "Ambel", "Ambite", "Amer", "Ames", "Améscoa Baja", "Ametlla de Mar, L", "Ametlla del Vallès, L", "Ameyugo", "Amezketa", "Amieva", "Amoeiro", "Amorebieta-Etxano", "Amoroto", "Ampolla, L", "Amposta", "Ampudia", "Ampuero", "Amurrio", "Amusco", "Amusquillo", "Anadón", "Anaya", "Anaya de Alba", "Anchuelo", "Anchuras", "Ancín/Antzin", "Andavías", "Andilla", "Andoain", "Andorra", "Andosilla", "Andratx", "Andújar", "Anento", "Anglès", "Anglesola", "Angón", "Anguciana", "Angüés", "Anguiano", "Anguita", "Anguix", "Anievas", "Aniñón", "Anna", "Anoeta", "Anquela del Ducado", "Anquela del Pedregal", "Ansó", "Ansoáin/Antsoain", "Antas", "Antas de Ulla", "Antella", "Antequera", "Antigua", "Antigua, La", "Antigüedad", "Antillón", "Antzuola", "Anue", "Añana", "Añe", "Añón de Moncayo", "Añora", "Añorbe", "Añover de Tajo", "Añover de Tormes", "Aoiz/Agoitz", "Arabayona de Mógica", "Aracena", "Arafo", "Aragüés del Puerto", "Arahal", "Arahuetes", "Araitz", "Arakaldo", "Arakil", "Arama", "Aramaio", "Aranarache/Aranaratxe", "Arancón", "Aranda de Duero", "Aranda de Moncayo", "Arándiga", "Arandilla", "Arandilla del Arroyo", "Aranga", "Aranguren", "Aranjuez", "Arano", "Arantza", "Arantzazu", "Aranzueque", "Arañuel", "Arapiles", "Aras", "Aras de los Olmos", "Arauzo de Miel", "Arauzo de Salce", "Arauzo de Torre", "Arbancón", "Arbeca", "Arbeteta", "Arbizu", "Arbo", "Arboç, L", "Arboleas", "Arbolí", "Arbúcies", "Arcas del Villar", "Arce/Artzi", "Arcediano", "Arcenillas", "Archena", "Árchez", "Archidona", "Arcicóllar", "Arco, El", "Arconada", "Arcones", "Arcos", "Arcos de Jalón", "Arcos de la Frontera", "Arcos de la Polvorosa", "Arcos de la Sierra", "Arcos de las Salinas", "Arcos, Los", "Ardales", "Ardisa", "Ardón", "Areatza", "Arellano", "Arén", "Arenal, El", "Arenales de San Gregorio", "Arenas", "Arenas de Iguña", "Arenas de San Juan", "Arenas de San Pedro", "Arenas del Rey", "Arenillas", "Arenillas de Riopisuerga", "Arens de Lledó", "Arenys de Mar", "Arenys de Munt", "Arenzana de Abajo", "Arenzana de Arriba", "Ares", "Ares del Maestrat", "Areso", "Aretxabaleta", "Arevalillo", "Arevalillo de Cega", "Arévalo", "Arévalo de la Sierra", "Argamasilla de Alba", "Argamasilla de Calatrava", "Arganda del Rey", "Arganza", "Argañín", "Argavieso", "Argecilla", "Argelaguer", "Argelita", "Argençola", "Argente", "Argentera, L", "Argentona", "Argés", "Argoños", "Arguedas", "Arguis", "Arguisuelas", "Argujillo", "Aria", "Ariany", "Aribe", "Arico", "Arija", "Ariño", "Ariza", "Arjona", "Arjonilla", "Arlanzón", "Armallones", "Armañanzas", "Armentera, L", "Armenteros", "Armilla", "Armiñón", "Armuña", "Armuña de Almanzora", "Armuña de Tajuña", "Arnedillo", "Arnedo", "Arnes", "Arnoia, A", "Arnuero", "Aroche", "Arona", "Arquillinos", "Arquillos", "Arrabalde", "Arraia-Maeztu", "Arrancacepas", "Arrankudiaga", "Arrasate/Mondragón", "Arratzu", "Arraya de Oca", "Arrazua-Ubarrundia", "Arrecife", "Arredondo", "Arres", "Arriate", "Arrieta", "Arrigorriaga", "Arroba de los Montes", "Arróniz", "Arroyo de la Encomienda", "Arroyo de la Luz", "Arroyo de las Fraguas", "Arroyo de San Serván", "Arroyo del Ojanco", "Arroyomolinos", "Arroyomolinos", "Arroyomolinos de la Vera", "Arroyomolinos de León", "Arruazu", "Arrúbal", "Arsèguel", "Artà", "Artajona", "Artana", "Artazu", "Artea", "Arteixo", "Artenara", "Artés", "Artesa de Lleida", "Artesa de Segre", "Artieda", "Artzentales", "Artziniega", "Arucas", "Arzúa", "Ascó", "Asín", "Aspa", "Aspariegos", "Asparrena", "Aspe", "Asteasu", "Astigarraga", "Astillero, El", "Astorga", "Astudillo", "Asturianos", "Atajate", "Atalaya", "Atalaya del Cañavate", "Atalaya, La", "Atanzón", "Atapuerca", "Ataquines", "Atarfe", "Ataun", "Atazar, El", "Atea", "Ateca", "Atez", "Atienza", "Atxondo", "Atzeneta dAlbaida", "Atzeneta del Maestrat", "Aulesti", "Auñón", "Auritz/Burguete", "Ausejo", "Ausejo de la Sierra", "Ausines, Los", "Autilla del Pino", "Autillo de Campos", "Autol", "Aveinte", "Avellaneda", "Avellanes i Santa Linya, Les", "Avellanosa de Muñó", "Avià", "Ávila", "Avilés", "Avinyó", "Avinyonet de Puigventós", "Avinyonet del Penedès", "Avión", "Ayala/Aiara", "Ayamonte", "Ayegui/Aiegi", "Ayerbe", "Ayllón", "Ayna", "Ayódar", "Ayoó de Vidriales", "Ayora", "Ayuela", "Azagra", "Azaila", "Azanuy-Alins", "Azara", "Azkoitia", "Azlor", "Aznalcázar", "Aznalcóllar", "Azofra", "Azpeitia", "Azuaga", "Azuara", "Azuébar", "Azuelo", "Azuqueca de Henares", "Azután", "Babilafuente", "Bacares", "Badajoz", "Badalona", "Badarán", "Bádenas", "Badia del Vallès", "Badolatosa", "Badules", "Baélls", "Baena", "Baeza", "Bagà", "Báguena", "Bagüés", "Bahabón", "Bahabón de Esgueva", "Baides", "Bailén", "Bailo", "Baiona", "Baix Pallars", "Bakaiku", "Bakio", "Balaguer", "Balazote", "Balbases, Los", "Balboa", "Balconchán", "Baldellou", "Baleira", "Balenyà", "Baliarrain", "Ballestero, El", "Ballesteros de Calatrava", "Ballobar", "Balmaseda", "Balones", "Balsa de Ves", "Balsareny", "Baltanás", "Baltar", "Banastás", "Bande", "Banyalbufar", "Banyeres de Mariola", "Banyeres del Penedès", "Banyoles", "Baña, A", "Bañares", "Bañeza, La", "Bañobárez", "Bañón", "Baños de Ebro/Mañueta", "Baños de la Encina", "Baños de Molgas", "Baños de Montemayor", "Baños de Río Tobía", "Baños de Rioja", "Baños de Tajo", "Baños de Valdearados", "Bañuelos", "Bañuelos de Bureba", "Baquerín de Campos", "Barajas de Melo", "Barakaldo", "Baralla", "Barañain", "Baraona", "Barásoain", "Barbadás", "Barbadillo", "Barbadillo de Herreros", "Barbadillo del Mercado", "Barbadillo del Pez", "Barbalos", "Barbarin", "Barbastro", "Barbate", "Barbens", "Barberà de la Conca", "Barberà del Vallès", "Bárboles", "Barbolla", "Barbués", "Barbuñales", "Barca", "Bárcabo", "Barcarrota", "Barcelona", "Bárcena de Campos", "Bárcena de Cicero", "Bárcena de Pie de Concha", "Barceo", "Barchín del Hoyo", "Barcial de la Loma", "Barcial del Barco", "Barcience", "Barco de Ávila, El", "Barco de Valdeorras, O", "Barcones", "Bardallur", "Bareyo", "Bargas", "Bargota", "Barillas", "Barjas", "Barlovento", "Baronia de Rialb, La", "Barracas", "Barrachina", "Barraco, El", "Barrado", "Barrax", "Barreiros", "Barrika", "Barrio de Muñó", "Barriopedro", "Barrios de Bureba, Los", "Barrios de Colina", "Barrios de Luna, Los", "Barrios, Los", "Barro", "Barromán", "Barruecopardo", "Barruelo de Santullán", "Barruelo del Valle", "Barrundia", "Barx", "Barxeta", "Basaburua", "Basardilla", "Basauri", "Bàscara", "Basconcillos del Tozo", "Báscones de Ojeda", "Bascuñana", "Bascuñana de San Pedro", "Bassella", "Bastida, La", "Batea", "Baterno", "Batres", "Bausen", "Bayárcal", "Bayarque", "Bayubas de Abajo", "Bayubas de Arriba", "Baza", "Baztan", "Bea", "Beade", "Beamud", "Beariz", "Beas", "Beas de Granada", "Beas de Guadix", "Beas de Segura", "Beasain", "Becedas", "Becedillas", "Beceite", "Becerreá", "Becerril de Campos", "Becerril de la Sierra", "Becilla de Valderaduey", "Bédar", "Bedia", "Bedmar y Garcíez", "Begíjar", "Begonte", "Begues", "Begur", "Beintza-Labaien", "Beire", "Beires", "Beizama", "Béjar", "Bejís", "Belalcázar", "Belascoáin", "Belauntza", "Belbimbre", "Belchite", "Beleña", "Bèlgida", "Belianes", "Belinchón", "Bell-lloc dUrgell", "Bellaguarda", "Bellcaire dEmpordà", "Bellcaire dUrgell", "Bellmunt del Priorat", "Bellmunt dUrgell", "Bello", "Bellprat", "Bellpuig", "Bellreguard", "Bellús", "Bellvei", "Bellver de Cerdanya", "Bellvís", "Belmez", "Bélmez de la Moraleda", "Belmonte", "Belmonte de Campos", "Belmonte de Gracián", "Belmonte de Miranda", "Belmonte de San José", "Belmonte de Tajo", "Belmontejo", "Belorado", "Belver de Cinca", "Belver de los Montes", "Belvís de la Jara", "Belvís de Monroy", "Bembibre", "Benabarre", "Benacazón", "Benadalid", "Benafarces", "Benafer", "Benafigos", "Benagéber", "Benaguasil", "Benahadux", "Benahavís", "Benalauría", "Benalmádena", "Benalúa", "Benalúa de las Villas", "Benalup-Casas Viejas", "Benamargosa", "Benamaurel", "Benamejí", "Benamocarra", "Benaocaz", "Benaoján", "Benarrabá", "Benasal", "Benasau", "Benasque", "Benatae", "Benavent de Segrià", "Benavente", "Benavides", "Benavites", "Benegiles", "Beneixama", "Beneixida", "Benejúzar", "Benetússer", "Benferri", "Beniarbeig", "Beniardá", "Beniarjó", "Beniarrés", "Beniatjar", "Benicarló", "Benicasim/Benicàssim", "Benicolet", "Benicull de Xúquer", "Benidoleig", "Benidorm", "Beniel", "Benifaió", "Benifairó de la Valldigna", "Benifairó de les Valls", "Benifallet", "Benifallim", "Benifato", "Beniflá", "Benigánim", "Benigembla", "Benijófar", "Benilloba", "Benillup", "Benimantell", "Benimarfull", "Benimassot", "Benimeli", "Benimodo", "Benimuslem", "Beniparrell", "Benirredrà", "Benisanó", "Benissa", "Benissanet", "Benissoda", "Benisuera", "Benitachell/Poble Nou de Benitatxell, el", "Benitagla", "Benizalón", "Benlloch", "Benquerencia", "Benquerencia de la Serena", "Bentarique", "Benuza", "Bera", "Berango", "Berantevilla", "Beranuy", "Berastegi", "Beratón", "Berbegal", "Berberana", "Berbinzana", "Berceo", "Bercero", "Berceruelo", "Bérchules", "Bercial", "Bercial de Zapardiel", "Bercianos del Páramo", "Bercianos del Real Camino", "Bercimuel", "Berdejo", "Berga", "Bergara", "Bergasa", "Bergasillas Bajera", "Berge", "Bergondo", "Beriáin", "Berja", "Berlanas, Las", "Berlanga", "Berlanga de Duero", "Berlanga del Bierzo", "Berlangas de Roa", "Bermellar", "Bermeo", "Bermillo de Sayago", "Bernardos", "Bernedo", "Berninches", "Bernuy de Porreros", "Bernuy-Zapardiel", "Berriatua", "Berrioplano/Berriobeiti", "Berriozar", "Berriz", "Berrobi", "Berrocal", "Berrocal de Huebra", "Berrocal de Salvatierra", "Berrocalejo", "Berrocalejo de Aragona", "Berrueces", "Berrueco", "Berrueco, El", "Bertizarana", "Berzocana", "Berzosa de Bureba", "Berzosa del Lozoya", "Berzosilla", "Besalú", "Bescanó", "Betancuria", "Betanzos", "Betelu", "Bétera", "Beteta", "Betxí", "Beuda", "Bezares", "Bezas", "Biar", "Bicorp", "Bidaurreta", "Bidegoian", "Biel", "Bielsa", "Bienservida", "Bienvenida", "Bierge", "Biescas", "Bigastro", "Bigues i Riells", "Bijuesca", "Bilbao", "Bimenes", "Binaced", "Binéfar", "Binissalem", "Biosca", "Biota", "Bisaurri", "Bisbal de Falset, La", "Bisbal del Penedès, La", "Bisbal dEmpordà, La", "Biscarrués", "Bisimbre", "Biure", "Biurrun-Olcoz", "Blacos", "Blanca", "Blancafort", "Blancas", "Blancos, Os", "Blanes", "Blascomillán", "Blasconuño de Matacabras", "Blascosancho", "Blázquez, Los", "Blecua y Torres", "Blesa", "Bliecos", "Boada", "Boada de Campos", "Boadella i les Escaules", "Boadilla de Rioseco", "Boadilla del Camino", "Boadilla del Monte", "Boal", "Boalo, El", "Bobadilla", "Bobadilla del Campo", "Boborás", "Boca de Huérgano", "Bocairent", "Boceguillas", "Bocigas", "Bocos de Duero", "Bodera, La", "Bodón, El", "Bodonal de la Sierra", "Boecillo", "Bogajo", "Bogarra", "Bohodón, El", "Bohonal de Ibor", "Bohoyo", "Boimorto", "Boiro", "Bola, A", "Bolaños de Calatrava", "Bolaños de Campos", "Bolbaite", "Bollullos de la Mitación", "Bollullos Par del Condado", "Bolo, O", "Boltaña", "Bolulla", "Bolvir", "Bonansa", "Bonares", "Bonastre", "Bonete", "Boniches", "Bonilla de la Sierra", "Bonillo, El", "Bonrepòs i Mirambell", "Boñar", "Boqueixón", "Boquiñeni", "Borau", "Bordalba", "Bòrdes, Es", "Bordils", "Bordón", "Borge, El", "Borges Blanques, Les", "Borges del Camp, Les", "Borja", "Borjabad", "Bormujos", "Bornos", "Borobia", "Borox", "Borrassà", "Borredà", "Borrenes", "Borriana/Burriana", "Borriol", "Bosque, El", "Bossòst", "Bot", "Botarell", "Botija", "Botorrita", "Bouza, La", "Bóveda", "Bóveda de Toro, La", "Bóveda del Río Almar", "Bovera", "Bozoó", "Brabos", "Bràfim", "Brahojos de Medina", "Brañosera", "Braojos", "Brazacorta", "Brazatortas", "Brazuelo", "Brea de Aragón", "Brea de Tajo", "Breda", "Brenes", "Breña Alta", "Breña Baja", "Bretó", "Bretocino", "Brieva", "Brieva de Cameros", "Brihuega", "Brime de Sog", "Brime de Urz", "Brincones", "Briñas", "Brión", "Briones", "Briviesca", "Bronchales", "Broto", "Brozas", "Bruc, El", "Brull, El", "Brunete", "Brunyola", "Buberos", "Bubierca", "Bubión", "Buciegas", "Budia", "Buenache de Alarcón", "Buenache de la Sierra", "Buenamadre", "Buenaventura", "Buenavista", "Buenavista de Valdavia", "Buenavista del Norte", "Buendía", "Bueña", "Bueu", "Bufali", "Bugarra", "Bugedo", "Búger", "Buitrago", "Buitrago del Lozoya", "Bujalance", "Bujalaro", "Bujaraloz", "Bularros", "Bulbuente", "Bullas", "Buniel", "Bunyola", "Buñol", "Buñuel", "Burbáguena", "Burela", "Bureta", "Burganes de Valverde", "Burgo de Ebro, El", "Burgo de Osma-Ciudad de Osma", "Burgo Ranero, El", "Burgo, El", "Burgohondo", "Burgos", "Burgui/Burgi", "Burguillos", "Burguillos de Toledo", "Burguillos del Cerro", "Burjassot", "Burlada/Burlata", "Burón", "Burujón", "Busot", "Busquístar", "Bustares", "Bustarviejo", "Buste, El", "Bustillo de Chaves", "Bustillo de la Vega", "Bustillo del Oro", "Bustillo del Páramo", "Bustillo del Páramo de Carrión", "Busto de Bureba", "Busto, El", "Busturia", "Cabacés", "Cabaco, El", "Caballar", "Cabana de Bergantiños", "Cabanabona", "Cabanas", "Cabanelles", "Cabanes", "Cabanes", "Cabanillas", "Cabanillas de la Sierra", "Cabanillas del Campo", "Cabanyes, Les", "Cabañas de Ebro", "Cabañas de la Sagra", "Cabañas de Polendos", "Cabañas de Sayago", "Cabañas de Yepes", "Cabañas del Castillo", "Cabañas Raras", "Cabañes de Esgueva", "Cabeza de Béjar, La", "Cabeza del Buey", "Cabeza del Caballo", "Cabeza la Vaca", "Cabezabellosa", "Cabezabellosa de la Calzada", "Cabezamesada", "Cabezarados", "Cabezarrubias del Puerto", "Cabezas de Alambre", "Cabezas de San Juan, Las", "Cabezas del Pozo", "Cabezas del Villar", "Cabezas Rubias", "Cabezón de Cameros", "Cabezón de la Sal", "Cabezón de la Sierra", "Cabezón de Liébana", "Cabezón de Pisuerga", "Cabezón de Valderaduey", "Cabezuela", "Cabezuela del Valle", "Cabizuela", "Cabó", "Cabolafuente", "Cabra", "Cabra de Mora", "Cabra del Camp", "Cabra del Santo Cristo", "Cabrales", "Cabranes", "Cabredo", "Cabrejas del Campo", "Cabrejas del Pinar", "Cabrera dAnoia", "Cabrera de Mar", "Cabrera, La", "Cabrerizos", "Cabrero", "Cabreros del Monte", "Cabreros del Río", "Cabrillanes", "Cabrillas", "Cabrils", "Cabuérniga", "Cacabelos", "Cáceres", "Cachorrilla", "Cacín", "Cadalso", "Cadalso de los Vidrios", "Cadaqués", "Cádiar", "Cádiz", "Cadreita", "Cadrete", "Cájar", "Cala", "Calabazas de Fuentidueña", "Calaceite", "Calaf", "Calafell", "Calahorra", "Calahorra de Boedo", "Calahorra, La", "Calamocha", "Calamonte", "Calanda", "Calañas", "Calasparra", "Calatañazor", "Calatayud", "Calatorao", "Calcena", "Caldas de Reis", "Caldearenas", "Calders", "Caldes de Malavella", "Caldes de Montbui", "Caldes dEstrac", "Calella", "Calera de León", "Calera y Chozas", "Caleruega", "Caleruela", "Calicasas", "Càlig", "Calldetenes", "Calles", "Callosa de Segura", "Callosa dEn Sarrià", "Callús", "Calmarza", "Calomarde", "Calonge", "Calonge de Segarra", "Calp", "Caltojar", "Calvarrasa de Abajo", "Calvarrasa de Arriba", "Calvià", "Calvos de Randín", "Calzada de Béjar, La", "Calzada de Calatrava", "Calzada de Don Diego", "Calzada de los Molinos", "Calzada de Oropesa", "Calzada de Valdunciel", "Calzada del Coto", "Calzadilla", "Calzadilla de los Barros", "Calzadilla de Tera", "Camaleño", "Camañas", "Camarasa", "Camarena", "Camarena de la Sierra", "Camarenilla", "Camargo", "Camarillas", "Camariñas", "Camarles", "Camarma de Esteruelas", "Camarzana de Tera", "Camas", "Cambados", "Cambil", "Cambre", "Cambrils", "Caminomorisco", "Caminreal", "Camós", "Campana, La", "Campanario", "Campanet", "Campaspero", "Campazas", "Campdevànol", "Campelles", "Campello, el", "Campezo/Kanpezu", "Campillo de Altobuey", "Campillo de Aragón", "Campillo de Aranda", "Campillo de Arenas", "Campillo de Azaba", "Campillo de Deleitosa", "Campillo de Dueñas", "Campillo de la Jara, El", "Campillo de Llerena", "Campillo de Ranas", "Campillo, El", "Campillo, El", "Campillos", "Campillos-Paravientos", "Campillos-Sierra", "Campins", "Campisábalos", "Campllong", "Campo", "Campo de Criptana", "Campo de Mirra/Camp de Mirra, el", "Campo de Peñaranda, El", "Campo de San Pedro", "Campo de Villavidel", "Campo Lameiro", "Campo Lugar", "Campo Real", "Campofrío", "Campolara", "Camponaraya", "Campoo de Enmedio", "Campoo de Yuso", "Camporredondo", "Camporrélls", "Camporrobles", "Campos", "Campos del Paraíso", "Campos del Río", "Campotéjar", "Camprodon", "Camprovín", "Camuñas", "Canal de Berdún", "Canalejas de Peñafiel", "Canalejas del Arroyo", "Canales", "Canales de la Sierra", "Canals", "Candamo", "Candasnos", "Candelaria", "Candelario", "Candeleda", "Candilichera", "Candín", "Canejan", "Canena", "Canencia", "Canet dAdri", "Canet de Mar", "Canet dEn Berenguer", "Canet lo Roig", "Canfranc", "Cangas", "Cangas de Onís", "Cangas del Narcea", "Canicosa de la Sierra", "Caniles", "Canillas de Abajo", "Canillas de Aceituno", "Canillas de Albaida", "Canillas de Esgueva", "Canillas de Río Tuerto", "Canjáyar", "Canonja, La", "Canovelles", "Cànoves i Samalús", "Canredondo", "Cantabrana", "Cantagallo", "Cantalapiedra", "Cantalejo", "Cantallops", "Cantalojas", "Cantalpino", "Cantaracillo", "Cantavieja", "Cantillana", "Cantimpalos", "Cantiveros", "Cantoria", "Canyelles", "Cañada", "Cañada de Benatanduz", "Cañada de Calatrava", "Cañada de Verich, La", "Cañada del Hoyo", "Cañada Juncosa", "Cañada Rosal", "Cañada Vellida", "Cañamaque", "Cañamares", "Cañamero", "Cáñar", "Cañas", "Cañavate, El", "Cañaveral", "Cañaveral de León", "Cañaveras", "Cañaveruelas", "Cañete", "Cañete de las Torres", "Cañete la Real", "Cañiza, A", "Cañizal", "Cañizar", "Cañizar del Olivar", "Cañizares", "Cañizo", "Capafonts", "Caparroso", "Capçanes", "Capdepera", "Capdesaso", "Capela, A", "Capella", "Capellades", "Capileira", "Capilla", "Capillas", "Capmany", "Capolat", "Carabantes", "Carabaña", "Caracena", "Caracuel de Calatrava", "Carataunas", "Caravaca de la Cruz", "Caravia", "Carazo", "Carbajales de Alba", "Carbajo", "Carbajosa de la Sagrada", "Carballeda de Avia", "Carballeda de Valdeorras", "Carballedo", "Carballiño, O", "Carballo", "Carbellino", "Carboneras", "Carboneras de Guadazaón", "Carbonero el Mayor", "Carboneros", "Carcaboso", "Carcabuey", "Carcaixent", "Cárcar", "Carcastillo", "Carcedo de Bureba", "Carcedo de Burgos", "Carcelén", "Càrcer", "Cárcheles", "Cardedeu", "Cárdenas", "Cardenete", "Cardeña", "Cardeñadijo", "Cardeñajimeno", "Cardeñosa", "Cardeñosa de Volpejera", "Cardeñuela Riopico", "Cardiel de los Montes", "Cardona", "Cardoso de la Sierra, El", "Carenas", "Cariñena", "Cariño", "Carlet", "Carlota, La", "Carme", "Carmena", "Cármenes", "Carmona", "Carmonita", "Carnota", "Carolina, La", "Carpio", "Carpio de Azaba", "Carpio de Tajo, El", "Carpio, El", "Carracedelo", "Carral", "Carranque", "Carrascal de Barregas", "Carrascal del Obispo", "Carrascal del Río", "Carrascalejo", "Carrascalejo, El", "Carrascosa", "Carrascosa de Abajo", "Carrascosa de Haro", "Carrascosa de la Sierra", "Carratraca", "Carreño", "Carrera, La", "Carrias", "Carriches", "Carrícola", "Carrión de Calatrava", "Carrión de los Céspedes", "Carrión de los Condes", "Carrizo", "Carrizosa", "Carrocera", "Cartagena", "Cartajima", "Cártama", "Cartaya", "Cartelle", "Cartes", "Carucedo", "Casa de Uceda", "Casabermeja", "Casafranca", "Casalarreina", "Casar de Cáceres", "Casar de Escalona, El", "Casar de Palomero", "Casar, El", "Casarabonela", "Casarejos", "Casares", "Casares de las Hurdes", "Casariche", "Casarrubios del Monte", "Casarrubuelos", "Casas Altas", "Casas Bajas", "Casas de Benítez", "Casas de Don Antonio", "Casas de Don Gómez", "Casas de Don Pedro", "Casas de Fernando Alonso", "Casas de Garcimolina", "Casas de Guijarro", "Casas de Haro", "Casas de Juan Núñez", "Casas de Lázaro", "Casas de los Pinos", "Casas de Millán", "Casas de Miravete", "Casas de Reina", "Casas de San Galindo", "Casas de Ves", "Casas del Castañar", "Casas del Conde, Las", "Casas del Monte", "Casas del Puerto", "Casas-Ibáñez", "Casasbuenas", "Casaseca de Campeán", "Casaseca de las Chanas", "Casasimarro", "Casasola", "Casasola de Arión", "Casatejada", "Casavieja", "Casbas de Huesca", "Cascajares de Bureba", "Cascajares de la Sierra", "Cascante", "Cascante del Río", "Cáseda", "Caseres", "Casillas", "Casillas de Coria", "Casillas de Flores", "Casinos", "Casla", "Caso", "Caspe", "Caspueñas", "Cassà de la Selva", "Casserres", "Castalla", "Castañar de Ibor", "Castañares de Rioja", "Castañeda", "Castaño del Robledo", "Cástaras", "Castejón", "Castejón", "Castejón de Alarba", "Castejón de Henares", "Castejón de las Armas", "Castejón de Monegros", "Castejón de Sos", "Castejón de Tornos", "Castejón de Valdejasa", "Castejón del Puente", "Castel de Cabra", "Castelflorite", "Castell de Cabres", "Castell de Castells", "Castell de Guadalest, el", "Castell de lAreny", "Castell de Mur", "Castell-Platja dAro", "Castell, Es", "Castellanos de Castro", "Castellanos de Moriscos", "Castellanos de Villiquera", "Castellanos de Zapardiel", "Castellar", "Castellar de la Frontera", "Castellar de la Muela", "Castellar de la Ribera", "Castellar de nHug", "Castellar de Santiago", "Castellar del Riu", "Castellar del Vallès", "Castellar, El", "Castellbell i el Vilar", "Castellbisbal", "Castellcir", "Castelldans", "Castelldefels", "Castellet i la Gornal", "Castellfollit de la Roca", "Castellfollit de Riubregós", "Castellfollit del Boix", "Castellfort", "Castellgalí", "Castellnou de Bages", "Castellnou de Seana", "Castellnovo", "Castelló de Farfanya", "Castelló de Rugat", "Castelló dEmpúries", "Castellolí", "Castellón de la Plana/Castelló de la Plana", "Castellonet de la Conquesta", "Castellote", "Castellserà", "Castellterçol", "Castellvell del Camp", "Castellví de la Marca", "Castellví de Rosanes", "Castelnou", "Castelserás", "Castielfabib", "Castiello de Jaca", "Castigaleu", "Castil de Peones", "Castil de Vela", "Castilblanco", "Castilblanco de los Arroyos", "Castildelgado", "Castilfalé", "Castilforte", "Castilfrío de la Sierra", "Castiliscar", "Castillazuelo", "Castilleja de Guzmán", "Castilleja de la Cuesta", "Castilleja del Campo", "Castilléjar", "Castillejo de Iniesta", "Castillejo de Martín Viejo", "Castillejo de Mesleón", "Castillejo de Robledo", "Castillejo-Sierra", "Castillo de Bayuela", "Castillo de Garcimuñoz", "Castillo de las Guardas, El", "Castillo de Locubín", "Castillo de Villamalefa", "Castillo-Albaráñez", "Castillonroy", "Castillonuevo", "Castilnuevo", "Castilruiz", "Castraz", "Castrejón de la Peña", "Castrejón de Trabancos", "Castrelo de Miño", "Castrelo do Val", "Castril", "Castrillo de Cabrera", "Castrillo de Don Juan", "Castrillo de Duero", "Castrillo de la Guareña", "Castrillo de la Reina", "Castrillo de la Valduerna", "Castrillo de la Vega", "Castrillo de Onielo", "Castrillo de Riopisuerga", "Castrillo de Villavega", "Castrillo del Val", "Castrillo Matajudíos", "Castrillo-Tejeriego", "Castrillón", "Castro Caldelas", "Castro de Filabres", "Castro de Fuentidueña", "Castro de Rei", "Castro del Río", "Castro-Urdiales", "Castrobol", "Castrocalbón", "Castrocontrigo", "Castrodeza", "Castrogonzalo", "Castrojeriz", "Castrojimeno", "Castromembibre", "Castromocho", "Castromonte", "Castronuevo", "Castronuevo de Esgueva", "Castronuño", "Castropodame", "Castropol", "Castroponce", "Castroserna de Abajo", "Castroserracín", "Castrotierra de Valmadrigal", "Castroverde", "Castroverde de Campos", "Castroverde de Cerrato", "Castroviejo", "Castuera", "Catadau", "Catarroja", "Catí", "Catllar, El", "Catoira", "Catral", "Caudete", "Caudete de las Fuentes", "Caudiel", "Cava", "Cavia", "Cayuela", "Cazalegas", "Cazalilla", "Cazalla de la Sierra", "Cazorla", "Cazurra", "Cea", "Cebanico", "Cebolla", "Cebrecos", "Cebreros", "Cebrones del Río", "Ceclavín", "Cedeira", "Cedillo", "Cedillo de la Torre", "Cedillo del Condado", "Cedrillas", "Cee", "Cehegín", "Ceinos de Campos", "Celada del Camino", "Celadas", "Celanova", "Cella", "Cellera de Ter, La", "Cellorigo", "Celrà", "Cendea de Olza/Oltza Zendea", "Cendejas de Enmedio", "Cendejas de la Torre", "Cenes de la Vega", "Cenicero", "Cenicientos", "Cenizate", "Cenlle", "Centelles", "Centenera", "Centenera de Andaluz", "Cepeda", "Cepeda la Mora", "Cerbón", "Cerceda", "Cercedilla", "Cercs", "Cerdà", "Cerdanyola del Vallès", "Cerdedo", "Cerdido", "Cereceda de la Sierra", "Cerecinos de Campos", "Cerecinos del Carrizal", "Cerezal de Peñahorcada", "Cerezo", "Cerezo de Abajo", "Cerezo de Arriba", "Cerezo de Río Tirón", "Cernadilla", "Cerollera, La", "Cerralbo", "Cerralbos, Los", "Cerratón de Juarros", "Cerro de Andévalo, El", "Cerro, El", "Cervantes", "Cervatos de la Cueza", "Cervelló", "Cervera", "Cervera de Buitrago", "Cervera de la Cañada", "Cervera de los Montes", "Cervera de Pisuerga", "Cervera del Llano", "Cervera del Maestre", "Cervera del Río Alhama", "Cerveruela", "Cervià de les Garrigues", "Cervià de Ter", "Cervillego de la Cruz", "Cervo", "Cespedosa de Tormes", "Cesuras", "Cetina", "Ceuta", "Ceutí", "Cevico de la Torre", "Cevico Navero", "Chagarcía Medianero", "Chalamera", "Chamartín", "Chandrexa de Queixa", "Chantada", "Chañe", "Chapinería", "Chauchina", "Checa", "Cheles", "Chella", "Chelva", "Chequilla", "Chera", "Chercos", "Chert/Xert", "Cheste", "Chía", "Chiclana de la Frontera", "Chiclana de Segura", "Chilches/Xilxes", "Chillarón de Cuenca", "Chillarón del Rey", "Chillón", "Chilluévar", "Chiloeches", "Chimeneas", "Chimillas", "Chinchilla de Monte-Aragón", "Chinchón", "Chipiona", "Chiprana", "Chirivel", "Chiva", "Chodes", "Chodos/Xodos", "Chóvar", "Chozas de Abajo", "Chozas de Canales", "Chucena", "Chueca", "Chulilla", "Chumillas", "Churriana de la Vega", "Ciadoncha", "Cidamón", "Cidones", "Ciempozuelos", "Cierva, La", "Cieza", "Cieza", "Cifuentes", "Cigales", "Cigudosa", "Ciguñuela", "Cihuela", "Cihuri", "Cijuela", "Cillán", "Cillaperlata", "Cilleros", "Cilleros de la Bastida", "Cilleruelo de Abajo", "Cilleruelo de Arriba", "Cilleruelo de San Mamés", "Cillorigo de Liébana", "Cimanes de la Vega", "Cimanes del Tejar", "Cimballa", "Cinco Olivas", "Cincovillas", "Cinctorres", "Cintruénigo", "Cipérez", "Cirat", "Cirauqui/Zirauki", "Ciria", "Ciriza/Ziritza", "Ciruelas", "Ciruelos", "Ciruelos de Cervera", "Ciruelos del Pinar", "Cirueña", "Cirujales del Río", "Cisla", "Cisneros", "Cistella", "Cistérniga", "Cistierna", "Ciudad Real", "Ciudad Rodrigo", "Ciutadella de Menorca", "Ciutadilla", "Cizur", "Clarés de Ribota", "Clariana de Cardener", "Clavijo", "Coaña", "Cóbdar", "Cobeja", "Cobeña", "Cobeta", "Cobisa", "Cobos de Cerrato", "Cobos de Fuentidueña", "Cobreros", "Coca", "Coca de Alba", "Cocentaina", "Codo", "Codoñera, La", "Codorniz", "Codos", "Codosera, La", "Cofrentes", "Cogeces de Íscar", "Cogeces del Monte", "Cogollor", "Cogollos", "Cogollos de Guadix", "Cogollos de la Vega", "Cogolludo", "Cogul, El", "Coín", "Coirós", "Colera", "Coles", "Colilla, La", "Colindres", "Coll de Nargó", "Collado", "Collado de Contreras", "Collado del Mirón", "Collado Hermoso", "Collado Mediano", "Collado Villalba", "Collazos de Boedo", "Collbató", "Colldejou", "Collsuspina", "Colmenar", "Colmenar de Montemayor", "Colmenar de Oreja", "Colmenar del Arroyo", "Colmenar Viejo", "Colmenarejo", "Colomera", "Colomers", "Colunga", "Colungo", "Coma i la Pedra, La", "Comares", "Comillas", "Cómpeta", "Conca de Dalt", "Condado de Castilnovo", "Condado de Treviño", "Condemios de Abajo", "Condemios de Arriba", "Conesa", "Confrides", "Congosto", "Congosto de Valdavia", "Congostrina", "Conil de la Frontera", "Conquista", "Conquista de la Sierra", "Consell", "Constantí", "Constantina", "Constanzana", "Consuegra", "Contamina", "Contreras", "Coomonte", "Copernal", "Copons", "Corbalán", "Corbera", "Corbera de Llobregat", "Corbera dEbre", "Corbillos de los Oteros", "Corbins", "Corçà", "Corcos", "Corcubión", "Córdoba", "Cordobilla de Lácara", "Cordovilla", "Cordovilla la Real", "Cordovín", "Corduente", "Corella", "Corera", "Coreses", "Corgo, O", "Coria", "Coria del Río", "Coripe", "Coristanco", "Cornago", "Cornellà de Llobregat", "Cornellà del Terri", "Cornudella de Montsant", "Coronada, La", "Coronil, El", "Corpa", "Corporales", "Corral de Almaguer", "Corral de Ayllón", "Corral de Calatrava", "Corral-Rubio", "Corrales de Buelna, Los", "Corrales de Duero", "Corrales del Vino", "Corrales, Los", "Corte de Peleas", "Corteconcepción", "Cortegada", "Cortegana", "Cortelazor", "Cortes", "Cortes de Aragón", "Cortes de Arenoso", "Cortes de Baza", "Cortes de la Frontera", "Cortes de Pallás", "Cortes y Graena", "Cortijos, Los", "Corullón", "Coruña del Conde", "Coruña, A", "Corvera de Asturias", "Corvera de Toranzo", "Cosa", "Coscurita", "Coslada", "Cospeito", "Costitx", "Costur", "Cosuenda", "Cotanes del Monte", "Cotes", "Cotillas", "Cotobade", "Covaleda", "Covarrubias", "Covelo", "Coves de Vinromà, les", "Cox", "Cózar", "Cozuelos de Fuentidueña", "Crecente", "Creixell", "Crémenes", "Crespià", "Crespos", "Cretas", "Crevillent", "Cristina", "Cristóbal", "Crivillén", "Cruïlles, Monells i Sant Sadurní de lHeura", "Cuacos de Yuste", "Cuadros", "Cualedro", "Cuarte de Huerva", "Cuba, La", "Cubas de la Sagra", "Cubel", "Cubelles", "Cubells", "Cubilla", "Cubillas de Cerrato", "Cubillas de los Oteros", "Cubillas de Rueda", "Cubillas de Santa Marta", "Cubillo", "Cubillo de Uceda, El", "Cubillo del Campo", "Cubillos", "Cubillos del Sil", "Cubla", "Cubo de Benavente", "Cubo de Bureba", "Cubo de Don Sancho, El", "Cubo de la Solana", "Cubo de Tierra del Vino, El", "Cucalón", "Cudillero", "Cuelgamures", "Cuéllar", "Cuenca", "Cuenca de Campos", "Cuerlas, Las", "Cuerva", "Cuervo de Sevilla, El", "Cuervo, El", "Cueva de Ágreda", "Cueva de Roa, La", "Cueva del Hierro", "Cuevas Bajas", "Cuevas de Almudén", "Cuevas de Provanco", "Cuevas de San Clemente", "Cuevas de San Marcos", "Cuevas del Almanzora", "Cuevas del Becerro", "Cuevas del Campo", "Cuevas del Valle", "Cuevas Labradas", "Culla", "Cúllar", "Cúllar Vega", "Cullera", "Culleredo", "Cumbre, La", "Cumbres de Enmedio", "Cumbres de San Bartolomé", "Cumbres Mayores", "Cunit", "Cuntis", "Curiel de Duero", "Curtis", "Cútar", "Cuzcurrita de Río Tirón", "Daganzo de Arriba", "Daimiel", "Daimús", "Dalías", "Darnius", "Daroca", "Daroca de Rioja", "Darro", "Das", "Daya Nueva", "Daya Vieja", "Deba", "Degaña", "Dehesa de Montejo", "Dehesa de Romanos", "Dehesas de Guadix", "Deifontes", "Deleitosa", "Deltebre", "Dénia", "Derio", "Descargamaría", "Desojo", "Destriana", "Dévanos", "Deyá", "Deza", "Dicastillo", "Diego del Carpio", "Diezma", "Dílar", "Dima", "Dios le Guarde", "Dodro", "Dólar", "Dolores", "Domeño", "Domingo García", "Domingo Pérez", "Don Álvaro", "Don Benito", "Donamaria", "Doneztebe/Santesteban", "Donhierro", "Donjimeno", "Donostia-San Sebastián", "Donvidas", "Doña Mencía", "Doñinos de Ledesma", "Doñinos de Salamanca", "Dos Aguas", "Dos Hermanas", "Dos Torres", "Dosbarrios", "Dosrius", "Dozón", "Driebes", "Dúdar", "Dueñas", "Duesaigües", "Dumbría", "Durango", "Dúrcal", "Durón", "Duruelo", "Duruelo de la Sierra", "Ea", "Echarri", "Écija", "Egüés", "Eibar", "Eivissa", "Ejea de los Caballeros", "Ejeme", "Ejido, El", "Ejulve", "Elantxobe", "Elburgo/Burgelu", "Elche de la Sierra", "Elche/Elx", "Elciego", "Elda", "Elduain", "Elgeta", "Elgoibar", "Elgorriaga", "Eliana, l", "Eljas", "Elorrio", "Elvillar/Bilar", "Embid", "Embid de Ariza", "Emperador", "Encina de San Silvestre", "Encina, La", "Encinacorba", "Encinas", "Encinas de Abajo", "Encinas de Arriba", "Encinas de Esgueva", "Encinas Reales", "Encinasola", "Encinasola de los Comendadores", "Encinedo", "Encinillas", "Encío", "Enciso", "Endrinal", "Enériz/Eneritz", "Enguera", "Enguídanos", "Enix", "Ènova, l", "Entrala", "Entrambasaguas", "Entrena", "Entrimo", "Entrín Bajo", "Épila", "Erandio", "Eratsun", "Ercina, La", "Ereño", "Ergoiena", "Erla", "Ermua", "Errenteria", "Errezil", "Erriberagoitia/Ribera Alta", "Errigoiti", "Erro", "Erustes", "Escacena del Campo", "Escala, L", "Escalante", "Escalona", "Escalona del Prado", "Escalonilla", "Escamilla", "Escañuela", "Escarabajosa de Cabezas", "Escariche", "Escatrón", "Escobar de Campos", "Escobar de Polendos", "Escobosa de Almazán", "Escopete", "Escorca", "Escorial, El", "Escorihuela", "Escucha", "Escurial", "Escurial de la Sierra", "Escúzar", "Esgos", "Esguevillas de Esgueva", "Eskoriatza", "Eslava", "Eslida", "Espadaña", "Espadañedo", "Espadilla", "Esparragalejo", "Esparragosa de la Serena", "Esparragosa de Lares", "Esparreguera", "Espartinas", "Esparza de Salazar/Espartza Zaraitzu", "Espeja", "Espeja de San Marcelino", "Espejo", "Espejón", "Espelúy", "Espera", "Espiel", "Espinar, El", "Espinelves", "Espino de la Orbada", "Espinosa de Cerrato", "Espinosa de Cervera", "Espinosa de Henares", "Espinosa de los Caballeros", "Espinosa de los Monteros", "Espinosa de Villagonzalo", "Espinosa del Camino", "Espinoso del Rey", "Espirdo", "Esplegares", "Espluga Calba, L", "Espluga de Francolí, L", "Esplugues de Llobregat", "Esplús", "Espolla", "Esponellà", "Esporles", "Espot", "Espronceda", "Espunyola, L", "Esquivias", "Establés", "Estada", "Estadilla", "Estamariu", "Estany, L", "Estaràs", "Estella-Lizarra", "Estellencs", "Estepa", "Estepa de San Juan", "Estépar", "Estepona", "Estercuel", "Esteribar", "Esterri dÀneu", "Esterri de Cardós", "Estivella", "Estollo", "Estopiñán del Castillo", "Estrada, A", "Estrella, La", "Estremera", "Estriégana", "Estubeny", "Etayo", "Etxalar", "Etxarri-Aranatz", "Etxauri", "Etxebarri", "Etxebarria", "Eulate", "Ezcabarte", "Ezcaray", "Ezcároz/Ezkaroze", "Ezkio-Itsaso", "Ezkurra", "Ezprogui", "Fabara", "Fabero", "Facheca", "Fago", "Falces", "Falset", "Famorca", "Fanlo", "Fanzara", "Far dEmpordà, El", "Faraján", "Faramontanos de Tábara", "Fariza", "Farlete", "Farrera", "Fasnia", "Fatarella, La", "Faura", "Favara", "Fayón", "Fayos, Los", "Febró, La", "Felanitx", "Felix", "Fene", "Férez", "Feria", "Fermoselle", "Fernán Caballero", "Fernán-Núñez", "Ferreira", "Ferreras de Abajo", "Ferreras de Arriba", "Ferreries", "Ferreruela", "Ferreruela de Huerva", "Ferrol", "Figaró-Montmany", "Fígols", "Fígols i Alinyà", "Figuera, La", "Figueres", "Figuerola del Camp", "Figueroles", "Figueruela de Arriba", "Figueruelas", "Fines", "Finestrat", "Fiñana", "Firgas", "Fiscal", "Fisterra", "Fitero", "Flaçà", "Flix", "Flores de Ávila", "Floresta, La", "Florida de Liébana", "Fogars de la Selva", "Fogars de Montclús", "Foios", "Foixà", "Folgoso de la Ribera", "Folgoso do Courel", "Folgueroles", "Fombellida", "Fombuena", "Fompedraza", "Foncea", "Fondarella", "Fondó de les Neus, el/Hondón de las Nieves", "Fondón", "Fonelas", "Fonfría", "Fonfría", "Fonollosa", "Fonsagrada, A", "Font de la Figuera, la", "Font dEn Carròs, la", "Font-rubí", "Fontanals de Cerdanya", "Fontanar", "Fontanarejo", "Fontanars dels Alforins", "Fontanilles", "Fontcoberta", "Fontellas", "Fontihoyuelo", "Fontioso", "Fontiveros", "Fonz", "Fonzaleche", "Foradada", "Foradada del Toscar", "Forallac", "Forcall", "Forcarei", "Forès", "Forfoleda", "Formentera", "Formentera del Segura", "Formiche Alto", "Fornalutx", "Fornells de la Selva", "Fornelos de Montes", "Fórnoles", "Fortaleny", "Fortanete", "Fortià", "Fortuna", "Forua", "Foz", "Foz-Calanda", "Frades", "Frades de la Sierra", "Fraga", "Frago, El", "Frailes", "Franco, El", "Frandovínez", "Franqueses del Vallès, Les", "Frasno, El", "Frechilla", "Frechilla de Almazán", "Fregenal de la Sierra", "Fregeneda, La", "Freginals", "Freila", "Fréscano", "Fresneda de Altarejos", "Fresneda de Cuéllar", "Fresneda de la Sierra", "Fresneda de la Sierra Tirón", "Fresneda, La", "Fresnedilla", "Fresnedillas de la Oliva", "Fresnedoso", "Fresnedoso de Ibor", "Fresneña", "Fresnillo de las Dueñas", "Fresno Alhándiga", "Fresno de Cantespino", "Fresno de Caracena", "Fresno de la Fuente", "Fresno de la Polvorosa", "Fresno de la Ribera", "Fresno de la Vega", "Fresno de Río Tirón", "Fresno de Rodilla", "Fresno de Sayago", "Fresno de Torote", "Fresno del Río", "Fresno el Viejo", "Fresno, El", "Frías", "Frías de Albarracín", "Friera de Valverde", "Frigiliana", "Friol", "Frómista", "Frontera", "Frontera, La", "Fruiz", "Frumales", "Fuembellida", "Fuencaliente", "Fuencaliente de la Palma", "Fuencemillán", "Fuendejalón", "Fuendetodos", "Fuenferrada", "Fuengirola", "Fuenlabrada", "Fuenlabrada de los Montes", "Fuenllana", "Fuenmayor", "Fuensaldaña", "Fuensalida", "Fuensanta", "Fuensanta de Martos", "Fuente Álamo de Murcia", "Fuente de Cantos", "Fuente de Pedro Naharro", "Fuente de Piedra", "Fuente de San Esteban, La", "Fuente de Santa Cruz", "Fuente del Arco", "Fuente del Maestre", "Fuente el Fresno", "Fuente el Olmo de Fuentidueña", "Fuente el Olmo de Íscar", "Fuente el Saúz", "Fuente el Saz de Jarama", "Fuente el Sol", "Fuente Encalada", "Fuente la Lancha", "Fuente la Reina", "Fuente Obejuna", "Fuente Palmera", "Fuente Vaqueros", "Fuente-Álamo", "Fuente-Olmedo", "Fuente-Tójar", "Fuentealbilla", "Fuentearmegil", "Fuentebureba", "Fuentecambrón", "Fuentecantos", "Fuentecén", "Fuenteguinaldo", "Fuenteheridos", "Fuentelahiguera de Albatages", "Fuentelapeña", "Fuentelcésped", "Fuentelencina", "Fuentelespino de Haro", "Fuentelespino de Moya", "Fuenteliante", "Fuentelisendo", "Fuentelmonge", "Fuentelsaz", "Fuentelsaz de Soria", "Fuentelviejo", "Fuentemolinos", "Fuentenava de Jábaga", "Fuentenebro", "Fuentenovilla", "Fuentepelayo", "Fuentepinilla", "Fuentepiñel", "Fuenterrebollo", "Fuenterroble de Salvatierra", "Fuenterrobles", "Fuentes", "Fuentes Calientes", "Fuentes Claras", "Fuentes de Andalucía", "Fuentes de Año", "Fuentes de Ayódar", "Fuentes de Béjar", "Fuentes de Carbajal", "Fuentes de Ebro", "Fuentes de Jiloca", "Fuentes de León", "Fuentes de Magaña", "Fuentes de Nava", "Fuentes de Oñoro", "Fuentes de Ropel", "Fuentes de Rubielos", "Fuentes de Valdepero", "Fuentesaúco", "Fuentesaúco de Fuentidueña", "Fuentesecas", "Fuentesoto", "Fuentespalda", "Fuentespina", "Fuentespreadas", "Fuentestrún", "Fuentidueña", "Fuentidueña de Tajo", "Fuerte del Rey", "Fuertescusa", "Fueva, La", "Fuliola, La", "Fulleda", "Funes", "Fustiñana"}));
        }
        if (jComboBox_filtroAZ.getSelectedItem().toString().equals("De la G a la S")) {
            jComboBox_poblacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Gabaldón", "Gabias, Las", "Gabiria", "Gádor", "Gaià", "Gaianes", "Gaibiel", "Gaintza", "Gajanejos", "Gajates", "Galapagar", "Galápagos", "Galar", "Galaroza", "Galbarros", "Galbárruli", "Galdakao", "Galdames", "Gáldar", "Galende", "Galera", "Galera, La", "Galilea", "Galindo y Perahuy", "Galinduste", "Galisancho", "Galisteo", "Gallardos, Los", "Gallega, La", "Gallegos", "Gallegos de Altamiros", "Gallegos de Argañán", "Gallegos de Hornija", "Gallegos de Sobrinos", "Gallegos de Solmirón", "Gallegos del Pan", "Gallegos del Río", "Gallifa", "Gallinero de Cameros", "Gallipienzo/Galipentzu", "Gallocanta", "Gallués/Galoze", "Gallur", "Galve", "Galve de Sorbe", "Gálvez", "Gamiz-Fika", "Gamones", "Gandesa", "Gandia", "Garaballa", "Garachico", "Garafía", "Garai", "Garaioa", "Garbayuela", "Garcia", "Garciaz", "Garcibuey", "Garcihernández", "Garcillán", "Garciotum", "Garcirrey", "Garde", "Gargallo", "Garganta de los Montes", "Garganta del Villar", "Garganta la Olla", "Garganta, La", "Gargantilla", "Gargantilla del Lozoya y Pinilla de Buitrago", "Gargüera", "Garidells, Els", "Garínoain", "Garlitos", "Garrafe de Torío", "Garralda", "Garray", "Garriga, La", "Garrigàs", "Garrigoles", "Garriguella", "Garrobo, El", "Garrovilla, La", "Garrovillas de Alconétar", "Garrucha", "Garvín", "Gascones", "Gascueña", "Gascueña de Bornova", "Gastor, El", "Gata", "Gata de Gorgos", "Gatika", "Gatón de Campos", "Gátova", "Gaucín", "Gautegiz Arteaga", "Gavà", "Gavarda", "Gavet de la Conca", "Gavilanes", "Gaztelu", "Gea de Albarracín", "Gejuelo del Barro", "Geldo", "Gelida", "Gelsa", "Gelves", "Gema", "Gemuño", "Genalguacil", "Génave", "Genevilla", "Genovés", "Ger", "Gerena", "Gérgal", "Geria", "Gerindote", "Gernika-Lumo", "Gestalgar", "Getafe", "Getaria", "Getxo", "Gibraleón", "Gijón", "Gil García", "Gilbuena", "Gilena", "Gilet", "Gimenells i el Pla de la Font", "Gimialcón", "Gimileo", "Ginebrosa, La", "Gines", "Ginestar", "Gineta, La", "Girona", "Gironella", "Gisclareny", "Gistaín", "Gizaburuaga", "Gobernador", "Godall", "Godella", "Godelleta", "Godojos", "Goizueta", "Gójar", "Golmayo", "Golmés", "Golosalvo", "Golpejas", "Gómara", "Gombrèn", "Gomecello", "Gomesende", "Gomezserracín", "Gondomar", "Goñi", "Gor", "Gorafe", "Gordaliza del Pino", "Gordexola", "Gordo, El", "Gordoncillo", "Gorga", "Gorliz", "Gormaz", "Gósol", "Gotarrendura", "Gotor", "Gozón", "Gradefes", "Grado", "Grado, El", "Graja de Campalbo", "Graja de Iniesta", "Grajal de Campos", "Grajera", "Granada", "Granada de Río-Tinto, La", "Granada, La", "Granadella, La", "Granadilla de Abona", "Granado, El", "Granátula de Calatrava", "Grandas de Salime", "Grandes y San Martín", "Granera", "Granja de la Costera, la", "Granja de Moreruela", "Granja de Rocamora", "Granja de Torrehermosa", "Granja dEscarp, La", "Granja, La", "Granjuela, La", "Granollers", "Granucillo", "Granyanella", "Granyena de les Garrigues", "Granyena de Segarra", "Grañén", "Grañón", "Gratallops", "Graus", "Grávalos", "Grazalema", "Griegos", "Grijalba", "Grijota", "Griñón", "Grisaleña", "Grisel", "Grisén", "Grove, O", "Guadahortuna", "Guadalajara", "Guadalaviar", "Guadalcanal", "Guadalcázar", "Guadalix de la Sierra", "Guadalmez", "Guadalupe", "Guadamur", "Guadarrama", "Guadasséquies", "Guadassuar", "Guadix", "Guadramiro", "Guajares, Los", "Gualba", "Gualchos", "Gualta", "Guancha, La", "Guarda, A", "Guardamar de la Safor", "Guardamar del Segura", "Guardia de Jaén, La", "Guardia, La", "Guardiola de Berguedà", "Guardo", "Guareña", "Guaro", "Guarrate", "Guarromán", "Guaza de Campos", "Gúdar", "Gudiña, A", "Güejar Sierra", "Güeñes", "Güesa/Gorza", "Guesálaz/Gesalatz", "Güevéjar", "Guía de Isora", "Guiamets, Els", "Guijo de Ávila", "Guijo de Coria", "Guijo de Galisteo", "Guijo de Granadilla", "Guijo de Santa Bárbara", "Guijo, El", "Guijuelo", "Guillena", "Guils de Cerdanya", "Güímar", "Guimerà", "Guingueta dÀneu, La", "Guirguillano", "Guisando", "Guissona", "Guitiriz", "Guixers", "Gumiel de Izán", "Gumiel de Mercado", "Guntín", "Gurb", "Guriezo", "Gurrea de Gállego", "Gusendos de los Oteros", "Gutierre-Muñoz", "Haba, La", "Hacinas", "Harana/Valle de Arana", "Haría", "Haro", "Haza", "Hazas de Cesto", "Helechosa de los Montes", "Hellín", "Henarejos", "Henche", "Heras de Ayuso", "Herbés", "Herce", "Herencia", "Herencias, Las", "Herguijuela", "Herguijuela de Ciudad Rodrigo", "Herguijuela de la Sierra", "Herguijuela del Campo", "Hermandad de Campoo de Suso", "Hérmedes de Cerrato", "Hermigua", "Hermisende", "Hernán-Pérez", "Hernani", "Hernansancho", "Hernialde", "Herradón de Pinares", "Herramélluri", "Herrera", "Herrera de Alcántara", "Herrera de los Navarros", "Herrera de Pisuerga", "Herrera de Soria", "Herrera de Valdecañas", "Herrera del Duque", "Herrera, La", "Herrería", "Herrerías", "Herreros de Suso", "Herreruela", "Herreruela de Oropesa", "Herrín de Campos", "Herrumblar, El", "Hervás", "Hervías", "Hiendelaencina", "Higuera", "Higuera de Calatrava", "Higuera de la Serena", "Higuera de la Sierra", "Higuera de las Dueñas", "Higuera de Llerena", "Higuera de Vargas", "Higuera la Real", "Higueras", "Higueruela", "Higueruelas", "Hija de Dios, La", "Híjar", "Hijes", "Hiniesta, La", "Hinojal", "Hinojales", "Hinojares", "Hinojos", "Hinojosa de Duero", "Hinojosa de Jarque", "Hinojosa de San Vicente", "Hinojosa del Campo", "Hinojosa del Duque", "Hinojosa del Valle", "Hinojosa, La", "Hinojosas de Calatrava", "Hinojosos, Los", "Hiriberri/Villanueva de Aezkoa", "Hiruela, La", "Hita", "Hito, El", "Holguera", "Hombrados", "Hondarribia", "Hondón de los Frailes", "Honrubia", "Honrubia de la Cuesta", "Hontalbilla", "Hontanar", "Hontanares de Eresma", "Hontanas", "Hontanaya", "Hontangas", "Hontecillas", "Hontoba", "Hontoria de Cerrato", "Hontoria de la Cantera", "Hontoria de Valdearados", "Hontoria del Pinar", "Horcajada, La", "Horcajo de la Sierra-Aoslos", "Horcajo de las Torres", "Horcajo de los Montes", "Horcajo de Montemayor", "Horcajo de Santiago", "Horcajo Medianero", "Horcajuelo de la Sierra", "Horche", "Hormazas, Las", "Hormigos", "Hormilla", "Hormilleja", "Hornachos", "Hornachuelos", "Hornillo, El", "Hornillos de Cameros", "Hornillos de Cerrato", "Hornillos de Eresma", "Hornillos del Camino", "Hornos", "Hornos de Moncalvillo", "Horra, La", "Horta de Sant Joan", "Hortezuela de Océn", "Hortigüela", "Hospital de Órbigo", "Hospitalet de Llobregat, L", "Hostalets de Pierola, Els", "Hostalric", "Hoya-Gonzalo", "Hoya, La", "Hoyales de Roa", "Hoyo de Manzanares", "Hoyo de Pinares, El", "Hoyocasero", "Hoyorredondo", "Hoyos", "Hoyos de Miguel Muñoz", "Hoyos del Collado", "Hoyos del Espino", "Hoz de Jaca", "Hoz de la Vieja, La", "Hoz y Costean", "Huarte/Uharte", "Huecas", "Huécija", "Huélaga", "Huélago", "Huélamo", "Huelma", "Huelva", "Huelves", "Huéneja", "Huércal de Almería", "Huércal-Overa", "Huércanos", "Huerce, La", "Huérguina", "Huérmeces", "Huérmeces del Cerro", "Huerta", "Huerta de Arriba", "Huerta de la Obispalía", "Huerta de Rey", "Huerta de Valdecarábanos", "Huerta del Marquesado", "Huertahernando", "Huerto", "Huertos, Los", "Huesa", "Huesa del Común", "Huesca", "Huéscar", "Huete", "Huétor de Santillán", "Huétor Tájar", "Huétor Vega", "Hueva", "Huévar del Aljarafe", "Humada", "Humanes", "Humanes de Madrid", "Humilladero", "Hurones", "Hurtumpascual", "Husillos", "Ibahernando", "Ibargoiti", "Ibarra", "Ibarrangelu", "Ibdes", "Ibeas de Juarros", "Ibi", "Ibias", "Ibieca", "Ibrillos", "Ibros", "Icod de los Vinos", "Idiazabal", "Igantzi", "Igea", "Iglesiarrubia", "Iglesias", "Iglesuela del Cid, La", "Iglesuela, La", "Igorre", "Igriés", "Igualada", "Igualeja", "Igüeña", "Igúzquiza", "Ikaztegieta", "Ilche", "Illa de Arousa, A", "Illán de Vacas", "Illana", "Illano", "Illar", "Illas", "Illescas", "Illora", "Illueca", "Imotz", "Inca", "Incio, O", "Ingenio", "Iniesta", "Iniéstola", "Instinción", "Inviernas, Las", "Irañeta", "Irixo, O", "Irixoa", "Iruela, La", "Iruelos", "Irueste", "Irun", "Iruña Oka/Iruña de Oca", "Irura", "Iruraiz-Gauna", "Irurtzun", "Isaba/Izaba", "Isábena", "Isar", "Íscar", "Isla Cristina", "Isla Mayor", "Isona i Conca Dellà", "Isòvol", "Ispaster", "Istán", "Isuerre", "Itero de la Vega", "Itero del Castillo", "Itrabo", "Itsasondo", "Ituero de Azaba", "Ituero y Lama", "Ituren", "Iturmendi", "Iurreta", "Ivars de Noguera", "Ivars dUrgell", "Ivorra", "Iza/Itza", "Izagaondoa", "Izagre", "Izalzu/Itzaltzu", "Iznájar", "Iznalloz", "Iznate", "Iznatoraf", "Izurtza", "Jabaloyas", "Jabalquinto", "Jabugo", "Jaca", "Jacarilla", "Jadraque", "Jaén", "Jafre", "Jalance", "Jalón de Cameros", "Jambrina", "Jamilena", "Jana, la", "Jaraba", "Jarafuel", "Jaraicejo", "Jaraíz de la Vera", "Jaramillo de la Fuente", "Jaramillo Quemado", "Jarandilla de la Vera", "Jarilla", "Jarque", "Jarque de la Val", "Jasa", "Jatiel", "Jaulín", "Jaurrieta", "Jávea/Xàbia", "Javier", "Jayena", "Jerez de la Frontera", "Jerez de los Caballeros", "Jerez del Marquesado", "Jérica", "Jerte", "Jete", "Jijona/Xixona", "Jimena", "Jimena de la Frontera", "Jimera de Líbar", "Jirueque", "Joarilla de las Matas", "Jódar", "Jonquera, La", "Jorba", "Jorcas", "Jorquera", "Josa", "Josa i Tuixén", "Joyosa, La", "Juarros de Riomoros", "Juarros de Voltoya", "Jubrique", "Juià", "Jumilla", "Jun", "Junciana", "Juncosa", "Juneda", "Junta de Traslaloma", "Junta de Villalba de Losa", "Jurisdicción de Lara", "Jurisdicción de San Zadornil", "Juslapeña", "Justel", "Juviles", "Juzbado", "Júzcar", "Karrantza Harana/Valle de Carranza", "Kortezubi", "Kripan", "Kuartango", "Labajos", "Labastida/Bastida", "Labores, Las", "Labuerda", "Láchar", "Ladrillar", "Lagartera", "Lagartos", "Lagata", "Lagrán", "Laguardia", "Lagueruela", "Laguna Dalga", "Laguna de Cameros", "Laguna de Contreras", "Laguna de Duero", "Laguna de Negrillos", "Laguna del Marquesado", "Lagunaseca", "Lagunilla", "Lagunilla del Jubera", "Lahiguera", "Lakuntza", "Lalín", "Laluenga", "Lalueza", "Lama, A", "Lamasón", "Lana", "Lanaja", "Láncara", "Lanciego/Lantziego", "Landete", "Lanestosa", "Langa", "Langa de Duero", "Langa del Castillo", "Langayo", "Langreo", "Languilla", "Lanjarón", "Lantadilla", "Lantarón", "Lanteira", "Lantejuela, La", "Lantz", "Lanzahíta", "Lanzuela", "Lapa, La", "Laperdiguera", "Lapoblación", "Lapuebla de Labarca", "Laracha, A", "Lardero", "Laredo", "Larouco", "Laroya", "Larrabetzu", "Larraga", "Larraona", "Larraul", "Larraun", "Larrodrigo", "Larva", "Lasarte-Oria", "Lascellas-Ponzano", "Lascuarre", "Laspaúles", "Laspuña", "Lastras de Cuéllar", "Lastras del Pozo", "Lastrilla, La", "Laudio/Llodio", "Láujar de Andarax", "Laukiz", "Laviana", "Laxe", "Layana", "Layos", "Laza", "Lazagurría", "Lazkao", "Leaburu", "Leache", "Lebrija", "Lécera", "Lechón", "Leciñena", "Lecrín", "Ledanca", "Ledaña", "Ledesma", "Ledesma de la Cogolla", "Ledigos", "Ledrada", "Leganés", "Leganiel", "Legarda", "Legaria", "Legazpi", "Legorreta", "Legutio", "Leintz-Gatzaga", "Leioa", "Leiro", "Leitza", "Leiva", "Lekeitio", "Lekunberri", "Lemoa", "Lemoiz", "Lena", "Lentegí", "León", "Leoz/Leotz", "Lepe", "Lerga", "Lerín", "Lerma", "Les", "Lesaka", "Letur", "Letux", "Leza", "Leza de Río Leza", "Lezama", "Lezáun", "Lezo", "Lezuza", "Librilla", "Libros", "Liceras", "Lidón", "Liédena", "Liendo", "Liérganes", "Liétor", "Líjar", "Lillo", "Limpias", "Linares", "Linares de la Sierra", "Linares de Mora", "Linares de Riofrío", "Línea de la Concepción, La", "Linyola", "Litago", "Lituénigo", "Lizartza", "Lizoáin-Arriasgoiti", "Llacuna, La", "Lladó", "Lladorre", "Lladurs", "Llagosta, La", "Llagostera", "Llamas de la Ribera", "Llambilles", "Llanars", "Llançà", "Llanera", "Llanera de Ranes", "Llanes", "Llano de Bureba", "Llano de Olmedo", "Llanos de Aridane, Los", "Llanos de Tormes, Los", "Llanos del Caudillo", "Llardecans", "Llaurí", "Llavorsí", "Lledó", "Lleida", "Llera", "Llerena", "Llers", "Lles de Cerdanya", "Llíber", "Lliçà dAmunt", "Lliçà de Vall", "Llimiana", "Llinars del Vallès", "Llíria", "Llívia", "Lloar, El", "Llobera", "Llocnou de la Corona", "Llocnou de Sant Jeroni", "Llocnou dEn Fenollet", "Llombai", "Llorac", "Llorenç del Penedès", "Lloret de Mar", "Lloret de Vistalegre", "Llosa de Ranes, la", "Llosa, la", "Lloseta", "Llosses, Les", "Llubí", "Lluçà", "Llucmajor", "Llutxent", "Loarre", "Lobeira", "Lobera de Onsella", "Lobios", "Lobón", "Lobras", "Lodosa", "Loeches", "Logroño", "Logrosán", "Loiu", "Loja", "Loma de Ucieza", "Lomas", "Lominchar", "Lomoviejo", "Longares", "Longás", "Lónguida/Longida", "Lopera", "Loporzano", "Lora de Estepa", "Lora del Río", "Loranca de Tajuña", "Lorca", "Lorcha/Orxa, l", "Loriguilla", "Lorquí", "Losa del Obispo", "Losa, La", "Losacino", "Losacio", "Losar de la Vera", "Losar del Barco, El", "Loscorrales", "Loscos", "Losilla, La", "Lourenzá", "Lousame", "Lozoya", "Lozoyuela-Navas-Sieteiglesias", "Lubián", "Lubrín", "Lucainena de las Torres", "Lúcar", "Lucena", "Lucena de Jalón", "Lucena del Cid", "Lucena del Puerto", "Luceni", "Luciana", "Lucillo", "Lucillos", "Ludiente", "Luelmo", "Luena", "Luesia", "Luesma", "Lugo", "Lugros", "Luisiana, La", "Lújar", "Lumbier", "Lumbrales", "Lumbreras", "Lumpiaque", "Luna", "Lupiana", "Lupiñén-Ortilla", "Lupión", "Luque", "Luquin", "Luyego", "Luzaga", "Luzaide/Valcarlos", "Luzón", "Macael", "Maçanet de Cabrenys", "Maçanet de la Selva", "Macastre", "Maceda", "Machacón", "Macharaviaya", "Macotera", "Madarcos", "Maderal, El", "Maderuelo", "Madremanya", "Madrid", "Madridanos", "Madridejos", "Madrigal de la Vera", "Madrigal de las Altas Torres", "Madrigal del Monte", "Madrigalejo", "Madrigalejo del Monte", "Madrigueras", "Madroñal", "Madroñera", "Madroño, El", "Maella", "Maello", "Magacela", "Magallón", "Magán", "Magaña", "Magaz de Cepeda", "Magaz de Pisuerga", "Maguilla", "Mahamud", "Mahide", "Mahora", "Maià de Montcal", "Maials", "Maicas", "Maíllo, El", "Mainar", "Maire de Castroponce", "Mairena del Alcor", "Mairena del Aljarafe", "Majadahonda", "Majadas", "Majadas, Las", "Majaelrayo", "Maján", "Málaga", "Málaga del Fresno", "Malagón", "Malaguilla", "Malahá, La", "Malanquilla", "Malcocinado", "Maldà", "Maleján", "Malgrat de Mar", "Malla", "Mallabia", "Mallén", "Malón", "Malpartida", "Malpartida de Cáceres", "Malpartida de Corneja", "Malpartida de la Serena", "Malpartida de Plasencia", "Malpica de Bergantiños", "Malpica de Tajo", "Maluenda", "Malva", "Mamblas", "Mambrilla de Castrejón", "Mambrillas de Lara", "Mamolar", "Manacor", "Mancera de Abajo", "Mancera de Arriba", "Mancha Real", "Manchita", "Manchones", "Manciles", "Mancor de la Vall", "Mandayona", "Manganeses de la Lampreana", "Manganeses de la Polvorosa", "Manilva", "Manises", "Manjabálago", "Manjarrés", "Manlleu", "Manquillos", "Manresa", "Mansilla de la Sierra", "Mansilla de las Mulas", "Mansilla Mayor", "Mantiel", "Mantinos", "Manuel", "Manzanal de Arriba", "Manzanal de los Infantes", "Manzanal del Barco", "Manzanares", "Manzanares de Rioja", "Manzanares el Real", "Manzaneda", "Manzaneque", "Manzanera", "Manzanilla", "Manzanillo", "Manzano, El", "Mañaria", "Mañeru", "Mañón", "Maó", "Maqueda", "Mara", "Maracena", "Maranchón", "Maraña", "Marañón", "Marazoleja", "Marazuela", "Marbella", "Marçà", "Marchagaz", "Marchal", "Marchamalo", "Marchena", "Marcilla", "Marcilla de Campos", "Margalef", "Marganell", "María", "María de Huerva", "Maria de la Salut", "Mariana", "Marín", "Marina de Cudeyo", "Marinaleda", "Marines", "Marines, Los", "Marjaliza", "Markina-Xemein", "Marlín", "Marmolejo", "Marracos", "Marratxí", "Marrupe", "Martiago", "Martiherrero", "Martín de la Jara", "Martín de Yeltes", "Martín del Río", "Martín Miguel", "Martín Muñoz de la Dehesa", "Martín Muñoz de las Posadas", "Martinamor", "Martínez", "Martorell", "Martorelles", "Martos", "Marugán", "Maruri-Jatabe", "Marzales", "Mas de Barberans", "Mas de las Matas", "Masarac", "Mascaraque", "Masdenverge", "Masegosa", "Masegoso", "Masegoso de Tajuña", "Maside", "Masies de Roda, Les", "Masies de Voltregà, Les", "Masllorenç", "Masnou, El", "Masó, La", "Maspujols", "Masquefa", "Masroig, El", "Massalavés", "Massalcoreig", "Massalfassar", "Massamagrell", "Massanassa", "Massanes", "Massoteres", "Masueco", "Mata de Alcántara", "Mata de Cuéllar", "Mata de Ledesma, La", "Mata de los Olmos, La", "Mata de Morella, la", "Mata, La", "Matabuena", "Matadeón de los Oteros", "Matadepera", "Matalebreras", "Matallana de Torío", "Matamala de Almazán", "Matanza", "Matanza de Acentejo, La", "Matapozuelos", "Mataró", "Matarrubia", "Matet", "Matilla de Arzón", "Matilla de los Caños", "Matilla de los Caños del Río", "Matilla la Seca", "Matilla, La", "Matillas", "Matute", "Maya, La", "Mayalde", "Mayorga", "Mazaleón", "Mazarambroz", "Mazarete", "Mazaricos", "Mazariegos", "Mazarrón", "Mazcuerras", "Mazuecos", "Mazuecos de Valdeginate", "Mazuela", "Meaño", "Mecerreyes", "Meco", "Medellín", "Mediana de Aragón", "Mediana de Voltoya", "Medina de las Torres", "Medina de Pomar", "Medina de Rioseco", "Medina del Campo", "Medina-Sidonia", "Medinaceli", "Medinilla", "Medio Cudeyo", "Mediona", "Medranda", "Medrano", "Megeces", "Megina", "Meira", "Meis", "Mejorada", "Mejorada del Campo", "Melgar de Abajo", "Melgar de Arriba", "Melgar de Fernamental", "Melgar de Tera", "Melgar de Yuso", "Meliana", "Mélida", "Melide", "Melilla", "Melón", "Melque de Cercos", "Membibre de la Hoz", "Membribe de la Sierra", "Membrilla", "Membrillera", "Membrío", "Menàrguens", "Menasalbas", "Mendaro", "Mendata", "Mendavia", "Mendaza", "Mendexa", "Mendigorría", "Meneses de Campos", "Mengabril", "Mengamuñoz", "Mengíbar", "Méntrida", "Meñaka", "Mequinenza", "Meranges", "Merca, A", "Mercadal, Es", "Mérida", "Merindad de Cuesta-Urria", "Merindad de Montija", "Merindad de Río Ubierna", "Merindad de Sotoscueva", "Merindad de Valdeporres", "Merindad de Valdivielso", "Meruelo", "Mesas de Ibor", "Mesas, Las", "Mesegar de Corneja", "Mesegar de Tajo", "Mesía", "Mesones de Isuela", "Mestanza", "Metauten", "Mezalocha", "Mezquita de Jarque", "Mezquita, A", "Miajadas", "Mianos", "Micereces de Tera", "Micieces de Ojeda", "Miedes de Aragón", "Miedes de Atienza", "Miengo", "Miera", "Mieres", "Mieres", "Mierla, La", "Mieza", "Migjorn Gran, Es", "Miguel Esteban", "Migueláñez", "Miguelturra", "Mijares", "Mijas", "Milà, El", "Milagro", "Milagros", "Milano, El", "Millana", "Millanes", "Millares", "Millena", "Milles de la Polvorosa", "Milmarcos", "Minas de Riotinto", "Minaya", "Minglanilla", "Mingorría", "Miño", "Miño de Medinaceli", "Miño de San Esteban", "Miñosa, La", "Mira", "Mirabel", "Mirabueno", "Miraflores de la Sierra", "Mirafuentes", "Miralcamp", "Miralrío", "Miramar", "Mirambel", "Miranda de Arga", "Miranda de Azán", "Miranda de Ebro", "Miranda del Castañar", "Mirandilla", "Miraveche", "Miravet", "Miravete de la Sierra", "Mirón, El", "Mironcillo", "Mirueña de los Infanzones", "Mislata", "Moaña", "Mocejón", "Mochales", "Moclín", "Moclinejo", "Modúbar de la Emparedada", "Moeche", "Mogán", "Mogarraz", "Mogente/Moixent", "Moguer", "Mohedas de Granadilla", "Mohedas de la Jara", "Mohernando", "Moià", "Mojácar", "Mojados", "Mojonera, La", "Molacillos", "Molar, El", "Molar, El", "Molares, Los", "Molezuelas de la Carballeda", "Molina de Aragón", "Molina de Segura", "Molinaseca", "Molinicos", "Molinillo", "Molinos", "Molinos de Duero", "Molinos, Los", "Molins de Rei", "Molledo", "Mollerussa", "Mollet de Peralada", "Mollet del Vallès", "Mollina", "Molló", "Molsosa, La", "Molvízar", "Mombeltrán", "Momblona", "Mombuey", "Monachil", "Monasterio", "Monasterio de la Sierra", "Monasterio de Rodilla", "Monasterio de Vega", "Moncada", "Moncalvillo", "Moncofa", "Monda", "Mondariz", "Mondariz-Balneario", "Mondéjar", "Mondoñedo", "Monegrillo", "Monesma y Cajigar", "Monesterio", "Moneva", "Monfarracinos", "Monfero", "Monflorite-Lascasas", "Monforte de la Sierra", "Monforte de Lemos", "Monforte de Moyuela", "Monforte del Cid", "Monistrol de Calders", "Monistrol de Montserrat", "Monleón", "Monleras", "Monóvar/Monòver", "Monreal de Ariza", "Monreal del Campo", "Monreal del Llano", "Monreal/Elo", "Monroy", "Monroyo", "Monsagro", "Monsalupe", "Mont-ral", "Mont-ras", "Mont-roig del Camp", "Montagut i Oix", "Montalbán", "Montalbán de Córdoba", "Montalbanejo", "Montalbo", "Montalvos", "Montamarta", "Montán", "Montánchez", "Montanejos", "Montanuy", "Montarrón", "Montaverner", "Montblanc", "Montbrió del Camp", "Montcada i Reixac", "Montclar", "Monteagudo", "Monteagudo de las Salinas", "Monteagudo de las Vicarías", "Monteagudo del Castillo", "Montealegre de Campos", "Montealegre del Castillo", "Montearagón", "Montederramo", "Montefrío", "Montehermoso", "Montejaque", "Montejícar", "Montejo", "Montejo de Arévalo", "Montejo de la Sierra", "Montejo de la Vega de la Serrezuela", "Montejo de Tiermes", "Montellà i Martinet", "Montellano", "Montemayor", "Montemayor de Pililla", "Montemayor del Río", "Montemolín", "Montenegro de Cameros", "Monterde", "Monterde de Albarracín", "Monterrei", "Monterroso", "Monterrubio", "Monterrubio de Armuña", "Monterrubio de la Demanda", "Monterrubio de la Serena", "Monterrubio de la Sierra", "Montesa", "Montesclaros", "Montesinos, Los", "Montesquiu", "Montferrer i Castellbò", "Montferri", "Montgai", "Montgat", "Montiel", "Montijo", "Montilla", "Montillana", "Montitxelvo/Montichelvo", "Montizón", "Montmajor", "Montmaneu", "Montmell, El", "Montmeló", "Montoliu de Lleida", "Montoliu de Segarra", "Montón", "Montorio", "Montornès de Segarra", "Montornès del Vallès", "Montoro", "Montroy", "Montseny", "Montserrat", "Montuïri", "Monturque", "Monzón", "Monzón de Campos", "Mora", "Mora de Rubielos", "Móra dEbre", "Móra la Nova", "Moradillo de Roa", "Moral de Calatrava", "Moral de Hornuez", "Moral de la Reina", "Moral de Sayago", "Moraleda de Zafayona", "Moraleja", "Moraleja de Enmedio", "Moraleja de las Panaderas", "Moraleja de Matacabras", "Moraleja de Sayago", "Moraleja del Vino", "Morales de Campos", "Morales de Rey", "Morales de Toro", "Morales de Valverde", "Morales del Vino", "Moralina", "Moralzarzal", "Moraña", "Morasverdes", "Morata de Jalón", "Morata de Jiloca", "Morata de Tajuña", "Moratalla", "Moratilla de los Meleros", "Moratinos", "Morcillo", "Morcín", "Moreda de Álava/Moreda Araba", "Morelábor", "Morell, El", "Morella", "Morenilla", "Morentin", "Morera de Montsant, La", "Morera, La", "Moreruela de los Infanzones", "Moreruela de Tábara", "Morés", "Morga", "Moriles", "Morille", "Moríñigo", "Moriscos", "Morón de Almazán", "Morón de la Frontera", "Moronta", "Moros", "Mos", "Moscardón", "Mosqueruela", "Móstoles", "Mota de Altarejos", "Mota del Cuervo", "Mota del Marqués", "Motilla del Palancar", "Motilleja", "Motril", "Moya", "Moya", "Moyuela", "Mozárbez", "Mozoncillo", "Mozota", "Mucientes", "Mudá", "Mudarra, La", "Muduex", "Muel", "Muela, La", "Muelas de los Caballeros", "Muelas del Pan", "Mues", "Muga de Sayago", "Mugardos", "Muíños", "Mula", "Mundaka", "Munébrega", "Munera", "Mungia", "Muniesa", "Munilla", "Munitibar-Arbatzegi Gerrikaitz", "Muntanyola", "Muñana", "Muñico", "Muñogalindo", "Muñogrande", "Muñomer del Peco", "Muñopedro", "Muñopepe", "Muñosancho", "Muñotello", "Muñoveros", "Mura", "Muras", "Murchante", "Murcia", "Murero", "Murias de Paredes", "Muriel", "Muriel de la Fuente", "Muriel Viejo", "Murieta", "Murillo de Gállego", "Murillo de Río Leza", "Murillo el Cuende", "Murillo el Fruto", "Murla", "Muro", "Muro de Aguas", "Muro de Alcoy", "Muro en Cameros", "Muros", "Muros de Nalón", "Murtas", "Murueta", "Muruzábal", "Museros", "Muskiz", "Mutiloa", "Mutriku", "Mutxamel", "Muxía", "Muxika", "Nabarniz", "Nacimiento", "Nafría de Ucero", "Nájera", "Nalda", "Nalec", "Nambroca", "Náquera", "Narboneta", "Narón", "Narrillos del Álamo", "Narrillos del Rebollar", "Narros", "Narros de Matalayegua", "Narros de Saldueña", "Narros del Castillo", "Narros del Puerto", "Naut Aran", "Nava", "Nava de Arévalo", "Nava de Béjar", "Nava de Francia", "Nava de la Asunción", "Nava de Ricomalillo, La", "Nava de Roa", "Nava de Santiago, La", "Nava de Sotrobal", "Nava del Barco", "Nava del Rey", "Nava, La", "Navacarros", "Navacepedilla de Corneja", "Navacerrada", "Navaconcejo", "Navadijos", "Navaescurial", "Navafría", "Navahermosa", "Navahondilla", "Navajas", "Navajún", "Naval", "Navalacruz", "Navalafuente", "Navalagamella", "Navalcán", "Navalcarnero", "Navaleno", "Navales", "Navalilla", "Navalmanzano", "Navalmoral", "Navalmoral de Béjar", "Navalmoral de la Mata", "Navalmoralejo", "Navalmorales, Los", "Navalonguilla", "Navalosa", "Navalperal de Pinares", "Navalperal de Tormes", "Navalpino", "Navalucillos, Los", "Navaluenga", "Navalvillar de Ibor", "Navalvillar de Pela", "Navamorales", "Navamorcuende", "Navaquesera", "Navarcles", "Navardún", "Navares de Ayuso", "Navares de Enmedio", "Navares de las Cuevas", "Navaridas", "Navarredonda de Gredos", "Navarredonda de la Rinconada", "Navarredonda y San Mamés", "Navarredondilla", "Navarrés", "Navarrete", "Navarrevisca", "Navàs", "Navas de Bureba", "Navas de Estena", "Navas de Jadraque, Las", "Navas de Jorquera", "Navas de la Concepción, Las", "Navas de Oro", "Navas de Riofrío", "Navas de San Antonio", "Navas de San Juan", "Navas del Madroño", "Navas del Marqués, Las", "Navas del Rey", "Navascués/Nabaskoze", "Navasfrías", "Navata", "Navatalgordo", "Navatejares", "Navès", "Navezuelas", "Navia", "Navia de Suarna", "Navianos de Valverde", "Nazar", "Nebreda", "Neda", "Negredo", "Negreira", "Negrilla de Palencia", "Negueira de Muñiz", "Neila", "Neila de San Miguel", "Nepas", "Nerja", "Nerpio", "Nerva", "Nestares", "Nevada", "Neves, As", "Niebla", "Nieva", "Nieva de Cameros", "Nigrán", "Nigüelas", "Nigüella", "Niharra", "Níjar", "Nívar", "Noáin (Valle de Elorz)/Noain (Elortzibar)", "Noalejo", "Noblejas", "Noceda del Bierzo", "Noez", "Nogais, As", "Nogal de las Huertas", "Nogales", "Nogueira de Ramuín", "Noguera de Albarracín", "Nogueras", "Nogueruelas", "Noia", "Noja", "Nolay", "Nombela", "Nombrevilla", "Nonaspe", "Noreña", "Nou de Berguedà, La", "Nou de Gaià, La", "Novales", "Novallas", "Novelda", "Novelé/Novetlè", "Novés", "Noviercas", "Novillas", "Nucia, la", "Nueno", "Nueva Carteya", "Nueva Villa de las Torres", "Nuévalos", "Nuevo Baztán", "Nuez de Ebro", "Nules", "Nulles", "Numancia de la Sagra", "Nuño Gómez", "Nuñomoral", "Obanos", "Obejo", "Obón", "Ocaña", "Ocentejo", "Ochagavía/Otsagabia", "Ochánduri", "Oco", "Ocón", "Odèn", "Òdena", "Odieta", "Odón", "Oencia", "Ogassa", "Ogíjares", "Ohanes", "Oia", "Oiartzun", "Oímbra", "Oitz", "Ojacastro", "Ojén", "Ojós", "Ojos Negros", "Ojos-Albos", "Okondo", "Olaberria", "Olaibar", "Olazti/Olazagutía", "Olba", "Olea de Boedo", "Oleiros", "Olejua", "Olèrdola", "Olesa de Bonesvalls", "Olesa de Montserrat", "Oliana", "Olías del Rey", "Oliete", "Oliola", "Olite/Erriberri", "Olius", "Oliva", "Oliva de la Frontera", "Oliva de Mérida", "Oliva de Plasencia", "Oliva, La", "Olivar, El", "Olivares", "Olivares de Duero", "Olivares de Júcar", "Olivella", "Olivenza", "Ollauri", "Olleria, l", "Ollo", "Olmeda de Cobeta", "Olmeda de Jadraque, La", "Olmeda de la Cuesta", "Olmeda de las Fuentes", "Olmeda del Rey", "Olmedilla de Alarcón", "Olmedilla de Eliz", "Olmedillo de Roa", "Olmedo", "Olmedo de Camaces", "Olmillos de Castro", "Olmillos de Muñó", "Olmos de Esgueva", "Olmos de Ojeda", "Olmos de Peñafiel", "Olmos, Los", "Olocau", "Olocau del Rey", "Olombrada", "Olóriz/Oloritz", "Olost", "Olot", "Oluges, Les", "Olula de Castro", "Olula del Río", "Olvan", "Ólvega", "Olvena", "Olvera", "Olvés", "Omañas, Las", "Omellons, Els", "Omells de na Gaia, Els", "Oncala", "Onda", "Ondara", "Ondarroa", "Onil", "Onís", "Ontígola", "Ontinyent", "Ontiñena", "Ontur", "Onzonilla", "Oña", "Oñati", "Oquillas", "Orba", "Orbada, La", "Orbaizeta", "Orbaneja Riopico", "Orbara", "Orbita", "Orcajo", "Orce", "Orcera", "Ordes", "Ordial, El", "Ordis", "Ordizia", "Orea", "Orejana", "Orellana de la Sierra", "Orellana la Vieja", "Orendain", "Orera", "Orés", "Orexa", "Organyà", "Orgaz", "Órgiva", "Oria", "Orihuela", "Orihuela del Tremedal", "Orio", "Orís", "Orísoain", "Oristà", "Orkoien", "Ormaiztegi", "Oronz/Orontze", "Oropesa", "Oropesa del Mar/Orpesa", "Oroso", "Orotava, La", "Oroz-Betelu/Orotz-Betelu", "Orozko", "Orpí", "Orreaga/Roncesvalles", "Orrios", "Òrrius", "Ortigosa de Cameros", "Ortigosa de Pestaño", "Ortigosa del Monte", "Ortigueira", "Ortuella", "Orusco de Tajuña", "Orxeta", "Os de Balaguer", "Osa de la Vega", "Oseja", "Oseja de Sajambre", "Osera de Ebro", "Oso, El", "Osor", "Osornillo", "Osorno la Mayor", "Ossa de Montiel", "Osso de Cinca", "Ossó de Sió", "Osuna", "Oteiza", "Otero", "Otero de Bodas", "Otero de Herreros", "Otívar", "Otos", "Otura", "Otxandio", "Ourense", "Ourol", "Outeiro de Rei", "Outes", "Oviedo", "Oyón-Oion", "Oza dos Ríos", "Pacs del Penedès", "Paderne", "Paderne de Allariz", "Padiernos", "Padilla de Abajo", "Padilla de Arriba", "Padrenda", "Padrón", "Padrones de Bureba", "Padul", "Padules", "Paiporta", "Pájara", "Pajarejos", "Pajares de Adaja", "Pajares de la Laguna", "Pajares de la Lampreana", "Pajares de los Oteros", "Pajarón", "Pajaroncillo", "Palacios de Goda", "Palacios de la Sierra", "Palacios de la Valduerna", "Palacios de Riopisuerga", "Palacios de Sanabria", "Palacios del Arzobispo", "Palacios del Pan", "Palacios del Sil", "Palacios y Villafranca, Los", "Palaciosrubios", "Palafolls", "Palafrugell", "Palamós", "Palanques", "Palas de Rei", "Palau dAnglesola, El", "Palau de Santa Eulàlia", "Palau-sator", "Palau-saverdera", "Palau-solità i Plegamans", "Palazuelo de Vedija", "Palazuelos de Eresma", "Palazuelos de la Sierra", "Palazuelos de Muñó", "Palencia", "Palencia de Negrilla", "Palenciana", "Palenzuela", "Pallaresos, Els", "Pallejà", "Palma", "Palma de Cervelló, La", "Palma de Gandía", "Palma dEbre, La", "Palma del Condado, La", "Palma del Río", "Pálmaces de Jadraque", "Palmas de Gran Canaria, Las", "Palmera", "Palo", "Palol de Revardit", "Palomar de Arroyos", "Palomar, el", "Palomares del Campo", "Palomares del Río", "Palomas", "Palomeque", "Palomera", "Palomero", "Palos de la Frontera", "Pals", "Pampaneira", "Pampliega", "Pamplona/Iruña", "Pancorbo", "Pancrudo", "Paniza", "Panticosa", "Pantoja", "Pantón", "Papatrigo", "Papiol, El", "Paracuellos", "Paracuellos de Jarama", "Paracuellos de Jiloca", "Paracuellos de la Ribera", "Parada de Arriba", "Parada de Rubiales", "Parada de Sil", "Paradas", "Paradela", "Paradinas de San Juan", "Páramo de Boedo", "Páramo del Sil", "Páramo, O", "Parauta", "Parcent", "Pardilla", "Pardines", "Pardos", "Paredes", "Paredes de Escalona", "Paredes de Nava", "Paredes de Sigüenza", "Pareja", "Parets del Vallès", "Parla", "Parlavà", "Parra de las Vegas, La", "Parra, La", "Parral, El", "Parras de Castellote, Las", "Parres", "Parrilla, La", "Parrillas", "Partaloa", "Partido de la Sierra en Tobalina", "Pasaia", "Pasarón de la Vera", "Pascualcobo", "Paso, El", "Passanant i Belltall", "Pastores", "Pastoriza, A", "Pastrana", "Pastriz", "Paterna", "Paterna de Rivera", "Paterna del Campo", "Paterna del Madera", "Paterna del Río", "Patones", "Pau", "Paüls", "Pavías", "Paymogo", "Payo de Ojeda", "Payo, El", "Pazos de Borbén", "Pazuengos", "Peal de Becerro", "Pechina", "Pedernoso, El", "Pedrafita do Cebreiro", "Pedraja de Portillo, La", "Pedrajas de San Esteban", "Pedralba", "Pedralba de la Pradería", "Pedraza", "Pedraza de Alba", "Pedraza de Campos", "Pedregal, El", "Pedreguer", "Pedrera", "Pedret i Marzà", "Pedrezuela", "Pedro Abad", "Pedro Bernardo", "Pedro Martínez", "Pedro Muñoz", "Pedro-Rodríguez", "Pedroche", "Pedrola", "Pedroñeras, Las", "Pedrosa de Duero", "Pedrosa de la Vega", "Pedrosa de Río Úrbel", "Pedrosa del Páramo", "Pedrosa del Príncipe", "Pedrosa del Rey", "Pedrosas, Las", "Pedrosillo de Alba", "Pedrosillo de los Aires", "Pedrosillo el Ralo", "Pedroso", "Pedroso de Acim", "Pedroso de la Armuña, El", "Pedroso, El", "Pegalajar", "Pego", "Pego, El", "Peguerinos", "Pelabravo", "Pelahustán", "Pelarrodríguez", "Pelayos", "Pelayos de la Presa", "Pelayos del Arroyo", "Peleagonzalo", "Peleas de Abajo", "Peligros", "Penagos", "Penàguila", "Penelles", "Peníscola/Peñíscola", "Peña, La", "Peñacaballera", "Peñacerrada-Urizaharra", "Peñafiel", "Peñaflor", "Peñaflor de Hornija", "Peñalba", "Peñalba de Ávila", "Peñalén", "Peñalsordo", "Peñalver", "Peñamellera Alta", "Peñamellera Baja", "Peñaparda", "Peñaranda de Bracamonte", "Peñaranda de Duero", "Peñarandilla", "Peñarroya de Tastavins", "Peñarroya-Pueblonuevo", "Peñarrubia", "Peñas de Riglos, Las", "Peñas de San Pedro", "Peñascosa", "Peñausende", "Pepino", "Peque", "Pera, La", "Peracense", "Perafita", "Perafort", "Peral de Arlanza", "Peral, El", "Peralada", "Peraleda de la Mata", "Peraleda de San Román", "Peraleda del Zaucejo", "Peraleja, La", "Peralejos", "Peralejos de Abajo", "Peralejos de Arriba", "Peralejos de las Truchas", "Perales", "Perales de Tajuña", "Perales del Alfambra", "Perales del Puerto", "Peralta de Alcofea", "Peralta de Calasanz", "Peralta/Azkoien", "Peraltilla", "Peralveche", "Peramola", "Peranzanes", "Perarrúa", "Perdigón, El", "Perdiguera", "Pereiro de Aguiar, O", "Perelló, El", "Pereña de la Ribera", "Pereruela", "Periana", "Perilla de Castro", "Pernía, La", "Peromingo", "Perosillo", "Peroxa, A", "Pertusa", "Pesaguero", "Pescueza", "Pesga, La", "Pesoz", "Pesquera", "Pesquera de Duero", "Pesquera, La", "Petilla de Aragón", "Petín", "Petra", "Petrer", "Petrés", "Pétrola", "Peza, La", "Pezuela de las Torres", "Pías", "Picanya", "Picassent", "Picazo, El", "Picón", "Piedrabuena", "Piedrahíta", "Piedrahita de Castro", "Piedralaves", "Piedramillera", "Piedras Albas", "Piedratajada", "Piélagos", "Piera", "Piérnigas", "Pilar de la Horadada", "Pilas", "Piles", "Piles, Les", "Piloña", "Pina de Ebro", "Pina de Montalgrao", "Pinar de El Hierro, El", "Pinar, El", "Pinarejo", "Pinarejos", "Pinarnegrillo", "Pineda de Gigüela", "Pineda de la Sierra", "Pineda de Mar", "Pineda Trasmonte", "Pinedas", "Pinell de Brai, El", "Pinell de Solsonès", "Pinet", "Pinilla de Jadraque", "Pinilla de los Barruecos", "Pinilla de los Moros", "Pinilla de Molina", "Pinilla de Toro", "Pinilla del Campo", "Pinilla del Valle", "Pinilla Trasmonte", "Pinillos", "Pino de Tormes, El", "Pino del Oro", "Pino del Río", "Pino, O", "Pinofranqueado", "Pinós", "Pinos Genil", "Pinos Puente", "Pinós, el/Pinoso", "Pinseque", "Pintanos, Los", "Pinto", "Piña de Campos", "Piña de Esgueva", "Píñar", "Piñel de Abajo", "Piñel de Arriba", "Piñero, El", "Piñor", "Piñuécar-Gandullas", "Piornal", "Pioz", "Piqueras", "Piqueras del Castillo", "Pira", "Piracés", "Pitarque", "Pitiegua", "Pitillas", "Pizarra", "Pizarral", "Pla de Santa Maria, El", "Pla del Penedès, El", "Plan", "Planes", "Planes dHostoles, Les", "Planoles", "Plans de Sió, Els", "Plasencia", "Plasencia de Jalón", "Plasenzuela", "Pleitas", "Plenas", "Plentzia", "Pliego", "Plou", "Poal, El", "Pobla de Benifassà, la", "Pobla de Cérvoles, La", "Pobla de Claramunt, La", "Pobla de Farnals, la", "Pobla de Lillet, La", "Pobla de Mafumet, La", "Pobla de Massaluca, La", "Pobla de Montornès, La", "Pobla de Segur, La", "Pobla de Vallbona, la", "Pobla del Duc, la", "Pobla Llarga, la", "Pobla Tornesa, la", "Pobla, Sa", "Población de Arroyo", "Población de Campos", "Población de Cerrato", "Pobladura de Pelayo García", "Pobladura de Valderaduey", "Pobladura del Valle", "Poblete", "Poblets, els", "Pobo de Dueñas, El", "Pobo, El", "Poboleda", "Pobra de Trives, A", "Pobra do Brollón, A", "Pobra do Caramiñal, A", "Poio", "Pol", "Pola de Gordón, La", "Polaciones", "Polán", "Polanco", "Polentinos", "Poleñino", "Polícar", "Polinyà", "Polinyà de Xúquer", "Pollença", "Pollos", "Polop", "Polopos", "Pomar de Valdivia", "Pomer", "Ponferrada", "Ponga", "Pont dArmentera, El", "Pont de Bar, El", "Pont de Molins", "Pont de Suert, El", "Pont de Vilomara i Rocafort, El", "Ponte Caldelas", "Ponteareas", "Ponteceso", "Pontecesures", "Pontedeume", "Pontedeva", "Pontenova, A", "Pontes de García Rodríguez, As", "Pontevedra", "Pontils", "Pontons", "Pontós", "Ponts", "Porcuna", "Porqueira", "Porqueres", "Porrera", "Porreres", "Porriño, O", "Port de la Selva, El", "Portaje", "Portalrubio de Guadamejud", "Portas", "Portbou", "Portell de Morella", "Portella, La", "Portellada, La", "Portezuelo", "Portilla", "Portillo", "Portillo de Soria", "Portillo de Toledo", "Porto", "Porto do Son", "Portomarín", "Portugalete", "Pórtugos", "Porzuna", "Posada de Valdeón", "Posadas", "Potes", "Potríes", "Poveda", "Poveda de la Sierra", "Poveda de las Cintas", "Póveda de Soria, La", "Povedilla", "Poyales del Hoyo", "Poyatos", "Poza de la Sal", "Poza de la Vega", "Pozal de Gallinas", "Pozaldez", "Pozalmuro", "Pozán de Vero", "Pozanco", "Pozo Alcón", "Pozo Cañada", "Pozo de Almoguera", "Pozo de Guadalajara", "Pozo de Urama", "Pozo-Lorente", "Pozoamargo", "Pozoantiguo", "Pozoblanco", "Pozohondo", "Pozondón", "Pozorrubielos de la Mancha", "Pozorrubio", "Pozos de Hinojo", "Pozuel de Ariza", "Pozuel del Campo", "Pozuelo", "Pozuelo de Alarcón", "Pozuelo de Aragón", "Pozuelo de Calatrava", "Pozuelo de la Orden", "Pozuelo de Tábara", "Pozuelo de Zarzón", "Pozuelo del Páramo", "Pozuelo del Rey", "Pozuelo, El", "Pozuelos de Calatrava, Los", "Pradales", "Prádanos de Bureba", "Prádanos de Ojeda", "Pradejón", "Pradell de la Teixeta", "Prádena", "Prádena de Atienza", "Prádena del Rincón", "Prades", "Pradilla de Ebro", "Pradillo", "Prado", "Prado de la Guzpeña", "Prado del Rey", "Pradoluengo", "Prados Redondos", "Pradosegar", "Prat de Comte", "Prat de Llobregat, El", "Pratdip", "Prats de Lluçanès", "Prats de Rei, Els", "Prats i Sansor", "Pravia", "Preixana", "Preixens", "Préjano", "Premià de Dalt", "Premià de Mar", "Presencio", "Preses, Les", "Priaranza del Bierzo", "Priego", "Priego de Córdoba", "Prioro", "Proaza", "Provencio, El", "Prullans", "Pruna", "Puçol", "Puebla de Albortón", "Puebla de Alcocer", "Puebla de Alfindén, La", "Puebla de Almenara", "Puebla de Almoradiel, La", "Puebla de Arenoso", "Puebla de Arganzón, La", "Puebla de Azaba", "Puebla de Beleña", "Puebla de Castro, La", "Puebla de Cazalla, La", "Puebla de Don Fadrique", "Puebla de Don Rodrigo", "Puebla de Guzmán", "Puebla de Híjar, La", "Puebla de la Calzada", "Puebla de la Reina", "Puebla de la Sierra", "Puebla de Lillo", "Puebla de los Infantes, La", "Puebla de Montalbán, La", "Puebla de Obando", "Puebla de Pedraza", "Puebla de San Medel", "Puebla de San Miguel", "Puebla de Sanabria", "Puebla de Sancho Pérez", "Puebla de Valdavia, La", "Puebla de Valles", "Puebla de Valverde, La", "Puebla de Yeltes", "Puebla del Maestre", "Puebla del Príncipe", "Puebla del Prior", "Puebla del Río, La", "Puebla del Salvador", "Pueblanueva, La", "Pueblica de Valverde", "Pueblonuevo del Guadiana", "Puendeluna", "Puente de Domingo Flórez", "Puente de Génave", "Puente de Montañana", "Puente del Arzobispo, El", "Puente del Congosto", "Puente Genil", "Puente la Reina de Jaca", "Puente la Reina/Gares", "Puente Viesgo", "Puentedura", "Puentes Viejas", "Puerta de Segura, La", "Puertas", "Puerto Castilla", "Puerto de Béjar", "Puerto de la Cruz", "Puerto de San Vicente", "Puerto de Santa Cruz", "Puerto de Santa María, El", "Puerto del Rosario", "Puerto Lápice", "Puerto Lumbreras", "Puerto Moral", "Puerto Real", "Puerto Seguro", "Puerto Serrano", "Puértolas", "Puertollano", "Puertomingalvo", "Pueyo", "Pueyo de Araguás, El", "Pueyo de Santa Cruz", "Puig", "Puig-reig", "Puigcerdà", "Puigdàlber", "Puiggròs", "Puigpelat", "Puigpunyent", "Puigverd dAgramunt", "Puigverd de Lleida", "Pujalt", "Pujerra", "Pulgar", "Pulianas", "Pulpí", "Punta Umbría", "Puntagorda", "Puntallana", "Punxín", "Puras", "Purchena", "Purujosa", "Purullena", "Quar, La", "Quart", "Quart de les Valls", "Quart de Poblet", "Quartell", "Quatretonda", "Quatretondeta", "Quel", "Quemada", "Quéntar", "Quer", "Queralbs", "Quero", "Querol", "Quesa", "Quesada", "Quicena", "Quijorna", "Quintana de la Serena", "Quintana del Castillo", "Quintana del Marco", "Quintana del Pidio", "Quintana del Puente", "Quintana Redonda", "Quintana y Congosto", "Quintanabureba", "Quintanaélez", "Quintanaortuño", "Quintanapalla", "Quintanar de la Orden", "Quintanar de la Sierra", "Quintanar del Rey", "Quintanas de Gormaz", "Quintanavides", "Quintanilla de Arriba", "Quintanilla de la Mata", "Quintanilla de Onésimo", "Quintanilla de Onsoña", "Quintanilla de Trigueros", "Quintanilla de Urz", "Quintanilla del Agua y Tordueles", "Quintanilla del Coco", "Quintanilla del Molar", "Quintanilla del Monte", "Quintanilla del Olmo", "Quintanilla San García", "Quintanilla Vivar", "Quintanillas, Las", "Quintela de Leirado", "Quinto", "Quiñonería", "Quiroga", "Quirós", "Quiruelas de Vidriales", "Quismondo", "Rábade", "Rabanales", "Rabanera", "Rabanera del Pinar", "Rábano", "Rábano de Aliste", "Rábanos", "Rábanos, Los", "Rabé de las Calzadas", "Rabós", "Rada de Haro", "Rafal", "Ráfales", "Rafelbuñol/Rafelbunyol", "Rafelcofer", "Rafelguaraf", "Ràfol dAlmúnia, el", "Ráfol de Salem", "Rágama", "Rágol", "Rairiz de Veiga", "Rajadell", "Ramales de la Victoria", "Rambla, La", "Ramirás", "Ramiro", "Rapariegos", "Rascafría", "Rasillo de Cameros, El", "Rasines", "Rasquera", "Rasueros", "Real", "Real de Gandía", "Real de la Jara, El", "Real de San Vicente, El", "Real Sitio de San Ildefonso", "Realejos, Los", "Rebollar", "Rebollar", "Rebolledo de la Torre", "Rebollo", "Rebollosa de Jadraque", "Recas", "Recueja, La", "Recuenco, El", "Recuerda", "Redal, El", "Redecilla del Camino", "Redecilla del Campo", "Redonda, La", "Redondela", "Redován", "Redueña", "Regencós", "Regueras de Arriba", "Regueras, Las", "Regumiel de la Sierra", "Reíllo", "Reina", "Reinosa", "Reinoso", "Reinoso de Cerrato", "Relleu", "Rellinars", "Rello", "Remolinos", "Remondo", "Rena", "Renau", "Renedo de Esgueva", "Renedo de la Vega", "Renera", "Renieblas", "Reocín", "Requejo", "Requena", "Requena de Campos", "Respenda de la Peña", "Retamal de Llerena", "Retamoso de la Jara", "Retascón", "Retiendas", "Retortillo", "Retortillo de Soria", "Retuerta", "Retuerta del Bullaque", "Reus", "Revellinos", "Revenga de Campos", "Revilla de Collazos", "Revilla del Campo", "Revilla Vallejera", "Revilla y Ahedo, La", "Revillarruz", "Reyero", "Rezmondo", "Reznos", "Riaguas de San Bartolomé", "Rialp", "Rianxo", "Riaño", "Riaza", "Riba de Escalote, La", "Riba de Saelices", "Riba-roja de Túria", "Riba-roja dEbre", "Riba, La", "Ribadavia", "Ribadedeva", "Ribadeo", "Ribadesella", "Ribadumia", "Ribaforada", "Ribafrecha", "Ribamontán al Mar", "Ribamontán al Monte", "Ribas de Campos", "Ribas de Sil", "Ribatejada", "Ribeira", "Ribeira de Piquín", "Ribera Baja/Erribera Beitia", "Ribera de Arriba", "Ribera del Fresno", "Ribera dOndara", "Ribera dUrgellet", "Riberos de la Cueza", "Ribes de Freser", "Ribesalbes", "Ribota", "Ricla", "Ricote", "Riego de la Vega", "Riello", "Riells i Viabrea", "Rielves", "Riera de Gaià, La", "Rillo", "Rillo de Gallo", "Rincón de la Victoria", "Rincón de Soto", "Rinconada de la Sierra, La", "Rinconada, La", "Riner", "Riocabado", "Riocavado de la Sierra", "Riodeva", "Riofrío", "Riofrío de Aliste", "Riofrío de Riaza", "Riofrío del Llano", "Riogordo", "Rioja", "Riola", "Riolobos", "Rionansa", "Rionegro del Puente", "Riópar", "Riós", "Riosa", "Rioseco de Soria", "Rioseco de Tapia", "Riotorto", "Riotuerto", "Ripoll", "Ripollet", "Risco", "Riu de Cerdanya", "Riudarenes", "Riudaura", "Riudecanyes", "Riudecols", "Riudellots de la Selva", "Riudoms", "Riumors", "Rivas-Vaciamadrid", "Rivilla de Barajas", "Roa", "Roales", "Roales de Campos", "Robla, La", "Robladillo", "Robleda", "Robleda-Cervantes", "Robledillo de Gata", "Robledillo de la Jara", "Robledillo de la Vera", "Robledillo de Mohernando", "Robledillo de Trujillo", "Robledo", "Robledo de Chavela", "Robledo de Corpes", "Robledo del Mazo", "Robledo, El", "Robledollano", "Robliza de Cojos", "Robregordo", "Robres", "Robres del Castillo", "Roca de la Sierra, La", "Roca del Vallès, La", "Rocafort", "Rocafort de Queralt", "Rociana del Condado", "Roda de Andalucía, La", "Roda de Barà", "Roda de Eresma", "Roda de Ter", "Roda, La", "Rodeiro", "Ródenas", "Rodezno", "Rodonyà", "Roelos de Sayago", "Rois", "Rojales", "Rojas", "Rollamienta", "Rollán", "Romana, la", "Romangordo", "Romanillos de Atienza", "Romanones", "Romanos", "Romanzado", "Romeral, El", "Roncal/Erronkari", "Ronda", "Ronquillo, El", "Roperuelos del Páramo", "Roquetas de Mar", "Roquetes", "Rosal de la Frontera", "Rosal, O", "Rosalejo", "Rosario, El", "Roses", "Rosinos de la Requejada", "Rossell", "Rosselló", "Rota", "Rotglà i Corberà", "Rótova", "Roturas", "Rourell, El", "Royo, El", "Royuela", "Royuela de Río Franco", "Rozalén del Monte", "Rozas de Madrid, Las", "Rozas de Puerto Real", "Rozas de Valdearroyo, Las", "Rúa, A", "Ruanes", "Rubena", "Rubí", "Rubí de Bracamonte", "Rubiá", "Rubiales", "Rubielos de la Cérida", "Rubielos de Mora", "Rubió", "Rubio, El", "Rubite", "Rublacedo de Abajo", "Rucandio", "Rueda", "Rueda de Jalón", "Rueda de la Sierra", "Ruente", "Ruesca", "Ruesga", "Rugat", "Ruidera", "Ruiloba", "Rupià", "Rupit i Pruit", "Rus", "Rute", "Sabadell", "Sabero", "Sabiñán", "Sabiñánigo", "Sabiote", "Sacañet", "Sacecorbo", "Saceda-Trasierra", "Sacedón", "Saceruela", "Sacramenia", "Sada", "Sada", "Sádaba", "Saelices", "Saelices de la Sal", "Saelices de Mayorga", "Saelices el Chico", "Sagàs", "Sagra", "Sagrada, La", "Sagunto/Sagunt", "Sahagún", "Sahugo, El", "Sahún", "Sajazarra", "Salamanca", "Salar", "Salares", "Salas", "Salas Altas", "Salas Bajas", "Salas de Bureba", "Salas de los Infantes", "Salàs de Pallars", "Salce", "Salceda de Caselas", "Salcedillo", "Saldaña", "Saldaña de Burgos", "Saldeana", "Saldes", "Saldías", "Saldón", "Salduero", "Salem", "Sales de Llierca", "Salillas", "Salillas de Jalón", "Salinas", "Salinas de Oro/Jaitz", "Salinas de Pisuerga", "Salinas del Manzano", "Salines, Ses", "Salinillas de Bureba", "Sallent", "Sallent de Gállego", "Salmerón", "Salmeroncillos", "Salmoral", "Salobral", "Salobre", "Salobreña", "Salomó", "Salorino", "Salou", "Salt", "Salteras", "Salvacañete", "Salvadiós", "Salvador de Zapardiel", "Salvaleón", "Salvaterra de Miño", "Salvatierra de Esca", "Salvatierra de los Barros", "Salvatierra de Santiago", "Salvatierra de Tormes", "Salvatierra/Agurain", "Salzadella, la", "Samaniego", "Samboal", "Samir de los Caños", "Samos", "Samper de Calanda", "Samper del Salz", "San Adrián", "San Adrián de Juarros", "San Adrián del Valle", "San Agustín", "San Agustín del Guadalix", "San Agustín del Pozo", "San Amaro", "San Andrés del Congosto", "San Andrés del Rabanedo", "San Andrés del Rey", "San Andrés y Sauces", "San Antonio de Benagéber", "San Asensio", "San Bartolomé", "San Bartolomé de Béjar", "San Bartolomé de Corneja", "San Bartolomé de la Torre", "San Bartolomé de las Abiertas", "San Bartolomé de Pinares", "San Bartolomé de Tirajana", "San Carlos del Valle", "San Cebrián de Campos", "San Cebrián de Castro", "San Cebrián de Mazote", "San Cebrián de Mudá", "San Cibrao das Viñas", "San Clemente", "San Cristóbal de Boedo", "San Cristóbal de Cuéllar", "San Cristóbal de Entreviñas", "San Cristóbal de la Cuesta", "San Cristóbal de La Laguna", "San Cristóbal de la Polantera", "San Cristóbal de la Vega", "San Cristóbal de Segovia", "San Cristovo de Cea", "San Emiliano", "San Esteban de Gormaz", "San Esteban de la Sierra", "San Esteban de Litera", "San Esteban de los Patos", "San Esteban de Nogales", "San Esteban de Zapardiel", "San Esteban del Molar", "San Esteban del Valle", "San Felices", "San Felices de Buelna", "San Felices de los Gallegos", "San Fernando", "San Fernando de Henares", "San Fulgencio", "San García de Ingelmos", "San Isidro", "San Javier", "San José del Valle", "San Juan de Aznalfarache", "San Juan de Gredos", "San Juan de la Encinilla", "San Juan de la Nava", "San Juan de la Rambla", "San Juan de Plan", "San Juan del Molinillo", "San Juan del Monte", "San Juan del Olmo", "San Juan del Puerto", "San Justo", "San Justo de la Vega", "San Leonardo de Yagüe", "San Llorente", "San Lorenzo de Calatrava", "San Lorenzo de El Escorial", "San Lorenzo de la Parrilla", "San Lorenzo de Tormes", "San Mamés de Burgos", "San Mamés de Campos", "San Martín de Boniches", "San Martín de la Vega", "San Martín de la Vega del Alberche", "San Martín de la Virgen de Moncayo", "San Martín de Montalbán", "San Martín de Oscos", "San Martín de Pusa", "San Martín de Rubiales", "San Martín de Trevejo", "San Martín de Unx", "San Martín de Valdeiglesias", "San Martín de Valderaduey", "San Martín de Valvení", "San Martín del Castañar", "San Martín del Pimpollar", "San Martín del Rey Aurelio", "San Martín del Río", "San Martín y Mudrián", "San Mateo de Gállego", "San Miguel de Abona", "San Miguel de Aguayo", "San Miguel de Bernuy", "San Miguel de Corneja", "San Miguel de la Ribera", "San Miguel de Salinas", "San Miguel de Serrezuela", "San Miguel de Valero", "San Miguel del Arroyo", "San Miguel del Cinca", "San Miguel del Pino", "San Miguel del Robledo", "San Miguel del Valle", "San Millán de la Cogolla", "San Millán de Lara", "San Millán de los Caballeros", "San Millán de Yécora", "San Millán/Donemiliaga", "San Morales", "San Muñoz", "San Nicolás del Puerto", "San Pablo de la Moraleja", "San Pablo de los Montes", "San Pascual", "San Pedro", "San Pedro Bercianos", "San Pedro de Ceque", "San Pedro de Gaíllos", "San Pedro de la Nave-Almendra", "San Pedro de Latarce", "San Pedro de Mérida", "San Pedro de Rozados", "San Pedro del Arroyo", "San Pedro del Pinatar", "San Pedro del Romeral", "San Pedro del Valle", "San Pedro Manrique", "San Pedro Palmiches", "San Pelayo", "San Pelayo de Guareña", "San Rafael del Río", "San Román de Cameros", "San Román de Hornija", "San Román de la Cuba", "San Román de los Montes", "San Roque", "San Roque de Riomiera", "San Sadurniño", "San Salvador", "San Sebastián de la Gomera", "San Sebastián de los Ballesteros", "San Sebastián de los Reyes", "San Silvestre de Guzmán", "San Tirso de Abres", "San Torcuato", "San Vicente de Alcántara", "San Vicente de Arévalo", "San Vicente de la Barquera", "San Vicente de la Cabeza", "San Vicente de la Sonsierra", "San Vicente del Palacio", "San Vicente del Raspeig/Sant Vicent del Raspeig", "San Vicente del Valle", "San Vitero", "San Xoán de Río", "Sanaüja", "Sancedo", "Sanchidrián", "Sanchón de la Ribera", "Sanchón de la Sagrada", "Sanchonuño", "Sanchorreja", "Sanchotello", "Sancti-Spíritus", "Sancti-Spíritus", "Sandiás", "Sando", "Sanet y Negrals", "Sangarcía", "Sangarrén", "Sangüesa/Zangoza", "Sanlúcar de Barrameda", "Sanlúcar de Guadiana", "Sanlúcar la Mayor", "Sansol", "Sant Adrià de Besòs", "Sant Agustí de Lluçanès", "Sant Andreu de la Barca", "Sant Andreu de Llavaneres", "Sant Andreu Salou", "Sant Aniol de Finestres", "Sant Antoni de Portmany", "Sant Antoni de Vilamajor", "Sant Bartomeu del Grau", "Sant Boi de Llobregat", "Sant Boi de Lluçanès", "Sant Carles de la Ràpita", "Sant Cebrià de Vallalta", "Sant Celoni", "Sant Climent de Llobregat", "Sant Climent Sescebes", "Sant Cugat del Vallès", "Sant Cugat Sesgarrigues", "Sant Esteve de la Sarga", "Sant Esteve de Palautordera", "Sant Esteve Sesrovires", "Sant Feliu de Buixalleu", "Sant Feliu de Codines", "Sant Feliu de Guíxols", "Sant Feliu de Llobregat", "Sant Feliu de Pallerols", "Sant Feliu Sasserra", "Sant Ferriol", "Sant Fost de Campsentelles", "Sant Fruitós de Bages", "Sant Gregori", "Sant Guim de Freixenet", "Sant Guim de la Plana", "Sant Hilari Sacalm", "Sant Hipòlit de Voltregà", "Sant Iscle de Vallalta", "Sant Jaume de Frontanyà", "Sant Jaume de Llierca", "Sant Jaume dels Domenys", "Sant Jaume dEnveja", "Sant Joan", "Sant Joan dAlacant", "Sant Joan de Labritja", "Sant Joan de les Abadesses", "Sant Joan de Mollet", "Sant Joan de Moró", "Sant Joan de Vilatorrada", "Sant Joan Despí", "Sant Joan les Fonts", "Sant Joanet", "Sant Jordi Desvalls", "Sant Jordi/San Jorge", "Sant Josep de sa Talaia", "Sant Julià de Cerdanyola", "Sant Julià de Ramis", "Sant Julià de Vilatorta", "Sant Julià del Llor i Bonmatí", "Sant Just Desvern", "Sant Llorenç de la Muga", "Sant Llorenç de Morunys", "Sant Llorenç des Cardassar", "Sant Llorenç dHortons", "Sant Llorenç Savall", "Sant Lluís", "Sant Martí dAlbars", "Sant Martí de Centelles", "Sant Martí de Llémena", "Sant Martí de Riucorb", "Sant Martí de Tous", "Sant Martí Sarroca", "Sant Martí Sesgueioles", "Sant Martí Vell", "Sant Mateu", "Sant Mateu de Bages", "Sant Miquel de Campmajor", "Sant Miquel de Fluvià", "Sant Mori", "Sant Pau de Segúries", "Sant Pere de Ribes", "Sant Pere de Riudebitlles", "Sant Pere de Torelló", "Sant Pere de Vilamajor", "Sant Pere Pescador", "Sant Pere Sallavinera", "Sant Pol de Mar", "Sant Quintí de Mediona", "Sant Quirze de Besora", "Sant Quirze del Vallès", "Sant Quirze Safaja", "Sant Ramon", "Sant Sadurní dAnoia", "Sant Sadurní dOsormort", "Sant Salvador de Guardiola", "Sant Vicenç de Castellet", "Sant Vicenç de Montalt", "Sant Vicenç de Torelló", "Sant Vicenç dels Horts", "Santa Amalia", "Santa Ana", "Santa Ana de Pusa", "Santa Ana la Real", "Santa Bàrbara", "Santa Bárbara de Casa", "Santa Brígida", "Santa Cecilia", "Santa Cecília de Voltregà", "Santa Cecilia del Alcor", "Santa Cilia", "Santa Clara de Avedillo", "Santa Coloma", "Santa Coloma de Cervelló", "Santa Coloma de Farners", "Santa Coloma de Gramenet", "Santa Coloma de Queralt", "Santa Colomba de Curueño", "Santa Colomba de las Monjas", "Santa Colomba de Somoza", "Santa Comba", "Santa Cristina dAro", "Santa Cristina de la Polvorosa", "Santa Cristina de Valmadrigal", "Santa Croya de Tera", "Santa Cruz de Bezana", "Santa Cruz de Boedo", "Santa Cruz de Grío", "Santa Cruz de la Palma", "Santa Cruz de la Salceda", "Santa Cruz de la Serós", "Santa Cruz de la Sierra", "Santa Cruz de la Zarza", "Santa Cruz de los Cáñamos", "Santa Cruz de Marchena", "Santa Cruz de Moncayo", "Santa Cruz de Moya", "Santa Cruz de Mudela", "Santa Cruz de Nogueras", "Santa Cruz de Paniagua", "Santa Cruz de Pinares", "Santa Cruz de Tenerife", "Santa Cruz de Yanguas", "Santa Cruz del Comercio", "Santa Cruz del Retamar", "Santa Cruz del Valle", "Santa Cruz del Valle Urbión", "Santa Elena", "Santa Elena de Jamuz", "Santa Engracia del Jubera", "Santa Eufemia", "Santa Eufemia del Arroyo", "Santa Eufemia del Barco", "Santa Eugènia", "Santa Eugènia de Berga", "Santa Eulalia", "Santa Eulalia Bajera", "Santa Eulalia de Gállego", "Santa Eulalia de Oscos", "Santa Eulàlia de Riuprimer", "Santa Eulàlia de Ronçana", "Santa Eulalia del Río", "Santa Fe", "Santa Fe de Mondújar", "Santa Fe del Penedès", "Santa Gadea del Cid", "Santa Inés", "Santa Llogaia dÀlguema", "Santa Lucía de Tirajana", "Santa Magdalena de Pulpis", "Santa Margalida", "Santa Margarida de Montbui", "Santa Margarida i els Monjos", "Santa Maria de Besora", "Santa María de Cayón", "Santa Maria de Corcó", "Santa María de Dulcis", "Santa María de Guía de Gran Canaria", "Santa María de Huerta", "Santa María de la Alameda", "Santa María de la Isla", "Santa María de la Vega", "Santa María de las Hoyas", "Santa María de los Caballeros", "Santa María de los Llanos", "Santa Maria de Martorelles", "Santa Maria de Merlès", "Santa Maria de Miralles", "Santa María de Ordás", "Santa Maria de Palautordera", "Santa María de Sando", "Santa María de Valverde", "Santa María del Arroyo", "Santa María del Berrocal", "Santa María del Camí", "Santa María del Campo", "Santa María del Campo Rus", "Santa María del Cubillo", "Santa María del Invierno", "Santa María del Mercadillo", "Santa María del Monte de Cea", "Santa María del Páramo", "Santa María del Tiétar", "Santa María del Val", "Santa Maria dOló", "Santa María la Real de Nieva", "Santa María Rivarredonda", "Santa Marina del Rey", "Santa Marta", "Santa Marta de Magasca", "Santa Marta de Tormes", "Santa Marta del Cerro", "Santa Olalla", "Santa Olalla de Bureba", "Santa Olalla del Cala", "Santa Oliva", "Santa Pau", "Santa Perpètua de Mogoda", "Santa Pola", "Santa Susanna", "Santa Úrsula", "Santacara", "Santaella", "Santaliestra y San Quílez", "Santander", "Santanyí", "Santas Martas", "Santed", "Santervás de Campos", "Santervás de la Vega", "Santiago de Alcántara", "Santiago de Calatrava", "Santiago de Compostela", "Santiago de la Puebla", "Santiago del Campo", "Santiago del Collado", "Santiago del Teide", "Santiago del Tormes", "Santiago Millas", "Santiago-Pontones", "Santibáñez de Béjar", "Santibáñez de Ecla", "Santibáñez de Esgueva", "Santibáñez de la Peña", "Santibáñez de la Sierra", "Santibáñez de Tera", "Santibáñez de Valcorba", "Santibáñez de Vidriales", "Santibáñez del Val", "Santibáñez el Alto", "Santibáñez el Bajo", "Santillana del Mar", "Santiponce", "Santiso", "Santisteban del Puerto", "Santiurde de Reinosa", "Santiurde de Toranzo", "Santiuste", "Santiuste de Pedraza", "Santiuste de San Juan Bautista", "Santiz", "Santo Adriano", "Santo Domingo de la Calzada", "Santo Domingo de las Posadas", "Santo Domingo de Pirón", "Santo Domingo de Silos", "Santo Domingo-Caudilla", "Santo Tomé", "Santo Tomé de Zabarcos", "Santo Tomé del Puerto", "Santomera", "Santoña", "Santorcaz", "Santos de la Humosa, Los", "Santos de Maimona, Los", "Santos, Los", "Santovenia", "Santovenia de la Valdoncina", "Santovenia de Pisuerga", "Santoyo", "Santpedor", "Santurde de Rioja", "Santurdejo", "Santurtzi", "Sanxenxo", "Sanzoles", "Sardón de Duero", "Sardón de los Frailes", "Sargentes de la Lora", "Sariego", "Sariegos", "Sariñena", "Saro", "Sarracín", "Sarral", "Sarratella", "Sarreaus", "Sarria", "Sarrià de Ter", "Sarriés/Sartze", "Sarrión", "Sarroca de Bellera", "Sarroca de Lleida", "Sartaguda", "Sartajada", "Sasamón", "Sástago", "Saúca", "Saucedilla", "Saucejo, El", "Saucelle", "Sauquillo de Cabezas", "Saus, Camallera i Llampaies", "Sauzal, El", "Savallà del Comtat", "Saviñao, O", "Sax", "Sayalonga", "Sayatón", "Sebúlcor", "Seca, La", "Secastilla", "Secuita, La", "Sedaví", "Sedella", "Sediles", "Segart", "Segorbe", "Segovia", "Segura", "Segura de la Sierra", "Segura de León", "Segura de los Baños", "Segura de Toro", "Segurilla", "Seira", "Selas", "Selaya", "Sella", "Sellent", "Selva", "Selva de Mar, La", "Selva del Camp, La", "Semillas", "Sempere", "Sena", "Sena de Luna", "Senan", "Sencelles", "Senés", "Senés de Alcubierre", "Sénia, La", "Senija", "Seno", "Senterada", "Sentiu de Sió, La", "Sentmenat", "Senyera", "Sepulcro-Hilario", "Sepúlveda", "Sequera de Fresno", "Sequera de Haza, La", "Sequeros", "Serinyà", "Serna del Monte, La", "Serna, La", "Serón", "Serón de Nágima", "Seròs", "Serra", "Serra de Daró", "Serrada", "Serrada, La", "Serradilla", "Serradilla del Arroyo", "Serradilla del Llano", "Serranillos", "Serranillos del Valle", "Serrejón", "Sesa", "Seseña", "Sesma", "Sestao", "Sestrica", "Sesué", "Setcases", "Setenil de las Bodegas", "Setiles", "Seu dUrgell, La", "Seva", "Sevilla", "Sevilla la Nueva", "Sevilleja de la Jara", "Sidamon", "Sienes", "Siero", "Sierpe, La", "Sierra de Fuentes", "Sierra de Luna", "Sierra de Yeguas", "Sierra Engarcerán", "Sierro", "Siétamo", "Siete Aguas", "Siete Iglesias de Trabancos", "Sieteiglesias de Tormes", "Sigeres", "Sigüenza", "Sigüés", "Siles", "Silla", "Silleda", "Silos, Los", "Sils", "Simancas", "Simat de la Valldigna", "Sinarcas", "Sineu", "Singra", "Sinlabajos", "Siruela", "Sisamón", "Sisante", "Sitges", "Siurana", "Soba", "Sober", "Sobradiel", "Sobradillo", "Sobrado", "Sobrado", "Sobremunt", "Sobrescobio", "Socovos", "Socuéllamos", "Sojuela", "Solana de Ávila", "Solana de los Barros", "Solana de Rioalmar", "Solana del Pino", "Solana, La", "Solanillos del Extremo", "Solarana", "Solera de Gabaldón", "Soleràs, El", "Soliedra", "Solivella", "Sollana", "Sóller", "Solórzano", "Solosancho", "Solsona", "Somiedo", "Somolinos", "Somontín", "Somosierra", "Somozas, As", "Son Servera", "Sondika", "Soneja", "Sonseca", "Sopeira", "Sopelana", "Soportújar", "Sopuerta", "Sora", "Soraluze-Placencia de las Armas", "Sorbas", "Sordillos", "Soria", "Soriguera", "Sorihuela", "Sorihuela del Guadalimar", "Sorlada", "Sort", "Sorvilán", "Sorzano", "Sos del Rey Católico", "Soses", "Sot de Chera", "Sot de Ferrer", "Sotalbo", "Sotés", "Sotillo", "Sotillo de la Adrada", "Sotillo de la Ribera", "Sotillo de las Palomas", "Sotillo del Rincón", "Sotillo, El", "Soto de Cerrato", "Soto de la Vega", "Soto del Barco", "Soto del Real", "Soto en Cameros", "Soto y Amío", "Sotobañado y Priorato", "Sotodosos", "Sotonera, La", "Sotorribas", "Sotosalbos", "Sotoserrano", "Sotragero", "Sotresgudo", "Soutomaior", "Suances", "Subirats", "Sudanell", "Sueca", "Suellacabras", "Sueras/Suera", "Suflí", "Sukarrieta", "Sumacàrcer", "Sunbilla", "Sunyer", "Súria", "Susinos del Páramo", "Susqueda"}));
        }
        if (jComboBox_filtroAZ.getSelectedItem().toString().equals("De la T a la Z")) {
            jComboBox_poblacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Tabanera de Cerrato", "Tabanera de Valdavia", "Tabanera la Luenga", "Tábara", "Tabera de Abajo", "Tabernas", "Taberno", "Taboada", "Taboadela", "Tabuenca", "Tacoronte", "Tafalla", "Tagamanent", "Taha, La", "Tahal", "Tajahuerce", "Tajueco", "Tala, La", "Talamanca", "Talamanca de Jarama", "Talamantes", "Talarn", "Talarrubias", "Talaván", "Talavera", "Talavera de la Reina", "Talavera la Real", "Talaveruela de la Vera", "Talayuela", "Talayuelas", "Tales", "Táliga", "Tallada dEmpordà, La", "Talveila", "Tamajón", "Tamames", "Támara de Campos", "Tamarite de Litera", "Tamariz de Campos", "Tamarón", "Tamurejo", "Tanque, El", "Tapia de Casariego", "Tapioles", "Taradell", "Taragudo", "Taramundi", "Tarancón", "Taravilla", "Tarazona", "Tarazona de Guareña", "Tarazona de la Mancha", "Tàrbena", "Tardáguila", "Tardajos", "Tardelcuende", "Tardienta", "Tariego de Cerrato", "Tarifa", "Taroda", "Tarragona", "Tàrrega", "Tarrés", "Tarroja de Segarra", "Tartanedo", "Tauste", "Tavernes Blanques", "Tavernes de la Valldigna", "Tavèrnoles", "Tavertet", "Tazacorte", "Teba", "Tébar", "Tegueste", "Teguise", "Teià", "Teixeira, A", "Tejada", "Tejadillos", "Tejado", "Tejado, El", "Tejeda", "Tejeda de Tiétar", "Tejeda y Segoyuela", "Telde", "Tella-Sin", "Tembleque", "Tendilla", "Tenebrón", "Teo", "Teresa", "Teresa de Cofrentes", "Térmens", "Teror", "Terque", "Terrades", "Terradillos", "Terradillos de Esgueva", "Terrassa", "Terrateig", "Terrer", "Terriente", "Terrinches", "Terroba", "Teruel", "Terzaga", "Teulada", "Teverga", "Tiana", "Tías", "Tibi", "Tiebas-Muruarte de Reta", "Tiedra", "Tielmes", "Tiemblo, El", "Tierga", "Tierz", "Tierzo", "Tijarafe", "Tíjola", "Tinajas", "Tinajo", "Tineo", "Tinieblas de la Sierra", "Tiñosillos", "Tirapu", "Tirgo", "Tírig", "Tírvia", "Titaguas", "Titulcia", "Tiurana", "Tivenys", "Tivissa", "Toba, La", "Tobar", "Tobarra", "Tobed", "Tobía", "Toboso, El", "Tocina", "Todolella", "Toén", "Toga", "Tojos, Los", "Tolbaños", "Toledo", "Tollos", "Tolocirio", "Tolosa", "Tolox", "Tolva", "Tomares", "Tomelloso", "Tomiño", "Tona", "Topas", "Toques", "Torà", "Toral de los Guzmanes", "Toral de los Vados", "Torás", "Tordehumos", "Tordellego", "Tordelrábano", "Tordera", "Tordesillas", "Tordesilos", "Tordillos", "Tordoia", "Tordómar", "Torelló", "Toreno", "Torija", "Toril", "Toril y Masegoso", "Torla", "Torlengua", "Tormantos", "Tormellas", "Tormón", "Tormos", "Torms, Els", "Tornabous", "Tornadizo, El", "Tornadizos de Ávila", "Tornavacas", "Torno, El", "Tornos", "Toro", "Toro, El", "Torquemada", "Torralba", "Torralba de Aragón", "Torralba de Calatrava", "Torralba de los Frailes", "Torralba de los Sisones", "Torralba de Oropesa", "Torralba de Ribota", "Torralba del Pinar", "Torralba del Río", "Torralbilla", "Torre Alháquime", "Torre de Arcas", "Torre de Cabdella, La", "Torre de Claramunt, La", "Torre de Don Miguel", "Torre de Esgueva", "Torre de Esteban Hambrán, La", "Torre de Fontaubella, La", "Torre de Juan Abad", "Torre de las Arcas", "Torre de lEspanyol, La", "Torre de Miguel Sesmero", "Torre de Peñafiel", "Torre de Santa María", "Torre del Bierzo", "Torre del Burgo", "Torre del Campo", "Torre del Compte", "Torre del Valle, La", "Torre dEn Besora, la", "Torre den Doménec, la", "Torre en Cameros", "Torre la Ribera", "Torre los Negros", "Torre Val de San Pedro", "Torre-Cardela", "Torre-Pacheco", "Torre-serona", "Torre, La", "Torreadrada", "Torrebaja", "Torrebesses", "Torreblacos", "Torreblanca", "Torreblascopedro", "Torrecaballeros", "Torrecampo", "Torrechiva", "Torrecilla de Alcañiz", "Torrecilla de la Abadesa", "Torrecilla de la Jara", "Torrecilla de la Orden", "Torrecilla de la Torre", "Torrecilla de los Ángeles", "Torrecilla del Monte", "Torrecilla del Pinar", "Torrecilla del Rebollar", "Torrecilla en Cameros", "Torrecilla sobre Alesanco", "Torrecillas de la Tiesa", "Torrecuadrada de Molina", "Torrecuadradilla", "Torredembarra", "Torredonjimeno", "Torrefarrera", "Torrefeta i Florejacs", "Torregalindo", "Torregamones", "Torregrossa", "Torrehermosa", "Torreiglesias", "Torrejón de Ardoz", "Torrejón de la Calzada", "Torrejón de Velasco", "Torrejón del Rey", "Torrejón el Rubio", "Torrejoncillo", "Torrejoncillo del Rey", "Torrelacárcel", "Torrelaguna", "Torrelameu", "Torrelapaja", "Torrelara", "Torrelavega", "Torrelavit", "Torrella", "Torrellas", "Torrelles de Foix", "Torrelles de Llobregat", "Torrelobatón", "Torrelodones", "Torremanzanas", "Torremayor", "Torremejía", "Torremenga", "Torremocha", "Torremocha de Jadraque", "Torremocha de Jarama", "Torremocha de Jiloca", "Torremocha del Campo", "Torremocha del Pinar", "Torremochuela", "Torremolinos", "Torremontalbo", "Torremormojón", "Torrent", "Torrent", "Torrente de Cinca", "Torrenueva", "Torreorgaz", "Torrepadre", "Torreperogil", "Torrequemada", "Torres", "Torres de Albánchez", "Torres de Albarracín", "Torres de Alcanadre", "Torres de Barbués", "Torres de Berrellén", "Torres de Cotillas, Las", "Torres de la Alameda", "Torres de Segre", "Torres del Carrizal", "Torres del Río", "Torres Torres", "Torresandino", "Torrescárcela", "Torresmenudas", "Torrevelilla", "Torrevieja", "Torrico", "Torrijas", "Torrijo de la Cañada", "Torrijo del Campo", "Torrijos", "Torroella de Fluvià", "Torroella de Montgrí", "Torroja del Priorat", "Torrox", "Torrubia", "Torrubia de Soria", "Torrubia del Campo", "Torrubia del Castillo", "Tortellà", "Tórtola de Henares", "Tórtoles", "Tórtoles de Esgueva", "Tortosa", "Tortuera", "Tortuero", "Torvizcón", "Tosantos", "Toses", "Tosos", "Tossa de Mar", "Totalán", "Totana", "Totanés", "Touro", "Tous", "Trabada", "Trabadelo", "Trabanca", "Trabazos", "Tragacete", "Traíd", "Traiguera", "Tramacastiel", "Tramacastilla", "Tramaced", "Trasierra", "Trasmiras", "Trasmoz", "Trasobares", "Traspinedo", "Trazo", "Trebujena", "Trefacio", "Tremedal de Tormes", "Tremp", "Tres Cantos", "Tres Villas, Las", "Trescasas", "Tresjuncos", "Trespaderne", "Tresviso", "Trévago", "Trevélez", "Treviana", "Triacastela", "Tribaldos", "Tricio", "Trigueros", "Trigueros del Valle", "Trijueque", "Trillo", "Triollo", "Tronchón", "Truchas", "Trucios-Turtzioz", "Trujillanos", "Trujillo", "Tubilla del Agua", "Tubilla del Lago", "Tudanca", "Tudela", "Tudela de Duero", "Tudelilla", "Tuéjar", "Tui", "Tuineje", "Tulebras", "Turcia", "Turégano", "Turís", "Turleque", "Turón", "Turre", "Turrillas", "Úbeda", "Ubide", "Ubrique", "Ucar", "Uceda", "Ucero", "Uclés", "Udías", "Ugao-Miraballes", "Ugena", "Ugíjar", "Uharte-Arakil", "Ujados", "Ujué", "Ulea", "Uleila del Campo", "Ullà", "Ullastrell", "Ullastret", "Ulldecona", "Ulldemolins", "Ultramort", "Ultzama", "Umbrete", "Umbrías", "Uncastillo", "Unciti", "Undués de Lerda", "Unión de Campos, La", "Unión, La", "Unzué/Untzue", "Uña", "Uña de Quintana", "Úrbel del Castillo", "Urda", "Urdazubi/Urdax", "Urdiain", "Urdiales del Páramo", "Urduliz", "Urduña/Orduña", "Urkabustaiz", "Urnieta", "Urones de Castroponce", "Urrácal", "Urraul Alto", "Urraul Bajo", "Urrea de Gaén", "Urrea de Jalón", "Urretxu", "Urriés", "Urrotz", "Urroz-Villa", "Urueña", "Urueñas", "Uruñuela", "Urús", "Urzainqui/Urzainki", "Usagre", "Used", "Useras/Useres, les", "Usurbil", "Utande", "Utebo", "Uterga", "Utiel", "Utrera", "Utrillas", "Uztárroz/Uztarroze", "Vacarisses", "Vadillo", "Vadillo de la Guareña", "Vadillo de la Sierra", "Vadocondes", "Vajol, La", "Val de San Lorenzo", "Val de San Martín", "Val de San Vicente", "Val do Dubra", "Valacloche", "Valadouro, O", "Valbona", "Valbuena de Duero", "Valbuena de Pisuerga", "Valcabado", "Valdáliga", "Valdaracete", "Valdarachas", "Valdastillas", "Valde-Ucieza", "Valdealgorfa", "Valdeande", "Valdearcos de la Vega", "Valdearenas", "Valdeavellano", "Valdeavellano de Tera", "Valdeavero", "Valdeaveruelo", "Valdecaballeros", "Valdecañas de Tajo", "Valdecarros", "Valdecasa", "Valdecolmenas, Los", "Valdeconcha", "Valdecuenca", "Valdefinjas", "Valdefresno", "Valdefuentes", "Valdefuentes de Sangusín", "Valdefuentes del Páramo", "Valdeganga", "Valdegeña", "Valdegovía/Gaubea", "Valdegrudas", "Valdehijaderos", "Valdehorna", "Valdehúncar", "Valdelacalzada", "Valdelacasa", "Valdelacasa de Tajo", "Valdelageve", "Valdelagua del Cerro", "Valdelaguna", "Valdelarco", "Valdelcubo", "Valdelinares", "Valdelosa", "Valdeltormo", "Valdelugueros", "Valdemadera", "Valdemaluque", "Valdemanco", "Valdemanco del Esteras", "Valdemaqueda", "Valdemeca", "Valdemierque", "Valdemora", "Valdemorales", "Valdemorillo", "Valdemorillo de la Sierra", "Valdemoro", "Valdemoro-Sierra", "Valdenebro", "Valdenebro de los Valles", "Valdenuño Fernández", "Valdeobispo", "Valdeolea", "Valdeolivas", "Valdeolmillos", "Valdeolmos-Alalpardo", "Valdepeñas", "Valdepeñas de Jaén", "Valdepeñas de la Sierra", "Valdepiélago", "Valdepiélagos", "Valdepolo", "Valdeprado", "Valdeprado del Río", "Valdeprados", "Valderas", "Valderrábano", "Valderrebollo", "Valderredible", "Valderrey", "Valderrobres", "Valderrodilla", "Valderrodrigo", "Valderrueda", "Valdés", "Valdesamario", "Valdescorriel", "Valdesotos", "Valdestillas", "Valdetorres", "Valdetorres de Jarama", "Valdetórtola", "Valdevacas de Montejo", "Valdevacas y Guijar", "Valdeverdeja", "Valdevimbre", "Valdezate", "Valdilecha", "Valdorros", "Valdoviño", "Valdunciel", "Valdunquillo", "Valencia", "Valencia de Alcántara", "Valencia de Don Juan", "Valencia de las Torres", "Valencia del Mombuey", "Valencia del Ventoso", "Valencina de la Concepción", "Valenzuela", "Valenzuela de Calatrava", "Valeras, Las", "Valero", "Valfarta", "Valfermoso de Tajuña", "Valga", "Valgañón", "Valhermoso", "Valhermoso de la Fuente", "Valjunquera", "Vall dAlba", "Vall dAlcalà, la", "Vall de Almonacid", "Vall de Bianya, La", "Vall de Boí, La", "Vall de Cardós", "Vall de Gallinera", "Vall de Laguar, la", "Vall dEbo, la", "Vall den Bas, La", "Vall dUixó, la", "Vall-llobrega", "Vallada", "Valladolid", "Vallanca", "Vallarta de Bureba", "Vallat", "Vallbona dAnoia", "Vallbona de les Monges", "Vallcebre", "Vallclara", "Valldemossa", "Valle de Abdalajís", "Valle de Altomira, El", "Valle de Bardají", "Valle de Cerrato", "Valle de Hecho", "Valle de la Serena", "Valle de las Navas", "Valle de Lierp", "Valle de Losa", "Valle de Manzanedo", "Valle de Matamoros", "Valle de Mena", "Valle de Oca", "Valle de Santa Ana", "Valle de Santibáñez", "Valle de Sedano", "Valle de Tabladillo", "Valle de Tobalina", "Valle de Trápaga-Trapagaran", "Valle de Valdebezana", "Valle de Valdelaguna", "Valle de Valdelucio", "Valle de Villaverde", "Valle de Yerri/Deierri", "Valle de Zamanzas", "Valle del Retortillo", "Valle del Zalabí", "Valle Gran Rey", "Valle, El", "Vallecillo", "Vallecillo, El", "Vallehermoso", "Vallejera", "Vallejera de Riofrío", "Vallelado", "Valleruela de Pedraza", "Valleruela de Sepúlveda", "Vallés", "Valles de Palenzuela", "Vallesa de la Guareña", "Valleseco", "Vallfogona de Balaguer", "Vallfogona de Ripollès", "Vallfogona de Riucorb", "Vallgorguina", "Vallibona", "Vallirana", "Vallmoll", "Vallromanes", "Valls", "Valls dAguilar, Les", "Valls de Valira, Les", "Valluércanes", "Valmadrid", "Valmala", "Valmojado", "Válor", "Valoria la Buena", "Valpalmas", "Valsalabroso", "Valsalobre", "Valseca", "Valsequillo", "Valsequillo de Gran Canaria", "Valtablado del Río", "Valtajeros", "Valtiendas", "Valtierra", "Valtorres", "Valverde", "Valverde de Alcalá", "Valverde de Burguillos", "Valverde de Campos", "Valverde de Júcar", "Valverde de la Vera", "Valverde de la Virgen", "Valverde de Leganés", "Valverde de Llerena", "Valverde de los Arroyos", "Valverde de Mérida", "Valverde de Valdelacasa", "Valverde del Camino", "Valverde del Fresno", "Valverde del Majano", "Valverde-Enrique", "Valverdejo", "Valverdón", "Vandellòs i lHospitalet de lInfant", "Vansa i Fórnols, La", "Vara de Rey", "Veciana", "Vecilla, La", "Vecinos", "Vedra", "Vega de Espinareda", "Vega de Infanzones", "Vega de Liébana", "Vega de Pas", "Vega de Ruiponce", "Vega de San Mateo", "Vega de Santa María", "Vega de Tera", "Vega de Tirados", "Vega de Valcarce", "Vega de Valdetronco", "Vega de Villalobos", "Vega del Codorno", "Vegacervera", "Vegadeo", "Vegalatrave", "Veganzones", "Vegaquemada", "Vegas de Matute", "Vegas del Condado", "Vegas del Genil", "Vegaviana", "Veguillas de la Sierra", "Veguillas, Las", "Veiga, A", "Vejer de la Frontera", "Velada", "Velamazán", "Velascálvaro", "Velayos", "Velefique", "Vélez de Benaudalla", "Vélez-Blanco", "Vélez-Málaga", "Vélez-Rubio", "Velilla", "Velilla de Cinca", "Velilla de Ebro", "Velilla de Jiloca", "Velilla de la Sierra", "Velilla de los Ajos", "Velilla de San Antonio", "Velilla del Río Carrión", "Vellés, La", "Vellisca", "Velliza", "Vellón, El", "Vencillón", "Vendrell, El", "Venialbo", "Venta de Baños", "Venta del Moro", "Ventalló", "Ventas con Peña Aguilera, Las", "Ventas de Huelma", "Ventas de Retamosa, Las", "Ventas de San Julián, Las", "Ventosa", "Ventosa de la Cuesta", "Ventosa del Río Almar", "Ventosilla y Tejadilla", "Ventrosa", "Venturada", "Vera", "Vera de Moncayo", "Verdú", "Verea", "Verger, el", "Verges", "Verín", "Vertavillo", "Vespella de Gaià", "Vezdemarbán", "Viacamp y Litera", "Viana", "Viana de Cega", "Viana de Duero", "Viana de Jadraque", "Viana do Bolo", "Viandar de la Vera", "Vianos", "Viator", "Vic", "Vícar", "Vicedo, O", "Vicién", "Victoria de Acentejo, La", "Victoria, La", "Vid de Bureba, La", "Vid de Ojeda, La", "Vid y Barrios, La", "Vidángoz/Bidankoze", "Vidayanes", "Videmala", "Vídola, La", "Vidrà", "Vidreres", "Vielha e Mijaran", "Vierlas", "Vigo", "Viguera", "Vila de Cruces", "Vila-real", "Vila-rodona", "Vila-sacra", "Vila-sana", "Vila-seca", "Vilabella", "Vilabertran", "Vilablareix", "Vilaboa", "Vilada", "Viladamat", "Viladasens", "Viladecans", "Viladecavalls", "Vilademuls", "Viladrau", "Vilafamés", "Vilafant", "Vilaflor", "Vilafranca de Bonany", "Vilafranca del Penedès", "Vilagarcía de Arousa", "Vilagrassa", "Vilajuïga", "Vilalba", "Vilalba dels Arcs", "Vilalba Sasserra", "Vilaller", "Vilallonga de Ter", "Vilallonga del Camp", "Vilamacolum", "Vilamalla", "Vilamaniscle", "Vilamarín", "Vilamartín de Valdeorras", "Vilamarxant", "Vilamòs", "Vilanant", "Vilanova dAlcolea", "Vilanova de Arousa", "Vilanova de Bellpuig", "Vilanova de la Barca", "Vilanova de lAguda", "Vilanova de Meià", "Vilanova de Prades", "Vilanova de Sau", "Vilanova de Segrià", "Vilanova del Camí", "Vilanova del Vallès", "Vilanova dEscornalbou", "Vilanova i la Geltrú", "Vilaplana", "Vilar de Barrio", "Vilar de Canes", "Vilar de Santos", "Vilardevós", "Vilariño de Conso", "Vilarmaior", "Vilasantar", "Vilassar de Dalt", "Vilassar de Mar", "Vilaür", "Vilavella, la", "Vilaverd", "Vilches", "Vilella Alta, La", "Vilella Baixa, La", "Vileña", "Villa de Don Fadrique, La", "Villa de Mazo", "Villa de Ves", "Villa del Campo", "Villa del Prado", "Villa del Rey", "Villa del Río", "Villabáñez", "Villabaruz de Campos", "Villabasta de Valdavia", "Villablanca", "Villablino", "Villabona", "Villabrágima", "Villabraz", "Villabrázaro", "Villabuena de Álava/Eskuernaga", "Villabuena del Puente", "Villacañas", "Villacarralón", "Villacarriedo", "Villacarrillo", "Villacastín", "Villacid de Campos", "Villacidaler", "Villaciervos", "Villaco", "Villaconancio", "Villaconejos", "Villaconejos de Trabaque", "Villada", "Villadangos del Páramo", "Villademor de la Vega", "Villadepera", "Villadiego", "Villadoz", "Villaeles de Valdavia", "Villaescusa", "Villaescusa", "Villaescusa de Haro", "Villaescusa de Roa", "Villaescusa la Sombría", "Villaespasa", "Villafáfila", "Villafeliche", "Villaferrueña", "Villaflor", "Villaflores", "Villafrades de Campos", "Villafranca", "Villafranca de Córdoba", "Villafranca de Duero", "Villafranca de Ebro", "Villafranca de la Sierra", "Villafranca de los Barros", "Villafranca de los Caballeros", "Villafranca del Bierzo", "Villafranca del Campo", "Villafranca del Cid/Vilafranca", "Villafranca Montes de Oca", "Villafrechós", "Villafruela", "Villafuerte", "Villafufre", "Villagalijo", "Villagarcía de Campos", "Villagarcía de la Torre", "Villagarcía del Llano", "Villagatón", "Villageriz", "Villagómez la Nueva", "Villagonzalo", "Villagonzalo de Tormes", "Villagonzalo Pedernales", "Villahán", "Villaharta", "Villahermosa", "Villahermosa del Campo", "Villahermosa del Río", "Villaherreros", "Villahoz", "Villajoyosa/Vila Joiosa, la", "Villalaco", "Villalán de Campos", "Villalar de los Comuneros", "Villalazán", "Villalba de Duero", "Villalba de Guardo", "Villalba de la Lampreana", "Villalba de la Loma", "Villalba de la Sierra", "Villalba de los Alcores", "Villalba de los Barros", "Villalba de los Llanos", "Villalba de Perejil", "Villalba de Rioja", "Villalba del Alcor", "Villalba del Rey", "Villalbarba", "Villalbilla", "Villalbilla de Burgos", "Villalbilla de Gumiel", "Villalcampo", "Villalcázar de Sirga", "Villalcón", "Villaldemiro", "Villalengua", "Villalgordo del Júcar", "Villalgordo del Marquesado", "Villalmanzo", "Villalobar de Rioja", "Villalobón", "Villalobos", "Villalón de Campos", "Villalonga", "Villalonso", "Villalpando", "Villalpardo", "Villalube", "Villaluenga de la Sagra", "Villaluenga de la Vega", "Villaluenga del Rosario", "Villamalea", "Villamalur", "Villamandos", "Villamanín", "Villamanrique", "Villamanrique de la Condesa", "Villamanrique de Tajo", "Villamanta", "Villamantilla", "Villamañán", "Villamartín", "Villamartín de Campos", "Villamartín de Don Sancho", "Villamayor", "Villamayor de Calatrava", "Villamayor de Campos", "Villamayor de Gállego", "Villamayor de los Montes", "Villamayor de Monjardín", "Villamayor de Santiago", "Villamayor de Treviño", "Villambistia", "Villamediana", "Villamediana de Iregua", "Villamedianilla", "Villamejil", "Villamena", "Villameriel", "Villamesías", "Villamiel", "Villamiel de la Sierra", "Villamiel de Toledo", "Villaminaya", "Villamol", "Villamontán de la Valduerna", "Villamor de los Escuderos", "Villamoratiel de las Matas", "Villamoronta", "Villamuelas", "Villamuera de la Cueza", "Villamuriel de Campos", "Villamuriel de Cerrato", "Villán de Tordesillas", "Villanázar", "Villangómez", "Villanova", "Villanúa", "Villanubla", "Villanueva de Alcardete", "Villanueva de Alcorón", "Villanueva de Algaidas", "Villanueva de Argaño", "Villanueva de Argecilla", "Villanueva de Ávila", "Villanueva de Azoague", "Villanueva de Bogas", "Villanueva de Cameros", "Villanueva de Campeán", "Villanueva de Carazo", "Villanueva de Castellón", "Villanueva de Córdoba", "Villanueva de Duero", "Villanueva de Gállego", "Villanueva de Gómez", "Villanueva de Gormaz", "Villanueva de Guadamejud", "Villanueva de Gumiel", "Villanueva de Huerva", "Villanueva de Jiloca", "Villanueva de la Cañada", "Villanueva de la Concepción", "Villanueva de la Condesa", "Villanueva de la Fuente", "Villanueva de la Jara", "Villanueva de la Reina", "Villanueva de la Serena", "Villanueva de la Sierra", "Villanueva de la Torre", "Villanueva de la Vera", "Villanueva de las Cruces", "Villanueva de las Manzanas", "Villanueva de las Peras", "Villanueva de las Torres", "Villanueva de los Caballeros", "Villanueva de los Castillejos", "Villanueva de los Infantes", "Villanueva de los Infantes", "Villanueva de Oscos", "Villanueva de Perales", "Villanueva de San Carlos", "Villanueva de San Juan", "Villanueva de San Mancio", "Villanueva de Sigena", "Villanueva de Tapia", "Villanueva de Teba", "Villanueva de Viver", "Villanueva del Aceral", "Villanueva del Ariscal", "Villanueva del Arzobispo", "Villanueva del Campillo", "Villanueva del Campo", "Villanueva del Conde", "Villanueva del Duque", "Villanueva del Fresno", "Villanueva del Pardillo", "Villanueva del Rebollar", "Villanueva del Rebollar de la Sierra", "Villanueva del Rey", "Villanueva del Río Segura", "Villanueva del Río y Minas", "Villanueva del Rosario", "Villanueva del Trabuco", "Villanueva Mesía", "Villanuño de Valdavia", "Villaobispo de Otero", "Villaornate y Castro", "Villapalacios", "Villaprovedo", "Villaquejida", "Villaquilambre", "Villaquirán de la Puebla", "Villaquirán de los Infantes", "Villar de Argañán", "Villar de Arnedo, El", "Villar de Cañas", "Villar de Ciervo", "Villar de Corneja", "Villar de Domingo García", "Villar de Fallaves", "Villar de Gallimazo", "Villar de la Encina", "Villar de la Yegua", "Villar de los Navarros", "Villar de Olalla", "Villar de Peralonso", "Villar de Plasencia", "Villar de Rena", "Villar de Samaniego", "Villar de Torre", "Villar del Ala", "Villar del Arzobispo", "Villar del Buey", "Villar del Campo", "Villar del Cobo", "Villar del Humo", "Villar del Infantado", "Villar del Olmo", "Villar del Pedroso", "Villar del Pozo", "Villar del Rey", "Villar del Río", "Villar del Salz", "Villar y Velasco", "Villaralbo", "Villaralto", "Villarcayo de Merindad de Castilla la Vieja", "Villardeciervos", "Villardefrades", "Villardiegua de la Ribera", "Villárdiga", "Villardompardo", "Villardondiego", "Villarejo", "Villarejo de Fuentes", "Villarejo de la Peñuela", "Villarejo de Montalbán", "Villarejo de Órbigo", "Villarejo de Salvanés", "Villarejo del Valle", "Villarejo-Periesteban", "Villares de Jadraque", "Villares de la Reina", "Villares de Órbigo", "Villares de Soria, Los", "Villares de Yeltes", "Villares del Saz", "Villares, Los", "Villargordo del Cabriel", "Villariezo", "Villarino de los Aires", "Villarluengo", "Villarmayor", "Villarmentero de Campos", "Villarmentero de Esgueva", "Villarmuerto", "Villarquemado", "Villarrabé", "Villarramiel", "Villarrasa", "Villarreal de Huerva", "Villarrín de Campos", "Villarrobledo", "Villarrodrigo", "Villarroya", "Villarroya de la Sierra", "Villarroya de los Pinares", "Villarroya del Campo", "Villarrubia de los Ojos", "Villarrubia de Santiago", "Villarrubio", "Villarta", "Villarta de los Montes", "Villarta de San Juan", "Villarta-Quintana", "Villas de la Ventosa", "Villasabariego", "Villasandino", "Villasarracino", "Villasayas", "Villasbuenas", "Villasbuenas de Gata", "Villasdardo", "Villaseca de Arciel", "Villaseca de Henares", "Villaseca de la Sagra", "Villaseca de Uceda", "Villaseco de los Gamitos", "Villaseco de los Reyes", "Villaseco del Pan", "Villaselán", "Villasequilla", "Villasexmir", "Villasila de Valdavia", "Villasrubias", "Villastar", "Villasur de Herreros", "Villatobas", "Villatoro", "Villatorres", "Villatoya", "Villatuelda", "Villatuerta", "Villaturde", "Villaturiel", "Villaumbrales", "Villava/Atarrabia", "Villavaliente", "Villavaquerín", "Villavelayo", "Villavellid", "Villavendimio", "Villaverde de Guadalimar", "Villaverde de Guareña", "Villaverde de Íscar", "Villaverde de Medina", "Villaverde de Montejo", "Villaverde de Rioja", "Villaverde del Monte", "Villaverde del Río", "Villaverde y Pasaconsol", "Villaverde-Mogina", "Villaveza de Valverde", "Villaveza del Agua", "Villavicencio de los Caballeros", "Villaviciosa", "Villaviciosa de Córdoba", "Villaviciosa de Odón", "Villavieja de Yeltes", "Villavieja del Lozoya", "Villaviudas", "Villayerno Morquillas", "Villayón", "Villazala", "Villazanzo de Valderaduey", "Villazopeque", "Villegas", "Villeguillo", "Villel", "Villel de Mesa", "Villena", "Villerías de Campos", "Villodre", "Villodrigo", "Villoldo", "Víllora", "Villores", "Villoria", "Villoruebo", "Villoruela", "Villoslada de Cameros", "Villota del Páramo", "Villovieco", "Vilobí del Penedès", "Vilobí dOnyar", "Vilopriu", "Viloria", "Viloria de Rioja", "Vilosell, El", "Vilueña, La", "Vilvestre", "Vilviestre del Pinar", "Vimbodí i Poblet", "Vimianzo", "Vinaceite", "Vinaixa", "Vinalesa", "Vinaròs", "Vindel", "Vinebre", "Viniegra de Abajo", "Viniegra de Arriba", "Vinuesa", "Vinyols i els Arcs", "Viñas", "Viñegra de Moraña", "Viñuela", "Viñuelas", "Visiedo", "Viso de San Juan, El", "Viso del Alcor, El", "Viso del Marqués", "Viso, El", "Vistabella", "Vistabella del Maestrazgo", "Vita", "Vitigudino", "Vitoria-Gasteiz", "Viveiro", "Vivel del Río Martín", "Viver", "Viver i Serrateix", "Viveros", "Vizcaínos", "Vizmanos", "Víznar", "Voto", "Vozmediano", "Wamba", "Xaló", "Xàtiva", "Xeraco", "Xeresa", "Xermade", "Xerta", "Xinzo de Limia", "Xirivella", "Xove", "Xunqueira de Ambía", "Xunqueira de Espadanedo", "Yaiza", "Yanguas", "Yanguas de Eresma", "Yátova", "Yébenes, Los", "Yebes", "Yebra", "Yebra de Basa", "Yecla", "Yecla de Yeltes", "Yécora/Iekora", "Yélamos de Abajo", "Yélamos de Arriba", "Yeles", "Yelo", "Yémeda", "Yepes", "Yernes y Tameza", "Yesa", "Yesa, La", "Yésero", "Yeste", "Yuncler", "Yunclillos", "Yuncos", "Yunquera", "Yunquera de Henares", "Yunta, La", "Zabalza/Zabaltza", "Zael", "Zafarraya", "Zafra", "Zafra de Záncara", "Zafrilla", "Zagra", "Zahara", "Zahínos", "Zaida, La", "Zaidín", "Zalamea de la Serena", "Zalamea la Real", "Zaldibar", "Zaldibia", "Zalduondo", "Zalla", "Zamarra", "Zamayón", "Zambrana", "Zamora", "Zamudio", "Zaorejas", "Zapardiel de la Cañada", "Zapardiel de la Ribera", "Zaragoza", "Zarapicos", "Zaratamo", "Zaratán", "Zarautz", "Zarra", "Zarratón", "Zarza de Granadilla", "Zarza de Montánchez", "Zarza de Pumareda, La", "Zarza de Tajo", "Zarza la Mayor", "Zarza-Capilla", "Zarza, La", "Zarza, La", "Zarzalejo", "Zarzosa", "Zarzosa de Río Pisuerga", "Zarzuela", "Zarzuela de Jadraque", "Zarzuela del Monte", "Zarzuela del Pinar", "Zas", "Zazuar", "Zeanuri", "Zeberio", "Zegama", "Zerain", "Zestoa", "Zierbena", "Zigoitia", "Ziordia", "Ziortza-Bolibar", "Zizur Mayor/Zizur Nagusia", "Zizurkil", "Zoma, La", "Zorita", "Zorita de la Frontera", "Zorita de los Canes", "Zorita del Maestrazgo", "Zorraquín", "Zotes del Páramo", "Zubia, La", "Zubieta", "Zucaina", "Zuera", "Zufre", "Zugarramurdi", "Zuheros", "Zuia", "Zújar", "Zumaia", "Zumarraga", "Zuñeda", "Zúñiga", "Zurgena"}));
        }
    }//GEN-LAST:event_jComboBox_filtroAZActionPerformed

    private void jComboBox_poblacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_poblacionActionPerformed
        // TODO add your handling code here:
        //String poblacion = jComboBox_poblacion.getSelectedItem().toString();
    }//GEN-LAST:event_jComboBox_poblacionActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jComboBox_poblacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{}));
        if (tipoUsuario == 1) {
            switchPanels(jPanel_opcionesAdmin);
        } else if (tipoUsuario == 2) {
            switchPanels(jPanel_opcionesChef);
        } else if (tipoUsuario == 3) {
            switchPanels(jPanel_opcionesCliente);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox_disponibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox_disponibleActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jCheckBox_disponibleActionPerformed

    private void jButton_volverEliminarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_volverEliminarCuentaActionPerformed
        // TODO add your handling code here:
        if (tipoUsuario == 1) {
            switchPanels(jPanel_opcionesAdmin);
        } else if (tipoUsuario == 2) {
            switchPanels(jPanel_opcionesChef);
        } else if (tipoUsuario == 3) {
            switchPanels(jPanel_opcionesCliente);
        }
    }//GEN-LAST:event_jButton_volverEliminarCuentaActionPerformed

    private void jButton_eliminarEliminarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_eliminarEliminarCuentaActionPerformed
        // TODO add your handling code here:        
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/users/delete/user"; //Dirección en Cloud
            //String urlString = "https://localhost:8080/api/users/delete/user"; //Dirección en localhost
            urlString = urlString + "?usernameOrEmail=" + jTextField_usuario.getText();
            //      + "&password=password"; //+jPasswordField_password.getPassword().toString();
            String apiUrl = urlString; //api/http link                                    
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            if (connection.getResponseCode() != 200) {
                if (connection.getResponseCode() == 500) {
                    JOptionPane.showMessageDialog(null, "Usuario no encontrado");
                } else {
                    JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());
                }

            } else {
                //Leyendo la respuesta de la API
                StringBuilder sb = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linea;
                while ((linea = in.readLine()) != null) {
                    sb.append(linea).append("\n");
                }
                in.close();
                JOptionPane.showMessageDialog(null, sb.toString());

            }
            connection.disconnect();

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }//GEN-LAST:event_jButton_eliminarEliminarCuentaActionPerformed

    private void jToggleButton_bajaChefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_bajaChefActionPerformed
        // TODO add your handling code here:
        jToggleButton_bajaChef.setSelected(false); //Se reinicia el botón
        switchPanels(jPanel_eliminarPropiaCuenta); //Se cambia de panel
    }//GEN-LAST:event_jToggleButton_bajaChefActionPerformed

    private void jTextField_editarApellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_editarApellidosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_editarApellidosActionPerformed

    private void jToggleButton_altaNuevaReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_altaNuevaReservaActionPerformed
        // TODO add your handling code here:
        jToggleButton_altaNuevaReserva.setSelected(false);
        jComboBox_filtroAZReserva.setSelectedItem("De la A a la F");
        jComboBox_poblacionReserva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Ababuj", "Abades", "Abadía", "Abadín", "Abadiño", "Abáigar", "Abajas", "Ábalos", "Abaltzisketa", "Abánades", "Abanilla", "Abanto", "Abanto y Ciérvana-Abanto Zierbena", "Abarán", "Abarca de Campos", "Abárzuza/Abartzuza", "Abaurregaina/Abaurrea Alta", "Abaurrepea/Abaurrea Baja", "Abegondo", "Abejar", "Abejuela", "Abella de la Conca", "Abengibre", "Abenójar", "Aberin", "Abertura", "Abezames", "Abia de la Obispalía", "Abia de las Torres", "Abiego", "Abizanda", "Abla", "Ablanque", "Ablitas", "Abrera", "Abrucena", "Abusejo", "Acebeda, La", "Acebedo", "Acebo", "Acebrón, El", "Acedera", "Acehúche", "Aceituna", "Acered", "Aceuchal", "Adahuesca", "Adalia", "Adamuz", "Adanero", "Adeje", "Ademuz", "Adiós", "Adobes", "Ador", "Adra", "Adrada de Haza", "Adrada de Pirón", "Adrada, La", "Adradas", "Adrados", "Adsubia", "Aduna", "Agaete", "Agallas", "Àger", "Agolada", "Agón", "Agoncillo", "Agost", "Agramunt", "Ágreda", "Agres", "Agrón", "Aguadulce", "Aguarón", "Aguas Cándidas", "Aguasal", "Aguatón", "Aguaviva", "Agudo", "Agüero", "Aguilafuente", "Aguilar de Bureba", "Aguilar de Campoo", "Aguilar de Campos", "Aguilar de Codés", "Aguilar de la Frontera", "Aguilar de Segarra", "Aguilar del Alfambra", "Aguilar del Río Alhama", "Águilas", "Aguilón", "Agüimes", "Agullana", "Agullent", "Agulo", "Ahigal", "Ahigal de los Aceiteros", "Ahigal de Villarino", "Ahillones", "Aia", "Aibar/Oibar", "Aielo de Malferit", "Aielo de Rugat", "Aiguafreda", "Aiguamúrcia", "Aiguaviva", "Aigües", "Aín", "Aínsa-Sobrarbe", "Ainzón", "Aisa", "Aitona", "Aizarnazabal", "Ajalvir", "Ajamil de Cameros", "Ajangiz", "Ajofrín", "Alacant/Alicante", "Alacón", "Aladrén", "Alaejos", "Alagón", "Alagón del Río", "Alaior", "Alájar", "Alajeró", "Alameda", "Alameda de Gardón, La", "Alameda de la Sagra", "Alameda del Valle", "Alamedilla", "Alamedilla, La", "Alamillo", "Alaminos", "Álamo, El", "Alamús, Els", "Alange", "Alanís", "Alaquàs", "Alar del Rey", "Alaraz", "Alarba", "Alarcón", "Alarilla", "Alaró", "Alàs i Cerc", "Alatoz", "Alba", "Alba de Cerrato", "Alba de Tormes", "Alba de Yeltes", "Albacete", "Albagés, L", "Albaida", "Albaida del Aljarafe", "Albal", "Albalá", "Albaladejo", "Albaladejo del Cuende", "Albalat de la Ribera", "Albalat dels Sorells", "Albalat dels Tarongers", "Albalate de Cinca", "Albalate de las Nogueras", "Albalate de Zorita", "Albalate del Arzobispo", "Albalatillo", "Albánchez", "Albanchez de Mágina", "Albanyà", "Albares", "Albarracín", "Albarreal de Tajo", "Albatana", "Albatàrrec", "Albatera", "Albelda", "Albelda de Iregua", "Albendea", "Albendiego", "Albentosa", "Alberca de Záncara, La", "Alberca, La", "Alberguería de Argañán, La", "Alberic", "Alberite", "Alberite de San Juan", "Albero Alto", "Albero Bajo", "Alberuela de Tubo", "Albesa", "Albeta", "Albi, L", "Albillos", "Albinyana", "Albiol, L", "Albiztur", "Albocàsser", "Alboloduy", "Albolote", "Albondón", "Albons", "Alborache", "Alboraya", "Alborea", "Alborge", "Albornos", "Albox", "Albudeite", "Albuera, La", "Albuixech", "Albuñán", "Albuñol", "Albuñuelas", "Alburquerque", "Alcabón", "Alcadozo", "Alcaine", "Alcalá de Ebro", "Alcalá de Guadaíra", "Alcalá de Gurrea", "Alcalá de Henares", "Alcalá de la Selva", "Alcalá de la Vega", "Alcalá de los Gazules", "Alcalá de Moncayo", "Alcalà de Xivert", "Alcalá del Júcar", "Alcalá del Obispo", "Alcalá del Río", "Alcalá del Valle", "Alcalá la Real", "Alcalalí", "Alcampell", "Alcanadre", "Alcanar", "Alcanó", "Alcántara", "Alcantarilla", "Alcàntera de Xúquer", "Alcantud", "Alcañices", "Alcañiz", "Alcañizo", "Alcaracejos", "Alcaraz", "Alcarràs", "Alcàsser", "Alcaucín", "Alcaudete", "Alcaudete de la Jara", "Alcázar de San Juan", "Alcázar del Rey", "Alcazarén", "Alcázares, Los", "Alcoba", "Alcobendas", "Alcocer", "Alcocer de Planes", "Alcocero de Mola", "Alcohujate", "Alcolea", "Alcolea de Calatrava", "Alcolea de Cinca", "Alcolea de las Peñas", "Alcolea de Tajo", "Alcolea del Pinar", "Alcolea del Río", "Alcoleja", "Alcoletge", "Alcollarín", "Alconaba", "Alconada", "Alconada de Maderuelo", "Alconchel", "Alconchel de Ariza", "Alconchel de la Estrella", "Alconera", "Alcóntar", "Alcora, l", "Alcorcón", "Alcorisa", "Alcoroches", "Alcover", "Alcoy/Alcoi", "Alcubierre", "Alcubilla de Avellaneda", "Alcubilla de las Peñas", "Alcubilla de Nogales", "Alcubillas", "Alcublas", "Alcúdia", "Alcúdia de Crespins, l", "Alcudia de Monteagud", "Alcudia de Veo", "Alcúdia, l", "Alcuéscar", "Aldaia", "Aldea de San Miguel", "Aldea de San Nicolás, La", "Aldea del Cano", "Aldea del Fresno", "Aldea del Obispo", "Aldea del Obispo, La", "Aldea del Rey", "Aldea en Cabo", "Aldea Real", "Aldea, L", "Aldeacentenera", "Aldeacipreste", "Aldeadávila de la Ribera", "Aldealafuente", "Aldealcorvo", "Aldealengua", "Aldealengua de Pedraza", "Aldealengua de Santa María", "Aldealices", "Aldealpozo", "Aldealseñor", "Aldeamayor de San Martín", "Aldeanueva de Barbarroya", "Aldeanueva de Ebro", "Aldeanueva de Figueroa", "Aldeanueva de Guadalajara", "Aldeanueva de la Serrezuela", "Aldeanueva de la Sierra", "Aldeanueva de la Vera", "Aldeanueva de San Bartolomé", "Aldeanueva de Santa Cruz", "Aldeanueva del Camino", "Aldeanueva del Codonal", "Aldeaquemada", "Aldearrodrigo", "Aldearrubia", "Aldeaseca", "Aldeaseca de Alba", "Aldeaseca de la Frontera", "Aldeasoña", "Aldeatejada", "Aldeavieja de Tormes", "Aldehorno", "Aldehuela de Jerte", "Aldehuela de la Bóveda", "Aldehuela de Liestos", "Aldehuela de Periáñez", "Aldehuela de Yeltes", "Aldehuela del Codonal", "Aldehuela, La", "Aldehuelas, Las", "Aldeire", "Aldeonte", "Aldover", "Aledo", "Alegia", "Alegría-Dulantzi", "Aleixar, L", "Alella", "Alentisque", "Alerre", "Alesanco", "Alesón", "Alfacar", "Alfafar", "Alfafara", "Alfajarín", "Alfambra", "Alfamén", "Alfántega", "Alfara de Carles", "Alfara de la Baronia", "Alfara del Patriarca", "Alfaraz de Sayago", "Alfarnate", "Alfarnatejo", "Alfaro", "Alfarp", "Alfarràs", "Alfarrasí", "Alfàs del Pi, l", "Alfauir", "Alfés", "Alfondeguilla", "Alforja", "Alforque", "Alfoz", "Alfoz de Bricia", "Alfoz de Lloredo", "Alfoz de Quintanadueñas", "Alfoz de Santa Gadea", "Algaba, La", "Algadefe", "Algaida", "Algámitas", "Algar", "Algar de Mesa", "Algar de Palancia", "Algarinejo", "Algarra", "Algarrobo", "Algatocín", "Algeciras", "Algemesí", "Algerri", "Algete", "Algimia de Alfara", "Algimia de Almonacid", "Alginet", "Algodonales", "Algodre", "Algora", "Algorfa", "Alguaire", "Alguazas", "Algueña", "Alhabia", "Alhama de Almería", "Alhama de Aragón", "Alhama de Granada", "Alhama de Murcia", "Alhambra", "Alhaurín de la Torre", "Alhaurín el Grande", "Alhendín", "Alhóndiga", "Alía", "Aliaga", "Aliaguilla", "Alicún", "Alicún de Ortega", "Alija del Infantado", "Alins", "Alió", "Alique", "Aliseda", "Aliud", "Aljaraque", "Aljucén", "Alkiza", "Allande", "Allariz", "Allepuz", "Aller", "Allín/Allin", "Allo", "Alloza", "Allueva", "Almacelles", "Almáchar", "Almadén", "Almadén de la Plata", "Almadenejos", "Almadrones", "Almagro", "Almajano", "Almaluez", "Almansa", "Almanza", "Almaraz", "Almaraz de Duero", "Almarcha, La", "Almargen", "Almarza", "Almarza de Cameros", "Almàssera", "Almatret", "Almazán", "Almazora/Almassora", "Almazul", "Almedíjar", "Almedina", "Almedinilla", "Almegíjar", "Almeida de Sayago", "Almenar", "Almenar de Soria", "Almenara", "Almenara de Adaja", "Almenara de Tormes", "Almendra", "Almendral", "Almendral de la Cañada", "Almendralejo", "Almendro, El", "Almendros", "Almensilla", "Almería", "Almiserà", "Almochuel", "Almócita", "Almodóvar del Campo", "Almodóvar del Pinar", "Almodóvar del Río", "Almogía", "Almoguera", "Almohaja", "Almoharín", "Almoines", "Almolda, La", "Almonacid de la Cuba", "Almonacid de la Sierra", "Almonacid de Toledo", "Almonacid de Zorita", "Almonacid del Marquesado", "Almonaster la Real", "Almonte", "Almoradí", "Almorox", "Almoster", "Almudaina", "Almudévar", "Almunia de Doña Godina, La", "Almunia de San Juan", "Almuniente", "Almuñécar", "Almuradiel", "Almussafes", "Alobras", "Alocén", "Alonsotegi", "Álora", "Alòs de Balaguer", "Alosno", "Alovera", "Alozaina", "Alp", "Alpandeire", "Alpanseque", "Alpartir", "Alpedrete", "Alpens", "Alpeñés", "Alpera", "Alpicat", "Alpuente", "Alpujarra de la Sierra", "Alqueria dAsnar, l", "Alqueria de la Comtessa, l", "Alquerías del Niño Perdido", "Alquézar", "Alquife", "Alsodux", "Alt Àneu", "Altable", "Altafulla", "Altarejos", "Altea", "Altorricón", "Altos, Los", "Altsasu/Alsasua", "Altura", "Altzaga", "Altzo", "Alustante", "Alzira", "Amavida", "Amayuelas de Arriba", "Ambel", "Ambite", "Amer", "Ames", "Améscoa Baja", "Ametlla de Mar, L", "Ametlla del Vallès, L", "Ameyugo", "Amezketa", "Amieva", "Amoeiro", "Amorebieta-Etxano", "Amoroto", "Ampolla, L", "Amposta", "Ampudia", "Ampuero", "Amurrio", "Amusco", "Amusquillo", "Anadón", "Anaya", "Anaya de Alba", "Anchuelo", "Anchuras", "Ancín/Antzin", "Andavías", "Andilla", "Andoain", "Andorra", "Andosilla", "Andratx", "Andújar", "Anento", "Anglès", "Anglesola", "Angón", "Anguciana", "Angüés", "Anguiano", "Anguita", "Anguix", "Anievas", "Aniñón", "Anna", "Anoeta", "Anquela del Ducado", "Anquela del Pedregal", "Ansó", "Ansoáin/Antsoain", "Antas", "Antas de Ulla", "Antella", "Antequera", "Antigua", "Antigua, La", "Antigüedad", "Antillón", "Antzuola", "Anue", "Añana", "Añe", "Añón de Moncayo", "Añora", "Añorbe", "Añover de Tajo", "Añover de Tormes", "Aoiz/Agoitz", "Arabayona de Mógica", "Aracena", "Arafo", "Aragüés del Puerto", "Arahal", "Arahuetes", "Araitz", "Arakaldo", "Arakil", "Arama", "Aramaio", "Aranarache/Aranaratxe", "Arancón", "Aranda de Duero", "Aranda de Moncayo", "Arándiga", "Arandilla", "Arandilla del Arroyo", "Aranga", "Aranguren", "Aranjuez", "Arano", "Arantza", "Arantzazu", "Aranzueque", "Arañuel", "Arapiles", "Aras", "Aras de los Olmos", "Arauzo de Miel", "Arauzo de Salce", "Arauzo de Torre", "Arbancón", "Arbeca", "Arbeteta", "Arbizu", "Arbo", "Arboç, L", "Arboleas", "Arbolí", "Arbúcies", "Arcas del Villar", "Arce/Artzi", "Arcediano", "Arcenillas", "Archena", "Árchez", "Archidona", "Arcicóllar", "Arco, El", "Arconada", "Arcones", "Arcos", "Arcos de Jalón", "Arcos de la Frontera", "Arcos de la Polvorosa", "Arcos de la Sierra", "Arcos de las Salinas", "Arcos, Los", "Ardales", "Ardisa", "Ardón", "Areatza", "Arellano", "Arén", "Arenal, El", "Arenales de San Gregorio", "Arenas", "Arenas de Iguña", "Arenas de San Juan", "Arenas de San Pedro", "Arenas del Rey", "Arenillas", "Arenillas de Riopisuerga", "Arens de Lledó", "Arenys de Mar", "Arenys de Munt", "Arenzana de Abajo", "Arenzana de Arriba", "Ares", "Ares del Maestrat", "Areso", "Aretxabaleta", "Arevalillo", "Arevalillo de Cega", "Arévalo", "Arévalo de la Sierra", "Argamasilla de Alba", "Argamasilla de Calatrava", "Arganda del Rey", "Arganza", "Argañín", "Argavieso", "Argecilla", "Argelaguer", "Argelita", "Argençola", "Argente", "Argentera, L", "Argentona", "Argés", "Argoños", "Arguedas", "Arguis", "Arguisuelas", "Argujillo", "Aria", "Ariany", "Aribe", "Arico", "Arija", "Ariño", "Ariza", "Arjona", "Arjonilla", "Arlanzón", "Armallones", "Armañanzas", "Armentera, L", "Armenteros", "Armilla", "Armiñón", "Armuña", "Armuña de Almanzora", "Armuña de Tajuña", "Arnedillo", "Arnedo", "Arnes", "Arnoia, A", "Arnuero", "Aroche", "Arona", "Arquillinos", "Arquillos", "Arrabalde", "Arraia-Maeztu", "Arrancacepas", "Arrankudiaga", "Arrasate/Mondragón", "Arratzu", "Arraya de Oca", "Arrazua-Ubarrundia", "Arrecife", "Arredondo", "Arres", "Arriate", "Arrieta", "Arrigorriaga", "Arroba de los Montes", "Arróniz", "Arroyo de la Encomienda", "Arroyo de la Luz", "Arroyo de las Fraguas", "Arroyo de San Serván", "Arroyo del Ojanco", "Arroyomolinos", "Arroyomolinos", "Arroyomolinos de la Vera", "Arroyomolinos de León", "Arruazu", "Arrúbal", "Arsèguel", "Artà", "Artajona", "Artana", "Artazu", "Artea", "Arteixo", "Artenara", "Artés", "Artesa de Lleida", "Artesa de Segre", "Artieda", "Artzentales", "Artziniega", "Arucas", "Arzúa", "Ascó", "Asín", "Aspa", "Aspariegos", "Asparrena", "Aspe", "Asteasu", "Astigarraga", "Astillero, El", "Astorga", "Astudillo", "Asturianos", "Atajate", "Atalaya", "Atalaya del Cañavate", "Atalaya, La", "Atanzón", "Atapuerca", "Ataquines", "Atarfe", "Ataun", "Atazar, El", "Atea", "Ateca", "Atez", "Atienza", "Atxondo", "Atzeneta dAlbaida", "Atzeneta del Maestrat", "Aulesti", "Auñón", "Auritz/Burguete", "Ausejo", "Ausejo de la Sierra", "Ausines, Los", "Autilla del Pino", "Autillo de Campos", "Autol", "Aveinte", "Avellaneda", "Avellanes i Santa Linya, Les", "Avellanosa de Muñó", "Avià", "Ávila", "Avilés", "Avinyó", "Avinyonet de Puigventós", "Avinyonet del Penedès", "Avión", "Ayala/Aiara", "Ayamonte", "Ayegui/Aiegi", "Ayerbe", "Ayllón", "Ayna", "Ayódar", "Ayoó de Vidriales", "Ayora", "Ayuela", "Azagra", "Azaila", "Azanuy-Alins", "Azara", "Azkoitia", "Azlor", "Aznalcázar", "Aznalcóllar", "Azofra", "Azpeitia", "Azuaga", "Azuara", "Azuébar", "Azuelo", "Azuqueca de Henares", "Azután", "Babilafuente", "Bacares", "Badajoz", "Badalona", "Badarán", "Bádenas", "Badia del Vallès", "Badolatosa", "Badules", "Baélls", "Baena", "Baeza", "Bagà", "Báguena", "Bagüés", "Bahabón", "Bahabón de Esgueva", "Baides", "Bailén", "Bailo", "Baiona", "Baix Pallars", "Bakaiku", "Bakio", "Balaguer", "Balazote", "Balbases, Los", "Balboa", "Balconchán", "Baldellou", "Baleira", "Balenyà", "Baliarrain", "Ballestero, El", "Ballesteros de Calatrava", "Ballobar", "Balmaseda", "Balones", "Balsa de Ves", "Balsareny", "Baltanás", "Baltar", "Banastás", "Bande", "Banyalbufar", "Banyeres de Mariola", "Banyeres del Penedès", "Banyoles", "Baña, A", "Bañares", "Bañeza, La", "Bañobárez", "Bañón", "Baños de Ebro/Mañueta", "Baños de la Encina", "Baños de Molgas", "Baños de Montemayor", "Baños de Río Tobía", "Baños de Rioja", "Baños de Tajo", "Baños de Valdearados", "Bañuelos", "Bañuelos de Bureba", "Baquerín de Campos", "Barajas de Melo", "Barakaldo", "Baralla", "Barañain", "Baraona", "Barásoain", "Barbadás", "Barbadillo", "Barbadillo de Herreros", "Barbadillo del Mercado", "Barbadillo del Pez", "Barbalos", "Barbarin", "Barbastro", "Barbate", "Barbens", "Barberà de la Conca", "Barberà del Vallès", "Bárboles", "Barbolla", "Barbués", "Barbuñales", "Barca", "Bárcabo", "Barcarrota", "Barcelona", "Bárcena de Campos", "Bárcena de Cicero", "Bárcena de Pie de Concha", "Barceo", "Barchín del Hoyo", "Barcial de la Loma", "Barcial del Barco", "Barcience", "Barco de Ávila, El", "Barco de Valdeorras, O", "Barcones", "Bardallur", "Bareyo", "Bargas", "Bargota", "Barillas", "Barjas", "Barlovento", "Baronia de Rialb, La", "Barracas", "Barrachina", "Barraco, El", "Barrado", "Barrax", "Barreiros", "Barrika", "Barrio de Muñó", "Barriopedro", "Barrios de Bureba, Los", "Barrios de Colina", "Barrios de Luna, Los", "Barrios, Los", "Barro", "Barromán", "Barruecopardo", "Barruelo de Santullán", "Barruelo del Valle", "Barrundia", "Barx", "Barxeta", "Basaburua", "Basardilla", "Basauri", "Bàscara", "Basconcillos del Tozo", "Báscones de Ojeda", "Bascuñana", "Bascuñana de San Pedro", "Bassella", "Bastida, La", "Batea", "Baterno", "Batres", "Bausen", "Bayárcal", "Bayarque", "Bayubas de Abajo", "Bayubas de Arriba", "Baza", "Baztan", "Bea", "Beade", "Beamud", "Beariz", "Beas", "Beas de Granada", "Beas de Guadix", "Beas de Segura", "Beasain", "Becedas", "Becedillas", "Beceite", "Becerreá", "Becerril de Campos", "Becerril de la Sierra", "Becilla de Valderaduey", "Bédar", "Bedia", "Bedmar y Garcíez", "Begíjar", "Begonte", "Begues", "Begur", "Beintza-Labaien", "Beire", "Beires", "Beizama", "Béjar", "Bejís", "Belalcázar", "Belascoáin", "Belauntza", "Belbimbre", "Belchite", "Beleña", "Bèlgida", "Belianes", "Belinchón", "Bell-lloc dUrgell", "Bellaguarda", "Bellcaire dEmpordà", "Bellcaire dUrgell", "Bellmunt del Priorat", "Bellmunt dUrgell", "Bello", "Bellprat", "Bellpuig", "Bellreguard", "Bellús", "Bellvei", "Bellver de Cerdanya", "Bellvís", "Belmez", "Bélmez de la Moraleda", "Belmonte", "Belmonte de Campos", "Belmonte de Gracián", "Belmonte de Miranda", "Belmonte de San José", "Belmonte de Tajo", "Belmontejo", "Belorado", "Belver de Cinca", "Belver de los Montes", "Belvís de la Jara", "Belvís de Monroy", "Bembibre", "Benabarre", "Benacazón", "Benadalid", "Benafarces", "Benafer", "Benafigos", "Benagéber", "Benaguasil", "Benahadux", "Benahavís", "Benalauría", "Benalmádena", "Benalúa", "Benalúa de las Villas", "Benalup-Casas Viejas", "Benamargosa", "Benamaurel", "Benamejí", "Benamocarra", "Benaocaz", "Benaoján", "Benarrabá", "Benasal", "Benasau", "Benasque", "Benatae", "Benavent de Segrià", "Benavente", "Benavides", "Benavites", "Benegiles", "Beneixama", "Beneixida", "Benejúzar", "Benetússer", "Benferri", "Beniarbeig", "Beniardá", "Beniarjó", "Beniarrés", "Beniatjar", "Benicarló", "Benicasim/Benicàssim", "Benicolet", "Benicull de Xúquer", "Benidoleig", "Benidorm", "Beniel", "Benifaió", "Benifairó de la Valldigna", "Benifairó de les Valls", "Benifallet", "Benifallim", "Benifato", "Beniflá", "Benigánim", "Benigembla", "Benijófar", "Benilloba", "Benillup", "Benimantell", "Benimarfull", "Benimassot", "Benimeli", "Benimodo", "Benimuslem", "Beniparrell", "Benirredrà", "Benisanó", "Benissa", "Benissanet", "Benissoda", "Benisuera", "Benitachell/Poble Nou de Benitatxell, el", "Benitagla", "Benizalón", "Benlloch", "Benquerencia", "Benquerencia de la Serena", "Bentarique", "Benuza", "Bera", "Berango", "Berantevilla", "Beranuy", "Berastegi", "Beratón", "Berbegal", "Berberana", "Berbinzana", "Berceo", "Bercero", "Berceruelo", "Bérchules", "Bercial", "Bercial de Zapardiel", "Bercianos del Páramo", "Bercianos del Real Camino", "Bercimuel", "Berdejo", "Berga", "Bergara", "Bergasa", "Bergasillas Bajera", "Berge", "Bergondo", "Beriáin", "Berja", "Berlanas, Las", "Berlanga", "Berlanga de Duero", "Berlanga del Bierzo", "Berlangas de Roa", "Bermellar", "Bermeo", "Bermillo de Sayago", "Bernardos", "Bernedo", "Berninches", "Bernuy de Porreros", "Bernuy-Zapardiel", "Berriatua", "Berrioplano/Berriobeiti", "Berriozar", "Berriz", "Berrobi", "Berrocal", "Berrocal de Huebra", "Berrocal de Salvatierra", "Berrocalejo", "Berrocalejo de Aragona", "Berrueces", "Berrueco", "Berrueco, El", "Bertizarana", "Berzocana", "Berzosa de Bureba", "Berzosa del Lozoya", "Berzosilla", "Besalú", "Bescanó", "Betancuria", "Betanzos", "Betelu", "Bétera", "Beteta", "Betxí", "Beuda", "Bezares", "Bezas", "Biar", "Bicorp", "Bidaurreta", "Bidegoian", "Biel", "Bielsa", "Bienservida", "Bienvenida", "Bierge", "Biescas", "Bigastro", "Bigues i Riells", "Bijuesca", "Bilbao", "Bimenes", "Binaced", "Binéfar", "Binissalem", "Biosca", "Biota", "Bisaurri", "Bisbal de Falset, La", "Bisbal del Penedès, La", "Bisbal dEmpordà, La", "Biscarrués", "Bisimbre", "Biure", "Biurrun-Olcoz", "Blacos", "Blanca", "Blancafort", "Blancas", "Blancos, Os", "Blanes", "Blascomillán", "Blasconuño de Matacabras", "Blascosancho", "Blázquez, Los", "Blecua y Torres", "Blesa", "Bliecos", "Boada", "Boada de Campos", "Boadella i les Escaules", "Boadilla de Rioseco", "Boadilla del Camino", "Boadilla del Monte", "Boal", "Boalo, El", "Bobadilla", "Bobadilla del Campo", "Boborás", "Boca de Huérgano", "Bocairent", "Boceguillas", "Bocigas", "Bocos de Duero", "Bodera, La", "Bodón, El", "Bodonal de la Sierra", "Boecillo", "Bogajo", "Bogarra", "Bohodón, El", "Bohonal de Ibor", "Bohoyo", "Boimorto", "Boiro", "Bola, A", "Bolaños de Calatrava", "Bolaños de Campos", "Bolbaite", "Bollullos de la Mitación", "Bollullos Par del Condado", "Bolo, O", "Boltaña", "Bolulla", "Bolvir", "Bonansa", "Bonares", "Bonastre", "Bonete", "Boniches", "Bonilla de la Sierra", "Bonillo, El", "Bonrepòs i Mirambell", "Boñar", "Boqueixón", "Boquiñeni", "Borau", "Bordalba", "Bòrdes, Es", "Bordils", "Bordón", "Borge, El", "Borges Blanques, Les", "Borges del Camp, Les", "Borja", "Borjabad", "Bormujos", "Bornos", "Borobia", "Borox", "Borrassà", "Borredà", "Borrenes", "Borriana/Burriana", "Borriol", "Bosque, El", "Bossòst", "Bot", "Botarell", "Botija", "Botorrita", "Bouza, La", "Bóveda", "Bóveda de Toro, La", "Bóveda del Río Almar", "Bovera", "Bozoó", "Brabos", "Bràfim", "Brahojos de Medina", "Brañosera", "Braojos", "Brazacorta", "Brazatortas", "Brazuelo", "Brea de Aragón", "Brea de Tajo", "Breda", "Brenes", "Breña Alta", "Breña Baja", "Bretó", "Bretocino", "Brieva", "Brieva de Cameros", "Brihuega", "Brime de Sog", "Brime de Urz", "Brincones", "Briñas", "Brión", "Briones", "Briviesca", "Bronchales", "Broto", "Brozas", "Bruc, El", "Brull, El", "Brunete", "Brunyola", "Buberos", "Bubierca", "Bubión", "Buciegas", "Budia", "Buenache de Alarcón", "Buenache de la Sierra", "Buenamadre", "Buenaventura", "Buenavista", "Buenavista de Valdavia", "Buenavista del Norte", "Buendía", "Bueña", "Bueu", "Bufali", "Bugarra", "Bugedo", "Búger", "Buitrago", "Buitrago del Lozoya", "Bujalance", "Bujalaro", "Bujaraloz", "Bularros", "Bulbuente", "Bullas", "Buniel", "Bunyola", "Buñol", "Buñuel", "Burbáguena", "Burela", "Bureta", "Burganes de Valverde", "Burgo de Ebro, El", "Burgo de Osma-Ciudad de Osma", "Burgo Ranero, El", "Burgo, El", "Burgohondo", "Burgos", "Burgui/Burgi", "Burguillos", "Burguillos de Toledo", "Burguillos del Cerro", "Burjassot", "Burlada/Burlata", "Burón", "Burujón", "Busot", "Busquístar", "Bustares", "Bustarviejo", "Buste, El", "Bustillo de Chaves", "Bustillo de la Vega", "Bustillo del Oro", "Bustillo del Páramo", "Bustillo del Páramo de Carrión", "Busto de Bureba", "Busto, El", "Busturia", "Cabacés", "Cabaco, El", "Caballar", "Cabana de Bergantiños", "Cabanabona", "Cabanas", "Cabanelles", "Cabanes", "Cabanes", "Cabanillas", "Cabanillas de la Sierra", "Cabanillas del Campo", "Cabanyes, Les", "Cabañas de Ebro", "Cabañas de la Sagra", "Cabañas de Polendos", "Cabañas de Sayago", "Cabañas de Yepes", "Cabañas del Castillo", "Cabañas Raras", "Cabañes de Esgueva", "Cabeza de Béjar, La", "Cabeza del Buey", "Cabeza del Caballo", "Cabeza la Vaca", "Cabezabellosa", "Cabezabellosa de la Calzada", "Cabezamesada", "Cabezarados", "Cabezarrubias del Puerto", "Cabezas de Alambre", "Cabezas de San Juan, Las", "Cabezas del Pozo", "Cabezas del Villar", "Cabezas Rubias", "Cabezón de Cameros", "Cabezón de la Sal", "Cabezón de la Sierra", "Cabezón de Liébana", "Cabezón de Pisuerga", "Cabezón de Valderaduey", "Cabezuela", "Cabezuela del Valle", "Cabizuela", "Cabó", "Cabolafuente", "Cabra", "Cabra de Mora", "Cabra del Camp", "Cabra del Santo Cristo", "Cabrales", "Cabranes", "Cabredo", "Cabrejas del Campo", "Cabrejas del Pinar", "Cabrera dAnoia", "Cabrera de Mar", "Cabrera, La", "Cabrerizos", "Cabrero", "Cabreros del Monte", "Cabreros del Río", "Cabrillanes", "Cabrillas", "Cabrils", "Cabuérniga", "Cacabelos", "Cáceres", "Cachorrilla", "Cacín", "Cadalso", "Cadalso de los Vidrios", "Cadaqués", "Cádiar", "Cádiz", "Cadreita", "Cadrete", "Cájar", "Cala", "Calabazas de Fuentidueña", "Calaceite", "Calaf", "Calafell", "Calahorra", "Calahorra de Boedo", "Calahorra, La", "Calamocha", "Calamonte", "Calanda", "Calañas", "Calasparra", "Calatañazor", "Calatayud", "Calatorao", "Calcena", "Caldas de Reis", "Caldearenas", "Calders", "Caldes de Malavella", "Caldes de Montbui", "Caldes dEstrac", "Calella", "Calera de León", "Calera y Chozas", "Caleruega", "Caleruela", "Calicasas", "Càlig", "Calldetenes", "Calles", "Callosa de Segura", "Callosa dEn Sarrià", "Callús", "Calmarza", "Calomarde", "Calonge", "Calonge de Segarra", "Calp", "Caltojar", "Calvarrasa de Abajo", "Calvarrasa de Arriba", "Calvià", "Calvos de Randín", "Calzada de Béjar, La", "Calzada de Calatrava", "Calzada de Don Diego", "Calzada de los Molinos", "Calzada de Oropesa", "Calzada de Valdunciel", "Calzada del Coto", "Calzadilla", "Calzadilla de los Barros", "Calzadilla de Tera", "Camaleño", "Camañas", "Camarasa", "Camarena", "Camarena de la Sierra", "Camarenilla", "Camargo", "Camarillas", "Camariñas", "Camarles", "Camarma de Esteruelas", "Camarzana de Tera", "Camas", "Cambados", "Cambil", "Cambre", "Cambrils", "Caminomorisco", "Caminreal", "Camós", "Campana, La", "Campanario", "Campanet", "Campaspero", "Campazas", "Campdevànol", "Campelles", "Campello, el", "Campezo/Kanpezu", "Campillo de Altobuey", "Campillo de Aragón", "Campillo de Aranda", "Campillo de Arenas", "Campillo de Azaba", "Campillo de Deleitosa", "Campillo de Dueñas", "Campillo de la Jara, El", "Campillo de Llerena", "Campillo de Ranas", "Campillo, El", "Campillo, El", "Campillos", "Campillos-Paravientos", "Campillos-Sierra", "Campins", "Campisábalos", "Campllong", "Campo", "Campo de Criptana", "Campo de Mirra/Camp de Mirra, el", "Campo de Peñaranda, El", "Campo de San Pedro", "Campo de Villavidel", "Campo Lameiro", "Campo Lugar", "Campo Real", "Campofrío", "Campolara", "Camponaraya", "Campoo de Enmedio", "Campoo de Yuso", "Camporredondo", "Camporrélls", "Camporrobles", "Campos", "Campos del Paraíso", "Campos del Río", "Campotéjar", "Camprodon", "Camprovín", "Camuñas", "Canal de Berdún", "Canalejas de Peñafiel", "Canalejas del Arroyo", "Canales", "Canales de la Sierra", "Canals", "Candamo", "Candasnos", "Candelaria", "Candelario", "Candeleda", "Candilichera", "Candín", "Canejan", "Canena", "Canencia", "Canet dAdri", "Canet de Mar", "Canet dEn Berenguer", "Canet lo Roig", "Canfranc", "Cangas", "Cangas de Onís", "Cangas del Narcea", "Canicosa de la Sierra", "Caniles", "Canillas de Abajo", "Canillas de Aceituno", "Canillas de Albaida", "Canillas de Esgueva", "Canillas de Río Tuerto", "Canjáyar", "Canonja, La", "Canovelles", "Cànoves i Samalús", "Canredondo", "Cantabrana", "Cantagallo", "Cantalapiedra", "Cantalejo", "Cantallops", "Cantalojas", "Cantalpino", "Cantaracillo", "Cantavieja", "Cantillana", "Cantimpalos", "Cantiveros", "Cantoria", "Canyelles", "Cañada", "Cañada de Benatanduz", "Cañada de Calatrava", "Cañada de Verich, La", "Cañada del Hoyo", "Cañada Juncosa", "Cañada Rosal", "Cañada Vellida", "Cañamaque", "Cañamares", "Cañamero", "Cáñar", "Cañas", "Cañavate, El", "Cañaveral", "Cañaveral de León", "Cañaveras", "Cañaveruelas", "Cañete", "Cañete de las Torres", "Cañete la Real", "Cañiza, A", "Cañizal", "Cañizar", "Cañizar del Olivar", "Cañizares", "Cañizo", "Capafonts", "Caparroso", "Capçanes", "Capdepera", "Capdesaso", "Capela, A", "Capella", "Capellades", "Capileira", "Capilla", "Capillas", "Capmany", "Capolat", "Carabantes", "Carabaña", "Caracena", "Caracuel de Calatrava", "Carataunas", "Caravaca de la Cruz", "Caravia", "Carazo", "Carbajales de Alba", "Carbajo", "Carbajosa de la Sagrada", "Carballeda de Avia", "Carballeda de Valdeorras", "Carballedo", "Carballiño, O", "Carballo", "Carbellino", "Carboneras", "Carboneras de Guadazaón", "Carbonero el Mayor", "Carboneros", "Carcaboso", "Carcabuey", "Carcaixent", "Cárcar", "Carcastillo", "Carcedo de Bureba", "Carcedo de Burgos", "Carcelén", "Càrcer", "Cárcheles", "Cardedeu", "Cárdenas", "Cardenete", "Cardeña", "Cardeñadijo", "Cardeñajimeno", "Cardeñosa", "Cardeñosa de Volpejera", "Cardeñuela Riopico", "Cardiel de los Montes", "Cardona", "Cardoso de la Sierra, El", "Carenas", "Cariñena", "Cariño", "Carlet", "Carlota, La", "Carme", "Carmena", "Cármenes", "Carmona", "Carmonita", "Carnota", "Carolina, La", "Carpio", "Carpio de Azaba", "Carpio de Tajo, El", "Carpio, El", "Carracedelo", "Carral", "Carranque", "Carrascal de Barregas", "Carrascal del Obispo", "Carrascal del Río", "Carrascalejo", "Carrascalejo, El", "Carrascosa", "Carrascosa de Abajo", "Carrascosa de Haro", "Carrascosa de la Sierra", "Carratraca", "Carreño", "Carrera, La", "Carrias", "Carriches", "Carrícola", "Carrión de Calatrava", "Carrión de los Céspedes", "Carrión de los Condes", "Carrizo", "Carrizosa", "Carrocera", "Cartagena", "Cartajima", "Cártama", "Cartaya", "Cartelle", "Cartes", "Carucedo", "Casa de Uceda", "Casabermeja", "Casafranca", "Casalarreina", "Casar de Cáceres", "Casar de Escalona, El", "Casar de Palomero", "Casar, El", "Casarabonela", "Casarejos", "Casares", "Casares de las Hurdes", "Casariche", "Casarrubios del Monte", "Casarrubuelos", "Casas Altas", "Casas Bajas", "Casas de Benítez", "Casas de Don Antonio", "Casas de Don Gómez", "Casas de Don Pedro", "Casas de Fernando Alonso", "Casas de Garcimolina", "Casas de Guijarro", "Casas de Haro", "Casas de Juan Núñez", "Casas de Lázaro", "Casas de los Pinos", "Casas de Millán", "Casas de Miravete", "Casas de Reina", "Casas de San Galindo", "Casas de Ves", "Casas del Castañar", "Casas del Conde, Las", "Casas del Monte", "Casas del Puerto", "Casas-Ibáñez", "Casasbuenas", "Casaseca de Campeán", "Casaseca de las Chanas", "Casasimarro", "Casasola", "Casasola de Arión", "Casatejada", "Casavieja", "Casbas de Huesca", "Cascajares de Bureba", "Cascajares de la Sierra", "Cascante", "Cascante del Río", "Cáseda", "Caseres", "Casillas", "Casillas de Coria", "Casillas de Flores", "Casinos", "Casla", "Caso", "Caspe", "Caspueñas", "Cassà de la Selva", "Casserres", "Castalla", "Castañar de Ibor", "Castañares de Rioja", "Castañeda", "Castaño del Robledo", "Cástaras", "Castejón", "Castejón", "Castejón de Alarba", "Castejón de Henares", "Castejón de las Armas", "Castejón de Monegros", "Castejón de Sos", "Castejón de Tornos", "Castejón de Valdejasa", "Castejón del Puente", "Castel de Cabra", "Castelflorite", "Castell de Cabres", "Castell de Castells", "Castell de Guadalest, el", "Castell de lAreny", "Castell de Mur", "Castell-Platja dAro", "Castell, Es", "Castellanos de Castro", "Castellanos de Moriscos", "Castellanos de Villiquera", "Castellanos de Zapardiel", "Castellar", "Castellar de la Frontera", "Castellar de la Muela", "Castellar de la Ribera", "Castellar de nHug", "Castellar de Santiago", "Castellar del Riu", "Castellar del Vallès", "Castellar, El", "Castellbell i el Vilar", "Castellbisbal", "Castellcir", "Castelldans", "Castelldefels", "Castellet i la Gornal", "Castellfollit de la Roca", "Castellfollit de Riubregós", "Castellfollit del Boix", "Castellfort", "Castellgalí", "Castellnou de Bages", "Castellnou de Seana", "Castellnovo", "Castelló de Farfanya", "Castelló de Rugat", "Castelló dEmpúries", "Castellolí", "Castellón de la Plana/Castelló de la Plana", "Castellonet de la Conquesta", "Castellote", "Castellserà", "Castellterçol", "Castellvell del Camp", "Castellví de la Marca", "Castellví de Rosanes", "Castelnou", "Castelserás", "Castielfabib", "Castiello de Jaca", "Castigaleu", "Castil de Peones", "Castil de Vela", "Castilblanco", "Castilblanco de los Arroyos", "Castildelgado", "Castilfalé", "Castilforte", "Castilfrío de la Sierra", "Castiliscar", "Castillazuelo", "Castilleja de Guzmán", "Castilleja de la Cuesta", "Castilleja del Campo", "Castilléjar", "Castillejo de Iniesta", "Castillejo de Martín Viejo", "Castillejo de Mesleón", "Castillejo de Robledo", "Castillejo-Sierra", "Castillo de Bayuela", "Castillo de Garcimuñoz", "Castillo de las Guardas, El", "Castillo de Locubín", "Castillo de Villamalefa", "Castillo-Albaráñez", "Castillonroy", "Castillonuevo", "Castilnuevo", "Castilruiz", "Castraz", "Castrejón de la Peña", "Castrejón de Trabancos", "Castrelo de Miño", "Castrelo do Val", "Castril", "Castrillo de Cabrera", "Castrillo de Don Juan", "Castrillo de Duero", "Castrillo de la Guareña", "Castrillo de la Reina", "Castrillo de la Valduerna", "Castrillo de la Vega", "Castrillo de Onielo", "Castrillo de Riopisuerga", "Castrillo de Villavega", "Castrillo del Val", "Castrillo Matajudíos", "Castrillo-Tejeriego", "Castrillón", "Castro Caldelas", "Castro de Filabres", "Castro de Fuentidueña", "Castro de Rei", "Castro del Río", "Castro-Urdiales", "Castrobol", "Castrocalbón", "Castrocontrigo", "Castrodeza", "Castrogonzalo", "Castrojeriz", "Castrojimeno", "Castromembibre", "Castromocho", "Castromonte", "Castronuevo", "Castronuevo de Esgueva", "Castronuño", "Castropodame", "Castropol", "Castroponce", "Castroserna de Abajo", "Castroserracín", "Castrotierra de Valmadrigal", "Castroverde", "Castroverde de Campos", "Castroverde de Cerrato", "Castroviejo", "Castuera", "Catadau", "Catarroja", "Catí", "Catllar, El", "Catoira", "Catral", "Caudete", "Caudete de las Fuentes", "Caudiel", "Cava", "Cavia", "Cayuela", "Cazalegas", "Cazalilla", "Cazalla de la Sierra", "Cazorla", "Cazurra", "Cea", "Cebanico", "Cebolla", "Cebrecos", "Cebreros", "Cebrones del Río", "Ceclavín", "Cedeira", "Cedillo", "Cedillo de la Torre", "Cedillo del Condado", "Cedrillas", "Cee", "Cehegín", "Ceinos de Campos", "Celada del Camino", "Celadas", "Celanova", "Cella", "Cellera de Ter, La", "Cellorigo", "Celrà", "Cendea de Olza/Oltza Zendea", "Cendejas de Enmedio", "Cendejas de la Torre", "Cenes de la Vega", "Cenicero", "Cenicientos", "Cenizate", "Cenlle", "Centelles", "Centenera", "Centenera de Andaluz", "Cepeda", "Cepeda la Mora", "Cerbón", "Cerceda", "Cercedilla", "Cercs", "Cerdà", "Cerdanyola del Vallès", "Cerdedo", "Cerdido", "Cereceda de la Sierra", "Cerecinos de Campos", "Cerecinos del Carrizal", "Cerezal de Peñahorcada", "Cerezo", "Cerezo de Abajo", "Cerezo de Arriba", "Cerezo de Río Tirón", "Cernadilla", "Cerollera, La", "Cerralbo", "Cerralbos, Los", "Cerratón de Juarros", "Cerro de Andévalo, El", "Cerro, El", "Cervantes", "Cervatos de la Cueza", "Cervelló", "Cervera", "Cervera de Buitrago", "Cervera de la Cañada", "Cervera de los Montes", "Cervera de Pisuerga", "Cervera del Llano", "Cervera del Maestre", "Cervera del Río Alhama", "Cerveruela", "Cervià de les Garrigues", "Cervià de Ter", "Cervillego de la Cruz", "Cervo", "Cespedosa de Tormes", "Cesuras", "Cetina", "Ceuta", "Ceutí", "Cevico de la Torre", "Cevico Navero", "Chagarcía Medianero", "Chalamera", "Chamartín", "Chandrexa de Queixa", "Chantada", "Chañe", "Chapinería", "Chauchina", "Checa", "Cheles", "Chella", "Chelva", "Chequilla", "Chera", "Chercos", "Chert/Xert", "Cheste", "Chía", "Chiclana de la Frontera", "Chiclana de Segura", "Chilches/Xilxes", "Chillarón de Cuenca", "Chillarón del Rey", "Chillón", "Chilluévar", "Chiloeches", "Chimeneas", "Chimillas", "Chinchilla de Monte-Aragón", "Chinchón", "Chipiona", "Chiprana", "Chirivel", "Chiva", "Chodes", "Chodos/Xodos", "Chóvar", "Chozas de Abajo", "Chozas de Canales", "Chucena", "Chueca", "Chulilla", "Chumillas", "Churriana de la Vega", "Ciadoncha", "Cidamón", "Cidones", "Ciempozuelos", "Cierva, La", "Cieza", "Cieza", "Cifuentes", "Cigales", "Cigudosa", "Ciguñuela", "Cihuela", "Cihuri", "Cijuela", "Cillán", "Cillaperlata", "Cilleros", "Cilleros de la Bastida", "Cilleruelo de Abajo", "Cilleruelo de Arriba", "Cilleruelo de San Mamés", "Cillorigo de Liébana", "Cimanes de la Vega", "Cimanes del Tejar", "Cimballa", "Cinco Olivas", "Cincovillas", "Cinctorres", "Cintruénigo", "Cipérez", "Cirat", "Cirauqui/Zirauki", "Ciria", "Ciriza/Ziritza", "Ciruelas", "Ciruelos", "Ciruelos de Cervera", "Ciruelos del Pinar", "Cirueña", "Cirujales del Río", "Cisla", "Cisneros", "Cistella", "Cistérniga", "Cistierna", "Ciudad Real", "Ciudad Rodrigo", "Ciutadella de Menorca", "Ciutadilla", "Cizur", "Clarés de Ribota", "Clariana de Cardener", "Clavijo", "Coaña", "Cóbdar", "Cobeja", "Cobeña", "Cobeta", "Cobisa", "Cobos de Cerrato", "Cobos de Fuentidueña", "Cobreros", "Coca", "Coca de Alba", "Cocentaina", "Codo", "Codoñera, La", "Codorniz", "Codos", "Codosera, La", "Cofrentes", "Cogeces de Íscar", "Cogeces del Monte", "Cogollor", "Cogollos", "Cogollos de Guadix", "Cogollos de la Vega", "Cogolludo", "Cogul, El", "Coín", "Coirós", "Colera", "Coles", "Colilla, La", "Colindres", "Coll de Nargó", "Collado", "Collado de Contreras", "Collado del Mirón", "Collado Hermoso", "Collado Mediano", "Collado Villalba", "Collazos de Boedo", "Collbató", "Colldejou", "Collsuspina", "Colmenar", "Colmenar de Montemayor", "Colmenar de Oreja", "Colmenar del Arroyo", "Colmenar Viejo", "Colmenarejo", "Colomera", "Colomers", "Colunga", "Colungo", "Coma i la Pedra, La", "Comares", "Comillas", "Cómpeta", "Conca de Dalt", "Condado de Castilnovo", "Condado de Treviño", "Condemios de Abajo", "Condemios de Arriba", "Conesa", "Confrides", "Congosto", "Congosto de Valdavia", "Congostrina", "Conil de la Frontera", "Conquista", "Conquista de la Sierra", "Consell", "Constantí", "Constantina", "Constanzana", "Consuegra", "Contamina", "Contreras", "Coomonte", "Copernal", "Copons", "Corbalán", "Corbera", "Corbera de Llobregat", "Corbera dEbre", "Corbillos de los Oteros", "Corbins", "Corçà", "Corcos", "Corcubión", "Córdoba", "Cordobilla de Lácara", "Cordovilla", "Cordovilla la Real", "Cordovín", "Corduente", "Corella", "Corera", "Coreses", "Corgo, O", "Coria", "Coria del Río", "Coripe", "Coristanco", "Cornago", "Cornellà de Llobregat", "Cornellà del Terri", "Cornudella de Montsant", "Coronada, La", "Coronil, El", "Corpa", "Corporales", "Corral de Almaguer", "Corral de Ayllón", "Corral de Calatrava", "Corral-Rubio", "Corrales de Buelna, Los", "Corrales de Duero", "Corrales del Vino", "Corrales, Los", "Corte de Peleas", "Corteconcepción", "Cortegada", "Cortegana", "Cortelazor", "Cortes", "Cortes de Aragón", "Cortes de Arenoso", "Cortes de Baza", "Cortes de la Frontera", "Cortes de Pallás", "Cortes y Graena", "Cortijos, Los", "Corullón", "Coruña del Conde", "Coruña, A", "Corvera de Asturias", "Corvera de Toranzo", "Cosa", "Coscurita", "Coslada", "Cospeito", "Costitx", "Costur", "Cosuenda", "Cotanes del Monte", "Cotes", "Cotillas", "Cotobade", "Covaleda", "Covarrubias", "Covelo", "Coves de Vinromà, les", "Cox", "Cózar", "Cozuelos de Fuentidueña", "Crecente", "Creixell", "Crémenes", "Crespià", "Crespos", "Cretas", "Crevillent", "Cristina", "Cristóbal", "Crivillén", "Cruïlles, Monells i Sant Sadurní de lHeura", "Cuacos de Yuste", "Cuadros", "Cualedro", "Cuarte de Huerva", "Cuba, La", "Cubas de la Sagra", "Cubel", "Cubelles", "Cubells", "Cubilla", "Cubillas de Cerrato", "Cubillas de los Oteros", "Cubillas de Rueda", "Cubillas de Santa Marta", "Cubillo", "Cubillo de Uceda, El", "Cubillo del Campo", "Cubillos", "Cubillos del Sil", "Cubla", "Cubo de Benavente", "Cubo de Bureba", "Cubo de Don Sancho, El", "Cubo de la Solana", "Cubo de Tierra del Vino, El", "Cucalón", "Cudillero", "Cuelgamures", "Cuéllar", "Cuenca", "Cuenca de Campos", "Cuerlas, Las", "Cuerva", "Cuervo de Sevilla, El", "Cuervo, El", "Cueva de Ágreda", "Cueva de Roa, La", "Cueva del Hierro", "Cuevas Bajas", "Cuevas de Almudén", "Cuevas de Provanco", "Cuevas de San Clemente", "Cuevas de San Marcos", "Cuevas del Almanzora", "Cuevas del Becerro", "Cuevas del Campo", "Cuevas del Valle", "Cuevas Labradas", "Culla", "Cúllar", "Cúllar Vega", "Cullera", "Culleredo", "Cumbre, La", "Cumbres de Enmedio", "Cumbres de San Bartolomé", "Cumbres Mayores", "Cunit", "Cuntis", "Curiel de Duero", "Curtis", "Cútar", "Cuzcurrita de Río Tirón", "Daganzo de Arriba", "Daimiel", "Daimús", "Dalías", "Darnius", "Daroca", "Daroca de Rioja", "Darro", "Das", "Daya Nueva", "Daya Vieja", "Deba", "Degaña", "Dehesa de Montejo", "Dehesa de Romanos", "Dehesas de Guadix", "Deifontes", "Deleitosa", "Deltebre", "Dénia", "Derio", "Descargamaría", "Desojo", "Destriana", "Dévanos", "Deyá", "Deza", "Dicastillo", "Diego del Carpio", "Diezma", "Dílar", "Dima", "Dios le Guarde", "Dodro", "Dólar", "Dolores", "Domeño", "Domingo García", "Domingo Pérez", "Don Álvaro", "Don Benito", "Donamaria", "Doneztebe/Santesteban", "Donhierro", "Donjimeno", "Donostia-San Sebastián", "Donvidas", "Doña Mencía", "Doñinos de Ledesma", "Doñinos de Salamanca", "Dos Aguas", "Dos Hermanas", "Dos Torres", "Dosbarrios", "Dosrius", "Dozón", "Driebes", "Dúdar", "Dueñas", "Duesaigües", "Dumbría", "Durango", "Dúrcal", "Durón", "Duruelo", "Duruelo de la Sierra", "Ea", "Echarri", "Écija", "Egüés", "Eibar", "Eivissa", "Ejea de los Caballeros", "Ejeme", "Ejido, El", "Ejulve", "Elantxobe", "Elburgo/Burgelu", "Elche de la Sierra", "Elche/Elx", "Elciego", "Elda", "Elduain", "Elgeta", "Elgoibar", "Elgorriaga", "Eliana, l", "Eljas", "Elorrio", "Elvillar/Bilar", "Embid", "Embid de Ariza", "Emperador", "Encina de San Silvestre", "Encina, La", "Encinacorba", "Encinas", "Encinas de Abajo", "Encinas de Arriba", "Encinas de Esgueva", "Encinas Reales", "Encinasola", "Encinasola de los Comendadores", "Encinedo", "Encinillas", "Encío", "Enciso", "Endrinal", "Enériz/Eneritz", "Enguera", "Enguídanos", "Enix", "Ènova, l", "Entrala", "Entrambasaguas", "Entrena", "Entrimo", "Entrín Bajo", "Épila", "Erandio", "Eratsun", "Ercina, La", "Ereño", "Ergoiena", "Erla", "Ermua", "Errenteria", "Errezil", "Erriberagoitia/Ribera Alta", "Errigoiti", "Erro", "Erustes", "Escacena del Campo", "Escala, L", "Escalante", "Escalona", "Escalona del Prado", "Escalonilla", "Escamilla", "Escañuela", "Escarabajosa de Cabezas", "Escariche", "Escatrón", "Escobar de Campos", "Escobar de Polendos", "Escobosa de Almazán", "Escopete", "Escorca", "Escorial, El", "Escorihuela", "Escucha", "Escurial", "Escurial de la Sierra", "Escúzar", "Esgos", "Esguevillas de Esgueva", "Eskoriatza", "Eslava", "Eslida", "Espadaña", "Espadañedo", "Espadilla", "Esparragalejo", "Esparragosa de la Serena", "Esparragosa de Lares", "Esparreguera", "Espartinas", "Esparza de Salazar/Espartza Zaraitzu", "Espeja", "Espeja de San Marcelino", "Espejo", "Espejón", "Espelúy", "Espera", "Espiel", "Espinar, El", "Espinelves", "Espino de la Orbada", "Espinosa de Cerrato", "Espinosa de Cervera", "Espinosa de Henares", "Espinosa de los Caballeros", "Espinosa de los Monteros", "Espinosa de Villagonzalo", "Espinosa del Camino", "Espinoso del Rey", "Espirdo", "Esplegares", "Espluga Calba, L", "Espluga de Francolí, L", "Esplugues de Llobregat", "Esplús", "Espolla", "Esponellà", "Esporles", "Espot", "Espronceda", "Espunyola, L", "Esquivias", "Establés", "Estada", "Estadilla", "Estamariu", "Estany, L", "Estaràs", "Estella-Lizarra", "Estellencs", "Estepa", "Estepa de San Juan", "Estépar", "Estepona", "Estercuel", "Esteribar", "Esterri dÀneu", "Esterri de Cardós", "Estivella", "Estollo", "Estopiñán del Castillo", "Estrada, A", "Estrella, La", "Estremera", "Estriégana", "Estubeny", "Etayo", "Etxalar", "Etxarri-Aranatz", "Etxauri", "Etxebarri", "Etxebarria", "Eulate", "Ezcabarte", "Ezcaray", "Ezcároz/Ezkaroze", "Ezkio-Itsaso", "Ezkurra", "Ezprogui", "Fabara", "Fabero", "Facheca", "Fago", "Falces", "Falset", "Famorca", "Fanlo", "Fanzara", "Far dEmpordà, El", "Faraján", "Faramontanos de Tábara", "Fariza", "Farlete", "Farrera", "Fasnia", "Fatarella, La", "Faura", "Favara", "Fayón", "Fayos, Los", "Febró, La", "Felanitx", "Felix", "Fene", "Férez", "Feria", "Fermoselle", "Fernán Caballero", "Fernán-Núñez", "Ferreira", "Ferreras de Abajo", "Ferreras de Arriba", "Ferreries", "Ferreruela", "Ferreruela de Huerva", "Ferrol", "Figaró-Montmany", "Fígols", "Fígols i Alinyà", "Figuera, La", "Figueres", "Figuerola del Camp", "Figueroles", "Figueruela de Arriba", "Figueruelas", "Fines", "Finestrat", "Fiñana", "Firgas", "Fiscal", "Fisterra", "Fitero", "Flaçà", "Flix", "Flores de Ávila", "Floresta, La", "Florida de Liébana", "Fogars de la Selva", "Fogars de Montclús", "Foios", "Foixà", "Folgoso de la Ribera", "Folgoso do Courel", "Folgueroles", "Fombellida", "Fombuena", "Fompedraza", "Foncea", "Fondarella", "Fondó de les Neus, el/Hondón de las Nieves", "Fondón", "Fonelas", "Fonfría", "Fonfría", "Fonollosa", "Fonsagrada, A", "Font de la Figuera, la", "Font dEn Carròs, la", "Font-rubí", "Fontanals de Cerdanya", "Fontanar", "Fontanarejo", "Fontanars dels Alforins", "Fontanilles", "Fontcoberta", "Fontellas", "Fontihoyuelo", "Fontioso", "Fontiveros", "Fonz", "Fonzaleche", "Foradada", "Foradada del Toscar", "Forallac", "Forcall", "Forcarei", "Forès", "Forfoleda", "Formentera", "Formentera del Segura", "Formiche Alto", "Fornalutx", "Fornells de la Selva", "Fornelos de Montes", "Fórnoles", "Fortaleny", "Fortanete", "Fortià", "Fortuna", "Forua", "Foz", "Foz-Calanda", "Frades", "Frades de la Sierra", "Fraga", "Frago, El", "Frailes", "Franco, El", "Frandovínez", "Franqueses del Vallès, Les", "Frasno, El", "Frechilla", "Frechilla de Almazán", "Fregenal de la Sierra", "Fregeneda, La", "Freginals", "Freila", "Fréscano", "Fresneda de Altarejos", "Fresneda de Cuéllar", "Fresneda de la Sierra", "Fresneda de la Sierra Tirón", "Fresneda, La", "Fresnedilla", "Fresnedillas de la Oliva", "Fresnedoso", "Fresnedoso de Ibor", "Fresneña", "Fresnillo de las Dueñas", "Fresno Alhándiga", "Fresno de Cantespino", "Fresno de Caracena", "Fresno de la Fuente", "Fresno de la Polvorosa", "Fresno de la Ribera", "Fresno de la Vega", "Fresno de Río Tirón", "Fresno de Rodilla", "Fresno de Sayago", "Fresno de Torote", "Fresno del Río", "Fresno el Viejo", "Fresno, El", "Frías", "Frías de Albarracín", "Friera de Valverde", "Frigiliana", "Friol", "Frómista", "Frontera", "Frontera, La", "Fruiz", "Frumales", "Fuembellida", "Fuencaliente", "Fuencaliente de la Palma", "Fuencemillán", "Fuendejalón", "Fuendetodos", "Fuenferrada", "Fuengirola", "Fuenlabrada", "Fuenlabrada de los Montes", "Fuenllana", "Fuenmayor", "Fuensaldaña", "Fuensalida", "Fuensanta", "Fuensanta de Martos", "Fuente Álamo de Murcia", "Fuente de Cantos", "Fuente de Pedro Naharro", "Fuente de Piedra", "Fuente de San Esteban, La", "Fuente de Santa Cruz", "Fuente del Arco", "Fuente del Maestre", "Fuente el Fresno", "Fuente el Olmo de Fuentidueña", "Fuente el Olmo de Íscar", "Fuente el Saúz", "Fuente el Saz de Jarama", "Fuente el Sol", "Fuente Encalada", "Fuente la Lancha", "Fuente la Reina", "Fuente Obejuna", "Fuente Palmera", "Fuente Vaqueros", "Fuente-Álamo", "Fuente-Olmedo", "Fuente-Tójar", "Fuentealbilla", "Fuentearmegil", "Fuentebureba", "Fuentecambrón", "Fuentecantos", "Fuentecén", "Fuenteguinaldo", "Fuenteheridos", "Fuentelahiguera de Albatages", "Fuentelapeña", "Fuentelcésped", "Fuentelencina", "Fuentelespino de Haro", "Fuentelespino de Moya", "Fuenteliante", "Fuentelisendo", "Fuentelmonge", "Fuentelsaz", "Fuentelsaz de Soria", "Fuentelviejo", "Fuentemolinos", "Fuentenava de Jábaga", "Fuentenebro", "Fuentenovilla", "Fuentepelayo", "Fuentepinilla", "Fuentepiñel", "Fuenterrebollo", "Fuenterroble de Salvatierra", "Fuenterrobles", "Fuentes", "Fuentes Calientes", "Fuentes Claras", "Fuentes de Andalucía", "Fuentes de Año", "Fuentes de Ayódar", "Fuentes de Béjar", "Fuentes de Carbajal", "Fuentes de Ebro", "Fuentes de Jiloca", "Fuentes de León", "Fuentes de Magaña", "Fuentes de Nava", "Fuentes de Oñoro", "Fuentes de Ropel", "Fuentes de Rubielos", "Fuentes de Valdepero", "Fuentesaúco", "Fuentesaúco de Fuentidueña", "Fuentesecas", "Fuentesoto", "Fuentespalda", "Fuentespina", "Fuentespreadas", "Fuentestrún", "Fuentidueña", "Fuentidueña de Tajo", "Fuerte del Rey", "Fuertescusa", "Fueva, La", "Fuliola, La", "Fulleda", "Funes", "Fustiñana"}));

        switchPanels(jPanel_altaReserva);
    }//GEN-LAST:event_jToggleButton_altaNuevaReservaActionPerformed

    private void jButton_voverReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_voverReservaActionPerformed
        // TODO add your handling code here:
        jComboBox_poblacionReserva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{}));
        jComboBox_chefReserva.removeAllItems();
        if (tipoUsuario == 1) {
            switchPanels(jPanel_opcionesAdmin);
        } else if (tipoUsuario == 2) {
            switchPanels(jPanel_opcionesChef);
        } else if (tipoUsuario == 3) {
            switchPanels(jPanel_opcionesCliente);
        }
    }//GEN-LAST:event_jButton_voverReservaActionPerformed

    private void jComboBox_poblacionReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_poblacionReservaActionPerformed
        // TODO add your handling code here:
        jComboBox_chefReserva.removeAllItems();
        if (!jComboBox_poblacionReserva.getSelectedItem().equals(null) && !jComboBox_poblacionReserva.getSelectedItem().equals("")) {
            String estado = "Activo";
            JSONArray respuesta = obtenerChefsEstado();
            JSONObject chef;
            //Se añaden los campos a la tabla
            for (int i = 0; i < respuesta.length(); i++) {
                chef = respuesta.getJSONObject(i);
                if (chef.get("estado").toString().equals(estado) && chef.get("poblacion").toString().equals(jComboBox_poblacionReserva.getSelectedItem().toString())) {
                    jComboBox_chefReserva.addItem(chef.get("usernameOrEmail").toString());
                }
            }
        }
    }//GEN-LAST:event_jComboBox_poblacionReservaActionPerformed

    private void jComboBox_filtroAZReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_filtroAZReservaActionPerformed
        // TODO add your handling code here:
        if (jComboBox_filtroAZReserva.getSelectedItem().toString().equals("De la A a la F")) {
            jComboBox_poblacionReserva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Ababuj", "Abades", "Abadía", "Abadín", "Abadiño", "Abáigar", "Abajas", "Ábalos", "Abaltzisketa", "Abánades", "Abanilla", "Abanto", "Abanto y Ciérvana-Abanto Zierbena", "Abarán", "Abarca de Campos", "Abárzuza/Abartzuza", "Abaurregaina/Abaurrea Alta", "Abaurrepea/Abaurrea Baja", "Abegondo", "Abejar", "Abejuela", "Abella de la Conca", "Abengibre", "Abenójar", "Aberin", "Abertura", "Abezames", "Abia de la Obispalía", "Abia de las Torres", "Abiego", "Abizanda", "Abla", "Ablanque", "Ablitas", "Abrera", "Abrucena", "Abusejo", "Acebeda, La", "Acebedo", "Acebo", "Acebrón, El", "Acedera", "Acehúche", "Aceituna", "Acered", "Aceuchal", "Adahuesca", "Adalia", "Adamuz", "Adanero", "Adeje", "Ademuz", "Adiós", "Adobes", "Ador", "Adra", "Adrada de Haza", "Adrada de Pirón", "Adrada, La", "Adradas", "Adrados", "Adsubia", "Aduna", "Agaete", "Agallas", "Àger", "Agolada", "Agón", "Agoncillo", "Agost", "Agramunt", "Ágreda", "Agres", "Agrón", "Aguadulce", "Aguarón", "Aguas Cándidas", "Aguasal", "Aguatón", "Aguaviva", "Agudo", "Agüero", "Aguilafuente", "Aguilar de Bureba", "Aguilar de Campoo", "Aguilar de Campos", "Aguilar de Codés", "Aguilar de la Frontera", "Aguilar de Segarra", "Aguilar del Alfambra", "Aguilar del Río Alhama", "Águilas", "Aguilón", "Agüimes", "Agullana", "Agullent", "Agulo", "Ahigal", "Ahigal de los Aceiteros", "Ahigal de Villarino", "Ahillones", "Aia", "Aibar/Oibar", "Aielo de Malferit", "Aielo de Rugat", "Aiguafreda", "Aiguamúrcia", "Aiguaviva", "Aigües", "Aín", "Aínsa-Sobrarbe", "Ainzón", "Aisa", "Aitona", "Aizarnazabal", "Ajalvir", "Ajamil de Cameros", "Ajangiz", "Ajofrín", "Alacant/Alicante", "Alacón", "Aladrén", "Alaejos", "Alagón", "Alagón del Río", "Alaior", "Alájar", "Alajeró", "Alameda", "Alameda de Gardón, La", "Alameda de la Sagra", "Alameda del Valle", "Alamedilla", "Alamedilla, La", "Alamillo", "Alaminos", "Álamo, El", "Alamús, Els", "Alange", "Alanís", "Alaquàs", "Alar del Rey", "Alaraz", "Alarba", "Alarcón", "Alarilla", "Alaró", "Alàs i Cerc", "Alatoz", "Alba", "Alba de Cerrato", "Alba de Tormes", "Alba de Yeltes", "Albacete", "Albagés, L", "Albaida", "Albaida del Aljarafe", "Albal", "Albalá", "Albaladejo", "Albaladejo del Cuende", "Albalat de la Ribera", "Albalat dels Sorells", "Albalat dels Tarongers", "Albalate de Cinca", "Albalate de las Nogueras", "Albalate de Zorita", "Albalate del Arzobispo", "Albalatillo", "Albánchez", "Albanchez de Mágina", "Albanyà", "Albares", "Albarracín", "Albarreal de Tajo", "Albatana", "Albatàrrec", "Albatera", "Albelda", "Albelda de Iregua", "Albendea", "Albendiego", "Albentosa", "Alberca de Záncara, La", "Alberca, La", "Alberguería de Argañán, La", "Alberic", "Alberite", "Alberite de San Juan", "Albero Alto", "Albero Bajo", "Alberuela de Tubo", "Albesa", "Albeta", "Albi, L", "Albillos", "Albinyana", "Albiol, L", "Albiztur", "Albocàsser", "Alboloduy", "Albolote", "Albondón", "Albons", "Alborache", "Alboraya", "Alborea", "Alborge", "Albornos", "Albox", "Albudeite", "Albuera, La", "Albuixech", "Albuñán", "Albuñol", "Albuñuelas", "Alburquerque", "Alcabón", "Alcadozo", "Alcaine", "Alcalá de Ebro", "Alcalá de Guadaíra", "Alcalá de Gurrea", "Alcalá de Henares", "Alcalá de la Selva", "Alcalá de la Vega", "Alcalá de los Gazules", "Alcalá de Moncayo", "Alcalà de Xivert", "Alcalá del Júcar", "Alcalá del Obispo", "Alcalá del Río", "Alcalá del Valle", "Alcalá la Real", "Alcalalí", "Alcampell", "Alcanadre", "Alcanar", "Alcanó", "Alcántara", "Alcantarilla", "Alcàntera de Xúquer", "Alcantud", "Alcañices", "Alcañiz", "Alcañizo", "Alcaracejos", "Alcaraz", "Alcarràs", "Alcàsser", "Alcaucín", "Alcaudete", "Alcaudete de la Jara", "Alcázar de San Juan", "Alcázar del Rey", "Alcazarén", "Alcázares, Los", "Alcoba", "Alcobendas", "Alcocer", "Alcocer de Planes", "Alcocero de Mola", "Alcohujate", "Alcolea", "Alcolea de Calatrava", "Alcolea de Cinca", "Alcolea de las Peñas", "Alcolea de Tajo", "Alcolea del Pinar", "Alcolea del Río", "Alcoleja", "Alcoletge", "Alcollarín", "Alconaba", "Alconada", "Alconada de Maderuelo", "Alconchel", "Alconchel de Ariza", "Alconchel de la Estrella", "Alconera", "Alcóntar", "Alcora, l", "Alcorcón", "Alcorisa", "Alcoroches", "Alcover", "Alcoy/Alcoi", "Alcubierre", "Alcubilla de Avellaneda", "Alcubilla de las Peñas", "Alcubilla de Nogales", "Alcubillas", "Alcublas", "Alcúdia", "Alcúdia de Crespins, l", "Alcudia de Monteagud", "Alcudia de Veo", "Alcúdia, l", "Alcuéscar", "Aldaia", "Aldea de San Miguel", "Aldea de San Nicolás, La", "Aldea del Cano", "Aldea del Fresno", "Aldea del Obispo", "Aldea del Obispo, La", "Aldea del Rey", "Aldea en Cabo", "Aldea Real", "Aldea, L", "Aldeacentenera", "Aldeacipreste", "Aldeadávila de la Ribera", "Aldealafuente", "Aldealcorvo", "Aldealengua", "Aldealengua de Pedraza", "Aldealengua de Santa María", "Aldealices", "Aldealpozo", "Aldealseñor", "Aldeamayor de San Martín", "Aldeanueva de Barbarroya", "Aldeanueva de Ebro", "Aldeanueva de Figueroa", "Aldeanueva de Guadalajara", "Aldeanueva de la Serrezuela", "Aldeanueva de la Sierra", "Aldeanueva de la Vera", "Aldeanueva de San Bartolomé", "Aldeanueva de Santa Cruz", "Aldeanueva del Camino", "Aldeanueva del Codonal", "Aldeaquemada", "Aldearrodrigo", "Aldearrubia", "Aldeaseca", "Aldeaseca de Alba", "Aldeaseca de la Frontera", "Aldeasoña", "Aldeatejada", "Aldeavieja de Tormes", "Aldehorno", "Aldehuela de Jerte", "Aldehuela de la Bóveda", "Aldehuela de Liestos", "Aldehuela de Periáñez", "Aldehuela de Yeltes", "Aldehuela del Codonal", "Aldehuela, La", "Aldehuelas, Las", "Aldeire", "Aldeonte", "Aldover", "Aledo", "Alegia", "Alegría-Dulantzi", "Aleixar, L", "Alella", "Alentisque", "Alerre", "Alesanco", "Alesón", "Alfacar", "Alfafar", "Alfafara", "Alfajarín", "Alfambra", "Alfamén", "Alfántega", "Alfara de Carles", "Alfara de la Baronia", "Alfara del Patriarca", "Alfaraz de Sayago", "Alfarnate", "Alfarnatejo", "Alfaro", "Alfarp", "Alfarràs", "Alfarrasí", "Alfàs del Pi, l", "Alfauir", "Alfés", "Alfondeguilla", "Alforja", "Alforque", "Alfoz", "Alfoz de Bricia", "Alfoz de Lloredo", "Alfoz de Quintanadueñas", "Alfoz de Santa Gadea", "Algaba, La", "Algadefe", "Algaida", "Algámitas", "Algar", "Algar de Mesa", "Algar de Palancia", "Algarinejo", "Algarra", "Algarrobo", "Algatocín", "Algeciras", "Algemesí", "Algerri", "Algete", "Algimia de Alfara", "Algimia de Almonacid", "Alginet", "Algodonales", "Algodre", "Algora", "Algorfa", "Alguaire", "Alguazas", "Algueña", "Alhabia", "Alhama de Almería", "Alhama de Aragón", "Alhama de Granada", "Alhama de Murcia", "Alhambra", "Alhaurín de la Torre", "Alhaurín el Grande", "Alhendín", "Alhóndiga", "Alía", "Aliaga", "Aliaguilla", "Alicún", "Alicún de Ortega", "Alija del Infantado", "Alins", "Alió", "Alique", "Aliseda", "Aliud", "Aljaraque", "Aljucén", "Alkiza", "Allande", "Allariz", "Allepuz", "Aller", "Allín/Allin", "Allo", "Alloza", "Allueva", "Almacelles", "Almáchar", "Almadén", "Almadén de la Plata", "Almadenejos", "Almadrones", "Almagro", "Almajano", "Almaluez", "Almansa", "Almanza", "Almaraz", "Almaraz de Duero", "Almarcha, La", "Almargen", "Almarza", "Almarza de Cameros", "Almàssera", "Almatret", "Almazán", "Almazora/Almassora", "Almazul", "Almedíjar", "Almedina", "Almedinilla", "Almegíjar", "Almeida de Sayago", "Almenar", "Almenar de Soria", "Almenara", "Almenara de Adaja", "Almenara de Tormes", "Almendra", "Almendral", "Almendral de la Cañada", "Almendralejo", "Almendro, El", "Almendros", "Almensilla", "Almería", "Almiserà", "Almochuel", "Almócita", "Almodóvar del Campo", "Almodóvar del Pinar", "Almodóvar del Río", "Almogía", "Almoguera", "Almohaja", "Almoharín", "Almoines", "Almolda, La", "Almonacid de la Cuba", "Almonacid de la Sierra", "Almonacid de Toledo", "Almonacid de Zorita", "Almonacid del Marquesado", "Almonaster la Real", "Almonte", "Almoradí", "Almorox", "Almoster", "Almudaina", "Almudévar", "Almunia de Doña Godina, La", "Almunia de San Juan", "Almuniente", "Almuñécar", "Almuradiel", "Almussafes", "Alobras", "Alocén", "Alonsotegi", "Álora", "Alòs de Balaguer", "Alosno", "Alovera", "Alozaina", "Alp", "Alpandeire", "Alpanseque", "Alpartir", "Alpedrete", "Alpens", "Alpeñés", "Alpera", "Alpicat", "Alpuente", "Alpujarra de la Sierra", "Alqueria dAsnar, l", "Alqueria de la Comtessa, l", "Alquerías del Niño Perdido", "Alquézar", "Alquife", "Alsodux", "Alt Àneu", "Altable", "Altafulla", "Altarejos", "Altea", "Altorricón", "Altos, Los", "Altsasu/Alsasua", "Altura", "Altzaga", "Altzo", "Alustante", "Alzira", "Amavida", "Amayuelas de Arriba", "Ambel", "Ambite", "Amer", "Ames", "Améscoa Baja", "Ametlla de Mar, L", "Ametlla del Vallès, L", "Ameyugo", "Amezketa", "Amieva", "Amoeiro", "Amorebieta-Etxano", "Amoroto", "Ampolla, L", "Amposta", "Ampudia", "Ampuero", "Amurrio", "Amusco", "Amusquillo", "Anadón", "Anaya", "Anaya de Alba", "Anchuelo", "Anchuras", "Ancín/Antzin", "Andavías", "Andilla", "Andoain", "Andorra", "Andosilla", "Andratx", "Andújar", "Anento", "Anglès", "Anglesola", "Angón", "Anguciana", "Angüés", "Anguiano", "Anguita", "Anguix", "Anievas", "Aniñón", "Anna", "Anoeta", "Anquela del Ducado", "Anquela del Pedregal", "Ansó", "Ansoáin/Antsoain", "Antas", "Antas de Ulla", "Antella", "Antequera", "Antigua", "Antigua, La", "Antigüedad", "Antillón", "Antzuola", "Anue", "Añana", "Añe", "Añón de Moncayo", "Añora", "Añorbe", "Añover de Tajo", "Añover de Tormes", "Aoiz/Agoitz", "Arabayona de Mógica", "Aracena", "Arafo", "Aragüés del Puerto", "Arahal", "Arahuetes", "Araitz", "Arakaldo", "Arakil", "Arama", "Aramaio", "Aranarache/Aranaratxe", "Arancón", "Aranda de Duero", "Aranda de Moncayo", "Arándiga", "Arandilla", "Arandilla del Arroyo", "Aranga", "Aranguren", "Aranjuez", "Arano", "Arantza", "Arantzazu", "Aranzueque", "Arañuel", "Arapiles", "Aras", "Aras de los Olmos", "Arauzo de Miel", "Arauzo de Salce", "Arauzo de Torre", "Arbancón", "Arbeca", "Arbeteta", "Arbizu", "Arbo", "Arboç, L", "Arboleas", "Arbolí", "Arbúcies", "Arcas del Villar", "Arce/Artzi", "Arcediano", "Arcenillas", "Archena", "Árchez", "Archidona", "Arcicóllar", "Arco, El", "Arconada", "Arcones", "Arcos", "Arcos de Jalón", "Arcos de la Frontera", "Arcos de la Polvorosa", "Arcos de la Sierra", "Arcos de las Salinas", "Arcos, Los", "Ardales", "Ardisa", "Ardón", "Areatza", "Arellano", "Arén", "Arenal, El", "Arenales de San Gregorio", "Arenas", "Arenas de Iguña", "Arenas de San Juan", "Arenas de San Pedro", "Arenas del Rey", "Arenillas", "Arenillas de Riopisuerga", "Arens de Lledó", "Arenys de Mar", "Arenys de Munt", "Arenzana de Abajo", "Arenzana de Arriba", "Ares", "Ares del Maestrat", "Areso", "Aretxabaleta", "Arevalillo", "Arevalillo de Cega", "Arévalo", "Arévalo de la Sierra", "Argamasilla de Alba", "Argamasilla de Calatrava", "Arganda del Rey", "Arganza", "Argañín", "Argavieso", "Argecilla", "Argelaguer", "Argelita", "Argençola", "Argente", "Argentera, L", "Argentona", "Argés", "Argoños", "Arguedas", "Arguis", "Arguisuelas", "Argujillo", "Aria", "Ariany", "Aribe", "Arico", "Arija", "Ariño", "Ariza", "Arjona", "Arjonilla", "Arlanzón", "Armallones", "Armañanzas", "Armentera, L", "Armenteros", "Armilla", "Armiñón", "Armuña", "Armuña de Almanzora", "Armuña de Tajuña", "Arnedillo", "Arnedo", "Arnes", "Arnoia, A", "Arnuero", "Aroche", "Arona", "Arquillinos", "Arquillos", "Arrabalde", "Arraia-Maeztu", "Arrancacepas", "Arrankudiaga", "Arrasate/Mondragón", "Arratzu", "Arraya de Oca", "Arrazua-Ubarrundia", "Arrecife", "Arredondo", "Arres", "Arriate", "Arrieta", "Arrigorriaga", "Arroba de los Montes", "Arróniz", "Arroyo de la Encomienda", "Arroyo de la Luz", "Arroyo de las Fraguas", "Arroyo de San Serván", "Arroyo del Ojanco", "Arroyomolinos", "Arroyomolinos", "Arroyomolinos de la Vera", "Arroyomolinos de León", "Arruazu", "Arrúbal", "Arsèguel", "Artà", "Artajona", "Artana", "Artazu", "Artea", "Arteixo", "Artenara", "Artés", "Artesa de Lleida", "Artesa de Segre", "Artieda", "Artzentales", "Artziniega", "Arucas", "Arzúa", "Ascó", "Asín", "Aspa", "Aspariegos", "Asparrena", "Aspe", "Asteasu", "Astigarraga", "Astillero, El", "Astorga", "Astudillo", "Asturianos", "Atajate", "Atalaya", "Atalaya del Cañavate", "Atalaya, La", "Atanzón", "Atapuerca", "Ataquines", "Atarfe", "Ataun", "Atazar, El", "Atea", "Ateca", "Atez", "Atienza", "Atxondo", "Atzeneta dAlbaida", "Atzeneta del Maestrat", "Aulesti", "Auñón", "Auritz/Burguete", "Ausejo", "Ausejo de la Sierra", "Ausines, Los", "Autilla del Pino", "Autillo de Campos", "Autol", "Aveinte", "Avellaneda", "Avellanes i Santa Linya, Les", "Avellanosa de Muñó", "Avià", "Ávila", "Avilés", "Avinyó", "Avinyonet de Puigventós", "Avinyonet del Penedès", "Avión", "Ayala/Aiara", "Ayamonte", "Ayegui/Aiegi", "Ayerbe", "Ayllón", "Ayna", "Ayódar", "Ayoó de Vidriales", "Ayora", "Ayuela", "Azagra", "Azaila", "Azanuy-Alins", "Azara", "Azkoitia", "Azlor", "Aznalcázar", "Aznalcóllar", "Azofra", "Azpeitia", "Azuaga", "Azuara", "Azuébar", "Azuelo", "Azuqueca de Henares", "Azután", "Babilafuente", "Bacares", "Badajoz", "Badalona", "Badarán", "Bádenas", "Badia del Vallès", "Badolatosa", "Badules", "Baélls", "Baena", "Baeza", "Bagà", "Báguena", "Bagüés", "Bahabón", "Bahabón de Esgueva", "Baides", "Bailén", "Bailo", "Baiona", "Baix Pallars", "Bakaiku", "Bakio", "Balaguer", "Balazote", "Balbases, Los", "Balboa", "Balconchán", "Baldellou", "Baleira", "Balenyà", "Baliarrain", "Ballestero, El", "Ballesteros de Calatrava", "Ballobar", "Balmaseda", "Balones", "Balsa de Ves", "Balsareny", "Baltanás", "Baltar", "Banastás", "Bande", "Banyalbufar", "Banyeres de Mariola", "Banyeres del Penedès", "Banyoles", "Baña, A", "Bañares", "Bañeza, La", "Bañobárez", "Bañón", "Baños de Ebro/Mañueta", "Baños de la Encina", "Baños de Molgas", "Baños de Montemayor", "Baños de Río Tobía", "Baños de Rioja", "Baños de Tajo", "Baños de Valdearados", "Bañuelos", "Bañuelos de Bureba", "Baquerín de Campos", "Barajas de Melo", "Barakaldo", "Baralla", "Barañain", "Baraona", "Barásoain", "Barbadás", "Barbadillo", "Barbadillo de Herreros", "Barbadillo del Mercado", "Barbadillo del Pez", "Barbalos", "Barbarin", "Barbastro", "Barbate", "Barbens", "Barberà de la Conca", "Barberà del Vallès", "Bárboles", "Barbolla", "Barbués", "Barbuñales", "Barca", "Bárcabo", "Barcarrota", "Barcelona", "Bárcena de Campos", "Bárcena de Cicero", "Bárcena de Pie de Concha", "Barceo", "Barchín del Hoyo", "Barcial de la Loma", "Barcial del Barco", "Barcience", "Barco de Ávila, El", "Barco de Valdeorras, O", "Barcones", "Bardallur", "Bareyo", "Bargas", "Bargota", "Barillas", "Barjas", "Barlovento", "Baronia de Rialb, La", "Barracas", "Barrachina", "Barraco, El", "Barrado", "Barrax", "Barreiros", "Barrika", "Barrio de Muñó", "Barriopedro", "Barrios de Bureba, Los", "Barrios de Colina", "Barrios de Luna, Los", "Barrios, Los", "Barro", "Barromán", "Barruecopardo", "Barruelo de Santullán", "Barruelo del Valle", "Barrundia", "Barx", "Barxeta", "Basaburua", "Basardilla", "Basauri", "Bàscara", "Basconcillos del Tozo", "Báscones de Ojeda", "Bascuñana", "Bascuñana de San Pedro", "Bassella", "Bastida, La", "Batea", "Baterno", "Batres", "Bausen", "Bayárcal", "Bayarque", "Bayubas de Abajo", "Bayubas de Arriba", "Baza", "Baztan", "Bea", "Beade", "Beamud", "Beariz", "Beas", "Beas de Granada", "Beas de Guadix", "Beas de Segura", "Beasain", "Becedas", "Becedillas", "Beceite", "Becerreá", "Becerril de Campos", "Becerril de la Sierra", "Becilla de Valderaduey", "Bédar", "Bedia", "Bedmar y Garcíez", "Begíjar", "Begonte", "Begues", "Begur", "Beintza-Labaien", "Beire", "Beires", "Beizama", "Béjar", "Bejís", "Belalcázar", "Belascoáin", "Belauntza", "Belbimbre", "Belchite", "Beleña", "Bèlgida", "Belianes", "Belinchón", "Bell-lloc dUrgell", "Bellaguarda", "Bellcaire dEmpordà", "Bellcaire dUrgell", "Bellmunt del Priorat", "Bellmunt dUrgell", "Bello", "Bellprat", "Bellpuig", "Bellreguard", "Bellús", "Bellvei", "Bellver de Cerdanya", "Bellvís", "Belmez", "Bélmez de la Moraleda", "Belmonte", "Belmonte de Campos", "Belmonte de Gracián", "Belmonte de Miranda", "Belmonte de San José", "Belmonte de Tajo", "Belmontejo", "Belorado", "Belver de Cinca", "Belver de los Montes", "Belvís de la Jara", "Belvís de Monroy", "Bembibre", "Benabarre", "Benacazón", "Benadalid", "Benafarces", "Benafer", "Benafigos", "Benagéber", "Benaguasil", "Benahadux", "Benahavís", "Benalauría", "Benalmádena", "Benalúa", "Benalúa de las Villas", "Benalup-Casas Viejas", "Benamargosa", "Benamaurel", "Benamejí", "Benamocarra", "Benaocaz", "Benaoján", "Benarrabá", "Benasal", "Benasau", "Benasque", "Benatae", "Benavent de Segrià", "Benavente", "Benavides", "Benavites", "Benegiles", "Beneixama", "Beneixida", "Benejúzar", "Benetússer", "Benferri", "Beniarbeig", "Beniardá", "Beniarjó", "Beniarrés", "Beniatjar", "Benicarló", "Benicasim/Benicàssim", "Benicolet", "Benicull de Xúquer", "Benidoleig", "Benidorm", "Beniel", "Benifaió", "Benifairó de la Valldigna", "Benifairó de les Valls", "Benifallet", "Benifallim", "Benifato", "Beniflá", "Benigánim", "Benigembla", "Benijófar", "Benilloba", "Benillup", "Benimantell", "Benimarfull", "Benimassot", "Benimeli", "Benimodo", "Benimuslem", "Beniparrell", "Benirredrà", "Benisanó", "Benissa", "Benissanet", "Benissoda", "Benisuera", "Benitachell/Poble Nou de Benitatxell, el", "Benitagla", "Benizalón", "Benlloch", "Benquerencia", "Benquerencia de la Serena", "Bentarique", "Benuza", "Bera", "Berango", "Berantevilla", "Beranuy", "Berastegi", "Beratón", "Berbegal", "Berberana", "Berbinzana", "Berceo", "Bercero", "Berceruelo", "Bérchules", "Bercial", "Bercial de Zapardiel", "Bercianos del Páramo", "Bercianos del Real Camino", "Bercimuel", "Berdejo", "Berga", "Bergara", "Bergasa", "Bergasillas Bajera", "Berge", "Bergondo", "Beriáin", "Berja", "Berlanas, Las", "Berlanga", "Berlanga de Duero", "Berlanga del Bierzo", "Berlangas de Roa", "Bermellar", "Bermeo", "Bermillo de Sayago", "Bernardos", "Bernedo", "Berninches", "Bernuy de Porreros", "Bernuy-Zapardiel", "Berriatua", "Berrioplano/Berriobeiti", "Berriozar", "Berriz", "Berrobi", "Berrocal", "Berrocal de Huebra", "Berrocal de Salvatierra", "Berrocalejo", "Berrocalejo de Aragona", "Berrueces", "Berrueco", "Berrueco, El", "Bertizarana", "Berzocana", "Berzosa de Bureba", "Berzosa del Lozoya", "Berzosilla", "Besalú", "Bescanó", "Betancuria", "Betanzos", "Betelu", "Bétera", "Beteta", "Betxí", "Beuda", "Bezares", "Bezas", "Biar", "Bicorp", "Bidaurreta", "Bidegoian", "Biel", "Bielsa", "Bienservida", "Bienvenida", "Bierge", "Biescas", "Bigastro", "Bigues i Riells", "Bijuesca", "Bilbao", "Bimenes", "Binaced", "Binéfar", "Binissalem", "Biosca", "Biota", "Bisaurri", "Bisbal de Falset, La", "Bisbal del Penedès, La", "Bisbal dEmpordà, La", "Biscarrués", "Bisimbre", "Biure", "Biurrun-Olcoz", "Blacos", "Blanca", "Blancafort", "Blancas", "Blancos, Os", "Blanes", "Blascomillán", "Blasconuño de Matacabras", "Blascosancho", "Blázquez, Los", "Blecua y Torres", "Blesa", "Bliecos", "Boada", "Boada de Campos", "Boadella i les Escaules", "Boadilla de Rioseco", "Boadilla del Camino", "Boadilla del Monte", "Boal", "Boalo, El", "Bobadilla", "Bobadilla del Campo", "Boborás", "Boca de Huérgano", "Bocairent", "Boceguillas", "Bocigas", "Bocos de Duero", "Bodera, La", "Bodón, El", "Bodonal de la Sierra", "Boecillo", "Bogajo", "Bogarra", "Bohodón, El", "Bohonal de Ibor", "Bohoyo", "Boimorto", "Boiro", "Bola, A", "Bolaños de Calatrava", "Bolaños de Campos", "Bolbaite", "Bollullos de la Mitación", "Bollullos Par del Condado", "Bolo, O", "Boltaña", "Bolulla", "Bolvir", "Bonansa", "Bonares", "Bonastre", "Bonete", "Boniches", "Bonilla de la Sierra", "Bonillo, El", "Bonrepòs i Mirambell", "Boñar", "Boqueixón", "Boquiñeni", "Borau", "Bordalba", "Bòrdes, Es", "Bordils", "Bordón", "Borge, El", "Borges Blanques, Les", "Borges del Camp, Les", "Borja", "Borjabad", "Bormujos", "Bornos", "Borobia", "Borox", "Borrassà", "Borredà", "Borrenes", "Borriana/Burriana", "Borriol", "Bosque, El", "Bossòst", "Bot", "Botarell", "Botija", "Botorrita", "Bouza, La", "Bóveda", "Bóveda de Toro, La", "Bóveda del Río Almar", "Bovera", "Bozoó", "Brabos", "Bràfim", "Brahojos de Medina", "Brañosera", "Braojos", "Brazacorta", "Brazatortas", "Brazuelo", "Brea de Aragón", "Brea de Tajo", "Breda", "Brenes", "Breña Alta", "Breña Baja", "Bretó", "Bretocino", "Brieva", "Brieva de Cameros", "Brihuega", "Brime de Sog", "Brime de Urz", "Brincones", "Briñas", "Brión", "Briones", "Briviesca", "Bronchales", "Broto", "Brozas", "Bruc, El", "Brull, El", "Brunete", "Brunyola", "Buberos", "Bubierca", "Bubión", "Buciegas", "Budia", "Buenache de Alarcón", "Buenache de la Sierra", "Buenamadre", "Buenaventura", "Buenavista", "Buenavista de Valdavia", "Buenavista del Norte", "Buendía", "Bueña", "Bueu", "Bufali", "Bugarra", "Bugedo", "Búger", "Buitrago", "Buitrago del Lozoya", "Bujalance", "Bujalaro", "Bujaraloz", "Bularros", "Bulbuente", "Bullas", "Buniel", "Bunyola", "Buñol", "Buñuel", "Burbáguena", "Burela", "Bureta", "Burganes de Valverde", "Burgo de Ebro, El", "Burgo de Osma-Ciudad de Osma", "Burgo Ranero, El", "Burgo, El", "Burgohondo", "Burgos", "Burgui/Burgi", "Burguillos", "Burguillos de Toledo", "Burguillos del Cerro", "Burjassot", "Burlada/Burlata", "Burón", "Burujón", "Busot", "Busquístar", "Bustares", "Bustarviejo", "Buste, El", "Bustillo de Chaves", "Bustillo de la Vega", "Bustillo del Oro", "Bustillo del Páramo", "Bustillo del Páramo de Carrión", "Busto de Bureba", "Busto, El", "Busturia", "Cabacés", "Cabaco, El", "Caballar", "Cabana de Bergantiños", "Cabanabona", "Cabanas", "Cabanelles", "Cabanes", "Cabanes", "Cabanillas", "Cabanillas de la Sierra", "Cabanillas del Campo", "Cabanyes, Les", "Cabañas de Ebro", "Cabañas de la Sagra", "Cabañas de Polendos", "Cabañas de Sayago", "Cabañas de Yepes", "Cabañas del Castillo", "Cabañas Raras", "Cabañes de Esgueva", "Cabeza de Béjar, La", "Cabeza del Buey", "Cabeza del Caballo", "Cabeza la Vaca", "Cabezabellosa", "Cabezabellosa de la Calzada", "Cabezamesada", "Cabezarados", "Cabezarrubias del Puerto", "Cabezas de Alambre", "Cabezas de San Juan, Las", "Cabezas del Pozo", "Cabezas del Villar", "Cabezas Rubias", "Cabezón de Cameros", "Cabezón de la Sal", "Cabezón de la Sierra", "Cabezón de Liébana", "Cabezón de Pisuerga", "Cabezón de Valderaduey", "Cabezuela", "Cabezuela del Valle", "Cabizuela", "Cabó", "Cabolafuente", "Cabra", "Cabra de Mora", "Cabra del Camp", "Cabra del Santo Cristo", "Cabrales", "Cabranes", "Cabredo", "Cabrejas del Campo", "Cabrejas del Pinar", "Cabrera dAnoia", "Cabrera de Mar", "Cabrera, La", "Cabrerizos", "Cabrero", "Cabreros del Monte", "Cabreros del Río", "Cabrillanes", "Cabrillas", "Cabrils", "Cabuérniga", "Cacabelos", "Cáceres", "Cachorrilla", "Cacín", "Cadalso", "Cadalso de los Vidrios", "Cadaqués", "Cádiar", "Cádiz", "Cadreita", "Cadrete", "Cájar", "Cala", "Calabazas de Fuentidueña", "Calaceite", "Calaf", "Calafell", "Calahorra", "Calahorra de Boedo", "Calahorra, La", "Calamocha", "Calamonte", "Calanda", "Calañas", "Calasparra", "Calatañazor", "Calatayud", "Calatorao", "Calcena", "Caldas de Reis", "Caldearenas", "Calders", "Caldes de Malavella", "Caldes de Montbui", "Caldes dEstrac", "Calella", "Calera de León", "Calera y Chozas", "Caleruega", "Caleruela", "Calicasas", "Càlig", "Calldetenes", "Calles", "Callosa de Segura", "Callosa dEn Sarrià", "Callús", "Calmarza", "Calomarde", "Calonge", "Calonge de Segarra", "Calp", "Caltojar", "Calvarrasa de Abajo", "Calvarrasa de Arriba", "Calvià", "Calvos de Randín", "Calzada de Béjar, La", "Calzada de Calatrava", "Calzada de Don Diego", "Calzada de los Molinos", "Calzada de Oropesa", "Calzada de Valdunciel", "Calzada del Coto", "Calzadilla", "Calzadilla de los Barros", "Calzadilla de Tera", "Camaleño", "Camañas", "Camarasa", "Camarena", "Camarena de la Sierra", "Camarenilla", "Camargo", "Camarillas", "Camariñas", "Camarles", "Camarma de Esteruelas", "Camarzana de Tera", "Camas", "Cambados", "Cambil", "Cambre", "Cambrils", "Caminomorisco", "Caminreal", "Camós", "Campana, La", "Campanario", "Campanet", "Campaspero", "Campazas", "Campdevànol", "Campelles", "Campello, el", "Campezo/Kanpezu", "Campillo de Altobuey", "Campillo de Aragón", "Campillo de Aranda", "Campillo de Arenas", "Campillo de Azaba", "Campillo de Deleitosa", "Campillo de Dueñas", "Campillo de la Jara, El", "Campillo de Llerena", "Campillo de Ranas", "Campillo, El", "Campillo, El", "Campillos", "Campillos-Paravientos", "Campillos-Sierra", "Campins", "Campisábalos", "Campllong", "Campo", "Campo de Criptana", "Campo de Mirra/Camp de Mirra, el", "Campo de Peñaranda, El", "Campo de San Pedro", "Campo de Villavidel", "Campo Lameiro", "Campo Lugar", "Campo Real", "Campofrío", "Campolara", "Camponaraya", "Campoo de Enmedio", "Campoo de Yuso", "Camporredondo", "Camporrélls", "Camporrobles", "Campos", "Campos del Paraíso", "Campos del Río", "Campotéjar", "Camprodon", "Camprovín", "Camuñas", "Canal de Berdún", "Canalejas de Peñafiel", "Canalejas del Arroyo", "Canales", "Canales de la Sierra", "Canals", "Candamo", "Candasnos", "Candelaria", "Candelario", "Candeleda", "Candilichera", "Candín", "Canejan", "Canena", "Canencia", "Canet dAdri", "Canet de Mar", "Canet dEn Berenguer", "Canet lo Roig", "Canfranc", "Cangas", "Cangas de Onís", "Cangas del Narcea", "Canicosa de la Sierra", "Caniles", "Canillas de Abajo", "Canillas de Aceituno", "Canillas de Albaida", "Canillas de Esgueva", "Canillas de Río Tuerto", "Canjáyar", "Canonja, La", "Canovelles", "Cànoves i Samalús", "Canredondo", "Cantabrana", "Cantagallo", "Cantalapiedra", "Cantalejo", "Cantallops", "Cantalojas", "Cantalpino", "Cantaracillo", "Cantavieja", "Cantillana", "Cantimpalos", "Cantiveros", "Cantoria", "Canyelles", "Cañada", "Cañada de Benatanduz", "Cañada de Calatrava", "Cañada de Verich, La", "Cañada del Hoyo", "Cañada Juncosa", "Cañada Rosal", "Cañada Vellida", "Cañamaque", "Cañamares", "Cañamero", "Cáñar", "Cañas", "Cañavate, El", "Cañaveral", "Cañaveral de León", "Cañaveras", "Cañaveruelas", "Cañete", "Cañete de las Torres", "Cañete la Real", "Cañiza, A", "Cañizal", "Cañizar", "Cañizar del Olivar", "Cañizares", "Cañizo", "Capafonts", "Caparroso", "Capçanes", "Capdepera", "Capdesaso", "Capela, A", "Capella", "Capellades", "Capileira", "Capilla", "Capillas", "Capmany", "Capolat", "Carabantes", "Carabaña", "Caracena", "Caracuel de Calatrava", "Carataunas", "Caravaca de la Cruz", "Caravia", "Carazo", "Carbajales de Alba", "Carbajo", "Carbajosa de la Sagrada", "Carballeda de Avia", "Carballeda de Valdeorras", "Carballedo", "Carballiño, O", "Carballo", "Carbellino", "Carboneras", "Carboneras de Guadazaón", "Carbonero el Mayor", "Carboneros", "Carcaboso", "Carcabuey", "Carcaixent", "Cárcar", "Carcastillo", "Carcedo de Bureba", "Carcedo de Burgos", "Carcelén", "Càrcer", "Cárcheles", "Cardedeu", "Cárdenas", "Cardenete", "Cardeña", "Cardeñadijo", "Cardeñajimeno", "Cardeñosa", "Cardeñosa de Volpejera", "Cardeñuela Riopico", "Cardiel de los Montes", "Cardona", "Cardoso de la Sierra, El", "Carenas", "Cariñena", "Cariño", "Carlet", "Carlota, La", "Carme", "Carmena", "Cármenes", "Carmona", "Carmonita", "Carnota", "Carolina, La", "Carpio", "Carpio de Azaba", "Carpio de Tajo, El", "Carpio, El", "Carracedelo", "Carral", "Carranque", "Carrascal de Barregas", "Carrascal del Obispo", "Carrascal del Río", "Carrascalejo", "Carrascalejo, El", "Carrascosa", "Carrascosa de Abajo", "Carrascosa de Haro", "Carrascosa de la Sierra", "Carratraca", "Carreño", "Carrera, La", "Carrias", "Carriches", "Carrícola", "Carrión de Calatrava", "Carrión de los Céspedes", "Carrión de los Condes", "Carrizo", "Carrizosa", "Carrocera", "Cartagena", "Cartajima", "Cártama", "Cartaya", "Cartelle", "Cartes", "Carucedo", "Casa de Uceda", "Casabermeja", "Casafranca", "Casalarreina", "Casar de Cáceres", "Casar de Escalona, El", "Casar de Palomero", "Casar, El", "Casarabonela", "Casarejos", "Casares", "Casares de las Hurdes", "Casariche", "Casarrubios del Monte", "Casarrubuelos", "Casas Altas", "Casas Bajas", "Casas de Benítez", "Casas de Don Antonio", "Casas de Don Gómez", "Casas de Don Pedro", "Casas de Fernando Alonso", "Casas de Garcimolina", "Casas de Guijarro", "Casas de Haro", "Casas de Juan Núñez", "Casas de Lázaro", "Casas de los Pinos", "Casas de Millán", "Casas de Miravete", "Casas de Reina", "Casas de San Galindo", "Casas de Ves", "Casas del Castañar", "Casas del Conde, Las", "Casas del Monte", "Casas del Puerto", "Casas-Ibáñez", "Casasbuenas", "Casaseca de Campeán", "Casaseca de las Chanas", "Casasimarro", "Casasola", "Casasola de Arión", "Casatejada", "Casavieja", "Casbas de Huesca", "Cascajares de Bureba", "Cascajares de la Sierra", "Cascante", "Cascante del Río", "Cáseda", "Caseres", "Casillas", "Casillas de Coria", "Casillas de Flores", "Casinos", "Casla", "Caso", "Caspe", "Caspueñas", "Cassà de la Selva", "Casserres", "Castalla", "Castañar de Ibor", "Castañares de Rioja", "Castañeda", "Castaño del Robledo", "Cástaras", "Castejón", "Castejón", "Castejón de Alarba", "Castejón de Henares", "Castejón de las Armas", "Castejón de Monegros", "Castejón de Sos", "Castejón de Tornos", "Castejón de Valdejasa", "Castejón del Puente", "Castel de Cabra", "Castelflorite", "Castell de Cabres", "Castell de Castells", "Castell de Guadalest, el", "Castell de lAreny", "Castell de Mur", "Castell-Platja dAro", "Castell, Es", "Castellanos de Castro", "Castellanos de Moriscos", "Castellanos de Villiquera", "Castellanos de Zapardiel", "Castellar", "Castellar de la Frontera", "Castellar de la Muela", "Castellar de la Ribera", "Castellar de nHug", "Castellar de Santiago", "Castellar del Riu", "Castellar del Vallès", "Castellar, El", "Castellbell i el Vilar", "Castellbisbal", "Castellcir", "Castelldans", "Castelldefels", "Castellet i la Gornal", "Castellfollit de la Roca", "Castellfollit de Riubregós", "Castellfollit del Boix", "Castellfort", "Castellgalí", "Castellnou de Bages", "Castellnou de Seana", "Castellnovo", "Castelló de Farfanya", "Castelló de Rugat", "Castelló dEmpúries", "Castellolí", "Castellón de la Plana/Castelló de la Plana", "Castellonet de la Conquesta", "Castellote", "Castellserà", "Castellterçol", "Castellvell del Camp", "Castellví de la Marca", "Castellví de Rosanes", "Castelnou", "Castelserás", "Castielfabib", "Castiello de Jaca", "Castigaleu", "Castil de Peones", "Castil de Vela", "Castilblanco", "Castilblanco de los Arroyos", "Castildelgado", "Castilfalé", "Castilforte", "Castilfrío de la Sierra", "Castiliscar", "Castillazuelo", "Castilleja de Guzmán", "Castilleja de la Cuesta", "Castilleja del Campo", "Castilléjar", "Castillejo de Iniesta", "Castillejo de Martín Viejo", "Castillejo de Mesleón", "Castillejo de Robledo", "Castillejo-Sierra", "Castillo de Bayuela", "Castillo de Garcimuñoz", "Castillo de las Guardas, El", "Castillo de Locubín", "Castillo de Villamalefa", "Castillo-Albaráñez", "Castillonroy", "Castillonuevo", "Castilnuevo", "Castilruiz", "Castraz", "Castrejón de la Peña", "Castrejón de Trabancos", "Castrelo de Miño", "Castrelo do Val", "Castril", "Castrillo de Cabrera", "Castrillo de Don Juan", "Castrillo de Duero", "Castrillo de la Guareña", "Castrillo de la Reina", "Castrillo de la Valduerna", "Castrillo de la Vega", "Castrillo de Onielo", "Castrillo de Riopisuerga", "Castrillo de Villavega", "Castrillo del Val", "Castrillo Matajudíos", "Castrillo-Tejeriego", "Castrillón", "Castro Caldelas", "Castro de Filabres", "Castro de Fuentidueña", "Castro de Rei", "Castro del Río", "Castro-Urdiales", "Castrobol", "Castrocalbón", "Castrocontrigo", "Castrodeza", "Castrogonzalo", "Castrojeriz", "Castrojimeno", "Castromembibre", "Castromocho", "Castromonte", "Castronuevo", "Castronuevo de Esgueva", "Castronuño", "Castropodame", "Castropol", "Castroponce", "Castroserna de Abajo", "Castroserracín", "Castrotierra de Valmadrigal", "Castroverde", "Castroverde de Campos", "Castroverde de Cerrato", "Castroviejo", "Castuera", "Catadau", "Catarroja", "Catí", "Catllar, El", "Catoira", "Catral", "Caudete", "Caudete de las Fuentes", "Caudiel", "Cava", "Cavia", "Cayuela", "Cazalegas", "Cazalilla", "Cazalla de la Sierra", "Cazorla", "Cazurra", "Cea", "Cebanico", "Cebolla", "Cebrecos", "Cebreros", "Cebrones del Río", "Ceclavín", "Cedeira", "Cedillo", "Cedillo de la Torre", "Cedillo del Condado", "Cedrillas", "Cee", "Cehegín", "Ceinos de Campos", "Celada del Camino", "Celadas", "Celanova", "Cella", "Cellera de Ter, La", "Cellorigo", "Celrà", "Cendea de Olza/Oltza Zendea", "Cendejas de Enmedio", "Cendejas de la Torre", "Cenes de la Vega", "Cenicero", "Cenicientos", "Cenizate", "Cenlle", "Centelles", "Centenera", "Centenera de Andaluz", "Cepeda", "Cepeda la Mora", "Cerbón", "Cerceda", "Cercedilla", "Cercs", "Cerdà", "Cerdanyola del Vallès", "Cerdedo", "Cerdido", "Cereceda de la Sierra", "Cerecinos de Campos", "Cerecinos del Carrizal", "Cerezal de Peñahorcada", "Cerezo", "Cerezo de Abajo", "Cerezo de Arriba", "Cerezo de Río Tirón", "Cernadilla", "Cerollera, La", "Cerralbo", "Cerralbos, Los", "Cerratón de Juarros", "Cerro de Andévalo, El", "Cerro, El", "Cervantes", "Cervatos de la Cueza", "Cervelló", "Cervera", "Cervera de Buitrago", "Cervera de la Cañada", "Cervera de los Montes", "Cervera de Pisuerga", "Cervera del Llano", "Cervera del Maestre", "Cervera del Río Alhama", "Cerveruela", "Cervià de les Garrigues", "Cervià de Ter", "Cervillego de la Cruz", "Cervo", "Cespedosa de Tormes", "Cesuras", "Cetina", "Ceuta", "Ceutí", "Cevico de la Torre", "Cevico Navero", "Chagarcía Medianero", "Chalamera", "Chamartín", "Chandrexa de Queixa", "Chantada", "Chañe", "Chapinería", "Chauchina", "Checa", "Cheles", "Chella", "Chelva", "Chequilla", "Chera", "Chercos", "Chert/Xert", "Cheste", "Chía", "Chiclana de la Frontera", "Chiclana de Segura", "Chilches/Xilxes", "Chillarón de Cuenca", "Chillarón del Rey", "Chillón", "Chilluévar", "Chiloeches", "Chimeneas", "Chimillas", "Chinchilla de Monte-Aragón", "Chinchón", "Chipiona", "Chiprana", "Chirivel", "Chiva", "Chodes", "Chodos/Xodos", "Chóvar", "Chozas de Abajo", "Chozas de Canales", "Chucena", "Chueca", "Chulilla", "Chumillas", "Churriana de la Vega", "Ciadoncha", "Cidamón", "Cidones", "Ciempozuelos", "Cierva, La", "Cieza", "Cieza", "Cifuentes", "Cigales", "Cigudosa", "Ciguñuela", "Cihuela", "Cihuri", "Cijuela", "Cillán", "Cillaperlata", "Cilleros", "Cilleros de la Bastida", "Cilleruelo de Abajo", "Cilleruelo de Arriba", "Cilleruelo de San Mamés", "Cillorigo de Liébana", "Cimanes de la Vega", "Cimanes del Tejar", "Cimballa", "Cinco Olivas", "Cincovillas", "Cinctorres", "Cintruénigo", "Cipérez", "Cirat", "Cirauqui/Zirauki", "Ciria", "Ciriza/Ziritza", "Ciruelas", "Ciruelos", "Ciruelos de Cervera", "Ciruelos del Pinar", "Cirueña", "Cirujales del Río", "Cisla", "Cisneros", "Cistella", "Cistérniga", "Cistierna", "Ciudad Real", "Ciudad Rodrigo", "Ciutadella de Menorca", "Ciutadilla", "Cizur", "Clarés de Ribota", "Clariana de Cardener", "Clavijo", "Coaña", "Cóbdar", "Cobeja", "Cobeña", "Cobeta", "Cobisa", "Cobos de Cerrato", "Cobos de Fuentidueña", "Cobreros", "Coca", "Coca de Alba", "Cocentaina", "Codo", "Codoñera, La", "Codorniz", "Codos", "Codosera, La", "Cofrentes", "Cogeces de Íscar", "Cogeces del Monte", "Cogollor", "Cogollos", "Cogollos de Guadix", "Cogollos de la Vega", "Cogolludo", "Cogul, El", "Coín", "Coirós", "Colera", "Coles", "Colilla, La", "Colindres", "Coll de Nargó", "Collado", "Collado de Contreras", "Collado del Mirón", "Collado Hermoso", "Collado Mediano", "Collado Villalba", "Collazos de Boedo", "Collbató", "Colldejou", "Collsuspina", "Colmenar", "Colmenar de Montemayor", "Colmenar de Oreja", "Colmenar del Arroyo", "Colmenar Viejo", "Colmenarejo", "Colomera", "Colomers", "Colunga", "Colungo", "Coma i la Pedra, La", "Comares", "Comillas", "Cómpeta", "Conca de Dalt", "Condado de Castilnovo", "Condado de Treviño", "Condemios de Abajo", "Condemios de Arriba", "Conesa", "Confrides", "Congosto", "Congosto de Valdavia", "Congostrina", "Conil de la Frontera", "Conquista", "Conquista de la Sierra", "Consell", "Constantí", "Constantina", "Constanzana", "Consuegra", "Contamina", "Contreras", "Coomonte", "Copernal", "Copons", "Corbalán", "Corbera", "Corbera de Llobregat", "Corbera dEbre", "Corbillos de los Oteros", "Corbins", "Corçà", "Corcos", "Corcubión", "Córdoba", "Cordobilla de Lácara", "Cordovilla", "Cordovilla la Real", "Cordovín", "Corduente", "Corella", "Corera", "Coreses", "Corgo, O", "Coria", "Coria del Río", "Coripe", "Coristanco", "Cornago", "Cornellà de Llobregat", "Cornellà del Terri", "Cornudella de Montsant", "Coronada, La", "Coronil, El", "Corpa", "Corporales", "Corral de Almaguer", "Corral de Ayllón", "Corral de Calatrava", "Corral-Rubio", "Corrales de Buelna, Los", "Corrales de Duero", "Corrales del Vino", "Corrales, Los", "Corte de Peleas", "Corteconcepción", "Cortegada", "Cortegana", "Cortelazor", "Cortes", "Cortes de Aragón", "Cortes de Arenoso", "Cortes de Baza", "Cortes de la Frontera", "Cortes de Pallás", "Cortes y Graena", "Cortijos, Los", "Corullón", "Coruña del Conde", "Coruña, A", "Corvera de Asturias", "Corvera de Toranzo", "Cosa", "Coscurita", "Coslada", "Cospeito", "Costitx", "Costur", "Cosuenda", "Cotanes del Monte", "Cotes", "Cotillas", "Cotobade", "Covaleda", "Covarrubias", "Covelo", "Coves de Vinromà, les", "Cox", "Cózar", "Cozuelos de Fuentidueña", "Crecente", "Creixell", "Crémenes", "Crespià", "Crespos", "Cretas", "Crevillent", "Cristina", "Cristóbal", "Crivillén", "Cruïlles, Monells i Sant Sadurní de lHeura", "Cuacos de Yuste", "Cuadros", "Cualedro", "Cuarte de Huerva", "Cuba, La", "Cubas de la Sagra", "Cubel", "Cubelles", "Cubells", "Cubilla", "Cubillas de Cerrato", "Cubillas de los Oteros", "Cubillas de Rueda", "Cubillas de Santa Marta", "Cubillo", "Cubillo de Uceda, El", "Cubillo del Campo", "Cubillos", "Cubillos del Sil", "Cubla", "Cubo de Benavente", "Cubo de Bureba", "Cubo de Don Sancho, El", "Cubo de la Solana", "Cubo de Tierra del Vino, El", "Cucalón", "Cudillero", "Cuelgamures", "Cuéllar", "Cuenca", "Cuenca de Campos", "Cuerlas, Las", "Cuerva", "Cuervo de Sevilla, El", "Cuervo, El", "Cueva de Ágreda", "Cueva de Roa, La", "Cueva del Hierro", "Cuevas Bajas", "Cuevas de Almudén", "Cuevas de Provanco", "Cuevas de San Clemente", "Cuevas de San Marcos", "Cuevas del Almanzora", "Cuevas del Becerro", "Cuevas del Campo", "Cuevas del Valle", "Cuevas Labradas", "Culla", "Cúllar", "Cúllar Vega", "Cullera", "Culleredo", "Cumbre, La", "Cumbres de Enmedio", "Cumbres de San Bartolomé", "Cumbres Mayores", "Cunit", "Cuntis", "Curiel de Duero", "Curtis", "Cútar", "Cuzcurrita de Río Tirón", "Daganzo de Arriba", "Daimiel", "Daimús", "Dalías", "Darnius", "Daroca", "Daroca de Rioja", "Darro", "Das", "Daya Nueva", "Daya Vieja", "Deba", "Degaña", "Dehesa de Montejo", "Dehesa de Romanos", "Dehesas de Guadix", "Deifontes", "Deleitosa", "Deltebre", "Dénia", "Derio", "Descargamaría", "Desojo", "Destriana", "Dévanos", "Deyá", "Deza", "Dicastillo", "Diego del Carpio", "Diezma", "Dílar", "Dima", "Dios le Guarde", "Dodro", "Dólar", "Dolores", "Domeño", "Domingo García", "Domingo Pérez", "Don Álvaro", "Don Benito", "Donamaria", "Doneztebe/Santesteban", "Donhierro", "Donjimeno", "Donostia-San Sebastián", "Donvidas", "Doña Mencía", "Doñinos de Ledesma", "Doñinos de Salamanca", "Dos Aguas", "Dos Hermanas", "Dos Torres", "Dosbarrios", "Dosrius", "Dozón", "Driebes", "Dúdar", "Dueñas", "Duesaigües", "Dumbría", "Durango", "Dúrcal", "Durón", "Duruelo", "Duruelo de la Sierra", "Ea", "Echarri", "Écija", "Egüés", "Eibar", "Eivissa", "Ejea de los Caballeros", "Ejeme", "Ejido, El", "Ejulve", "Elantxobe", "Elburgo/Burgelu", "Elche de la Sierra", "Elche/Elx", "Elciego", "Elda", "Elduain", "Elgeta", "Elgoibar", "Elgorriaga", "Eliana, l", "Eljas", "Elorrio", "Elvillar/Bilar", "Embid", "Embid de Ariza", "Emperador", "Encina de San Silvestre", "Encina, La", "Encinacorba", "Encinas", "Encinas de Abajo", "Encinas de Arriba", "Encinas de Esgueva", "Encinas Reales", "Encinasola", "Encinasola de los Comendadores", "Encinedo", "Encinillas", "Encío", "Enciso", "Endrinal", "Enériz/Eneritz", "Enguera", "Enguídanos", "Enix", "Ènova, l", "Entrala", "Entrambasaguas", "Entrena", "Entrimo", "Entrín Bajo", "Épila", "Erandio", "Eratsun", "Ercina, La", "Ereño", "Ergoiena", "Erla", "Ermua", "Errenteria", "Errezil", "Erriberagoitia/Ribera Alta", "Errigoiti", "Erro", "Erustes", "Escacena del Campo", "Escala, L", "Escalante", "Escalona", "Escalona del Prado", "Escalonilla", "Escamilla", "Escañuela", "Escarabajosa de Cabezas", "Escariche", "Escatrón", "Escobar de Campos", "Escobar de Polendos", "Escobosa de Almazán", "Escopete", "Escorca", "Escorial, El", "Escorihuela", "Escucha", "Escurial", "Escurial de la Sierra", "Escúzar", "Esgos", "Esguevillas de Esgueva", "Eskoriatza", "Eslava", "Eslida", "Espadaña", "Espadañedo", "Espadilla", "Esparragalejo", "Esparragosa de la Serena", "Esparragosa de Lares", "Esparreguera", "Espartinas", "Esparza de Salazar/Espartza Zaraitzu", "Espeja", "Espeja de San Marcelino", "Espejo", "Espejón", "Espelúy", "Espera", "Espiel", "Espinar, El", "Espinelves", "Espino de la Orbada", "Espinosa de Cerrato", "Espinosa de Cervera", "Espinosa de Henares", "Espinosa de los Caballeros", "Espinosa de los Monteros", "Espinosa de Villagonzalo", "Espinosa del Camino", "Espinoso del Rey", "Espirdo", "Esplegares", "Espluga Calba, L", "Espluga de Francolí, L", "Esplugues de Llobregat", "Esplús", "Espolla", "Esponellà", "Esporles", "Espot", "Espronceda", "Espunyola, L", "Esquivias", "Establés", "Estada", "Estadilla", "Estamariu", "Estany, L", "Estaràs", "Estella-Lizarra", "Estellencs", "Estepa", "Estepa de San Juan", "Estépar", "Estepona", "Estercuel", "Esteribar", "Esterri dÀneu", "Esterri de Cardós", "Estivella", "Estollo", "Estopiñán del Castillo", "Estrada, A", "Estrella, La", "Estremera", "Estriégana", "Estubeny", "Etayo", "Etxalar", "Etxarri-Aranatz", "Etxauri", "Etxebarri", "Etxebarria", "Eulate", "Ezcabarte", "Ezcaray", "Ezcároz/Ezkaroze", "Ezkio-Itsaso", "Ezkurra", "Ezprogui", "Fabara", "Fabero", "Facheca", "Fago", "Falces", "Falset", "Famorca", "Fanlo", "Fanzara", "Far dEmpordà, El", "Faraján", "Faramontanos de Tábara", "Fariza", "Farlete", "Farrera", "Fasnia", "Fatarella, La", "Faura", "Favara", "Fayón", "Fayos, Los", "Febró, La", "Felanitx", "Felix", "Fene", "Férez", "Feria", "Fermoselle", "Fernán Caballero", "Fernán-Núñez", "Ferreira", "Ferreras de Abajo", "Ferreras de Arriba", "Ferreries", "Ferreruela", "Ferreruela de Huerva", "Ferrol", "Figaró-Montmany", "Fígols", "Fígols i Alinyà", "Figuera, La", "Figueres", "Figuerola del Camp", "Figueroles", "Figueruela de Arriba", "Figueruelas", "Fines", "Finestrat", "Fiñana", "Firgas", "Fiscal", "Fisterra", "Fitero", "Flaçà", "Flix", "Flores de Ávila", "Floresta, La", "Florida de Liébana", "Fogars de la Selva", "Fogars de Montclús", "Foios", "Foixà", "Folgoso de la Ribera", "Folgoso do Courel", "Folgueroles", "Fombellida", "Fombuena", "Fompedraza", "Foncea", "Fondarella", "Fondó de les Neus, el/Hondón de las Nieves", "Fondón", "Fonelas", "Fonfría", "Fonfría", "Fonollosa", "Fonsagrada, A", "Font de la Figuera, la", "Font dEn Carròs, la", "Font-rubí", "Fontanals de Cerdanya", "Fontanar", "Fontanarejo", "Fontanars dels Alforins", "Fontanilles", "Fontcoberta", "Fontellas", "Fontihoyuelo", "Fontioso", "Fontiveros", "Fonz", "Fonzaleche", "Foradada", "Foradada del Toscar", "Forallac", "Forcall", "Forcarei", "Forès", "Forfoleda", "Formentera", "Formentera del Segura", "Formiche Alto", "Fornalutx", "Fornells de la Selva", "Fornelos de Montes", "Fórnoles", "Fortaleny", "Fortanete", "Fortià", "Fortuna", "Forua", "Foz", "Foz-Calanda", "Frades", "Frades de la Sierra", "Fraga", "Frago, El", "Frailes", "Franco, El", "Frandovínez", "Franqueses del Vallès, Les", "Frasno, El", "Frechilla", "Frechilla de Almazán", "Fregenal de la Sierra", "Fregeneda, La", "Freginals", "Freila", "Fréscano", "Fresneda de Altarejos", "Fresneda de Cuéllar", "Fresneda de la Sierra", "Fresneda de la Sierra Tirón", "Fresneda, La", "Fresnedilla", "Fresnedillas de la Oliva", "Fresnedoso", "Fresnedoso de Ibor", "Fresneña", "Fresnillo de las Dueñas", "Fresno Alhándiga", "Fresno de Cantespino", "Fresno de Caracena", "Fresno de la Fuente", "Fresno de la Polvorosa", "Fresno de la Ribera", "Fresno de la Vega", "Fresno de Río Tirón", "Fresno de Rodilla", "Fresno de Sayago", "Fresno de Torote", "Fresno del Río", "Fresno el Viejo", "Fresno, El", "Frías", "Frías de Albarracín", "Friera de Valverde", "Frigiliana", "Friol", "Frómista", "Frontera", "Frontera, La", "Fruiz", "Frumales", "Fuembellida", "Fuencaliente", "Fuencaliente de la Palma", "Fuencemillán", "Fuendejalón", "Fuendetodos", "Fuenferrada", "Fuengirola", "Fuenlabrada", "Fuenlabrada de los Montes", "Fuenllana", "Fuenmayor", "Fuensaldaña", "Fuensalida", "Fuensanta", "Fuensanta de Martos", "Fuente Álamo de Murcia", "Fuente de Cantos", "Fuente de Pedro Naharro", "Fuente de Piedra", "Fuente de San Esteban, La", "Fuente de Santa Cruz", "Fuente del Arco", "Fuente del Maestre", "Fuente el Fresno", "Fuente el Olmo de Fuentidueña", "Fuente el Olmo de Íscar", "Fuente el Saúz", "Fuente el Saz de Jarama", "Fuente el Sol", "Fuente Encalada", "Fuente la Lancha", "Fuente la Reina", "Fuente Obejuna", "Fuente Palmera", "Fuente Vaqueros", "Fuente-Álamo", "Fuente-Olmedo", "Fuente-Tójar", "Fuentealbilla", "Fuentearmegil", "Fuentebureba", "Fuentecambrón", "Fuentecantos", "Fuentecén", "Fuenteguinaldo", "Fuenteheridos", "Fuentelahiguera de Albatages", "Fuentelapeña", "Fuentelcésped", "Fuentelencina", "Fuentelespino de Haro", "Fuentelespino de Moya", "Fuenteliante", "Fuentelisendo", "Fuentelmonge", "Fuentelsaz", "Fuentelsaz de Soria", "Fuentelviejo", "Fuentemolinos", "Fuentenava de Jábaga", "Fuentenebro", "Fuentenovilla", "Fuentepelayo", "Fuentepinilla", "Fuentepiñel", "Fuenterrebollo", "Fuenterroble de Salvatierra", "Fuenterrobles", "Fuentes", "Fuentes Calientes", "Fuentes Claras", "Fuentes de Andalucía", "Fuentes de Año", "Fuentes de Ayódar", "Fuentes de Béjar", "Fuentes de Carbajal", "Fuentes de Ebro", "Fuentes de Jiloca", "Fuentes de León", "Fuentes de Magaña", "Fuentes de Nava", "Fuentes de Oñoro", "Fuentes de Ropel", "Fuentes de Rubielos", "Fuentes de Valdepero", "Fuentesaúco", "Fuentesaúco de Fuentidueña", "Fuentesecas", "Fuentesoto", "Fuentespalda", "Fuentespina", "Fuentespreadas", "Fuentestrún", "Fuentidueña", "Fuentidueña de Tajo", "Fuerte del Rey", "Fuertescusa", "Fueva, La", "Fuliola, La", "Fulleda", "Funes", "Fustiñana"}));
        }
        if (jComboBox_filtroAZReserva.getSelectedItem().toString().equals("De la G a la S")) {
            jComboBox_poblacionReserva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Gabaldón", "Gabias, Las", "Gabiria", "Gádor", "Gaià", "Gaianes", "Gaibiel", "Gaintza", "Gajanejos", "Gajates", "Galapagar", "Galápagos", "Galar", "Galaroza", "Galbarros", "Galbárruli", "Galdakao", "Galdames", "Gáldar", "Galende", "Galera", "Galera, La", "Galilea", "Galindo y Perahuy", "Galinduste", "Galisancho", "Galisteo", "Gallardos, Los", "Gallega, La", "Gallegos", "Gallegos de Altamiros", "Gallegos de Argañán", "Gallegos de Hornija", "Gallegos de Sobrinos", "Gallegos de Solmirón", "Gallegos del Pan", "Gallegos del Río", "Gallifa", "Gallinero de Cameros", "Gallipienzo/Galipentzu", "Gallocanta", "Gallués/Galoze", "Gallur", "Galve", "Galve de Sorbe", "Gálvez", "Gamiz-Fika", "Gamones", "Gandesa", "Gandia", "Garaballa", "Garachico", "Garafía", "Garai", "Garaioa", "Garbayuela", "Garcia", "Garciaz", "Garcibuey", "Garcihernández", "Garcillán", "Garciotum", "Garcirrey", "Garde", "Gargallo", "Garganta de los Montes", "Garganta del Villar", "Garganta la Olla", "Garganta, La", "Gargantilla", "Gargantilla del Lozoya y Pinilla de Buitrago", "Gargüera", "Garidells, Els", "Garínoain", "Garlitos", "Garrafe de Torío", "Garralda", "Garray", "Garriga, La", "Garrigàs", "Garrigoles", "Garriguella", "Garrobo, El", "Garrovilla, La", "Garrovillas de Alconétar", "Garrucha", "Garvín", "Gascones", "Gascueña", "Gascueña de Bornova", "Gastor, El", "Gata", "Gata de Gorgos", "Gatika", "Gatón de Campos", "Gátova", "Gaucín", "Gautegiz Arteaga", "Gavà", "Gavarda", "Gavet de la Conca", "Gavilanes", "Gaztelu", "Gea de Albarracín", "Gejuelo del Barro", "Geldo", "Gelida", "Gelsa", "Gelves", "Gema", "Gemuño", "Genalguacil", "Génave", "Genevilla", "Genovés", "Ger", "Gerena", "Gérgal", "Geria", "Gerindote", "Gernika-Lumo", "Gestalgar", "Getafe", "Getaria", "Getxo", "Gibraleón", "Gijón", "Gil García", "Gilbuena", "Gilena", "Gilet", "Gimenells i el Pla de la Font", "Gimialcón", "Gimileo", "Ginebrosa, La", "Gines", "Ginestar", "Gineta, La", "Girona", "Gironella", "Gisclareny", "Gistaín", "Gizaburuaga", "Gobernador", "Godall", "Godella", "Godelleta", "Godojos", "Goizueta", "Gójar", "Golmayo", "Golmés", "Golosalvo", "Golpejas", "Gómara", "Gombrèn", "Gomecello", "Gomesende", "Gomezserracín", "Gondomar", "Goñi", "Gor", "Gorafe", "Gordaliza del Pino", "Gordexola", "Gordo, El", "Gordoncillo", "Gorga", "Gorliz", "Gormaz", "Gósol", "Gotarrendura", "Gotor", "Gozón", "Gradefes", "Grado", "Grado, El", "Graja de Campalbo", "Graja de Iniesta", "Grajal de Campos", "Grajera", "Granada", "Granada de Río-Tinto, La", "Granada, La", "Granadella, La", "Granadilla de Abona", "Granado, El", "Granátula de Calatrava", "Grandas de Salime", "Grandes y San Martín", "Granera", "Granja de la Costera, la", "Granja de Moreruela", "Granja de Rocamora", "Granja de Torrehermosa", "Granja dEscarp, La", "Granja, La", "Granjuela, La", "Granollers", "Granucillo", "Granyanella", "Granyena de les Garrigues", "Granyena de Segarra", "Grañén", "Grañón", "Gratallops", "Graus", "Grávalos", "Grazalema", "Griegos", "Grijalba", "Grijota", "Griñón", "Grisaleña", "Grisel", "Grisén", "Grove, O", "Guadahortuna", "Guadalajara", "Guadalaviar", "Guadalcanal", "Guadalcázar", "Guadalix de la Sierra", "Guadalmez", "Guadalupe", "Guadamur", "Guadarrama", "Guadasséquies", "Guadassuar", "Guadix", "Guadramiro", "Guajares, Los", "Gualba", "Gualchos", "Gualta", "Guancha, La", "Guarda, A", "Guardamar de la Safor", "Guardamar del Segura", "Guardia de Jaén, La", "Guardia, La", "Guardiola de Berguedà", "Guardo", "Guareña", "Guaro", "Guarrate", "Guarromán", "Guaza de Campos", "Gúdar", "Gudiña, A", "Güejar Sierra", "Güeñes", "Güesa/Gorza", "Guesálaz/Gesalatz", "Güevéjar", "Guía de Isora", "Guiamets, Els", "Guijo de Ávila", "Guijo de Coria", "Guijo de Galisteo", "Guijo de Granadilla", "Guijo de Santa Bárbara", "Guijo, El", "Guijuelo", "Guillena", "Guils de Cerdanya", "Güímar", "Guimerà", "Guingueta dÀneu, La", "Guirguillano", "Guisando", "Guissona", "Guitiriz", "Guixers", "Gumiel de Izán", "Gumiel de Mercado", "Guntín", "Gurb", "Guriezo", "Gurrea de Gállego", "Gusendos de los Oteros", "Gutierre-Muñoz", "Haba, La", "Hacinas", "Harana/Valle de Arana", "Haría", "Haro", "Haza", "Hazas de Cesto", "Helechosa de los Montes", "Hellín", "Henarejos", "Henche", "Heras de Ayuso", "Herbés", "Herce", "Herencia", "Herencias, Las", "Herguijuela", "Herguijuela de Ciudad Rodrigo", "Herguijuela de la Sierra", "Herguijuela del Campo", "Hermandad de Campoo de Suso", "Hérmedes de Cerrato", "Hermigua", "Hermisende", "Hernán-Pérez", "Hernani", "Hernansancho", "Hernialde", "Herradón de Pinares", "Herramélluri", "Herrera", "Herrera de Alcántara", "Herrera de los Navarros", "Herrera de Pisuerga", "Herrera de Soria", "Herrera de Valdecañas", "Herrera del Duque", "Herrera, La", "Herrería", "Herrerías", "Herreros de Suso", "Herreruela", "Herreruela de Oropesa", "Herrín de Campos", "Herrumblar, El", "Hervás", "Hervías", "Hiendelaencina", "Higuera", "Higuera de Calatrava", "Higuera de la Serena", "Higuera de la Sierra", "Higuera de las Dueñas", "Higuera de Llerena", "Higuera de Vargas", "Higuera la Real", "Higueras", "Higueruela", "Higueruelas", "Hija de Dios, La", "Híjar", "Hijes", "Hiniesta, La", "Hinojal", "Hinojales", "Hinojares", "Hinojos", "Hinojosa de Duero", "Hinojosa de Jarque", "Hinojosa de San Vicente", "Hinojosa del Campo", "Hinojosa del Duque", "Hinojosa del Valle", "Hinojosa, La", "Hinojosas de Calatrava", "Hinojosos, Los", "Hiriberri/Villanueva de Aezkoa", "Hiruela, La", "Hita", "Hito, El", "Holguera", "Hombrados", "Hondarribia", "Hondón de los Frailes", "Honrubia", "Honrubia de la Cuesta", "Hontalbilla", "Hontanar", "Hontanares de Eresma", "Hontanas", "Hontanaya", "Hontangas", "Hontecillas", "Hontoba", "Hontoria de Cerrato", "Hontoria de la Cantera", "Hontoria de Valdearados", "Hontoria del Pinar", "Horcajada, La", "Horcajo de la Sierra-Aoslos", "Horcajo de las Torres", "Horcajo de los Montes", "Horcajo de Montemayor", "Horcajo de Santiago", "Horcajo Medianero", "Horcajuelo de la Sierra", "Horche", "Hormazas, Las", "Hormigos", "Hormilla", "Hormilleja", "Hornachos", "Hornachuelos", "Hornillo, El", "Hornillos de Cameros", "Hornillos de Cerrato", "Hornillos de Eresma", "Hornillos del Camino", "Hornos", "Hornos de Moncalvillo", "Horra, La", "Horta de Sant Joan", "Hortezuela de Océn", "Hortigüela", "Hospital de Órbigo", "Hospitalet de Llobregat, L", "Hostalets de Pierola, Els", "Hostalric", "Hoya-Gonzalo", "Hoya, La", "Hoyales de Roa", "Hoyo de Manzanares", "Hoyo de Pinares, El", "Hoyocasero", "Hoyorredondo", "Hoyos", "Hoyos de Miguel Muñoz", "Hoyos del Collado", "Hoyos del Espino", "Hoz de Jaca", "Hoz de la Vieja, La", "Hoz y Costean", "Huarte/Uharte", "Huecas", "Huécija", "Huélaga", "Huélago", "Huélamo", "Huelma", "Huelva", "Huelves", "Huéneja", "Huércal de Almería", "Huércal-Overa", "Huércanos", "Huerce, La", "Huérguina", "Huérmeces", "Huérmeces del Cerro", "Huerta", "Huerta de Arriba", "Huerta de la Obispalía", "Huerta de Rey", "Huerta de Valdecarábanos", "Huerta del Marquesado", "Huertahernando", "Huerto", "Huertos, Los", "Huesa", "Huesa del Común", "Huesca", "Huéscar", "Huete", "Huétor de Santillán", "Huétor Tájar", "Huétor Vega", "Hueva", "Huévar del Aljarafe", "Humada", "Humanes", "Humanes de Madrid", "Humilladero", "Hurones", "Hurtumpascual", "Husillos", "Ibahernando", "Ibargoiti", "Ibarra", "Ibarrangelu", "Ibdes", "Ibeas de Juarros", "Ibi", "Ibias", "Ibieca", "Ibrillos", "Ibros", "Icod de los Vinos", "Idiazabal", "Igantzi", "Igea", "Iglesiarrubia", "Iglesias", "Iglesuela del Cid, La", "Iglesuela, La", "Igorre", "Igriés", "Igualada", "Igualeja", "Igüeña", "Igúzquiza", "Ikaztegieta", "Ilche", "Illa de Arousa, A", "Illán de Vacas", "Illana", "Illano", "Illar", "Illas", "Illescas", "Illora", "Illueca", "Imotz", "Inca", "Incio, O", "Ingenio", "Iniesta", "Iniéstola", "Instinción", "Inviernas, Las", "Irañeta", "Irixo, O", "Irixoa", "Iruela, La", "Iruelos", "Irueste", "Irun", "Iruña Oka/Iruña de Oca", "Irura", "Iruraiz-Gauna", "Irurtzun", "Isaba/Izaba", "Isábena", "Isar", "Íscar", "Isla Cristina", "Isla Mayor", "Isona i Conca Dellà", "Isòvol", "Ispaster", "Istán", "Isuerre", "Itero de la Vega", "Itero del Castillo", "Itrabo", "Itsasondo", "Ituero de Azaba", "Ituero y Lama", "Ituren", "Iturmendi", "Iurreta", "Ivars de Noguera", "Ivars dUrgell", "Ivorra", "Iza/Itza", "Izagaondoa", "Izagre", "Izalzu/Itzaltzu", "Iznájar", "Iznalloz", "Iznate", "Iznatoraf", "Izurtza", "Jabaloyas", "Jabalquinto", "Jabugo", "Jaca", "Jacarilla", "Jadraque", "Jaén", "Jafre", "Jalance", "Jalón de Cameros", "Jambrina", "Jamilena", "Jana, la", "Jaraba", "Jarafuel", "Jaraicejo", "Jaraíz de la Vera", "Jaramillo de la Fuente", "Jaramillo Quemado", "Jarandilla de la Vera", "Jarilla", "Jarque", "Jarque de la Val", "Jasa", "Jatiel", "Jaulín", "Jaurrieta", "Jávea/Xàbia", "Javier", "Jayena", "Jerez de la Frontera", "Jerez de los Caballeros", "Jerez del Marquesado", "Jérica", "Jerte", "Jete", "Jijona/Xixona", "Jimena", "Jimena de la Frontera", "Jimera de Líbar", "Jirueque", "Joarilla de las Matas", "Jódar", "Jonquera, La", "Jorba", "Jorcas", "Jorquera", "Josa", "Josa i Tuixén", "Joyosa, La", "Juarros de Riomoros", "Juarros de Voltoya", "Jubrique", "Juià", "Jumilla", "Jun", "Junciana", "Juncosa", "Juneda", "Junta de Traslaloma", "Junta de Villalba de Losa", "Jurisdicción de Lara", "Jurisdicción de San Zadornil", "Juslapeña", "Justel", "Juviles", "Juzbado", "Júzcar", "Karrantza Harana/Valle de Carranza", "Kortezubi", "Kripan", "Kuartango", "Labajos", "Labastida/Bastida", "Labores, Las", "Labuerda", "Láchar", "Ladrillar", "Lagartera", "Lagartos", "Lagata", "Lagrán", "Laguardia", "Lagueruela", "Laguna Dalga", "Laguna de Cameros", "Laguna de Contreras", "Laguna de Duero", "Laguna de Negrillos", "Laguna del Marquesado", "Lagunaseca", "Lagunilla", "Lagunilla del Jubera", "Lahiguera", "Lakuntza", "Lalín", "Laluenga", "Lalueza", "Lama, A", "Lamasón", "Lana", "Lanaja", "Láncara", "Lanciego/Lantziego", "Landete", "Lanestosa", "Langa", "Langa de Duero", "Langa del Castillo", "Langayo", "Langreo", "Languilla", "Lanjarón", "Lantadilla", "Lantarón", "Lanteira", "Lantejuela, La", "Lantz", "Lanzahíta", "Lanzuela", "Lapa, La", "Laperdiguera", "Lapoblación", "Lapuebla de Labarca", "Laracha, A", "Lardero", "Laredo", "Larouco", "Laroya", "Larrabetzu", "Larraga", "Larraona", "Larraul", "Larraun", "Larrodrigo", "Larva", "Lasarte-Oria", "Lascellas-Ponzano", "Lascuarre", "Laspaúles", "Laspuña", "Lastras de Cuéllar", "Lastras del Pozo", "Lastrilla, La", "Laudio/Llodio", "Láujar de Andarax", "Laukiz", "Laviana", "Laxe", "Layana", "Layos", "Laza", "Lazagurría", "Lazkao", "Leaburu", "Leache", "Lebrija", "Lécera", "Lechón", "Leciñena", "Lecrín", "Ledanca", "Ledaña", "Ledesma", "Ledesma de la Cogolla", "Ledigos", "Ledrada", "Leganés", "Leganiel", "Legarda", "Legaria", "Legazpi", "Legorreta", "Legutio", "Leintz-Gatzaga", "Leioa", "Leiro", "Leitza", "Leiva", "Lekeitio", "Lekunberri", "Lemoa", "Lemoiz", "Lena", "Lentegí", "León", "Leoz/Leotz", "Lepe", "Lerga", "Lerín", "Lerma", "Les", "Lesaka", "Letur", "Letux", "Leza", "Leza de Río Leza", "Lezama", "Lezáun", "Lezo", "Lezuza", "Librilla", "Libros", "Liceras", "Lidón", "Liédena", "Liendo", "Liérganes", "Liétor", "Líjar", "Lillo", "Limpias", "Linares", "Linares de la Sierra", "Linares de Mora", "Linares de Riofrío", "Línea de la Concepción, La", "Linyola", "Litago", "Lituénigo", "Lizartza", "Lizoáin-Arriasgoiti", "Llacuna, La", "Lladó", "Lladorre", "Lladurs", "Llagosta, La", "Llagostera", "Llamas de la Ribera", "Llambilles", "Llanars", "Llançà", "Llanera", "Llanera de Ranes", "Llanes", "Llano de Bureba", "Llano de Olmedo", "Llanos de Aridane, Los", "Llanos de Tormes, Los", "Llanos del Caudillo", "Llardecans", "Llaurí", "Llavorsí", "Lledó", "Lleida", "Llera", "Llerena", "Llers", "Lles de Cerdanya", "Llíber", "Lliçà dAmunt", "Lliçà de Vall", "Llimiana", "Llinars del Vallès", "Llíria", "Llívia", "Lloar, El", "Llobera", "Llocnou de la Corona", "Llocnou de Sant Jeroni", "Llocnou dEn Fenollet", "Llombai", "Llorac", "Llorenç del Penedès", "Lloret de Mar", "Lloret de Vistalegre", "Llosa de Ranes, la", "Llosa, la", "Lloseta", "Llosses, Les", "Llubí", "Lluçà", "Llucmajor", "Llutxent", "Loarre", "Lobeira", "Lobera de Onsella", "Lobios", "Lobón", "Lobras", "Lodosa", "Loeches", "Logroño", "Logrosán", "Loiu", "Loja", "Loma de Ucieza", "Lomas", "Lominchar", "Lomoviejo", "Longares", "Longás", "Lónguida/Longida", "Lopera", "Loporzano", "Lora de Estepa", "Lora del Río", "Loranca de Tajuña", "Lorca", "Lorcha/Orxa, l", "Loriguilla", "Lorquí", "Losa del Obispo", "Losa, La", "Losacino", "Losacio", "Losar de la Vera", "Losar del Barco, El", "Loscorrales", "Loscos", "Losilla, La", "Lourenzá", "Lousame", "Lozoya", "Lozoyuela-Navas-Sieteiglesias", "Lubián", "Lubrín", "Lucainena de las Torres", "Lúcar", "Lucena", "Lucena de Jalón", "Lucena del Cid", "Lucena del Puerto", "Luceni", "Luciana", "Lucillo", "Lucillos", "Ludiente", "Luelmo", "Luena", "Luesia", "Luesma", "Lugo", "Lugros", "Luisiana, La", "Lújar", "Lumbier", "Lumbrales", "Lumbreras", "Lumpiaque", "Luna", "Lupiana", "Lupiñén-Ortilla", "Lupión", "Luque", "Luquin", "Luyego", "Luzaga", "Luzaide/Valcarlos", "Luzón", "Macael", "Maçanet de Cabrenys", "Maçanet de la Selva", "Macastre", "Maceda", "Machacón", "Macharaviaya", "Macotera", "Madarcos", "Maderal, El", "Maderuelo", "Madremanya", "Madrid", "Madridanos", "Madridejos", "Madrigal de la Vera", "Madrigal de las Altas Torres", "Madrigal del Monte", "Madrigalejo", "Madrigalejo del Monte", "Madrigueras", "Madroñal", "Madroñera", "Madroño, El", "Maella", "Maello", "Magacela", "Magallón", "Magán", "Magaña", "Magaz de Cepeda", "Magaz de Pisuerga", "Maguilla", "Mahamud", "Mahide", "Mahora", "Maià de Montcal", "Maials", "Maicas", "Maíllo, El", "Mainar", "Maire de Castroponce", "Mairena del Alcor", "Mairena del Aljarafe", "Majadahonda", "Majadas", "Majadas, Las", "Majaelrayo", "Maján", "Málaga", "Málaga del Fresno", "Malagón", "Malaguilla", "Malahá, La", "Malanquilla", "Malcocinado", "Maldà", "Maleján", "Malgrat de Mar", "Malla", "Mallabia", "Mallén", "Malón", "Malpartida", "Malpartida de Cáceres", "Malpartida de Corneja", "Malpartida de la Serena", "Malpartida de Plasencia", "Malpica de Bergantiños", "Malpica de Tajo", "Maluenda", "Malva", "Mamblas", "Mambrilla de Castrejón", "Mambrillas de Lara", "Mamolar", "Manacor", "Mancera de Abajo", "Mancera de Arriba", "Mancha Real", "Manchita", "Manchones", "Manciles", "Mancor de la Vall", "Mandayona", "Manganeses de la Lampreana", "Manganeses de la Polvorosa", "Manilva", "Manises", "Manjabálago", "Manjarrés", "Manlleu", "Manquillos", "Manresa", "Mansilla de la Sierra", "Mansilla de las Mulas", "Mansilla Mayor", "Mantiel", "Mantinos", "Manuel", "Manzanal de Arriba", "Manzanal de los Infantes", "Manzanal del Barco", "Manzanares", "Manzanares de Rioja", "Manzanares el Real", "Manzaneda", "Manzaneque", "Manzanera", "Manzanilla", "Manzanillo", "Manzano, El", "Mañaria", "Mañeru", "Mañón", "Maó", "Maqueda", "Mara", "Maracena", "Maranchón", "Maraña", "Marañón", "Marazoleja", "Marazuela", "Marbella", "Marçà", "Marchagaz", "Marchal", "Marchamalo", "Marchena", "Marcilla", "Marcilla de Campos", "Margalef", "Marganell", "María", "María de Huerva", "Maria de la Salut", "Mariana", "Marín", "Marina de Cudeyo", "Marinaleda", "Marines", "Marines, Los", "Marjaliza", "Markina-Xemein", "Marlín", "Marmolejo", "Marracos", "Marratxí", "Marrupe", "Martiago", "Martiherrero", "Martín de la Jara", "Martín de Yeltes", "Martín del Río", "Martín Miguel", "Martín Muñoz de la Dehesa", "Martín Muñoz de las Posadas", "Martinamor", "Martínez", "Martorell", "Martorelles", "Martos", "Marugán", "Maruri-Jatabe", "Marzales", "Mas de Barberans", "Mas de las Matas", "Masarac", "Mascaraque", "Masdenverge", "Masegosa", "Masegoso", "Masegoso de Tajuña", "Maside", "Masies de Roda, Les", "Masies de Voltregà, Les", "Masllorenç", "Masnou, El", "Masó, La", "Maspujols", "Masquefa", "Masroig, El", "Massalavés", "Massalcoreig", "Massalfassar", "Massamagrell", "Massanassa", "Massanes", "Massoteres", "Masueco", "Mata de Alcántara", "Mata de Cuéllar", "Mata de Ledesma, La", "Mata de los Olmos, La", "Mata de Morella, la", "Mata, La", "Matabuena", "Matadeón de los Oteros", "Matadepera", "Matalebreras", "Matallana de Torío", "Matamala de Almazán", "Matanza", "Matanza de Acentejo, La", "Matapozuelos", "Mataró", "Matarrubia", "Matet", "Matilla de Arzón", "Matilla de los Caños", "Matilla de los Caños del Río", "Matilla la Seca", "Matilla, La", "Matillas", "Matute", "Maya, La", "Mayalde", "Mayorga", "Mazaleón", "Mazarambroz", "Mazarete", "Mazaricos", "Mazariegos", "Mazarrón", "Mazcuerras", "Mazuecos", "Mazuecos de Valdeginate", "Mazuela", "Meaño", "Mecerreyes", "Meco", "Medellín", "Mediana de Aragón", "Mediana de Voltoya", "Medina de las Torres", "Medina de Pomar", "Medina de Rioseco", "Medina del Campo", "Medina-Sidonia", "Medinaceli", "Medinilla", "Medio Cudeyo", "Mediona", "Medranda", "Medrano", "Megeces", "Megina", "Meira", "Meis", "Mejorada", "Mejorada del Campo", "Melgar de Abajo", "Melgar de Arriba", "Melgar de Fernamental", "Melgar de Tera", "Melgar de Yuso", "Meliana", "Mélida", "Melide", "Melilla", "Melón", "Melque de Cercos", "Membibre de la Hoz", "Membribe de la Sierra", "Membrilla", "Membrillera", "Membrío", "Menàrguens", "Menasalbas", "Mendaro", "Mendata", "Mendavia", "Mendaza", "Mendexa", "Mendigorría", "Meneses de Campos", "Mengabril", "Mengamuñoz", "Mengíbar", "Méntrida", "Meñaka", "Mequinenza", "Meranges", "Merca, A", "Mercadal, Es", "Mérida", "Merindad de Cuesta-Urria", "Merindad de Montija", "Merindad de Río Ubierna", "Merindad de Sotoscueva", "Merindad de Valdeporres", "Merindad de Valdivielso", "Meruelo", "Mesas de Ibor", "Mesas, Las", "Mesegar de Corneja", "Mesegar de Tajo", "Mesía", "Mesones de Isuela", "Mestanza", "Metauten", "Mezalocha", "Mezquita de Jarque", "Mezquita, A", "Miajadas", "Mianos", "Micereces de Tera", "Micieces de Ojeda", "Miedes de Aragón", "Miedes de Atienza", "Miengo", "Miera", "Mieres", "Mieres", "Mierla, La", "Mieza", "Migjorn Gran, Es", "Miguel Esteban", "Migueláñez", "Miguelturra", "Mijares", "Mijas", "Milà, El", "Milagro", "Milagros", "Milano, El", "Millana", "Millanes", "Millares", "Millena", "Milles de la Polvorosa", "Milmarcos", "Minas de Riotinto", "Minaya", "Minglanilla", "Mingorría", "Miño", "Miño de Medinaceli", "Miño de San Esteban", "Miñosa, La", "Mira", "Mirabel", "Mirabueno", "Miraflores de la Sierra", "Mirafuentes", "Miralcamp", "Miralrío", "Miramar", "Mirambel", "Miranda de Arga", "Miranda de Azán", "Miranda de Ebro", "Miranda del Castañar", "Mirandilla", "Miraveche", "Miravet", "Miravete de la Sierra", "Mirón, El", "Mironcillo", "Mirueña de los Infanzones", "Mislata", "Moaña", "Mocejón", "Mochales", "Moclín", "Moclinejo", "Modúbar de la Emparedada", "Moeche", "Mogán", "Mogarraz", "Mogente/Moixent", "Moguer", "Mohedas de Granadilla", "Mohedas de la Jara", "Mohernando", "Moià", "Mojácar", "Mojados", "Mojonera, La", "Molacillos", "Molar, El", "Molar, El", "Molares, Los", "Molezuelas de la Carballeda", "Molina de Aragón", "Molina de Segura", "Molinaseca", "Molinicos", "Molinillo", "Molinos", "Molinos de Duero", "Molinos, Los", "Molins de Rei", "Molledo", "Mollerussa", "Mollet de Peralada", "Mollet del Vallès", "Mollina", "Molló", "Molsosa, La", "Molvízar", "Mombeltrán", "Momblona", "Mombuey", "Monachil", "Monasterio", "Monasterio de la Sierra", "Monasterio de Rodilla", "Monasterio de Vega", "Moncada", "Moncalvillo", "Moncofa", "Monda", "Mondariz", "Mondariz-Balneario", "Mondéjar", "Mondoñedo", "Monegrillo", "Monesma y Cajigar", "Monesterio", "Moneva", "Monfarracinos", "Monfero", "Monflorite-Lascasas", "Monforte de la Sierra", "Monforte de Lemos", "Monforte de Moyuela", "Monforte del Cid", "Monistrol de Calders", "Monistrol de Montserrat", "Monleón", "Monleras", "Monóvar/Monòver", "Monreal de Ariza", "Monreal del Campo", "Monreal del Llano", "Monreal/Elo", "Monroy", "Monroyo", "Monsagro", "Monsalupe", "Mont-ral", "Mont-ras", "Mont-roig del Camp", "Montagut i Oix", "Montalbán", "Montalbán de Córdoba", "Montalbanejo", "Montalbo", "Montalvos", "Montamarta", "Montán", "Montánchez", "Montanejos", "Montanuy", "Montarrón", "Montaverner", "Montblanc", "Montbrió del Camp", "Montcada i Reixac", "Montclar", "Monteagudo", "Monteagudo de las Salinas", "Monteagudo de las Vicarías", "Monteagudo del Castillo", "Montealegre de Campos", "Montealegre del Castillo", "Montearagón", "Montederramo", "Montefrío", "Montehermoso", "Montejaque", "Montejícar", "Montejo", "Montejo de Arévalo", "Montejo de la Sierra", "Montejo de la Vega de la Serrezuela", "Montejo de Tiermes", "Montellà i Martinet", "Montellano", "Montemayor", "Montemayor de Pililla", "Montemayor del Río", "Montemolín", "Montenegro de Cameros", "Monterde", "Monterde de Albarracín", "Monterrei", "Monterroso", "Monterrubio", "Monterrubio de Armuña", "Monterrubio de la Demanda", "Monterrubio de la Serena", "Monterrubio de la Sierra", "Montesa", "Montesclaros", "Montesinos, Los", "Montesquiu", "Montferrer i Castellbò", "Montferri", "Montgai", "Montgat", "Montiel", "Montijo", "Montilla", "Montillana", "Montitxelvo/Montichelvo", "Montizón", "Montmajor", "Montmaneu", "Montmell, El", "Montmeló", "Montoliu de Lleida", "Montoliu de Segarra", "Montón", "Montorio", "Montornès de Segarra", "Montornès del Vallès", "Montoro", "Montroy", "Montseny", "Montserrat", "Montuïri", "Monturque", "Monzón", "Monzón de Campos", "Mora", "Mora de Rubielos", "Móra dEbre", "Móra la Nova", "Moradillo de Roa", "Moral de Calatrava", "Moral de Hornuez", "Moral de la Reina", "Moral de Sayago", "Moraleda de Zafayona", "Moraleja", "Moraleja de Enmedio", "Moraleja de las Panaderas", "Moraleja de Matacabras", "Moraleja de Sayago", "Moraleja del Vino", "Morales de Campos", "Morales de Rey", "Morales de Toro", "Morales de Valverde", "Morales del Vino", "Moralina", "Moralzarzal", "Moraña", "Morasverdes", "Morata de Jalón", "Morata de Jiloca", "Morata de Tajuña", "Moratalla", "Moratilla de los Meleros", "Moratinos", "Morcillo", "Morcín", "Moreda de Álava/Moreda Araba", "Morelábor", "Morell, El", "Morella", "Morenilla", "Morentin", "Morera de Montsant, La", "Morera, La", "Moreruela de los Infanzones", "Moreruela de Tábara", "Morés", "Morga", "Moriles", "Morille", "Moríñigo", "Moriscos", "Morón de Almazán", "Morón de la Frontera", "Moronta", "Moros", "Mos", "Moscardón", "Mosqueruela", "Móstoles", "Mota de Altarejos", "Mota del Cuervo", "Mota del Marqués", "Motilla del Palancar", "Motilleja", "Motril", "Moya", "Moya", "Moyuela", "Mozárbez", "Mozoncillo", "Mozota", "Mucientes", "Mudá", "Mudarra, La", "Muduex", "Muel", "Muela, La", "Muelas de los Caballeros", "Muelas del Pan", "Mues", "Muga de Sayago", "Mugardos", "Muíños", "Mula", "Mundaka", "Munébrega", "Munera", "Mungia", "Muniesa", "Munilla", "Munitibar-Arbatzegi Gerrikaitz", "Muntanyola", "Muñana", "Muñico", "Muñogalindo", "Muñogrande", "Muñomer del Peco", "Muñopedro", "Muñopepe", "Muñosancho", "Muñotello", "Muñoveros", "Mura", "Muras", "Murchante", "Murcia", "Murero", "Murias de Paredes", "Muriel", "Muriel de la Fuente", "Muriel Viejo", "Murieta", "Murillo de Gállego", "Murillo de Río Leza", "Murillo el Cuende", "Murillo el Fruto", "Murla", "Muro", "Muro de Aguas", "Muro de Alcoy", "Muro en Cameros", "Muros", "Muros de Nalón", "Murtas", "Murueta", "Muruzábal", "Museros", "Muskiz", "Mutiloa", "Mutriku", "Mutxamel", "Muxía", "Muxika", "Nabarniz", "Nacimiento", "Nafría de Ucero", "Nájera", "Nalda", "Nalec", "Nambroca", "Náquera", "Narboneta", "Narón", "Narrillos del Álamo", "Narrillos del Rebollar", "Narros", "Narros de Matalayegua", "Narros de Saldueña", "Narros del Castillo", "Narros del Puerto", "Naut Aran", "Nava", "Nava de Arévalo", "Nava de Béjar", "Nava de Francia", "Nava de la Asunción", "Nava de Ricomalillo, La", "Nava de Roa", "Nava de Santiago, La", "Nava de Sotrobal", "Nava del Barco", "Nava del Rey", "Nava, La", "Navacarros", "Navacepedilla de Corneja", "Navacerrada", "Navaconcejo", "Navadijos", "Navaescurial", "Navafría", "Navahermosa", "Navahondilla", "Navajas", "Navajún", "Naval", "Navalacruz", "Navalafuente", "Navalagamella", "Navalcán", "Navalcarnero", "Navaleno", "Navales", "Navalilla", "Navalmanzano", "Navalmoral", "Navalmoral de Béjar", "Navalmoral de la Mata", "Navalmoralejo", "Navalmorales, Los", "Navalonguilla", "Navalosa", "Navalperal de Pinares", "Navalperal de Tormes", "Navalpino", "Navalucillos, Los", "Navaluenga", "Navalvillar de Ibor", "Navalvillar de Pela", "Navamorales", "Navamorcuende", "Navaquesera", "Navarcles", "Navardún", "Navares de Ayuso", "Navares de Enmedio", "Navares de las Cuevas", "Navaridas", "Navarredonda de Gredos", "Navarredonda de la Rinconada", "Navarredonda y San Mamés", "Navarredondilla", "Navarrés", "Navarrete", "Navarrevisca", "Navàs", "Navas de Bureba", "Navas de Estena", "Navas de Jadraque, Las", "Navas de Jorquera", "Navas de la Concepción, Las", "Navas de Oro", "Navas de Riofrío", "Navas de San Antonio", "Navas de San Juan", "Navas del Madroño", "Navas del Marqués, Las", "Navas del Rey", "Navascués/Nabaskoze", "Navasfrías", "Navata", "Navatalgordo", "Navatejares", "Navès", "Navezuelas", "Navia", "Navia de Suarna", "Navianos de Valverde", "Nazar", "Nebreda", "Neda", "Negredo", "Negreira", "Negrilla de Palencia", "Negueira de Muñiz", "Neila", "Neila de San Miguel", "Nepas", "Nerja", "Nerpio", "Nerva", "Nestares", "Nevada", "Neves, As", "Niebla", "Nieva", "Nieva de Cameros", "Nigrán", "Nigüelas", "Nigüella", "Niharra", "Níjar", "Nívar", "Noáin (Valle de Elorz)/Noain (Elortzibar)", "Noalejo", "Noblejas", "Noceda del Bierzo", "Noez", "Nogais, As", "Nogal de las Huertas", "Nogales", "Nogueira de Ramuín", "Noguera de Albarracín", "Nogueras", "Nogueruelas", "Noia", "Noja", "Nolay", "Nombela", "Nombrevilla", "Nonaspe", "Noreña", "Nou de Berguedà, La", "Nou de Gaià, La", "Novales", "Novallas", "Novelda", "Novelé/Novetlè", "Novés", "Noviercas", "Novillas", "Nucia, la", "Nueno", "Nueva Carteya", "Nueva Villa de las Torres", "Nuévalos", "Nuevo Baztán", "Nuez de Ebro", "Nules", "Nulles", "Numancia de la Sagra", "Nuño Gómez", "Nuñomoral", "Obanos", "Obejo", "Obón", "Ocaña", "Ocentejo", "Ochagavía/Otsagabia", "Ochánduri", "Oco", "Ocón", "Odèn", "Òdena", "Odieta", "Odón", "Oencia", "Ogassa", "Ogíjares", "Ohanes", "Oia", "Oiartzun", "Oímbra", "Oitz", "Ojacastro", "Ojén", "Ojós", "Ojos Negros", "Ojos-Albos", "Okondo", "Olaberria", "Olaibar", "Olazti/Olazagutía", "Olba", "Olea de Boedo", "Oleiros", "Olejua", "Olèrdola", "Olesa de Bonesvalls", "Olesa de Montserrat", "Oliana", "Olías del Rey", "Oliete", "Oliola", "Olite/Erriberri", "Olius", "Oliva", "Oliva de la Frontera", "Oliva de Mérida", "Oliva de Plasencia", "Oliva, La", "Olivar, El", "Olivares", "Olivares de Duero", "Olivares de Júcar", "Olivella", "Olivenza", "Ollauri", "Olleria, l", "Ollo", "Olmeda de Cobeta", "Olmeda de Jadraque, La", "Olmeda de la Cuesta", "Olmeda de las Fuentes", "Olmeda del Rey", "Olmedilla de Alarcón", "Olmedilla de Eliz", "Olmedillo de Roa", "Olmedo", "Olmedo de Camaces", "Olmillos de Castro", "Olmillos de Muñó", "Olmos de Esgueva", "Olmos de Ojeda", "Olmos de Peñafiel", "Olmos, Los", "Olocau", "Olocau del Rey", "Olombrada", "Olóriz/Oloritz", "Olost", "Olot", "Oluges, Les", "Olula de Castro", "Olula del Río", "Olvan", "Ólvega", "Olvena", "Olvera", "Olvés", "Omañas, Las", "Omellons, Els", "Omells de na Gaia, Els", "Oncala", "Onda", "Ondara", "Ondarroa", "Onil", "Onís", "Ontígola", "Ontinyent", "Ontiñena", "Ontur", "Onzonilla", "Oña", "Oñati", "Oquillas", "Orba", "Orbada, La", "Orbaizeta", "Orbaneja Riopico", "Orbara", "Orbita", "Orcajo", "Orce", "Orcera", "Ordes", "Ordial, El", "Ordis", "Ordizia", "Orea", "Orejana", "Orellana de la Sierra", "Orellana la Vieja", "Orendain", "Orera", "Orés", "Orexa", "Organyà", "Orgaz", "Órgiva", "Oria", "Orihuela", "Orihuela del Tremedal", "Orio", "Orís", "Orísoain", "Oristà", "Orkoien", "Ormaiztegi", "Oronz/Orontze", "Oropesa", "Oropesa del Mar/Orpesa", "Oroso", "Orotava, La", "Oroz-Betelu/Orotz-Betelu", "Orozko", "Orpí", "Orreaga/Roncesvalles", "Orrios", "Òrrius", "Ortigosa de Cameros", "Ortigosa de Pestaño", "Ortigosa del Monte", "Ortigueira", "Ortuella", "Orusco de Tajuña", "Orxeta", "Os de Balaguer", "Osa de la Vega", "Oseja", "Oseja de Sajambre", "Osera de Ebro", "Oso, El", "Osor", "Osornillo", "Osorno la Mayor", "Ossa de Montiel", "Osso de Cinca", "Ossó de Sió", "Osuna", "Oteiza", "Otero", "Otero de Bodas", "Otero de Herreros", "Otívar", "Otos", "Otura", "Otxandio", "Ourense", "Ourol", "Outeiro de Rei", "Outes", "Oviedo", "Oyón-Oion", "Oza dos Ríos", "Pacs del Penedès", "Paderne", "Paderne de Allariz", "Padiernos", "Padilla de Abajo", "Padilla de Arriba", "Padrenda", "Padrón", "Padrones de Bureba", "Padul", "Padules", "Paiporta", "Pájara", "Pajarejos", "Pajares de Adaja", "Pajares de la Laguna", "Pajares de la Lampreana", "Pajares de los Oteros", "Pajarón", "Pajaroncillo", "Palacios de Goda", "Palacios de la Sierra", "Palacios de la Valduerna", "Palacios de Riopisuerga", "Palacios de Sanabria", "Palacios del Arzobispo", "Palacios del Pan", "Palacios del Sil", "Palacios y Villafranca, Los", "Palaciosrubios", "Palafolls", "Palafrugell", "Palamós", "Palanques", "Palas de Rei", "Palau dAnglesola, El", "Palau de Santa Eulàlia", "Palau-sator", "Palau-saverdera", "Palau-solità i Plegamans", "Palazuelo de Vedija", "Palazuelos de Eresma", "Palazuelos de la Sierra", "Palazuelos de Muñó", "Palencia", "Palencia de Negrilla", "Palenciana", "Palenzuela", "Pallaresos, Els", "Pallejà", "Palma", "Palma de Cervelló, La", "Palma de Gandía", "Palma dEbre, La", "Palma del Condado, La", "Palma del Río", "Pálmaces de Jadraque", "Palmas de Gran Canaria, Las", "Palmera", "Palo", "Palol de Revardit", "Palomar de Arroyos", "Palomar, el", "Palomares del Campo", "Palomares del Río", "Palomas", "Palomeque", "Palomera", "Palomero", "Palos de la Frontera", "Pals", "Pampaneira", "Pampliega", "Pamplona/Iruña", "Pancorbo", "Pancrudo", "Paniza", "Panticosa", "Pantoja", "Pantón", "Papatrigo", "Papiol, El", "Paracuellos", "Paracuellos de Jarama", "Paracuellos de Jiloca", "Paracuellos de la Ribera", "Parada de Arriba", "Parada de Rubiales", "Parada de Sil", "Paradas", "Paradela", "Paradinas de San Juan", "Páramo de Boedo", "Páramo del Sil", "Páramo, O", "Parauta", "Parcent", "Pardilla", "Pardines", "Pardos", "Paredes", "Paredes de Escalona", "Paredes de Nava", "Paredes de Sigüenza", "Pareja", "Parets del Vallès", "Parla", "Parlavà", "Parra de las Vegas, La", "Parra, La", "Parral, El", "Parras de Castellote, Las", "Parres", "Parrilla, La", "Parrillas", "Partaloa", "Partido de la Sierra en Tobalina", "Pasaia", "Pasarón de la Vera", "Pascualcobo", "Paso, El", "Passanant i Belltall", "Pastores", "Pastoriza, A", "Pastrana", "Pastriz", "Paterna", "Paterna de Rivera", "Paterna del Campo", "Paterna del Madera", "Paterna del Río", "Patones", "Pau", "Paüls", "Pavías", "Paymogo", "Payo de Ojeda", "Payo, El", "Pazos de Borbén", "Pazuengos", "Peal de Becerro", "Pechina", "Pedernoso, El", "Pedrafita do Cebreiro", "Pedraja de Portillo, La", "Pedrajas de San Esteban", "Pedralba", "Pedralba de la Pradería", "Pedraza", "Pedraza de Alba", "Pedraza de Campos", "Pedregal, El", "Pedreguer", "Pedrera", "Pedret i Marzà", "Pedrezuela", "Pedro Abad", "Pedro Bernardo", "Pedro Martínez", "Pedro Muñoz", "Pedro-Rodríguez", "Pedroche", "Pedrola", "Pedroñeras, Las", "Pedrosa de Duero", "Pedrosa de la Vega", "Pedrosa de Río Úrbel", "Pedrosa del Páramo", "Pedrosa del Príncipe", "Pedrosa del Rey", "Pedrosas, Las", "Pedrosillo de Alba", "Pedrosillo de los Aires", "Pedrosillo el Ralo", "Pedroso", "Pedroso de Acim", "Pedroso de la Armuña, El", "Pedroso, El", "Pegalajar", "Pego", "Pego, El", "Peguerinos", "Pelabravo", "Pelahustán", "Pelarrodríguez", "Pelayos", "Pelayos de la Presa", "Pelayos del Arroyo", "Peleagonzalo", "Peleas de Abajo", "Peligros", "Penagos", "Penàguila", "Penelles", "Peníscola/Peñíscola", "Peña, La", "Peñacaballera", "Peñacerrada-Urizaharra", "Peñafiel", "Peñaflor", "Peñaflor de Hornija", "Peñalba", "Peñalba de Ávila", "Peñalén", "Peñalsordo", "Peñalver", "Peñamellera Alta", "Peñamellera Baja", "Peñaparda", "Peñaranda de Bracamonte", "Peñaranda de Duero", "Peñarandilla", "Peñarroya de Tastavins", "Peñarroya-Pueblonuevo", "Peñarrubia", "Peñas de Riglos, Las", "Peñas de San Pedro", "Peñascosa", "Peñausende", "Pepino", "Peque", "Pera, La", "Peracense", "Perafita", "Perafort", "Peral de Arlanza", "Peral, El", "Peralada", "Peraleda de la Mata", "Peraleda de San Román", "Peraleda del Zaucejo", "Peraleja, La", "Peralejos", "Peralejos de Abajo", "Peralejos de Arriba", "Peralejos de las Truchas", "Perales", "Perales de Tajuña", "Perales del Alfambra", "Perales del Puerto", "Peralta de Alcofea", "Peralta de Calasanz", "Peralta/Azkoien", "Peraltilla", "Peralveche", "Peramola", "Peranzanes", "Perarrúa", "Perdigón, El", "Perdiguera", "Pereiro de Aguiar, O", "Perelló, El", "Pereña de la Ribera", "Pereruela", "Periana", "Perilla de Castro", "Pernía, La", "Peromingo", "Perosillo", "Peroxa, A", "Pertusa", "Pesaguero", "Pescueza", "Pesga, La", "Pesoz", "Pesquera", "Pesquera de Duero", "Pesquera, La", "Petilla de Aragón", "Petín", "Petra", "Petrer", "Petrés", "Pétrola", "Peza, La", "Pezuela de las Torres", "Pías", "Picanya", "Picassent", "Picazo, El", "Picón", "Piedrabuena", "Piedrahíta", "Piedrahita de Castro", "Piedralaves", "Piedramillera", "Piedras Albas", "Piedratajada", "Piélagos", "Piera", "Piérnigas", "Pilar de la Horadada", "Pilas", "Piles", "Piles, Les", "Piloña", "Pina de Ebro", "Pina de Montalgrao", "Pinar de El Hierro, El", "Pinar, El", "Pinarejo", "Pinarejos", "Pinarnegrillo", "Pineda de Gigüela", "Pineda de la Sierra", "Pineda de Mar", "Pineda Trasmonte", "Pinedas", "Pinell de Brai, El", "Pinell de Solsonès", "Pinet", "Pinilla de Jadraque", "Pinilla de los Barruecos", "Pinilla de los Moros", "Pinilla de Molina", "Pinilla de Toro", "Pinilla del Campo", "Pinilla del Valle", "Pinilla Trasmonte", "Pinillos", "Pino de Tormes, El", "Pino del Oro", "Pino del Río", "Pino, O", "Pinofranqueado", "Pinós", "Pinos Genil", "Pinos Puente", "Pinós, el/Pinoso", "Pinseque", "Pintanos, Los", "Pinto", "Piña de Campos", "Piña de Esgueva", "Píñar", "Piñel de Abajo", "Piñel de Arriba", "Piñero, El", "Piñor", "Piñuécar-Gandullas", "Piornal", "Pioz", "Piqueras", "Piqueras del Castillo", "Pira", "Piracés", "Pitarque", "Pitiegua", "Pitillas", "Pizarra", "Pizarral", "Pla de Santa Maria, El", "Pla del Penedès, El", "Plan", "Planes", "Planes dHostoles, Les", "Planoles", "Plans de Sió, Els", "Plasencia", "Plasencia de Jalón", "Plasenzuela", "Pleitas", "Plenas", "Plentzia", "Pliego", "Plou", "Poal, El", "Pobla de Benifassà, la", "Pobla de Cérvoles, La", "Pobla de Claramunt, La", "Pobla de Farnals, la", "Pobla de Lillet, La", "Pobla de Mafumet, La", "Pobla de Massaluca, La", "Pobla de Montornès, La", "Pobla de Segur, La", "Pobla de Vallbona, la", "Pobla del Duc, la", "Pobla Llarga, la", "Pobla Tornesa, la", "Pobla, Sa", "Población de Arroyo", "Población de Campos", "Población de Cerrato", "Pobladura de Pelayo García", "Pobladura de Valderaduey", "Pobladura del Valle", "Poblete", "Poblets, els", "Pobo de Dueñas, El", "Pobo, El", "Poboleda", "Pobra de Trives, A", "Pobra do Brollón, A", "Pobra do Caramiñal, A", "Poio", "Pol", "Pola de Gordón, La", "Polaciones", "Polán", "Polanco", "Polentinos", "Poleñino", "Polícar", "Polinyà", "Polinyà de Xúquer", "Pollença", "Pollos", "Polop", "Polopos", "Pomar de Valdivia", "Pomer", "Ponferrada", "Ponga", "Pont dArmentera, El", "Pont de Bar, El", "Pont de Molins", "Pont de Suert, El", "Pont de Vilomara i Rocafort, El", "Ponte Caldelas", "Ponteareas", "Ponteceso", "Pontecesures", "Pontedeume", "Pontedeva", "Pontenova, A", "Pontes de García Rodríguez, As", "Pontevedra", "Pontils", "Pontons", "Pontós", "Ponts", "Porcuna", "Porqueira", "Porqueres", "Porrera", "Porreres", "Porriño, O", "Port de la Selva, El", "Portaje", "Portalrubio de Guadamejud", "Portas", "Portbou", "Portell de Morella", "Portella, La", "Portellada, La", "Portezuelo", "Portilla", "Portillo", "Portillo de Soria", "Portillo de Toledo", "Porto", "Porto do Son", "Portomarín", "Portugalete", "Pórtugos", "Porzuna", "Posada de Valdeón", "Posadas", "Potes", "Potríes", "Poveda", "Poveda de la Sierra", "Poveda de las Cintas", "Póveda de Soria, La", "Povedilla", "Poyales del Hoyo", "Poyatos", "Poza de la Sal", "Poza de la Vega", "Pozal de Gallinas", "Pozaldez", "Pozalmuro", "Pozán de Vero", "Pozanco", "Pozo Alcón", "Pozo Cañada", "Pozo de Almoguera", "Pozo de Guadalajara", "Pozo de Urama", "Pozo-Lorente", "Pozoamargo", "Pozoantiguo", "Pozoblanco", "Pozohondo", "Pozondón", "Pozorrubielos de la Mancha", "Pozorrubio", "Pozos de Hinojo", "Pozuel de Ariza", "Pozuel del Campo", "Pozuelo", "Pozuelo de Alarcón", "Pozuelo de Aragón", "Pozuelo de Calatrava", "Pozuelo de la Orden", "Pozuelo de Tábara", "Pozuelo de Zarzón", "Pozuelo del Páramo", "Pozuelo del Rey", "Pozuelo, El", "Pozuelos de Calatrava, Los", "Pradales", "Prádanos de Bureba", "Prádanos de Ojeda", "Pradejón", "Pradell de la Teixeta", "Prádena", "Prádena de Atienza", "Prádena del Rincón", "Prades", "Pradilla de Ebro", "Pradillo", "Prado", "Prado de la Guzpeña", "Prado del Rey", "Pradoluengo", "Prados Redondos", "Pradosegar", "Prat de Comte", "Prat de Llobregat, El", "Pratdip", "Prats de Lluçanès", "Prats de Rei, Els", "Prats i Sansor", "Pravia", "Preixana", "Preixens", "Préjano", "Premià de Dalt", "Premià de Mar", "Presencio", "Preses, Les", "Priaranza del Bierzo", "Priego", "Priego de Córdoba", "Prioro", "Proaza", "Provencio, El", "Prullans", "Pruna", "Puçol", "Puebla de Albortón", "Puebla de Alcocer", "Puebla de Alfindén, La", "Puebla de Almenara", "Puebla de Almoradiel, La", "Puebla de Arenoso", "Puebla de Arganzón, La", "Puebla de Azaba", "Puebla de Beleña", "Puebla de Castro, La", "Puebla de Cazalla, La", "Puebla de Don Fadrique", "Puebla de Don Rodrigo", "Puebla de Guzmán", "Puebla de Híjar, La", "Puebla de la Calzada", "Puebla de la Reina", "Puebla de la Sierra", "Puebla de Lillo", "Puebla de los Infantes, La", "Puebla de Montalbán, La", "Puebla de Obando", "Puebla de Pedraza", "Puebla de San Medel", "Puebla de San Miguel", "Puebla de Sanabria", "Puebla de Sancho Pérez", "Puebla de Valdavia, La", "Puebla de Valles", "Puebla de Valverde, La", "Puebla de Yeltes", "Puebla del Maestre", "Puebla del Príncipe", "Puebla del Prior", "Puebla del Río, La", "Puebla del Salvador", "Pueblanueva, La", "Pueblica de Valverde", "Pueblonuevo del Guadiana", "Puendeluna", "Puente de Domingo Flórez", "Puente de Génave", "Puente de Montañana", "Puente del Arzobispo, El", "Puente del Congosto", "Puente Genil", "Puente la Reina de Jaca", "Puente la Reina/Gares", "Puente Viesgo", "Puentedura", "Puentes Viejas", "Puerta de Segura, La", "Puertas", "Puerto Castilla", "Puerto de Béjar", "Puerto de la Cruz", "Puerto de San Vicente", "Puerto de Santa Cruz", "Puerto de Santa María, El", "Puerto del Rosario", "Puerto Lápice", "Puerto Lumbreras", "Puerto Moral", "Puerto Real", "Puerto Seguro", "Puerto Serrano", "Puértolas", "Puertollano", "Puertomingalvo", "Pueyo", "Pueyo de Araguás, El", "Pueyo de Santa Cruz", "Puig", "Puig-reig", "Puigcerdà", "Puigdàlber", "Puiggròs", "Puigpelat", "Puigpunyent", "Puigverd dAgramunt", "Puigverd de Lleida", "Pujalt", "Pujerra", "Pulgar", "Pulianas", "Pulpí", "Punta Umbría", "Puntagorda", "Puntallana", "Punxín", "Puras", "Purchena", "Purujosa", "Purullena", "Quar, La", "Quart", "Quart de les Valls", "Quart de Poblet", "Quartell", "Quatretonda", "Quatretondeta", "Quel", "Quemada", "Quéntar", "Quer", "Queralbs", "Quero", "Querol", "Quesa", "Quesada", "Quicena", "Quijorna", "Quintana de la Serena", "Quintana del Castillo", "Quintana del Marco", "Quintana del Pidio", "Quintana del Puente", "Quintana Redonda", "Quintana y Congosto", "Quintanabureba", "Quintanaélez", "Quintanaortuño", "Quintanapalla", "Quintanar de la Orden", "Quintanar de la Sierra", "Quintanar del Rey", "Quintanas de Gormaz", "Quintanavides", "Quintanilla de Arriba", "Quintanilla de la Mata", "Quintanilla de Onésimo", "Quintanilla de Onsoña", "Quintanilla de Trigueros", "Quintanilla de Urz", "Quintanilla del Agua y Tordueles", "Quintanilla del Coco", "Quintanilla del Molar", "Quintanilla del Monte", "Quintanilla del Olmo", "Quintanilla San García", "Quintanilla Vivar", "Quintanillas, Las", "Quintela de Leirado", "Quinto", "Quiñonería", "Quiroga", "Quirós", "Quiruelas de Vidriales", "Quismondo", "Rábade", "Rabanales", "Rabanera", "Rabanera del Pinar", "Rábano", "Rábano de Aliste", "Rábanos", "Rábanos, Los", "Rabé de las Calzadas", "Rabós", "Rada de Haro", "Rafal", "Ráfales", "Rafelbuñol/Rafelbunyol", "Rafelcofer", "Rafelguaraf", "Ràfol dAlmúnia, el", "Ráfol de Salem", "Rágama", "Rágol", "Rairiz de Veiga", "Rajadell", "Ramales de la Victoria", "Rambla, La", "Ramirás", "Ramiro", "Rapariegos", "Rascafría", "Rasillo de Cameros, El", "Rasines", "Rasquera", "Rasueros", "Real", "Real de Gandía", "Real de la Jara, El", "Real de San Vicente, El", "Real Sitio de San Ildefonso", "Realejos, Los", "Rebollar", "Rebollar", "Rebolledo de la Torre", "Rebollo", "Rebollosa de Jadraque", "Recas", "Recueja, La", "Recuenco, El", "Recuerda", "Redal, El", "Redecilla del Camino", "Redecilla del Campo", "Redonda, La", "Redondela", "Redován", "Redueña", "Regencós", "Regueras de Arriba", "Regueras, Las", "Regumiel de la Sierra", "Reíllo", "Reina", "Reinosa", "Reinoso", "Reinoso de Cerrato", "Relleu", "Rellinars", "Rello", "Remolinos", "Remondo", "Rena", "Renau", "Renedo de Esgueva", "Renedo de la Vega", "Renera", "Renieblas", "Reocín", "Requejo", "Requena", "Requena de Campos", "Respenda de la Peña", "Retamal de Llerena", "Retamoso de la Jara", "Retascón", "Retiendas", "Retortillo", "Retortillo de Soria", "Retuerta", "Retuerta del Bullaque", "Reus", "Revellinos", "Revenga de Campos", "Revilla de Collazos", "Revilla del Campo", "Revilla Vallejera", "Revilla y Ahedo, La", "Revillarruz", "Reyero", "Rezmondo", "Reznos", "Riaguas de San Bartolomé", "Rialp", "Rianxo", "Riaño", "Riaza", "Riba de Escalote, La", "Riba de Saelices", "Riba-roja de Túria", "Riba-roja dEbre", "Riba, La", "Ribadavia", "Ribadedeva", "Ribadeo", "Ribadesella", "Ribadumia", "Ribaforada", "Ribafrecha", "Ribamontán al Mar", "Ribamontán al Monte", "Ribas de Campos", "Ribas de Sil", "Ribatejada", "Ribeira", "Ribeira de Piquín", "Ribera Baja/Erribera Beitia", "Ribera de Arriba", "Ribera del Fresno", "Ribera dOndara", "Ribera dUrgellet", "Riberos de la Cueza", "Ribes de Freser", "Ribesalbes", "Ribota", "Ricla", "Ricote", "Riego de la Vega", "Riello", "Riells i Viabrea", "Rielves", "Riera de Gaià, La", "Rillo", "Rillo de Gallo", "Rincón de la Victoria", "Rincón de Soto", "Rinconada de la Sierra, La", "Rinconada, La", "Riner", "Riocabado", "Riocavado de la Sierra", "Riodeva", "Riofrío", "Riofrío de Aliste", "Riofrío de Riaza", "Riofrío del Llano", "Riogordo", "Rioja", "Riola", "Riolobos", "Rionansa", "Rionegro del Puente", "Riópar", "Riós", "Riosa", "Rioseco de Soria", "Rioseco de Tapia", "Riotorto", "Riotuerto", "Ripoll", "Ripollet", "Risco", "Riu de Cerdanya", "Riudarenes", "Riudaura", "Riudecanyes", "Riudecols", "Riudellots de la Selva", "Riudoms", "Riumors", "Rivas-Vaciamadrid", "Rivilla de Barajas", "Roa", "Roales", "Roales de Campos", "Robla, La", "Robladillo", "Robleda", "Robleda-Cervantes", "Robledillo de Gata", "Robledillo de la Jara", "Robledillo de la Vera", "Robledillo de Mohernando", "Robledillo de Trujillo", "Robledo", "Robledo de Chavela", "Robledo de Corpes", "Robledo del Mazo", "Robledo, El", "Robledollano", "Robliza de Cojos", "Robregordo", "Robres", "Robres del Castillo", "Roca de la Sierra, La", "Roca del Vallès, La", "Rocafort", "Rocafort de Queralt", "Rociana del Condado", "Roda de Andalucía, La", "Roda de Barà", "Roda de Eresma", "Roda de Ter", "Roda, La", "Rodeiro", "Ródenas", "Rodezno", "Rodonyà", "Roelos de Sayago", "Rois", "Rojales", "Rojas", "Rollamienta", "Rollán", "Romana, la", "Romangordo", "Romanillos de Atienza", "Romanones", "Romanos", "Romanzado", "Romeral, El", "Roncal/Erronkari", "Ronda", "Ronquillo, El", "Roperuelos del Páramo", "Roquetas de Mar", "Roquetes", "Rosal de la Frontera", "Rosal, O", "Rosalejo", "Rosario, El", "Roses", "Rosinos de la Requejada", "Rossell", "Rosselló", "Rota", "Rotglà i Corberà", "Rótova", "Roturas", "Rourell, El", "Royo, El", "Royuela", "Royuela de Río Franco", "Rozalén del Monte", "Rozas de Madrid, Las", "Rozas de Puerto Real", "Rozas de Valdearroyo, Las", "Rúa, A", "Ruanes", "Rubena", "Rubí", "Rubí de Bracamonte", "Rubiá", "Rubiales", "Rubielos de la Cérida", "Rubielos de Mora", "Rubió", "Rubio, El", "Rubite", "Rublacedo de Abajo", "Rucandio", "Rueda", "Rueda de Jalón", "Rueda de la Sierra", "Ruente", "Ruesca", "Ruesga", "Rugat", "Ruidera", "Ruiloba", "Rupià", "Rupit i Pruit", "Rus", "Rute", "Sabadell", "Sabero", "Sabiñán", "Sabiñánigo", "Sabiote", "Sacañet", "Sacecorbo", "Saceda-Trasierra", "Sacedón", "Saceruela", "Sacramenia", "Sada", "Sada", "Sádaba", "Saelices", "Saelices de la Sal", "Saelices de Mayorga", "Saelices el Chico", "Sagàs", "Sagra", "Sagrada, La", "Sagunto/Sagunt", "Sahagún", "Sahugo, El", "Sahún", "Sajazarra", "Salamanca", "Salar", "Salares", "Salas", "Salas Altas", "Salas Bajas", "Salas de Bureba", "Salas de los Infantes", "Salàs de Pallars", "Salce", "Salceda de Caselas", "Salcedillo", "Saldaña", "Saldaña de Burgos", "Saldeana", "Saldes", "Saldías", "Saldón", "Salduero", "Salem", "Sales de Llierca", "Salillas", "Salillas de Jalón", "Salinas", "Salinas de Oro/Jaitz", "Salinas de Pisuerga", "Salinas del Manzano", "Salines, Ses", "Salinillas de Bureba", "Sallent", "Sallent de Gállego", "Salmerón", "Salmeroncillos", "Salmoral", "Salobral", "Salobre", "Salobreña", "Salomó", "Salorino", "Salou", "Salt", "Salteras", "Salvacañete", "Salvadiós", "Salvador de Zapardiel", "Salvaleón", "Salvaterra de Miño", "Salvatierra de Esca", "Salvatierra de los Barros", "Salvatierra de Santiago", "Salvatierra de Tormes", "Salvatierra/Agurain", "Salzadella, la", "Samaniego", "Samboal", "Samir de los Caños", "Samos", "Samper de Calanda", "Samper del Salz", "San Adrián", "San Adrián de Juarros", "San Adrián del Valle", "San Agustín", "San Agustín del Guadalix", "San Agustín del Pozo", "San Amaro", "San Andrés del Congosto", "San Andrés del Rabanedo", "San Andrés del Rey", "San Andrés y Sauces", "San Antonio de Benagéber", "San Asensio", "San Bartolomé", "San Bartolomé de Béjar", "San Bartolomé de Corneja", "San Bartolomé de la Torre", "San Bartolomé de las Abiertas", "San Bartolomé de Pinares", "San Bartolomé de Tirajana", "San Carlos del Valle", "San Cebrián de Campos", "San Cebrián de Castro", "San Cebrián de Mazote", "San Cebrián de Mudá", "San Cibrao das Viñas", "San Clemente", "San Cristóbal de Boedo", "San Cristóbal de Cuéllar", "San Cristóbal de Entreviñas", "San Cristóbal de la Cuesta", "San Cristóbal de La Laguna", "San Cristóbal de la Polantera", "San Cristóbal de la Vega", "San Cristóbal de Segovia", "San Cristovo de Cea", "San Emiliano", "San Esteban de Gormaz", "San Esteban de la Sierra", "San Esteban de Litera", "San Esteban de los Patos", "San Esteban de Nogales", "San Esteban de Zapardiel", "San Esteban del Molar", "San Esteban del Valle", "San Felices", "San Felices de Buelna", "San Felices de los Gallegos", "San Fernando", "San Fernando de Henares", "San Fulgencio", "San García de Ingelmos", "San Isidro", "San Javier", "San José del Valle", "San Juan de Aznalfarache", "San Juan de Gredos", "San Juan de la Encinilla", "San Juan de la Nava", "San Juan de la Rambla", "San Juan de Plan", "San Juan del Molinillo", "San Juan del Monte", "San Juan del Olmo", "San Juan del Puerto", "San Justo", "San Justo de la Vega", "San Leonardo de Yagüe", "San Llorente", "San Lorenzo de Calatrava", "San Lorenzo de El Escorial", "San Lorenzo de la Parrilla", "San Lorenzo de Tormes", "San Mamés de Burgos", "San Mamés de Campos", "San Martín de Boniches", "San Martín de la Vega", "San Martín de la Vega del Alberche", "San Martín de la Virgen de Moncayo", "San Martín de Montalbán", "San Martín de Oscos", "San Martín de Pusa", "San Martín de Rubiales", "San Martín de Trevejo", "San Martín de Unx", "San Martín de Valdeiglesias", "San Martín de Valderaduey", "San Martín de Valvení", "San Martín del Castañar", "San Martín del Pimpollar", "San Martín del Rey Aurelio", "San Martín del Río", "San Martín y Mudrián", "San Mateo de Gállego", "San Miguel de Abona", "San Miguel de Aguayo", "San Miguel de Bernuy", "San Miguel de Corneja", "San Miguel de la Ribera", "San Miguel de Salinas", "San Miguel de Serrezuela", "San Miguel de Valero", "San Miguel del Arroyo", "San Miguel del Cinca", "San Miguel del Pino", "San Miguel del Robledo", "San Miguel del Valle", "San Millán de la Cogolla", "San Millán de Lara", "San Millán de los Caballeros", "San Millán de Yécora", "San Millán/Donemiliaga", "San Morales", "San Muñoz", "San Nicolás del Puerto", "San Pablo de la Moraleja", "San Pablo de los Montes", "San Pascual", "San Pedro", "San Pedro Bercianos", "San Pedro de Ceque", "San Pedro de Gaíllos", "San Pedro de la Nave-Almendra", "San Pedro de Latarce", "San Pedro de Mérida", "San Pedro de Rozados", "San Pedro del Arroyo", "San Pedro del Pinatar", "San Pedro del Romeral", "San Pedro del Valle", "San Pedro Manrique", "San Pedro Palmiches", "San Pelayo", "San Pelayo de Guareña", "San Rafael del Río", "San Román de Cameros", "San Román de Hornija", "San Román de la Cuba", "San Román de los Montes", "San Roque", "San Roque de Riomiera", "San Sadurniño", "San Salvador", "San Sebastián de la Gomera", "San Sebastián de los Ballesteros", "San Sebastián de los Reyes", "San Silvestre de Guzmán", "San Tirso de Abres", "San Torcuato", "San Vicente de Alcántara", "San Vicente de Arévalo", "San Vicente de la Barquera", "San Vicente de la Cabeza", "San Vicente de la Sonsierra", "San Vicente del Palacio", "San Vicente del Raspeig/Sant Vicent del Raspeig", "San Vicente del Valle", "San Vitero", "San Xoán de Río", "Sanaüja", "Sancedo", "Sanchidrián", "Sanchón de la Ribera", "Sanchón de la Sagrada", "Sanchonuño", "Sanchorreja", "Sanchotello", "Sancti-Spíritus", "Sancti-Spíritus", "Sandiás", "Sando", "Sanet y Negrals", "Sangarcía", "Sangarrén", "Sangüesa/Zangoza", "Sanlúcar de Barrameda", "Sanlúcar de Guadiana", "Sanlúcar la Mayor", "Sansol", "Sant Adrià de Besòs", "Sant Agustí de Lluçanès", "Sant Andreu de la Barca", "Sant Andreu de Llavaneres", "Sant Andreu Salou", "Sant Aniol de Finestres", "Sant Antoni de Portmany", "Sant Antoni de Vilamajor", "Sant Bartomeu del Grau", "Sant Boi de Llobregat", "Sant Boi de Lluçanès", "Sant Carles de la Ràpita", "Sant Cebrià de Vallalta", "Sant Celoni", "Sant Climent de Llobregat", "Sant Climent Sescebes", "Sant Cugat del Vallès", "Sant Cugat Sesgarrigues", "Sant Esteve de la Sarga", "Sant Esteve de Palautordera", "Sant Esteve Sesrovires", "Sant Feliu de Buixalleu", "Sant Feliu de Codines", "Sant Feliu de Guíxols", "Sant Feliu de Llobregat", "Sant Feliu de Pallerols", "Sant Feliu Sasserra", "Sant Ferriol", "Sant Fost de Campsentelles", "Sant Fruitós de Bages", "Sant Gregori", "Sant Guim de Freixenet", "Sant Guim de la Plana", "Sant Hilari Sacalm", "Sant Hipòlit de Voltregà", "Sant Iscle de Vallalta", "Sant Jaume de Frontanyà", "Sant Jaume de Llierca", "Sant Jaume dels Domenys", "Sant Jaume dEnveja", "Sant Joan", "Sant Joan dAlacant", "Sant Joan de Labritja", "Sant Joan de les Abadesses", "Sant Joan de Mollet", "Sant Joan de Moró", "Sant Joan de Vilatorrada", "Sant Joan Despí", "Sant Joan les Fonts", "Sant Joanet", "Sant Jordi Desvalls", "Sant Jordi/San Jorge", "Sant Josep de sa Talaia", "Sant Julià de Cerdanyola", "Sant Julià de Ramis", "Sant Julià de Vilatorta", "Sant Julià del Llor i Bonmatí", "Sant Just Desvern", "Sant Llorenç de la Muga", "Sant Llorenç de Morunys", "Sant Llorenç des Cardassar", "Sant Llorenç dHortons", "Sant Llorenç Savall", "Sant Lluís", "Sant Martí dAlbars", "Sant Martí de Centelles", "Sant Martí de Llémena", "Sant Martí de Riucorb", "Sant Martí de Tous", "Sant Martí Sarroca", "Sant Martí Sesgueioles", "Sant Martí Vell", "Sant Mateu", "Sant Mateu de Bages", "Sant Miquel de Campmajor", "Sant Miquel de Fluvià", "Sant Mori", "Sant Pau de Segúries", "Sant Pere de Ribes", "Sant Pere de Riudebitlles", "Sant Pere de Torelló", "Sant Pere de Vilamajor", "Sant Pere Pescador", "Sant Pere Sallavinera", "Sant Pol de Mar", "Sant Quintí de Mediona", "Sant Quirze de Besora", "Sant Quirze del Vallès", "Sant Quirze Safaja", "Sant Ramon", "Sant Sadurní dAnoia", "Sant Sadurní dOsormort", "Sant Salvador de Guardiola", "Sant Vicenç de Castellet", "Sant Vicenç de Montalt", "Sant Vicenç de Torelló", "Sant Vicenç dels Horts", "Santa Amalia", "Santa Ana", "Santa Ana de Pusa", "Santa Ana la Real", "Santa Bàrbara", "Santa Bárbara de Casa", "Santa Brígida", "Santa Cecilia", "Santa Cecília de Voltregà", "Santa Cecilia del Alcor", "Santa Cilia", "Santa Clara de Avedillo", "Santa Coloma", "Santa Coloma de Cervelló", "Santa Coloma de Farners", "Santa Coloma de Gramenet", "Santa Coloma de Queralt", "Santa Colomba de Curueño", "Santa Colomba de las Monjas", "Santa Colomba de Somoza", "Santa Comba", "Santa Cristina dAro", "Santa Cristina de la Polvorosa", "Santa Cristina de Valmadrigal", "Santa Croya de Tera", "Santa Cruz de Bezana", "Santa Cruz de Boedo", "Santa Cruz de Grío", "Santa Cruz de la Palma", "Santa Cruz de la Salceda", "Santa Cruz de la Serós", "Santa Cruz de la Sierra", "Santa Cruz de la Zarza", "Santa Cruz de los Cáñamos", "Santa Cruz de Marchena", "Santa Cruz de Moncayo", "Santa Cruz de Moya", "Santa Cruz de Mudela", "Santa Cruz de Nogueras", "Santa Cruz de Paniagua", "Santa Cruz de Pinares", "Santa Cruz de Tenerife", "Santa Cruz de Yanguas", "Santa Cruz del Comercio", "Santa Cruz del Retamar", "Santa Cruz del Valle", "Santa Cruz del Valle Urbión", "Santa Elena", "Santa Elena de Jamuz", "Santa Engracia del Jubera", "Santa Eufemia", "Santa Eufemia del Arroyo", "Santa Eufemia del Barco", "Santa Eugènia", "Santa Eugènia de Berga", "Santa Eulalia", "Santa Eulalia Bajera", "Santa Eulalia de Gállego", "Santa Eulalia de Oscos", "Santa Eulàlia de Riuprimer", "Santa Eulàlia de Ronçana", "Santa Eulalia del Río", "Santa Fe", "Santa Fe de Mondújar", "Santa Fe del Penedès", "Santa Gadea del Cid", "Santa Inés", "Santa Llogaia dÀlguema", "Santa Lucía de Tirajana", "Santa Magdalena de Pulpis", "Santa Margalida", "Santa Margarida de Montbui", "Santa Margarida i els Monjos", "Santa Maria de Besora", "Santa María de Cayón", "Santa Maria de Corcó", "Santa María de Dulcis", "Santa María de Guía de Gran Canaria", "Santa María de Huerta", "Santa María de la Alameda", "Santa María de la Isla", "Santa María de la Vega", "Santa María de las Hoyas", "Santa María de los Caballeros", "Santa María de los Llanos", "Santa Maria de Martorelles", "Santa Maria de Merlès", "Santa Maria de Miralles", "Santa María de Ordás", "Santa Maria de Palautordera", "Santa María de Sando", "Santa María de Valverde", "Santa María del Arroyo", "Santa María del Berrocal", "Santa María del Camí", "Santa María del Campo", "Santa María del Campo Rus", "Santa María del Cubillo", "Santa María del Invierno", "Santa María del Mercadillo", "Santa María del Monte de Cea", "Santa María del Páramo", "Santa María del Tiétar", "Santa María del Val", "Santa Maria dOló", "Santa María la Real de Nieva", "Santa María Rivarredonda", "Santa Marina del Rey", "Santa Marta", "Santa Marta de Magasca", "Santa Marta de Tormes", "Santa Marta del Cerro", "Santa Olalla", "Santa Olalla de Bureba", "Santa Olalla del Cala", "Santa Oliva", "Santa Pau", "Santa Perpètua de Mogoda", "Santa Pola", "Santa Susanna", "Santa Úrsula", "Santacara", "Santaella", "Santaliestra y San Quílez", "Santander", "Santanyí", "Santas Martas", "Santed", "Santervás de Campos", "Santervás de la Vega", "Santiago de Alcántara", "Santiago de Calatrava", "Santiago de Compostela", "Santiago de la Puebla", "Santiago del Campo", "Santiago del Collado", "Santiago del Teide", "Santiago del Tormes", "Santiago Millas", "Santiago-Pontones", "Santibáñez de Béjar", "Santibáñez de Ecla", "Santibáñez de Esgueva", "Santibáñez de la Peña", "Santibáñez de la Sierra", "Santibáñez de Tera", "Santibáñez de Valcorba", "Santibáñez de Vidriales", "Santibáñez del Val", "Santibáñez el Alto", "Santibáñez el Bajo", "Santillana del Mar", "Santiponce", "Santiso", "Santisteban del Puerto", "Santiurde de Reinosa", "Santiurde de Toranzo", "Santiuste", "Santiuste de Pedraza", "Santiuste de San Juan Bautista", "Santiz", "Santo Adriano", "Santo Domingo de la Calzada", "Santo Domingo de las Posadas", "Santo Domingo de Pirón", "Santo Domingo de Silos", "Santo Domingo-Caudilla", "Santo Tomé", "Santo Tomé de Zabarcos", "Santo Tomé del Puerto", "Santomera", "Santoña", "Santorcaz", "Santos de la Humosa, Los", "Santos de Maimona, Los", "Santos, Los", "Santovenia", "Santovenia de la Valdoncina", "Santovenia de Pisuerga", "Santoyo", "Santpedor", "Santurde de Rioja", "Santurdejo", "Santurtzi", "Sanxenxo", "Sanzoles", "Sardón de Duero", "Sardón de los Frailes", "Sargentes de la Lora", "Sariego", "Sariegos", "Sariñena", "Saro", "Sarracín", "Sarral", "Sarratella", "Sarreaus", "Sarria", "Sarrià de Ter", "Sarriés/Sartze", "Sarrión", "Sarroca de Bellera", "Sarroca de Lleida", "Sartaguda", "Sartajada", "Sasamón", "Sástago", "Saúca", "Saucedilla", "Saucejo, El", "Saucelle", "Sauquillo de Cabezas", "Saus, Camallera i Llampaies", "Sauzal, El", "Savallà del Comtat", "Saviñao, O", "Sax", "Sayalonga", "Sayatón", "Sebúlcor", "Seca, La", "Secastilla", "Secuita, La", "Sedaví", "Sedella", "Sediles", "Segart", "Segorbe", "Segovia", "Segura", "Segura de la Sierra", "Segura de León", "Segura de los Baños", "Segura de Toro", "Segurilla", "Seira", "Selas", "Selaya", "Sella", "Sellent", "Selva", "Selva de Mar, La", "Selva del Camp, La", "Semillas", "Sempere", "Sena", "Sena de Luna", "Senan", "Sencelles", "Senés", "Senés de Alcubierre", "Sénia, La", "Senija", "Seno", "Senterada", "Sentiu de Sió, La", "Sentmenat", "Senyera", "Sepulcro-Hilario", "Sepúlveda", "Sequera de Fresno", "Sequera de Haza, La", "Sequeros", "Serinyà", "Serna del Monte, La", "Serna, La", "Serón", "Serón de Nágima", "Seròs", "Serra", "Serra de Daró", "Serrada", "Serrada, La", "Serradilla", "Serradilla del Arroyo", "Serradilla del Llano", "Serranillos", "Serranillos del Valle", "Serrejón", "Sesa", "Seseña", "Sesma", "Sestao", "Sestrica", "Sesué", "Setcases", "Setenil de las Bodegas", "Setiles", "Seu dUrgell, La", "Seva", "Sevilla", "Sevilla la Nueva", "Sevilleja de la Jara", "Sidamon", "Sienes", "Siero", "Sierpe, La", "Sierra de Fuentes", "Sierra de Luna", "Sierra de Yeguas", "Sierra Engarcerán", "Sierro", "Siétamo", "Siete Aguas", "Siete Iglesias de Trabancos", "Sieteiglesias de Tormes", "Sigeres", "Sigüenza", "Sigüés", "Siles", "Silla", "Silleda", "Silos, Los", "Sils", "Simancas", "Simat de la Valldigna", "Sinarcas", "Sineu", "Singra", "Sinlabajos", "Siruela", "Sisamón", "Sisante", "Sitges", "Siurana", "Soba", "Sober", "Sobradiel", "Sobradillo", "Sobrado", "Sobrado", "Sobremunt", "Sobrescobio", "Socovos", "Socuéllamos", "Sojuela", "Solana de Ávila", "Solana de los Barros", "Solana de Rioalmar", "Solana del Pino", "Solana, La", "Solanillos del Extremo", "Solarana", "Solera de Gabaldón", "Soleràs, El", "Soliedra", "Solivella", "Sollana", "Sóller", "Solórzano", "Solosancho", "Solsona", "Somiedo", "Somolinos", "Somontín", "Somosierra", "Somozas, As", "Son Servera", "Sondika", "Soneja", "Sonseca", "Sopeira", "Sopelana", "Soportújar", "Sopuerta", "Sora", "Soraluze-Placencia de las Armas", "Sorbas", "Sordillos", "Soria", "Soriguera", "Sorihuela", "Sorihuela del Guadalimar", "Sorlada", "Sort", "Sorvilán", "Sorzano", "Sos del Rey Católico", "Soses", "Sot de Chera", "Sot de Ferrer", "Sotalbo", "Sotés", "Sotillo", "Sotillo de la Adrada", "Sotillo de la Ribera", "Sotillo de las Palomas", "Sotillo del Rincón", "Sotillo, El", "Soto de Cerrato", "Soto de la Vega", "Soto del Barco", "Soto del Real", "Soto en Cameros", "Soto y Amío", "Sotobañado y Priorato", "Sotodosos", "Sotonera, La", "Sotorribas", "Sotosalbos", "Sotoserrano", "Sotragero", "Sotresgudo", "Soutomaior", "Suances", "Subirats", "Sudanell", "Sueca", "Suellacabras", "Sueras/Suera", "Suflí", "Sukarrieta", "Sumacàrcer", "Sunbilla", "Sunyer", "Súria", "Susinos del Páramo", "Susqueda"}));
        }
        if (jComboBox_filtroAZReserva.getSelectedItem().toString().equals("De la T a la Z")) {
            jComboBox_poblacionReserva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Tabanera de Cerrato", "Tabanera de Valdavia", "Tabanera la Luenga", "Tábara", "Tabera de Abajo", "Tabernas", "Taberno", "Taboada", "Taboadela", "Tabuenca", "Tacoronte", "Tafalla", "Tagamanent", "Taha, La", "Tahal", "Tajahuerce", "Tajueco", "Tala, La", "Talamanca", "Talamanca de Jarama", "Talamantes", "Talarn", "Talarrubias", "Talaván", "Talavera", "Talavera de la Reina", "Talavera la Real", "Talaveruela de la Vera", "Talayuela", "Talayuelas", "Tales", "Táliga", "Tallada dEmpordà, La", "Talveila", "Tamajón", "Tamames", "Támara de Campos", "Tamarite de Litera", "Tamariz de Campos", "Tamarón", "Tamurejo", "Tanque, El", "Tapia de Casariego", "Tapioles", "Taradell", "Taragudo", "Taramundi", "Tarancón", "Taravilla", "Tarazona", "Tarazona de Guareña", "Tarazona de la Mancha", "Tàrbena", "Tardáguila", "Tardajos", "Tardelcuende", "Tardienta", "Tariego de Cerrato", "Tarifa", "Taroda", "Tarragona", "Tàrrega", "Tarrés", "Tarroja de Segarra", "Tartanedo", "Tauste", "Tavernes Blanques", "Tavernes de la Valldigna", "Tavèrnoles", "Tavertet", "Tazacorte", "Teba", "Tébar", "Tegueste", "Teguise", "Teià", "Teixeira, A", "Tejada", "Tejadillos", "Tejado", "Tejado, El", "Tejeda", "Tejeda de Tiétar", "Tejeda y Segoyuela", "Telde", "Tella-Sin", "Tembleque", "Tendilla", "Tenebrón", "Teo", "Teresa", "Teresa de Cofrentes", "Térmens", "Teror", "Terque", "Terrades", "Terradillos", "Terradillos de Esgueva", "Terrassa", "Terrateig", "Terrer", "Terriente", "Terrinches", "Terroba", "Teruel", "Terzaga", "Teulada", "Teverga", "Tiana", "Tías", "Tibi", "Tiebas-Muruarte de Reta", "Tiedra", "Tielmes", "Tiemblo, El", "Tierga", "Tierz", "Tierzo", "Tijarafe", "Tíjola", "Tinajas", "Tinajo", "Tineo", "Tinieblas de la Sierra", "Tiñosillos", "Tirapu", "Tirgo", "Tírig", "Tírvia", "Titaguas", "Titulcia", "Tiurana", "Tivenys", "Tivissa", "Toba, La", "Tobar", "Tobarra", "Tobed", "Tobía", "Toboso, El", "Tocina", "Todolella", "Toén", "Toga", "Tojos, Los", "Tolbaños", "Toledo", "Tollos", "Tolocirio", "Tolosa", "Tolox", "Tolva", "Tomares", "Tomelloso", "Tomiño", "Tona", "Topas", "Toques", "Torà", "Toral de los Guzmanes", "Toral de los Vados", "Torás", "Tordehumos", "Tordellego", "Tordelrábano", "Tordera", "Tordesillas", "Tordesilos", "Tordillos", "Tordoia", "Tordómar", "Torelló", "Toreno", "Torija", "Toril", "Toril y Masegoso", "Torla", "Torlengua", "Tormantos", "Tormellas", "Tormón", "Tormos", "Torms, Els", "Tornabous", "Tornadizo, El", "Tornadizos de Ávila", "Tornavacas", "Torno, El", "Tornos", "Toro", "Toro, El", "Torquemada", "Torralba", "Torralba de Aragón", "Torralba de Calatrava", "Torralba de los Frailes", "Torralba de los Sisones", "Torralba de Oropesa", "Torralba de Ribota", "Torralba del Pinar", "Torralba del Río", "Torralbilla", "Torre Alháquime", "Torre de Arcas", "Torre de Cabdella, La", "Torre de Claramunt, La", "Torre de Don Miguel", "Torre de Esgueva", "Torre de Esteban Hambrán, La", "Torre de Fontaubella, La", "Torre de Juan Abad", "Torre de las Arcas", "Torre de lEspanyol, La", "Torre de Miguel Sesmero", "Torre de Peñafiel", "Torre de Santa María", "Torre del Bierzo", "Torre del Burgo", "Torre del Campo", "Torre del Compte", "Torre del Valle, La", "Torre dEn Besora, la", "Torre den Doménec, la", "Torre en Cameros", "Torre la Ribera", "Torre los Negros", "Torre Val de San Pedro", "Torre-Cardela", "Torre-Pacheco", "Torre-serona", "Torre, La", "Torreadrada", "Torrebaja", "Torrebesses", "Torreblacos", "Torreblanca", "Torreblascopedro", "Torrecaballeros", "Torrecampo", "Torrechiva", "Torrecilla de Alcañiz", "Torrecilla de la Abadesa", "Torrecilla de la Jara", "Torrecilla de la Orden", "Torrecilla de la Torre", "Torrecilla de los Ángeles", "Torrecilla del Monte", "Torrecilla del Pinar", "Torrecilla del Rebollar", "Torrecilla en Cameros", "Torrecilla sobre Alesanco", "Torrecillas de la Tiesa", "Torrecuadrada de Molina", "Torrecuadradilla", "Torredembarra", "Torredonjimeno", "Torrefarrera", "Torrefeta i Florejacs", "Torregalindo", "Torregamones", "Torregrossa", "Torrehermosa", "Torreiglesias", "Torrejón de Ardoz", "Torrejón de la Calzada", "Torrejón de Velasco", "Torrejón del Rey", "Torrejón el Rubio", "Torrejoncillo", "Torrejoncillo del Rey", "Torrelacárcel", "Torrelaguna", "Torrelameu", "Torrelapaja", "Torrelara", "Torrelavega", "Torrelavit", "Torrella", "Torrellas", "Torrelles de Foix", "Torrelles de Llobregat", "Torrelobatón", "Torrelodones", "Torremanzanas", "Torremayor", "Torremejía", "Torremenga", "Torremocha", "Torremocha de Jadraque", "Torremocha de Jarama", "Torremocha de Jiloca", "Torremocha del Campo", "Torremocha del Pinar", "Torremochuela", "Torremolinos", "Torremontalbo", "Torremormojón", "Torrent", "Torrent", "Torrente de Cinca", "Torrenueva", "Torreorgaz", "Torrepadre", "Torreperogil", "Torrequemada", "Torres", "Torres de Albánchez", "Torres de Albarracín", "Torres de Alcanadre", "Torres de Barbués", "Torres de Berrellén", "Torres de Cotillas, Las", "Torres de la Alameda", "Torres de Segre", "Torres del Carrizal", "Torres del Río", "Torres Torres", "Torresandino", "Torrescárcela", "Torresmenudas", "Torrevelilla", "Torrevieja", "Torrico", "Torrijas", "Torrijo de la Cañada", "Torrijo del Campo", "Torrijos", "Torroella de Fluvià", "Torroella de Montgrí", "Torroja del Priorat", "Torrox", "Torrubia", "Torrubia de Soria", "Torrubia del Campo", "Torrubia del Castillo", "Tortellà", "Tórtola de Henares", "Tórtoles", "Tórtoles de Esgueva", "Tortosa", "Tortuera", "Tortuero", "Torvizcón", "Tosantos", "Toses", "Tosos", "Tossa de Mar", "Totalán", "Totana", "Totanés", "Touro", "Tous", "Trabada", "Trabadelo", "Trabanca", "Trabazos", "Tragacete", "Traíd", "Traiguera", "Tramacastiel", "Tramacastilla", "Tramaced", "Trasierra", "Trasmiras", "Trasmoz", "Trasobares", "Traspinedo", "Trazo", "Trebujena", "Trefacio", "Tremedal de Tormes", "Tremp", "Tres Cantos", "Tres Villas, Las", "Trescasas", "Tresjuncos", "Trespaderne", "Tresviso", "Trévago", "Trevélez", "Treviana", "Triacastela", "Tribaldos", "Tricio", "Trigueros", "Trigueros del Valle", "Trijueque", "Trillo", "Triollo", "Tronchón", "Truchas", "Trucios-Turtzioz", "Trujillanos", "Trujillo", "Tubilla del Agua", "Tubilla del Lago", "Tudanca", "Tudela", "Tudela de Duero", "Tudelilla", "Tuéjar", "Tui", "Tuineje", "Tulebras", "Turcia", "Turégano", "Turís", "Turleque", "Turón", "Turre", "Turrillas", "Úbeda", "Ubide", "Ubrique", "Ucar", "Uceda", "Ucero", "Uclés", "Udías", "Ugao-Miraballes", "Ugena", "Ugíjar", "Uharte-Arakil", "Ujados", "Ujué", "Ulea", "Uleila del Campo", "Ullà", "Ullastrell", "Ullastret", "Ulldecona", "Ulldemolins", "Ultramort", "Ultzama", "Umbrete", "Umbrías", "Uncastillo", "Unciti", "Undués de Lerda", "Unión de Campos, La", "Unión, La", "Unzué/Untzue", "Uña", "Uña de Quintana", "Úrbel del Castillo", "Urda", "Urdazubi/Urdax", "Urdiain", "Urdiales del Páramo", "Urduliz", "Urduña/Orduña", "Urkabustaiz", "Urnieta", "Urones de Castroponce", "Urrácal", "Urraul Alto", "Urraul Bajo", "Urrea de Gaén", "Urrea de Jalón", "Urretxu", "Urriés", "Urrotz", "Urroz-Villa", "Urueña", "Urueñas", "Uruñuela", "Urús", "Urzainqui/Urzainki", "Usagre", "Used", "Useras/Useres, les", "Usurbil", "Utande", "Utebo", "Uterga", "Utiel", "Utrera", "Utrillas", "Uztárroz/Uztarroze", "Vacarisses", "Vadillo", "Vadillo de la Guareña", "Vadillo de la Sierra", "Vadocondes", "Vajol, La", "Val de San Lorenzo", "Val de San Martín", "Val de San Vicente", "Val do Dubra", "Valacloche", "Valadouro, O", "Valbona", "Valbuena de Duero", "Valbuena de Pisuerga", "Valcabado", "Valdáliga", "Valdaracete", "Valdarachas", "Valdastillas", "Valde-Ucieza", "Valdealgorfa", "Valdeande", "Valdearcos de la Vega", "Valdearenas", "Valdeavellano", "Valdeavellano de Tera", "Valdeavero", "Valdeaveruelo", "Valdecaballeros", "Valdecañas de Tajo", "Valdecarros", "Valdecasa", "Valdecolmenas, Los", "Valdeconcha", "Valdecuenca", "Valdefinjas", "Valdefresno", "Valdefuentes", "Valdefuentes de Sangusín", "Valdefuentes del Páramo", "Valdeganga", "Valdegeña", "Valdegovía/Gaubea", "Valdegrudas", "Valdehijaderos", "Valdehorna", "Valdehúncar", "Valdelacalzada", "Valdelacasa", "Valdelacasa de Tajo", "Valdelageve", "Valdelagua del Cerro", "Valdelaguna", "Valdelarco", "Valdelcubo", "Valdelinares", "Valdelosa", "Valdeltormo", "Valdelugueros", "Valdemadera", "Valdemaluque", "Valdemanco", "Valdemanco del Esteras", "Valdemaqueda", "Valdemeca", "Valdemierque", "Valdemora", "Valdemorales", "Valdemorillo", "Valdemorillo de la Sierra", "Valdemoro", "Valdemoro-Sierra", "Valdenebro", "Valdenebro de los Valles", "Valdenuño Fernández", "Valdeobispo", "Valdeolea", "Valdeolivas", "Valdeolmillos", "Valdeolmos-Alalpardo", "Valdepeñas", "Valdepeñas de Jaén", "Valdepeñas de la Sierra", "Valdepiélago", "Valdepiélagos", "Valdepolo", "Valdeprado", "Valdeprado del Río", "Valdeprados", "Valderas", "Valderrábano", "Valderrebollo", "Valderredible", "Valderrey", "Valderrobres", "Valderrodilla", "Valderrodrigo", "Valderrueda", "Valdés", "Valdesamario", "Valdescorriel", "Valdesotos", "Valdestillas", "Valdetorres", "Valdetorres de Jarama", "Valdetórtola", "Valdevacas de Montejo", "Valdevacas y Guijar", "Valdeverdeja", "Valdevimbre", "Valdezate", "Valdilecha", "Valdorros", "Valdoviño", "Valdunciel", "Valdunquillo", "Valencia", "Valencia de Alcántara", "Valencia de Don Juan", "Valencia de las Torres", "Valencia del Mombuey", "Valencia del Ventoso", "Valencina de la Concepción", "Valenzuela", "Valenzuela de Calatrava", "Valeras, Las", "Valero", "Valfarta", "Valfermoso de Tajuña", "Valga", "Valgañón", "Valhermoso", "Valhermoso de la Fuente", "Valjunquera", "Vall dAlba", "Vall dAlcalà, la", "Vall de Almonacid", "Vall de Bianya, La", "Vall de Boí, La", "Vall de Cardós", "Vall de Gallinera", "Vall de Laguar, la", "Vall dEbo, la", "Vall den Bas, La", "Vall dUixó, la", "Vall-llobrega", "Vallada", "Valladolid", "Vallanca", "Vallarta de Bureba", "Vallat", "Vallbona dAnoia", "Vallbona de les Monges", "Vallcebre", "Vallclara", "Valldemossa", "Valle de Abdalajís", "Valle de Altomira, El", "Valle de Bardají", "Valle de Cerrato", "Valle de Hecho", "Valle de la Serena", "Valle de las Navas", "Valle de Lierp", "Valle de Losa", "Valle de Manzanedo", "Valle de Matamoros", "Valle de Mena", "Valle de Oca", "Valle de Santa Ana", "Valle de Santibáñez", "Valle de Sedano", "Valle de Tabladillo", "Valle de Tobalina", "Valle de Trápaga-Trapagaran", "Valle de Valdebezana", "Valle de Valdelaguna", "Valle de Valdelucio", "Valle de Villaverde", "Valle de Yerri/Deierri", "Valle de Zamanzas", "Valle del Retortillo", "Valle del Zalabí", "Valle Gran Rey", "Valle, El", "Vallecillo", "Vallecillo, El", "Vallehermoso", "Vallejera", "Vallejera de Riofrío", "Vallelado", "Valleruela de Pedraza", "Valleruela de Sepúlveda", "Vallés", "Valles de Palenzuela", "Vallesa de la Guareña", "Valleseco", "Vallfogona de Balaguer", "Vallfogona de Ripollès", "Vallfogona de Riucorb", "Vallgorguina", "Vallibona", "Vallirana", "Vallmoll", "Vallromanes", "Valls", "Valls dAguilar, Les", "Valls de Valira, Les", "Valluércanes", "Valmadrid", "Valmala", "Valmojado", "Válor", "Valoria la Buena", "Valpalmas", "Valsalabroso", "Valsalobre", "Valseca", "Valsequillo", "Valsequillo de Gran Canaria", "Valtablado del Río", "Valtajeros", "Valtiendas", "Valtierra", "Valtorres", "Valverde", "Valverde de Alcalá", "Valverde de Burguillos", "Valverde de Campos", "Valverde de Júcar", "Valverde de la Vera", "Valverde de la Virgen", "Valverde de Leganés", "Valverde de Llerena", "Valverde de los Arroyos", "Valverde de Mérida", "Valverde de Valdelacasa", "Valverde del Camino", "Valverde del Fresno", "Valverde del Majano", "Valverde-Enrique", "Valverdejo", "Valverdón", "Vandellòs i lHospitalet de lInfant", "Vansa i Fórnols, La", "Vara de Rey", "Veciana", "Vecilla, La", "Vecinos", "Vedra", "Vega de Espinareda", "Vega de Infanzones", "Vega de Liébana", "Vega de Pas", "Vega de Ruiponce", "Vega de San Mateo", "Vega de Santa María", "Vega de Tera", "Vega de Tirados", "Vega de Valcarce", "Vega de Valdetronco", "Vega de Villalobos", "Vega del Codorno", "Vegacervera", "Vegadeo", "Vegalatrave", "Veganzones", "Vegaquemada", "Vegas de Matute", "Vegas del Condado", "Vegas del Genil", "Vegaviana", "Veguillas de la Sierra", "Veguillas, Las", "Veiga, A", "Vejer de la Frontera", "Velada", "Velamazán", "Velascálvaro", "Velayos", "Velefique", "Vélez de Benaudalla", "Vélez-Blanco", "Vélez-Málaga", "Vélez-Rubio", "Velilla", "Velilla de Cinca", "Velilla de Ebro", "Velilla de Jiloca", "Velilla de la Sierra", "Velilla de los Ajos", "Velilla de San Antonio", "Velilla del Río Carrión", "Vellés, La", "Vellisca", "Velliza", "Vellón, El", "Vencillón", "Vendrell, El", "Venialbo", "Venta de Baños", "Venta del Moro", "Ventalló", "Ventas con Peña Aguilera, Las", "Ventas de Huelma", "Ventas de Retamosa, Las", "Ventas de San Julián, Las", "Ventosa", "Ventosa de la Cuesta", "Ventosa del Río Almar", "Ventosilla y Tejadilla", "Ventrosa", "Venturada", "Vera", "Vera de Moncayo", "Verdú", "Verea", "Verger, el", "Verges", "Verín", "Vertavillo", "Vespella de Gaià", "Vezdemarbán", "Viacamp y Litera", "Viana", "Viana de Cega", "Viana de Duero", "Viana de Jadraque", "Viana do Bolo", "Viandar de la Vera", "Vianos", "Viator", "Vic", "Vícar", "Vicedo, O", "Vicién", "Victoria de Acentejo, La", "Victoria, La", "Vid de Bureba, La", "Vid de Ojeda, La", "Vid y Barrios, La", "Vidángoz/Bidankoze", "Vidayanes", "Videmala", "Vídola, La", "Vidrà", "Vidreres", "Vielha e Mijaran", "Vierlas", "Vigo", "Viguera", "Vila de Cruces", "Vila-real", "Vila-rodona", "Vila-sacra", "Vila-sana", "Vila-seca", "Vilabella", "Vilabertran", "Vilablareix", "Vilaboa", "Vilada", "Viladamat", "Viladasens", "Viladecans", "Viladecavalls", "Vilademuls", "Viladrau", "Vilafamés", "Vilafant", "Vilaflor", "Vilafranca de Bonany", "Vilafranca del Penedès", "Vilagarcía de Arousa", "Vilagrassa", "Vilajuïga", "Vilalba", "Vilalba dels Arcs", "Vilalba Sasserra", "Vilaller", "Vilallonga de Ter", "Vilallonga del Camp", "Vilamacolum", "Vilamalla", "Vilamaniscle", "Vilamarín", "Vilamartín de Valdeorras", "Vilamarxant", "Vilamòs", "Vilanant", "Vilanova dAlcolea", "Vilanova de Arousa", "Vilanova de Bellpuig", "Vilanova de la Barca", "Vilanova de lAguda", "Vilanova de Meià", "Vilanova de Prades", "Vilanova de Sau", "Vilanova de Segrià", "Vilanova del Camí", "Vilanova del Vallès", "Vilanova dEscornalbou", "Vilanova i la Geltrú", "Vilaplana", "Vilar de Barrio", "Vilar de Canes", "Vilar de Santos", "Vilardevós", "Vilariño de Conso", "Vilarmaior", "Vilasantar", "Vilassar de Dalt", "Vilassar de Mar", "Vilaür", "Vilavella, la", "Vilaverd", "Vilches", "Vilella Alta, La", "Vilella Baixa, La", "Vileña", "Villa de Don Fadrique, La", "Villa de Mazo", "Villa de Ves", "Villa del Campo", "Villa del Prado", "Villa del Rey", "Villa del Río", "Villabáñez", "Villabaruz de Campos", "Villabasta de Valdavia", "Villablanca", "Villablino", "Villabona", "Villabrágima", "Villabraz", "Villabrázaro", "Villabuena de Álava/Eskuernaga", "Villabuena del Puente", "Villacañas", "Villacarralón", "Villacarriedo", "Villacarrillo", "Villacastín", "Villacid de Campos", "Villacidaler", "Villaciervos", "Villaco", "Villaconancio", "Villaconejos", "Villaconejos de Trabaque", "Villada", "Villadangos del Páramo", "Villademor de la Vega", "Villadepera", "Villadiego", "Villadoz", "Villaeles de Valdavia", "Villaescusa", "Villaescusa", "Villaescusa de Haro", "Villaescusa de Roa", "Villaescusa la Sombría", "Villaespasa", "Villafáfila", "Villafeliche", "Villaferrueña", "Villaflor", "Villaflores", "Villafrades de Campos", "Villafranca", "Villafranca de Córdoba", "Villafranca de Duero", "Villafranca de Ebro", "Villafranca de la Sierra", "Villafranca de los Barros", "Villafranca de los Caballeros", "Villafranca del Bierzo", "Villafranca del Campo", "Villafranca del Cid/Vilafranca", "Villafranca Montes de Oca", "Villafrechós", "Villafruela", "Villafuerte", "Villafufre", "Villagalijo", "Villagarcía de Campos", "Villagarcía de la Torre", "Villagarcía del Llano", "Villagatón", "Villageriz", "Villagómez la Nueva", "Villagonzalo", "Villagonzalo de Tormes", "Villagonzalo Pedernales", "Villahán", "Villaharta", "Villahermosa", "Villahermosa del Campo", "Villahermosa del Río", "Villaherreros", "Villahoz", "Villajoyosa/Vila Joiosa, la", "Villalaco", "Villalán de Campos", "Villalar de los Comuneros", "Villalazán", "Villalba de Duero", "Villalba de Guardo", "Villalba de la Lampreana", "Villalba de la Loma", "Villalba de la Sierra", "Villalba de los Alcores", "Villalba de los Barros", "Villalba de los Llanos", "Villalba de Perejil", "Villalba de Rioja", "Villalba del Alcor", "Villalba del Rey", "Villalbarba", "Villalbilla", "Villalbilla de Burgos", "Villalbilla de Gumiel", "Villalcampo", "Villalcázar de Sirga", "Villalcón", "Villaldemiro", "Villalengua", "Villalgordo del Júcar", "Villalgordo del Marquesado", "Villalmanzo", "Villalobar de Rioja", "Villalobón", "Villalobos", "Villalón de Campos", "Villalonga", "Villalonso", "Villalpando", "Villalpardo", "Villalube", "Villaluenga de la Sagra", "Villaluenga de la Vega", "Villaluenga del Rosario", "Villamalea", "Villamalur", "Villamandos", "Villamanín", "Villamanrique", "Villamanrique de la Condesa", "Villamanrique de Tajo", "Villamanta", "Villamantilla", "Villamañán", "Villamartín", "Villamartín de Campos", "Villamartín de Don Sancho", "Villamayor", "Villamayor de Calatrava", "Villamayor de Campos", "Villamayor de Gállego", "Villamayor de los Montes", "Villamayor de Monjardín", "Villamayor de Santiago", "Villamayor de Treviño", "Villambistia", "Villamediana", "Villamediana de Iregua", "Villamedianilla", "Villamejil", "Villamena", "Villameriel", "Villamesías", "Villamiel", "Villamiel de la Sierra", "Villamiel de Toledo", "Villaminaya", "Villamol", "Villamontán de la Valduerna", "Villamor de los Escuderos", "Villamoratiel de las Matas", "Villamoronta", "Villamuelas", "Villamuera de la Cueza", "Villamuriel de Campos", "Villamuriel de Cerrato", "Villán de Tordesillas", "Villanázar", "Villangómez", "Villanova", "Villanúa", "Villanubla", "Villanueva de Alcardete", "Villanueva de Alcorón", "Villanueva de Algaidas", "Villanueva de Argaño", "Villanueva de Argecilla", "Villanueva de Ávila", "Villanueva de Azoague", "Villanueva de Bogas", "Villanueva de Cameros", "Villanueva de Campeán", "Villanueva de Carazo", "Villanueva de Castellón", "Villanueva de Córdoba", "Villanueva de Duero", "Villanueva de Gállego", "Villanueva de Gómez", "Villanueva de Gormaz", "Villanueva de Guadamejud", "Villanueva de Gumiel", "Villanueva de Huerva", "Villanueva de Jiloca", "Villanueva de la Cañada", "Villanueva de la Concepción", "Villanueva de la Condesa", "Villanueva de la Fuente", "Villanueva de la Jara", "Villanueva de la Reina", "Villanueva de la Serena", "Villanueva de la Sierra", "Villanueva de la Torre", "Villanueva de la Vera", "Villanueva de las Cruces", "Villanueva de las Manzanas", "Villanueva de las Peras", "Villanueva de las Torres", "Villanueva de los Caballeros", "Villanueva de los Castillejos", "Villanueva de los Infantes", "Villanueva de los Infantes", "Villanueva de Oscos", "Villanueva de Perales", "Villanueva de San Carlos", "Villanueva de San Juan", "Villanueva de San Mancio", "Villanueva de Sigena", "Villanueva de Tapia", "Villanueva de Teba", "Villanueva de Viver", "Villanueva del Aceral", "Villanueva del Ariscal", "Villanueva del Arzobispo", "Villanueva del Campillo", "Villanueva del Campo", "Villanueva del Conde", "Villanueva del Duque", "Villanueva del Fresno", "Villanueva del Pardillo", "Villanueva del Rebollar", "Villanueva del Rebollar de la Sierra", "Villanueva del Rey", "Villanueva del Río Segura", "Villanueva del Río y Minas", "Villanueva del Rosario", "Villanueva del Trabuco", "Villanueva Mesía", "Villanuño de Valdavia", "Villaobispo de Otero", "Villaornate y Castro", "Villapalacios", "Villaprovedo", "Villaquejida", "Villaquilambre", "Villaquirán de la Puebla", "Villaquirán de los Infantes", "Villar de Argañán", "Villar de Arnedo, El", "Villar de Cañas", "Villar de Ciervo", "Villar de Corneja", "Villar de Domingo García", "Villar de Fallaves", "Villar de Gallimazo", "Villar de la Encina", "Villar de la Yegua", "Villar de los Navarros", "Villar de Olalla", "Villar de Peralonso", "Villar de Plasencia", "Villar de Rena", "Villar de Samaniego", "Villar de Torre", "Villar del Ala", "Villar del Arzobispo", "Villar del Buey", "Villar del Campo", "Villar del Cobo", "Villar del Humo", "Villar del Infantado", "Villar del Olmo", "Villar del Pedroso", "Villar del Pozo", "Villar del Rey", "Villar del Río", "Villar del Salz", "Villar y Velasco", "Villaralbo", "Villaralto", "Villarcayo de Merindad de Castilla la Vieja", "Villardeciervos", "Villardefrades", "Villardiegua de la Ribera", "Villárdiga", "Villardompardo", "Villardondiego", "Villarejo", "Villarejo de Fuentes", "Villarejo de la Peñuela", "Villarejo de Montalbán", "Villarejo de Órbigo", "Villarejo de Salvanés", "Villarejo del Valle", "Villarejo-Periesteban", "Villares de Jadraque", "Villares de la Reina", "Villares de Órbigo", "Villares de Soria, Los", "Villares de Yeltes", "Villares del Saz", "Villares, Los", "Villargordo del Cabriel", "Villariezo", "Villarino de los Aires", "Villarluengo", "Villarmayor", "Villarmentero de Campos", "Villarmentero de Esgueva", "Villarmuerto", "Villarquemado", "Villarrabé", "Villarramiel", "Villarrasa", "Villarreal de Huerva", "Villarrín de Campos", "Villarrobledo", "Villarrodrigo", "Villarroya", "Villarroya de la Sierra", "Villarroya de los Pinares", "Villarroya del Campo", "Villarrubia de los Ojos", "Villarrubia de Santiago", "Villarrubio", "Villarta", "Villarta de los Montes", "Villarta de San Juan", "Villarta-Quintana", "Villas de la Ventosa", "Villasabariego", "Villasandino", "Villasarracino", "Villasayas", "Villasbuenas", "Villasbuenas de Gata", "Villasdardo", "Villaseca de Arciel", "Villaseca de Henares", "Villaseca de la Sagra", "Villaseca de Uceda", "Villaseco de los Gamitos", "Villaseco de los Reyes", "Villaseco del Pan", "Villaselán", "Villasequilla", "Villasexmir", "Villasila de Valdavia", "Villasrubias", "Villastar", "Villasur de Herreros", "Villatobas", "Villatoro", "Villatorres", "Villatoya", "Villatuelda", "Villatuerta", "Villaturde", "Villaturiel", "Villaumbrales", "Villava/Atarrabia", "Villavaliente", "Villavaquerín", "Villavelayo", "Villavellid", "Villavendimio", "Villaverde de Guadalimar", "Villaverde de Guareña", "Villaverde de Íscar", "Villaverde de Medina", "Villaverde de Montejo", "Villaverde de Rioja", "Villaverde del Monte", "Villaverde del Río", "Villaverde y Pasaconsol", "Villaverde-Mogina", "Villaveza de Valverde", "Villaveza del Agua", "Villavicencio de los Caballeros", "Villaviciosa", "Villaviciosa de Córdoba", "Villaviciosa de Odón", "Villavieja de Yeltes", "Villavieja del Lozoya", "Villaviudas", "Villayerno Morquillas", "Villayón", "Villazala", "Villazanzo de Valderaduey", "Villazopeque", "Villegas", "Villeguillo", "Villel", "Villel de Mesa", "Villena", "Villerías de Campos", "Villodre", "Villodrigo", "Villoldo", "Víllora", "Villores", "Villoria", "Villoruebo", "Villoruela", "Villoslada de Cameros", "Villota del Páramo", "Villovieco", "Vilobí del Penedès", "Vilobí dOnyar", "Vilopriu", "Viloria", "Viloria de Rioja", "Vilosell, El", "Vilueña, La", "Vilvestre", "Vilviestre del Pinar", "Vimbodí i Poblet", "Vimianzo", "Vinaceite", "Vinaixa", "Vinalesa", "Vinaròs", "Vindel", "Vinebre", "Viniegra de Abajo", "Viniegra de Arriba", "Vinuesa", "Vinyols i els Arcs", "Viñas", "Viñegra de Moraña", "Viñuela", "Viñuelas", "Visiedo", "Viso de San Juan, El", "Viso del Alcor, El", "Viso del Marqués", "Viso, El", "Vistabella", "Vistabella del Maestrazgo", "Vita", "Vitigudino", "Vitoria-Gasteiz", "Viveiro", "Vivel del Río Martín", "Viver", "Viver i Serrateix", "Viveros", "Vizcaínos", "Vizmanos", "Víznar", "Voto", "Vozmediano", "Wamba", "Xaló", "Xàtiva", "Xeraco", "Xeresa", "Xermade", "Xerta", "Xinzo de Limia", "Xirivella", "Xove", "Xunqueira de Ambía", "Xunqueira de Espadanedo", "Yaiza", "Yanguas", "Yanguas de Eresma", "Yátova", "Yébenes, Los", "Yebes", "Yebra", "Yebra de Basa", "Yecla", "Yecla de Yeltes", "Yécora/Iekora", "Yélamos de Abajo", "Yélamos de Arriba", "Yeles", "Yelo", "Yémeda", "Yepes", "Yernes y Tameza", "Yesa", "Yesa, La", "Yésero", "Yeste", "Yuncler", "Yunclillos", "Yuncos", "Yunquera", "Yunquera de Henares", "Yunta, La", "Zabalza/Zabaltza", "Zael", "Zafarraya", "Zafra", "Zafra de Záncara", "Zafrilla", "Zagra", "Zahara", "Zahínos", "Zaida, La", "Zaidín", "Zalamea de la Serena", "Zalamea la Real", "Zaldibar", "Zaldibia", "Zalduondo", "Zalla", "Zamarra", "Zamayón", "Zambrana", "Zamora", "Zamudio", "Zaorejas", "Zapardiel de la Cañada", "Zapardiel de la Ribera", "Zaragoza", "Zarapicos", "Zaratamo", "Zaratán", "Zarautz", "Zarra", "Zarratón", "Zarza de Granadilla", "Zarza de Montánchez", "Zarza de Pumareda, La", "Zarza de Tajo", "Zarza la Mayor", "Zarza-Capilla", "Zarza, La", "Zarza, La", "Zarzalejo", "Zarzosa", "Zarzosa de Río Pisuerga", "Zarzuela", "Zarzuela de Jadraque", "Zarzuela del Monte", "Zarzuela del Pinar", "Zas", "Zazuar", "Zeanuri", "Zeberio", "Zegama", "Zerain", "Zestoa", "Zierbena", "Zigoitia", "Ziordia", "Ziortza-Bolibar", "Zizur Mayor/Zizur Nagusia", "Zizurkil", "Zoma, La", "Zorita", "Zorita de la Frontera", "Zorita de los Canes", "Zorita del Maestrazgo", "Zorraquín", "Zotes del Páramo", "Zubia, La", "Zubieta", "Zucaina", "Zuera", "Zufre", "Zugarramurdi", "Zuheros", "Zuia", "Zújar", "Zumaia", "Zumarraga", "Zuñeda", "Zúñiga", "Zurgena"}));
        }
    }//GEN-LAST:event_jComboBox_filtroAZReservaActionPerformed

    private void jButton_solicitarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_solicitarReservaActionPerformed
        // TODO add your handling code here:        
        String fechaSeleccionada;
        String fechaInicioReserva;
        String fechaFinReserva;
        Double precio = 0.00; //Ahora este campo no se utiliza ya que está autocalculado con la tarifa
        int comensales;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        fechaSeleccionada = formatoFecha.format(jCalendar_fechaCalendarioReserva.getDate());
        //Se comprueba que la hora de fin sea superior a la inicial
        if (Integer.parseInt(jComboBox_horaInicioReserva.getSelectedItem().toString()) < Integer.parseInt(jComboBox_horaFinReserva.getSelectedItem().toString())) {
            fechaInicioReserva = fechaSeleccionada + "T" + jComboBox_horaInicioReserva.getSelectedItem().toString() + ":" + jComboBox_minutosInicioReserva.getSelectedItem().toString() + ":00.00000";
            fechaFinReserva = fechaSeleccionada + "T" + jComboBox_horaFinReserva.getSelectedItem().toString() + ":" + jComboBox_minutosFinReserva.getSelectedItem().toString() + ":00.00000";

            try {
                //Se crea el estado de la reserva
                String estadoReserva = "pendiente";
                comensales = Integer.parseInt(jTextField_comensalesReserva.getText());
                altaReserva(estadoReserva, jTextField_usuario.getText(), jComboBox_chefReserva.getSelectedItem().toString(), fechaInicioReserva, fechaFinReserva, precio, comensales);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Por favor, revise los datos introducidos");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Por favor, revise las horas seleccionadas");
        }

    }//GEN-LAST:event_jButton_solicitarReservaActionPerformed

    private void jTextField_precioReservaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_precioReservaKeyTyped
        // TODO add your handling code here:     
    }//GEN-LAST:event_jTextField_precioReservaKeyTyped

    private void jTextField_precioReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_precioReservaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_precioReservaActionPerformed

    private void jToggleButton_eliminarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_eliminarReservaActionPerformed
        // TODO add your handling code here:
        RepaintManager.setCurrentManager(null);
        //Se crea el objeto tabla
        dtm = (DefaultTableModel) jTable_listaReservasCliente.getModel();
        //Se realiza la conexión y se obtiene un array JSon
        JSONArray respuesta = obtenerListaReservasCliente();
        JSONObject reserva;
        //Se especifica los settings de la columna oculta
        //jTable_listaReservasCliente.getColumn(9).setMaxWidth(0);
        //jTable_listaReservasCliente.getColumn(9).setMinWidth(0);
        //jTable_listaReservasCliente.getColumnModel().getColumn(9).setMaxWidth(0);
        //jTable_listaReservasCliente.getColumnModel().getColumn(9).setMinWidth(0);
        //jTable_listaReservasCliente.getColumnModel().getColumn(9).setPreferredWidth(0);
        //Se añaden los campos a la tabla
        for (int i = 0; i < respuesta.length(); i++) {
            reserva = respuesta.getJSONObject(i);
            String diaReserva = reserva.get("incio").toString();
            String horaInicioReserva = reserva.get("incio").toString();
            String horaFinReserva = reserva.get("fin").toString();
            //Se extrae los datos de día y horas de los String pertinentes 
            if (!diaReserva.isEmpty() && !diaReserva.equals("null")) {
                diaReserva = reserva.get("incio").toString().substring(0, 10);
                horaInicioReserva = reserva.get("incio").toString().substring(11, 16);

            }
            if (!horaFinReserva.isEmpty() && !horaFinReserva.equals("null")) {
                horaFinReserva = reserva.get("fin").toString().substring(11, 16);
            }
            //Se añaden los datos a la tabla           
            dtm.addRow(new Object[]{reserva.get("id").toString(),
                reserva.get("estado").toString(),
                reserva.get("chef").toString(),
                diaReserva,
                horaInicioReserva,
                horaFinReserva,
                reserva.get("comensales").toString(),
                reserva.get("precio").toString(),
                reserva.get("instruccions").toString()
            });

        }
        jToggleButton_eliminarReserva.setSelected(false); //Se reinicia el botón  
        switchPanels(jPanel_editarReserva); //Se cambia de panel
    }//GEN-LAST:event_jToggleButton_eliminarReservaActionPerformed

    private void jButton_volverEliminarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_volverEliminarReservaActionPerformed
        // TODO add your handling code here:
        //Se borra la tabla
        dtm.setRowCount(0);
        //Se borran los campos de texto
        jTextField_IDReservaEliminar.setText("");
        jTextField_estadoReservaEliminar.setText("");
        jTextField_chefReservaEliminar.setText("");
        jTextField_diaReservaEliminar.setText("");
        jTextField_inicioReservaEliminar.setText("");
        jTextField_precioReservaEliminar.setText("");
        jTextField_comensalesReservaEliminar.setText("");
        jTextField_finReservaEliminar.setText("");
        jTextArea_infoPagoReserva.setText("");
        //Se vuelve al panel anterior según tipo de usuario
        if (tipoUsuario == 1) {
            switchPanels(jPanel_opcionesAdmin);
        } else if (tipoUsuario == 2) {
            switchPanels(jPanel_opcionesChef);
        } else if (tipoUsuario == 3) {
            switchPanels(jPanel_opcionesCliente);
        }
    }//GEN-LAST:event_jButton_volverEliminarReservaActionPerformed

    private void jTable_listaReservasClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_listaReservasClienteMouseClicked
        // TODO add your handling code here:
        int seleccionar = jTable_listaReservasCliente.rowAtPoint(evt.getPoint());
        jTextField_IDReservaEliminar.setText(String.valueOf(jTable_listaReservasCliente.getValueAt(seleccionar, 0)));
        jTextField_estadoReservaEliminar.setText(String.valueOf(jTable_listaReservasCliente.getValueAt(seleccionar, 1)));
        jTextField_chefReservaEliminar.setText(String.valueOf(jTable_listaReservasCliente.getValueAt(seleccionar, 2)));
        jTextField_diaReservaEliminar.setText(String.valueOf(jTable_listaReservasCliente.getValueAt(seleccionar, 3)));
        jTextField_inicioReservaEliminar.setText(String.valueOf(jTable_listaReservasCliente.getValueAt(seleccionar, 4)));
        jTextField_finReservaEliminar.setText(String.valueOf(jTable_listaReservasCliente.getValueAt(seleccionar, 5)));
        jTextField_comensalesReservaEliminar.setText(String.valueOf(jTable_listaReservasCliente.getValueAt(seleccionar, 6)));
        jTextField_precioReservaEliminar.setText(String.valueOf(jTable_listaReservasCliente.getValueAt(seleccionar, 7)));
        jTextArea_infoPagoReserva.setLineWrap(true);
        jTextArea_infoPagoReserva.setText(String.valueOf(jTable_listaReservasCliente.getValueAt(seleccionar, 8)));
    }//GEN-LAST:event_jTable_listaReservasClienteMouseClicked

    private void jButton_eliminarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_eliminarReservaActionPerformed
        // TODO add your handling code here:
        borrarReserva(jTextField_IDReservaEliminar.getText());
    }//GEN-LAST:event_jButton_eliminarReservaActionPerformed

    private void jTextField_comensalesReservaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_comensalesReservaKeyTyped
        // TODO add your handling code here:
        //Sólo se aceptan números
        char testTeclaPulsada = evt.getKeyChar();
        if (!(Character.isDigit(testTeclaPulsada))) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField_comensalesReservaKeyTyped

    private void jTextField_inicioReservaEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_inicioReservaEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_inicioReservaEliminarActionPerformed

    private void jTextField_editarCodigoPostalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_editarCodigoPostalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_editarCodigoPostalActionPerformed

    private void jTextField_editarEdadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_editarEdadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_editarEdadActionPerformed

    private void jTextField_comensalesReservaEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_comensalesReservaEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_comensalesReservaEliminarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        //Se borra la tabla
        dtm.setRowCount(0);
        //Se borran los campos de texto
        jTextField_IDReservaChef.setText("");
        jTextField_estadoReservaChef.setText("");
        jTextField_clienteReservaChef.setText("");
        jTextField_fechaReservaChef.setText("");
        jTextField_inicioReservaChef.setText("");
        jTextField_finReservaChef.setText("");
        jTextField_comensalesReservaChef.setText("");
        jTextField_precioReservaChef.setText("");
        //Se vuelve al panel anterior según tipo de usuario
        if (tipoUsuario == 1) {
            switchPanels(jPanel_opcionesAdmin);
        } else if (tipoUsuario == 2) {
            switchPanels(jPanel_opcionesChef);
        } else if (tipoUsuario == 3) {
            switchPanels(jPanel_opcionesCliente);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField_clienteReservaChefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_clienteReservaChefActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_clienteReservaChefActionPerformed

    private void jToggleButton_revisarReservasChefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_revisarReservasChefActionPerformed
        // TODO add your handling code here:
        RepaintManager.setCurrentManager(null);
        //Se crea el objeto tabla
        dtm = (DefaultTableModel) jTable_listaReservasChef.getModel();
        //Se realiza la conexión y se obtiene un array JSon
        JSONArray respuesta = obtenerListaReservasChef();
        JSONObject reserva;
        //Se añaden los campos a la tabla
        for (int i = 0; i < respuesta.length(); i++) {
            reserva = respuesta.getJSONObject(i);
            String diaReserva = reserva.get("incio").toString();
            String horaInicioReserva = reserva.get("incio").toString();
            String horaFinReserva = reserva.get("fin").toString();
            //Se extrae los datos de día y horas de los String pertinentes 
            if (!diaReserva.isEmpty() && !diaReserva.equals("null")) {
                diaReserva = reserva.get("incio").toString().substring(0, 9);
                horaInicioReserva = reserva.get("incio").toString().substring(11, 16);

            }
            if (!horaFinReserva.isEmpty() && !horaFinReserva.equals("null")) {
                horaFinReserva = reserva.get("fin").toString().substring(11, 16);
            }
            //Se añaden los datos a la tabla           
            dtm.addRow(new Object[]{reserva.get("id").toString(),
                reserva.get("estado").toString(),
                reserva.get("cliente").toString(),
                diaReserva,
                horaInicioReserva,
                horaFinReserva,
                reserva.get("comensales").toString(),
                reserva.get("precio").toString()
            });

        }
        jToggleButton_revisarReservasChef.setSelected(false); //Se reinicia el botón  
        switchPanels(jPanel_aceptarReservaChef); //Se cambia de panel
    }//GEN-LAST:event_jToggleButton_revisarReservasChefActionPerformed

    private void jTable_listaReservasChefMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_listaReservasChefMouseClicked
        // TODO add your handling code here:
        // TODO add your handling code here:
        int seleccionar = jTable_listaReservasChef.rowAtPoint(evt.getPoint());
        jTextField_IDReservaChef.setText(String.valueOf(jTable_listaReservasChef.getValueAt(seleccionar, 0)));
        jTextField_estadoReservaChef.setText(String.valueOf(jTable_listaReservasChef.getValueAt(seleccionar, 1)));
        jTextField_clienteReservaChef.setText(String.valueOf(jTable_listaReservasChef.getValueAt(seleccionar, 2)));
        jTextField_fechaReservaChef.setText(String.valueOf(jTable_listaReservasChef.getValueAt(seleccionar, 3)));
        jTextField_inicioReservaChef.setText(String.valueOf(jTable_listaReservasChef.getValueAt(seleccionar, 4)));
        jTextField_finReservaChef.setText(String.valueOf(jTable_listaReservasChef.getValueAt(seleccionar, 5)));
        jTextField_comensalesReservaChef.setText(String.valueOf(jTable_listaReservasChef.getValueAt(seleccionar, 6)));
        jTextField_precioReservaChef.setText(String.valueOf(jTable_listaReservasChef.getValueAt(seleccionar, 7)));
    }//GEN-LAST:event_jTable_listaReservasChefMouseClicked

    private void jButton_aceptarReservaChefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_aceptarReservaChefActionPerformed
        // TODO add your handling code here:
        String estado = "confirmado";
        cambiarEstadoReserva(jTextField_IDReservaChef.getText(), estado);
    }//GEN-LAST:event_jButton_aceptarReservaChefActionPerformed

    private void jButton_confirmarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_confirmarPagoActionPerformed
        // TODO add your handling code here:
        String estado = "conciliado";
        cambiarEstadoReserva(jTextField_IDReservaChef.getText(), estado);
    }//GEN-LAST:event_jButton_confirmarPagoActionPerformed

    private void jButton_rechazarReservaChefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_rechazarReservaChefActionPerformed
        // TODO add your handling code here:
        String estado = "rechazado";
        cambiarEstadoReserva(jTextField_IDReservaChef.getText(), estado);
    }//GEN-LAST:event_jButton_rechazarReservaChefActionPerformed

    private void jButton_pagoReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_pagoReservaActionPerformed
        // TODO add your handling code here:
        String estado = "pagado";
        cambiarEstadoReserva(jTextField_IDReservaEliminar.getText(), estado);
    }//GEN-LAST:event_jButton_pagoReservaActionPerformed

    private void jButton_volverTarifaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_volverTarifaActionPerformed
        // TODO add your handling code here:
        if (tipoUsuario == 1) {
            switchPanels(jPanel_opcionesAdmin);
        } else if (tipoUsuario == 2) {
            switchPanels(jPanel_opcionesChef);
        } else if (tipoUsuario == 3) {
            switchPanels(jPanel_opcionesCliente);
        }
    }//GEN-LAST:event_jButton_volverTarifaActionPerformed

    private void jTextField_indicarTarifaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_indicarTarifaKeyTyped
        // TODO add your handling code here:
        char testTeclaPulsada = evt.getKeyChar();
        if (!(Character.isDigit(testTeclaPulsada) || testTeclaPulsada == KeyEvent.VK_PERIOD)) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField_indicarTarifaKeyTyped

    private void jToggleButton_indicarTarifaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton_indicarTarifaActionPerformed
        // TODO add your handling code here:
        String tarifa = obtenerTarifa(jTextField_usuario.getText());
        //Se comprueba que no se recepcione NULL
        if (tarifa != null) {
            jTextField_indicarTarifa.setText(tarifa);
            jButton_modificarTarifa.setText("Modificar Tarifa");
        } else {
            jButton_modificarTarifa.setText("Alta Tarifa");
        }
        jToggleButton_indicarTarifa.setSelected(false); //Se reinicia el botón  
        switchPanels(jPanel_modificarTarifa); //Se cambia de panel
    }//GEN-LAST:event_jToggleButton_indicarTarifaActionPerformed

    private void jButton_volverModificarContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_volverModificarContraseñaActionPerformed
        // TODO add your handling code here:
        switchPanels(jPanel_editarCuenta);
    }//GEN-LAST:event_jButton_volverModificarContraseñaActionPerformed

    private void jButton_modificarContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_modificarContraseñaActionPerformed
        // TODO add your handling code here:
        modificarContraseña(jTextField_nuevaContraseña.getText());
    }//GEN-LAST:event_jButton_modificarContraseñaActionPerformed

    private void jButton_cambiarContraseñaUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_cambiarContraseñaUsuarioActionPerformed
        // TODO add your handling code here:
        switchPanels(jPanel_cambiarContraseña);
    }//GEN-LAST:event_jButton_cambiarContraseñaUsuarioActionPerformed

    private void jButton_calcularPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_calcularPrecioActionPerformed
        // TODO add your handling code here:
        Double precio;
        String tarifa;
        Double horasServicio = Double.parseDouble(jComboBox_horaFinReserva.getSelectedItem().toString())
                - Double.parseDouble(jComboBox_horaInicioReserva.getSelectedItem().toString());
        //Se comprueba que el usuario haya puesto todos los datos
        if (!jComboBox_horaInicioReserva.getSelectedItem().toString().isEmpty()
                && !jComboBox_horaFinReserva.getSelectedItem().toString().isEmpty()
                && !jCalendar_fechaCalendarioReserva.getDate().toString().isEmpty()
                && !jTextField_comensalesReserva.getText().isEmpty()
                && !jComboBox_chefReserva.getSelectedItem().toString().isEmpty()) {
            //Se obtiene la tarifa del chef 
            tarifa = obtenerTarifa(jComboBox_chefReserva.getSelectedItem().toString());
            //Se comprueba que no se recepcione NULL
            if (tarifa != null) {
                //Se realiza la multiplicación de comensales x tarifa y se muestra el precio de la reserva
                precio = Double.parseDouble(jTextField_comensalesReserva.getText())
                        * Double.parseDouble(tarifa) * horasServicio;
                jTextField_precioReserva.setText(precio.toString());
            }
        }
    }//GEN-LAST:event_jButton_calcularPrecioActionPerformed

    private void jButton_modificarTarifaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_modificarTarifaActionPerformed
        // TODO add your handling code here:        
        Double tarifa = null;
        //Se comprueba que el usuario chef haya introducido la tarifa
        if (jButton_modificarTarifa.getText().equals("Alta Tarifa")) {
            //Se comprueba que haya introducido una tarifa válida
            try {
                String usernameOrEmail = jTextField_usuario.getText();
                Double precioHora = Double.parseDouble(jTextField_indicarTarifa.getText());
                /*Aunque se inicia la preparación de la configuración de menús, aún no está habilitado
                para su aplicación. Se realizaría en futuros desarrollos y ahora se aplica un guión*/
                String entrante = "";
                String primero = "";
                String segundo = "";
                String postre = "";
                String cafes = "";
                altaTarifa(usernameOrEmail, precioHora, entrante, primero, segundo, postre, cafes);

            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "Error en la tarifa introducida");
            }

        } else {
            try {
                String usernameOrEmail = jTextField_usuario.getText();
                Double precioHora = Double.parseDouble(jTextField_indicarTarifa.getText());
                String entrante = "";
                String primero = "";
                String segundo = "";
                String postre = "";
                String cafes = "";
                modificarTarifa(idTarifa, usernameOrEmail, precioHora, entrante, primero, segundo, postre, cafes);

            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "Error en la tarifa introducida");
            }
        }
    }//GEN-LAST:event_jButton_modificarTarifaActionPerformed

    private void jTextField_indicarTarifaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_indicarTarifaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_indicarTarifaActionPerformed

    private void jComboBox_poblacionReservaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox_poblacionReservaItemStateChanged
        // TODO add your handling code here:        
    }//GEN-LAST:event_jComboBox_poblacionReservaItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });

    }

    public void switchPanels(JPanel panel) {
        jLayeredPane_contenedor.removeAll();
        jLayeredPane_contenedor.add(panel);
        jLayeredPane_contenedor.repaint();
        jLayeredPane_contenedor.revalidate();
    }

    /* Deprecated. Método de aplicación en anteriores TEA
    private boolean comprobarLogin(String userName, char[] password) {
        userName = "Alberto";
        password = new char[]{'1', '2', '3', '4'};
        String correctPassword = String.valueOf(password);
        if (userName.equalsIgnoreCase(jTextField_usuario.getText()) && correctPassword.equals(String.valueOf(jPasswordField_password.getPassword()))) {
            return true;
        } else {
            return false;
        }
    }
     */
    private JSONArray obtenerListaUsers() {
        // Se realiza una solicitud GET para recibir los datos del usuario
        String linea;
        JSONArray jsonArray = null;
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/users/get/users"; //Dirección en Cloud
            //String urlString = "https://localhost:8080/api/users/get/users"; //Dirección en localhost
            //urlString = urlString + "?usernameOrEmail=" + jTextField_usuario.getText()
            //      + "&password=password"; //+jPasswordField_password.getPassword().toString();

            String apiUrl = urlString; //api/http link                                    
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            if (connection.getResponseCode() != 200) {
                JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());

            } else {

                InputStream response = connection.getInputStream();
                //Leyendo la respuesta de la API
                StringBuilder sb2 = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(response));
                while ((linea = in.readLine()) != null) {
                    sb2.append(linea).append("\n");
                }
                in.close();
                //Pasando la respuesta a objeto JSON array para poder usarla
                jsonArray = new JSONArray(sb2.toString());
                connection.disconnect();
            }

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        return jsonArray;

    }

    public void obtenerDatosUsuario() {
        // Se realiza una solicitud GET para recibir los datos del usuario
        String linea;
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/users/get/user";
            //String urlString = "https://localhost:8080/api/users/get/user";
            urlString = urlString + "?usernameOrEmail=" + jTextField_usuario.getText();
            // + "&password=password"; //+jPasswordField_password.getPassword().toString();

            String apiUrl = urlString; //api/http link                                    
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            if (connection.getResponseCode() != 200) {
                JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());

            } else {

                //Leyendo la respuesta de la API
                StringBuilder sb2 = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((linea = in.readLine()) != null) {
                    sb2.append(linea).append("\n");
                }
                in.close();
                //Pasando la respuesta a objeto JSON para poder usarla
                JSONObject jsonObject = new JSONObject(sb2.toString());
                //Se aplican los valores recibidos a los campos de lpanel Usuarios
                if (jsonObject.getString("nombre") != null) {
                    jLabel_nombreUsuario.setText(jsonObject.getString("nombre"));
                    jTextField_editarNombre.setText(jsonObject.getString("nombre"));
                }
                if (jsonObject.get("apellidos") != null) {
                    jLabel_apellidosUsuario.setText(String.valueOf(jsonObject.get("apellidos")));
                    jTextField_editarApellidos.setText(String.valueOf(jsonObject.get("apellidos")));
                }
                if (jsonObject.get("direccion") != null) {
                    jLabel_direccionUsuario.setText(String.valueOf(jsonObject.get("direccion")));
                    jTextField_editarDireccion.setText(String.valueOf(jsonObject.get("direccion")));
                }
                if (jsonObject.get("codigoPostal") != null) {
                    jLabel_codigoPostalUsuario.setText(String.valueOf(jsonObject.get("codigoPostal")));
                    jTextField_editarCodigoPostal.setText(String.valueOf(jsonObject.get("codigoPostal")));
                }
                if (jsonObject.get("poblacion") != null) {
                    jLabel_poblacionUsuario.setText(String.valueOf(jsonObject.get("poblacion")));
                    jTextField_editarPoblacion.setText(String.valueOf(jsonObject.get("poblacion")));
                }
                if (jsonObject.get("nacionalidad") != null) {
                    jLabel_nacionalidadUsuario.setText(String.valueOf(jsonObject.get("nacionalidad")));
                    jTextField_editarNacionalidad.setText(String.valueOf(jsonObject.get("nacionalidad")));
                }
                if (jsonObject.get("edad") != null) {
                    jLabel_edadUsuario.setText(String.valueOf(jsonObject.get("edad")));
                    jTextField_editarEdad.setText(String.valueOf(jsonObject.get("edad")));
                }
                if (jsonObject.get("telefono") != null) {
                    jLabel_telefonoUsuario.setText(String.valueOf(jsonObject.get("telefono")));
                    jTextField_editarTelefono.setText(String.valueOf(jsonObject.get("telefono")));
                }
                if (jsonObject.get("email") != null) {
                    jLabel_emailUsuario.setText(jsonObject.getString("email"));
                    jTextField_editarEmail.setText(jsonObject.getString("email"));
                }
                if (jsonObject.get("username") != null) {
                    jLabel_usernameUsuario.setText(jsonObject.getString("username"));
                    jTextField_editarUsername.setText(jsonObject.getString("username"));
                }
                if (jsonObject.get("iban") != null) {
                    jLabel_IBANUsuario.setText(String.valueOf(jsonObject.get("iban")));
                    jTextField_editarIBAN.setText(String.valueOf(jsonObject.get("iban")));
                }
                if (jsonObject.get("id") != null) {
                    ID = jsonObject.get("id").toString();
                }

                //Asignamos el tipo al ComboBox y lo mostramos en el panel de usuario y de editar
                if (jsonObject.get("roles") != null) {
                    JSONArray jsonArray = new JSONArray(jsonObject.get("roles").toString());
                    JSONObject jsonObjectRoles = jsonArray.getJSONObject(0);
                    if (jsonObjectRoles.getString("role").equals("ROLE_ADMIN")) {
                        jComboBox_tipoUsuario.setSelectedItem("Administrador");
                        jComboBox_editarTipoUsuario.setSelectedItem("Administrador");
                        jLabel_tipoUsuarioUsuario.setText(jComboBox_tipoUsuario.getSelectedItem().toString());
                        tipoUsuario = 1;
                        switchPanels(jPanel_opcionesAdmin);
                    }
                    if (jsonObjectRoles.getString("role").equals("ROLE_CHEF")) {
                        jComboBox_tipoUsuario.setSelectedItem("Chef");
                        jComboBox_editarTipoUsuario.setSelectedItem("Chef");
                        jLabel_tipoUsuarioUsuario.setText(jComboBox_tipoUsuario.getSelectedItem().toString());
                        tipoUsuario = 2;
                        switchPanels(jPanel_opcionesChef);
                    }
                    if (jsonObjectRoles.getString("role").equals("ROLE_CLIENT")) {
                        jComboBox_tipoUsuario.setSelectedItem("Cliente");
                        jComboBox_editarTipoUsuario.setSelectedItem("Cliente");
                        jLabel_tipoUsuarioUsuario.setText(jComboBox_tipoUsuario.getSelectedItem().toString());
                        tipoUsuario = 3;
                        switchPanels(jPanel_opcionesCliente);

                    }

                }
            }
            connection.disconnect();

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }

    private JSONArray obtenerChefsEstado() {
        // Se realiza una solicitud GET para recibir los datos del usuario
        String linea;
        JSONArray jsonArray = null;
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/chef/disponibilidad/get/filtrado"; //Dirección en Cloud
            //String urlString = "https://localhost:8080/api/chef/disponibilidad/get/filtrado"; //Dirección en localhost
            urlString = urlString + "?estado=todos"
                    + "&poblacion=todos"; //+jPasswordField_password.getPassword().toString();

            String apiUrl = urlString; //api/http link                                    
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            if (connection.getResponseCode() != 200) {
                JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());

            } else {

                //Leyendo la respuesta de la API
                StringBuilder sb2 = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((linea = in.readLine()) != null) {
                    sb2.append(linea).append("\n");
                }
                in.close();
                //Pasando la respuesta a objeto JSON para poder usarla
                jsonArray = new JSONArray(sb2.toString());

            }
            connection.disconnect();

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        return jsonArray;
    }

    public void altaReserva(String estado, String userCliente, String userChef, String fechaInicio, String fechaFin, Double precio, int comensales) {
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/client/reserva/post"; //Dirección en Cloud
            //String urlString = "https://localhost:8080/api/client/reserva/post"; //Dirección en localhost
            String apiUrl = urlString;
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            String payload = "{\"estado\": \"" + estado + "\","
                    + " \"cliente\": \"" + userCliente + "\","
                    + " \"chef\": \"" + userChef + "\","
                    + " \"incio\": \"" + fechaInicio + "\","
                    + " \"fin\": \"" + fechaFin + "\","
                    + " \"precio\": \"" + precio + "\","
                    + " \"comensales\": \"" + comensales + "\"}";
            byte[] out = payload.getBytes(StandardCharsets.UTF_8);
            OutputStream stream = connection.getOutputStream();
            stream.write(out);

            if (connection.getResponseCode() != 200) {
                if (connection.getResponseCode() == 500) {
                    JOptionPane.showMessageDialog(null, "Usuario no encontrado");
                } else {
                    JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());
                }

            } else {
                JOptionPane.showMessageDialog(null, "Alta de nueva reserva realizada");

            }
            connection.disconnect();

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }

    private JSONArray obtenerListaReservasCliente() {
        // Se realiza una solicitud GET para recibir los datos del usuario
        String linea;
        JSONArray jsonArray = null;
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/client/reserva/get/client/paginable"; //Dirección en Cloud
            //String urlString = "https://localhost:8080/api/client/reserva/get/client/paginable"; //Dirección en localhost
            urlString = urlString + "?usernameOrEmail=" + jTextField_usuario.getText()
                    + "&pageIndex=0"
                    + "&pageSize=200";

            String apiUrl = urlString; //api/http link                                    
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            if (connection.getResponseCode() != 200) {
                JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());

            } else {

                //Leyendo la respuesta de la API
                StringBuilder sb2 = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((linea = in.readLine()) != null) {
                    sb2.append(linea).append("\n");
                }
                in.close();
                //Pasando la respuesta a objeto JSON para poder usarla
                jsonArray = new JSONArray(sb2.toString());

            }
            connection.disconnect();

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        return jsonArray;
    }

    public JSONArray obtenerListaReservasChef() {
        // Se realiza una solicitud GET para recibir los datos del usuario
        String linea;
        JSONArray jsonArray = null;
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/client/reserva/get/chef/paginable"; //Dirección en Cloud
            //String urlString = "https://localhost:8080/api/client/reserva/get/chef/paginable"; //Dirección en localhost
            urlString = urlString + "?usernameOrEmail=" + jTextField_usuario.getText()
                    + "&pageIndex=0"
                    + "&pageSize=200";

            String apiUrl = urlString; //api/http link                                    
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            if (connection.getResponseCode() != 200) {
                JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());

            } else {

                //Leyendo la respuesta de la API
                StringBuilder sb2 = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((linea = in.readLine()) != null) {
                    sb2.append(linea).append("\n");
                }
                in.close();
                //Pasando la respuesta a objeto JSON para poder usarla
                jsonArray = new JSONArray(sb2.toString());

            }
            connection.disconnect();

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
        return jsonArray;
    }

    public void borrarReserva(String ID) {
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/client/reserva/delete"; //Dirección en Cloud
            //String urlString = "https://localhost:8080/api/client/reserva/delete"; //Dirección en localhost
            urlString = urlString + "?id=" + ID;
            //      + "&password=password"; //+jPasswordField_password.getPassword().toString();
            String apiUrl = urlString; //api/http link                                    
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            if (connection.getResponseCode() != 200) {
                if (connection.getResponseCode() == 500) {
                    JOptionPane.showMessageDialog(null, "Reserva no encontrada");
                } else {
                    JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());
                }

            } else {
                //Leyendo la respuesta de la API
                StringBuilder sb = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linea;
                while ((linea = in.readLine()) != null) {
                    sb.append(linea).append("\n");
                }
                in.close();
                JOptionPane.showMessageDialog(null, sb.toString());

            }
            connection.disconnect();

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }

    public void cambiarEstadoReserva(String ID, String estado) {
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/client/reserva/update/estado"; //Dirección en Cloud
            //String urlString = "https://localhost:8080/api/client/reserva/update/estado"; //Dirección en localhost
            urlString = urlString + "?id=" + ID
                    + "&estado=" + estado;
            String apiUrl = urlString; //api/http link                                    
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            if (connection.getResponseCode() != 200) {
                if (connection.getResponseCode() == 500) {
                    JOptionPane.showMessageDialog(null, "Reserva no encontrada");
                } else {
                    JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());
                }

            } else {
                //Leyendo la respuesta de la API
                StringBuilder sb = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linea;
                while ((linea = in.readLine()) != null) {
                    sb.append(linea).append("\n");
                }
                in.close();
                JOptionPane.showMessageDialog(null, sb.toString());

            }
            connection.disconnect();

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }

    public String obtenerTarifa(String username) {
        JSONObject jsonObject = null;
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/chef/tarifa/get/user"; //Dirección en Cloud
            //String urlString = "https://localhost:8080/api/chef/tarifa/get/user"; //Dirección en localhost
            urlString = urlString + "?usernameOrEmail=" + username;

            String apiUrl = urlString; //api/http link                                    
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            if (connection.getResponseCode() != 200) {
                if (connection.getResponseCode() == 500) {
                    JOptionPane.showMessageDialog(null, "Tarifa no encontrada");
                }

            } else {
                //Leyendo la respuesta de la API
                StringBuilder sb = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linea;
                while ((linea = in.readLine()) != null) {
                    sb.append(linea).append("\n");
                }
                jsonObject = new JSONObject(sb.toString());
                in.close();
                idTarifa = jsonObject.get("id").toString();
                return jsonObject.get("precioHora").toString();

            }
            connection.disconnect();
            return null;

        } catch (MalformedURLException ex) {
            return null;

        } catch (IOException ex) {
            return null;
        }

    }

    public void modificarContraseña(String nuevaContraseña) {
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/users/password/user"; //Dirección en Cloud
            //String urlString = "https://localhost:8080/api/users/password/user"; //Dirección en localhost
            urlString = urlString + "?usernameOrEmail=" + jTextField_usuario.getText()
                    + "&newPassword=" + nuevaContraseña;
            String apiUrl = urlString; //api/http link                                    
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            if (connection.getResponseCode() != 200) {
                if (connection.getResponseCode() == 500) {
                    JOptionPane.showMessageDialog(null, "Usuario no encontrado");
                } else {
                    JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());
                }

            } else {
                //Leyendo la respuesta de la API
                StringBuilder sb = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linea;
                while ((linea = in.readLine()) != null) {
                    sb.append(linea).append("\n");
                }
                in.close();
                if (!sb.toString().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Su contraseña ha sido actualizada");
                }

            }
            connection.disconnect();

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }

    public void altaTarifa(String usernameOrEmail, Double precioHora, String entrante, String primero, String segundo, String postre, String cafes) {
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/chef/tarifa/post"; //Dirección en Cloud
            //String urlString = "https://localhost:8080/api/chef/tarifa/post"; //Dirección en localhost
            String apiUrl = urlString;
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            String payload = "{\"usernameOrEmail\": \"" + usernameOrEmail + "\","
                    + " \"precioHora\": \"" + precioHora + "\","
                    + " \"entrante\": \"" + entrante + "\","
                    + " \"primero\": \"" + primero + "\","
                    + " \"segundo\": \"" + segundo + "\","
                    + " \"postre\": \"" + postre + "\","
                    + " \"cafes\": \"" + cafes + "\"}";
            byte[] out = payload.getBytes(StandardCharsets.UTF_8);
            OutputStream stream = connection.getOutputStream();
            stream.write(out);

            if (connection.getResponseCode() != 200) {
                if (connection.getResponseCode() == 500) {
                    JOptionPane.showMessageDialog(null, "Tarifa no guardada. Compruebe los datos introducidos");
                } else {
                    JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());
                }

            } else {
                JOptionPane.showMessageDialog(null, "Alta de nueva tarifa realizada");
            }
            connection.disconnect();

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }

    public void modificarTarifa(String id, String usernameOrEmail, Double precioHora, String entrante, String primero, String segundo, String postre, String cafes) {
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/chef/tarifa/update"; //Dirección en Cloud
            //String urlString = "https://localhost:8080/api/chef/tarifa/update"; //Dirección en localhost
            String apiUrl = urlString;
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            String payload = "{\"id\": \"" + id + "\","
                    + " \"usernameOrEmail\": \"" + usernameOrEmail + "\","
                    + " \"precioHora\": \"" + precioHora + "\","
                    + " \"entrante\": \"" + entrante + "\","
                    + " \"primero\": \"" + primero + "\","
                    + " \"segundo\": \"" + segundo + "\","
                    + " \"postre\": \"" + postre + "\","
                    + " \"cafes\": \"" + cafes + "\"}";
            byte[] out = payload.getBytes(StandardCharsets.UTF_8);
            OutputStream stream = connection.getOutputStream();
            stream.write(out);

            if (connection.getResponseCode() != 200) {
                if (connection.getResponseCode() == 500) {
                    JOptionPane.showMessageDialog(null, "Tarifa no guardada. Compruebe los datos introducidos");
                } else {
                    JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());
                }

            } else {
                JOptionPane.showMessageDialog(null, "Alta de nueva tarifa realizada");
            }
            connection.disconnect();

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }

    public void borrarTarifa(String ID) {
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/chef/tarifa/delete"; //Dirección en Cloud
            //String urlString = "https://localhost:8080/api/chef/tarifa/delete"; //Dirección en localhost
            urlString = urlString + "?id=" + ID;
            //      + "&password=password"; //+jPasswordField_password.getPassword().toString();
            String apiUrl = urlString; //api/http link                                    
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            if (connection.getResponseCode() != 200) {
                if (connection.getResponseCode() == 500) {
                    JOptionPane.showMessageDialog(null, "Tarifa no encontrada");
                } else {
                    JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());
                }

            } else {
                //Leyendo la respuesta de la API
                StringBuilder sb = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linea;
                while ((linea = in.readLine()) != null) {
                    sb.append(linea).append("\n");
                }
                in.close();
                JOptionPane.showMessageDialog(null, sb.toString());

            }
            connection.disconnect();

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }

    public void altaDisponibilidad(String estado) {
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/chef/disponibilidad/post"; //Dirección en Cloud
            //String urlString = "https://localhost:8080/api/chef/disponibilidad/post"; //Dirección en localhost
            //urlString = urlString + "?usernameOrEmail=" + jTextField_usuarioBorrar.getText();
            //      + "&password=password"; //+jPasswordField_password.getPassword().toString();
            String apiUrl = urlString; //api/http link
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            String payload = "{\"usernameOrEmail\": \"" + jTextField_usuario.getText() + "\","
                    + " \"estado\": \"" + estado + "\","
                    + " \"poblacion\": \"" + jComboBox_poblacion.getSelectedItem().toString() + "\"}";
            byte[] out = payload.getBytes(StandardCharsets.UTF_8);
            OutputStream stream = connection.getOutputStream();
            stream.write(out);

            if (connection.getResponseCode() != 200) {
                if (connection.getResponseCode() == 500) {
                    JOptionPane.showMessageDialog(null, "Disponibilidad no encontrada");
                } else {
                    JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());
                }

            } else {
                //Leyendo la respuesta de la API
                StringBuilder sb = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linea;
                while ((linea = in.readLine()) != null) {
                    sb.append(linea).append("\n");
                }
                in.close();
                JOptionPane.showMessageDialog(null, sb.toString());

            }
            connection.disconnect();

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }

    public String obtenerDisponibilidadPoblacion(String username, String poblacion) {
        String disponibilidadPoblacion = null;
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/chef/disponibilidad/get/username"; //Dirección en Cloud
            //String urlString = "https://localhost:8080/api/chef/disponibilidad/get/username"; //Dirección en localhost
            urlString = urlString + "?usernameOrEmail=" + username;

            String apiUrl = urlString; //api/http link                                    
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            if (connection.getResponseCode() != 200) {
                if (connection.getResponseCode() == 500) {
                    JOptionPane.showMessageDialog(null, "Disponibilidad no encontrada");
                }

            } else {
                //Leyendo la respuesta de la API
                StringBuilder sb = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linea;
                while ((linea = in.readLine()) != null) {
                    sb.append(linea).append("\n");
                }
                JSONArray jsonArray = new JSONArray(sb.toString());
                JSONObject jsonObject;
                //Se añaden los campos a la tabla
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.get("poblacion").toString().equals(poblacion)) {
                        disponibilidadPoblacion = jsonObject.get("estado").toString();
                        idDisponibilidad = jsonObject.get("id").toString();
                    }

                }

                in.close();
                return disponibilidadPoblacion;

            }
            connection.disconnect();
            return null;

        } catch (MalformedURLException ex) {
            return null;

        } catch (IOException ex) {
            return null;
        }

    }

    public void modificarDisponibilidad(String estado, String poblacion) {
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/chef/disponibilidad/update/estado"; //Dirección en Cloud
            //String urlString = "https://localhost:8080/api/chef/disponibilidad/update/estado"; //Dirección en localhost
            urlString = urlString + "?usernameOrEmail=" + jTextField_usuario.getText()
                    + "&poblacion=" + poblacion; //+jPasswordField_password.getPassword().toString();
            String apiUrl = urlString; //api/http link
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);
            String payload = "{\"estado\": \"" + estado + "\"}";
            byte[] out = payload.getBytes(StandardCharsets.UTF_8);
            OutputStream stream = connection.getOutputStream();
            stream.write(out);

            if (connection.getResponseCode() != 200) {
                if (connection.getResponseCode() == 500) {
                    JOptionPane.showMessageDialog(null, "Disponibilidad no encontrada");
                } else {
                    JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());
                }

            } else {
                //Leyendo la respuesta de la API
                StringBuilder sb = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linea;
                while ((linea = in.readLine()) != null) {
                    sb.append(linea).append("\n");
                }
                in.close();
                JOptionPane.showMessageDialog(null, "Cambio en la disponibilidad de " + poblacion + " realizado");

            }
            connection.disconnect();

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }

    public void borrarDisponibilidad(String ID) {
        try {
            String urlString = "https://render-chef-deluxe.onrender.com/api/chef/disponibilidad/delete"; //Dirección en Cloud
            //String urlString = "https://localhost:8080/api/chef/disponibilidad/delete"; //Dirección en localhost
            urlString = urlString + "?id=" + ID;
            //      + "&password=password"; //+jPasswordField_password.getPassword().toString();
            String apiUrl = urlString; //api/http link                                    
            URL url = new URL(apiUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + token);

            if (connection.getResponseCode() != 200) {
                if (connection.getResponseCode() == 500) {
                    JOptionPane.showMessageDialog(null, "Disponibilidad no encontrada");
                } else {
                    JOptionPane.showMessageDialog(null, "Fallo de conexión con el servidor. Error " + connection.getResponseCode());
                }

            } else {
                //Leyendo la respuesta de la API
                StringBuilder sb = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linea;
                while ((linea = in.readLine()) != null) {
                    sb.append(linea).append("\n");
                }
                in.close();
                JOptionPane.showMessageDialog(null, sb.toString());

            }
            connection.disconnect();

        } catch (MalformedURLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }

    public void centrarAplicacion() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    }

    public void setImageLabel(JLabel labelName, String root) {
        ImageIcon imagen = new ImageIcon(root);
        Icon logo = new ImageIcon(imagen.getImage().getScaledInstance(labelName.getWidth(), labelName.getHeight(), Image.SCALE_DEFAULT));
        labelName.setIcon(logo);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton_aceptarReservaChef;
    private javax.swing.JButton jButton_aplicarCambiosDisponibilidad;
    private javax.swing.JButton jButton_calcularPrecio;
    private javax.swing.JButton jButton_cambiarContraseñaUsuario;
    private javax.swing.JButton jButton_confirmarPago;
    private javax.swing.JButton jButton_crearCuenta;
    private javax.swing.JButton jButton_editarCuenta;
    private javax.swing.JButton jButton_eliminarBorrarUsuario;
    private javax.swing.JButton jButton_eliminarEliminarCuenta;
    private javax.swing.JButton jButton_eliminarReserva;
    private javax.swing.JButton jButton_exit;
    private javax.swing.JButton jButton_exit1;
    private javax.swing.JButton jButton_login;
    private javax.swing.JButton jButton_modificarContraseña;
    private javax.swing.JButton jButton_modificarTarifa;
    private javax.swing.JButton jButton_nuevaCuenta;
    private javax.swing.JButton jButton_pagoReserva;
    private javax.swing.JButton jButton_rechazarReservaChef;
    private javax.swing.JButton jButton_solicitarReserva;
    private javax.swing.JButton jButton_usuario;
    private javax.swing.JButton jButton_volverBorrarUsuario;
    private javax.swing.JButton jButton_volverEliminarCuenta;
    private javax.swing.JButton jButton_volverEliminarReserva;
    private javax.swing.JButton jButton_volverListadoChefsDisponibles;
    private javax.swing.JButton jButton_volverListarUsuarios;
    private javax.swing.JButton jButton_volverModificarContraseña;
    private javax.swing.JButton jButton_volverTarifa;
    private javax.swing.JButton jButton_voverReserva;
    private com.toedter.calendar.JCalendar jCalendar_fechaCalendarioReserva;
    private javax.swing.JCheckBox jCheckBox_disponible;
    private javax.swing.JComboBox<String> jComboBox_chefReserva;
    private javax.swing.JComboBox<String> jComboBox_editarTipoUsuario;
    private javax.swing.JComboBox<String> jComboBox_filtroAZ;
    private javax.swing.JComboBox<String> jComboBox_filtroAZ1;
    private javax.swing.JComboBox<String> jComboBox_filtroAZReserva;
    private javax.swing.JComboBox<String> jComboBox_horaFinReserva;
    private javax.swing.JComboBox<String> jComboBox_horaInicioReserva;
    private javax.swing.JComboBox<String> jComboBox_minutosFinReserva;
    private javax.swing.JComboBox<String> jComboBox_minutosInicioReserva;
    private javax.swing.JComboBox<String> jComboBox_poblacion;
    private javax.swing.JComboBox<String> jComboBox_poblacion1;
    private javax.swing.JComboBox<String> jComboBox_poblacionReserva;
    private javax.swing.JComboBox<String> jComboBox_tipoUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_IBANUsuario;
    private javax.swing.JLabel jLabel_apellidos;
    private javax.swing.JLabel jLabel_apellidos1;
    private javax.swing.JLabel jLabel_apellidos2;
    private javax.swing.JLabel jLabel_apellidosUsuario;
    private javax.swing.JLabel jLabel_codigoPostal;
    private javax.swing.JLabel jLabel_codigoPostal1;
    private javax.swing.JLabel jLabel_codigoPostal2;
    private javax.swing.JLabel jLabel_codigoPostalUsuario;
    private javax.swing.JLabel jLabel_direccion;
    private javax.swing.JLabel jLabel_direccion1;
    private javax.swing.JLabel jLabel_direccion2;
    private javax.swing.JLabel jLabel_direccionUsuario;
    private javax.swing.JLabel jLabel_edad;
    private javax.swing.JLabel jLabel_edad1;
    private javax.swing.JLabel jLabel_edad2;
    private javax.swing.JLabel jLabel_edadUsuario;
    private javax.swing.JLabel jLabel_email;
    private javax.swing.JLabel jLabel_email1;
    private javax.swing.JLabel jLabel_emailUsuario;
    private javax.swing.JLabel jLabel_logo_login;
    private javax.swing.JLabel jLabel_mensajeLogin;
    private javax.swing.JLabel jLabel_nacionalidad;
    private javax.swing.JLabel jLabel_nacionalidad1;
    private javax.swing.JLabel jLabel_nacionalidad2;
    private javax.swing.JLabel jLabel_nacionalidadUsuario;
    private javax.swing.JLabel jLabel_nombre;
    private javax.swing.JLabel jLabel_nombre1;
    private javax.swing.JLabel jLabel_nombre2;
    private javax.swing.JLabel jLabel_nombreUsuario;
    private javax.swing.JLabel jLabel_poblacion;
    private javax.swing.JLabel jLabel_poblacion1;
    private javax.swing.JLabel jLabel_poblacion2;
    private javax.swing.JLabel jLabel_poblacionUsuario;
    private javax.swing.JLabel jLabel_telefono;
    private javax.swing.JLabel jLabel_telefono1;
    private javax.swing.JLabel jLabel_telefonoUsuario;
    private javax.swing.JLabel jLabel_tipoUsuario;
    private javax.swing.JLabel jLabel_tipoUsuario1;
    private javax.swing.JLabel jLabel_tipoUsuario2;
    private javax.swing.JLabel jLabel_tipoUsuarioUsuario;
    private javax.swing.JLabel jLabel_usernameUsuario;
    private javax.swing.JLayeredPane jLayeredPane_contenedor;
    private javax.swing.JPanel jPanel_Usuario;
    private javax.swing.JPanel jPanel_aceptarReservaChef;
    private javax.swing.JPanel jPanel_altaReserva;
    private javax.swing.JPanel jPanel_background;
    private javax.swing.JPanel jPanel_borrarUsuario;
    private javax.swing.JPanel jPanel_cambiarContraseña;
    private javax.swing.JPanel jPanel_crearCuenta;
    private javax.swing.JPanel jPanel_disponibilidad;
    private javax.swing.JPanel jPanel_editarCuenta;
    private javax.swing.JPanel jPanel_editarReserva;
    private javax.swing.JPanel jPanel_eliminarPropiaCuenta;
    private javax.swing.JPanel jPanel_listaChefsDisponibles;
    private javax.swing.JPanel jPanel_listarUsuarios;
    private javax.swing.JPanel jPanel_login;
    private javax.swing.JPanel jPanel_modificarTarifa;
    private javax.swing.JPanel jPanel_opcionesAdmin;
    private javax.swing.JPanel jPanel_opcionesChef;
    private javax.swing.JPanel jPanel_opcionesCliente;
    private javax.swing.JPasswordField jPasswordField_password;
    private javax.swing.JRadioButton jRadioButton_filtradoListaChefs;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable_listaChefsEstado;
    private javax.swing.JTable jTable_listaReservasChef;
    private javax.swing.JTable jTable_listaReservasCliente;
    private javax.swing.JTable jTable_usuarios;
    private javax.swing.JTextArea jTextArea_infoPagoReserva;
    private javax.swing.JTextField jTextField_IBAN;
    private javax.swing.JTextField jTextField_IDReservaChef;
    private javax.swing.JTextField jTextField_IDReservaEliminar;
    private javax.swing.JTextField jTextField_apellidos;
    private javax.swing.JTextField jTextField_chefReservaEliminar;
    private javax.swing.JTextField jTextField_clienteReservaChef;
    private javax.swing.JTextField jTextField_codigoPostal;
    private javax.swing.JTextField jTextField_comensalesReserva;
    private javax.swing.JTextField jTextField_comensalesReservaChef;
    private javax.swing.JTextField jTextField_comensalesReservaEliminar;
    private javax.swing.JTextField jTextField_contraseña;
    private javax.swing.JTextField jTextField_diaReservaEliminar;
    private javax.swing.JTextField jTextField_direccion;
    private javax.swing.JTextField jTextField_edad;
    private javax.swing.JTextField jTextField_editarApellidos;
    private javax.swing.JTextField jTextField_editarCodigoPostal;
    private javax.swing.JTextField jTextField_editarDireccion;
    private javax.swing.JTextField jTextField_editarEdad;
    private javax.swing.JTextField jTextField_editarEmail;
    private javax.swing.JTextField jTextField_editarIBAN;
    private javax.swing.JTextField jTextField_editarNacionalidad;
    private javax.swing.JTextField jTextField_editarNombre;
    private javax.swing.JTextField jTextField_editarPoblacion;
    private javax.swing.JTextField jTextField_editarTelefono;
    private javax.swing.JTextField jTextField_editarUsername;
    private javax.swing.JTextField jTextField_email;
    private javax.swing.JTextField jTextField_estadoReservaChef;
    private javax.swing.JTextField jTextField_estadoReservaEliminar;
    private javax.swing.JTextField jTextField_fechaReservaChef;
    private javax.swing.JTextField jTextField_finReservaChef;
    private javax.swing.JTextField jTextField_finReservaEliminar;
    private javax.swing.JTextField jTextField_indicarTarifa;
    private javax.swing.JTextField jTextField_inicioReservaChef;
    private javax.swing.JTextField jTextField_inicioReservaEliminar;
    private javax.swing.JTextField jTextField_nacionalidad;
    private javax.swing.JTextField jTextField_nombre;
    private javax.swing.JTextField jTextField_nuevaContraseña;
    private javax.swing.JTextField jTextField_poblacion;
    private javax.swing.JTextField jTextField_precioReserva;
    private javax.swing.JTextField jTextField_precioReservaChef;
    private javax.swing.JTextField jTextField_precioReservaEliminar;
    private javax.swing.JTextField jTextField_telefono;
    private javax.swing.JTextField jTextField_username;
    private javax.swing.JTextField jTextField_usuario;
    private javax.swing.JTextField jTextField_usuarioBorrar;
    private javax.swing.JToggleButton jToggleButton_altaNuevaReserva;
    private javax.swing.JToggleButton jToggleButton_bajaChef;
    private javax.swing.JToggleButton jToggleButton_bajaCliente;
    private javax.swing.JToggleButton jToggleButton_borrarUsuarioAdmin;
    private javax.swing.JToggleButton jToggleButton_disponibilidadChef;
    private javax.swing.JToggleButton jToggleButton_editarDatosAdmin;
    private javax.swing.JToggleButton jToggleButton_editarDatosChef;
    private javax.swing.JToggleButton jToggleButton_editarDatosCliente;
    private javax.swing.JToggleButton jToggleButton_eliminarReserva;
    private javax.swing.JToggleButton jToggleButton_indicarTarifa;
    private javax.swing.JToggleButton jToggleButton_revisarReservasChef;
    private javax.swing.JToggleButton jToggleButton_salirOpcionesAdmin;
    private javax.swing.JToggleButton jToggleButton_salirOpcionesChef;
    private javax.swing.JToggleButton jToggleButton_salirOpcionesCliente;
    private javax.swing.JToggleButton jToggleButton_verDatosAdmin;
    private javax.swing.JToggleButton jToggleButton_verDatosChef;
    private javax.swing.JToggleButton jToggleButton_verDatosCliente;
    private javax.swing.JToggleButton jToggleButton_verListaAdmin;
    private javax.swing.JToggleButton jToggleButton_verListaCliente;
    // End of variables declaration//GEN-END:variables

}
