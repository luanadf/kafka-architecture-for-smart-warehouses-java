package model;

import java.math.BigDecimal;

public class MensagemFinalizacao {

	// TODO Adaptar para modelo de mensagem de retorno do George
	private Integer id_pedido;
	private BigDecimal tempo_total_entrega;

	public MensagemFinalizacao() {}

	public MensagemFinalizacao(Integer id_pedido, BigDecimal tempo_total_entrega) {
		super();
		this.id_pedido = id_pedido;
		this.tempo_total_entrega = tempo_total_entrega;
	}

	public Integer getId() {
		return id_pedido;
	}

	public void setId(Integer id_pedido) {
		this.id_pedido = id_pedido;
	}

	public BigDecimal getTempo_total_entrega() {
		return tempo_total_entrega;
	}

	public void setTempo_total_entrega(BigDecimal tempo_total_entrega) {
		this.tempo_total_entrega = tempo_total_entrega;
	}

	@Override
	public String toString() {
		return "MensagemFinalizacao [id_pedido=" + id_pedido + ", tempo_total_entrega=" + tempo_total_entrega + "]";
	}

}
