package rxreddit.model;

public interface Votable extends Archivable {
  void applyVote(int direction);
  Boolean isLiked();
  void isLiked(Boolean b);
  String getId();
  String getKind();
}
