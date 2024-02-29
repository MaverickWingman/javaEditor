// Source code is decompiled from a .class file using FernFlower decompiler.
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Date;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class Editor extends JFrame implements KeyListener, ActionListener {
   JFrame jf;
   String[] fileMenuStr = new String[]{"New", "Open", "Save", "Save As", "Print", "Exit"};
   String[] editMenuStr = new String[]{"Cut", "Copy", "Paste", "Delete", "Select All", "Time & Date"};
   String[] runMenuStr = new String[]{"Compile", "Run"};
   JMenuItem[] fileMenu;
   JMenuItem[] editMenu;
   JMenuItem[] runMenu;
   JMenuItem formatMenu;
   byte[] b;
   String filename;
   String fname;
   FileOutputStream fos;
   FileInputStream fis;
   JScrollPane sp;
   JScrollPane sp1;
   boolean textchange;
   JMenu file;
   JMenu edit;
   JMenu help;
   JMenu format;
   JMenu run;
   JTextArea ta;
   JTextArea ta1;
   JMenuBar jmb;
   FileDialog fd;
   JColorChooser cc;
   Runtime r;

   public Editor() {
      this.fileMenu = new JMenuItem[this.fileMenuStr.length];
      this.editMenu = new JMenuItem[this.editMenuStr.length];
      this.runMenu = new JMenuItem[this.runMenuStr.length];
      this.formatMenu = new JMenuItem("Color");
      this.filename = "";
      this.fname = "";
      this.textchange = false;
      this.r = Runtime.getRuntime();
      this.jf = new JFrame();
      this.jf.setLayout((LayoutManager)null);
      this.jf.setSize(1500, 700);
      this.jf.setVisible(true);
      this.jf.setDefaultCloseOperation(3);
      this.jmb = new JMenuBar();
      this.jf.setJMenuBar(this.jmb);
      this.ta = new JTextArea(50, 50);
      this.ta.setFont(new Font("SF Pro", 0, 20));
      this.ta1 = new JTextArea(50, 50);
      this.ta1.setFont(new Font("SF Pro", 0, 20));
      this.ta.addKeyListener(this);
      this.ta1.addKeyListener(this);
      this.sp = new JScrollPane(this.ta);
      this.sp1 = new JScrollPane(this.ta1);
      this.sp.setBounds(0, 0, 1350, 520);
      this.sp1.setBounds(0, 530, 1350, 135);
      this.jf.add(this.sp);
      this.jf.add(this.sp1);
      this.file = new JMenu("Files");
      this.file.setMnemonic(70);
      this.jmb.add(this.file);
      this.edit = new JMenu("Edit");
      this.edit.setMnemonic(69);
      this.jmb.add(this.edit);
      this.format = new JMenu("Format");
      this.format.setMnemonic(79);
      this.jmb.add(this.format);
      this.run = new JMenu("Run");
      this.run.setMnemonic(82);
      this.jmb.add(this.run);
      this.help = new JMenu("Help");
      this.help.setMnemonic(72);
      this.jmb.add(this.help);
      this.format.add(this.formatMenu);
      this.formatMenu.addActionListener(this);

      int var1;
      for(var1 = 0; var1 < this.fileMenuStr.length; ++var1) {
         this.fileMenu[var1] = new JMenuItem(this.fileMenuStr[var1]);
         this.file.add(this.fileMenu[var1]);
         this.fileMenu[var1].addActionListener(this);
      }

      for(var1 = 0; var1 < this.editMenuStr.length; ++var1) {
         this.editMenu[var1] = new JMenuItem(this.editMenuStr[var1]);
         this.edit.add(this.editMenu[var1]);
         this.editMenu[var1].addActionListener(this);
      }

      for(var1 = 0; var1 < this.runMenuStr.length; ++var1) {
         this.runMenu[var1] = new JMenuItem(this.runMenuStr[var1]);
         this.run.add(this.runMenu[var1]);
         this.runMenu[var1].addActionListener(this);
      }

      this.fileMenu[0].setAccelerator(KeyStroke.getKeyStroke(78, 2));
      this.fileMenu[1].setAccelerator(KeyStroke.getKeyStroke(79, 2));
      this.fileMenu[2].setAccelerator(KeyStroke.getKeyStroke(83, 2));
      this.editMenu[0].setAccelerator(KeyStroke.getKeyStroke(88, 2));
      this.editMenu[1].setAccelerator(KeyStroke.getKeyStroke(67, 2));
      this.editMenu[2].setAccelerator(KeyStroke.getKeyStroke(86, 2));
      this.jf.setTitle("Editor : Untitled");
   }

   public static void main(String[] var0) {
      new Editor();
   }

   public void keyPressed(KeyEvent var1) {
      if (var1.getSource() == this.ta) {
         this.textchange = true;
      }

   }

   public void keyReleased(KeyEvent var1) {
   }

   public void keyTyped(KeyEvent var1) {
   }

   public void actionPerformed(ActionEvent var1) {
      int var2;
      if (var1.getActionCommand().equals("New")) {
         if (this.textchange) {
            var2 = JOptionPane.showConfirmDialog(this, "Text has been changed.\nDo you want to save it?", "Changes Save", 1, 3);
            if (var2 == 0) {
               this.saveContent();
               this.ta.setText((String)null);
               this.NewButtonCoding();
            } else if (var2 == 1) {
               this.ta.setText((String)null);
               this.NewButtonCoding();
            }
         } else {
            this.ta.setText((String)null);
            this.NewButtonCoding();
         }
      } else if (var1.getActionCommand().equals("Save")) {
         this.saveContent();
      } else {
         String var10001;
         if (var1.getActionCommand().equals("Open")) {
            this.fd = new FileDialog(this, "Open a file. ", 0);
            this.fd.show();
            this.fname = this.fd.getFile();
            var10001 = this.fd.getDirectory();
            this.filename = var10001 + this.fd.getFile();
            this.jf.setTitle("Editor : " + this.fname);

            try {
               this.fis = new FileInputStream(this.filename);
               this.b = new byte[this.fis.available()];
               this.fis.read(this.b);
               this.ta.setText(new String(this.b));
               this.fis.close();
            } catch (Exception var7) {
            }

            this.textchange = false;
         } else if (var1.getActionCommand().equals("Exit")) {
            if (this.textchange) {
               var2 = JOptionPane.showConfirmDialog(this, "Text has been changed.\nDo you want to save it", "Changes Save", 1, 3);
               if (var2 == 0) {
                  this.saveContent();
                  System.exit(0);
               } else if (var2 == 1) {
                  System.exit(0);
               }
            } else {
               System.exit(0);
            }
         } else if (var1.getActionCommand().equals("Cut")) {
            this.ta.cut();
         } else if (var1.getActionCommand().equals("Paste")) {
            this.ta.paste();
         } else if (var1.getActionCommand().equals("Copy")) {
            this.ta.copy();
         } else if (var1.getActionCommand().equals("Select All")) {
            this.ta.selectAll();
         } else if (var1.getActionCommand().equals("Time & Date")) {
            this.ta.insert("" + String.valueOf(new Date()), this.ta.getSelectionStart());
         } else if (var1.getActionCommand().equals("Delete")) {
            this.ta.replaceSelection("");
         } else if (var1.getActionCommand().equals("Color")) {
            this.cc = new JColorChooser();
            JColorChooser var10000 = this.cc;
            Color var10 = JColorChooser.showDialog(this, "Select a Color", Color.black);
            this.ta.setForeground(var10);
            this.ta.setSelectedTextColor(var10);
            this.ta1.setForeground(var10);
            this.ta1.setSelectedTextColor(var10);
         } else {
            Process var3;
            BufferedReader var4;
            BufferedReader var5;
            String var6;
            String var11;
            if (var1.getActionCommand().equals("Compile")) {
               var11 = "";
               if (!this.filename.equals("")) {
                  try {
                     var3 = this.r.exec("/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/bin/javac " + this.filename);
                     var4 = new BufferedReader(new InputStreamReader(var3.getInputStream()));
                     var5 = new BufferedReader(new InputStreamReader(var3.getErrorStream()));

                     while(true) {
                        var6 = var4.readLine();
                        if (var6 == null) {
                           if (var11.equals("")) {
                              while(true) {
                                 var6 = var5.readLine();
                                 if (var6 == null) {
                                    break;
                                 }

                                 var11 = var11 + var6;
                                 var11 = var11 + "\n";
                              }
                           }

                           var4.close();
                           var5.close();
                           this.ta1.setText(var11);
                           break;
                        }

                        var11 = var11 + var6;
                        var11 = var11 + "\n";
                     }
                  } catch (Exception var9) {
                  }
               } else {
                  this.ta1.setText("Save The file before Compiling it.");
               }
            } else if (var1.getActionCommand().equals("Run")) {
               var11 = "";
               if (!this.filename.equals("")) {
                  try {
                     var10001 = this.fname;
                     var3 = this.r.exec("/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/bin/java " + var10001.substring(0, this.fname.indexOf(".")));
                     var4 = new BufferedReader(new InputStreamReader(var3.getInputStream()));
                     var5 = new BufferedReader(new InputStreamReader(var3.getErrorStream()));

                     while(true) {
                        var6 = var4.readLine();
                        if (var6 == null) {
                           if (var11.equals("")) {
                              while(true) {
                                 var6 = var5.readLine();
                                 if (var6 == null) {
                                    break;
                                 }

                                 var11 = var11 + var6;
                                 var11 = var11 + "\n";
                              }
                           }

                           var4.close();
                           var5.close();
                           this.ta1.setText(var11);
                           break;
                        }

                        var11 = var11 + var6;
                        var11 = var11 + "\n";
                     }
                  } catch (Exception var8) {
                  }
               }
            }
         }
      }

   }

   public void saveContent() {
      try {
         if (this.jf.getTitle().equals("Editor : Untitled")) {
            this.fd = new FileDialog(this, "Save File", 1);
            this.fd.show();
            String var1 = this.fd.getFile();
            if (var1 == null) {
               return;
            }

            String var10003 = "/Users/aakarshgoel/Documents/java/textpad/tution/Practice.java/AWTSwing";
            this.fos = new FileOutputStream(var10003 + this.fd.getFile());
         } else {
            this.fos = new FileOutputStream(this.filename);
         }

         this.b = new byte[this.ta.getText().length()];
         this.b = this.ta.getText().getBytes();
         String var10001 = this.fd.getDirectory();
         this.filename = var10001 + this.fd.getFile();
         this.fname = this.fd.getFile();
         this.fos.write(this.b);
         this.ta1.setText(" File has been saved. ");
         this.fos.close();
      } catch (Exception var2) {
      }

   }

   public void NewButtonCoding() {
      String var1 = JOptionPane.showInputDialog((Component)null, "Please Enter the class name", "Provide a class name", -1);
      if (var1 != null) {
         this.ta.setText("public class " + var1 + "\n{\n\tpublic static void main(String[]s)\n\t{\n   \n\t}\n}");
         this.jf.setTitle("Editor : Untitled");
      }
   }
}
