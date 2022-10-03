package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.math3.distribution.GammaDistribution;

public class TesteGamma {

	public static void main(String[] args) throws InterruptedException {
		/*
		 * Orders were generated each 1 minute, and the number of orders followed a gamma distribution with parameters α = 6.34
		 * and β = 1.0 (more or less six orders every minute).
		 */
		double shape = 6.34;
		double scale = 1.0;

		while (true) {
			double sample = new GammaDistribution(shape, scale).sample();

			double n_pedidos = round(sample, 0);

			double segundos_por_pedido = 60.0 / n_pedidos;

			double x = round(segundos_por_pedido, 0);

			System.out.println(n_pedidos + " pedidos = 1 pedido a cada: " + (int) x + " segundos");

			System.out.println("em millisegundos: " + (int) x * 1000);

			Thread.sleep(1000);
		}
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}
