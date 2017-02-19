package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.iammaxim.tesitems.GUI.Elements.*;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.*;
import ru.iammaxim.tesitems.Networking.MessageQuest;
import ru.iammaxim.tesitems.Player.AdminTemporaryStorage;
import ru.iammaxim.tesitems.Questing.Quest;
import ru.iammaxim.tesitems.Questing.QuestStage;
import ru.iammaxim.tesitems.TESItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxim on 11/7/16 at 4:14 PM.
 */
public class GuiQuestEditor extends Screen {
    private VerticalLayout layout;
    private Quest quest;

    public GuiQuestEditor() {
        quest = AdminTemporaryStorage.lastEditedQuest.copy();

        ScrollableLayout scrollableLayout = new ScrollableLayout();
        contentLayout.setElement(scrollableLayout);
        layout = new VerticalLayout();
        scrollableLayout.setElement(layout);

        layout.add(new HeaderLayout("Quest editor"));
        layout.add(new Text("Quest ID: " + quest.id));
        TextField questName = (TextField) new TextField().setHint("Quest name").setText(quest.name).setWidthOverride(ElementBase.FILL);
        layout.add(questName);
        layout.add(new HorizontalDivider());

        layout.add(new Button("Edit stages").center(true).setWidthOverride(ElementBase.FILL).setOnClick(e ->
                ScreenStack.addScreen(new StagesEditor(quest))));

        layout.add(new HorizontalDivider());
        layout.add(new HorizontalLayout()
                .add(new Button("Save").setOnClick(e -> {
                        TESItems.networkWrapper.sendToServer(new MessageQuest(quest));
                        ScreenStack.addScreen(new GuiAlertDialog("Quest saved"));
                })).add(new Button("Back").setOnClick(e ->
                        ScreenStack.close()))
                .center(true).setWidthOverride(ElementBase.FILL));
    }

    @Override
    public boolean close() {
        ScreenStack.addScreen(new GuiConfirmationDialog("Are you sure you don't want to save changes?", ScreenStack::forceClose));
        return false;
    }

    private class StagesEditor extends Screen {
        private Quest quest;

        public StagesEditor(Quest quest) {
            this.quest = quest;

            contentLayout = new DarkBackgroundFrameLayout().setInnerPaddingHorizontal(0).setInnerPaddingVertical(0)
                    .setElement(new FixedSizeLayout()
                            .setFixedHeight(res.getScaledHeight() - 64)
                            .setFixedWidth(res.getScaledWidth() - 64)
                            .setElement(new StagesElement()));

            root.setElement(contentLayout);
            root.doLayout();
        }

        private class StagesElement extends LayoutBase {
            private FontRenderer fontRenderer = TESItems.ClientThings.fontRenderer;
            private ArrayList<StageElement> stages = new ArrayList<>();
            private int offsetX = 0, offsetY = 0;
            private HorizontalLayout buttons;
            private int lastMouseX = -1, lastMouseY = -1;

            public StagesElement() {
                quest.stages.forEach(s -> stages.add(new StageElement(s)));


                buttons = new HorizontalLayout()
                        .add(new Button("Add stage").setOnClick(b -> {
                            QuestStage s = new QuestStage();
                            quest.stages.add(s);
                            stages.add(new StageElement(s));
                            doLayout();
                        }))
                        .add(new Button("Back").setOnClick(b -> {
                            ScreenStack.close();
                        }));
                int w = buttons.getWidth();
                int cx = res.getScaledWidth() / 2;
                buttons.setBounds(cx - w / 2, 4, cx + w / 2, 4 + buttons.getHeight());
                buttons.doLayout();
            }

            @Override
            public void setParent(ElementBase parent) {
                super.setParent(parent);
                offsetX = parent.getWidth() / 2;
                offsetY = parent.getHeight() / 2;
            }

            @Override
            public void checkClick(int mouseX, int mouseY) {
                buttons.checkClick(mouseX, mouseY);
            }

            @Override
            public void doLayout() {
                int x = left + offsetX;
                int y = top + offsetY;
                for (StageElement element : stages) {
                    element.doLayout(x, y);
                    y += 40;
                }
            }

            @Override
            public List<ElementBase> getChildren() {
                return new ArrayList<>();
            }

            @Override
            public void draw(int mouseX, int mouseY) {
                if (Mouse.isButtonDown(0)) {
                    if (lastMouseX == -1)
                        lastMouseX = mouseX;
                    if (lastMouseY == -1)
                        lastMouseY = mouseY;

                    int deltaX = lastMouseX - mouseX;
                    int deltaY = lastMouseY - mouseY;

                    offsetX -= deltaX;
                    offsetY -= deltaY;

                    doLayout();
                    lastMouseX = mouseX;
                    lastMouseY = mouseY;
                } else {
                    lastMouseX = -1;
                    lastMouseY = -1;
                }

                GL11.glEnable(GL11.GL_SCISSOR_TEST);
                GL11.glScissor(left * res.getScaleFactor(), top * res.getScaleFactor(), width * res.getScaleFactor(), height * res.getScaleFactor());
                stages.forEach(StageElement::draw);
                GL11.glDisable(GL11.GL_SCISSOR_TEST);

                buttons.draw(mouseX, mouseY);
            }

            @Override
            public String getName() {
                return "StagesElement";
            }

            private class StageElement {
                private static final int BG_COLOR = 0x60000000;
                private static final int FG_COLOR = 0xffffffff;

                public QuestStage stage;
                public int left, top, w, h;

                public StageElement(QuestStage stage) {
                    this.stage = stage;
                }

                public int getWidth() {
                    return 32;
                }

                public int getHeight() {
                    return 16;
                }

                public void doLayout(int x, int y) {
                    this.w = getWidth();
                    this.h = getHeight();
                    this.left = x - w / 2;
                    this.top = y - h / 2;
                }

                public void draw() {
                    ElementBase.drawColoredRect(Tessellator.getInstance(), left, top, left + w, top + h, BG_COLOR);
                    fontRenderer.drawString(stage.id + "", (2 * left + w - fontRenderer.getStringWidth(stage.id + "")) / 2, (2 * top + h - 8) / 2, FG_COLOR);
                }
            }
        }
    }
}
