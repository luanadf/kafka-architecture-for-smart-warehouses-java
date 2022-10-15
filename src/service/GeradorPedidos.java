package service;

import java.util.Properties;

import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import components.LeitorPedidosJson;
import model.Pedido;
import serializer.PedidoSerializer;
import utils.FuncoesUteis;

public class GeradorPedidos {

	static LeitorPedidosJson leitorPedidos = new LeitorPedidosJson("SIMU-i180-o6-r250-dHT03-d52-1.1.json");

	public static void main(String[] args) throws InterruptedException {
		Properties properties = new Properties();
		// properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // local
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.17.0.3:9092"); // docker container
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PedidoSerializer.class.getName());

		/*
		 * Orders were generated each 1 minute, and the number of orders followed a gamma distribution with parameters α = 6.34
		 * and β = 1.0 (more or less six orders every minute).
		 */
		double shape = 6.34;
		double scale = 1.0;

		int n_pedidos_enviados = 0;
		boolean producerIsClosed = false;

		// Thread.sleep(20000);

		// Inicio envio de pedidos
		try (KafkaProducer<String, Pedido> producer = new KafkaProducer<String, Pedido>(properties)) {
			while (true) {

				double sample = new GammaDistribution(shape, scale).sample();

				double n_pedidos = FuncoesUteis.round(sample, 0);
				long tempo_sleep = (long) FuncoesUteis.round((60.0 / n_pedidos), 0);

				// Cada volta do for representa mais ou menos 60 segundos;
				for (int i = 0; i <= n_pedidos; i++) {

					// Pega o proximo pedido do arquivo
					Pedido pedido = leitorPedidos.getProximoPedido();

					// Confere se existe um próximo pedido
					if (pedido == null) {
						System.out.println("\nEnvio de pedidos finaliado");
						System.out.println(" - Foram enviados " + n_pedidos_enviados + " pedidos.");
						producer.close();
						producerIsClosed = true;
						break;
					} else {
						ProducerRecord<String, Pedido> record = new ProducerRecord<String, Pedido>("topico-pedidos", pedido);
						producer.send(record);
						n_pedidos_enviados++;
						System.out.println(record.value());

						// Execução normal: 6 pedidos por minuto = 1 peido a cada 10 segundos
						// Thread.sleep(tempo_sleep * 1000);

						// Execução 10x mais rápida para testes: 6 peidos a cada 6 segundos = 1 pedido por segundo
						// Thread.sleep(tempo_sleep * 100);

						// Execução 100x mais rápida para testes: 6 peidos a cada 0,6 segundos = 1 pedido por 0,1 segundo
						Thread.sleep(tempo_sleep * 10);
					}
				}

				// Confere se o producer foi fechado
				if (producerIsClosed) {
					break;
				}

			}
		}
	}

}
