package com.Kungfu.panda.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.Kungfu.panda.entity.Users;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
/*@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)*/
class UsersRepoTest {

    @Autowired
    Environment env;

    @Autowired
    private UsersRepo usersRepo;

    @Test
    void testSaveAndFindById() {
        // Given
        Users user = new Users();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        // When
        Users savedUser = usersRepo.save(user);

        // Then
        assertThat(savedUser.getUser_id()).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("John Doe");
        assertThat(savedUser.getEmail()).isEqualTo("john.doe@example.com");

        // When
        Optional<Users> foundUser = usersRepo.findById(savedUser.getUser_id());

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("John Doe");
        assertThat(foundUser.get().getEmail()).isEqualTo("john.doe@example.com");
    }

    @Test
    void testFindAll() {
        // Given
        Users user1 = new Users();
        user1.setName("Jane Doe");
        user1.setEmail("jane.doe@example.com");
        usersRepo.save(user1);

        Users user2 = new Users();
        user2.setName("Bob Smith");
        user2.setEmail("bob.smith@example.com");
        usersRepo.save(user2);

        // When
        List<Users> users = usersRepo.findAll();

        // Then
        assertThat(users).hasSizeGreaterThanOrEqualTo(2);
        assertThat(users).extracting(Users::getName).contains("Jane Doe", "Bob Smith");
    }
}
