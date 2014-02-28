package com.ericsson.drawing;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;

public class TestPanel extends JPanel {
    public static void main(String[] args)
    {
        MyFrame my=new MyFrame();   
        my.setVisible(true);
    }
}

class Mypanel extends javax.swing.JPanel
{
    public void paintComponent(Graphics g)
    {
        int begin;
        begin=0;

        while(begin!=360)
        {
          Color c=new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
          g.setColor(Color.red);
          g.drawArc(150,150,200,200,begin,36);
          g.setColor(c);
          g.fillArc(150,150,200,200,begin,36);
          begin+=36;
        }
    }
} 

class MyFrame extends javax.swing.JFrame
{
    MyFrame()
    {
       this.setTitle("变色圆圈");
       this.setSize(500,500);
       mp=new Mypanel();
       this.add(mp);
       clock.start();   
       this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
    }
    private Mypanel mp;
    javax.swing.Timer clock=new javax.swing.Timer(1000,new ActionListener(){
        public void actionPerformed(ActionEvent e)
        {
            mp.repaint();
        }
    });
}

