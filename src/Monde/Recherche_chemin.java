package Monde;

public class Recherche_chemin {
	private int [][] terrain;
	private int dx;
	private int dy;
	private int x_depart;
	private int y_depart;
	private int x_arrivé;
	private int y_arrivé;
	
	public Recherche_chemin(int dx,int dy, int x1,int y1, int x2, int y2) {
		terrain = new int[dy][dx];
		this.dx=dx;
		this.dy=dy;
		x_depart=x1;
		y_depart=y1;
		x_arrivé=x2;
		y_arrivé=y2;
		for (int i=0;i<dy;i++) {
			for (int j=0;j<dx;j++) {
				if (Terrain.getTerrain()[i][j][2] == 1) {
					terrain[i][j]=-Integer.MIN_VALUE;
				}else {
					terrain[i][j]=0;					
				}
			}
			terrain[y_depart][x_depart]=1;
		}
//		System.out.println("|");
//		for (int i=0;i<dy;i++) {
//			for (int j=0;j<dx;j++) {
//				if (terrain[i][j]==Integer.MIN_VALUE) {
//					System.out.print("M ");
//					continue;
//				}
//				System.out.print(""+terrain[i][j]+" ");
//			}
//			System.out.println("");
//		}
//		System.out.println("|");
	}
	public void afficher() {
		System.out.println("");
		for (int i=0;i<dy;i++) {
			for (int j=0;j<dx;j++) {
				if (i==y_arrivé && j==x_arrivé) {
					System.out.print("A  ");
					continue;
				}
				if (i==y_depart && j==x_depart) {
					System.out.print("D  ");
					continue;
				}
				if (terrain[i][j]==-1) {
					System.out.print("O  ");
					continue;
				}
				if (terrain[i][j]==Integer.MIN_VALUE) {
					System.out.print("M  ");
					continue;
				}
				if (terrain[i][j]>=0)
					System.out.print(""+terrain[i][j]+"  ");
				else
					System.out.print(""+terrain[i][j]+" ");
				
			}
			System.out.println("");
		}		
	}
	public void recherche(int cpt) {
		if (terrain[y_arrivé][x_arrivé]!=0 || cpt==dx*dy) {
			terrain[y_arrivé][x_arrivé]=-1;
			return;
		}
		int[][] terrain2 = new int[dy][dx];
		for (int i=0;i<dy;i++) {
			for (int j=0;j<dx;j++) {
				terrain2[i][j]=terrain[i][j];
			}
		}
		for (int i=0;i<dy;i++) {
			for (int j=0;j<dx;j++) {
				if (terrain[i][j]==1) {
					if (terrain[i][j]==1) {
						if (terrain[(i-1+dy)%dy][j] !=Integer.MIN_VALUE && terrain2[(i-1+dy)%dy][j]==0) {
							terrain2[(i-1+dy)%dy][j]+=1;
						}
						if (terrain[(i+1+dy)%dy][j] !=Integer.MIN_VALUE && terrain2[(i+1+dy)%dy][j]==0) {
							terrain2[(i+1+dy)%dy][j]+=1;
						}
						if (terrain[i][(j-1+dx)%dx] !=Integer.MIN_VALUE && terrain2[i][(j-1+dx)%dx]==0) {
							terrain2[i][(j-1+dx)%dx]+=1;
						}
						if (terrain[i][(j+1+dx)%dx] !=Integer.MIN_VALUE && terrain2[i][(j+1+dx)%dx]==0) {
							terrain2[i][(j+1+dx)%dx]+=1;
						}
						
					}
				}
			}
		}
		for (int i=0;i<dy;i++) {
			for (int j=0;j<dx;j++) {
				if (terrain[i][j]>=1) {
					terrain2[i][j]+=1;
				}
			}
		}
		terrain=terrain2;
		recherche(cpt+=1);
	}
	public void chemin_final(int cpt){
		if (terrain[y_depart][x_depart]==-9 || cpt==dx*dy) {
			return;
		}
		int[][] terrain2 = new int[dy][dx];
		for (int i=0;i<dy;i++) {
			for (int j=0;j<dx;j++) {
				terrain2[i][j]=terrain[i][j];
			}
		}
		for (int i=0;i<dy;i++) {
			for (int j=0;j<dx;j++) {
				if (terrain[i][j]<=-1 && terrain[i][j]!=Integer.MIN_VALUE) {
					if (terrain[(i-1+dy)%dy][j] == cpt+2) {
						terrain2[(i-1+dy)%dy][j]=0-terrain[(i-1+dy)%dy][j];;
					}
					if (terrain[(i+1+dy)%dy][j] == cpt+2) {
						terrain2[(i+1+dy)%dy][j]=0-terrain[(i+1+dy)%dy][j];
					}
					if (terrain[i][(j-1+dx)%dx] == cpt+2) {
						terrain2[i][(j-1+dx)%dx]=0-terrain[i][(j-1+dx)%dx];
					}
					if (terrain[i][(j+1+dx)%dx] == cpt+2) {
						terrain2[i][(j+1+dx)%dx]=0-terrain[i][(j+1+dx)%dx];
					}
				}
			}
		}
		terrain=terrain2;
//		System.out.println(""+cpt);
//		this.afficher();
//		System.out.println("");
		chemin_final(cpt+=1);
	}
	public void visibility_schema() {
		System.out.println("");
		for (int i=0;i<dy;i++) {
			for (int j=0;j<dx;j++) {
				if (i==y_depart && j==x_depart) {
					System.out.print("D ");
					continue;
				}
				if (i==y_arrivé && j==x_arrivé) {
					System.out.print("A ");
					continue;
				}
				if (terrain[i][j]==Integer.MIN_VALUE) {
					System.out.print("M ");
					continue;
				}
				if (terrain[i][j]<0) {
					System.out.print("+ ");
				}else {
					System.out.print("- ");
				}
			}
			System.out.println("");
		}
	}
	public void simplification() {
		for (int i=0;i<dy;i++) {
			for (int j=0;j<dx;j++) {
				if (terrain[i][j]==Integer.MIN_VALUE) {
					terrain[i][j]=0;
					continue;
				}
				if (terrain[i][j]<0) {
					terrain[i][j]=1;
				}else {
					terrain[i][j]=0;
				}
			}
		}
	}
	public int[][] getTerrain() {
		return terrain;
	}
	
	
} 