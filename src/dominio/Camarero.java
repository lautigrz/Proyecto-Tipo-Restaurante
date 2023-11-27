package dominio;

public class Camarero {
	private String nombre;
	private double propina;
	private int[] numeroDeMesa = new int[20];

	public Camarero(String nombre) {
		this.nombre = nombre;
		this.propina = 0.0;
	}

	public String getNombre() {
		return nombre;
	}

	public double getPropina() {
		return propina;
	}

	public void recibirPropina(double propina) {
		this.propina += propina;
	}

	public int[] getNumeroDeMesa() {
		return numeroDeMesa;
	}

	public void agregarNumeroDeMesaAlCamarero(int numeroDeMesa) {

		boolean seAgrego = false;
		int contador = 0;

		while (!seAgrego && contador < this.numeroDeMesa.length) {
			if (this.numeroDeMesa[contador] == 0) {
				this.numeroDeMesa[contador] = numeroDeMesa;
				seAgrego = true;
			}
			contador++;
		}

	}

	@Override
	public String toString() {
		return "Camarero " + nombre + ", propina " + propina + "$";
	}

}
