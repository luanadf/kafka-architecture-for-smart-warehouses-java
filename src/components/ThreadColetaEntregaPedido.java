package components;

import java.math.BigDecimal;

import model.MensagemFinalizacao;
import model.Pedido;
import service.TransmissorMensagens;
import utils.FuncoesUteis;

public class ThreadColetaEntregaPedido extends Thread {

	private Pedido pedido;
	private BigDecimal tempoPedido;
	private TransmissorMensagens transmissor;

	public ThreadColetaEntregaPedido(Pedido pedido, BigDecimal tempoPedido, TransmissorMensagens transmissor) {
		super();
		this.pedido = pedido;
		this.tempoPedido = tempoPedido;
		this.transmissor = transmissor;
		start();
	}

	@Override
	public void run() {
		Pedido pedido = getPedido();
		BigDecimal tempo = getTempoPedido();

		try {
			// Thread faz o sleep pelo tempo que o pedido leva para ser finalizado
			long tempoPedidoEmMilisegundos = FuncoesUteis.bigDecimalSegundosToLongMilisegundos(tempo);

			// Execução no tempo normal
			// Thread.sleep(tempoPedidoEmMilisegundos);

			// Execução 10x mais rapida para testes
			// Thread.sleep(tempoPedidoEmMilisegundos / 10);

			// Execução 100x mais rapida para testes
			// Thread.sleep(tempoPedidoEmMilisegundos / 100);

			// Execução 1000x mais rapida para testes
			Thread.sleep(tempoPedidoEmMilisegundos / 1000);

			// Cria a e envia a mensagem de finalização do pedido para o transmissor
			MensagemFinalizacao mensagem = new MensagemFinalizacao(pedido.getIdPedido(), tempo);
			transmissor.transmitirMensagem(mensagem);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Pedido getPedido() {
		return pedido;
	}

	public BigDecimal getTempoPedido() {
		return tempoPedido;
	}

}
