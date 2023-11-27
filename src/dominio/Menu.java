package dominio;


public class Menu {
	private String nombre;
	private double precio;
	private TipoProducto tipoProducto;
	private int id;

	public Menu(String nombre, double precio, TipoProducto tipoProducto, int id) {

		this.nombre = nombre;
		this.precio = precio;
		this.tipoProducto = tipoProducto;
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String platos() {
		StringBuilder producto = new StringBuilder();
		producto.append(String.format("%-10s: %s\n", tipoProducto, nombre));
		producto.append(String.format("%-10s: %s\n", "Precio", "$" + precio));
		producto.append(String.format("%-10s: %s\n", "ID", id));

		return producto.toString();
	}

}