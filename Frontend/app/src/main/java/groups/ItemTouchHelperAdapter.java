package groups;

/**
 * This interface provides methods to handle item movement and dismissal in a RecyclerView.
 *
 * @author Tristan Nono
 */
public interface ItemTouchHelperAdapter {
    /**
     * Called when an item is moved to a new position in the RecyclerView.
     *
     * @param fromPosition The starting position of the moved item.
     * @param toPosition   The target position of the moved item.
     * @return True if the item move is handled, false otherwise.
     */
    boolean onItemMove(int fromPosition, int toPosition);

    /**
     * Called when an item is dismissed from the RecyclerView.
     *
     * @param position The position of the dismissed item.
     */
    void onItemDismiss(int position);
}
