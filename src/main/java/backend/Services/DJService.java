package backend.Services;

import backend.Entities.DJ;
import backend.Entities.Performance;
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

    //SAVE
    public DjDTO save(DJ dj){
        return djMapper.mapToDto(djRepository.save(dj));
    }

    //FIND ALL
    public Iterable<DjDTO> findAll(){
        List<DjDTO> djDTOList = djRepository.findAll()
                .stream()
                .map(djMapper::mapToDto)
                .collect(Collectors.toList());

        return djDTOList;
    }

    //DELETE BY ID
    public void deleteById(Long djId){
        Set<Long> performanceIdSet = djRepository.findById(djId).map(djMapper::mapToDto).get().getPerformanceIDs();
        if (performanceIdSet.isEmpty()){
            djRepository.deleteById(djId);
            System.out.println("DJ lacking performances deleted");
        }
        else{
            //For each performance in DJ's set, removes the DJ from said set prior to deletion.
            DJ dj = djRepository.findById(djId).get();
            for (Long performanceId : performanceIdSet){
                Performance performance = performanceRepository.findById(performanceId).get();
                Set<DJ> djs = performance.getDJ();
                djs.remove(dj);
                performance.setDJ(djs);
                performanceRepository.save(performance);
                System.out.println("DJ " + djId + " deleted for performance " + performanceId);
            }
            djRepository.deleteById(djId);
        }
    }

    //FIND BY ID
    public Optional<DjDTO> findById(Long id){
        return djRepository.findById(id).map(djMapper::mapToDto);
    }

    //FIND BY NAME AND/OR GENRE
    public Iterable<DjDTO> findByNameAndGenre(String name, String genre){
        return djRepository.findByNameEqualsIgnoreCaseAndGenreEqualsIgnoreCase(name, genre)
                .stream()
                .map(djMapper::mapToDto)
                .collect(Collectors.toList());
    }

    //FIND BY NAME
    public Iterable<DjDTO> findByName(String name){
        return djRepository.findDJsByNameEqualsIgnoreCase(name)
                .stream()
                .map(djMapper::mapToDto)
                .collect(Collectors.toList());
    }

    //FIND BY GENRE
    public Iterable<DjDTO> findByGenre(String genre) {
        return djRepository.findDJsByGenreEqualsIgnoreCase(genre)
                .stream()
                .map(djMapper::mapToDto)
                .collect(Collectors.toList());
    }
}


