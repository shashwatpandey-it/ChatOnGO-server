package com.shashwat.components;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollBar;

public class MyScrollBar extends JScrollBar{

	public MyScrollBar() {
		super.setUI(new ModernScrollBarUI());
		super.setPreferredSize(new Dimension(4, 4));
		super.setUnitIncrement(15);
		super.setBackground(Color.BLACK);
		super.setForeground(Color.GREEN);
	}
}
