package ms.vanity.news.domains

import org.hibernate.validator.constraints.NotBlank

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity
class ContentSource {

    @Id
    @GeneratedValue
    Long id

    @NotNull
    @NotBlank
    String target

    Integer priority = 0

    Boolean disabled = false
}
