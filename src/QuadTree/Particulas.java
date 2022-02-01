package QuadTree;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Particulas {
	float px, py, lx, ly;
	float X, Y;
    int STATE;
	int Aviso = 1;
	boolean Colidindo = false;
	int TempoDColisao;

	ArrayList<Particulas> particles = new ArrayList<Particulas>();

	Color color = Color.blue;

	public Particulas(float px, float py, float lx, float ly) {
		this.px = px;
		this.py = py;
		this.lx = lx;
		this.ly = ly;
		this.X = px;
		this.Y = py;
	}

	public void Draw(Graphics g) {
		g.setColor(this.color);
		g.fillOval((int) this.px, (int) this.py, 10, 10);
	}

	public void Execute() {
		TempoDColisao = 0;
	
		if (Colidindo == true) {
			color = Color.green;
			TempoDColisao--;
			if (TempoDColisao <= 0)
				Colidindo = false;
		} else {
			color = Color.blue;
		}

		switch (STATE) {
		case 1:
			px++;
			break;
		case 2:
			px--;
			break;
		case 3:
			py++;
			break;
		case 4:
			py--;
			break;
		case 5:
			px++;
			py++;
			break;
		case 6:
			px--;
			py--;
			break;
		case 7:
			px++;
			py--;
			break;
		case 8:
			px--;
			py++;
			break;
		default:
			break;
		}

		this.Aviso++;
		if (Aviso > 100) {
			Random r = new Random();
		this.STATE = r.nextInt(9);
			Aviso = 0;
		}

		if (this.px < 0)
			this.px = this.ly;
		if (this.py < 0)
			this.py = this.ly;
		if (this.px > this.lx)
			this.px = 0;
		if (this.py > this.ly)
			this.py = 0;

		X = px;
		Y = py;
	}

	public void Check() {
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i) != this) {
				Particulas Out = particles.get(i);
				if (px <= Out.px + 5 && px + 5 >= Out.px && py <= Out.py + 5 && py + 5 >= Out.py) {
					Colidindo = true;
					TempoDColisao = 8;
					Out.Colidindo = true;
					Out.TempoDColisao = 8;
				}
			}
		}
	}

	public void Collide() {
		px = X;
		py = Y;
		return;
	}

}
