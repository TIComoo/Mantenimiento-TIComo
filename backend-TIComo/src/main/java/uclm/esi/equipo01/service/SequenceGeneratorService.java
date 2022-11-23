package uclm.esi.equipo01.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import uclm.esi.equipo01.model.DatabaseSequence;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {

    private MongoOperations mongoOperations;

    @Autowired
    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long generateSequence(int seqId) {
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqId)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;

    }
    
    public long setSequence(int seqId, int value) {    	
        DatabaseSequence update = mongoOperations.findAndModify(query(where("_id").is(seqId)),
                new Update().set("seq",value), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(update) ? update.getSeq() : value;
    }
}