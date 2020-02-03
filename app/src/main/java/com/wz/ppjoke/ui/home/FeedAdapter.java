package com.wz.ppjoke.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.wz.ppjoke.databinding.LayoutFeedTypeImageBinding;
import com.wz.ppjoke.databinding.LayoutFeedTypeVideoBinding;
import com.wz.ppjoke.model.Feed;

/**
 * @author wangzhen
 * @date 2020/02/03
 */
public class FeedAdapter extends PagedListAdapter<Feed, FeedAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final String mCategory;

    public FeedAdapter(Context context, String category) {
        super(new DiffUtil.ItemCallback<Feed>() {
            @Override
            public boolean areItemsTheSame(@NonNull Feed oldItem, @NonNull Feed newItem) {
                return oldItem.id == newItem.id;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Feed oldItem, @NonNull Feed newItem) {
                return oldItem.equals(newItem);
            }
        });

        inflater = LayoutInflater.from(context);
        mCategory = category;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).itemType;
    }

    @NonNull
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding;
        if (viewType == Feed.TYPE_IMAGE_TEXT) {
            binding = LayoutFeedTypeImageBinding.inflate(inflater);
        } else {
            binding = LayoutFeedTypeVideoBinding.inflate(inflater);
        }
        return new ViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {
        holder.bindData(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ViewDataBinding mBinding;

        public ViewHolder(@NonNull View itemView, ViewDataBinding binding) {
            super(itemView);
            mBinding = binding;
        }

        public void bindData(Feed item) {
            if (mBinding instanceof LayoutFeedTypeImageBinding) {
                LayoutFeedTypeImageBinding binding = (LayoutFeedTypeImageBinding) mBinding;
                binding.setFeed(item);
                binding.feedImage.bindData(item.width, item.height, 16, item.cover);
            } else {
                LayoutFeedTypeVideoBinding binding = (LayoutFeedTypeVideoBinding) mBinding;
                binding.setFeed(item);
                binding.listPlayerView.bindData(mCategory, item.width, item.height, item.cover, item.url);
            }
        }
    }
}
