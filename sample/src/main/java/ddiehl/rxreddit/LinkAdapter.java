package ddiehl.rxreddit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ddiehl.rxreddit.sample.R;
import rxreddit.model.Link;

final class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.VH> {

    private List<Link> data;

    public LinkAdapter(List<Link> data) {
        this.data = data;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.link_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static final class VH extends RecyclerView.ViewHolder {

        private TextView titleText;
        private TextView authorText;

        public VH(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.title_view);
            authorText = itemView.findViewById(R.id.author_view);
        }

        public void bind(Link link) {
            titleText.setText(link.getTitle());
            authorText.setText(
                    String.format(
                            itemView.getContext().getString(R.string.author_formatter),
                            link.getAuthor()
                    ));
        }
    }
}
