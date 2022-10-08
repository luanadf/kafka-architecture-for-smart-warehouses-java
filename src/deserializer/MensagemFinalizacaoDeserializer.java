package deserializer;

import java.io.IOException;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.MensagemFinalizacao;

public class MensagemFinalizacaoDeserializer implements Deserializer<MensagemFinalizacao> {

	@Override
	public MensagemFinalizacao deserialize(String topic, byte[] mensagem) {
		try {
			return new ObjectMapper().readValue(mensagem, MensagemFinalizacao.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
