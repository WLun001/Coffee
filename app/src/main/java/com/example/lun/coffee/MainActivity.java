package com.example.lun.coffee;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.app.ToolbarActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.widget.Toast.makeText;


public class MainActivity extends ActionBarActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is called when + button is clicked.
     */
    public void increment(View view) {
        if(quantity == 100) {
           Toast toast = new Toast(this);
            toast.makeText(this, "Cannot more than 100 cups of coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when - button is clicked.
     */
    public void decrement(View view) {
        if(quantity == 1) {
            Toast.makeText(this, "Cannot less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //Figure if the user selected expresso
        CheckBox espressoSelect = (CheckBox) findViewById(R.id.expresso_checkBox);
        boolean espressoCup = espressoSelect.isChecked();

        //Figure if the user selected greentea
        CheckBox greenTeaSelect = (CheckBox) findViewById(R.id.greentea_checkbox);
        boolean greenTeaCup = greenTeaSelect.isChecked();

        //Figure if the user selected mocha
        CheckBox mochaSelect = (CheckBox) findViewById(R.id.mocha_checkbox);
        boolean mochaCup= mochaSelect.isChecked();

        //Figure if the users want whipped cream on top
        CheckBox creamCheckBox = (CheckBox) findViewById(R.id.cream_checkbox);
        boolean withCream = creamCheckBox.isChecked();

        //Figure if the users want chocolate on top
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean withChocolate = chocolateCheckBox.isChecked();

        //Figure if the users want vanilla on top
        CheckBox vanillaCheckBox = (CheckBox) findViewById(R.id.vanilla_checkbox);
        boolean withVanilla = vanillaCheckBox.isChecked();

        //The name of the users
        EditText userNameEditText = (EditText) findViewById(R.id.user_name);
        String userName = userNameEditText.getText().toString();

        String baseMessage = orderMessage(withCream,withChocolate,withVanilla,espressoCup,greenTeaCup,mochaCup);
        double price = calculatePrice(withCream, withChocolate, withVanilla, espressoCup, greenTeaCup, mochaCup);
        displayMessage(createOrderSummary(price,userName,baseMessage));
        composeEmail(createOrderSummary(price,userName,baseMessage));
    }

    /* Create order summary
    @param price of the order
    @param cream status
    @param chocolate status
    @return text summary
     */
    private String createOrderSummary(double price, String userName, String baseMessage) {
        String orderSummary = "Name : " + userName;
        orderSummary += "\n" + baseMessage;
        orderSummary += "\nQuantity : " + quantity;
        orderSummary += "\nTotal : $" + price + "0";
        orderSummary += "\nThank You. Have a Nice Day!";
        return orderSummary;
    }
    //display the different message when different checkbox is checked
    private String orderMessage(Boolean addedCream, Boolean addedChocolate, Boolean addedVanilla, Boolean espressoCup, Boolean greenTeaCup, Boolean mochaCup){
        String baseMessage = "Your Order :";

        //if espresso is checked
        if (espressoCup){
            baseMessage += " Espresso";
        }

        //if greentea is checked
        if (greenTeaCup){
            baseMessage += " GreenTea Latte";
        }

        //if mocha is checked
        if (mochaCup){
            baseMessage += " Dark Mocha";
        }

        //if cream is checked
        if (addedCream){
            baseMessage += ", Whipped Cream on top";
        }

        //if chocolate is checked
        if (addedChocolate){
            baseMessage += ", Chocolate on top";
        }

        //if vanilla is checked
        if (addedVanilla){
            baseMessage += ", Vanilla on Top";
        }

        return baseMessage;
    }


    /**
     * Calculates the price of the order.
     *@param addedChocolate is whether the user wants to add cream on top or not
     *@param addedCream is whether the user wants to add chocolate on top or not
     * @return the price of coffee
     */
    private double calculatePrice(Boolean addedCream, Boolean addedChocolate, Boolean addedVanilla,Boolean espressoCupB, Boolean greenTeaCupB, Boolean mochaCupB) {
       //the original price of one cup of coffee
        double basePrice = 0;

        //if espresso is selected
        if (espressoCupB){
            basePrice += 12.00;
        }

        //if latte is selected
        if (greenTeaCupB){
            basePrice += 10.00;
        }

        //if mocha is selected
        if (mochaCupB){
            basePrice += 13.00;
        }
        
        //if the cream is added
        if (addedCream){
            basePrice += 0.8;
        }
        //if the chocolate is added
        if (addedChocolate){
            basePrice += 1.2;
        }
        //if the vanilla is added
        if (addedVanilla){
            basePrice += 1.5;
        }

        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method intents order summary to email
     */
    private void composeEmail(String message){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}
