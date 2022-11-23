package uclm.esi.equipo01.listener;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*********************************************************************
*
* Class Name: MongoListener.
* Class description: Audit changes to the mongoDB.
*
**********************************************************************/
@Component
public class MongoListener extends AbstractMongoEventListener<Object> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoListener.class); // Find or create a logger with the name passed as parameter
	
	/*********************************************************************
	*
	* - Method name: onAfterSave.
	* - Description of the Method: Audit changes to the mongoDB after saving an object.
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• AfterSaveEvent<Object> event: Event captured.
	* - Return value: None.
	* - Required Files: None.
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Override
	public void onAfterSave(AfterSaveEvent<Object> event) {
		if (LOGGER.isInfoEnabled())
			LOGGER.info(String.format("SAVED %s in collection %S", event.getSource(), event.getCollectionName()));	
	}
	
	/*********************************************************************
	*
	* - Method name: onAfterDelete.
	* - Description of the Method: Audit changes to the mongoDB after deleting an object.
	* - Calling arguments: A list of the calling arguments, their types, and
	* brief explanations of what they do:
	* 		• AfterDeleteEvent<Object> event: Event captured.
	* - Return value: None.
	* - Required Files: None.
	* - List of Checked Exceptions and an indication of when each exception
	* is thrown: None.
	*
	*********************************************************************/
	@Override
	public void onAfterDelete(AfterDeleteEvent<Object> event) {
		if (LOGGER.isInfoEnabled())
			LOGGER.info(String.format("DELETED %s in collection %S", event.getSource(), event.getCollectionName()));		
	}

}