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

	static LeitorPedidosJson leitorPedidos = new LeitorPedidosJson("../resources/orders_of_SIMU-i180-o6-r250-dHT03-d52-1.1.json");

	public static void main(String[] args) throws InterruptedException {
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PedidoSerializer.class.getName());

		/*
		 * Orders were generated each 1 minute, and the number of orders followed a gamma distribution with parameters α = 6.34
		 * and β = 1.0 (more or less six orders every minute).
		 */
		double shape = 6.34;
		double scale = 1.0;

		Thread.sleep(10000);

		// Inicio envio de pedidos
		try (KafkaProducer<String, Pedido> producer = new KafkaProducer<String, Pedido>(properties)) {
			while (true) {

				double sample = new GammaDistribution(shape, scale).sample();

				double n_pedidos = FuncoesUteis.round(sample, 0);
				long tempo_sleep = (long) FuncoesUteis.round((60.0 / n_pedidos), 0);

				System.out.println("--> " + n_pedidos + " pedidos = 1 pedido a cada " + tempo_sleep + " segundos.");

				// Cada volta do for representa mais ou menos 60 segundos;
				for (int i = 0; i <= n_pedidos; i++) {

					// get proximo pedido do arquivo
					Pedido pedido = leitorPedidos.getProximoPedido();

					ProducerRecord<String, Pedido> record = new ProducerRecord<String, Pedido>("topico-pedidos", pedido);
					producer.send(record);
					System.out.println(record.value());

					Thread.sleep(tempo_sleep * 1000);

				}
			}
		}
	}

}
