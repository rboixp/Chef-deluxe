package masjuan.ioc.chefdeluxe.utils.recyclerView;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Classe Listener RecyclerView, s'encarrega de controlar els esdeveniments que es produeixin, en aquest cas:
 * onItemClick i  onLongItemClick
 */
public class RvItemClickListener implements RecyclerView.OnItemTouchListener {
    private final OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

        public void onLongItemClick(View view, int position);
    }

    // Detecta gestos i esdeveniments utilitzat per els MotionEvents
    GestureDetector mGestureDetector;

    public RvItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mListener != null) {
                    mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    /**
     * Intercepta esdeveniments tàctils
     *
     * @param view RecyclerView
     * @param me   MotionEvent, objecte que informa dels esdeveniments de moviment
     * @return boolean
     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent me) {
        View childView = view.findChildViewUnder(me.getX(), me.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(me)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView view, @NonNull MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

}