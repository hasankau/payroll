/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Home;

import DBClasses.DB_query;
import DBClasses.DB_query_payroll;
import DBClasses.table_update;
import Data.Employee;
import UIDesign.MajorFrame;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

/**
 *
 * @author HASANKA
 */
public class payment extends MajorFrame {

    DB_query db = new DB_query();
    Vector desig = new Vector();

    /**
     * Creates new form payment
     */
    public payment() {
        initComponents();
        desig = db.loadDesignations();
        searchEmployees("%");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        fromDate.setSelectedDate(cal);
    }

    String fromd, toda;
    
    
    private void selectEmployee(int empn) {
        Employee e = db.getEmployee(empn);
        fromd = fromDate.getSelectedDate().toString();
        toda = toDate.getSelectedDate().toString();
        
        empno.setText(e.getEmpno());
        empname.setText(e.getFirstname() + " " + e.getLastnme());
        design.setText(desig.get(Integer.parseInt(e.getDesignation())).toString());
        basic.setText(e.getBasicsalary() + "");

    }

    private void searchEmployees(String wildcard) {
        String sql = "SELECT emp_no, first_name, last_name FROM employee where (first_name ilike '" + wildcard + "' or last_name ilike '" + wildcard + "' or nic ilike '" + wildcard + "')";
        table_update.getInstance().update_table(sql, emptable);
    }
    final BigDecimal twofourty = new BigDecimal("240.0");
    final BigDecimal normalOTmul = new BigDecimal("1.5");
    final BigDecimal doubleOTmul = new BigDecimal("2.0");
    final BigDecimal attendanceall = new BigDecimal("3000.0");
    int workdayspermonth = 20;
    int empworkdays = 20;

    BigDecimal basicsal;
    BigDecimal grosssal;
    BigDecimal taxsal;
    BigDecimal netsalary;
    BigDecimal normalOTVal;
    BigDecimal doubleOTVal;

    private void initCreateSalary() {
        Date frd = fromDate.getSelectedDate().getTime();
        Date tod = toDate.getSelectedDate().getTime();
        DB_query_payroll dbp = new DB_query_payroll();
        Data.OT ot = dbp.getOvertime(Integer.parseInt(empno.getText()), frd.toString(), tod.toString());

        nothours.setText(ot.getNormalOt().toPlainString());
        dothours.setText(ot.getDoubleOt().toPlainString());
        basicsal = new BigDecimal(basic.getText());
        
        

        //OT cal normal
        {
            BigDecimal noth = new BigDecimal(nothours.getText());
            normalOTVal = basicsal.divide(twofourty).multiply(normalOTmul).multiply(noth);
            notval.setText(normalOTVal.toPlainString());
        }
        //OT cal double

        {
            BigDecimal doth = new BigDecimal(dothours.getText());
            doubleOTVal = basicsal.divide(twofourty).multiply(doubleOTmul).multiply(doth);
            dotval.setText(doubleOTVal.toPlainString());
        }

        {
            //Attendance allowance
            if (workdayspermonth == empworkdays) {
                atal.setText(attendanceall.toPlainString());

            }
            //gross salary
            BigDecimal att = new BigDecimal(atal.getText());
            BigDecimal all = new BigDecimal(al.getText());
            grosssal = basicsal.add(normalOTVal).add(doubleOTVal).add(att).add(all);
            gross.setText(grosssal.toPlainString());
        }

        //taxable salary
        {
            BigDecimal nonc = new BigDecimal(noncb.getText());
            BigDecimal meds = new BigDecimal(medsc.getText());
            taxsal = grosssal.add(nonc).add(meds);
            taxa.setText(taxsal.toPlainString());
        }

        //deductions
        {
            BigDecimal payetax = new BigDecimal(payt.getText());
            BigDecimal proper = new BigDecimal(provper.getText());

            BigDecimal listded = new BigDecimal(lisded.getText());
            BigDecimal festded = new BigDecimal(fesded.getText());
            BigDecimal overpay = new BigDecimal(ovepay.getText());
            BigDecimal salaryad = new BigDecimal(salad.getText());
            BigDecimal bicloans = new BigDecimal(bicloa.getText());

            BigDecimal provi = basicsal.divide(new BigDecimal("100")).multiply(proper);
            provval.setText(provi.toPlainString());

            netsalary = taxsal.subtract(payetax)
                    .subtract(provi)
                    .subtract(listded)
                    .subtract(festded)
                    .subtract(overpay)
                    .subtract(salaryad)
                    .subtract(bicloans);
            netsal.setText(netsalary.toPlainString());

        }

        //E.P.F/E.T.F cal
        {
            BigDecimal epfpercent = new BigDecimal(epfper.getText());
            BigDecimal epf = basicsal.divide(new BigDecimal("100")).multiply(epfpercent);
            epfval.setText(epf.toPlainString());

            BigDecimal etfpercent = new BigDecimal(etfper.getText());
            BigDecimal etf = basicsal.divide(new BigDecimal("100")).multiply(etfpercent);
            etfval.setText(etf.toPlainString());
        }
    }

