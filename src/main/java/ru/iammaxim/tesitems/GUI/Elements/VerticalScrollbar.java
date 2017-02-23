package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Mouse;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.LayoutBase;
import ru.iammaxim.tesitems.GUI.ResManager;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by maxim on 2/3/17 at 2:00 PM.
 */
public class VerticalScrollbar extends LayoutBase {
    private boolean clicked = false;
    private int scroll = 0;
    private Consumer<VerticalScrollbar> onScroll;
    private int scrollbarWidth = 8, scrollbarEndHeight = 16, scrollbarHandleHeight = 16;
    private int elementHeight = 0;
    private ScrollbarArrow arrowUp, arrowDown;

    public int getScroll() {
        return scroll;
    }

    public void setScroll(int scroll) {
        this.scroll = scroll;
    }

    public VerticalScrollbar() {
        arrowUp = new ScrollbarArrow(-20, Direction.UP);
        arrowDown = new ScrollbarArrow(20, Direction.DOWN);
    }

    @Override
    public void checkClick(int mouseX, int mouseY) {
        super.checkClick(mouseX, mouseY);
        arrowUp.checkClick(mouseX, mouseY);
        arrowDown.checkClick(mouseX, mouseY);
    }

    @Override
    public void doLayout() {
        //don't need to layout anything
    }

    @Override
    public List<ElementBase> getChildren() {
        return Arrays.asList(arrowUp, arrowDown);
    }

    public int getElementHeight() {
        return elementHeight;
    }

    public void setElementHeight(int elementHeight) {
        this.elementHeight = elementHeight;
    }

    public VerticalScrollbar setOnScroll(Consumer<VerticalScrollbar> onScroll) {
        this.onScroll = onScroll;
        return this;
    }

    @Override
    public ElementBase setBounds(int left, int top, int right, int bottom) {
        arrowUp.setBounds(left, top, right, top + scrollbarEndHeight);
        arrowDown.setBounds(left, bottom - scrollbarEndHeight, right, bottom);
        return super.setBounds(left, top + scrollbarEndHeight, right, bottom - scrollbarEndHeight);
    }

    @Override
    public int getWidth() {
        return scrollbarWidth;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        arrowUp.draw(mouseX, mouseY);
        arrowDown.draw(mouseX, mouseY);

        int scrollbarTopOffset = (int) ((float) (parent.height() - scrollbarHandleHeight - 2 * scrollbarEndHeight) /
                (elementHeight - (parent.height() - ((LayoutBase) parent).getPaddingTop() - ((LayoutBase) parent).getPaddingBottom()))
                * scroll);

        //draw background
        drawTexturedRect(Tessellator.getInstance(), left, top, right, bottom, ResManager.inv_scrollbar_bg_center);
        //draw handle
        drawTexturedRect(Tessellator.getInstance(), left, top + scrollbarTopOffset, right, top + scrollbarTopOffset + scrollbarHandleHeight, ResManager.inv_scrollbar);

        if (Mouse.isButtonDown(0)) {
            if (elementHeight > height && mouseY > top && mouseY < bottom && mouseX > left && mouseX < right) {
                clicked = true;
                /*((VerticalLayout) element).setTop(top + paddingTop - scroll);
                if (element instanceof LayoutBase) {
                    ((LayoutBase) element).doLayout();
                }*/
            }
        } else
            clicked = false;

        if (clicked) {
            scroll = (int) ((float) (mouseY - top - scrollbarHandleHeight / 2) / (height - scrollbarHandleHeight) * (elementHeight - (parent.height() - ((LayoutBase) parent).getPaddingTop() - ((LayoutBase) parent).getPaddingBottom())));
            if (scroll < 0)
                scroll = 0;
            int max_scroll = elementHeight - (VerticalScrollbar.this.parent.height() - ((LayoutBase)VerticalScrollbar.this.parent).getPaddingTop() - ((LayoutBase)VerticalScrollbar.this.parent).getPaddingBottom());
            if (scroll > max_scroll)
                scroll = max_scroll;

            onScroll.accept(this);
        }
    }

    @Override
    public String getName() {
        return "VerticalScrollbar";
    }

    public int getMinHeight() {
        return scrollbarEndHeight * 2 + scrollbarHandleHeight;
    }

    public enum Direction {
        UP,
        DOWN
    }

    class ScrollbarArrow extends ElementBase {
        private Direction direction;

        public ScrollbarArrow(int scrollDelta, Direction direction) {
            this.direction = direction;
            setOnClick(e -> {
                scroll += scrollDelta;
                if (scroll < 0)
                    scroll = 0;

                int max_scroll = elementHeight - (VerticalScrollbar.this.parent.height() - ((LayoutBase)VerticalScrollbar.this.parent).getPaddingTop() - ((LayoutBase)VerticalScrollbar.this.parent).getPaddingBottom());
                if (scroll > max_scroll)
                    scroll = max_scroll;
                onScroll.accept(VerticalScrollbar.this);
            });
        }

        @Override
        public void draw(int mouseX, int mouseY) {
            drawTexturedRect(Tessellator.getInstance(), left, top, right, bottom, direction == Direction.UP ? ResManager.inv_scrollbar_bg_top : ResManager.inv_scrollbar_bg_bottom);
        }

        @Override
        public String getName() {
            return "ScrollbarArrow (" + direction.toString() + ")";
        }
    }
}
