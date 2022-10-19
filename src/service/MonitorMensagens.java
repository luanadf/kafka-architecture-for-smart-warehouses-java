package service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import deserializer.MensagemFinalizacaoDeserializer;
import model.MensagemFinalizacao;

public class MonitorMensagens {

	public static void main(String[] args) throws InterruptedException {

		Properties propriedadesConsumidorMensagens = getPropriedadesConsumidorMensagens();

		int n_mensagens_recebidas = 0;

		try (KafkaConsumer<String, MensagemFinalizacao> consumer = new KafkaConsumer<String, MensagemFinalizacao>(propriedadesConsumidorMensagens)) {

			TopicPartition partitionToReadFrom = new TopicPartition("topico-monitoramento", 0);
			consumer.assign(Arrays.asList(partitionToReadFrom));
			consumer.seek(partitionToReadFrom, 0);

			while (true) {
				// TODO mudar aqui conforme mudar as velocidades
				// Faz o pool das mensagens a cada 0,1 segundo: Duration.ofMillis(100)
				ConsumerRecords<String, MensagemFinalizacao> mensagens = consumer.poll(Duration.ofMillis(100));

				// Para cada mensagem que ele recebeu durante o pool
				for (ConsumerRecord<String, MensagemFinalizacao> record : mensagens) {
					MensagemFinalizacao mensagemFinalizacao = record.value();
					n_mensagens_recebidas++;

					System.out.println("\n Pedido " + mensagemFinalizacao.getIdPedido() + " finalizado: " + mensagemFinalizacao);
					System.out.println(" - " + n_mensagens_recebidas + " mensagens de finalização recebidas.");
				}
			}
		}
	}

	// Cria as propriedades do kafka-consumer
	public static Properties getPropriedadesConsumidorMensagens() {
		Properties properties = new Properties();

		// Executando com cluster local
		// - 1 broker
		// properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

		// Executando com cluster em um container do docker
		// - 1 broker
		// properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.17.0.3:9092");

		// - 3 brokers
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Arrays.asList("172.17.0.3:9092", "172.17.0.4:9093", "172.17.0.5:9094"));

		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MensagemFinalizacaoDeserializer.class.getName());
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "grupo-consumidor-mensagens");
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		return properties;
	}

}
