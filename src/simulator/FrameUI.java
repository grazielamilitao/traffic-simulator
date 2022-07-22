package simulator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.*;

class FrameUI extends JFrame {

	private JPanel jPanel2;

	public FrameUI() {
		initComponents();

	}

	private void initComponents() {
		jPanel2 = new Panel2();
		jPanel2.setBackground(new java.awt.Color(255, 255, 255));
		jPanel2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.setContentPane(jPanel2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}

	public static void main(String args[]) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new FrameUI().setVisible(true);
				
			}
		});
	}

	class Panel2 extends JPanel {

		private List<Car> carros;

		Panel2() {
			setPreferredSize(new Dimension(800, 600));
			iniciarCarros();
		}

		private Color defineColor() {
			Random rand = new Random();
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			return new Color(r, g, b);
		}

		private void iniciarCarros() {

			Random rand = new Random();

			//Cria 10 novos carros
			carros = new ArrayList<Car>();
			for (int i = 0; i < 10; i++) {
				Car c = new Car(rand.nextInt(800), rand.nextInt(600), rand.nextInt(50), defineColor());
				c.mover();
				
				//criando e startando o novo objeto runnable
				new Thread(new ThreadAbastecer(c,10)).start();

				carros.add(c);
			}

			//desenhar os carros
			new Thread(new Runnable(){
				@Override
				public void run() {
					while(true) {
						FrameUI.this.repaint();
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();

			carros.forEach(c -> c.mover());
			Panel2.this.repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			for (Car c : carros) {
				c.draw(g);
			}
		}
	}
}
