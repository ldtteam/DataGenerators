package com.ldtteam.data;

import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@ApiStatus.OverrideOnly
public abstract class LanguageProvider implements DataProvider {

    private final Collection<SubProvider> subProviders;

    private final Map<String, String> data = new TreeMap<>();
    private final DataGenerator gen;
    private final String modid;
    private final String locale;

    public LanguageProvider(DataGenerator gen, String modid, String locale, Collection<SubProvider> subProviders) {
        this.subProviders = subProviders;
        this.gen = gen;
        this.modid = modid;
        this.locale = locale;
    }

    private void addTranslations() {
        final InternalAcceptor acceptor = new InternalAcceptor();
        for (final SubProvider subProvider : subProviders) {
            subProvider.addTranslations(acceptor);
        }
    }

    @Override
    public final @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        addTranslations();

        if (!data.isEmpty())
            return save(cache, this.gen.getPackOutput().getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(this.modid).resolve("lang").resolve(this.locale + ".json"));

        return CompletableFuture.allOf();
    }

    private CompletableFuture<?> save(CachedOutput cache, Path target) {
        JsonObject json = new JsonObject();
        this.data.forEach(json::addProperty);

        return DataProvider.saveStable(cache, json, target);
    }

    public interface SubProvider {

        void addTranslations(final LanguageAcceptor acceptor);
    }

    public interface LanguageAcceptor {
        void addBlock(Supplier<? extends Block> key, String name);

        void add(Block key, String name);

        void addItem(Supplier<? extends Item> key, String name);

        void add(Item key, String name);

        void addItemStack(Supplier<ItemStack> key, String name);

        void add(ItemStack key, String name);

        void addEnchantment(Supplier<? extends Enchantment> key, String name);

        void add(Enchantment key, String name);

        void addEffect(Supplier<? extends MobEffect> key, String name);

        void add(MobEffect key, String name);

        void addEntityType(Supplier<? extends EntityType<?>> key, String name);

        void add(EntityType<?> key, String name);

        void add(String key, String value);
    }

    private final class InternalAcceptor implements LanguageAcceptor {
        public void addBlock(Supplier<? extends Block> key, String name) {
            add(key.get(), name);
        }

        public void add(Block key, String name) {
            add(key.getDescriptionId(), name);
        }

        public void addItem(Supplier<? extends Item> key, String name) {
            add(key.get(), name);
        }

        public void add(Item key, String name) {
            add(key.getDescriptionId(), name);
        }

        public void addItemStack(Supplier<ItemStack> key, String name) {
            add(key.get(), name);
        }

        public void add(ItemStack key, String name) {
            add(key.getDescriptionId(), name);
        }

        public void addEnchantment(Supplier<? extends Enchantment> key, String name) {
            add(key.get(), name);
        }

        public void add(Enchantment key, String name) {
            add(key.getDescriptionId(), name);
        }

        public void addEffect(Supplier<? extends MobEffect> key, String name) {
            add(key.get(), name);
        }

        public void add(MobEffect key, String name) {
            add(key.getDescriptionId(), name);
        }

        public void addEntityType(Supplier<? extends EntityType<?>> key, String name) {
            add(key.get(), name);
        }

        public void add(EntityType<?> key, String name) {
            add(key.getDescriptionId(), name);
        }

        public void add(String key, String value) {
            if (data.put(key, value) != null)
                throw new IllegalStateException("Duplicate translation key " + key);
        }
    }
}
