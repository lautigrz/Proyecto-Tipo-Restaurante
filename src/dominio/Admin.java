package dominio;

public class Admin {

	private String usuario;
	private String contrasenia;
	private Camarero[] camarero = new Camarero[10];

	public Admin(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public boolean validarContrasenia(String contraseniaIngresada) {
		return contrasenia.equals(contraseniaIngresada);
	}

	public boolean contratarCamarero(Camarero camarero) {
		boolean seContrato = false;
		int contador = 0;
		while (!seContrato && contador < this.camarero.length) {
			if (this.camarero[contador] == null) {
				this.camarero[contador] = camarero;
				seContrato = true;
			}
			contador++;
		}
		return seContrato;

	}

	public boolean despedirCamarero(String nombre) {
		boolean seDespidio = false;
		int contador = 0;
		while (!seDespidio && contador < this.camarero.length) {
			if (this.camarero[contador] != null && this.camarero[contador].getNombre().equalsIgnoreCase(nombre)) {
				this.camarero[contador] = null;
				seDespidio = true;
			}
			contador++;
		}
		return seDespidio;
	}
	
	

	public Camarero[] getCamarero() {
		return camarero;
	}

	public void setCamarero(Camarero[] camarero) {
		this.camarero = camarero;
	}

	public Camarero[] camareros() {
		Camarero[] camareros = new Camarero[this.camarero.length];

		for (int i = 0; i < this.camarero.length; i++) {
			if (this.camarero[i] != null) {
				camareros[i] = this.camarero[i];
			}
		}

		return camareros;
	}

	public Camarero obtenerCamarero() {

		Camarero camarero = null;

		boolean seObtuvoCamarero = false;

		while (!seObtuvoCamarero) {
			int numRandom = (int) (Math.random() * this.camarero.length);
			camarero = this.camarero[numRandom];
			if (camarero != null) {
				seObtuvoCamarero = true;
			}
		}

		return camarero;
	}

}
