package springbatch.springbatch.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import springbatch.springbatch.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{


}
