package interfaz;

import dominio.*;
import java.util.*;

public class MainRestaurante {
	static Scanner teclado = new Scanner(System.in);

	// Colores ANSI para la consola
	public static final String RESET = "\u001B[0m";
	public static final String AZUL_CLARO = "\u001B[94m";
	public static final String BLANCO = "\u001B[37m";

	public static void main(String[] args) {

		Restaurante restaurante = new Restaurante("Restaurante messi");
		Admin admin = new Admin("1234");
		MenuPrincipal menuPrincipal = null;
		agregarPlatosAleatoriaamente(restaurante);
		agregarCamarerosAleatoriamente(admin);

		mostrarPorPantalla(BLANCO + "Bienvenido a " + AZUL_CLARO + restaurante.getNombreRestaurant() + RESET);
		do {
			menuPrincipal = menuPrincipal();

			switch (menuPrincipal) {
			case MENU_CAMARERO:
				menuCamarero(restaurante, admin);
				break;
			case MENU_ADMIN:
				administracion(restaurante, admin);
				break;
			case SALIR:
				break;
			}
		} while (menuPrincipal != MenuPrincipal.SALIR);

		mostrarPorPantalla("Restaurante cerrado.");

	}

	private static void administracion(Restaurante restaurante, Admin admin) {
		String contrasenia = ingresarString("Ingresar contrasenia:");
		boolean esValida = admin.validarContrasenia(contrasenia);
		// TODO Auto-generated method stub
		if (esValida) {
			verMenuAdmin(restaurante, admin);
		} else {
			mostrarPorPantalla("Contrasenia incorrecta");
		}

	}

	// Menú del camarero
	private static void menuCamarero(Restaurante restaurante, Admin admin) {
		MenuCamarero menu = null;
		do {
			mostrarMenu();
			menu = menuCamarero("Ingrese opcion");
			Camarero camarero = admin.obtenerCamarero();
			switch (menu) {
			case ATENDER_CLIENTE:
				agregarCliente(restaurante, camarero);
				break;
			case MESA_A_PAGAR:
				pagarCuenta(restaurante, admin);
				break;
			case VER_PROPINA:
				propinaDeCamarero(admin);
			case SALIR:
				break;
			default:
				mostrarPorPantalla("Opción no válida. Intente de nuevo.");
				break;
			}

		} while (menu != MenuCamarero.SALIR);

	}

	private static void verMenuAdmin(Restaurante restaurante, Admin admin) {
		MenuAdmin menu = null;
		do {
			mostrarMenuAdmin();
			menu = menuAdmin("Ingrese opcion");

			switch (menu) {
			case VER_RECAUDACION_DEL_DIA:
				verRecaucaionDelDia(restaurante);
				break;
			case VER_TICKET_POR_TIPODEPAGO:
				verTicketPorTipoDePago(restaurante);
				break;
			case VER_VENTAS_DEL_DIA:
				verVentasDelDia(restaurante);
				break;
			case CONTRATAR_CAMARERO:
				contratarCamarero(admin);
				break;
			case DESPEDIR_CAMARERO:
				despedirCamarero(admin);
				break;
			case AGREGAR_PLATO:
				agregarPlato(restaurante);
				break;
			case ELIMINAR_PLATO:
				eliminarPlato(restaurante);
				break;
			case ATRAS:
				break;
			default:
				mostrarPorPantalla("Opción no válida. Intente de nuevo.");
				break;
			}

		} while (menu != MenuAdmin.ATRAS);
	}

	private static void verVentasDelDia(Restaurante restaurante) {
		// TODO Auto-generated method stub
		int opcion = 0;

		do {
			opcion = ingresarEntero("1)VER POR PRECIO ASCENDENTE \n2)VER POR TIPO DE PRODCUTO \n3)SALIR");
			switch (opcion) {
			case 1:
				Menu[] menuVentas = restaurante.ordenarMenuDeVentasPorPrecioAscendente();

				if (menuVentas[0] != null) {
					mostrarPlatos(menuVentas);
				} else {
					mostrarPorPantalla("Aun no existen ventas.");
				}
				break;
			case 2:

				TipoProducto tipo = obtenerTipoProducto("Escriba opcion (PLATO O BEBIDA)");

				Menu[] menuVentasPorProducto = restaurante.obtenerMenuDeVentasPorProduco(tipo);

				if (menuVentasPorProducto[0] != null) {
					mostrarPlatos(menuVentasPorProducto);
				} else {
					mostrarPorPantalla("Aun no existen ventas de ese tipo.");
				}
				break;

			}
		} while (opcion != 3);

	}

