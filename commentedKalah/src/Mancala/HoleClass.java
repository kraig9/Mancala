package Mancala;

public class HoleClass {
	public int store; //indicates whether current hole is a store, 0 = not a store, 1 = player 1's store, 2 = player 2's store
	public int numPieces; //number of pieces currently in the hole
	public int side; //represents which side the hole is on, 1 = player 1's side, 2 = player 2's side
	
	public int height;
	public int width;
	public int diameter;
	
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
	
	//COPY CONSTRUCTOR
		public HoleClass(HoleClass hole) {
			this.store = hole.store;
			this.numPieces = hole.numPieces;
			this.side = hole.side;
			this.height = hole.height;
			this.diameter = hole.diameter;
			this.width = hole.width;
		}
}
