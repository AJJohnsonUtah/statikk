/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author AJ
 */
public enum ItemStat {

    AttackSpeed("PercentAttackSpeedMod"),
    MagicResist("FlatSpellBlockMod"),
    AbilityPower("FlatMagicDamageMod"),
    PctAbilityPower("PercentMagicDamageMod"),
    MoveSpeed("FlatMovementSpeedMod"),
    Armor("FlatArmorMod"),
    HealthRegen("FlatHPRegenMod"),
    Mana("FlatMPPoolMod"),
    PctHealthRegen("PercentHPRegenMod"),
    PctManaRegen("PercentMPRegenMod"),
    PctMoveSpeed("PercentMovementSpeedMod"),
    Health("FlatHPPoolMod"),
    PctHealth("PercentHPPoolMod"),
    CritChance("FlatCritChanceMod"),
    PctCritDamage("PercentCritDamageMod"),
    AttackDamage("FlatPhysicalDamageMod"),
    PctAttackDamage("PercentPhysicalDamageMod"),
    LifeSteal("PercentLifeStealMod"),
    SpellVamp("PercentSpellVampMod"),
    PhysicalDamageHeal("PercentPhysicalDamageHealMod"),
    DamageHeal("PercentDamageHealMod"),
    ArmorPen("FlatArmorPenMod"),
    PctArmorPen("PctArmorPenMod"),
    MagicPen("FlatMagicPenMod"),
    PctMagicPen("PctMagicPenMod"),
    CooldownReduction("rPercentCooldownMod");

    private final String idCode;

    public static HashMap<ItemStat, HashSet<Long>> itemsWithStat = null;

    public static HashSet<ItemStat> valuesHashSet() {
        HashSet<ItemStat> values = new HashSet<>();
        values.addAll(Arrays.asList(values()));
        return values;
    }

    ItemStat(String code) {
        this.idCode = code;
    }

    @JsonCreator
    public static ItemStat fromId(String id) {
        for (ItemStat itemStat : ItemStat.values()) {
            if (id.equals(itemStat.getIdCode())) {
                return itemStat;
            }
        }
        return null;
    }

    public String getIdCode() {
        return idCode;
    }

    public boolean isTankStat() {
        return this.equals(MagicResist) || this.equals(Armor) || this.equals(Health) || this.equals(PctHealth)
                || this.equals(HealthRegen) || this.equals(PctHealthRegen) || this.equals(LifeSteal)
                || this.equals(SpellVamp) || this.equals(DamageHeal) || this.equals(PhysicalDamageHeal);
    }

    public boolean isAdStat() {
        return this.equals(AttackDamage) || this.equals(AttackSpeed) || this.equals(CritChance) || this.equals(ArmorPen)
                || this.equals(PctArmorPen) || this.equals(PctCritDamage) || this.equals(PctAttackDamage);
    }

    public boolean isApStat() {
        return this.equals(AbilityPower) || this.equals(PctAbilityPower) || this.equals(MagicPen)
                || this.equals(PctMagicPen);
    }

}
