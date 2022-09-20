package model;

public class Pedido {

	// TODO Criar classe Produto e um lista de produtos
	private Long id;
	private Integer n_itens;
	private Integer warehouse_block;

	public Pedido(Long id, Integer n_items, Integer warehouse_block) {
		super();
		this.id = id;
		this.n_itens = n_items;
		this.warehouse_block = warehouse_block;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getN_items() {
		return n_itens;
	}

	public void setN_items(Integer n_items) {
		this.n_itens = n_items;
	}

	public Integer getWarehouse_block() {
		return warehouse_block;
	}

	public void setWarehouse_block(Integer warehouse_block) {
		this.warehouse_block = warehouse_block;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", n_itens=" + n_itens + ", warehouse_block=" + warehouse_block + "]";
	}

}
