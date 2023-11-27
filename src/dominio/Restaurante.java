package dominio;

public class Restaurante {
	private String nombreRestaurant;
	private Cliente[] cliente = new Cliente[1000];
	private Menu[] menu = new Menu[100];
	private Menu[] menuVentas = new Menu[100];
	private int[] mesasDelRestaurant = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };

	private final double APLICACION = 0.1;
	private final double TARJETA = 0.10;
	private final double TRANSFERENCIA = 0.1;
	private final double EFECTIVO = 0.0;

	public Restaurante(String nombreRestaurant) {
		this.nombreRestaurant = nombreRestaurant;
	}

	public String getNombreRestaurant() {
		return nombreRestaurant;
	}

	public void setNombreRestaurant(String nombreRestaurant) {
		this.nombreRestaurant = nombreRestaurant;
	}

	public Cliente[] getCliente() {
		return cliente;
	}

	public Menu[] getMenu() {
		return menu;
	}

	public Menu[] getMenuVentas() {
		return menuVentas;
	}

	public boolean nuevoCliente(Cliente cliente) {
		boolean seAgrego = false;
		int contador = 0;
		while (!seAgrego && contador < this.cliente.length) {
			if (this.cliente[contador] == null) {
				this.cliente[contador] = cliente;
				seAgrego = true;
			}
			contador++;
		}
		return seAgrego;
	}

	public Menu buscarPlatoPorID(int id) {
		Menu menu = null;
		boolean seEncontro = false;
		int contador = 0;

		while (!seEncontro && contador < this.menu.length) {
			if (this.menu[contador].getId() == id) {
				menu = this.menu[contador];
				seEncontro = true;
			}
			contador++;
		}
		return menu;
	}

	public boolean agregarPlatoAlMenu(Menu menu) {

		boolean seAgrego = false;
		int contador = 0;

		while (!seAgrego && contador < this.menu.length) {
			if (this.menu[contador] == null) {
				this.menu[contador] = menu;
				seAgrego = true;
			}
			contador++;
		}
		return seAgrego;

	}

	public boolean agregarPlato(Menu plato) {
		boolean seAgrego = false;
		int contador = 0;

		while (!seAgrego && contador < this.menu.length) {
			if (this.menu[contador] == null) {
				this.menu[contador] = plato;
				seAgrego = true;
			}
			contador++;
		}

		return seAgrego;
	}

	public boolean eliminarPlatoPorId(int id) {
		boolean seElimino = false;
		int contador = 0;

		while (!seElimino && contador < this.menu.length) {
			if (this.menu[contador] != null && this.menu[contador].getId() == id) {
				this.menu[contador] = null;
				seElimino = true;
			}
			contador++;
		}

		return seElimino;
	}

	public Cliente[] ticketDeClientePorTipoDepago(TipoPago tipoPago) {

		Cliente[] clientes = new Cliente[this.cliente.length];
		int posicion = 0;
		for (int i = 0; i < this.cliente.length; i++) {
			if (this.cliente[i] != null && this.cliente[i].getTipoPago().equals(tipoPago)) {
				clientes[posicion] = this.cliente[i];
				posicion++;
			}
		}

		return clientes;
	}

	public double recaudacionDelDia() {

		double montoObtenido = 0;

		for (int i = 0; i < this.cliente.length; i++) {
			if (this.cliente[i] != null) {
				montoObtenido += this.cliente[i].getMontoAPagar();
			}
		}

		return montoObtenido;

	}

	public void habilitarMesa(Cliente cliente, boolean pagoRealizado) {
		int mesa = cliente.getNumeroMesa();
		if (pagoRealizado) {
			this.mesasDelRestaurant[mesa - 1] = mesa;
		}
	}

	public int asignarMesaDisponible() {
		int numeroDeMesa = 0;

		for (int i = 0; i < this.mesasDelRestaurant.length; i++) {
			if (this.mesasDelRestaurant[i] != 0) {
				numeroDeMesa = this.mesasDelRestaurant[i];
				this.mesasDelRestaurant[numeroDeMesa - 1] = 0;
				break;
			}
		}
		return numeroDeMesa;
	}

	public Cliente obtenerClientePorNumeroDeMesa(int id) {

		Cliente cliente = null;

		boolean seEncontro = false;
		int contador = 0;

		while (!seEncontro && contador < this.cliente.length) {
			if (this.cliente[contador] != null && this.cliente[contador].getNumeroMesa() == id) {
				cliente = this.cliente[contador];
				seEncontro = true;
			}
			contador++;
		}
		return cliente;

	}

	public int obtenerID() {
		int id = (int) (Math.random() * this.menu.length);
		verificarID(id);

		return id;
	}

	public int verificarID(int id) {
		int nuevoID = 0;

		boolean seEncontro = false;
		int contador = 0;

		while (!seEncontro && contador < this.cliente.length) {
			if (this.cliente[contador] != null && this.cliente[contador].getNumeroMesa() == id) {
				seEncontro = true;
			}
			contador++;
		}

		nuevoID = id;

		if (seEncontro) {
			nuevoID = obtenerID();
		}

		return nuevoID;
	}

	public Cliente[] clientesQueDebenPagar() {

		Cliente[] clientes = new Cliente[this.cliente.length];
		int contador = 0;
		for (int i = 0; i < this.cliente.length; i++) {

			if (this.cliente[i] != null && !this.cliente[i].isEstadoDePago()) {
				clientes[contador] = this.cliente[i];
				contador++;
			}

		}

		return clientes;

	}

	public Menu[] ordenarMenuDeVentasPorPrecioAscendente() {

		for (int i = 0; i < this.menuVentas.length; i++) {
			for (int j = 0; j < this.menu.length - 1; j++) {

				if (this.menuVentas[j] != null && this.menuVentas[j + 1] != null
						&& this.menuVentas[j].getPrecio() > this.menuVentas[j + 1].getPrecio()) {
					Menu aux = this.menuVentas[j];
					this.menuVentas[j] = this.menuVentas[j + 1];
					this.menuVentas[j + 1] = aux;
				}

			}
		}
		return this.menuVentas;
	}

	public Menu[] obtenerMenuDeVentasPorProduco(TipoProducto tipoProducto) {

		Menu[] menu = new Menu[this.menu.length];
		int contador = 0;
		for (int i = 0; i < this.menu.length; i++) {
			if (this.menuVentas[i] != null && this.menuVentas[i].getTipoProducto().equals(tipoProducto)) {
				menu[contador] = this.menuVentas[i];
				contador++;
			}

		}
		return menu;

	}

	public double obtenerPrecio(TipoPago tipoPago, double monto) {

		double montoFinal = monto;

		switch (tipoPago) {
		case APLICACION:
			monto *= APLICACION;
			montoFinal -= monto;
			break;
		case TARJETA_CREDITO:
			monto *= TARJETA;
			montoFinal += monto;
			break;
		case TRANSFERENCIA:
			monto *= TRANSFERENCIA;
			montoFinal += monto;
			break;
		default:
			monto *= EFECTIVO;
			montoFinal += monto;
			break;
		}
		return montoFinal;
	}

	public double verMontoDePago(Cliente cliente) {

		double montoSinRecargo = 0;

		for (int i = 0; i < cliente.getMenu().length; i++) {

			if (cliente.getMenu()[i] != null) {

				switch (cliente.getMenu()[i].getTipoProducto()) {
				case PLATO:
					montoSinRecargo += cliente.getMenu()[i].getPrecio();
					break;
				case BEBIDA:
					montoSinRecargo += cliente.getMenu()[i].getPrecio();
					break;
				default:
					break;
				}
			}
		}

		double montoFinal = obtenerPrecio(cliente.getTipoPago(), montoSinRecargo);

		cliente.setMontoAPagar(montoFinal);
		return montoFinal;

	}

	private void agregarVenta(Cliente cliente, boolean pagoRealizado) {
		int posicion = 0;
		if (pagoRealizado) {
			for (int i = 0; i < this.menuVentas.length && posicion < cliente.getMenu().length; i++) {
				if (this.menuVentas[i] == null && cliente.getMenu()[posicion] != null) {
					this.menuVentas[i] = cliente.getMenu()[posicion];
					posicion++;
				}
			}
			posicion = 0;
		}
	}

	public boolean realizarPago(Cliente cliente, double monto, Camarero camarero) {

		boolean pagoRealizado = false;

		TipoPago tipo = cliente.getTipoPago();

		if (monto >= cliente.getMontoAPagar()) {
			switch (tipo) {

			case APLICACION:
				pagoRealizado = !verificarSiElMontoEsMayor(monto, cliente);
				break;
			case TARJETA_CREDITO:
				pagoRealizado = !verificarSiElMontoEsMayor(monto, cliente);
				break;
			case TRANSFERENCIA:
				pagoRealizado = !verificarSiElMontoEsMayor(monto, cliente);
				break;
			case EFECTIVO:
				double propina = monto - cliente.getMontoAPagar();
				camarero.recibirPropina(propina);
				pagoRealizado = true;
				break;
			}
			agregarVenta(cliente, pagoRealizado);
			habilitarMesa(cliente, pagoRealizado);
		}

		return pagoRealizado;
	}

	private boolean verificarSiElMontoEsMayor(double monto, Cliente cliente) {

		boolean esMayor = false;

		if (monto > cliente.getMontoAPagar()) {
			esMayor = true;
		}
		return esMayor;
	}

}
