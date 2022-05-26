package fr.zunf1x.ztronics.machines.coalgenerator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCoalGenerator extends Container {

    private TileEntityCoalGenerator tile;
    private int	burnTimeLeft = 0;
    private int energy = 0;
    private int capacity = 0;

    public ContainerCoalGenerator(TileEntityCoalGenerator tile, InventoryPlayer playerInventory) {
        this.tile = tile;

        this.addSlotToContainer(new Slot(tile, 0, 80, 35));

        addPlayerInventory(playerInventory);
    }

    private void addPlayerInventory(InventoryPlayer inventoryPlayer) {
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tile.isUsableByPlayer(player);
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tile);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for(int i = 0; i < this.listeners.size(); ++i) {
            IContainerListener icontainerlistener = (IContainerListener) this.listeners
                    .get(i);

            if (this.burnTimeLeft != this.tile.getField(0)) {
                icontainerlistener.sendWindowProperty(this, 0,
                        this.tile.getField(0));
            }

            if (this.energy != this.tile.getField(1)) {
                icontainerlistener.sendWindowProperty(this, 1,
                        this.tile.getField(1));
            }

            if (this.capacity != this.tile.getField(2)) {
                icontainerlistener.sendWindowProperty(this, 2,
                        this.tile.getField(2));
            }
        }

        this.burnTimeLeft = this.tile.getField(0);
        this.energy = this.tile.getField(1);
        this.capacity = this.tile.getField(2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        this.tile.setField(id, data);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack stackToReturn = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            stackToReturn = stack.copy();

            if (index < 1) {
                if (!this.mergeItemStack(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(stack, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

        }
        return stackToReturn;
    }
}