	private static void agregarCliente(Restaurante restaurante, Camarero camarero) {
		// TODO Auto-generated method stub
		mostrarPorPantalla("Nombre del cliente:");
		String nombre = teclado.next();
		mostrarPorPantalla("Cantidad de platos a pedir:");
		int platos = teclado.nextInt();
		int mesa = restaurante.asignarMesaDisponible();

		Cliente cliente = new Cliente(nombre, platos, mesa);
		boolean agregado = restaurante.nuevoCliente(cliente);

		if (agregado) {
			camarero.agregarNumeroDeMesaAlCamarero(mesa);
			mostrarPorPantalla("Hola " + cliente.getNombreCliente() + " que va a pedir?");
			Menu[] platosDelMenu = restaurante.getMenu();
			mostrarPlatos(platosDelMenu);
			agregarPlatosAlCliente(cliente, platos, restaurante);
		}

	}

	private static void agregarPlatosAlCliente(Cliente cliente, int platos, Restaurante restaurante) {
		// TODO Auto-generated method stub
		for (int i = 0; i < platos; i++) {
			int id = ingresarEntero("Ingrese ID");

			Menu menu = restaurante.buscarPlatoPorID(id);

			boolean platoAgregado = cliente.agregarPlato(menu);

			if (platoAgregado) {
				mostrarPorPantalla("Plato agregado");
			} else {
				mostrarPorPantalla("El plato no pudo ser agregado");
			}
		}
	}

	private static void agregarPlato(Restaurante restaurante) {
		mostrarPorPantalla("Ingrese los detalles del nuevo plato:");

		String nombre = ingresarString("Nombre:");

		double precio = ingresarEntero("Precio:");

		TipoProducto tipoProducto = obtenerTipoProducto("Tipo de producto (PLATO o BEBIDA):");
		int id = obtenerID();

		Menu nuevoPlato = new Menu(nombre, precio, tipoProducto, id);

		if (restaurante.agregarPlato(nuevoPlato)) {
			mostrarPorPantalla("Plato agregado correctamente.");
		} else {
			mostrarPorPantalla("No se pudo agregar el plato.");
		}
	}

	private static void eliminarPlato(Restaurante restaurante) {
		mostrarPlatos(restaurante.getMenu());

		int id = ingresarEntero("Ingrese el ID del plato que desea eliminar:");

		if (restaurante.eliminarPlatoPorId(id)) {
			mostrarPorPantalla("Plato eliminado correctamente.");
		} else {
			mostrarPorPantalla("No se pudo encontrar el plato con el ID especificado.");
		}
	}

	private static void contratarCamarero(Admin admin) {
		String nombre = ingresarString("Ingresar nombre del nuevo camarero: ");
		Camarero camarero = new Camarero(nombre);
		if (admin.contratarCamarero(camarero)) {
			mostrarPorPantalla("Bienvenido " + nombre);
		} else {
			mostrarPorPantalla("Nosotros te llamamos");
		}

	}

	private static void despedirCamarero(Admin admin) {
		Camarero[] camarero = admin.camareros();
		mostrarPorPantalla("Lista de camareros: ");
		for (int i = 0; i < camarero.length; i++) {
			if (camarero[i] != null) {
				mostrarPorPantalla("El nombre del camarero es: " + camarero[i].getNombre());
			}
		}
		String nombre = ingresarString("Ingresar nombre del camarero que desee despedir: ");
		if (admin.despedirCamarero(nombre)) {
			mostrarPorPantalla("Chau " + nombre + " que te vaya bien!");
		} else {
			mostrarPorPantalla("No hay ningun camarero con el nombre: " + nombre);
		}
	}

