package QuadTree;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Tela extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	public static Main main;
	public static int Width = 1920;
	public static int Height = 1080;

	public static int QntParticulas = 0;

	int Tipos = 0;

	public static long milissegundos = 0;

	private static ArrayList<Particulas> part = new ArrayList<Particulas>();

	public enum TiposDeColisões {
		MDNORMAL, MDQUADTREE
	};

	public static TiposDeColisões mode = TiposDeColisões.MDNORMAL;

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public QuadTree quad;

	public Tela(Main main) {
		Tela.main = main;
		setBackground(Color.DARK_GRAY);
		setPreferredSize(new Dimension(Width, Height));
		System.out.println("Quantos Particulas Você quer Simular?\n");

		try {
			QntParticulas = Integer.parseInt(br.readLine());
		} catch (NumberFormatException e) {
			System.out.println("Invalido... Digita apenas número!!!");
			System.exit(1);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Seleciona tipos de colisões Abaixo\n");
		System.out.println("\nDigite Número 1 para colisão normal\n");
		System.out.println("\nDigite Número 2 para colisão baseado ao QuadTree\n");

		try {
			Tipos = Integer.parseInt(br.readLine());
		} catch (NumberFormatException e) {
			System.out.println("Invalido... Digita apenas número!!!");
			System.exit(1);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Modo();

	}

	public void Modo() {

		if (Tipos == 1)
			mode = TiposDeColisões.MDNORMAL;
		if (Tipos == 2)
			mode = TiposDeColisões.MDQUADTREE;

		for (int i = 0; i < QntParticulas; i++) {
			Random r = new Random();
			float rx = r.nextFloat() * Width;
			float ry = r.nextFloat() * Height;
			part.add(new Particulas(rx, ry, Width, Height));
		}

		if (mode == TiposDeColisões.MDNORMAL)
			for (Particulas p : part)
				p.particles = part;

		Timer timer = new Timer(1, this);
		timer.setRepeats(true);
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawScene(g);
	}

	public void drawScene(Graphics g) {
		for (int i = 0; i < part.size(); i++) {
			part.get(i).Draw(g);
		}
		if (quad != null)
			quad.Draw(g);
	}

	public void run() {

		long start = System.currentTimeMillis();

		switch (mode) {
		case MDNORMAL:
			for (Particulas p : part)
				p.Execute();
			for (Particulas p : part)
				p.Check();
			break;
		case MDQUADTREE:
			quad = new QuadTree(new Rect(0, 0, Width, Height), 4);
			for (Particulas p : part)
				quad.acao(p);
			for (Particulas p : part)
				p.Execute();
			for (Particulas p : part)
				p.Check();
			for (Particulas p : part)
				p.particles = new ArrayList<Particulas>();
			break;
		}

		repaint();

		long totalTime = System.currentTimeMillis() - start;
		milissegundos = totalTime;

		main.setTitle("Obj[Quadtree] Particulas: " + part.size() + " - " + totalTime + " Milisegundos ");
		Loop: while (totalTime != 0) {
			System.out.println(totalTime);
			break Loop;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		run();
	}

}
