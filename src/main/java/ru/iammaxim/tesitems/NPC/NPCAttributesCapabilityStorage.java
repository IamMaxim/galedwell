package ru.iammaxim.tesitems.NPC;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import ru.iammaxim.tesitems.Scripting.GaledwellLang.Values.ValueObject;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by Maxim on 06.06.2016.
 */
public class NPCAttributesCapabilityStorage implements Capability.IStorage<INPCAttributesCapability> {
    @Override
    public NBTBase writeNBT(Capability<INPCAttributesCapability> capability, INPCAttributesCapability cap, EnumFacing enumFacing) {
        NBTTagCompound nbttag = new NBTTagCompound();
        for (String s : TESItems.ATTRIBUTES) {
            nbttag.setFloat(s, cap.getAttribute(s));
        }
        nbttag.setTag("spellbook", cap.saveSpellbook(true));
        nbttag.setTag("inventory", cap.getInventory().writeToNBT());
        nbttag.setTag("variableStorage", cap.getVariableStorage().writeToNBT());
        nbttag.setFloat("magicka", cap.getMagicka());
        return nbttag;
    }

    @Override
    public void readNBT(Capability<INPCAttributesCapability> capability, INPCAttributesCapability cap, EnumFacing enumFacing, NBTBase nbtBase) {
        NBTTagCompound tag = (NBTTagCompound) nbtBase;
        for (String s : TESItems.ATTRIBUTES) {
            cap.setAttribute(s, tag.getFloat(s));
        }
        cap.loadSpellbook(tag.getCompoundTag("spellbook"));
        cap.getInventory().loadFromNBT(tag.getCompoundTag("inventory"));
        cap.setVariableStorage(ValueObject.loadValueFromNBT(tag.getCompoundTag("variableStorage")));
        cap.setMagicka(tag.getFloat("magicka"));
    }
}
