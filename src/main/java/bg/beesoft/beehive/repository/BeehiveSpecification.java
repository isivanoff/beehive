package bg.beesoft.beehive.repository;

import bg.beesoft.beehive.model.dto.SearchBeehiveDTO;
import bg.beesoft.beehive.model.entity.BeehiveEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BeehiveSpecification implements Specification<BeehiveEntity> {
   private SearchBeehiveDTO searchBeehiveDTO;
   private Long apiaryId;

    public BeehiveSpecification(SearchBeehiveDTO searchBeehiveDTO, Long apiaryId) {
        this.searchBeehiveDTO = searchBeehiveDTO;
        this.apiaryId = apiaryId;
    }

    @Override
    public Predicate toPredicate(Root<BeehiveEntity> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        Predicate p = criteriaBuilder.conjunction();

        if(searchBeehiveDTO.getReferenceNumber() != null){
            p.getExpressions().add(criteriaBuilder.and(criteriaBuilder.equal(root.get("referenceNumber"),searchBeehiveDTO.getReferenceNumber())));
        }


        if(searchBeehiveDTO.getType() != null){
            p.getExpressions().add(criteriaBuilder.and(criteriaBuilder.equal(root.get("type"),searchBeehiveDTO.getType())));
        }

        p.getExpressions().add(criteriaBuilder.and(criteriaBuilder.equal(root.join("apiary").get("id"),apiaryId)));

        return p;
    }
}
