package nuchess.view.fenbuilder;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

class FENSettingsView
{
	private JPanel panel;
	private SpringLayout layout;
	
	private JLabel[] fieldLabels;
	private JLabel[] errorLabels;
	private JTextField[] fields;
	
	public FENSettingsView(String... fieldNames)
	{
		panel = new JPanel();
		layout = new SpringLayout();
		panel.setLayout(layout);
		
		initComponents();
		initFields(fieldNames);
		putFieldConstraints();
		addFieldComponents();
	}
	
	private void initComponents()
	{
		panel.setPreferredSize(new Dimension(300, 300));
		panel.setBackground(Color.CYAN);
		
		
	}
	
	private void initFields(String[] fieldNames)
	{
		fieldLabels = new JLabel[fieldNames.length];
		errorLabels = new JLabel[fieldNames.length];
		fields = new JTextField[fieldNames.length];
		
		for(int i = 0; i < fieldNames.length; i++)
		{
			fieldLabels[i] = getNewFieldLabel(fieldNames[i]);
			errorLabels[i] = getNewErrorLabel();
			fields[i] = getNewJTextField();
		}
	}
	
	private void putFieldConstraints()
	{
		JLabel longestFieldLabelText = fieldLabels[0];
		
		for(int i = 1; i < fields.length; i++)
		{
			if(fieldLabels[i].getText().length() > longestFieldLabelText.getText().length())
			{
				longestFieldLabelText = fieldLabels[i];
			}
		}
		
		layout.putConstraint(SpringLayout.NORTH, errorLabels[0], 0, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.NORTH, fields[0], 0, SpringLayout.SOUTH, errorLabels[0]);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, fieldLabels[0], 0, SpringLayout.VERTICAL_CENTER, fields[0]);
		
		layout.putConstraint(SpringLayout.WEST, longestFieldLabelText, 0, SpringLayout.WEST, panel);
		if(fieldLabels[0] != longestFieldLabelText)
		{
			layout.putConstraint(SpringLayout.EAST, fieldLabels[0], 0, SpringLayout.EAST, longestFieldLabelText);
		}
		layout.putConstraint(SpringLayout.WEST, errorLabels[0], 0, SpringLayout.EAST, fieldLabels[0]);
		layout.putConstraint(SpringLayout.WEST, fields[0], 0, SpringLayout.EAST, fieldLabels[0]);
		
		layout.putConstraint(SpringLayout.EAST, fields[0], 0, SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.EAST, errorLabels[0], 0, SpringLayout.EAST, fields[0]);
		
		for(int i = 1; i < fields.length; i++)
		{
			layout.putConstraint(SpringLayout.NORTH, errorLabels[i], 0, SpringLayout.SOUTH, fields[i - 1]);
			layout.putConstraint(SpringLayout.NORTH, fields[i], 0, SpringLayout.SOUTH, errorLabels[i]);
			layout.putConstraint(SpringLayout.VERTICAL_CENTER, fieldLabels[i], 0, SpringLayout.VERTICAL_CENTER, fields[i]);
			
			if(fieldLabels[i] != longestFieldLabelText)
			{
				layout.putConstraint(SpringLayout.EAST, fieldLabels[i], 0, SpringLayout.EAST, longestFieldLabelText);
			}
			layout.putConstraint(SpringLayout.WEST, errorLabels[i], 0, SpringLayout.EAST, fieldLabels[i]);
			layout.putConstraint(SpringLayout.WEST, fields[i], 0, SpringLayout.EAST, fieldLabels[i]);
			
			layout.putConstraint(SpringLayout.EAST, fields[i], 0, SpringLayout.EAST, panel);
			layout.putConstraint(SpringLayout.EAST, errorLabels[i], 0, SpringLayout.EAST, fields[i]);
		}
	}
	
	private void addFieldComponents()
	{
		for(int i = 0; i < fields.length; i++)
		{
			panel.add(fieldLabels[i]);
			panel.add(errorLabels[i]);
			panel.add(fields[i]);
		}
	}
	
	private JLabel getNewFieldLabel(String fieldName)
	{
		JLabel fieldLabel = new JLabel();
		fieldLabel.setText(fieldName);
		return fieldLabel;
	}
	
	private JLabel getNewErrorLabel()
	{
		JLabel errorLabel = new JLabel();
		errorLabel.setForeground(Color.RED);
		errorLabel.setText("I AM ERROR!!!");
		return errorLabel;
	}
	
	private JTextField getNewJTextField()
	{
		JTextField textField = new JTextField();
		return textField;
	}
	
	public JPanel getPanel()
	{
		return panel;
	}
}
