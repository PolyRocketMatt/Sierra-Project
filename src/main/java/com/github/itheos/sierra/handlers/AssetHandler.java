package com.github.itheos.sierra.handlers;

import com.github.itheos.sierra.Sierra;
import com.github.itheos.sierra.assets.PlaceableAsset;
import com.github.itheos.sierra.assets.SierraAsset;
import com.github.itheos.sierra.exception.MongoException;
import com.github.itheos.sierra.handlers.handles.JSONHandle;
import com.github.itheos.sierra.utils.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by PolyRocketMatt on 15/03/2021.
 *
 * Handler that processes assets.
 */

public class AssetHandler extends Handler {

    private HandlerState state;
    private JSONHandle handle;
    private ArrayList<SierraAsset> assets;
    private ArrayList<PlaceableAsset> placeableAssets;

    public AssetHandler(JSONHandle handle) {
        super();

        this.state = HandlerState.RUNNING;
        this.handle = handle;
        this.assets = new ArrayList<>();
        this.placeableAssets = new ArrayList<>();
    }

    public void downloadAssets() {
        Sierra.getDefaultLogger().log("Preparing to download assets, this can take a while");

        try {
            URL url = new URL("https://sierra-tg-server.herokuapp.com/models");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                connection.disconnect();

                String json = new BufferedReader(new InputStreamReader(url.openStream()))
                        .lines().collect(Collectors.joining("\n"));

                SierraAsset[] assets = handle.getObjectMapper().readValue(json, SierraAsset[].class);
                Sierra.getDefaultLogger().log("Found " + assets.length + " assets");

                System.out.println(assets[0].blocks.size());

                this.assets = new ArrayList<>(Arrays.asList(assets));

                install();
            } else
                throw new MongoException("Something went wrong! " + responseCode + " -> " + connection.getResponseMessage());
        } catch (Exception ex) {
            Sierra.getDefaultLogger().log("Something went wrong while downloading assets, please contact a developer");

            ex.printStackTrace();
        }
    }

    private void install() {
        for (int i = 0; i < assets.size(); i++) {
            try {
                Sierra.getDefaultLogger().log("Installing asset " + (i + 1) + "/" + assets.size());
                SierraAsset asset = assets.get(i);
                File assetFile = new File(IOUtils.getAssetPath() + asset.getName() + ".json");

                handle.getObjectMapper().writeValue(assetFile, asset);
            } catch (Exception ex) {
                Sierra.getDefaultLogger().log("Something went wrong while installing asset " + (i + 1));
            }
        }

        Sierra.getDefaultLogger().log("Successfully installed " + assets.size() + " assets!");

        convert();
    }

    public void load() {
        //  Clear assets
        this.assets.clear();

        File dir = new File(IOUtils.getAssetPath());

        if (dir.listFiles() != null)
            for (File asset : Objects.requireNonNull(dir.listFiles())) {
                try {
                    assets.add(handle.getObjectMapper().readValue(asset, SierraAsset.class));
                } catch (Exception ex) {
                    Sierra.getDefaultLogger().log("Something went wrong while loading asset " + asset.getName());
                }
            }

        Sierra.getDefaultLogger().log("Loaded " + assets.size() + " assets");

        convert();
    }

    private void convert() {
        Sierra.getDefaultLogger().log("Converting assets to in-game assets");

        for (int i = 0; i < assets.size(); i++) {
            Sierra.getDefaultLogger().log("Converting asset " + (i + 1) + "/" + assets.size());

            placeableAssets.add(new PlaceableAsset(assets.get(i)));
        }

        Sierra.getDefaultLogger().log("Successfully converted " + placeableAssets.size() + " assets!");
    }

    public ArrayList<PlaceableAsset> getPlaceableAssets() {
        return placeableAssets;
    }

    @Nullable
    @Override
    public JSONHandle getHandle() {
        return handle;
    }

    @Override
    public @NotNull HandlerState getState() {
        return state;
    }

    @Override
    public @NotNull String getName() {
        return "AssetHandler";
    }

    @Override
    public void suspend() { }
}
