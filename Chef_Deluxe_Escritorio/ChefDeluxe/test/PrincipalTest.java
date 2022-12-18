/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.text.ParseException;
import junit.framework.TestCase;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Álvaro
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PrincipalTest extends TestCase {

    public PrincipalTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private Principal principal;

    public void inicio() {
        principal = new Principal();

    }

    @Test
    public void testA_CrearCuentaAdmin() throws IOException {
        inicio();
        principal.getjTextField_nombre().setText("AlvaroAdminTest");
        principal.getjTextField_username().setText("alvaroadmintest");
        principal.getjTextField_contraseña().setText("1234");
        principal.getjComboBox_tipoUsuario().setSelectedItem("Administrador");
        principal.getjTextField_apellidos().setText("Ariet");
        principal.getjTextField_direccion().setText("Calle Tres");
        principal.getjTextField_codigoPostal().setText("08790");
        principal.getjTextField_poblacion().setText("Gelida");
        principal.getjTextField_nacionalidad().setText("España");
        principal.getjTextField_edad().setText("47");
        principal.getjTextField_telefono().setText("629540398");
        principal.getjTextField_IBAN().setText("ES64949491984974");
        principal.getjTextField_email().setText("alvaroadmintest@gmail.com");
        principal.getjButton_crearCuenta().doClick();
        //principal.getjToggleButton_verDatosAdmin().doClick();
        int conectionCode = principal.getConnection().getResponseCode();
        assertEquals(200, conectionCode);

    }

    @Test
    public void testB_LoginCuenta() throws IOException {
        inicio();
        principal.getjTextField_usuario().setText("alvaroadmintest");
        principal.getjPasswordField_password().setText("1234");
        principal.getjButton_login().doClick();
        int conectionCode = principal.getConnection().getResponseCode();
        assertEquals(200, conectionCode);
    }

    @Test
    public void testC_obtenerDatosCuenta() throws IOException {
        inicio();
        //Hacemos login
        principal.getjTextField_usuario().setText("alvaroadmintest");
        principal.getjPasswordField_password().setText("1234");
        principal.getjButton_login().doClick();
        //Se utiliza el método para obtener los datos del usuario,
        //se comprueba su recepción y que se está mostrando en la aplicación
        principal.obtenerDatosUsuario();
        assertEquals("629540398", principal.getjTextField_editarTelefono().getText());
    }

    @Test
    public void testD_cambiarContraseña() throws IOException {
        inicio();
        //Hacemos login
        principal.getjTextField_usuario().setText("alvaroadmintest");
        principal.getjPasswordField_password().setText("1234");
        principal.getjButton_login().doClick();
        String nuevaContraseña = "password";
        principal.modificarContraseña(nuevaContraseña);
        int conectionCode = principal.getConnection().getResponseCode();
        assertEquals(200, conectionCode);
    }

    @Test
    public void testG_CrearCuentaChef() throws IOException {
        inicio();
        principal.getjTextField_nombre().setText("AlvaroChefTest");
        principal.getjTextField_username().setText("alvarocheftest");
        principal.getjTextField_contraseña().setText("1234");
        principal.getjComboBox_tipoUsuario().setSelectedItem("Chef");
        principal.getjTextField_apellidos().setText("Ariet");
        principal.getjTextField_direccion().setText("Calle Tres");
        principal.getjTextField_codigoPostal().setText("08790");
        principal.getjTextField_poblacion().setText("Gelida");
        principal.getjTextField_nacionalidad().setText("España");
        principal.getjTextField_edad().setText("47");
        principal.getjTextField_telefono().setText("629540398");
        principal.getjTextField_IBAN().setText("ES64949491984974");
        principal.getjTextField_email().setText("alvarocheftest@gmail.com");
        principal.getjButton_crearCuenta().doClick();
        //principal.getjToggleButton_verDatosAdmin().doClick();
        int conectionCode = principal.getConnection().getResponseCode();
        assertEquals(200, conectionCode);

    }

    @Test
    public void testH_CrearCuentaCliente() throws IOException {
        inicio();
        principal.getjTextField_nombre().setText("AlvaroClienteTest");
        principal.getjTextField_username().setText("alvaroclientetest");
        principal.getjTextField_contraseña().setText("1234");
        principal.getjComboBox_tipoUsuario().setSelectedItem("Cliente");
        principal.getjTextField_apellidos().setText("Ariet");
        principal.getjTextField_direccion().setText("Calle Tres");
        principal.getjTextField_codigoPostal().setText("08790");
        principal.getjTextField_poblacion().setText("Gelida");
        principal.getjTextField_nacionalidad().setText("España");
        principal.getjTextField_edad().setText("47");
        principal.getjTextField_telefono().setText("629540398");
        principal.getjTextField_IBAN().setText("ES64949491984974");
        principal.getjTextField_email().setText("alvaroclientetest@gmail.com");
        principal.getjButton_crearCuenta().doClick();
        //principal.getjToggleButton_verDatosAdmin().doClick();
        int conectionCode = principal.getConnection().getResponseCode();
        assertEquals(200, conectionCode);

    }

    @Test
    public void testI_altaTarifa() throws IOException {
        inicio();
        //Hacemos login
        principal.getjTextField_usuario().setText("alvarocheftest");
        principal.getjPasswordField_password().setText("1234");
        principal.getjButton_login().doClick();
        //Se comprueba el alta de la tarifa              
        principal.getjTextField_indicarTarifa().setText("82.00");
        principal.getjButton_modificarTarifa();
        int conectionCode = principal.getConnection().getResponseCode();
        assertEquals(200, conectionCode);
    }

    @Test
    public void testK_altaDisponibilidad() throws IOException {
        inicio();
        //Hacemos login
        principal.getjTextField_usuario().setText("alvarocheftest");
        principal.getjPasswordField_password().setText("1234");
        principal.getjButton_login().doClick();
        //Se obtiene el ID de la tarifa
        principal.obtenerTarifa("alvarocheftest");
        //Se comprueba el alta de la disponibilidad en una población
        principal.getjToggleButton_disponibilidadChef().doClick();
        principal.getjCheckBox_disponible().setSelected(true);
        principal.getjComboBox_poblacion().setSelectedItem("Abades");
        principal.getjButton_aplicarCambiosDisponibilidad().doClick();
        int conectionCode = principal.getConnection().getResponseCode();
        assertEquals(200, conectionCode);

    }

    @Test
    public void testL_bajaDisponibilidad() throws IOException {
        inicio();
        //Hacemos login
        principal.getjTextField_usuario().setText("alvarocheftest");
        principal.getjPasswordField_password().setText("1234");
        principal.getjButton_login().doClick();
        //Se obtiene el ID de la tarifa
        principal.obtenerTarifa("alvarocheftest");
        //Se comprueba el alta de la disponibilidad en una población
        principal.getjToggleButton_disponibilidadChef().doClick();
        principal.getjCheckBox_disponible().setSelected(false);
        principal.getjComboBox_poblacion().setSelectedItem("Abades");
        principal.getjButton_aplicarCambiosDisponibilidad().doClick();
        int conectionCode = principal.getConnection().getResponseCode();
        assertEquals(200, conectionCode);
    }

    @Test
    public void testN_altaReserva() throws IOException, ParseException {
        inicio();
        //Hacemos login
        principal.getjTextField_usuario().setText("alvaroclientetest");
        principal.getjPasswordField_password().setText("1234");
        principal.getjButton_login().doClick();
        //Se indican los datos necesarios para realizar la reserva        
        String estado = "pendiente";
        String cliente = "alvaroclientetest";
        String chef = "alvarocheftest";
        String inicio = "2022-12-31T21:15:00.00000";
        String fin = "2022-12-31T23:45:00.00000";
        Double precio = 0.00;
        int comensales = 4;
        //Se comprueba el alta de la reserva
        principal.altaReserva(estado, cliente, chef, inicio, fin, precio, comensales);
        int conectionCode = principal.getConnection().getResponseCode();
        assertEquals(200, conectionCode);
    }

    @Test
    public void testO_eliminarReserva() throws IOException {
        inicio();
        //Hacemos login con cuenta chef
        principal.getjTextField_usuario().setText("alvarocheftest");
        principal.getjPasswordField_password().setText("1234");
        principal.getjButton_login().doClick();
        JSONArray respuesta = principal.obtenerListaReservasChef();
        JSONObject reserva = respuesta.getJSONObject(0);
        //Conseguimos el id de la reserva a eliminar
        String idReserva = reserva.get("id").toString();
        principal.getjToggleButton_salirOpcionesChef().doClick();
        //Hacemos login con cuenta cliente
        principal.getjTextField_usuario().setText("alvaroadmintest");
        principal.getjPasswordField_password().setText("password");
        principal.getjButton_login().doClick();
        //Se elimina la reserva con el ID
        principal.borrarReserva(idReserva);
        int conectionCode = principal.getConnection().getResponseCode();
        assertEquals(200, conectionCode);
    }

    @Test
    public void testP_eliminarDisponibilidad() throws IOException {
        inicio();
        //Hacemos login
        principal.getjTextField_usuario().setText("alvaroadmintest");
        principal.getjPasswordField_password().setText("password");
        principal.getjButton_login().doClick();
        //Se obtiene el ID de la disponibilidad
        principal.obtenerDisponibilidadPoblacion("alvarocheftest", "Abades");
        //Se comprueba la eliminacion de la disponibilidad
        principal.borrarDisponibilidad(principal.getIdDisponibilidad());
        int conectionCode = principal.getConnection().getResponseCode();
        assertEquals(200, conectionCode);
    }

    @Test
    public void testX_EliminarPropiaCuentaChef() throws IOException {
        inicio();
        //Hacemos login
        principal.getjTextField_usuario().setText("alvarocheftest");
        principal.getjPasswordField_password().setText("1234");
        principal.getjButton_login().doClick();
        //Se comprueba el borrado de la propia cuenta chef              
        principal.getjButton_eliminarEliminarCuenta().doClick();
        int conectionCode = principal.getConnection().getResponseCode();
        assertEquals(200, conectionCode);
    }

    @Test
    public void testY_EliminarPropiaCuentaCliente() throws IOException {
        inicio();
        //Hacemos login
        principal.getjTextField_usuario().setText("alvaroclientetest");
        principal.getjPasswordField_password().setText("1234");
        principal.getjButton_login().doClick();
        //Se comprueba el borrado de la propia cuenta cliente              
        principal.getjButton_eliminarEliminarCuenta().doClick();
        int conectionCode = principal.getConnection().getResponseCode();
        assertEquals(200, conectionCode);

    }

    @Test
    public void testZ_EliminarCuentaUsuario() throws IOException {
        inicio();
        //Hacemos login
        principal.getjTextField_usuario().setText("alvaroadmintest");
        principal.getjPasswordField_password().setText("password");
        principal.getjButton_login().doClick();
        //Se comprueba el borrado de un usuario por parte del Administrador                
        principal.getjTextField_usuarioBorrar().setText("alvaroadmintest");
        principal.getjButton_eliminarBorrarUsuario().doClick();
        int conectionCode = principal.getConnection().getResponseCode();
        assertEquals(200, conectionCode);
    }

}
