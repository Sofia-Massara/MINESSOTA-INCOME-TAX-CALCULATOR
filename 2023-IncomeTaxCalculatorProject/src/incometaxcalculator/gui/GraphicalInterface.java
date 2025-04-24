package incometaxcalculator.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.WrongFileEndingException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

import java.io.File; 

public class GraphicalInterface extends JFrame {

  private JPanel contentPane;
  private TaxpayerManager taxpayerManager = new TaxpayerManager();
  private String taxpayersTRN = new String();
  private JTextField txtTaxRegistrationNumber;
  
  public ArrayList<String> allFiles(final File folder) {
    ArrayList<String> account_files = new ArrayList<String>();

    for (File file : folder.listFiles()) {
      account_files.add(file.getPath());
      }
    return account_files;
    }
 

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          GraphicalInterface frame = new GraphicalInterface();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  public GraphicalInterface() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 450, 500);
    contentPane = new JPanel();
    contentPane.setBackground(new Color(204, 204, 204));
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | UnsupportedLookAndFeelException e2) {
      e2.printStackTrace();
    }

    JTextPane textPane = new JTextPane();
    textPane.setEditable(false);
    textPane.setBackground(new Color(153, 204, 204));
    textPane.setBounds(0, 21, 433, 441);

    JPanel fileLoaderPanel = new JPanel(new BorderLayout());
    JPanel boxPanel = new JPanel(new BorderLayout());
    JPanel taxRegistrationNumberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
    JLabel TRN = new JLabel("Give the tax registration number:");
    JTextField taxRegistrationNumberField = new JTextField(20);
    taxRegistrationNumberPanel.add(TRN);
    taxRegistrationNumberPanel.add(taxRegistrationNumberField);
    JPanel loadPanel = new JPanel(new GridLayout(1, 2));
    loadPanel.add(taxRegistrationNumberPanel);
    fileLoaderPanel.add(boxPanel, BorderLayout.NORTH);
    fileLoaderPanel.add(loadPanel, BorderLayout.CENTER);
    JCheckBox txtBox = new JCheckBox("Txt file");
    JCheckBox xmlBox = new JCheckBox("Xml file");

    txtBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        xmlBox.setSelected(false);
      }
    });

    xmlBox.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        txtBox.setSelected(false);
      }
    });
    txtBox.doClick();
    boxPanel.add(txtBox, BorderLayout.WEST);
    boxPanel.add(xmlBox, BorderLayout.EAST);

    DefaultListModel<String> taxRegisterNumberModel = new DefaultListModel<String>();

    JList<String> taxRegisterNumberList = new JList<String>(taxRegisterNumberModel);
    taxRegisterNumberList.setBackground(new Color(153, 204, 204));
    taxRegisterNumberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    taxRegisterNumberList.setSelectedIndex(0);
    taxRegisterNumberList.setVisibleRowCount(3);
    
    taxRegisterNumberList.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
          JList<String> list = (JList<String>)evt.getSource();
          if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {

              // Double-click detected
              System.out.println(list.getSelectedValue());
              String trn = list.getSelectedValue();
              int taxRegistrationNumber;
              try {
                taxRegistrationNumber = Integer.parseInt(trn);
                if (taxpayerManager.containsTaxpayer(taxRegistrationNumber)) {
                  TaxpayerData taxpayerData = new TaxpayerData(taxRegistrationNumber,
                      taxpayerManager);
                  taxpayerData.setVisible(true);
                } else {
                  JOptionPane.showMessageDialog(null, "This tax registration number isn't loaded.");
                }
              } catch (NumberFormatException e1) {
                JOptionPane.showMessageDialog(null, "You must give a tax registation number.");
              } catch (Exception e1) {
                e1.printStackTrace();
              }
          }
      }
  });
    
    JScrollPane taxRegisterNumberListScrollPane = new JScrollPane(taxRegisterNumberList);
    taxRegisterNumberListScrollPane.setSize(300, 300);
    taxRegisterNumberListScrollPane.setLocation(70, 100);
    contentPane.add(taxRegisterNumberListScrollPane);

    JButton btnLoadTaxpayer = new JButton("Load Taxpayer");
    btnLoadTaxpayer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        //System.out.println(taxpayerManager.getTaxpayerHashmap().keySet());
               
       JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
       int returnValue = fileChooser.showOpenDialog(null);

       if (returnValue == JFileChooser.APPROVE_OPTION) {
         File selectedFile = fileChooser.getSelectedFile();
         try {
           if(selectedFile.getName().contains("_INFO")) {
             taxpayerManager.loadTaxpayer(selectedFile.getAbsolutePath());
           }
           else {
             JOptionPane.showMessageDialog(null,
                 "Invalid taxpayer info file.\n" + " Make sure to select the \"_INFO\" file.");
           }
        } catch (NumberFormatException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (WrongFileFormatException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (WrongFileEndingException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (WrongTaxpayerStatusException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (WrongReceiptKindException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        } catch (WrongReceiptDateException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
       }
              
        for(int n: taxpayerManager.getTaxpayerHashmap().keySet() ) {
          String str1 = Integer.toString(n);
          System.out.println(taxpayerManager.getTaxpayerHashmap().keySet());
          taxRegisterNumberModel.addElement(str1);                                         
          }  
      }
    });
    btnLoadTaxpayer.setBounds(0, 0, 146, 23);
    contentPane.add(btnLoadTaxpayer);


    JButton btnDeleteTaxpayer = new JButton("Delete Taxpayer");
    btnDeleteTaxpayer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        if (taxpayerManager.containsTaxpayer()) {
          String trn = JOptionPane.showInputDialog(null,
              "Give the tax registration number that you want to delete: ");
          int taxRegistrationNumber;
          try {
            taxRegistrationNumber = Integer.parseInt(trn);
            if (taxpayerManager.containsTaxpayer(taxRegistrationNumber)) {
              taxpayerManager.removeTaxpayer(taxRegistrationNumber);
              taxRegisterNumberModel.removeElement(trn);
            }
          } catch (NumberFormatException e) {

          }
        } else {
          JOptionPane.showMessageDialog(null,
              "There isn't any taxpayer loaded. Please load one first.");
        }
      }
    });
    btnDeleteTaxpayer.setBounds(145, 0, 146, 23);
    contentPane.add(btnDeleteTaxpayer);

    txtTaxRegistrationNumber = new JTextField();
    txtTaxRegistrationNumber.setEditable(false);
    txtTaxRegistrationNumber.setBackground(new Color(153, 204, 204));
    txtTaxRegistrationNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
    txtTaxRegistrationNumber.setText("Tax Registration Number:");
    txtTaxRegistrationNumber.setBounds(70, 80, 300, 20);
    contentPane.add(txtTaxRegistrationNumber);
    txtTaxRegistrationNumber.setColumns(10);

  }
}