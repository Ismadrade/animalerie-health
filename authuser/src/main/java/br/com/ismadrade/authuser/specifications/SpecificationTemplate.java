package br.com.ismadrade.authuser.specifications;

import br.com.ismadrade.authuser.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {
    @And({
            @Spec(path = "userStatus", spec= Equal.class),
            @Spec(path = "email", spec= Like.class),
            @Spec(path = "fullName", spec= Like.class)
    })
    public interface UserSpec extends Specification<UserModel> {}
}
