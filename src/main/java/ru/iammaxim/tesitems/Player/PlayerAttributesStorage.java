package ru.iammaxim.tesitems.Player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import ru.iammaxim.tesitems.TESItems;

/**
 * Created by Maxim on 06.06.2016.
 */
public class PlayerAttributesStorage implements Capability.IStorage<IPlayerAttributesCapability> {
    @Override
    public NBTBase writeNBT(Capability<IPlayerAttributesCapability> capability, IPlayerAttributesCapability cap, EnumFacing enumFacing) {
        NBTTagCompound nbttag = new NBTTagCompound();
        for (String s : TESItems.ATTRIBUTES) {
            nbttag.setFloat(s, cap.getAttribute(s));
        }
        nbttag.setTag("spellbook", cap.saveSpellbook(true));
        nbttag.setTag("inventory", cap.getInventory().writeToNBT());
        nbttag.setTag("quests", cap.saveQuests());
        nbttag.setString("journal", cap.getJournal());
        nbttag.setTag("variableStorage", cap.getVariableStorage().writeToNBT());
        return nbttag;
    }

    @Override
    public void readNBT(Capability<IPlayerAttributesCapability> capability, IPlayerAttributesCapability cap, EnumFacing enumFacing, NBTBase nbtBase) {
        NBTTagCompound nbttag = (NBTTagCompound) nbtBase;
        for (String s : TESItems.ATTRIBUTES) {
            cap.setAttribute(s, nbttag.getFloat(s));
        }
        cap.loadSpellbook(nbttag.getCompoundTag("spellbook"));
        cap.getInventory().loadFromNBT(nbttag.getCompoundTag("inventory"));
        cap.loadQuests(nbttag.getCompoundTag("quests"));
        cap.setJournal(nbttag.getString("journal"));
        cap.getVariableStorage().loadValueFromNBT(nbttag.getCompoundTag("variableStorage"));
    }
}
