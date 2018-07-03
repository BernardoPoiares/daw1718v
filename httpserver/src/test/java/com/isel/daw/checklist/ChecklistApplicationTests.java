package com.isel.daw.checklist;

import com.isel.daw.checklist.model.DataBaseDTOs.CheckItem;
import com.isel.daw.checklist.model.DataBaseDTOs.CheckItemTemplate;
import com.isel.daw.checklist.model.DataBaseDTOs.Users;
import com.isel.daw.checklist.repositories.CheckItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ChecklistApplicationTests {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CheckItemRepository repository;

	@Test
	public void testGetCheckItemById(){
		Users user =new Users("user1","password","tk");
		this.entityManager.persist(user);
		CheckItemTemplate checkItemTemplate=new CheckItemTemplate("da",user);
		entityManager.persist(checkItemTemplate);
		CheckItem customer1 = new CheckItem("uncomplet", checkItemTemplate);
		entityManager.persist(customer1);
		CheckItem checkItem = repository.findById(1);
		assertThat(checkItem).isNotNull();
	}

}

