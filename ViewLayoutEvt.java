package firstPrj;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

public class ViewFormEvt implements ActionListener {

    private JTextArea textArea;
    private ViewForm vl;
    private JButton openFileBtn;
	private JButton resultBtn;

    public ViewFormEvt(ViewForm vl) {
        this.vl = vl;
        this.openFileBtn = vl.getOpenFileBtn();
        this.resultBtn=vl.getResultBtn();
        this.textArea = vl.getJta();
    } //ViewLayoutEvt

    
         // 버튼 이벤트 리스너
        @Override
        public void actionPerformed(ActionEvent ae) {
            // '파일 열기' 버튼이 클릭된 경우
            if (ae.getSource() == openFileBtn) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Log Files", "log"));
                int returnValue = fileChooser.showOpenDialog(vl);
                
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    StringBuilder sb = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                        textArea.setText(sb.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } //end openFileBtn if
            
            // 여기에 전체 1~6 문제 결과값 추가하고 새로운 창에 결과값 띄우게끔 이벤트 실행
            if (ae.getSource() == resultBtn) {
            	new ResultForm();
            } //end resultBtn if
        
    } //actionPerformed
} //class
