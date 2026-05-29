package   com.Kungfu.panda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Kungfu.panda.entity.Users;
import  org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
    // You can add custom query methods here if needed
}