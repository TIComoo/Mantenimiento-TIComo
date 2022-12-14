package uclm.esi.equipo01.model;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import uclm.esi.equipo01.http.Manager;

@Document(collection = "Atencion")
public class AtencionTelefonica extends User {
	
	@Transient
	public static final int SEQUENCE_ID = Sequence.ATENCION.getValue();
	
	public AtencionTelefonica() {
		super();
	}
	
	public AtencionTelefonica(String email, String pwd, String name, String surname) {
		super(email, pwd, name, surname);
		super.setId(Manager.get().generateSequence(SEQUENCE_ID));
	}

	@Override
	public boolean isActiveAccount() {
		return true;
	}

}
