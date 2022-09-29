package model;

import java.util.ArrayList;

public class Pedido {

	private Long id;

	private ArrayList<Produto> produtos;

	public Pedido() {}

	public Pedido(Long id, ArrayList<Produto> produtos) {
		super();
		this.id = id;
		this.produtos = produtos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(ArrayList<Produto> produtos) {
		this.produtos = produtos;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", produtos=" + produtos + "]";
	}

}
