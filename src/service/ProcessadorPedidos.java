package service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import deserializer.PedidoDeserializer;
import model.MensagemFinalizacao;
import model.Pedido;

public class ProcessadorPedidos {

	private static Random rand = new Random();

	public static void main(String[] args) throws InterruptedException {

		Properties properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PedidoDeserializer.class.getName());
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "grupo-consumidor");
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		try (KafkaConsumer<String, Pedido> consumer = new KafkaConsumer<String, Pedido>(properties)) {
			consumer.subscribe(Arrays.asList("topico-pedidos"));

			while (true) {
				// Faz o pool das mensagens a cada 1 segundo: Duration.ofMillis(1000)
				ConsumerRecords<String, Pedido> pedidos = consumer.poll(Duration.ofMillis(1000));

				// Para cada mensagem que ele recebeu durante o pool
				for (ConsumerRecord<String, Pedido> record : pedidos) {
					Pedido pedido = record.value();

					// Chama uma simulacao para cada pedido
					MensagemFinalizacao mensagem = basic_linear_simulation(pedido);

					// Printa a mensagem de retorno da simulação
					System.out.println(mensagem);

					// TODO enviar essa mensagem para o topico de monitoramento
					// Envio da mensagem para o topico de monitoramento
					TransmissorMensagens transmissor = new TransmissorMensagens();
					transmissor.transmitirMensagem(mensagem);

				}
			}
		}
	}

	// Primeira função de simulacao (executa linearmente com um sleep de 1-5 segundos)
	private static MensagemFinalizacao basic_linear_simulation(Pedido pedido) throws InterruptedException {

		// TODO pegar o tempo do pedido do arquivo baseado no id do pedido e colocar no sleep

		int time = rand.nextInt(5000);
		Thread.sleep(time);

		MensagemFinalizacao mensagem = new MensagemFinalizacao(pedido.getId(), time);

		// return "Pedido " + pedido.getId() + " entregue em " + time + "ms";
		return mensagem;
	}

}
