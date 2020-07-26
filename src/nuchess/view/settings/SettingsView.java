package nuchess.view.settings;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.border.BevelBorder;

import nuchess.view.View;

public class SettingsView implements View
{
	private JPanel panel, container, sizeSettingSpring, gapSettingSpring;
	private SpringLayout layout;
	private ArrayList<JPanel> settingList;
	
	public SettingsView(int width, int height, int horizontalSize, int verticalSize, int borderGap, int verticalGap)
	{
		initComponents(width, height, horizontalSize, verticalSize, borderGap, verticalGap);
		putConstraints();
		initListeners();
		addComponents();
	}
	
	private void initComponents(int width, int height, int horizontalSize, int verticalSize, int borderGap, int verticalGap)
	{
		panel = new JPanel();
		container = new JPanel();
		sizeSettingSpring = new JPanel();
		gapSettingSpring = new JPanel();
		settingList = new ArrayList<JPanel>();
		
		sizeSettingSpring.setPreferredSize(new Dimension(horizontalSize, verticalSize));
		gapSettingSpring.setPreferredSize(new Dimension(borderGap, verticalGap));
		panel.setPreferredSize(new Dimension(width, height));
		container.setBackground(Color.BLUE);
	}
	
	private void putConstraints()
	{
		layout = new SpringLayout();
		panel.setLayout(layout);
		
		SpringLayout containerLayout = new SpringLayout();
		containerLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, panel, 0, SpringLayout.HORIZONTAL_CENTER, container);
		containerLayout.putConstraint(SpringLayout.VERTICAL_CENTER, panel, 0, SpringLayout.VERTICAL_CENTER, container);
		container.setLayout(containerLayout);
		container.add(panel);
		
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	}
	
	private void initListeners()
	{
		
	}
	
	private void addComponents()
	{
		
	}
	
	public void addSetting(String settingName, Setting setting)
	{
		JPanel settingPanel = getSettingPanel(settingName, setting);
		if(settingList.isEmpty())
		{
			layout.putConstraint(SpringLayout.NORTH, settingPanel, Spring.width(gapSettingSpring), SpringLayout.NORTH, panel);
		}
		else
		{
			layout.putConstraint(SpringLayout.NORTH, settingPanel, Spring.height(gapSettingSpring), SpringLayout.SOUTH, settingList.get(settingList.size() - 1));
		}
		layout.putConstraint(SpringLayout.EAST, settingPanel, Spring.minus(Spring.width(gapSettingSpring)), SpringLayout.EAST, panel);
		layout.putConstraint(SpringLayout.WEST, settingPanel, Spring.width(gapSettingSpring), SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.SOUTH, settingPanel, Spring.height(sizeSettingSpring), SpringLayout.NORTH, settingPanel);
		settingList.add(settingPanel);
		panel.add(settingPanel);
	}
	
	private JPanel getSettingPanel(String settingName, Setting setting)
	{
		setting.setBackground(Color.GREEN);
		
		JPanel settingPanel = new JPanel();
		JLabel settingTitle = new JLabel(settingName);
		SpringLayout settingPanelLayout = new SpringLayout();
		settingPanel.add(settingTitle);
		settingPanel.add(setting);
		settingPanelLayout.putConstraint(SpringLayout.WEST, settingTitle, 0, SpringLayout.WEST, settingPanel);
		settingPanelLayout.putConstraint(SpringLayout.VERTICAL_CENTER, settingTitle, 0, SpringLayout.VERTICAL_CENTER, settingPanel);
		settingPanelLayout.putConstraint(SpringLayout.EAST, setting, 0, SpringLayout.EAST, settingPanel);
		settingPanelLayout.putConstraint(SpringLayout.WEST, setting, Spring.minus(Spring.width(sizeSettingSpring)), SpringLayout.EAST, setting);
		settingPanelLayout.putConstraint(SpringLayout.NORTH, setting, 0, SpringLayout.NORTH, settingPanel);
		settingPanelLayout.putConstraint(SpringLayout.SOUTH, setting, 0, SpringLayout.SOUTH, settingPanel);
		settingPanelLayout.putConstraint(SpringLayout.VERTICAL_CENTER, setting, 0, SpringLayout.VERTICAL_CENTER, settingPanel);
		settingPanelLayout.putConstraint(SpringLayout.EAST, settingTitle, 0, SpringLayout.WEST, setting);
		settingPanel.setLayout(settingPanelLayout);
		settingPanel.setBackground(Color.RED);
		return settingPanel;
	}
	
	@Override
	public void close()
	{
		
	}

	@Override
	public void saveGraphicsAs()
	{
		
	}

	@Override
	public JPanel getPanel()
	{
		return container;
	}

	@Override
	public String getTitle()
	{
		return "Settings View";
	}
}
