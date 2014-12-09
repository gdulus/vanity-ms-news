package ms.vanity.news.domains

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.validator.constraints.NotBlank

import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "article", uniqueConstraints = [@UniqueConstraint(columnNames = ["external_id", "source_id"]), @UniqueConstraint(columnNames = ["hash"])])
class Article {

    @Id
    @GeneratedValue
    Long id

    @NotNull
    @NotBlank
    @Column(name = 'external_id')
    @JsonIgnore
    String externalId

    @NotNull
    @NotBlank
    @OneToOne
    @JsonIgnore
    ContentSource source

    @NotNull
    @NotBlank
    @Size(max = 32)
    @JsonIgnore
    String hash

    @NotNull
    @NotBlank
    String body

    @NotNull
    @NotBlank
    String title

    @NotNull
    @NotBlank
    @org.hibernate.validator.constraints.URL
    String url

    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    ArticleStatus status

    @NotNull
    Date publicationDate

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "article_tag", joinColumns = @JoinColumn(name = 'article_tags_id'), inverseJoinColumns = @JoinColumn(name = 'tag_id'))
    @JsonIgnore
    Set<Tag> tags

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

    public Collection<Tag> getRootTags() {
        tags.findAll { it.searchable() }
    }

    public String getSourceName() {
        source.target
    }
}

