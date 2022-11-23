package uclm.esi.equipo01.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*********************************************************************
*
* Class Name: DatabaseSequence.
* Class description: Stores the sequences of the model Objects.
*
**********************************************************************/
@Document(collection = "Sequences")
public class DatabaseSequence {

    @Id
    private int id; // Sequence counter

    private long seq; // Id of the sequence

    public DatabaseSequence() {
		super();
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }
}