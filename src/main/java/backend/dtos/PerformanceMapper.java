package backend.dtos;

import backend.entities.Dj;
import backend.entities.Performance;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PerformanceMapper {
    public PerformanceDto mapToDto(Performance performance) {
        PerformanceDto performanceDto = new PerformanceDto(performance.getId(), performance.getName(), performance.getGenre());

        performanceDto.setDj(performance.getDJ()
                .stream()
                .map(Dj::getId)
                .collect(Collectors.toSet())); //Sets performanceDTO's DJs, being a set of IDs (longs).

        return performanceDto;
    }

    public Performance mapFromDto(PerformanceDto performanceDTO) {
        return new Performance(performanceDTO.getId(), performanceDTO.getName(), performanceDTO.getGenre());
    }
}