package model;

public class MensagemFinalizacao {

	// TODO Adaptar para modelo de mensagem de retorno do George
	private Long id_pedido;
	private Integer tempo_total_entrega;

	public MensagemFinalizacao() {}

	public MensagemFinalizacao(Long id_pedido, Integer tempo_total_entrega) {
		super();
		this.id_pedido = id_pedido;
		this.tempo_total_entrega = tempo_total_entrega;
	}

	public Long getId() {
		return id_pedido;
	}

	public void setId(Long id) {
		this.id_pedido = id;
	}

	public Integer getTempo_total_entrega() {
		return tempo_total_entrega;
	}

	public void setTempo_total_entrega(Integer tempo_total_entrega) {
		this.tempo_total_entrega = tempo_total_entrega;
	}

	@Override
	public String toString() {
		return "MensagemFinalizacao [id_pedido=" + id_pedido + ", tempo_total_entrega=" + tempo_total_entrega + "]";
	}

}
