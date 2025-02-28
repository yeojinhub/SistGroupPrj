package firstPrj;

import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * ViewForm class 에서 '결과 출력' 버튼을 누르고 사용자에게 보여지는 class
 * JTextArea에 1~6번 문제의 결과 값 출력
 */
@SuppressWarnings("serial")
public class ResultForm extends JFrame{
	
	@SuppressWarnings("unused")
	private ViewFormEvt vfe;
    
	private JTextArea resultText;
	
    private int successCount;
    private int failCount;
    
    private Map<String, Integer> mapKey = new HashMap<>();
    private int requestNum;
    private int start;
    private int end;
    
    private int abnormalCount;
    private float serviceCount;

	public ResultForm(ViewFormEvt vfe) {
		this.vfe=vfe;
		
		JPanel resultPanel=new JPanel();
		resultText=new JTextArea();
		
		resultPanel.add(resultText);
		
		add(resultPanel);
		
		String result=countKey();
		
//		3번 문제
		String resultTemp=countSuccessAndFail();
		result=result+"\n"+resultTemp;
		
		resultTemp=countAbNormal();
		result=result+"\n"+resultTemp;
		
		resultText.setText(result);
		
//		4. window 크기 설정
		setLayout(new FlowLayout());
		setBounds(100, 100, 500, 500);
		
		setVisible(true);
	} //ResultForm
	
	// Count key occurrences from a single line
	public void countKey(String temp) {
		String key = null;
		if (temp.contains("key")) {
			key = temp.substring(temp.indexOf("=") + 1, temp.indexOf("&"));
			mapKey.put(key, mapKey.get(key) != null ? mapKey.get(key) + 1 : 1);
		} //end if
	} //countKey
	
	/**
	 * 1번 문제
	 */
	public String countKey() {
		String result=null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("c:/Temp/sist_input_1.log"));
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
			result=countMaxKey();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException e) {
				e.printStackTrace();
			} //end try catch
		} //end try catch finally
		return result;
	} //countKey
	
    // Method to count the most frequent key in the log
    public String countMaxKey() {
        // Initialize the map to store key counts
        Map<String, Integer> keyCountMap = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader("c:/Temp/sist_input_1.log"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Extract the key from the log line
                String key = extractKeyFromLine(line);
                // Increment the count of this key in the map
                keyCountMap.put(key, keyCountMap.getOrDefault(key, 0) + 1);
            } //end while
        } catch (IOException e) {
            e.printStackTrace();
        } //end try catch

        // Find the key with the maximum count
        String maxKey = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : keyCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxKey = entry.getKey();
            } //end if
        } //end for

        // Update the JTextArea with the most frequent key
        String output = maxKey != null ? "1.최다 사용 키의 이름 : " + maxKey + "\n  최다 사용 횟수 : "+ maxCount 
                                      : "log의 키 값을 찾을 수 없습니다.";
        return output;  // Set the output in JTextArea
    } //countMaxKey

    // Helper method to extract key from the log line
    private String extractKeyFromLine(String line) {
        String key = null;
        if (line.contains("key")) {
            key = line.substring(line.indexOf("=") + 1, line.indexOf("&"));
        } //end if
        return key != null ? key : "Unknown";
    } //extractKeyFormLine


    /**
     * 3번 문제
     */
    public String countSuccessAndFail() {
        successCount = 0;
        failCount = 0;

        try (BufferedReader bufferReader = new BufferedReader(new FileReader("c:/Temp/sist_input_1.log"))) {
            String line;
            while ((line = bufferReader.readLine()) != null) {
            	//숫자 200이 포함된 줄 일 경우, 성공횟수 증가
                if (line.contains("[200]")) {
                    successCount++;
                } else if (line.contains("[404]")) {
                	//숫자 404이 포함된 줄 일 경우, 실패횟수 증가
                    failCount++;
                } //end else if
            } //end while
        } catch (IOException ie) {
            ie.printStackTrace();
        } //end try catch

        // 결과 값을 변수 result 에 저장하여 JLabel 에 결과값 출력
        String result = "3.서비스를 성공한 횟수(200) : " + successCount + "\n  서비스를 실패한 횟수 (404) : " + failCount;
        return result;
    } //countServiceResults
    
    /**
     * 5번 문제
     * @return
     */
    public String countAbNormal() {
    	abnormalCount = 0;
    	serviceCount = 0;

        try (BufferedReader bufferReader = new BufferedReader(new FileReader("c:/Temp/sist_input_1.log"))) {
            String line;
            while ((line = bufferReader.readLine()) != null) {
            	//줄을 읽어올 때 마다 전체서비스요청 횟수 증가
            	serviceCount++;
            	//숫자 403이 포함된 줄 일 경우, 비정상적인 요청 횟수 증가
                if (line.contains("[403]")) {
                	abnormalCount++;
                } //end if
            } //end while
        } catch (IOException ie) {
            ie.printStackTrace();
        } //end try catch
        
        // 비정상적인 요청 횟수의 비율 계산
        float abnormalRate = serviceCount > 0 ? ((float) abnormalCount / serviceCount) * 100 : 0;

        // 결과 값을 변수 result 에 저장하여 JLabel 에 결과값 출력
        String result = "5.비정상적인 요청 횟수(403) : " + abnormalCount + "\n  비정상적인 요청 비율 : " + ((int)abnormalRate)+" %";
        return result;
    } //countServiceResults
	
} //class
