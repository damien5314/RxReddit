package rxreddit.model;

public interface Savable {

  boolean isSaved();

  void isSaved(boolean b);

  String getFullName();
}
