package com.ericsson.drawing;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.*;

public class O2Object {
	private int x, y, h, w;
	private int font;
	private String n;
	public O2Object(String name){
		x = y = h = w = 0;
		font = 0;
		n = name;
	}
	public void setX(int v){
		x = v;
	}
	public void setY(int v){
		y = v;
	}
	public void setH(int v){
		h = v;
	}
	public void setW(int v){
		w = v;
	}
	public String getN(){
		return n;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getH(){
		return h;
	}
	public int getW(){
		return w;
	}
	public void setFont(){
		font = 1;
		// To be edited.
	}
	public void paint(Graphics g){
		g.drawRect(x, y, w, h);
		if ( font == 0 ){
			int length = n.length();
//			Font f = g.getFont();
//			int Font_size = f.getSize();
			String _n;
			if(length <= 10){
				_n = n;
			}else{
				_n = n.substring(0, 7) + "...";
			}
				
//			if(length > 10)
//			{
//				Font_size -= length/10;
//				if (Font_size < 5){
//					Font_size = 5;
//				}
//			}
			//g.setFont(new Font(f.getFontName(), Font_size, Font.PLAIN));
			g.drawString(_n, x, y + h/2);
		}else{
			// To be edited.
		}
	}
}
