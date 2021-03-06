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
import ru.iammaxim.tesitems.Utils.ClientThings;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
                new StagesEditor(quest).show()));

        layout.add(new HorizontalDivider());
        layout.add(new HorizontalLayout()
                .add(new Button("Save").setOnClick(e -> {
                    TESItems.networkWrapper.sendToServer(new MessageQuest(quest));
                    new GuiAlertDialog("Quest saved").show();
                })).add(new Button("Back").setOnClick(e ->
                        ScreenStack.close()))
                .center(true).setWidthOverride(ElementBase.FILL));
    }

    @Override
    public boolean close() {
        new GuiConfirmationDialog("Are you sure you don't want to save changes?", ScreenStack::forceClose).show();
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
            public ArrayList<StageElement> stages = new ArrayList<>();
            private FontRenderer fontRenderer = ClientThings.fontRenderer;
            private int offsetX = 0, offsetY = 0;
            private HorizontalLayout buttons;
            private int lastMouseX = -1, lastMouseY = -1;
            private int startX = -1, startY = -1;
            private boolean ignoreUntilReleased = false;

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

                ignoreUntilReleased = true; //mouse is down while creating this screen, so ignore this click
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
                if (!ignoreUntilReleased && Mouse.isButtonDown(0) && ScreenStack.lastScreen() == StagesEditor.this) {
                    if (lastMouseX == -1) {
                        lastMouseX = mouseX;
                        startX = mouseX;
                    }
                    if (lastMouseY == -1) {
                        lastMouseY = mouseY;
                        startY = mouseY;
                    }

                    int deltaX = lastMouseX - mouseX;
                    int deltaY = lastMouseY - mouseY;

                    offsetX -= deltaX;
                    offsetY -= deltaY;

                    doLayout();
                    lastMouseX = mouseX;
                    lastMouseY = mouseY;
                } else {
                    if (lastMouseX != -1) {
                        int deltaX = lastMouseX - startX;
                        if (deltaX < 0) deltaX = -deltaX;
                        int deltaY = lastMouseY - startY;
                        if (deltaY < 0) deltaY = -deltaY;

                        if (!ignoreUntilReleased && deltaX < 2 && deltaY < 2)
                            stages.forEach(s -> s.checkClick(lastMouseX, lastMouseY));
                    }

                    lastMouseX = -1;
                    lastMouseY = -1;

                    if (!Mouse.isButtonDown(0))
                        ignoreUntilReleased = false;
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
                public Consumer<StageElement> onClick;

                public StageElement(QuestStage stage) {
                    this.stage = stage;
                    this.setOnClick(e -> {
                        new StageEditor(StagesElement.this, this).show();
                    });
                }

                public void setOnClick(Consumer<StageElement> onClick) {
                    this.onClick = onClick;
                }

                public void checkClick(int mouseX, int mouseY) {
                    if (onClick != null && mouseX > left && mouseX < right && mouseY > top && mouseY < bottom)
                        onClick.accept(this);
                }

                public int getWidth() {
                    return fontRenderer.getStringWidth(stage.textID) + 8;
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
                    fontRenderer.drawString(stage.textID, (2 * left + w - fontRenderer.getStringWidth(stage.textID)) / 2, (2 * top + h - 8) / 2, FG_COLOR);
                }
            }
        }


    }

    private class StageEditor extends Screen {
        StagesEditor.StagesElement parent;

        public StageEditor(StagesEditor.StagesElement parent, StagesEditor.StagesElement.StageElement element) {
            QuestStage copy = element.stage.copy();

            this.parent = parent;

            VerticalLayout layout = new VerticalLayout();
            layout.setPadding(4);
            layout.add(new HeaderLayout("Quest stage editor"));
            layout.add(new TextField().setHint("textID").setText(copy.textID).setOnType(tf -> copy.textID = tf.getText()));


            layout.add(new HorizontalLayout().add(new Button("Save").setOnClick(b -> {
                System.out.println("Setting " + quest.stages.get(quest.stages.indexOf(element.stage)) + " to " + copy);
                quest.stages.set(quest.stages.indexOf(element.stage), copy);
                element.stage = copy;
                parent.doLayout();
                parent.ignoreUntilReleased = true;
                ScreenStack.forceClose();
            })).add(new Button("Back").setOnClick(b -> {
                new GuiConfirmationDialog("Are you sure you don't want to save changes?", () -> {
                    parent.ignoreUntilReleased = true;
                    ScreenStack.forceClose();
                }).show();
            })));

            contentLayout.setElement(layout);
            root.doLayout();
        }

        @Override
        public boolean close() {
            new GuiConfirmationDialog("Are you sure you don't want to save changes?", () -> {
                parent.ignoreUntilReleased = true;
                ScreenStack.forceClose();
            }).show();
            return false;
        }
    }
}
