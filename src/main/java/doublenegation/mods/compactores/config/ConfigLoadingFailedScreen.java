package doublenegation.mods.compactores.config;

import doublenegation.mods.compactores.CompactOres;
import doublenegation.mods.compactores.Utils;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class ConfigLoadingFailedScreen extends Utils.ReturningScreen {

    private static final String I18N_KEY_BASE = "gui." + CompactOres.MODID + ".configloadfailure.";

    private final ITextComponent message;
    private final ITextComponent buttonTextQuit;
    private final ITextComponent buttonTextResetAndQuit;
    private final Button.IPressable onQuit;
    private final Button.IPressable onResetAndQuit;

    private final List<String> wrappedMessage = new ArrayList<>();

    protected ConfigLoadingFailedScreen(String errorMessage, Button.IPressable onQuit, Button.IPressable onResetAndQuit) {
        super(new TranslationTextComponent(I18N_KEY_BASE + "title"));
        message = new TranslationTextComponent(I18N_KEY_BASE + "message", errorMessage);
        buttonTextQuit = new TranslationTextComponent(I18N_KEY_BASE + "deny");
        buttonTextResetAndQuit = new TranslationTextComponent(I18N_KEY_BASE + "confirm");
        this.onResetAndQuit = onResetAndQuit;
        this.onQuit = onQuit;
    }

    @Override
    protected void init() {
        super.init();
        wrappedMessage.clear();
        wrappedMessage.addAll(this.font.listFormattedStringToWidth(message.getFormattedText(), this.width - 50));
        int i = (wrappedMessage.size() + 1) * 9;
        this.addButton(new Button(this.width / 2 - 150, i + 100 - 30, 300, 20, buttonTextQuit.getFormattedText(), onQuit));
        this.addButton(new Button(this.width / 2 - 150, i + 124 - 30, 300, 20, buttonTextResetAndQuit.getFormattedText(), onResetAndQuit));
    }

    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 30, 0xFFFFFF);
        int y = 50;
        for(String s : wrappedMessage) {
            this.drawCenteredString(this.font, s, this.width / 2, y, 0xFFFFFF);
            y += 9;
        }
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

}
