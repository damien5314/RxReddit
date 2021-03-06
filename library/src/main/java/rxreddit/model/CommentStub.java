package rxreddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class CommentStub extends AbsComment {

    @SerializedName("data")
    Data data;

    @Override
    public String getId() {
        return data.id;
    }

    @Override
    public String getParentId() {
        if (data.parentId == null) return null;
        if (data.parentId.contains("_")) {
            return data.parentId.substring(3);
        } else return data.parentId;
    }

    public Integer getCount() {
        return data.count;
    }

    public void setCount(int num) {
        data.count = num;
    }

    public List<String> getChildren() {
        return data.children;
    }

    public void removeChildren(List<Listing> comments) {
        for (Listing comment : comments) {
            data.children.remove(comment.getId());
        }
    }

    @Override
    public boolean isCollapsed() {
        return false;
    }

    @Override
    public void setCollapsed(boolean b) {
        throw new UnsupportedOperationException("Cannot collapse a comment stub");
    }

    @Override
    public String toString() {
        return String.format("MoreComments (%s)", getCount());
    }

    static class Data extends AbsComment.Data {

        @SerializedName("count")
        Integer count;
        @SerializedName("children")
        List<String> children;
    }
}
