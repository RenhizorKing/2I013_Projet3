package Monde;

public class main_test {
	private static int dx;
	private static int dy;
	public static void main(String[] args) {
		Terrain terrain= new Terrain(dx=10,dy=10);
		Monde monde = new Monde(dx,dy,1,0.01,0.05);
		Recherche_chemin Path= new Recherche_chemin(dx, dy, 2, 2, 8, 8);
		Path.afficher();
		System.out.println("-------------");
		Path.recherche(0);
		Path.afficher();
		System.out.println("");
		System.out.println(""+Path.getTerrain()[2][2]+" ... "+Path.getTerrain()[8][8]);
		System.out.println("-------------");
		Path.chemin_final(0);
		System.out.println(""+Path.getTerrain()[2][2]+" ... "+Path.getTerrain()[8][8]);
//		Path.visibility_schema();
		System.out.println("-------------");
		Path.simplification();
		Path.afficher();
		System.out.println("\n|"+Path.getTerrain()[2][2]+"|"+Path.getTerrain()[8][8]+"|");
	}
}