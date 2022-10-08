package model;

import java.util.ArrayList;

public class Pedido {

	private Integer id;

	private ArrayList<Produto> produtos;

	public Pedido() {}

	public Pedido(Integer id, ArrayList<Produto> produtos) {
		super();
		this.id = id;
		this.produtos = produtos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
