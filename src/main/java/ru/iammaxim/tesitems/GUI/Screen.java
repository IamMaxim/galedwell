package ru.iammaxim.tesitems.GUI;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import ru.iammaxim.tesitems.GUI.Debugger.DebuggerWindow;
import ru.iammaxim.tesitems.GUI.Elements.Button;
import ru.iammaxim.tesitems.GUI.Elements.Layouts.*;

import java.io.IOException;

/**
 * Created by maxim on 11/9/16 at 9:34 PM.
 */
public class Screen {
    protected Minecraft mc;
    protected ScaledResolution res;
    protected ScreenCenteredLayout root;
    protected FrameLayout contentLayout;

    protected DoubleStateFrameLayout debugWindow;

    public Screen() {
        mc = Minecraft.getMinecraft();
        res = new ScaledResolution(mc);
        root = new ScreenCenteredLayout();
        contentLayout = new FancyFrameLayout();
        root.setElement(contentLayout);
    }

    public void onResize(Minecraft mcIn, int w, int h) {
        res = new ScaledResolution(mcIn);
        root.doLayout();
        root.onResize();

        ResManager.gaussianBlurShader.createBindFramebuffers(w, h);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (debugWindow == null) {
            buildDebugWindow();
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

    public void checkClick(int mouseX, int mouseY) {
        root.checkClick(mouseX, mouseY);
        debugWindow.checkClick(mouseX, mouseY);
    }

    public void keyTyped(char typedChar, int keyCode) throws IOException {
        root.keyTyped(typedChar, keyCode);
    }

    public boolean close() {
        return true;
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

    public void show() {
        ScreenStack.addScreen(this);
    }
}
