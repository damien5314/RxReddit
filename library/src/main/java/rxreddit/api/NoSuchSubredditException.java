package rxreddit.api;

/**
 * Exception indicating no subreddit exists for the given query.
 */
class NoSuchSubredditException extends RuntimeException {

    private String name;

    NoSuchSubredditException(String name) {
        super("No subreddit found with name: " + name);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
