package ssa;

public class Major {
	
	private int id;
	private String description;
	private int requiredSAT;
	
		
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getRequiredSAT() {
		return requiredSAT;
	}


	public void setRequiredSAT(int requiredSAT) {
		this.requiredSAT = requiredSAT;
	}


	public Major (int id, String description, int requiredSAT) {
		this.id = id;
		this.description = description;
		this.requiredSAT = requiredSAT;
	}
	
	public Major () {}

}
