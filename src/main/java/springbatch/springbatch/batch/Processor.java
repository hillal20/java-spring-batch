package springbatch.springbatch.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import springbatch.springbatch.model.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class Processor implements ItemProcessor<User, User> {


//    here we need to process the dep names to replace the ids by real names, because in the csv file are numbers
    private static final Map<String, String> DEP_NAMES = new HashMap<>();

    public Processor(){
        // this empty constructor is setting  the hash map with all the deps according to it code
        DEP_NAMES.put("001", "technology");
        DEP_NAMES.put("002", "finance");
        DEP_NAMES.put("003", "procurement");
        DEP_NAMES.put("004", "archive");
        DEP_NAMES.put("005", "consumer");
        DEP_NAMES.put("006", "media");
    }


    @Override
    public User process(User user) throws Exception {

            // we get the code and we changing it to a real name from the hash map
             String depCode = user.getDept();
             String dept = DEP_NAMES.get(depCode);

             // setting the real name instead of the code
             user.setDept(dept);
             user.setTime(new Date());

        return user;
    }
}
