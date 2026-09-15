package net.ntrdeal.realapi.client.item.stack_holder;

import com.mojang.serialization.DataResult;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.ntrdeal.realapi.item.stack_holder.StackHolder;
import org.apache.commons.lang3.math.Fraction;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Environment(EnvType.CLIENT)
public class BundleLikeTooltip<T extends StackHolder<T>> implements ClientTooltipComponent {
    public static final Identifier PROGRESSBAR_BORDER_SPRITE = Identifier.withDefaultNamespace("container/bundle/bundle_progressbar_border");
    public static final Identifier PROGRESSBAR_FILL_SPRITE = Identifier.withDefaultNamespace("container/bundle/bundle_progressbar_fill");
    public static final Identifier PROGRESSBAR_FULL_SPRITE = Identifier.withDefaultNamespace("container/bundle/bundle_progressbar_full");
    public static final Identifier SLOT_HIGHLIGHT_BACK_SPRITE = Identifier.withDefaultNamespace("container/bundle/slot_highlight_back");
    public static final Identifier SLOT_HIGHLIGHT_FRONT_SPRITE = Identifier.withDefaultNamespace("container/bundle/slot_highlight_front");
    public static final Identifier SLOT_BACKGROUND_SPRITE = Identifier.withDefaultNamespace("container/bundle/slot_background");
    public static final Component BUNDLE_FULL_TEXT = Component.translatable("item.minecraft.bundle.full");
    public static final Component BUNDLE_EMPTY_TEXT = Component.translatable("item.minecraft.bundle.empty");
    public static final Component BUNDLE_EMPTY_DESCRIPTION = Component.translatable("item.minecraft.bundle.empty.description");
    public final T holder;

    public BundleLikeTooltip(T holder) {
        this.holder = holder;
    }

    @Override
    public int getHeight(Font font) {
        return this.holder.isEmpty() ? this.getEmptyBundleBackgroundHeight(font) : this.backgroundHeight();
    }

    @Override
    public int getWidth(Font font) {
        return 96;
    }

    @Override
    public boolean showTooltipWithItemInHand() {
        return true;
    }

    public int getEmptyBundleBackgroundHeight(Font font) {
        return this.getEmptyBundleDescriptionTextHeight(font) + 13 + 8;
    }

    public int backgroundHeight() {
        return this.itemGridHeight() + 13 + 8;
    }

    public int itemGridHeight() {
        return this.gridSizeY() * 24;
    }

    public int getContentXOffset(int tooltipWidth) {
        return (tooltipWidth - 96) / 2;
    }

    public int gridSizeY() {
        return Mth.positiveCeilDiv(this.slotCount(), 4);
    }

    public int slotCount() {
        return Math.min(12, this.holder.size());
    }

    @Override
    public void extractImage(Font font, int x, int y, int w, int h, GuiGraphicsExtractor graphics) {
        DataResult<Fraction> weight = this.holder.weight();
        if (weight.isError()) return;
        if (this.holder.isEmpty()) this.extractEmptyBundleTooltip(font, x, y, w, h, graphics);
        else this.extractBundleWithItemsTooltip(font, x, y, w, h, graphics, weight.getOrThrow());
    }

    public void extractEmptyBundleTooltip(Font font, int x, int y, int w, int h, GuiGraphicsExtractor graphics) {
        int left = x + this.getContentXOffset(w);
        this.extractEmptyBundleDescriptionText(left, y, font, graphics);
        this.extractProgressbar(left, y + this.getEmptyBundleDescriptionTextHeight(font) + 4, font, graphics, Fraction.ZERO);
    }

    public void extractBundleWithItemsTooltip(
            Font font, int x, int y, int w, int h, GuiGraphicsExtractor graphics, Fraction weight
    ) {
        boolean isOverflowing = this.holder.size() > 12;
        List<ItemStackTemplate> shownItems = this.getShownItems(this.holder.getNumberOfItemsToShow());
        int xStartPos = x + this.getContentXOffset(w) + 96;
        int yStartPos = y + this.gridSizeY() * 24;
        int slotNumber = 1;

        for (int rowNumber = 1; rowNumber <= this.gridSizeY(); rowNumber++) {
            for (int columnNumber = 1; columnNumber <= 4; columnNumber++) {
                int drawX = xStartPos - columnNumber * 24;
                int drawY = yStartPos - rowNumber * 24;
                if (this.shouldRenderSurplusText(isOverflowing, columnNumber, rowNumber)) {
                    this.extractCount(drawX, drawY, this.getAmountOfHiddenItems(shownItems), font, graphics);
                } else if (this.shouldRenderItemSlot(shownItems, slotNumber)) {
                    this.extractSlot(slotNumber, drawX, drawY, shownItems, slotNumber, font, graphics);
                    slotNumber++;
                }
            }
        }

        this.extractSelectedItemTooltip(font, graphics, x, y, w);
        this.extractProgressbar(x + this.getContentXOffset(w), y + this.itemGridHeight() + 4, font, graphics, weight);
    }

