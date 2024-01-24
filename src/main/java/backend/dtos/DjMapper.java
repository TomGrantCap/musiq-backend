package backend.dtos;

import backend.entities.Dj;
import backend.entities.Performance;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DjMapper {
    public DjDto mapToDto(Dj dj) {
        DjDto djDto = new DjDto(dj.getId(), dj.getName(), dj.getGenre());

        djDto.setPerformanceIds(dj.getPerformance()
                .stream()
                .map(Performance::getId)
                .collect(Collectors.toSet()));  //Sets DJDTO's performances, being a set of IDs (longs).

        return djDto;
    }

    public Dj mapFromDto(DjDto djDto) {
        return new Dj(djDto.getId(), djDto.getName(), djDto.getGenre());
    }
}
