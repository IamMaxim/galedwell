package ru.iammaxim.tesitems.GUI.Elements;

import ru.iammaxim.tesitems.GUI.Elements.Layouts.LayoutBase;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.LayoutWithList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by maxim on 2/24/17 at 9:08 PM.
 */
public class TableEntry extends LayoutBase implements LayoutWithList {
    private ArrayList<ElementBase> columns = new ArrayList<>();
    private int verticalDividerWidth = 1;
    private Consumer<Integer> onLeftClick, onRightClick;
    protected int index;

    public TableEntry setOnEntryLeftClick(Consumer<Integer> onLeftClick) {
        this.onLeftClick = onLeftClick;
        return this;
    }

    public TableEntry setOnEntryRightClick(Consumer<Integer> onRightClick) {
        this.onRightClick = onRightClick;
        return this;
    }

    public TableEntry(int index) {
        this.index = index;
    }

    public TableEntry() {
        this(0);
    }

    public TableEntry setVerticalDividerWidth(int width) {
        this.verticalDividerWidth = width;
        return this;
    }

    @Override
    public void click(int relativeX, int relativeY) {
        if (onLeftClick == null) {
            return;
        }
        onLeftClick.accept(index);
    }

    @Override
    public void rightClick(int relativeX, int relativeY) {
        if (onRightClick == null) {
            return;
        }
        onRightClick.accept(index);
    }

    public void setupColumns(TableEntry header) {
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i)._setwidth(header.getChildren().get(i).width());
        }
    }

    @Override
    public void doLayout() {
        int x = left + paddingLeft;
        int y = top + paddingTop;
        int h = getHeight();
        for (ElementBase column : columns) {
            int w = column.width();
            column.setBounds(x, y, x + w, y + h);
            if (column instanceof LayoutBase)
                ((LayoutBase) column).doLayout();
            x += w + verticalDividerWidth;
        }
    }

    @Override
    public List<ElementBase> getChildren() {
        return columns;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (background != null)
            background.draw(mouseX, mouseY);
        columns.forEach(c -> c.draw(mouseX, mouseY));
    }

    @Override
    public int getWidth() {
        int w = paddingLeft + paddingRight + marginLeft + marginRight;
        for (ElementBase column : columns) {
            w += column.width();
        }
        w += (columns.size() - 1) * verticalDividerWidth;
        return w;
    }

    @Override
    public int getHeight() {
        int h = 0;
        for (ElementBase column : columns) {
            int ch = column.getHeight();
            if (ch > h)
                h = ch;
        }
        return h + paddingTop + paddingBottom + marginBottom + marginTop;
    }

    @Override
    public String getName() {
        return "TableEntry";
    }

    @Override
    public LayoutWithList add(ElementBase element) {
        columns.add(element);
        element.setParent(this);
        return this;
    }

    @Override
    public void remove(ElementBase element) {
        columns.remove(element);
    }

    @Override
    public void clear() {
        columns.clear();
    }
}
