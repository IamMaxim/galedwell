package ru.iammaxim.tesitems.GUI.Elements;

import ru.iammaxim.tesitems.GUI.Elements.Layouts.LayoutBase;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.LayoutWithList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxim on 2/24/17 at 9:07 PM.
 */
public class Table extends LayoutBase implements LayoutWithList {
    private ArrayList<ElementBase> entries = new ArrayList<>();
    private int w = 10, spacing = 0;
    private TableEntry header;
    private HorizontalDivider divider;

    public Table(TableEntry header) {
        this.header = header;
        divider = new HorizontalDivider();
    }

    public Table setHeader(TableEntry header) {
        this.header = header;
        entries.forEach(e -> ((TableEntry) e).setupColumns(this.header));
        return this;
    }

    public Table setWidth(int width) {
        this.w = width;
        return this;
    }

    public Table setSpacing(int spacing) {
        this.spacing = spacing;
        return this;
    }

    @Override
    public LayoutWithList add(ElementBase element) {
        entries.add(element);
        element.setParent(this);
        ((TableEntry) element).setupColumns(header);
//        element.setWidthOverride(FILL);
        return this;
    }

    @Override
    public void remove(ElementBase element) {
        entries.remove(element);
    }

    @Override
    public void clear() {
        entries.clear();
    }

    @Override
    public int getWidth() {
        return w + paddingLeft + paddingRight + marginLeft + marginRight;
    }

    @Override
    public int getHeight() {
        int h = paddingTop + paddingBottom + marginTop + marginBottom;
        h += header.getHeight();
        h += divider.getHeight();

        for (ElementBase entry : entries) {
            h += entry.getHeight();
            h += spacing;
        }
        h -= spacing;
        return h;
    }

    @Override
    public void doLayout() {
        int x = left + paddingLeft;
        int y = top + paddingTop;

        int headerHeight = header.getHeight();
        header.setBounds(x, y, x + w, y + headerHeight);
        y += headerHeight;

        int dividerHeight = divider.getHeight();
        divider.setBounds(x, y, x + w, y + dividerHeight);
        y += dividerHeight;

        for (ElementBase entry : entries) {
            int h = entry.getHeight();
            entry.setBounds(x, y, x + w, y + h);
            if (entry instanceof LayoutBase)
                ((LayoutBase) entry).doLayout();
            y += h + spacing;
        }
    }

    @Override
    public List<ElementBase> getChildren() {
        ArrayList<ElementBase> elements = new ArrayList<>();
        elements.add(header);
        elements.add(divider);
        elements.addAll(entries);
        return elements;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        header.draw(mouseX, mouseY);
        divider.draw(mouseX, mouseY);
        entries.forEach(e -> e.draw(mouseX, mouseY));
    }

    @Override
    public String getName() {
        return "Table";
    }
}
