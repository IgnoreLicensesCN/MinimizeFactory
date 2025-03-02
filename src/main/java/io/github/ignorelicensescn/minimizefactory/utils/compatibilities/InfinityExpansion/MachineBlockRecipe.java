package io.github.ignorelicensescn.minimizefactory.utils.compatibilities.InfinityExpansion;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineRecipe;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public final class MachineBlockRecipe extends MachineRecipe {

    public final String[] strings;
    public final int[] amounts;
    public final ItemStack output;
    public final ItemStack[] input;
    public int ticksPerOut = 1;
//    private Map<String, MachineInput> lastMatch;

    public MachineBlockRecipe(ItemStack[] output, String[] strings, int[] amounts, int ticksPerOut, ItemStack[] newEmpty) {
        super(ticksPerOut,newEmpty,output);
        assert strings.length == amounts.length;
        this.setTicks(ticksPerOut);
        this.ticksPerOut = ticksPerOut;
        this.output = output[0];
        this.amounts = amounts;//useless
        this.strings = strings;//useless
        this.input = newEmpty;
        for (int i=0;i<strings.length;i+=1){
            ItemStack itemStack;
            SlimefunItem sfItem = SlimefunItem.getById(strings[i]);
            if (sfItem != null) {
                itemStack = sfItem.getItem().clone();
            }else {
                itemStack = new ItemStack(Material.getMaterial(strings[i]));
            }
            itemStack.setAmount(amounts[i]);
            newEmpty[i] = itemStack;
        }
    }


}
