package service;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import model.MensagemFinalizacao;
import serializer.MensagemFinalizacaoSerializer;

public class TransmissorMensagens {

	private Properties properties;

	public TransmissorMensagens() {
		super();
		this.properties = criarPropriedades();
	}

	// Faz o envio de uma mensagem (producer)
	public void transmitirMensagem(MensagemFinalizacao mensagem) {

		// Envia a mensagem para o topico de monitoramento
		try (KafkaProducer<String, MensagemFinalizacao> producer = new KafkaProducer<String, MensagemFinalizacao>(this.properties)) {
			ProducerRecord<String, MensagemFinalizacao> record = new ProducerRecord<String, MensagemFinalizacao>("topico-monitoramento", mensagem);
			System.out.println(record.value());
			producer.send(record);
		}

	}

	// Cria as propriedades do kafka producer
	public static Properties criarPropriedades() {
		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MensagemFinalizacaoSerializer.class.getName());

		return properties;
	}

}
