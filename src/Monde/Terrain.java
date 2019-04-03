package Monde;

import Mobs.M;
import Mobs.M1;
import Mobs.M2;

import java.awt.image.ImageObserver;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;


import javax.imageio.ImageIO;

public class Terrain {
	private static int[][][] terrain;
	private int dx;
	private int dy;
	private  BufferedImage image;
	private static boolean pluie=false;
	
	public Terrain(int dx,int dy) {
		try {
			image = ImageIO.read(new File("Bruit_de_Perlin220x220.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dx=dx;
		this.dy=dy;
		terrain = new int[dy][dx][3];
		int taille_y=((int) (Math.random()*(Math.max(0,image.getHeight()-dy))));
		int taille_x=((int) (Math.random()*(Math.max(0,image.getHeight()-dx))));
		taille_x=0;
		taille_y=0;
		for (int i=taille_y;i<taille_y+dy;i++) {
			for (int j=taille_x;j<taille_x+dx;j++) {
				Color c = new Color(image.getRGB(i, j));
				int couleur = c.getRed();
				terrain[i-taille_y][j-taille_x][0]=couleur; //Altitude
				terrain[i-taille_y][j-taille_x][1]=couleur; //Type de terrain(biome)
				terrain[i-taille_y][j-taille_x][2]=0; //Si il y a un arbre ou pas
				
				System.out.print(" "+ terrain[i-taille_y][j-taille_x][1]);
			}
			System.out.println("");
		}
	}
	
	public static int[][][] getTerrain() {
		return terrain;
	}
	public void eruption() {
		for(int i = 0; i < dy ; i++) {
			for(int j = 0; j < dx; j++) {
				if(this.getTerrain()[i][j][0] >=(this.sommetVolcan() - 1)) { //Pour un haut altitude
					this.getTerrain()[i][j][1] =  this.SolLave(); //Pour debuter ecoulement de lave 
				}	
			}
		}
	}
	
	public void propagation_lave() {
		for(int i = 0; i < dy ; i++) {
			for(int j = 0; j < dx; j++) {
				if (((this.getTerrain()[i][j][0] <= this.getTerrain()[(i-1+dy)%dy][j][0] && (this.getTerrain()[(i-1+dy)%dy][j][1] == this.SolLave())) 
				|| (this.getTerrain()[i][j][0] <= this.getTerrain()[(i+1+dy)%dy][j][0] && (this.getTerrain()[(i+1+dy)%dy][j][1] == this.SolLave())
				|| (this.getTerrain()[i][j][0] <= this.getTerrain()[i][(j+1+dx)%dx][0] && (this.getTerrain()[i][(j+1+dx)%dx][1] == this.SolLave()))
				|| (this.getTerrain()[i][j][0] <= this.getTerrain()[i][(j-1+dx)%dx][0] && (this.getTerrain()[i][(j-1+dx)%dx][1] == this.SolLave()))))) { //si l'altitude est moins élevé
					
					if(Math.random() <= 0.1) {
						this.getTerrain()[i][j][1] = this.SolLave(); //coulé de lave
					}
				}
			}
		}
	}
	public void partir_lave() {
		for(int i = 0; i < dy ; i++) {
			for(int j = 0; j < dx; j++) {
				if(this.getTerrain()[i][j][1] == this.SolLave() && this.getTerrain()[i][j][0] <= this.contourRoche()) {
					this.getTerrain()[i][j][1]=this.getTerre(); //devient de la terre
				}
				else {
					if(this.getTerrain()[i][j][1] == 255 && this.getTerrain()[i][j][0] >= this.contourRoche()) {	
						this.getTerrain()[i][j][1]=this.getTerrain()[i][j][0]; //la lave redevient solide
					}
				}
			}
		}
	}
	public void MonteEau(boolean pluie) {  //L'eau monte lorsqu'il pleut
		if(pluie) {
			for(int i = 0; i < dy ; i++) {
				for(int j = 0; j < dx; j++) {
					if((this.getTerrain()[i][j][1] < this.contourRoche()) && ((this.getTerrain()[(i+1+dy)%dy][j][1]<this.getEau()) 
							|| this.getTerrain()[(i-1+dy)%dy][j][1]<this.getEau() 
							|| this.getTerrain()[i][(j+1+dx)%dx][1]<this.getEau() 
							|| this.getTerrain()[i][(j-1+dx)%dx][1]<this.getEau()) && (this.getTerrain()[i][j][1]>=this.getEau())) {
						if(Math.random() < 0.2) {
							this.getTerrain()[i][j][1]=this.getTerrain()[i][j][1]-5;
						}
					}
				}
			}
		}
	}
	public void evaporeLave(boolean pluie) { //La lave s'evapore lorsqu'il pleut
		if(pluie) {
			for(int i = 0; i < dy ; i++) {
				for(int j = 0; j < dx; j++) {
					if((this.getTerrain()[i][j][1] == this.SolLave()) && ((this.getTerrain()[(i+1+dy)%dy][j][1]!=this.SolLave()) 
							|| this.getTerrain()[(i-1+dy)%dy][j][1]!=this.SolLave() 
							|| this.getTerrain()[i][(j+1+dx)%dx][1]!=this.SolLave() 
							|| this.getTerrain()[i][(j-1+dx)%dx][1]!=this.SolLave())) {
						if(Math.random() < 0.05) {
							this.getTerrain()[i][j][1]=this.getTerrain()[i][j][0]; 
						}
					}
				}
			}
		}
	}
	public void herbePoussant(){
		for(int i = 0; i < dy ; i++) {
			for(int j = 0; j < dx; j++) {
				if(this.getTerrain()[i][j][1] >= this.getTerre() - 2 && this.getTerrain()[i][j][1] <= this.getTerre() + 2) {
					if(Math.random() < 0.05) {
						this.getTerrain()[i][j][1]=grass(); 
					}
				}
			}
		}
	}
	public static boolean getPluie() {
		return pluie;
	}
	public void setPluie(boolean pluie) {
		this.pluie=pluie;
	}
	public static int getTerre() {
		return 207;
	}
	public static int getEau() {
		return 205;
	}
	public static int contourRoche() {
		return 239;
	}
	public static int sommetVolcan() {
		return 248;
	}
	public static int SolLave() {
		return 255;
	}
	public static int grass(){
		return 220;
	}
}