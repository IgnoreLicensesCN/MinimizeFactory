package io.github.ignorelicensescn.minimizeFactory;

import io.github.acdeasdff.infinityCompress.items.Multiblock_Autocrafter;
import io.github.ignorelicensescn.minimizeFactory.Items.Registers;
import io.github.ignorelicensescn.minimizeFactory.SFGroups.Groups;
import io.github.ignorelicensescn.minimizeFactory.utils.compabilities.DynaTech.DynaTechSerializedMachineRecipes;
import io.github.ignorelicensescn.minimizeFactory.utils.compabilities.FNAmp.FNAmplificationSerializedMachineRecipes;
import io.github.ignorelicensescn.minimizeFactory.utils.compabilities.FluffyMachines.FluffyMachinesSerializedMachineRecipes;
import io.github.ignorelicensescn.minimizeFactory.utils.compabilities.InfinityExpansion.InfinityCompress.InfinityCompressSerializedMachineRecipes;
import io.github.ignorelicensescn.minimizeFactory.utils.compabilities.InfinityExpansion.InfinityExpansionSerializedMachineRecipes;
import io.github.ignorelicensescn.minimizeFactory.utils.compabilities.LiteX.LiteXpansionSerializedMachineRecipes;
import io.github.ignorelicensescn.minimizeFactory.utils.TweakedProperty2.TweakedProperty2;
import io.github.ignorelicensescn.minimizeFactory.utils.compabilities.Slimefun.SlimefunSerializedMachineRecipes;
import io.github.ignorelicensescn.minimizeFactory.utils.searchregistries.SearchRegistries;
import io.github.ignorelicensescn.minimizeFactory.utils.simpleStructure.SimplePair;
import io.github.mooy1.infinityexpansion.InfinityExpansion;
import io.github.mooy1.infinityexpansion.items.quarries.Quarry;
import io.github.mooy1.infinitylib.core.AbstractAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.ncbpfluffybear.fluffymachines.machines.AutoTableSaw;
import ne.fnfal113.fnamplifications.materialgenerators.implementations.CustomMaterialGenerator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.github.acdeasdff.infinityCompress.items.FNFALsAmplifications.BlocksFN.*;
import static io.github.acdeasdff.infinityCompress.items.LiteXpansion.BlocksLiteXpansion.*;
import static io.github.ignorelicensescn.minimizeFactory.Items.Registers.emptyStringArray;
import static io.github.ignorelicensescn.minimizeFactory.utils.compabilities.Slimefun.SlimefunConsts.*;
import static io.github.ignorelicensescn.minimizeFactory.utils.compabilities.DynaTech.DynaTechConsts.*;
import static io.github.ignorelicensescn.minimizeFactory.utils.compabilities.FNAmp.FNAmpConsts.getMaterialGeneratorsOutput;
import static io.github.ignorelicensescn.minimizeFactory.utils.compabilities.InfinityExpansion.InfinityCompress.InfinityCompressConsts.getMultiblockAutocrafterRecipes;
import static io.github.ignorelicensescn.minimizeFactory.utils.compabilities.InfinityExpansion.InfinityExpansionConsts.*;
import static io.github.ignorelicensescn.minimizeFactory.utils.recipesupport.InfoScan.initAutoTableSawRecipes;
import static io.github.ignorelicensescn.minimizeFactory.utils.ItemStackUtil.RecipeChoiceListToItemStackArray_formated;
import static io.github.mooy1.infinityexpansion.items.quarries.Quarries.*;
import static io.ncbpfluffybear.fluffymachines.utils.FluffyItems.AUTO_TABLE_SAW;
import static ne.fnfal113.fnamplifications.items.FNAmpItems.*;

public class MinimizeFactory extends AbstractAddon {
    public static long LONG_MESSAGE_DELAY = 3000;
    public static int LONG_MESSAGE_COUNT = 5;
    public static int LONG_MESSAGE_BLOCK_NOTIFICATION_COUNT = 5;
    public static int GEOMINER_BIOME_EVERY_LINE = 3;

    public static MinimizeFactory instance;
    public static Logger logger;
    public static String language;
    public static TweakedProperty2 properties;
    public static boolean InfinityCompressFlag = false;
    public static boolean InfinityExpansionFlag = false;
    public static boolean LiteXpansionFlag = false;
    public static boolean SlimeFrameFlag = false;
    public static boolean FNAmplificationsFlag = false;
    public static boolean GlobalWarmingFlag = false;
    public static boolean GalactifunFlag = false;
    public static boolean DynaTechFlag = false;
    public static boolean FluffyMachinesFlag = false;
    public static long NETWORK_MAX_DISTANCE = 5;
    public static long NETWORK_CONNECTOR_DELAY_FOR_ALL = 5000;
    public static long NETWORK_CONNECTOR_DELAY_FOR_ONE = 15000;
    public static boolean isTest = true;//remember to turn false!
    public static Map<String,Long> msgSendDelay = new HashMap<>();
    public static Map<String,Integer> blockMessageCounter = new HashMap<>();
    public static long lastConnectorUsedTime = 0;
    public static Map<UUID,Long> PlayerLastConnectorUsedTime = new HashMap<>();
    public static SimplePair<ItemStack[],ItemStack>[] vanillaRecipeArray = new SimplePair[0];
    public static List<SimplePair<ItemStack[],ItemStack>> altarRecipes = new ArrayList<>();

