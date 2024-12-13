package com.password_generator;

import java.util.Random;

import static com.password_generator.GeneratorController.logger;

/**
 * Class responsible for generating the password
 *
 */
public class Password_gen {
    /**
     * Field in which the password will be generated
     */
    private StringBuilder password= new StringBuilder("");
    /**
     * Field with lowercase latin characters
     */
    private char[] let_arr = "qwertyuiopasdfghjklzxcvbnm".toCharArray();
    /**
     * Field with numbers characters
     */
    private char[] nums_arr = "1234567890".toCharArray();
    /**
     * Field with uppercase latin characters
     */
    private char[] upper_let_arr = "QWERTYUIOPASDFGHJKLZXCVBNM".toCharArray();
    /**
     * Field with special characters
     */
    private char[] spec_arr = "!@#$%^&*()-_=+}{[];:>,<,?/\\\"\'`~".toCharArray();
    /**
     * Field with Cyrillic characters in lower case
     */
    private char[] ru_let_arr = "йцукенгшщзхъфывапролджэячсмитьбюё".toCharArray();
    /**
     * Field with Cyrillic characters in upper case
     */
    private char[] ru_upper_let_arr = "ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮЁ".toCharArray();
    /**
     * Field that will contain all the characters used to generate the password.
     */
    private char[] res_arr = let_arr.clone();
    private int let_arr_leng = let_arr.length;
    private int num_arr_leng = nums_arr.length;
    private int upper_let_arr_leng = upper_let_arr.length;
    private int spec_arr_leng = spec_arr.length;
    private int ru_let_arr_leng = ru_let_arr.length;
    private int ru_upper_let_arr_leng = ru_upper_let_arr.length;
    /**
     * Field that contains the characters the user wishes to insert.
     */
    private char[] user_symb_arr;
    private int user_symb_arr_leng;

    Random rn = new Random();
    boolean uncheck;

    /**
     * Function implementing password generation
     * @param args Object array containing password generation parameters
     *             args[0] - boolean value: use numbers or not
     *             args[1] - boolean value: use upper case or not
     *             args[2] - boolean value: use special characters or not
     *             args[4] - integer value: length of generating password
     *             args[5] - string value: string of elements user need to get in generated password
     * @return generated password
     */
    protected String generate(Object[] args) {
        logger.trace("Entering method generate().");
        check_args(args);
        create_arr(args);
        int pass_leng = Integer.parseInt((String) args[4]);

        int arr_leng = res_arr.length;
        for (int i = 0; i < pass_leng; i++){
            int index = rn.nextInt(arr_leng);
            password.append(res_arr[index]);
        }

        correct_generation(pass_leng, args);
        return password.toString();
    }

    /**
     * Function that checks whether a current password contains a character of the required type.
     *  If it does not, it inserts a random required character into a random place in the password
     * @param symb_arr char array: array with required characters
     * @param symb_arr_leng integer value: length of array with required characters
     * @param pass_leng integer value: length of password
     * @param need boolean value: do user require symbols this type in password or not
     * @param all boolean value: is it required that all characters of this type be included in the password or not
     */
    private void include_check(char[] symb_arr, int symb_arr_leng, int pass_leng, boolean need, boolean all){
        boolean uncheck_symb = true;
        if (all && need){
            uncheck_symb = false;
            for (int i = 0; i < symb_arr_leng; i++) {
                if (!password.toString().contains(Character.toString(symb_arr[i]))) {
                    uncheck_symb = true;
                    break;
                }
            }
        }else {
            for (int i = 0; i < symb_arr_leng && need; i++) {
                if (password.toString().contains(Character.toString(symb_arr[i]))) {
                    uncheck_symb = false;
                    break;
                }
            }
        }
        if (uncheck_symb && need){
            uncheck = true;
            password.setCharAt(rn.nextInt(pass_leng), symb_arr[rn.nextInt(symb_arr_leng)]);
        }
    }

    /**
     * Function that implements the union of two arrays into one
     * @param arrs two-dimensional char array: contains two char arrays that need to be merged
     * @return array that includes all elements of two arrays from the argument
     */
    private char[] merge(char[][] arrs){
        char[] restult_arr = new char[arrs[0].length + arrs[1].length];
        int arr1_len = arrs[0].length;
        int arr2_len = arrs[1].length;
        for (int i = 0; i < arr1_len; i++){
            restult_arr[i] = arrs[0][i];
        }
        for (int i =0; i< arr2_len; i++){
            restult_arr[i+arr1_len] = arrs[1][i];
        }
        return restult_arr;
    }

