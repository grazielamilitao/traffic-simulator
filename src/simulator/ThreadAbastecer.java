package simulator;

public class ThreadAbastecer implements Runnable{

	static int combustivel;
	Car carro;
	int quantCombustivel;

	public ThreadAbastecer(Car carro, int quantidade) {
		this.carro = carro;
		quantCombustivel = quantidade;
	}

	synchronized void abastecerFabrica() {
		try {
			//abastecer a fabrica com 50L a cada 30s
			combustivel += 50;
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	synchronized public void run() {
		while(true) {
			try {
				//enquanto houver combustivel na Fabrica, abastece o carro da vez, mininui no estoque, move o carro e faz a thread esperar 5s notificando as outras
				while(combustivel>0) {
					carro.setCombustivel(carro.getCombustivel()+quantCombustivel);
					combustivel=-quantCombustivel;
					carro.mover();
					Thread.sleep(5000);
					notify();
				}
				abastecerFabrica();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
