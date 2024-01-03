package backend.Services;

import backend.Entities.DJ;
import backend.Repositories.DJRepository;
import backend.Repositories.PerformanceRepository;
import backend.dtos.DjDTO;
import backend.dtos.DjMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DJService {

    final DJRepository djRepository;

    final DjMapper djMapper;

    final PerformanceRepository performanceRepository;

    public DJService(DJRepository djRepository, DjMapper djMapper, PerformanceRepository performanceRepository) {
        this.djRepository = djRepository;
        this.djMapper = djMapper;
        this.performanceRepository = performanceRepository;
    }

    public DjDTO save(DJ dj){
        return djMapper.mapToDto(djRepository.save(dj));
    }
    public Iterable<DjDTO> findAll(){
        List<DjDTO> djDTOList = djRepository.findAll()
                .stream()
                .map(djMapper::mapToDto)
                .collect(Collectors.toList());

        return populateNamesList(djDTOList);
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

    public Iterable<DjDTO> populateNamesList(Iterable<DjDTO> djDTOList){
        //Populates DjDTO's performanceNames column.

        for (DjDTO djDTO : djDTOList){
            djDTO.setPerformanceNames(getNames(djDTO));
        }
        return djDTOList;
    }

    public Set<String> getNames(DjDTO djDTO){
        Set<String> performanceNames = new HashSet<>();
        for (Long id : djDTO.getPerformanceIDs()){
            performanceNames.add(performanceRepository.findById(id).get().getName());
        }
        djDTO.setPerformanceNames(performanceNames);
        return performanceNames;
    }
}


