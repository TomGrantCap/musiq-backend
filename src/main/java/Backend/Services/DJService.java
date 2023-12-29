package Backend.Services;

import Backend.Entities.DJ;
import Backend.Repositories.DJRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DJService {

    @Autowired
    DJRepository djRepository;

    public DJ Save(DJ dj){
        return djRepository.save(dj);
    }
    public Iterable<DJ> FindAll(){
        return djRepository.findAll();
    }
    public void DeleteById(Long id){
        djRepository.deleteById(id);
    }

    public Optional<DJ> FindById(Long id){
        return djRepository.findById(id);
    }

    public Iterable<DJ> FindByNameAndGenre(String name, String genre){
        return djRepository.findByNameEqualsIgnoreCaseAndGenreEqualsIgnoreCase(name, genre);
    }

    public Iterable<DJ> FindByName(String name){
        return djRepository.findDJsByNameEqualsIgnoreCase(name);
    }

    public Iterable<DJ> FindByGenre(String genre) {
        return djRepository.findDJsByGenreEqualsIgnoreCase(genre);
    }
}
