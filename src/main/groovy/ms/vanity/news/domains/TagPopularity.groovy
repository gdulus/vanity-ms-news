package ms.vanity.news.domains

class TagPopularity {

    private static final Integer MAX_SCALE = 10

    final Tag tag

    final Integer rank

    TagPopularity(Tag tag, Integer rank, Integer maxRank) {
        this.tag = tag
        this.rank = Math.floor(MAX_SCALE * (rank / maxRank))
    }

}
