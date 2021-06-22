package com.xiana.fe3hguide.model;

import com.xiana.fe3hguide.R;

public enum Skill {
    Sword, Lance, Axe, Bow, Brawling, Reason, Faith, Authority, Heavy_Armor, Riding, Flying;

    public static int getIcon(String rawSkill){
        for (Skill skill : Skill.values()) {
            if (rawSkill.contains(skill.name())) return getIcon(skill);
            else if (rawSkill.contains("Brawl")) return getIcon(Brawling);
            else if (rawSkill.contains("Heavy Armor")) return getIcon(Heavy_Armor);
        }
        return R.drawable.missing_number;
    }

    public static int getIcon(Skill skill){
        switch (skill){
            case Sword: return R.drawable.sword;
            case Lance: return R.drawable.lance;
            case Axe: return R.drawable.axe;
            case Bow: return R.drawable.bow;
            case Brawling: return R.drawable.brawl;
            case Reason: return R.drawable.reason;
            case Faith: return R.drawable.faith;
            case Authority: return R.drawable.authority;
            case Heavy_Armor: return R.drawable.heavy_armor;
            case Riding: return R.drawable.riding;
            case Flying: return R.drawable.flying;
        }
        return R.drawable.missing_number;
    }
}
