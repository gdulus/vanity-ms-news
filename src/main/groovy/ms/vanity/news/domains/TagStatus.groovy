package ms.vanity.news.domains

public enum TagStatus {

    PUBLISHED,
    TO_BE_REVIEWED,
    PROMOTED,
    SPAM

    public static final List<TagStatus> OPEN_STATUSES = [PUBLISHED, PROMOTED].asImmutable()

}

