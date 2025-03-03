package firstPrj;

import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	private String fileRute1="C:/Temp/sist_input_1.log";
	
	
    private Map<String, Integer> mapKey = new HashMap<>();
    private int requestNum;
    private int start;
    private int end;
    
    private int[] browserCount;
    
    private int successCount;
    private int failCount;
    
    private int abnormalCount;
    private int serviceCount;

    private int booksCount;
    private int errorCount;
    
	public ResultForm(ViewFormEvt vfe) {
		this.vfe=vfe;
		
		JPanel resultPanel=new JPanel();
		resultText=new JTextArea();
		
		resultPanel.add(resultText);
		
		add(resultPanel);
		
		// 각 문제의 결과를 계산하고 리스트에 저장
		List<String> results = new ArrayList<String>();
		results.add(countKey());			// 1번 문제
		results.add(countBrowser());  		// 2번 문제
		results.add(countSuccessAndFail());	// 3번 문제
//		results.add(timeMostRequest());		// 4번 문제
		results.add(countAbNormal());		// 5번 문제
		results.add(countBooksError());		// 6번 문제

		// 결과들을 줄바꿈으로 구분하여 텍스트에 설정
		resultText.setText(String.join("\n", results));
		
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
        
        try (BufferedReader br = new BufferedReader(new FileReader(fileRute1))) {
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
        String output = maxKey != null ? "1번.\n최다 사용 키의 이름 : " + maxKey + "\n최다 사용 횟수 : "+ maxCount 
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
     * 2번 문제
     * 2. 브라우저별 접속횟수, 비율
     * IE - xx (xx%)
     * Chrome - xx (xx%)
     * @return
     */
    public String countBrowser() {
    	browserCount = new int[5];
    	String[] browserNames = { "[ie]", "[firefox]", "[opera]", "[Chrome]", "[Safari]" };
    	serviceCount = 0;

        try (BufferedReader bufferReader = new BufferedReader(new FileReader(fileRute1))) {
            String line;
            while ((line = bufferReader.readLine()) != null) {
            	//줄을 읽어올 때 마다 전체서비스요청 횟수 증가
            	serviceCount++;
            	//브라우저별 요청 횟수 증가
            	for(int i=0; i<browserNames.length; i++) {
            		if(line.contains(browserNames[i])) {
            			browserCount[i]++;
            		} //end if
            	} //end for
            } //end while
        } catch (IOException ie) {
            ie.printStackTrace();
        } //end try catch
        
        // 비정상적인 요청 횟수의 비율 계산
        float[] browserRates = new float[5];
        for(int i=0; i<browserCount.length; i++) {
        	browserRates[i]=serviceCount > 0 ? ((float)browserCount[i] / serviceCount) * 100 : 0;
        } //end for
        
        // 결과 값을 변수 result 에 저장하여 JLabel 에 결과값 출력
        StringBuilder result = new StringBuilder("\n2번.\n");
        String[] browser = {"ie", "firefox", "opera", "Chrome", "Safari"};
        
        for(int i=0; i<browserCount.length; i++) {
        	result.append(browser[i]).append(" - ").append(browserCount[i]).append("(").append((int)browserRates[i]).append("%)\n");
        } //end for
        
        return result.toString();
    } //countBrowser

    /**
     * 3번 문제
     * 서비스를 성공적으로 수행한(200) 횟수,실패(404) 횟수
     */
    public String countSuccessAndFail() {
        successCount = 0;
        failCount = 0;

        try (BufferedReader bufferReader = new BufferedReader(new FileReader(fileRute1))) {
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
        String result = "3번.\n서비스를 성공한 횟수(200) : " + successCount + "\n서비스를 실패한 횟수 (404) : " + failCount;
        return result;
    } //countSuccessAndFail
    
    /**
     * 4번 - 요청이 많은 시간
     * @param logString
     * @param lineCount
     */
//    public void timeMostRequest(String[] logString, int lineCount) {
//    	//패턴 만들어서 적용, 그룹 사용
//        Map<String, Integer> timeMap = new HashMap<>();
//        Pattern timePattern = Pattern.compile(".*(\\d{2}):\\d{2}:\\d{2}.*");
//        for (int i = 0; i < lineCount; i++) {
//            Matcher matcher = timePattern.matcher(logString[i]);
//            if (matcher.matches()) { //만약 매치가 된다면 ( 시간이 정상적으로 들어온 것 ) 
//                String hour = matcher.group(1); // 첫번째 그룹(00-23)- 시간
//                timeMap.put(hour, timeMap.getOrDefault(hour, 0) + 1);
//            }// end if
//        }// end for
//        String mostTimeHour = null;
//        int maxCount = 0;
//        for (Map.Entry<String, Integer> entry : timeMap.entrySet()) {
//            if (entry.getValue() > maxCount) {
//                maxCount = entry.getValue();
//                mostTimeHour = entry.getKey();
//            }// end if
//        }// end for
//        System.out.println("\n4. 요청이 가장 많은 시간 [" + mostTimeHour + "시]\n");
//    }// timeMostRequest

    
    /**
     * 5번 문제
     * 비정상적인 요청(403)이 발생한 횟수, 비율구하기
     * @return
     */
    public String countAbNormal() {
    	abnormalCount = 0;
    	serviceCount = 0;

        try (BufferedReader bufferReader = new BufferedReader(new FileReader(fileRute1))) {
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
        String result = "5번.\n비정상적인 요청 횟수(403) : " + abnormalCount + "\n비정상적인 요청 비율 : " + ((int)abnormalRate)+" %\n";
        return result;
    } //countAbNormal
    
    /**
     * 6번 문제
     * books 에 대한 요청 URL중 에러(500)가 발생한 횟수, 비율 구하기
     * @return
     */
    public String countBooksError() {
    	booksCount = 0;
    	errorCount = 0;

        try (BufferedReader bufferReader = new BufferedReader(new FileReader(fileRute1))) {
            String line;
            while ((line = bufferReader.readLine()) != null) {
                if (line.contains("books?")) {
                	//books 문자열이 포함된 줄일 경우, 요청 키값 증가
                	booksCount++;
                	if(line.contains("[500]")) {
                		//숫자 500이 포함된 줄일 경우, 에러 횟수 증가
                		errorCount++;
                	} //end 2nd if
                } //end 1st if
            } //end while
        } catch (IOException ie) {
            ie.printStackTrace();
        } //end try catch
        
        // 에러 요청 횟수의 비율 계산
        float errorRate = booksCount > 0 ? ((float) errorCount / booksCount) * 100 : 0;

        // 결과 값을 변수 result 에 저장하여 JLabel 에 결과값 출력
        String result = "6번.\nbooks URL 요청 횟수 : " + booksCount + "\n에러 발생 횟수(500) : "+errorCount + "\n에러 발생 비율(500) : "+((int)errorRate)+" %";
        return result;
    } //countBooksError
	
} //class
