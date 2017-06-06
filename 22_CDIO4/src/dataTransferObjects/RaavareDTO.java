package dataTransferObjects;

/**
 * Raavare Data Objekt
 * 
 * @author mn/sh/tb
 * @version 1.2
 */

public class RaavareDTO
{
    /** between 1 and 99999999 chosen by the user. */
    int id;                     
    /** between 2 and 20 characters */
    String name;                
    /** between 2 and 20 characters */
    String supplier;         
	
	public RaavareDTO(int raavareId, String raavareNavn, String leverandoer)
	{
		this.id = raavareId;
		this.name = raavareNavn;
		this.supplier = leverandoer;
	}
	
    public int getId() { return id; }
    public void setId(int raavareId) { this.id = raavareId; }
    public String getName() { return name; }
    public void setName(String raavareNavn) { this.name = raavareNavn; }
    public String getSupplier() { return supplier; }
    public void setSupplier(String leverandoer) { this.supplier = leverandoer; }
    public String toString() { 
		return id + "\t" + name +"\t" + supplier; 
	}
    
    public RaavareDTO copy(){
    	return new RaavareDTO (id, name, supplier);
    }
}
