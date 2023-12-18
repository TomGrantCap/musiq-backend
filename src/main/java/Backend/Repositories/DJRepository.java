package Backend.Repositories;
import java.util.ArrayList;
import  java.util.List;
import Backend.Entities.DJ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DJRepository extends JpaRepository<DJ, Long> {

    DJ findDJsByGenreEqualsIgnoreCase(String genre);
    DJ findDJsByNameEqualsIgnoreCase(String name);

    default List<DJ> findDJIfNameLongerThan(int length){
        List<DJ> djList = new ArrayList<DJ>();
        for (DJ dj : this.findAll()){
            if (dj.getName().length() > length){
                djList.add(dj);
            }
        }
        return djList;
    }
}