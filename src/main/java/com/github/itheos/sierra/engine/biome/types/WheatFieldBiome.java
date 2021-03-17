package com.github.itheos.sierra.engine.biome.types;

import com.github.itheos.sierra.assets.PlaceableAsset;
import com.github.itheos.sierra.assets.SierraAsset;
import com.github.itheos.sierra.engine.SierraWorld;
import com.github.itheos.sierra.engine.biome.BiomeManager;
import com.github.itheos.sierra.engine.biome.SierraBiome;
import com.github.itheos.sierra.engine.generator.biome.WheatFieldGenerator;
import com.github.itheos.sierra.utils.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

/**
 * Created by PolyRocketMatt on 13/03/2021.
 */

public class WheatFieldBiome extends SierraBiome {

    private Random random;
    private WheatFieldGenerator generator;

    public WheatFieldBiome(SierraWorld world) {
        super(world, BiomeManager.Biomes.WHEAT_FIELDS);

        this.random = new Random();
        this.generator = new WheatFieldGenerator(world.getConfig().<Integer>get("worlds." + world.getName() + ".seeds.biome.wheat-fields"));
    }

    @Override
    public WheatFieldGenerator getGenerator() {
        return generator;
    }

    @Override
    public int computeBiome(int cX, int cZ, int x, int z, int base, float baseValue) {
        //  Create a generator value
        float value = MathUtils.sigmoid(-0.60f, 0.0f, 14.0f, 1.0f, generator.noise(cX + x, cZ + z));

        //  Calculate influence value
        float influence = MathUtils.normalize(0.98f, 1.0f, baseValue);
        float finalValue = influence * value;

        //  Multiply actual value with the influence and the multiplier
        return base + (int) (finalValue * generator.getMultiplier());
    }

    //  TODO: Make biomes 3D
    @Override
    public ChunkGenerator.ChunkData buildBiome(ChunkGenerator.ChunkData data, int x, int y, int z) {
        data.setBlock(x, y + 1, z, Material.WHEAT);
        data.setBlock(x, y, z, Material.FARMLAND);
        data.setBlock(x, 0, z, Material.BEDROCK);

        for (int offsetY = y - 1; offsetY >= 66; offsetY--)
            data.setBlock(x, offsetY, z, Material.DIRT);

        for (int offsetY = 65; offsetY >= 1; offsetY--)
            data.setBlock(x, offsetY, z, Material.STONE);

        if (data.getBlockData(x, y + 1, z) instanceof Ageable) {
            Ageable crop = (Ageable) data.getBlockData(x, y + 1, z);

            crop.setAge(crop.getMaximumAge());
            data.setBlock(x, y + 1, z, crop);
        }

        return data;
    }

    @Override
    public ChunkGenerator.ChunkData populate(ChunkGenerator.ChunkData data, int x, int y, int z) {
        //  TODO: Fix tree generation
        //  TODO: For now, max 1 tree per chunk
        PlaceableAsset asset = assets.get(random.nextInt(assets.size()));

        if (random.nextInt(10000) >= 9985) {
            //  Check if all blocks of the asset fit
            boolean fit = true;
            for (PlaceableAsset.AssetData assetData : asset.getData()) {
                if (assetData.getOffsetX() < 0 || assetData.getOffsetX() > 15 ||
                        assetData.getOffsetZ() < 0 || assetData.getOffsetZ() > 15) {
                    fit = false;

                    break;
                }
            }

            if (fit) {
                for (PlaceableAsset.AssetData assetData : asset.getData()) {
                    int oX = assetData.getOffsetX();
                    int oY = assetData.getOffsetY();
                    int oZ = assetData.getOffsetZ();

                    data.setBlock(x + oX, y + oY, z + oZ, assetData.getMaterial());
                    data.setBlock(oX, oY, oZ, Bukkit.createBlockData(assetData.getData()));
                }
            }
        }

        return data;
    }
}
