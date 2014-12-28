package ms.vanity.news.domains

class TagPopularity {

    private static final Integer MAX_SCALE = 10

    final Tag tag

    final Long rank

    TagPopularity(Tag tag, Long rank, Long maxRank) {
        this.tag = tag
        this.rank = Math.floor(MAX_SCALE * (rank / maxRank))
    }

}
