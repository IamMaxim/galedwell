package ru.iammaxim.tesitems.GUI.Elements.Layouts;

import ru.iammaxim.tesitems.GUI.Elements.ElementBase;

/**
 * Created by maxim on 08.03.2017.
 */
public class StartFromRightHorizontalLayout extends HorizontalLayout {
    @Override
    public void doLayout() {
        int _w = getWidth();
        int y = top + paddingTop;
        int x;
        int x_min = left + paddingLeft;
        if (center) x = right - (width - _w) / 2;
        else x = right - paddingRight;
        for (int i = elements.size() - 1; i >= 0; i--) {
            ElementBase element = elements.get(i);
            int w;
            int h;
            int h_override = element.getHeightOverride();
            if (h_override == FILL)
                h = height - paddingTop - paddingBottom;
            else
                h = Math.min(height - paddingTop - paddingBottom, element.getHeight());

            int w_override = element.getWidthOverride();
            if (w_override == FILL)
                w = x - x_min;
            else
                w = Math.min(element.getWidth(), x - x_min);

            element.setBounds(x - w, y, x, y + h);
            if (element instanceof LayoutBase)
                ((LayoutBase) element).doLayout();
            x -= w + spacing;
        }
    }
}
