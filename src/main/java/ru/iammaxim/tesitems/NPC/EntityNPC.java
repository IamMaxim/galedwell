package ru.iammaxim.tesitems.NPC;

import com.google.common.base.Objects;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.iammaxim.tesitems.Dialogs.Dialog;
import ru.iammaxim.tesitems.Items.mItems;
import ru.iammaxim.tesitems.Networking.MessageDialog;
import ru.iammaxim.tesitems.Networking.MessageFactionList;
import ru.iammaxim.tesitems.Networking.MessageNPCUpdate;
import ru.iammaxim.tesitems.Player.IPlayerAttributesCapability;
import ru.iammaxim.tesitems.TESItems;
import scala.actors.threadpool.Arrays;

import javax.annotation.Nullable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

/**
 * Created by Maxim on 19.07.2016.
 */
public class EntityNPC extends EntityLivingBase {
    public NPC npc;
    private HashMap<Type, ResourceLocation> npcTextures = new HashMap<>();
    private static final File skinCacheDir = new File("galedwell/NPCSkinCache");
    private boolean textureLoaded = false;

    public enum Type {
        SKIN
    }

    public EntityNPC(World worldIn) {
        super(worldIn);
        npc = new NPC();

        if (worldIn.isRemote)
            loadTextures();
    }

    @SideOnly(Side.CLIENT)
    public ResourceLocation getEntityTexture() {
        loadTextures();
        return Objects.firstNonNull(npcTextures.get(Type.SKIN), DefaultPlayerSkin.getDefaultSkinLegacy());
    }

    @SideOnly(Side.CLIENT)
    private void loadTextures() {
        synchronized (this) {
            if (!textureLoaded || npc.skinDirty) {
                ResourceLocation resourceLocation = new ResourceLocation("skins/" + npc.skinName);
                ITextureObject iTextureObject = Minecraft.getMinecraft().getTextureManager().getTexture(resourceLocation);

                if (iTextureObject != null && !npc.skinDirty) {
                    npcTextures.put(Type.SKIN, resourceLocation);
                } else if (Minecraft.getMinecraft().getCurrentServerData() != null) {
                    File file2 = new File(skinCacheDir, npc.skinName + ".png");
                    final IImageBuffer iImageBuffer = new ImageBufferDownload();
                    String url = "http://" + Minecraft.getMinecraft().getCurrentServerData().serverIP + ":8080/" + npc.skinName + ".png";
                    System.out.println("URL: " + url);
                    ThreadDownloadImageData threadDownloadImageData = new ThreadDownloadImageData(file2, url, DefaultPlayerSkin.getDefaultSkinLegacy(), new IImageBuffer() {
                        public BufferedImage parseUserSkin(BufferedImage image) {
                            if (iImageBuffer != null) {
                                image = iImageBuffer.parseUserSkin(image);
                            }
                            return image;
                        }

                        public void skinAvailable() {
                            if (iImageBuffer != null) {
                                iImageBuffer.skinAvailable();
                            }
                            npcTextures.put(Type.SKIN, resourceLocation);
                        }
                    });
                    Minecraft.getMinecraft().getTextureManager().loadTexture(resourceLocation, threadDownloadImageData);
                }
                npc.skinDirty = false;
            }
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return !npc.isInvulnerable && super.attackEntityFrom(source, amount);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        npc.writeToNBT(tag);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        npc.readFromNBT(tag);
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return Arrays.asList(npc.inventory.armorInventory);
    }

    @Nullable
    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
        return slotIn == EntityEquipmentSlot.MAINHAND ? npc.inventory.getMainhandItem() : (slotIn == EntityEquipmentSlot.OFFHAND ? npc.inventory.getOffhandItem() : (slotIn.getSlotType() == EntityEquipmentSlot.Type.ARMOR ? npc.inventory.armorInventory[slotIn.getIndex()] : null));
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slotIn, @Nullable ItemStack stack) {
        if (slotIn == EntityEquipmentSlot.MAINHAND) setHeldItem(EnumHand.MAIN_HAND, stack);
        else if (slotIn == EntityEquipmentSlot.OFFHAND) setHeldItem(EnumHand.OFF_HAND, stack);
        else if (slotIn.getSlotType() == EntityEquipmentSlot.Type.ARMOR)
            npc.inventory.armorInventory[slotIn.getIndex()] = stack;
    }

    @Override
    public EnumHandSide getPrimaryHand() {
        return npc.primaryHand;
    }

    public boolean isWearing(EnumPlayerModelParts part) {
        return false;
    }

    @Override
    public String getCustomNameTag() {
        return npc.name;
    }

    @Override
    public String getName() {
        return npc.name;
    }

    @Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, @Nullable ItemStack stack, EnumHand hand) {
        IPlayerAttributesCapability cap = TESItems.getCapability(player);
        if (player.worldObj.isRemote) {
            cap.setLatestNPC(npc);
            return EnumActionResult.SUCCESS;
        }

        //if latest NPC player interacted with is not this NPC, send him this NPC data (name etc.)
        if (cap.getLatestNPC() != npc) {
            TESItems.networkWrapper.sendTo(new MessageNPCUpdate(npc.getNBT()), (EntityPlayerMP) player);
            cap.setLatestNPC(npc);
        }
        if (player.getHeldItemOffhand() != null && player.getHeldItemOffhand().getItem() == mItems.itemNPCEditorTool) {
            //let the player edit this NPC
            TESItems.networkWrapper.sendTo(new MessageFactionList(), (EntityPlayerMP) player);
            player.openGui(TESItems.instance, TESItems.guiNPCEditor, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
        } else {
            //open dialog GUI
            TESItems.networkWrapper.sendTo(new MessageDialog(Dialog.createDialogForPlayer(npc, player).saveToNBT()), (EntityPlayerMP) player);
            player.openGui(TESItems.instance, TESItems.guiNpcDialog, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString(npc.name);
    }
}
