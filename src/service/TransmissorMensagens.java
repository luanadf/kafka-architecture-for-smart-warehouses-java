package service;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import model.MensagemFinalizacao;
import serializer.MensagemFinalizacaoSerializer;

public class TransmissorMensagens {

	private Properties properties;
	private KafkaProducer<String, MensagemFinalizacao> producer;

	public TransmissorMensagens() {
		super();
		this.properties = criarPropriedades();
	}

	// Inicia o producer
	public void start() {
		this.producer = new KafkaProducer<String, MensagemFinalizacao>(getProperties());
	}

	// Fecha a conexão do producer
	public void stop() {
		this.producer.close();
	}

	// Faz o envio de uma mensagem para o topico de monitoramento
	public void transmitirMensagem(MensagemFinalizacao mensagem) {
		ProducerRecord<String, MensagemFinalizacao> record = new ProducerRecord<String, MensagemFinalizacao>("topico-monitoramento", mensagem);
		producer.send(record);
	}

	// Cria as propriedades do kafka-producer
	private static Properties criarPropriedades() {
		Properties properties = new Properties();

		// Executando com cluster local
		// - 1 broker
		// properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

		// Executando com cluster em um container do docker
		// - 1 broker
		// properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.17.0.3:9092");

		// - 3 brokers
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Arrays.asList("172.17.0.3:9092", "172.17.0.4:9093", "172.17.0.5:9094"));

		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MensagemFinalizacaoSerializer.class.getName());

		// ACKS
		properties.put(ProducerConfig.ACKS_CONFIG, "0");

		return properties;
	}

	public Properties getProperties() {
		return properties;
	}

}
