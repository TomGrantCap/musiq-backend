package backend.api.repository;

import backend.entities.Dj;
import backend.repositories.DjRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class DjRepositoryTests {

    @Autowired
    private DjRepository djRepository;

    @Test
    public void DjRepository_Save_ReturnDj(){

        //Arrange
        Dj testDj = new Dj(1L, "TestName","TestGenre");

        //Act
        Dj savedDj = djRepository.save(testDj);

        //Assert
        Assertions.assertThat(savedDj).isNotNull();
        Assertions.assertThat(savedDj.getId()).isGreaterThan(0);
    }

    @Test
    public void DjRepository_FindById_ReturnDj(){

        //Arrange
        Dj testDj = new Dj(1L, "TestName","TestGenre");
        djRepository.save(testDj);

        //Act
        Dj foundDj = djRepository.findById(testDj.getId()).get();

        //Assert
        Assertions.assertThat(foundDj).isNotNull();
    }
    @Test
    public void DjRepository_ModifyDj_ReturnDj(){

        //Arrange
        Dj testDj = new Dj(1L, "TestName","TestGenre");
        djRepository.save(testDj);


        testDj.setName("NewName");
        testDj.setGenre("NewGenre");
        djRepository.save(testDj);

        //Act
        Dj foundDj = djRepository.findById(testDj.getId()).get();

        //Assert
        Assertions.assertThat(foundDj.getName()).isNotNull();
        Assertions.assertThat(foundDj.getGenre()).isNotNull();
    }

    @Test
    public void DjRepository_DeleteDj_ReturnNull(){

        //Arrange
        Dj testDj = new Dj(1L, "TestName","TestGenre");
        djRepository.save(testDj);

        djRepository.deleteById(testDj.getId());

        //Act
        Optional<Dj> foundDj = djRepository.findById(testDj.getId());

        //Assert
        Assertions.assertThat(foundDj).isEmpty();
    }




}
