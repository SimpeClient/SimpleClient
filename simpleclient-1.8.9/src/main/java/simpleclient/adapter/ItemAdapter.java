package simpleclient.adapter;

import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ItemAdapter {
    public static net.minecraft.item.ItemStack adapt(simpleclient.item.ItemStack stack) {
        net.minecraft.item.Item item = net.minecraft.item.Item.REGISTRY.get(new Identifier(stack.getItem().getId()));
        net.minecraft.item.ItemStack adapted = new net.minecraft.item.ItemStack(item);
        adapted.count = stack.getCount();
        adapted.setDamage(stack.getDamage());
        return adapted;
    }

    public static simpleclient.item.ItemStack adapt(net.minecraft.item.ItemStack stack) {
        simpleclient.item.Item item = new simpleclient.item.Item(net.minecraft.item.Item.REGISTRY.getIdentifier(stack.getItem()).toString());
        simpleclient.item.ItemStack adapted = new simpleclient.item.ItemStack(item);
        adapted.setCount(stack.count);
        adapted.setDamage(stack.getDamage());
        return adapted;
    }

    public static Function<net.minecraft.entity.player.PlayerInventory, net.minecraft.inventory.slot.Slot> adapt(simpleclient.item.EquipmentSlot slot) {
        if (slot == simpleclient.item.EquipmentSlot.MAINHAND) return inv -> new net.minecraft.inventory.slot.Slot(inv, inv.selectedSlot, inv.selectedSlot, 0);
        if (slot == simpleclient.item.EquipmentSlot.OFFHAND) return null;
        if (slot == simpleclient.item.EquipmentSlot.FEET) return inv -> new net.minecraft.inventory.slot.Slot(inv, 36, 0, 4);
        if (slot == simpleclient.item.EquipmentSlot.LEGS) return inv -> new net.minecraft.inventory.slot.Slot(inv, 37, 1, 4);
        if (slot == simpleclient.item.EquipmentSlot.CHEST) return inv -> new net.minecraft.inventory.slot.Slot(inv, 38, 2, 4);
        if (slot == simpleclient.item.EquipmentSlot.HEAD) return inv -> new net.minecraft.inventory.slot.Slot(inv, 39, 3, 4);
        return null;
    }
}