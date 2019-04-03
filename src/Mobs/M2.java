package Mobs;

import java.util.ArrayList;

import Monde.Monde;
import Monde.Terrain;

public class M2 extends M{

	public M2(int x, int y) {
		super(x, y);
		atk=(int) (Math.random()*100);
		pv=500;
		S="0";
	}

	public String getS() {
		return S;
	}
	
	public void move(int dx, int dy) {
		if(!this.getMort()) {
			if (this.sens == 0) {
				this.x=(this.x-1+dx)%dx;
			}
			if (this.sens == 1) {
				this.x=(this.x+1+dx)%dx;
			}
			if (this.sens == 2) {
				this.y=(this.y+1+dy)%dy;
			}
			if (this.sens == 3) {
				this.y=(this.y-1+dy)%dy;
			}
			for (int m=  0; m < Monde.getcarte_P().size();m++) {
				if (Monde.getcarte_P().get(m) instanceof Pomme && ((Pomme) Monde.getcarte_P().get(m)).getX() == this.x && ((Pomme) Monde.getcarte_P().get(m)).getY() == this.y) {
					manger_pomme((Pomme) Monde.getcarte_P().get(m));
				}
			}
		}
	}
	
	public void evoluer() {
		if(nb_pomme_manger >= 30 && evolution == false) {
			evolution = true;
		}
	}
	public void manger_pomme(Pomme apple) {
		for(int i = 0; i < Monde.getcarte_P().size(); i++) {
				if(Monde.getcarte_P().get(i).equals(apple)){
					if (apple.isEstPourrie()) 
						nb_pomme_manger += 1 ;
					else 
						nb_pomme_manger += 2 ;
					Monde.getcarte_P().remove(i);
					evoluer();
					return ;
			}
		}
	}
	public void setSens() {
		int cpt=0;
		int tab_A[] = new int[4];
		for (int i=0;i<Monde.getcarte_Ab().size();i++) {
			
			if (Monde.getcarte_Ab().get(i).getX()==this.x-1 && Monde.getcarte_Ab().get(i).getY()==this.y) {
				tab_A[0]=i;
				cpt+=1;
			}
			if (Monde.getcarte_Ab().get(i).getX()==this.x+1 && Monde.getcarte_Ab().get(i).getY()==this.y) {
				tab_A[1]=i;
				cpt+=1;
			}
			if (Monde.getcarte_Ab().get(i).getX()==this.x && Monde.getcarte_Ab().get(i).getY()==this.y+1) {
				tab_A[2]=i;
				cpt+=1;
			}
			if (Monde.getcarte_Ab().get(i).getX()==this.x && Monde.getcarte_Ab().get(i).getY()==this.y-1) {
				tab_A[3]=i;
				cpt+=1;
			}
		}
		if (cpt == 4) {
			this.setMort(true);
			System.out.println("R");
			System.out.println(""+Monde.getcarte_Ag().size());
			return ;
		}
		
		for(int m=  0; m < Monde.getcarte_P().size();m++) {
			if(((Pomme) Monde.getcarte_P().get(m)).getX() == this.x-1 && ((Pomme) Monde.getcarte_P().get(m)).getY() == this.y) {
					this.sens = 0;
					return ;
				}
				if(((Pomme) Monde.getcarte_P().get(m)).getX() == this.x+1 && ((Pomme) Monde.getcarte_P().get(m)).getY() == this.y) {
					this.sens = 1;
					return ;
				}
				if(((Pomme) Monde.getcarte_P().get(m)).getX() == this.x && ((Pomme) Monde.getcarte_P().get(m)).getY() == this.y+1) {
					this.sens = 2;
					return ;
				}
				if(((Pomme) Monde.getcarte_P().get(m)).getX() == this.x && ((Pomme) Monde.getcarte_P().get(m)).getY() == this.y-1) {
					this.sens = 3;
					return ;
				}
			
		}
		int x1,y1;
		boolean boolA=false;
		do {
			boolA=false;
			x1=this.x;
			y1=this.y;
			this.sens = (int)(Math.random()*4);
			if (this.sens == 0) {
				x1=(x1-1+Monde.getDx())%Monde.getDx();
				if(Terrain.getTerrain()[y1][x1][1] >= Terrain.contourRoche()) {
					this.sens=4;
					return;
				}
			}
			if (this.sens == 1) {
				x1=(x1+1+Monde.getDx())%Monde.getDx();
				if(Terrain.getTerrain()[y1][x1][1] >= Terrain.contourRoche()) {
					this.sens=5;
					return;
				}
			}
			if (this.sens == 2) {
				y1=(y1+1+Monde.getDy())%Monde.getDy();
				if(Terrain.getTerrain()[y1][x1][1] >= Terrain.contourRoche()) {
					this.sens=6;
					return;
				}
			}
			if (this.sens == 3) {
				y1=(y1-1+Monde.getDy())%Monde.getDy();
				if(Terrain.getTerrain()[y1][x1][1] >= Terrain.contourRoche()) {
					this.sens=7;
					return;
				}
			}
			for (int i=0;i<Monde.getcarte_Ab().size();i++) {
				if ((Monde.getcarte_Ab().get(i).getX() == x1 && Monde.getcarte_Ab().get(i).getY() == y1)) {
					boolA=true;
					break;
				}
			}
		}while (boolA);
	}
	
	public int getSens() {
		return this.sens;
	}
	
}