    public MinimizeFactory(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file,
                "ignorelicensescn", "MinimizeFactory", "master", "auto-update");
    }
    public MinimizeFactory() {
        super("ignorelicensescn", "MinimizeFactory", "master", "auto-update");
    }

    @Override
    protected void enable() {
        instance = this;
        logger = this.getLogger();
        logger.log(Level.INFO,"Logger loaded.");

        logger.log(Level.INFO,"Loading configs.");
        GEOMINER_BIOME_EVERY_LINE = instance.getConfig().getInt("geominer_info_lore_biome_every_line", 1, 100);
        LONG_MESSAGE_DELAY = instance.getConfig().getLong("long_message_delay", 3000);
        LONG_MESSAGE_COUNT = instance.getConfig().getInt("long_message_count", 5);
        LONG_MESSAGE_BLOCK_NOTIFICATION_COUNT = instance.getConfig().getInt("long_message_block_notification_time", 5);
        NETWORK_MAX_DISTANCE = instance.getConfig().getLong("machine_network_max_distance", 5);
        NETWORK_CONNECTOR_DELAY_FOR_ALL = instance.getConfig().getLong("machine_network_remote_connector_delay_millseconds_for_all",5000);
        NETWORK_CONNECTOR_DELAY_FOR_ONE = instance.getConfig().getLong("machine_network_remote_connector_delay_millseconds_for_one",15000);
        logger.log(Level.INFO,"Configs loaded.");

        logger.log(Level.INFO, "Checking installed plugins.");
        if (Bukkit.getPluginManager().isPluginEnabled("InfinityExpansion")){
            InfinityExpansionFlag = true;
            logger.log(Level.INFO,"InfinityExpansion Detected");
            COST_MULTIPLIER =
                    InfinityExpansion.config().getDouble("balance-options.singularity-cost-multiplier", 0.1, 100);
        }
        if (Bukkit.getPluginManager().isPluginEnabled("InfinityCompress")){
            InfinityCompressFlag = true;
            logger.log(Level.INFO,"InfinityCompress Detected");
        }
        if (Bukkit.getPluginManager().isPluginEnabled("LiteXpansion")){
            LiteXpansionFlag = true;
            logger.log(Level.INFO,"LiteXpansion Detected");
        }
        if (Bukkit.getPluginManager().isPluginEnabled("FNAmplifications")){
            FNAmplificationsFlag = true;
            logger.log(Level.INFO,"FNAmplifications Detected");
        }
        if (Bukkit.getPluginManager().isPluginEnabled("SlimeFrame")){
            SlimeFrameFlag = true;
            logger.log(Level.INFO,"SlimeFrame Detected");
        }
        if (Bukkit.getPluginManager().isPluginEnabled("GlobalWarming")){
            GlobalWarmingFlag = true;
            logger.log(Level.INFO,"GlobalWarming Detected");
        }
        if (Bukkit.getPluginManager().isPluginEnabled("Galactifun")){
            GalactifunFlag = true;
            logger.log(Level.INFO,"Galactifun Detected");
        }
        if (Bukkit.getPluginManager().isPluginEnabled("DynaTech")){
            DynaTechFlag = true;
            logger.log(Level.INFO,"DynaTech Detected");
        }
        if (Bukkit.getPluginManager().isPluginEnabled("FluffyMachines")){
            FluffyMachinesFlag = true;
            logger.log(Level.INFO,"FluffyMachines Detected");
        }

        logger.log(Level.INFO, "Installed plugins Checked.");

        new Thread(){
            @Override
            public void run() {shapeRecipes();}
        }.start();

        language = getConfig().getString("language");
        logger.log(Level.INFO,"Loading language properties...");
        properties = new TweakedProperty2();
        try {
            InputStream inStream = this.getClassLoader().getResourceAsStream("language/" + language + ".properties");
            if (inStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inStream, StandardCharsets.UTF_8);
                properties.load(inputStreamReader);
            }else {
                inStream = this.getClassLoader().getResourceAsStream("language/zh_CN.properties");
                InputStreamReader inputStreamReader = new InputStreamReader(inStream, StandardCharsets.UTF_8);
                properties.load(inputStreamReader);
            }
            logger.log(Level.INFO,"Properties loaded.");
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Properties load failed.");
            e.printStackTrace();
        }

        logger.log(Level.INFO,"Loading Groups.");
        Groups.setup(instance);
        Registers.setup(instance);
        logger.log(Level.INFO,"Groups loaded.");

        //if u have lots of cores.
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
            logger.log(Level.INFO,"Loading recipe information.");
            new Thread(() -> {
                initEnergyConst();
                initAllGEOResources();
                initAncientAltarRecipes();
                SlimefunSerializedMachineRecipes.init();
            }).start();
            if (InfinityExpansionFlag){
                new Thread(() -> {try{getInfinitySingularities();} catch (Exception e){
                        e.printStackTrace();
                    }}).start();
                new Thread(() -> {try{getRandomizedItemStackClass();} catch (Exception e){
                    e.printStackTrace();
                }}).start();
                new Thread(() -> {try{getEnergyConsts();} catch (Exception e){
                    e.printStackTrace();
                }}).start();
                new Thread(() -> {try{initInfinityWorkbenchRecipes();} catch (Exception e){
                    e.printStackTrace();
                }}).start();
                new Thread(() -> {try{initGearTransformerRecipes();} catch (Exception e){
                    e.printStackTrace();
                }}).start();
                new Thread(() -> {try{getStoneworksFactoryRecipes();} catch (Exception e){
                    e.printStackTrace();
                }}).start();
                new Thread(() -> {try{
                    getOscillators();
                    quarryInfo((Quarry) SlimefunItem.getByItem(BASIC_QUARRY));
                    quarryInfo((Quarry) SlimefunItem.getByItem(ADVANCED_QUARRY));
                    quarryInfo((Quarry) SlimefunItem.getByItem(VOID_QUARRY));
                    quarryInfo((Quarry) SlimefunItem.getByItem(INFINITY_QUARRY));
                } catch (Exception e){
                    e.printStackTrace();
                }}).start();
                InfinityExpansionSerializedMachineRecipes.init();
                if (InfinityCompressFlag){
                    if (LiteXpansionFlag){
                        new Thread(() -> {

                            try{getMultiblockAutocrafterRecipes((Multiblock_Autocrafter) SlimefunItem.getByItem(AUTO_METAL_FORGE));}
                            catch (Exception e){e.printStackTrace();}
                            try{getMultiblockAutocrafterRecipes((Multiblock_Autocrafter) SlimefunItem.getByItem(AUTO_SMELTRY));}
                            catch (Exception e){e.printStackTrace();}
                            try{getMultiblockAutocrafterRecipes((Multiblock_Autocrafter) SlimefunItem.getByItem(AUTO_MILL));}
                            catch (Exception e){e.printStackTrace();}
                    }).start();
                    }
                    if (FNAmplificationsFlag){
                        new Thread(() -> {

                            try{getMultiblockAutocrafterRecipes((Multiblock_Autocrafter) SlimefunItem.getByItem(AUTO_MAGIC_ALTAR));}
                            catch (Exception e){e.printStackTrace();}
                            try{getMultiblockAutocrafterRecipes((Multiblock_Autocrafter) SlimefunItem.getByItem(AUTO_ASSEMBLING_STATION));}
                            catch (Exception e){e.printStackTrace();}
                            try{getMultiblockAutocrafterRecipes((Multiblock_Autocrafter) SlimefunItem.getByItem(AUTO_SCRAP_RECYCLER));}
                            catch (Exception e){e.printStackTrace();}
                            try{getMultiblockAutocrafterRecipes((Multiblock_Autocrafter) SlimefunItem.getByItem(AUTO_GEM_ALTAR));}
                            catch (Exception e){e.printStackTrace();}
                            try{getMultiblockAutocrafterRecipes((Multiblock_Autocrafter) SlimefunItem.getByItem(AUTO_STICK_ALTAR));}
                            catch (Exception e){e.printStackTrace();}
                        }).start();
                    }
                    InfinityCompressSerializedMachineRecipes.init();
                }
            }
            if (DynaTechFlag){
                try{getStardustReactorFuels();} catch (Exception e){e.printStackTrace();}
                try{getCulinaryGeneratorFuels();} catch (Exception e){e.printStackTrace();}
                try{getGrowthChambersRecipes();} catch (Exception e){e.printStackTrace();}
                SearchRegistries.scan();
                DynaTechSerializedMachineRecipes.init();
            }
            if (LiteXpansionFlag){
                LiteXpansionSerializedMachineRecipes.init();
            }
            if (FNAmplificationsFlag){
                new Thread(() -> {

                    for (ItemStack i:new ItemStack[]{
                            FMG_GENERATOR_CLAY,
                            FMG_GENERATOR_BONE,
                            FMG_GENERATOR_DIAMOND,
                            FMG_GENERATOR_DIRT,
                            FMG_GENERATOR_EMERALD,
                            FMG_GENERATOR_HONEYCOMB,
                            FMG_GENERATOR_WARPED
                    }){
                        try {
                            getMaterialGeneratorsOutput((CustomMaterialGenerator) SlimefunItem.getByItem(i));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    FNAmplificationSerializedMachineRecipes.init();
                }).start();
            }
            if (FluffyMachinesFlag){
                new Thread(() -> {

                    try{initAutoTableSawRecipes((AutoTableSaw) AUTO_TABLE_SAW.getItem());} catch (Exception e){e.printStackTrace();}
                    FluffyMachinesSerializedMachineRecipes.init();
                }).start();
            }
            logger.log(Level.INFO,"Recipe information load thread set.");
        });
        long period = LONG_MESSAGE_DELAY * 300;
        if (period < 0) {
            period = Long.MAX_VALUE;
        }
        Bukkit.getServer().getScheduler().runTaskTimer(instance, () -> {
            msgSendDelay.clear();
            blockMessageCounter.clear();
            PlayerLastConnectorUsedTime.clear();
        },period, period);
    }
    @Override
    protected void disable() {

    }

    /**
     * I don't know whether a new thread will bring better performance.
     */
    public void msgSend(Player p, List<String> msgs){
        new Thread(){
            @Override
            public void run() {
                
                if (msgs.size() >= LONG_MESSAGE_COUNT
                ){
                    if (
                            (msgSendDelay.get(p.getPlayerListName()) != null
                                    && ((System.currentTimeMillis() - msgSendDelay.get(p.getPlayerListName())) >= LONG_MESSAGE_DELAY))
                                    || (msgSendDelay.get(p.getPlayerListName())==null)
                    ){
                        p.sendMessage(msgs.toArray(emptyStringArray));
                        blockMessageCounter.remove(p.getPlayerListName());
                        msgSendDelay.put(p.getPlayerListName(),System.currentTimeMillis());
                    }else {
                        blockMessageCounter.put(p.getPlayerListName(), blockMessageCounter.get(p.getPlayerListName()) == null ? 1 : blockMessageCounter.get(p.getPlayerListName()) + 1);
                        if (blockMessageCounter.get(p.getPlayerListName()) <= LONG_MESSAGE_BLOCK_NOTIFICATION_COUNT){
                            p.sendMessage(properties.getReplacedProperty("PLEASE_TRY_AGAIN_LATER") + LONG_MESSAGE_DELAY + properties.getReplacedProperty("PLEASE_TRY_AGAIN_LATER_UNIT"));
                        }
                    }
                }
                else{
                    blockMessageCounter.remove(p.getPlayerListName());
                    p.sendMessage(msgs.toArray(emptyStringArray));
                }
            }
        }.start();
    }
    
    public static void shapeRecipes(){
        logger.log(Level.INFO,"Loading vanilla recipes");


        HashMap<List<RecipeChoice>, SimplePair<ItemStack, Recipe>> shapedVanillaRecipes = new HashMap<>();
        HashMap<List<RecipeChoice>, SimplePair<ItemStack, Recipe>> shapelessVanillaRecipes = new HashMap<>();
        //from fluffymachines
        Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();
        while (recipeIterator.hasNext()) {
            Recipe r = recipeIterator.next();
            if (r instanceof ShapedRecipe) {
                ShapedRecipe sr = (ShapedRecipe) r;
                List<RecipeChoice> rc = new ArrayList<>();
                for (RecipeChoice choice : sr.getChoiceMap().values()) {
                    if (choice != null) {
                        rc.add(choice);
                    }
                }
                shapedVanillaRecipes.put(rc, new SimplePair<>(sr.getResult().clone(), r));
            }
            else if (r instanceof ShapelessRecipe slr) {
                shapelessVanillaRecipes.put(slr.getChoiceList(), new SimplePair<>(slr.getResult().clone(), slr));
            }
        }
        vanillaRecipeArray = new SimplePair[shapedVanillaRecipes.size() + shapelessVanillaRecipes.size()];
        int i=0;
        for (List<RecipeChoice> rcList:shapedVanillaRecipes.keySet()){
            vanillaRecipeArray[i] = new SimplePair<>(RecipeChoiceListToItemStackArray_formated(rcList),shapedVanillaRecipes.get(rcList).first);
            i+=1;
        }
        shapedVanillaRecipes.clear();
        for (List<RecipeChoice> rcList:shapelessVanillaRecipes.keySet()){
            vanillaRecipeArray[i] = new SimplePair<>(RecipeChoiceListToItemStackArray_formated(rcList),shapelessVanillaRecipes.get(rcList).first);
            i+=1;
        }
        shapelessVanillaRecipes.clear();
        logger.log(Level.INFO,"Vanilla recipes loaded");
    }
}
