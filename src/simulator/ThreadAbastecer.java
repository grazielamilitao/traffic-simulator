package simulator;

public class ThreadAbastecer implements Runnable{

	static int combustivel;
	boolean trafego = true;
	Car carro;
	int quantCombustivel;

	public ThreadAbastecer(Car carro, int quantidade) {
		this.carro = carro;
		quantCombustivel = quantidade;
	}

	synchronized void abastecerFabrica() {
		while(trafego) {
			try {
				combustivel += 50;
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	synchronized public void run() {
		if(combustivel>0) {
			while(combustivel>0) {
				try {
					if(quantCombustivel<combustivel) {
						carro.setCombustivel(carro.getCombustivel()+quantCombustivel);
						combustivel=-quantCombustivel;
						carro.mover();
					}
					Thread.sleep(5000);
					notify();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		else if(combustivel<=0) {
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		abastecerFabrica();
	}

}
