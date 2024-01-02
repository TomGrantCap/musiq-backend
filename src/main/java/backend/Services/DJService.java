package backend.Services;

import backend.Entities.DJ;
import backend.Repositories.DJRepository;
import backend.dtos.DjDTO;
import backend.dtos.DjMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DJService {

    final DJRepository djRepository;

    final DjMapper djMapper;

    public DJService(DJRepository djRepository, DjMapper djMapper) {
        this.djRepository = djRepository;
        this.djMapper = djMapper;
    }

    public DjDTO save(DJ dj){
        return djMapper.mapToDto(djRepository.save(dj));
    }
    public Iterable<DjDTO> findAll(){
        return djRepository.findAll().stream().map(djMapper::mapToDto).collect(Collectors.toList());
    }
    public void deleteById(Long id){
        djRepository.deleteById(id);
    }

    public Optional<DjDTO> findById(Long id){
        return djRepository.findById(id)
                .map(djMapper::mapToDto);
    }

    public Iterable<DjDTO> findByNameAndGenre(String name, String genre){
        return djRepository.findByNameEqualsIgnoreCaseAndGenreEqualsIgnoreCase(name, genre)
                .stream()
                .map(djMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public Iterable<DjDTO> findByName(String name){
        return djRepository.findDJsByNameEqualsIgnoreCase(name)
                .stream()
                .map(djMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public Iterable<DjDTO> findByGenre(String genre) {
        return djRepository.findDJsByGenreEqualsIgnoreCase(genre)
                .stream()
                .map(djMapper::mapToDto)
                .collect(Collectors.toList());
    }
}


