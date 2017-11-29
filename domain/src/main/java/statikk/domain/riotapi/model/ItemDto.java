/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author AJ
 */
public class ItemDto implements Serializable {

    private String colloq;
    private boolean consumeOnFull;
    private boolean consumed;
    private int depth;
    private String description;
    private Map<String, String> effect;
    private List<Integer> from;
    private GoldDto gold;
    private String group;
    private boolean hideFromAll;
    private int id;
    private ImageDto image;
    private boolean inStore;
    private List<Integer> into;
    private Map<MapType, Boolean> maps;
    private String name;
    private String plaintext;
    private String requiredChampion;
    private MetaDataDto rune;
    private String sanitizedDescription;
    private int specialRecipe;
    private int stacks;
    private Map<ItemStat, Double> stats;
    private List<String> tags;

    public ItemDto() {
        this.into = Collections.EMPTY_LIST;
        this.from = Collections.EMPTY_LIST;
        this.tags = Collections.EMPTY_LIST;
        this.inStore = true;
    }

    public String getColloq() {
        return colloq;
    }

    public boolean isConsumeOnFull() {
        return consumeOnFull;
    }

    public boolean isConsumed() {
        return consumed;
    }

    public int getDepth() {
        return depth;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getEffect() {
        return effect;
    }

    public List<Integer> getFrom() {
        return from;
    }

    public GoldDto getGold() {
        return gold;
    }

    public String getGroup() {
        return group;
    }

    public boolean isHideFromAll() {
        return hideFromAll;
    }

    public int getId() {
        return id;
    }

    public ImageDto getImage() {
        return image;
    }

    public boolean isInStore() {
        return inStore;
    }

    public List<Integer> getInto() {
        return into;
    }

    public Map<MapType, Boolean> getMaps() {
        return maps;
    }

    public String getName() {
        return name;
    }

    public String getPlaintext() {
        return plaintext;
    }

    public String getRequiredChampion() {
        return requiredChampion;
    }

    public MetaDataDto getRune() {
        return rune;
    }

    public String getSanitizedDescription() {
        return sanitizedDescription;
    }

    public int getSpecialRecipe() {
        return specialRecipe;
    }

    public int getStacks() {
        return stacks;
    }

    public Map<ItemStat, Double> getStats() {
        return stats;
    }

    public List<String> getTags() {
        return tags;
    }

    public void syncItemStatsFromDescription() {
        if (this.sanitizedDescription == null) {
            return;
        }
        addStatFromPatternDescription("([0-9]+)% (Bonus )?Armor Penetration", ItemStat.PctArmorPen);
        addStatFromPatternDescription("([0-9]+)% (Bonus )?Magic Penetration", ItemStat.PctMagicPen);
        addStatFromPatternDescription("([0-9]+) (Bonus )?Magic Penetration", ItemStat.MagicPen);
        addStatFromPatternDescription("([0-9]+) (Bonus )?Lethality", ItemStat.ArmorPen);
        addStatFromPatternDescription("([0-9]+) Base Health Regen", ItemStat.PctHealthRegen);
        addStatFromPatternDescription("([0-9]+)% Bonus Health", ItemStat.PctHealth);
        addStatFromPatternDescription("Increases Ability Power by ([0-9]+)%", ItemStat.PctAbilityPower);
    }

    private void addStatFromPatternDescription(String pattern, ItemStat stat) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(sanitizedDescription);
        if (m.find() && m.group(1) != null && m.group(1).length() > 0) {
            addStatIfDoesNotExist(stat, Double.parseDouble(m.group(1)));
        }
    }

    private void addStatIfDoesNotExist(ItemStat stat, Double amount) {
        if (!this.stats.containsKey(stat) || !Objects.equals(amount, this.stats.get(stat))) {
            this.stats.put(stat, amount);
        }
    }

    /**
     * Returns true if the item is considered a traditional "supporting" item
     * @return 
     */
    public boolean isSupportItem() {
        if (this.tags != null && (this.tags.contains("GoldPer") || this.tags.contains("Vision"))) {
            return true;
        }
        if (this.sanitizedDescription != null
                && (this.sanitizedDescription.toLowerCase().contains(" allies")
                || this.sanitizedDescription.toLowerCase().contains(" ally")
                || this.sanitizedDescription.toLowerCase().contains(" allied"))) {
            return true;
        }
        return false;
    }

}
