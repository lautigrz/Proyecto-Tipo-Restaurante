package dominio;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Cliente {
	private String nombreCliente;
	private Menu menu[];
	private TipoPago tipoPago;
	private double montoFinal;
	private int numeroMesa;
	private boolean estadoDePago;

	public Cliente(String nombreCliente, int cantidadPlatos, int mesa) {
		this.nombreCliente = nombreCliente;
		this.menu = new Menu[cantidadPlatos];
		this.numeroMesa = mesa;
		// Crear contador para las mesas
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public boolean agregarPlato(Menu menu) {
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

	public int getNumeroMesa() {
		return numeroMesa;
	}

	public TipoPago getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(TipoPago tipoPago) {
		this.tipoPago = tipoPago;
	}

	public double getMontoAPagar() {
		return montoFinal;
	}

	public void setMontoAPagar(double montoPagado) {
		this.montoFinal = montoPagado;
	}

	public Menu[] getMenu() {
		return menu;
	}

	public boolean isEstadoDePago() {
		return estadoDePago;
	}

	public void setEstadoDePago(boolean estadoDePago) {
		this.estadoDePago = estadoDePago;
	}

	public String listaClientes() {
		StringBuilder ticketBuilder = new StringBuilder();
		ticketBuilder.append(String.format("%-20s: %s\n", "Cliente", nombreCliente));
		ticketBuilder.append(String.format("%-20s: %s\n", "Número de Mesa", numeroMesa));

		return ticketBuilder.toString();
	}

	public String toString() {

		StringBuilder ticketBuilder = new StringBuilder();
		LocalDateTime fechaHora = LocalDateTime.now();

		// LocalDate fecha = fechaHora.toLocalDate();
		// LocalTime hora = fechaHora.toLocalTime();

		DateTimeFormatter formaterFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		DateTimeFormatter formaterHora = DateTimeFormatter.ofPattern("HH:mm:ss");

		String formatoFecha = fechaHora.format(formaterFecha);
		String formatoHora = fechaHora.format(formaterHora);

		ticketBuilder.append("*******************************\n");
		ticketBuilder.append("*         T I C K E T         *\n");
		ticketBuilder.append("*******************************\n");

		ticketBuilder.append(String.format("%-20s: %s\n", "Cliente", nombreCliente));
		ticketBuilder.append(String.format("%-20s: %s\n", "Número de Mesa", numeroMesa));

		ticketBuilder.append("------- Detalles del Pedido -------\n");
		for (int i = 0; i < menu.length; i++) {
			ticketBuilder.append(String.format("%-10s: %s\n", "Consumo " + (i + 1), menu[i].platos()));
		}

		ticketBuilder.append(String.format("%-20s: %s\n", "Tipo de Pago", tipoPago));
		ticketBuilder.append(String.format("%-20s: %s\n", "Monto Final", montoFinal));

		ticketBuilder.append("------- Estado de Pago -------\n");
		ticketBuilder.append(String.format("%-20s: %s\n", "Estado de Pago", "Aprobado"));
		ticketBuilder.append(String.format("%-20s: %s\n", "Fecha", formatoFecha));
		ticketBuilder.append(String.format("%-20s: %s\n", "Hora", formatoHora));

		ticketBuilder.append("*******************************\n");

		return ticketBuilder.toString();
	}

}