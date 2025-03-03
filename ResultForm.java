package firstPrj;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * ViewForm class 에서 '결과 출력' 버튼을 누르고 사용자에게 보여지는 class
 * JTextArea에 1~6번 문제의 결과 값 출력
 */
@SuppressWarnings("serial")
public class ResultForm extends JFrame{
	
	private String index;
	
	public ResultFormEvt rf;
	
    public ResultForm() {
    	JPanel panel = new JPanel(); 
    	JLabel inputIndexLabel = new JLabel("'1'을 입력하면 결과값을 출력하고, 2가 입력되면 파일을 생성합니다.");
    	JTextField indexField = new JTextField(10);
    	
    	panel.add(inputIndexLabel);
    	panel.add(indexField);
    	
    	panel.setLayout(new FlowLayout());
    	
    	add(panel);
    	
    	setBounds(100,100,500,150);
    	setVisible(true);
    	
//    	index=indexField.getText();
    	
    	indexField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
               if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
            	   switch(ke.getKeyChar()){
            	   case 1 : { 
            		   JOptionPane.showMessageDialog(null, "선택한 파일의 결과 값을 출력을 선택하셨습니다.");
            		   new ResultFormEvt(); break;
            	   } //end case 1
            	   case 2 : {
            		   JOptionPane.showMessageDialog(null, "선택한 파일의 결과 값 저장을 선택하셨습니다.");
            		   break;
            	   } //end case 2
            	   default : {
            		   JOptionPane.showMessageDialog(null, "입력된 값이 다릅니다. 다시 입력해주세요.", "경고 메세지", JOptionPane.WARNING_MESSAGE); break;
            	   } //end default
            	   } //end switch
               } //end if
            }
         });
    	
    	
    } //ViewLayoutEvt

	public String getIndex() {
		return index;
	} //getIndex
    
	
} //class
