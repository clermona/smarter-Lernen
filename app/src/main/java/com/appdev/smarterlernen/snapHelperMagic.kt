import android.graphics.PointF
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

class CardSnapHelper : SnapHelper() {

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray? {
        val out = IntArray(2)
        if (layoutManager is LinearLayoutManager) {
            val snapView = findSnapView(layoutManager) ?: return null
            val targetPos = layoutManager.getPosition(targetView)
            val currentPos = layoutManager.getPosition(snapView)
            val direction = if (targetPos > currentPos) 1 else -1
            if (layoutManager.orientation == LinearLayoutManager.HORIZONTAL) {
                out[0] = targetView.left - snapView.left
                out[1] = 0
            } else {
                out[0] = 0
                out[1] = targetView.top - snapView.top
            }
        }
        return out
    }

    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager,
        velocityX: Int,
        velocityY: Int
    ): Int {
        if (layoutManager !is LinearLayoutManager) {
            return RecyclerView.NO_POSITION
        }

        val itemCount = layoutManager.itemCount
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION
        }

        val currentView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
        val currentPosition = layoutManager.getPosition(currentView)
        if (currentPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION
        }

        val reverseLayout = layoutManager.reverseLayout
        val flingVelocity = if (layoutManager.orientation == LinearLayoutManager.HORIZONTAL) {
            if (reverseLayout) -velocityX else velocityX
        } else {
            if (reverseLayout) -velocityY else velocityY
        }

        return if (flingVelocity > 0) {
            currentPosition + 1
        } else if (flingVelocity < 0) {
            currentPosition - 1
        } else {
            currentPosition
        }
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        if (layoutManager !is LinearLayoutManager) {
            return null
        }

        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        if (firstVisibleItemPosition == RecyclerView.NO_POSITION || lastVisibleItemPosition == RecyclerView.NO_POSITION) {
            return null
        }

        val itemCount = layoutManager.itemCount
        if (itemCount == 0) {
            return null
        }

        val centerPosition = (firstVisibleItemPosition + lastVisibleItemPosition) / 2
        val offset = if (layoutManager.canScrollHorizontally()) {
            (layoutManager.width - layoutManager.getDecoratedLeft(layoutManager.getChildAt(0)!!)) /
                    layoutManager.getChildAt(0)!!.width.toFloat()
        } else {
            (layoutManager.height - layoutManager.getDecoratedTop(layoutManager.getChildAt(0)!!)) /
                    layoutManager.getChildAt(0)!!.height.toFloat()
        }

        return if (offset >= 0.5) {
            layoutManager.findViewByPosition(lastVisibleItemPosition)
        } else {
            layoutManager.findViewByPosition(centerPosition)
        }
    }
}
