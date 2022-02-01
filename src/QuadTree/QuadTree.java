package QuadTree;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class QuadTree {

	ArrayList<Particulas> part = new ArrayList<Particulas>();
	QuadTree[] subQ = new QuadTree[4];
	Rect[] subR = new Rect[4];
	Rect front;
	boolean divisao = false;
	int l;

	public QuadTree(Rect Front, int limit) {
		this.front = Front;
		this.l = limit;
	}

	public void acao(Particulas p) {
		if (this.front.w > 4 && this.front.h > 4) {
			if (part.size() < l) {
				part.add(p);
				p.particles = part;
			} else {
				if (!divisao) {
				subR[0] = new Rect(front.x, front.y, front.w / 2, front.h / 2);
				subR[1] = new Rect(front.x + front.w / 2, front.y, front.w / 2, front.h / 2);
				subR[2] = new Rect(front.x + front.w / 2, front.y + front.h / 2, front.w / 2, front.h / 2);
				subR[3] = new Rect(front.x, front.y + front.h / 2, front.w / 2, front.h / 2);
				subQ[0] = new QuadTree(subR[0], l);
				subQ[1] = new QuadTree(subR[1], l);
				subQ[2] = new QuadTree(subR[2], l);
				subQ[3] = new QuadTree(subR[3], l);
				divisao = true;
				}
				if (subQ[0].front.Contains(p)) {
					subQ[0].acao(p);
					for (Particulas pt : part)
						p.particles.add(pt);
				}
				if (subQ[1].front.Contains(p)) {
					subQ[1].acao(p);
					for (Particulas pt : part)
						p.particles.add(pt);
				}
				if (subQ[2].front.Contains(p)) {
					subQ[2].acao(p);
					for (Particulas pt : part)
						p.particles.add(pt);
				}
				if (subQ[3].front.Contains(p)) {
					subQ[3].acao(p);
					for (Particulas pt : part)
						p.particles.add(pt);
				}
			}
		}
	}

	public void Draw(Graphics g) {
		g.setColor(Color.white);
		g.drawRect((int) front.x, (int) front.y, (int) front.w, (int) front.h);
		for (QuadTree q : subQ)
			if (q != null)
				q.Draw(g);
	}

}