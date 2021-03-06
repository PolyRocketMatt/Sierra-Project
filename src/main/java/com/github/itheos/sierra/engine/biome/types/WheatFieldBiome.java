package com.github.itheos.sierra.engine.biome.types;

import com.github.itheos.sierra.assets.PlaceableAsset;
import com.github.itheos.sierra.engine.SierraWorld;
import com.github.itheos.sierra.engine.biome.BiomeController;
import com.github.itheos.sierra.engine.biome.SierraBiome;
import com.github.itheos.sierra.engine.generator.climate.PrecipitationGenerator;
import com.github.itheos.sierra.engine.generator.climate.TemperatureGenerator;
import com.github.itheos.sierra.engine.generator.climate.WindGenerator;
import com.github.itheos.sierra.engine.generator.ProceduralRock;
import com.github.itheos.sierra.engine.generator.biome.WheatFieldGenerator;
import com.github.itheos.sierra.utils.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.Ageable;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by PolyRocketMatt on 13/03/2021.
 *
 * Wheat Field Biome. An idyllic biome
 * with fields of wheat mixed in with some
 * roads and trees.
 */

public class WheatFieldBiome extends SierraBiome {

    private Random random;
    private WheatFieldGenerator generator;

    private static TemperatureGenerator.TemperatureLevel[] temperatureLevels =
            new TemperatureGenerator.TemperatureLevel[] { TemperatureGenerator.TemperatureLevel.LUKEWARM };
    private static WindGenerator.WindLevel[] windLevels =
            new WindGenerator.WindLevel[] { WindGenerator.WindLevel.CALM, WindGenerator.WindLevel.WINDY };
    private static PrecipitationGenerator.PrecipitationLevel[] precipitationLevels =
            new PrecipitationGenerator.PrecipitationLevel[] { PrecipitationGenerator.PrecipitationLevel.REGULAR_WET, PrecipitationGenerator.PrecipitationLevel.DRY };

    public WheatFieldBiome(SierraWorld world) {
        super(world, BiomeController.BiomeType.WHEAT_FIELDS);

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
        } else if (random.nextInt(10000) >= 9900) {
            int size = random.nextInt(5);
            size = 5;
            if (x > 1 && z > 1 && x + size < 15 && z + size < 15) {
                int[][][] rock = ProceduralRock.generate(size, 0.1f);

                for (int i = 0; i < rock.length; i++) {
                    for (int j = 0; j < rock[0].length; j++) {
                        for (int k = 0; k < rock[0][0].length; k++) {
                            if (rock[i][j][k] == 1)
                                data.setBlock(x + i, y + k, z + j, Material.STONE);
                        }
                    }
                }
            }
        }

        return data;
    }

    public static String[] getKeys() {
        List<String> keys = new ArrayList<>();

        for (TemperatureGenerator.TemperatureLevel temperatureLevel : temperatureLevels)
            for (WindGenerator.WindLevel windLevel : windLevels)
                for (PrecipitationGenerator.PrecipitationLevel precipitationLevel : precipitationLevels)
                    keys.add(String.join(".", temperatureLevel.getKey(), windLevel.getKey(), precipitationLevel.getKey()));
        return keys.toArray(new String[keys.size()]);
    }
}
