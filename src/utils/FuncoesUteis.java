package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FuncoesUteis {

	// Arredonda valores 'double'
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	// Converte tempo BigDecimal em segundos para long millisegundos
	public static long bigDecimalSegundosToLongMilisegundos(BigDecimal tempoBigDecimal) {
		BigDecimal tempoMilisegundos = tempoBigDecimal.multiply(new BigDecimal(1000));
		return tempoMilisegundos.longValue();
	}

}
