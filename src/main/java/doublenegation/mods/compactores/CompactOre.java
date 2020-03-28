package doublenegation.mods.compactores;

import net.minecraft.block.Block;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompactOre implements Comparable<CompactOre>, IStringSerializable {

    private static Set<String> usedResourceNames = new HashSet<>();

    private String resourceName;
    private ResourceLocation baseBlockLoc;
    private Block baseBlock;
    private int minRolls;
    private int maxRolls;
    private ResourceLocation baseOreTexture;
    private ResourceLocation baseUnderlyingTexture;
    private float spawnProbability;
    private int maxOreLayerColorDiff;
    private boolean lateGeneration;
    private boolean generateTexture;

    public CompactOre(ResourceLocation baseBlockLoc, int minRolls, int maxRolls, ResourceLocation baseOreTexture,
                      ResourceLocation baseUnderlyingTexture, float spawnProbability, int maxOreLayerColorDiff,
                      boolean lateGeneration, boolean generateTexture) {
        this.baseBlockLoc = baseBlockLoc;
        this.minRolls = minRolls;
        this.maxRolls = maxRolls;
        this.baseOreTexture = baseOreTexture == null ? null : new ResourceLocation(baseOreTexture.getNamespace(), "textures/" + baseOreTexture.getPath() + ".png");
        this.baseUnderlyingTexture = baseUnderlyingTexture == null ? null : new ResourceLocation(baseUnderlyingTexture.getNamespace(), "textures/" + baseUnderlyingTexture.getPath() + ".png");
        this.spawnProbability = spawnProbability;
        this.maxOreLayerColorDiff = maxOreLayerColorDiff;
        this.lateGeneration = lateGeneration;
        this.generateTexture = generateTexture;
        String resourceName = baseBlockLoc.toString().replace(":", "__");
        while(usedResourceNames.contains(resourceName)) {
            resourceName += "_";
        }
        this.resourceName = resourceName;
        usedResourceNames.add(resourceName);
    }

    public ResourceLocation getBaseBlockRegistryName() {
        return baseBlockLoc;
    }

    /**<b>Do NOT call before all mods have registered all their blocks.</b>*/
    public Block getBaseBlock() {
        if(baseBlock == null) {
            baseBlock = ForgeRegistries.BLOCKS.getValue(baseBlockLoc);
        }
        return baseBlock;
    }

    public int getMinRolls() {
        return minRolls;
    }

    public int getMaxRolls() {
        return maxRolls;
    }

    public ResourceLocation getBaseOreTexture() {
        return baseOreTexture;
    }

    public ResourceLocation getBaseUnderlyingTexture() {
        return baseUnderlyingTexture;
    }

    public float getSpawnProbability() {
        return spawnProbability;
    }

    public int getMaxOreLayerColorDiff() {
        return maxOreLayerColorDiff;
    }

    public boolean isLateGeneration() {
        return lateGeneration;
    }

    public boolean isGenerateTexture() {
        return generateTexture;
    }

    @Override
    public int compareTo(CompactOre compactOre) {
        ModList.get().getMods();
        if(this.baseBlockLoc.getNamespace().equals(compactOre.baseBlockLoc.getNamespace())) {
            return this.baseBlockLoc.getPath().compareTo(compactOre.baseBlockLoc.getPath());
        } else {
            List<ModInfo> modList = ModList.get().getMods();
            int thisIndex = -1, otherIndex = -1;
            for(int i = 0; i < modList.size(); i++) {
                String modId = modList.get(i).getModId();
                if(this.baseBlockLoc.getNamespace().equals(modId)) thisIndex = i;
                else if(compactOre.baseBlockLoc.getNamespace().equals(modId)) otherIndex = i;
                if(thisIndex != -1 && otherIndex != -1) break;
            }
            return thisIndex - otherIndex;
        }
    }

    @Override
    public String getName() {
        return resourceName;
    }

}
