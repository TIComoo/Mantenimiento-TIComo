package uclm.esi.equipo01.model;

public enum Sequence{
    ADMIN(1), 
    RIDER(2), 
    CLIENT(3),
	RESTAURANT(4),
	PLATE(5),
	ORDER(6),
	PLATEANDORDER(7),
	SIMULTANEOUS_NUMBER_OF_ORDERS(8);
	
    private int value;
  
    public int getValue()
    {
        return this.value;
    }
  
    private Sequence(int value)
    {
        this.value = value;
    }
}
  
