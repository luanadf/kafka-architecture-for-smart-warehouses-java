package model;

import java.util.ArrayList;

public class Pedido {

	private Integer id_pedido;

	private ArrayList<Produto> produtos;

	public Pedido() {}

	public Pedido(Integer id_pedido, ArrayList<Produto> produtos) {
		super();
		this.id_pedido = id_pedido;
		this.produtos = produtos;
	}

	public Integer getIdPedido() {
		return id_pedido;
	}

	public void setIdPedido(Integer id_pedido) {
		this.id_pedido = id_pedido;
	}

	public ArrayList<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(ArrayList<Produto> produtos) {
		this.produtos = produtos;
	}

	@Override
	public String toString() {
		return "Pedido [id_pedido=" + id_pedido + ", produtos=" + produtos + "]";
	}

}
