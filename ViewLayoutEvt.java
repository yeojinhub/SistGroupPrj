package firstPrj;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

public class ViewLayoutEvt implements ActionListener {

    private JTextArea textArea;
    private ViewLayout vl;
    private JButton openFileBtn;
	private JButton resultBtn;
    private JButton mostKeyBtn;
    private JButton successOrFailBtn;
    private Map<String, Integer> mapKey = new HashMap<>();
    private int requestNum;
    private int start;
    private int end;
    private boolean reportFlag;
    
    private int successCount;
    private int failureCount;

    public ViewLayoutEvt(ViewLayout vl) {
        this.vl = vl;
        this.openFileBtn = vl.getOpenFileBtn();
        this.mostKeyBtn = vl.getMostKeyBtn();
        this.successOrFailBtn=vl.getSuccessOrFailBtn();
        this.textArea = vl.getJta();
    }

    // Method to count the most frequent key in the log
    public void countMaxKey() {
        // Initialize the map to store key counts
        Map<String, Integer> keyCountMap = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader("c:/dev/temp/sist_input_1.log"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Extract the key from the log line
                String key = extractKeyFromLine(line);
                // Increment the count of this key in the map
                keyCountMap.put(key, keyCountMap.getOrDefault(key, 0) + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Find the key with the maximum count
        String maxKey = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : keyCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxKey = entry.getKey();
            }
        }

        // Update the JTextArea with the most frequent key
        String output = maxKey != null ? "Most Frequent Key: " + maxKey + " with " + maxCount + " occurrences" 
                                      : "No keys found in the log.";
        textArea.setText(output);  // Set the output in JTextArea
    }

    // Helper method to extract key from the log line
    private String extractKeyFromLine(String line) {
        String key = null;
        if (line.contains("key")) {
            key = line.substring(line.indexOf("=") + 1, line.indexOf("&"));
        }
        return key != null ? key : "Unknown";
    }

    // Count key occurrences from a single line
    public void countKey(String temp) {
        String key = null;
        if (temp.contains("key")) {
            key = temp.substring(temp.indexOf("=") + 1, temp.indexOf("&"));
            mapKey.put(key, mapKey.get(key) != null ? mapKey.get(key) + 1 : 1);
        }
    }

    public void countServiceResults() {
        successCount = 0;
        failureCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("c:/dev/temp/sist_input_1.log"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("[200]")) {
                    successCount++;
                } else if (line.contains("[404]")) {
                    failureCount++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the result in the JTextArea
        String result = "Success Count (200): " + successCount + "\nFailure Count (404): " + failureCount;
        textArea.setText(result);
    }

    
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
            
            if (ae.getSource() == resultBtn) {
//            	여기에 전체 1~6 버튼 기능 추가하고 JLabel 창에 결과값 띄우게끔 이벤트 실행
            } //end resultBtn if
            
        // 'Most Key' button pressed
        if (ae.getSource() == mostKeyBtn) {
        	textArea.setText(""); 
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader("c:/dev/temp/sist_input_1.log"));
                String temp;
                while ((temp = br.readLine()) != null) {
                    requestNum++;
                    countKey(temp);
                    if (start != 0 && end != 0) {
                        if (requestNum >= start && requestNum <= end) {
                            countKey(temp);
                        } //end 2nd if
                    } //end 1st if
                } //end while
                // Call countMaxKey() after reading the log
                countMaxKey();
                reportFlag = true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } //end try catch
            } //end try catch finally
        } //end if mostKeyBtn
        
        if (ae.getSource() == successOrFailBtn) {
        	textArea.setText("");
           countServiceResults();
        } //end if successOrFailBtn
        
    } //actionPerformed
} //class