package backend.dtos;

import backend.Entities.DJ;
import backend.Entities.Performance;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DjMapper {
    public DjDTO mapToDto(DJ dj) {
        DjDTO djDto = new DjDTO(dj.getId(), dj.getName(), dj.getGenre());

        djDto.setPerformanceIDs(dj.getPerformance()
                .stream()
                .map(Performance::getId)
                .collect(Collectors.toSet()));  //Sets DJDTO's performances, being a set of IDs (longs).

        return djDto;
    }
}
