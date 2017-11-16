package Mancala;

public class HoleClass {
	public int store; //indicates whether current hole is a store, 0 = not a store, 1 = player 1's store, 2 = player 2's store
	public int numPieces; //number of pieces currently in the hole
	public int side; //represents which side the hole is on, 1 = player 1's side, 2 = player 2's side
	
	int height;
	int width;
	int diameter;
	
	public String toString() {
		String concatenate = store + " " + numPieces + " " + side + " " + height + " "
				+ width + " " + diameter;
		return concatenate;
	}
	public static HoleClass fromString(String s) {
		HoleClass hc = new HoleClass();
		String tokens[] = s.split(" ");
		hc.store=Integer.parseInt(tokens[0]);
		hc.numPieces=Integer.parseInt(tokens[1]);
		hc.side=Integer.parseInt(tokens[2]);
		hc.height=Integer.parseInt(tokens[3]);
		hc.width=Integer.parseInt(tokens[4]);
		hc.diameter=Integer.parseInt(tokens[5]);
		return hc;
	}
	
	public HoleClass()
	{
		store = 0;
		numPieces = 0;
		side = 0;
		height=0;
		width =0;
		diameter=0;
				
	}
	
	public void setStore(int store){
		this.store = store;
	}
	
	public void setNumPieces(int numPieces){
		this.numPieces = numPieces;
	}
	
	public void setSide(int side){
		this.side = side;
	}
	
	public int getStore(){
		return store;
	}
	
	public int getNumPieces(){
		return numPieces;
	}
	
	public int getSide(){
		return side;
	}
}
