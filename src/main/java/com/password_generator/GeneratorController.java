package com.password_generator;


import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/**
 * Class responsible for managing the application logic
 */
public class GeneratorController {
    /**
     * Field that contains an object that is responsible for the "Password length" field in the graphical interface.
     */
    @FXML
    private TextField password_len;
    /**
     * Field that contains an object that is responsible for the "Generation result" field in the graphical interface.
     */
    @FXML
    private TextField showed_password;
    /**
     * Field that contains an object that represents the field
     *  that contains the characters the user wishes to add to the password.
     */
    @FXML
    private TextField users_symbols;
    /**
     * Field that contains an object responsible for displaying the password generation time
     */
    @FXML
    private Text generation_time;
    /**
     * Field that contains an object responsible for checkbox that controls the use of numeric characters.
     */
    @FXML
    private CheckBox nums_checkbox;
    /**
     * Field that contains an object responsible for checkbox that controls the use of special characters.
     */
    @FXML
    private CheckBox specsymbols_checkbox;
    /**
     * Field that contains an object responsible for checkbox that controls the use of upper case characters.
     */
    @FXML
    private CheckBox upper_checkbox;
    /**
     * Field that contains an object responsible for checkbox that controls the use of Cyrillic characters.
     */
    @FXML
    private CheckBox rus_checkbox;
    /**
     * Object used for logging
     */
    protected static final Logger logger = LogManager.getLogger();

    /**
     *Function responsible for generating a password and placing it in the "Generation result" field
     *  when the "Generation" button is pressed
     */
    @FXML
    private void onGenerateButtonClick() {
        logger.trace("Entering method onGenerateButtonClick().");
        Password_gen generator = new Password_gen();
        Object[] args = ReadParameters();
        try {
            logger.info("The correctness of the generation parameters has been checked.");
            long start_time = System.currentTimeMillis();
            String password = generator.generate(args);
            long end_time = System.currentTimeMillis();
            long time = end_time - start_time;
            showed_password.setText(password);
            generation_time.setText("Generation time: " + time + "ms");
            logger.info("Password generated successfully.");
        }
        catch (RuntimeException e){
            logger.warn(e.getMessage());
            showed_password.setText(e.getMessage());
        }
    }

    /**
     *
     *Function responsible for copying the contents of the "Generation result" field to the clipboard
     *  when the "Copy" button is pressed
     */
    @FXML
    private void onCopyButtonClick() {
        String text = showed_password.getText();
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    /**
     *Function that is responsible for reading the generation parameters from the graphical interface
     * @return Object array with password generation parameters
     *      args[0] - boolean value: use numbers or not
     *      args[1] - boolean value: use upper case or not
     *      args[2] - boolean value: use special characters or not
     *      args[3] - boolean value: use Cyrillic characters or not
     *      args[4] - integer value: length of generating password
     *      args[5] - string value: string of elements user need to get in generated password
     */
    private Object[] ReadParameters() {
        Object[] args = new Object[7];
        args[0] = nums_checkbox.isSelected();
        args[1] = upper_checkbox.isSelected();
        args[2] = specsymbols_checkbox.isSelected();
        args[3] = rus_checkbox.isSelected();
        args[4] = password_len.getText();
        args[5] = users_symbols.getText();
        return args;
    }


}
