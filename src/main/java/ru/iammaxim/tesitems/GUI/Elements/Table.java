package ru.iammaxim.tesitems.GUI.Elements;

import net.minecraft.client.renderer.Tessellator;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.LayoutBase;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.LayoutWithList;
import ru.iammaxim.tesitems.GUI.ResManager;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by maxim on 2/24/17 at 9:07 PM.
 */
public class Table extends LayoutBase implements LayoutWithList {
    private ArrayList<ElementBase> entries = new ArrayList<>();
    private int w = 10, spacing = 0;
    private TableEntry header;
    private HorizontalDivider divider;
    private int verticalDividerWidth = 1;
    private ReentrantLock lock = new ReentrantLock();

    public Table(TableEntry header) {
        this.header = header;
        divider = new HorizontalDivider();
        divider.setColor(ResManager.MAIN_COLOR_SEMITRANSPARENT);
    }

    public Table setHeader(TableEntry header) {
        this.header = header;
        try {
            lock.lock();
            entries.forEach(e -> ((TableEntry) e).setupColumns(this.header));
        } finally {
            lock.unlock();
        }
        return this;
    }

    public Table setSpacing(int spacing) {
        this.spacing = spacing;
        return this;
    }

    @Override
    public LayoutWithList add(ElementBase element) {
        element.setParent(this);
        ((TableEntry) element).setupColumns(header);
        try {
            lock.lock();
            entries.add(element);
        } finally {
            lock.unlock();
        }
        return this;
    }

    @Override
    public void remove(ElementBase element) {
        try {
            lock.lock();
            entries.remove(element);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clear() {
        try {
            lock.lock();
            entries.clear();
        } finally {
            lock.unlock();
        }

    }

    @Override
    public int getWidth() {
        return w + paddingLeft + paddingRight + marginLeft + marginRight;
    }

    public Table setWidth(int width) {
        this.w = width;
        return this;
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
    public void checkClick(int mouseX, int mouseY) {
        super.checkClick(mouseX, mouseY);

        entries.forEach(e -> e.checkClick(mouseX, mouseY));
    }

    @Override
    public void checkRightClick(int mouseX, int mouseY) {
        super.checkRightClick(mouseX, mouseY);

        entries.forEach(e -> e.checkRightClick(mouseX, mouseY));
    }

    @Override
    public void doLayout() {
        int x = left + paddingLeft;
        int y = top + paddingTop;

        int headerHeight = header.getHeight();
        header.setBounds(x, y, x + w, y + headerHeight);
        header.doLayout();
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
        try {
            lock.lock();
            entries.forEach(e -> e.draw(mouseX, mouseY));
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        int height = getHeight();
        //draw vertical dividers
        Tessellator tess = Tessellator.getInstance();
        List<ElementBase> columns = header.getChildren();
        int x = left - verticalDividerWidth;
        for (int i = 0; i < columns.size() - 1; i++) {
            x += columns.get(i).width() + verticalDividerWidth;
            drawColoredRect(tess, x, top, x + verticalDividerWidth, top + height, ResManager.MAIN_COLOR_SEMITRANSPARENT);
        }
    }

    @Override
    public String getName() {
        return "Table";
    }

    public void setVerticalDividerWidth(int width) {
        this.verticalDividerWidth = width;
    }
}
