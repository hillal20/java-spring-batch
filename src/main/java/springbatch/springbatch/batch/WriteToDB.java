package springbatch.springbatch.batch;


import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springbatch.springbatch.repository.UserRepo;

import java.util.List;

@Component
public class WriteToDB implements ItemWriter {


    @Autowired
    private UserRepo userRepo;

    @Override
    public void write(List users) throws Exception {

        // saving the user into the db
        userRepo.saveAll(users);


        System.out.println("====> data is saved ====== ");
    }


}
