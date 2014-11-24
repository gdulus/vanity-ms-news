package ms.vanity.news.domains

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.validator.constraints.NotBlank

import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "tag", uniqueConstraints = [@UniqueConstraint(columnNames = ["hash"])])
class Tag {

    @Id
    @GeneratedValue
    Long id

    @NotNull
    @NotBlank
    @Size(max = 200)
    String name

    @NotNull
    @NotBlank
    @Size(max = 200)
    String normalizedName

    @NotNull
    @NotBlank
    @JsonIgnore
    String hash

    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    TagStatus status

    @JsonIgnore
    Boolean root = false

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tag_tag", joinColumns = @JoinColumn(name = 'tag_child_tags_id'), inverseJoinColumns = @JoinColumn(name = 'tag_id'))
    @JsonIgnore
    Set<Tag> childTags

    @JsonIgnore
    Date dateCreated

    @JsonIgnore
    Date lastUpdated

    @PrePersist
    void createdAt() {
        this.dateCreated = this.lastUpdated = new Date();
    }

    @PreUpdate
    void updatedAt() {
        this.lastUpdated = new Date();
    }


}

