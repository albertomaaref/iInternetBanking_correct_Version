package com.example.iinternetbanking.Models;

public enum TypeCompteEnum {
    Epargne("Epargne"),
    Personnel("Personnel"),
    Courant("Courant"),
    Le_compte_chèques_en_dinars("Le compte chèques en dinars"),
    Le_compte_chèque_en_dinars_et_en_dinars_convertibles("Le compte chèque en dinars et en dinars convertibles"),
    Le_compte_chèque_en_dinars_convertibles_pour_les_TRE("Le compte chèque en dinars convertibles pour les TRE"),
    Le_compte_spécial_en_dinars_convertibles_ou_en_devises("Le compte spécial en dinars convertibles ou en devises"),
    Le_compte_professionnel_en_dinars_convertibles_ou_en_devises("Le compte professionnel en dinars convertibles ou en devises"),
    Le_compte_intérieur_non_résident("Le compte intérieur non résident"),
    Le_compte_jeune("Le compte jeune");

    private String displayName;
    TypeCompteEnum(String displayName) {
        this.displayName = displayName;
    }
    public String displayName() { return displayName; }

    // Optionally and/or additionally, toString.
    @Override public String toString() { return displayName; }

}