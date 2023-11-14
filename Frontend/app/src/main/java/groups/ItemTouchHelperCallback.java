package groups;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Helper class for managing drag and drop functionality in a RecyclerView.
 * This class extends ItemTouchHelper.Callback and is used to enable dragging
 * items up and down in the RecyclerView.
 * @author Tristan Nono
 */
public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
    /**
     * The adapter associated with the RecyclerView to handle item movements.
     */
    private final ItemTouchHelperAdapter adapter;

    /**
     * Constructs an ItemTouchHelperCallback with the given adapter.
     *
     * @param adapter The adapter to handle item movements.
     */
    public ItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * Determines the movement flags for drag and swipe actions.
     *
     * @param recyclerView The RecyclerView to which ItemTouchHelper is attached.
     * @param viewHolder   The ViewHolder for which the movement flags should be returned.
     * @return The movement flags for drag and swipe actions.
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN; // Enable drag up and down
        int swipeFlags = 0; // Disable swipe
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * Called when an item is dragged to a new position.
     *
     * @param recyclerView The RecyclerView to which ItemTouchHelper is attached.
     * @param source       The ViewHolder from which the item is being moved.
     * @param target       The ViewHolder to which the item is being moved.
     * @return True if the item move is handled, false otherwise.
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        return adapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
    }

    /**
     * Called when an item is swiped.
     *
     * @param viewHolder The ViewHolder of the item that was swiped.
     * @param direction  The direction in which the item was swiped.
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // Handle swipe if needed (not used in this case)
    }

    /**
     * Indicates whether long press drag is enabled.
     *
     * @return True if long press drag is enabled, false otherwise.
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }
}

