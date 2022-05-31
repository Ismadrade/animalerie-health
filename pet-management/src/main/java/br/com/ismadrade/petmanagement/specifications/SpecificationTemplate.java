package br.com.ismadrade.petmanagement.specifications;

import br.com.ismadrade.petmanagement.models.PetModel;
import br.com.ismadrade.petmanagement.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {
    @And({
            @Spec(path = "gender", spec= Equal.class),
            @Spec(path = "rga", spec= Like.class),
            @Spec(path = "name", spec= Like.class)
    })
    public interface PetSpec extends Specification<PetModel> {}

    public static Specification<PetModel> petsByUserId(final UUID userId){
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.and(cb.equal(root.get("user").get("userId"), userId));
        };
    }
}