	private static void pagarCuenta(Restaurante restaurante, Admin admin) {
		mostrarPorPantalla("Lista de clientes");
		Cliente[] clientes = restaurante.clientesQueDebenPagar();

		if (clientes[0] != null) {
			mostrarListaClientes(clientes);

			int numeroMesa = ingresarEntero("Seleccione numero de mesa que va a pagar");

			Cliente clienteMesa = restaurante.obtenerClientePorNumeroDeMesa(numeroMesa);
			Camarero camarero = obtenerCamareroPorNumeroDeMesa(numeroMesa, admin);
			if (clienteMesa != null && camarero != null) {
				mostrarCliente(clienteMesa);
				finalizarPago(restaurante, clienteMesa, camarero);
			} else {
				mostrarPorPantalla("Mesa no encontrada");
			}
		} else {
			mostrarPorPantalla("No hay mesas disponibles.");
		}
	}

	private static void finalizarPago(Restaurante restaurante, Cliente cliente, Camarero camarero) {

		for (int i = 1; i <= TipoPago.values().length; i++) {

			mostrarPorPantalla(i + ")" + TipoPago.values()[i - 1]);

		}

		TipoPago metodo = metodoPago("Eliga un metodo de pago");

		cliente.setTipoPago(metodo);
		mostrarPorPantalla("Monto a pagar:$" + restaurante.verMontoDePago(cliente));
		boolean yaPago = false;
		do {
			int montoAPagar = ingresarEntero("Ingrese monto de pago:$");

			yaPago = restaurante.realizarPago(cliente, montoAPagar, camarero);

			if (yaPago) {
				mostrarPorPantalla("Pago efectuado");
				cliente.setEstadoDePago(yaPago);
				mostrarTicketCliente(cliente);
			} else {
				mostrarPorPantalla("El pago no pudo ser efectuado, intente de nuevo");
			}
		} while (!yaPago);
	}

	// Ver propina del camarero

	private static void propinaDeCamarero(Admin admin) {
		// TODO Auto-generated method stub

		for (int i = 0; i < admin.camareros().length; i++) {
			if (admin.camareros()[i] != null) {
				mostrarPorPantalla("Camarero:" + admin.camareros()[i].getNombre() + "\n");
				mostrarPorPantalla("Propina:" + admin.camareros()[i].getPropina() + "\n");

			}
		}
	}

	private static void verTicketPorTipoDePago(Restaurante restaurante) {
		// TODO Auto-generated method stub

		for (int i = 1; i <= TipoPago.values().length; i++) {

			mostrarPorPantalla(i + ")" + TipoPago.values()[i - 1]);

		}

		TipoPago metodoPago = metodoPago("Ingrese el tipo de pago");

		Cliente[] clientes = restaurante.ticketDeClientePorTipoDepago(metodoPago);

		if (clientes[0] != null) {
			mostrarTicketClientes(clientes);
		} else {
			mostrarPorPantalla("Aun no existen tickets");
		}
	}

	private static void verRecaucaionDelDia(Restaurante restaurante) {
		// TODO Auto-generated method stub

		mostrarPorPantalla("La recaudacion del dia es:$" + restaurante.recaudacionDelDia());

	}

	public static Camarero obtenerCamareroPorNumeroDeMesa(int numeroMesa, Admin admin) {

		for (int i = 0; i < admin.camareros().length; i++) {
			if (admin.camareros()[i] != null) { 
				for (int j = 0; j < admin.camareros()[i].getNumeroDeMesa().length; j++) {
					if (admin.camareros()[i].getNumeroDeMesa()[j] == numeroMesa) {
						return admin.camareros()[i];
					}
				}
			}
		}
		return null;
	}

	private static int obtenerID() {
		return (int) (Math.random() * 100) + 1;
	}

	private static TipoProducto obtenerTipoProducto(String mensaje) {
		mostrarPorPantalla(mensaje);
		String tipo = teclado.next().toUpperCase();
		return TipoProducto.valueOf(tipo);
	}

	private static int ingresarEntero(String mensaje) {
		mostrarPorPantalla(mensaje);

		return teclado.nextInt();
	}

	private static String ingresarString(String mensaje) {
		mostrarPorPantalla(mensaje);

		return teclado.next();
	}

	public static TipoPago metodoPago(String mensaje) {
		mostrarPorPantalla(mensaje);
		int num = teclado.nextInt();
		return TipoPago.values()[num - 1];
	}

