package serializer;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.MensagemFinalizacao;

public class MensagemFinalizacaoSerializer implements Serializer<MensagemFinalizacao> {

	@Override
	public byte[] serialize(String topic, MensagemFinalizacao mensagem) {
		try {
			return new ObjectMapper().writeValueAsBytes(mensagem);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}

}
