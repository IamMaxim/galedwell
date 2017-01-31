package ru.iammaxim.tesitems.GUI.Debugger;

import net.minecraft.client.renderer.Tessellator;
import ru.iammaxim.tesitems.GUI.Elements.Arrow;
import ru.iammaxim.tesitems.GUI.Elements.ElementBase;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.*;
import ru.iammaxim.tesitems.GUI.Elements.Text;

/**
 * Created by maxim on 30.01.17.
 */
public class DebuggerWindow {
    public static ElementBase buildForElement(ElementBase element) {
        return new ScrollableLayout()
                .setMinHeight(1000000)
                .setElement(new VerticalLayout().add(buildNode(element)))
                .setWidthOverride(ElementBase.FILL);
    }

    public static void drawElementOverlay(ElementBase element) {
        ElementBase.drawColoredRect(Tessellator.getInstance(),
                element.left() - element.getLeftMargin(),
                element.top() - element.getTopMargin(),
                element.right() + element.getRightMargin(),
                element.bottom() + element.getBottomMargin(),
                0x55fecb9b);
        ElementBase.drawColoredRect(Tessellator.getInstance(),
                element.left(),
                element.top(),
                element.right(),
                element.bottom(),
                0x553bede4);
        if (element instanceof LayoutBase)
            ElementBase.drawColoredRect(Tessellator.getInstance(),
                    element.left() + ((LayoutBase) element).getPaddingLeft(),
                    element.top() + ((LayoutBase) element).getPaddingTop(),
                    element.right() - ((LayoutBase) element).getPaddingRight(),
                    element.bottom() - ((LayoutBase) element).getPaddingBottom(),
                    0x556304b2);
        else
            ElementBase.drawColoredRect(Tessellator.getInstance(),
                    element.left(),
                    element.top(),
                    element.right(),
                    element.bottom(),
                    0x556304b2);
    }

    public static ElementBase buildNode(ElementBase element) {
        if (element instanceof LayoutBase) {
            DoubleStateFrameLayout layout = new DoubleStateFrameLayout();
            VerticalLayout opened = (VerticalLayout) new VerticalLayout().setPaddingLeft(12);
            for (ElementBase e : ((LayoutBase) element).getChildren())
                opened.add(buildNode(e));

            layout.setFirstState(
                    new HorizontalLayout() {
                        @Override
                        public void checkHover(int mouseX, int mouseY) {
                            if (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom)
                                drawElementOverlay(element);
                        }
                    }
                            .add(new Arrow(Arrow.Direction.RIGHT))
                            .add(new Text(element.getClass().getSimpleName()))
                            .setOnClick(e -> {
                                layout.selectSecond();
                                ((LayoutBase) e.getRoot()).doLayout();
                            }))
                    .setSecondState(
                            new VerticalLayout()
                                    .add(new HorizontalLayout() {
                                        @Override
                                        public void checkHover(int mouseX, int mouseY) {
                                            if (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom)
                                                drawElementOverlay(element);
                                        }
                                    }
                                            .add(new Arrow(Arrow.Direction.DOWN))
                                            .add(new Text(element.getClass().getSimpleName()))
                                            .setOnClick(e -> {
                                                layout.selectFirst();
                                                ((LayoutBase) e.getRoot()).doLayout();
                                            }))
                                    .add(opened)).selectFirst();
            return layout;
        } else {
            return new Text(element.getClass().getSimpleName()) {
                @Override
                public void checkHover(int mouseX, int mouseY) {
                    if (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom)
                        drawElementOverlay(element);
                }
            };
        }
    }
}