	public static MenuPrincipal menuPrincipal() {

		for (int i = 1; i <= MenuPrincipal.values().length; i++) {
			mostrarPorPantalla(i + ")" + MenuPrincipal.values()[i - 1]);
		}

		int num = teclado.nextInt();

		if (num > MenuPrincipal.values().length) {
			return MenuPrincipal.SALIR;
		}
		return MenuPrincipal.values()[num - 1];
	}

	public static MenuCamarero menuCamarero(String mensaje) {
		mostrarPorPantalla(mensaje);
		int num = teclado.nextInt();
		if (num > MenuCamarero.values().length) {
			return MenuCamarero.SALIR;
		}
		return MenuCamarero.values()[num - 1];
	}

	public static MenuAdmin menuAdmin(String mensaje) {
		mostrarPorPantalla(mensaje);
		int num = teclado.nextInt();

		if (num > MenuAdmin.values().length) {
			return MenuAdmin.ATRAS;
		}
		return MenuAdmin.values()[num - 1];
	}

	private static void mostrarMenu() {
		// TODO Auto-generated method stub
		for (int i = 1; i <= MenuCamarero.values().length; i++) {
			mostrarPorPantalla(i + ")" + MenuCamarero.values()[i - 1]);
		}
	}

	private static void mostrarMenuAdmin() {
		// TODO Auto-generated method stub

		for (int i = 1; i <= MenuAdmin.values().length; i++) {
			mostrarPorPantalla(i + ")" + MenuAdmin.values()[i - 1]);
		}

	}

	private static void mostrarListaClientes(Cliente[] clientes) {
		// TODO Auto-generated method stub
		for (int i = 0; i < clientes.length; i++) {
			if (clientes[i] != null) {
				mostrarPorPantalla(clientes[i].listaClientes());
			}
		}

	}

	private static void mostrarTicketClientes(Cliente[] clientes) {
		// TODO Auto-generated method stub
		for (int i = 0; i < clientes.length; i++) {
			if (clientes[i] != null) {
				mostrarPorPantalla(clientes[i].toString());
			}
		}

	}

	private static void mostrarCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		System.out.println(cliente.listaClientes());
	}

	private static void mostrarTicketCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		mostrarPorPantalla(cliente.toString());

	}

	private static void mostrarPlatos(Menu menu[]) {
		for (int i = 0; i < menu.length; i++) {
			if (menu[i] != null) {
				mostrarPorPantalla(menu[i].platos());
			}
		}
	}

	private static void mostrarPorPantalla(String mensaje) {
		System.out.println(mensaje);
	}

	private static void agregarCamarerosAleatoriamente(Admin admin) {
		// TODO Auto-generated method stub

		String[] nombre = { "Gustavo", "Rocio", "Jimena" };

		for (int i = 0; i < 3; i++) {

			Camarero nuevoCamarero = new Camarero(nombre[i]);

			admin.contratarCamarero(nuevoCamarero);

		}

	}

	private static void agregarPlatosAleatoriaamente(Restaurante restaurante) {
		String[] plato = { "Asado", "Pizza", "Pastas", "Empanadas", "Milanesa", "Tarta", "Pastel de papas" };

		String[] bebida = { "Coca Cola", "Pepsi", "Manaos", "Seven up", "Fanta" };

		double[] precioBebida = { 350, 250, 400, 550, 450 };

		double[] precioPlato = { 1567, 1150, 1600, 1750, 1000, 1670, 1450 };

		// Random random = new Random();

		for (int i = 0; i < 7; i++) {
			String nombrePlato = plato[i];
			double precio = precioPlato[i];
			int id = (int) (Math.random() * 99) + 1;
			Menu menu = new Menu(nombrePlato, precio, TipoProducto.PLATO, id);
			restaurante.agregarPlatoAlMenu(menu);

		}

		for (int i = 0; i < 5; i++) {
			String nombrePlato = bebida[i];
			double precio = precioBebida[i];
			int id = (int) (Math.random() * 99) + 1;
			Menu menu = new Menu(nombrePlato, precio, TipoProducto.BEBIDA, id);
			restaurante.agregarPlatoAlMenu(menu);

		}
	}

}