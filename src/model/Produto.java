package model;

public class Produto {

	private Long id;
	private int coordenada_x;
	private int coordenada_y;
	private int altura_prateleira;
	private float peso;

	public Produto() {}

	public Produto(Long id, int coordenada_x, int coordenada_y, int altura_prateleira, float peso) {
		super();
		this.id = id;
		this.coordenada_x = coordenada_x;
		this.coordenada_y = coordenada_y;
		this.altura_prateleira = altura_prateleira;
		this.peso = peso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCoordenada_x() {
		return coordenada_x;
	}

	public void setCoordenada_x(int coordenada_x) {
		this.coordenada_x = coordenada_x;
	}

	public int getCoordenada_y() {
		return coordenada_y;
	}

	public void setCoordenada_y(int coordenada_y) {
		this.coordenada_y = coordenada_y;
	}

	public int getAltura_prateleira() {
		return altura_prateleira;
	}

	public void setAltura_prateleira(int altura_prateleira) {
		this.altura_prateleira = altura_prateleira;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", coordenada_x=" + coordenada_x + ", coordenada_y=" + coordenada_y + ", altura_prateleira=" + altura_prateleira + ", peso=" + peso + "]";
	}

}
