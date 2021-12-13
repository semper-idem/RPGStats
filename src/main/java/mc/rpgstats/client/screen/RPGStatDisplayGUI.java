package mc.rpgstats.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import mc.rpgstats.main.RPGStats;
import mc.rpgstats.main.RPGStatsClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class RPGStatDisplayGUI extends LightweightGuiDescription {
    TextRenderer tr = MinecraftClient.getInstance().textRenderer;
    ArrayList<Identifier> data = new ArrayList<>();
    
    public RPGStatDisplayGUI() {
        super();
        data.addAll(RPGStatsClient.currentStats.keySet());
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(288, 198);

        MutableText title = new LiteralText(" Skills ").formatted(Formatting.DARK_GRAY, Formatting.BOLD);
        WLabel guiTitle = new WLabel(title);
        System.out.println(tr.getWidth(title));
        root.add(guiTitle, 7, 1);
    
        BiConsumer<Identifier, StatEntry> configurator = (Identifier identifier, StatEntry entry) -> {
            int level = RPGStatsClient.currentStats.get(identifier).getLeft();
            int xp = RPGStatsClient.currentStats.get(identifier).getRight();
            
            String name = RPGStatsClient.nameMap.get(identifier);
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            entry.name.setText(new LiteralText("§6§l" + name + ":"));
            entry.level.setText(new LiteralText("§6[§f" + level + "§6]"));
            entry.level.setLocation(117 - tr.getWidth(entry.level.getText()), 5);
            entry.xp.setText(new LiteralText("§6[§f" + xp + "§6/§f" + RPGStats.calculateXpNeededToReachLevel(level + 1) + "§6]"));
            entry.xp.setLocation(235 - tr.getWidth(entry.xp.getText()), 5);
        };

        WListPanel<Identifier, StatEntry> list = new WListPanel<>(data, StatEntry::new, configurator);
        list.setListItemHeight(16);
        list.setBackgroundPainter(BackgroundPainter.createColorful(0xDB423792));
        root.add(list, 1, 2, 14, 8);
        root.validate(this);
    }


    
    public static class StatEntry extends WPlainPanel {
        TextRenderer tr = MinecraftClient.getInstance().textRenderer;
        WLabel name;
        WLabel levelLabel;
        WLabel level;
        WLabel xpLabel;
        WLabel xp;
        
        public StatEntry() {
            name = new WLabel("Foo");
            this.add(name, 5, 5, 5*18, 18);
            levelLabel = new WLabel(new LiteralText("§6Lvl:"));
            this.add(levelLabel, 72, 5, 6*18, 18);
            level = new WLabel("0");
            this.add(level, 102 - tr.getWidth(level.getText()), 5, 6*18, 18);
            xpLabel = new WLabel(new LiteralText("§6Exp:"));
            this.add(xpLabel, 139, 5, 6*18, 18);
            xp = new WLabel("[0/0]");
            this.add(xp, 174 - tr.getWidth(xp.getText()), 5, 6*18, 18);
            
            this.setSize(7*18, 2*18);
        }
    }


}
