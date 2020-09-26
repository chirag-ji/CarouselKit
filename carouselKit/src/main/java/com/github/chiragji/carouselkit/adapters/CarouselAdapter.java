package com.github.chiragji.carouselkit.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.github.chiragji.carouselkit.CarouselItem;
import com.github.chiragji.carouselkit.core.CarouselItemFragment;
import com.github.chiragji.carouselkit.models.Arguments;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * An adapter, which is responsible for showing the image contents or video contents in the
 * Carousel's {@link androidx.viewpager2.widget.ViewPager2}
 *
 * @author Chirag [apps.chiragji@outlook.com]
 * @version 1
 * @since 1.0.0
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP_PREFIX)
public class CarouselAdapter extends FragmentStateAdapter {
    private final Arguments arguments;
    private final ArrayList<CarouselItemFragment> fragments = new ArrayList<>();

    public CarouselAdapter(@NonNull Arguments arguments, @NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.arguments = arguments;
    }

    public CarouselAdapter(@NonNull Arguments arguments, @NonNull Fragment fragment) {
        super(fragment);
        this.arguments = arguments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public void applyCarouselItem(@NonNull CarouselItem... items) {
        fragments.addAll(getFragments(items));
        notifyDataSetChanged();
    }

    public void setCarouselItems(@NonNull CarouselItem... items) {
        LinkedList<CarouselItemFragment> data = getFragments(items);
        fragments.clear();
        fragments.addAll(data);
        notifyDataSetChanged();
    }

    private LinkedList<CarouselItemFragment> getFragments(@NonNull CarouselItem... items) {
        LinkedList<CarouselItemFragment> fragments = new LinkedList<>();
        for (CarouselItem carouselItem : items) {
            fragments.add(new CarouselItemFragment(carouselItem, arguments));
        }
        return fragments;
    }

    public void onPageChanged(int currentPos, int oldPos) {
        if (currentPos < fragments.size())
            fragments.get(currentPos).onFocusUpdated(true);
        if (oldPos < fragments.size() && currentPos != oldPos)
            fragments.get(oldPos).onFocusUpdated(false);
    }

    public void clear() {
        fragments.clear();
    }
}