    /**
     * Function that creates an array containing all the characters that will be used to generate a password
     * @param args Object array containing password generation parameters
     *      *             args[0] - boolean value: use numbers or not
     *      *             args[1] - boolean value: use upper case or not
     *      *             args[2] - boolean value: use special characters or not
     *      *             args[4] - integer value: length of generating password
     *      *             args[5] - string value: string of elements user need to get in generated password
     */
    private void create_arr(Object[] args){
        if ((boolean) args[0]){
            res_arr = merge(new char[][]{res_arr, nums_arr});
        }
        if ((boolean) args[1]){
            res_arr = merge(new char[][]{res_arr, upper_let_arr});
        }
        if ((boolean)  args[2]){
            res_arr = merge(new char[][]{res_arr, spec_arr});
        }
        if ((boolean) args[3]){
            res_arr = merge(new char[][]{res_arr, ru_let_arr});
        }
        if ((boolean) args[3] && (boolean)  args[1]){
            res_arr = merge(new char[][]{res_arr, ru_upper_let_arr});
        }

        user_symb_arr = args[5].toString().toCharArray();
        user_symb_arr_leng = user_symb_arr.length;
        res_arr = merge(new char[][]{res_arr, user_symb_arr});

    }

    /**
     * Function that ensures that a password contains all required character types
     * @param pass_leng integer value: length of password
     * @param args Object array containing password generation parameters
     *      *             args[0] - boolean value: use numbers or not
     *      *             args[1] - boolean value: use upper case or not
     *      *             args[2] - boolean value: use special characters or not
     *      *             args[4] - integer value: length of generating password
     *      *             args[5] - string value: string of elements user need to get in generated password
     */
    private void correct_generation(int pass_leng, Object[] args){
        uncheck = true;
        logger.trace("Entering method correct_generation().");
        while (uncheck){
            uncheck = false;
            include_check(let_arr, let_arr_leng, pass_leng,  true, false);
            include_check(nums_arr, num_arr_leng, pass_leng, (boolean) args[0], false);
            include_check(upper_let_arr, upper_let_arr_leng, pass_leng, (boolean) args[1], false);
            include_check(spec_arr, spec_arr_leng, pass_leng, (boolean) args[2], false);
            include_check(ru_let_arr, ru_let_arr_leng, pass_leng, (boolean) args[3], false);
            include_check(ru_upper_let_arr, ru_upper_let_arr_leng, pass_leng, (boolean) args[3] && (boolean) args[1], false);
            include_check(user_symb_arr, user_symb_arr_leng, pass_leng, true, true);
        }
    }
    /**
     *
     *
     * @param args Object array containing password generation parameters
     *      *      *             args[0] - boolean value: use numbers or not
     *      *      *             args[1] - boolean value: use upper case or not
     *      *      *             args[2] - boolean value: use special characters or not
     *      *      *             args[4] - integer value: length of generating password
     *      *      *             args[5] - string value: string of elements user need to get in generated password
     * @throws RuntimeException unchecked exceptions type that is used to handle incorrect user input
     */
    private void check_args(Object[] args) throws RuntimeException {
        String nums = "1234567890";

        if (args[4]=="") {
            throw new RuntimeException("Necessary to enter the desired password length");
        }
        for (char i : args[4].toString().toCharArray()) {
            if (!nums.contains(Character.toString(i))) {
                throw new RuntimeException("Incorrect value in \"Password length\" field - " + i);
            }

        }
        logger.info("The characters in the field responsible for the password length have been checked.");

        if (Integer.parseInt(args[4].toString()) < (checkBoxToInt(args[0]) + checkBoxToInt(args[1]) +
                checkBoxToInt(args[2]) + checkBoxToInt(args[3]) + args[5].toString().length() + 1)) { //+1 т.к. буквы есть всегда
            throw new RuntimeException("The given password length is too short for the given parameters.");
        }
        logger.info("The password length matches the specified parameters checked");
    }

    /**
     * \
     * @param checkbox boolean value: have parameters corresponding to the checkbox been selected by the user or not
     * @return 1 if checkbox is selected and 0 if not
     */
    private int checkBoxToInt(Object checkbox) {
        return (boolean) checkbox ? 1 : 0;
    }
}