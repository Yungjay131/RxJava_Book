package com.slyworks.rxjava_book.chap_03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.core.Observable;

public class ValidationUtils {
    private ValidationUtils(){
        throw new RuntimeException("you should not create instance of this Util class");
    }

    public static boolean checkExpirationDate(String expirationDate){
        Pattern expirationDatePattern = Pattern.compile("^\\d\\d\\d\\d$");
        return expirationDatePattern.matcher(expirationDate).matches();
    }

    public static int[] convertFromStringToIntArray(String input){
        char[] c = input.toCharArray();

        int[] array = new int[c.length];
        for(int i = 0; i < c.length; i++){
            array[i] = c[i];
        }

        return array;
    }

    public static boolean checkCardCheckSum(int[] digits){
        int sum = 0;
        int length = digits.length;
        for (int i = 0; i < length; i++) {

            // Get digits in reverse order
            int digit = digits[length - i - 1];

            // Every 2nd number multiply with 2
            if (i % 2 == 1) {
                digit *= 2;
            }
            sum += digit > 9 ? digit - 9 : digit;
        }
        return sum % 10 == 0;
    }

    @SafeVarargs
    public static Observable<String> andStrings(Observable<String>... observables){
        return Observable.combineLatest(
                (Arrays.asList(observables)),
                (strings)->{
                   StringBuilder sb = new StringBuilder();
                   for(Object s: strings){
                       if(!"".equals(s)){
                           sb.append(s);
                           sb.append("\n");
                       }
                   }

                   return sb.toString();
                });
    }
    public static Observable<Boolean> and(Observable<Boolean>... observables){
        //ArrayList<Observable<Boolean>> list = new ArrayList<>(Arrays.asList(observables));
        return Observable.combineLatest(
                Arrays.asList(observables),
                (_observables)->{
                    Boolean status = true;
                    for(Object b: _observables){
                        status = status && (Boolean)b;
                    }

                    return status;
                });
    }
    @SafeVarargs
    public static Observable<Boolean> and2(Observable<Boolean>... observables ){
        Observable<Boolean> result = Observable.just(true);

        for(Observable<Boolean> o : observables){
             result = and(result, o);
        }

        return result;
    }
    private static Observable<Boolean> and(Observable<Boolean> a, Observable<Boolean> b){
        return Observable.combineLatest(a,b,
                (valueA, valueB)-> valueA && valueB);
    }


    public static <T> Observable<Boolean> equals(Observable<T> a, Observable<T> b){
        return Observable.combineLatest(a,b,
                /*(valueA,valueB)->valueA.equals(valueB)*/
                   Object::equals);
    }
}

