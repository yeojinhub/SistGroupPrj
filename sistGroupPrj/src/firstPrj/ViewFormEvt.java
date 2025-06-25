package firstPrj;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ViewFormEvt implements ActionListener {

	private ViewForm vl;
	private String selectedFilePath; // 선택한 파일
	
    private JTextArea logTextarea;
    private JTextArea resultTextarea;
    
    private JButton openFileBtn;
	private JButton resultLogBtn;
	private JButton reportSaveBtn;
	
	private List<String> results;
	
    public ViewFormEvt(ViewForm vl) {
        this.vl = vl;
        this.logTextarea = vl.getLogTextarea();
        this.resultTextarea = vl.getResultTextarea();
        this.openFileBtn = vl.getOpenFileBtn();
        this.resultLogBtn=vl.getResultLogBtn();
        this.reportSaveBtn=vl.getReportSaveBtn();
    } //ViewLayoutEvt

    public String getSelectedFilePath() {
        return selectedFilePath;
    } //getSelectedFilePath
    
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
    			selectedFilePath = selectedFile.getAbsolutePath(); // 파일 경로 저장
    			StringBuilder sb = new StringBuilder();
    			try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
    				String line;
    				while ((line = br.readLine()) != null) {
    					sb.append(line).append("\n");
    				}
    				logTextarea.setText(sb.toString());
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	} //end openFileBtn if
    	
    	results = new ResultForm(this).getResults();
    	
    	// '로그파일 분석' 버튼이 클릭된 경우
    	if (ae.getSource() == resultLogBtn) {
    		if (selectedFilePath != null) {          // 추가된 코드 파일 선택되었는지 조건 추가 
    			new ResultForm(this); // 파일 경로 전달
    			resultTextarea.setText(String.join("\n", results));
    		} else {               
    			JOptionPane.showMessageDialog(null, "파일을 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);  
    		}// end if
    	} //end resultBtn if
    	
    	// '레포트 생성' 버튼이 클릭된 경우
    	if (ae.getSource() == reportSaveBtn) {
    		if (selectedFilePath != null) {          // 추가된 코드 파일 선택되었는지 조건 추가 
    	        new ResultForm(this).reportSave(); //파일 생성 method
    	        JOptionPane.showMessageDialog(null, "파일이 저장되었습니다.");
    		} else {               
    			JOptionPane.showMessageDialog(null, "파일을 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);  
    		}// end else if
    	} //end resultBtn if
    	
    } //actionPerformed
} //class
