package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import ru.iammaxim.tesitems.GUI.Debugger.DebuggerWindow;
import ru.iammaxim.tesitems.GUI.Elements.Button;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.*;

import java.io.IOException;

/**
 * Created by maxim on 11/9/16 at 9:34 PM.
 */
public class Screen extends GuiScreen {
    protected Minecraft mc;
    protected ScaledResolution res;
    protected ScreenCenteredLayout root;
    protected FrameLayout contentLayout;
    protected boolean wasClicked = false;
    protected DoubleStateFrameLayout debugWindow;

    public Screen() {
        mc = Minecraft.getMinecraft();
        res = new ScaledResolution(mc);
        root = new ScreenCenteredLayout();
        contentLayout = new FancyFrameLayout();
        root.setElement(contentLayout);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void onResize(Minecraft mcIn, int w, int h) {
        super.onResize(mcIn, w, h);
        res = new ScaledResolution(mcIn);
        root.doLayout();
        root.onResize();

        ResManager.gaussianBlurShader.createBindFramebuffers(w, h);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (debugWindow == null) {
            buildDebugWindow();
        }

        if (Mouse.isButtonDown(0)) {
            if (!wasClicked) {
                root.checkClick(mouseX, mouseY);
                debugWindow.checkClick(mouseX, mouseY);
                wasClicked = true;
            }
        } else {
            wasClicked = false;
        }

        if (ResManager.enableBlur) {
            if (ResManager.gaussianBlurShader == null) {
                ResManager.loadShaders();

                if (ResManager.gaussianBlurShader == null) {
                    System.out.println("ERROR! Shader wasn't loaded!");
                    return;
                }
            }

            ResManager.gaussianBlurShader.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
            ResManager.gaussianBlurShader.loadShaderGroup(partialTicks);
            mc.getFramebuffer().bindFramebuffer(false);
        }

        root.checkHover(mouseX, mouseY);
        root.draw(mouseX, mouseY);

        debugWindow.checkHover(mouseX, mouseY);
        debugWindow.draw(mouseX, mouseY);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        root.keyTyped(typedChar, keyCode);
    }

    public void buildDebugWindow() {
        debugWindow = (DoubleStateFrameLayout) new DoubleStateFrameLayout()
                .setFirstState(
                        new VerticalLayout().add(
                                new Button("Show debug window").setOnClick(e -> {
                                    debugWindow.selectSecond();
                                    debugWindow.doLayout();
                                })))
                .setSecondState(
                        new BlurBackgroundFrameLayout().setElement(
                                new DarkBackgroundFrameLayout().setElement(
                                        new VerticalLayout()
                                                .add(new HorizontalLayout()
                                                        .add(new Button("Hide debug window").setOnClick(e -> {
                                                            debugWindow.selectFirst();
                                                            debugWindow.doLayout();
                                                        }))
                                                        .add(new Button("Rebuild tree").setOnClick(e -> {
                                                            buildDebugWindow();
                                                            debugWindow.selectSecond();
                                                            debugWindow.doLayout();
                                                        }))
                                                )
                                                .add(DebuggerWindow.buildForElement(root))
                                ))).selectFirst()
                .setBounds(8, 8, 308, res.getScaledHeight() - 8);
        debugWindow.doLayout();
    }
}