    public List<ItemStackTemplate> getShownItems(int amountOfItemsToShow) {
        int lastToDisplay = Math.min(this.holder.size(), amountOfItemsToShow);
        return this.holder.stacks().subList(0, lastToDisplay);
    }

    public boolean shouldRenderSurplusText(boolean isOverflowing, int column, final int row) {
        return isOverflowing && column * row == 1;
    }

    public boolean shouldRenderItemSlot(List<? extends ItemInstance> shownItems, int slotNumber) {
        return shownItems.size() >= slotNumber;
    }

    public int getAmountOfHiddenItems(List<ItemStackTemplate> shownItems) {
        return this.holder.stacks().stream().skip(shownItems.size()).mapToInt(ItemInstance::count).sum();
    }

    public void extractSlot(
            int slotNumber, int drawX, int drawY,
            List<ItemStackTemplate> shownItems, int slotIndex,
            Font font, GuiGraphicsExtractor graphics
    ) {
        int itemVisualOrderIndex = shownItems.size() - slotNumber;
        boolean hasHighlight = itemVisualOrderIndex == this.holder.index();
        ItemStack item = shownItems.get(itemVisualOrderIndex).create();
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, hasHighlight ? SLOT_HIGHLIGHT_BACK_SPRITE : SLOT_BACKGROUND_SPRITE, drawX, drawY, 24, 24);
        graphics.item(item, drawX + 4, drawY + 4, slotIndex);
        graphics.itemDecorations(font, item, drawX + 4, drawY + 4);
        if (hasHighlight) graphics.blitSprite(RenderPipelines.GUI_TEXTURED, SLOT_HIGHLIGHT_FRONT_SPRITE, drawX, drawY, 24, 24);
    }

    public void extractCount(int drawX, int drawY, int hiddenItemCount, Font font, GuiGraphicsExtractor graphics) {
        graphics.centeredText(font, "+" + hiddenItemCount, drawX + 12, drawY + 10, -1);
    }

    public void extractSelectedItemTooltip(Font font, GuiGraphicsExtractor graphics, int x, int y, int w) {
        ItemStackTemplate selectedItem = this.holder.getSelectedItem();
        if (selectedItem == null) return;

        ItemStack itemStack = selectedItem.create();
        Component selectedItemName = itemStack.getStyledHoverName();
        int textWidth = font.width(selectedItemName.getVisualOrderText());
        int centerTooltip = x + w / 2 - 12;
        ClientTooltipComponent selectedItemNameTooltip = ClientTooltipComponent.create(selectedItemName.getVisualOrderText());
        graphics.tooltip(
                font,
                List.of(selectedItemNameTooltip),
                centerTooltip - textWidth / 2,
                y - 15,
                DefaultTooltipPositioner.INSTANCE,
                itemStack.get(DataComponents.TOOLTIP_STYLE),
                false
        );
    }

    public void extractProgressbar(int x, int y, Font font, GuiGraphicsExtractor graphics, Fraction weight) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.getProgressBarTexture(weight), x + 1, y, this.getProgressBarFill(weight), 13);
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, PROGRESSBAR_BORDER_SPRITE, x, y, 96, 13);
        Component progressBarFillText = this.getProgressBarFillText(weight);
        if (progressBarFillText != null) graphics.centeredText(font, progressBarFillText, x + 48, y + 3, -1);
    }

    public void extractEmptyBundleDescriptionText(int x, int y, Font font, GuiGraphicsExtractor graphics) {
        graphics.textWithWordWrap(font, BUNDLE_EMPTY_DESCRIPTION, x, y, 96, -5592406);
    }

    public int getEmptyBundleDescriptionTextHeight(Font font) {
        return font.split(BUNDLE_EMPTY_DESCRIPTION, 96).size() * 9;
    }

    public int getProgressBarFill(Fraction weight) {
        return Mth.clamp(Mth.mulAndTruncate(weight, 94), 0, 94);
    }

    public Identifier getProgressBarTexture(Fraction weight) {
        return weight.compareTo(Fraction.ONE) >= 0 ? PROGRESSBAR_FULL_SPRITE : PROGRESSBAR_FILL_SPRITE;
    }

    @Nullable
    public Component getProgressBarFillText(Fraction weight) {
        return weight.compareTo(Fraction.ZERO) == 0 ? BUNDLE_EMPTY_TEXT : weight.compareTo(Fraction.ONE) >= 0 ? BUNDLE_FULL_TEXT : null;
    }
}
