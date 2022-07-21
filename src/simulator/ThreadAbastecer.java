package simulator;

public class ThreadAbastecer implements Runnable{

	static int combustivel;
	Car carro;
	int quantCombustivel;
	
	public ThreadAbastecer() {
		
	}

	public ThreadAbastecer(Car carro, int quantidade) {
		this.carro = carro;
		quantCombustivel = quantidade;
	}

	synchronized void abastecerFabrica() {
		while(true) {
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
		while(true) {
			try {
				while(combustivel>0) {
					System.out.println(combustivel);
					carro.setCombustivel(carro.getCombustivel()+quantCombustivel);
					combustivel=-quantCombustivel;
					carro.mover();
					Thread.sleep(5000);
					notify();
				}
				if(combustivel<=0) {
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				abastecerFabrica();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
