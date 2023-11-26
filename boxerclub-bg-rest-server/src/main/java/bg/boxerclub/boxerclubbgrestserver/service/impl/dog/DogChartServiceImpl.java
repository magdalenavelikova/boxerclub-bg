package bg.boxerclub.boxerclubbgrestserver.service.impl.dog;

import bg.boxerclub.boxerclubbgrestserver.exception.DogNotFoundException;
import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.DogAttributesDTO;
import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.DogChartNodeDTO;
import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import bg.boxerclub.boxerclubbgrestserver.repository.DogRepository;
import bg.boxerclub.boxerclubbgrestserver.service.dog.DogChartService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DogChartServiceImpl implements DogChartService {
    private final DogRepository dogRepository;

    public DogChartServiceImpl(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @Override
    public DogChartNodeDTO createDogChart(Long id, int depth) {
        DogEntity dogEntity = dogRepository.findById(id).orElseThrow(() -> new DogNotFoundException(id));
        DogChartNodeDTO node = new DogChartNodeDTO();
        node.setName(dogEntity.getName());

        DogAttributesDTO attributes = new DogAttributesDTO();
        attributes.setSex(dogEntity.getSex().toString());
        attributes.setColor(dogEntity.getColor().toString());
        node.setAttributes(attributes);

        if (depth > 0) {
            DogEntity mother = dogEntity.getMother();
            DogEntity father = dogEntity.getFather();
            List<DogEntity> children = new ArrayList<>();
            if (mother != null) {
                children.add(mother);
            }
            if (father != null) {
                children.add(father);
            }
            if (!children.isEmpty()) {
                node.setChildren(createChildrenNodes(children, depth - 1));
            }
        }

        return node;
    }

    private List<DogChartNodeDTO> createChildrenNodes(List<DogEntity> dogEntities, int depth) {
        return dogEntities.stream()
                .filter(Objects::nonNull)
                .map(dogEntity -> createDogChart(dogEntity.getId(), depth))
                .collect(Collectors.toList());
    }
}
