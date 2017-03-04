package ru.iammaxim.tesitems.Proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.iammaxim.tesitems.Blocks.BlockChestTileEntity;
import ru.iammaxim.tesitems.Blocks.mBlocks;
import ru.iammaxim.tesitems.Client.ClientHandler;
import ru.iammaxim.tesitems.ConfigManager;
import ru.iammaxim.tesitems.GUI.KeyBindings;
import ru.iammaxim.tesitems.GUI.ResManager;
import ru.iammaxim.tesitems.GUI.ScreenStack;
import ru.iammaxim.tesitems.Magic.EntityFlyingSpell;
import ru.iammaxim.tesitems.Magic.EntityRangedSpellEffect;
import ru.iammaxim.tesitems.Magic.RenderEntityFlyingSpell;
import ru.iammaxim.tesitems.Magic.RenderEntityRangedSpellEffect;
import ru.iammaxim.tesitems.NPC.EntityNPC;
import ru.iammaxim.tesitems.NPC.RenderNPC;
import ru.iammaxim.tesitems.Utils.ClientThings;

import java.io.IOException;


/**
 * Created by maxim on 3/3/17 at 7:01 PM.
 */
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        super.preInit(event);

        //load settings from config
        ResManager.enableBlur = ConfigManager.getBool("enableBlur");

        MinecraftForge.EVENT_BUS.register(new ClientHandler());
        mBlocks.initModels();

        RenderingRegistry.registerEntityRenderingHandler(EntityRangedSpellEffect.class, RenderEntityRangedSpellEffect::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFlyingSpell.class, RenderEntityFlyingSpell::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityNPC.class, RenderNPC::new);
    }

    @Override
    public void init(FMLInitializationEvent event) {
//        mBlocks.initModels();

        ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(mBlocks.block_chest_01), 0, BlockChestTileEntity.class);


        KeyBindings.register();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        ClientThings.loadFonts();
        ScreenStack.instance = new ScreenStack();
    }
}
