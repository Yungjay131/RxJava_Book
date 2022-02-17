package com.slyworks.rxjava_book.chap_03;

import java.util.regex.Pattern;

/**
 * Created by Joshua Sylvanus, 2:29 AM, 06-Jan-22.
 */
public enum CardType {
    UNKNOWN(-1),
    VISA(3),
    MASTER_CARD(3),
    AMERICA_EXPRESS(4);

    //region Vars
    private final int CVCLength;

    private static final Pattern mRegVisa =
            Pattern.compile("^4[0-9]{12}(?:[0-9]{3})?$");
    private static final Pattern mRegMasterCard =
            Pattern.compile("^5[1-5][0-9]{14}$");
    private static final Pattern mRegAmericanExpress =
            Pattern.compile("^3[47][0-9]{13}$");
    //endregion

    CardType(int CVCLength){
        this.CVCLength = CVCLength;
    }

    public int getCVCLength() {
        return CVCLength;
    }

    public static CardType getCardTypeFromString(String number){
        if(mRegVisa.matcher(number).matches())
            return VISA;
        else if(mRegMasterCard.matcher(number).matches())
            return MASTER_CARD;
        else if(mRegAmericanExpress.matcher(number).matches())
            return AMERICA_EXPRESS;

        return UNKNOWN;
    }
}
