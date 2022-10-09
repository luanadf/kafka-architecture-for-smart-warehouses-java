package model;

import java.math.BigDecimal;

public class MensagemFinalizacao {

	// TODO Adaptar para modelo de mensagem de retorno do George
	private Integer idPedido;
	private BigDecimal tempoTotalEntrega;

	public MensagemFinalizacao() {}

	public MensagemFinalizacao(Integer idPedido, BigDecimal tempoTotalEntrega) {
		super();
		this.idPedido = idPedido;
		this.tempoTotalEntrega = tempoTotalEntrega;
	}

	public Integer getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}

	public BigDecimal getTempo_total_entrega() {
		return tempoTotalEntrega;
	}

	public void setTempo_total_entrega(BigDecimal tempoTotalEntrega) {
		this.tempoTotalEntrega = tempoTotalEntrega;
	}

	@Override
	public String toString() {
		return "MensagemFinalizacao [idPedido=" + idPedido + ", tempoTotalEntrega=" + tempoTotalEntrega + "]";
	}

}
