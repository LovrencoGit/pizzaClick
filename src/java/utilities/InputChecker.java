/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Pizza;

/**
 *
 * @author Lovrenco
 */
public class InputChecker {

    final private static String[] illegalChar = {"'", "#", ";", ":", "*", "+", "{", "}",
        "(", ")", "]", "[", "§", "!", "?", "/", "%", "£", "$", "%", "&", "="};

    /*
    TRUE    STOP input errato
    FALSE   OK   input corretto
     */
    public static boolean checkGenericText(String text) {
        for (String special : illegalChar) {
            if(text.contains(special)){
                return true;
            }
        }
        return false;
    }

    public static boolean checkData(String data) {      //2016-09-13
        //return !( data.length()==10 && data.charAt(4)=='-' && data.charAt(7)=='-' ); 
        Pattern regex = Pattern.compile("^([0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9])$");
        Matcher matcher = regex.matcher(data);
        return !matcher.find();
    }

    
    public static boolean checkOra(String ora) {      //19:09
        Pattern regex = Pattern.compile("^([0-9][0-9]:[0-9][0-9])$");
        Matcher matcher = regex.matcher(ora);
        return !matcher.find();
    }

    public static boolean checkPrezzo(String ora) {      //6.00 o 10.00
        Pattern regex = Pattern.compile("^[0-9]{1,2}\\.[0-9]{2}$");
        Matcher matcher = regex.matcher(ora);
        return !matcher.find();
    }


    /*
    public static HashMap<Pizza, Integer> getElencoPizzeToHashMap(ArrayList<Pizza> elencoPizze){
        HashMap<Pizza,Integer> map = new HashMap<>();
        for(Pizza pizza:elencoPizze){
            Integer value = map.get(pizza);
            if(value == null){
                map.put(pizza, 1);
            }else{
                map.put(pizza, value+1);
            }
        }
        return map;
    }
    */
    
    
}
//  http://regexr.com/