    private void calGrossSalary(JTextField textf) {
        if (textf.getText().length() == 0) {
            textf.setText("0.0");
        }
        BigDecimal att = new BigDecimal(atal.getText());
        BigDecimal all = new BigDecimal(al.getText());
        grosssal = basicsal.add(normalOTVal).add(doubleOTVal).add(att).add(all);
        gross.setText(grosssal.toPlainString());
    }

    private void calTaxableSalary(JTextField textf) {
        if (textf.getText().length() == 0) {
            textf.setText("0.0");
        }
        BigDecimal nonc = new BigDecimal(noncb.getText());
        BigDecimal meds = new BigDecimal(medsc.getText());
        taxsal = grosssal.add(nonc).add(meds);
        taxa.setText(taxsal.toPlainString());
    }

    private void calNetSalary(JTextField textf) {
        if (textf.getText().length() == 0) {
            textf.setText("0.0");
        }
        BigDecimal payetax = new BigDecimal(payt.getText());

        BigDecimal listded = new BigDecimal(lisded.getText());
        BigDecimal festded = new BigDecimal(fesded.getText());
        BigDecimal overpay = new BigDecimal(ovepay.getText());
        BigDecimal salaryad = new BigDecimal(salad.getText());
        BigDecimal bicloans = new BigDecimal(bicloa.getText());

        BigDecimal provi = new BigDecimal(provval.getText());

        netsalary = taxsal.subtract(payetax)
                .subtract(provi)
                .subtract(listded)
                .subtract(festded)
                .subtract(overpay)
                .subtract(salaryad)
                .subtract(bicloans);

        if (netsalary.compareTo(BigDecimal.ZERO) != -1) {
            textf.setBorder(new JTextField().getBorder());
            netsal.setText(netsalary.toPlainString());
        } else {
            textf.setBorder(new LineBorder(Color.red));
            JOptionPane.showMessageDialog(jPanel9, "The net salary cannot be a negative value", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        fname2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        emptable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        fromDate = new datechooser.beans.DateChooserCombo();
        jLabel15 = new javax.swing.JLabel();
        toDate = new datechooser.beans.DateChooserCombo();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        basic = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        nothours = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        dothours = new javax.swing.JTextField();
        al = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        atal = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        dotval = new javax.swing.JTextField();
        notval = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        gross = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        payt = new javax.swing.JTextField();
        provval = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        lisded = new javax.swing.JTextField();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        provper = new javax.swing.JTextField();
        fesded = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        ovepay = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        bicloa = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        salad = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        netsal = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        epfval = new javax.swing.JTextField();
        etfval = new javax.swing.JTextField();
        epfper = new javax.swing.JTextField();
        etfper = new javax.swing.JTextField();
        jLabel79 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        noncb = new javax.swing.JTextField();
        medsc = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        taxa = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jLabel83 = new javax.swing.JLabel();
        nopaydays = new javax.swing.JTextField();
        nopayded = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        empno = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        design = new javax.swing.JTextField();
        empname = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(254, 254, 254));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        fname2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fname2KeyReleased(evt);
            }
        });

        emptable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Name"
            }
        ));
        emptable.setGridColor(new java.awt.Color(255, 255, 255));
        emptable.setRowHeight(20);
        emptable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                emptableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(emptable);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Male", "Female" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Filter by");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(254, 254, 254));

        jPanel5.setBackground(new java.awt.Color(234, 240, 240));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Salary Calculation");

        jLabel14.setText("<html>Salary <strong>from</strong> date</html>");

        jLabel15.setText("<html>Salary <strong>to</strong> date</html>");

        toDate.setCurrentView(new datechooser.view.appearance.AppearancesList("Light",
            new datechooser.view.appearance.ViewAppearance("custom",
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    true,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 255),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(128, 128, 128),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(255, 0, 0),
                    false,
                    false,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                (datechooser.view.BackRenderer)null,
                false,
                true)));
    toDate.setFormat(2);

    jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
    jPanel8.setOpaque(false);

    jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    jLabel6.setText("<html><u>Incomes</u></html>");

    jLabel16.setText("Basic Salary:-");

    basic.setEditable(false);
    basic.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
    basic.setText("0.0");

    jLabel17.setText("Normal Overtime Hourly:-");

    nothours.setEditable(false);
    nothours.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    nothours.setText("0.0");

    jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel18.setText("<html><u>Hours</u></html>");

    jLabel19.setText("Double Overtime Hourly:-");

    dothours.setEditable(false);
    dothours.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    dothours.setText("0.0");

    al.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
    al.setText("0.0");
    al.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            alKeyReleased(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            alKeyTyped(evt);
        }
    });

    jLabel20.setText("Allowance:-");

    jLabel21.setText("Attendance Allowance:-");

    atal.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
    atal.setText("0.0");
    atal.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            atalKeyReleased(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            atalKeyTyped(evt);
        }
    });

    jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel22.setText("----------------------------");

    jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel23.setText("----------------------------");

    jLabel24.setForeground(new java.awt.Color(0, 153, 153));
    jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel24.setText("<html><u>OT</u></html>");

    jLabel25.setForeground(new java.awt.Color(0, 153, 153));
    jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel25.setText("<html><u>Allowances</u></html>");

    dotval.setEditable(false);
    dotval.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
    dotval.setText("0.0");

    notval.setEditable(false);
    notval.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
    notval.setText("0.0");

    jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel26.setText("<html><u>Rs</u></html>");

    gross.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    gross.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    gross.setText("0.0");

    jLabel5.setText("Gross Salary:-");

    jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel27.setText("----------------------------");

    javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
    jPanel8.setLayout(jPanel8Layout);
    jPanel8Layout.setHorizontalGroup(
        jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel8Layout.createSequentialGroup()
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(144, 144, 144)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(143, 143, 143))
        .addGroup(jPanel8Layout.createSequentialGroup()
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(basic, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGap(6, 6, 6)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(nothours, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                        .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dothours, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(6, 6, 6)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(gross, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(al, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(atal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(dotval, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(notval, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel8Layout.setVerticalGroup(
        jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel8Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(basic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(6, 6, 6)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(nothours)
                .addComponent(notval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(6, 6, 6)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(dothours)
                .addComponent(dotval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(11, 11, 11)
            .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(3, 3, 3)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(atal)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(6, 6, 6)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(al)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(6, 6, 6)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(gross, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(14, Short.MAX_VALUE))
    );

    jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
    jPanel9.setOpaque(false);

    jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    jLabel10.setText("<html><u>Deductions</u></html>");

    jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel56.setText("----------------------------");

    payt.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
    payt.setText("0.0");
    payt.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            paytKeyReleased(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            paytKeyTyped(evt);
        }
    });

    provval.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
    provval.setText("0.0");
    provval.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            provvalKeyReleased(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            provvalKeyTyped(evt);
        }
    });

    jLabel57.setText("PAYE Tax:-");

    jLabel58.setText("Provident Fund:-");

    lisded.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
    lisded.setText("0.0");
    lisded.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            lisdedKeyReleased(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            lisdedKeyTyped(evt);
        }
    });

    jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel60.setText("----------------------------");

    jLabel61.setText("LIST Deduction:-");

    provper.setEditable(false);
    provper.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    provper.setText("8");

    fesded.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
    fesded.setText("0.0");
    fesded.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            fesdedKeyReleased(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            fesdedKeyTyped(evt);
        }
    });

    jLabel63.setText("Festival Deduction:-");

    jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel64.setText("----------------------------");

    ovepay.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
    ovepay.setText("0.0");
    ovepay.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            ovepayKeyReleased(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            ovepayKeyTyped(evt);
        }
    });

    jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel65.setText("----------------------------");

    jLabel66.setText("Over Payment:-");

    jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel69.setText("----------------------------");

    jLabel70.setText("Salary Advance:-");

    bicloa.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
    bicloa.setText("0.0");
    bicloa.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            bicloaKeyReleased(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            bicloaKeyTyped(evt);
        }
    });

    jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel71.setText("----------------------------");

    jLabel72.setText("Bicycle Loans:-");

    salad.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
    salad.setText("0.0");
    salad.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            saladKeyReleased(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            saladKeyTyped(evt);
        }
    });

    jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel31.setText("<html><u>Rs</u></html>");

    jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel32.setText("<html><u>%</u></html>");

    javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
    jPanel9.setLayout(jPanel9Layout);
    jPanel9Layout.setHorizontalGroup(
        jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel9Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel72, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel70, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel66, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel63, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel61, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel58, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel57, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGap(6, 6, 6)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel71, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel69, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel65, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel64, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel60, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel56, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(provper))
                    .addGap(6, 6, 6)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bicloa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(salad, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ovepay, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(fesded, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lisded, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(provval, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(payt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(11, 11, 11))
    );
    jPanel9Layout.setVerticalGroup(
        jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel9Layout.createSequentialGroup()
            .addGap(11, 11, 11)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(4, 4, 4)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(payt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addGap(26, 26, 26)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(provper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(provval, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGap(6, 6, 6)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lisded, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(fesded, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(ovepay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(bicloa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel71, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel72, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(30, Short.MAX_VALUE))
    );

    jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
    jPanel10.setOpaque(false);

    netsal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    netsal.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    netsal.setText("0.0");

    jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel73.setText("----------------------------");

    jLabel74.setText("Net Salary:-");

    javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
    jPanel10.setLayout(jPanel10Layout);
    jPanel10Layout.setHorizontalGroup(
        jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
            .addGap(72, 72, 72)
            .addComponent(jLabel74)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(netsal, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );
    jPanel10Layout.setVerticalGroup(
        jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel10Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(netsal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
    );

    jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
    jPanel12.setOpaque(false);

    jLabel78.setText("E.P.F:-");

    epfval.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

    etfval.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

    epfper.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    epfper.setText("12");

    etfper.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    etfper.setText("3");

    jLabel79.setText("E.T.F:-");

    javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
    jPanel12.setLayout(jPanel12Layout);
    jPanel12Layout.setHorizontalGroup(
        jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel12Layout.createSequentialGroup()
            .addGap(89, 89, 89)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jLabel78)
                .addComponent(jLabel79))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(epfper, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(etfper, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(etfval, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(epfval, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
    );
    jPanel12Layout.setVerticalGroup(
        jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel12Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(epfval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(epfper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(etfval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(etfper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel79, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
    );

    jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
    jPanel13.setOpaque(false);

    jLabel80.setText("Non Cash Benefits:-");

    noncb.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
    noncb.setText("0.0");
    noncb.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            noncbKeyReleased(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            noncbKeyTyped(evt);
        }
    });

    medsc.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
    medsc.setText("0.0");
    medsc.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            medscKeyReleased(evt);
        }
        public void keyTyped(java.awt.event.KeyEvent evt) {
            medscKeyTyped(evt);
        }
    });

    jLabel81.setText("Medical Scemes:-");

    jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel28.setText("----------------------------");

    jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel30.setText("----------------------------");

    javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
    jPanel13.setLayout(jPanel13Layout);
    jPanel13Layout.setHorizontalGroup(
        jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel13Layout.createSequentialGroup()
            .addGap(27, 27, 27)
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jLabel80)
                .addComponent(jLabel81))
            .addGap(18, 18, 18)
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(medsc)
                .addComponent(noncb))
            .addContainerGap())
    );
    jPanel13Layout.setVerticalGroup(
        jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel13Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(noncb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(medsc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
    );

    jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
    jPanel14.setOpaque(false);

    taxa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
    taxa.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    taxa.setText("0.0");

    jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel77.setText("----------------------------");

    jLabel82.setText("Taxable Salary:-");

    javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
    jPanel14.setLayout(jPanel14Layout);
    jPanel14Layout.setHorizontalGroup(
        jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
            .addGap(51, 51, 51)
            .addComponent(jLabel82)
            .addGap(18, 18, 18)
            .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(taxa, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );
    jPanel14Layout.setVerticalGroup(
        jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel14Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(taxa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
    );

    jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    jPanel15.setOpaque(false);

    jLabel9.setForeground(new java.awt.Color(0, 153, 204));
    jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/info.png"))); // NOI18N
    jLabel9.setText("<html><strong>Note:-</strong>All calculations are done in LKR. | Normal OT = BASIC/240)X1.5 | Double OT = BASIC/240)X2 </html>");

    javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
    jPanel15.setLayout(jPanel15Layout);
    jPanel15Layout.setHorizontalGroup(
        jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel15Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel15Layout.setVerticalGroup(
        jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel15Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel9)
            .addContainerGap())
    );

    jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
    jPanel16.setOpaque(false);

    jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pdf.png"))); // NOI18N
    jButton1.setText("Print Salary");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });

    jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/save.png"))); // NOI18N
    jButton2.setText("Save Salary");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
    jPanel16.setLayout(jPanel16Layout);
    jPanel16Layout.setHorizontalGroup(
        jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jButton1)
            .addContainerGap())
    );
    jPanel16Layout.setVerticalGroup(
        jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel16Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton1)
                .addComponent(jButton2))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    jButton3.setText("Create Salary");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
        }
    });

    jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
    jPanel17.setOpaque(false);

    jLabel83.setText("No Pay:-");

    nopaydays.setHorizontalAlignment(javax.swing.JTextField.CENTER);

    nopayded.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

    jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel33.setText("<html><u>days</u></html>");

    javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
    jPanel17.setLayout(jPanel17Layout);
    jPanel17Layout.setHorizontalGroup(
        jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel17Layout.createSequentialGroup()
                    .addComponent(jLabel83)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(nopaydays, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(nopayded, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );
    jPanel17Layout.setVerticalGroup(
        jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 6, Short.MAX_VALUE)
            .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(nopayded, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(nopaydays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel83, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
    );

    javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
    jPanel5.setLayout(jPanel5Layout);
    jPanel5Layout.setHorizontalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(toDate, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jButton3)))
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGap(195, 195, 195))
    );
    jPanel5Layout.setVerticalGroup(
        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel5Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addGap(11, 11, 11)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton3)
                        .addComponent(toDate, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGap(11, 11, 11)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(6, 6, 6)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addContainerGap(35, Short.MAX_VALUE))
    );

    jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    jPanel6.setForeground(new java.awt.Color(204, 204, 204));
    jPanel6.setOpaque(false);

    jLabel11.setText("Emp No:-");

    jLabel12.setText("Designation");

    jLabel29.setText("Employee:-");

    javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
    jPanel6.setLayout(jPanel6Layout);
    jPanel6Layout.setHorizontalGroup(
        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel6Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel11)
            .addGap(30, 30, 30)
            .addComponent(empno, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(33, 33, 33)
            .addComponent(jLabel29)
            .addGap(30, 30, 30)
            .addComponent(empname, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(31, 31, 31)
            .addComponent(jLabel12)
            .addGap(18, 18, 18)
            .addComponent(design, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel6Layout.setVerticalGroup(
        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
            .addGap(11, 11, 11)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(design, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(11, 11, 11))
    );

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGap(0, 0, 0))
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(63, 63, 63))
    );

    jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search.gif"))); // NOI18N
    jButton5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton5ActionPerformed(evt);
        }
    });

    jPanel4.setBackground(new java.awt.Color(234, 240, 240));
    jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
    jPanel4.setForeground(new java.awt.Color(153, 0, 0));

    jLabel3.setForeground(new java.awt.Color(0, 153, 204));
    jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/info.png"))); // NOI18N
    jLabel3.setText("<html><strong>Note:-</strong> Click on a row to view employee destails</html>");

    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );
    jPanel4Layout.setVerticalGroup(
        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel4Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
            .addContainerGap())
    );

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                    .addComponent(fname2, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10, 10, 10))
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(fname2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(62, 62, 62)))
            .addContainerGap())
    );

    javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
    jPanel7.setLayout(jPanel7Layout);
    jPanel7Layout.setHorizontalGroup(
        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel7Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel7Layout.setVerticalGroup(
        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel7Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fname2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fname2KeyReleased
        searchEmployees(fname2.getText() + "%");
    }//GEN-LAST:event_fname2KeyReleased

    private void emptableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_emptableMouseClicked
        selectEmployee(Integer.parseInt(emptable.getValueAt(emptable.getSelectedRow(), 0).toString()));
    }//GEN-LAST:event_emptableMouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        String wildcard = fname2.getText() + "%";
        String gen = jComboBox1.getSelectedItem().toString();
        if (!gen.equals("")) {
            String sql = "SELECT emp_no, first_name, last_name FROM employee where (first_name like '" + wildcard + "' or first_name like '" + wildcard + "' or last_name like '" + wildcard + "' or nic like '" + wildcard + "') and gen = '" + gen + "'";
            table_update.getInstance().update_table(sql, emptable);
        } else {
            searchEmployees(fname2.getText() + "%");
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        initCreateSalary();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void paytKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_paytKeyTyped
        int c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_PERIOD)) {
            evt.consume();
        }
    }//GEN-LAST:event_paytKeyTyped

    private void lisdedKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lisdedKeyTyped
        int c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_PERIOD)) {
            evt.consume();
        }
    }//GEN-LAST:event_lisdedKeyTyped

    private void fesdedKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fesdedKeyTyped
        int c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_PERIOD)) {
            evt.consume();
        }
    }//GEN-LAST:event_fesdedKeyTyped

    private void ovepayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ovepayKeyTyped
        int c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_PERIOD)) {
            evt.consume();
        }
    }//GEN-LAST:event_ovepayKeyTyped

    private void saladKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_saladKeyTyped
        int c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_PERIOD)) {
            evt.consume();
        }
    }//GEN-LAST:event_saladKeyTyped

    private void bicloaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bicloaKeyTyped
        int c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_PERIOD)) {
            evt.consume();
        }
    }//GEN-LAST:event_bicloaKeyTyped

    private void atalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_atalKeyTyped
        int c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_PERIOD)) {
            evt.consume();
        }
    }//GEN-LAST:event_atalKeyTyped

    private void alKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_alKeyTyped
        int c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_PERIOD)) {
            evt.consume();
        }
    }//GEN-LAST:event_alKeyTyped

    private void noncbKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_noncbKeyTyped
        int c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_PERIOD)) {
            evt.consume();
        }
    }//GEN-LAST:event_noncbKeyTyped

    private void medscKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_medscKeyTyped
        int c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_PERIOD)) {
            evt.consume();
        }
    }//GEN-LAST:event_medscKeyTyped

    private void atalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_atalKeyReleased
        calGrossSalary(atal);
        calTaxableSalary(atal);
        calNetSalary(atal);
    }//GEN-LAST:event_atalKeyReleased

    private void alKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_alKeyReleased
        calGrossSalary(al);
        calTaxableSalary(atal);
        calNetSalary(atal);
    }//GEN-LAST:event_alKeyReleased

    private void noncbKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_noncbKeyReleased
        calTaxableSalary(noncb);
        calNetSalary(noncb);
    }//GEN-LAST:event_noncbKeyReleased

    private void medscKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_medscKeyReleased
        calTaxableSalary(medsc);
        calNetSalary(medsc);
    }//GEN-LAST:event_medscKeyReleased

    private void paytKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_paytKeyReleased
        calNetSalary(payt);
    }//GEN-LAST:event_paytKeyReleased

    private void provvalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_provvalKeyReleased
        calNetSalary(provper);
    }//GEN-LAST:event_provvalKeyReleased

    private void lisdedKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lisdedKeyReleased
        calNetSalary(lisded);
    }//GEN-LAST:event_lisdedKeyReleased

    private void fesdedKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fesdedKeyReleased
        calNetSalary(fesded);
    }//GEN-LAST:event_fesdedKeyReleased

    private void ovepayKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ovepayKeyReleased
        calNetSalary(ovepay);
    }//GEN-LAST:event_ovepayKeyReleased

    private void saladKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_saladKeyReleased
        calNetSalary(salad);
    }//GEN-LAST:event_saladKeyReleased

    private void bicloaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bicloaKeyReleased
        calNetSalary(bicloa);
    }//GEN-LAST:event_bicloaKeyReleased

    private void provvalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_provvalKeyTyped
        int c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_PERIOD)) {
            evt.consume();
        }
    }//GEN-LAST:event_provvalKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DB_query_payroll dbp = new DB_query_payroll();
        dbp.insertIntoSalaryRecord(Integer.parseInt(empno.getText()), fromDate.getSelectedDate().toString(), netsal.getText());
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new payment().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField al;
    private javax.swing.JTextField atal;
    private javax.swing.JTextField basic;
    private javax.swing.JTextField bicloa;
    private javax.swing.JTextField design;
    private javax.swing.JTextField dothours;
    private javax.swing.JTextField dotval;
    private javax.swing.JTextField empname;
    private javax.swing.JTextField empno;
    private javax.swing.JTable emptable;
    private javax.swing.JTextField epfper;
    private javax.swing.JTextField epfval;
    private javax.swing.JTextField etfper;
    private javax.swing.JTextField etfval;
    private javax.swing.JTextField fesded;
    private javax.swing.JTextField fname2;
    private datechooser.beans.DateChooserCombo fromDate;
    private javax.swing.JLabel gross;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lisded;
    private javax.swing.JTextField medsc;
    private javax.swing.JLabel netsal;
    private javax.swing.JTextField noncb;
    private javax.swing.JTextField nopaydays;
    private javax.swing.JTextField nopayded;
    private javax.swing.JTextField nothours;
    private javax.swing.JTextField notval;
    private javax.swing.JTextField ovepay;
    private javax.swing.JTextField payt;
    private javax.swing.JTextField provper;
    private javax.swing.JTextField provval;
    private javax.swing.JTextField salad;
    private javax.swing.JLabel taxa;
    private datechooser.beans.DateChooserCombo toDate;
    // End of variables declaration//GEN-END:variables
}
