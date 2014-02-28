package com.ericsson.o2top.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class ZoomoutButton extends JButton {
	
	public ZoomoutButton(O2TopMainFrame mainFrame){
		
		setText("Zoom Out");
		setFocusable(false);
		setHorizontalTextPosition(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.BOTTOM);

		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				//TODO
			}
		});
	}

}
