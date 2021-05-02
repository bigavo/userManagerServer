package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

		@Autowired
		private UserRepository repo;
		
		@Autowired
		private TestEntityManager entityManager;
		@Test
		public void testCreateNewUserWithOneRole() {
			Role roleAdmin = entityManager.find(Role.class, 1);
			User userTrinhtr = new User("trinh.tran@gmail.com","trinh124", "Trinh", "Tran");
			userTrinhtr.addRole(roleAdmin);
			User savedUser =repo.save(userTrinhtr);
			assertThat(savedUser.getId()).isGreaterThan(0);
		}
		
		@Test
		public void testCreateNewUserWithTwoRoles() {
			User userRavi = new User("ravi@gmail.com", "ravi2020", "Ravi", "Kumar");
			Role roleEditor = entityManager.find(Role.class, 3);
			Role roleAssistant = entityManager.find(Role.class, 5);
			userRavi.addRole(roleEditor);
			userRavi.addRole(roleAssistant);
			
			User savedUser = repo.save(userRavi);
			assertThat(savedUser.getId()).isGreaterThan(0);
		}
		
		@Test
		public void testListAllUsers() {
			Iterable<User> listUsers = repo.findAll();
			listUsers.forEach(user -> System.out.println(user));
		}
		
//		@Test
//		public void GetUserById() {
//			User userTrinh = repo.findById(52).get();
//			System.out.println(userTrinh);
//			assertThat(userTrinh).isNotNull();
//		}
		
		@Test
		public void testUpdateUserDetails() {
			User userTrinh = repo.findById(52).get();
			userTrinh.setEnabled(true);
			userTrinh.setEmail("trinh.springboot@gmail.com");
			repo.save(userTrinh);
		}
		
		@Test
		public void testUpdateUserRoles() {
			
			Role roleSalesperson = entityManager.find(Role.class, 2);
			User userMike = repo.findById(2).get();
		
			userMike.addRole(roleSalesperson);
			repo.save(userMike);
		}
		
//		@Test
//		public void testDeleteUser() {
//			Integer userId = 22;
//			repo.deleteById(userId);
//		}
//		
//		@Test
//		public void testCountById() {
//			Integer id = 52;
//			Long countById = repo.countById(id);
//			
//			assertThat(countById).isNotNull().isGreaterThan(0);
//		}
		
		
		
}
