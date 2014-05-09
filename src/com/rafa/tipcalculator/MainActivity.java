package com.rafa.tipcalculator;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final String BILL_TOTAL = "BILL_TOTAL";
	private static final String CUSTOM_PERCENT = "CUSTOM_PERCENT";

	private double currentBillTotal; // bill amount entered by user
	private int currentCustomPercent; // tip % set with the seekBar
	private EditText tip10EditText; // displays 10% tip
	private EditText total10EditText; // displays total with 10% tip
	private EditText tip15EditText; // displays 15% tip
	private EditText total15EditText; // displays total with 15% tip
	private EditText billEditText; // accepts user input for bill total
	private EditText tip20EditText; // displays 20% tip
	private EditText total20EditText; // displays total with 20% tip
	private TextView customTipTextView; // displays custom tip percentage
	private TextView tipCustomEditText; // displays custom tip amount
	private EditText totalCustomEditText; // displays total with custom tip

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		if (savedInstanceState == null) {
			currentBillTotal = 0.0; // initialize the bill amount to zero
			currentCustomPercent = 18; // initialize the custom tip to 18%
		} else {// app is being restored from memory, not executed from scratch
			currentBillTotal = savedInstanceState.getDouble(BILL_TOTAL);

			// initialize the custom tip to saved tip percent
			currentCustomPercent = savedInstanceState.getInt(CUSTOM_PERCENT);

		}

		// get references to the 10%, 15%, and 20% tip and total EditTexts
		tip10EditText = (EditText) findViewById(R.id.tip10_edit_text);
		total10EditText = (EditText) findViewById(R.id.tota10_edit_text);
		tip15EditText = (EditText) findViewById(R.id.tip15_edit_text);
		total15EditText = (EditText) findViewById(R.id.total15_edit_text);
		tip20EditText = (EditText) findViewById(R.id.tip20_edit_text);
		total20EditText = (EditText) findViewById(R.id.total20_edit_text);

		// get the TextView displaying the custom tip percentage
		customTipTextView = (TextView) findViewById(R.id.custom_tip_text_view);

		// get the custom tip and total EditTexts
		tipCustomEditText = (EditText) findViewById(R.id.tip_custom_edit_text);
		totalCustomEditText = (EditText) findViewById(R.id.total_custom_edit_text);

		// get the billEditText;
		billEditText = (EditText) findViewById(R.id.bill_edit_text);

		// billEditTextWatcher handles billEditText's onTextChanged event
		billEditText.addTextChangedListener(billEditTextWatcher);

		// get the SeekBar used to set the custom tip amount
		SeekBar customSeekBar = (SeekBar) findViewById(R.id.custom_seekbar);
		customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
	}

	// updates 10,15 and 20 percent tip EditTexts
	private void updateStandard() {
		// calculate bill total with a ten percent tip
		double tenPercentTip = currentBillTotal * .1;
		double tenPercentTotal = currentBillTotal + tenPercentTip;

		// set tipTenEditText's text to tenPercentTip
		tip10EditText.setText(String.format("%.02f", tenPercentTip));

		// set totalTenEditText's text to tenPercentTotal
		total10EditText.setText(String.format("%.02f", tenPercentTip));

		// calculate bill total with a fifteen percent tip
		double fifteenPercentTip = currentBillTotal * .15;
		double fifteenPercentTotal = currentBillTotal + fifteenPercentTip;

		// set tipFifteenEditText's text to fifteenPercentTip
		tip15EditText.setText(String.format("%.02f", fifteenPercentTip));

		// set totalFifteenEditText's text to fifteenPercentTotal
		total15EditText.setText(String.format("%.02f", fifteenPercentTotal));

		// calculate bill total with a twenty percent tip
		double twentyPercentTip = currentBillTotal * .20;
		double twentyPercentTotal = currentBillTotal + twentyPercentTip;

		// set tipTwentyEditText's text to twentyPercentTip
		tip20EditText.setText(String.format("%.02f", twentyPercentTip));

		// set totalTwentyEditText's text to twentyPercentTotal
		total20EditText.setText(String.format("%.02f", twentyPercentTotal));
	}
	
	private void updateCustom(){
		//set customTipTextView's text to match the position of seekbar
		customTipTextView.setText(currentCustomPercent + "%");
		
		//calculate the custom tip amount
		double customTipAmount = currentBillTotal * currentCustomPercent * .01;
		
		//display the tip and total bill amounts
		double customTotalAmount = currentBillTotal + customTipAmount;
		tipCustomEditText.setText(String.format("%.02f", customTipAmount));
		totalCustomEditText.setText(String.format("%.02f", customTotalAmount));
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		
		outState.putDouble(BILL_TOTAL, currentBillTotal);
		outState.putInt(CUSTOM_PERCENT, currentCustomPercent);
	}
	
	private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener(){
		
		//update currentCustomPercent, then call updateCustom
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
			//sets currentCustomPercent to position of the seekBar's thumb
			currentCustomPercent = seekBar.getProgress();
			updateCustom();
		}
		
		@Override public void onStartTrackingTouch(SeekBar seekBar){
			//end method onStartTrackingTouch
		}
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar){
			//end method onStopTrackingTouch
		}
	};//end onSeekBarChangeListener
	
	//event-handling object that responds to billEditText's event
	private TextWatcher billEditTextWatcher = new TextWatcher()
	{

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			try{
				currentBillTotal = Double.parseDouble(s.toString());
			}
			catch(NumberFormatException e){
				currentBillTotal = 0.0;
			}
			//update standard and custom tipEditTexts
			updateStandard();
			updateCustom();
		}
		
	};
	
			
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
